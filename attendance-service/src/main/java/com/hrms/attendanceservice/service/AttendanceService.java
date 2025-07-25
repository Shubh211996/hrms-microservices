package com.hrms.attendanceservice.service;

import com.hrms.attendanceservice.entity.Attendance;
import com.hrms.attendanceservice.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Attendance getAttendanceById(Long id) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
        return optionalAttendance.orElse(null);
    }

    public Attendance updateAttendance(Long id, Attendance updatedAttendance) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
        if (optionalAttendance.isPresent()) {
            Attendance existingAttendance = optionalAttendance.get();
            existingAttendance.setEmployeeId(updatedAttendance.getEmployeeId());
            existingAttendance.setDate(updatedAttendance.getDate());
            existingAttendance.setPresent(updatedAttendance.isPresent());
            return attendanceRepository.save(existingAttendance);
        } else {
            return null;
        }
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}
