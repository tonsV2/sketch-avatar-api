# Sketch Avatar API

This project has been designed to run on infrastructure produced by the below application.

https://github.com/tonsV2/sketch-avatar-infrastructure

# Quick start
The following commands will create a bucket, build a fat jar and upload it to S3.

(The default region for the infrastructure project is `us-east-1` so that should be used here as well)

```bash
aws s3 mb s3://mn-lambda
./gradlew clean shadowJar
aws s3 cp build/libs/sketch-avatar-api-1.0.0-all.jar s3://mn-lambda/mn/v1.0.0/
```

# Handler

[AWS Lambda Handler](https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html)

API Handler: io.micronaut.function.aws.proxy.MicronautLambdaHandler

Worker Handler: sketch.avatar.api.handler.LegacyToModernRequestHandler

# Feature aws-lambda documentation

- [Micronaut AWS Lambda Function documentation](https://micronaut-projects.github.io/micronaut-aws/latest/guide/index.html#lambda)
