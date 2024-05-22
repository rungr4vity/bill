package com.example.bill.playgrounds

import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main() {

    // Specify the path to your XML file
    val filePath = "app/src/main/java/com/example/bill/playgrounds/example.xml"
    val xmlFile = File(filePath)

    // Parse the XML file
    val documentBuilderFactory = DocumentBuilderFactory.newInstance()
    val documentBuilder = documentBuilderFactory.newDocumentBuilder()
    val document: Document = documentBuilder.parse(xmlFile)

    // Normalize the XML structure
    document.documentElement.normalize()

    // Get all book elements
    val nodeList = document.getElementsByTagName("book")

    for (i in 0 until nodeList.length) {
        val node = nodeList.item(i)

        if (node.nodeType == org.w3c.dom.Node.ELEMENT_NODE) {
            val element = node as Element

            // Extract data from the XML elements
            val title = element.getElementsByTagName("title").item(0).textContent
            val author = element.getElementsByTagName("author").item(0).textContent

            println("Book Title: $title")
            println("Author: $author")
        }
    }

}


