plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")


}

android {
    namespace = "ipca.example.pcmp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.pcmp.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }


    buildFeatures {

        compose = true
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
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.firebase.storage.ktx)
    implementation(libs.androidx.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore-ktx")

    //navigation
    implementation ("androidx.navigation:navigation-compose:2.7.1")


    //compose
    implementation ("androidx.compose.ui:ui:1.5.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")



    //Charts
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation ("androidx.compose.ui:ui:1.5.0")
    implementation ("androidx.compose.material:material:1.5.0")
    implementation ("androidx.compose.foundation:foundation:1.5.0")
    implementation ("androidx.compose.runtime:runtime-livedata:1.5.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.0")


    //Hilt
    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")

    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10")

    //Coil
    implementation("io.coil-kt:coil-compose:2.4.0")


    //Pager
    implementation ("com.google.accompanist:accompanist-pager:0.24.13-rc")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.24.13-rc")

    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")


}


