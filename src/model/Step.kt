package vip.qsos.flow.model

import vip.qsos.flow.core.IStep

/**步骤
 * @author : 华清松
 * @param id
 * @param flowId 流程ID
 * @param title
 * @param desc
 * @param state 步骤状态
 * - 0 待触发
 * - 1 已触发（未启动）
 * - 2 进行中（已启动）
 * - 3 已停止
 * @param form
 */
data class Step constructor(
    var id: Int = -1,
    var flowId: Int = -1,
    var title: String = "新建步骤",
    var desc: String = "步骤描述",
    var state: Int = 0,
    var form: Int = -1
) {

    var starter: Starter = Starter()

    var task: Task = Task()

    var replies: List<Reply> = arrayListOf()

    var next: Next = Next()

    /**步骤启动器
     * @author : 华清松
     */
    data class Starter(
        override var starterType: Int = 0,
        override var starterTime: Long = 0L
    ) : IStep.IStarter

    /**步骤任务
     * @author : 华清松
     */
    data class Task(
        override var taskType: Int = 0,
        override var taskIds: Set<Int> = HashSet()
    ) : IStep.ITask

    /**步骤回执
     * @author : 华清松
     */
    data class Reply(
        override var taskId: Int,
        override var formId: Int
    ) : IStep.IReply

    /**下一步配置
     * @author : 华清松
     * @param flowType 下一步类型：
     * - 0 串行执行（步骤完成，直接触发后续步骤）；
     * - 1 并行执行（步骤完成，直接触发后续步骤，后续多步骤并行触发）；
     * - 2 网关判定（步骤完成，根据条件触发后续步骤）；
     * - 3 流程结束（步骤完成，流程结束）
     */
    data class Next(
        var flowType: Int = 0,
        var result: Int = -1,
        var steps: List<Step> = arrayListOf()
    ) {
        var next: List<Next> = arrayListOf()
    }

}
