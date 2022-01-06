package com.mediscreen.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import com.mediscreen.report.models.Note;
import com.mediscreen.report.models.Patient;
import com.mediscreen.report.services.ReportService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    ReportService reportService;

    @Test
    public void generateReportTest() {
        // Risk level: NONE
        Patient riskNone = new Patient();
        riskNone.setFirstName("John");
        riskNone.setLastName("Doe");
        riskNone.setBirthdate(LocalDate.parse("2002-01-01"));
        riskNone.setSex("M");
        Note noteRiskNone = new Note();
        noteRiskNone.setObservation("Just a note without triggers.");
        assertEquals("Patient: John Doe (age 20) diabetes assessment is: NONE",
                reportService.generateReport(riskNone, List.of(noteRiskNone)));

        // Risk level: BORDERLINE
        Patient riskBorderline = new Patient();
        riskBorderline.setFirstName("John");
        riskBorderline.setLastName("Doe");
        riskBorderline.setBirthdate(LocalDate.parse("1982-01-01"));
        riskBorderline.setSex("M");
        Note noteRiskBorderline = new Note();
        noteRiskBorderline.setObservation("Note with 2 triggers: Microalbumine, Rechute");
        assertEquals("Patient: John Doe (age 40) diabetes assessment is: BORDERLINE",
                reportService.generateReport(riskBorderline, List.of(noteRiskBorderline)));

        // Risk level: IN_DANGER
        Patient youngerThan30MaleInDanger = new Patient();
        youngerThan30MaleInDanger.setFirstName("John");
        youngerThan30MaleInDanger.setLastName("Doe");
        youngerThan30MaleInDanger.setBirthdate(LocalDate.parse("2002-01-01"));
        youngerThan30MaleInDanger.setSex("M");
        Note noteYoungerThan30MaleInDanger = new Note();
        noteYoungerThan30MaleInDanger.setObservation("Note with 3 triggers: Microalbumine, Rechute, Vertige");
        assertEquals("Patient: John Doe (age 20) diabetes assessment is: IN_DANGER",
                reportService.generateReport(youngerThan30MaleInDanger, List.of(noteYoungerThan30MaleInDanger)));

        Patient youngerThan30FemaleInDanger = new Patient();
        youngerThan30FemaleInDanger.setFirstName("Jane");
        youngerThan30FemaleInDanger.setLastName("Doe");
        youngerThan30FemaleInDanger.setBirthdate(LocalDate.parse("2002-01-01"));
        youngerThan30FemaleInDanger.setSex("F");
        Note noteYoungerThan30FemaleInDanger = new Note();
        noteYoungerThan30FemaleInDanger
                .setObservation("Note with 4 triggers: Microalbumine, Rechute, Vertige, Réaction");
        assertEquals("Patient: Jane Doe (age 20) diabetes assessment is: IN_DANGER",
                reportService.generateReport(youngerThan30FemaleInDanger, List.of(noteYoungerThan30FemaleInDanger)));

        Patient olderThan30InDanger = new Patient();
        olderThan30InDanger.setFirstName("John");
        olderThan30InDanger.setLastName("Doe");
        olderThan30InDanger.setBirthdate(LocalDate.parse("1982-01-01"));
        olderThan30InDanger.setSex("M");
        Note noteOlderThan30InDanger = new Note();
        noteOlderThan30InDanger
                .setObservation("Note with 6 triggers: Microalbumine, Rechute, Vertige, Réaction, Cholestérol, Poids");
        assertEquals("Patient: John Doe (age 40) diabetes assessment is: IN_DANGER",
                reportService.generateReport(olderThan30InDanger, List.of(noteOlderThan30InDanger)));

        // Risk level: EARLY_ONSET
        Patient youngerThan30MaleEarlyOnSet = new Patient();
        youngerThan30MaleEarlyOnSet.setFirstName("John");
        youngerThan30MaleEarlyOnSet.setLastName("Doe");
        youngerThan30MaleEarlyOnSet.setBirthdate(LocalDate.parse("2002-01-01"));
        youngerThan30MaleEarlyOnSet.setSex("M");
        Note noteYoungerThan30MaleEarlyOnSet = new Note();
        noteYoungerThan30MaleEarlyOnSet
                .setObservation("Note with 5 triggers: Microalbumine, Rechute, Vertige, Réaction, Cholestérol");
        assertEquals("Patient: John Doe (age 20) diabetes assessment is: EARLY_ONSET",
                reportService.generateReport(youngerThan30MaleEarlyOnSet, List.of(noteYoungerThan30MaleEarlyOnSet)));

        Patient youngerThan30FemaleEarlyOnSet = new Patient();
        youngerThan30FemaleEarlyOnSet.setFirstName("Jane");
        youngerThan30FemaleEarlyOnSet.setLastName("Doe");
        youngerThan30FemaleEarlyOnSet.setBirthdate(LocalDate.parse("2002-01-01"));
        youngerThan30FemaleEarlyOnSet.setSex("F");
        Note noteYoungerThan30FemaleEarlyOnSet = new Note();
        noteYoungerThan30FemaleEarlyOnSet.setObservation(
                "Note with 7 triggers: Microalbumine, Rechute, Vertige, Réaction, Cholestérol, Anormal, Anticorps");
        assertEquals("Patient: Jane Doe (age 20) diabetes assessment is: EARLY_ONSET",
                reportService.generateReport(youngerThan30FemaleEarlyOnSet,
                        List.of(noteYoungerThan30FemaleEarlyOnSet)));

        Patient olderThan30EarlyOnSet = new Patient();
        olderThan30EarlyOnSet.setFirstName("John");
        olderThan30EarlyOnSet.setLastName("Doe");
        olderThan30EarlyOnSet.setBirthdate(LocalDate.parse("1982-01-01"));
        olderThan30EarlyOnSet.setSex("M");
        Note noteOlderThan30EarlyOnSet = new Note();
        noteOlderThan30EarlyOnSet
                .setObservation("Note with 8 triggers: Microalbumine, Rechute, Vertige, Réaction, Cholestérol, Taille, Anticorps, Poids");
        assertEquals("Patient: John Doe (age 40) diabetes assessment is: EARLY_ONSET",
                reportService.generateReport(olderThan30EarlyOnSet, List.of(noteOlderThan30EarlyOnSet)));

    }

    @Test
    public void calculateTriggersTest() {
        /*
         * Just for reference:
         * "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur",
         * "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps";
         */
        Note note1 = new Note();
        String observation1 = "Le patient déclare qu'il ressent beaucoup de stress au travail\nIl se plaint également que son audition est anormale dernièrement";
        note1.setObservation(observation1);

        Note note2 = new Note();
        String observation2 = "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois\nIl remarque également que son audition continue d'être anormale";
        note2.setObservation(observation2);

        Note note3 = new Note();
        String observation3 = "Tests de laboratoire indiquant une microalbumine élevée";
        note3.setObservation(observation3);

        Note note4 = new Note();
        String observation4 = "Le patient déclare que tout semble aller bien\nLe laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé\nLe patient déclare qu’il fume depuis longtemps";
        note4.setObservation(observation4);

        int actual = reportService.calculateTriggers(List.of(note1, note2, note3, note4));

        assertEquals(4, actual);
    }

    @Test
    public void isOlderThan30Test() {
        Patient older = new Patient();
        older.setBirthdate(LocalDate.parse("1982-07-28"));
        assertTrue(reportService.isOlderThan30(older));

        Patient younger = new Patient();
        younger.setBirthdate(LocalDate.parse("2002-07-28"));
        assertFalse(reportService.isOlderThan30(younger));
    }

    @Test
    public void calculateAgeTest() {
        Patient pat1 = new Patient();
        pat1.setBirthdate(LocalDate.parse("1982-07-28"));
        assertEquals(39, reportService.calculateAge(pat1.getBirthdate()));

        Patient pat2 = new Patient();
        pat2.setBirthdate(LocalDate.parse("2002-07-28"));
        assertEquals(19, reportService.calculateAge(pat2.getBirthdate()));
    }
}
