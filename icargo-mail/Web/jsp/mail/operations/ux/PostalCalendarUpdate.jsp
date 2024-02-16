<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.session.HttpSessionManager"%>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import = "java.util.Collection" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page info="lite" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm" %>
<%@ page import="com.ibsplc.icargo.framework.util.currency.Money"%>






		<!DOCTYPE html>

<html:html>

<head>






		 <title>Modify Postal Calendar</title>
    <meta name="decorator" content="popuppanelux">
	<common:include type="script" src="/js/mail/operations/ux/PostalCalendarUpdate_Script.jsp" />


</head>

<body>



	 <bean:define id="form"
	 name="MailPerformanceForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm"
	 toScope="page" />

	<ihtml:form action="/mail.operations.ux.mailperformance.postalcalendar.editcalendar.do">
	<ihtml:hidden property="postalCalendarAction"/>
		<div class="w-100 pad-md">
			 <div class="row">
				<div class="col-8">
				<div class="form-group">
					<label class="form-control-label"><common:message key="mailtracking.defaults.ux.mailperformance.postalcalendar.lbl.postalperiod"/></label>
					<logic:present	name="form" property="postalPeriod">
						<ihtml:text property="postalPeriod" name="form"
									  styleClass="form-control"
									  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_PERIOD" disabled="true"/>
					</logic:present>
					<logic:notPresent	name="form" property="postalPeriod">
						<ihtml:text   property="postalPeriod" name="form"
									  styleClass="form-control"  value=""
									  componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_PERIOD" disabled="true"/>
					</logic:notPresent>
					</div>
				</div>
				<div class="col-8">
				<div class="form-group">
					<label class="form-control-label"><common:message key="mailtracking.defaults.ux.mailperformance.postalcalendar.lbl.postalfrom"/></label>
					<logic:present	name="form" property="postalFromDate">
						<bean:define id="postalFromDate" name="form" property="postalFromDate" toScope="page"/>
							<ibusiness:litecalendar  id="postalFromDate" property="postalFromDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_FROM" value="<%=String.valueOf(postalFromDate)%>" disabled="true"/>
					</logic:present>
					<logic:notPresent	name="form" property="postalFromDate">
						<ibusiness:litecalendar  id="postalFromDate" property="postalFromDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_FROM" value="" disabled="true"/>
					</logic:notPresent>
					</div>
				</div>
				<div class="col-8">
				<div class="form-group">
					<label class="form-control-label"><common:message key="mailtracking.defaults.ux.mailperformance.postalcalendar.lbl.postalto"/></label>
					<logic:present	name="form" property="postalToDate">
						<bean:define id="postalToDate" name="form" property="postalToDate" toScope="page"/>
							<ibusiness:litecalendar  id="postalToDate" property="postalToDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_TO" value="<%=String.valueOf(postalToDate)%>" disabled="true"/>
					</logic:present>
					<logic:notPresent	name="form" property="postalToDate">
						<ibusiness:litecalendar  id="postalToDate" property="postalToDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_TO" value ="" disabled="true"/>
					</logic:notPresent>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-8">
				<div class="form-group">
					<label class="form-control-label"><common:message key="mailtracking.defaults.ux.mailperformance.postalcalendar.lbl.postalpayeft"/></label>
					<logic:present	name="form" property="postalDiscEftDate">
						<bean:define id="postalDiscEftDate" name="form" property="postalDiscEftDate" toScope="page"/>
							<ibusiness:litecalendar  tabindex="2" id="postalDiscEftDate" property="postalDiscEftDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_PAYEFT" value="<%=String.valueOf(postalDiscEftDate)%>" readonly="false"/>
					</logic:present>
					<logic:notPresent	name="form" property="postalDiscEftDate">
						<ibusiness:litecalendar  tabindex="2" id="postalDiscEftDate" property="postalDiscEftDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_PAYEFT" value=""/>
					</logic:notPresent>
					</div>
				</div>
				<div class="col-8">
				<div class="form-group">
					<label class="form-control-label"><common:message key="mailtracking.defaults.ux.mailperformance.postalcalendar.lbl.postalinccal"/></label>
					<logic:present	name="form" property="postalIncCalcDate">
						<bean:define id="postalIncCalcDate" name="form" property="postalIncCalcDate" toScope="page"/>
							<ibusiness:litecalendar tabindex="3"  id="postalIncCalcDate" property="postalIncCalcDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_INCCAL" value="<%=String.valueOf(postalIncCalcDate)%>" readonly="false"/>
					</logic:present>
					<logic:notPresent	name="form" property="postalIncCalcDate">
						<ibusiness:litecalendar tabindex="3"  id="postalIncCalcDate" property="postalIncCalcDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_INCCAL" value=""/>
					</logic:notPresent>
					</div>
				</div>
				<div class="col-8">
				<div class="form-group">
					<label class="form-control-label"><common:message key="mailtracking.defaults.ux.mailperformance.postalcalendar.lbl.postalinceft"/></label>
					<logic:present	name="form" property="postalIncEftDate">
						<bean:define id="postalIncEftDate" name="form" property="postalIncEftDate" toScope="page"/>
							<ibusiness:litecalendar tabindex="4"  id="postalIncEftDate" property="postalIncEftDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_INCEFT" value="<%=String.valueOf(postalIncEftDate)%>" readonly="false"/>
					</logic:present>
					<logic:notPresent	name="form" property="postalIncEftDate">
						<ibusiness:litecalendar tabindex="4"  id="postalIncEftDate" property="postalIncEftDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_INCEFT" value=""/>
					</logic:notPresent>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-8">
				<div class="form-group">
					<label class="form-control-label"><common:message key="mailtracking.defaults.ux.mailperformance.postalcalendar.lbl.postalincrcv"/></label>
					<logic:present	name="form" property="postalIncRecvDate">
						<bean:define id="postalIncRecvDate" name="form" property="postalIncRecvDate" toScope="page"/>
							<ibusiness:litecalendar tabindex="5"  id="postalIncRecvDate" property="postalIncRecvDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_INCRCV" value="<%=String.valueOf(postalIncRecvDate)%>" readonly="false"/>
					</logic:present>
					<logic:notPresent	name="form" property="postalIncRecvDate">
						<ibusiness:litecalendar tabindex="5"  id="postalIncRecvDate" property="postalIncRecvDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_INCRCV" value=""/>
					</logic:notPresent>
					</div>
				</div>
				<div class="col-8">
				<div class="form-group">
					<label class="form-control-label"><common:message key="mailtracking.defaults.ux.mailperformance.postalcalendar.lbl.postalclmgen"/></label>
					<logic:present	name="form" property="postalClaimGenDate">
						<bean:define id="postalClaimGenDate" name="form" property="postalClaimGenDate" toScope="page"/>
							<ibusiness:litecalendar tabindex="6"  id="postalClaimGenDate" property="postalClaimGenDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_CLMGEN" value="<%=String.valueOf(postalClaimGenDate)%>" readonly="false"/>
					</logic:present>
					<logic:notPresent	name="form" property="postalClaimGenDate">
						<ibusiness:litecalendar tabindex="6"  id="postalClaimGenDate" property="postalClaimGenDate" componentID="CMP_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_CLMGEN" value=""/>
					</logic:notPresent>
				</div>
				</div>
			</div>
		</div>
		<div class="w-100">
            <div class="btn-row">
				<ihtml:nbutton styleClass="btn btn-primary" id="btnPostalUpdate" property="btnPostalUpdate" accesskey ="U" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_UPDATE">
					<common:message key="mailtracking.defaults.postalcalendar.tooltip.btupdate" />
			    </ihtml:nbutton>
				<ihtml:nbutton styleClass="btn btn-primary" id="btnPostalClose" property="btnPostalClose" accesskey ="C" componentID="BUT_MAILTRACKING_DEFAULTS_UX_MAILPERFORMANCE_POSTAL_CLOSE">
					<common:message key="mailtracking.defaults.ux.mailperformance.btn.close" />
			    </ihtml:nbutton>
			</div>
		</div>

	</ihtml:form>


	</body>


</html:html>

