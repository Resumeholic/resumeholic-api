import org.jetbrains.kotlin.gradle.idea.proto.generated.kpm.ideaKpmModuleProto

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.openapi.generator") version "7.8.0"
	idea
}

group = "org.resumeholic"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("jakarta.validation:jakarta.validation-api")
	implementation("io.swagger.core.v3:swagger-core:2.2.22")
	implementation("io.springfox:springfox-swagger-ui:3.0.0") {
		exclude("io.swagger.core.v3:swagger-annotations")
	}
	implementation("io.swagger.core.v3:swagger-annotations-jakarta:2.2.22")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

val specFileName = "openapi.yaml"
val sourceSpecDir = "../spec/tsp-output/@typespec/openapi3"
val sourceSpecFilePath = "$sourceSpecDir/$specFileName"

val generatedSrcDir = "${layout.buildDirectory.get().asFile.path}/generated-src"
val generatedSpecDir = "${generatedSrcDir}/spec"
val generatedSpecFilePath = "$generatedSpecDir/$specFileName"
openApiGenerate {
	generatorName = "kotlin-spring"
	inputSpec = generatedSpecFilePath
	outputDir = generatedSrcDir
	apiPackage = "org.resumeholic.api"
	modelPackage = "org.resumeholic.api.model"
	openapiGeneratorIgnoreList.addAll("src/main/java/org/openapitools/OpenApiGeneratorApplication.java")
	configOptions.set(
		mapOf(
			"delegatePattern" to "true",
			"interfaceOnly" to "false",
			"useSpringBoot3" to "true",
			"useSwaggerUI" to "true",
			"interfaceOnly" to "true"
		)
	)
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register("prepareDirs") {
	mustRunAfter("clean")
	doLast {
		mkdir(generatedSrcDir)
		mkdir(generatedSpecDir)
	}
}

tasks.register("copySpec") {
	doFirst {
		copy {
			from(sourceSpecFilePath)
			into(generatedSpecDir)
		}
	}
}

tasks.openApiGenerate {
	dependsOn("copySpec")
}

tasks.named("build") {
	dependsOn(tasks.openApiGenerate)
}

tasks.forEach {
	if (it.name != "prepareDirs" && it.name != "clean") {
		it.dependsOn("prepareDirs")
	}
}

sourceSets.getByName("main").kotlin.srcDir(generatedSrcDir)
idea {
	module {
		generatedSourceDirs.add(file(generatedSrcDir))
	}
}
tasks.compileKotlin.get().dependsOn(tasks.openApiGenerate)