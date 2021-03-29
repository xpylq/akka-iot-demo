package example.actor.hierarchy;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc:
 *  1. 主要是演示actor之间的层级结构
 *  2. actor的层级结构类似URL，父子关系，通过URL来描述
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/29 下午6:06 <br/>
 * @Author: youzhihao
 */
public class Main {

    public static void main(String[] args) {
        ActorRef<String> testSystem = ActorSystem.create(GuardianActor.create(), "testSystem");
        testSystem.tell("start");
    }
}
