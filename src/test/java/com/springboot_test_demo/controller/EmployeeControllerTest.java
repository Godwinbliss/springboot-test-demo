package com.springboot_test_demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot_test_demo.entity.Employee;
import com.springboot_test_demo.repository.EmployeeRepository;
import com.springboot_test_demo.repository.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import  org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService service;

    private Employee employee1;
    private List<Employee> employeeList;
//    @Autowired
//    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employee1 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();

        Employee employee2 = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@gmail.com")
                .build();

       employeeList = List.of(employee1, employee2);
    }

    @AfterEach
    void tearDown() {
        // No need to manually close mocks
    }

    @Test
    @DisplayName("Junit test to save employee in controller layer")
    void givenEmployee_whenSaveEmployee_thenReturnEmployeeObject() throws Exception {
        // given
        given(service.saveEmployee(employee1)).willReturn(employee1);

        // when and then
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee1)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee1.getEmail())))
        ;
    }

    @Test
    @DisplayName("Junit test to getAllEmployees")
    void givenListOFEmployees_whenGetAllEmployees_thenReturnListEmployees() throws Exception {
        // given
        given(service.getAllEmployees()).willReturn(employeeList);

        // when and then
        mockMvc.perform(get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeList)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(employeeList.size())));

    }
    @Test
    @DisplayName("Junit test to getEmployeeById")
    void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee() throws Exception {
        // given
        employee1.setEmpId(1);
        given(service.getEmployeeById(employee1.getEmpId())).willReturn(Optional.of(employee1));

        // when and then
        mockMvc.perform(get("/api/employees/{id}", employee1.getEmpId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee1)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee1.getEmail())))
        ;

    }

    @Test
    @DisplayName("Junit test to update an employee")
    void givenEmployeeId_whenUpdateEmployee_thenReturnEmployeeObject() throws Exception {
        employee1.setEmpId(1);
        given(service.updateEmployee(employee1)).willReturn(employee1);
        mockMvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Junit test to delete an employee")
    void givenEmployeeId_whenDeleteEmployeeById_thenReturnEmployee() throws Exception {
        employee1.setEmpId(1);
        doNothing().when(service).deleteEmployee(employee1.getEmpId());
        mockMvc.perform(delete("/api/employees/{id}", employee1.getEmpId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1)))
                .andDo(print())
                .andExpect(status().isNoContent());


    }
}

