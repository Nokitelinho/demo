<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>
	
		<!DOCTYPE html>			
	
<html>
<head> 	
<title><common:message bundle="maintainregcustomerform" key="customermanagement.defaults.customerregistration.lbl.notificationpreferences"  scope="request" /></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script"  src="/js/customermanagement/defaults/NotificationPreferences_Script.jsp" />
</head>

<body id="bodyStyle">

<bean:define id="form"
	 name="MaintainCustomerRegistrationForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"
	 toScope="page" />
	 
	 <business:sessionBean id="GeneralPreference"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get"
			attribute="oneTimeValues" />
			
		<business:sessionBean id="LanguagesEbkAndQa" moduleName="customermanagement.defaults" 
		screenID="customermanagement.defaults.maintainregcustomer" method="get" attribute="languagesForEbkAndQa" />
			
	 <business:sessionBean id="NotificationPreference"
						  moduleName="customermanagement.defaults"
						  screenID="customermanagement.defaults.maintainregcustomer"
		method="get" attribute="notificationPreferences" />
		
<div class="iCargoPopUpContent" style="height:320px;"   >
<ihtml:form action="customermanagement.defaults.listNotificationPreferences.do" styleClass="ic-main-form">
<ihtml:hidden property="reloadParent" />
<ihtml:hidden property="emailFlag" />
<ihtml:hidden property="mobileFlag" />
<ihtml:hidden property="faxFlag" />
<ihtml:hidden property="selectedContactIndex" />
<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		
	</span>
	<div class="ic-head-container">
	</div>
	<%
		String contactType = ((MaintainRegCustomerForm)form).getContactType();									
	%>
	
	<div class="ic-main-container">
		<% if(("QAE".equals(contactType)) || ("EBK".equals(contactType))){ %>
			<div id="div1" class="tableContainer"  style="height:150px;width:100%;">
		<% }else if(("EFRT".equals(contactType))){ %>
			<div id="div1" class="tableContainer"  style="height:80px;width:100%;">
		<% }else if(("ETR".equals(contactType)) || ("ETRTOA".equals(contactType))){ %>
			<div id="div1" class="tableContainer"  style="height:80px;width:100%;">
		<% } else {%>
			<div id="div1" class="tableContainer"  style="height:60px;width:100%;">
		<% }%>
			<table class="fixed-header-table" style="width:100%">
							<thead>
								<tr>
									
									<td width="60%" class="iCargoTableHeadingLeft">
										<common:message  key="customermanagement.defaults.customerregistration.lbl.generalpreference" scope="request"/>
									</td>
									<td width="40%" class="iCargoTableHeadingLeft">
										<common:message  key="customermanagement.defaults.customerregistration.lbl.parametervalue" scope="request"/>
									</td>
								</tr>
							</thead>
							<tbody id="listContact">
								
											<tr>
												
												<td class="iCargoTableTd">
													<common:message  key="customermanagement.defaults.customerregistration.lbl.preferredLanguage" scope="request"/>										
												</td>
												<td class="iCargoTableTd">
												<ihtml:select property="notificationLanguageCode" componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_LANGUAGE">
													<% if(!("QAE".equals(contactType)) && !("EBK".equals(contactType))){ %>
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="GeneralPreference">
														<bean:define id="OneTimeValuesMap" name="GeneralPreference" type="java.util.HashMap" />
															<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																	<logic:equal name="parameterCode" value="customermanagement.defaults.customercontact.language"	  >
																	<logic:iterate id="parameterValue" name="parameterValues">
																			<logic:present name="parameterValue" property="fieldValue">
																				<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																				<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																				<ihtml:option value="<%=(String)fieldValue%>"><%=fieldDescription%></ihtml:option>
																			</logic:present>
															</logic:iterate>
															</logic:equal >
															</logic:iterate>
														</logic:present>
													<% } %>
													<logic:present name="LanguagesEbkAndQa">
														<logic:iterate id="parameterValue" name="LanguagesEbkAndQa">
															<logic:present name="parameterValue" property="fieldValue">
																<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																<ihtml:option value="<%=(String)fieldValue%>"><%=fieldDescription%></ihtml:option>
															</logic:present>
														</logic:iterate>
													</logic:present>	
												</ihtml:select>
												<% if(("QAE".equals(contactType)) || ("EBK".equals(contactType)) || ("EFRT".equals(contactType))){ %>
												<logic:notPresent name="GeneralPreference">														
														<ihtml:option value="">English</ihtml:option>
												</logic:notPresent>													
												<% } %>		
												</td >
												
										<% if(("QAE".equals(contactType)) || ("EBK".equals(contactType)) || ("EFRT".equals(contactType))){ %>
											</tr>
											<tr>
												<td class="iCargoTableTd">
													<common:message  key="customermanagement.defaults.customerregistration.lbl.preferredFormat" scope="request"/>										
												</td>
												<td class="iCargoTableTd">
												<ihtml:select property="notificationFormat" componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_FORMAT">
													<logic:present name="GeneralPreference">
														<bean:define id="OneTimeValuesMap" name="GeneralPreference" type="java.util.HashMap" />
														<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
															<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
															<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
															<logic:equal name="parameterCode" value="customermanagement.defaults.customercontact.format"  >
																<logic:iterate id="parameterValue" name="parameterValues">
																	<logic:present name="parameterValue" property="fieldValue">
																		<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																		<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																		<ihtml:option value="<%=(String)fieldValue%>"><%=fieldDescription%></ihtml:option>
																	</logic:present>
																</logic:iterate>
															</logic:equal>
														</logic:iterate>
													</logic:present>	
													<logic:notPresent name="GeneralPreference">														
														<ihtml:option value="">HTML</ihtml:option>
													</logic:notPresent>														
												</ihtml:select>
												</td >
										<% } %>
										<% if("ETR".equals(contactType) || "ETRTOA".equals(contactType)){ %>
											</tr>
											<tr>
												<td class="iCargoTableTd">
													<common:message  key="customermanagement.defaults.customerregistration.lbl.exportImportFilter" scope="request"/>										
												</td>
												<td class="iCargoTableTd">
												<ihtml:select property="notifyShipmentType" componentID="CUSTOMERMANAGEMENT_DEFAULTS_EXPIMP_FILTER">
													<logic:present name="GeneralPreference">
														<bean:define id="OneTimeValuesMap" name="GeneralPreference" type="java.util.HashMap" />
														<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
															<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
															<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
															<logic:equal name="parameterCode" value="customermanagement.defaults.customercontact.notificationfilters"  >
																<logic:iterate id="parameterValue" name="parameterValues">
																	<logic:present name="parameterValue" property="fieldValue">
																		<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																		<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																		<ihtml:option value="<%=(String)fieldValue%>"><%=fieldDescription%></ihtml:option>
																	</logic:present>
																</logic:iterate>
															</logic:equal>
														</logic:iterate>
													</logic:present>	
													<logic:notPresent name="GeneralPreference">														
														<ihtml:option value="A"> </ihtml:option>
													</logic:notPresent>														
												</ihtml:select>
												</td >
										<% } %>
											</tr>
									
							</tbody>
						</table>
		</div>
		<% if(!("QAE".equals(contactType)) && !("EBK".equals(contactType))){ %>
		<div class="ic-row">
			<h4><common:message  key="customermanagement.defaults.customerregistration.lbl.notificationpreferences" scope="request"/></h4>
		</div>
		<% if(!("EFRT".equals(contactType))) {%>
			<div id="div1" class="tableContainer"  style="height:265px;width:100%;">
		<%} else {%>
			<div id="div1" class="tableContainer"  style="height:180px;width:100%;">
		<% } %>
			<table class="fixed-header-table" style="width:100%">
				<thead>
					<tr>
						<% if(!("EFRT".equals(contactType))) {%>
							<td width="40%" class="iCargoTableHeadingCenter">Standard Events</td>
							<td width="20%" class="iCargoTableHeadingCenter">Email</td>
							<td width="20%" class="iCargoTableHeadingCenter">SMS</td>
							<td width="20%" class="iCargoTableHeadingCenter">Fax</td>
						<% }
							else {
						%>
							<td width="50%" class="iCargoTableHeadingCenter">Standard Events</td>
							<td width="25%" class="iCargoTableHeadingCenter">Email</td>
							<td width="25%" class="iCargoTableHeadingCenter">SMS</td>
						<% } %>
						
					</tr>
				</thead>
				<tbody>
					<logic:present name="NotificationPreference" >
						<bean:define id="notificationPreferencesList" name="NotificationPreference"/>
							<logic:iterate id = "notificationPreference" name="notificationPreferencesList" indexId="indexId" type="com.ibsplc.icargo.business.shared.customer.vo.NotificationPreferenceVO">								
							<tr>
								<td class="iCargoTableTd">
									<bean:define id="evenCodeValue" name="notificationPreference" property="eventCode"/>
									<ihtml:hidden property="eventCode" value="<%=(String)evenCodeValue%>"/>
									<bean:write name="notificationPreference" property="eventCode"/> /<bean:write name="notificationPreference" property="eventDescription"/>
								</td>
								<td class="iCargoTableDataTd ic-center">
									<logic:present name="notificationPreference" property="emailFlag">
										<logic:equal name="notificationPreference" property="emailFlag" value="Y">
											<input type="checkbox" name="emailCheck" checked value="Y"/>
										</logic:equal >
										<logic:notEqual name="notificationPreference" property="emailFlag" value="Y" >
											<input type="checkbox" name="emailCheck" value="N"/>
										</logic:notEqual>
									</logic:present>
								</td>
								<td class="iCargoTableDataTd ic-center">
									<logic:present name="notificationPreference" property="mobileFlag">
										<logic:equal name="notificationPreference" property="mobileFlag" value="Y">
											<input type="checkbox" name="mobileCheck" checked value="Y"/>
										</logic:equal >
										<logic:notEqual name="notificationPreference" property="mobileFlag" value="Y" >
											<input type="checkbox" name="mobileCheck" value="N"/>
										</logic:notEqual>
									</logic:present>
								</td>
								<%if(!("EFRT".equals(contactType))) {%>
								<td class="iCargoTableDataTd ic-center">
									<logic:present name="notificationPreference" property="faxFlag">
										<logic:equal name="notificationPreference" property="faxFlag" value="Y">
											<input type="checkbox" name="faxCheck" checked value="Y"/>
										</logic:equal >
										<logic:notEqual name="notificationPreference" property="faxFlag" value="Y" >
											<input type="checkbox" name="faxCheck" value="N"/>
										</logic:notEqual>
									</logic:present>
								</td>
								<% } %>
							</tr>
							</logic:iterate>
					</logic:present>
							</tbody>
						</table>
		</div>
		<% } %>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">       
				<ihtml:nbutton property="btnOk" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_NOTIFICATIONPREFERENCES_OK"  tabindex="5">
					<common:message  key="customermanagement.defaults.customerregistration.notificationpreferences.btn.ok" scope="request"/>	
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_NOTIFICATIONPREFERENCES_CLOSE"  tabindex="6">
					<common:message  key="customermanagement.defaults.customerregistration.notificationpreferences.btn.close" scope="request"/>	
				</ihtml:nbutton>
			</div>
		</div>
	</div>
</div>
</ihtml:form>
</div>

</body>
</html>