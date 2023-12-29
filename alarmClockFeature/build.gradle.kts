import com.android.build.api.dsl.Packaging
import org.gradle.internal.impldep.org.junit.experimental.categories.Categories.CategoryFilter.exclude

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.devtoolsKsp)
}

android {
    namespace = "com.yes.alarmclockfeature"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        viewBinding = true
    }


    packaging {
        resources {
            excludes += listOf(
                "META-INF/LICENSE.md",
                "com/yes/alarmclockfeature/presentation/ui/DatePicker.java"
            )

        }
    }

}

dependencies {
    implementation(project(":core"))
    implementation(project(":coreUI"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    //viewModel
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.fragment.ktx)
    //Dagger/////////////////////
    implementation(libs.dagger)
    androidTestImplementation(libs.dagger)
    kapt(libs.dagger.compiler)
    kaptAndroidTest(libs.dagger.compiler)
    //Preferences DataStore
    implementation(libs.androidx.datastore.preferences)
    androidTestImplementation(libs.androidx.datastore.preferences.core)
    androidTestImplementation(libs.androidx.preference.ktx)
    //  implementation libs.media3//tmp
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.dash)
    implementation(libs.androidx.media3.ui)

}