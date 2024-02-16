<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO" %>
<%@ page import = "java.util.Collection" %>


<html:html locale="true">
<head> 
	
	<title><common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.title" /></title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/operations/ExistingMailbag_Script.jsp" />
</head>

<body id="bodyStyle">
	
	
<ihtml:form action="/mailtracking.defaults.mailacceptance.existingmailbag.screenload.do" >
	<ihtml:hidden property="popupCloseFlag" />
	<ihtml:hidden property="fromScreen" />
	<ihtml:hidden property="currentStation" />
	<bean:define id="MailAcceptanceForm" name="MailAcceptanceForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
		toScope="page" scope="request"/>
	<business:sessionBean id="existingMailbagVOs"
			  moduleName="mail.operations"
			  screenID="mailtracking.defaults.mailacceptance"
			  method="get"
			  attribute="existingMailbagVO" />
	<business:sessionBean id="flightStatusVOSess"
			  moduleName="mail.operations"
			  screenID="mailtracking.defaults.mailacceptance"
			  method="get"
			  attribute="oneTimeFlightStatus" />
	<div id="divmain" class="iCargoPopUpContent"  style="position:static; width:100%; height:290px; z-index:1;">	
		<div class="ic-content-main">
			<div class="ic-head-container">
				<span class="ic-page-title ic-display-none"><common:message key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.pagetitle" /></span>
				<div class="ic-row ">
				<div class="ic-row iCargoHeadingLabel">
					<b><common:message key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.pageinfo" /></b>
				</div>
			</div>
			</div>
			<div class="ic-main-container">
				<div class="ic-row ic-border">
					<div class="ic-row">
						<div  id="div1" class="tableContainer" style="height:250px;width:100%" >
							<table width="99%" class="fixed-header-table">
								<thead>
									<tr class="iCargoTableHeadingLeft" height="25px">
										<td width="35%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.mailid" /></td>
										<td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.container" /></td>
										<td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.flight" /></td>
										<td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.date" /></td>
										<td width="15%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.airport" /></td>
										<td width="13%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.status" /></td>
										<td width="10%" class="iCargoTableHeaderLabel"><common:message key="mailtracking.defaults.mailacceptance.existingmailbag.lbl.reassign" /></td>
									</tr>
								</thead>
								<%int count=0;%>
								<tbody>
									<logic:present name="existingMailbagVOs">
										<logic:iterate id="existingMailbagVo" name="existingMailbagVOs" indexId="rowCount">
											<tr>
												<td class="iCargoTableDataTd">
														<bean:write name="existingMailbagVo" property="mailId" />
												</td>
												<td class="iCargoTableDataTd">
													<bean:write name="existingMailbagVo" property="containerNumber" />
												</td>
												<td  class="iCargoTableDataTd">
													<bean:write name="existingMailbagVo" property="carrierCode" />
													<logic:notEqual name="existingMailbagVo" property="flightNumber" value="-1">
														<bean:write name="existingMailbagVo" property="flightNumber" />
													</logic:notEqual>
												</td>
												<td  class="iCargoTableDataTd">
													<logic:present name="existingMailbagVo" property="flightDate">
														<bean:define id ="flightDate" name = "existingMailbagVo" property="flightDate" type="com.ibsplc.icargo.framework.util.time.LocalDate"/>
														<%= flightDate.toString().substring(0,11) %>
													</logic:present>
												</td>
												<td  class="iCargoTableDataTd">
													<bean:write name="existingMailbagVo" property="currentAirport" />
												</td>
												<td  class="iCargoTableDataTd">
													<logic:iterate id="oneTimeVO" name="flightStatusVOSess" >
														<bean:define id="fieldValue" name="oneTimeVO" property="fieldValue" toScope="page" />
														<bean:define id="fieldDesc" name="oneTimeVO" property="fieldDescription" toScope="page" />
														<bean:define id="flightStatus" name="existingMailbagVo" property="flightStatus" toScope="page" />
														<logic:equal name="oneTimeVO" property="fieldValue" value="<%= flightStatus.toString() %>">
															<bean:write name="oneTimeVO" property="fieldDescription" />
														</logic:equal>
													</logic:iterate>
												</td>
												<td class="iCargoTableDataTd" style="text-align:center;" >
													<logic:equal name="existingMailbagVo" property="currentAirport" value="<%= MailAcceptanceForm.getCurrentStation() %>">
														<logic:notEmpty name="existingMailbagVo" property="flightStatus">
															<logic:equal name="existingMailbagVo" property="flightStatus" value="O">
																<ihtml:checkbox property="reassign" value="<%=String.valueOf(rowCount)%>"/>
															</logic:equal>	
															<logic:equal name="existingMailbagVo" property="flightStatus" value="C">
																<ihtml:checkbox property="reassign" value="<%=String.valueOf(rowCount)%>" disabled="true"/>
															</logic:equal>	
														</logic:notEmpty>
														<logic:empty name="existingMailbagVo" property="flightStatus">
															<ihtml:checkbox property="reassign" value="<%=String.valueOf(rowCount)%>"/>
														</logic:empty>
													</logic:equal>
													<logic:notEqual name="existingMailbagVo" property="currentAirport" value="<%= MailAcceptanceForm.getCurrentStation() %>">
														<ihtml:checkbox property="reassign" value="<%=String.valueOf(rowCount)%>" disabled="true"/>
													</logic:notEqual>
												</td>
											</tr>
											<%count++; %>
										</logic:iterate>
									</logic:present>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<div class="ic-foot-container">
					<div class="ic-button-container">
						<ihtml:nbutton property="btnOk" componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_EXISTINGMAILBAG_OK" tabindex="3">
						   <common:message key="mailtracking.defaults.mailacceptance.existingmailbag.btn.ok" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_MAILACCEPTANCE_EXISTINGMAILBAG_CLOSE" tabindex="4">
						   <common:message key="mailtracking.defaults.mailacceptance.existingmailbag.btn.close" />
						</ihtml:nbutton>
					</div>
				</div>	
			</div>
		</div>
	</ihtml:form>
</div>		
	
	
	</body>
</html:html>
