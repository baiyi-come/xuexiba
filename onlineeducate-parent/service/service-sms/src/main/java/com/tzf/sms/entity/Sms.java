package com.tzf.sms.entity;



import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "short.message")
public class Sms  {

    private String host;
    private String path;
    private String method;
    private String appcode;
    private String smsSignId;
    private String templateId;

    public Sms() {
    }

    public Sms(String host, String path, String method, String appcode, String smsSignId, String templateId) {
        this.host = host;
        this.path = path;
        this.method = method;
        this.appcode = appcode;
        this.smsSignId = smsSignId;
        this.templateId = templateId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    public String getSmsSignId() {
        return smsSignId;
    }

    public void setSmsSignId(String smsSignId) {
        this.smsSignId = smsSignId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
