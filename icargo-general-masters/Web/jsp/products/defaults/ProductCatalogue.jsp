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
	name="ProductCatalogueForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductCatalogueForm"
	toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="products.defaults.title" scope="request"/></title>
<common:include type="script"
				src="/js/products/defaults/ProductCatalogue_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">
</head>

<body id="bodyStyle">
	


<%@include file="/jsp/includes/reports/printFrame.jsp" %>
	
<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>
<div id="productCatalogueDiv" class="iCargoPopUpContent ic-masterbg" style="overflow:auto">
<ihtml:form action="products.defaults.screenloadproductcatalogue.do" styleClass="ic-main-form">

<logic:present name="ProductCatalogueForm" property="productVO">

<bean:define id="productVO"
name="ProductCatalogueForm"  property="productVO"
toScope="page" />

</logic:present>
<html:hidden property="code" />
<html:hidden property="productName" />

<div class="ic-content-main bg-white">
	<div class="ic-main-container">
		<div class="ic-row">
			<h3>
				<common:message  key="products.defaults.ProductCatalogue" scope="request"/>
			</h3>
		</div>
		<div class="ic-row">
			<div class="ic-section ic-input-container ic-border ic-label-20">
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label ">
						<label>
							<common:message  key="products.defaults.ProductName" scope="request"/>
						</label>
						<span class="ic-value">
							&nbsp;:&nbsp;<bean:write name="productVO" property="productName"/>
							<bean:define id="isProductIconPresent" name="productVO" property="isProductIconPresent" />
								<% 
									String productIconPresent=String.valueOf(isProductIconPresent);
									
								%>
							    
								 <%		
										if(productIconPresent.equals("true")){
								 %>
										
								   <img src="<%=request.getContextPath()%>/showimage.img?prdCode=<%=form.getCode()%>"/>
										
								  <%}%>
						</span>					
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label">
						<label>
							<common:message  key="products.defaults.ProductSummary" scope="request"/>
						</label>
						<span class="ic-value" style="word-break: break-word;white-space: normal;display: inline-block; width:80%">
							&nbsp;:&nbsp;<bean:write name="productVO" property="description"/>
						</span>					
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label">
						<label>
							<common:message  key="products.defaults.ProductDescription" scope="request"/>
						</label>
						<span class="ic-value" style="word-break: break-word;white-space: normal;display: inline-block;  width:80%">
							&nbsp;:&nbsp;<bean:write name="productVO" property="detailedDescription"/>
						</span>					
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label">
						<label>
							<common:message  key="products.defaults.AvailablePeriod" scope="request"/>
						</label>
						<span class="ic-value">
							&nbsp;:&nbsp;<bean:write name="productVO" property="finalDate"/>
						</span>					
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label">
						<label>
							<common:message  key="products.defaults.ServiceProvided" scope="request"/>
						</label>
						<span class="ic-value">
							&nbsp;:&nbsp;<bean:write name="productVO" property="finalService"/>
						</span>					
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label">
						<label>
							<common:message  key="products.defaults.HandlingInfo" scope="request"/>
						</label>
						<span class="ic-value">
							&nbsp;:&nbsp;<bean:write name="productVO" property="handlingInfo"/>
						</span>					
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label">
						<label>
							<common:message  key="products.defaults.TransportModes" scope="request"/>
						</label>
						<span class="ic-value">
							&nbsp;:&nbsp;<bean:write name="productVO" property="finalTransportMode"/>
						</span>					
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label">
						<label>
							<common:message  key="products.defaults.Commodities" scope="request"/>
						</label>
						<span class="ic-value">
							&nbsp;:&nbsp;<bean:write name="productVO" property="finalCommodities"/>
						</span>					
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label">
						<label>
							<common:message  key="products.defaults.GeneralInstructions" scope="request"/>
						</label>
						<span class="ic-value" style="word-break: break-word;white-space: normal;display: inline-block;  width:80%">
							&nbsp;:&nbsp;<bean:write name="productVO" property="finalGenInstructions"/>
						</span>					
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-info ic-split-100 ic-inline-label">
						<label>
							<common:message  key="products.defaults.Priority" scope="request"/>
						</label>
						<span class="ic-value">
							&nbsp;:&nbsp;<bean:write name="productVO" property="finalPriority"/>
						</span>					
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container">
				<ihtml:nbutton property="btnFeedBack" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTCATALOGUE_FEEDBACK" tabindex="1">
				   <common:message  key="products.defaults.FeedBack" scope="request"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnCheckStationAvailability" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTCATALOGUE_CHKSTNAVAILABILITY" tabindex="2">
				   <common:message  key="products.defaults.CheckStationAvailability" scope="request"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnEMail" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTCATALOGUE_EMAIL" tabindex="3">
				   <common:message  key="products.defaults.EMail" scope="request"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnPrint" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTCATALOGUE_PRINT" tabindex="4">
				   <common:message  key="products.defaults.Print" scope="request"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTCATALOGUE_CLOSE" tabindex="5">
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

