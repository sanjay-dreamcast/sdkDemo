# SmartFrameSDK


# Integration Steps in Android App:

1. **Place the `SmartFrameSDK.aar` file** inside the `libs` folder in your project root.

2. **Update `build.gradle.kts`**:  
   In the app-level `build.gradle.kts` file, add the following inside the `dependencies` block:
   ```kotlin
   dependencies {
       implementation(files("../libs/SmartFrameSDK.aar"))
   }
   ```
3. **Launch the WebView**:
   Use the following code to launch the WebView UI:
```kotlin
SmartFrameSdkLauncher.launch(
    context = this,  // Replace 'this' with your activity or fragment context
    url = "add your url here"
)
```


# Integration Steps in Flutter Projects:
Step 1: Prepare the Flutter Project.

Step 2: Add the .aar File
Place the `SmartFrameSDK.aar` file in the libs folder of the android/app module.
If the libs folder doesn’t exist, create it inside the android/app directory.

Step 3: Modify the build.gradle.kts
Update the build.gradle.kts file in the android/app directory with the following configurations:
```kotlin
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    // Add the local SDK
    implementation(files("../libs/SmartFrameSDK.aar"))

    // AndroidX and Material Design libraries and  Replace with the latest version
    implementation("androidx.constraintlayout:constraintlayout:2.2.0") 
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0") 
}

```

Step 4: Update `gradle-wrapper.properties`

Navigate to android/gradle/wrapper/gradle-wrapper.properties.
Update the distributionUrl to specify the required Gradle version (8.7):
```kotlin
distributionUrl=https\://services.gradle.org/distributions/gradle-8.7-bin.zip
```
Step 5: Add Required Plugins

To ensure the correct versions of the Android and Kotlin plugins are applied, update the android/build.gradle and android/settings.gradle files as follows:

`android/build.gradle`:

```kotlin
buildscript {
    dependencies {
        classpath "com.android.tools.build:gradle:8.6.0" // add required version
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0" // // add required version
    }
}

```
`android/settings.gradle`:

```kotlin
plugins {
        id("com.android.application") version "8.6.0" apply false
        id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    }
```


Step 6: Sync the Project


step 7: **Launch the WebView**:

Use the following code to launch the WebView UI:
```kotlin
SmartFrameSdkLauncher.launch(
    context = this,  // Replace 'this' with your activity or fragment context
    url = "add your url here"
)
```
