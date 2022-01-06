package com.mediscreen.report.controllers;

import com.mediscreen.report.models.ReportDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {

    /* @GetMapping(value = "/rest/assess/{patientId}")
    ResponseEntity<ReportDTO> getReport(@PathVariable("patientId") Integer patientId) {
        return new ResponseEntity<>(new ReportDTO(), HttpStatus.OK);
    } */

    @GetMapping(value = "/rest/assess/{patientId}")
    ResponseEntity<String> getReport(@PathVariable("patientId") Integer patientId) {
        return new ResponseEntity<>("This is a REPORT", HttpStatus.OK);
    }
}
