package com.dianer.service.api;

import com.dianer.domain.editor.AbstractModel;
import com.dianer.domain.editor.Model;
import com.dianer.domain.editor.ModelHistory;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.identity.User;

import java.util.List;
import java.util.Map;

public interface ModelService {

    Model getModel(String modelId);

    List<AbstractModel> getModelsByModelType(Integer modelType);

    ModelHistory getModelHistory(String modelId, String modelHistoryId);

    Long getModelCountForUser(User user, int modelTypeApp);

    BpmnModel getBpmnModel(AbstractModel model);

    byte[] getBpmnXML(BpmnModel bpmnMode);

    byte[] getBpmnXML(AbstractModel model);

    BpmnModel getBpmnModel(AbstractModel model, Map<String, Model> formMap, Map<String, Model> decisionTableMap);

    Model createModel(Model newModel, User createdBy);

    Model saveModel(Model modelObject);

    Model saveModel(Model modelObject, String editorJson, byte[] imageBytes, boolean newVersion, String newVersionComment, User updatedBy);

    Model saveModel(String modelId, String name, String key, String description, String editorJson,
                    boolean newVersion, String newVersionComment, User updatedBy);

    Model createNewModelVersion(Model modelObject, String comment, User updatedBy);

    ModelHistory createNewModelVersionAndReturnModelHistory(Model modelObject, String comment, User updatedBy);

    void deleteModel(String modelId, boolean cascadeHistory, boolean deleteRuntimeApp);

}
