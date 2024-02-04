plugins {
    id("com.android.application")
//    id("com.google.devtools.ksp") version "1.9.0-1.0.13"
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}



android {
    namespace = "com.example.reportsfordrivers"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.reportsfordrivers"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "runner.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.incremental"] = "true"
            }
        }
    }



    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")
//    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    kapt("androidx.room:room-compiler:${rootProject.extra["room_version"]}")

    //Testing
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")


    //Hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra["hilt_version"]}")
    annotationProcessor("com.google.dagger:hilt-compiler:${rootProject.extra["hilt_version"]}")
    //For instrumentation test (HILT)
    androidTestImplementation(
        "com.google.dagger:hilt-android-testing:${rootProject.extra["hilt_version"]}"
    )
    androidTestAnnotationProcessor(
        "com.google.dagger:hilt-compiler:${rootProject.extra["hilt_version"]}"
    )
    kaptTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["hilt_version"]}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${rootProject.extra["hilt_version"]}")
    //For local unit tests (HILT)
    testImplementation("com.google.dagger:hilt-android-testing:${rootProject.extra["hilt_version"]}")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:${rootProject.extra["hilt_version"]}")

    //
}
