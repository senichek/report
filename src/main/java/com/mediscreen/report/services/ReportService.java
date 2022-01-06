package com.mediscreen.report.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mediscreen.report.models.Note;
import com.mediscreen.report.models.Patient;
import com.mediscreen.report.models.Risk;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ReportService {

    private static List<String> triggers = List.of("Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur",
            "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps");

    public String generateReport(Patient patient, List<Note> notes) {
        String report = "Not Available";
        int triggersAmount = calculateTriggers(notes);
        if (triggersAmount == 0) {
            report = String.format("Patient: %s %s (age %s) diabetes assessment is: %s",
                    patient.getFirstName(), patient.getLastName(), calculateAge(patient.getBirthdate()), Risk.NONE);
        }
        if (triggersAmount >= 2 && triggersAmount < 6 && calculateAge(patient.getBirthdate()) > 30) {
            report = String.format("Patient: %s %s (age %s) diabetes assessment is: %s", patient.getFirstName(),
                    patient.getLastName(), calculateAge(patient.getBirthdate()), Risk.BORDERLINE);
        }
        if (patient.getSex().equals("M") && calculateAge(patient.getBirthdate()) < 30 && triggersAmount >= 3) {
            report = String.format("Patient: %s %s (age %s) diabetes assessment is: %s", patient.getFirstName(),
                    patient.getLastName(), calculateAge(patient.getBirthdate()), Risk.IN_DANGER);
        }
        if (patient.getSex().equals("F") && calculateAge(patient.getBirthdate()) < 30 && triggersAmount >= 4) {
            report = String.format("Patient: %s %s (age %s) diabetes assessment is: %s", patient.getFirstName(),
                    patient.getLastName(), calculateAge(patient.getBirthdate()), Risk.IN_DANGER);
        }
        if (calculateAge(patient.getBirthdate()) > 30 && triggersAmount >= 6) {
            report = String.format("Patient: %s %s (age %s) diabetes assessment is: %s", patient.getFirstName(),
                    patient.getLastName(), calculateAge(patient.getBirthdate()), Risk.IN_DANGER);
        }
        if (patient.getSex().equals("M") && calculateAge(patient.getBirthdate()) < 30 && triggersAmount >= 5) {
            report = String.format("Patient: %s %s (age %s) diabetes assessment is: %s", patient.getFirstName(),
                    patient.getLastName(), calculateAge(patient.getBirthdate()), Risk.EARLY_ONSET);
        }
        if (patient.getSex().equals("F") && calculateAge(patient.getBirthdate()) < 30 && triggersAmount >= 7) {
            report = String.format("Patient: %s %s (age %s) diabetes assessment is: %s", patient.getFirstName(),
                    patient.getLastName(), calculateAge(patient.getBirthdate()), Risk.EARLY_ONSET);
        }
        if (calculateAge(patient.getBirthdate()) > 30 && triggersAmount >=8) {
            report = String.format("Patient: %s %s (age %s) diabetes assessment is: %s", patient.getFirstName(),
                    patient.getLastName(), calculateAge(patient.getBirthdate()), Risk.EARLY_ONSET);
        }
        log.info("The report was generated for patientID={}", patient.getId());
        return report;
    }

    public boolean isOlderThan30(Patient patient) {
        LocalDate now = LocalDate.now();
        int age = Period.between(patient.getBirthdate(), now).getYears();

        if (age > 30) {
            return true;
        } else {
            return false;
        }
    }

    public int calculateTriggers(List<Note> notes) {
        Set<String> matches = new HashSet<>();
        triggers.forEach(trigger -> {
            notes.forEach(note -> {
                if (note.getObservation().toLowerCase().contains(trigger.toLowerCase())) {
                    matches.add(trigger);
                }
            });
        });
        return matches.size();
    }

    public int calculateAge(LocalDate birthdate) {
        LocalDate now = LocalDate.now();
        int age = Period.between(birthdate, now).getYears();
        return age;
    }
}
