package com.syansoft.school_management_system.service;


import com.syansoft.school_management_system.entity.Teacher;
import com.syansoft.school_management_system.repository.TeacherRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }


    @Cacheable(value = "teachers")
    public List<Teacher> getAllTeachers() {
        System.out.println("Fetching teachers from DB...");
        return teacherRepository.findAll();
    }


    @Cacheable(value = "teacher", key = "#id")
    public Optional<Teacher> getTeacherById(Long id) {
        System.out.println("Fetching teacher from DB...");
        return teacherRepository.findById(id);
    }

    @CacheEvict(value = "teachers", allEntries = true)
    public Teacher addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }


    @CacheEvict(value = {"teachers", "teacher"}, key = "#id")
    public Teacher updateTeacher(Long id, Teacher teacher) {
        teacher.setId(id);
        return teacherRepository.save(teacher);
    }

    @CacheEvict(value = {"teachers", "teacher"}, key = "#id")
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }
}

