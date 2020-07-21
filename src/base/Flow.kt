package vip.qsos.flow.base

import vip.qsos.flow.core.IChart
import vip.qsos.flow.core.IFlow
import vip.qsos.flow.core.ILog
import vip.qsos.flow.core.IStep

/**流程
 * @author : 华清松
 */
data class Flow(
    override var id: Int = -1,
    override var title: String = "新建流程",
    override var desc: String = "流程描述",
    override var state: Int = 0,
    override var startStep: Int = -1,
    override var endStep: Int = -1,
    override var version: Int = 1
) : IFlow {

    /*步骤列表*/
    override var steps: List<IStep> = arrayListOf()

    /*日志列表*/
    override var logs: List<ILog> = arrayListOf()

    /*图列表*/
    override var charts: List<IChart> = arrayListOf()

}