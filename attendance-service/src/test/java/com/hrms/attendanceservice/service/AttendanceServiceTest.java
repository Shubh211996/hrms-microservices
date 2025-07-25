package com.hrms.attendanceservice.service;

import com.hrms.attendanceservice.entity.Attendance;
import com.hrms.attendanceservice.repository.AttendanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AttendanceServiceTest {

    @InjectMocks
    private AttendanceService attendanceService;

    @Mock
    private AttendanceRepository attendanceRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAttendance() {
        Attendance attendance = new Attendance(1L, LocalDate.now(), true);
        when(attendanceRepository.save(attendance)).thenReturn(attendance);

        Attendance saved = attendanceService.saveAttendance(attendance);

        assertNotNull(saved);
        assertEquals(1L, saved.getEmployeeId());
        assertTrue(saved.isPresent());
    }

    @Test
    public void testGetAllAttendances() {
        List<Attendance> list = Arrays.asList(
            new Attendance(1L, LocalDate.now(), true),
            new Attendance(2L, LocalDate.now(), false)
        );

        when(attendanceRepository.findAll()).thenReturn(list);

        List<Attendance> result = attendanceService.getAllAttendances();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetAttendanceById() {
        Attendance attendance = new Attendance(1L, LocalDate.now(), true);
        attendance.setId(10L);

        when(attendanceRepository.findById(10L)).thenReturn(Optional.of(attendance));

        Attendance found = attendanceService.getAttendanceById(10L);

        assertNotNull(found);
        assertEquals(10L, found.getId());
    }

    @Test
    public void testUpdateAttendance() {
        Attendance existing = new Attendance(1L, LocalDate.of(2024, 1, 1), true);
        existing.setId(100L);
        Attendance updated = new Attendance(1L, LocalDate.of(2024, 1, 2), false);

        when(attendanceRepository.findById(100L)).thenReturn(Optional.of(existing));
        when(attendanceRepository.save(existing)).thenReturn(existing);

        Attendance result = attendanceService.updateAttendance(100L, updated);

        assertNotNull(result);
        assertEquals(LocalDate.of(2024, 1, 2), result.getDate());
        assertFalse(result.isPresent());
    }

    @Test
    public void testDeleteAttendance() {
        Long id = 200L;
        doNothing().when(attendanceRepository).deleteById(id);

        attendanceService.deleteAttendance(id);

        verify(attendanceRepository, times(1)).deleteById(id);
    }
}
