package example.iot;


import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.javadsl.TimerScheduler;
import example.iot.message.Command;
import example.iot.message.QueryDeviceGroupTemperatureRes;
import example.iot.message.QueryDeviceTemperatureReq;
import example.iot.message.QueryDeviceTemperatureRes;
import example.iot.message.common.DeviceStatus;
import example.iot.message.common.ReqStatus;
import example.iot.model.DeviceInfo;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/31 下午2:00 <br/>
 * @Author: youzhihao
 */
public class DeviceGroupQuery extends AbstractBehavior<Command> {

    private final String requestId;

    private final String groupId;

    private final ActorRef<QueryDeviceGroupTemperatureRes> requester;

    /**
     * 已相应的设备
     */
    private Map<String, DeviceInfo> repliesSoFar = new HashMap<>();

    /**
     * 仍未相应的设备
     */
    private final Set<String> stillWaiting;

    public static Behavior<Command> create(Map<String, ActorRef<Command>> deviceIdToActor, String requestId, String groupId,
        ActorRef<QueryDeviceGroupTemperatureRes> requester, Duration timeout) {
        return Behaviors
            .setup(context -> Behaviors.withTimers(timers -> new DeviceGroupQuery(deviceIdToActor, requestId, groupId, requester, timeout, context, timers)));
    }


    private DeviceGroupQuery(Map<String, ActorRef<Command>> deviceIdToActor, String requestId, String groupId,
        ActorRef<QueryDeviceGroupTemperatureRes> requester, Duration timeout, ActorContext<Command> context,
        TimerScheduler<Command> timers) {
        super(context);
        this.requestId = requestId;
        this.groupId = groupId;
        this.requester = requester;
        timers.startSingleTimer(new ReqStatus(requestId, "timeout"), timeout);
        for (Map.Entry<String, ActorRef<Command>> entry : deviceIdToActor.entrySet()) {
            context.watchWith(entry.getValue(), new DeviceStatus(entry.getKey(), "terminated"));
            entry.getValue().tell(new QueryDeviceTemperatureReq(requestId, this.getContext().messageAdapter(QueryDeviceTemperatureRes.class, res -> res)));
        }
        stillWaiting = new HashSet<>(deviceIdToActor.keySet());
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
            .onMessage(QueryDeviceTemperatureRes.class, this::onQueryDeviceTemperature)
            .onMessage(DeviceStatus.class, this::onProcessDeviceStatus)
            .onMessage(ReqStatus.class, this::onProcessReqStatus)
            .build();
    }

    private Behavior<Command> onQueryDeviceTemperature(QueryDeviceTemperatureRes r) {
        String deviceId = r.getDeviceId();
        repliesSoFar.put(deviceId, new DeviceInfo(this.groupId, r.getDeviceId(), "success", r.getValue()));
        stillWaiting.remove(deviceId);
        return isAllDeviceComplete();
    }

    private Behavior<Command> onProcessDeviceStatus(DeviceStatus deviceStatus) {
        if (stillWaiting.contains(deviceStatus.getDeviceId()) && "terminated".equals(deviceStatus.getStatus())) {
            repliesSoFar.put(deviceStatus.getDeviceId(), new DeviceInfo(this.groupId, deviceStatus.getDeviceId(), "terminated", Optional.empty()));
            stillWaiting.remove(deviceStatus.getDeviceId());
        }
        return isAllDeviceComplete();
    }

    private Behavior<Command> onProcessReqStatus(ReqStatus reqStatus) {
        if ("timeout".equals(reqStatus.getStatus())) {
            for (String deviceId : stillWaiting) {
                repliesSoFar.put(deviceId, new DeviceInfo(this.groupId, deviceId, "timeout", Optional.empty()));
            }
            stillWaiting.clear();
        }
        return isAllDeviceComplete();
    }

    private Behavior<Command> isAllDeviceComplete() {
        if (stillWaiting.isEmpty()) {
            requester.tell(new QueryDeviceGroupTemperatureRes(requestId, groupId, repliesSoFar));
            return Behaviors.stopped();
        } else {
            return this;
        }
    }
}
