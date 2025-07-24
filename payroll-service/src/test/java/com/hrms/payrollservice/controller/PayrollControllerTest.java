package com.hrms.payrollservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrms.payrollservice.entity.Payroll;
import com.hrms.payrollservice.service.PayrollService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PayrollController.class)
public class PayrollControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PayrollService payrollService;

    @Autowired
    private ObjectMapper objectMapper;

    private Payroll payroll;

    @BeforeEach
    void setUp() {
        payroll = new Payroll(1L, 101L, 50000.0, 5000.0, LocalDate.now(), "PAID");
    }

    @Test
    void testSavePayroll() throws Exception {
        when(payrollService.savePayroll(any(Payroll.class))).thenReturn(payroll);

        mockMvc.perform(post("/api/payrolls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payroll)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(101L));
    }

    @Test
    void testGetAllPayrolls() throws Exception {
        List<Payroll> list = Arrays.asList(payroll);
        when(payrollService.getAllPayrolls()).thenReturn(list);

        mockMvc.perform(get("/api/payrolls"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(list.size()));
    }

    @Test
    void testGetPayrollById() throws Exception {
        when(payrollService.getPayrollById(1L)).thenReturn(payroll);

        mockMvc.perform(get("/api/payrolls/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("PAID"));
    }

    @Test
    void testUpdatePayroll() throws Exception {
        Payroll updated = new Payroll(1L, 101L, 60000.0, 7000.0, LocalDate.now(), "PAID");
        when(payrollService.updatePayroll(Mockito.eq(1L), any(Payroll.class))).thenReturn(updated);

        mockMvc.perform(put("/api/payrolls/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.salary").value(60000.0));
    }

    @Test
    void testDeletePayroll() throws Exception {
        mockMvc.perform(delete("/api/payrolls/1"))
                .andExpect(status().isOk());
    }
}
