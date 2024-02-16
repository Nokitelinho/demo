/*
 * BitbucketRepository.java Created on 23/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.ibsplc.neoicargo.relmgr.dao.ArtifactMasterRepository;
import com.ibsplc.neoicargo.relmgr.entity.ArtifactMaster;
import com.ibsplc.neoicargo.relmgr.entity.ReleaseCatalogue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author jens
 */
@Component
public class BitbucketRepository {

    static final Logger logger = LoggerFactory.getLogger(BitbucketRepository.class);

    private final String repositoryUrl;
    private final String user;
    private final String password;
    private final JacksonJsonProvider provider;
    private final ArtifactMasterRepository artifactMasterRepository;
    private RestApi restClient;

    @Autowired
    public BitbucketRepository(@Value("${bitbucket.repositoryUrl:undefined}") String repositoryUrl,
                               @Value("${bitbucket.user:undefined}") String user,
                               @Value("${bitbucket.password:undefined}") String password,
                               @Qualifier("jaxrsJsonProvider") JacksonJsonProvider provider,
                               ArtifactMasterRepository artifactMasterRepository) {
        this.repositoryUrl = repositoryUrl;
        this.user = user;
        this.password = password;
        this.provider = provider;
        this.restClient = createApiClient();
        this.artifactMasterRepository = artifactMasterRepository;
    }

    public void primeReleaseCatalogue(ReleaseCatalogue release) {
        Optional<ArtifactMaster> masterOptional = this.artifactMasterRepository.findById(release.getReleaseCataloguePk().getArtifactId());
        masterOptional.ifPresent(master -> populateCommitAttribs(release, master));
    }

    private void populateCommitAttribs(ReleaseCatalogue release, ArtifactMaster master) {
        if (master.getProjectRepositoryName() == null || master.getProjectName() == null) {
            logger.warn("ProjectRepositoryName/ProjectName is not configured for artifact {}", release.getReleaseCataloguePk().getArtifactId());
            return;
        }
        CommitInfo commitInfo = this.restClient.commits(master.getProjectName(), master.getProjectRepositoryName(), release.getBranch(), 1);
        Person committer = committer(commitInfo);
        String description = committer.getDisplayName() + " - " + commitInfo.getMessage();
        // column size
        if (description.length() > 250)
            description = description.substring(0, 250);
        release.setDescription(description);
        release.setCommitterEmail(committer.getEmailAddress());
        release.setActvtyRef(getActvyRef(commitInfo));
    }

    private String getActvyRef(CommitInfo commitInfo) {
    	if (null!= commitInfo.getProperties()) {
    		return Arrays.toString(commitInfo.getProperties().getJirakey());
    	}
    	return null;
    }
    private Person committer(CommitInfo info){
        if(info.getCommitter() != null)
            return info.getCommitter();
        return info.getAuthor();
    }

    protected RestApi createApiClient() {
        JAXRSClientFactoryBean factoryBean = new JAXRSClientFactoryBean();
        factoryBean.setProvider(provider);
        factoryBean.setServiceClass(RestApi.class);
        factoryBean.setThreadSafe(true);
        factoryBean.setAddress(repositoryUrl);
        factoryBean.setUsername(user);
        factoryBean.setPassword(password);
        return factoryBean.create(RestApi.class);
    }

    @Path("/rest/api/1.0")
    public interface RestApi {

        @GET
        @Path("/projects/{project}/repos/{repository}/commits/{branch}")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        CommitInfo commits(@PathParam("project") String project, @PathParam("repository") String repository, @PathParam("branch") String branch, @QueryParam("limit") int limit);

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CommitInfo {
        private String id;
        private String displayId;
        private Person author;
        private Person committer;
        private String message;
        private Properties properties;
    }

    
    
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Properties {
    	@JsonProperty("jira-key")
        private String[] jirakey;
    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Person {
        private String name;
        private String emailAddress;
        private String displayName;
        private String type;
    }
}
