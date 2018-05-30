
package org.activiti.editor.controller;

import org.activiti.editor.exception.InternalServerErrorException;
import org.activiti.editor.model.ModelRepresentation;
import org.activiti.editor.model.ResultListDataRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/rest/import-process-model", method = RequestMethod.POST, produces = "application/json")
    public ModelRepresentation importProcessModel(HttpServletRequest request, @RequestParam("file") MultipartFile file) {

        ModelRepresentation modelRepresentation = super.importProcessModel(request, file);
        return modelRepresentation;
    }
    /*
     * specific endpoint for IE9 flash upload component
     */
    @RequestMapping(value = "/rest/import-process-model/text", method = RequestMethod.POST)
    public String importProcessModelText(HttpServletRequest request, @RequestParam("file") MultipartFile file) {

        ModelRepresentation modelRepresentation = super.importProcessModel(request, file);
        String modelRepresentationJson = null;
        try {
            modelRepresentationJson = objectMapper.writeValueAsString(modelRepresentation);
        } catch (Exception e) {
            logger.error("Error while processing Model representation json", e);
            throw new InternalServerErrorException("Model Representation could not be saved");
        }
        return modelRepresentationJson;
    }

    @RequestMapping(value = "/rest/models-for-app-definition", method = RequestMethod.GET, produces = "application/json")
    public ResultListDataRepresentation getModelsToIncludeInAppDefinition() {
        return null;
    }
}
