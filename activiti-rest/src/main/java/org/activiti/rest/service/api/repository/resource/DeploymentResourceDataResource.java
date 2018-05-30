
package org.activiti.rest.service.api.repository.resource;

import javax.servlet.http.HttpServletResponse;

import org.activiti.rest.service.api.repository.resource.BaseDeploymentResourceDataResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Frederik Heremans
 */
@RestController
public class DeploymentResourceDataResource extends BaseDeploymentResourceDataResource {

    @RequestMapping(value = "/repository/deployments/{deploymentId}/resourcedata/{resourceId}", method = RequestMethod.GET)
    public @ResponseBody
    byte[] getDeploymentResource(@PathVariable("deploymentId") String deploymentId,
                                 @PathVariable("resourceId") String resourceId, HttpServletResponse response) {

        return getDeploymentResourceData(deploymentId, resourceId, response);
    }
}
