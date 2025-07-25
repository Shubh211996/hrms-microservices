package com.hrms.attendanceservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrms.attendanceservice.entity.Attendance;
import com.hrms.attendanceservice.service.AttendanceService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AttendanceController.class)
public class AttendanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AttendanceService attendanceService;

    @Autowired
    private ObjectMapper objectMapper;

    private Attendance attendance;

    @BeforeEach
    void setUp() {
        attendance = new Attendance();
        attendance.setId(1L);
        attendance.setEmployeeId(101L);
        attendance.setDate(LocalDate.of(2025, 7, 15));
    }

    @Test
    void testSaveAttendance() throws Exception {
        Mockito.when(attendanceService.saveAttendance(any(Attendance.class))).thenReturn(attendance);

        mockMvc.perform(post("/api/attendances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attendance)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(attendance.getId()))
                .andExpect(jsonPath("$.employeeId").value(attendance.getEmployeeId()));
    }

    @Test
    void testGetAllAttendances() throws Exception {
        List<Attendance> attendances = Arrays.asList(attendance);
        Mockito.when(attendanceService.getAllAttendances()).thenReturn(attendances);

        mockMvc.perform(get("/api/attendances"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(attendances.size()));
    }

    @Test
    void testGetAttendanceByIdFound() throws Exception {
        Mockito.when(attendanceService.getAttendanceById(1L)).thenReturn(attendance);

        mockMvc.perform(get("/api/attendances/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(attendance.getId()));
    }

    @Test
    void testGetAttendanceByIdNotFound() throws Exception {
        Mockito.when(attendanceService.getAttendanceById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/attendances/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAttendanceFound() throws Exception {
        Attendance updatedAttendance = new Attendance();
        updatedAttendance.setId(1L);
        updatedAttendance.setEmployeeId(102L);
        updatedAttendance.setDate(LocalDate.of(2025, 7, 16));

        Mockito.when(attendanceService.updateAttendance(eq(1L), any(Attendance.class))).thenReturn(updatedAttendance);

        mockMvc.perform(put("/api/attendances/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAttendance)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(updatedAttendance.getEmployeeId()));
    }

    @Test
    void testUpdateAttendanceNotFound() throws Exception {
        Mockito.when(attendanceService.updateAttendance(eq(99L), any(Attendance.class))).thenReturn(null);

        mockMvc.perform(put("/api/attendances/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(attendance)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteAttendance() throws Exception {
        mockMvc.perform(delete("/api/attendances/1"))
                .andExpect(status().isNoContent());
    }
}
