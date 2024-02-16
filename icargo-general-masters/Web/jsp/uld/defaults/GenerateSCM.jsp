<%--
* Project	 		: iCargo
* Module Code & Name: ULD:Generate SCM
* File Name			: GenerateSCM.jsp
* Date				: 01/08/06
* Author(s)			: Anitha George M : A-1862
--%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm" %>
 <%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>

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
<%@ include file="/jsp/includes/customcss.jsp" %>
<title>

<bean:message bundle="generatescmResources" key="uld.defaults.messaging.generatescm.generatescmTitle" scope="request"/>

</title>
<meta name="decorator" content="mainpanel">
<common:include type="script" src="/js/uld/defaults/GenerateSCM_Script.jsp"/>

</head>

<body id="bodyStyle">


<div id="pageDiv" class="iCargoContent ic-masterbg" >
<ihtml:form action="/uld.defaults.messaging.screenloadgeneratescm.do">
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<bean:define id="form"
	 name="GenerateSCMForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.GenerateSCMForm"
	 toScope="page" />
	<business:sessionBean id="systemStock"
		   moduleName="uld.defaults"
			screenID="uld.defaults.generatescm" method="get"
		attribute="systemStock"/>
		
	<business:sessionBean id="newSystemStock"
		   moduleName="uld.defaults"
			screenID="uld.defaults.generatescm" method="get"
		attribute="newSystemStock"/>

<business:sessionBean id="facilityType"
moduleName="uld.defaults"
screenID="uld.defaults.generatescm"
method="get" attribute="facilityType" />

<business:sessionBean id="oneTimeValues"
moduleName="uld.defaults"
screenID="uld.defaults.generatescm"
method="get" attribute="uldStatusList" />

<ihtml:hidden property="screenStatusFlag"/>
<ihtml:hidden property="missingFlag" />
<ihtml:hidden property="totalRecords" />
<ihtml:hidden property="pageURL"/>
<ihtml:hidden property="listStatus" />
<ihtml:hidden property="airportDisable" />
<ihtml:hidden property="lastPageNum" />
<ihtml:hidden property="displayPage" />
<ihtml:hidden property="msgFlag" />
<ihtml:hidden property="defaultComboValue" />
<ihtml:hidden property="locationName" />
<ihtml:hidden property="changeStatusFlag" />
<ihtml:hidden property="listFromStock" />
<ihtml:hidden property="fromFinalized" />

<ihtml:hidden property="listedDynamicQuery"  />
<%  GenerateSCMForm frm = (GenerateSCMForm)request.getAttribute("GenerateSCMForm");%>
<logic:present name="oneTimeValues">
	<bean:define id="oneTimeValues" name="oneTimeValues" type="java.util.ArrayList" />
</logic:present>

<div class="ic-content-main">
                  <span class="ic-page-title ic-display-none">
				  <common:message key="uld.defaults.messaging.GenerateSCM" scope="request"/>
				  </span>
				  
                  <div class="ic-head-container">
				 
						<div class="ic-filter-panel">
						<div class="ic-input-container">
						
						<div class="ic-row ic-round-border">
							<div class="ic-input ic-split-18 ic-label-33 ic-mandatory">
							<label><common:message key="uld.defaults.messaging.airline" scope="request"/></label>
            <ihtml:text property="scmAirline"  componentID="TXT_ULD_DEFAULTS_GENERATESCM_AIRLINE" maxlength="3" />
			<div class="lovImg">
			<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" id="airlinelov" name="airlinelov" alt="Airline LOV"/>
            </div>
            </div>
				            <div class="ic-input ic-split-18 ic-mandatory">
							<label><common:message key="uld.defaults.messaging.airport" scope="request"/></label>
            <ihtml:text property="scmAirport"  componentID="TXT_ULD_DEFAULTS_GENERATESCM_AIRPORT" maxlength="3"/>
			<div class="lovImg">
			<img src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" id="airportlov" name="airportlov" alt="Airport LOV"/>
			</div>
            </div>
				            <div class="ic-input ic-split-20 ic-mandatory">
							<label><common:message key="uld.defaults.messaging.stockcheck" scope="request"/></label>
            <ibusiness:calendar componentID="TXT_ULD_DEFAULTS_GENERATESCM_STOCKCHKDATE" id="scmStockCheckDate" property="scmStockCheckDate" type="image"/>
           </div>
				            <div class="ic-input ic-split-18 ic-label-33">
							<label>Time</label>
            <ibusiness:releasetimer  property="scmStockCheckTime" title="Stock Check Time"  type="asTimeComponent" id="stkChkTime"  />
           </div>
				            <div class="ic-button-container paddR5">
             <ihtml:nbutton property="btnList" componentID="BTN_ULD_DEFAULTS_GENERATESCM_LIST" accesskey="L" >
			<common:message key="uld.defaults.messaging.btnlist" scope="request"/>
			</ihtml:nbutton>
			<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_GENERATESCM_CLEAR" accesskey="C" >
			<common:message key="uld.defaults.messaging.btnclear" scope="request"/>
			</ihtml:nbutton>
			</div>
         </div>
        
		 </div>
		 </div>
		</div>
		<div class="ic-main-container" id="generateSCMMainContainer">
					<div class="ic-row ic-right">
					<%String textColor="";
							textColor="red";%>
					<div class="ic-bold-label ic-right" style="color:<%=textColor%>;">
						<logic:equal name="form" property="scmMessageSendingFlag" value="D">
							<label><common:message key="uld.defaults.messaging.stock.check.msg" /></label>
						</logic:equal>
					</div>
					</div>
					<div class="ic-row">
					<div class="ic-bold-label ic-input ic-split-50">
				<div class="ic-row marginL10">
				<label><common:message key="uld.defaults.generatescm.stkdtls" /></label>
				</div>
				 <div class="ic-border marginT5">
		 <div class="ic-input ic-split-30 ic-label-40">
						<label><common:message key="uld.defaults.messaging.uldno" scope="request"/></label>
						<ihtml:text property="uldNumberStock" componentID="CMB_ULD_DEFAULTS_GENERATESCM_ULDNO" maxlength="20" />
						</div>
						<div class="ic-input ic-split-40 ic-label-33 multi-select-wrap">
						<label><common:message key="uld.defaults.messaging.status" scope="request"/></label>
																<ihtml:select multiSelect="true"  multiSelectNoneSelectedText="Select" multiSelectMinWidth="180" multiple="multiple" property="uldStatus" componentID="CMB_ULD_DEFAULTS_GENERATESCM_STATUS" size="3">
							 <%
								String uldStatus = ((GenerateSCMForm)form).getUldStatus();
							  %>
							<logic:present name="oneTimeValues">
								<logic:iterate id="parameterValue" name="oneTimeValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
									<logic:present name="parameterValue" property="fieldValue">
										<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
										<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
							  <%
											if(uldStatus!=null && uldStatus.contains((String)fieldValue)){
							  %>
											<option value="<%=(String)fieldValue%>" selected="selected"><%=(String)fieldDescription%></option>
							  <%
										}else{
							  %>
												<option value="<%=(String)fieldValue%>" ><%=(String)fieldDescription%></option>
							  <%
										}
							  %>
										
									</logic:present>
								</logic:iterate>
							</logic:present>
						</ihtml:select>
					</div>
					<div class="ic-button-container marginT10 paddR5">
						<ihtml:nbutton property="btnStockList" componentID="BTN_ULD_DEFAULTS_STOCKCHECK_LIST" accesskey="I" >
							<common:message key="uld.defaults.messaging.btnlist" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton styleClass="secondary iCargoButtonSmall" property="btnStockClear" componentID="BTN_ULD_DEFAULTS_STOCKCHECK_CLEAR" accesskey="E" >
							<common:message key="uld.defaults.messaging.btnclear" scope="request"/>
						</ihtml:nbutton>
					</div>
						<div class="ic-input ic-split-100">
						<label><common:message key="uld.defaults.generatescm.customsearch"/></label>
						<common:dynamicSearch id="dynamicsearch" liteMode="true" value="<%=form.getDynamicQuery()%>" tabindex="27" componentID="TXT_CAPACITY_BOOKING_STANDARD_LISTBOOKINGS_DYNAMICSEARCH"
						displayName="DynamicSearch" cols="180" rows="2"/>
				</div>
				</div>
				
				<div class="ic-row marginT5" id="_paginationResultsLabel" style="display: inline-block">
				
				<%String lstPgNo = "";%>
				<div class="ic-col-30 marginT10" style="margin-left:-2px;">
				<logic:present name="GenerateSCMForm" property="lastPageNum">
					<bean:define id="lastPg" name="GenerateSCMForm" property="lastPageNum" scope="request"  toScope="page" />
					<!--bean:write name="GenerateSCMForm" property="lastPageNum" scope="request"  toScope="page" /-->
					<%
						lstPgNo = (String) lastPg;
					%>

				  </logic:present>
			      <logic:present name="systemStock" >
			      <bean:define id="pageObj" name="systemStock"  />
				  <common:paginationTag pageURL="uld.defaults.messaging.listgeneratescm.do"
							name="pageObj"
							display="label"
							labelStyleClass="iCargoResultsLabel"
							lastPageNum
							="<%=lstPgNo%>" />
							</logic:present>
				<!-- removed closing div from here as part of ICRD-193996 BY A-7426 -->	
				</div>
				<div class="ic-button-container" id="_paginationLink">
				 <logic:present name="systemStock" >
			             <bean:define id="pageObj1" name="systemStock"  />
			     		<common:paginationTag
			     		  linkStyleClass="iCargoLink"
			   		  pageURL="javascript:submitList('lastPageNum','displayPage')"
			   		  name="pageObj1"
			   		  display="pages"
					  
					  disabledLinkStyleClass="iCargoLink"
			   		  lastPageNum="<%=lstPgNo%>"/>
			          </logic:present>
				</div>
				
				
				</div>	<!-- added closing div here as part of ICRD-193996 BY A-7426 -->	
				
				
				
				
				<div class="ic-row">
                  
					
                 <div class="tableContainer marginL5" id="div1" style="height:465px;">
					 <table class="fixed-header-table" id="ULDTable">
					<thead >
						<tr class="iCargoTableHeadingLeft">
						 
						<td width="5%"><input type="checkbox" name="checkSysStockAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.checkSysStockAll,this.form.selectedSysStock)" tabindex="35" /></td>
						<td><common:message key="uld.defaults.messaging.uldno" scope="request"/></td>						
						<td><common:message key="uld.defaults.messaging.status" scope="request"/></td>
						<td><common:message key="uld.defaults.messaging.latestscmsent" scope="request"/></td>
						</tr>
					</thead>
					<% int rowID = 0;%>
					<tbody>
					<%int count=0;%>
						<logic:present name="systemStock" >
							<bean:define id="ULDVO" name="systemStock"  />
								<logic:iterate id="uldVO" name="ULDVO" indexId="sysstockindex" type="com.ibsplc.icargo.business.uld.defaults.vo.ULDVO">
									
										<tr>

												<ihtml:hidden property="scmStatusFlags"  value="<%=uldVO.getScmStatusFlag()%>"/>
												<ihtml:hidden property="facilityType"  value=""/>	
												<ihtml:hidden property="locations"  value=""/>	
																						
												<td  class="iCargoTableDataTd ic-center"><input type="checkbox" name="selectedSysStock" value="<%=String.valueOf(sysstockindex)%>" onclick="toggleTableHeaderCheckbox('selectedSysStock',this.form.elements.checkSysStockAll)"/>
												</td>
												<td class="iCargoTableDataTd" >
													<logic:present name="uldVO" property="scmStatusFlag">
														<bean:define id="statusValue" name="uldVO" property="scmStatusFlag" />
														<logic:equal name="statusValue" value="S">
														<ihtml:text property="extrauld" indexId="sysstockindex" styleId="extrauld" readonly="true" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" value="<%=uldVO.getUldNumber()%>"/>
														</logic:equal>
													<logic:equal name="statusValue" value="F">
											<ihtml:text property="extrauld" indexId="sysstockindex" styleId="extrauld" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" value="<%=uldVO.getUldNumber()%>"/>
										</logic:equal>
								<logic:equal name="statusValue" value="M">
									<ihtml:text property="extrauld" readonly="true" indexId="sysstockindex" styleId="extrauld"  styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" value="<%=uldVO.getUldNumber()%>"/>
								</logic:equal>
						</logic:present>
						</td>
						<td class="iCargoTableDataTd">
							<logic:present name="uldVO" property="scmStatusFlag">
								<bean:define id="statusValue" name="uldVO" property="scmStatusFlag" />
								<logic:equal name="statusValue" value="S">
									<common:message key="uld.defaults.generatescm.sysstock" />
									<ihtml:hidden property="operationFlag"  value=""/>
								</logic:equal>
								<!--<logic:equal name="statusValue" value="F">
									<common:message key="uld.defaults.generatescm.found" />
									<ihtml:hidden property="operationFlag"  value="I"/>
								</logic:equal>-->

									<logic:equal name="statusValue" value="M">
										<common:message key="uld.defaults.generatescm.missing" />
										<ihtml:hidden property="operationFlag"  value="U"/>
									</logic:equal>
									<logic:equal name="statusValue" value="F">
										<common:message key="uld.defaults.generatescm.sighted" />
										<ihtml:hidden property="operationFlag"  value="F"/>
									</logic:equal>
							</logic:present>
						</td>
									<td class="iCargoTableDataTd" >
													<logic:present name="uldVO" property="stockCheckDate">
														<bean:define id="stockCheckDate" name="uldVO" property="stockCheckDate" />
														<logic:equal name="statusValue" value="S">
														<ihtml:hidden property="stockCheckDate"  indexId="sysstockindex" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12"/>
														<%
			  								String sendscm ="";
			  								if(uldVO.getStockCheckDate() != null) {
			  								sendscm = TimeConvertor.toStringFormat(
			  											uldVO.getStockCheckDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
			  								}
														%>
			  							<%=sendscm%>							
														</logic:equal>
													<logic:equal name="statusValue" value="F">
											<ihtml:hidden property="stockCheckDate"   indexId="sysstockindex" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12"/>
										<%
											String sendscm ="";
			  								if(uldVO.getStockCheckDate() != null) {
			  								sendscm = TimeConvertor.toStringFormat(
			  											uldVO.getStockCheckDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
			  								}
			  							%>
			  							<%=sendscm%>	
										</logic:equal>
								<logic:equal name="statusValue" value="M">
									<ihtml:hidden property="stockCheckDate"  indexId="sysstockindex" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12"/>
									<%
											String sendscm ="";
			  								if(uldVO.getStockCheckDate() != null) {
			  								sendscm = TimeConvertor.toStringFormat(
			  											uldVO.getStockCheckDate().toCalendar(),"dd-MMM-yyyy HH:mm:ss");
			  								}
			  							%>
			  							<%=sendscm%>	
								</logic:equal>
						</logic:present>
						</td>
						</tr>
						<%count++;%>
						
						</logic:iterate>

						</logic:present>						
					</tbody>
					</table>
                    </div>
					</div>
					</div>
               
                
				
			<div class="ic-bold-label ic-input ic-split-50 ">
			<div class="ic-row">
			<div class="ic-col ic-split-85 marginT10">
            <label><common:message key="uld.defaults.generatescm.foundulddtls" /></label>
			</div>
			<div class="ic-col ic-split-15">
			
							<div class="ic-button-container paddR5" style="margin-right:-1px;">
															
									<a href="#" class="iCargoLink" id="addUld" onclick="addExtraUld()" style="vertical-align:middle;" >Add</a> <span>|</span>
									<a href="#" class="iCargoLink" id="deleteUld" onclick="onClickDelete()" style="vertical-align:middle;">Delete</a>
				 

							</div>
						
						</div>
						</div>
			<div class="ic-row">
		
			 <div class="tableContainer" id="div2" style="height:545px;">
								 <table class="fixed-header-table" id="ULDTable">
								<thead >
									<tr class="iCargoTableHeadingLeft">
									
									<td width="5%"><input type="checkbox" name="checkSysAddStockAll" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.checkSysAddStockAll,this.form.selectedAddSysStock)" tabindex="35" /></td>
									<td><common:message key="uld.defaults.messaging.uldno" scope="request"/></td>
									<td>Facility Type</td>
									<td>Location</td>						
									</tr>
								</thead>
								
								
					<tbody id="scmTableBody">
					<%int addCount=0;%>
						<logic:present name="newSystemStock" >
							<bean:define id="ULDVO" name="newSystemStock"  />
								<logic:iterate id="uldVO" name="ULDVO" indexId="sysaddstockindex" type="com.ibsplc.icargo.business.uld.defaults.vo.ULDVO">
									
									<logic:notEqual name="uldVO" property="operationalFlag" value="D">
										<tr>													
												<ihtml:hidden property="newOperationFlag"  value="U"/>
																						
												<td  class="iCargoTableDataTd ic-center"><input type="checkbox" name="selectedAddSysStock" value="<%=String.valueOf(sysaddstockindex)%>" onclick="toggleTableHeaderCheckbox('selectedAddSysStock',this.form.elements.checkSysAddStockAll)"/> 
												</td>
												<td class="iCargoTableDataTd" >
													<logic:present name="uldVO" property="scmStatusFlag">
														<bean:define id="statusValue" name="uldVO" property="scmStatusFlag" />														
															<logic:equal name="statusValue" value="F">
																<ihtml:text property="newuld" indexId="sysaddstockindex" styleId="newuld" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" value="<%=uldVO.getUldNumber()%>" readonly="true"/>
															</logic:equal>
															<logic:equal name="statusValue" value="N">
																<ihtml:text property="newuld" indexId="sysaddstockindex" styleId="newuld" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" value="<%=uldVO.getUldNumber()%>" readonly="true"/>
															</logic:equal>
													</logic:present>	
												</td>
												<td class="iCargoTableDataTd">
													<logic:present name="uldVO" property="facilityType">
														<bean:define id="newfacilityType" name="uldVO" property="facilityType" />														
														<logic:present name="facilityType">														
															<ihtml:select property="newFacilityType"  indexId="sysaddstockindex" styleClass="iCargoMediumComboBox" value="<%=(String)newfacilityType%>">
															<ihtml:option value="">---Select----</ihtml:option>
															<bean:define id="contents" name="facilityType"/>
															<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription"/>
															</ihtml:select>
															</logic:present>
														<logic:notPresent name="facilityType">	
															<ihtml:select property="newFacilityType"  indexId="sysaddstockindex" styleClass="iCargoMediumComboBox" >
																<ihtml:option value="">---Select----</ihtml:option>
															</ihtml:select>
														</logic:notPresent>
													</logic:present>													
													<logic:notPresent name="uldVO" property="facilityType">
													<logic:present name="facilityType">														
															<ihtml:select property="newFacilityType"  indexId="sysaddstockindex" styleClass="iCargoMediumComboBox" value="">
															<ihtml:option value="">---Select----</ihtml:option>
															<bean:define id="contents" name="facilityType"/>
															<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription"/>
															</ihtml:select>
														</logic:present>
														<logic:notPresent name="facilityType">	
															<ihtml:select property="newFacilityType"  indexId="sysaddstockindex" styleClass="iCargoMediumComboBox" >
																<ihtml:option value="">---Select----</ihtml:option>
															</ihtml:select>
														</logic:notPresent>
													</logic:notPresent>										
												</td>					
												<td class="iCargoTableDataTd">
													<logic:present name="uldVO" property="location">
														<bean:define id="newlocation" name="uldVO" property="location" />
														<logic:equal name="uldVO" property="operationalFlag" value="I">
															<ihtml:text property="newLocations" indexId="sysaddstockindex" styleId="newLocations" styleClass="iCargoEditableTextFieldRowColor1"  maxlength="15" style="width:60px; height:25px;" value="<%=(String)newlocation%>"/>
															<div class="lovImg">
															<img name="locationlovs" id="locationlovs" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" onclick="populateLocationLov(<%=addCount%>);" alt="Location LOV"/>
															</div>
														</logic:equal>
													</logic:present>
													<logic:notPresent name="uldVO" property="location">	
													<ihtml:text property="newLocations" indexId="sysaddstockindex" styleId="newLocations" styleClass="iCargoEditableTextFieldRowColor1"  maxlength="15" style="width:60px; height:25px;" value=""/>
													<div class="lovImg">
														<img name="locationlovs" id="locationlovs" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" onclick="populateLocationLov(<%=addCount%>);" alt="Location LOV"/>
														</div>
													</logic:notPresent>
												</td>																			
											</tr>
						<%addCount++;%>
						</logic:notEqual>
						
						</logic:iterate>
						</logic:present>
						<bean:define id="templateRowCount" value="0"/>
						<tr template="true" id="scmTemplateRow" style="display:none">
							<ihtml:hidden property="tempOperationFlag"  value="I"/>
							<ihtml:hidden property="newOperationFlag"  value="NOOP"/>													
							<td  class="iCargoTableDataTd ic-center" width="5%" style="text-align:right;"><input type="checkbox" name="selectedAddSysStock" value="<%=addCount%>"/>
							</td>
							<td class="iCargoTableDataTd" >
									<ihtml:text property="newuld"  styleId="tempExtrauld" indexId="templateRowCount" styleClass="iCargoEditableTextFieldRowColor1" maxlength="12" value=""/>
							</td>
							<td class="iCargoTableDataTd">
								<logic:present name="facilityType">
								<ihtml:select property="newFacilityType" styleClass="iCargoMediumComboBox">
								<ihtml:option value="">---Select----</ihtml:option>
								<bean:define id="contents" name="facilityType"/>
								<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription"/>
								</ihtml:select>
								</logic:present>
							</td>
							<td>
								<ihtml:text property="newLocations" name="GenerateSCMForm" componentID="TXT_ULD_DEFAULTS_GENERATESCM_LOCATION"  maxlength="15" style="width:60px; height:25px;" value=""/>
								<div class="lovImg">
								<img name="locationlov" id="locationlov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" border="0" alt="Location LOV"/>
								</div>
							</td>							
						</tr>
					</tbody>
				</table>
				</div>
				
				</div>
            </div>
			</div>
              <div class="ic-row">
	         <div class="ic-input ic-split-50">
			<div class="ic-button-container">
			   <ihtml:nbutton property="btnSighted"  componentID="BTN_ULD_DEFAULTS_GENERATESCM_SIGHTED" accesskey="K" >
				<label><common:message key="uld.defaults.generatescm.btn.sighted" /></label>
			  </ihtml:nbutton>
				<ihtml:nbutton property="btnMark"  componentID="BTN_ULD_DEFAULTS_GENERATESCM_MARK" accesskey="M" >
				<common:message key="uld.defaults.generatescm.mark" />
			  </ihtml:nbutton>
				<ihtml:nbutton property="btnReturn"  componentID="BTN_ULD_DEFAULTS_GENERATESCM_RETURN" accesskey="R" >
				<common:message key="uld.defaults.generatescm.return" />
			  </ihtml:nbutton>
				</div>
				</div>
				</div>
             <div class="ic-row"> 
			 <div class="ic-split-50 ic-label-8 booked_flight marginL5">
			 <label><common:message key="uld.defaults.generatescm.remarks" /></label>
					<ihtml:textarea property="remarks" maxlength="61" componentID="TXT_ULD_DEFAULTS_GENERATESCM_REMARKS"  value="" cols="60" rows="2"></ihtml:textarea>
				</div>
				</div>
				</div>
				<div class="ic-foot-container">
				<div class="ic-row">
                <div class="ic-button-container paddR5">
					  <ihtml:nbutton property="btnSaveASDraft" componentID="BTN_ULD_DEFAULTS_GENERATESCM_SAVEAS_DRAFT" accesskey="S" >
					  <common:message key="uld.defaults.messaging.btn.saveasdraft" scope="request"/>
					  </ihtml:nbutton>
					  <ihtml:nbutton property="btnStockCheck" componentID="BTN_ULD_DEFAULTS_GENERATESCM_STOCK_CHECK" accesskey="F" >
					  <common:message key="uld.defaults.messaging.btn.stockcheck" scope="request"/>
					  </ihtml:nbutton>
					  <ihtml:nbutton property="btnSendSCM" componentID="BTN_ULD_DEFAULTS_GENERATESCM_SEND_SCM" accesskey="N" >
					  <common:message key="uld.defaults.messaging.btn.sendscm" scope="request"/>
					  </ihtml:nbutton>
					  <!--
						<ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_GENERATESCM_SAVE" accesskey="S" >
					  <common:message key="uld.defaults.messaging.btnsave" scope="request"/>
					  </ihtml:nbutton> -->
						<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_GENERATESCM_CLOSE" accesskey="O" >
					  <common:message key="uld.defaults.messaging.btnclose" scope="request"/>
					  </ihtml:nbutton>

				</div>
				</div>
			 
				</div>
				</div>
               </ihtml:form>
               </div>
	</body>
</html:html>

