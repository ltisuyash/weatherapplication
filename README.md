# Fetch Weather Report

## Overview
This project is a Spring Boot application designed to fetch weather information from openweathermap.org and store it in an AWS DynamoDB table. The application leverages several AWS services, including Lambda, API Gateway to manage and serve weather data efficiently.

## CloudFormation Template
The CloudFormation template defines the infrastructure required for the application. Here are the key components:

### DynamoDB Table (`WeatherData`)
- **Purpose**: Stores weather data with `city` as the partition key.
- **Configuration**: The table is provisioned with 5 read and write capacity units. It is configured to be retained on update or deletion to prevent data loss.

### IAM Role (`WeatherIamUserRole`)
- **Purpose**: Allows AWS Lambda functions to assume this role and interact with other AWS services.
- **Configuration**: The role includes a policy that allows it to be assumed by the Lambda service and attaches the `AWSLambdaBasicExecutionRole` managed policy for basic Lambda execution permissions.

### IAM Policy (`WeatherIamUserPolicy`)
- **Purpose**: Grants the necessary permissions for the Lambda function to interact with the DynamoDB table.
- **Configuration**: The policy includes actions such as `dynamodb:GetItem`, `dynamodb:PutItem`, and other DynamoDB operations, and is attached to the `WeatherIamUserRole`.

### Lambda Function (`WeatherUpdatesLambdaFunction`)
- **Purpose**: Fetches weather data from the OpenWeatherMap API and stores it in the DynamoDB table.
- **Configuration**: The function is configured with 1024 MB of memory, a 30-second timeout, and uses Java 17 runtime. The code for the function is stored in an S3 bucket.

### API Gateway (`WeatherRestApi`)
- **Purpose**: Provides a REST API to serve weather data.
- **Configuration**: The API Gateway is set up with resources and methods to invoke the Lambda function, allowing external clients to fetch weather data.

## Spring Boot Application

### Global Exception Handler
The application includes a global exception handler to manage exceptions and provide meaningful error responses. This ensures that users receive clear and helpful error messages when something goes wrong.

### Weather Service
The core service of the application fetches weather data from the OpenWeatherMap API. It uses the RestTemplate to make HTTP requests and the ObjectMapper to parse the JSON responses. The service extracts relevant weather information, such as the city name and temperature, and stores it in the DynamoDB table.

## Deployment Steps
1. **Deploy the CloudFormation Stack**: Use the provided CloudFormation template to create the necessary AWS resources, including the DynamoDB table, IAM roles and policies, Lambda function, API Gateway, and EventBridge rule.
2. **Build and Package the Spring Boot Application**: Compile the application and package it into a JAR file.
3. **Upload the JAR File to S3**: Store the packaged JAR file in the specified S3 bucket.
4. **Deploy the Lambda Function**: Ensure the Lambda function is configured to use the JAR file from S3 and has the necessary permissions to interact with DynamoDB.
5. **Access the API Endpoint**: Use the API Gateway endpoint provided in the CloudFormation outputs to fetch weather data.

## Error Handling
The application includes robust error handling to manage various scenarios, such as city not found errors and generic exceptions. This ensures that users receive appropriate feedback and the application can handle unexpected issues gracefully.

## Conclusion
This project demonstrates a comprehensive approach to building a serverless application using AWS services and Spring Boot. By leveraging CloudFormation for infrastructure as code, the application ensures a scalable and maintainable architecture. The integration with OpenWeatherMap provides real-time weather data, making the application both functional and informative.

