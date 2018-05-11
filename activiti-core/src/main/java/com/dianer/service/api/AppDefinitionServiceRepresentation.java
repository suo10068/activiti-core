package com.dianer.service.api;


import java.util.List;


public class AppDefinitionServiceRepresentation {

    protected String id;
    protected String name;
    protected String description;
    protected Integer version;
    protected String icon;
    protected String theme;
    protected List<String> models;
    protected String definition;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getVersion() {
        return version;
    }
    public void setVersion(Integer version) {
        this.version = version;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getTheme() {
        return theme;
    }
    public void setTheme(String theme) {
        this.theme = theme;
    }
    public List<String> getModels() {
        return models;
    }
    public void setModels(List<String> models) {
        this.models = models;
    }
    public String getDefinition() {
        return definition;
    }
    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
