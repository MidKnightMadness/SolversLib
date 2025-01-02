plugins {
    id("dev.frozenmilk.android-library") version "10.1.1-0.1.3"
    id("dev.frozenmilk.publish") version "0.0.4"
    id("dev.frozenmilk.doc") version "0.0.4"
}

android.namespace = "org.solverslib.vision"

ftc {
    kotlin

    sdk {
        FtcCommon
        RobotCore
        Vision
        Hardware
    }
}

dairyPublishing {
    gitDir = file("..")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "org.solverslib"
            artifactId = "vision"
            // note that version was previously 2.1.0

            artifact(dairyDoc.dokkaHtmlJar)
            artifact(dairyDoc.dokkaJavadocJar)

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}