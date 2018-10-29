package com.omega.buildplugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

open class HttpLegacyFixTask : DefaultTask() {

    @Input
    var manifestFile : File? = null

    @Suppress("unused")
    @TaskAction
    fun fix() {
        manifestFile?.let {
            val manifestDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(it)
            val root = manifestDoc.documentElement
            val applicationNodeList = root.getElementsByTagName("")
            if ()
        }
    }

    private fun save(doc : Document) {
        val transformer = TransformerFactory.newInstance().newTransformer()
        val output = StreamResult(File("output.xml"))
        val input = DOMSource(doc)

        transformer.transform(input, output)
    }
}
