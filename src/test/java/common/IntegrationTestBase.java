package common;

import io.quarkus.test.junit.QuarkusTest;
import se.fk.gradle.examples.jaxrsspec.controllers.generatedsource.model.OmbudRequest;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class IntegrationTestBase
{
   public String getAlternatives()
   {
      return given()
            .when().get(TestDataFactory.PATH_ALTERNATIVES)
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();
   }

   public static String checkOmbud(OmbudRequest ombudRequest)
   {
      return given()
            .contentType("application/json")
            .body(JsonHelper.toJsonPretty(ombudRequest))
            .when().put(TestDataFactory.PATH_OMBUD)
            .then()
            .extract()
            .body()
            .asString();
   }
}
