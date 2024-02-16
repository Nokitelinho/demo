<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="applicableCode" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="dayOfOperationApplicableOn" />

<%
Integer dcIndex =(Integer)request.getAttribute("dcIndex");%>
<td class="ic-center">
<bean:define id="geographiclevel" name="_geographiclevel" />
				<logic:present name="geographiclevel" property="geographicLevel">
					<bean:define id="geoLevel" name ="geographiclevel" property="geographicLevel" type="String" />
					<ihtml:select property="geographicLevel" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_GeographicLevel" value="<%=(String)geoLevel%>" onchange="javasciript:clearValues(this,targetFormName)">
						  <logic:present name="dayOfOperationApplicableOn">
							<logic:iterate id="level" name="dayOfOperationApplicableOn">
								<bean:define id="levelvalue" name="level" property="fieldValue" />
								<html:option value="<%=(String)levelvalue %>"><bean:write name="level" property="fieldDescription" /></html:option>
							</logic:iterate>
						  </logic:present>                 
					</ihtml:select>
				</logic:present>
				<logic:notPresent name="geographiclevel" property="geographicLevel">
					<ihtml:select property="geographicLevel" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_GeographicLevel" onchange="javasciript:clearValues(this,targetFormName)">
						  <logic:present name="dayOfOperationApplicableOn">
							<logic:iterate id="level" name="dayOfOperationApplicableOn">
								<bean:define id="levelvalue" name="level" property="fieldValue" />
								<html:option value="<%=(String)levelvalue %>"><bean:write name="level" property="fieldDescription" /></html:option>
							</logic:iterate>
						  </logic:present>                 
					</ihtml:select>
				</logic:notPresent>
				</td>
				<td class="ic-center">
				<logic:present name="geographiclevel" property="geographicLevelType">
					<bean:define id="geoLevelType" name ="geographiclevel" property="geographicLevelType" type="String" />
					<ihtml:select property="geographicLevelType" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_GeographicLevelType" value="<%=(String)geoLevelType%>" onchange="javasciript:clearValues(this,targetFormName)" >
						  <logic:present name="geographicLevelType">
							<logic:iterate id="type" name="geographicLevelType">
								<bean:define id="typevalue" name="type" property="fieldValue" />
								<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
							</logic:iterate>
						  </logic:present>                 
					</ihtml:select>
				</logic:present>
				<logic:notPresent name="geographiclevel" property="geographicLevelType">					
					<ihtml:select property="geographicLevelType" id="index" indexId="dcIndex"  componentID="CMP_Reco_Defaults_MaintainComplianceInfo_GeographicLevelType" onchange="javasciript:clearValues(this,targetFormName)">
						  <logic:present name="geographicLevelType">
							<logic:iterate id="type" name="geographicLevelType">
								<bean:define id="typevalue" name="type" property="fieldValue" />
								<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
							</logic:iterate>
						  </logic:present>                 
					</ihtml:select>
				</logic:notPresent>
				</td>
				<td class="ic-center">
				<logic:present name="geographiclevel" property="geographicLevelApplicableOn">
					<bean:define id="applicableon" name ="geographiclevel" property="geographicLevelApplicableOn" type="String" />
					<ihtml:select property="geographicLevelApplicableOn" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_IsIncluded" value="<%=(String)applicableon%>" >
						  <logic:present name="applicableValues">
							<logic:iterate id="applicable" name="applicableValues">
								<bean:define id="applicablevalue" name="applicable" property="fieldValue" />
								<html:option value="<%=(String)applicablevalue %>"><bean:write name="applicable" property="fieldDescription" /></html:option>
							</logic:iterate>
						  </logic:present>                 
					</ihtml:select>
				</logic:present>
				<logic:notPresent name="geographiclevel" property="geographicLevelApplicableOn">
					<ihtml:select property="geographicLevelApplicableOn" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_IsIncluded" >
						  <logic:present name="applicableValues">
							<logic:iterate id="applicable" name="applicableValues">
								<bean:define id="applicablevalue" name="applicable" property="fieldValue" />
								<html:option value="<%=(String)applicablevalue %>"><bean:write name="applicable" property="fieldDescription" /></html:option>
							</logic:iterate>
						  </logic:present>                 
					</ihtml:select>
				</logic:notPresent>
				</td>