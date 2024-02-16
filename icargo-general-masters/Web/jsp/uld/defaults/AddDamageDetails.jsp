<%--
* Project	 		: iCargo
* Module Code & Name:
* File Name			:
* Date				:
* Author(s)			:
 --%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.xibase.server.framework.persistence.query.Page"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
	
<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>


<html:html>
<head>
		
			
	
<title>
		<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.damagecklist" scope="request"/>
</title>
<meta name="decorator" content="popuppanelrestyledui" >
<common:include type="script" src="/js/uld/defaults/AddDamageDetails_Script.jsp"/>



</head>
<body >
	


<bean:define id="form"
		 name="maintainDamageReportForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.MaintainDamageReportForm"
		 toScope="page" />
<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintaindamagereport"
		method="get"
		attribute="oneTimeValues" />



<business:sessionBean id="KEY_ULDDMGCHKLST"
				moduleName="uld.defaults"
				screenID="uld.defaults.damagechecklistmaster"
				method="get"
				attribute="ULDDamageChecklistVO" />

			<business:sessionBean id="KEY_ULDDMGCHKLST"
							moduleName="uld.defaults"
							screenID="uld.defaults.maintaindamagereport"
							method="get"
				attribute="ULDDamageChecklistVO" />
	<div class="iCargoPopUpContent" style="overflow:auto;width:100%;height:100%">
	    <ihtml:form action="/uld.defaults.damageDetailsScreenLoadCommand.do" enctype="multipart/form-data" styleClass="ic-main-form">
	    <ihtml:hidden property="statusFlag"/>
	    <ihtml:hidden property="flag"/>
	    <ihtml:hidden property="screenStatusFlag"/>
	    <ihtml:hidden property="dmgdisplayPage"/>
	    <ihtml:hidden property="dmglastPageNum"/>
	    <ihtml:hidden property="dmgtotalRecords"/>
	    <ihtml:hidden property="dmgcurrentPageNum"/>
	    <ihtml:hidden property="screenStatusValue"/>
	    <ihtml:hidden property="seqNum"/>
	    <ihtml:hidden property="currentStation"/>
		<ihtml:hidden property="overStatus"/>
		<ihtml:hidden property="sections"/>
		<!-- <ihtml:hidden property="description"/>  -->
		<ihtml:hidden property="damageDescription"/>
	    <input type="hidden" name="currentDialogId" />
	    <input type="hidden" name="currentDialogOption" />
		<div class="ic-content-main">    
		        <span class="ic-page-title ic-display-none">
						Damage Checklist
				</span>			
		<div class="ic-main-container">		
		 <div class="ic-row ic-input-round-border"> 
          <div class="ic-row">
		   <div class="ic-input ic-label-55 ic-split-35">
            <label>
			<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.DamageRefNo" />
            </label>		
            <ihtml:text property="dmgRefNo" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_DMGREFNO" name="maintainDamageReportForm"  readonly="true"/>
           </div>  
           <div class="ic-input ic-label-55 ic-split-30">
            <label>	   
		    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.Closed" />
			</label>
			<html:checkbox property="closed" name="maintainDamageReportForm" /></td>
		   </div>
		  </div>
		  <div class="ic-row">
		   <div class="ic-input ic-label-55 ic-split-35 ic-mandatory">
            <label>
		    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.Severity" />
			</label>
				<ihtml:select property="severity" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SEVERITY" >
					<logic:present name="oneTimeValues">
						<logic:iterate id="oneTimeValue" name="oneTimeValues">
							<bean:define id="parameterCode" name="oneTimeValue" property="key" />
								<logic:equal name="parameterCode" value="uld.defaults.damageseverity">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<html:option value="<%=(String)fieldValue%>">
														<%=(String)fieldDescription%>
													</html:option>
											</logic:present>
										</logic:iterate>
								</logic:equal>
						</logic:iterate>
					</logic:present>
				</ihtml:select>
	        </div>
		    <div class="ic-input ic-label-55 ic-split-30 ic-mandatory">
            <label>
		    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.ReportedStn" />
			</label>
			    <ihtml:text property="repStn" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_REPSTN" name="maintainDamageReportForm" maxlength="3" />
				<div class="lovImg">
					<img src="<%= request.getContextPath()%>/images/lov.png" width="18" height="18" name="airportLovImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[0].repStn.value,'CurrentAirport','0','repStn','',0)" alt="Airport LOV"/>
				 </div>
		
            </div>
			<div class="ic-input ic-label-45 ic-split-35 ic-mandatory">
            <label>
		    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.reporteddate" />
			</label>			        
			<ibusiness:calendar property="reportedDate" type="image" id="reportedDate" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SEVERITY_REPORTEDDATE" />
			</div>
		  </div>
		 </div>
		 <div class="ic-row">
			<div class="ic-input ic-label-30 ic-split-65 ic-mandatory">
            <label>
		    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.Section" />
			</label>
			<ihtml:select  property="section" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SECTION"  value="<%=form.getSection()%>" >
			<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
			 <logic:present name="oneTimeValues">
				<logic:iterate id="oneTimeValue" name="oneTimeValues">
					<bean:define id="parameterCode" name="oneTimeValue" property="key" />
							<logic:equal name="parameterCode" value="uld.defaults.section">
					<bean:define id="parameterValues" name="oneTimeValue" property="value" />
						<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
							<logic:present name="parameterValue">
							<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
								<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
								<ihtml:option value="<%=(String)fieldValue%>">
									<%=(String)fieldDescription%>
								</ihtml:option>
							</logic:present>
						</logic:iterate>
					</logic:equal>
				</logic:iterate>
			</logic:present>
			</ihtml:select>
           </div>
         </div>		   
        <div class="ic-row">           
		<div class="tableContainer" id="div1"   style="height:120px;position:relative;overflow:auto">
		 <table class="fixed-header-table"> 
		<!--<table style="width:100%;" align="center"  border="0x"  cellpadding="0" cellspacing="1" class="iCargoBorderedTable"> -->
		 <thead >
		 <tr class="iCargoTableHeadingLeft">
		 <td width="10%;">&nbsp;</td>
		 <td width="45%;" class="iCargoTableHeader"><common:message key="uld.defaults.maintaindamagereport.lbl.Section" /></td>
		 <td width="45%;" class="iCargoTableHeader"><common:message key="uld.defaults.maintaindamagereport.lbl.Description" /></td>
		
		 </tr>
		 </thead>
		 <tbody>
			<logic:present name="KEY_ULDDMGCHKLST">
				<bean:define id="key_ulddmgchklst" name="KEY_ULDDMGCHKLST"/>
					<logic:iterate id = "uldDamageChecklistVO" name="key_ulddmgchklst"
						type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO"
						indexId="rowCount" scope="page">
						<common:rowColorTag index="rowCount">
						<logic:present name="uldDamageChecklistVO" property="operationFlag">

						</logic:present>

						<logic:notPresent name="uldDamageChecklistVO" property="operationFlag">

							<tr>
							<%
								String eachVal = "";
							%>
							<td class="iCargoTableDataTd">


								<logic:present name="uldDamageChecklistVO" property="checkInOut">
									  <bean:define id="checkInOut" name="uldDamageChecklistVO" property="checkInOut" />

									  <logic:equal name="checkInOut" value="true">

										<input type="checkbox" name="checkBxVal" value="<%=eachVal%>" checked/>
									</logic:equal>
					      				  <logic:notEqual name="checkInOut" value="true">

										<input type="checkbox" name="checkBxVal" value="<%=eachVal%>"/>
									</logic:notEqual>

								</logic:present>
								<logic:notPresent name="uldDamageChecklistVO" property="checkInOut">
									<input type="checkbox" name="checkbx" />
								</logic:notPresent>

									<logic:present name="uldDamageChecklistVO" property="noOfPoints" >
											<bean:define id="noofPoints" name="uldDamageChecklistVO" property="noOfPoints"/>

											<ihtml:hidden property="noofPoints"   value ="<%=noofPoints.toString()%>" />
									</logic:present>
									<logic:notPresent name="uldDamageChecklistVO" property="noOfPoints">
											<ihtml:hidden property="noofPoints" value="" />

									</logic:notPresent>

							</td>
							<td class="iCargoTableDataTd" align="center">

								<!--	<ihtml:select property="section" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SECTION" disabled="true">
										<logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="uld.defaults.section">
														<bean:define id="parameterValues" name="oneTimeValue" property="value" />
															<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																	<logic:present name="parameterValue">
																		<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																		<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																			<logic:equal name="uldDamageChecklistVO" property="section" value="<%=(String)fieldValue%>">
																				<%=(String)fieldDescription%>
																			</logic:equal>
																	</logic:present>
															</logic:iterate>
													</logic:equal>
											</logic:iterate>
										</logic:present>
									</ihtml:select> -->
									<logic:present name="uldDamageChecklistVO" property="section">
									<!-- <bean:write name="uldDamageChecklistVO" property="section"/> -->

									<logic:present name="oneTimeValues">
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
												<logic:equal name="parameterCode" value="uld.defaults.section">
													<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue">
																<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																<logic:equal name="uldDamageChecklistVO" property="section" value="<%=(String)fieldValue%>">
																			
															    <ihtml:text property="section" value ="<%=(String)fieldDescription%>"  componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_SECTION_DISPLAY"  readonly="true"/> 
																

																			<%
																				eachVal = (String)fieldValue;
																			%>
																<input type="hidden" name="sectionCode" value="<%=(String)fieldValue%>" />
																</logic:equal>
															</logic:present>
														</logic:iterate>
												</logic:equal>
										</logic:iterate>
									</logic:present>

						   		</logic:present>


							</td>
							<td  class="iCargoTableDataTd" align="center">
									<logic:present name="uldDamageChecklistVO" property="description">
										<bean:define id="description" name="uldDamageChecklistVO" property="description"/>
											   <ihtml:text property="description"  value ="<%=description.toString()%>"  componentID="TXT_ULD_DEFAULTS_DAMAGECHECKLISTMASTER_DESCRIPTION_DISPLAY" tabindex="6"   readonly="true"/>    
											<!-- <bean:write name="uldDamageChecklistVO" property="description"/>  
											 <ihtml:hidden property="description"   value ="<%=description.toString()%>" /> -->
											<%
												eachVal = eachVal+ String.valueOf(description);
											%>
									</logic:present>
									<logic:notPresent name="uldDamageChecklistVO" property="description">
										 <ihtml:text property="description" value =""  tabindex="6"   />  
										
									</logic:notPresent>
							</td>

								


						    </tr>
						</logic:notPresent>
						</common:rowColorTag>
					</logic:iterate>
				</logic:present>
		</tbody>
		</table>
		</div>
	    </div>
		<div class="ic-row"> 
        <div class="ic-input ic-right ic-split-100px">
        <label>		
		<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.totalpoints" />
	    </label>
		<ihtml:text property="totalPoints" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_TOTALPOINTS" style="text-align:right" name="maintainDamageReportForm"  maxlength="15" readonly="true"/>
        </div>
		</div>
		<div class="ic-row ic-input-round-border"> 
         <div class="ic-row">
		 <div class="ic-input ic-split-25">
         <label>
		 <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.facilitytype" />
		 </label>
	<!-- Modified by A-5170 For ICRD-32241 starts --> 
		<!-- <span class="iCargoMandatoryFieldIcon">*</span></td> -->		                   
		<ihtml:select property="facilityType" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_FACILITYTYPE">
									<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
			<logic:present name="oneTimeValues">
				<logic:iterate id="oneTimeValue" name="oneTimeValues">
					<bean:define id="parameterCode" name="oneTimeValue" property="key" />
											<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
							<bean:define id="parameterValues" name="oneTimeValue" property="value" />
								<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
											<html:option value="<%=(String)fieldValue%>">
												<%=(String)fieldDescription%>
											</html:option>
									</logic:present>
								</logic:iterate>
						</logic:equal>
				</logic:iterate>
			</logic:present>
		</ihtml:select>
		</div>
		<div class="ic-input  ic-split-25">
        <label>
	    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.location" />
		</label>	                    
	    <ihtml:text property="location" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_LOCATION" name="maintainDamageReportForm"  maxlength="15" />
	    <button type="button" class="iCargoLovButton" name="facilitycodelov" id="facilitycodelov"  ></button>
									<!-- <img src="<%= request.getContextPath()%>/images/lov.gif" name="facilitycodelov"  id="facilitycodelov" alt="Party LOV"/> -->
	
        </div>
		<div class="ic-input  ic-split-25">
        <label>
	    <bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.partytype" />
		</label>				<!-- <span class="iCargoMandatoryFieldIcon">*</span> -->
									<ihtml:select property="partyType" componentID="CMB_ULD_DEFAULTS_MAINTAINDMG_PARTYTYPE" >
									<html:option value=""><common:message key="combo.select"/></html:option>
										 <logic:present name="oneTimeValues">
											<logic:iterate id="oneTimeValue" name="oneTimeValues">
												<bean:define id="parameterCode" name="oneTimeValue" property="key" />
												<logic:equal name="parameterCode" value="uld.defaults.PartyType">
												<bean:define id="parameterValues" name="oneTimeValue" property="value" />
													<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
														<logic:present name="parameterValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
															<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
															<html:option value="<%=(String)fieldValue%>">
																<%=(String)fieldDescription%>
															</html:option>
														</logic:present>
													</logic:iterate>
												</logic:equal>
											</logic:iterate>
										</logic:present>
									</ihtml:select>
	    </div>
		<div class="ic-input ic-split-25">
        <label>
		<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.party" />
		</label>		<!-- <span class="iCargoMandatoryFieldIcon">*</span> -->
	
			<ihtml:text property="party" componentID="TXT_ULD_DEFAULTS_MAINTAINDMG_PARTY" name="maintainDamageReportForm" maxlength="15" />			
		<div class="lovImg">			
			<img src="<%= request.getContextPath()%>/images/lov.png" name="partycodelov"  id="partycodelov" alt="Party LOV"/>

		</div>
	   </div>
	  </div>
<!-- Modified by A-5170 For ICRD-32241 ends -->
	  <div class="ic-row">
		<div class="ic-input ic-split-50">
        <label>
		<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindamagereport.lbl.Remarks" /></td>
		</label>			
                
             <ihtml:textarea property="remarks"  componentID="TXT_ULD_DEFAULTS_ADDDAMAGE_REMARKS" rows="2" cols="50" style="width:280px;"/>
        </div>
       </div>		
	  </div>
     </div>
    </div>
    <div class="ic-foot-container">						
	 <div class="ic-row">
	  <div class="ic-button-container">	
            <ihtml:nbutton property="btnOK" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_OK">
				<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.ok" />
			</ihtml:nbutton>
			<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_MAINTAINDMG_DMGCLOSE">
				<bean:message bundle="maintainDamageReportResources" key="uld.defaults.maintaindmgrep.btn.close" />
			</ihtml:nbutton>
       </div>
      </div>
     </div>	
</div>	 
 </ihtml:form>
 </div>
			
		   
	</body>
			</html:html>




