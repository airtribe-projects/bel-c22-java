package org.airtribe.LearnerManagementSystemBelC22.integTess;

import org.airtribe.LearnerManagementSystemBelC22.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC22.repository.LearnerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LearnerManagementSystemIntegTests {

  @Autowired
  private MockMvc _mockMvc;

  @Autowired
  private LearnerRepository _learnerRepository;

  @BeforeEach
  public void cleanupBeforeEach() {
    _learnerRepository.deleteAll();
  }

  @AfterEach
  public void cleanupAfterEach() {
    _learnerRepository.deleteAll();
  }

  @Test
  public void testCreateLearner_Successfully() throws Exception {
    //ARRANGEMENT
    _mockMvc.perform(MockMvcRequestBuilders.post("/learners")
            .content("{\"learnerName\":\"John Doe\",\"learnerEmail\":\"test@gmail.com\",\"learnerPhone\":\"1234567890\"}")
            .contentType("application/json")).andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.learnerId").isNotEmpty())
        .andExpect(jsonPath("$.learnerName").value("John Doe"))
        .andExpect(jsonPath("$.learnerEmail").value("test@gmail.com"))
        .andExpect(jsonPath("$.learnerPhone").value("1234567890"));
  }
}
