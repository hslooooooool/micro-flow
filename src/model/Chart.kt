package vip.qsos.flow.model

import vip.qsos.flow.core.IChart

/**流程图
 * @author : 华清松
 */
data class Chart(
    override var id: Int = -1,
    override var type: Int = 0,
    override var map: String = ""
) : IChart