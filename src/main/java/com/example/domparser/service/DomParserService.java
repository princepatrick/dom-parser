package com.example.domparser.service;

import com.example.domparser.exception.DomParserException;
import com.example.domparser.model.ParserResponse;
import com.example.domparser.service.impl.DomParserServiceImpl;
import com.example.domparser.util.DomParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service()
@Component()
public class DomParserService {

    private static Logger logger = LoggerFactory.getLogger(DomParserService.class);

    @Autowired
    DomParserServiceImpl domParserServiceImpl;

    @Autowired
    DomParserUtil domParserUtil;

    public List<ParserResponse> parseDomTree( String requestBody, String elementFilter,
                                              String attributeListFilter, String returnAttribute ){

        logger.info("Parsing the dom tree using parseDomTree() in DomParserService class");

        if( !domParserServiceImpl.checkValidity( requestBody ) ){
            logger.info("The domParserServiceImpl is invalid");
            throw new DomParserException("The Dom Tree is not valid");
        }

        List<ParserResponse> response = new ArrayList<>();

        if( elementFilter != "None" && attributeListFilter != "None" ){
            logger.info("The dom tree is parsed with element and attribute list");
            response = parseDomTreeWithElementAndAttrList( requestBody, elementFilter, attributeListFilter, returnAttribute );
        } else if( elementFilter == "None" && attributeListFilter != "None" ){
            logger.info("The dom tree is parsed with attribute list");
            response = parseDomTreeWithAttrList( requestBody, attributeListFilter, returnAttribute );
        } else if( elementFilter != "None" && attributeListFilter == "None" ){
            logger.info("The dom tree is parsed with element");
            response = parseDomTreeWithElement( requestBody, elementFilter, returnAttribute );
        }

        return response;

    }

    public List<ParserResponse> parseDomTreeWithElementAndAttrList( String requestBody, String elementFilter,
                                                                    String attributeListFilter, String returnAttribute  ){
        List<ParserResponse> response = new ArrayList<>();

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(new InputSource(new StringReader(requestBody)));
            document.getDocumentElement().normalize();

            NodeList elementList = document.getElementsByTagName(elementFilter);
            logger.info("Found elements with the tag name {}", elementFilter);

            List<String> attributeFilters = Arrays.asList(attributeListFilter.split(","));

            for( int i=0 ; i<elementList.getLength() ; i++ ){
                Node node = elementList.item(i);

                if( node.getNodeType() == Node.ELEMENT_NODE ){
                    Element element = (Element) node;
                    boolean matchesAllAtributes = true;

                    for( String attribute : attributeFilters ){
                        String[] parts = attribute.split("=");

                        if( parts.length != 2 ) continue;
                        String attributeName = parts[0];
                        String attributeValue = parts[1];

                        if( !element.hasAttribute(attributeName)
                            || element.getAttribute(attributeName) != attributeValue ){
                            matchesAllAtributes = false;
                            break;
                        }
                    }

                    if( !matchesAllAtributes ) continue;

                    logger.info("Matches All Attribute for the element with the returnAttribute: {}", returnAttribute);

                    ParserResponse parserResponse = new ParserResponse();

                    if( returnAttribute.equals("INNER_TEXT")){
                        parserResponse.setParsedResponse(element.getTextContent());
                    } else if( returnAttribute.equals("INNER_HTML")){
                        parserResponse.setParsedResponse(domParserServiceImpl.getInnerHtml(element));
                    } else if( returnAttribute.equals("OUTER_HTML")){
                        parserResponse.setParsedResponse(domParserServiceImpl.getOuterHtml(element));
                    } else {
                        parserResponse.setParsedResponse(element.getAttribute(returnAttribute));
                    }

                    Map<String, String> attributes = domParserServiceImpl.getAttributes(element);
                    parserResponse.setAttributes(attributes);

                    response.add(parserResponse);
                }
            }
        } catch( Exception e ){
            logger.error("Exception while parsing the DOM tree");
            throw new DomParserException("We received an error while parsing the DOM tree!!" + e.getMessage());
        }

        return response;
    }

    public List<ParserResponse> parseDomTreeWithAttrList( String requestBody,
                                                                    String attributeListFilter, String returnAttribute  ){
        List<ParserResponse> response = new ArrayList<>();

        try{
            logger.info("The dom tree is parsed with the attribute list");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(requestBody)));
            document.getDocumentElement().normalize();

            List<String> attributeList = Arrays.asList(attributeListFilter.split(","));
            Element root = document.getDocumentElement();

            findElementByAttributes( attributeList, root, response, returnAttribute );

        } catch (Exception e){
            logger.error("Errors occuring at parseDomTreeWithAttrList() with the exception " + e.getMessage());
            throw new DomParserException("We are facing the DomParserException while parsing the data " + e.getMessage());
        }

        return response;

    }

    public List<ParserResponse> parseDomTreeWithElement( String requestBody,  String elementFilter,
                                                          String returnAttribute  ){

        List<ParserResponse> response = new ArrayList<>();

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(requestBody)));
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName(elementFilter);

            for( int i=0 ; i<nodeList.getLength() ; i++ ){
                Node node = nodeList.item(i);

                if( node.getNodeType() == Node.ELEMENT_NODE ){
                    Element element = (Element) node;
                    ParserResponse parserResponse = new ParserResponse();

                    logger.info("Matches All Attribute for the element with the returnAttribute: {}", returnAttribute);

                    if(returnAttribute.equals("INNER_TEXT")){
                        parserResponse.setParsedResponse(element.getTextContent());
                    } else if(returnAttribute.equals("INNER_HTML")){
                        parserResponse.setParsedResponse(domParserServiceImpl.getInnerHtml(element));
                    } else if(returnAttribute.equals("OUTER_HTML")){
                        parserResponse.setParsedResponse(domParserServiceImpl.getOuterHtml(element));
                    } else {
                        parserResponse.setParsedResponse(element.getAttribute(returnAttribute));
                    }

                    Map<String, String> attributeList = domParserServiceImpl.getAttributes(element);
                    parserResponse.setAttributes(attributeList);

                    response.add(parserResponse);
                }
            }
        } catch (Exception e){
            logger.error("Errors occuring at parseDomTreeWithAttrList() with the exception " + e.getMessage());
            throw new DomParserException("We are facing the DomParserException while parsing the data " + e.getMessage());
        }

        return response;
    }

    private void findElementByAttributes( List<String> attributeList, Element root, List<ParserResponse> response, String returnAttribute ){
        boolean matchesAllAttributes = true;

        for(String attribute : attributeList){
            String[] attributePair = attribute.split("=");

            if( attributePair.length != 2 ) {
                matchesAllAttributes = false;
                break;
            }

            String attributeKey = attributePair[0];
            String attributeVal = attributePair[1];

            if( !root.hasAttribute(attributeKey) || !root.getAttribute(attributeKey).equals(attributeVal) ){
                matchesAllAttributes = false;
                break;
            }
        }

        if( matchesAllAttributes ){
            logger.info("Matches All Attribute for the element with the returnAttribute: {}", returnAttribute);

            ParserResponse parserResponse = new ParserResponse();

            if( returnAttribute.equals("INNER_TEXT") ){
                parserResponse.setParsedResponse(root.getTextContent());
            } else if( returnAttribute.equals("INNER_HTML") ){
                parserResponse.setParsedResponse(domParserServiceImpl.getInnerHtml(root));
            } else if( returnAttribute.equals("OUTER_HTML") ) {
                parserResponse.setParsedResponse(domParserServiceImpl.getOuterHtml(root));
            } else {
                parserResponse.setParsedResponse(root.getAttribute(returnAttribute));
            }

            Map<String, String> attributes = domParserServiceImpl.getAttributes(root);
            parserResponse.setAttributes(attributes);

            response.add(parserResponse);
        }

        NodeList childNodes = domParserServiceImpl.getChildElements(root);

        for( int i=0 ; i<childNodes.getLength() ; i++ ){
            Node node = childNodes.item(i);

            if( node.getNodeType() == Node.ELEMENT_NODE ){
                findElementByAttributes(attributeList, (Element) node, response, returnAttribute);
            }
        }
    }
}
