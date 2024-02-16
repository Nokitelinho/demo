<%--

* Project	 		: iCargo
* Module Code & Name:		: mra-airlinebilling
* File Name			: CaptureInvoice.jsp
* Date				: 11-June-2008
* Author(s)			: A-3447
 
 
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm" %>
<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor" %>
<%@ page import="java.util.HashMap"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@include file="/jsp/includes/reports/printFrame.jsp" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<bean:define id="form" name="CaptureMailInvoiceForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailInvoiceForm"
		toScope="page" />



<ihtml:form action="/mailtracking.mra.airlinebilling.inward.captureinvoice.onScreenLoad.do">

	<div id = "Childdiv" >
	<label><common:message key="mra.inwardbilling.captureinvoice.airlineno" scope="request"/></label>
	<ihtml:text property="airlineNo" componentID="CMP_MRA_AIRLINEBILLING_INWARD_AIRLINENO" maxlength="4"/>			
	<img name="airlinecodelov" id="airlinecodelov" height="16" src="<%=request.getContextPath()%>/images/lov.gif" width="16" />					
	    	</div>				
  </ihtml:form >
  	
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>