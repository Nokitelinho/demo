<%--
* Project 				: iCargo
* Module Code & Name	: Mail MRA
* File Name				: InvoicRejectPopup.jsp
* Date					: 17-Jun-2021
* Author(s)				: A-8176
--%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm" %>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp"%>
		
	
<html:html>
	<head>
	   <common:include type="script" src="/js/mail/mra/gpareporting/ux/InvoicRejectPopup_Script.jsp"/>
		<title></title>
		<meta name="decorator" content="popuppanelrestyledui">
		</head>

	<body>

	<bean:define id="form" name="ListInvoicForm" toScope="page" type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm"/>

	<div class="iCargoPopUpContent">
	<ihtml:form action="mail.mra.gpareporting.ux.listinvoic.rejectConfirmation.do" styleClass="ic-main-form">
	     
    <div class="ic-content-main">
			
			<div class="ic-head-container">
			<div class="ic-filter-panel">
			<div class="ic-section  ic-pad-2"> <!--Modified by A-8146 for ICRD-261809-->
			<ihtml:hidden name="listInvoicForm" property="rejectFlag"  value="true"/>
				<div class="ic-row">
					<div>
						<label>Remarks</label>
						 <html:textarea property="remarks" title=" Remarks"   cols="50" rows="3"  ></html:textarea>
					</div>
					
					
				</div>
				
			</div>	
			</div>
			
		</div>
		<div class="ic-foot-container">
			<div class="ic-button-container">
			 <ihtml:nbutton styleClass="btn primary"  property="btnSave" id="btnSave">
							<common:message	key="mail.mra.gpareporting.ux.listinvoic.btn.remarksSave" />
						</ihtml:nbutton>
					<ihtml:nbutton styleClass="btn btn-default" property="btnClose" id="btnClose" >
							<common:message	key="mail.mra.gpareporting.ux.listinvoic.btn.remarksClose" />
						</ihtml:nbutton>
			</div>
		</div>
		
	</div>
	</ihtml:form>
				
		 
	</body>
</html:html>
