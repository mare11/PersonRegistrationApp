package com.example.personregapp.cucumber;

import com.example.personregapp.model.Person;
import gherkin.deps.com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

public class PersonRegAppStepdefs {

    private WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();

    private FluxExchangeResult<Person> exchangeResult;

    @When("client make GET call to \\/persons")
    public void clientMakeGETCallToPersons() {
        exchangeResult = webTestClient.get()
                .uri("/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().returnResult(Person.class);
    }

    @When("client make GET call to \\/persons\\/username with username {string}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void clientMakeGETCallToPersonsUsernameWithUsername(String username) {
        exchangeResult = webTestClient.get()
                .uri("/persons/" + username)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().returnResult(Person.class);
    }

    @Then("client receives status code of {int}")
    public void clientReceivesStatusCodeOf(int statusCode) {
        assertEquals(exchangeResult.getStatus(), HttpStatus.valueOf(statusCode));
        System.out.println(exchangeResult.getStatus());
    }

    @And("the response should contain:")
    public void theResponseShouldContain(String expectedResponse) {
        String actualResponse = new Gson().toJson(exchangeResult.getResponseBody().collectList().block());
        assertEquals(actualResponse, expectedResponse);
        System.out.println(actualResponse);
    }

}
