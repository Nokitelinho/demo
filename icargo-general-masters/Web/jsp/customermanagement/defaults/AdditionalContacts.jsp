<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"%>
<%@ page import="java.util.Collection" %>





		
		<!DOCTYPE html>			
	
<html>
<head> 
<title><common:message bundle="maintainregcustomerform" key="customermanagement.defaults.listcustomer.pagetitle"  scope="request" /></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script"  src="/js/customermanagement/defaults/AdditionalContacts_Script.jsp" />
</head>

<body id="bodyStyle">


<bean:define id="form"
	 name="MaintainCustomerRegistrationForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm"
	 toScope="page" />
	 
	 <business:sessionBean id="ContactType"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get"
			attribute="oneTimeValues" />
			
	 <business:sessionBean id="AdditionalContacts"
			moduleName="customermanagement.defaults"
			screenID="customermanagement.defaults.maintainregcustomer"
			method="get"
			attribute="additionalContacts" />
			
<div class="iCargoPopUpContent" style="height:320px;"   >
<ihtml:form action="customermanagement.defaults.listAdditionalContacts.do" styleClass="ic-main-form">
<ihtml:hidden property="selectedContactIndex" />
<ihtml:hidden property="reloadParent" />
<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message key="customermanagement.defaults.listcustomer.pagetitle" />
	</span>
	
	<%
		String contactType = ((MaintainRegCustomerForm)form).getContactType();									
	%>
	<div class="ic-main-container">
				<div class="ic-row">
					<h4><common:message key="customermanagement.defaults.listcustomer.addtionalcontactdetails" /></h4>
				</div>
	
		<div class="ic-button-container">
						<a href="#" id="addLink" class="iCargoLink">Add</a>&nbsp;| <a
							href="#" id="deleteLink" class="iCargoLink">Delete</a>
					</div>
		<div id="div1" class="tableContainer"  style="height:160px;width:100%;">
			<table class="fixed-header-table" style="width:100%">
							<thead>
								<tr>
									<td width="5%" class="iCargoTableHeadingCenter">
										<input type="checkbox" name="checkAllBox" onclick="updateHeaderCheckBox(this.form, this, this.form.check);"/>
									</td>
									<td width="35%" class="iCargoTableHeadingLeft">
										<common:message key="customermanagement.defaults.listcustomer.lbl.Type" />
									</td>
									<td width="60%" class="iCargoTableHeadingLeft">
										<common:message key="customermanagement.defaults.listcustomer.lbl.Details" />
									</td>
								</tr>
							</thead>
							<tbody id="listContact">
												<logic:present name="AdditionalContacts">
												<bean:define id="additionalContacts" name="AdditionalContacts" type="java.util.Collection" />
 												
												<logic:iterate id="additionalContact" name="additionalContacts" indexId="index">
													
													
													<logic:present name="additionalContact" property="operationalFlag">
														<bean:define id="opFlag" name="additionalContact" property="operationalFlag"/>
														<ihtml:hidden property="opFlag" value="<%=(String)opFlag%>"/>
													</logic:present>
													
													<logic:notPresent name="additionalContact" property="operationalFlag">
														<bean:define id="opFlag"  value="NA"/>
														<ihtml:hidden property="opFlag" value="NA"/>
													</logic:notPresent>

													<logic:notMatch name="opFlag" value="D">

														<logic:match name="opFlag" value="I">
															<ihtml:hidden property="hiddenOpFlagForAddtContacts" value="I"/>
														</logic:match>
														<logic:notMatch name="opFlag" value="I">
													<ihtml:hidden property="hiddenOpFlagForAddtContacts" value="U"/>
														</logic:notMatch>
													<bean:define id="contactModeValue" name="additionalContact" property="contactMode"/>
													<bean:define id="contactAddressValue" name="additionalContact" property="contactAddress"/>
											<!-- DATA ROW -->
											<tr>
												<td class="iCargoTableTd" style="text-align: center;">
													<html:checkbox property="check" onclick="toggleTableHeaderCheckbox('check',this.form.checkAllBox)" />	
												</td>
												<td class="iCargoTableTd">
													<ihtml:select property="contactMode" id="contactMode" indexId="index" componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CONTACTMODE" value="<%=(String)contactModeValue%>">
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="ContactType">
														<bean:define id="OneTimeValuesMap" name="ContactType" type="java.util.HashMap" />
															<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																	<logic:equal name="parameterCode" value="customermanagement.defaults.customercontact.contactmode" >
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
													</ihtml:select>
												</td>
												<td class="iCargoTableTd" >
													<ihtml:text property="contactAddress"  id="contactAddress" indexId="index"
													maxlength="75" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CONTACTADDRESS" value="<%=(String)contactAddressValue%>"/>
												</td>
												</tr>
												
												</logic:notMatch>
												</logic:iterate>
												</logic:present>
												
												<!-- DEFAULT ROW -->
												<logic:notPresent name="AdditionalContacts">
												<tr>
												<td class="iCargoTableTd" style="text-align: center;">
													<html:checkbox property="check" onclick="toggleTableHeaderCheckbox('check',this.form.checkAllBox)" />
													<ihtml:hidden property="hiddenOpFlagForAddtContacts" value="NA" />
												</td>
												<td class="iCargoTableTd">
												 <ihtml:select property="contactMode" id="contactMode" componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CONTACTMODE">
													<% if("BTP".equals(contactType)){ %>
													<ihtml:option value="M">Email</ihtml:option>
													<% } else { %>
													<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
													<logic:present name="ContactType">
														<bean:define id="OneTimeValuesMap" name="ContactType" type="java.util.HashMap" />
															<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
																<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
																<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
																	<logic:equal name="parameterCode" value="customermanagement.defaults.customercontact.contactmode" >
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
												</ihtml:select>
												</td >
												<td class="iCargoTableTd" >
													<ihtml:text property="contactAddress" id="contactAddress" 
													maxlength="75" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CONTACTADDRESS"/>
												</td>
											</tr>
												</logic:notPresent>
									<!-- TEMPLATE ROW -->
									<tr template="true" id="addtionalCOntactsTemplateRow"
									style="display: none">
									<td class="iCargoTableDataTd" style="text-align: center;">
										<html:checkbox property="check"
											onclick="toggleTableHeaderCheckbox('check',this.form.checkAllBox)" />
										<ihtml:hidden property="hiddenOpFlagForAddtContacts" value="NOOP" />
									</td>
									<td class="iCargoTableDataTd" >
									  <ihtml:select property="contactMode" id="contactMode" componentID="CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CONTACTMODE">
										  <% if("BTP".equals(contactType)){ %>
											<ihtml:option value="M">Email</ihtml:option>
										  <% } else { %>
										  <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
										 <logic:present name="ContactType">
												<bean:define id="OneTimeValuesMap" name="ContactType" type="java.util.HashMap" />
													<logic:iterate id="oneTimeValue" name="OneTimeValuesMap">
														<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
														<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
															<logic:equal name="parameterCode" value="customermanagement.defaults.customercontact.contactmode" >
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
										</ihtml:select>
										
									</td>
									<td class="iCargoTableDataTd">
										<ihtml:text property="contactAddress" id="contactAddress"
													maxlength="75" componentID="TXT_CUSTOMERMANAGEMENT_DEFAULTS_CUSTREGN_CONTACTADDRESS"/>
									</td>
								</tr>
								
							</tbody>
						</table>
		
	
		</div>
	</div>
	
	
	
	<div class="ic-foot-container">
		<div class="ic-row paddR5">
			<div class="ic-button-container">       
				<ihtml:nbutton property="btnOK" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_ADDITIONSLCONTACTS_OK"  tabindex="5">
					<common:message  key="customermanagement.defaults.customerregistration.additionalContacts.btn.ok" scope="request"/>	
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="BTN_CUSTOMERMANAGEMENT_DEFAULTS_ADDITIONSLCONTACTS_CLOSE"  tabindex="6">
					<common:message  key="customermanagement.defaults.customerregistration.additionalContacts.btn.close" scope="request"/>	
				</ihtml:nbutton>
			</div>
		</div>
	</div>
</div>
</ihtml:form>
</div>

</body>
</html>