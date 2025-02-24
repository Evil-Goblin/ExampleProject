plugins {
    id("jpa-convention")
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(":db-module:h2"))
}
