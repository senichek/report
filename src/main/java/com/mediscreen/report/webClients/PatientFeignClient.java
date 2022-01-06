package com.mediscreen.report.webClients;

import com.mediscreen.report.models.Patient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "patients", url = "localhost:8080")
public interface PatientFeignClient {

    @GetMapping(value = "/rest/patient/{id}")
    Patient getPatientById(@PathVariable("id") Integer id);
}
