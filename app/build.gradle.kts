plugins {
    application
}
repositories {
    mavenCentral()
}
dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}
application {
    mainClass = "interpreter.Main"
}
tasks.named<JavaExec>("run") {
    standardInput = System.`in` // keyboard input for the text menu
    workingDir = rootDir          // new: test.in and log files in the project root
}
tasks.named<Test>("test") {
    useJUnitPlatform()
}
