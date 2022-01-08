package com.mediscreen.report;

import com.mediscreen.report.controllers.ReportController;
import com.mediscreen.report.services.ReportService;
import com.mediscreen.report.webClients.NoteFeignClient;
import com.mediscreen.report.webClients.PatientFeignClient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteFeignClient noteFeignClient;

    @MockBean
    private PatientFeignClient patientFeignClient;

    @MockBean
    private ReportService reportService;

    @Test
    public void getReportTest() throws Exception {
        mockMvc.perform(get("/rest/assess/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
