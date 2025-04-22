# Basic Spring Boot with Docker

<!-- TOC -->
* [Basic Spring Boot with Docker](#basic-spring-boot-with-docker)
  * [How to run this project with maven:](#how-to-run-this-project-with-maven)
    * [1. Create a jar with the following command:](#1-create-a-jar-with-the-following-command)
    * [🧹 clean](#-clean)
    * [📦 package](#-package)
    * [🔧 -DAPP_PORT=8080](#--dapp_port8080)
    * [📦 Where does this go?](#-where-does-this-go)
* [Explanation of Dockerfile](#explanation-of-dockerfile)
    * [📦 `FROM maven:3.9.6-eclipse-temurin-17`](#-from-maven396-eclipse-temurin-17)
    * [🌐 `EXPOSE 8080`](#-expose-8080)
    * [📂 `WORKDIR /app`](#-workdir-app)
    * [📄 `COPY target/*.jar app.jar`](#-copy-targetjar-appjar)
    * [🚀 `ENTRYPOINT ["java", "-DAPP_PORT=8080", "-jar", "app.jar"]`](#-entrypoint-java--dapp_port8080--jar-appjar)
    * [🔁 Execution Flow When the Container Runs](#-execution-flow-when-the-container-runs)
    * [Example Build and Run](#example-build-and-run)
    * [🧠 What It Does:](#-what-it-does)
<!-- TOC -->

## How to run this project with maven:

---
### 1. Create a jar with the following command:
```
mvn -DAPP_PORT=8080 clean package
```
### 🧹 clean
- **Explanation**:
    - **Purpose**: Deletes the target/ directory (where Maven stores compiled code and build artifacts).
    - This ensures you're starting with a **clean slate**, removing any previously compiled files, .jars, .class files, etc.
    - Prevents old or corrupted builds from interfering with the new one.

### 📦 package
- **Explanation**:
    - **Purpose**: Compiles the code, runs tests, and then packages the compiled code into a distributable format—typically a .jar or .war file.
    - The output is placed in the target/ directory (e.g., target/my-app-1.0.0.jar).

### 🔧 -DAPP_PORT=8080
- **Explanation**:
    - -D **stands for define. You're defining a property**.
    - `APP_PORT` is the name of the property you're setting.
    - `8080` is the value you’re assigning to APP_PORT.

### 📦 Where does this go?
- This value can be used inside your `application.properties` file like this: `${APP_PORT}`.
- Example:
```
server.port=${APP_PORT}
```

---

[//]: # (### 2. Run the generated jar)

[//]: # (```)

[//]: # (java -DAPP_PORT=8010 -jar target\SpringBootDockerDemo-0.0.1-SNAPSHOT.jar)

[//]: # (```)

[//]: # (#### By default the application will run on port 8080. )

[//]: # (#### So go to the http://localhost:8080/api/v1/test and you will see the result)


# Explanation of Dockerfile

---

### 📦 `FROM maven:3.9.6-eclipse-temurin-17`
- **Explanation**:
    - This line sets the **base image** for the Docker container: `maven:3.9.6-eclipse-temurin-17`.
    - This image comes pre-installed with:
        - **Maven version 3.9.6**
        - **Java 17**, using the **Eclipse Temurin** distribution (a popular OpenJDK alternative).

- **Why use this?**  
  This base image is perfect for building and running **Java applications with Maven**.

---

### 🌐 `EXPOSE 8080`
- **Explanation**:
    - Declares that the container **uses port 8080** for network communication.
    - This does **not actually open the port**, but serves as documentation or metadata for tools like Docker Compose.

---

### 📂 `WORKDIR /app`
- **Explanation**:
    - Sets the **working directory inside the container** to `/app`.
    - All subsequent commands (`COPY`, `RUN`, etc.) will be executed from this directory.
    - If the `/app` directory does not exist, Docker will **create it automatically**.

---

### 📄 `COPY target/*.jar app.jar`
- **Explanation**:
    - Copies a `.jar` file from the **`target` directory on your host machine** into the container.
    - The file will be **renamed to `app.jar`** inside the container.
    - For example: `target/my-app-0.0.1-SNAPSHOT.jar` becomes `/app/app.jar`.

---

### 🚀 `ENTRYPOINT ["java", "-DAPP_PORT=8080", "-jar", "app.jar"]`
- **Explanation**:
    - Defines the **main command** to run when the container starts.
    - This means when the container runs, it will automatically execute:

      ```
      java -DAPP_PORT=8080 -jar app.jar
      ```

    - **`-DAPP_PORT=8080`**: Passes a **Java system property** named `APP_PORT` with the value `8080`, which can be accessed using `System.getProperty("APP_PORT")` in your application.
    - **`-jar app.jar`**: Executes the `app.jar` file as a Java application.
    - **`APP_PORT`** is defined in application.properties as follows **`server.port=${APP_PORT}`**, so we can change the port dynamically.

---

### 🔁 Execution Flow When the Container Runs
1. The Docker container is built using this image.
2. Port 8080 is declared as the app’s main port.
3. The working directory is set to `/app`.
4. The `.jar` file from Maven's `target` folder is copied into the container as `app.jar`.
5. When the container starts, it runs `java -DAPP_PORT=8080 -jar app.jar` to launch your Java application.

---

### Example Build and Run

```bash
# Build the Docker image
docker build -t my-springboot-app .

# Run a container from the image
docker run -d -p 8010:8080 --rm --name=spring my-springboot-app
```

| 🧩 | Part | Description |
|-----|------|-------------|
| 🐳 | `docker run` | Start a new container from a Docker image. |
| 🔄 | `-d` | Run the container in **detached mode** (in the background). |
| 🌐 | `-p 8010:8080` | **Port mapping**: Maps port `8010` on your host to port `8080` inside the container (where the Spring Boot app usually runs). So you access it at `http://localhost:8010`. |
| ♻️ | `--rm` | Automatically **remove the container** when it stops. Keeps things clean. |
| 🏷️ | `--name=spring` | Assigns a **name** to the container (`spring`) for easier management. |
| 📦 | `my-springboot-docker` | The **Docker image name** to run. This should already be built using `docker build`. |

---

### 🧠 What It Does:

1. Starts a new container from the image `my-springboot-docker`.
2. The Spring Boot app inside it runs on port `8080`.
3. You can access it on your **host machine** at [http://localhost:8010](http://localhost:8010).
4. When the container is stopped, it's automatically deleted.