package com.erp.studentservice.repositories;

import com.erp.studentservice.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Author: Rajib Kumer Ghosh
 */

@Repository
public interface IStudentRepositories extends JpaRepository<Student, Integer> {
}
