# Staging Management System
An application to track attendance, job status, and requirements of associates

## How to Set Up your Envrionment

1. Make sure *Maven* is installed and accessible my the `mvn` command on Git Bash or Windows CommandLine
2. Make sure your *JAVA_HOME* is set to something like *C:\Program Files\Java\jdk1.8.0_101*
3. Create a GitHub account, and direct message Christopher with your GitHub username
4. Make sure you're set up on [JIRA](https://revaturetraining.atlassian.net/) (Contact Christopher if you have problems)
5. Set up the environmental variables as described in Slack
6. Clone this project on your local machine (run `git clone https://github.com/revaturelabs/staging-management-system.git` )
7. In Git Bash, `cd` to the *Jars* folder inside of the project you just cloned, and run this command: `mvn install:install-file -Dfile=ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1 -Dpackaging=jar`
8. Download and use [Spring Tool Suite](https://spring.io/tools/sts/all) for the development of this application
9. In Spring Tool Suite, select `File->Import...` In the `General` folder, select `Projects from Folders or Archive`. Then, select the folder of the repo you just copied
10. At this point, you should be able to run the project as a Spring Boot App and access the project on your browser at `http://localhost`
11. Log in to [JIRA](https://revaturetraining.atlassian.net/secure/RapidBoard.jspa?rapidView=48&projectKey=SMS&view=detail), drag an issue you would like to work on to the *In Progress* column, and push the `A` key on your keyboard. Click `Assign to Me`, and the `Assign` button at the bottom of the modal box that pops up
12. Create a feature branch and pull request for the issue you're working (please name it according to the issue number, such as `SMS-7`) To do this:
  * On Git Bash, use `git checkout development` to make sure you're on the development branch
  * Run `git pull` to pull the latest code
  * Use `git checkout -b SMS-7` to create a local branch for your user-story
  * Make a small change to your feature branch and commit it (This could be adding an empty text file or a very small change to a file you're working with. Not anything that would impact any functionality)
  * Type `git push --set-upstream origin SMS-7` to create the branch remotely
  * On GitHub, you should see a box near the top saying that (TODO: finish writing steps for creating pull requests in GitHub. Still depends on the pipeline)
13. To access the database, use [SQL Developer](http://www.oracle.com/technetwork/developer-tools/sql-developer/downloads/index.html).  
  * Create a new connection with the username and password you found in the Slack information (check the *Save Password* box for your convienance)
  * for the URL, take the URL that you found in the Slack information, and omit `jdbc:oracle:thin:@` at the beginning and `:1521:ORCL` at the end
  * Use port `1521`
  * Use the SID `ORCL`
  * Test the connection
  * If it works, save it and connect to it
