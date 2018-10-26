package com.omega.buildplugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

open class HttpLegacyFixTask : DefaultTask() {

    @Input
    var manifestFile : File? = null

    @Suppress("unused")
    @TaskAction
    fun fix() {
        System.out.println("PUSHING HTTP LEGACY SUPPORT TO MANIFEST")
    }
}
