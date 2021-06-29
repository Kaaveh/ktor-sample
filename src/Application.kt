package com.example

import com.example.data.authentication.JwtConfig
import com.example.data.model.DraftItem
import com.example.data.model.Item
import com.example.data.model.LoginBody
import com.example.data.repository.InMemoryRepository
import com.example.data.repository.InMemoryUserRepository
import com.example.data.repository.MySQLItemRepository
import com.example.data.repository.RepositoryInterface
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

val jwtConfig = JwtConfig(System.getenv("JWT_SECRET"))

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(CallLogging)
    install(ContentNegotiation) {
        gson()
    }
    install(Authentication) {
        jwt {
            jwtConfig.configureKtorFeature(this)
        }
    }
    routing {
        val repository: RepositoryInterface = MySQLItemRepository()
        val userRepository = InMemoryUserRepository()
        get("/ktor") {
            call.respondText("Hello Ktor!")
        }
        post("/login") {
            val loginBody = call.receive<LoginBody>()
            val user = userRepository.getUser(loginBody.username, loginBody.password)
            user?.let {
                val token = jwtConfig.generateToken(JwtConfig.JwtUser(user.userId, user.username))
                call.respond(token)
            }
        }
        authenticate {
            get("/me") {
                val user = call.authentication.principal as JwtConfig.JwtUser
                call.respond(user)
            }
            get("/items") {
                call.respond(repository.getAllItems())
            }
            get("/item/{id}") {
                val id = call.parameters["id"]?.toInt()
                id?.let { itemId ->
                    val item = repository.getItem(itemId)
                    item?.let {
                        call.respond(it)
                    }
                }
            }
            post("/item") {
                val draftItem = call.receive<DraftItem>()
                repository.addItem(draftItem)
            }
            put("/item/{id}") {
                val draftItem = call.receive<DraftItem>()
                val itemId = call.parameters["id"]?.toInt()
                itemId?.let { id ->
                    repository.updateItem(id, draftItem)
                }
            }
            delete("item/{id}") {
                val itemId = call.parameters["id"]?.toInt()
                itemId?.let {
                    repository.removeItem(it)
                }
            }
        }
    }
}