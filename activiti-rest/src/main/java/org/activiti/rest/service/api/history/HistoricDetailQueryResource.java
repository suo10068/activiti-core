
package org.activiti.rest.service.api.history;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.rest.common.api.DataResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Tijs Rademakers
 */
@RestController
public class HistoricDetailQueryResource extends HistoricDetailBaseResource {

    @RequestMapping(value = "/query/historic-detail", method = RequestMethod.POST, produces = "application/json")
    public DataResponse queryHistoricDetail(@RequestBody HistoricDetailQueryRequest queryRequest,
                                            @RequestParam Map<String, String> allRequestParams, HttpServletRequest request) {

        return getQueryResponse(queryRequest, allRequestParams);
    }
}
