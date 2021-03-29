package example.actor.exception;

import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.PreRestart;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/29 下午6:50 <br/>
 * @Author: youzhihao
 */
public class ChildActor extends AbstractBehavior<String> {

    static Behavior<String> create() {
        return Behaviors.setup(ChildActor::new);
    }

    private ChildActor(ActorContext<String> context) {
        super(context);
        System.out.println("child actor started");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
            .onMessageEquals("fail", this::fail)
            .onSignal(PreRestart.class, signal -> preRestart())
            .onSignal(PostStop.class, signal -> postStop())
            .build();
    }

    private Behavior<String> fail() {
        System.out.println("child actor fails now");
        throw new RuntimeException("I failed!");
    }

    private Behavior<String> preRestart() {
        System.out.println("child second will be restarted");
        return this;
    }

    private Behavior<String> postStop() {
        System.out.println("child second stopped");
        return this;
    }
}
