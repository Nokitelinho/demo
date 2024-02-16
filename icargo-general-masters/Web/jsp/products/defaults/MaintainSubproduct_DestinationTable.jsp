 <%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import="com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO" %>
 <bean:define id="form"
 name="MaintainSubProductForm"
 type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm"
 toScope="page" />
 <jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />

<ihtml:form action="products.defaults.maintainsubproductscreenload.do">

 <business:sessionBean id="stationVOsFromSession" moduleName="product.defaults"
    screenID="products.defaults.maintainsubproducts" method="get" attribute="stationVOs" />

 <table class="fixed-header-table" width="100%">
<thead>
  <tr class="iCargoTableHeadingLeft">
	<td width="18%" class="iCargoTableHeadingCenter"
	align="center"><input type="checkbox" name="checkbox73" value="checkbox"
	onclick="updateHeaderCheckBox(this.form,this,this.form.destinationCheck)"
	title='<common:message  bundle="MaintainSubProduct" key="products.defaults.specifydestn"
	scope="request"/>' /></td>
	<td width="82%">
	 <common:message  key="destination" scope="request"/>
	 </td>
  </tr>
</thead>
<tbody>
	<logic:present name="stationVOsFromSession">
		<logic:iterate name="stationVOsFromSession" id="stationVO" indexId="rowCount6" >
			<bean:define name="stationVO" id="originFlag" property="isOrigin" />
			<logic:equal name="originFlag" value="false" >
			<bean:define name="stationVO" id="destn" property="station" />
				<logic:present name="stationVO" property="operationFlag">
					<logic:notEqual name="stationVO" property="operationFlag" value="D">
					<tr>
						  <td class="iCargoTableTd" class="ic-center">
						  <html:checkbox property="destinationCheck" value="<%=(String)destn%>"
						  onclick="toggleTableHeaderCheckbox('destinationCheck',this.form.checkbox73)"/></td>
						  <td class="iCargoTableTd"><%=destn%>
						  <html:hidden property="destination" value="<%=(String)destn%>" />
						  </td>
					</tr>
					</logic:notEqual>
				</logic:present>
				<logic:notPresent name="stationVO" property="operationFlag">
				<tr>
					  <td class="iCargoTableTd" class="ic-center">
					  <html:checkbox property="destinationCheck" value="<%=(String)destn%>"
					  onclick="toggleTableHeaderCheckbox('destinationCheck',this.form.checkbox73)"/></td>
					  <td class="iCargoTableTd"><%=destn%>
					  <html:hidden property="destination" value="<%=(String)destn%>" />
					  </td>
				</tr>
				</logic:notPresent>
		</logic:equal>
		</logic:iterate>
	</logic:present>
</tbody>
 </table>
 	</ihtml:form>
<jsp:include page="/jsp/includes/ajaxPageFooter.jsp" />

