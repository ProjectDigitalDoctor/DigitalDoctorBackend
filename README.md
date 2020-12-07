# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.0/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.0/gradle-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#using-boot-devtools)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.4.0/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

## Swagger / OpenAPI

- UI: http://localhost:8080/swagger-ui
- JSON: http://localhost:8080/v3/api-docs
- YAML: http://localhost:8080/v3/api-docs.yaml

## Twilio Setup

- Create a free [Twilio account](https://www.twilio.com/try-twilio)
- Copy the `src/main/resources/example.env` to `src/main/resources/.env`
- Go to the [Twilio Console](https://www.twilio.com/console)
- Copy the Account SID and Auth token in the `.env` file
- Go to the [Twilio Video API Keys](https://www.twilio.com/console/video/project/api-keys)
- Create a new API Key and copy the SID and Secret in the corresponding fields of the `.env` file
- Optionally set some default [room settings](https://www.twilio.com/console/video/configure) to prevent usage of trial balance:
    - Room Type: `go`
    - Maximum Participants: 2
    - Client-Side Room Creation: Off
    
### Usage

- Start the server
- Go to the Doctor backend at `localhost:8080`
- Enter a valid appointment id (`1` should always be available)
- Click on `Create Room & Join` to create a new room, save the meeting name into the apointment and join in the browser
- Open a second browser and do the same steps (Login at the moment not needed)
- Click on `Join Room` to join the meeting with the name saved in the appointment
- You should now have a video call between these two browsers