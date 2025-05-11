plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compilerKsp)
    alias(libs.plugins.androidx.navigation.safe.args)
}

android {
    namespace = "com.example.impromission"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.impromission"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation (libs.androidx.navigation.dynamic.features.fragment)
    ksp(libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.ktx) // import viewModel
    implementation(libs.androidx.lifecycle.livedata.ktx) // импорт для flow, чтобы был live data, который отлеживает изменения

    implementation(libs.androidx.fragment.ktx)


    // корутины
    implementation( libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation( libs.kotlinx.coroutines.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}