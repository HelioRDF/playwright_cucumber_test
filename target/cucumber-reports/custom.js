const reporter = require('cucumber-html-reporter');

const options = {
    theme: 'bootstrap',
    jsonFile: 'CucumberTestReport.json',
    output: 'target/cucumber-reports/cucumber-report.html',
    reportSuiteAsScenarios: true,
    scenarioTimestamp: true,
    launchReport: true,
};

reporter.generate(options);