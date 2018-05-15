package test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.repository.CrudRepository
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class TestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null

    @Column
    lateinit var title: String
}

@Repository
interface TestModelRepository : CrudRepository<TestModel, Long>

@RestController
@Transactional
class TestModelController {

    @Autowired
    lateinit var testModelRepository: TestModelRepository

    @PostMapping(value = ["test"],
            consumes = [MediaType.APPLICATION_JSON_UTF8_VALUE],
            produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun createTestModel(
            @RequestBody(required = true) model: TestModel
    ) : TestModel {
        testModelRepository.save(model)
        return model
    }

    @Scheduled(fixedDelay = 2000)
    fun scheduledCreation() {
        val model = TestModel()
        model.title = LocalDateTime.now().toString()
        testModelRepository.save(model)
    }

}

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
class Application {

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
