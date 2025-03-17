package com.example.domparser.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

@Service
public class DomParserServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(DomParserServiceImpl.class);

    public boolean checkValidity( String requestBody ){
        logger.info("The DomParser service is checked for validity");
        return true;
    }

    public String getInnerHtml( Element element ){
        logger.info("The Inner HTML is parsed in this block");
        return "GET_INNER_HTML";
    }

    public String getOuterHtml( Element element ){
        logger.info("The Outer HTML is parsed in this block");
        return "GET_OUTER_HTML";
    }

    public Map<String, String> getAttributes(Element element){
        return new HashMap<>();
    }
}
