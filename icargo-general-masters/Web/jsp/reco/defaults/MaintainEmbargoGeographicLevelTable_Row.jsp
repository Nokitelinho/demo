<%@ include file="/jsp/includes/tlds.jsp" %>
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableCode" />
<business:sessionBean id="embargoVo" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoVo" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="geographicLevelOnetimeValues" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="geographicLevel" />
<bean:define id="form" name="MaintainEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"/>

<%
Integer dcIndex =(Integer) request.getAttribute("dcIndex");
%>
<td>
<bean:define id="geographiclevel" name="_geographiclevel" />
					<div class="ic-center">
							<logic:present name="geographiclevel" property="geographicLevel">
							<bean:define id="geoLevel" name ="geographiclevel" property="geographicLevel" type="String" />
							<ihtml:select property="geographicLevel" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevel" value="<%=(String)geoLevel%>"      onchange="javasciript:clearValues(this,targetFormName)">
								  <logic:present name="geographicLevelOnetimeValues">
									<logic:iterate id="level" name="geographicLevelOnetimeValues">
										<bean:define id="levelvalue" name="level" property="fieldValue" />
										<html:option value="<%=(String)levelvalue %>"><bean:write name="level" property="fieldDescription" /></html:option>
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</logic:present>
						<logic:notPresent name="geographiclevel" property="geographicLevel">
							<ihtml:select property="geographicLevel" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevel" onchange="javasciript:clearValues(this,targetFormName)">
								  <logic:present name="geographicLevelOnetimeValues">
									<logic:iterate id="level" name="geographicLevelOnetimeValues">
										<bean:define id="levelvalue" name="level" property="fieldValue" />
										<html:option value="<%=(String)levelvalue %>"><bean:write name="level" property="fieldDescription" /></html:option>
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</logic:notPresent>
					</div>
					</td>
					<td>
					<div class="ic-center">
						<logic:present name="geographiclevel" property="geographicLevelType">
							<bean:define id="geoLevelType" name ="geographiclevel" property="geographicLevelType" type="String" />
							<ihtml:select property="geographicLevelType" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevelType" value="<%=(String)geoLevelType%>" onchange="javasciript:clearValues(this,targetFormName)">
								  <logic:present name="geographicLevelType">
									<logic:iterate id="type" name="geographicLevelType">
										<bean:define id="typevalue" name="type" property="fieldValue" />
										<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</logic:present>
						<logic:notPresent name="geographiclevel" property="geographicLevelType">					
							<ihtml:select property="geographicLevelType" id="index" indexId="dcIndex"  componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevelType" onchange="javasciript:clearValues(this,targetFormName)">
								  <logic:present name="geographicLevelType">
									<logic:iterate id="type" name="geographicLevelType">
										<bean:define id="typevalue" name="type" property="fieldValue" />
										<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</logic:notPresent>
					</div>
					</td>
					<td>
					<div class="ic-center">
						<logic:present name="geographiclevel" property="geographicLevelApplicableOn">
							<bean:define id="applicableon" name ="geographiclevel" property="geographicLevelApplicableOn" type="String" />
							<ihtml:select property="geographicLevelApplicableOn" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded" value="<%=(String)applicableon%>" >
								  <logic:present name="applicableValues">
									<logic:iterate id="applicable" name="applicableValues">
										<bean:define id="applicablevalue" name="applicable" property="fieldValue" />
										<logic:notEqual name="applicablevalue" value="EXIF">
										<html:option value="<%=(String)applicablevalue %>"><bean:write name="applicable" property="fieldDescription" /></html:option>
										</logic:notEqual>										
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</logic:present>
						<logic:notPresent name="geographiclevel" property="geographicLevelApplicableOn">
							<ihtml:select property="geographicLevelApplicableOn" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded" >
								  <logic:present name="applicableValues">
									<logic:iterate id="applicable" name="applicableValues">
										<bean:define id="applicablevalue" name="applicable" property="fieldValue" />
										<logic:notEqual name="applicablevalue" value="EXIF">
										<html:option value="<%=(String)applicablevalue %>"><bean:write name="applicable" property="fieldDescription" /></html:option>
										</logic:notEqual>	
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</logic:notPresent>
					</div>
					</td>