<%--
 /***********************************************************************
* Project	 		: iCargo
* Module Code & Name: Products
* File Name			: TransportMode.jsp
* Date				: 15-07-2005
* Author(s)			: Akhila.S
*************************************************************************/
 --%>
<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
			
	
<common:include type="script" src="/js/products/defaults/TransportModeLov_Script.jsp" />
<bean:define id="form"
name="TransportModeLovForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.TransportModeLovForm"
toScope="page" />
<title><common:message  bundle="<%=form.getBundle()%>" key="products.defaults.title" scope="request"/></title>
<meta name="decorator" content="popuppanelrestyledui">
</head>
<body>
	

<div class="iCargoPopUpContent">
<ihtml:form action="/products.defaults.screenloadtransportmodelov.do" styleClass="ic-main-form">

<business:sessionBean id="requestListFromSession" moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="transModeLovVOs" />

<business:sessionBean id="nextParentAction" moduleName="product.defaults"
	screenID="products.defaults.maintainproduct"
	method="get"
	attribute="nextAction" />

<bean:define id="nextAction" name="nextParentAction" toScope="page" />
<logic:present name="requestListFromSession">
	<bean:define id="requestlist" name="requestListFromSession" toScope="page"/>
</logic:present>


<bean:define id="submitParent" name="form" property="selectedData" />

<html:hidden property="nextAction" name="form" />
<div class="ic-main-container">
	<div class="ic-row">
		  <div class="tableContainer" id="div1" style="height:360px"> 
		  <table id="transportModeLovTable" class="fixed-header-table">
			<thead>
              <tr class="iCargoTableHeadingLeft">
                <td width="10%">
                  <input type="checkbox" name="selectAllDetails" style="text-align:center"
                        onclick="updateHeaderCheckBox(this.form,this,this.form.transportModeChecked)"/>
                </td>
                <td width="44%" ><common:message  key="products.defaults.TransportMode" scope="request"/></td>
                <td width="46%"> <common:message  key="products.defaults.Description" scope="request"/></td>
              </tr>
            </thead>
			<tbody>
              <logic:present name="requestlist" >
			  	<logic:iterate id="vo" name="requestlist" indexId="nIndex">
				  <bean:define id="code" name="vo" property="fieldValue"/>
                <tr>
                <td class="iCargoTableDataTd" style="text-align:center">

                <html:checkbox property="transportModeChecked"
					value="<%=(String)code%>" onclick="toggleTableHeaderCheckbox('transportModeChecked',this.form.selectAllDetails)"/>

                </td>
                <td class="iCargoTableDataTd"><bean:write name="vo" property="fieldValue" /></td>
                <td class="iCargoTableDataTd"><bean:write name="vo" property="fieldDescription" /></td>
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
				    <ihtml:button property="btok" componentID="BTN_PRODUCTS_DEFAULTS_TRANSPORTMODELOV_OK" onclick="closeScreen(this.form,'products.defaults.getSelectedTransportModeData.do','OK','<%=(String)nextAction%>')" title="Ok">
      <common:message  key="products.defaults.ok" scope="request"/>
      </ihtml:button>
     <ihtml:button property="btclose"  componentID="BTN_PRODUCTS_DEFAULTS_TRANSPORTMODELOV_CLOSE" onclick="closeScreen(this.form,'removeTransportLovFromSession.do','close','<%=(String)nextAction%>')">
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

