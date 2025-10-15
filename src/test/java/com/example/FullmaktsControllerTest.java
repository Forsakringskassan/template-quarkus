package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class FullmaktsControllerTest
{
   @Test
   void testAlternatives()
   {
      String actualResponse = given()
            .when().get("/api/template/alternatives")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

      assertThat(actualResponse).isEqualToIgnoringWhitespace("""
            {
                "alternatives": [
                    {
                        "id": "1",
                        "name": "Alt 1"
                    },
                    {
                        "id": "2",
                        "name": "Alt 2"
                    }
                ]
            }
            """);
   }

   @Test
   void testCheckOmbud()
   {
      String actualResponse = given()
            .contentType("application/json")
            .body("""
                  {
                      "personnummer": "123456789012"
                  }
                  """)
            .when().put("/api/template/ombud")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .asString();

      assertThat(actualResponse).isEqualToIgnoringWhitespace("""
            {
                "name": "Test Testsson"
            }
            """);
   }
}
