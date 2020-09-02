import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import org.hamcrest.Matcher;
import org.hamcrest.text.MatchesPattern;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class JIRAApiTest {

    @Test
    public void getExistingIssue() {

        Response response =
                        given().
                            auth().preemptive().basic("webinar5", "webinar5").
                            contentType(ContentType.JSON).
                        when().get("http://jira.hillel.it/rest/api/2/issue/WEBINAR-9060").
                        then().
                            contentType(ContentType.JSON).
                            statusCode(200).
                            extract().response();

        // assertEquals(response.statusCode(), 200);
        // assertEquals(response.contentType(), ContentType.JSON.toString());
        assertEquals("WEBINAR-9060", response.path("key")); // JSON Path syntax
    }

    @Test
    public void createIssueRaw(){

                given().
                    auth().preemptive().basic("webinar5", "webinar5").
                    contentType(ContentType.JSON).
                    body("{\n" +
                        "   \"fields\":{\n" +
                        "      \"summary\":\"Main order flow broken\",\n" +
                        "      \"issuetype\":{\n" +
                        "         \"id\":\"10105\",\n" +
                        "         \"name\":\"test\"\n" +
                        "      },\n" +
                        "      \"project\":{\n" +
                        "         \"id\":\"10508\"\n" +
                        "      },\n" +
                        "   \"reporter\": {\n" +
                        "      \"name\": \"webinar5\"\n" +
                        "    }\n" +
                        "   }\n" +
                        "}\u2029").
                when().
                    post("https://jira.hillel.it/rest/api/2/issue").
                then().
                    contentType(ContentType.JSON).
                    statusCode(201).
                    extract().response().print();
    }

    @Test
    public void createIssue() {
        Response response = JiraAPISteps.createIssue();
        assertEquals(201, response.statusCode());
        response.then().extract().path("key");
        // TODO check that id matches pattern
    }
}





//        Cookies coockies = response.getDetailedCookies();

//        final Matcher<String> matcher = new MatchesPattern(Pattern.compile("[A-Z]+\\-[0-9]+"));
//        assertTrue(matcher.matches("WEBINAR-9060"));
// TODO preemptive - что дает?