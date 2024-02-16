
<%--
/***********************************************************************
* Project	 			:  iCargo
* Module Code & Name	:  IN - Inventory Control
* File Name				:  MaintainProduct_RestrictionTab_Commodity.jsp
* Date					:  15-July-2001
* Author(s)				:  Amritha S

*************************************************************************/
--%>

 <%@ include file="/jsp/includes/tlds.jsp" %>

<bean:define id="form"
name="MaintainProductForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
toScope="page" />

 <business:sessionBean id="commodityVOsFromSession" moduleName="product.defaults"
			screenID="products.defaults.maintainproduct"
			method="get"
			attribute="productCommodityVOs" />

<div class="ic-section">
	<div class="ic-row">
		<h3><common:message bundle="<%=form.getBundle()%>" key="products.defaults.CommodityRestriction" scope="request"/></h3>
	</div>
	<div class="ic-border">
		<div class="ic-row">
			<ihtml:radio property="commodityStatus" value="Restrict" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_RESTRICTCOMMODITY"/>
			<common:message  key="products.defaults.Restrict" scope="request"/>
			<ihtml:radio property="commodityStatus" value="Allow"  componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_ALLOWCOMMODITY"/>
			<common:message  key="products.defaults.Allow" scope="request"/>
		</div>
		<div class="ic-row">
			<div class="ic-button-container">
				<a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenCommodityLov.do',
					'products.defaults.screenloadCommodityLov.do',
					'products.defaults.selectCommodityNew.do','MAINTAIN_PRODUCT_SESSION','div11')">
					<common:message  key="products.defaults.add" scope="request"/>
				</a>
				| 
				<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteCommodityNew.do','commodityCheck','div11')">
					<common:message  key="products.defaults.delete" scope="request"/>
				</a>
			</div>
		</div>
		<div class="ic-row">
			<div class="tableContainer" id="div11" style="height:120px;">
				<table id="table5" class="fixed-header-table">
				<thead>
				  <tr class="iCargoTableHeadingLeft">
					<td width="18%" class="iCargoTableHeadingCenter"><div >
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
												  <td class="iCargoTableTd">
												  <html:checkbox property="commodityCheck" value="<%=(String)commodity%>" onclick="toggleTableHeaderCheckbox('commodityCheck',this.form.checkAllCommodity)"/></td>
												  <td class="iCargoTableTd"><bean:write name="commodity" />
												  <html:hidden value="<%=(String)commodity%>" property="commodity"/>

												  </td>
												</tr>
												</logic:present>
											</logic:notEqual>
									</logic:present>

									<logic:notPresent name="comdtyVO" property="operationFlag">
										<logic:present name="comdtyVO" property="commodity">
											<bean:define id="commodity" name="comdtyVO" property="commodity"/>
											<tr>
										  <td class="iCargoTableTd">
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
				</div>
			</div>
		</div>
	</div>
