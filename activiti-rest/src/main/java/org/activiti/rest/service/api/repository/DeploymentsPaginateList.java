
package org.activiti.rest.service.api.repository;

import java.util.List;

import org.activiti.rest.common.api.AbstractPaginateList;
import org.activiti.rest.service.api.RestResponseFactory;

/**
 * @author Tijs Rademakers
 */
public class DeploymentsPaginateList extends AbstractPaginateList {

    protected RestResponseFactory restResponseFactory;

    public DeploymentsPaginateList(RestResponseFactory restResponseFactory) {
        this.restResponseFactory = restResponseFactory;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    protected List processList(List list) {
        return restResponseFactory.createDeploymentResponseList(list);
    }
}
