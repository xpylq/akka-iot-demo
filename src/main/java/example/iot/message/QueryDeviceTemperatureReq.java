package example.iot.message;

import akka.actor.typed.ActorRef;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:34 <br/>
 * @Author: youzhihao
 */
public class QueryDeviceTemperatureReq implements Command {

    private final long requestId;
    private final ActorRef<QueryDeviceTemperatureRes> replyTo;

    public QueryDeviceTemperatureReq(long requestId, ActorRef<QueryDeviceTemperatureRes> replyTo) {
        this.requestId = requestId;
        this.replyTo = replyTo;
    }

    public long getRequestId() {
        return requestId;
    }

    public ActorRef<QueryDeviceTemperatureRes> getReplyTo() {
        return replyTo;
    }
}
