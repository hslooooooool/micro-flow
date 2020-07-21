package vip.qsos.flow.base.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import vip.qsos.flow.base.Flow
import vip.qsos.flow.base.server.FlowServer
import vip.qsos.flow.base.server.FlowServerImpl
import javax.xml.ws.http.HTTPException

private val mFlowServer: FlowServer by lazy {
    FlowServerImpl()
}

@kotlin.jvm.JvmOverloads
fun Application.flow(testing: Boolean = false) {

    routing {
        get("/flow/model/create") {
            var flow = call.receive<Flow>()
            flow = mFlowServer.creatFlowModel(flow)
            call.respond(
                mapOf(
                    "success" to true,
                    "data" to flow
                )
            )
        }

        get("/flow/create") {
            val flowId = call.parameters["id"]?.toInt() ?: throw HTTPException(500)
            val flow = mFlowServer.creatFlowBean(flowId)
            call.respond(
                mapOf(
                    "success" to true,
                    "data" to flow
                )
            )
        }

        get("/flow/start") {
            val flowId = call.parameters["id"]?.toInt() ?: throw HTTPException(500)
            mFlowServer.startFlowBean(flowId)
            call.respond(
                mapOf(
                    "success" to true
                )
            )
        }
    }
}