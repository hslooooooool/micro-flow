package vip.qsos.flow

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import vip.qsos.flow.base.api.flow
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testFlow() {
        withTestApplication({ flow(testing = true) }) {
            handleRequest(HttpMethod.Get, "/flow/jackson").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }
}
