package com.omega.buildplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.net.URL

open class OmegaBuildPlugin: Plugin<Project> {
    override fun apply(project : Project) {
        val ext = project.extensions.create("networkBuild", NetworkBuildData::class.java)
        project.afterEvaluate {
            System.out.println("Passed domain = ${getDomain(ext.serverIp)}")
        }
    }

    fun getDomain(serverIp : String?) : String {
        System.out.println(serverIp)
        return serverIp?.let { URL(it).host } ?: ""
    }
}
