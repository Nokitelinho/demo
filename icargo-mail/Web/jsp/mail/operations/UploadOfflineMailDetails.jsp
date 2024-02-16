<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  Mailtracking
* File Name				:  UploadOfflineMailDetails.jsp
* Date					:  13-Oct-2014
*************************************************************************/
 --%>


<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page
	import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm"%>

		
	
<html:html>

<head>
	

<title><common:message bundle="uploadMailDetailsResources"
	key="mailtracking.defaults.uploadofflinemaildetails.lbl.title" /></title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include type="script"
	src="/js/mail/operations/UploadOfflineMailDetails_Script.jsp" />
</head>

<body>



<bean:define id="form" name="UploadOfflineMailDetailsForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadOfflineMailDetailsForm"
	toScope="page" scope="request" />

<business:sessionBean id="scannedMailDetailVOs"
	moduleName="mail.operations"
	screenID="mailtracking.defaults.offlinemailupload" method="get"
	attribute="ScannedMailDetailsVOs" />

<div class="iCargoContent ic-masterbg"
	style="width: 100%; height: 100%; overflow: auto;"><ihtml:form
	action="/mailtracking.defaults.offlineupload.do">

	<input type="hidden" name="currentDialogId" />
	<input type="hidden" name="currentDialogOption" />
	<ihtml:hidden property="scanningPort" />
	<input type="hidden" name="isUnsavedDataPresent"
		id="isUnsavedDataPresent" />
	<div class="ic-content-main bg-white">
	<div class="ic-head-container"><span
		class="ic-page-title ic-display-none"> <common:message
		key="mailtracking.defaults.uploadofflinemaildetails.lbl.title" /> </span></div>
	<div class="ic-main-container">
	<div class="ic-row">
	<h3><common:message
		key="mailtracking.defaults.uploadofflinemaildetails.lbl.pageheading" />
	</h3>
	</div>
	<div class="ic-row">
	<div id="div2" class="tableContainer" style="height: 900px;">
	<table id="uploadTable2" class="fixed-header-table">
		<thead>
			<tr>
				<td width="2%"class="iCargoTableHeaderLabel"><input type="checkbox"
					id="checkAllOfflineUploadMailTag"
					name="checkAllOfflineUploadMailTag" onclick="updateHeaderCheckBox(targetFormName, targetFormName.elements.checkAllOfflineUploadMailTag, targetFormName.elements.selectedMails)"
					checked="true" /></td>
				<td width="15%" class="iCargoTableHeaderLabel"><common:message
					bundle="uploadMailDetailsResources"
					key="mailtracking.defaults.uploadofflinemaildetails.head.processpoint" />
				</td>
				<td  width="15%" class="iCargoTableHeaderLabel"><common:message
					bundle="uploadMailDetailsResources"
					key="mailtracking.defaults.uploadofflinemaildetails.head.status" />
				</td>
				<td width="20%" class="iCargoTableHeaderLabel"><common:message
					bundle="uploadMailDetailsResources"
					key="mailtracking.defaults.uploadofflinemaildetails.head.outboundflight" />
				</td>
				<td width="15%" class="iCargoTableHeaderLabel"><common:message
					bundle="uploadMailDetailsResources"
					key="mailtracking.defaults.uploadofflinemaildetails.head.inboundflight" />
				</td>
				<td width="18%" class="iCargoTableHeaderLabel"><common:message
					bundle="uploadMailDetailsResources"
					key="mailtracking.defaults.uploadofflinemaildetails.head.containerno" />
				</td>
				<td width="15%" class="iCargoTableHeaderLabel"><common:message
					bundle="uploadMailDetailsResources"
					key="mailtracking.defaults.uploadmaildetails.head.scanned" /></td>
			</tr>
		</thead>
		<tbody>
			<logic:present name="scannedMailDetailVOs">
				<logic:iterate id="scannedMailDetailVO" name="scannedMailDetailVOs"
					type="com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO">
<%String errorStyle = "";%>
					<logic:present name="scannedMailDetailVO" property="hasErrors">
											<logic:equal  name="scannedMailDetailVO" property="hasErrors" value="true">
												<% errorStyle = "style=\"color:#ff0000\"";%>
						</logic:equal>
					</logic:present>
					

					<tr>
						<td class="iCargoTableDataTd ic-center">
						<bean:define id="mailKey"
							name="scannedMailDetailVO" property="remarks" toScope="request" />
						<%String disableCheck = "";%> <%String defaultCheck ="checked";%> <logic:present
							name="scannedMailDetailVO" property="status">
							<logic:equal name="scannedMailDetailVO" property="status"
								value="S">
								<% disableCheck = "disabled=\"true\"";%>
								<% defaultCheck = "";%>
							</logic:equal>

						</logic:present> <input type="checkbox" name="selectedMails" value="<%=mailKey%>"
							<%= disableCheck %> <%=defaultCheck %>
							 />
						</td>
						<td class="iCargoTableDataTd" <%= errorStyle %>><!-- processpoint -->	
						<logic:present name="scannedMailDetailVO" property="processPoint">
							<bean:write name="scannedMailDetailVO" property="processPoint" />
						</logic:present></td>

						<td class="iCargoTableDataTd" <%= errorStyle %>><logic:present
							name="scannedMailDetailVO" property="status">
							<logic:equal name="scannedMailDetailVO" property="status"
								value="U">
								<bean:message bundle='uploadMailDetailsResources'
									key='mailtracking.defaults.uploadofflinemaildetails.message.unsaved' />
							</logic:equal>
							<logic:equal name="scannedMailDetailVO" property="status"
								value="S">
								<bean:message bundle='uploadMailDetailsResources'
									key='mailtracking.defaults.uploadofflinemaildetails.message.saved' />
							</logic:equal>
							<bean:define id="status" name="scannedMailDetailVO"
								property="status" />
							<input type="hidden" name="status" value="<%=status%>" />
						</logic:present></td>

						<td class="iCargoTableDataTd" <%= errorStyle %>><!-- outboundflight -->
						<logic:equal name="scannedMailDetailVO" property="processPoint"
							value="OUT">
							<%StringBuilder outflightNumber=new StringBuilder();%>

							<logic:present name="scannedMailDetailVO" property="flightDate">
								<%outflightNumber=outflightNumber.append(scannedMailDetailVO.getCarrierCode());%>
								<logic:present name="scannedMailDetailVO" property="flightDate">
									<%outflightNumber.append(" ").append(scannedMailDetailVO.getFlightNumber());%>
									<bean:define id="outflightDate" name="scannedMailDetailVO"
										property="flightDate"
										type="com.ibsplc.icargo.framework.util.time.LocalDate" />
									<%outflightNumber=outflightNumber.append(" ").append(outflightDate.toDisplayDateOnlyFormat());%>
								</logic:present>
							</logic:present>

							<logic:notPresent name="scannedMailDetailVO"
								property="flightDate">
								<logic:present name="scannedMailDetailVO" property="carrierCode">
									<%outflightNumber=outflightNumber.append(scannedMailDetailVO.getCarrierCode());%>
								</logic:present>
								<logic:present name="scannedMailDetailVO" property="destination">
									<%outflightNumber=outflightNumber.append("-").append(scannedMailDetailVO.getDestination());%>
								</logic:present>
							</logic:notPresent>
							<%=(outflightNumber.toString())%>
						</logic:equal></td>

						<td class="iCargoTableDataTd" <%= errorStyle %>><!-- inboundflight -->
						<logic:equal name="scannedMailDetailVO" property="processPoint"
							value="IN">
							<%StringBuilder inflightNumber=new StringBuilder();%>

							<logic:present name="scannedMailDetailVO" property="flightDate">
								<%inflightNumber=inflightNumber.append(scannedMailDetailVO.getCarrierCode());%>
								<logic:present name="scannedMailDetailVO" property="flightDate">
									<%inflightNumber.append(" ").append(scannedMailDetailVO.getFlightNumber());%>
									<bean:define id="inflightDate" name="scannedMailDetailVO"
										property="flightDate"
										type="com.ibsplc.icargo.framework.util.time.LocalDate" />
									<%inflightNumber=inflightNumber.append(" ").append(inflightDate.toDisplayDateOnlyFormat());%>
								</logic:present>
							</logic:present>

							<logic:notPresent name="scannedMailDetailVO"
								property="flightDate">
								<logic:present name="scannedMailDetailVO"
									property="inboundCarrierCode">
									<%inflightNumber=inflightNumber.append(scannedMailDetailVO.getCarrierCode());%>
								</logic:present>
								<logic:present name="scannedMailDetailVO" property="destination">
									<%inflightNumber=inflightNumber.append("-").append(scannedMailDetailVO.getDestination());%>
								</logic:present>
							</logic:notPresent>
							<%=(inflightNumber.toString())%>
						</logic:equal></td>

						<td class="iCargoTableDataTd" <%= errorStyle %>><logic:present
							name="scannedMailDetailVO" property="containerNumber">
							<bean:write name="scannedMailDetailVO" property="containerNumber" />
						</logic:present></td>
						<td class="iCargoTableDataTd" <%= errorStyle %>><logic:present
							name="scannedMailDetailVO" property="scannedBags">
							<bean:write name="scannedMailDetailVO" property="scannedBags" />
						</logic:present></td>
					</tr>

				</logic:iterate>
			</logic:present>
			<logic:notPresent name="scannedMailDetailVOs">
				<tr>
					<td colspan="7" class="iCargoLabel"><br>
					<span class="ic-bold-label">No data available for Upload</span> </br>
					</td>
				</tr>
			</logic:notPresent>
		</tbody>
	</table>
	</div>
	</div>
	</div>
	<div class="ic-foot-container">
	<div class="ic-button-container"><ihtml:nbutton
		property="btnSave"
		componentID="BTN_MAILTRACKING_DEFAULTS_UPLOADOFFLINEMAIL_SAVE">
		<common:message bundle="uploadMailDetailsResources"
			key="mailtracking.defaults.uploadofflinemaildetails.btn.save" />
	</ihtml:nbutton> <ihtml:nbutton property="btnClose"
		componentID="BTN_MAILTRACKING_DEFAULTS_UPLOADOFFLINEMAIL_CLOSE">
		<common:message bundle="uploadMailDetailsResources"
			key="mailtracking.defaults.uploadofflinemaildetails.btn.close" />
	</ihtml:nbutton></div>
	</div>

	</div>
</ihtml:form></div>



	</body>
</html:html>
