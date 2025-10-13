package com.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.model.Alternative;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.model.AlternativesResponse;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.model.OmbudRequest;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.model.OmbudResponse;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.FullmaktsControllerApi;

@ApplicationScoped
@Path("/api")
public class FullmaktsControllerApiImpl implements FullmaktsControllerApi
{

   @Override
   public AlternativesResponse apiAlternativesGet()
   {
      return new AlternativesResponse()
            .addAlternativesItem(new Alternative().id("1").name("Alt 1"))
            .addAlternativesItem(new Alternative().id("2").name("Alt 2"));
   }

   @Override
   public OmbudResponse apiOmbudPut(OmbudRequest ombudRequest)
   {
      return new OmbudResponse()
            .name("Namnet");
   }
}
