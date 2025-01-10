plugins {
    `kotlin-dsl`
}

@Suppress("UnstableApiUsage")
gradlePlugin {
   plugins {
        register("my-cool-plugin") {
            id = "com.kevinschildhorn.gcartifactmanager"
            implementationClass = "com.kevinschildhorn.gcartifactmanager.KMMBridgeGooglePlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("co.touchlab.kmmbridge:kmmbridge:1.2.0")
    implementation("com.google.cloud:google-cloud-storage:2.46.0")
}