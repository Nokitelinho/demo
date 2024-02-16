<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  MRA
* File Name				:  ListCCA_Ajax.jsp
* Date					:  22-OCT-2008
* Author(s)				:  A-3251
*************************************************************************/
 --%>


 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
 <%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm"%>
 <%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
 <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>	 
 <ihtml:form action="/mailtracking.mra.defaults.listcca.screenload.do">
 
 <bean:define id="form"
		 name="ListCCAForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm"
		 toScope="page" />

	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.listcca"
	 	  method="get"
	  attribute="OneTimeVOs" />

	 <business:sessionBean id="KEY_CCALIST"
		moduleName="mailtracking.mra.defaults"
		screenID="mailtracking.mra.defaults.listcca"
		method="get"
		attribute="CCADetailsVOs" />

	<business:sessionBean
			id="LIST_FILTERVO"
			moduleName="mailtracking.mra.defaults"
			screenID="mailtracking.mra.defaults.listcca"
			method="get"
		attribute="CCAFilterVO" />
		
		
	<ihtml:hidden property="lastPageNumber" />
    <ihtml:hidden property="displayPageNum" />
    <ihtml:hidden property="comboFlag" />	
		
	<div id="_gpanameDiv">
	<label><common:message key="mailtracking.mra.gpabilling.listmca.lbl.gpaname" /></label>
	<ihtml:text property="gpaName" maxlength="50" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_GPANAME" readonly="true"/>
	
	</div>	
		
 
 
 </ihtml:form>
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
 