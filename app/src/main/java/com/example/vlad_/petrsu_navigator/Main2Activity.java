package com.example.vlad_.petrsu_navigator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Main2Activity extends AppCompatActivity {

    String rssResult = "";
    Boolean item = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView rss = findViewById(R.id.rss);
        try {
            URL rssUrl = new URL("https://petrsu.ru/rss");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            RSSHandler rssHandler = new RSSHandler();
            xmlReader.setContentHandler(rssHandler);
            InputSource inputSource = new InputSource(rssUrl.openStream());
            xmlReader.parse(inputSource);
            rss.setText(rssResult);
        } catch (IOException e) {rss.setText(e.getMessage());
        } catch (SAXException e) {rss.setText(e.getMessage());
        } catch (ParserConfigurationException e) {rss.setText(e.getMessage());
        }
    }

    //Обработчик (парсер) rss ленты
    private class RSSHandler extends DefaultHandler {

        public void startElement(String uri, String localName, String qName,
                                 Attributes attrs) throws SAXException {
            if (localName.equals("item"))
                item = true;

            if (!localName.equals("item") && item == true)
                rssResult = rssResult + localName + ": ";

        }

        public void endElement(String namespaceURI, String localName,
                               String qName) throws SAXException {

        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
            String cdata = new String(ch, start, length);
            if (item == true)
                rssResult = rssResult +(cdata.trim()).replaceAll("\\s+", " ")+"\t";

        }
    }
}
