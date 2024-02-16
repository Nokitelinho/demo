<%--
* Project	 	: iCargo
* Module Code & Name	: Mail Tracking
* File Name		: ContainerChangePopUp.jsp.jsp
* Date			: 13-Jan-2016
* Author(s)		: A-6245
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html>

<head> 
	
	<%@ include file="/jsp/includes/customcss.jsp" %>
	<title><common:message bundle="mailArrivalResources" key="mailtracking.defaults.containerchange.lbl.pagetitle" /></title>
	<meta name="decorator" content="popup_panel">
	<common:include type="script" src="/js/mail/operations/ContainerChangePopUp_Script.jsp" />
</head>

<body>
	
<div id="divmain" class="iCargoPopUpContent">
<ihtml:form action="/mailtracking.defaults.containerchange.screenload.do" styleClass="ic-main-form">

<bean:define id="form"
             name="MailArrivalForm"
             type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm"
             toScope="page" scope="request"/>
<ihtml:hidden property="popupCloseFlag" />
<ihtml:hidden property="childCont" />

<div class="ic-content-main">
		<div class="ic-head-container">	
			<span class="ic-page-title ic-display-none">
				<common:message key="mailtracking.defaults.containerchange.lbl.pagetitle" />
			</span>
			<div class="ic-filter-panel">
			<div class="ic-row ic-border">
			<div class="ic-row">
					<div class="ic-input ic-col-30 ic-mandatory">
					<label><common:message key="mailtracking.defaults.mailarrival.lbl.flightno" /></label>


					<ibusiness:flightnumber id="fltNo" carrierCodeProperty="fltCarrierCode"  
					flightCodeProperty="fltNumber" carriercodevalue="<%=form.getFltCarrierCode()%>" flightcodevalue="<%=form.getFltNumber()%>" 
					carrierCodeMaxlength="3" flightCodeMaxlength="5" componentID="CMP_MAILTRACKING_DEFAULTS_MAILARRIVAL_FLIGHTNUMBER"/>
					</div>
					<div class="ic-input ic-col-30 ic-mandatory">
					<label><common:message key="mailtracking.defaults.mailarrival.lbl.flightdate" /></label>
					<ibusiness:calendar property="arrDate" id="flightDate"
				   type="image" value="<%=form.getArrDate()%>" componentID="CMP_MAILTRACKING_DEFAULTS_MAILARRIVAL_DATE" />
					</div>
					<div class="ic-input ic-col-40 ic-mandatory">
				<label><common:message key="mailtracking.defaults.mailarrival.lbl.scandatetime" /></label>


				<ibusiness:calendar property="flightScanDate" id="flightScanDate" type="image" componentID="CMB_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANDATE" value="<%=form.getFlightScanDate()%>" />
				<ibusiness:releasetimer property="flightScanTime" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_SCANTIME" id="flightScanTime"  type="asTimeComponent" value="<%=form.getFlightScanTime()%>" />
				</div>
			</div>
			
				<div class="ic-row ic-col-80">
				<label><common:message key="mailtracking.defaults.mailarrival.lbl.remarks" /></label>


				<ihtml:textarea property="remarks" cols="45" rows="3" componentID="TXT_MAILTRACKING_DEFAULTS_ARRIVEMAIL_REMARKS" value="" style="height:48px"></ihtml:textarea>

				</div>
			</div>
			</div>
			</div>
				<div class="ic-foot-container">
			<div class="ic-button-container">
			        <ihtml:nbutton property="btnOk"   componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_OK" accesskey="O">
					<common:message key="mailtracking.defaults.arrivemail.btn.ok" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClear"   componentID="BTN_MAILTRACKING_DEFAULTS_MAILARRIVAL_CLEAR" accesskey="C">
						<common:message key="mailtracking.defaults.mailarrival.btn.clear" />
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose"   componentID="BTN_MAILTRACKING_DEFAULTS_ARRIVEMAIL_CANCEL" accesskey="S">
						<common:message key="mailtracking.defaults.arrivemail.btn.cancel" />
					</ihtml:nbutton>
			</div>
		</div>
		</div>
</div>

</ihtml:form>
</div>
		
	</body>
</html:html>
