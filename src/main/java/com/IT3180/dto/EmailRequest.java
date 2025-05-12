package com.IT3180.dto;

import java.util.List;

public class EmailRequest {
    private String to;
    private String subject;
    private String body;
    private List<String> attachments;

    // Getters & Setters
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public List<String> getAttachments() { return attachments; }
    public void setAttachments(List<String> attachments) { this.attachments = attachments; }
}
