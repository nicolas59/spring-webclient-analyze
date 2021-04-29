package com.nro.demo.webclientanalyze.client;

public class SignUrlResponse {
    private String filename;
    private String signedUrl;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSignedUrl() {
        return signedUrl;
    }

    public void setSignedUrl(String signedUrl) {
        this.signedUrl = signedUrl;
    }
}
