 <%@ include file="/jsp/includes/tlds.jsp" %>
   <bean:define id="form"
  name="MaintainSubProductForm"
  type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm"
  toScope="page" />
  <jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />
<ihtml:form action="products.defaults.maintainsubproductscreenload.do">

<business:sessionBean id="custGrpVOsFromSession" moduleName="product.defaults"
screenID="products.defaults.maintainsubproducts" method="get" attribute="custGroupVOs" />

  <table class="fixed-header-table" width="100%">
	<thead>
      <tr class="iCargoTableHeadingLeft">
	<td width="18%" align="center"><input type="checkbox"
	name="checkbox6" value="checkbox" onclick="updateHeaderCheckBox(this.form,this,this.form.custGroupCheck)"
	title='<common:message  bundle="MaintainSubProduct" key="customerGroup"
								scope="request"/>' /></td>
	<td width="82%">
	 <common:message  key="customerGroup" scope="request"/>
	</td>
      </tr>
    </thead>
<tbody>
<logic:present name="custGrpVOsFromSession" >
   <bean:define id="custGrpList" name="custGrpVOsFromSession"/>

<logic:present name="custGrpList">

	  <logic:iterate id="custGroupVO" name="custGrpList" indexId="nIndex">
				
		  <logic:present name="custGroupVO" property="operationFlag">
			<bean:define id="opFlag" name="custGroupVO" property="operationFlag" />

			<logic:notEqual value="D" name="custGroupVO" property="operationFlag" >

		<logic:present name="custGroupVO" property="customerGroup">
			<bean:define id="customerGroup" name="custGroupVO" property="customerGroup"/>
			<tr>
		  <td class="iCargoTableDataTd ic-center">
		  <ihtml:checkbox property="custGroupCheck"
					value="<%=(String)customerGroup%>"
					componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_CUSTGRP"
					onclick="toggleTableHeaderCheckbox('custGroupCheck',this.form.checkbox6)"/>
		</td>
           <td class="iCargoTableDataTd"><%=(String)customerGroup%>
		   <html:hidden value="<%=(String)customerGroup%>" property="custGroup"/>

		  </td>
		</tr>
	</logic:present>
	</logic:notEqual>
	</logic:present>

	<logic:notPresent name="custGroupVO" property="operationFlag">

		<logic:present name="custGroupVO" property="customerGroup">
			<bean:define id="customerGroup" name="custGroupVO" property="customerGroup"/>
			<tr>
		  <td class="iCargoTableDataTd ic-center">
		  <ihtml:checkbox property="custGroupCheck"
					value="<%=(String)customerGroup%>"
					componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_CUSTGRP"
					onclick="toggleTableHeaderCheckbox('custGroupCheck',this.form.checkbox6)"/>
		</td>
		  <td class="iCargoTableDataTd"><%=(String)customerGroup%>
		   <html:hidden value="<%=(String)customerGroup%>" property="custGroup"/>
		  </td>
		</tr>
		</logic:present>
	</logic:notPresent>
	</logic:iterate>
	</logic:present>
</logic:present>
</tbody>
</table>
</ihtml:form>
<jsp:include page="/jsp/includes/ajaxPageFooter.jsp" />

