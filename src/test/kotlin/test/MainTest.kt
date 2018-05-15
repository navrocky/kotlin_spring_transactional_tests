package test

import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
@AutoConfigureMockMvc()
class MainTest {

    @Autowired
    lateinit var testModelRepository: TestModelRepository

    @Autowired
    private lateinit var mvc: MockMvc;

    @Test
    fun testModel() {
        val model = TestModel()
        model.title = "aaa"
        testModelRepository.save(model)
        Assert.assertEquals(1, testModelRepository.findAll().toList().size)
    }

    @Test
    fun testMockMvc() {
        mvc.perform(post("/test").contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8).content("{\"title\": \"blabla\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("\"title\":\"blabla\"")))
    }
}