package com.example.domparser.controller;

import com.example.domparser.model.ParserResponse;
import com.example.domparser.service.DomParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/dom-parser/")
public class DomParserController {

    @Autowired
    DomParserService domParserService;

    @PostMapping("get-value/")
    public List<ParserResponse> parseDom(
            @RequestBody() String requestBody,
            @RequestParam(value = "element", required = false, defaultValue = "None") String elementFilter,
            @RequestParam(value = "attribute", required = false, defaultValue = "None") String attribueListFilter,
            @RequestParam(value = "returnAttribute", required = false, defaultValue = "None") String returnAttribute ){

        System.out.println("Entered the parseDom method in DomParserController ");

        List<ParserResponse> responseDom = new ArrayList<>();

        responseDom = domParserService.parseDomTree( requestBody, elementFilter, attribueListFilter, returnAttribute );

        return responseDom;
    }


}
