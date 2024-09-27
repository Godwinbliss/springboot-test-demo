package com.springboot_test_demo.repository;

import com.springboot_test_demo.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
   // Employee updateEmployee(Employee employee);
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(int id);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(int id);
}
