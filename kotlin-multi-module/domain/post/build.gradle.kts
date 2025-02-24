plugins {
    id("jpa-convention")
}

dependencies {
    implementation(project(":domain:jpa-common"))
    implementation(project(":domain:account"))
}
