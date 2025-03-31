plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.dokka)
}

kotlin {
    jvmToolchain(1_8)
}

dependencies {
    api(projects.trekkieTestCore)
    implementation(libs.kotlinx.coroutines.core)

    api(platform(libs.kotest.bom))
    api(libs.kotest.assertions)
    testImplementation(libs.kotest.junit5)

//    testImplementation(libs.kotlin.test)
//    testImplementation(libs.kotlinx.coroutines.test)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
