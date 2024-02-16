
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  PieceLevelTracking.jsp
* Date					:  16-dec-2005
* Author(s)				:  Akhila S

*************************************************************************/
 --%>
 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp" %>

<html:html>

<head>
	
<title><common:message bundle="sendEmailResources" key="products.defaults.title" scope="request"/></title>

<common:include type="script"
				src="/js/products/defaults/SendEmail_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui"></head>

<body id="bodyStyle">
	
	
	
<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<bean:define id="form"
	name="SendEmailForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.SendEmailForm"
	toScope="page" />
<div id="sendemailDiv" class="iCargoPopUpContent" style="overflow:auto;width:100%;height:100%">
<ihtml:form action="products.defaults.screenloadsendemail.do" styleClass="ic-main-form">
<div class="ic-content-main">
	<span class="ic-page-title ic-display-none">
		<common:message  key="products.defaults.SendEmail" scope="request"/>
	</span>
	<div class="ic-main-container">
		<div class="ic-input-container ic-border ic-label-15">
				<div class="ic-row">
					<div class="ic-input ic-mandatory ic-split-90">
						<label>
							<common:message  key="products.defaults.YourName" scope="request"/>
						</label>
						<ihtml:text property="name"	componentID="TXT_PRODUCTS_DEFAULTS_SENDEMAIL_YOURNAME" maxlength="50" tabindex="1" />				
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-mandatory ic-split-90  marginT5">
						<label>
							<common:message  key="products.defaults.YourEmail" scope="request"/>
						</label>
						<ihtml:text property="email" componentID="TXT_PRODUCTS_DEFAULTS_SENDEMAIL_YOUREMAIL" maxlength="75" tabindex="2"/>
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-mandatory ic-split-90  marginT5">
						<label>
							<common:message  key="products.defaults.FriendName" scope="request"/>
						</label>
						<ihtml:text property="friendName" componentID="TXT_PRODUCTS_DEFAULTS_SENDEMAIL_FRIENDSNAME" maxlength="50" tabindex="3"/>
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-mandatory ic-split-90  marginT5">
						<label>
							<common:message  key="products.defaults.FriendEmail" scope="request"/>
						</label>
						<ihtml:text property="friendEmail" componentID="TXT_PRODUCTS_DEFAULTS_SENDEMAIL_FRIENDSEMAL" maxlength="75" tabindex="4"/>
					</div>
				</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:nbutton property="btnSend" componentID="BTN_PRODUCTS_DEFAULTS_SENDEMAIL_SEND" tabindex="5">
					<common:message  key="products.defaults.Send" scope="request"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="BTN_PRODUCTS_DEFAULTS_SENDEMAIL_CLOSE" tabindex="6">
					<common:message  key="products.defaults.Close" scope="request"/>
				</ihtml:nbutton>
			</div>
		</div>
	</div>	
</div>

</ihtml:form>
</div>

	</body>
</html:html>

