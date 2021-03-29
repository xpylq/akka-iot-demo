package example.actor.exception;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc:
 * 1. 主要用来演示actor的异常处理机制
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/29 下午6:52 <br/>
 * @Author: youzhihao
 */
public class Main {

    public static void main(String[] args) {
        ActorRef<String> testSystem = ActorSystem.create(GuardianActor.create(), "testSystem");
        testSystem.tell("failChild");
    }

}
