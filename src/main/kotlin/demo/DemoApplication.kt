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
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PostMapping


@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

// Messages table created in SQL schema. Table structure matches Message class:
/*
	CREATE TABLE IF NOT EXISTS messages (
	  id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY, <- Creates unique ID
	  text                   VARCHAR      NOT NULL <- Can't be null (as specified in data class below)
	);
 */
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
class MessageResource(val service: MessageService) {
	@GetMapping("/")
	fun index(): List<Message> = service.findMessages()

	@PostMapping
	fun post(@RequestBody message: Message) {
		service.post(message)
	}
}
