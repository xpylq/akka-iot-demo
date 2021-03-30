package example.iot.message;

import akka.actor.typed.ActorRef;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:01 <br/>
 * @Author: youzhihao
 */
public final class RegisterDeviceRes implements Command {

    private final ActorRef<Command> device;

    public RegisterDeviceRes(ActorRef<Command> device) {
        this.device = device;
    }

    public ActorRef<Command> getDevice() {
        return device;
    }
}
