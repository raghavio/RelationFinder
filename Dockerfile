# Use an official Java runtime as a parent image
FROM openjdk:8-jdk-alpine

# Set the working directory in the container
WORKDIR /app
COPY ./repo /app/repo
# Copy the project's pom.xml and install dependencies
COPY ./pom.xml /app
RUN apk add --no-cache maven && mvn dependency:resolve

# Copy the local code to the container
COPY . /app

# Build the project
RUN mvn package

# Expose port 8080 for the application
EXPOSE 8080

# Run the webapp-runner with the compiled WAR file
CMD ["java", "-jar", "target/dependency/webapp-runner.jar", "--port", "8080", "--expand-war", "target/RelationFinder-1.0-SNAPSHOT.war"]
