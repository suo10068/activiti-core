package org.activiti.editor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.editor.exception.BadRequestException;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.activiti.editor.model.AbstractModel;
import org.activiti.editor.model.ModelRepresentation;
import org.activiti.editor.model.ResultListDataRepresentation;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import org.activiti.editor.constants.ModelDataJsonConstants;

public class AbstractModelsResource {

    protected static final String FILTER_SHARED_WITH_ME = "sharedWithMe";
    protected static final String FILTER_SHARED_WITH_OTHERS = "sharedWithOthers";
    protected static final String FILTER_FAVORITE = "favorite";

    protected static final String SORT_NAME_ASC = "nameAsc";
    protected static final String SORT_NAME_DESC = "nameDesc";
    protected static final String SORT_MODIFIED_ASC = "modifiedAsc";
    protected static final int MIN_FILTER_LENGTH = 1;

    String MODEL_ID = "modelId";
    String MODEL_NAME = "name";
    String MODEL_REVISION = "revision";
    String MODEL_DESCRIPTION = "description";

    private final Logger logger = LoggerFactory.getLogger(AbstractModelsResource.class);

    @Autowired
    RepositoryService repositoryService;

    @Inject
    protected ObjectMapper objectMapper;

    protected BpmnXMLConverter bpmnXmlConverter = new BpmnXMLConverter();
    protected BpmnJsonConverter bpmnJsonConverter = new BpmnJsonConverter();

    public ResultListDataRepresentation getModels(String filter, String sort, Integer modelType, HttpServletRequest request) {
        ResultListDataRepresentation result = new ResultListDataRepresentation();
        return result;
    }

    public ModelRepresentation importProcessModel(HttpServletRequest request, MultipartFile file) {

        String fileName = file.getOriginalFilename();
        if (fileName != null && (fileName.endsWith(".bpmn") || fileName.endsWith(".bpmn20.xml"))) {
            try {
                XMLInputFactory xif = XmlUtil.createSafeXmlInputFactory();
                InputStreamReader xmlIn = new InputStreamReader(file.getInputStream(), "UTF-8");
                XMLStreamReader xtr = xif.createXMLStreamReader(xmlIn);
                BpmnModel bpmnModel = bpmnXmlConverter.convertToBpmnModel(xtr);
                if (CollectionUtils.isEmpty(bpmnModel.getProcesses())) {
                    throw new BadRequestException("No process found in definition " + fileName);
                }

                // TODO 需查询这是干嘛的？？？
                if (bpmnModel.getLocationMap().size() == 0) {
                    BpmnAutoLayout bpmnLayout = new BpmnAutoLayout(bpmnModel);
                    bpmnLayout.execute();
                }

                ObjectNode modelNode = bpmnJsonConverter.convertToJson(bpmnModel);

                Process process = bpmnModel.getMainProcess();
                String processName = process.getId();
                if (StringUtils.isNotEmpty(process.getName())) {
                    processName = process.getName();
                }
                String description = process.getDocumentation();

                ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
                modelObjectNode.put(MODEL_NAME, processName);
                modelObjectNode.put(MODEL_REVISION, 1);
                modelObjectNode.put(MODEL_DESCRIPTION, description);

                Model model = repositoryService.newModel();
                model.setKey(process.getId());
                model.setName(processName);
                model.setMetaInfo(modelObjectNode.toString());
                repositoryService.saveModel(model);

                BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
                ObjectNode editorNode = jsonConverter.convertToJson(bpmnModel);

                repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));

                ModelRepresentation modelRepresentation = new ModelRepresentation();
                modelRepresentation.setId(model.getId());
                modelRepresentation.setKey(process.getId());
                modelRepresentation.setName(processName);
                modelRepresentation.setDescription(description);
                modelRepresentation.setModelType(AbstractModel.MODEL_TYPE_BPMN);
                return modelRepresentation;

            } catch (BadRequestException e) {
                throw e;
            } catch (Exception e) {
                logger.error("Import failed for " + fileName, e);
                throw new BadRequestException("Import failed for " + fileName + ", error message " + e.getMessage());
            }
        } else {
            throw new BadRequestException("Invalid file name, only .bpmn and .bpmn20.xml files are supported not " + fileName);
        }
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
