import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.openapi.generator") version "7.6.0"
}

group = "br.upe.poli"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

springBoot {
	mainClass.set("br.upe.poli.sgdtr.SgdtrApplicationKt")
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

<<<<<<< HEAD
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")

=======
>>>>>>> main
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.register("generateApi", GenerateTask::class){
	generatorName.set("kotlin-spring")
	inputSpec.set("$projectDir/swagger/sgdtr-api.yaml")
	outputDir.set("$buildDir/generated")
	apiPackage.set("br.upe.poli")
	modelPackage.set("br.upe.poli.dto")
	typeMappings.set(
		mapOf(
			"number" to "kotlin.Long",
			"integer" to "java.math.BigInteger",
			"file" to "org.springframework.web.multipart.MultipartFile",
			"array" to "kotlin.collections.List"
		)
	)
	configOptions.set(
		mapOf(
			"swaggerAnnotations" to "true",
			"skipDefaultInterface" to "true",
			"openApiNullable" to "false",
			"useOptional" to "true",
			"documentationProvider" to "springdoc",
			"useTags" to "true",
			"useSpringBoot3" to "true",
			"serviceInterface" to "true",
			"modelMutable" to "true",
		)
	)
}

sourceSets {
	main {
		java.srcDir(listOf(
			File("${buildDir}/generated/src/main/kotlin"),
		))
	}
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.compileKotlin {
	dependsOn("generateApi")
}

tasks.getByName<Jar>("jar") {
	enabled = false
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
	archiveFileName.set("sgdtr-backend.jar")
}

