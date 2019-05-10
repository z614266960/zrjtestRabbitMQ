package publish;

import Util.ConnectionUtils;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ZhuRuiJie
 * @Date: 2019/5/9 16:05
 * @Description:
 * 交换路由
 */
public class EmitLog {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtils.getChannel();

        //param : 路由名，方式fanout
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");

        String message = "message-1";

        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
    }
}
