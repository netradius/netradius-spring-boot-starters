# NetRadius Spring Boot Starters

This project consists of several Spring Boot starter projects which provide several useful
extensions and abstractions.

## NetRadius Spring Errors

This project extends the error handling in Spring Boot by providing a set of checked
exceptions which can be used to send useful errors to the invoking client. These exceptions
can triggers things such as HTTP Not Found, HTTP Bad Request and HTTP Not Implemented
status codes which may include JSON payloads that provide useful information to the client
about the error they triggered.

The following modules make up this starter

 * netradius-spring-errors
 * netradius-spring-errors-sample
 * netradius-spring-errors-spring-boot-starter
 

