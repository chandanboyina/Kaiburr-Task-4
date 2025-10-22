# Kaiburr Task 4: CI/CD Pipeline (GitHub Actions)

This repository contains the Continuous Integration/Continuous Delivery (CI/CD) pipeline configuration for the application built in Tasks 1 and 3, using GitHub Actions.

* Backend: https://github.com/chandanboyina/Kaiburr-Task-1

* Frontend: https://github.com/chandanboyina/Kaiburr-Task-3

* Kubernates: https://github.com/chandanboyina/Kaiburr-Task-2


## Backend CI/CD Pipeline (backend-ci.yml)

This pipeline runs on the Java Spring Boot application (Task 1).

### Functionality:

* Code Build (CI): Compiles the Java code and creates the executable JAR file using Maven.
* Docker Build & Push (CD): Builds the Docker image and pushes it to Docker Hub, making it available for Kubernetes deployment.
* URL: https://github.com/chandanboyina/Kaiburr-Task-2/actions

 ![Backend1](https://github.com/chandanboyina/Kaiburr-Task-4/blob/main/backemd1.jpeg)

 ![Backend1](https://github.com/chandanboyina/Kaiburr-Task-4/blob/main/backend%202.jpeg)



### Setup Details

* Trigger: Pushes to the main branch.

* Secrets Required: This pipeline requires the following secrets to be configured in GitHub Settings:
         * DOCKER_USERNAME
         * DOCKER_PASSWORD

## Frontend CI Pipeline (frontend-ci.yml)

This pipeline runs on the React/TypeScript frontend application (Task 3).

### Functionality:

* Dependency Caching: Installs Node dependencies (npm ci).
* Code Build (CI): Compiles the TypeScript and React code into optimized static assets (npm run build), ensuring the application is stable and ready for deployment to any static host.
* URL: https://github.com/chandanboyina/Kaiburr-Task-3/actions

![Frontend](https://github.com/chandanboyina/Kaiburr-Task-4/blob/main/frontend1.jpeg)

![Frontend](https://github.com/chandanboyina/Kaiburr-Task-4/blob/main/frontend2.jpeg)


  
