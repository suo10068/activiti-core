package org.activiti.conf;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.activiti.engine.*;
import org.activiti.engine.form.AbstractFormType;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.rest.form.MonthFormType;
import org.activiti.rest.form.ProcessDefinitionFormType;
import org.activiti.rest.form.UserFormType;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.ArrayList;
import java.util.List;

//@Configuration
//@PropertySources({
//        @PropertySource(value = "classpath:/properties/activiti.properties", ignoreResourceNotFound = true)
//})
//@ComponentScan(basePackages = { "org.activiti" })
//@ImportResource({"classpath:activiti-ui-context.xml",
//        "classpath:activiti-login-context.xml",
//        "classpath:activiti-custom-context.xml"})
public class ActivitiEngineConfiguration {

    private final Logger log = LoggerFactory.getLogger(ActivitiEngineConfiguration.class);

    @Autowired
    protected Environment env;

    @Autowired
    protected ComboPooledDataSource dataSource;
    @Autowired
    protected DataSourceTransactionManager transactionManager;


    @Bean(name = "processEngineFactoryBean")
    public ProcessEngineFactoryBean processEngineFactoryBean() {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
        return factoryBean;
    }

    @Bean(name = "processEngine")
    public ProcessEngine processEngine() {
        // Safe to call the getObject() on the @Bean annotated processEngineFactoryBean(), will be
        // the fully initialized object instanced from the factory and will NOT be created more than once
        try {
            return processEngineFactoryBean().getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "processEngineConfiguration")
    public ProcessEngineConfigurationImpl processEngineConfiguration() {
        SpringProcessEngineConfiguration processEngineConfiguration = new SpringProcessEngineConfiguration();
        processEngineConfiguration.setDataSource(dataSource);
        processEngineConfiguration.setDatabaseSchemaUpdate(env.getProperty("engine.schema.update", "true"));
        processEngineConfiguration.setTransactionManager(transactionManager);
        processEngineConfiguration.setJobExecutorActivate(Boolean.valueOf(env.getProperty("engine.activate.jobexecutor", "false")));
        processEngineConfiguration.setAsyncExecutorEnabled(Boolean.valueOf(env.getProperty("engine.asyncexecutor.enabled", "true")));
        processEngineConfiguration.setAsyncExecutorActivate(Boolean.valueOf(env.getProperty("engine.asyncexecutor.activate", "true")));
        processEngineConfiguration.setHistory(env.getProperty("engine.history.level", "full"));

        String mailEnabled = env.getProperty("engine.email.enabled");
        if ("true".equals(mailEnabled)) {
            processEngineConfiguration.setMailServerHost(env.getProperty("engine.email.host"));
            int emailPort = 1025;
            String emailPortProperty = env.getProperty("engine.email.port");
            if (StringUtils.isNotEmpty(emailPortProperty)) {
                emailPort = Integer.valueOf(emailPortProperty);
            }
            processEngineConfiguration.setMailServerPort(emailPort);
            String emailUsernameProperty = env.getProperty("engine.email.username");
            if (StringUtils.isNotEmpty(emailUsernameProperty)) {
                processEngineConfiguration.setMailServerUsername(emailUsernameProperty);
            }

            String emailPasswordProperty = env.getProperty("engine.email.password");
            if (StringUtils.isNotEmpty(emailPasswordProperty)) {
                processEngineConfiguration.setMailServerPassword(emailPasswordProperty);
            }
        }

        List<AbstractFormType> formTypes = new ArrayList<>();
        formTypes.add(new UserFormType());
        formTypes.add(new ProcessDefinitionFormType());
        formTypes.add(new MonthFormType());
        processEngineConfiguration.setCustomFormTypes(formTypes);

        return processEngineConfiguration;
    }

    @Bean
    public RepositoryService repositoryService() {
        return processEngine().getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService() {
        return processEngine().getRuntimeService();
    }

    @Bean
    public TaskService taskService() {
        return processEngine().getTaskService();
    }

    @Bean
    public HistoryService historyService() {
        return processEngine().getHistoryService();
    }

    @Bean
    public FormService formService() {
        return processEngine().getFormService();
    }

    @Bean
    public IdentityService identityService() {
        return processEngine().getIdentityService();
    }

    @Bean
    public ManagementService managementService() {
        return processEngine().getManagementService();
    }
}
