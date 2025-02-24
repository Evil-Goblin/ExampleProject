package playground.kotlinmultimodule

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import playground.kotlinmultimodule.dto.RequestCreateUser

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
@SpringBootTest
class CoreControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) {

    @Test
    fun `save account test`() {
        // given
        val requestCreateUser = RequestCreateUser(
            email = "email@email.com",
            name = "name",
            password = "password"
        )

        val requestJson = objectMapper.writeValueAsString(requestCreateUser)

        // expect
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(requestCreateUser.email))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(requestCreateUser.name))
    }
}
