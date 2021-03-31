package example.iot.message.common;

import example.iot.message.Command;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: 设备状态 <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/31 下午2:47 <br/>
 * @Author: youzhihao
 */
public class DeviceStatus implements Command {

    private String deviceId;

    private String status;

    public DeviceStatus(String deviceId, String status) {
        this.deviceId = deviceId;
        this.status = status;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getStatus() {
        return status;
    }
}
