package com.erp.studentservice.controllers;

import com.erp.studentservice.model.Student;
import com.erp.studentservice.services.iservices.IStudentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * Author: Rajib Kumer Ghosh
 */

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentServices iStudentServices;

    public StudentController(IStudentServices iStudentServices) {
        this.iStudentServices = iStudentServices;
    }
	

    /*Data JPA CRUD*/

    /*GET http://localhost:8080/api/rest/student/getallstudents*/
    @GetMapping("/getallstudents")
    public List<Student> getAllStudents() throws Exception {
        return iStudentServices.getAllStudents();
    }

    /*GET http://localhost:8080/api/rest/student/getstudentbyid/2*/
    @GetMapping("/getstudentbyid/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Integer id) throws Exception {
        Optional<Student> student = iStudentServices.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*POST http://localhost:8080/api/rest/student/savestudent
    {
        "name": "Rajib Kumer Ghosh",
        "age": 35
    }*/
    @PostMapping("/savestudent")
    public ResponseEntity<Student> saveStudent(@RequestBody Student student) {
        return new ResponseEntity<>(iStudentServices.saveStudent(student), HttpStatus.CREATED); // Returns a 201 Created response
    }

    /*PUT http://localhost:8080/api/rest/student/updatestudent
    {
        "id":"4",
        "name": "Raju Ghosh",
        "age": 38
    }*/
    @PutMapping("/update")
    public ResponseEntity<Student> update(@RequestBody Student student) throws Exception {
        return new ResponseEntity<>(iStudentServices.update(student), HttpStatus.OK);
    }

    /*PUT http://localhost:8080/api/rest/student/updatestudent
    {
        "name": "Raju Ghosh",
        "age": 38
    }*/
    @PatchMapping("/updatepartial/{id}")
    public ResponseEntity<Student> updatePartial(@PathVariable Integer id, @RequestBody Student student) throws Exception {
        return new ResponseEntity<>(iStudentServices.updatePartial(id, student), HttpStatus.OK);
    }

    @DeleteMapping("/deletestudentbyid/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) throws Exception {
        iStudentServices.deleteStudent(id);
        return ResponseEntity.noContent().build(); //Returns a 204 No Content response
    }

}
