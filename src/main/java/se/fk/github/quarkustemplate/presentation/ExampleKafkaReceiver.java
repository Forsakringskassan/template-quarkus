package se.fk.github.quarkustemplate.presentation;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class ExampleKafkaReceiver
{
   private static final Logger LOGGER = LoggerFactory.getLogger(ExampleKafkaReceiver.class);
   private final AtomicInteger receivedMessageCount = new AtomicInteger(0);

   @Incoming("exempel-rtf-requests")
   public CompletionStage<Void> receiveExempelRtfRequest(Message<String> message)
   {
      try
      {
         String payload = message.getPayload();
         LOGGER.info("Received exempel RTF request: %s", payload);
         receivedMessageCount.incrementAndGet();
         return message.ack();
      }
      catch (Exception e)
      {
         LOGGER.error("Error processing exempel RTF request", e);
         return message.nack(e);
      }
   }

   public int getReceivedMessageCount()
   {
      return receivedMessageCount.get();
   }

   public void resetCounter()
   {
      receivedMessageCount.set(0);
   }
}
