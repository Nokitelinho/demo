
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO" %>

<bean:define id="form"
	name="MaintainProductForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
	toScope="page" />
<jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />	
<ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data" styleClass="ic-main-form">
<table class="fixed-header-table" id="transportModeTable">
			  <thead>
				  <tr class="iCargoTableHeadingLeft">
					<td width="16%" class="iCargoTableHeadingCenter" style="text-align:center">
					<ihtml:checkbox  property="checkAllTransportmode"
					onclick="updateHeaderCheckBox(this.form,this,this.form.transportModeCheck)"
					componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_TRANSPORTMODE"/></td>
					<td width="84%" >

					  <common:message  key="products.defaults.TransMode" scope="request"/>  <span class="iCargoMandatoryFieldIcon">*</span>&nbsp; &nbsp;</td>
				  </tr>
				  </thead>
				  <tbody>
				  <business:sessionBean id="transportModeVOsFromSession" moduleName="product.defaults"
					screenID="products.defaults.maintainproduct"
					method="get"
					attribute="productTransportModeVOs" />
					<logic:present name="transportModeVOsFromSession" >
					  <bean:define id="transportModes" name="transportModeVOsFromSession"  />

						<logic:iterate id="transportMode" name="transportModes" indexId="rowCount" >
							<logic:present name="transportMode" property="operationFlag">
								<bean:define id="opFlag" name="transportMode" property="operationFlag" />
								<logic:notEqual value="D" name="transportMode" property="operationFlag" >
									<logic:present name="transportMode" property="transportMode">
										<bean:define id="mode" name="transportMode" property="transportMode"/>

										<tr>
											<td  class="iCargoTableTd" style="text-align:center">
											<ihtml:checkbox property="transportModeCheck"
											value="<%=(String)mode%>" onclick="toggleTableHeaderCheckbox('transportModeCheck',this.form.checkAllTransportmode)"
											componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_TRANSPORTMODE"/></td>
											<td class="iCargoTableTd"><bean:write name="transportMode" property="transportMode" />
											<html:hidden value="<%=(String)mode%>" property="transportMode"/></td>
										</tr>
									</logic:present>
								</logic:notEqual>
							</logic:present>

							<logic:notPresent name="transportMode" property="operationFlag">

										<logic:present name="transportMode" property="transportMode">
											<bean:define id="mode" name="transportMode" property="transportMode"/>
												<tr>
												<td  class="iCargoTableTd" style="text-align:center">
												<ihtml:checkbox property="transportModeCheck"
												value="<%=(String)mode%>" onclick="toggleTableHeaderCheckbox('transportModeCheck',this.form.checkAllTransportmode)"
												componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_TRANSPORTMODE"/></td>
												<td class="iCargoTableTd"><bean:write name="transportMode" property="transportMode" />
												<html:hidden value="<%=(String)mode%>" property="transportMode"/></td>
											</tr>
									</logic:present>
							</logic:notPresent>
						</logic:iterate>
					</logic:present>
				</tbody>

				</table>
</ihtml:form>
<@%include page="/jsp/includes/ajaxPageFooter.jsp"%>

