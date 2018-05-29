
package org.activiti.repository;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.ModelQueryProperty;
import org.activiti.engine.query.QueryProperty;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.rest.common.api.DataResponse;
import org.activiti.rest.service.api.RestResponseFactory;
import org.activiti.rest.service.api.repository.ModelsPaginateList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-base.xml","classpath:spring-servlet.xml"})
public class BaseModelResourceTest {

    @Autowired
    protected RestResponseFactory restResponseFactory;

    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    Environment env;
    @Test
    public void getModelFromRequest() {
        String modelId = "50";
        Model model = repositoryService.createModelQuery().modelId(modelId).singleResult();
        if (model == null) {
            throw new ActivitiObjectNotFoundException("Could not find a model with id '" + modelId + "'.", ProcessDefinition.class);
        }
        System.out.println("=======");
    }
    @Test
    public void test(){

        Map<String, QueryProperty> allowedSortProperties = new HashMap<>();

        allowedSortProperties.put("id", ModelQueryProperty.MODEL_ID);
        allowedSortProperties.put("category", ModelQueryProperty.MODEL_CATEGORY);
        allowedSortProperties.put("createTime", ModelQueryProperty.MODEL_CREATE_TIME);
        allowedSortProperties.put("key", ModelQueryProperty.MODEL_KEY);
        allowedSortProperties.put("lastUpdateTime", ModelQueryProperty.MODEL_LAST_UPDATE_TIME);
        allowedSortProperties.put("name", ModelQueryProperty.MODEL_NAME);
        allowedSortProperties.put("version", ModelQueryProperty.MODEL_VERSION);
        allowedSortProperties.put("tenantId", ModelQueryProperty.MODEL_TENANT_ID);

        ModelQuery modelQuery = repositoryService.createModelQuery();
        Map<String, String> allRequestParams = new HashMap<>();
//        allRequestParams.put();
        try {
            DataResponse response = new ModelsPaginateList(restResponseFactory).paginateList(allRequestParams, modelQuery, "id", allowedSortProperties);
            System.out.println("======");
        }catch (Exception e){
            System.out.println("======");
        }
    }
}
