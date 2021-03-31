package example.iot;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import example.iot.message.Command;
import example.iot.message.DeviceTerminatedReq;
import example.iot.message.QueryDeviceTemperatureReq;
import example.iot.message.QueryDeviceTemperatureRes;
import example.iot.message.UploadTemperatureReq;
import example.iot.message.UploadTemperatureRes;
import java.util.Optional;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: 设备Actor <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 上午9:44 <br/>
 * @Author: youzhihao
 */
public class Device extends AbstractBehavior<Command> {

    /**
     * 设备组ID
     */
    private final String groupId;

    /**
     * 设备ID
     */
    private final String deviceId;

    /**
     * 最后一次读数
     */
    private Optional<Double> lastTemperatureReading = Optional.empty();

    private Device(ActorContext<Command> context, String groupId, String deviceId) {
        super(context);
        this.groupId = groupId;
        this.deviceId = deviceId;
        context.getLog().info("Device actor {}-{} started", groupId, deviceId);
    }

    /**
     * 创建设备Actor
     */
    public static Behavior<Command> create(String groupId, String deviceId) {
        return Behaviors.setup(context -> new Device(context, groupId, deviceId));
    }
    
    /**
     * 定义消息和信号接收的类型，以及对应处理的逻辑
     */
    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
            .onMessage(UploadTemperatureReq.class, this::onUploadTemperature)
            .onMessage(QueryDeviceTemperatureReq.class, this::onQueryDeviceTemperature)
            .onMessage(DeviceTerminatedReq.class, m -> Behaviors.stopped())
            .onSignal(PostStop.class, signal -> onPostStop())
            .build();
    }


    private Behavior<Command> onUploadTemperature(UploadTemperatureReq r) {
        getContext().getLog().info("Recorded temperature reading {} with {}", r.getValue(), r.getRequestId());
        lastTemperatureReading = Optional.of(r.getValue());
        r.getReplyTo().tell(new UploadTemperatureRes(r.getRequestId()));
        return this;
    }

    private Behavior<Command> onQueryDeviceTemperature(QueryDeviceTemperatureReq r) {
        r.getReplyTo().tell(new QueryDeviceTemperatureRes(r.getRequestId(), this.deviceId, lastTemperatureReading));
        return this;
    }

    private Behavior<Command> onPostStop() {
        getContext().getLog().info("Device actor {}-{} stopped", groupId, deviceId);
        return Behaviors.stopped();
    }
}
