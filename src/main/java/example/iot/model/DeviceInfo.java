package example.iot.model;

import java.util.Optional;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: 设备信息<br />
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/31 下午2:13 <br/>
 * @Author: youzhihao
 */
public class DeviceInfo {

    private String groupId;

    private String deviceId;

    private String code;

    private Optional<Double> temperature;

    public DeviceInfo(String groupId, String deviceId, String code, Optional<Double> temperature) {
        this.groupId = groupId;
        this.deviceId = deviceId;
        this.code = code;
        this.temperature = temperature;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getCode() {
        return code;
    }

    public Optional<Double> getTemperature() {
        return temperature;
    }
}
