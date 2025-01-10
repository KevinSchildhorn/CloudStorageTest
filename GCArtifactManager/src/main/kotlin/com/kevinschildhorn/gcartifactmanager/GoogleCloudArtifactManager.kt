package com.kevinschildhorn.gcartifactmanager

import co.touchlab.kmmbridge.KmmBridgeExtension
import co.touchlab.kmmbridge.artifactmanager.ArtifactManager
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.StorageOptions
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.logging.LogLevel
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.getByType
import java.io.File
import java.nio.file.Paths
import java.util.concurrent.TimeUnit


class GoogleCloudArtifactManager(
    private val bucketName: String,
    private val projectId: String,
) : ArtifactManager {

    lateinit var frameworkName: String

    override fun configure(
        project: Project, version: String, uploadTask: TaskProvider<Task>, kmmPublishTask: TaskProvider<Task>
    ) {
        this.frameworkName = project.kmmBridgeExtension.frameworkName.get()
    }

    private val Project.kmmBridgeExtension get() = extensions.getByType<KmmBridgeExtension>()

    override fun deployArtifact(task: Task, zipFilePath: File, version: String): String {
        // The name in your bucket
        val artifactName = "$frameworkName-$version"

        val storage = StorageOptions.newBuilder()
            .setProjectId(projectId)
            .build()
            .service

        val blobId = BlobId.of(bucketName, artifactName)
        val blobInfo = BlobInfo.newBuilder(blobId).build()
        val blob = storage.createFrom(blobInfo, Paths.get(zipFilePath.path))

        return "https://storage.googleapis.com/${bucketName}/${blob.name}.zip"
    }
}