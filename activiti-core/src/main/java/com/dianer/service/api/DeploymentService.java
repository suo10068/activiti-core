package com.dianer.service.api;

import com.dianer.domain.editor.Model;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.springframework.transaction.annotation.Transactional;

public interface DeploymentService {

    @Transactional
    Deployment updateAppDefinition(Model appDefinition, User user);

    @Transactional
    void deleteAppDefinition(String appDefinitionId);

}