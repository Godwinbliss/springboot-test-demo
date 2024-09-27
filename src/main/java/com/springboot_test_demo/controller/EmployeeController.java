package com.springboot_test_demo.controller;

import com.springboot_test_demo.entity.Employee;
import com.springboot_test_demo.repository.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee saveEmployee(@RequestBody Employee employee){
        return service.saveEmployee(employee);
    }

    @GetMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public List<Employee> getAllEmployees(){
        return  service.getAllEmployees();
    }

    @GetMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Integer empId){
        return service.getEmployeeById(empId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable("id") Integer empId){
        service.deleteEmployee(empId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee){
        Employee updatedEmployee  = service.updateEmployee(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

}
