
package org.activiti.rest.service.api.repository.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Model;
import org.activiti.rest.service.api.repository.request.ModelRequest;
import org.activiti.rest.service.api.repository.response.ModelResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Frederik Heremans
 */
@RestController
@RequestMapping(value = "/workflow/repository/models/{modelId}")
public class ModelResource extends BaseModelResource {

    private static Logger logger = LoggerFactory.getLogger(ModelResource.class);

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public ModelResponse getModel(@PathVariable String modelId, HttpServletRequest request) {
        Model model = getModelFromRequest(modelId);
        ModelResponse response = restResponseFactory.createModelResponse(model);
        return response;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    public ModelResponse updateModel(@PathVariable String modelId, @RequestBody ModelRequest modelRequest, HttpServletRequest request) {
        Model model = getModelFromRequest(modelId);

        if (modelRequest.isCategoryChanged()) {
            model.setCategory(modelRequest.getCategory());
        }
        if (modelRequest.isDeploymentChanged()) {
            model.setDeploymentId(modelRequest.getDeploymentId());
        }
        if (modelRequest.isKeyChanged()) {
            model.setKey(modelRequest.getKey());
        }
        if (modelRequest.isMetaInfoChanged()) {
            model.setMetaInfo(modelRequest.getMetaInfo());
        }
        if (modelRequest.isNameChanged()) {
            model.setName(modelRequest.getName());
        }
        if (modelRequest.isVersionChanged()) {
            model.setVersion(modelRequest.getVersion());
        }
        if (modelRequest.isTenantIdChanged()) {
            model.setTenantId(modelRequest.getTenantId());
        }

        repositoryService.saveModel(model);
        return restResponseFactory.createModelResponse(model);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public void deleteModel(@PathVariable String modelId, HttpServletResponse response) {
        Model model = getModelFromRequest(modelId);
        repositoryService.deleteModel(model.getId());
        response.setStatus(HttpStatus.NO_CONTENT.value());
    }
}
