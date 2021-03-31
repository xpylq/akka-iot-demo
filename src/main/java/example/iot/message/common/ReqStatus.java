package example.iot.message.common;

import example.iot.message.Command;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: 请求命令状态 <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/31 下午2:49 <br/>
 * @Author: youzhihao
 */
public class ReqStatus implements Command {

    private String requestId;

    private String status;

    public ReqStatus(String requestId, String status) {
        this.requestId = requestId;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getStatus() {
        return status;
    }
}
