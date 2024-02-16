<%@ page language="java" %>
  <%@ include file="/jsp/includes/tlds.jsp" %>

  <bean:define id="form"
  	name="MaintainProductForm"
  	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
  	toScope="page" />
	<business:sessionBean id="stationVOsFromSession" moduleName="product.defaults"
		screenID="products.defaults.maintainproduct"
		method="get"
	attribute="productStationVOs" />
	<logic:present name="stationVOsFromSession">
		<bean:define id="stationList" name="stationVOsFromSession" />
	</logic:present>
  <jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />
<ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data">
 	  <table class="fixed-header-table" id="origin">

		 <thead>
				  <tr class="iCargoTableHeadingLeft">
					<td width="16%" class="iCargoTableHeadingCenter"><div>
					<input type="checkbox" name="checkAllOrigin"
					title='<common:message  bundle="MaintainProduct" key="products.defaults.specifyorigin"
					scope="request"/>'
					onclick="updateHeaderCheckBox(this.form,this,this.form.originCheck)" /></div></td>
					<td class="iCargoTableHeadingCenter">
					<common:message  key="products.defaults.origin" scope="request"/></td>
				  </tr>
					</thead>
					<tbody>
					<logic:present name="stationList">
						<logic:iterate name="stationList" id="stationVO" indexId="rowCount5" >
							<bean:define name="stationVO" id="originFlag" property="isOrigin" />
							<logic:equal name="originFlag" value="true" >
							<bean:define name="stationVO" id="originStation" property="station" />
								<logic:present name="stationVO" property="operationFlag">
									<logic:notEqual name="stationVO" property="operationFlag" value="D">
									<tr>
										  <td style="text-align:center" class="iCargoTableTd">
										  <html:checkbox property="originCheck" value="<%=(String)originStation%>" 
										  			onclick="toggleTableHeaderCheckbox('originCheck',this.form.checkAllOrigin)"/></td>
										  <td class="iCargoTableTd"><bean:write name="stationVO" property="station" />
										  <html:hidden property="origin" value="<%=(String)originStation%>" />
										  </td>
									</tr>
									</logic:notEqual>
								</logic:present>
								<logic:notPresent name="stationVO" property="operationFlag">
								<tr >
									  <td class="iCargoTableTd">
									  <html:checkbox property="originCheck" value="<%=(String)originStation%>" 
									  		onclick="toggleTableHeaderCheckbox('originCheck',this.form.checkAllOrigin)"/></td>
									  <td  width="82%" class="iCargoTableTd"><bean:write name="stationVO" property="station" />
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

