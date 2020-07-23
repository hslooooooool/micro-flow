package vip.qsos.flow

import com.google.gson.Gson
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import vip.qsos.flow.api.api
import vip.qsos.flow.model.Flow
import vip.qsos.flow.model.Step
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ api(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testFlow1() {
        /*测试串行流程，普通触发，单任务执行*/
        val flow = Flow().apply {
            id = 1
            title = "测试流程模版标题"
            desc = "测试流程模版描述"
            state = 0
            version = 1

            val sSteps = arrayListOf<Step>()
            val step1 = Step().apply {
                title = "第一步骤标题"
                desc = "第一步骤描述"
                state = 0
                form = 1
                starter = Step.Starter(0, 0L)
                task = Step.Task(0, setOf(1))
                next = Step.Next(0, 1)
            }
            sSteps.add(step1)
            val step2 = Step().apply {
                title = "第二步骤标题"
                desc = "第二步骤描述"
                state = 0
                form = 2
                starter = Step.Starter(0, 0L)
                task = Step.Task(0, setOf(1))
                next = Step.Next(0, 1)
            }
            step1.next.steps = arrayListOf(step2)
            sSteps.add(step2)
            val step3 = Step().apply {
                title = "第三步骤标题"
                desc = "第三步骤描述"
                state = 0
                form = 2
                starter = Step.Starter(0, 0L)
                task = Step.Task(0, setOf(1))
                next = Step.Next(0, 0)
            }
            step2.next.steps = arrayListOf(step3)
            sSteps.add(step3)
            steps = sSteps
        }
        withTestApplication({ api(testing = true, testFlow = flow) }) {

            handleRequest(HttpMethod.Post, "/flow/model/create").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
            println("【通过】较验流程模版创建")

            handleRequest(HttpMethod.Post, "/flow/create?id=1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
            println("【通过】较验流程实例创建")

            var testStep: Step
            handleRequest(HttpMethod.Post, "/flow/start?id=1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val f = Gson().fromJson(response.content, Flow::class.java)
                assertEquals(1, f.state)
                assertEquals(2, f.steps.first().state)

                testStep = f.steps.first()
            }
            println("【通过】较验触发流程实例启动")

            handleRequest(HttpMethod.Post, "/step/modify").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val s = Gson().fromJson(response.content, Step::class.java)
                assertEquals(2, s.state)
            }
            println("【通过】较验流程第一步完成，触发下一步骤")

        }
    }
}
