<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  INV - Inventory Control
* File Name				:  CustomerGroupLov.jsp
* Date					:  27-July-2005
* Author(s)				:  Amritha S

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
			
	
<bean:define id="form"
name="CustomerGroupForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.CustomerGroupLovForm"
toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="products.defaults.title" scope="request"/></title>

<common:include type="script" src="/js/products/defaults/CustomerGroupLov_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">
</head>

<body id="bodyStyle">
	
	

<div class="iCargoPopUpContent" style="overflow:auto;width:100%;height:100%">
<ihtml:form action="/products.defaults.screenloadCustGroupLov.do" styleClass="ic-main-form">

<business:sessionBean id="requestListFromSession" moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="custGrpLovVOs" />

<business:sessionBean id="nextParentAction" moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="nextAction" />

<bean:define id="nextAction" name="nextParentAction" toScope="page" />

<%
response.setHeader("Pragma", "No-cache");
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-cache");
%>

<bean:define id="submitParent" name="form" property="selectedData" />

<html:hidden property="nextAction" name="form" />

<div class="ic-main-container">
	<div class="ic-row">
		<div class="tableContainer" id="div1" style="height:360px"> 
			<table id="customerGroupLovTable" class="fixed-header-table">
				<thead>
					<tr class="iCargoTableHeadingLeft">
						<td width="11%" style="text-align:center">
							<input type="checkbox" name="selectAllCustomer"/>
						</td>
						<td width="44%" ><common:message  key="products.defaults.CustGrpCode" scope="request"/></td>
						<td width="45%"><common:message  key="products.defaults.CustGrpDesc" scope="request"/></td>
					</tr>
				</thead>
				<tbody>
					<logic:present name="requestListFromSession">
						<bean:define id="pages" name="requestListFromSession" />
						<logic:iterate id="lovVo" name="pages" indexId="rowCount">
							<bean:define id="code" name="lovVo" property="fieldValue" />
							<tr>
								<td style="text-align:center" class="iCargoTableTd">				
									<html:checkbox property="customerGroupChecked" value="<%=(String)code%>" />				
								</td>
								<td class="iCargoTableTd" width="45%"><bean:write name="code" /></td>
								<td class="iCargoTableTd"  width="44%"><bean:write name="lovVo" property="fieldDescription" /></td>
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
		<div class="ic-button-container">
			<ihtml:button property="btnok"  componentID="BTN_PRODUCTS_DEFAULTS_CUSTOMERGRP_OK"
				onclick="closeScreen(this.form,'products.defaults.getSelectedCustomerGroup.do','OK','<%=(String)nextAction%>')" >
				<common:message  key="products.defaults.ok" scope="request"/>
			</ihtml:button>
			<ihtml:button property="btnclose" componentID="BTN_PRODUCTS_DEFAULTS_CUSTOMERGRP_CLOSE"
				onclick="closeScreen(this.form,'products.defaults.removeCustomerGroupLovFromSession.do','close','<%=(String)nextAction%>')" >
				<common:message  key="products.defaults.close" scope="request"/>
			</ihtml:button>
		</div>
	</div>
</div>

<script language="javascript">
	callFun('<%=(String)submitParent%>','<%=(String)nextAction%>');
</script>
</ihtml:form>
</div>   

		
	</body>
</html>

