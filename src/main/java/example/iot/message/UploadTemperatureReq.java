package example.iot.message;

import akka.actor.typed.ActorRef;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:23 <br/>
 * @Author: youzhihao
 */
public class UploadTemperatureReq implements Command {

    private final long requestId;
    private final double value;
    private final ActorRef<UploadTemperatureRes> replyTo;

    public UploadTemperatureReq(long requestId, double value, ActorRef<UploadTemperatureRes> replyTo) {
        this.requestId = requestId;
        this.value = value;
        this.replyTo = replyTo;
    }

    public long getRequestId() {
        return requestId;
    }

    public double getValue() {
        return value;
    }

    public ActorRef<UploadTemperatureRes> getReplyTo() {
        return replyTo;
    }
}
