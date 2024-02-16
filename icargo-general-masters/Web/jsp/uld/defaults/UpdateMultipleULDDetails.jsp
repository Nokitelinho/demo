<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  ULD
* File Name				:  UpdateMultiplULDDetails.jsp
* Date					:  06-June-2006
* Author(s)				:  A-2047
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm"%>
 <%@ page import = "com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO"%>

		
	
		
	 <html:html>

 <head>
		
		<%@ include file="/jsp/includes/customcss.jsp" %>
		
	
		
	
    <meta name="decorator" content="mainpanel">
 <common:include type="script" src="/js/uld/defaults/UpdateMultipleULDDetails_script.jsp"/>
 </head>

 <body style="width:65%" class="ic-center">
	
	
	

	<bean:define id="form"
		 name="UpdateMultipleULDDetailsForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.UpdateMultipleULDDetailsForm"
		 toScope="page" />
		 
    <business:sessionBean
		id="OneTimeVOs"
		moduleName="uld.defaults"
		screenID="uld.defaults.updatemultipleulddetails"
		method="get"
		attribute="oneTimeValues" />
		
	<business:sessionBean
		id="ULDs"
		moduleName="uld.defaults"
		screenID="uld.defaults.updatemultipleulddetails"
		method="get"
		attribute="ULDDamageRepairDetailsVOs" />


		
	<logic:present name="ULDs">
	    <bean:define id="ULDs" name="ULDs" />
   </logic:present>

	<div id="mainDiv" class="iCargoContent" style="width:100%;height:100%;overflow:auto;">
	<ihtml:form action="/uld.defaults.misc.updatemultipleulddamage.do" enctype="multipart/form-data">

 	
		<div class="ic-content-main">
		
		<div class="ic-head-container">
			<!--<div class="ic-row">
				<div class="ic-input-container"><h4>
				<common:message key="mailtracking.defaults.mailsubclassmaster.lbl.search" /></h4>
				</div>
			</div>-->
		<!--DWLayoutTable-->
			
		</div>
		<div class="ic-main-container">
		<ihtml:hidden property="selectedRow" />
		<ihtml:hidden property="statusFlag" />
		<div class="ic-section  ic-pad-2">
		<div class="ic-row">
		<div class="tableContainer" style="height:650px;">
		<table  class="fixed-header-table" id="subclassTableBody">
			                  <thead>
							   <tr >
					
					<td class="iCargoTableHeader" width="30%">
						<common:message key="uld.defaults.updatemultipleuld.uldnumber" />
					
					</td>
			
					<td class="iCargoTableHeader" width="30%">
							<common:message key="uld.defaults.updatemultipleuld.operationalstatus" />
					</td>
					
					<td class="iCargoTableHeader" width="30%">
						<common:message key="uld.defaults.updatemultipleuld.damagestatus" />
					</td>
					
					<td class="iCargoTableHeader" width="10%">
						<common:message key="uld.defaults.updatemultipleuld.damagedetails" />
					</td>
				</tr>
							  </thead>
							  <tbody id="uldTableBody" style="overflow:auto;">
							    <% boolean checkbox=true;%>
								  <logic:present name="ULDs">
								  <%int cnt = 0;%>
								     <logic:iterate id="uld" name="ULDs" indexId="uldindex" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO">
								     <bean:define id="count" value="<%=String.valueOf(cnt)%>" />
									   <tr>
									   
									   <td class="iCargoTableDataTd" width="30%">
									  
										
									   <logic:present name="uld" property="uldNumber">	
											<bean:define id="uldNumber" name="uld" property="uldNumber" />
										   <ihtml:text property="uldNumbers" indexId="uldindex" value="<%=(String)uldNumber%>" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" />	
										 </logic:present>
										 <logic:notPresent name="uld" property="uldNumber">
										 <ibusiness:uld id="<%=count%>" uldProperty="uldNumbers" style="text-transform: uppercase" maxlength="12"/>
										  <%--<ihtml:text property="uldNumbers" styleId="spotRatePrivilegeId" indexId="uldindex"  maxlength="12"/>--%>
										 </logic:notPresent>
										</td> 
										<td class="iCargoTableDataTd">
										<div class="ic-center ic-col-50">
										
										  <logic:present name="uld" property="overallStatus">
										  <bean:define id="overallStatus" name="uld" property="overallStatus" />
										   <ihtml:select property="operationalStatus" indexId="uldindex" value="<%=(String)overallStatus%>" id="operationalStatus">
										     <logic:present name="OneTimeVOs" >
																	<ihtml:option value="">---Select---</ihtml:option>
																		<bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
																		<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																			<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																			<logic:equal name="parameterCode" value="uld.defaults.overallStatus">
																				<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																				<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																					<logic:present name="parameterValue" property="fieldValue">
																						<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																						<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>																			 
																						
																						<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option> 
																						
																					</logic:present>

																				</logic:iterate>
																			</logic:equal>
																		</logic:iterate>
													</logic:present>
												</ihtml:select>	
												
					
					
										 </logic:present>
										 <logic:notPresent name="uld" property="overallStatus">
											   <ihtml:select property="operationalStatus" indexId="uldindex" id="operationalStatus">
											   
													<logic:present name="OneTimeVOs">
																	<ihtml:option value="">---Select---</ihtml:option>
																		<bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
																		<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																			<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																			<logic:equal name="parameterCode" value="uld.defaults.overallStatus">
																				<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																				<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																					<logic:present name="parameterValue" property="fieldValue">
																						<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																						<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>																			 
																						
																						<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option> 
																						
																					</logic:present>

																				</logic:iterate>
																			</logic:equal>
																		</logic:iterate>
													</logic:present>
										</ihtml:select>	

				
										</logic:notPresent>
										</div>
										<div class="ic-center ic-col-50">
										<% if(checkbox==true) {%>
									
					                   <ihtml:checkbox property="operationalStatusCopyAll"  id="operationalStatusCopyAll"/>
					         <common:message key="uld.defaults.updatemultipleuld.copyall" />
										  <% }%>
										  </div>
										</td>
										
									
										<td class="iCargoTableDataTd" width="30%">
										<div class="ic-center ic-col-50">
										<logic:present name="uld" property="damageStatus">
										  <bean:define id="damageStatus" name="uld" property="damageStatus" />
										   <ihtml:select property="damagedStatus" indexId="uldindex"   value="<%=(String)damageStatus%>" id="damagedStatus">
										      <logic:present name="OneTimeVOs">
													
																		<ihtml:option value="">---Select---</ihtml:option>
																			<bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
																			<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																				<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																				<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
																					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																					<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																						<logic:present name="parameterValue" property="fieldValue">
																							<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																							<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>																			 
																							
																							<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option> 
																							
																						</logic:present>

																					</logic:iterate>
																				</logic:equal>
																			</logic:iterate>
														</logic:present>
													</ihtml:select>	
										 </logic:present>
										 <logic:notPresent name="uld" property="damageStatus">
										   <ihtml:select property="damagedStatus" indexId="uldindex" id="damagedStatus">
											        <logic:present name="OneTimeVOs">
													
																		<ihtml:option value="">---Select---</ihtml:option>
																			<bean:define id="OneTimeValuesMap" name="OneTimeVOs" type="java.util.HashMap" />
																			<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																				<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																				<logic:equal name="parameterCode" value="uld.defaults.damageStatus">
																					<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																					<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																						<logic:present name="parameterValue" property="fieldValue">
																							<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																							<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>																			 
																							
																							<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option> 
																							
																						</logic:present>

																					</logic:iterate>
																				</logic:equal>
																			</logic:iterate>
														</logic:present>
													</ihtml:select>	
										 
										 </logic:notPresent>
										 </div>
										 	<div class="ic-center ic-col-50">
										<% if(checkbox==true) {%>
									
					                   <ihtml:checkbox property="damagedStatusCopyAll"  id="damagedStatusCopyAll"/>
					         <common:message key="uld.defaults.updatemultipleuld.copyall" />
										  <% }checkbox=false;%>
										  </div>
										 </td>
										
									  <td class="iCargoTableDataTd" width="10%">
												<div class="ic-input">
											 <bean:define id="damageDetails" value="false" />
											
												
												<logic:present name="uld" property="uldDamageVOs">
												<bean:define id="damageVos" name="uld" property="uldDamageVOs" />
													<logic:notEqual name="damageDetails" value="true">
														<logic:iterate id="damages" name="damageVos">
															<logic:notEqual name="damages" property="operationFlag" value="D">
																<bean:define id="damageDetails" value="true" />
															</logic:notEqual>
														</logic:iterate>
													</logic:notEqual>
												</logic:present>
												<logic:equal name="damageDetails" value="true">
													<ihtml:text componentID="CMP_Operations_Shipment_CaptureAWB_UldDetails" title="ULD" style="width:30px;" tabindex="36" property="damageDetails" size="65" value="YES" />
												</logic:equal>
												<logic:notEqual name="damageDetails" value="true">
													<ihtml:text componentID="CMP_Operations_Shipment_CaptureAWB_UldDetails" title="ULD" style="width:30px;" tabindex="36"  property="damageDetails" size="65" value="NO" />
												</logic:notEqual>
												<div class="lovImgTbl valignT">
												<img name="uldLOV" id="uldLOV" index="<%=String.valueOf(count)%>" src="<%=request.getContextPath()%>/images/lov.png" width="16" height="16" />
												</div>
												</div>
											</td>
										</tr> 
									<%cnt++;%>
									 </logic:iterate>
								  </logic:present>
							  </tbody>
							</table>
		 </div>
     
         </div>
		
	   </div>
			
			
	</div>
 
<div class="ic-foot-container">   
	<div class="ic-button-container">
	   
		    
			<ihtml:nbutton property="btSave" componentID="BTN_ULD_DEFAULTS_UPDATEMULTUPLEULD_DMGSAVE">
			<common:message key="uld.defaults.updatemultipleuld.btn.save" />
			</ihtml:nbutton>
	<ihtml:nbutton property="btClose" componentID="BTN_ULD_DEFAULTS_UPDATEMULTUPLEULD_DMGCLOSE">
			<common:message key="uld.defaults.updatemultipleuld.btn.close" />
			</ihtml:nbutton>
		 </div>
</div>
</div>		 
</div>		 

		  
   

 </ihtml:form>
 </div>

 
				
		
	
				
		
	</body>
 </html:html>

