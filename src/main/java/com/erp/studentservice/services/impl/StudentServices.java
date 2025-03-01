package com.erp.studentservice.services.impl;

import com.erp.studentservice.model.Student;
import com.erp.studentservice.repositories.IStudentRepositories;
import com.erp.studentservice.services.iservices.IStudentServices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * Author: Rajib Kumer Ghosh
 */

@Service
public class StudentServices implements IStudentServices {

    private final IStudentRepositories iStudentRepositories;

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

    @Override
    public List<Student> getAllStudents() {
        return iStudentRepositories.findAll();
    }

    @Override
    public Optional<Student> getStudentById(Integer id) {
        return iStudentRepositories.findById(id);
    }

    @Override
    public Student saveStudent(Student student) {
        return iStudentRepositories.save(student);
    }

    @Override
    public Student update(Student student) {
        Optional<Student> studentOpt = iStudentRepositories.findById(student.getId());
        Student studentUpdate = studentOpt.get();
        studentUpdate.setName(student.getName());
        studentUpdate.setAge(student.getAge());
        //student.setDescription(student.getDescription());
        return iStudentRepositories.save(studentUpdate);
    }

    @Override
    public Student updatePartial(Integer id, Student student) {
        Optional<Student> studentOpt = iStudentRepositories.findById(id);
        Student studentUpdate = studentOpt.get();
        //studentUpdate.setName(student.getName());
        studentUpdate.setAge(student.getAge());
        return iStudentRepositories.save(studentUpdate);
    }

    @Override
    public void deleteStudent(Integer id) {
        iStudentRepositories.deleteById(id);
    }
}
