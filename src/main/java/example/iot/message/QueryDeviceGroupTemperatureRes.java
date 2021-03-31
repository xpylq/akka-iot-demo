package example.iot.message;

import example.iot.model.DeviceInfo;
import java.util.Map;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/31 下午2:11 <br/>
 * @Author: youzhihao
 */
public class QueryDeviceGroupTemperatureRes implements Command {

    private final String requestId;

    private final String groupId;

    private final Map<String, DeviceInfo> deviceInfoMap;

    public QueryDeviceGroupTemperatureRes(String requestId, String groupId,
        Map<String, DeviceInfo> deviceInfoMap) {
        this.requestId = requestId;
        this.groupId = groupId;
        this.deviceInfoMap = deviceInfoMap;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getGroupId() {
        return groupId;
    }

    public Map<String, DeviceInfo> getDeviceInfoMap() {
        return deviceInfoMap;
    }
}
