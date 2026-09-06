package com.example.bglogger.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Setter;

@Setter
public class ExternalGameDTO {

    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private String id;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "name")
    private List<BggNameDTO> names;
    
    @JacksonXmlProperty(localName = "minplayers")
    private BggValueDTO minPlayers;

    @JacksonXmlProperty(localName = "maxplayers")
    private BggValueDTO maxPlayers;

    @JacksonXmlProperty(localName = "minplaytime")
    private BggValueDTO minPlayingTime;

    @JacksonXmlProperty(localName = "maxplaytime")
    private BggValueDTO maxPlayingTime;

    @JacksonXmlProperty(localName = "image")
    private String image;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "link")
    private List<BggLinkDTO> links;

    public String getId() {
        return this.id;
    }

    public String getPrimaryName() {
        if (names == null || names.isEmpty()) {
            return "Unknown";
        }

        return names.stream()
            .filter(n -> "primary".equalsIgnoreCase(n.getType()))
            .map(BggNameDTO::getValue)
            .findFirst()
            .orElse(names.get(0).getValue());
    }

    public int getMinPlayers() {
        return this.minPlayers != null ? this.minPlayers.getValue() : null; 
    }

    public int getMaxPlayers() {
        return this.maxPlayers != null ? this.maxPlayers.getValue() : null;
    }

    public int getMinPlayingTime() {
        return this.minPlayingTime != null ? this.minPlayingTime.getValue() : null;
    }

    public int getMaxPlayingTime() {
        return this.maxPlayingTime != null ? this.maxPlayingTime.getValue() : null;
    }

    public String getImage() {
        return this.image;
    }

    public List<String> getCategories() {
        if (links == null || links.isEmpty()) {
            return Collections.emptyList();
        }

        return links.stream()
            .filter(link -> "boardgamecategory".equals(link.getType()))
            .map(BggLinkDTO::getValue)
            .collect(Collectors.toList());
    }
    
}
