package example.iot.message;

import java.util.Optional;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:35 <br/>
 * @Author: youzhihao
 */
public class QueryDeviceTemperatureRes implements Command {

    private final String requestId;

    private final String deviceId;

    private final Optional<Double> value;

    public QueryDeviceTemperatureRes(String requestId, String deviceId, Optional<Double> value) {
        this.requestId = requestId;
        this.deviceId = deviceId;
        this.value = value;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Optional<Double> getValue() {
        return value;
    }
}
