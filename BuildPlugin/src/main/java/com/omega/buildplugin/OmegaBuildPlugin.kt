package com.omega.buildplugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectSet
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.net.URL

class OmegaBuildPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val ext = project.extensions.create("networkBuild", NetworkBuildData::class.java)

        project.afterEvaluate {
            System.out.println("Passed domain = ${getDomain(ext.serverIp)}")
            val hasApp = project.plugins.hasPlugin(AppPlugin::class.java)
            val hasLib = project.plugins.hasPlugin(LibraryPlugin::class.java)

            if (!hasApp && !hasLib) {
                throw IllegalStateException("'com.android.application' or 'com.android.library' plugin required.")
            }

            val variants: DomainObjectSet<out BaseVariant> = if (hasApp) {
                project.extensions.findByType(AppExtension::class.java)!!.applicationVariants
            } else {
                project.extensions.findByType(LibraryExtension::class.java)!!.libraryVariants
            }

            variants.all { variant ->
                variant.outputs.all { output ->
                    val manifestFile =
                        File(output.processManifest.manifestOutputDirectory.absolutePath + "/AndroidManifest.xml")

                    val httpLegacyFixTask = project.tasks.create(
                        "preform${variant.name.capitalize()}${HttpLegacyFixTask::class.java.name}",
                        HttpLegacyFixTask::class.java)
                    httpLegacyFixTask.manifestFile = manifestFile
                    httpLegacyFixTask.dependsOn.add("process${variant.name.capitalize()}Manifest")
                    variant.register(httpLegacyFixTask)
                }
            }
        }
    }

    fun getDomain(serverIp: String?): String {
        return serverIp?.let { URL(it).host } ?: ""
    }
}
