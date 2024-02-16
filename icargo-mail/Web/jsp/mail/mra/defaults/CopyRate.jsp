<%--******************************************************************
* Project	 				: iCargo
* Module Code & Name				: MRA.DEFAULTS
* File Name					: CopyRate.jsp
* Date						: 07-FEB-2007
* Author(s)					: Prem Kumar.M
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
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyRateForm" %>
<bean:define id="form"
		 name="CopyRateForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyRateForm"
		 toScope="page" />
			
	
<html:html>
<head> 
	
<title><common:message bundle="<%=form.getBundle()%>" key="mailtracking.mra.defaults.copyrate.title" /></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/CopyRate_Script.jsp" />
</head>
<body>
	









<div class="iCargoPopUpContent" style="overflow:auto;height:160px;">
 <ihtml:form action="/mailtracking.mra.defaults.screenloadcopyrate.do" styleClass="ic-main-form" >
  <ihtml:hidden property="screenMode" />
  <ihtml:hidden property="screenFlag" />
	<div class="ic-content-main">
<span class="ic-page-title ic-display-none">
<label><common:message  key="mailtracking.mra.defaults.copyrate.pagetitle" /></td>
		  </label>
							</span>
		      	<div class="ic-head-container">

<div class="ic-filter-panel" >
<div class="ic-input-container">
<div class="ic-row">
							<div class="ic-input ic-split-60 ic-label-50 ic-mandatory"><label><common:message  key="mailtracking.mra.defaults.copyrate.ratecardID" /></label>
						 
							<ihtml:text name="form" property="rateCardId"  componentID="CMP_MRA_DEFAULTS_COPYRATE_RATECARDID" />
							<div class="lovImg">
							<img name="rateCardLov" id="rateCardLov"
								 src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="" />
							</div>
						</div>
						<div class="ic-input ic-split-40">
						<div class="ic-button-container">
						  <ihtml:nbutton property="btnList" componentID="CMP_MRA_DEFAULTS_COPYRATE_BTNLIST" accesskey="L" >
							<common:message key="mailtracking.mra.defaults.copyrate.btn.list" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" componentID="CMP_MRA_DEFAULTS_COPYRATE_BTNCLEAR" accesskey="C" >
							<common:message key="mra.derfaults.copyrate.btn.clear" />
						</ihtml:nbutton>
                        </div>
						</div>
						</div>
						</div>
                        </div>
						</div>
						<div class="ic-main-container">
					 <div class="ic-row">
					 							<div class="ic-input ic-split-50 ic-label-35"><label><common:message  key="mailtracking.mra.defaults.copyrate.lbl.validfrom" /></label>
						
							<ibusiness:calendar id="validFrom"
									 property="validFrom"
									 componentID="CMP_MRA_DEFAULTS_COPYRATE_FRMDATE"
									 type="image"
								 maxlength="11"/>
					</div>

                                         <div class="ic-input ic-split-50 ic-label-33"><label><common:message  key="mailtracking.mra.defaults.copyrate.lbl.validto" /></label>
						 
							<ibusiness:calendar id="validTo"
									 property="validTo"
									 componentID="CMP_MRA_DEFAULTS_COPYRATE_TODATE"
									 type="image"
								 maxlength="11"/>
</div>
						</div>
                       </div>
<div class="ic-foot-container paddR5">
			<div class="ic-row">
					<div class="ic-button-container">


			    <ihtml:nbutton property="btnOk" componentID="CMP_MRA_DEFAULTS_COPYRATE_OK_BTN" accesskey="O" >
					<common:message key="mailtracking.mra.defaults.copyrate.btn.btnok" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btClose" componentID="CMP_MRA_DEFAULTS_COPYRATE_CLOSE_BTN" accesskey="S" >
					<common:message key="mailtracking.mra.defaults.copyrate.btn.btnClose" />
				</ihtml:nbutton>
</div>
</div>
			</div>
			</div>

</ihtml:form>
</div>




	</body>
</html:html>
