package com.example.bglogger.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter 
public class BggValueDTO {

    @JacksonXmlProperty(isAttribute = true, localName = "value")
    private Integer value;
}
