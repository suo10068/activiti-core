
package org.activiti.rest;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.rest.service.api.runtime.process.ExecutionBaseResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-base.xml","classpath:spring-servlet.xml"})
public class ExecutionBaseResourceTest {

    @Autowired
    protected RuntimeService runtimeService;

    @Test
    public void getModelFromRequest() {
        Execution execution = runtimeService.createExecutionQuery().executionId("45080").singleResult();
        System.out.println(JSON.toJSONString(execution));
    }
}
