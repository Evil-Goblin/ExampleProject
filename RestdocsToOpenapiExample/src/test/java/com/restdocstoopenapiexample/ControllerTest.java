package com.restdocstoopenapiexample;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class ControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("RestdocsToOpenapiExample.com")
                        .withPort(443)
                        .and()
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint()))
                .build();
    }

    @DisplayName("[RestDocs] PathVariable 성공시")
    @Test
    void pathVariableSuccessTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/path/{value}/variable", "whenSuccess"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("whenSuccess"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("Success"))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                "CasePathVariable",
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("value").description("PathVariable")
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("message").description("message"),
                                        PayloadDocumentation.fieldWithPath("responseCode").description("responseCode")
                                ),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("Example")
                                                .description("Openapi Case PathVariable")
                                                .pathParameters(ResourceDocumentation.parameterWithName("value").description("PathVariable"))
                                                .responseSchema(Schema.schema("ResponseForm"))
                                                .responseFields(
                                                        PayloadDocumentation.fieldWithPath("message").description("message"),
                                                        PayloadDocumentation.fieldWithPath("responseCode").description("responseCode")
                                                )
                                                .build()
                                )
                        ));
    }

    @DisplayName("[RestDocs] PathVariable 실패시")
    @Test
    void pathVariableFailTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/path/{value}/variable", "error"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("error value"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("ErrorCode"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("message"))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                "CasePathVariable-Error",
                                RequestDocumentation.pathParameters(
                                        RequestDocumentation.parameterWithName("value").description("PathVariable")
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("error").description("error"),
                                        PayloadDocumentation.fieldWithPath("message").description("message"),
                                        PayloadDocumentation.fieldWithPath("responseCode").description("responseCode")
                                ),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("Example")
                                                .description("Openapi Case PathVariable Error")
                                                .pathParameters(ResourceDocumentation.parameterWithName("value").description("PathVariable"))
                                                .responseSchema(Schema.schema("ErrorResponse"))
                                                .responseFields(
                                                        PayloadDocumentation.fieldWithPath("error").description("error"),
                                                        PayloadDocumentation.fieldWithPath("message").description("message"),
                                                        PayloadDocumentation.fieldWithPath("responseCode").description("responseCode")
                                                )
                                                .build()
                                )
                        )
                );
    }

    @DisplayName("[RestDocs] Post 성공시")
    @Test
    void postSuccessTest() throws Exception {

        RequestForm requestForm = RequestForm.builder()
                .name("name")
                .value("value")
                .build();

        String requestJson = objectMapper.writeValueAsString(requestForm);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("name value"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("Success"))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                "CasePost",
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("name").description("name"),
                                        PayloadDocumentation.fieldWithPath("value").description("value")
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("message").description("message"),
                                        PayloadDocumentation.fieldWithPath("responseCode").description("responseCode")
                                ),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("Example")
                                                .description("Openapi Case Post")
                                                .requestSchema(Schema.schema("RequestForm"))
                                                .requestFields(
                                                        PayloadDocumentation.fieldWithPath("name").description("name"),
                                                        PayloadDocumentation.fieldWithPath("value").description("value")
                                                )
                                                .responseSchema(Schema.schema("ResponseForm"))
                                                .responseFields(
                                                        PayloadDocumentation.fieldWithPath("message").description("message"),
                                                        PayloadDocumentation.fieldWithPath("responseCode").description("responseCode")
                                                )
                                                .build()
                                )
                        )
                );
    }

    @DisplayName("[RestDocs] Post 실패시")
    @Test
    void postFailTest() throws Exception {

        RequestForm requestForm = RequestForm.builder()
                .name("")
                .value("")
                .build();

        String requestJson = objectMapper.writeValueAsString(requestForm);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("namevalue"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("message"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.responseCode").value("ErrorCode"))
                .andDo(
                        MockMvcRestDocumentationWrapper.document(
                                "CasePost-Error",
                                PayloadDocumentation.requestFields(
                                        PayloadDocumentation.fieldWithPath("name").description("name"),
                                        PayloadDocumentation.fieldWithPath("value").description("value")
                                ),
                                PayloadDocumentation.responseFields(
                                        PayloadDocumentation.fieldWithPath("error").description("error"),
                                        PayloadDocumentation.fieldWithPath("message").description("message"),
                                        PayloadDocumentation.fieldWithPath("responseCode").description("responseCode")
                                ),
                                ResourceDocumentation.resource(
                                        ResourceSnippetParameters.builder()
                                                .tag("Example")
                                                .description("Openapi Case Post Error")
                                                .requestSchema(Schema.schema("RequestForm"))
                                                .requestFields(
                                                        PayloadDocumentation.fieldWithPath("name").description("name"),
                                                        PayloadDocumentation.fieldWithPath("value").description("value")
                                                )
                                                .responseSchema(Schema.schema("ResponseForm"))
                                                .responseFields(
                                                        PayloadDocumentation.fieldWithPath("error").description("error"),
                                                        PayloadDocumentation.fieldWithPath("message").description("message"),
                                                        PayloadDocumentation.fieldWithPath("responseCode").description("responseCode")
                                                )
                                                .build()
                                )
                        )
                );
    }
}
