package EU10.spartan.admin;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Disabled
@SerenityTest
public class SpartanAdminGetTest {

    @BeforeAll
    public static void init() {
        // save baseUrl inside this variable so that we don't need to type it in each http method
        baseURI = "http://35.175.135.167:7000";
    }

    @Test
    public void getAllSpartan() {
        SerenityRest.given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .when()
                .get("/api/spartans")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void getOneSpartan() {
        SerenityRest.given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}");

        // if you send a request using serenityRest, the response object
        // can be obtained from the method called lastResponse() method withoud being saved separately
        System.out.println("Status Code = " + lastResponse().statusCode());

        // print id
        // instead of using response.path, we use lastResponse.path
        System.out.println("lastResponse().path(\"id\") = " + lastResponse().path("id"));

        // use jsonPath with lastResponse and get the name
        String name = lastResponse().jsonPath().getString("name");
        System.out.println("name = " + name);

    }

    @DisplayName("GET request with Serenity Assertion")
    @Test
    public void getOneSpartanAssertion() {
        SerenityRest.given()
                .accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}");

        // Serenity way of Assertion
        Ensure.that("Status code is 200", validatableResponse -> validatableResponse.statusCode(200));
        Ensure.that("Content type is Json", vRes -> vRes.contentType(ContentType.JSON));
        Ensure.that("ID is 15", vRes -> vRes.body("id", is(15)));
    }


}
