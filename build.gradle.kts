plugins {
    java
}

group = "org.nhnnext"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Guava
    implementation("com.google.guava:guava:33.5.0-jre")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.5.21")
    implementation("org.slf4j:slf4j-api:2.0.17")

    // Testing
    testImplementation(platform("org.junit:junit-bom:5.14.1"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.assertj:assertj-core:3.27.6")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}