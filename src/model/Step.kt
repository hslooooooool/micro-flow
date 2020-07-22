package vip.qsos.flow.base

import vip.qsos.flow.core.IStep

/**步骤
 * @author : 华清松
 */
data class Step(
    override var id: Int = -1,
    override var title: String = "新建步骤",
    override var desc: String = "步骤描述",
    override var state: Int = 0,
    override var form: Int = -1,
    override var starter: IStep.IStarter = Starter(),
    override var task: IStep.ITask = Task(),
    override var replies: List<IStep.IReply> = arrayListOf(),
    override var next: IStep.INext = Next()
) : IStep {

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
        override var actionId: Int,
        override var formId: Int
    ) : IStep.IReply

    /**下一步配置
     * @author : 华清松
     */
    data class Next(
        override var flow: Int = 0,
        override var condition: String? = null
    ) : IStep.INext

}
