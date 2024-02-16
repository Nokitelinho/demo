package com.ibsplc.neoicargo.datamonitor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.ibsplc.neoicargo.datamonitor.exception.ProjectsNotEnabledException;
import com.ibsplc.neoicargo.datamonitor.exception.SchedulingConfNotFoundException;
import com.ibsplc.neoicargo.datamonitor.jdbc.NativeRepository;
import com.ibsplc.neoicargo.datamonitor.model.DataMonitorConfig;
import com.ibsplc.neoicargo.datamonitor.model.DataMonitorConfigList;
import com.ibsplc.neoicargo.datamonitor.model.ScheduleConfig;
import com.ibsplc.neoicargo.datamonitor.notify.NotificationService;
import com.ibsplc.neoicargo.datamonitor.notify.WebhookNotificationListener;
import com.ibsplc.neoicargo.datamonitor.scheduler.DataMonitorAbstractTask;
import com.ibsplc.neoicargo.datamonitor.scheduler.DataMonitorGenericTask;
import com.ibsplc.neoicargo.framework.core.lang.notation.apis.PrivateAPI;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableScheduling
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Service
@PrivateAPI
@Slf4j
public class DataMonitorController {

    private static final long SEC_TO_MILLI = 1000;
    private static final String STRING_TOKENIZER = ",";

    @Value("${spring.cloud.config.uri}")
    private String configServerUrl;

    @Value("${datamonitor.projects.enabled}")
    private String applications;


    @Autowired
    private NotificationService notificationService;

    @Autowired
    private DataMonitorConfigList dataMonitorConfigList;

    @Autowired
    private NativeRepository repository;

    @Autowired
    private ContextUtil tenant;

    @Autowired
    private ConfigServerReader configReader;

    @Autowired
    private ConfigurableEnvironment confEnv;

    private TaskScheduler scheduler = new ThreadPoolTaskScheduler();

    private static int i = 0;

    @PostConstruct
    public void init() {

        String profile = confEnv.getProperty("spring.profiles.active");
        log.info("Environment variable 'spring.profiles.active' is {}", profile);
        log.info("Config server url is {}", configServerUrl);
        String myApp = confEnv.getProperty("spring.application.name");
        log.info("THIS application is 'spring.application.name' is {}", myApp);
        Object enabledProjects = configReader.getConfig(myApp, profile, "datamonitor.projects.enabled");
        if (enabledProjects == null || StringUtils.isEmpty(enabledProjects.toString())) {
            log.error("No projects are enabled to monitor data... ");
            throw new ProjectsNotEnabledException(profile, tenant.getTenant());
        }

        List<String> applicationList = Arrays.asList(applications.split(STRING_TOKENIZER));
        //List<String> applicationList = Arrays.asList(enabledProjects.toString().split(STRING_TOKENIZER));
        log.info("Instantiating instance {} with tenant {}", ++i, tenant.getTenant());

        for (String url : dataMonitorConfigList.getWebhookUrls()) {
            log.info("Creating notification Listener with url {}", url);
            notificationService.addNotificationListener(new WebhookNotificationListener(url));
        }

        //dataMonitorConfigList.setMonitorMap(new HashMap<String, DataMonitorConfig>());

        DataMonitorConfigList appDataMonitorList;

        for (String application : applicationList) {
            application = application.trim();
            if (StringUtils.isEmpty(application)) {
                continue;
            }
            //repository.addJdbcTemplate(application.trim(), configMap);
            repository.addJdbcTemplate(application, profile);
            appDataMonitorList = (DataMonitorConfigList) configReader.getBean(application, profile, DataMonitorConfigList.class);
            if (appDataMonitorList == null || appDataMonitorList.getMonitorMap() == null) {
                log.warn("Datamonitor Configurations not found for {} in environment {} ", application, profile);
                continue;
            }
            log.info("Application {}, Datamonitorlist {}", application, appDataMonitorList);
            int size = appDataMonitorList.getMonitorMap().keySet().size();
            log.info("size of the monitorMap for {}  is {}", application, size);
            log.info("monitors are {}  ", appDataMonitorList.getMonitorMap().keySet());
            scheduler = threadPoolTaskScheduler(size);
            for (String key : appDataMonitorList.getMonitorMap().keySet()) {
                DataMonitorConfig con = appDataMonitorList.getMonitorMap().get(key);
                con.setId(key);
                if (con.isDisabled()) {
                    continue;
                }
                /*
                 * If the enabled-applications does not contain the module, then skip that monitor config
                 */
                if (!applicationList.contains(con.getModule())) {
                    continue;
                }
                if (con.isCqrs()) {
                    repository.addCqrsJdbcTemplate(application, profile); // duplication is handled in addCqrsJdbcTemplate method.
                }
                DataMonitorGenericTask task = new DataMonitorGenericTask(notificationService, con, repository);
                schedule(con, task);
            }
        }
        configReader.closeAllContexts();
    }


    private ThreadPoolTaskScheduler threadPoolTaskScheduler(int size) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(size);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }

    private void schedule(DataMonitorConfig conf, DataMonitorAbstractTask task) {
        ScheduleConfig sc = conf.getScheduler();
        if (sc == null) {
            throw new SchedulingConfNotFoundException(conf.getId());
        }
        if (sc.getFixedDelayInSeconds() != 0) {
            scheduler.scheduleWithFixedDelay(task, sc.getFixedDelayInSeconds() * SEC_TO_MILLI);
        } else if (sc.getFixedFrequencyInSeconds() != 0) {
            scheduler.scheduleAtFixedRate(task, sc.getFixedFrequencyInSeconds() * SEC_TO_MILLI);
        } else if (StringUtils.isNotEmpty(sc.getCronExpression())) {
            scheduler.schedule(task, new CronTrigger(sc.getCronExpression()));
        } else {
            throw new SchedulingConfNotFoundException(conf.getId());
        }
    }


    @Path("/health")
    public void health() throws IOException {
    }

}
