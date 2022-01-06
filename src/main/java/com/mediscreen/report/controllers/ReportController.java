package com.mediscreen.report.controllers;

import java.util.List;

import com.mediscreen.report.models.Note;
import com.mediscreen.report.models.Patient;
import com.mediscreen.report.services.ReportService;
import com.mediscreen.report.webClients.NoteFeignClient;
import com.mediscreen.report.webClients.PatientFeignClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    @Autowired
    private PatientFeignClient patientFeignClient;

    @Autowired
    private NoteFeignClient noteFeignClient;

    @Autowired
    private ReportService reportService;

    @GetMapping(value = "/rest/assess/{patientId}")
    ResponseEntity<String> getReport(@PathVariable("patientId") Integer patientId) {
        Patient patient = patientFeignClient.getPatientById(patientId);
        List<Note> notes = noteFeignClient.getNotesByUserId(patientId);
        String report = reportService.generateReport(patient, notes);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
