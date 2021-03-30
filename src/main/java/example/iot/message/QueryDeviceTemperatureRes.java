package example.iot.message;

import java.util.Optional;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:35 <br/>
 * @Author: youzhihao
 */
public class QueryDeviceTemperatureRes {

    private final long requestId;
    private final Optional<Double> value;

    public QueryDeviceTemperatureRes(long requestId, Optional<Double> value) {
        this.requestId = requestId;
        this.value = value;
    }


    public long getRequestId() {
        return requestId;
    }

    public Optional<Double> getValue() {
        return value;
    }
}
