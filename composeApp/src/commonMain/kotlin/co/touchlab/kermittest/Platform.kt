package co.touchlab.kermittest

import com.google.api.gax.paging.Page
import com.google.auth.Credentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import java.io.IOException

fun quickstart(bucketName: String = "kevins_sample") {
    // [START storage_quickstart]
    // import com.google.cloud.storage.StorageOptions
    val storage = StorageOptions.getDefaultInstance().service
    val bucket = storage.get(bucketName) ?: error("Bucket $bucketName does not exist.")

    println("Listing all blobs in bucket $bucketName:")
    bucket.list().iterateAll().forEach { blob ->
        println("${blob.name} (content-type: ${blob.contentType}, size: ${blob.size})")
    }

    val bucket2 = storage.get(bucketName)
        ?: error("Bucket $bucketName does not exist. You can create a new bucket with the command 'create <bucket>'. \n")
    // [END storage_quickstart]
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