
<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : CaptureFormThree.jsp
* Date                 	 :08-Sep-2008
* Author(s)              : A-2554
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<bean:define id="form"
name="CaptureMailFormThreeForm"
type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.CaptureMailFormThreeForm"
toScope="page" />


  <business:sessionBean id="KEY_CAPTUREFORM3"
   		  moduleName="mailtracking.mra.airlinebilling"
   		  screenID="mailtracking.mra.airlinebilling.inward.captureformthree"
   		  method="get"
	attribute="airlineNumbericCode" />

<ihtml:form action="/mailtracking.mra.airlinebilling.inward.captureFormThree.ajaxOnScreenLoad.do">
			
			
			<logic:present name="KEY_CAPTUREFORM3" >
			<bean:define id="airlineNum" name="KEY_CAPTUREFORM3" type="java.lang.String"/>
			<div id="childNumericCode">	
			<%=airlineNum%>
			</div>
			</logic:present>

		  </ihtml:form >
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
		