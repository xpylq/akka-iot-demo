package example.actor.lifecycle;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/29 下午6:33 <br/>
 * @Author: youzhihao
 */
class StartStopActor1 extends AbstractBehavior<String> {

    public static Behavior<String> create() {
        return Behaviors.setup(StartStopActor1::new);
    }

    private StartStopActor1(ActorContext<String> context) {
        super(context);
        System.out.println("first started");
        context.spawn(StartStopActor2.create(), "second");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
            // 接收stop消息，处理方法为Behaviors::stopped，起内部会发送PostStop信号
            .onMessageEquals("stop", Behaviors::stopped)
            // 处理PostStop信号，处理方法为onPostStop()
            .onSignal(PostStop.class, signal -> onPostStop())
            .build();
    }

    private Behavior<String> onPostStop() {
        System.out.println("first stopped");
        return this;
    }
}
