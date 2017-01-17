# Staging Management System
An application to track attendance, job status, and requirements of associates!

### Commit early and often!!!!

Aim for 3 commits a day (not including any dummy commits), with the last one being as you leave for the day.

## How to Set Up your Envrionment

1. Make sure *Maven* is installed and accessible my the `mvn` command on Git Bash or Windows CommandLine
2. Make sure your *JAVA_HOME* environmental variable is set to something like *C:\Program Files\Java\jdk1.8.0_101*
3. Create a GitHub account, and direct message Chris with your GitHub username
4. Make sure you're set up on [JIRA](https://revaturetraining.atlassian.net/secure/RapidBoard.jspa?rapidView=48&projectKey=SMS&view=detail) (Contact Chris if you don't have a login or if your account is inactive)
5. Set up the environmental variables as described in Slack
6. Clone this project on your local machine (run `git clone https://github.com/revaturelabs/staging-management-system.git` )
7. In Git Bash, `cd` to the *Jars* folder inside of the project you just cloned, and run this command: `mvn install:install-file -Dfile=ojdbc7.jar -DgroupId=com.oracle -DartifactId=ojdbc7 -Dversion=12.1.0.1 -Dpackaging=jar`
8. Download and use [Spring Tool Suite](https://spring.io/tools/sts/all) for the development of this application
9. In Spring Tool Suite, select `File->Import...` In the `General` folder, select `Projects from Folders or Archive`. Then, select the folder of the repo you just copied
10. At this point, you should be able to run the project as a Spring Boot App and access the project on your browser at `http://localhost`
  * NOTE: If you don't want to run the appliation, locally, at port 80, you can override the default port by setting the environment variable `SERVER_PORT`
11. Log in to [JIRA](https://revaturetraining.atlassian.net/secure/RapidBoard.jspa?rapidView=48&projectKey=SMS&view=detail), drag an issue you would like to work on to the *In Progress* column, and push the `A` key on your keyboard. Click `Assign to Me`, and the `Assign` button at the bottom of the modal box that pops up
12. Create a feature branch and pull request for the issue you're working (please name it according to the issue number, such as `SMS-24`) To do this:
  * On Git Bash, use `git checkout development` to make sure you're on the development branch
  * Run `git pull` to pull the latest code
  * Use `git checkout -b SMS-24` to create a local branch for your user-story
  * Make a small change to your feature branch and commit it (One option for doing this is to touch a dummy file, to do that, follow these steps:
    1. Run `touch dummy.txt`
    1. Run `git commit -am "added dummy"` )
  * Type `git push --set-upstream origin SMS-24` to create the branch remotely (For subsequent pushes, you can simply use `git push`)
  * If you created a dummy file, revert it with the following steps:
    1. `git rm dummy.txt`
    1. `git commit -am "removed dummy file"`
    1. `git push`
  * On GitHub, you should see a box near the top saying that you recently pushed to it, and you'll see a button labeled *Compare & pull request* .
  * Click that, give your pull request the same name as your branch, and continue working. Each commit will run a feature test
 
 ## BEFORE EVERY COMMIT, RUN `git pull origin development` TO MAKE SURE YOU'RE ON THE MOST UP TO DATE VERSION OF THE CODE
  * When you're ready for your branch to be merged to the development branch, comment `RFI` on the pull request, and that will notify one of the reviewers that your code is ready to be reviewed
13. To access the database, use [SQL Developer](http://www.oracle.com/technetwork/developer-tools/sql-developer/downloads/index.html).  
  * Create a new connection with the username and password you found in the Slack information (check the *Save Password* box for your convienance)
  * for the URL, take the URL that you found in the Slack information, and omit `jdbc:oracle:thin:@` at the beginning and `:1521:ORCL` at the end
  * Use port `1521`
  * Use the SID `ORCL`
  * Test the connection
  * If it works, save it and connect to it


## I have a question! Who should I ask?

If your question relates to the requirements of a userstory, contact Karan or Ankit.

If your question pertains to the UI or design of the app, contact Ben.

If your question has to do with testing, contact Alex.

If your question has to do with your development enviroment, or if you have a problem that impedes your ability to develop, contact Chris.

## I haven't used Git Bash in a long while! How do I...
* generally use it? After you've cloned as above and created your branch, the general flow is:
  1. Before you start working, run a `git pull` to make sure you have the most up to date code
  1. Work on your stuff
  1. Add files by running `git add [files you created]`
  1. Commit your changes by running `git commit -am "[message about what you did]"`
  1. Push your changes by running `git push` (use `git push --set-upstream origin [branch name]` if it's your first time pushing to the branch)
* store my credentials? Run `git config --global credential.helper wincred`
* deal with a merge conflict? Cry. Loudly. 
* actually deal with a merge conflict? Run `git status` to see what files have conflicts and read through the conflicts. A merge conflict will be set off by `<<<<<<<<<<`, and the two conflicting segments are separated by `=======`. The line `>>>>>>>> [SHA hash]` signifies the end of the conflicts.  The code before the `=======` is normally what was there before you tried to merge, and after is what would replace it. Use this information to try to figure things out on your own, but if you want a second opinion, or need help figuring things out, don't be afraid to ask someone for help.

## How should I structure my code?
The general structure of the frontend is already in place, just follow the folder structure of HTML/CSS/Angular files. As far as the Angular code itself, follow the style guidelines set by [John Papa](https://github.com/johnpapa/angular-styleguide/blob/master/a1/README.md). These are industry standard practices endorsed by the team that is developing Angular at Google.
