# Staging Management System
An application to track attendance, job status, and requirements of associates

## How to Set Up your Envrionment

0. Make sure *Maven* is installed and accessible my the `mvn` command on Git Bash or Windows CommandLine
0. Make sure your *JAVA_HOME* is set to something like *C:\Program Files\Java\jdk1.8.0_101*
0. Create a GitHub account, and direct message Christopher with your GitHub username
0. Make sure you're set up on [JIRA](https://revaturetraining.atlassian.net/) (Contact Christopher if you have problems)
1. Set up the environmental variables as described in Slack
2. Clone this project on your local machine (run `git clone https://github.com/revaturelabs/staging-management-system.git` )
3. In Git Bash, `cd` to the *Jars* folder inside of the project you just cloned, and run this command: `mvn install:install-file -Dfile=ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1 -Dpackaging=jar`
4. Download and use [Spring Tool Suite](https://spring.io/tools/sts/all) for the development of this application
5. In Spring Tool Suite, select `File->Import...` In the `General` folder, select `Projects from Folders or Archive`. Then, select the folder of the repo you just copied
6. At this point, you should be able to run the project as a Spring Boot App and access the project on your browser at `http://localhost`
6. Create a feature branch for the issue you're working (please name it according to the issue number, such as `SMS-7`)
7. To do this, on Git Bash, use `git checkout development` to make sure you're on the development branch. run `git pull` to pull the latest code, use `git checkout -b SMS-7` to create a local branch for your user-story, type `git push --set-upstream origin SMS-7` to create the branch remotely as well
