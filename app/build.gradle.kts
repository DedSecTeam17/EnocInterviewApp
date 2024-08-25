plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("dagger.hilt.android.plugin")
    kotlin("kapt") // Add this line
    id("kotlin-kapt")

}

android {
    namespace = "com.example.enocinterview"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.enocinterview"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        testInstrumentationRunner = "com.example.enocinterview.HiltTestRunner" // Replace with your actual package name
    }
//    testOptions {
//        unitTests.isReturnDefaultValues = true
//    }

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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.ui.test.junit4.android)
    implementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.runner)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.rules) // Ensure you're using the latest version
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Retrofit
    // Retrofit and Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.retrofit.mock)

    // Retrofit Mock for testing

    testImplementation(libs.retrofit.mock)

    // Dagger Hilt
    implementation(libs.hilt.android)

    kapt(libs.hilt.compiler)

    // Coroutines and Flow
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Lifecycle components for ViewModel and LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.retrofit.mock)
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing) // For InstantTaskExecutorRule



    // Hilt testing dependencies
    androidTestImplementation(libs.dagger.hilt.android.testing)
    kaptAndroidTest(libs.hilt.compiler)

    // UI Testing for Compose
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit42)


    debugImplementation(libs.androidx.compose.ui.ui.test.manifest2)


    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1") // Required for Intents



//    // Espresso and AndroidX test
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
//    androidTestImplementation("androidx.test.ext:junit:1.2.1")
//
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test:rules:1.4.0")
//    androidTestImplementation("androidx.test:runner:1.4.0")


    // Espresso Core for UI testing
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
//
//    // AndroidX Testing
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test:rules:1.4.0")
//    androidTestImplementation("androidx.test:runner:1.4.0")
//
//    // Hilt testing dependencies
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
//    kaptAndroidTest("com.google.dagger:hilt-compiler:2.48")
//
//    // Compose UI Testing
//
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.8") {
//        exclude(group = "androidx.test.espresso", module = "espresso-core")
//    }
//
//    debugImplementation("androidx.compose.ui:ui-test-manifest")



    //    androidTestImplementation(libs.dagger.hilt.android.testing)

    // Hilt support for ViewModel
//    implementation(libs.androidx.hilt.lifecycle.viewmodel)
    kapt(libs.androidx.hilt.compiler)
}