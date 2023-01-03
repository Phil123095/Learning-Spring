package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Table("MESSAGES")
data class Message(@Id val id: String?, val text: String)

// Spring Data Repository API to access DB.
// @Repository = Classes in the PERSISTENCE layer (DB repository)
interface MessageRepository: CrudRepository<Message, String>{
	// When calling the findMessages() method on instance of this, it will exec. the query.
	@Query("select * from messages")
	fun findMessages(): List<Message>
}

// @Service = Annotation marking class as a SERVICE PROVIDER (= providing some business functionalities).
// = Classes residing in SERVICE LAYER.
@Service
class MessageService(val db: MessageRepository) {
	// Getting all the messages from the DB.
	fun findMessages(): List<Message> = db.findMessages()

	fun post(message: Message) {
		db.save(message)
	}
}


/* @RestController = "extension" of @Controller notation. Tells Spring Framework that the class serves as a Controller
	in Spring MVC.
 */
@RestController
class MessageResource {
	@GetMapping("/")
	fun index(): List<Message> = listOf(
		Message("1", "Hello!"),
		Message("2", "Bonjour!"),
		Message("3", "Privet!")
	)
}
