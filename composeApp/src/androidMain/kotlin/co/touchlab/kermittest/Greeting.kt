package co.touchlab.kermittest

import com.google.api.gax.paging.Page
import com.google.auth.ApiKeyCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Bucket
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.util.concurrent.TimeUnit


suspend fun uploadArtifact() {
    val storage = StorageOptions.newBuilder().setProjectId("lithe-tesla-445318-g6").build().service
    val blobId = BlobId.of("kevins_sample", "test.md")
    val blobInfo = BlobInfo.newBuilder(blobId).build()
    val blob = storage.get(blobId)
    print(blob)
}


class Greeting {
    fun greet(): String {
        return "Hello, World!"
    }
}