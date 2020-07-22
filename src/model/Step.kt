package vip.qsos.flow.model

import vip.qsos.flow.core.IStep

/**步骤
 * @author : 华清松
 */
data class Step(
    var id: Int = -1,
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
