
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
	name="CheckAvailabilityForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.CheckAvailabilityForm"
	toScope="page" />
<title>
<common:message bundle="<%=form.getBundle()%>"  key="products.defaults.title" scope="request"/>
</title>
<common:include type="script"
				src="/js/products/defaults/CheckAvailability_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">
</head>

<body id="bodyStyle" >
	
	
	
<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<div class="iCargoPopUpContent ic-masterbg" style="overflow:auto;">
<ihtml:form action="products.defaults.screenloadcheckavailability.do" styleClass="ic-main-form">
<html:hidden property="code" />


<div class="ic-content-main bg-white">
	<span class="ic-page-title ic-display-none">
		<common:message  key="products.defaults.screenName" scope="request"/>
	</span>
	<div class="ic-main-container">
		<div class="ic-input-container ic-label-30">
			<div class="ic-row">			
				<div class="ic-input ic-mandatory ic-split-33">
					<label>
						<common:message  key="products.defaults.Origin" scope="request"/>
					</label>
					<ihtml:text  property="origin" componentID="TXT_PRODUCTS_DEFAULTS_CHECKAVAILABILITY_ORIGIN" maxlength="3" tabindex="1" onblur="validateOD(this,origin,destination);"/>
					<div class="lovImg">
					<img src="<%=request.getContextPath()%>/images/lov.png"
						onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[0].origin.value,'Origin','0','origin','','0')" tabindex="2">
				</div>
				</div>
				<div class="ic-input ic-mandatory ic-split-33">
					<label>
						<common:message  key="products.defaults.Destination" scope="request"/>
					</label>
					<ihtml:text property="destination" componentID="TXT_PRODUCTS_DEFAULTS_CHECKAVAILABILITY_DESTN" maxlength="3" tabindex="3" onblur="validateOD(this,origin,destination);"/>
					<div class="lovImg">
					<img src="<%=request.getContextPath()%>/images/lov.png" 
						onclick="displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[0].destination.value,'Destination','0','destination','','0')" tabindex="4">
				</div>
			</div>
			
			
				<div class="ic-input ic-mandatory ic-split-33">
					<label>
						<common:message  key="products.defaults.Commodity" scope="request"/>
					</label>
					<ihtml:text property="commodity" componentID="TXT_PRODUCTS_DEFAULTS_CHECKAVAILABILITY_COMMODITY" maxlength="10" tabindex="5"/>
					<div class="lovImg">
					<img src="<%=request.getContextPath()%>/images/lov.png" 
						onclick="displayLOV('showCommodity.do','N','Y','showCommodity.do',document.forms[0].commodity.value,'Commodity','0','commodity','','0')" tabindex="6">
				</div>
				</div>
				</div>
				<div class="ic-row">
					 <div class="ic-col-100">
						<div class="ic-button-container">
					<ihtml:nbutton property="btnCheck" componentID="BTN_PRODUCTS_DEFAULTS_CHECKAVAILABILITY_CHECK" tabindex="7">
						<common:message  key="products.defaults.Check" scope="request"/>
					</ihtml:nbutton>
					<ihtml:nbutton property="btnClose" componentID="BTN_PRODUCTS_DEFAULTS_CHECKAVAILABILITY_CLOSE" tabindex="8">
						<common:message  key="products.defaults.Close" scope="request"/>
					</ihtml:nbutton>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</ihtml:form>
</div>   

</body>
</html:html>

