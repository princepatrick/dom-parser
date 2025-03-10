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

import java.util.ArrayList;
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

        return response;

    }

    public List<ParserResponse> parseDomTreeWithAttrList( String requestBody,
                                                                    String attributeListFilter, String returnAttribute  ){

        List<ParserResponse> response = new ArrayList<>();

        return response;

    }

    public List<ParserResponse> parseDomTreeWithElement( String requestBody,  String elementFilter,
                                                          String returnAttribute  ){

        List<ParserResponse> response = new ArrayList<>();

        return response;

    }
}
