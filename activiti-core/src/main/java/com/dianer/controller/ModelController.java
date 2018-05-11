package com.dianer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelController {

    private static final Logger log = LoggerFactory.getLogger(ModelController.class);

    private static final String RESOLVE_ACTION_OVERWRITE = "overwrite";
    private static final String RESOLVE_ACTION_SAVE_AS = "saveAs";
    private static final String RESOLVE_ACTION_NEW_VERSION = "newVersion";

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    protected ObjectMapper objectMapper;

    protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    protected BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();

    /**
     * GET /rest/models/{modelId}/editor/json -> get the JSON model
     */
    @RequestMapping(value = "/model/{modelId}/json", method = RequestMethod.GET, produces = "application/json")
    public ObjectNode getModelJSON(@PathVariable String modelId) {
        Model model = repositoryService.getModel(modelId);
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put("modelId", model.getId());
        modelNode.put("name", model.getName());
        modelNode.put("key", model.getKey());
        return modelNode;
    }


//    protected ModelRepresentation createNewModel(String name, String description, Integer modelType, String editorJson) {
//        ModelRepresentation model = new ModelRepresentation();
//        model.setName(name);
//        model.setDescription(description);
//        model.setModelType(modelType);
//        Model newModel = modelService.createModel(model, editorJson, SecurityUtils.getCurrentUserObject());
//        return new ModelRepresentation(newModel);
//    }
}
