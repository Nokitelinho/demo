<%--******************************************************************
* Project	 				: iCargo
* Module Code & Name				: MRA.DEFAULTS
* File Name					: ChangeEndDatePopup.jsp
* Date						: 13-Dec-2022
* Author(s)					: Teena
 *********************************************************************--%>

<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyBlgLineForm" %>
<bean:define id="form" name="BillingMatrixForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm"
	toScope="page" />
			
					
	
<html:html>
<head> 
		
<title><common:message bundle="billingmatrix" key="mailtracking.mra.defaults.changeenddatepopup.title" /></title>	
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/ChangeEndDatePopup_Scripts.jsp" />
</head>
<body>
		<logic:present name="popup">
			<div id="walkthroughholder"> </div>
		</logic:present>
	
	
	



<div class="iCargoPopUpContent" style="width:600px;">
 <ihtml:form action="/mailtracking.mra.defaults.maintainbillingmatrix.changedate.do" styleClass="ic-main-form" >
 <html:hidden name="BillingMatrixForm" property="formStatusFlag" />
 <html:hidden name="BillingMatrixForm" property="selectedIndex" />
  

 <div class="ic-content-main">
		
	<div class="container">
		
		
	    <div class="ic-main-container" style="width:570px;> <!--Modified by A-8236 as part of ICRD-250611 -->
		  
		    <div class="ic-row paddL10" >
			<br>
			<table style = "border:.1px groove grey;width:550px;height:110px;border-radius:2px;" id = "newtable">
		    <tr>
		    <td>
					
					<div class="ic-input ic-split-40 ic-mandatory">
					
														
					</div>
					<div class="ic-input ic-split-50 ic-mandatory">
					<label><common:message  key="mailtracking.mra.defaults.maintainbillingmatrix.tooltip.EndDate" /></label>
					
					<ibusiness:calendar id="validFrom"
									 property="validFrom"
									 componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ValidFrom" type="image" maxlength="11" />
														
					</div>
			</td>
			</tr>
		  </table>
		    </div>
		</div>
		
		
		
		
		
		
		
		
		
		<div class="ic-foot-container paddR5">
			<div class="ic-button-container">	
			<ihtml:nbutton property="btnSave" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_SaveBtn">
				<common:message key="mailtracking.mra.defaults.maintainbillingmatrix.tooltip.btnsave" />
			</ihtml:nbutton>
			<ihtml:nbutton property="btnClose" componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_CloseBtn" onclick="window.close();">
				<common:message key="mailtracking.mra.defaults.maintainbillingmatrix.tooltip.btnclose" />
			</ihtml:nbutton>
		</div>
		</div>
		
		
		</div>
		
		
</ihtml:form>
</div>	

	</body>
</html:html>
