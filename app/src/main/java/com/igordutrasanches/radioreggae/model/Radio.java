package com.igordutrasanches.radioreggae.model;

import java.io.Serializable;

public class Radio implements Serializable {

    private String urlStreaming, name, sloga, linkImage;

    public Radio(String urlStreaming, String name, String sloga, String linkImage) {

        this.urlStreaming = urlStreaming;
        this.name = name;
        this.sloga = sloga;
        this.linkImage = linkImage;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public String getUrlStreaming() {
        return urlStreaming;
    }

    public void setUrlStreaming(String urlStreaming) {
        this.urlStreaming = urlStreaming;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSloga() {
        return sloga;
    }

    public void setSloga(String sloga) {
        this.sloga = sloga;
    }

}
