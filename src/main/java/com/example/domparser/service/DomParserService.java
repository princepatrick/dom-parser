package com.example.domparser.service;

import com.example.domparser.exception.DomParserException;
import com.example.domparser.model.ParserResponse;
import com.example.domparser.service.impl.DomParserServiceImpl;
import com.example.domparser.util.DomParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service()
@Component()
public class DomParserService {

    @Autowired
    DomParserServiceImpl domParserServiceImpl;

    @Autowired
    DomParserUtil domParserUtil;

    public List<ParserResponse> parseDomTree( String requestBody, String elementFilter,
                                              String attributeListFilter, String returnAttribute ){

        if( !domParserServiceImpl.checkValidity( requestBody ) ){
            throw new DomParserException("The Dom Tree is valid");
        }

        List<ParserResponse> response = new ArrayList<>();

        if( elementFilter != "None" && attributeListFilter != "None" ){
            response = parseDomTreeWithElementAndAttrList( requestBody, elementFilter, attributeListFilter, returnAttribute );
        } else if( elementFilter == "None" && attributeListFilter != "None" ){
            response = parseDomTreeWithAttrList( requestBody, attributeListFilter, returnAttribute );
        } else if( elementFilter != "None" && attributeListFilter == "None" ){
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
