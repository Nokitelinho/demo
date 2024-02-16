<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  INV - Inventory Control
* File Name				:  PriorityLov.jsp
* Date					:  28-July-2005
* Author(s)				:  Amritha

*************************************************************************/
 --%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
			
	
<bean:define id="form"
name="priorityLovForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.PriorityLovForm"
toScope="page" />
<title><common:message bundle="<%=form.getBundle() %>" key="products.defaults.title" scope="request"/></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/products/defaults/PriorityLov_Script.jsp" />

</head>

<body>
	

<div class="iCargoPopUpContent" style="overflow:auto;width:100%;height:100%">
<ihtml:form action="/products.defaults.screenloadprioritylov.do" styleClass="ic-main-form">

<ihtml:hidden property="nextAction" name="priorityLovForm" />
<ihtml:hidden property="source" id="source" name="priorityLovForm" />
<ihtml:hidden property="multiselect"  name="priorityLovForm"/>
<ihtml:hidden property="formCount" name="priorityLovForm" />
<ihtml:hidden property="lovTxtFieldName"  name="priorityLovForm"/>
<ihtml:hidden property="lovDescriptionTxtFieldName" name="priorityLovForm"/>
<ihtml:hidden property="index" name="priorityLovForm"/>
<ihtml:hidden name="priorityLovForm" property="selectedValues"  />
<business:sessionBean id="requestListFromSession" moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="priorityLovVOs" />

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


<div class="ic-main-container">
	<div class="ic-row">
		<div class="tableContainer" id="div1" style="height:360px"> 
		  <table id="priorityLovTable" class="fixed-header-table">
            <thead>
              <tr class="iCargoTableHeadingLeft">
                <td width="11%" style="text-align:center"> <input type="checkbox" name="selectAllDetails" value="checkbox" />
                </td>
                <td width="44%"><common:message bundle="<%=form.getBundle() %>" key="products.defaults.PriorityCode" scope="request"/></td>
                <td width="45%"><common:message bundle="<%=form.getBundle() %>" key="products.defaults.Description" scope="request"/></td>
              </tr>
            </thead>
            </tbody>
            <logic:present name="requestListFromSession">
			<bean:define id="pages" name="requestListFromSession"  />
			<logic:iterate id="lovVo" name="pages" indexId="nIndex">
             <bean:define id="code" name="lovVo" property="fieldValue" />
			    <tr>
				<td class="iCargoTableDataTd" style="text-align:center">
				
				<html:checkbox property="priorityChecked" value="<%=(String)code%>" />
				
				</td>
				<td class="iCargoTableDataTd"><bean:write name="code" /></td>
				<td class="iCargoTableDataTd"><bean:write name="lovVo" property="fieldDescription" /></td>
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
			<ihtml:button property="btok" componentID="BTN_PRODUCTS_DEFAULTS_PRIORITYLOV_OK"
				onclick="closeScreen(this.form,'products.defaults.getSelectedPriorityData.do','OK','<%=(String)nextAction%>')" >
				<common:message bundle="<%=form.getBundle() %>" key="products.defaults.ok" scope="request"/>
			</ihtml:button>
			<ihtml:button property="btclose" componentID="BTN_PRODUCTS_DEFAULTS_PRIORITYLOV_CLOSE"
				onclick="closeScreen(this.form,'products.defaults.removePriorityLovFromSession.do','close','<%=(String)nextAction%>')" >
				<common:message bundle="<%=form.getBundle() %>" key="products.defaults.close" scope="request"/>
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

