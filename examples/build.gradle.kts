plugins {
    id("dev.frozenmilk.teamcode") version "10.3.0-0.1.4"
}

ftc {
    kotlin

    // note: sdk automatically added
}

dependencies {
    implementation("org.solverslib:core")
    implementation("org.solverslib:pedroPathing")

    api("com.pedropathing:pedro:1.0.9")
}

repositories {
    maven("https://maven.brott.dev/")
    maven("https://maven.pedropathing.com/")
}
