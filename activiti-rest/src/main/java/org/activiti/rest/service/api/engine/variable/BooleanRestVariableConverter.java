
package org.activiti.rest.service.api.engine.variable;

import org.activiti.engine.ActivitiIllegalArgumentException;


/**
 * @author Frederik Heremans
 */
public class BooleanRestVariableConverter implements RestVariableConverter {

    @Override
    public String getRestTypeName() {
        return "boolean";
    }

    @Override
    public Class<?> getVariableType() {
        return Boolean.class;
    }

    @Override
    public Object getVariableValue(RestVariable result) {
        if (result.getValue() != null) {
            if (!(result.getValue() instanceof Boolean)) {
                throw new ActivitiIllegalArgumentException("Converter can only convert booleans");
            }
            return result.getValue();
        }
        return null;
    }

    @Override
    public void convertVariableValue(Object variableValue, RestVariable result) {
        if (variableValue != null) {
            if (!(variableValue instanceof Boolean)) {
                throw new ActivitiIllegalArgumentException("Converter can only convert booleans");
            }
            result.setValue(variableValue);
        } else {
            result.setValue(null);
        }
    }

}
