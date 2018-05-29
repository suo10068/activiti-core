
package org.activiti.rest.service.api.repository.request;

import java.util.Date;

import org.activiti.rest.service.api.RestActionRequest;


/**
 * @author Frederik Heremans
 */
public class ProcessDefinitionActionRequest extends RestActionRequest {

    public static final String ACTION_SUSPEND = "suspend";
    public static final String ACTION_ACTIVATE = "activate";

    private boolean includeProcessInstances = false;
    private Date date;
    private String category;

    public void setIncludeProcessInstances(boolean includeProcessInstances) {
        this.includeProcessInstances = includeProcessInstances;
    }

    public boolean isIncludeProcessInstances() {
        return includeProcessInstances;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
