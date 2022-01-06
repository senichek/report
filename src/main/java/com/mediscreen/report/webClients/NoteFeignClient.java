package com.mediscreen.report.webClients;

import java.util.List;

import com.mediscreen.report.models.Note;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "notes", url = "localhost:8081")
public interface NoteFeignClient {

    @GetMapping(value = "/rest/patHistory/{userId}")
    List<Note> getNotesByUserId(@PathVariable("userId") Integer id);
}
