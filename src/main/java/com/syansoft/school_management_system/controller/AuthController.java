package com.syansoft.school_management_system.controller;

import com.syansoft.school_management_system.entity.User;
import com.syansoft.school_management_system.event.EnrollmentEventPublisher;
import com.syansoft.school_management_system.config.JwtUtil;
import com.syansoft.school_management_system.entity.Student;
import com.syansoft.school_management_system.entity.Teacher;
import com.syansoft.school_management_system.repository.UserRepository;
import com.syansoft.school_management_system.repository.StudentRepository;
import com.syansoft.school_management_system.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private EnrollmentEventPublisher enrollmentEventPublisher; 

  
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        if ("STUDENT".equalsIgnoreCase(user.getRole().toString())) {
            Student student = new Student();
            student.setUserId(user.getId());
            student.setName(user.getUsername());
            student.setEmail(user.getEmail());
            studentRepository.save(student);
        } else if ("TEACHER".equalsIgnoreCase(user.getRole().toString())) {
            Teacher teacher = new Teacher();
            teacher.setUserId(user.getId());
            teacher.setName(user.getUsername());
            teacher.setEmail(user.getEmail());
            teacherRepository.save(teacher);
        }
        
        enrollmentEventPublisher.publishUserRegistrationEvent(user.getId().toString(), user.getUsername().toString(), user.getRole().toString());
        return ResponseEntity.ok("User registered successfully!");
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User requestUser) {
        User user = userRepository.findByUsername(requestUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(requestUser.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password!");
        }

        String jwt = jwtUtil.generateToken(user);
        return ResponseEntity.ok(jwt);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logged out successfully!");
    }
}
