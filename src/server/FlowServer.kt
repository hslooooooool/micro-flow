package vip.qsos.flow.server

import vip.qsos.flow.model.Flow
import vip.qsos.flow.model.Step
import javax.xml.ws.http.HTTPException

private val mFlowModelList: HashMap<Int, Flow> = HashMap()
private val mFlowList: HashMap<Int, Flow> = HashMap()
private val mFormModelList: HashMap<Int, Map<String, Int>> = HashMap()
private val mFormList: HashMap<Int, Map<String, Int>> = HashMap()

/**流程服务接口
 * @author : 华清松
 */
interface FlowServer {
    /**创建流程模型
     * @param flow 流程数据
     * */
    fun createFlowModel(flow: Flow): Flow

    /**编辑流程模型
     * @param flow 流程数据
     * */
    fun modifyFlowModel(flow: Flow): Flow

    /**创建一个流程实例
     * @param flowId 流程模版ID
     * */
    fun createFlowBean(flowId: Int): Flow

    /**激活一个流程实例
     * @param flowId 流程模版ID
     * */
    fun startFlowBean(flowId: Int): Flow

    /**挂起一个流程实例
     * @param flowId 流程实例ID
     * */
    fun suspendFlow(flowId: Int): Flow

    /**终止一个流程实例
     * @param flowId 流程实例ID
     * */
    fun stopFlow(flowId: Int): Flow
}

class FlowServerImpl : FlowServer {

    init {
        mFormList.clear()
        mFormModelList.clear()
        mFormModelList[1] = mapOf(
            "result" to 0
        )
        mFormModelList[2] = mapOf(
            "result" to 1
        )
    }

    override fun createFlowModel(flow: Flow): Flow {
        val id = mFlowModelList.size
        flow.id = id + 1

        if (flow.steps.isEmpty()) throw HTTPException(500)

        flow.steps.forEachIndexed { x, step ->
            step.id = x

            mFormModelList[step.form] ?: throw  HTTPException(500)
        }
        flow.startStep = flow.steps.first().id
        flow.endStep = flow.steps.last().id
        //TODO 增加 Chart 数据
        mFlowModelList[flow.id] = flow
        return flow
    }

    override fun modifyFlowModel(flow: Flow): Flow {
        return flow
    }

    override fun createFlowBean(flowId: Int): Flow {
        val flow = mFlowModelList[flowId] ?: throw HTTPException(500)
        val id = mFlowList.size
        flow.steps.forEach {
            it.flowId = id
        }
        mFlowList[id + 1] = flow
        return flow
    }

    override fun startFlowBean(flowId: Int): Flow {
        val flow = mFlowList[flowId] ?: throw HTTPException(500)
        flow.state = 1
        //TODO 执行启动触发条件
        val step = flow.steps.find { it.id == flow.startStep } ?: throw HTTPException(500)
        when (step.starter.starterType) {
            0 -> {
                step.state = 2
            }
            1 -> {
                step.state = 1
                //TODO 加入计时器，倒计时
            }
        }

        return flow
    }

    override fun suspendFlow(flowId: Int): Flow {
        val flow = mFlowList[flowId] ?: throw HTTPException(500)
        flow.state = 2

        return flow
    }

    override fun stopFlow(flowId: Int): Flow {
        val flow = mFlowList[flowId] ?: throw HTTPException(500)
        flow.state = 3

        return flow
    }

}

/**流程步骤接口
 * @author : 华清松
 */
interface StepServer {
    /**流程步骤变更
     * @param step 流程步骤数据
     * */
    fun modify(step: Step): Step
}

class StepServerImpl : StepServer {
    override fun modify(sstep: Step): Step {
        //fixme
        val step = mFlowList[1]?.steps?.first() ?: throw HTTPException(500)
        //fixme

        val taskSize = step.task.taskIds.size
        val resultMap: HashMap<Int, Int> = HashMap(taskSize)
        step.task.taskIds.map { taskId ->
            step.replies.find { reply ->
                reply.taskId == taskId
            }?.also { reply ->
                val form = mFormList[reply.formId]
                resultMap[reply.taskId] = form?.get("result") ?: 0
            }
        }
        var a = 0
        resultMap.values.forEach {
            a += it
        }
        val pass = when (step.task.taskType) {
            0 -> a > 0
            1 -> a == taskSize
            2 -> a > 0
            else -> false
        }
        if (pass) {
            step.state = 3

            when (step.next.flowType) {
                0, 1 -> {
                    step.next.steps.forEach { s ->
                        modify(s)
                    }
                }
                2 -> {
                    step.next.next.find { it.result == 1 }?.run {
                        steps.forEach {
                            modify(it)
                        }
                    }
                }
                3 -> {
                    step.id
                }
            }

        }
        return step
    }
}