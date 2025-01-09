plugins {
    `kotlin-dsl`
    //id("java-gradle-plugin")
}

@Suppress("UnstableApiUsage")
gradlePlugin {
   plugins {
        register("google-plugin") {
            id = "com.kevinschildhorn.gcartifactmanager"
            implementationClass = "com.kevinschildhorn.gcartifactmanager.KMMBridgeGooglePlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //implementation(kotlin("stdlib"))
    implementation("co.touchlab.kmmbridge:kmmbridge:1.2.0")
    implementation("com.google.cloud:google-cloud-storage:2.46.0")
}