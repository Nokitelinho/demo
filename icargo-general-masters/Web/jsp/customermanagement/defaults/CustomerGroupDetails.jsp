<!--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - CUSTOMER MANAGEMENT
* File Name				:  CustomerGroupDetails.jsp
* Date					:  22-Dec-2021
* Author(s)				:  A-2569
*************************************************************************/
 -->
<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
		
	
	
<html:html>
<head> 
		
	

	<title>Group List</title>
	<meta name="decorator" content="popuppanelrestyledui">
	<common:include type="script" src="/js/customermanagement/defaults/CustomerGroupDetails_Script.jsp" />
	<business:sessionBean id="OneTimeValues"
						moduleName="customermanagement.defaults"
						screenID="customermanagement.defaults.maintainregcustomer"
						method="get"
						attribute="OneTimeValues" />
	<business:sessionBean id="groupList"
						moduleName="customermanagement.defaults"
						screenID="customermanagement.defaults.maintainregcustomer"
						method="get"
						attribute="customerGroupDetails" />
</head>

<body>
	<logic:present name="popup">
		<div id="walkthroughholder"> </div>
	</logic:present>
	
	<bean:define id="form" name="ListGroupDetailsForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListGroupDetailsForm"
		toScope="page" />
	
	<%@ include file="/jsp/includes/contextpath.jsp"%>

	<div class="iCargoPopUpContent">
	
		<ihtml:form action="/customermanagement.defaults.showgroupdetailsforcustomer.do">
		<ihtml:hidden property="customerCode"/>
			<div class="ic-head-container" >
				<div class="ic-filter-panel">			  
					<div class="ic-row">
						<div class="ic-col-100">
							<div class="ic-input-container">
								<div class="ic-row">
									<div class="ic-input ic-split-20">
										<label class="ic-label-30">
											<common:message key="customermanagement.defaults.customerregistration.groupList.grouptype" scope="request"/>
										</label>
										<logic:present name="form" property="groupType">
											<bean:define id="groupType" name="form"  property="groupType"  toScope="page"/>
											<ihtml:select property="groupType"
												componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTOMERTYPE" value="<%=(String)groupType%>">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="OneTimeValues">
													<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
															<logic:equal name="parameterCode" value="customermanagement.defaults.grouptype" >
																<logic:present name="parameterValue" property="fieldValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																			<html:option value="<%=(String)fieldValue %>"><%=fieldDescription%></html:option>								
																</logic:present>
															</logic:equal >
														</logic:iterate >
													</logic:iterate>
												</logic:present>
											</ihtml:select>
										</logic:present>
										<logic:notPresent name="form" property="groupType">
											<ihtml:select property="groupType"
												 componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTOMERTYPE">
												 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												 <logic:present name="OneTimeValues">
													<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
															<logic:equal name="parameterCode" value="customermanagement.defaults.grouptype" >
																<logic:present name="parameterValue" property="fieldValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																	<html:option value="<%=(String)fieldValue %>"><%=fieldDescription%></html:option>								
																</logic:present>
															</logic:equal >
														</logic:iterate >
													</logic:iterate>
												</logic:present>
											</ihtml:select>
										</logic:notPresent>
									</div>
									<div class="ic-input ic-split-20">
										<label class="ic-label-30">
											<common:message key="customermanagement.defaults.customerregistration.groupList.groupname" scope="request"/>
										</label>
										<ihtml:text property="groupName" maxlength="15" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTCODE"/>
										<div class="lovImg"><img id="groupnamelov" src="<%= request.getContextPath()%>/images/lov.png" width="22" height="22"/></div>
									</div>
									<div class="ic-input ic-split-20">
										<label class="ic-label-30">
											<common:message key="customermanagement.defaults.customerregistration.groupList.groupdesc" scope="request"/>
										</label>
										<ihtml:text property="groupDescription" maxlength="15" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTCODE"/>
									</div>
									<div class="ic-input ic-split-20">
										<label class="ic-label-30">
											<common:message key="customermanagement.defaults.customerregistration.groupList.category" scope="request"/>
										</label>
										<logic:present name="form" property="category">
											<bean:define id="category" name="form"  property="category"  toScope="page"/>
											<ihtml:select property="category"
												componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTOMERTYPE" value="<%=(String)category%>">
												<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												<logic:present name="OneTimeValues">
													<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
															<logic:equal name="parameterCode" value="shared.generalmastergrouping.groupcategory" >
																<logic:present name="parameterValue" property="fieldValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																			<html:option value="<%=(String)fieldValue %>"><%=fieldDescription%></html:option>								
																</logic:present>
															</logic:equal >
														</logic:iterate >
													</logic:iterate>
												</logic:present>
											</ihtml:select>
										</logic:present>
										<logic:notPresent name="form" property="category">
											<ihtml:select property="category"
												 componentID="CMB_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CUSTOMERTYPE">
												 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
												 <logic:present name="OneTimeValues">
													<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
															<logic:equal name="parameterCode" value="shared.generalmastergrouping.groupcategory" >
																<logic:present name="parameterValue" property="fieldValue">
																	<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																	<html:option value="<%=(String)fieldValue %>"><%=fieldDescription%></html:option>								
																</logic:present>
															</logic:equal >
														</logic:iterate >
													</logic:iterate>
												</logic:present>
											</ihtml:select>
										</logic:notPresent>
									</div>
								</div>
							</div>
						
						
							<div class="ic-button-container">
								<ihtml:nbutton accesskey="L" property="btnList" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_GROUPLIST_LIST">
									<common:message key="customermanagement.defaults.customerregistration.groupList.btn.list" scope="request"/>
								</ihtml:nbutton>
								<ihtml:nbutton accesskey="C" property="btnClear" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_GROUPLIST_CLEAR">
									<common:message key="customermanagement.defaults.customerregistration.groupList.btn.clear" scope="request"/>
								</ihtml:nbutton>
							</div>
							
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">			
				<div class="ic-row">
					<div id="div1" class="tableContainer" style="width:95%;height:450px;">
						<table width="95%" id="groupdetails" class="fixed-header-table">
							<thead>
								<tr>
									<th class="iCargoTableHeadingCenter"  style="width: 15%;" ><common:message key="customermanagement.defaults.customerregistration.groupList.code" scope="request"/></th>
									<th class="iCargoTableHeadingCenter"  style="width: 11%;" ><common:message key="customermanagement.defaults.customerregistration.groupList.grouptype" scope="request"/></th>
									<th class="iCargoTableHeadingCenter"  style="width: 15%;" ><common:message key="customermanagement.defaults.customerregistration.groupList.category" scope="request"/></th>
									<th class="iCargoTableHeadingCenter"  style="width: 19%;" ><common:message key="customermanagement.defaults.customerregistration.groupList.groupname" scope="request"/></th>
									<th class="iCargoTableHeadingCenter"  style="width: 20%;" ><common:message key="customermanagement.defaults.customerregistration.groupList.groupdesc" scope="request"/></th>
									<th class="iCargoTableHeadingCenter"  style="width: 10%;" ><common:message key="customermanagement.defaults.customerregistration.groupList.validfrom" scope="request"/></th>
									<th class="iCargoTableHeadingCenter"  style="width: 10%;" ><common:message key="customermanagement.defaults.customerregistration.groupList.validto" scope="request"/></th>
								</tr>
								
							</thead>
							<tbody id="listPAD">
								
								<logic:present name="groupList">
									<logic:iterate id = "groupListRow" name="groupList" indexId="indexId">
										<tr>
											<td class="iCargoTableDataTd"><bean:write name="form" property="customerCode"/></td>
											<td class="iCargoTableDataTd">
												
												<logic:present name="OneTimeValues">
													<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
															<logic:equal name="parameterCode" value="customermanagement.defaults.grouptype" >
																<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																<logic:equal name="groupListRow" property="groupType" value="<%=(String)fieldValue %>">
																	<%=fieldDescription%>						
																</logic:equal>
															</logic:equal >
														</logic:iterate >
													</logic:iterate>
												</logic:present>
											</td>
											<td class="iCargoTableDataTd">
												<logic:present name="OneTimeValues">
													<bean:define id="OneTimeValuesMap" name="OneTimeValues" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
														<logic:iterate id="parameterValue" name="parameterValues" indexId = "rowCount">
															<logic:equal name="parameterCode" value="shared.generalmastergrouping.groupcategory" >
																<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																<logic:equal name="groupListRow" property="groupCategory" value="<%=(String)fieldValue %>">
																	<%=fieldDescription%>						
																</logic:equal>
															</logic:equal >
														</logic:iterate >
													</logic:iterate>
												</logic:present>
											</td>
											<td class="iCargoTableDataTd"><bean:write name="groupListRow" property="groupName"/></td>
											<td class="iCargoTableDataTd"><bean:write name="groupListRow" property="groupDescription"/></td>
											
											<logic:present name="groupListRow" property="groupDetailsVOs">
												<logic:iterate id = "groupDetListRow" name="groupListRow" property="groupDetailsVOs" indexId="detailsIndexId">
													<td class="iCargoTableDataTd">
														<logic:notEmpty name="groupDetListRow" property="validFromDate">
															<bean:define id="sDate" name="groupDetListRow" property="validFromDate"/>
															<% String sdate = ((LocalDate)sDate).toDisplayDateOnlyFormat(); %>
															<%= sdate%>
														</logic:notEmpty>
													</td>
													<td class="iCargoTableDataTd">
														<logic:notEmpty name="groupDetListRow" property="validToDate">
															<bean:define id="fDate" name="groupDetListRow" property="validToDate"/>
															<% String fdate = ((LocalDate)fDate).toDisplayDateOnlyFormat(); %>
															<%= fdate%>
														</logic:notEmpty>
													</td>
												</logic:iterate>
											</logic:present>
										</tr>
									</logic:iterate>
								</logic:present>
	  		
							</tbody>
						</table>
					</div>
					
				</div>
			</div>
		
			<div class="ic-foot-container">
				<div class="ic-button-container paddR5">
					<ihtml:nbutton accesskey="O" property="btnClose" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_GROUPLIST_CLOSE">
						<common:message key="customermanagement.defaults.customerregistration.groupList.btn.close" scope="request"/>
					</ihtml:nbutton>
				</div>
			</div>
		</ihtml:form>
	</div>
	
	
</body>
</html:html>
