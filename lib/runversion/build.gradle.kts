plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    commonConfig()
}

dependencies {
    implementation(project(":core"))

    kapt("com.google.dagger:dagger-compiler:${Versions.dagger}")

    // Testing
    testImplementation("junit:junit:${Versions.junit}")
    testImplementation("org.mockito:mockito-inline:${Versions.mockito}")
    testImplementation("com.nhaarman:mockito-kotlin-kt1.1:${Versions.mockitoKotlin}") {
        exclude("org.jetbrains.kotlin")
    }
    testImplementation("com.willowtreeapps.assertk:assertk:${Versions.assertk}") {
        exclude("org.jetbrains.kotlin")
    }
}
