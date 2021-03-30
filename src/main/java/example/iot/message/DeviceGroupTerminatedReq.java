package example.iot.message;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:06 <br/>
 * @Author: youzhihao
 */
public class DeviceGroupTerminatedReq implements Command {

    private final String groupId;

    public DeviceGroupTerminatedReq(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }
}
