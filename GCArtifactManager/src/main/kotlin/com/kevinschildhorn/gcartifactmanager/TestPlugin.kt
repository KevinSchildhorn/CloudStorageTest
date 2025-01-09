package com.kevinschildhorn.gcartifactmanager
/*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File
import java.nio.file.Files

@Suppress("unused")
class KMMBridgeGitHubPlugin : BaseKMMBridgePlugin() {
    override fun apply(project: Project) {
        super.apply(project)
        val githubDeploySourceRepo = project.findStringProperty("githubDeploySourceRepo")
        val githubDeployTargetRepo = project.findStringProperty("githubDeployTargetRepo")
        if (githubDeploySourceRepo != null && githubDeployTargetRepo != null) {
            project.tasks.register("setupDeployKeys") {
                group = TASK_GROUP_NAME
                description = "Helper task to setup GitHub deploy keys. Creates an ssh public/private key pair and adds them to the target and source repos."
                outputs.upToDateWhen { false } // This should always run

                @Suppress("ObjectLiteralToLambda")
                doLast(object : Action<Task> {
                    override fun execute(t: Task) {
                        val githubDeployKeyPrefix = project.findStringProperty("githubDeployKeyPrefix") ?: "KMMBridge"

                        val keyname = "$githubDeployKeyPrefix Key"

                        val tempDir = Files.createTempDirectory("kmmbridge")
                        println("Generated temp dir: $tempDir")
                        val deployKeyName = "deploykey"
                        val deployKeyPrivateFilePath = File(tempDir.toFile(), deployKeyName)
                        val deployKeyPublicFilePath = File(tempDir.toFile(), "${deployKeyName}.pub")

                        try {
                            project.procRunFailLog(
                                "ssh-keygen",
                                "-t",
                                "ed25519",
                                "-f",
                                deployKeyPrivateFilePath.toString(),
                                "-C",
                                "git@github.com:$githubDeployTargetRepo",
                                "-P",
                                ""
                            )

                            project.procRunFailLog(
                                "gh",
                                "repo",
                                "deploy-key",
                                "add",
                                deployKeyPublicFilePath.toString(),
                                "-w",
                                "-R",
                                githubDeployTargetRepo,
                                "-t",
                                keyname
                            )

                            project.procRunFailLog(
                                "gh",
                                "secret",
                                "set",
                                "${githubDeployKeyPrefix}_SSH_KEY",
                                "--body",
                                deployKeyPrivateFilePath.readText(),
                                "-R",
                                githubDeploySourceRepo
                            )
                        } finally {
                            deployKeyPrivateFilePath.delete()
                            deployKeyPublicFilePath.delete()
                            Files.deleteIfExists(tempDir)
                        }
                    }
                })
            }
        }
    }
}*/