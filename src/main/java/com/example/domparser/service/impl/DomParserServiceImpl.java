package com.example.domparser.service.impl;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DomParserServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(DomParserServiceImpl.class);

    public boolean checkValidity( String requestBody ){

        logger.info("The DomParser service is checked for validity");
        return true;

    }
}
