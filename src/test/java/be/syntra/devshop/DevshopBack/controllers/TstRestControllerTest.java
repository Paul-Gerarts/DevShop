package be.syntra.devshop.DevshopBack.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TstRestController.class)
class TstRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void displayTstString() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/devshop/test"));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("DevShop"))
                .andExpect(jsonPath("$").value("DevShop"));
    }
}