import co.touchlab.kmmbridge.artifactmanager.ArtifactManager
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import com.google.cloud.storage.StorageOptions

dependencies {
    debugImplementation(compose.uiTooling)
    implementation(libs.google.cloud.storage)
}

buildscript {
    dependencies {
        classpath(libs.google.cloud.storage)
    }
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kmmbridge)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    val xcf = XCFramework()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            isStatic = false
            baseName = "KevinTest"
            xcf.add(this)
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(libs.google.cloud.storage)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        iosMain.dependencies {

        }
    }
}

android {
    namespace = "co.touchlab.kermittest"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "co.touchlab.kermittest"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packagingOptions {
        exclude("META-INF/INDEX.LIST")
    }
}




class GoogleArtifactManager : ArtifactManager {

    override fun deployArtifact(task: Task, zipFilePath: File, version: String): String {
        val bucketName = "kevins_sample"
        val storage = StorageOptions.getDefaultInstance().service
        val responses = storage.testIamPermissions(
            bucketName,
            listOf("storage.buckets.get", "storage.objects.get", "storage.objects.create")
        )
        task.logger.log(LogLevel.INFO, responses.toString())
        val bucket = storage.get(bucketName)
        task.logger.log(LogLevel.INFO, bucket.name)
        val blob = bucket.create(zipFilePath.name, zipFilePath.readBytes())
        return blob.signUrl(4, TimeUnit.DAYS).path
    }
}

kmmbridge {
    artifactManager.set(GoogleArtifactManager())
}

