plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "uz.java"
version = "0.0.1-SNAPSHOT"
description = "hh-web-app"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
//    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("org.liquibase:liquibase-core")
    implementation("io.minio:minio:8.5.4")
//    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
//    implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("com.auth0:java-jwt:3.18.2")

    //Keyclok
    implementation("org.keycloak:keycloak-admin-client:26.0.6")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    implementation("org.apache.commons:commons-lang3:3.14.0")
    implementation("redis.clients:jedis")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")


    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-web-test")

    testCompileOnly("org.projectlombok:lombok")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
