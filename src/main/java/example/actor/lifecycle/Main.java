package example.actor.lifecycle;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc:
 * 1. 主要演示actor之间的生命周期
 * 2. 当一个actor停止的时候，会先递归停止所有子actor
 * 3. 常用方法:
 *  * Behaviors.stopped()会停止当前actor，其内部会递归先停止该actor的子actor
 *  * context.stop(childRef)会指定停止某一个子actor
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/29 下午6:39 <br/>
 * @Author: youzhihao
 */
public class Main {

    public static void main(String[] args) {
        ActorRef<String> testSystem = ActorSystem.create(StartStopActor1.create(), "testSystem");
        testSystem.tell("stop");
    }

}
