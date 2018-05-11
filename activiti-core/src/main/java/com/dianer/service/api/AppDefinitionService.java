package com.dianer.service.api;

import org.activiti.engine.identity.User;

import java.util.List;

public interface AppDefinitionService {

	List<AppDefinitionServiceRepresentation> getAppDefinitions();
	
	List<AppDefinitionServiceRepresentation> getDeployableAppDefinitions(User user);
	
	String getDefinitionIdForModelAndUser(String modelId, User user);

}