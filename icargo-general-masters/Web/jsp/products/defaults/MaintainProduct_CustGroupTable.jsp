
  <%@ include file="/jsp/includes/tlds.jsp" %>

  <bean:define id="form"
  	name="MaintainProductForm"
  	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
  	toScope="page" />
  	
  <business:sessionBean id="custGrpVOsFromSession" moduleName="product.defaults"
		screenID="products.defaults.maintainproduct"
		method="get"
		attribute="productCustGrpVOs" />  	
  <jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />

<ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data">

	<table id="table7" class="fixed-header-table">
					 <!-- thead Goes here-->
			<thead>
			<tr class="iCargoTableHeadingCenter">
			  <td  width="18%" class="iCargoTableHeadingCenter"><div>
			  <input type="checkbox" name="checkAllCustGroup"
			  onclick="updateHeaderCheckBox(this.form,this,this.form.custGroupCheck)"
			  title='<bean:message  bundle="MaintainProduct" key="products.defaults.CustGroup"
									scope="request"/>'/></div></td>
			  <td width="82%" class="iCargoTableHeadingCenter">
			 <common:message  key="products.defaults.CustGroup" scope="request"/> </td>
			</tr>

			</thead>
			<tbody>


				<logic:present name="custGrpVOsFromSession" >
					<bean:define id="custGrpList" name="custGrpVOsFromSession"/>

					  <logic:present name="custGrpList">

						  <logic:iterate id="custGroupVO" name="custGrpList" indexId="rowCount3" >

							  <logic:present name="custGroupVO" property="operationFlag">
								<bean:define id="opFlag" name="custGroupVO" property="operationFlag" />

								<logic:notEqual value="D" name="custGroupVO" property="operationFlag" >

							<logic:present name="custGroupVO" property="customerGroup">
								<bean:define id="customerGroup" name="custGroupVO" property="customerGroup"/>
								<tr>
							  <td style="text-align:center" class="iCargoTableTd">
							  <html:checkbox property="custGroupCheck"
										value="<%=(String)customerGroup%>" onclick="toggleTableHeaderCheckbox('custGroupCheck',this.form.checkAllCustGroup)"/></td>
							  <td   class="iCargoTableTd"><bean:write name="custGroupVO" property="customerGroup"/>
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
							  <td style="text-align:center" class="iCargoTableTd" width="3%">
							  <html:checkbox property="custGroupCheck"
										value="<%=(String)customerGroup%>" onclick="toggleTableHeaderCheckbox('custGroupCheck',this.form.checkAllCustGroup)"/></td>
							  <td  class="iCargoTableTd"><bean:write name="custGroupVO" property="customerGroup"/>
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

