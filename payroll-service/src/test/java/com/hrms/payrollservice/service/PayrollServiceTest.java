package com.hrms.payrollservice.service;

import com.hrms.payrollservice.entity.Payroll;
import com.hrms.payrollservice.repository.PayrollRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PayrollServiceTest {

    @InjectMocks
    private PayrollService payrollService;

    @Mock
    private PayrollRepository payrollRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePayroll() {
        Payroll payroll = new Payroll(1L, 1001L, 50000.0, 5000.0, LocalDate.now(), "PAID");
        when(payrollRepository.save(payroll)).thenReturn(payroll);

        Payroll saved = payrollService.savePayroll(payroll);

        assertNotNull(saved);
        assertEquals(1001L, saved.getEmployeeId());
        verify(payrollRepository, times(1)).save(payroll);
    }

    @Test
    void testGetAllPayrolls() {
        List<Payroll> payrollList = Arrays.asList(
                new Payroll(1L, 1001L, 50000.0, 5000.0, LocalDate.now(), "PAID"),
                new Payroll(2L, 1002L, 60000.0, 6000.0, LocalDate.now(), "UNPAID")
        );
        when(payrollRepository.findAll()).thenReturn(payrollList);

        List<Payroll> result = payrollService.getAllPayrolls();

        assertEquals(2, result.size());
        verify(payrollRepository, times(1)).findAll();
    }

    @Test
    void testGetPayrollById() {
        Payroll payroll = new Payroll(1L, 1001L, 50000.0, 5000.0, LocalDate.now(), "PAID");
        when(payrollRepository.findById(1L)).thenReturn(Optional.of(payroll));

        Payroll result = payrollService.getPayrollById(1L);

        assertNotNull(result);
        assertEquals(1001L, result.getEmployeeId());
        verify(payrollRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdatePayroll() {
        Payroll existing = new Payroll(1L, 1001L, 50000.0, 5000.0, LocalDate.now(), "PAID");
        Payroll updated = new Payroll(1L, 1002L, 60000.0, 6000.0, LocalDate.now().plusDays(1), "UNPAID");

        when(payrollRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(payrollRepository.save(any(Payroll.class))).thenReturn(updated);

        Payroll result = payrollService.updatePayroll(1L, updated);

        assertEquals(1002L, result.getEmployeeId());
        assertEquals("UNPAID", result.getStatus());
        verify(payrollRepository, times(1)).save(existing);
    }

    @Test
    void testDeletePayroll() {
        doNothing().when(payrollRepository).deleteById(1L);

        payrollService.deletePayroll(1L);

        verify(payrollRepository, times(1)).deleteById(1L);
    }
}
