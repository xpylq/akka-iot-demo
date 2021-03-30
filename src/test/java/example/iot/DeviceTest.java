package example.iot;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import example.iot.message.Command;
import example.iot.message.RegisterDeviceReq;
import example.iot.message.RegisterDeviceRes;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: 设备Actor测试类 <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 上午11:15 <br/>
 * @Author: youzhihao
 */
public class DeviceTest {

    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Test
    public void testRegisterDevice() throws Exception {
        TestProbe<RegisterDeviceRes> device1 = testKit.createTestProbe(RegisterDeviceRes.class);
        // 创建设备管理Actor
        ActorRef<Command> deviceManager = testKit.spawn(DeviceManager.create());
        // 注册设备
        deviceManager.tell(new RegisterDeviceReq("group1", "device1", device1.getRef()));
        device1.receiveMessage();
    }


}
