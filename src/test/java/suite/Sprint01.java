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
		features = "src/test/resources/features",
		tags = "@Login or @Cadastro or @Vendas",
		//tags = "(@CT02 or @CT01) ",
		//name = "Realizar login com sucesso",
		
	    plugin = {	
	                "pretty",
	                "html:target/cucumber-reports/cucumber-report.html",
	                "json:target/cucumber-reports/CucumberTestReport.json",
	                "timeline:target/test-output-thread/"
	    })
public class Sprint01 {
}