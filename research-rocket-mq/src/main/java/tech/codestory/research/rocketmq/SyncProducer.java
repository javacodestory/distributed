package tech.codestory.research.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * 消息 Publish
 *
 * @author liaojunyong
 */
@Slf4j
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        int messageCount = 10;
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new
                DefaultMQProducer("demo_group_name");
        // Specify name server addresses.
        producer.setNamesrvAddr("192.168.1.9:9876");
        //Launch the instance.
        producer.start();
        try {
            for (int i = 0; i < messageCount; i++) {
                //Create a message instance, specifying topic, tag and message body.
                String messageBody = "Hello RocketMQ " + i;
                Message msg = new Message("demo-topic", "demo-tag",
                        messageBody.getBytes(StandardCharsets.UTF_8)
                );
                //Call send message to deliver message to one of brokers.
                SendResult sendResult = producer.send(msg);
                log.info("\r\n{}", sendResult);
            }
        } finally {
            //Shut down once the producer instance is not longer in use.
            producer.shutdown();
        }
    }
}
