package receive;

import Util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * @Author: ZhuRuiJie
 * @Date: 2019/5/9 14:50
 * @Description:
 * 第二个实例，同时启动多个worker，将接受一个任务发出的消息，轮流接受
 */
public class Worker {
    private final static String QUEUE_NAME = "hello";
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] argv) throws Exception {
        Channel channel = ConnectionUtils.getChannel();

//        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //durable = true 为消息持久化
        boolean durable = true ;
        channel.queueDeclare(TASK_QUEUE_NAME,durable,false,false,null);

        System.out.println("[*]等待消息。退出按CTRL + C");


        channel.basicQos(1);//一次只接受一个未包装的消息（见下文）

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            try {
                doWork(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(" [x] Done");
                //回复ack
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
//        boolean autoAck = true ; //确认如下
        boolean autoAck = false ; //取消自动确认
        channel.basicConsume(QUEUE_NAME,autoAck,deliverCallback,consumerTag -> {});

    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(1000);
        }
    }
}
