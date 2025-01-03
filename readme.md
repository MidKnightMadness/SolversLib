# SolversLib

An updated and maintained fork of FTCLib

Project led by FTC 23511, Seattle Solvers.

Head maintainer: [Arush](https://github.com/ArushYadlapati)

---
## Resources
You can find the latest version number (and the implementation details) at the [Dairy Foundation](https://repo.dairy.foundation/#/releases/org/solverslib/core), where SolversLib is hosted.

Javadocs for SolversLib are available at <https://repo.dairy.foundation/javadoc/releases/org/solverslib/core/x.y.z>, where x.y.z is the desired version number.

FTCLib documentation (has information about how to use FTCLib, and does not have any information about SolversLib) - <https://docs.ftclib.org/ftclib>

Please note that the vision part of FTCLib has been removed in SolversLib, as it is severly outdated. If you want vision, please use EasyOpenCV (which is built into the SDK).

## Installing SolversLib

1. Open up your FTC SDK Project in Android Studio.

2. Go to your `build.common.gradle` file in your project.

    ![BuildCommonGradle](https://github.com/OpenFTC/EasyOpenCV/blob/master/doc/images/build-common-gradle.png)
    
3. Add the following to the `repositories` section at the bottom of the file.

   ```groovy
   mavenCentral()
   ```
    
4. Open the `build.gradle` file in your TeamCode module. 
    
    ![TeamCodeGradle](https://github.com/OpenFTC/EasyOpenCV/blob/master/doc/images/teamcode-gradle.png)
    
5. Go to the bottom of the file, and add the following.

    ```groovy
    dependencies {
        implementation "org.solverslib:core:0.1.0"
        // the vision part of ftclib is no longer in solverslib, as it is extremely old
        // please use EasyOpenCV instead
    }
    ```

You can find the latest version number at the [Dairy Foundation](https://repo.dairy.foundation/#/releases/org/solverslib/core), where SolversLib is hosted.
Alternatively, you can find it using the number below (which is the latest [tag](https://github.com/FTC-23511/SolversLib/tags)):

![Latest SolversLib Version](https://img.shields.io/github/tag/FTC-23511/SolversLib?label=version&color=blue
)

6. Because FTCLib makes use of advanced features, you need to increase the minSdkVersion to 24. Unfortunately, this means that ZTE Speed Phones are not supported in this release.

In build.common.gradle, change the minSdkVersion from 23 to 24:
```groovy

    defaultConfig {
        applicationId 'com.qualcomm.ftcrobotcontroller'
        minSdkVersion 24
        targetSdkVersion 28
    }
```
7. Ensure your Java version is set to 8. This should already be the case with the latest SDK.
If not, you will need to change your Java version. Scroll down in `build.common.gradle` until you find the `compileOptions` block.
```groovy
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_7
    targetCompatibility JavaVersion.VERSION_1_7
}
```
Change the 7 to an 8 like so:
```groovy
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```
    
8. Perform a gradle sync to implement your changes.

    ![GradleSync](https://github.com/OpenFTC/EasyOpenCV/blob/master/doc/images/gradle-sync.png)


__NOTE:__ If your module has a few dependencies, you might have an error related to multidex on building the project.
This is caused by the project exceeding the limit for imports enforced by Android Studio. To solve this, 
add `multiDexEnabled true` to the below location inside the `build.common.gradle` file.

```groovy

    defaultConfig {
        applicationId 'com.qualcomm.ftcrobotcontroller'
        minSdkVersion 24
        targetSdkVersion 28


        multiDexEnabled true
    }
```

## Contributing

Since this is a community-driven, open source library, we are constantly looking for more content. If you feel there is something missing from our library, feel free to contribute! If you want to contribute to the project, be sure to read the [CONTRIBUTING.md](.github/CONTRIBUTING.md).

Please make sure to contact us if you have any other questions.
