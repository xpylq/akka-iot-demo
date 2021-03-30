package example.iot;

import akka.actor.testkit.typed.javadsl.TestKitJunitResource;
import akka.actor.testkit.typed.javadsl.TestProbe;
import akka.actor.typed.ActorRef;
import com.alibaba.fastjson.JSONObject;
import example.iot.message.Command;
import example.iot.message.QueryDeviceListReq;
import example.iot.message.QueryDeviceListRes;
import example.iot.message.RegisterDeviceReq;
import example.iot.message.RegisterDeviceRes;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: 设备Actor测试类 <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 上午11:15 <br/>
 * @Author: youzhihao
 */
public class DeviceTest {

    public static final Logger logger = LoggerFactory.getLogger("DeviceTest");

    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Test
    public void testRegisterDevice() throws Exception {
        TestProbe<RegisterDeviceRes> device1 = testKit.createTestProbe(RegisterDeviceRes.class);
        TestProbe<RegisterDeviceRes> device2 = testKit.createTestProbe(RegisterDeviceRes.class);
        TestProbe<RegisterDeviceRes> device3 = testKit.createTestProbe(RegisterDeviceRes.class);
        // 创建设备管理Actor
        ActorRef<Command> deviceManager = testKit.spawn(DeviceManager.create());
        // 注册设备1
        deviceManager.tell(new RegisterDeviceReq("group1", "device1", device1.getRef()));
        // 注册设备2
        deviceManager.tell(new RegisterDeviceReq("group1", "device2", device2.getRef()));
        // 模拟相同设备注册
        deviceManager.tell(new RegisterDeviceReq("group1", "device1", device3.getRef()));
        RegisterDeviceRes deviceRes1 = device1.receiveMessage();
        RegisterDeviceRes deviceRes2 = device2.receiveMessage();
        RegisterDeviceRes deviceRes3 = device3.receiveMessage();
        Assert.assertEquals(deviceRes1.getDevice(), deviceRes3.getDevice());
    }

    @Test
    public void testQueryDeviceList() throws Exception {
        TestProbe<RegisterDeviceRes> device1 = testKit.createTestProbe(RegisterDeviceRes.class);
        TestProbe<RegisterDeviceRes> device2 = testKit.createTestProbe(RegisterDeviceRes.class);
        TestProbe<QueryDeviceListRes> device3 = testKit.createTestProbe(QueryDeviceListRes.class);
        // 创建设备管理Actor
        ActorRef<Command> deviceManager = testKit.spawn(DeviceManager.create());
        // 注册设备1
        deviceManager.tell(new RegisterDeviceReq("group1", "device1", device1.getRef()));
        // 注册设备2
        deviceManager.tell(new RegisterDeviceReq("group1", "device2", device2.getRef()));
        device1.receiveMessage();
        device2.receiveMessage();
        // 查询设备组中的所有设备
        deviceManager.tell(new QueryDeviceListReq(System.currentTimeMillis(), "group1", device3.getRef()));
        logger.info("查询group1中的设备id为:{}", JSONObject.toJSONString(device3.receiveMessage().getIds()));
    }


}
