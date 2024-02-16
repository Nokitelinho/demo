<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - CUSTOMER MANAGEMENT
* File Name				:  ListCustomer.jsp
* Date					:  13-Apr-2006
* Author(s)				:  A-2046
*************************************************************************/
 --%>
 <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm"%>
 <%@ page import="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"%>	
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "com.ibsplc.icargo.framework.util.time.LocalDate" %>
<html:html locale="true">
<head>	
		
<title>iCargo : List Customer</title>
<meta name="decorator" content="mainpanelrestyledui" >
<common:include type="script" src="/js/customermanagement/defaults/ListCustomer_Script.jsp"/>
</head>
<body>
	
		
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<!--change form-->


	<bean:define id="form"
	 name="ListCustomerForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm"
	 toScope="page" />

		<business:sessionBean id="statusValues"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.customerlisting"
			method="get" attribute="customerStatus"/>
			
			<business:sessionBean id="OneTimeCustomerType"
	moduleName="shared.customer"
	screenID="shared.customer" 
	method="get"
	attribute="OneTimeCustomerType"/> 

		<business:sessionBean id="customerTypes"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.customerlisting"
			method="get" attribute="customerType"/>
		<business:sessionBean id="filterVO"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.customerlisting"
			method="get" attribute="listFilterVO"/>

		<business:sessionBean id="customers"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.customerlisting"
			method="get" attribute="customerVOs"/>

		<business:sessionBean id="locationType"
		moduleName="shared.area.country"
		screenID="customermanagement.defaults.customerlisting"
		method="get"
		attribute="locationType" />	
		<div id="pageDiv" class="iCargoContent ic-masterbg" style="width:100%;height:100%;overflow:auto;">
		<ihtml:form action="customermanagement.defaults.screenloadlistcustomer.do">
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<input type="hidden" name="mySearchEnabled" /> 

		<ihtml:hidden property="lastPageNumber" />
		<ihtml:hidden property="displayPageNum" />
		<ihtml:hidden property="canRedeem" />
		<ihtml:hidden property="closeStatus" />
		<ihtml:hidden property="screenFlag" />
		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message key="customermanagement.defaults.listcustomer.lbl.listcustomer" scope="request"/>
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-col-100">	
							<div class="ic-input-container">
								<div class="ic-row">
									<div class="ic-input ic-split-20">
										   <label class="ic-label-33">
											<common:message key="customermanagement.defaults.listcustomer.lbl.customercode" scope="request"/>
										   </label>
										<logic:present name="filterVO" property="customerCode">
											<bean:define id="custCode"  name="filterVO" property="customerCode"/>
											<ihtml:text property="custCode" value="<%=(String)custCode%>"
											componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_CUSTCODE"/>
										</logic:present>
										<logic:notPresent name="filterVO" property="customerCode">
											<ihtml:text property="custCode" 
											value="" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_CUSTCODE"/>
										</logic:notPresent>
											<div class="lovImg">
											<img id="customerlov" src="<%= request.getContextPath()%>/images/lov.png"
								 width="22" height="22" />
								 </div>
										
									</div>
									<div class="ic-input ic-split-20">
										<label class="ic-label-33">
											<common:message key="customermanagement.defaults.listcustomer.lbl.locationtype" scope="request"/>
										</label>
										<logic:present name="filterVO" property="locationType">
											<bean:define id="selectedLocationType"  name="filterVO" property="locationType"/>
											<ihtml:select property="locationType" 								componentID="SELECT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_LOCATIONTYPE" value="<%=(String)selectedLocationType%>">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="locationType">									
													<logic:iterate id="locationType" name="locationType">	
														<logic:present name="locationType" property="fieldDescription" >				
															<bean:define id="sFieldDescription" 
															name="locationType" property="fieldDescription"/>			
															<logic:present name="locationType" property="fieldValue">
																<bean:define id="sFieldValue" name="locationType" property="fieldValue"/> 
																<html:option value="<%=(String)sFieldValue%>">
																	<bean:write name="sFieldDescription"/>
																</html:option>
															</logic:present>
														</logic:present>
													</logic:iterate>
												</logic:present>
											</ihtml:select>								
										</logic:present>								 
										<logic:notPresent name="filterVO" property="locationType">
											<ihtml:select 
												property="locationType" componentID="SELECT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_LOCATIONTYPE">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="locationType">									
													<logic:iterate id="locationType" name="locationType">	
														<logic:present name="locationType" property="fieldDescription" >						
															<bean:define id="sFieldDescription" 
															name="locationType" property="fieldDescription"/>			
															<logic:present name="locationType" property="fieldValue">
																<bean:define id="sFieldValue" name="locationType" property="fieldValue"/> 
																<html:option value="<%=(String)sFieldValue%>">
																	<bean:write name="sFieldDescription"/>
																</html:option>
															</logic:present>
														</logic:present>
													</logic:iterate>
												</logic:present>
											</ihtml:select>
										</logic:notPresent>
									</div>
									<div class="ic-input ic-split-20">
										<label class="ic-label-33">
											<common:message key="customermanagement.defaults.listcustomer.lbl.locationvalue" scope="request"/>
										</label>
										<logic:present name="filterVO" property="locationValue">
											<bean:define id="locationValue"  name="filterVO" property="locationValue"/>
											<ihtml:text property="locationValue"  value="<%=(String)locationValue%>"
												componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_LOCATIONVALUE" style="width:55px"/>
										</logic:present>
										<logic:notPresent name="filterVO" property="locationValue">
											<ihtml:text property="locationValue"  value=""
												componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_LOCATIONVALUE" style="width:55px"/>
										</logic:notPresent>
										<div class="lovImg">
										<img height="22" id="locationValueLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
									    </div>
									</div>
									<div class="ic-input ic-split-20">
										<label class="ic-label-20">
											<common:message key="customermanagement.defaults.listcustomer.lbl.status" scope="request"/>
										</label>
										<logic:present name="filterVO" property="activeStatus">
											<bean:define id="activeStatus" name="filterVO" property="activeStatus"/>
											<ihtml:select property="status" 
												value="<%=(String)activeStatus%>" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_STATUS">
												<ihtml:option value="">ALL</ihtml:option>
												<logic:present name="statusValues">
													<bean:define id="status" name="statusValues"/>
													<ihtml:options collection="status" property="fieldValue" labelProperty="fieldDescription"/>
												</logic:present>
											</ihtml:select>
										</logic:present>
										<logic:notPresent name="filterVO" property="activeStatus">
											<ihtml:select property="status" componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_STATUS">
												<ihtml:option value="">ALL</ihtml:option>
												<logic:present name="statusValues">
													<bean:define id="status" name="statusValues"/>
													<ihtml:options collection="status" property="fieldValue" labelProperty="fieldDescription"/>
												</logic:present>
											</ihtml:select>
										</logic:notPresent>
									</div>
									<div class="ic-input ic-split-20">
										<label>
											<common:message key="customermanagement.defaults.listcustomer.lbl.internalAccHolder" scope="request"/>
										</label>
										<logic:present name="filterVO" property="internalAccountHolder">
											<bean:define id="internalAccountHolder"  name="filterVO" property="internalAccountHolder"/>
											<ihtml:text property="internalAccountHolder" value="<%=(String)internalAccountHolder%>" 
											componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_INTACCHOLDER" maxlength="14"/>
										</logic:present>
										<logic:notPresent name="filterVO" property="internalAccountHolder">
											<ihtml:text property="internalAccountHolder" 
											componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_INTACCHOLDER" maxlength="14"/>
										</logic:notPresent>
										<div class="lovImg">
											<img height="22" width="22" src="<%=request.getContextPath()%>/images/lov.png" onclick="displayLOV('showUserLOV.do','N','Y','showUserLOV.do',targetFormName.internalAccountHolder.value,'internalAccountHolder','1','internalAccountHolder','',0)"/>
								</div>
									</div>
								</div>
								<div class="ic-row">
									<div class="ic-input ic-split-20 margint10">
										    <label class="ic-label-20">
											<common:message key="customermanagement.defaults.listcustomer.lbl.iatacode" scope="request"/>
											</label>
											<logic:present name="filterVO" property="iataCode">
											<bean:define id="iata"  name="filterVO" property="iataCode"/>
											<ihtml:text property="iataCode" value="<%=(String)iata%>" 
											componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_IATACODE" maxlength="14"/>
											</logic:present>
											<logic:notPresent name="filterVO" property="iataCode">
												<ihtml:text property="iataCode" 
												value="" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_IATACODE"/>
											</logic:notPresent>
																				
									</div>
									<div class="ic-input ic-split-20 multi-select-wrap margint10">
										    <label class="ic-label-20">
											<common:message key="customermanagement.defaults.listcustomer.lbl.customertype" scope="request"/>
										     </label>
										<ihtml:select 
											multiSelect="true" 
											multiSelectNoneSelectedText="Select" 
											multiSelectMinWidth="100" 
											multiple="multiple" 
											property="custType" 
											componentID="CMP_CUSTOMERMANAGEMENT_DEFAULTS_CUSTTYPE_MULTISELECT" >
											<logic:present  name="customerTypes">							
												<logic:iterate id="groupTypeInFilter" name="customerTypes" >	
													<ihtml:option value= "<%= ((OneTimeVO)groupTypeInFilter).getFieldValue() %>">
														<%= ((OneTimeVO)groupTypeInFilter).getFieldDescription() %>
													</ihtml:option>
												</logic:iterate>
											</logic:present>
										</ihtml:select>
									</div>
									<div class="ic-input ic-split-20 margint10">
										<label class="ic-label-33">
											<common:message  key="customermanagement.defaults.listcustomer.lbl.expiringbefore" scope="request"/>
										</label>
										<ibusiness:calendar id="expiringBefore" 
											componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_EXPIRINGBEFORE"
											property="expiringBefore" type="image" />
									</div>
									<div class="ic-input ic-split-20 margint20">
										<ihtml:checkbox property="cassAgent" componentID="CHK_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_CASSAGENT"  />
											<common:message key="customermanagement.defaults.listcustomer.lbl.cassagent" scope="request"/>
									</div>
									<div class="ic-input ic-split-20 margint20">
										<logic:equal name="ListCustomerForm" property="clearingAgentFlag" value="true">
											<input type="checkbox" property="clearingAgentFlag" title="Clearing Agent " name=	"clearingAgentFlag" checked/>
										</logic:equal>
										<logic:notEqual name="ListCustomerForm" property="clearingAgentFlag" value="true">
											<input type="checkbox" property="clearingAgentFlag" title="Clearing Agent " name="clearingAgentFlag" />
										</logic:notEqual>
										<label class="ic-inline">
											<common:message key="customermanagement.defaults.listcustomer.lbl.clearingAgentFlag"/>
										</label>
									</div>
									</div>
									<div class="ic-row">
										<div class="ic-button-container">
											<ihtml:nbutton property="btnList" 
												accesskey ="L" 	componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_LIST">
												<common:message key="customermanagement.defaults.listcustomer.btn.list" scope="request"/>
											</ihtml:nbutton>
											<ihtml:nbutton property="btnClear" 
												accesskey ="C" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_CLEAR">
												<common:message key="customermanagement.defaults.listcustomer.btn.clear" scope="request"/>
											</ihtml:nbutton>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			<div class="ic-main-container">
				<div class="ic-row">
					<h4><common:message key="customermanagement.defaults.listcustomer.lbl.custdetails" scope="request"/></h4>
				</div>
				<div class="ic-row" id="listTable">
					<div class="ic-col-100">
						<div class="ic-row">
							<div class="ic-col-60">
								<logic:present name="customers">
						            <common:paginationTag pageURL="javascript:submitList('lastPageNum','displayPage')"
										name="customers"
										display="label"
										labelStyleClass="iCargoResultsLabel"
										lastPageNum="<%=((ListCustomerForm)form).getLastPageNumber() %>" />
								</logic:present>							    
							</div>
							<div class="ic-col-40 paddR5">
								<div class="ic-button-container">
									<logic:present name="customers">
										<common:paginationTag
											pageURL="javascript:submitList('lastPageNum','displayPage')"
											name="customers"
											display="pages"
											linkStyleClass="iCargoLink"
											disabledLinkStyleClass="iCargoLink"
											lastPageNum="<%=((ListCustomerForm)form).getLastPageNumber()%>" 
											columnSelector="true"
											exportToExcel="true"
											exportTableId="listCustomerTable"
											exportAction="customermanagement.defaults.listcustomerdetails.do"
											pageNumberFormAttribute="displayPageNum"
											tableId="listCustomerTable"/>
									</logic:present>									
									<logic:notPresent name="customers">
									&nbsp;<common:columnSelector name="listCustomerTable" tableId="listCustomerTable" id="listCustomerTable" treetype="false"/>
									</logic:notPresent>										
								</div>
							</div>
						</div>
						<div class="ic-row">
							<div id="tableDiv1" class="tableContainer" style="height:500px;">
								<table id="listCustomerTable" class="fixed-header-table" >									
						            <thead>
										<tr class="iCargoTableHeadingLeft">
											<td width="4%" class="ic-center" data-ic-csid="td1">											
												<input type="checkbox" name="masterRowId" 
													onclick="updateHeaderCheckBox(this.form,this.form.masterRowId,this.form.check)"/>
												<span></span>
											</td>
											<td width="10%" data-ic-csid="td2">
												<common:message key="customermanagement.defaults.listcustomer.lbl.customertype" 				scope="request"/>								<span></span>
											</td>
											<td width="12%" data-ic-csid="td3">
												<common:message key="customermanagement.defaults.listcustomer.lbl.customercode" scope="request"/>								<span></span>
											</td>
											<td width="12%" data-ic-csid="td4">
												<common:message key="customermanagement.defaults.listcustomer.lbl.custname" scope="request"/>												 <span></span>
											</td>
											<td  width="12%" data-ic-csid="td5">
												<common:message key="customermanagement.defaults.listcustomer.lbl.accountno" scope="request"/>												  <span></span>
											</td>
											<td  width="10%" data-ic-csid="td6">
												<common:message key="customermanagement.defaults.listcustomer.lbl.iatacode" scope="request"/>												 <span></span>
											</td>
											<td  width="12%" data-ic-csid="td7">
												<common:message key="customermanagement.defaults.listcustomer.lbl.validFrom" scope="request"/>												 <span></span>
											</td>
											<td  width="12%" data-ic-csid="td8">
												<common:message key="customermanagement.defaults.listcustomer.lbl.validTo" scope="request"/>												 <span></span>
											</td>
											<td  width="6%" data-ic-csid="td9"> 
												<common:message key="customermanagement.defaults.listcustomer.lbl.country" scope="request"/>												<span></span>
											</td>
											<td width="6%" data-ic-csid="td10">
												<common:message key="customermanagement.defaults.listcustomer.lbl.station" scope="request"/>												<span></span>
											</td>
											<td width="8%" data-ic-csid="td11">
												<common:message key="customermanagement.defaults.listcustomer.lbl.status" scope="request"/>												   <span></span>
											</td>
											<td width="10%" data-ic-csid="td12">
												<common:message key="customermanagement.defaults.listcustomer.lbl.tel" scope="request"/>												<span></span>
											</td>
											<td width="10%" data-ic-csid="td13">
												<common:message key="customermanagement.defaults.listcustomer.lbl.fax" scope="request"/>												<span></span>
											</td>
										</tr>
									</thead>
									<tbody>
										<logic:present name="customers">
											<logic:iterate id="customerVO" name="customers" 
												indexId="nIndex" type="com.ibsplc.icargo.business.shared.customer.vo.CustomerVO">
												<tr>
													<td class="iCargoTableDataTd ic-center" data-ic-csid="td1">
														<input type="checkbox" name="check" property="check" 
value="<%=nIndex.toString()%>" onclick="toggleTableHeaderCheckbox('check',this.form.masterRowId)"/>
													</td>
													<td class="iCargoTableDataTd" data-ic-csid="td2">
														<%!String custType=""; %>
														<logic:present name="customerVO" property="customerType">
															<bean:define id="customerType" name="customerVO" property="customerType"/>
																<logic:present name="customerTypes">
																	<logic:iterate id="customerTypeOneTime" name="customerTypes">
																		<bean:define id="fieldVal" 
																		name="customerTypeOneTime" property="fieldValue" />
																		<bean:define id="fieldDesc" name="customerTypeOneTime" 					property="fieldDescription" />
																		<%if((String.valueOf(fieldVal)).equals(String.valueOf(customerType))) custType = (String.valueOf(fieldDesc).toUpperCase());%>
																	</logic:iterate>							
																</logic:present>							
																<%= custType%>
														</logic:present> 	
													</td>
													<td  class="iCargoTableDataTd" data-ic-csid="td3">
														<ihtml:hidden property="customerCodes" value="<%=customerVO.getCustomerCode()%>"/>
														<input type="hidden" name="statusvalues" value="<%=customerVO.getStatus()%>"/>
														<logic:present name="customerVO" property="customerCode">
															<bean:write name="customerVO" property="customerCode"/>
														</logic:present>
													</td>
													<td  class="iCargoTableDataTd" data-ic-csid="td4">
														<logic:present name="customerVO" property="customerName">
															<bean:write name="customerVO" property="customerName"/>
														</logic:present>														
													</td>
													<td  class="iCargoTableDataTd" data-ic-csid="td5">
														<logic:present name="customerVO" property="accountNumber">
															<bean:write name="customerVO" property="accountNumber"/>
														</logic:present>
													</td>
													<td  class="iCargoTableDataTd" data-ic-csid="td6">
														<logic:present name="customerVO" property="iataCode">
															<bean:write name="customerVO" property="iataCode"/>
														</logic:present>
													</td>
													<td class="iCargoTableDataTd" data-ic-csid="td7">
														<logic:present name="customerVO" property="validFrom">
														<bean:define id="validFrom" name="customerVO" property="validFrom" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
														<%String validF=TimeConvertor.toStringFormat(validFrom.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
														<%=validF%>
														</logic:present>
													</td><td class="iCargoTableDataTd" data-ic-csid="td8">
														<logic:present name="customerVO" property="validTo">
														<bean:define id="validTo" name="customerVO" property="validTo" type="com.ibsplc.icargo.framework.util.time.LocalDate" />
														<%String validT=TimeConvertor.toStringFormat(validTo.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);%>
														<%=validT%>
														</logic:present>
													</td>
													<td  class="iCargoTableDataTd"data-ic-csid="td9">
														<logic:present name="customerVO" property="country">
															<bean:write name="customerVO" property="country"/>
														</logic:present>
													</td>
													<td  class="iCargoTableDataTd"data-ic-csid="td10">
														<logic:present name="customerVO" property="stationCode">
															<bean:write name="customerVO" property="stationCode"/>
														</logic:present>
													</td>
													<td  class="iCargoTableDataTd"data-ic-csid="td11">
														<logic:present name="customerVO" property="status">
															<bean:define id="status" name="customerVO" property="status"/>
															<logic:present name="statusValues">
																<bean:define id="statusType" name="statusValues"/>
																<logic:iterate id="actualStatus" name="statusType">
																	<bean:define id="onetimevo" name="actualStatus" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO"/>
																	<%if(onetimevo.getFieldValue().equals(status)){%>
																		<bean:write name="onetimevo" property="fieldDescription"/>
																	<%}%>
																</logic:iterate>
															</logic:present>
														</logic:present>
													</td>
													<td class="iCargoTableDataTd"data-ic-csid="td12">
														<logic:present name="customerVO" property="telephone">
															<bean:write name="customerVO" property="telephone"  />
														</logic:present>
													</td>
													<td class="iCargoTableDataTd"data-ic-csid="td13">
														<logic:present name="customerVO" property="fax">
															<bean:write name="customerVO" property="fax" />
														</logic:present>
													</td>													
												</tr>
											</logic:iterate>
										</logic:present>
									</tbody>
								</table>
							</div>
							<jsp:include page="/jsp/includes/columnchooser/columnchooser.jsp"/>
						</div>
					</div>					
				</div>
			</div>
			<div class="ic-foot-container paddR5">
				<div class="ic-row">
					<div class="ic-button-container">
						<ihtml:nbutton property="btnUploadTSA" accesskey ="U" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_UPLOADTSA">
							<common:message key="customermanagement.defaults.listcustomer.btn.uploadtsa" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnPrint" accesskey ="P" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_PRINT">
							<common:message key="customermanagement.defaults.listcustomer.btn.print" scope="request"/>
						</ihtml:nbutton>
					    <ihtml:nbutton property="btnActivate" accesskey ="I" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_ACTIVATE">
							<common:message key="customermanagement.defaults.listcustomer.btn.activate" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnInactivate" accesskey ="V" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_INACTIVATE">
							<common:message key="customermanagement.defaults.listcustomer.btn.inactivate" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnBlacklist" accesskey ="B" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_BLACKLIST">
							<common:message key="customermanagement.defaults.listcustomer.btn.blacklist" scope="request"/>
						</ihtml:nbutton>
					    <ihtml:nbutton property="btnRevoke" accesskey ="R" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_REVOKE">
					    	<common:message key="customermanagement.defaults.listcustomer.btn.revoke" scope="request"/>
						</ihtml:nbutton>							
						<ihtml:nbutton property="btnCreate" accesskey ="R" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_CREATE">
							<common:message key="customermanagement.defaults.listcustomer.btn.create" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnDetails" accesskey ="D" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_DETAILS">
							<common:message key="customermanagement.defaults.listcustomer.btn.details" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnclose" accesskey ="O" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_LISTCUST_CLOSE">
							<common:message key="customermanagement.defaults.listcustomer.btn.close" scope="request"/>
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>
	</ihtml:form>
</div>

	</body>
</html:html>
