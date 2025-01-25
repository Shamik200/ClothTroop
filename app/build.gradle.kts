plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.clothtroop"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.clothtroop"
        minSdk = 24
        targetSdk = 34
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions{
        kotlinCompilerExtensionVersion="1.6.0"
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
    implementation(libs.firebase.database)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    implementation(libs.ui)
//    implementation(libs.androidx.material)
//    implementation(libs.ui.tooling.preview)
//    debugImplementation(libs.androidx.ui.tooling.v160)
//
//    implementation(libs.androidx.ui.v160)
//    implementation(libs.material3)
//    implementation(libs.androidx.ui.tooling.preview.v160)
//    debugImplementation(libs.ui.test.manifest)
//    implementation(libs.coil.compose.v240)

    implementation(libs.coil.compose)
    implementation(libs.coil.compose.v222)
//    implementation(libs.accompanist.pager.indicator)
//    implementation(libs.accompanist.pager.indicator.v0312alpha)
    implementation(libs.accompanist.pager)
//    implementation(libs.runtime.livedata)
//    implementation(libs.androidx.runtime.livedata)
    implementation(libs.glide)
    implementation(libs.gson)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.foundation)
    implementation(libs.ui.tooling)
    implementation(libs.material)
}
