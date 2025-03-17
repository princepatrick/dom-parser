package com.example.domparser.service;

import com.example.domparser.exception.DomParserException;
import com.example.domparser.model.ParserResponse;
import com.example.domparser.service.impl.DomParserServiceImpl;
import com.example.domparser.util.DomParserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        } catch (Exception e){

        }

        return response;

    }

    public List<ParserResponse> parseDomTreeWithElement( String requestBody,  String elementFilter,
                                                          String returnAttribute  ){

        List<ParserResponse> response = new ArrayList<>();

        return response;

    }
}
