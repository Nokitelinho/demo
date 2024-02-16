
  <%@ include file="/jsp/includes/tlds.jsp" %>

  <bean:define id="form"
  	name="MaintainProductForm"
  	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
  	toScope="page" />
  <jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />

<ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data">
 <table class="fixed-header-table" id="serviceTable">
	  <thead>
			<tr class="iCargoTableHeadingLeft">
			<td width="8%" class="iCargoTableHeadingCenter" style="text-align:center">
			<input type="checkbox"
			name="checkAllService"
			title='<common:message  bundle="MaintainProduct" key="products.defaults.service"
			scope="request"/>'
			onclick="updateHeaderCheckBox(this.form,this,this.form.productServiceCheck)"  /></td>

			<td width="30%">
			<common:message  key="products.defaults.ServiceCode" scope="request"/></td>
			<td width="62%">
			<common:message  key="products.defaults.ServiceDesc" scope="request"/>
			</td>
		  </tr>
		  </thead>
		<tbody>

			<business:sessionBean id="serviceVOsFromSession" moduleName="product.defaults"
			screenID="products.defaults.maintainproduct"
			method="get"
			attribute="productServiceVOs" />


			<logic:present name="serviceVOsFromSession"  >
				<bean:define id="selectedServices" name="serviceVOsFromSession" />
				<logic:iterate id="service" name="selectedServices" indexId="rowCount3">

					<logic:present name="service" property="operationFlag">
						<bean:define id="opFlag" name="service" property="operationFlag" />
						<logic:notEqual value="D" name="service" property="operationFlag" >

							<logic:present name="service" property="serviceCode">
								<bean:define id="serviceCode" name="service" property="serviceCode" />
							 <tr>
								<td class="iCargoTableTd" style="text-align:center">
								<ihtml:checkbox property="productServiceCheck"
								value="<%=(String)serviceCode%>" onclick="toggleTableHeaderCheckbox('productServiceCheck',this.form.checkAllService)"
								componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_SERVICE"/>
								</td>
								<td class="iCargoTableTd"><bean:write name="serviceCode" />

								<html:hidden property="productServices" value="<%=(String)serviceCode%>"/></td>
								</logic:present>

								<logic:present name="service" property="serviceDescription">
									<bean:define id="serviceDesc" name="service" property="serviceDescription" />
									<td class="iCargoTableTd"><bean:write name="serviceDesc" /></td>
							  </logic:present>

							  <logic:notPresent name="service" property="serviceDescription">
									<td class="iCargoTableTd" >&nbsp;</td>
							  </logic:notPresent>

							</tr>
							</logic:notEqual>
							</logic:present>

							<logic:notPresent name="service" property="operationFlag">

							<logic:present name="service" property="serviceCode">
								<bean:define id="serviceCode" name="service" property="serviceCode" />
							  <tr>
								<td class="iCargoTableTd" style="text-align:center">
								<ihtml:checkbox property="productServiceCheck" onclick="toggleTableHeaderCheckbox('productServiceCheck',this.form.checkAllService)"
								componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_SERVICE"
								value="<%=(String)serviceCode%>" />
								</td>
								<td class="iCargoTableTd"><bean:write name="serviceCode" />
								<html:hidden property="productServices" value="<%=(String)serviceCode%>"/></td>
								</logic:present>
								<logic:present name="service" property="serviceDescription">
									<bean:define id="serviceDesc" name="service" property="serviceDescription" />

									<td class="iCargoTableTd"><bean:write name="serviceDesc" /></td>
							  </logic:present>
							  <logic:notPresent name="service" property="serviceDescription">
									<td class="iCargoTableTd">&nbsp;</td>
							  </logic:notPresent>

							</tr>

							</logic:notPresent>
						</logic:iterate>
					</logic:present>


		</tbody>
		  </table>
	</ihtml:form>
<jsp:include page="/jsp/includes/ajaxPageFooter.jsp" />

