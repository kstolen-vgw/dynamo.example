import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.4"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
}

group = "co.vgw.chu.ref.arch"
version = "0.0.1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("aws.sdk.kotlin:dynamodb:1.1.16")
	implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")

	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.1.0")

	implementation("org.springframework.data:spring-data-r2dbc")
	implementation("org.flywaydb:flyway-core:9.22.2")
	implementation("org.postgresql:postgresql:42.6.0")
	implementation("org.postgresql:r2dbc-postgresql:1.0.2.RELEASE")

	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	implementation("org.jetbrains.kotlinx", "kotlinx-serialization-core")
	implementation("org.jetbrains.kotlinx", "kotlinx-serialization-json")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
