plugins {
    id("dev.frozenmilk.teamcode") version "10.1.1-0.1.3"
}

ftc {
    kotlin

    // note: sdk automatically added
}

dependencies {
    implementation("org.solverslib:core")
    implementation("org.solverslib:pedroPathing")

    api("com.pedropathing:pedro:1.0.4")
}

repositories {
    maven("https://maven.brott.dev/")
    maven("https://maven.pedropathing.com/")
}
