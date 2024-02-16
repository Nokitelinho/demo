
  <%@ include file="/jsp/includes/tlds.jsp" %>

  <bean:define id="form"
  	name="MaintainProductForm"
  	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
  	toScope="page" />
  <jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />

<ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data">
 <table class="fixed-header-table" id="sccTable">
  <thead>
	  <tr class="iCargoTableHeadingLeft">
		<td width="16%" class="iCargoTableHeadingCenter" style="text-align:center">
		<input type="checkbox"
		name="checkAllScc"
		onclick="updateHeaderCheckBox(this.form,this,this.form.sccCheck)"
		title='<common:message  bundle="MaintainProduct" key="products.defaults.SCC"
		scope="request"/>'
		/></td>
		<td width="84%">
		<common:message  key="products.defaults.SCC" scope="request"/> <span class="iCargoMandatoryFieldIcon">*</span>&nbsp; &nbsp;</td>
	  </tr>
	  </thead>

	<tbody>

		  <business:sessionBean id="sccVOsFromSession" moduleName="product.defaults"
			screenID="products.defaults.maintainproduct"
			method="get"
			attribute="productSccVOs" />

				<logic:present name="sccVOsFromSession" >
				  <bean:define id="sccList" name="sccVOsFromSession" />
					<logic:iterate id="sccVO" name="sccList" indexId="rowCount2" >

						<logic:present name="sccVO" property="operationFlag">
							<bean:define id="opFlag" name="sccVO" property="operationFlag" />

							<logic:notEqual value="D" name="sccVO" property="operationFlag" >
							<logic:present name="sccVO" property="scc">
							<bean:define id="sccCode" name="sccVO" property="scc"/>
								<tr>
									<td class="iCargoTableTd" style="text-align:center">
									<ihtml:checkbox property="sccCheck" value="<%=(String)sccCode%>" onclick="toggleTableHeaderCheckbox('sccCheck',this.form.checkAllScc)" 
									componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_SCC" /></td>
									<td class="iCargoTableTd"><bean:write name="sccVO" property="scc"/>
									<html:hidden value="<%=(String)sccCode%>" property="sccCode"/></td>
								</tr>
							 </logic:present>
						</logic:notEqual>
					</logic:present>

					<logic:notPresent name="sccVO" property="operationFlag">
						<logic:present name="sccVO" property="scc">
						<bean:define id="sccCode" name="sccVO" property="scc"/>

							<tr>
								<td class="iCargoTableTd" style="text-align:center">
								<ihtml:checkbox property="sccCheck" value="<%=(String)sccCode%>" onclick="toggleTableHeaderCheckbox('sccCheck',this.form.checkAllScc)" 
								componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_SCC"/></td>
								<td class="iCargoTableTd"><bean:write name="sccVO" property="scc"/>
								<html:hidden value="<%=(String)sccCode%>" property="sccCode"/></td>
							</tr>
						 </logic:present>
					</logic:notPresent>
				</logic:iterate>
			</logic:present>
		</tbody>
	  </table>
	</ihtml:form>
<@%include page="/jsp/includes/ajaxPageFooter.jsp"%>

