package com.example.weatherservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.example.weatherservice.model.Weather;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.DependsOn;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.example.weatherservice.repository.dynamodb")
public class DynamoDBConfig {

    @Value("${aws.accessKeyId}")
    private String awsAccessKeyId;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withRegion(awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(awsAccessKeyId, awsSecretKey)))
                .build();

        return client;
    }

    @Bean
    @Primary
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }

    @Bean
    @DependsOn("amazonDynamoDB")
    public CreateTableResult createDynamoDBTable(AmazonDynamoDB amazonDynamoDB, DynamoDBMapper dynamoDBMapper) {
        String tableName = "Weather";
        try {
            amazonDynamoDB.describeTable(tableName);
        } catch (ResourceNotFoundException e) {
            CreateTableRequest createTableRequest = dynamoDBMapper
                    .generateCreateTableRequest(Weather.class)
                    .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
            return amazonDynamoDB.createTable(createTableRequest);
        }
        return null;
    }
}
