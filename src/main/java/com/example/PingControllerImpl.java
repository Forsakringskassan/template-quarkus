package com.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.model.PingResponse;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.PingControllerApi;

@ApplicationScoped
@Path("/api/ping")
public class PingControllerImpl implements PingControllerApi
{

   @Override
   public PingResponse apiPingGet()
   {
      return new PingResponse()
            .pong(true);
   }
}
