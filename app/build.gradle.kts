plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.booktarang"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.booktarang"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file("./BookTarang.jks")
            storePassword = System.getenv("KEYSTORE_PASSWORD")
            keyAlias = System.getenv("KEY_ALIAS")
            keyPassword = System.getenv("KEYSTORE_PASSWORD")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    flavorDimensions += "BookTarang"
    productFlavors {
        create("dev") {
            dimension = "BookTarang"
            applicationId = "com.example.booktarang.dev"
            resValue("string", "app_name", "Book Tarang Test")
            buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8000/api/\"")
        }
        create("prd") {
            dimension = "BookTarang"
            applicationId = "com.example.booktarang"
            resValue("string", "app_name", "Book Tarang")
            buildConfigField("String", "API_BASE_URL", "\"http://10.0.2.2:8000/api/\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.androidx.navigation.fragment.ktx)
        implementation(libs.androidx.navigation.ui.ktx)
        implementation(libs.androidx.annotation)
        implementation(libs.androidx.lifecycle.livedata.ktx)
        implementation(libs.androidx.lifecycle.viewmodel.ktx)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)

        // Retrofit
        implementation("com.squareup.retrofit2:retrofit:2.11.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

        // Picasso
        implementation("com.squareup.picasso:picasso:2.71828")

        // AndroidX
        implementation("androidx.activity:activity-ktx:1.8.2")
        implementation("androidx.fragment:fragment-ktx:1.6.2")

        // Gson
        implementation("com.google.code.gson:gson:2.10.1")

        // Security
        implementation("androidx.security:security-crypto:1.1.0-alpha06")

        // Material Design
        implementation("com.google.android.material:material:1.11.0")
    }
}