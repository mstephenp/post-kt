# Sample Message Board Application

This server uses [Javalin](https://javalin.io/) to create a REST api for a frontend message board-like application.
There are different ways to test the application:

1. Unit testing to verify handler functionality using [mockk](https://mockk.io/).
2. Integration testing for end to end functionality using [unirest](https://kong.github.io/unirest-java/).
3. REST API testing using the dev.http file and a rest client - VS Code has a free extension called
   [rest client](https://github.com/Huachao/vscode-restclient) that works very well, but the calls can be made from
   Intelli Ultimate (paid) as well.
