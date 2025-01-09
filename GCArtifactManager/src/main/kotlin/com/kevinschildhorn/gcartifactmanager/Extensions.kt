package com.kevinschildhorn.gcartifactmanager

import co.touchlab.kmmbridge.KmmBridgeExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
fun Project.gitHubReleaseArtifacts(
    bucket: String,
    releaseString: String? = null,
) {
    kmmBridgeExtension.setupGitHubReleaseArtifacts(
        GoogleCloudArtifactManager(
            bucket,
            releaseString,
        )
    )
}

private fun KmmBridgeExtension.setupGitHubReleaseArtifacts(
    githubReleaseArtifactManager: GoogleCloudArtifactManager
) {
    artifactManager.setAndFinalize(githubReleaseArtifactManager)
}

internal val Project.kmmBridgeExtension get() = extensions.getByType<KmmBridgeExtension>()
