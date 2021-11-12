# Exercise
## introduction
### code
This is a java project based on Spring boot. The entry point is`com.excercise.fetchrewards.FetchRewardsApplication`
### route
3 routes as requested are:
    
* `/balance/gain` Add transactions for a specific payer and dat
* `/balance/spend` Spend points using the rules above
* `/balance/show` Return all payer point balances

You can find them in `com.excercise.fetchrewards.web.controller.BalanceController`
### implementation
I implemented this exercise base on my personal practice. The three key packages of this project are:
* `web` - web controller and all relatives.
* `core` - the location of core logic.
* `repository` - enclosure of data retrieval and maintenance. In this project, I used a simple `Arraylist` to simulate the data source

I made it functional, but there are till some parts that are not implemented for time issue and some other reasons. 
* Validation. All the parameters of requests should be validated. I usually use `org.hibernate:hibernate-validator` to do this job
* Thread safety. Both the logic and data source are not thread safe. Without fine support of locking `annotation`, the code itself may look ugly. So I rather not do that for now.
* Friendly Exception. It's an important part in industrial practice, every expected error should be caught and transformed to a human-friendly message before returned to the browser. It also needs fine support of relative `annotation` or `aspects` to keep the code clean.
* Of course there are more to consider like distributed lock, transactions and flow control in real condition. But I choose to not move so far.
* etc.

## run

### prerequisite
To run this project, opening it with intellij Idea ultimate edition is the best way.

Make sure you use the latest version of Idea

Java 8 or higher must be installed

you need to have the following plugins in Idea:
* Gradle
* Spring 

### open and run

1. Click file->open in idea, then select the project folder you cloned from GitHub
2. Wait util Idea finish all its loading procedures, and install all the plugins, download all the filed it requires.
3. When it's done, you should be able to run this server by press `shift+F10`
4. You can now check this file to send test requests `generated-requests.http`

[Relative web page here](https://spring.io/guides/gs/spring-boot/)