package com.springboot_test_demo.service;

import com.springboot_test_demo.entity.Employee;
import com.springboot_test_demo.exeptions.EmailAlreadyExistException;
import com.springboot_test_demo.repository.EmployeeRepository;
import com.springboot_test_demo.repository.EmployeeService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    AutoCloseable autoCloseable;

    Employee employee, employee1;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
       // employeeRepository = mock(EmployeeRepository.class);
        employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com").build();

        employee1 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@gmail.com").build();
        employeeRepository.save(employee1);
        employeeRepository.save(employee);
        //employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    @DisplayName("Junit test for save employee method")
    void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject_success(){
       // Employee employee1 = new Employee(2,"Jane","Doe", "jane@email.com");
        //Employee savedEmployee = employeeRepository.save(employee);
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
        assertThat(employeeService.saveEmployee(employee)).isNotNull();
    }

    @Test
    @DisplayName("Junit test that throws AlreadyExistException")
    void givenExistEmail_whenSaveEmployee_thenThrowEmployeeAlreadyExistException() {
        // Given
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        // when

        assertThrows(EmailAlreadyExistException.class, () -> employeeService.saveEmployee(employee));
       // verify(employeeRepository, never()).save(any(Employee.class));

    }

    @Test
    @DisplayName("Junit test to list all employees")
    void givenListOfEmployees_whenGetAllEmployees_thenReturnAllEmployees(){
        BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));

        assertThat(employeeService.getAllEmployees()).isNotNull();

    }

    @Test
    @DisplayName("Junit test to get employee by Id")
    void givenEmployeeObject_whenGetEmployeeById_thenReturnEmployeeObject(){
        employee.setEmpId(1);
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        assertThat(employeeService.getEmployeeById(employee.getEmpId()).isPresent());
    }

    @Test
    @DisplayName("Junit Test to update Employee")
    void givenEmployeeObject_whenSaved_thenReturnEmployeeObject(){
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
//        List<Employee> list = new ArrayList<>();
        employee.setEmail("Jane@gmail.com");
        employee.setFirstName("Jane");
        employee.setLastName("Doe");

//        list.add(employee);

        Employee updatedEmployee = employeeService.updateEmployee(employee);
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee).isSameAs(employee);
        assertThat(updatedEmployee.getFirstName()).isEqualToIgnoringCase("jane");
    }

    @Test
    @DisplayName("Junit test to delete Employee")
    void givenEmployeeId_whenDeleteById_thenReturnNothing() {
//        EmployeeServiceImpl employeeService = mock(EmployeeServiceImpl.class);
//        EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
//
//        Employee employee = new Employee();
        employee.setEmpId(1);

        doNothing().when(employeeRepository).deleteById(any());

        employeeService.deleteEmployee(employee.getEmpId());

        verify(employeeRepository, times(1)).deleteById(employee.getEmpId());
    }


}