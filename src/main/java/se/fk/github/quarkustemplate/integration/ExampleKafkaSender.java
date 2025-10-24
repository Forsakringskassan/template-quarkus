package se.fk.github.quarkustemplate.integration;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.fk.gradle.examples.asyncapi.ExempelRtfResponsePayload;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class ExampleKafkaSender
{
   private static final Logger LOG = LoggerFactory.getLogger(ExampleKafkaSender.class);

   private final Emitter<String> emitter;
   private final ObjectMapper objectMapper;

   public ExampleKafkaSender(
         @Channel("exempel-rtf-responses") Emitter<String> emitter)
   {
      this.emitter = emitter;
      this.objectMapper = new ObjectMapper();
   }

   public void sendExempelRtfResponse(ExempelRtfResponsePayload payload)
   {
      try
      {
         String jsonPayload = objectMapper.writeValueAsString(payload);

         // Create metadata with optional Kafka headers if needed
         OutgoingKafkaRecordMetadata<String> metadata = OutgoingKafkaRecordMetadata.<String> builder()
               .withKey(payload.getProcessId())
               .build();

         Message<String> message = Message.of(jsonPayload)
               .addMetadata(metadata);

         emitter.send(message);

         LOG.info("Sent exempel RTF response for processId: {}", payload.getProcessId());
      }
      catch (JsonProcessingException e)
      {
         LOG.error("Failed to serialize exempel RTF response payload", e);
         throw new RuntimeException("Failed to send exempel RTF response", e);
      }
   }
}
