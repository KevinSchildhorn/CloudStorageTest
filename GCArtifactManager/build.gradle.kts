
/*
* Copyright (c) 2024 Touchlab.
* Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
* in compliance with the License. You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software distributed under the License
* is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
* or implied. See the License for the specific language governing permissions and limitations under
* the License.
*/

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