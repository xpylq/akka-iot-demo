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
 * @Date: 2021/3/29 下午6:00 <br/>
 * @Author: youzhihao
 */
public class GuardianActor extends AbstractBehavior<String> {

    public static Behavior<String> create() {
        return Behaviors.setup(GuardianActor::new);
    }

    private GuardianActor(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder().onMessageEquals("start", this::start).build();
    }

    private Behavior<String> start() {
        ActorRef<String> firstRef = getContext().spawn(PrintMyActorRefActor.create(), "first-actor");

        System.out.println("First: " + firstRef);
        firstRef.tell("printit");
        return Behaviors.same();
    }
}
