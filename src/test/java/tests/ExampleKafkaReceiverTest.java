package tests;

import common.JsonHelper;
import common.KafkaTestBase;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import se.fk.gradle.examples.asyncapi.ExempelRtfRequestPayload;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@QuarkusTest
class ExampleKafkaReceiverTest extends KafkaTestBase
{

   @Test
   void testReceiveExempelRtfRequest()
   {
      ExempelRtfRequestPayload payload = new ExempelRtfRequestPayload();
      payload.setProcessId("test-process-123");
      payload.setPersonNummer("19990101-0123");

      inMemorySource.send(JsonHelper.toJson(payload));

      await()
            .atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
               assertThat(receiver.getReceivedMessageCount())
                     .as("One message should have been received")
                     .isEqualTo(1);
            });
   }

   @Test
   void testReceiveMultipleExempelRtfRequests()
   {
      ExempelRtfRequestPayload payload1 = new ExempelRtfRequestPayload();
      payload1.setProcessId("test-process-456");
      payload1.setPersonNummer("19990101-0123");

      ExempelRtfRequestPayload payload2 = new ExempelRtfRequestPayload();
      payload2.setProcessId("test-process-789");
      payload2.setPersonNummer("19850515-1234");

      inMemorySource.send(JsonHelper.toJson(payload1));
      inMemorySource.send(JsonHelper.toJson(payload2));

      await()
            .atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
               assertThat(receiver.getReceivedMessageCount())
                     .as("Two messages should have been received")
                     .isEqualTo(2);
            });
   }
}
