# Kaiburr Task 2: Kubernetes Deployment

This repository contains the solution for Kaiburr Assessment Task 2, which involves containerizing and deploying a Java Spring Boot application to a Kubernetes cluster.



---

## Deployment Instructions

## 1. Prerequisites

* **Java JDK 17+**: Required to build the application.
* **Maven**: Required to package the application.
* **Docker Desktop**: Used to build the Docker image and provides a local Kubernetes cluster.
* **kubectl**: The command-line tool for interacting with the Kubernetes cluster.

## 2. Build and Push the Docker Image

First, build the application's executable JAR file. From your project's root directory, run:
```bash
mvnw.cmd clean package
```

Next, build the Docker image for your application using the Dockerfile located in the root directory.
```bash
docker build -t <dockerhub_username>/<application_image_name> .
```

Finally, push the image to your Docker Hub repository.
```bash
docker push chandanboyina/kaiburr-app:1.0
```

## 3. Deploy to Kubernetes

Deploy RBAC Resources: This manifest creates the necessary Service Account, Role, and RoleBinding to give your application pod permissions to create and manage other pods.
```bash
kubectl apply -f rbac.yaml
```

Deploy the Application: This manifest deploys your application's pod and service. It uses a ConfigMap to inject the MongoDB connection URI as an environment variable.
```bash
kubectl apply -f app-deployment.yaml
```

Deploy MongoDB: This manifest creates a MongoDB pod with persistent storage and a service for your application to connect to.
```bash
kubectl apply -f mongodb-deployment.yaml
```
![Homepage](https://github.com/chandanboyina/Kaiburr-Task-2/blob/master/deployemtn%20yaml.jpeg)

1.
```bash
kubectl get pods
```
This command shows that the application and MongoDB pods are in a Running state.
![Homepage](https://github.com/chandanboyina/Kaiburr-Task-2/blob/master/get%20pods.jpeg)


```bash
kubectl get services
```
This command shows the NodePort service is active and exposing your application's endpoints to your host machine.
![Homepage](https://github.com/chandanboyina/Kaiburr-Task-2/blob/master/get%20services.jpeg)


```bash
curl -X PUT http://localhost:30007/tasks/1/execute
```
This command proves that an application endpoint is available from the host machine. The successful response shows that the PUT a TaskExecution endpoint correctly created and ran a command in a new pod.
![Homepage](https://github.com/chandanboyina/Kaiburr-Task-2/blob/master/curl%20command.jpeg)
