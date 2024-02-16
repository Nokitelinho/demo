
 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.MRAAirlineAuditForm"%>

 <bean:define id="form"
	 name="MRAAirlineAuditForm"
	 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.MRAAirlineAuditForm"
	 toScope="page" />

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

	<ihtml:form action="/mailtracking.mra.airlinebilling.defaults.airlineauditscreenload.do">

	<ihtml:hidden property="screenStatusFlag"/>


	<div id="_filter">
	
	<div class="ic-section" style="height:80%;">
		<div class="ic-input-round-border" style="height:100%;">
			<div class="ic-input ic-col-40">
				<label>
					 <common:message key="mailtracking.mra.airlinebilling.defaults.audit.lbl.airlinecode"/>
				</label>
					<ihtml:text property="airlineCode" componentID="TXT_MAILTRACKING_MRA_DEFAULTS_AUDIT_ARLCOD" maxlength="6"/>
				<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="airlineCodeLov" height="22" width="22"
		         onClick="displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineCode.value,'Airline Code','1','airlineCode','',0)" alt="" />
			</div>
			</div>
			<div class="ic-input ic-col-30">
				<label>
					<common:message key="mailtracking.mra.airlinebilling.defaults.audit.lbl.clearanceperiod"/>
				</label>
				<ihtml:text property="clearancePeriod" componentID="TXT_MAILTRACKING_MRA_DEFAULTS_AUDIT_CLRPRD" maxlength="6"/>
				<div class="lovImg"><img src="<%=request.getContextPath()%>/images/lov.png" id="clearancePeriodLov" height="22" width="22"
				onClick="displayLOV('showClearancePeriods.do','N','Y','showClearancePeriods.do',targetFormName.clearancePeriod.value,'Clearance Period Details','1','clearancePeriod','',0)"
				alt="" />
				</div>
			</div>
		</div>
	</div>
		

	<div class="ic-button-container">
			<ihtml:nbutton accesskey = "l" property="btList" componentID="BUT_MAILTRACKING_MRA_DEFAULTS_AUDIT_LIST">
				<common:message key="mailtracking.mra.defaults.oemaster.btn.audlist" />
			</ihtml:nbutton>

			<ihtml:nbutton accesskey = "c" property="btClear" componentID="BUT_MAILTRACKING_MRA_DEFAULTS_AUDIT_CLEAR">
				<common:message key="mailtracking.defaults.oemaster.btn.audclear" />
			</ihtml:nbutton>
	</div>

	</div>
   </ihtml:form>

<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
