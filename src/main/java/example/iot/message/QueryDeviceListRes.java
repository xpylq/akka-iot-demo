package example.iot.message;

import java.util.Set;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2021 <br/>
 * @Desc: <br/>
 * @ProjectName: akka-iot-demo <br/>
 * @Date: 2021/3/30 下午3:05 <br/>
 * @Author: youzhihao
 */
public class QueryDeviceListRes {

    private final long requestId;
    private final Set<String> ids;

    public QueryDeviceListRes(long requestId, Set<String> ids) {
        this.requestId = requestId;
        this.ids = ids;
    }

    public long getRequestId() {
        return requestId;
    }

    public Set<String> getIds() {
        return ids;
    }
}
