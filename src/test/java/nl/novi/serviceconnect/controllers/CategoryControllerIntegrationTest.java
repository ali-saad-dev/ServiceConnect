package nl.novi.serviceconnect.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import nl.novi.serviceconnect.dtos.ServiceCategoryOutputDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class CategoryControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username="admin", roles="ADMIN")
    public void shouldCreateCategory() throws Exception {

        String requestJson = """
                {
                    "name": "Test Professional Service",
                    "description": "Test The services of individuals with professional qualifications such as an accountant or lawyer"
                }
                """;
        //POST request
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/serviceCategory")
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String locationHeader = result.getResponse().getHeader("Location");
        assertThat(locationHeader, matchesPattern("^.*/serviceCategory/[0-9]+$"));
    }

    @Test
    public void shouldGetCorrectCategory() throws Exception {

        String requestJson = """
            {
                "name": "Test Professional Service",
                "description": "Test The services of individuals with professional qualifications such as an accountant or lawyer"
            }
            """;

        //POST request
        MvcResult result = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/serviceCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String createdIdAsString = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(createdIdAsString);
        long createdId = jsonResponse.get("id").asLong();

        //GET request
        this.mockMvc.perform(MockMvcRequestBuilders.get("/serviceCategory/" + createdId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", is("Test Professional Service")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", is("Test The services of individuals with professional qualifications such as an accountant or lawyer")));
    }

    @Test
    public void shouldGetAllCategories() throws Exception {

        String requestJson = """
        {
            "name": "Test Category",
            "description": "Test Description"
        }
        """;

        //POST request
        this.mockMvc.perform(MockMvcRequestBuilders.post("/serviceCategory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        //GET request
        MvcResult getResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/serviceCategory")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        final ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = getResult.getResponse().getContentAsString();
        List<ServiceCategoryOutputDto> categories = objectMapper.readValue(responseBody, new TypeReference<>() {});


        assertNotNull(categories);
        assertTrue(categories.stream().anyMatch(category -> category.name.equals("Test Category") && category.description.equals("Test Description")));
    }
}