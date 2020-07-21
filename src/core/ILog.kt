package vip.qsos.flow.core

/**流程执行日志
 * @author : 华清松
 */
interface ILog {
    /**流程ID*/
    var flowId: Int

    /**日志ID*/
    var id: Long

    /**日志标题*/
    var title: String

    /**日志编号*/
    var code: String

    /**日志存储内容或日志文件存储路径*/
    var content: String
}