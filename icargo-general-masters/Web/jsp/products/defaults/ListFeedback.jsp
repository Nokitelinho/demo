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
		
<title><common:message  bundle="listfeedbackResources" key="products.defaults.title" scope="request"/>
</title>
<common:include type="script"
				src="/js/products/defaults/ListFeedback_Script.jsp" />
<meta name="decorator" content="mainpanelrestyledui">

</head>

<body  id="bodyStyle">

<bean:define id="form"
	name="ListFeedbackForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListFeedbackForm"
	toScope="page" />

<div id="mainDiv" class="iCargoContent ic-masterbg" style="overflow:auto;height:100%">
<ihtml:form action="products.defaults.screenloadlistfeedback.do">
 <html:hidden property="lastPageNumber"/>
 <html:hidden property="displayPage"/>
 <html:hidden property="productCode"/>
 <input type="hidden" name="mySearchEnabled" />

<div class="ic-content-main bg-white">
	<span class="ic-page-title ic-display-none">
		<common:message  key="products.defaults.ListFeedback" scope="request"/>
	</span>
	<div class="ic-head-container">	
		<div class="ic-filter-panel">
			<div class="ic-row">
				<div class="ic-input-container ic-label-25">
					<div class="ic-row">
						<h3>
						<common:message  key="products.defaults.SearchCriteria" scope="request"/>
						</h3>
					</div>
					<div class="ic-row">
						<div class="ic-input ic-split-25">
							<label>
								<common:message  key="products.defaults.Product" scope="request"/>
							</label>
							 <ihtml:text property="productName"
							  componentID="TXT_PRODUCTS_DEFAULTS_LISTFEEDBACK_PRDNAME"
							 maxlength="30" tabindex="1" />
							 <div class="lovImg">
							 <img src="<%=request.getContextPath()%>/images/lov.png" 
							 tabindex="2" id="lovimage"
							 onclick="displayProductLov('products.defaults.screenloadProductLov.do',document.forms[1].elements.productName.value,'productName','1','productCode')" />
							</div>
						</div>
						<div class="ic-input ic-split-25">
							<label>
								<common:message  key="products.defaults.FromDate" scope="request"/>
							</label>
								 <ibusiness:calendar property="fromDate"  type="image"
									componentID="TXT_PRODUCTS_DEFAULTS_LISTFEEDBACK_FROMDATE" id="TXT_PRODUCTS_DEFAULTS_LISTFEEDBACK_FROMDATE"
									value="<%=form.getFromDate()%>"
									tabindex="3"/>
						</div>
						<div class="ic-input ic-split-25">
							<label>
								<common:message  key="products.defaults.toDate" scope="request"/>
							</label>
							<ibusiness:calendar property="toDate"  type="image"
							  componentID="TXT_PRODUCTS_DEFAULTS_LISTFEEDBACK_TODATE"
							  id="TXT_PRODUCTS_DEFAULTS_LISTFEEDBACK_TODATE"
							  value="<%=form.getToDate()%>"  tabindex="5"/>
						</div>
						<div class="ic-input ic-split-25">
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="BTN_PRODUCTS_DEFAULTS_LISTFEEDBACK_LIST" tabindex="7" accesskey="L">
									<common:message  key="products.defaults.List" scope="request"/>
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="BTN_PRODUCTS_DEFAULTS_LISTFEEDBACK_CLEAR" tabindex="8" accesskey="C" >
									<common:message  key="products.defaults.Clear" scope="request"/>
								</ihtml:nbutton>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="ic-main-container">
		<div class="ic-row">
			<h3>
				<common:message  key="products.defaults.FeedbackDetails" scope="request"/>
			</h3>
		</div>
		<div class="ic-row">
			<logic:present name="ListFeedbackForm" property="pageProductFeedback">
				<bean:define id="pageProductFeedback"
				name="ListFeedbackForm"  property="pageProductFeedback"
				toScope="page" />
			</logic:present>
			<div class="ic-col-75">
				<logic:present name="pageProductFeedback">
					<common:paginationTag
					pageURL="products.defaults.listfeedback.do"
					name="pageProductFeedback"
					display="label"
					labelStyleClass="iCargoResultsLabel"
					lastPageNum="<%=form.getLastPageNumber() %>" />
				</logic:present>

				<logic:notPresent name="pageProductFeedback">
						&nbsp;
				</logic:notPresent>
			</div>
			<div class="ic-col-25">
			  <div class="ic-row paddR5" style="text-align:right">
				 <logic:present name="pageProductFeedback">
					<common:paginationTag linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
					pageURL="javascript:callFunction('lastPageNum','displayPage')"
					name="pageProductFeedback"
					display="pages"
					lastPageNum="<%=form.getLastPageNumber()%>" 
					exportToExcel="true"
					exportTableId="feedbackTable"
					exportAction="products.defaults.listfeedback.do"/>
				</logic:present>

				<logic:notPresent name="pageProductFeedback">
						&nbsp;
				</logic:notPresent>
			</div>
		</div>
		</div>
		<div class="ic-row">
			<div id="div1" class="tableContainer" style="height:700px;">
				<table id="feedbackTable" class="fixed-header-table">
					<thead>
					  <tr class="iCargoTableHeadingLeft">
						<td style="width:4%;">
							&nbsp;
							<span></span>
						</td>
						<td style="width:31%;">
							<common:message  key="products.defaults.Product" scope="request"/>
							<span></span>
						</td>
						<td style="width:33%;">
							<common:message  key="products.defaults.SenderName" scope="request"/>
							<span></span>
						</td>
						<td style="width:32%;">
							<common:message  key="products.defaults.SendDate" scope="request"/>
							<span></span>
						</td>
					  </tr>
					</thead>
					<tbody>
					   <logic:present name="pageProductFeedback">
						<%int index = 0;%>

						<logic:iterate id="lovVo" name="pageProductFeedback" indexId="nIndex">
						   <bean:define id="code" name="lovVo" property="productCode" />
							<bean:define id="feedbackid" name="lovVo" property="feedbackId" />
							 <% String primaryKey=(String)code+"-"+feedbackid.toString(); %>
							  <tr>
							  
								<td class="iCargoTableDataTd">
								<div class="ic-center">
								<html:checkbox property="checkbox"  styleId="<%=primaryKey%>"
								value="<%=new Integer(index).toString()%>" onclick="singleSelect(this)" tabindex="9"/>
								</div>
								</td>
								
								<td class="iCargoTableDataTd"><bean:write name="lovVo" property="productName"/></td>
								<td class="iCargoTableDataTd"><bean:write name="lovVo" property="name"/></td>
								<td class="iCargoTableDataTd"><bean:write name="lovVo" property="date"/></td>
							  </tr>							
						   <%index++;%>
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
				<ihtml:nbutton property="btnView" componentID="BTN_PRODUCTS_DEFAULTS_LISTFEEDBACK_VIEW" tabindex="10" accesskey="V">
					<common:message  key="products.defaults.View" scope="request"/>
				</ihtml:nbutton>
				<ihtml:nbutton property="btnClose" componentID="BTN_PRODUCTS_DEFAULTS_LISTFEEDBACK_CLOSE" tabindex="11" accesskey="O">
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

