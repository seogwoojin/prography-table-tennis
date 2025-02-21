package com.prography.tabletennis.global.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.prography.tabletennis.global.response.ReturnCode;

@WebMvcTest(HealthCheckController.class)
public class HealthCheckControllerTest {
  @MockitoBean JpaMetamodelMappingContext jpaMetamodelMappingContext;
  @Autowired private MockMvc mockMvc;

  @Test
  public void testCheckServerHealth() throws Exception {
    mockMvc
        .perform(get("/health"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(200))
        .andExpect(jsonPath("$.message").value(ReturnCode.SUCCESS.getMessage()));
  }
}
