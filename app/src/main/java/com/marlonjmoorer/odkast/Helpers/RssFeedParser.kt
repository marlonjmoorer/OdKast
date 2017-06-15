package com.marlonjmoorer.odkast.Helpers

import com.marlonjmoorer.odkast.Models.Feed
import org.w3c.dom.Element
import org.xml.sax.SAXException
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException


/**
 * Created by marlonmoorer on 6/12/17.
 */
class RssFeedParser {


    @Throws(XmlPullParserException::class, IOException::class)
    fun parseFeed(inputStream: InputStream) {
        val factory = DocumentBuilderFactory.newInstance()
        factory.setValidating(true)
        factory.setIgnoringElementContentWhitespace(true)
        try {
            val builder = factory.newDocumentBuilder()

            val doc = builder.parse(inputStream)
            var root=doc.documentElement.firstChild as Element
            var feed= Feed().apply {
                title=root.getElementsByTagName("title").item(0).textContent
                description=root.getElementsByTagName("description").item(0).textContent
            }






        } catch (e: ParserConfigurationException) {
        } catch (e: SAXException) {
        } catch (e: IOException) {
        }


    }
}