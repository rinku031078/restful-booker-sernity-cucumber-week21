package com.restfulbooker.cucumber;

import com.restfulbooker.testbase.TestBase;
import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;


@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/java/resources/feature/")
public class CucumberRunner extends TestBase {
}
