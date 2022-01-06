package com.mediscreen.report.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mediscreen.report.models.Note;
import com.mediscreen.report.models.Patient;

import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private static List<String> triggers = List.of("Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur",
            "Fume", "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps");

    public String generateReport(Patient patient, List<Note> notes) {
        return "This is Report";
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
}
