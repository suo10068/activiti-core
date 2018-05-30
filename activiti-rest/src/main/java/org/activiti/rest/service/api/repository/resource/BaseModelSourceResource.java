
package org.activiti.rest.service.api.repository.resource;

import org.activiti.engine.ActivitiException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Tijs Rademakers
 */
public abstract class BaseModelSourceResource extends BaseModelResource {

    public byte[] getFileBytes(MultipartFile file) {
        byte[] byteArray = null;
        try {
            byteArray = file.getBytes();
        } catch (Exception e) {
            throw new ActivitiException("Error getting file bytes", e);
        }
        return byteArray;
    }
}
