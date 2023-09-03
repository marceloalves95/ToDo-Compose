plugins {
    id("com.android.application")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-android")
    id("jacoco-reports")
}

android {
    namespace = "br.com.todo_compose"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.todo_compose"
        minSdk = 26
        targetSdk = 33
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    tasks.withType<Test> {
        extensions.configure(JacocoTaskExtension::class) {
            isIncludeNoLocationClasses = true
            excludes = listOf("jdk.internal.*")
        }
    }
}

dependencies {

    implementation(project(":libraries:compose"))
    implementation(project(":libraries:extensions"))
    implementation(project(":libraries:testing"))

    //AndroidX
    implementation(libs.core.ktx)
    //Lifecycle
    implementation(libs.bundles.lifecycle)
    //Compose
    implementation(libs.bundles.compose)
    //Material3
    implementation(libs.material3)
    implementation(libs.material.icons.extended)

    //Room
    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    //ThirdParty
    implementation(libs.koin)
    implementation(libs.coil)
    implementation(libs.coil.svg)
    implementation(libs.material.dialogs.datetime)

    //Desugaring
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    //Unit Test
    testImplementation(libs.assertK)
    testImplementation(libs.robolectric)
    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.test.ext.junit)
    testImplementation(libs.test.core.ktx)


    //Instrumental Test
    androidTestImplementation(libs.assertK)
    androidTestImplementation(libs.test.core.ktx)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.test.ext.junit)
    androidTestImplementation(libs.mockk.agent)
    androidTestImplementation(libs.arch.core.testing)
    //androidTestImplementation(libs.barista)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.truth.test)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}