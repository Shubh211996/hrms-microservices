package com.hrms.leaveservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.hrms.leaveservice.entity.Leave;
import com.hrms.leaveservice.repository.LeaveRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LeaveServiceTest {

    @Mock
    private LeaveRepository leaveRepository;

    @InjectMocks
    private LeaveService leaveService;

    private Leave leave;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        leave = new Leave(1L, 101L, "Sick Leave", "2025-07-20", "2025-07-22", "Pending");
    }

    @Test
    void testSaveLeave() {
        when(leaveRepository.save(leave)).thenReturn(leave);
        Leave saved = leaveService.saveLeave(leave);
        assertEquals(leave.getId(), saved.getId());
        verify(leaveRepository, times(1)).save(leave);
    }

    @Test
    void testGetAllLeaves() {
        List<Leave> leaves = Arrays.asList(leave);
        when(leaveRepository.findAll()).thenReturn(leaves);
        List<Leave> result = leaveService.getAllLeaves();
        assertEquals(1, result.size());
        verify(leaveRepository, times(1)).findAll();
    }

    @Test
    void testGetLeaveById() {
        when(leaveRepository.findById(1L)).thenReturn(Optional.of(leave));
        Optional<Leave> found = leaveService.getLeaveById(1L);
        assertTrue(found.isPresent());
        assertEquals(leave.getId(), found.get().getId());
    }

    @Test
    void testUpdateLeave() {
        Leave updated = new Leave(1L, 101L, "Casual Leave", "2025-07-25", "2025-07-26", "Approved");
        when(leaveRepository.findById(1L)).thenReturn(Optional.of(leave));
        when(leaveRepository.save(any(Leave.class))).thenReturn(updated);

        Leave result = leaveService.updateLeave(1L, updated);

        assertNotNull(result);
        assertEquals("Casual Leave", result.getLeaveType());
        assertEquals("Approved", result.getStatus());
    }

    @Test
    void testDeleteLeave() {
        doNothing().when(leaveRepository).deleteById(1L);
        leaveService.deleteLeave(1L);
        verify(leaveRepository, times(1)).deleteById(1L);
    }
}
