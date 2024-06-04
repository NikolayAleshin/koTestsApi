plugins {
    kotlin("jvm") version "1.9.23"
    id("io.qameta.allure") version "2.11.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"
val allureVersion = "2.27.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("io.qameta.allure:allure-rest-assured:2.27.0")
    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.4.0")
    testImplementation("org.slf4j:slf4j-api:1.7.36")
    testImplementation("org.slf4j:slf4j-simple:1.7.36")
    // Import allure-bom to ensure correct versions of all the dependencies are used
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    // Add necessary Allure dependencies
    testImplementation("io.qameta.allure:allure-junit5")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(20)
}

allure {
    adapter {
        autoconfigure.set(true)
        aspectjWeaver.set(true)
    }
    version.set("2.27.0")
}

// Define the version of AspectJ
val aspectJVersion = "1.9.21"

// Define configuration for AspectJ agent
val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

dependencies {
    // Add aspectjweaver dependency
    agent("org.aspectj:aspectjweaver:${aspectJVersion}")
}

// Configure javaagent for test execution
tasks.test {
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}
