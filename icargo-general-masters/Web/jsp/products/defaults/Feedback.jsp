
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
		
<bean:define id="form"
	name="FeedbackForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.FeedbackForm"
	toScope="page" />

<title><common:message bundle="<%=form.getBundle()%>" key="products.defaults.title" scope="request"/></title>
<common:include type="script" src="/js/products/defaults/Feedback_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">
</head>

<body id="bodyStyle">
	
	
	
<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<%boolean statusDisable = false;%>
<logic:equal name="FeedbackForm" property="mode" value="Y" >
   	<% statusDisable = true;
   	 %>
 </logic:equal>
 
<div id="feedbackDiv" class="iCargoPopUpContent ic-masterbg" style="overflow:auto;">
<ihtml:form action="products.defaults.screenloadfeedback.do" styleClass="ic-main-form">
<html:hidden property="code" />
<html:hidden property="saveSuccessful" />

<div class="ic-content-main bg-white">
	<div class="ic-main-container">
		<div class="ic-row">
			<h3>
				<common:message  key="products.defaults.Feedback" scope="request"/>
			</h3>
		</div>
		<div class="ic-row">
			<div class="ic-section ic-input-container ic-border ic-label-15">
				<div class="ic-row">
					<div class="ic-input ic-mandatory ic-split-90">
						<label>
							<common:message  key="products.defaults.Name" scope="request"/>
						</label>
						<ihtml:text property="name" componentID="TXT_PRODUCTS_DEFAULTS_FEEDBACK_NAME" maxlength="50" tabindex="1"
						   value="<%=form.getName()%>" readonly="<%=statusDisable%>"/>						
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-mandatory ic-split-90   marginT5">
						<label>
							<common:message  key="products.defaults.Email" scope="request"/>
						</label>
						 <ihtml:text property="email" componentID="TXT_PRODUCTS_DEFAULTS_FEEDBACK_YOUREMAIL" maxlength="75" tabindex="2"
							value="<%=form.getEmail()%>" readonly="<%=statusDisable%>"/>		
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-split-90  marginT5">
						<label>
							<common:message  key="products.defaults.Address" scope="request"/>
						</label>
						<ihtml:textarea property="address" cols="46" componentID="TXT_PRODUCTS_DEFAULTS_FEEDBACK_ADDRESS"  tabindex="3"
							value="<%=form.getAddress()%>" readonly="<%=statusDisable%>">
						</ihtml:textarea>		
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-input ic-split-90   marginT5">
						<label>
							<common:message  key="products.defaults.Comments" scope="request"/>
						</label>
						<ihtml:textarea property="comments" cols="46" componentID="TXT_PRODUCTS_DEFAULTS_FEEDBACK_COMMENTS"	tabindex="4"
							value="<%=form.getComments()%>" readonly="<%=statusDisable%>" >
						</ihtml:textarea>			
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container paddR5">
				<ihtml:nbutton property="btnSend" componentID="BTN_PRODUCTS_DEFAULTS_FEEDBACK_SEND" tabindex="5" disabled="<%=statusDisable%>">
					<common:message key="products.defaults.Send" scope="request"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="BTN_PRODUCTS_DEFAULTS_FEEDBACK_CLOSE" tabindex="6">
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

