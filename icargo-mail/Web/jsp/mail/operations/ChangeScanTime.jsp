<%--
 /***********************************************************************
* Project	 		:  iCargo
* Module Code & Name		:  Mailtracking
* File Name			:  LookupDocument.jsp
* Date				:  29-Jan-2007
* Author(s)			:  A-1876
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"%>
 		
	 <html:html>
  <head> 
	
			
	
	<%@ include file="/jsp/includes/customcss.jsp" %>
 	<title><common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.changescantime.lbl.title" /></title>
 	<meta name="decorator" content="popup_panel">
 	 <common:include type="script" src="/js/mail/operations/ChangeScanTime_Script.jsp"/>

 </head>
 
 <body id="bodyStyle">
	
 	
 <bean:define id="MailAcceptanceForm" name="MailAcceptanceForm"
         type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm"
  	 toScope="page" scope="request"/>
	 <div class="iCargoPopUpContent ic-masterbg" style="overflow:auto;height:170px;">
	 <ihtml:form action="/mailtracking.defaults.mailacceptance.lookup.do" styleClass="ic-main-form">
<ihtml:hidden property="scanTimeFlag" />
<ihtml:hidden property="scanTimeFromScreen" />
<ihtml:hidden property="strToDelivery" />

<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />
<div class="ic-content-main">
			<div class="ic-head-container">	
			</div>
		  <div class="ic-main-container">
		   <div class="ic-row">
			 <span class="ic-page-title ic-display-none">  <common:message key="mailtracking.defaults.changescantime.lbl.pagetitle" />
					</span>
		 </div>
		 
		
		   <div class ="ic-row">
		  <div class="ic-input-container  ic-round-border">

<div class="ic-input ic-mandatory ic-split-100 multi-input">
		   <label>
		   <common:message key="mailtracking.defaults.changescantime.lbl.date" />
		   </label>

		    <ibusiness:calendar property="scanDate" id="scanDate" type="image" componentID="CMP_MAILTRACKING_DEFAULTS_CHANGESCANTIME_DATE"/>
		   <label>
		 <common:message key="mailtracking.defaults.changescantime.lbl.time" />
		   </label>
		    <ibusiness:releasetimer property="scanTime" componentID="CMP_MAILTRACKING_DEFAULTS_CHANGESCANTIME_TIME" id="scanTime"  type="asTimeComponent"/>
		   </div>

		   </div>
		 
		  </div>
		  </div>
		  <div class="ic-foot-container">
		  <div class="ic-button-container">
	<ihtml:nbutton property="btnOk" componentID="BTN_MAILTRACKING_DEFAULTS_CHANGESCANTIME_OK">
					<common:message key="mailtracking.defaults.changescantime.btn.ok" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="BTN_MAILTRACKING_DEFAULTS_CHANGESCANTIME_CANCEL">
					<common:message key="mailtracking.defaults.changescantime.btn.cancel" />
				</ihtml:nbutton>
		  </div>
		  </div>
</div>

</ihtml:form>
	 </div>
	 	
		 
	</body>
 </html:html>