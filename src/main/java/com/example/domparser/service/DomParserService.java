package com.example.domparser.service;

import com.example.domparser.model.ParserResponse;

import java.util.ArrayList;
import java.util.List;

public class DomParserService {

    public List<ParserResponse> parseDomTree( String requestBody, String elementFilter,
                                              String attributeListFilter, String returnAttribute ){

        List<ParserResponse> response = new ArrayList<>();

        if( elementFilter != "None" && attributeListFilter != "None" ){
            return parseDomTreeWithElementAndAttrList( requestBody, elementFilter, attributeListFilter, returnAttribute );
        } else if( elementFilter == "None" && attributeListFilter != "None" ){
            return parseDomTreeWithAttrList( requestBody, attributeListFilter, returnAttribute );
        } else if( elementFilter != "None" && attributeListFilter == "None" ){
            return parseDomTreeWithElement( requestBody, elementFilter, returnAttribute );
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
