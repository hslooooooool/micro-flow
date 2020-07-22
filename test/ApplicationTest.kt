package vip.qsos.flow

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import vip.qsos.flow.api.api
import vip.qsos.flow.base.Flow
import vip.qsos.flow.base.Step
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
    fun testFlow() {
        val flow = Flow().apply {
            id = 1
            title = "测试流程模版标题"
            desc = "测试流程模版描述"
            state = 0
            version = 1
            // 定义一个串联流程
            val sSteps = arrayListOf<Step>()
            val step1 = Step().apply {
                title = "第一步骤标题"
                desc = "第一步骤描述"
                state = 0
                form = 1
                starter = Step.Starter(0, 0L)
                task = Step.Task(0, setOf(1))
                next = Step.Next(0, "a>1&b<0")
            }
            sSteps.add(step1)
            val step2 = Step().apply {
                title = "第二步骤标题"
                desc = "第二步骤描述"
                state = 0
                form = 2
                starter = Step.Starter(0, 0L)
                task = Step.Task(0, setOf(1))
                next = Step.Next(0, "a=1")
            }
            sSteps.add(step2)
            val step3 = Step().apply {
                title = "第三步骤标题"
                desc = "第三步骤描述"
                state = 0
                form = 2
                starter = Step.Starter(0, 0L)
                task = Step.Task(0, setOf(1))
                next = Step.Next(0, "b=1")
            }
            sSteps.add(step3)
            steps = sSteps
        }
        withTestApplication({ api(testing = true, testFlow = flow) }) {
            handleRequest(HttpMethod.Post, "/flow/model/create").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                println(response.content)
            }
        }
    }
}
