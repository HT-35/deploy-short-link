plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.freefair.lombok") version "8.11"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	dependencies {
		implementation("com.turkraft.springfilter:jpa:3.1.7")
		implementation("org.springframework.boot:spring-boot-starter-actuator")
		implementation("org.springframework.boot:spring-boot-starter-data-jpa")
		implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
		implementation("org.springframework.boot:spring-boot-starter-security")
		implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.springframework.boot:spring-boot-starter-web")
		implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
		implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
		implementation("io.github.cdimascio:java-dotenv:5.2.2")
		implementation("org.springframework.boot:spring-boot-devtools")
		implementation("org.apache.commons:commons-lang3:3.14.0")
		implementation("org.postgresql:postgresql:42.6.0") // connect PostgreSQL
		implementation("org.springframework.boot:spring-boot-starter-data-redis")





		developmentOnly("org.springframework.boot:spring-boot-devtools")
		runtimeOnly("com.mysql:mysql-connector-j")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("org.springframework.security:spring-security-test")
		testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
