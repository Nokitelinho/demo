
<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  INV - Inventory Control
* File Name				:  MileStoneLov.jsp
* Date					:  28-July-2005
* Author(s)				:  Salish

*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.EventLovVO" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>

			
	
<bean:define id="form"
name="MileStoneLovActionForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MileStoneLovForm"
toScope="page" />
<title><common:message bundle="<%=form.getBundle()%>" key="products.defaults.title" scope="request"/></title>
<common:include type="script" src="/js/products/defaults/MileStoneLov_Script.jsp" />
<meta name="decorator" content="popuppanelrestyledui">

</head>
<body>
	

<div class="iCargoPopUpContent" style="overflow:auto;width:100%;height:100%">
<ihtml:form action="/products.defaults.screenloadMileStoneLov.do" styleClass="ic-main-form">

<business:sessionBean id="milestonefromsession"
moduleName="product.defaults"
screenID="products.defaults.maintainproduct"
method="get"
attribute="mileStoneLovVos" />

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
		<span class="ic-page-title ic-display-none">
			<common:message key="products.defaults.milestone" scope="request"/>
		</span>
	</div>
	<div class="ic-row">
		<div class="tableContainer" id="div1" style="height:384px;">
          <table id="milestoneLovTable" class="fixed-header-table">
          <thead>
          <tr class="iCargoTableHeadingLeft">
                <td width="10%" style="text-align:center"> <input type="checkbox" name="checkbox5" value="checkbox" />
                </td>
                <td width="44%" ><common:message  key="products.defaults.MileStoneCode" scope="request"/></td>
                <td width="46%"><common:message  key="products.defaults.MileStoneDescription" scope="request"/></td>
              </tr>
              </thead>
			<tbody>



			<logic:present name="milestonefromsession" >
			<bean:define id="requestlist" name="milestonefromsession" />
			<logic:iterate id="vo" name="requestlist" indexId="rowCount">
			<bean:define id="code" name="vo" property="milestoneCode" />
			<tr>
                <td class="iCargoTableTd" style="text-align:center">
                
			<% EventLovVO evo = (EventLovVO)vo;
			boolean isService = evo.isService();
			if(!isService){
			%>
               <input type="checkbox" name="mileStoneChecked"
								value="<%=(String)code%>" />
			<%}else
			{
			%>
			 <input type="checkbox" name="mileStoneChecked"
			 				value="<%=(String)code%>" checked="true" />

			<%}
			%>

               </td>
              <td class="iCargoTableTd"><bean:write name="vo" property="milestoneCode" /></td>
              <td class="iCargoTableTd"><bean:write name="vo" property="milestoneDescription" /></td>
              </tr>
              </logic:iterate>
	    </logic:present>
		</tbody>
            </table></div>
	</div>
</div>
<div class="ic-foot-container">
	<div class="ic-row">
		<div class="ic-button-container">
			<ihtml:button  property="btok"  componentID="BTN_PRODUCTS_DEFAULTS_MILESTONELOV_OK"
				onclick="closeScreen(this.form,'products.defaults.getSelectedMileStoneData.do','OK','<%=(String)nextAction%>')" >
				<common:message  key="products.defaults.ok" scope="request"/>
			</ihtml:button>
			<ihtml:button property="btclose" componentID="BTN_PRODUCTS_DEFAULTS_MILESTONELOV_CLOSE"
				onclick="closeScreen(this.form,'products.defaults.removeMileStoneLovFromSession.do','close','<%=(String)nextAction%>')">
				<common:message  key="products.defaults.Close" scope="request"/>
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

