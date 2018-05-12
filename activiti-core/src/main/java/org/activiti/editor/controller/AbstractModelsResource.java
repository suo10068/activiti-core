package org.activiti.editor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.editor.model.ResultListDataRepresentation;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.List;

public class AbstractModelsResource {

    protected static final String FILTER_SHARED_WITH_ME = "sharedWithMe";
    protected static final String FILTER_SHARED_WITH_OTHERS = "sharedWithOthers";
    protected static final String FILTER_FAVORITE = "favorite";

    protected static final String SORT_NAME_ASC = "nameAsc";
    protected static final String SORT_NAME_DESC = "nameDesc";
    protected static final String SORT_MODIFIED_ASC = "modifiedAsc";
    protected static final int MIN_FILTER_LENGTH = 1;

    private final Logger logger = LoggerFactory.getLogger(AbstractModelsResource.class);


    @Inject
    protected ObjectMapper objectMapper;

    protected BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
    protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    public ResultListDataRepresentation getModels(String filter, String sort, Integer modelType, HttpServletRequest request) {



        ResultListDataRepresentation result = new ResultListDataRepresentation();
        return result;
    }



    protected String makeValidFilterText(String filterText) {
        String validFilter = null;

        if (filterText != null) {
            String trimmed = StringUtils.trim(filterText);
            if (trimmed.length() >= MIN_FILTER_LENGTH) {
                validFilter = "%" + trimmed.toLowerCase() + "%";
            }
        }
        return validFilter;
    }

    protected Sort getSort(String sort, boolean prefixWithProcessModel) {
        String propName;
        Direction direction;
        if (SORT_NAME_ASC.equals(sort)) {
            if (prefixWithProcessModel) {
                propName = "model.name";
            } else {
                propName = "name";
            }
            direction = Direction.ASC;
        } else if (SORT_NAME_DESC.equals(sort)) {
            if (prefixWithProcessModel) {
                propName = "model.name";
            } else {
                propName = "name";
            }
            direction = Direction.DESC;
        } else if (SORT_MODIFIED_ASC.equals(sort)) {
            if (prefixWithProcessModel) {
                propName = "model.lastUpdated";
            } else {
                propName = "lastUpdated";
            }
            direction = Direction.ASC;
        } else {
            // Default sorting
            if (prefixWithProcessModel) {
                propName = "model.lastUpdated";
            } else {
                propName = "lastUpdated";
            }
            direction = Direction.DESC;
        }
        return new Sort(direction, propName);
    }

}
