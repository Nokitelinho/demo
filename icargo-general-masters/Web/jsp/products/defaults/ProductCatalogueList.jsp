
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
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.ProductVO"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ProductCatalogueListForm" %>

<html:html>
<head>
	

<title><common:message bundle="productCatalogueListResources" key="products.defaults.title" scope="request"/></title>
<common:include type="script"
				src="/js/products/defaults/ProductCatalogueList_Script.jsp" />
<meta name="decorator" content="mainpanelrestyledui">
</head>

<body id="bodyStyle" style="width:80%" class="ic-center">

<%@include file="/jsp/includes/reports/printFrame.jsp" %>

 <div id="mainDiv" class="iCargoContent " style="height:100%; overflow-y:auto">
<ihtml:form action="products.defaults.screenloadproductcataloguelist.do">
<% ProductCatalogueListForm frm = (ProductCatalogueListForm)(request.getAttribute("ProductCatalogueListForm"));%>
 


 <input type="hidden" name="mySearchEnabled" />
 <html:hidden property="lastPageNumber"/>
 <html:hidden property="displayPage"/>
 <html:hidden property="productCode" value=""/>
 <html:hidden property="hasPreview" value=""/>

<logic:present name="ProductCatalogueListForm" property="pageProductCatalogue">
	<bean:define id="pageProductCatalogue" name="ProductCatalogueListForm"  property="pageProductCatalogue"	toScope="page" />
</logic:present>
<bean:define name="ProductCatalogueListForm" property="lastPageNumber" id="lastPageNumber" />

<div class="ic-content-main bg-white">
	<span class="ic-page-title ic-display-none">
		<common:message  key="products.defaults.ProductCatalogueList" scope="request"/>
	</span>
	<div class="ic-main-container">
		<div class="ic-row">
			<h3>
				<common:message  key="products.defaults.ProductDetails" scope="request"/>
			</h3>
		</div>
		<div class="ic-row">
			<div class="ic-col-65">
				<logic:present name="pageProductCatalogue">
					<common:paginationTag
					pageURL="products.defaults.screenloadproductcataloguelist.do"
					name="pageProductCatalogue"
					display="label"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=(String)lastPageNumber %>" />
				</logic:present>

				<logic:notPresent name="pageProductCatalogue">
					&nbsp;
				</logic:notPresent>
			</div>
			<div class="ic-col-35">
				<logic:present name="pageProductCatalogue">
					<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
					pageURL="javascript:callFunction('lastPageNum','displayPage')"
					name="pageProductCatalogue"
					display="pages"
					lastPageNum="<%=(String)lastPageNumber%>" 
					exportToExcel="true"
					exportTableId="prdCatalougeTable"
					exportAction="products.defaults.screenloadproductcataloguelist.do"/>
				</logic:present>

				<logic:notPresent name="pageProductCatalogue">
					&nbsp;
				</logic:notPresent>
			</div>
		</div>
		<div class="ic-row">
			<div id="div1" class="tableContainer" style="height:660px;">
				<table  cellspacing="0" id="prdCatalougeTable" class="fixed-header-table" style="width:100%">
					<thead>
					  <tr class=iCargotableheadingLeft>
						<td width="3%" class="iCargoTableDataTd">
							&nbsp;
							<span></span>
						</td>
						<td width="44%" class="iCargoTableDataTd">
							<common:message  key="products.defaults.Product" scope="request"/>
							<span></span>
						</td>
						<td width="53%" class="iCargoTableDataTd">
							<common:message  key="products.defaults.Summary" scope="request"/>
							<span></span>
						</td>
					  </tr>
					</thead>


					<tbody>
						<logic:present name="pageProductCatalogue">
							<logic:iterate id="lovVo" name="pageProductCatalogue" indexId="nIndex">
								<bean:define id="code" name="lovVo" property="productCode" />
								<bean:define id="isProductIconPresent" name="lovVo" property="finalService" />
								<tr>
									<td class="iCargoTableDataTd">
									<div class="ic-center">
										<input type="checkbox"  name="productChecked" value="<%=(String)code%>"  onclick="singleSelectCb(this.form,'<%=(String)code%>','productChecked');"/>
									</div>
									</td>
									<td class="iCargoTableDataTd">
									
										<%				
										if(isProductIconPresent.equals("true")){
										%>
										<img src="<%=request.getContextPath()%>/showimage.img?prdCode=<%=code%>"/>										
										<%}%>										
										<bean:write name="lovVo" property="productName"/>
										
									</td>
									<td class="iCargoTableDataTd">
										<bean:write name="lovVo" property="description"/>
									</td>
								</tr>
							</logic:iterate>
						</logic:present>
				   </tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="ic-foot-container">
		<div class="ic-row">
			<div class="ic-button-container paddR5">
				<ihtml:nbutton property="btnview" accesskey="B" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTCATALOGUELIST_VIEWPRDBRCHR" >
					<common:message  key="products.defaults.viewPrdBrchr" scope="request" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnprint" accesskey="P" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTCATALOGUELIST_PRINTPRDBRCHR">
					<common:message  key="products.defaults.printPrdBrchr" scope="request" />
				</ihtml:nbutton>
				<ihtml:nbutton property="btnViewDtl" value="View" accesskey="V"	componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTCATALOGUELIST_VIEW">
					<common:message  key="products.defaults.View" scope="request"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" value="Close" accesskey="O" componentID="BTN_PRODUCTS_DEFAULTS_PRODUCTCATALOGUELIST_CLOSE">
					<common:message  key="products.defaults.Close" scope="request" />
				</ihtml:nbutton>
			</div>
		</div>
	</div>
</div>
</ihtml:form>
 </div>

	</body>
</html:html>

