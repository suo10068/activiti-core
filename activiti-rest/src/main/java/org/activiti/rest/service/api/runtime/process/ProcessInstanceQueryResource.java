
package org.activiti.rest.service.api.runtime.process;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.rest.common.api.DataResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Frederik Heremans
 */
@RestController
public class ProcessInstanceQueryResource extends BaseProcessInstanceResource {

    @RequestMapping(value = "/query/process-instances", method = RequestMethod.POST, produces = "application/json")
    public DataResponse queryProcessInstances(@RequestBody ProcessInstanceQueryRequest queryRequest,
                                              @RequestParam Map<String, String> allRequestParams, HttpServletRequest request) {

        return getQueryResponse(queryRequest, allRequestParams);
    }
}
