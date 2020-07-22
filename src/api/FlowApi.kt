package vip.qsos.flow.api

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.routing.routing
import vip.qsos.flow.model.Flow
import vip.qsos.flow.model.Step
import vip.qsos.flow.server.FlowServer
import vip.qsos.flow.server.FlowServerImpl
import vip.qsos.flow.server.StepServer
import vip.qsos.flow.server.StepServerImpl
import javax.xml.ws.http.HTTPException

private val mFlowServer: FlowServer by lazy {
    FlowServerImpl()
}

private val mStepServer: StepServer by lazy {
    StepServerImpl()
}

@kotlin.jvm.JvmOverloads
fun Application.api(testing: Boolean = false, testFlow: Flow? = null) {
    if (testing) {
        installContentNegotiation()
    }
    routing {
        /**流程模版创建*/
        post("/flow/model/create") {
            var flow: Flow = if (testing) {
                testFlow ?: throw HTTPException(500)
            } else {
                call.receive()
            }

            flow = mFlowServer.creatFlowModel(flow)
            call.respond(flow)
        }

        /**流程实例创建*/
        post("/flow/create") {
            val flowId = call.parameters["id"]?.toInt() ?: throw HTTPException(500)
            val flow = mFlowServer.creatFlowBean(flowId)
            call.respond(flow)
        }

        /**流程实例激活*/
        post("/flow/start") {
            val flowId = call.parameters["id"]?.toInt() ?: throw HTTPException(500)
            val flow = mFlowServer.startFlowBean(flowId)
            call.respond(flow)
        }
        /**流程实例终止*/
        post("/flow/stop") {
            val flowId = call.parameters["id"]?.toInt() ?: throw HTTPException(500)
            val flow = mFlowServer.stopFlow(flowId)
            call.respond(flow)
        }
    }

    routing {
        /**流程步骤变动，任意变动将判断流程状态，及其下一步触发条件，若满足条件，则完成此步骤并触发后续步骤*/
        post("/step/modify") {
            val step = call.receive<Step>()
            call.respond(mStepServer.modify(step))
        }
    }
}