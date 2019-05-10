package publish;

import Util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Author: ZhuRuiJie
 * @Date: 2019/5/9 10:00
 * @Description:
 * 第一个实例，演示如何发送消息
 */
public class sendMsg {
    private  final  static String QUEUE_NAME ="hello";
    public  static  void main(String [] argv)throws Exception {

        Channel channel = ConnectionUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String message = "Hello World!";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("sent:"+message);
    }
}
