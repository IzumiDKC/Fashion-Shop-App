plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.fashionshopapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.fashionshopapp"
        minSdk = 26
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
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics")

    // Image
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    implementation("com.google.accompanist:accompanist-permissions:0.32.0")
    implementation("com.google.mlkit:image-labeling:17.0.9")
    implementation("com.google.mlkit:image-labeling-custom:16.3.1")
    implementation("com.google.mlkit:object-detection:17.0.0")
    implementation("com.google.mlkit:vision-common:17.3.0")
    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    // SwipeRefresh
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.30.1")
    // Animation
    implementation ("com.airbnb.android:lottie-compose:6.0.0")
    // Icon
    implementation ("androidx.compose.material:material-icons-extended:1.5.1")
    // Gps
    implementation ("com.squareup.okhttp3:okhttp:4.11.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.accompanist:accompanist-permissions:0.28.0")
    // ViewModel và LiveData
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation ("androidx.navigation:navigation-compose:2.4.0-alpha10")
    // Accompanist Pager
    implementation ("com.google.accompanist:accompanist-pager:0.28.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")
    implementation ("androidx.compose.ui:ui:1.4.0")
    implementation ("androidx.compose.material:material:1.4.0")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation ("androidx.activity:activity-compose:1.6.0")
    // Thư viện Coil cho Jetpack Compose
    implementation ("io.coil-kt:coil:2.2.2")
    implementation ("io.coil-kt:coil-compose:2.2.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    // api
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.play.services.location)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}