package com.example.etlProject.repository;

import com.example.etlProject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtlEmployeeRepository extends JpaRepository<Employee, Long> {
}
