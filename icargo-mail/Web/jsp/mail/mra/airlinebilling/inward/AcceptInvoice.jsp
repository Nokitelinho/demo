<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : RemarksPopUp.jsp
* Date                 	 : 24-Jul-2007
* Author(s)              : A-2391
*************************************************************************/
--%>
<%@ page language="java" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import="com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceVO"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm" %>
<%@ page import="com.ibsplc.icargo.presentation.web.session.impl.mail.mra.airlinebilling.inward.InvoiceExceptionsSessionImpl" %>



<bean:define id="form"
		 name="InvoiceExceptionsForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm"
		 toScope="page" />

<html:html>
<head> 
		
	
			

<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.airlinebilling.inward.remarks.title" /></title>
<meta name="decorator" content="popup_panel">

<common:include type="script" src="/js/mail/mra/airlinebilling/inward/AcceptInvoice_Script.jsp" />
</head>

<body id="bodyStyle">
	

<div class="iCargoPopUpContent" >
	<ihtml:form action="/mailtracking.mra.airlinebilling.inward.remarkspopup.do" styleClass="ic-main-form">
		<ihtml:hidden property="screenStatus"/>
		<div class="ic-content-main">
			<div class="ic-main-container">
				<div class ="ic-border">
					<div class="ic-row">
						<span class="ic-page-title ic-display-none"><common:message key="mailtracking.mra.airlinebilling.inward.remarks.pagetitle" /></span>
					</div>
					<div class="ic-row ">
						<div class="ic-input ic-split-100">
							<common:message key="mailtracking.mra.airlinebilling.inward.remarks.remarks" />
							<ihtml:textarea property="popupRemarks" componentID="TXTAREA_MRA_AIRLINEBILLING_INWARD_REMARKS"  cols="60" rows="3" />
						</div>
					</div>
					<div class="ic-row ">
						<div class="ic-button-container ">	
							<ihtml:nbutton property="btnOk" componentID="CMP_MRA_AIRLINEBILLING_INWARD_OK" >
								<common:message key="mailtracking.mra.airlinebilling.inward.remark.button.ok" />
							</ihtml:nbutton>
							<ihtml:nbutton property="btClose" componentID="CMP_MRA_AIRLINEBILLING_INWARD_CLOSE" >
								<common:message key="mailtracking.mra.airlinebilling.inward.remark.button.close" />
							</ihtml:nbutton>
						</div>
					</div>	
				</div>	
			</div>
		</div>
	</ihtml:form>
</div>		


	</body>
</html:html>
