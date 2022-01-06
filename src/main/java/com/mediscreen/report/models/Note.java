package com.mediscreen.report.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private String id;
    private Integer ownerId;
    private String date;
    private String observation;
}
