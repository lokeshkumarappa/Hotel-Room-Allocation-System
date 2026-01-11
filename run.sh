#!/bin/bash
# Build the application
./gradlew build -x test

# Start the application
java -jar build/libs/hotel-allocation-1.0.0.jar --server.port=8080