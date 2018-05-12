
package org.activiti.editor.controller;

import org.activiti.editor.model.ResultListDataRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/workflow")
public class ModelsResource extends AbstractModelsResource {

    private final Logger logger = LoggerFactory.getLogger(ModelsResource.class);

    @RequestMapping(value = "/rest/models", method = RequestMethod.GET, produces = "application/json")
    public ResultListDataRepresentation getModels(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort,
                                                  @RequestParam(required = false) Integer modelType, HttpServletRequest request) {

        return super.getModels(filter, sort, modelType, request);
    }

    @RequestMapping(value = "/rest/models-for-app-definition", method = RequestMethod.GET, produces = "application/json")
    public ResultListDataRepresentation getModelsToIncludeInAppDefinition() {
        return null;
    }
}
