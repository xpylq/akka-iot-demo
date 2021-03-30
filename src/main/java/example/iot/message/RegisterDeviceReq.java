package example.iot.message;

import akka.actor.typed.ActorRef;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:00 <br/>
 * @Author: youzhihao
 */
public class RegisterDeviceReq implements Command {

    private final String groupId;
    private final String deviceId;
    private final ActorRef<RegisterDeviceRes> replyTo;

    public RegisterDeviceReq(String groupId, String deviceId, ActorRef<RegisterDeviceRes> replyTo) {
        this.groupId = groupId;
        this.deviceId = deviceId;
        this.replyTo = replyTo;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public ActorRef<RegisterDeviceRes> getReplyTo() {
        return replyTo;
    }
}