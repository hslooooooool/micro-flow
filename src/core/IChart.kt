package vip.qsos.flow.core

/**流程图元数据
 * @author : 华清松
 */
interface IChart {
    /**图ID*/
    var id: Int

    /**图类型：
     * - 0-全步骤流转图；
     * - 1-执行位置示意图
     * */
    var type: Int

    /**图数据*/
    var map: String
}