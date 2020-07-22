package vip.qsos.flow.api

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.html.respondHtml
import io.ktor.jackson.jackson
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.*
import java.text.DateFormat

fun Application.installContentNegotiation(): ContentNegotiation {
    return install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }

        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}

@kotlin.jvm.JvmOverloads
fun Application.api(testing: Boolean = false) {
    installContentNegotiation()

    routing {
        get("/") {
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
    }

}
