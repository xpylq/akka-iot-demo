package example.actor.hierarchy;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/29 下午5:55 <br/>
 * @Author: youzhihao
 */
public class PrintMyActorRefActor extends AbstractBehavior<String> {

    static Behavior<String> create() {
        return Behaviors.setup(PrintMyActorRefActor::new);
    }

    private PrintMyActorRefActor(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder().onMessageEquals("printit", this::printIt).build();
    }

    private Behavior<String> printIt() {
        ActorRef<String> secondRef = getContext().spawn(Behaviors.empty(), "second-actor");
        System.out.println("Second: " + secondRef);
        return this;
    }
}

