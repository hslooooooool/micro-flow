package vip.qsos.flow.model

import vip.qsos.flow.core.ILog

/**流程执行日志
 * @author : 华清松
 */
data class Log(
    override var flowId: Int = -1,
    override var id: Long = -1L,
    override var title: String = "新建日志",
    override var code: String = "",
    override var content: String = ""
) : ILog