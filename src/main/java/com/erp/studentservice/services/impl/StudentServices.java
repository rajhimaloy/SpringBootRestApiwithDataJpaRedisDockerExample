package com.erp.studentservice.services.impl;

import com.erp.studentservice.model.Student;
import com.erp.studentservice.repositories.IStudentRepositories;
import com.erp.studentservice.services.iservices.IStudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * Author: Rajib Kumer Ghosh
 */

@CacheConfig(cacheNames = "student")
@Service
public class StudentServices implements IStudentServices {

    @Autowired
    private IStudentRepositories iStudentRepositories;

    public StudentServices(IStudentRepositories iStudentRepositories) {
        this.iStudentRepositories = iStudentRepositories;
    }

    @Override
    public String getByName(String name) {
        Student student = new Student();
        student.setId(1001);
        student.setName(name);
        student.setAge(38);
        return "Student Details: ID = " + student.getId() + ", Name = " + student.getName() + ", Age = " + student.getAge();
    }

    @Override
    public String createStudent(Student student) {
        return "Student Created: ID = " + student.getId() + ", Name = " + student.getName() + ", Age = " + student.getAge();
    }

    @Override
    public String updateStudent(Student student) {
        return "Student Updated: ID = " + student.getId() + ", Name = " + student.getName() + ", Age = " + student.getAge();
    }

    @Override
    public String updateStudentAge(Integer id, Integer age) {
        return "Student Partial Update with ID " + id + ", updated field: " + age;
    }

    @Override
    public String updateStudentPartial(Integer id, Map<String, Object> student) {
        return "Student with ID " + id + " updated fields: " + student;
    }

    @Override
    public String deleteStudent(Student student) {
        return "Student Deleted: ID = " + student.getId() + ", Name = " + student.getName() + ", Age = " + student.getAge();
    }



    /*Data JPA CRUD*/

    // Retrieve all students and cache the result
    @Cacheable(value = "students")
    @Override
    public List<Student> getAllStudents() throws Exception {
        return iStudentRepositories.findAll();
    }

    // Retrieve an student by ID and cache the result
    @Cacheable(value = "student", key = "#id")
    @Override
    public Optional<Student> getStudentById(Integer id) throws Exception {
        return Optional.ofNullable(iStudentRepositories.findById(id).orElseThrow(() -> new Exception("Student Not Found")));
    }

    // Create a new student and evict the cache for student
    //@CacheEvict(cacheNames = "students", allEntries = true)
    @CachePut(value = "students", key = "#student.id")
    @Override
    public Student saveStudent(Student student) {
        return iStudentRepositories.save(student);
    }

    // Update an existing student and manage cache eviction
    @CachePut(value = "students", key = "#student.id")
    @Override
    public Student update(Student student) throws Exception {
        Optional<Student> existingStudent = Optional.ofNullable(iStudentRepositories.findById(student.getId()).orElseThrow(() -> new Exception("Student Not Found")));
        Student studentUpdate = existingStudent.get();
        studentUpdate.setName(student.getName());
        studentUpdate.setAge(student.getAge());
        //student.setDescription(student.getDescription());
        return iStudentRepositories.save(studentUpdate);
    }

    // Update an existing student and manage cache eviction
    @CachePut(value = "students", key = "#id")
    @Override
    public Student updatePartial(Integer id, Student student) throws Exception {
        Optional<Student> existingStudent = Optional.ofNullable(iStudentRepositories.findById(id).orElseThrow(() -> new Exception("Student Not Found")));
        Student studentUpdate = existingStudent.get();
        //studentUpdate.setName(student.getName());
        studentUpdate.setAge(student.getAge());
        return iStudentRepositories.save(studentUpdate);
    }

    // Delete an student and manage cache eviction
    //@CacheEvict(value = "students", key = "#id") // Evicts the specific student and clears the specific student from the cache
    @CacheEvict(cacheNames = {"student", "students"}, allEntries = true, key = "#id") // Evicts the specific student and clears all students from the cache
    @Override
    public void deleteStudent(Integer id) throws Exception {
        if(!iStudentRepositories.existsById(id)) {
            throw new Exception("Student Not Found");
        }

        iStudentRepositories.deleteById(id);
    }
}
