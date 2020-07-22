package vip.qsos.flow.base

import vip.qsos.flow.core.IFlow

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
    var steps: List<Step> = arrayListOf()

    /*日志列表*/
    var logs: List<Log> = arrayListOf()

    /*图列表*/
    var charts: List<Chart> = arrayListOf()

}