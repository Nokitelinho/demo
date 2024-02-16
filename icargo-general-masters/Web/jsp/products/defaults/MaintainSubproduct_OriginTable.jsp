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
    <td width="18%" class="iCargoTableHeadingCenter">
    <input type="checkbox" name="checkbox72"
    value="checkbox"
    onclick="updateHeaderCheckBox(this.form,this,this.form.originCheck)"
    title='<common:message  bundle="MaintainSubProduct" key="products.defaults.specifyorigin"
		scope="request"/>'/></td>
    <td width="82%">
     <common:message  key="origin" scope="request"/>
    </td>
  </tr>
</thead>
<tbody>
	<logic:present name="stationVOsFromSession">
		<logic:iterate name="stationVOsFromSession" id="stationVO" indexId="rowCount5" >
			<bean:define name="stationVO" id="originFlag" property="isOrigin" />
			<logic:equal name="originFlag" value="true" >
			<bean:define name="stationVO" id="originStation" property="station" />
				<logic:present name="stationVO" property="operationFlag">
					<logic:notEqual name="stationVO" property="operationFlag" value="D">
					<tr>
						  <td class="iCargoTableTd ic-center">
						  <html:checkbox property="originCheck" value="<%=(String)originStation%>"
						  onclick="toggleTableHeaderCheckbox('originCheck',this.form.checkbox72)"/></td>
						  <td class="iCargoTableTd"><%=originStation%>
						  <html:hidden property="origin" value="<%=(String)originStation%>" />
						  </td>
					</tr>
					</logic:notEqual>
				</logic:present>
				<logic:notPresent name="stationVO" property="operationFlag">
				<tr>
					  <td class="iCargoTableTd ic-center">
					  <html:checkbox property="originCheck" value="<%=(String)originStation%>"
					  onclick="toggleTableHeaderCheckbox('originCheck',this.form.checkbox72)"/></td>
					  <td class="iCargoTableTd"><%=originStation%>
					  <html:hidden property="origin" value="<%=(String)originStation%>" />
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

