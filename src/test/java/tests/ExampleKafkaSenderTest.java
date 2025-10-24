package tests;

import common.JsonHelper;
import common.KafkaTestBase;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import se.fk.gradle.examples.asyncapi.ExempelRtfResponsePayload;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@QuarkusTest
class ExampleKafkaSenderTest extends KafkaTestBase
{

   @Test
   void testSendExempelRtfResponse()
   {
      ExempelRtfResponsePayload payload = new ExempelRtfResponsePayload();
      payload.setProcessId("test-process-123");
      payload.setPersonNummer("19990101-0123");
      payload.setRattTillForsakring(true);

      sender.sendExempelRtfResponse(payload);

      await()
            .atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
               assertThat(inMemorySink.received())
                     .as("One message should have been sent")
                     .hasSize(1);

               String sentMessage = inMemorySink.received().get(0).getPayload();
               ExempelRtfResponsePayload receivedPayload = JsonHelper.fromJson(
                     sentMessage, ExempelRtfResponsePayload.class);

               assertThat(receivedPayload.getProcessId()).isEqualTo("test-process-123");
               assertThat(receivedPayload.getPersonNummer()).isEqualTo("19990101-0123");
               assertThat(receivedPayload.getRattTillForsakring()).isTrue();
            });
   }

   @Test
   void testSendMultipleExempelRtfResponses() throws Exception
   {
      ExempelRtfResponsePayload payload1 = new ExempelRtfResponsePayload();
      payload1.setProcessId("test-process-456");
      payload1.setPersonNummer("19990101-0123");
      payload1.setRattTillForsakring(true);

      ExempelRtfResponsePayload payload2 = new ExempelRtfResponsePayload();
      payload2.setProcessId("test-process-789");
      payload2.setPersonNummer("19850515-1234");
      payload2.setRattTillForsakring(false);

      sender.sendExempelRtfResponse(payload1);
      sender.sendExempelRtfResponse(payload2);

      await()
            .atMost(Duration.ofSeconds(5))
            .untilAsserted(() -> {
               assertThat(inMemorySink.received())
                     .as("Two messages should have been sent")
                     .hasSize(2);

               String sentMessage1 = inMemorySink.received().get(0).getPayload();
               ExempelRtfResponsePayload receivedPayload1 = JsonHelper.fromJson(
                     sentMessage1, ExempelRtfResponsePayload.class);

               assertThat(receivedPayload1.getProcessId()).isEqualTo("test-process-456");
               assertThat(receivedPayload1.getRattTillForsakring()).isTrue();

               String sentMessage2 = inMemorySink.received().get(1).getPayload();
               ExempelRtfResponsePayload receivedPayload2 = JsonHelper.fromJson(
                     sentMessage2, ExempelRtfResponsePayload.class);

               assertThat(receivedPayload2.getProcessId()).isEqualTo("test-process-789");
               assertThat(receivedPayload2.getRattTillForsakring()).isFalse();
            });
   }
}
