package com.chubb.test;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Pins the OpenAPI contract exposed by springdoc: every current endpoint must
 * be published with description, parameters and responses.
 */
@SpringBootTest
@AutoConfigureMockMvc
class OpenApiDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void apiDocsExposesEveryClientEndpoint() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/clients'].get").exists())
                .andExpect(jsonPath("$.paths['/api/client'].post").exists())
                .andExpect(jsonPath("$.paths['/api/client/{id}'].delete").exists());
    }

    @Test
    void listClientsIsDescribedWithItsResponses() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/clients'].get.summary", notNullValue()))
                .andExpect(jsonPath("$.paths['/api/clients'].get.description", notNullValue()))
                .andExpect(jsonPath("$.paths['/api/clients'].get.responses.200.description",
                        not(emptyOrNullString())));
    }

    @Test
    void saveClientDocumentsItsRequestAndResponses() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/client'].post.description", notNullValue()))
                .andExpect(jsonPath(
                        "$.paths['/api/client'].post.requestBody.content['application/json'].schema",
                        notNullValue()))
                .andExpect(jsonPath("$.paths['/api/client'].post.responses.200.description",
                        not(emptyOrNullString())));
    }

    @Test
    void deleteClientDocumentsItsPathVariableAndResponses() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/client/{id}'].delete.parameters[0].name", is("id")))
                .andExpect(jsonPath("$.paths['/api/client/{id}'].delete.parameters[0].required", is(true)))
                .andExpect(jsonPath("$.paths['/api/client/{id}'].delete.parameters[0].description",
                        not(emptyOrNullString())))
                .andExpect(jsonPath("$.paths['/api/client/{id}'].delete.responses.204.description",
                        not(emptyOrNullString())))
                .andExpect(jsonPath("$.paths['/api/client/{id}'].delete.responses.404.description",
                        not(emptyOrNullString())));
    }

    @Test
    void swaggerUiIsReachable() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }
}
