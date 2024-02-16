<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  MaintainProduct.jsp
* Date					:  15-July-2001
* Author(s)				:  Amritha S

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/contextpath.jsp" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collection" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	
<bean:define id="form"
name="MaintainProductForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="products.defaults.title" scope="request"/></title>
<common:include type="script" src="/js/products/defaults/MaintainProduct_Script.jsp" />
<meta name="decorator" content="mainpanelrestyledui">
	<common:include type="script"  src="/js/tabbedpane.js" />
</head>

<body id="bodyStyle">	


<%
	response.setDateHeader("Expires",0);
	response.setHeader("Pragma","no-cache");

	if (request.getProtocol().equals("HTTP/1.1")) {
		response.setHeader("Cache-Control","no-cache");
	}
%>
<div class="iCargoContent ic-masterbg" style="overflow:auto;height:100%">
    <ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data">
		<html:hidden property="TABPANE_ID_FLD" styleId="tabPaneFld"/>
		<html:hidden property="canSave" />
		<html:hidden property="buttonStatusFlag" />

		<business:sessionBean id="subProductVOsFromSession"
			moduleName="product.defaults"
			screenID="products.defaults.maintainproduct"
			method="get"
			attribute="subProductVOs" />
		<!--A-6843--productSubProductMapping and productlist popups are not use anywhere	
		<!--<logic:present name="subProductVOsFromSession">
			<script language="javascript">
				openWindow('productSubProductMapping');
			</script>
		</logic:present>-->

		<business:sessionBean id="productValidationVosFromSession"
			moduleName="product.defaults"
			screenID="products.defaults.maintainproduct"
			method="get"
			attribute="productValidationVos" />

		<!--<logic:present name="productValidationVosFromSession">
			<script language="javascript">
				openWindow('productlist');
			</script>
		</logic:present>-->

		<bean:define id="form"
			name="MaintainProductForm"
			type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
			toScope="page" />

		<html:hidden property="productCode" />
		<html:hidden property="checkedExternal"  />
		<html:hidden property="checkedInternal" />
		<html:hidden property="checkedTransit" />
		<html:hidden property="mode" />
		<html:hidden property="nextAction" />
		<html:hidden property="lovAction" />
		<html:hidden property="parentSession"  />
		<html:hidden property="hiddenProductCode" />
		<input type="hidden" name="currentDialogId" />
		<input type="hidden" name="currentDialogOption" />
		<ihtml:hidden property="fromListProduct" />

		<div class="ic-content-main">
			<span class="ic-page-title ic-display-none">
				<common:message  key="products.defaults.title" scope="request"/>
			</span>
			<div class="ic-head-container">
				<div class="ic-filter-panel">
					<div class="ic-row">
						<div class="ic-col-100">	
							<div class="ic-input-container">
								<div class="ic-row">
									<div class="ic-input ic-mandatory ic-split-30">
										<label>
											<common:message key="products.defaults.name" scope="request"/>
										</label>
										<% String disableFlag="false";%>
										<logic:present name="form" property="productName">
											<logic:notEqual name="form" property="productName" value="">
												<% disableFlag="true";%>		
											</logic:notEqual>
										</logic:present>
										<ihtml:text property="productName" readonly='<%="true".equals(disableFlag)%>' componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_PRDNAME"
											maxlength="30" />
									</div>
									<div class="ic-input ic-split-30">
										<label>
											<common:message  key="products.defaults.fromdate" scope="request"/>
										</label>
										<ibusiness:calendar property="startDate"
											type="image" componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_STARTDATE"
											id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_STARTDATE" value="<%=form.getStartDate()%>" maxlength="11"/>
									</div>
									<div class="ic-input ic-split-30">
										<label>
											<common:message  key="products.defaults.toDate" scope="request"/>
										</label>
										<ibusiness:calendar property="endDate"
											type="image"  componentID="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ENDDATE"
											id="TXT_PRODUCTS_DEFAULTS_MAINTAINPRD_ENDDATE" value="<%=form.getEndDate()%>" maxlength="11"/>
									</div>
									<div class="ic-input ic-split-10">
										<div class="ic-button-container">
											<ihtml:nbutton property="btnDisplay"
												componentID="BTN_PRODUCTS_DEFAULTS_MAINTAINPRD_DISPLAYDETAILS" accesskey="L">
												<common:message  key="products.defaults.dispDetail" scope="request"/>
											</ihtml:nbutton>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ic-main-container">
			<div class="ic-row">
				<div  id="container1">
					<ul class="tabs">
						<li>
							<button id="bt1" type="button" accesskey="l" class="tab" onClick="return showPane(event,'pane1', this)" >
								<common:message key="products.defaults.GeneralTab" />
							</button>
						</li>
						<li>
							<button id="bt2" type="button" accesskey="u" class="tab" onClick="return showPane(event,'pane2',this)">
								<common:message  key="products.defaults.MilestoneTab" />
							</button>
						</li>
						<li>
							<button id="bt3" type="button" accesskey="u" class="tab" onClick="return showPane(event,'pane3',this)">
								<common:message  key="products.defaults.RestrictionTab" />
							</button>
						</li>
					</ul>
						<!-- DIV Tabpanes Start-->
					<div class="tab-panes">
						<div id="pane1" class="content">
							<jsp:include page="MaintainProduct_General_Tab.jsp" />
						</div>						   
						<div id="pane2" class="content" >
							<jsp:include page="MaintainProduct_MileStone_tab.jsp" />
						</div>
						<div id="pane3" class="content">
							<jsp:include page="MaintainProduct_Restriction_Tab.jsp" />
						</div>
					</div>
						<!-- DIV Tabpanes End-->
				</div>
				</div>
			</div></div>
			<div class="ic-foot-container">
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<ihtml:nbutton property="btnListSubProduct"  componentID="BTN_PRODUCTS_DEFAULTS_MAINTAINPRD_LISTSUBPRD" accesskey="U">
							<common:message  key="products.defaults.listsubprod" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnSave"  componentID="BTN_PRODUCTS_DEFAULTS_MAINTAINPRD_SAVE" accesskey="S">
							<common:message  key="products.defaults.save" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClear" componentID="BTN_PRODUCTS_DEFAULTS_MAINTAINPRD_CLEAR" accesskey="C">
							<common:message  key="products.defaults.clear" scope="request"/>
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose"  componentID="BTN_PRODUCTS_DEFAULTS_MAINTAINPRD_CLOSE" accesskey="O">
							<common:message  key="products.defaults.close" scope="request"/>
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>
	</ihtml:form> 
</div>

<script language="javascript">
	onLoadFunctions()
</script>
				
	</body>
</html>

