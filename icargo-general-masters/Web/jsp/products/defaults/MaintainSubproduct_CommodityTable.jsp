 <%@ include file="/jsp/includes/tlds.jsp" %>


<bean:define id="form"
name="MaintainSubProductForm"
type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainSubProductForm"
toScope="page" />
<jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />
<ihtml:form action="products.defaults.maintainsubproductscreenload.do">

<business:sessionBean id="commodityVOs" moduleName="product.defaults"
        	screenID="products.defaults.maintainsubproducts" method="get" attribute="commodityVOs" />

<table class="fixed-header-table" width="100%">
	<thead>
		 <tr class="iCargoTableHeadingLeft">
			<td width="18%" class="iCargoTableHeadingCenter" align="center">
			<input type="checkbox" name="checkbox63" value="checkbox"
			onclick="updateHeaderCheckBox(this.form,this,this.form.commodityCheck)"
				title='<common:message  bundle="MaintainSubProduct" key="products.defaults.specifycomdty"
				scope="request"/>' /></td>
			<td width="82%"> <common:message  key="commodity" scope="request"/></td>
		  </tr>
	</thead>
	<tbody>
		<logic:present name="commodityVOs" >
			<bean:define id="commodityList" name="commodityVOs"/>
		     <logic:iterate id="comdtyVO" name="commodityList" indexId="nIndex">
			 
	       <logic:present name="comdtyVO" property="operationFlag">
						<bean:define id="opFlag" name="comdtyVO" property="operationFlag" />
							<logic:notEqual value="D" name="comdtyVO" property="operationFlag" >
							<logic:present name="comdtyVO" property="commodity">
								<bean:define id="commodity" name="comdtyVO" property="commodity"/>
								<tr>
							  <td class="iCargoTableDataTd ic-center">
							  <ihtml:checkbox property="commodityCheck"
							  value="<%=(String)commodity%>"
							  componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_COMMODITY" /></td>
							  <td class="iCargoTableDataTd ic-center">
							  <bean:write name="commodity" />
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
					  <td class="iCargoTableDataTd ic-center">
					  <ihtml:checkbox property="commodityCheck" value="<%=(String)commodity%>"
					  componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINSUBPRD_COMMODITY" /></td>
					  <td class="iCargoTableDataTd ic-center"><bean:write name="commodity" />
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

