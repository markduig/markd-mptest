import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.*;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import java.io.File;

@TestMethodOrder(OrderAnnotation.class)
public class testPetStoreCRUD {



    @Test
    @Order(1)
    public void createPet() {
        //File createPetFile = new File ("src/test/resources/createpet.json");

        String jsonCreatePet= "{\n" +
                "  \"id\": 20321,\n" +
                "  \"category\": {\n" +
                "    \"id\": 20321,\n" +
                "    \"name\": \"daredevil\"\n" +
                "  },\n" +
                "  \"name\": \"Ben\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://photobucket.com/cutebenpics\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 30321,\n" +
                "      \"name\": \"description\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri("https://petstore.swagger.io/v2/pet")
                        .setPort(443)
                .setContentType(ContentType.JSON)
                .setBody(jsonCreatePet);

        RequestSpecification reqSpec = reqBuilder.build();
        RequestSpecification reqSpecHit = RestAssured.given(reqSpec).log().all();


        Response response = reqSpecHit.post();
        ResponseBody body = response.getBody();
        String strBody = body.asString();
        Assert.assertEquals(response.statusCode() /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        Assert.assertTrue(strBody.contains("\"id\":20321,"));
        Assert.assertTrue(strBody.contains("\"name\":\"Ben\","));
        Assert.assertTrue(strBody.contains("https://photobucket.com/cutebenpics"));
    }

    @Test
    @Order(2)
    public void checkPet() {

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri("https://petstore.swagger.io/v2/pet/20321")
                .setPort(443);

        RequestSpecification reqSpec = reqBuilder.build();
        RequestSpecification reqSpecHit = RestAssured.given(reqSpec);


        Response response = reqSpecHit.get();
        ResponseBody body = response.getBody();
        String strBody = body.asString();
        Assert.assertEquals(response.statusCode() /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        Assert.assertTrue(strBody.contains("\"id\":20321,"));
        Assert.assertTrue(strBody.contains("\"name\":\"Ben\","));
        Assert.assertTrue(strBody.contains("https://photobucket.com/cutebenpics"));
    }

    @Test
    @Order(3)
    public void updatePet() {

        String jsonCreatePet= "{\n" +
                "  \"id\": 20321,\n" +
                "  \"category\": {\n" +
                "    \"id\": 20321,\n" +
                "    \"name\": \"daredevil\"\n" +
                "  },\n" +
                "  \"name\": \"PeterParker\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://photobucket.com/cutebenpics\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 30321,\n" +
                "      \"name\": \"description\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri("https://petstore.swagger.io/v2/pet")
                .setPort(443)
                .setContentType(ContentType.JSON)
                .setBody(jsonCreatePet);

        RequestSpecification reqSpec = reqBuilder.build();
        RequestSpecification reqSpecHit = RestAssured.given(reqSpec).log().all();


        Response response = reqSpecHit.put();
        ResponseBody body = response.getBody();
        String strBody = body.asString();
        Assert.assertEquals(response.statusCode() /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        Assert.assertTrue(strBody.contains("\"id\":20321,"));
        Assert.assertFalse(strBody.contains("\"name\":\"Ben\","));
        Assert.assertTrue(strBody.contains("PeterParker"));

    }
    @Test
    @Order(4)
    public void deletePet() {

        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri("https://petstore.swagger.io/v2/pet/20321")
                .setPort(443);

        RequestSpecification reqSpec = reqBuilder.build();
        RequestSpecification reqSpecHit = RestAssured.given(reqSpec);


        Response response = reqSpecHit.delete();
        ResponseBody body = response.getBody();
        String strBody = body.asString();
        Assert.assertEquals(response.statusCode() /*actual value*/, 200 /*expected value*/, "Correct status code returned for deletion");

        Response resp2= reqSpecHit.get();
        Assert.assertEquals(resp2.statusCode() /*actual value*/, 404 /*expected value*/, "Confirm Deletion");


    }







}
