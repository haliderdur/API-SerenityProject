package EU10.spartan.editor;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utilities.SpartanNewBase;
import utilities.SpartanUtil;


import java.util.LinkedHashMap;
import java.util.Map;


@Disabled
@SerenityTest
public class SpartanEditorPostTest extends SpartanNewBase {

    @DisplayName("Editor should be able to POST")
    @Test
    public void postSpartanAsEditor() {

        // create one spartan using util
        Map<String, Object> bodyMap = SpartanUtil.getRandomSpartanMap();

        System.out.println("bodyMap = " + bodyMap);

        // send a POST request as editor
        SerenityRest
                .given()
                .auth().basic("editor", "editor")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .log().body()
                .when()
                .post("/spartans")
                .then().log().all();

        Ensure.that("status code is 201", VstatusCode -> VstatusCode.statusCode(201));
        Ensure.that("content type is Json", VcontentType -> VcontentType.contentType(ContentType.JSON));
        Ensure.that("success message is A Spartan is Born!", Vmessage -> Vmessage.body("success", Matchers.is("A Spartan is Born!")));
        Ensure.that("id is not null", Vid -> Vid.body("data.id", Matchers.notNullValue()));
        Ensure.that("name is correct", Vname -> Vname.body("data.name", Matchers.is(bodyMap.get("name"))));
        Ensure.that("name is correct", Vgender -> Vgender.body("data.gender", Matchers.is(bodyMap.get("gender"))));
        Ensure.that("name is correct", Vphone -> Vphone.body("data.phone", Matchers.is(bodyMap.get("phone"))));

        String id = SerenityRest.lastResponse().jsonPath().getString("data.id");
        Ensure.that("check location header ends with newly generated id",
                Vid -> Vid.header("Location", Matchers.endsWith(id)));

    }


    /*
    we can give name to each execution using name = ""
    and if you want to get index of iteration, we can use {index}
    and if you want to include parameter in your test name, {0},{1},.... based on the order of parameters we provide
     */
    @ParameterizedTest(name = "New Spartan {index} - name: {0}")
    @CsvFileSource(resources = "/SpartanData.csv", numLinesToSkip = 1)
    public void postSpartanWithCSV(String name, String gender, long phone) {
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        // create one spartan using util
        Map<String, Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name",name);
        bodyMap.put("gender",gender);
        bodyMap.put("phone",phone);


        System.out.println("bodyMap = " + bodyMap);

        // send a POST request as editor
        SerenityRest
                .given()
                .auth().basic("editor", "editor")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .log().body()
                .when()
                .post("/spartans")
                .then().log().all();

        Ensure.that("status code is 201", VstatusCode -> VstatusCode.statusCode(201));
        Ensure.that("content type is Json", VcontentType -> VcontentType.contentType(ContentType.JSON));
        Ensure.that("success message is A Spartan is Born!", Vmessage -> Vmessage.body("success", Matchers.is("A Spartan is Born!")));
        Ensure.that("id is not null", Vid -> Vid.body("data.id", Matchers.notNullValue()));
        Ensure.that("name is correct", Vname -> Vname.body("data.name", Matchers.is(bodyMap.get("name"))));
        Ensure.that("name is correct", Vgender -> Vgender.body("data.gender", Matchers.is(bodyMap.get("gender"))));
        Ensure.that("name is correct", Vphone -> Vphone.body("data.phone", Matchers.is(bodyMap.get("phone"))));

        String id = SerenityRest.lastResponse().jsonPath().getString("data.id");
        Ensure.that("check location header ends with newly generated id",
                Vid -> Vid.header("Location", Matchers.endsWith(id)));
    }


}
