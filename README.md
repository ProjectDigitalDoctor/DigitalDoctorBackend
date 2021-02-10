# DigitalDoctor Backend

This server is for a prototype of an app for digital doctor appointments, prescriptions and medical certificates.
It has been built for the "Mobile Software Engineering" course at the HS Mannheim.

It is build in Java with Spring Boot and is to be used together with the [DigitalDoctor App](https://github.com/ProjectDigitalDoctor/DigitalDoctorApp).

## Getting started

To run the server locally, a recent version of Java has to be installed on the system.
Afterwards simply `./gradlew bootRun` or `.\gradlew.bat bootRun` has to be executed.
The server is now running at `http://localhost:8080`.

To build a docker image, use the `bootBuildImage` gradle command.

## Demo page

For demo purposes a web page is available at the root server URL.
On this page video calls can be started (if Twilio is set up) in order for the app to join the call or other entities can be created.

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
