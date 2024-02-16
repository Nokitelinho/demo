
  <%@ include file="/jsp/includes/tlds.jsp" %>

  <bean:define id="form"
  	name="MaintainProductForm"
  	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
  	toScope="page" />
  <jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />

<ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data">

<business:sessionBean id="commodityVOsFromSession" moduleName="product.defaults"
		screenID="products.defaults.maintainproduct"
		method="get"
		attribute="productCommodityVOs" />
<table id="table5" style="width:100%" class="fixed-header-table">
							 <!-- thead Goes here-->
		<thead>
		  <tr class="iCargoTableHeadingLeft">
			<td  class="iCargoTableHeadingCenter"><div>
			<input type="checkbox" name="checkAllCommodity"
			onclick="updateHeaderCheckBox(this.form,this,this.form.commodityCheck)"
			title='<bean:message  bundle="MaintainProduct" key="products.defaults.specifycomdty"
				scope="request"/>'/></div></td>
			<td class="iCargoTableHeadingCenter" width="82%">
			<common:message  key="products.defaults.Commodity" scope="request"/></td>
		  </tr>
		</thead>
		<tbody>
			<logic:present name="commodityVOsFromSession" >
				<bean:define id="commodityList" name="commodityVOsFromSession"/>

						  <logic:iterate id="comdtyVO" name="commodityList" indexId="rowCount">
							  <logic:present name="comdtyVO" property="operationFlag">
									<bean:define id="opFlag" name="comdtyVO" property="operationFlag" />
									<logic:notEqual value="D" name="comdtyVO" property="operationFlag" >
										<logic:present name="comdtyVO" property="commodity">
											<bean:define id="commodity" name="comdtyVO" property="commodity"/>
											<tr>
										  <td style="text-align:center" class="iCargoTableTd">
										  <html:checkbox property="commodityCheck" value="<%=(String)commodity%>" onclick="toggleTableHeaderCheckbox('commodityCheck',this.form.checkAllCommodity)"/></td>
										  <td  class="iCargoTableTd"><bean:write name="commodity" />
										  <html:hidden value="<%=(String)commodity%>" property="commodity"/>

										  </td>
										</tr>
										</logic:present>
									</logic:notEqual>
							</logic:present>

							<logic:notPresent name="comdtyVO" property="operationFlag">
								<logic:present name="comdtyVO" property="commodity">
									<bean:define id="commodity" name="comdtyVO" property="commodity"/>
									<tr >
								  <td style="text-align:center" class="iCargoTableTd">
								  <html:checkbox property="commodityCheck" value="<%=(String)commodity%>" onclick="toggleTableHeaderCheckbox('commodityCheck',this.form.checkAllCommodity)"/></td>
								  <td class="iCargoTableTd"><bean:write name="commodity" />
								  <html:hidden value="<%=(String)commodity%>" property="commodity"/>

								  </td>
								</tr>
								</logic:present>
							</logic:notPresent>
							</logic:iterate>
					</logic:present>

			</tbody>
		  </table>
	</ihtml:form>
<jsp:include page="/jsp/includes/ajaxPageFooter.jsp" />

