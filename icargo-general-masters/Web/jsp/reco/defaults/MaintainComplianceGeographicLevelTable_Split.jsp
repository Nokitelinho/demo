<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="applicableCode" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="dayOfOperationApplicableOn" moduleName="reco.defaults" screenID="reco.defaults.maintaincomplianceinfo" method="get" attribute="dayOfOperationApplicableOn" />
		<% int templateRowCount=0;%>	
<%
Integer dcIndex =(Integer)request.getAttribute("dcIndex");%>		
			<tr template="true" id="listSelectedDataTemplateRow" style="display:none">
			
				<td class="ic-center"> 
<html:hidden property="glOperationFlag" value="NOOP"/>
					<ihtml:checkbox property="index" onclick="toggleTableHeaderCheckbox('index',this.form.masterCheckbox)"/>
				</td>
				<td class="ic-center">
					
					<ihtml:select property="geographicLevel" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_GeographicLevel" onchange="javasciript:clearValues(this,targetFormName)">
						  <logic:present name="dayOfOperationApplicableOn">
							<logic:iterate id="level" name="dayOfOperationApplicableOn">
								<bean:define id="levelvalue" name="level" property="fieldValue" />
								<html:option value="<%=(String)levelvalue %>"><bean:write name="level" property="fieldDescription" /></html:option>
							</logic:iterate>
						  </logic:present>                 
					</ihtml:select>
				</td>
				<td class="ic-center">
					<ihtml:select property="geographicLevelType" id="index" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_GeographicLevelType" onchange="javasciript:clearValues(this,targetFormName)" >
						  <logic:present name="geographicLevelType">
							<logic:iterate id="type" name="geographicLevelType">
								<bean:define id="typevalue" name="type" property="fieldValue" />
								<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
							</logic:iterate>
						  </logic:present>                 
					</ihtml:select>
				</td>
				<td class="ic-center">
					<ihtml:select property="geographicLevelApplicableOn" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_IsIncluded" >
						  <logic:present name="applicableValues">
							<logic:iterate id="applicable" name="applicableValues">
								<bean:define id="applicablevalue" name="applicable" property="fieldValue" />
								<html:option value="<%=(String)applicablevalue %>"><bean:write name="applicable" property="fieldDescription" /></html:option>
							</logic:iterate>
						  </logic:present>                 
					</ihtml:select>
				</td>
				<td class="ic-center">
					<ihtml:text property="geographicLevelValues" indexId="dcIndex" componentID="CMP_Reco_Defaults_MaintainComplianceInfo_Values" style="width:50%" value="" onblur="restrictValues(this)" />
					<div class="lovImgTbl valignT"><img src="<%=request.getContextPath()%>/images/lov_sm.png" id="geoLevelLov" name="geoLevelLov" onclick="showGeoLevelLov(this,targetFormName)" width="15" height="15" /></div>
				</td>
				<%templateRowCount++;%>
			</tr>
		