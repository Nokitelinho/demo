<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : ListFormOne_Ajax.jsp
* Date                 	 : 30-July-2008
* Author(s)              : A-3434
*************************************************************************/
--%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ListMailFormOneForm"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<common:include type="script" src="/js/mail/mra/airlinebilling/outward/ListFormOne_Script.jsp"/>

<bean:define
		 id="listFormOneForm"
		 name="ListMailFormOneForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ListMailFormOneForm"
	 	 toScope="page" />

<ihtml:form action="/mailtracking.mra.airlinebilling.outward.listform1.onScreenLoad.do">

	  		<div id="ListFormOneChild">
				<ihtml:text componentID="CMP_MRA_AIRLINEBILLING_OUTWARD_AIRLINENO" property="airlineNumber" maxlength="3" />
			</div>


 </ihtml:form>
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>



