package com.controller;

import com.json.JsonResponses;
import com.unisystems.UniStructureApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UniStructureApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyFeature {
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(roles="EMPLOYEE")
    public void getAllCompanies() {
        try {
            MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                    .get("/getCompanies").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.companies").isArray())
                    .andExpect(jsonPath("$.companies[0].companyId").exists())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.companies[0].companyName").value("UniSystems"))
                    .andReturn();
            assertEquals(result.getResponse().getContentAsString(), JsonResponses.jsonCompanyResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}