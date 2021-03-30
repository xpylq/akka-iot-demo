package example.iot;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example.iot.message.Command;
import example.iot.message.DeviceTerminatedReq;
import example.iot.message.QueryDeviceListReq;
import example.iot.message.QueryDeviceListRes;
import example.iot.message.RegisterDeviceReq;
import example.iot.message.RegisterDeviceRes;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: 设备组Actor <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午2:18 <br/>
 * @Author: youzhihao
 */
public class DeviceGroup extends AbstractBehavior<Command> {


    private final String groupId;

    /**
     * 保存该设备组Actor下的所有设备Actor
     * key: 设备ID
     */
    private final Map<String, ActorRef<Command>> deviceIdToActor = new HashMap<>();

    private DeviceGroup(ActorContext<Command> context, String groupId) {
        super(context);
        this.groupId = groupId;
        context.getLog().info("DeviceGroup {} started", groupId);
    }

    public static Behavior<Command> create(String groupId) {
        return Behaviors.setup(context -> new DeviceGroup(context, groupId));
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
            .onMessage(RegisterDeviceReq.class, this::onRegisterDevice)
            .onMessage(QueryDeviceListReq.class, r -> r.getGroupId().equals(groupId), this::onQueryDeviceList)
            .onMessage(DeviceTerminatedReq.class, this::onDeviceTerminated)
            // 处理设备组Actor停止信号
            .onSignal(PostStop.class, signal -> onPostStop())
            .build();
    }

    /**
     * 处理设备注册请求
     */
    private DeviceGroup onRegisterDevice(RegisterDeviceReq registerDeviceReq) {
        if (this.groupId.equals(registerDeviceReq.getGroupId())) {
            ActorRef<Command> deviceActor = deviceIdToActor.get(registerDeviceReq.getDeviceId());
            if (deviceActor != null) {
                getContext().getLog().info("Device actor {} is exist", registerDeviceReq.getDeviceId());
                registerDeviceReq.getReplyTo().tell(new RegisterDeviceRes(deviceActor));
            } else {
                getContext().getLog().info("Creating device actor for {}", registerDeviceReq.getDeviceId());
                deviceActor = getContext().spawn(Device.create(groupId, registerDeviceReq.getDeviceId()),
                    "device-" + registerDeviceReq.getDeviceId());
                deviceIdToActor.put(registerDeviceReq.getDeviceId(), deviceActor);
                registerDeviceReq.getReplyTo().tell(new RegisterDeviceRes(deviceActor));
            }
        } else {
            getContext().getLog().warn("Ignoring TrackDevice request for {}. This actor is responsible for {}.",
                registerDeviceReq.getGroupId(), this.groupId);
        }
        return this;
    }

    private DeviceGroup onQueryDeviceList(QueryDeviceListReq r) {
        r.getReplyTo().tell(new QueryDeviceListRes(r.getRequestId(), deviceIdToActor.keySet()));
        return this;
    }

    private DeviceGroup onDeviceTerminated(DeviceTerminatedReq t) {
        getContext().getLog().info("Device actor for {} has been terminated", t.getDeviceId());
        deviceIdToActor.remove(t.getDeviceId());
        return this;
    }


    private DeviceGroup onPostStop() {
        getContext().getLog().info("DeviceGroup {} stopped", groupId);
        return this;
    }
}