package example.iot.message;

import akka.actor.typed.ActorRef;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:12 <br/>
 * @Author: youzhihao
 */
public class DeviceTerminatedReq implements Command {

    private final ActorRef<Command> device;
    private final String groupId;
    private final String deviceId;

    public DeviceTerminatedReq(ActorRef<Command> device, String groupId, String deviceId) {
        this.device = device;
        this.groupId = groupId;
        this.deviceId = deviceId;
    }

    public ActorRef<Command> getDevice() {
        return device;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
