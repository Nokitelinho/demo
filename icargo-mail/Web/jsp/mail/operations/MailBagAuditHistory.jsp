
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAuditHistoryForm"%>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO"%>
<html:html>
<head>

	<title><common:message bundle="MailAuditHistoryResources" key="mailtracking.defaults.mailaudit.lbl.title" /></title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/operations/MailBagAuditHistory_Script.jsp"/>
</head>
<body>
<bean:define id="form"
		 name="MailAuditHistoryForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAuditHistoryForm"
		 toScope="page" />
<ibusiness:sessionBean id="mailaudithistoryvos" moduleName="mail.operations" screenID="mailtracking.defaults.mailaudithistory" method="get" attribute="mailBagAuditHistoryVOs" />
<ibusiness:sessionBean id="transactionCodes" moduleName="mail.operations" screenID="mailtracking.defaults.mailaudithistory" method="get" attribute="transactions" />
<ibusiness:sessionBean id="auditableFields" moduleName="mail.operations" screenID="mailtracking.defaults.mailaudithistory" method="get" attribute="auditableFields" />
		<div class="iCargoPopUpContent">
<ihtml:form action="/mailtracking.defaults.mailBagaudithistorylist.do" styleClass="ic-main-form">
<ihtml:hidden property="screenMode" />
	 <div class="ic-content-main">
		<div class="ic-head-container">
			<span class="ic-page-title ic-display-none"><common:message key="mailtracking.defaults.mailaudit.lbl.pagetitle"/></span>
			<div class="ic-filter-panel">
				<div class="ic-round border">
					<div class="ic-input ic-split-32">
						<common:message   key="mailtracking.defaults.mailaudit.lbl.MailBagID" />
							<logic:present property="mailbagId" name="form" >
								<ihtml:text property="mailbagId" componentID="CMP_Mailtracking_Defaults_MailBagAuditHistory_MALIDR" maxlength="29" disabled="true"/>
								<bean:define id="mailID" property="mailbagId" name="form" />
							</logic:present>
					</div>
					<div class="ic-input ic-split-24">
						<common:message   key="mailtracking.defaults.mailaudit.lbl.transactions" />
						<logic:present property="transaction" name="form" >
							<bean:define id="txnCode" name="form" property="transaction" />
						</logic:present>
						<ihtml:select property="transaction"  componentID="CMP_Mailtracking_Defaults_MailBagAuditHistory_Transaction" onchange="onTransactionNameChanged(this.form);">
							<html:option value=""><common:message key="combo.select"/></html:option>
							<logic:present name="transactionCodes">
								<logic:iterate id="transactionCode" name="transactionCodes">
									<bean:define id="parameterCode" name="transactionCode" property="key"/>
									<bean:define id="parameterValue" name="transactionCode" property="value"/>
									<html:option value="<%=(String)parameterCode%>"><%=(String)parameterValue%></html:option>
								</logic:iterate>
							</logic:present>
						</ihtml:select>
					</div>
					<div class="ic-input ic-split-25">
						<common:message   key="mailtracking.defaults.mailaudit.lbl.auditablefields" />
						<logic:present property="auditableField" name="form" >
							<bean:define id="auditableField" name="form" property="auditableField" />
						</logic:present>
						<ihtml:select property="auditableField"  componentID="CMP_Mailtracking_Defaults_MailBagAuditHistory_AuditableField">
							<html:option value=""><common:message key="combo.select"/></html:option>
							<logic:present name="auditableFields">
								<logic:iterate id="parameterValue" name="auditableFields" type="java.lang.String">
									<html:option value="<%=(String)parameterValue%>"><%=(String)parameterValue%></html:option>
								</logic:iterate>
							</logic:present>
						</ihtml:select>
					</div>
					<div class="ic-input ic-split-17">
						<div class="ic-button-container">
							<ihtml:nbutton property="btList"  componentID="CMP_Mailtracking_Defaults_MailBagAuditHistory_btList">
								<common:message    key="mailtracking.defaults.mailaudit.btn.list"/>
							</ihtml:nbutton>
							<ihtml:nbutton property="btClear" componentID="CMP_Mailtracking_Defaults_MailBagAuditHistory_btClear">
								<common:message   key="mailtracking.defaults.mailaudit.btn.clear"/>
							</ihtml:nbutton>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-main-container">
					<common:message   key="mailtracking.defaults.mailaudit.lbl.historyDetails"/>
					<div id="div3" class="tableContainer" align="center" style="height:514px" >
                        <table class="fixed-header-table">
                          <thead>
                           <tr class="iCargoTableHeadingLeft">
								<td class="iCargoTableHeader" style="width:12%;">
									<common:message   key="mailtracking.defaults.mailaudit.lbl.Transaction"/>
								</td>
								<td class="iCargoTableHeader" style="width:9%;">
									<common:message   key="mailtracking.defaults.mailaudit.lbl.auditablefield"/></td>
								<td class="iCargoTableHeader" style="width:22%;">
									<common:message   key="mailtracking.defaults.mailaudit.lbl.oldvalue"/></td>
								<td class="iCargoTableHeader" style="width:22%;">
									<common:message   key="mailtracking.defaults.mailaudit.lbl.newvalue"/></td>
								<td  class="iCargoTableHeader" style="width:9%;">
									<common:message  key="mailtracking.defaults.mailaudit.lbl.user"/></td>
								<td class="iCargoTableHeader" style="width:9%;">
									<common:message   key="mailtracking.defaults.mailaudit.lbl.station"/></td>
								<td  class="iCargoTableHeader" style="width:17%;">
									<common:message  key="mailtracking.defaults.mailaudit.lbl.transcationtime"/></td>
							</tr>
                          </thead>
						  <tbody>
						  <logic:present name="mailaudithistoryvos">
						<logic:iterate id="mailAuditHistoryVO" name="mailaudithistoryvos">
						<logic:present name="mailAuditHistoryVO" property="station">
						 </logic:present>
						<td class="iCargoTableDataTd"  style="width:12%;" ><common:write name="mailAuditHistoryVO" property="actionCode"/></td>
						<td class="iCargoTableDataTd"  style="width:9%;" ><bean:write name="mailAuditHistoryVO" property="auditField"/></td>
						<td class="iCargoTableDataTd"  style="width:22%;"><bean:write name="mailAuditHistoryVO" property="oldValue"/></td>
						<td class="iCargoTableDataTd"  style="width:22%;" ><bean:write name="mailAuditHistoryVO" property="newValue"/></td>
						<td class="iCargoTableDataTd"   style="width:9%;"><common:write name="mailAuditHistoryVO" property="userId"/></td>
						<td class="iCargoTableDataTd"  style="width:9%;"><bean:write name="mailAuditHistoryVO" property="stationCode"/></td>
						<td class="iCargoTableDataTd" style="width:17%;">
						<logic:present name="mailAuditHistoryVO" property="lastUpdateTime">
						<bean:define id ="lastUpdateTime" name = "mailAuditHistoryVO" property="lastUpdateTime" type="java.util.Calendar" />
					    <%String asgnDate=TimeConvertor.toStringFormat(lastUpdateTime,"dd-MMM-yyyy HH:mm");%>
					    <%=asgnDate%>
									</logic:present>
									</td>
						 </tr>
						</logic:iterate>
						  </logic:present>
						  </tbody>
                        </table>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-button-container">
				<ihtml:nbutton property="btClose" componentID="CMP_Mailtracking_Defaults_MailBagAuditHistory_btClose">
					<common:message bundle="MailAuditHistoryResources"  key="mailtracking.defaults.mailaudit.btn.close"/>
				</ihtml:nbutton>
			</div>
		</div>
	</div>
  </ihtml:form>
  </div>

	</body>
</html:html>