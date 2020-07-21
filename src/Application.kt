package vip.qsos.flow

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import kotlinx.html.*
import java.text.DateFormat

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }

        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        get("/index.html") {
            val title = "测试页面"
            val content = "测试内容"
            val list = arrayListOf<String>("蛋糕\uD83C\uDF82", "啤酒\uD83C\uDF7A", "饮料🥤", "水果🍎")
            call.respondHtml {
                head {
                    title { +title }
                }
                body {
                    p {
                        +content
                    }
                    ul {
                        list.forEach {
                            li {
                                +it
                            }
                        }
                    }
                }
            }
        }
        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }

        get("/json/jackson") {
            call.respond(mapOf("hello" to "world"))
        }

        post("/form") {
            val form = call.receive<Form>()
            println(form.toString())
            call.respond(mapOf("hello" to "world"))
        }
    }

}

data class Form(val id: Int = -1)
