package com.springboot_test_demo.service;

import com.springboot_test_demo.entity.Employee;
import com.springboot_test_demo.exeptions.EmailAlreadyExistException;
import com.springboot_test_demo.repository.EmployeeRepository;
import com.springboot_test_demo.repository.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

     EmployeeService employeeService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        super();
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee){
        Optional<Employee> emp = employeeRepository.findByEmail(employee.getEmail());
        if(emp.isPresent()) {
            throw new EmailAlreadyExistException("An employee exist with this email" + employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(int id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }
}
