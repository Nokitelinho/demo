<%@ include file="/jsp/includes/tlds.jsp" %>
<business:sessionBean id="applicableValues"	moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="applicableCode" />
<business:sessionBean id="embargoVo" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="embargoVo" />
<business:sessionBean id="geographicLevelType" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="geographicLevelType"/>
<business:sessionBean id="geographicLevelOnetimeValues" moduleName="reco.defaults" screenID="reco.defaults.maintainembargo" method="get" attribute="geographicLevel" />
<bean:define id="form" name="MaintainEmbargoRulesForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"/>

			<% int count=0;%>
			
			<logic:present name="embargoVo">
				<logic:present name="embargoVo" property="geographicLevels">
				
				<logic:iterate id="geographiclevel" name="embargoVo" property="geographicLevels" indexId="dcIndex">
					<%
						request.setAttribute("dcIndex",dcIndex);
					%>
					<bean:define id="_geographiclevel" name="geographiclevel" toScope="request"/>
					<logic:present name="geographiclevel" property="geographicLevelValues">
						<bean:define id="val" name="geographiclevel" property="geographicLevelValues" />
						<logic:notEqual  name="val" value="-" >
					
						<logic:present name="geographiclevel" property="operationFlag" >
										
							<bean:define id="flag" name="geographiclevel" property="operationFlag" />
							
							<logic:notEqual name="flag" value="D" >
							<logic:equal name="flag" value="I">
								<ihtml:hidden name="form" property="glOperationFlag" value="I"/>
							</logic:equal>
							<logic:notEqual name="flag" value="I">
								<ihtml:hidden name="form" property="glOperationFlag" value="U"/>
							</logic:notEqual>
											
				<tr>
					<td>
						<div class="ic-center">                            
							<ihtml:checkbox property="index" value="<%=String.valueOf(dcIndex)%>" onclick="toggleTableHeaderCheckbox('index',this.form.masterCheckbox)"/>
						</div>
					</td>
					<jsp:include page="MaintainEmbargoGeographicLevelTable_Row.jsp"/>
					<td>
						<div class="ic-center ic-input">
							<logic:present name="geographiclevel" property="geographicLevelValues">
								<bean:define id="values" name="geographiclevel" property="geographicLevelValues" />
								<ihtml:text indexId="dcIndex" property="geographicLevelValues" value="<%=String.valueOf(values)%>" style="width:72%" componentID="CMP_Reco_Defaults_MaintainEmbargo_Values" onblur="restrictGeoLevelValue(this)"/>
                                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" id="geographicLevelLov" onclick="showGeographicLevelLov(targetFormName,<%=String.valueOf(count)%>)" width="20" height="20" /></div>
							</logic:present>
							<logic:notPresent name="geographiclevel" property="geographicLevelValues">
								<ihtml:text indexId="dcIndex" property="geographicLevelValues" componentID="CMP_Reco_Defaults_MaintainEmbargo_Values" style="width:72%" value="" onblur="restrictGeoLevelValue(this)"/>
                                <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" id="geographicLevelLov" onclick="showGeographicLevelLov(targetFormName,<%=String.valueOf(count)%>)" width="20" height="20" /></div>
							</logic:notPresent>		
						</div>
					</td>
				</tr>
				</logic:notEqual>
				
					<logic:equal name="flag" value="D">
						<ihtml:hidden name="form" property="glOperationFlag" value="D"/>											
						<ihtml:hidden property="index" name="form" value="<%=String.valueOf(dcIndex)%>"/>
					</logic:equal>
										
			</logic:present>
			
			<logic:notPresent name="geographiclevel" property="operationFlag">
			
				<ihtml:hidden name="form" property="glOperationFlag" value="U"/>
				<tr>
					<td>
						<div class="ic-center">                            
							<ihtml:checkbox property="index" value="<%=String.valueOf(dcIndex)%>" onclick="toggleTableHeaderCheckbox('index',this.form.masterCheckbox)"/>
						</div>
					</td>
					<td>
					<div class="ic-center">
						<logic:present name="geographiclevel" property="geographicLevel">
							<bean:define id="geoLevel" name ="geographiclevel" property="geographicLevel" type="String" />
							<ihtml:select property="geographicLevel" indexId="dcIndex" id="index" componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevel" value="<%=(String)geoLevel%>" onchange="javasciript:clearValues(this,targetFormName)" >
								  <logic:present name="geographicLevelOnetimeValues">
									<logic:iterate id="level" name="geographicLevelOnetimeValues">
										<bean:define id="levelvalue" name="level" property="fieldValue" />
										<html:option value="<%=(String)levelvalue %>"><bean:write name="level" property="fieldDescription" /></html:option>
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</logic:present>
						<logic:notPresent name="geographiclevel" property="geographicLevel">
							<ihtml:select property="geographicLevel" indexId="dcIndex" id="index" componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevel" onchange="javasciript:clearValues(this,targetFormName)">
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
							<ihtml:select property="geographicLevelType" indexId="dcIndex" id="index" componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevelType" value="<%=(String)geoLevelType%>" onchange="javasciript:clearValues(this,targetFormName)">
								  <logic:present name="geographicLevelType">
									<logic:iterate id="type" name="geographicLevelType">
										<bean:define id="typevalue" name="type" property="fieldValue" />
										<html:option value="<%=(String)typevalue %>"><bean:write name="type" property="fieldDescription" /></html:option>
									</logic:iterate>
								  </logic:present>                 
							</ihtml:select>
						</logic:present>
						<logic:notPresent name="geographiclevel" property="geographicLevelType">					
							<ihtml:select property="geographicLevelType" indexId="dcIndex" id="index" componentID="CMP_Reco_Defaults_MaintainEmbargo_GeographicLevelType" onchange="javasciript:clearValues(this,targetFormName)">
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
					<td>
					<div class="ic-center ic-input">
						<logic:present name="geographiclevel" property="geographicLevelValues">
							<bean:define id="values" name="geographiclevel" property="geographicLevelValues" />
							<ihtml:text indexId="dcIndex" property="geographicLevelValues" value="<%=String.valueOf(values)%>" style="width:72%" componentID="CMP_Reco_Defaults_MaintainEmbargo_Values" onblur="restrictGeoLevelValue(this)"/>
                            <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" id="geographicLevelLov" onclick="showGeographicLevelLov(targetFormName,<%=String.valueOf(count)%>)"  width="20" height="20" /></div>
						</logic:present>
						<logic:notPresent name="geographiclevel" property="geographicLevelValues">
							<ihtml:text indexId="dcIndex" property="geographicLevelValues" componentID="CMP_Reco_Defaults_MaintainEmbargo_Values" style="width:72%" value="" onblur="restrictGeoLevelValue(this)"/>
                            <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" id="geographicLevelLov" onclick="showGeographicLevelLov(targetFormName,<%=String.valueOf(count)%>)" width="20" height="20" /></div>
						</logic:notPresent>		
					</div>
					</td>
				</tr>
			</logic:notPresent>
				
			</logic:notEqual>
			
			<logic:equal name="val" value="-">
				<html:hidden property="glOperationFlag" value="DELETE"/>
				<tr style="display:none">
					<td>
						<div class="ic-center">                            
							<ihtml:checkbox property="index" value="<%=String.valueOf(dcIndex)%>" onclick="toggleTableHeaderCheckbox('index',this.form.masterCheckbox)"/>
						</div>
					</td>
					<td>
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
					<td>
					<div class="ic-center ic-input">
						<logic:present name="geographiclevel" property="geographicLevelValues">
							<bean:define id="values" name="geographiclevel" property="geographicLevelValues" />
							<ihtml:text indexId="dcIndex" property="geographicLevelValues" value="<%=String.valueOf(values)%>" style="width:72%" componentID="CMP_Reco_Defaults_MaintainEmbargo_Values"/>
                            <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" id="geographicLevelLov" onclick="showGeographicLevelLov(targetFormName,<%=String.valueOf(count)%>)" width="20" height="20" /></div>
						</logic:present>
						<logic:notPresent name="geographiclevel" property="geographicLevelValues">
							<ihtml:text indexId="dcIndex" property="geographicLevelValues" componentID="CMP_Reco_Defaults_MaintainEmbargo_Values" style="width:72%" value=""/>
                            <div class= "lovImgTbl"><img src="<%=request.getContextPath()%>/images/lov.png" id="geographicLevelLov" onclick="showGeographicLevelLov(targetFormName,<%=String.valueOf(count)%>)" width="20" height="20" /></div>
						</logic:notPresent>		
					</div>
					</td>
				</tr>
			</logic:equal>
		</logic:present>
				<%count++; %>
		</logic:iterate>
		</logic:present>
	</logic:present>
			
			<!-----------------------------------Template Row----------------------------------->
			<jsp:include page="MaintainEmbargoGeographicLevelTable_Split.jsp"/>