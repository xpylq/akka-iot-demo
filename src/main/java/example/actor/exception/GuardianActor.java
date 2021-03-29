package example.actor.exception;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/29 下午6:49 <br/>
 * @Author: youzhihao
 */
public class GuardianActor extends AbstractBehavior<String> {

    public static Behavior<String> create() {
        return Behaviors.setup(GuardianActor::new);
    }

    private final ActorRef<String> child;

    private GuardianActor(ActorContext<String> context) {
        super(context);
        child = context.spawn(Behaviors.supervise(ChildActor.create()).onFailure(SupervisorStrategy.restart()), "supervised-actor");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder().onMessageEquals("failChild", this::onFailChild).build();
    }

    private Behavior<String> onFailChild() {
        child.tell("fail");
        return this;
    }
}

