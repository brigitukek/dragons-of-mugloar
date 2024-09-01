plugins {
  id("java")
  id("org.springframework.boot") version "3.3.3"
  id("io.spring.dependency-management") version "1.1.6"
}

group = "com.brigita.dragons.of.mugloar"
version = "1.0-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.boot:spring-boot-starter-web")

  implementation("org.springframework.cloud:spring-cloud-starter")
  implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
  implementation("io.github.openfeign:feign-okhttp")

  compileOnly("org.projectlombok:lombok:1.18.34")
  annotationProcessor("org.projectlombok:lombok:1.18.34")

  testImplementation(platform("org.junit:junit-bom:5.11.0"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testImplementation("org.mockito:mockito-core:5.13.0")
  testImplementation("org.mockito:mockito-junit-jupiter:5.13.0")

}

dependencyManagement {
  imports {
    mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3")
  }
}

tasks.test {
  useJUnitPlatform()
}
