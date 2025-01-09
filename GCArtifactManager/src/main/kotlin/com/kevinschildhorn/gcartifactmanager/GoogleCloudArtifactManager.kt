package com.kevinschildhorn.gcartifactmanager

import co.touchlab.kmmbridge.artifactmanager.ArtifactManager
import org.gradle.api.Task
import java.io.File
import com.google.cloud.storage.StorageOptions
import org.gradle.api.logging.LogLevel
import java.util.concurrent.TimeUnit

class GoogleCloudArtifactManager(
    private val bucketName: String,
    private val releaseString: String? = null,
) : ArtifactManager {
    override fun deployArtifact(task: Task, zipFilePath: File, version: String): String {
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