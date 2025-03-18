package com.example.domparser.service.impl;

import com.example.domparser.exception.DomParserException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Service
public class DomParserServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(DomParserServiceImpl.class);

    public boolean checkValidity( String requestBody ){
        logger.info("The DomParser service is checked for validity");

        if( requestBody == null || requestBody.isEmpty() || requestBody.trim().equals("") ){
            logger.info("The request is null or empty");
            return false;
        }

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(requestBody)));

            logger.info("The request is valid");
            return true;
        } catch ( Exception e ){
            logger.error("We faced an exception while parsing the request body {}", e.getMessage());
            return false;
        }
    }

    public String getInnerHtml( Element element ){
        logger.info("The Inner HTML is parsed in this block");

        try{
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter writer = new StringWriter();
            NodeList nodeList = element.getChildNodes();

            for( int i=0 ; i<nodeList.getLength() ; i++ ){
                Node node = nodeList.item(i);

                if( node.getNodeType() == Node.ELEMENT_NODE ){
                    Element element1 = (Element) node;
                    transformer.transform(new DOMSource(element1), new StreamResult(writer));
                }
            }
            return writer.toString();
        } catch ( TransformerException ex ){
            logger.error("Getting the following error during the transformer process: {}", ex.getMessage());
            return "";
        }

    }

    public String getOuterHtml( Element element ){
        logger.info("The Outer HTML is parsed in this block");
        StringBuilder sb = new StringBuilder();

        try{
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(element), new StreamResult(writer));

            return writer.toString();
        } catch ( TransformerException ex ){
            logger.error("Getting the following error during the transformer process: {}", ex.getMessage());
            return "";
        }
    }


    public NodeList getChildElements(Element element){

        NodeList childNodes = element.getChildNodes();

        for( int i=0 ; i<childNodes.getLength() ; i++ ){
            Node node = childNodes.item(i);
//            if( node.getNodeType() == Node.ELEMENT_NODE ){
//                newRoot.appendChild(node);
//            }
        }

        return childNodes;
    }

    public Map<String, String> getAttributes(Element element){
        logger.info("We are parsing the elements from the getAttributes() with the node tag: " + element.getTagName());
        NamedNodeMap namedNodeMap = element.getAttributes();
        Map<String, String> attributeMap = new HashMap<>();

        for( int i=0 ; i<namedNodeMap.getLength() ; i++ ){
            Node node = namedNodeMap.item(i);
            attributeMap.put(node.getNodeName(), node.getNodeValue());
        }

        logger.info("We are parsing the elements from the getAttributes() with the node tag: " + element.getTagName());
        return attributeMap;
    }
}
