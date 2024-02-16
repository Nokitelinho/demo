<%--******************************
* Project	 				: iCargo
* Module Code & Name		: mailtrackng/mra
* File Name					: GPABillingReports_Ajax.jsp
* Date						: 02-05-2014
* Author(s)					: KrishnaManohar
 ********************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<bean:define id="form"
		 name="GPABillingReportsForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingReportsForm"
		 toScope="page" />


<ihtml:form action="/mailtracking.defaults.gpa.list.do">

<div class = "ic-content" id ="gpaNameDiv">
		<ihtml:text property="gpaName" componentID="MRA_GPABILLING_REPORTS_GPANAME_GPABILL" readonly="true"/>	
</div>
<div class = "ic-content" id ="gpaNameDiv1">
		<ihtml:text property="gpaNamePeriodBillSmy" componentID="MRA_GPABILLING_REPORTS_GPANAME_PCNSMY" readonly="true"/>	
</div>
<div class = "ic-content" id ="gpaNameDiv2">
		<ihtml:text property="gpaNameGpaBillSmy" componentID="MRA_GPABILLING_REPORTS_GPANAME_GCNSMY" readonly="true"/>		
</div>
<div class = "ic-content" id ="gpaNameDiv3">
		<ihtml:text property="gpaNamePeriod51" componentID="MRA_GPABILLING_REPORTS_GPANAME_PCN51" readonly="true"/>
</div>
<div class = "ic-content" id ="gpaNameDiv4">
		<ihtml:text property="gpaNameGpa51" componentID="MRA_GPABILLING_REPORTS_GPANAME_GCN51" readonly="true"/>
</div>
<div class = "ic-content" id ="gpaNameDiv5">
		<ihtml:text property="gpaNamePeriod66" componentID="MRA_GPABILLING_REPORTS_GPANAME_PCN66" readonly="true"/>
</div>
<div class = "ic-content" id ="gpaNameDiv6">
		<ihtml:text property="gpaNameGpa66" componentID="MRA_GPABILLING_REPORTS_GPANAME_GCN66" readonly="true"/>
</div>
</ihtml:form>

<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

