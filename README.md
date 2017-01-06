# Staging Management System
An application to track attendance, job status, and requirements of associates

## How to Set Up your Envrionment

0. Make sure you're set up on [JIRA](https://revaturetraining.atlassian.net/) (Contact Christopher if you have problems
1. Set up the environmental variables as described in Slack
2. In Git Bash, `cd` to the *Jars* folder, and run this command: `mvn install:install-file -Dfile=ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1 -Dpackaging=jar`
3. Download and use [Spring Tool Suite](https://spring.io/tools/sts/all) for the development of this application
4. Clone this project on your local machine
5. In Spring Tool Suite, select `File->Import...`. In the `General` folder, select `Projects from Folders or Archive`. Then, select the folder of the repo you just copied
6. Create a feature branch for the issue you're working (please name it according to the issue number, such as `SMS-7`
