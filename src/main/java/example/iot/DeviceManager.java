package example.iot;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example.iot.message.Command;
import example.iot.message.DeviceGroupTerminatedReq;
import example.iot.message.QueryDeviceListReq;
import example.iot.message.QueryDeviceListRes;
import example.iot.message.RegisterDeviceReq;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: 设备管理Actor <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午2:13 <br/>
 * @Author: youzhihao
 */
public class DeviceManager extends AbstractBehavior<Command> {

    // <groupId,设备组Actor>
    private final Map<String, ActorRef<Command>> groupIdToActor = new HashMap<>();

    private DeviceManager(ActorContext<Command> context) {
        super(context);
        context.getLog().info("DeviceManager started");
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(DeviceManager::new);
    }


    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
            .onMessage(RegisterDeviceReq.class, this::onRegisterDevice)
            .onMessage(QueryDeviceListReq.class, this::onQueryDeviceList)
            .onMessage(DeviceGroupTerminatedReq.class, this::onTerminated)
            .onSignal(PostStop.class, signal -> onPostStop())
            .build();
    }

    /**
     * 处理设备注册请求
     */
    private DeviceManager onRegisterDevice(RegisterDeviceReq registerDeviceReq) {
        String groupId = registerDeviceReq.getGroupId();
        ActorRef<Command> ref = groupIdToActor.get(groupId);
        if (ref != null) {
            ref.tell(registerDeviceReq);
        } else {
            getContext().getLog().info("Creating device group actor for {}", groupId);
            ActorRef<Command> groupActor = getContext().spawn(DeviceGroup.create(groupId), "group-" + groupId);
            // 监听设备组Actor的DeviceGroupTerminatedReq消息
            getContext().watchWith(groupActor, new DeviceGroupTerminatedReq(groupId));
            groupActor.tell(registerDeviceReq);
            groupIdToActor.put(groupId, groupActor);
        }
        return this;
    }

    /**
     * 处理查询设备列表请求
     */
    private DeviceManager onQueryDeviceList(QueryDeviceListReq request) {
        ActorRef<Command> ref = groupIdToActor.get(request.getGroupId());
        if (ref != null) {
            ref.tell(request);
        } else {
            request.getReplyTo().tell(new QueryDeviceListRes(request.getRequestId(), Collections.emptySet()));
        }
        return this;
    }

    private DeviceManager onTerminated(DeviceGroupTerminatedReq t) {
        getContext().getLog().info("Device group actor for {} has been terminated", t.getGroupId());
        groupIdToActor.remove(t.getGroupId());
        return this;
    }


    private DeviceManager onPostStop() {
        getContext().getLog().info("DeviceManager stopped");
        return this;
    }
}