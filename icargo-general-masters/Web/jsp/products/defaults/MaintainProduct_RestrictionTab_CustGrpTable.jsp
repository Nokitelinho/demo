 <%--
  /***********************************************************************
 * Project	 			:  iCargo
 * Module Code & Name	:  IN - Inventory Control
 * File Name			:  MaintainProduct_RestrictionTab_CustGrpTable.jsp
 * Date					:  15-July-2001
 * Author(s)			:  Amritha S

 *************************************************************************/
  --%>

 <%@ include file="/jsp/includes/tlds.jsp" %>

<bean:define id="form"
 	name="MaintainProductForm"
 	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
 	toScope="page" />

	<business:sessionBean id="custGrpVOsFromSession" moduleName="product.defaults"
		screenID="products.defaults.maintainproduct"
		method="get"
		attribute="productCustGrpVOs" />
		
		
<div class="ic-row">
	<ihtml:radio property="custGroupStatus" value="Restrict" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_RESTRICTCUSTGRP"/>
		<common:message bundle="<%=form.getBundle() %>" key="products.defaults.Restrict" scope="request"/>
	<ihtml:radio property="custGroupStatus" value="Allow" componentID="RAD_PRODUCTS_DEFAULTS_MAINTAINPRD_ALLOWCUSTGRP" />
		<common:message bundle="<%=form.getBundle() %>" key="products.defaults.Allow" scope="request"/>
</div>
<div class="ic-row">
	<div class="ic-button-container">
		<a href="#" class="iCargoLink" onclick="addDataFromLOV1('products.defaults.beforeOpenCustGrpLov.do',
		   'products.defaults.screenloadCustGroupLov.do',
		   'products.defaults.selectCustomerGroupNew.do','','div17')">
			<common:message  key="products.defaults.add" scope="request"/>
		</a>
		| 
		<a href="#" class="iCargoLink" onclick="deleteTableRow1('products.defaults.deleteCustomerGroupNew.do','custGroupCheck','div17')">
			<common:message  key="products.defaults.delete" scope="request"/>
		</a>
	</div>
</div>
<div class="ic-row">
	<div class="tableContainer" id="div17" style="height:120px">

				<table id="table7" class="fixed-header-table">
					 <!-- thead Goes here-->
			<thead>
			<tr class="iCargoTableHeadingCenter">
			  <td width="18%" class="iCargoTableHeadingCenter"><div>
			  <input type="checkbox" name="checkAllCustGroup"
			  onclick="updateHeaderCheckBox(this.form,this,this.form.custGroupCheck)"
			  title='<bean:message  bundle="MaintainProduct" key="products.defaults.CustGroup"
									scope="request"/>'/></div></td>
			  <td width="82%" class="iCargoTableHeadingLeft">
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
							  <html:checkbox property="custGroupCheck" value="<%=(String)customerGroup%>"
									onclick="toggleTableHeaderCheckbox('custGroupCheck',this.form.checkAllCustGroup)"/></td>
							  <td  class="iCargoTableTd"><bean:write name="custGroupVO" property="customerGroup"/>
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
							<td style="text-align:center" class="iCargoTableTd">
								<html:checkbox property="custGroupCheck" value="<%=(String)customerGroup%>"
									onclick="toggleTableHeaderCheckbox('custGroupCheck',this.form.checkAllCustGroup)"/>
							</td>
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
	</div>
</div>
