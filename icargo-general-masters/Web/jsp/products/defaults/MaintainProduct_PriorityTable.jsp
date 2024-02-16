
  <%@ include file="/jsp/includes/tlds.jsp" %>

  <bean:define id="form"
  	name="MaintainProductForm"
  	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
  	toScope="page" />
  <jsp:include page="/jsp/includes/ajaxPageHeader.jsp" />

<ihtml:form action="/products.defaults.screenloadmaintainprd.do" enctype="multipart/form-data">
  <table class="fixed-header-table" id="priorityTable">
		 <thead>
		 <tr class="iCargoTableHeadingLeft">
			<td width="16%" class="iCargoTableHeadingCenter" style="text-align:center">
			<input type="checkbox"
			name="checkAllPriority"
			onclick="updateHeaderCheckBox(this.form,this,this.form.priorityCheck)"
			title='<common:message  bundle="MaintainProduct" key="products.defaults.priotity"
			scope="request"/>'/></td>
			<td width="84%">Priority <span class="iCargoMandatoryFieldIcon">*</span> &nbsp; &nbsp; </td>
		  </tr>
		  </thead>


			<tbody>


					<business:sessionBean id="priorityVOsFromSession" moduleName="product.defaults"
						screenID="products.defaults.maintainproduct"
						method="get"
						attribute="productPriorityVOs" />

						<logic:present name="priorityVOsFromSession" >
						  <bean:define id="priorities" name="priorityVOsFromSession" />

							<logic:iterate id="priority" name="priorities" indexId="rowCount1" >
								<logic:present name="priority" property="operationFlag">
									<bean:define id="opFlag" name="priority" property="operationFlag" />

									<logic:notEqual value="D" name="priority" property="operationFlag" >
									<logic:present name="priority" property="priority">
									<bean:define id="priorityCode" name="priority" property="priority"/>
										<tr>
											<td class="iCargoTableTd" style="text-align:center">
											<ihtml:checkbox property="priorityCheck"
											value="<%=(String)priorityCode%>" onclick="toggleTableHeaderCheckbox('priorityCheck',this.form.checkAllPriority)" 
											componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_PRIORITY"/></td>
											<td class="iCargoTableTd"><bean:write name="priority" property="priorityDisplay" />
											<html:hidden value="<%=(String)priorityCode%>" property="priority"/></td>
										</tr>
									 </logic:present>
									</logic:notEqual>
								</logic:present>

								<logic:notPresent name="priority" property="operationFlag">
									<logic:present name="priority" property="priority">
									<bean:define id="priorityCode" name="priority" property="priority"/>
										<tr>
											<td class="iCargoTableTd" style="text-align:center">
											<ihtml:checkbox property="priorityCheck"
											value="<%=(String)priorityCode%>" onclick="toggleTableHeaderCheckbox('priorityCheck',this.form.checkAllPriority)" 
											componentID="CHK_PRODUCTS_DEFAULTS_MAINTAINPRD_PRIORITY"/></td>
											<td class="iCargoTableTd"><bean:write name="priority" property="priorityDisplay" />
											<html:hidden value="<%=(String)priorityCode%>" property="priority"/></td>
										</tr>
									 </logic:present>
								</logic:notPresent>
							</logic:iterate>
						</logic:present>
		</tbody>

	</table>
	</ihtml:form>
<@%include page="/jsp/includes/ajaxPageFooter.jsp"%>

