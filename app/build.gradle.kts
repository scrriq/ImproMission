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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation (libs.androidx.navigation.dynamic.features.fragment)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.navigation.compose.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.foundation.layout.android)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.androidx.foundation.layout.android)
    ksp(libs.androidx.room.compiler)
    implementation (libs.androidx.room.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.ktx) // import viewModel
    implementation(libs.androidx.lifecycle.livedata.ktx) // импорт для flow, чтобы был live data, который отлеживает изменения

    implementation(libs.androidx.fragment.ktx)

    //compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("androidx.compose.material:material-icons-core:1.6.0")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")





    // корутины
    implementation( libs.org.jetbrains.kotlinx.kotlinx.coroutines.core)
    implementation( libs.kotlinx.coroutines.android)


    //compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}