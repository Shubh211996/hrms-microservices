package com.hrms.payrollservice.service;

import com.hrms.payrollservice.entity.Payroll;
import com.hrms.payrollservice.repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository payrollRepository;

    public Payroll savePayroll(Payroll payroll) {
        return payrollRepository.save(payroll);
    }

    public List<Payroll> getAllPayrolls() {
        return payrollRepository.findAll();
    }

    public Payroll getPayrollById(Long id) {
        return payrollRepository.findById(id).orElse(null);
    }

    public Payroll updatePayroll(Long id, Payroll updatedPayroll) {
        Payroll existing = payrollRepository.findById(id).orElse(null);
        if (existing != null) {
            existing.setEmployeeId(updatedPayroll.getEmployeeId());
            existing.setSalary(updatedPayroll.getSalary());
            existing.setBonus(updatedPayroll.getBonus());
            existing.setPayDate(updatedPayroll.getPayDate());
            existing.setStatus(updatedPayroll.getStatus());
            return payrollRepository.save(existing);
        } else {
            return null;
        }
    }

    public void deletePayroll(Long id) {
        payrollRepository.deleteById(id);
    }
}
