package vip.qsos.flow.core

/**步骤元数据
 * @author : 华清松
 */
interface IStep {
    /**步骤ID*/
    var id: Int

    /**步骤标题*/
    var title: String

    /**步骤描述*/
    var desc: String

    /**步骤状态
     * - 0-待触发
     * - 1-已触发（未启动）
     * - 2-进行中（已启动）
     * - 3-已停止
     * */
    var state: Int

    /**步骤表单（表单模版ID）*/
    var form: Int

    /**步骤启动器*/
    var starter: IStarter

    /**步骤任务*/
    var task: ITask

    /**回执列表（一个任务一个回执）*/
    var replies: List<IReply>

    /**下一步配置*/
    var next: INext

    /**步骤启动器元数据
     * @author : 华清松
     */
    interface IStarter {
        /**启动器类型：
         * - 0-普通启动器（步骤被告知启动即步骤启动）；
         * - 1-计时启动器（步骤被告知启动，进入计时器倒计时，计时结束步骤启动）
         * */
        var starterType: Int

        /*延迟启动倒计时，毫秒数*/
        var starterTime: Long
    }

    /**步骤任务元数据
     * @author : 华清松
     */
    interface ITask {
        /**任务类型：
         * - 0-单任务执行；
         * - 1-多任务会签（全部执行完成，步骤完成）；
         * - 2-多任务竟签（任一任务完成，步骤完成）；
         * - 3-自动任务（自动执行，任务执行即步骤完成）
         * */
        var taskType: Int

        /*任务ID集合*/
        var taskIds: Set<Int>
    }

    /**步骤回执元数据
     * @author : 华清松
     */
    interface IReply {
        /*任务ID*/
        var taskId: Int

        /*表单ID*/
        var formId: Int
    }

    /**下一步配置元数据
     * @author : 华清松
     */
    interface INext {

        var flowType: Int

        /*下一步触发条件（正则表达式）*/
        var condition: String?
    }

}