
package org.activiti.rest.service.api.repository.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.repository.Model;
import org.activiti.rest.service.api.repository.resource.BaseModelSourceResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * @author Frederik Heremans
 */
@RestController
@RequestMapping(value = "/workflow/repository/models/{modelId}/source-extra")
public class ModelSourceExtraResource extends BaseModelSourceResource {

    @RequestMapping(value = "", method = RequestMethod.GET)
    protected @ResponseBody byte[] getModelBytes(@PathVariable String modelId, HttpServletResponse response) {
        byte[] editorSource = repositoryService.getModelEditorSourceExtra(modelId);
        if (editorSource == null) {
            throw new ActivitiObjectNotFoundException("Model with id '" + modelId + "' does not have extra source available.", String.class);
        }
        response.setContentType("application/octet-stream");
        return editorSource;
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    protected void setModelSource(@PathVariable String modelId, HttpServletRequest request, HttpServletResponse response) {
        Model model = getModelFromRequest(modelId);
        if (model != null) {
            try {

                if (request instanceof MultipartHttpServletRequest == false) {
                    throw new ActivitiIllegalArgumentException("Multipart request is required");
                }

                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

                if (multipartRequest.getFileMap().size() == 0) {
                    throw new ActivitiIllegalArgumentException("Multipart request with file content is required");
                }

                MultipartFile file = multipartRequest.getFileMap().values().iterator().next();

                repositoryService.addModelEditorSourceExtra(model.getId(), file.getBytes());
                response.setStatus(HttpStatus.NO_CONTENT.value());

            } catch (Exception e) {
                throw new ActivitiException("Error adding model editor source extra", e);
            }
        }
    }

}
