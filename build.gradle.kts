plugins {
    id("java")
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id ("com.google.cloud.tools.jib") version "3.4.4"
}

group = "ru.glavs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

jib {
    from{
        image = "bellsoft/liberica-openjdk-alpine-musl:21.0.1"
    }
    to {
        image = "glavs/swapi-kafka-consumer"
    }
}


java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.kafka:spring-kafka")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars:sockjs-client:1.5.1")
    implementation("org.webjars:stomp-websocket:2.3.4")
    implementation("org.webjars:bootstrap:5.2.3")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")

    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}