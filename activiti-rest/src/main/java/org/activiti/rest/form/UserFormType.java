
package org.activiti.rest.form;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.form.AbstractFormType;
import org.activiti.engine.identity.User;


/**
 * Form type that holds an ID of a user.
 *
 * @author 'Frederik Heremans'
 */
public class UserFormType extends AbstractFormType {

    private static final long serialVersionUID = 1L;

    public static final String TYPE_NAME = "user";

    public String getName() {
        return TYPE_NAME;
    }

    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        // Check if user exists
        if (propertyValue != null) {
            // TODO: perhaps better wiring mechanism for service
            long count = ProcessEngines.getDefaultProcessEngine()
                    .getIdentityService()
                    .createUserQuery()
                    .userId(propertyValue).count();

            if (count == 0) {
                throw new ActivitiObjectNotFoundException("User " + propertyValue + " does not exist", User.class);
            }
            return propertyValue;
        }
        return null;
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        return (String) modelValue;
    }
}
