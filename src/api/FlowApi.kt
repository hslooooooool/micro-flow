package vip.qsos.flow.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import vip.qsos.flow.base.Flow
import vip.qsos.flow.base.server.FlowServer
import vip.qsos.flow.base.server.FlowServerImpl
import javax.xml.ws.http.HTTPException

private val mFlowServer: FlowServer by lazy {
    FlowServerImpl()
}

@kotlin.jvm.JvmOverloads
fun Application.api(testing: Boolean = false, testFlow: Flow? = null) {
    if (testing) {
        installContentNegotiation()
    }
    routing {
        post("/flow/model/create") {
            var flow: Flow = if (testing) {
                testFlow ?: throw HTTPException(500)
            } else {
                call.receive()
            }

            flow = mFlowServer.creatFlowModel(flow)
            call.respond(
                mapOf(
                    "success" to true,
                    "data" to flow
                )
            )
        }

        post("/flow/create") {
            val flowId = call.parameters["id"]?.toInt() ?: throw HTTPException(500)
            val flow = mFlowServer.creatFlowBean(flowId)
            call.respond(
                mapOf(
                    "success" to true,
                    "data" to flow
                )
            )
        }

        post("/flow/start") {
            val flowId = call.parameters["id"]?.toInt() ?: throw HTTPException(500)
            val flow = mFlowServer.startFlowBean(flowId)
            call.respond(
                mapOf(
                    "success" to true,
                    "data" to flow
                )
            )
        }

        post("/flow/stop") {
            val flowId = call.parameters["id"]?.toInt() ?: throw HTTPException(500)
            val flow = mFlowServer.stopFlow(flowId)
            call.respond(
                mapOf(
                    "success" to true,
                    "data" to flow
                )
            )
        }
    }
}