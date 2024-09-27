package com.springboot_test_demo.repository;

import com.springboot_test_demo.entity.Employee;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.repository.query.parser.Part;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    /*
    Using BDD -> behaviour Driven Test
    Approach;
    Given -> Precondition or Setup
    When -> Action or Behaviour to test
    Then -> Verify the output
     */
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp(){
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("John@gmail.com").build();
        employeeRepository.save(employee);
    }
    @AfterEach
    void tearDown(){
        employee = null;
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Junit test for saving an Employee")
    //@Disabled
    void givenEmployeeObject_whenSaved_thenReturnEmployee() {
        //given


        // When
        Employee savedEmployee = employeeRepository.save(employee);

       // Optional<Employee> employeeOptional = employeeRepository.findById(1);

         // Then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmpId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Junit test for saving employee by ID")
    @Disabled
    public void givenEmployeeObject_whenSavedById_thenReturnEmployee(){
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("John@gmail.com").build();
        //When
        employeeRepository.save(employee);
        Optional<Employee> optionalEmployee = employeeRepository.findById(1);

        // Then

        assertTrue(optionalEmployee.isPresent());
    }

    @Test
    @DisplayName("Junit test to retrieve all employees")
    @Disabled
    public void givenEmployeeObject_whenRetrievingAllEmployees_thenReturnEmployees(){
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("John@gmail.com").build();

        Employee employee1 = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("Jane@gmail.com").build();
        // When

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        List<Employee> employeeList = employeeRepository.findAll();

        //Then
       assertThat(employeeList).isNotNull();
       assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Junit test to retrieve Employee by Id ")
    @Disabled
    public void givenEmployeeObject_whenRetrievingById_thenReturnEmployeeObject(){

        //Given
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("John@gmail.com").build();

        //When
        Employee savedEmp = employeeRepository.save(employee);

        Employee emp = employeeRepository.findById(savedEmp.getEmpId()).get();

        //Then
        assertThat(emp).isNotNull();
        assertThat(emp.getEmpId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Junit test to find Employee by Email")
    @Disabled
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("John@gmail.com").build();
        //When
        Employee savedEmp = employeeRepository.save(employee);

        Employee emp = employeeRepository.findByEmail(savedEmp.getEmail()).get();

        //Then

        assertThat(emp.getEmail()).isNotNull();
        assertEquals(emp.getEmail(), "John@gmail.com");

    }

    @Test
    @DisplayName("Junit Test to update Employee")
    @Disabled
    public void givenEmployeeObject_whenSave_thenReturnUpdatedEmployee(){
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("Johndoe@gmail.com").build();
        // When
        employeeRepository.save(employee);

        Employee updateEmp = employeeRepository.findById(employee.getEmpId()).get();
        updateEmp.setEmail("Jane@gmail.com");
        updateEmp.setFirstName("Jane");
        updateEmp.setLastName("Doe");

        Employee updatedEmp = employeeRepository.save(updateEmp);

       // assertEquals(true, updatedEmp.equals(employee));
        assertThat(updatedEmp.getEmail()).isEqualTo("Jane@gmail.com");
        assertThat(updatedEmp.getFirstName()).isEqualTo("Jane");
        assertThat(updatedEmp.getLastName()).isEqualTo("Doe");

    }

    @Test
    @DisplayName("Junit test to delete an Employee")
    @Disabled
    public void givenEmployeeObject_whenDelete_ThenReturnBlank(){
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("Johndoe@gmail.com").build();
        Employee savedEmp = employeeRepository.save(employee);

        employeeRepository.delete(savedEmp);

        Optional<Employee> optional = employeeRepository.findById(savedEmp.getEmpId());

        assertThat(optional.isEmpty());


    }
    // JPQL -> Java Persistent Query Language
    @Test
    @DisplayName("Junit test to findByJPQL with index params")

    public void givenEmployeeObject_whenFirstnameAndLastname_thenReturnEmployeeObject(){
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("Johndoe@gmail.com").build();
        Employee saved = employeeRepository.save(employee);

        String firstName = "John";
        String lastName = "Doe";
        Employee object = employeeRepository.findByJPQL(firstName, lastName);

        assertThat(object).isNotNull();
        assertEquals(saved, object);
    }


}