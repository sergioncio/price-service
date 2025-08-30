package com.example.pricing.bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceSteps {

    @LocalServerPort
    private int port;

    private String date;
    private long productId;
    private long brandId;
    private ResponseEntity<Map> response;

    @Given("an application date {string}")
    public void an_application_date(String d) { this.date = d; }

    @Given("a productId {long}")
    public void a_product_id(long id) { this.productId = id; }

    @Given("a brandId {long}")
    public void a_brand_id(long id) { this.brandId = id; }

    @When("I GET /prices")
    public void i_get_prices() {
        String url = String.format("http://localhost:%d/prices?applicationDate=%s&productId=%d&brandId=%d",
                port, date, productId, brandId);
        response = new RestTemplate().getForEntity(URI.create(url), Map.class);
    }

    @Then("the response has status {int}")
    public void the_response_has_status(Integer status) {
        assertEquals(status, response.getStatusCode().value());
    }

    @Then("the priceList is {int}")
    public void the_price_list_is(Integer expected) {
        assertEquals(expected, ((Number)response.getBody().get("priceList")).intValue());
    }

    @Then("the price is {string}")
    public void the_price_is(String expected) {
        assertEquals(expected, response.getBody().get("price").toString());
    }
}
