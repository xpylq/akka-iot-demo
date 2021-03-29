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
class StartStopActor2 extends AbstractBehavior<String> {

    public static Behavior<String> create() {
        return Behaviors.setup(StartStopActor2::new);
    }

    private StartStopActor2(ActorContext<String> context) {
        super(context);
        System.out.println("second started");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder().onSignal(PostStop.class, signal -> onPostStop()).build();
    }

    private Behavior<String> onPostStop() {
        System.out.println("second stopped");
        return this;
    }
}
