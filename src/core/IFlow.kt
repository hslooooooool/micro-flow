package vip.qsos.flow.core

/**流程元数据
 * @author : 华清松
 */
interface IFlow {
    /**流程ID*/
    var id: Int

    /**流程标题*/
    var title: String

    /**流程描述*/
    var desc: String

    /**流程状态：
     * - 0-创建
     * - 1-激活
     * - 2-挂起
     * - 3-终止
     */
    var state: Int

    /**流程起始步骤（步骤ID）*/
    var startStep: Int

    /**流程结束步骤（步骤ID）*/
    var endStep: Int

    /**流程版本*/
    var version: Int

    /**步骤列表*/
    var steps: List<IStep>

    /**日志列表*/
    var logs: List<ILog>

    /**图列表*/
    var charts: List<IChart>
}