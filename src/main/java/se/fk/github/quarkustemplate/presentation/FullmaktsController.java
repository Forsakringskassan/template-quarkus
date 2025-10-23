package se.fk.github.quarkustemplate.presentation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import se.fk.github.quarkustemplate.logic.FullmaktsService;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.FullmaktsControllerApi;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.model.AlternativesResponse;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.model.OmbudRequest;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.model.OmbudResponse;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Path("/api/template")
public class FullmaktsController implements FullmaktsControllerApi
{

   @Inject
   FullmaktsService fullmaktsService;

   @Inject
   PresentationMapper presentationMapper;

   @Override
   public AlternativesResponse getAlternatives()
   {
      LoggerFactory.getLogger(FullmaktsController.class).info("alternatives");
      var logicAlternatives = fullmaktsService.getAlternatives();
      var presentationDto = presentationMapper.toPresentation(logicAlternatives);
      return presentationMapper.toExternalApi(presentationDto);
   }

   @Override
   public OmbudResponse checkOmbud(OmbudRequest ombudRequest)
   {
      var presentationRequest = presentationMapper.fromExternalApi(ombudRequest);
      var logicRequest = presentationMapper.toLogic(presentationRequest);
      var logicResponse = fullmaktsService.checkOmbud(logicRequest);
      var presentationResponse = presentationMapper.toPresentation(logicResponse);
      return presentationMapper.toExternalApi(presentationResponse);
   }
}
