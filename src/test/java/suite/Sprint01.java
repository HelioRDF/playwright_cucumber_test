package suite;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
		monochrome = false,
		snippets=SnippetType.CAMELCASE,
		dryRun = false,
		glue = "steps",
		features = "src/test/resources/",
		tags = "(@CT02) ",
		
	    plugin = {	
	                "pretty",
	                "html:target/cucumber-reports/cucumber-pretty.html",
	                "json:target/cucumber-reports/CucumberTestReport.json",
	                "timeline:target/test-output-thread/"
	    })
public class Sprint01 {
}