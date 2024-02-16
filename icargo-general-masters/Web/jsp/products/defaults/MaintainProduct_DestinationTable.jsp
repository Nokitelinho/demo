
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

<table class="fixed-header-table" id="destn">
			  <thead>
				  <tr class="iCargoTableHeadingLeft">
					<td width="18%" class="iCargoTableHeadingCenter"><div>
					<input type="checkbox" name="checkAllDestn"
					title='<common:message  bundle="MaintainProduct" key="products.defaults.specifydestn"
					scope="request"/>'
					onclick="updateHeaderCheckBox(this.form,this,this.form.destinationCheck)" /></div></td>
					<td class="iCargoTableHeadingCenter" width="82%">
					<common:message  key="products.defaults.dest" scope="request"/></td>
				  </tr>
					</thead>
				<tbody>
					<logic:present name="stationList">
						<logic:iterate name="stationList" id="stationVO" indexId="rowCount6" >
							<bean:define name="stationVO" id="originFlag" property="isOrigin" />
							<logic:equal name="originFlag" value="false" >
							<bean:define name="stationVO" id="destn" property="station" />
								<logic:present name="stationVO" property="operationFlag">
									<logic:notEqual name="stationVO" property="operationFlag" value="D">
									<tr>
										  <td  class="iCargoTableTd ic-center">
										  <html:checkbox property="destinationCheck" value="<%=(String)destn%>" 
										  		onclick="toggleTableHeaderCheckbox('destinationCheck',this.form.checkAllDestn)"/></td>
										  <td class="iCargoTableTd"><bean:write name="stationVO" property="station" />
										  <html:hidden property="destination" value="<%=(String)destn%>" />
										  </td>
									</tr>
									</logic:notEqual>
								</logic:present>
								<logic:notPresent name="stationVO" property="operationFlag">
								<tr>
									  <td  class="iCargoTableTd">
									  <html:checkbox property="destinationCheck" value="<%=(String)destn%>" 
									  		onclick="toggleTableHeaderCheckbox('destinationCheck',this.form.checkAllDestn)"/></td>
									  <td class="iCargoTableTd"><bean:write name="stationVO" property="station" />
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

