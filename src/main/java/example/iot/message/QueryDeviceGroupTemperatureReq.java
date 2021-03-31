package example.iot.message;

import akka.actor.typed.ActorRef;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/31 下午2:10 <br/>
 * @Author: youzhihao
 */
public class QueryDeviceGroupTemperatureReq implements Command {

    private final long requestId;
    private final String groupId;
    private final ActorRef<QueryDeviceGroupTemperatureRes> replyTo;

    public QueryDeviceGroupTemperatureReq(long requestId, String groupId, ActorRef<QueryDeviceGroupTemperatureRes> replyTo) {
        this.requestId = requestId;
        this.groupId = groupId;
        this.replyTo = replyTo;
    }

    public long getRequestId() {
        return requestId;
    }

    public String getGroupId() {
        return groupId;
    }

    public ActorRef<QueryDeviceGroupTemperatureRes> getReplyTo() {
        return replyTo;
    }
}
