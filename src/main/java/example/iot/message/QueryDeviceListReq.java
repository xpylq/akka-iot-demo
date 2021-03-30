package example.iot.message;

import akka.actor.typed.ActorRef;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:03 <br/>
 * @Author: youzhihao
 */
public class QueryDeviceListReq implements Command {

    private final long requestId;
    private final String groupId;
    private final ActorRef<QueryDeviceListRes> replyTo;

    public QueryDeviceListReq(long requestId, String groupId, ActorRef<QueryDeviceListRes> replyTo) {
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

    public ActorRef<QueryDeviceListRes> getReplyTo() {
        return replyTo;
    }
}
