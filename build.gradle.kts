plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("org.jetbrains.kotlin.kapt") version "1.4.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("io.micronaut.library") version "1.2.0"
    id("application")

    id("org.jetbrains.kotlin.plugin.jpa") version "1.4.10"
}

version = "1.0.0"
group = "sketch.avatar.api"

application {
    mainClassName = "sketch.avatar.api.Application"
}

allOpen {
    annotations("io.micronaut.aop.Around", "javax.transaction.Transactional")
}

val kotlinVersion = project.properties["kotlinVersion"]

repositories {
    mavenCentral()
    jcenter()
}

micronaut {
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("sketch.avatar.api.*")
    }
}

dependencies {
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.aws:micronaut-function-aws")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("io.micronaut:micronaut-http-server-netty")
    implementation("io.micronaut:micronaut-http-client")

    kaptTest("io.micronaut:micronaut-inject-java")
    testImplementation("io.micronaut.test:micronaut-test-kotest:2.2.1")
    testImplementation("io.mockk:mockk:1.10.2")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.3.0")

    kapt("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.sql:micronaut-hibernate-jpa")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:2.7.1")

    implementation("io.micronaut.aws:micronaut-function-aws-api-proxy")
    implementation("com.amazonaws:aws-java-sdk-s3:1.11.414")
    implementation("com.amazonaws:aws-lambda-java-events:3.6.0")

    implementation("io.github.microutils:kotlin-logging:2.0.3")
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}

java {
    sourceCompatibility = JavaVersion.toVersion("1.8")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}
