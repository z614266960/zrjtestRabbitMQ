package publish;

import Util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: ZhuRuiJie
 * @Date: 2019/5/9 14:37
 * @Description:
 * 第二个实例，在main中带参模拟发送消息
 */
public class NewTask {
    private  final  static String QUEUE_NAME ="hello";
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = ConnectionUtils.getChannel();
//        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //durable = true 为消息持久化
        boolean durable = true ;
        channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);

        String message = String.join("",args);

        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("sent:"+message);
    }
}
