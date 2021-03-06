plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-android-extensions")
}

android {
    commonConfig()

    flavorDimensions("environment")

    productFlavors {
        create("production") {
            setDimension("environment")
            buildConfigField("boolean", "DEVELOP", "false")
        }

        create("develop") {
            setDimension("environment")
            buildConfigField("boolean", "DEVELOP", "true")
        }
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":lib:ui"))
    implementation(project(":lib:analytics"))
    implementation(project(":lib:settings"))
    implementation(project(":lib:places"))

    implementation("androidx.preference:preference-ktx:${Versions.androidPreference}")

    kapt("com.google.dagger:dagger-compiler:${Versions.dagger}")
}
