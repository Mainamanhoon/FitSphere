plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")

}

android {
    namespace = "com.example.fitsphere"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fitsphere"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7") // Stable version
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")

    // Glide for image loading
    implementation(libs.glide)

    // Firebase dependencies managed by BoM
    implementation(platform(libs.firebase.bom.v3370))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-analytics-ktx")

    // Hilt dependencies
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")


    // Hilt navigation for Compose
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    //Custom Loader
    implementation ("com.airbnb.android:lottie:3.4.2")

    //firestore
    implementation ("com.google.firebase:firebase-firestore:25.1.1")
    implementation ("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-analytics")

    implementation ("com.google.firebase:firebase-firestore-ktx")
    implementation ("com.google.firebase:firebase-storage-ktx")


    //for circular imageViews
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //for coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

}