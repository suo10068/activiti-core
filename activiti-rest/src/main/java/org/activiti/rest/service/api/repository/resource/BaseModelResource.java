
package org.activiti.rest.service.api.repository.resource;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.rest.service.api.RestResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Frederik Heremans
 */
public class BaseModelResource {

    @Autowired
    protected RestResponseFactory restResponseFactory;

    @Autowired
    protected RepositoryService repositoryService;

    /**
     * Returns the {@link Model} that is requested. Throws the right exceptions
     * when bad request was made or model is not found.
     */
    protected Model getModelFromRequest(String modelId) {
        Model model = repositoryService.createModelQuery().modelId(modelId).singleResult();

        if (model == null) {
            throw new ActivitiObjectNotFoundException("Could not find a model with id '" + modelId + "'.", ProcessDefinition.class);
        }
        return model;
    }
}
