# Assign Force
### Revature Batch Management Application

## TOC
[Getting Started](#getting-started)  
[Project Background](#project-background)  
[Working with the Repo](#working-with-the-repo)  
[Development Particulars](#development-particulars)  

## Getting Started
Welcome to the team! To get started working on this project, you will have to do some initial environment setup. All developers will need their local environment to include:
    
### Environment
* [Eclipse](https://eclipse.org/downloads) with the `Spring Tool Suite` extension installed or [Spring Tool Suite IDE](http://spring.io/tools/
* [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/)
* [Git CLI](https://git-scm.com/downloads)

Once your environment is setup you can clone the repo and import in into Eclipse to start developing.

### Project Background
`Assign Force` is an internal `Revature` project developed to help manage training batches, training locations, rooms and trainers. The application has two typical users `admin`
and `trainer`. `Assign Force` allows an `admin` user to manipulate the before mention objects in order to generate help info-graphics and reports. `Assign Force` will also allow
a `trainer` to manage a brief profile that will help the application more intelligently generate those info-graphics and reports.

### Working with the Repo
To keep the pipeline consistent all developers must follow the same workflow when it comes to working with the repo. In some cases, the lead developer will assign you a user story
to work on, and in other cases you may freely pick up an open issue. In either case the repo work flow will be the same.
1. Checkout the development branch
2. Pull any changes from that branch
3. Create a feature branch
    * The branch name should be the name of your issue or user story (like hotfix-28 or RS-17)
    * Immediately create a merge request for your new branch.
        * Source branch should be your new hotfix or issue branch
        * Target branch should be development branch
4. Do your development thing in your new branch
5. When finished or at a stopping point
    * Commit and push your changes
6. When you have completed your development commit and push your changes with the commit message "Ready for final inspection"
7. Your code will be reviewed for a final time and the merge request will be accepted or denied for fixes.


### Development Particulars
Development of `Assign Force` is meant to leverage the power of some new, industry standard frameworks. You can view this project as 2 separate applications a client side
application developed in [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/), [HTML5](https://developer.mozilla.org/en-US/docs/Web/Guide/HTML/HTML5/),
[CSS3](https://developer.mozilla.org/en-US/docs/Web/CSS/), and **`MOST`** importantly [AngularJS](https://docs.angularjs.org/api/). These technologies together create a client
facing application that leverages a `Java` based web service. The second part of the application is the `Java` based web service, which utilizes the ultra powerful and industry leading
[Spring Framework](https://spring.io/docs/), **`MOST`** notably the [Spring Boot](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) module. Using this framework
allows us to develop the project more expeditiously and it is **REALLY COOL**.
