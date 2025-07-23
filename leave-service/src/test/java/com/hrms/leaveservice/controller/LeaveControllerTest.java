package com.hrms.leaveservice.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrms.leaveservice.entity.Leave;
import com.hrms.leaveservice.service.LeaveService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class LeaveControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LeaveService leaveService;

    @InjectMocks
    private LeaveController leaveController;

    private Leave leave;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(leaveController).build();

        leave = new Leave(1L, 101L, "Sick Leave", "2025-07-20", "2025-07-22", "Pending");
    }

    @Test
    void testCreateLeave() throws Exception {
        when(leaveService.saveLeave(any(Leave.class))).thenReturn(leave);

        mockMvc.perform(post("/api/leaves")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(leave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leaveType").value("Sick Leave"));
    }

    @Test
    void testGetAllLeaves() throws Exception {
        when(leaveService.getAllLeaves()).thenReturn(Arrays.asList(leave));

        mockMvc.perform(get("/api/leaves"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void testGetLeaveByIdFound() throws Exception {
        when(leaveService.getLeaveById(1L)).thenReturn(Optional.of(leave));

        mockMvc.perform(get("/api/leaves/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testGetLeaveByIdNotFound() throws Exception {
        when(leaveService.getLeaveById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/leaves/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateLeave() throws Exception {
        Leave updated = new Leave(1L, 101L, "Casual Leave", "2025-07-25", "2025-07-26", "Approved");
        when(leaveService.updateLeave(eq(1L), any(Leave.class))).thenReturn(updated);

        mockMvc.perform(put("/api/leaves/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.leaveType").value("Casual Leave"));
    }

    @Test
    void testUpdateLeave_NotFound() throws Exception {
        when(leaveService.updateLeave(eq(1L), any(Leave.class))).thenReturn(null);

        mockMvc.perform(put("/api/leaves/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(leave)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteLeave() throws Exception {
        doNothing().when(leaveService).deleteLeave(1L);

        mockMvc.perform(delete("/api/leaves/1"))
                .andExpect(status().isNoContent());
    }
}
