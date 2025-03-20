
package com.syansoft.school_management_system.service;

import com.syansoft.school_management_system.entity.Student;
import com.syansoft.school_management_system.repository.StudentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

   
    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @Cacheable(value = "students")
    public List<Student> getAllStudents() {
        System.out.println("Fetching students from DB...");
        return studentRepository.findAll();
    }


    @PreAuthorize("hasAuthority('STUDENT') or hasAuthority('TEACHER')")
    @Cacheable(value = "student", key = "#id")
    public Student getStudentById(Long id) {
        System.out.println("Fetching student from DB...");
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }


    @PreAuthorize("hasAuthority('TEACHER')")
    @CacheEvict(value = "students", allEntries = true)
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

 
    @PreAuthorize("hasAuthority('TEACHER')")
    @CacheEvict(value = {"students", "student"}, key = "#id")
    public Student updateStudent(Long id, Student student) {
        Student existingStudent = getStudentById(id);
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        return studentRepository.save(existingStudent);
    }


    @PreAuthorize("hasAuthority('TEACHER')")
    @CacheEvict(value = {"students", "student"}, key = "#id")
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}

