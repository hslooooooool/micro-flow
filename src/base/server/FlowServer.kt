package vip.qsos.flow.base.server

import vip.qsos.flow.base.Flow
import javax.xml.ws.http.HTTPException

private val mFlowModelList: HashMap<Int, Flow> = HashMap()
private val mFlowList: HashMap<Int, Flow> = HashMap()

class FlowServerImpl : FlowServer {

    override fun creatFlowModel(flow: Flow): Flow {
        val id = mFlowModelList.size
        flow.id = id + 1
        flow.steps.forEachIndexed { x, step ->
            step.id = x
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

    override fun creatFlowBean(flowId: Int): Flow {
        val flow = mFlowModelList[flowId] ?: throw HTTPException(500)
        val id = mFlowList.size
        mFlowList[id + 1] = flow
        return flow
    }

    override fun startFlowBean(flowId: Int) {
        val flow = mFlowList[flowId] ?: throw HTTPException(500)
        flow.state = 1
        //TODO 执行启动触发条件
        val step = flow.steps.find { it.id == flow.startStep } ?: throw HTTPException(500)
        when (step.starter.starterType) {
            0 -> {
                step.id
            }
            1 -> {
            }
        }
    }

    override fun suspendFlow(flowId: Int) {
        TODO("Not yet implemented")
    }

    override fun stopFlow(flowId: Int) {
        TODO("Not yet implemented")
    }

}

interface FlowServer {
    /**创建流程模型
     * @param flow 流程数据
     * */
    fun creatFlowModel(flow: Flow): Flow

    /**编辑流程模型
     * @param flow 流程数据
     * */
    fun modifyFlowModel(flow: Flow): Flow

    /**创建一个流程实例
     * @param flowId 流程模版ID
     * */
    fun creatFlowBean(flowId: Int): Flow

    /**激活一个流程实例
     * @param flowId 流程模版ID
     * */
    fun startFlowBean(flowId: Int)

    /**挂起一个流程实例
     * @param flowId 流程实例ID
     * */
    fun suspendFlow(flowId: Int)

    /**终止一个流程实例
     * @param flowId 流程实例ID
     * */
    fun stopFlow(flowId: Int)
}