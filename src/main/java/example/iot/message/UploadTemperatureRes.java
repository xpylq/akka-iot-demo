package example.iot.message;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:32 <br/>
 * @Author: youzhihao
 */
public class UploadTemperatureRes implements Command {

    private final long requestId;

    public UploadTemperatureRes(long requestId) {
        this.requestId = requestId;
    }

    public long getRequestId() {
        return requestId;
    }
}
