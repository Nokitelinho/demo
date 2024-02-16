/*
 * ReleaseMangerSqlDao.java Created on 04/10/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.relmgr.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ibsplc.neoicargo.relmgr.QueryDefinition;
import com.ibsplc.neoicargo.relmgr.ReleaseManagerController;
import com.ibsplc.neoicargo.relmgr.entity.BuildCatalogue;
import com.ibsplc.neoicargo.relmgr.model.ActiveTask;
import com.ibsplc.neoicargo.relmgr.model.Application;
import com.ibsplc.neoicargo.relmgr.model.Artifact;
import com.ibsplc.neoicargo.relmgr.model.Build;
import com.ibsplc.neoicargo.relmgr.model.DeploymentUnit;
import com.ibsplc.neoicargo.relmgr.model.JiraIdResponse;
import com.ibsplc.neoicargo.relmgr.model.ReleaseTime;
import com.ibsplc.neoicargo.relmgr.model.ServiceTechnology;
import com.ibsplc.neoicargo.relmgr.model.ServiceType;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jens
 */
@Component
@Transactional
@Slf4j
public class ReleaseManagerSqlDao {

	static final Logger logger = LoggerFactory.getLogger(ReleaseManagerSqlDao.class);

	private final JdbcTemplate jdbcTemplate;
	private final QueryDefinition queryDefinition;

	@Autowired
	public ReleaseManagerSqlDao(JdbcTemplate template, QueryDefinition queryDefinition) {
		this.jdbcTemplate = template;
		this.queryDefinition = queryDefinition;
	}

	public List<Artifact> findTenantCatalogue(String tenantId) {
		PreparedStatementSetter pss = ps -> ps.setString(1, tenantId);
		final String query = queryDefinition.getDefinitions().get("findTenantCatalogue");
		List<Artifact> artifacts = jdbcTemplate.query(query, pss, this::mapArtifact);
		return artifacts;
	}

	public List<Artifact> findTenantCatalogueForApplication(String tenantId, String applicationId) {
		PreparedStatementSetter pss = ps -> {
			ps.setString(1, tenantId);
			ps.setString(2, applicationId);
		};
		final String query = queryDefinition.getDefinitions().get("findTenantCatalogueForApplication");
		List<Artifact> artifacts = jdbcTemplate.query(query, pss, this::mapArtifact);
		return artifacts;
	}

	public List<DeploymentUnit> findTenantDeploymentBom(String tenantId, String applicationId, String branch) {
		PreparedStatementSetter pss = ps -> {
			int index = 0;
			ps.setString(++index, branch == null ? ReleaseManagerController.MASTER_BRANCH : branch);
			ps.setString(++index, tenantId);
			ps.setString(++index, applicationId);
		};
		final String query = queryDefinition.getDefinitions().get("findTenantDeploymentBom");
		List<DeploymentUnit> answer = jdbcTemplate.query(query, pss, this::mapDeploymentUnit);
		return answer;
	}

	public List<ActiveTask> findAggregatedJiraId(String tenantId, String applicationId) {
		Timestamp timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toLocalDateTime());
		PreparedStatementSetter pss = ps -> {
			ps.setString(1, tenantId);
			ps.setString(2, applicationId);
			ps.setTimestamp(3, timestamp);
		};
		final String query = queryDefinition.getDefinitions().get("findAggregatedActivity");
		List<ActiveTask> answer = jdbcTemplate.query(query, pss, this::mapActiveTask);
		return answer;
	}
	public List<String> findAggregatedJiraIdWithVersion(String tenantId, String applicationId, Pair<String,Integer> currentVersion, Pair<String,Integer> newVersion) {
		if(currentVersion.getFirst().equals(newVersion.getFirst())) {
			// Means the release numbers are same hence direct comparison of build numbers are enough...
			PreparedStatementSetter pss = ps -> {
				ps.setString(1,applicationId );
				ps.setString(2, tenantId);
				ps.setInt(3, currentVersion.getSecond()+1);
				ps.setInt(4, newVersion.getSecond());
				ps.setString(5, newVersion.getFirst());
			};
			final String query = queryDefinition.getDefinitions().get("findAggregatedActivityJIRA");
			List<String> answer = jdbcTemplate.query(query, pss, this::mapActiveJira);
			return answer;
		}else {
			// Means need to consider JIRA ids across two release versions
			PreparedStatementSetter pss = ps -> {
				ps.setString(1,applicationId );
				ps.setString(2, tenantId);
				ps.setInt(3, currentVersion.getSecond());
				ps.setString(4, newVersion.getFirst());
				ps.setString(5,applicationId );
				ps.setString(6, tenantId);
				ps.setInt(7, currentVersion.getSecond());
				ps.setString(8, newVersion.getFirst());

			};
			final String query = queryDefinition.getDefinitions().get("findAggregatedActivityJIRAAcrossRelese");
			List<String> answer = jdbcTemplate.query(query, pss, this::mapActiveJira);
			return answer;
		}
		
	}

	public JiraIdResponse  findActiveJiraList(Map<String,Set<String>> listOfItems) {
		Timestamp timestamp = Timestamp.valueOf(ZonedDateTime.now(ZoneOffset.UTC).minusDays(1).toLocalDateTime());
		List<List<ActiveTask>> activeList = new ArrayList<List<ActiveTask>>();
		for(Map.Entry<String,Set<String>> commits: listOfItems.entrySet() ) {
			commits.getValue().stream().toArray(String[]:: new);
			List<String> aList = commits.getValue().stream().collect(Collectors.toList());
			PreparedStatementSetter pss = ps -> {
				ps.setString(1,commits.getKey() );
				for(int i=0;i<aList.size();i++) {
					ps.setString(i+2,aList.get(i) );
				}
			};
			String minMaxReleasequery = queryDefinition.getDefinitions().get("findMinAndMaxReleaseTime");
			minMaxReleasequery = "select max (rel.reltim) as max, min(rel.reltim) as min from relctg rel where rel.artidr=? and rel.artver in (";
			for(int i=0;i<aList.size();i++) {
				minMaxReleasequery =minMaxReleasequery.concat("?,");
			}
			minMaxReleasequery = minMaxReleasequery.substring(0,minMaxReleasequery.length()-1).concat(" );");
			List<ReleaseTime> relaseTimes = jdbcTemplate.query(minMaxReleasequery, pss, this::mapTimestamps);

			PreparedStatementSetter pssRel = ps -> {
				ps.setString(1,commits.getKey() );
				ps.setTimestamp(2,(relaseTimes.get(0).getMin()==null)?null:Timestamp.valueOf(relaseTimes.get(0).getMin()));
				ps.setTimestamp(3,(relaseTimes.get(0).getMax()==null)?null:Timestamp.valueOf(relaseTimes.get(0).getMax()));

			};
			final String activeRefQuery = queryDefinition.getDefinitions().get("findActiveRefMinAndMaxReleaseTime");
			List<ActiveTask> answer = jdbcTemplate.query(activeRefQuery, pssRel, this::mapActiveTask);
			activeList.add(answer);
		}
		JiraIdResponse respone = new JiraIdResponse();
		Map<String,String> jiraMap = new HashMap<String, String>();
		activeList.stream().forEach(value ->
			value.stream().forEach(activeTask->{
				jiraMap.put(activeTask.getArtifactId(), activeTask.getJira());
			})
				);
		respone.setJiraIdTask(jiraMap);
		return respone;
	}


	private Artifact mapArtifact(ResultSet rs, int row) throws SQLException {
		Artifact art = new Artifact();
		art.setArtifactId(rs.getString("artidr"));
		art.setPort(rs.getInt("port"));
		art.setHealthEndpoint(rs.getString("hltenp"));
		art.setContextPath(rs.getString("ctxpth"));
		art.setDescription(rs.getString("dsc"));
		art.setDomain(rs.getString("domnam"));
		art.setServiceTechnology(ServiceTechnology.valueOf(rs.getString("srvtch")));
		art.setServiceType(ServiceType.valueOf(rs.getString("srvtyp")));
		return art;
	}

	private DeploymentUnit mapDeploymentUnit(ResultSet rs, int row) throws SQLException {
		DeploymentUnit unit = new DeploymentUnit();
		unit.setArtifactId(rs.getString("artidr"));
		String domain = rs.getString("domnam");
		String artDesc = rs.getString("artdsc");
		String relDesc = rs.getString("reldsc");
		StringBuilder dbul = new StringBuilder();
		dbul.append("Domain : ").append(domain);
		if(artDesc != null)
			dbul.append(" - ").append(artDesc);
		if(relDesc != null)
			dbul.append(" - ").append(relDesc);
		unit.setDescription(dbul.toString());
		unit.setProcessType(ServiceTechnology.valueOf(rs.getString("srvtch")));
		unit.setServiceType(ServiceType.valueOf(rs.getString("srvtyp")));
		unit.setHealthEndpoint(rs.getString("hltenp"));
		unit.setPort(rs.getInt("port"));
		unit.setContextPath(rs.getString("ctxpth"));
		unit.setHostsEnterpriseApi(rs.getBoolean("entapi"));
		unit.setHostsPrivateApi(rs.getBoolean("prvapi"));
		unit.setHostsPublicApi(rs.getBoolean("pubapi"));
		Timestamp releaseTs = rs.getTimestamp("reltim");


		ZonedDateTime releaseTime = ZonedDateTime.ofInstant(releaseTs.toInstant(), ZoneId.of("UTC"));
		unit.setReleaseTime(releaseTime);
		String image = unit.getArtifactId() + ":" + rs.getString("artver");
		unit.setImage(image);
		boolean serviceRequired = unit.isHostsEnterpriseApi() || unit.isHostsPrivateApi() || unit.isHostsPublicApi()
				|| ServiceType.WEB_BFF == unit.getServiceType() || ServiceType.WEB_FE == unit.getServiceType() || ServiceType.WEB_GW == unit.getServiceType();
		if(serviceRequired)
			unit.setServiceName(unit.getArtifactId());
		unit.setBranch(rs.getString("relbrh"));
		unit.setArtifactVersion(rs.getString("artver"));
		// some query does not join tntctg
		try {
			unit.setDeploymentGroup(rs.getString("depgrp"));
		}catch (SQLException e){
			//ignored
		}
		return unit;
	}

	private ActiveTask mapActiveTask(ResultSet rs, int row) throws SQLException {
		ActiveTask activeTask = new ActiveTask();
		activeTask.setArtifactId(rs.getString("artidr"));
		activeTask.setJira(rs.getString("actvtyref"));
		return activeTask;
	}

	private String getBuildVersionBeta(ResultSet rs, int row) throws SQLException {
		String value =(rs.getString("relnum")+"."+rs.getString("bldnum")	);
		return value;
	}
	
	private String mapActiveJira(ResultSet rs, int row) throws SQLException {
		String value =(rs.getString("depctg"));
		return value;
	}

	private ReleaseTime mapTimestamps(ResultSet rs, int row) throws SQLException {
		ReleaseTime relTime = new ReleaseTime();
		relTime.setMax(rs.getString("max"));
		relTime.setMin(rs.getString("min"));
		return relTime;
	}

	public List<DeploymentUnit> findAllLatestReleases(@Nullable String artfactId) {
		List<DeploymentUnit> result = null;
		var dynamicCdtn="";boolean hasDynamic = false;
		var intervalFilter="'15 day'";
		if(!StringUtils.isEmpty(artfactId)){
			dynamicCdtn = "and cat1.artidr = ?";
			hasDynamic = true;
		}
		final String query = MessageFormat.format(queryDefinition.getDefinitions().get("findAllLatestReleases"),intervalFilter,dynamicCdtn);
		PreparedStatementSetter pss = ps -> {
			ps.setString(1, artfactId);
		};
		log.info("findAllLatestReleases query {}",query);
		return hasDynamic?jdbcTemplate.query(query, pss,this::mapDeploymentUnit):jdbcTemplate.query(query, this::mapDeploymentUnit);
	}

	public List<DeploymentUnit> findArtefactReleaseDeplUnit(String artfactId, String artver) {
		final String query = queryDefinition.getDefinitions().get("findArtReleaseBom");
		PreparedStatementSetter pss = ps -> {
			ps.setString(1, artfactId);
			ps.setString(2, artver);
		};
		return jdbcTemplate.query(query, pss,this::mapDeploymentUnit);
	}

	public List<String> getBomForDeployment(String appIdr, String tenantIdr, String env) {
		final String query = queryDefinition.getDefinitions().get("findArtVerForBom");
		PreparedStatementSetter pss = ps -> {
			ps.setString(1, env);
			ps.setString(2, appIdr);
			ps.setString(3, tenantIdr);
			
		};
		return jdbcTemplate.query(query, pss,this::getBuildVersionBeta);
	}
	
	public long findNextBuildNum(String appId, String relNum){
		final String query = queryDefinition.getDefinitions().get("findNextBuildNum");
		return jdbcTemplate.queryForObject(query,new Object[] {appId,relNum},Long.class);
	}


	//Returns -1 if no more patch numbers available (max of 100 on a build)
	public long findNextAvailablePatchBuildNum(long orgBuildNum,String relNum){
		final String query = queryDefinition.getDefinitions().get("findNextPatchBuildNum");
		long result = -1;
		PreparedStatementSetter pss = ps -> {
			ps.setLong(1, orgBuildNum);
			ps.setLong(2, orgBuildNum);
			ps.setLong(3, orgBuildNum);
			ps.setString(4, relNum);
		};
		var buildNums = jdbcTemplate.query(query, pss, (rs, index) ->
		Pair.of(rs.getLong("bld_max_nxt"),rs.getLong("bld_max_abs")));
		if(buildNums.size()>0){
			var bldNum = buildNums.get(0);
			//Not yet max patch build number?
			if(bldNum.getFirst()<=bldNum.getSecond()){
				result = bldNum.getFirst();
			}
		}
		return result;
	}

	public List<Build> findLastNDaysBuilds(int days) {
		var dateFilter = ZonedDateTime.now(ZoneOffset.UTC).minusDays(days);
		final String query = queryDefinition.getDefinitions().get("findBuilds");
		PreparedStatementSetter pss = ps -> {
			ps.setDate(1, Date.valueOf(dateFilter.toLocalDate()));
		};

		return jdbcTemplate.query(query, pss, (rs, index) -> {
			var build = new Build();
			build.setBuildNum(rs.getString("relnum")+"."+ rs.getLong("bldnum"));
			build.setTenantId(rs.getString("tntidr"));
			build.setApplicationIdr(rs.getString("appidr"));
			build.setBuildStartTime(ZonedDateTime.ofInstant(rs.getTimestamp("bldstrtim").toInstant(), ZoneOffset.UTC));
			var bldendtim = rs.getTimestamp("bldendtim");
			if (Objects.nonNull(bldendtim)) {
				build.setBuildEndTime(ZonedDateTime.ofInstant(bldendtim.toInstant(), ZoneOffset.UTC));
			}
			build.setReleasePackagesWithBuild(rs.getInt("relcnt"));
			build.setBuildStatus(BuildCatalogue.BuildStatus.valueOf(rs.getString("bldsta")));
			var bldqty = BuildCatalogue.BuildQuality.valueOf(rs.getString("bldqly"));
			build.setBuildQuality(bldqty);
			build.setBuildQualityDesc(bldqty.getMessage());
			return build;
		});

	}

	public List<Application> findApplicationsOfTenant(String tenantId){
		final String query = queryDefinition.getDefinitions().get("findApplicationIds");
		PreparedStatementSetter pss = ps -> {
			ps.setString(1, tenantId);
		};
		return jdbcTemplate.query(query, pss, (rs, index) -> {
			var app = new Application();
			app.setTenantId(tenantId);
			app.setApplicationId(rs.getString("appidr"));
			app.setApplicationDesc(rs.getString("appdsc"));
			return app;
		});

	}


}
