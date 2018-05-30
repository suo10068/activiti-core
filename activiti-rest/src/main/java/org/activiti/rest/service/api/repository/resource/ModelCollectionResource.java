
package org.activiti.rest.service.api.repository.resource;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.impl.ModelQueryProperty;
import org.activiti.engine.query.QueryProperty;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.repository.request.ModelRequest;
import org.activiti.rest.service.api.repository.response.ModelResponse;
import org.activiti.rest.service.api.repository.ModelsPaginateList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Frederik Heremans
 */
@RestController
@RequestMapping(value = "/workflow")
public class ModelCollectionResource extends BaseModelResource {

    private Logger logger = LoggerFactory.getLogger(ModelCollectionResource.class);

    private static Map<String, QueryProperty> allowedSortProperties = new HashMap<String, QueryProperty>();

    static {
        allowedSortProperties.put("id", ModelQueryProperty.MODEL_ID);
        allowedSortProperties.put("category", ModelQueryProperty.MODEL_CATEGORY);
        allowedSortProperties.put("createTime", ModelQueryProperty.MODEL_CREATE_TIME);
        allowedSortProperties.put("key", ModelQueryProperty.MODEL_KEY);
        allowedSortProperties.put("lastUpdateTime", ModelQueryProperty.MODEL_LAST_UPDATE_TIME);
        allowedSortProperties.put("name", ModelQueryProperty.MODEL_NAME);
        allowedSortProperties.put("version", ModelQueryProperty.MODEL_VERSION);
        allowedSortProperties.put("tenantId", ModelQueryProperty.MODEL_TENANT_ID);
    }

    @RequestMapping(value = "/repository/models", method = RequestMethod.GET, produces = "application/json")
    public DataResponse getModels(@RequestParam Map<String, String> allRequestParams, HttpServletRequest request) {
        ModelQuery modelQuery = repositoryService.createModelQuery();

        if (allRequestParams.containsKey("id")) {
            modelQuery.modelId(allRequestParams.get("id"));
        }
        if (allRequestParams.containsKey("category")) {
            modelQuery.modelCategory(allRequestParams.get("category"));
        }
        if (allRequestParams.containsKey("categoryLike")) {
            modelQuery.modelCategoryLike(allRequestParams.get("categoryLike"));
        }
        if (allRequestParams.containsKey("categoryNotEquals")) {
            modelQuery.modelCategoryNotEquals(allRequestParams.get("categoryNotEquals"));
        }
        if (allRequestParams.containsKey("name")) {
            modelQuery.modelName(allRequestParams.get("name"));
        }
        if (allRequestParams.containsKey("nameLike")) {
            modelQuery.modelNameLike(allRequestParams.get("nameLike"));
        }
        if (allRequestParams.containsKey("key")) {
            modelQuery.modelKey(allRequestParams.get("key"));
        }
        if (allRequestParams.containsKey("version")) {
            modelQuery.modelVersion(Integer.valueOf(allRequestParams.get("version")));
        }
        if (allRequestParams.containsKey("latestVersion")) {
            boolean isLatestVersion = Boolean.valueOf(allRequestParams.get("latestVersion"));
            if (isLatestVersion) {
                modelQuery.latestVersion();
            }
        }
        if (allRequestParams.containsKey("deploymentId")) {
            modelQuery.deploymentId(allRequestParams.get("deploymentId"));
        }
        if (allRequestParams.containsKey("deployed")) {
            boolean isDeployed = Boolean.valueOf(allRequestParams.get("deployed"));
            if (isDeployed) {
                modelQuery.deployed();
            } else {
                modelQuery.notDeployed();
            }
        }
        if (allRequestParams.containsKey("tenantId")) {
            modelQuery.modelTenantId(allRequestParams.get("tenantId"));
        }
        if (allRequestParams.containsKey("tenantIdLike")) {
            modelQuery.modelTenantIdLike(allRequestParams.get("tenantIdLike"));
        }
        if (allRequestParams.containsKey("withoutTenantId")) {
            boolean withoutTenantId = Boolean.valueOf(allRequestParams.get("withoutTenantId"));
            if (withoutTenantId) {
                modelQuery.modelWithoutTenantId();
            }
        }
        return new ModelsPaginateList(restResponseFactory).paginateList(allRequestParams, modelQuery, "id", allowedSortProperties);
    }

    /**
     * 创建model
     * @param modelRequest
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/repository/models", method = RequestMethod.POST, produces = "application/json")
    public ModelResponse createModel(@RequestBody ModelRequest modelRequest, HttpServletRequest request,
                                     HttpServletResponse response) {

        logger.info("创建流程模型："+ JSON.toJSONString(modelRequest));
        Model model = repositoryService.newModel();
        model.setCategory(modelRequest.getCategory());
        model.setDeploymentId(modelRequest.getDeploymentId());
        model.setKey(modelRequest.getKey());
        model.setMetaInfo(modelRequest.getMetaInfo());
        model.setName(modelRequest.getName());
        model.setVersion(modelRequest.getVersion());
        model.setTenantId(modelRequest.getTenantId());

        repositoryService.saveModel(model);
        response.setStatus(HttpStatus.CREATED.value());
        return restResponseFactory.createModelResponse(model);
    }
}
