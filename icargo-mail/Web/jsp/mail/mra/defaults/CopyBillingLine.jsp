<%--******************************************************************
* Project	 				: iCargo
* Module Code & Name				: MRA.DEFAULTS
* File Name					: CopyBillingLine.jsp
* Date						: 21-MAR-2007
* Author(s)					: Indu V.K.
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
<bean:define id="form"
		 name="CopyBlgLineForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyBlgLineForm"
		 toScope="page" />
			
	
<html:html>
<head> 

	
			
<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.defaults.copybillingline.title" /></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/CopyBillingLine_Script.jsp" />
</head>
<body>
	
	



<div class="iCargoPopUpContent" style="width:460px;">
 <ihtml:form action="/mailtracking.mra.defaults.screenloadcopybillingline.do" styleClass="ic-main-form" >
  <ihtml:hidden property="screenMode" />
  <ihtml:hidden property="screenFlag" />
  <ihtml:hidden property="opFlag" />

  <div class="ic-content-main">
		
			<span class="ic-page-title ic-display-none">
				<common:message  key="mailtracking.mra.defaults.copybillingline.pagetitle" />
			</span>
			<!--Modified by A-8236 as part of ICRD-250611-->
			<div class="ic-head-container"> 
			<div class="ic-filter-panel">
			<div class="ic-row"> <!--End-->
					<div class="ic-input ic-split-60 ic-mandatory">
					<label><common:message  key="mailtracking.mra.defaults.copybillingline.billingmatrixid" /></label>
					<ihtml:text name="form" property="blgMatrixId"  componentID="CMP_MRA_DEFAULTS_COPYBILLINGLINE_BILLINGMATRIXID" />
						<div class="lovImg">
					<img src="<%=request.getContextPath()%>/images/lov.png" id="blgMatrixIDLov" height="22" width="22" alt="" /></div>
					</div>
					<div class="ic-input ic-split-40 ic-mandatory">
					<div class="ic-button-container  paddR5">
					<ihtml:nbutton property="btnList" componentID="CMP_MRA_DEFAULTS_COPYBILLINGLINE_BTNLIST" >
							<common:message key="mailtracking.mra.defaults.copybillingline.btn.list" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" componentID="CMP_MRA_DEFAULTS_COPYBILLINGLINE_BTNCLEAR" >
							<common:message key="mra.derfaults.copybillingline.btn.clear" />
						</ihtml:nbutton>
					</div>
					</div>
			</div>
			</div>
			</div>
		
		<div class="ic-main-container"> <!--Modified by A-8236 as part of ICRD-250611 -->
		<div class="ic-row paddL10">
					<div class="ic-input ic-split-50 ic-mandatory">
					<label><common:message  key="mailtracking.mra.defaults.copybillingline.lbl.validfrom" /></label>
					<ibusiness:calendar id="validFrom"
									 property="validFrom"
									 componentID="CMP_MRA_DEFAULTS_COPYBILLINGLINE_FRMDATE"
									 type="image"
								 maxlength="11"/>
					</div>
					<div class="ic-input ic-split-50 ic-mandatory">
					<label><common:message  key="mailtracking.mra.defaults.copybillingline.lbl.validto" /></label>
					<ibusiness:calendar id="validTo"
									 property="validTo"
									 componentID="CMP_MRA_DEFAULTS_COPYBILLINGLINE_TODATE"
									 type="image"
								 maxlength="11"/>
					</div>
		</div>
		</div>
		<div class="ic-foot-container paddR5">
		<div class="ic-row">
		<div class="ic-button-container">
				<ihtml:nbutton property="btnOk" componentID="CMP_MRA_DEFAULTS_COPYBILLINGLINE_OK_BTN" >
					<common:message key="mailtracking.mra.defaults.copybillingline.btn.btnok" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btClose" componentID="CMP_MRA_DEFAULTS_COPYBILLINGLINE_CLOSE_BTN" >
					<common:message key="mailtracking.mra.defaults.copybillingline.btn.btnClose" />
				</ihtml:nbutton>
		</div>
		</div>
		</div>
		</div>
</ihtml:form>
</div>	
		
	</body>
</html:html>
