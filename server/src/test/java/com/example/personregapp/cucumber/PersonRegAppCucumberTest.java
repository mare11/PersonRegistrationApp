package com.example.personregapp.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/", plugin = {"pretty", "html:target/cucumber" })
public class PersonRegAppCucumberTest {

}
