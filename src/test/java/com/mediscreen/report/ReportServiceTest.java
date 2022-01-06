package com.mediscreen.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.mediscreen.report.models.Note;
import com.mediscreen.report.services.ReportService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReportServiceTest {

    @Autowired
    ReportService reportService;

    @Test
    public void calculateTriggersTest() {
       /*  Just for reference: 
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur",
            "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps"; 
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

        assertEquals(5, actual);
    }

}
