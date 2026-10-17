package org.airtribe.LearnerManagementSystemBelC22.controller;

import org.airtribe.LearnerManagementSystemBelC22.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC22.service.LearnerManagementService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class LearnerControllerTest {

  @MockitoBean
  private LearnerManagementService _learnerManagementService;

  @Autowired
  private MockMvc _mockMvc;

  @Test
  public void testCreateLearner_Successfully() throws Exception {
    //ARRANGEMENT
    Learner learner = new Learner(1L, "John Doe", "test", "1234567890");
    when(_learnerManagementService.createLearner(any())).thenReturn(learner);

    _mockMvc.perform(MockMvcRequestBuilders.post("/learners")
        .content("{\"learnerName\":\"John Doe\",\"learnerEmail\":\"test@gmail.com\",\"learnerPhone\":\"1234567890\"}")
        .contentType("application/json")).andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.learnerId").value(1))
        .andExpect(jsonPath("$.learnerName").value("John Doe"))
        .andExpect(jsonPath("$.learnerEmail").value("test"))
        .andExpect(jsonPath("$.learnerPhone").value("1234567890"));
  }

  @Test
  public void testCreateLearner_ValidationFailure() throws Exception {
    _mockMvc.perform(MockMvcRequestBuilders.post("/learners")
        .content("{\"learnerName\":\"John Doe\",\"learnerEmail\":\"test\",\"learnerPhone\":\"1234567890\"}")
        .contentType("application/json"))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

}
