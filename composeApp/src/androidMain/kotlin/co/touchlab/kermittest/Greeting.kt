package co.touchlab.kermittest

import com.google.api.gax.paging.Page
import com.google.auth.ApiKeyCredentials
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


private fun uploadArtifact(zipFilePath: File, fileName: String) {
    val bucketName: String = "kevins_sample"
    val storage = StorageOptions.getDefaultInstance().service
    /*
    val storageOptions = StorageOptions.newBuilder()
        .setCredentials(ApiKeyCredentials.create("APIKEY"))
        .build()
    val storage = storageOptions.service*/
    val responses = storage.testIamPermissions(
        bucketName,
        listOf("storage.buckets.get", "storage.objects.get", "storage.objects.create")
    )
    val bucket = storage.get(bucketName) ?: error("Bucket $bucketName does not exist.")
    println(bucket)
    val blob = bucket.create(fileName, zipFilePath.readBytes())
    blob.signUrl(4, TimeUnit.DAYS).path
}


suspend fun quickstart(bucketName: String = "kevins_sample") {
    //val storage = StorageOptions.getDefaultInstance().service
    StorageOptions.getDefaultInstance()
    val storageOptions = StorageOptions.newBuilder()
        .setProjectId("lithe-tesla-445318-g6")
        //.setCredentials(ApiKeyCredentials.create("AIzaSyAWApNofSCNyG7nl7Fa91VrekeUBHAW6xo"))
        .build()
    val storage = storageOptions.service

    val responses = storage.testIamPermissions(
        bucketName,
        listOf("storage.buckets.get", "storage.objects.get", "storage.objects.create")
    )
    val bucket = storage.get(bucketName) ?: error("Bucket $bucketName does not exist.")
    println(bucket)
}


// When interacting with Google Cloud Client libraries, the library can auto-detect the
// credentials to use.
@Throws(IOException::class)
suspend fun authenticateImplicitWithAdc(project: String? = "lithe-tesla-445318-g6") {
    // *NOTE*: Replace the client created below with the client required for your application.
    // Note that the credentials are not specified when constructing the client.
    // Hence, the client library will look for credentials using ADC.
    //
    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests.

    val storage: Storage = StorageOptions.newBuilder().setProjectId(project).build().service

    println("Buckets:")
    val buckets: Page<Bucket> = storage.list()
    for (bucket in buckets.iterateAll()) {
        println(bucket.toString())
    }
    println("Listed all storage buckets.")
}


class Greeting {
    fun greet(): String {
        return "Hello, World!"
    }
}