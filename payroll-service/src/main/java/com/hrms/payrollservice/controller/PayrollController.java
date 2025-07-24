package com.hrms.payrollservice.controller;

import com.hrms.payrollservice.entity.Payroll;
import com.hrms.payrollservice.service.PayrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payrolls")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @PostMapping
    public Payroll savePayroll(@RequestBody Payroll payroll) {
        return payrollService.savePayroll(payroll);
    }

    @GetMapping
    public List<Payroll> getAllPayrolls() {
        return payrollService.getAllPayrolls();
    }

    @GetMapping("/{id}")
    public Payroll getPayrollById(@PathVariable Long id) {
        return payrollService.getPayrollById(id);
    }

    @PutMapping("/{id}")
    public Payroll updatePayroll(@PathVariable Long id, @RequestBody Payroll payroll) {
        return payrollService.updatePayroll(id, payroll);
    }

    @DeleteMapping("/{id}")
    public void deletePayroll(@PathVariable Long id) {
        payrollService.deletePayroll(id);
    }
}
