<%@ include file="/jsp/includes/tlds.jsp" %>
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableCode" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="geographicLevelOnetimeValues" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="geographicLevel" />
<bean:define id="form" name="MaintainEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"/>


<% int templateRowCount=0;
Integer dcIndex =(Integer)request.getAttribute("dcIndex");
%>	

				<tr template="true" id="listSelectedDataTemplateRow" style="display:none">
				
					<td> 
						<div class="ic-center">  
							<html:hidden property="glOperationFlag" value="NOOP"/>
							<ihtml:checkbox property="index" onclick="toggleTableHeaderCheckbox('index',this.form.masterCheckbox)"/>
						</div>
					</td>
					<td>
						<div class="ic-center">
							<ihtml:select property="geographicLevel" id="index" indexId="dcIndex" onchange="clearValues(this,targetFormName);" componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevel" >
								  <logic:present name="geographicLevelOnetimeValues">
									<logic:iterate id="level" name="geographicLevelOnetimeValues">
										<bean:define id="levelvalue" name="level" property="fieldValue" />
										<html:option value="<%=(String)levelvalue %>"><bean:write name="level" property="fieldDescription" /></html:option>
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</div>
					</td>
					<td>
						<div class="ic-center">
							<ihtml:select property="geographicLevelType" id="index" indexId="dcIndex" onchange="clearValues(this,targetFormName);" componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevelType" >
								  <logic:present name="geographicLevelType">
									<logic:iterate id="type" name="geographicLevelType">
										<bean:define id="typevalue" name="type" property="fieldValue" />
										<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</div>
					</td>
					<td>
						<div class="ic-center">
							<ihtml:select property="geographicLevelApplicableOn" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainEmbargo_IsIncluded" >
								  <logic:present name="applicableValues">
									<logic:iterate id="applicable" name="applicableValues">
										<bean:define id="applicablevalue" name="applicable" property="fieldValue" />
										<logic:notEqual name="applicablevalue" value="EXIF">
										<html:option value="<%=(String)applicablevalue %>"><bean:write name="applicable" property="fieldDescription" /></html:option>
										</logic:notEqual>
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</div>
					</td>
					<td>
						<div class="ic-center ic-input">
							<ihtml:text property="geographicLevelValues" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainEmbargo_Values" style="width:72%" onblur="restrictGeoLevelValue(this)" value=""/>
                            <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" id="geoLevelLov" name="geoLevelLov" onclick="showGeoLevelLov(this,targetFormName)" width="20" height="20" /></div>
						</div>
					</td>
					<%templateRowCount++;%>
				</tr>
			