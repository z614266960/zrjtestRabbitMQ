package receive;

import Util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


/**
 * @Author: ZhuRuiJie
 * @Date: 2019/5/9 10:14
 * @Description:
 * 第一个实例，如何接受消息
 */
public class Recv {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        Channel channel = ConnectionUtils.getChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println("[*]等待消息。退出按CTRL + C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
