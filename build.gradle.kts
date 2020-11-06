import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.3.5.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    kotlin("jvm") version "1.4.10"
    kotlin("plugin.spring") version "1.4.10"
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

allprojects {
    group = "nl.hlopez"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }
}

subprojects {

    extra["igniteVersion"] = "2.9.0"

    ext {
        set("h2.version", "1.4.197")
    }

    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    dependencyManagement {

        dependencies {
            dependencySet("org.apache.ignite:${property("igniteVersion")}") {
                entry("ignite-spark-2.4")
                entry("ignite-slf4j")
                entry("ignite-spring")
            }
        }

        dependencies {
            dependencySet("org.codehaus.janino:3.0.9") {
                entry("commons-compiler")
                entry("janino")
            }
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}
