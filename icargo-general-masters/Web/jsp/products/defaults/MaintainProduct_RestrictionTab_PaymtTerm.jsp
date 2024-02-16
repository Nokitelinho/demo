 <%--
  /***********************************************************************
 * Project	 			:  iCargo
 * Module Code & Name	:  IN - Inventory Control
 * File Name			:  MaintainProduct_RestrictionTab_PaymentTerm.jsp
 * Date					:  15-July-2001
 * Author(s)			:  Amritha S

 *************************************************************************/
  --%>

 <%@ include file="/jsp/includes/tlds.jsp" %>
 <%@ page import="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm" %>
 <bean:define id="form"
 	name="MaintainProductForm"
 	type="com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm"
 	toScope="page" />

 <div class="tableContainer" id="div8" style="height:162px;">
	<table class="fixed-header-table" id="restPaymenTermTable">
		<thead>
			<tr class="iCargoTableHeadingCenter">
			  <td width="18%" class="iCargoTableHeadingCenter" style="text-align:center">
			  <input type="checkbox" name="checkAllPaymentTerm"
					onclick="updateHeaderCheckBox(this.form,this,this.form.restrictedTermsCheck)"
					title='<common:message  bundle="MaintainProduct" key="products.defaults.RestPaymntTerm"
									scope="request"/>'/></td>
			  <td width="82%" >
			  <common:message  key="products.defaults.RestPaymntTerm" scope="request"/>
			  </td>
			</tr>
		  </thead>
		  <tbody>

			<business:sessionBean id="paymentTermsListFromSession" moduleName="product.defaults"
					screenID="products.defaults.maintainproduct"
					method="get"
				attribute="restrictionPaymentTerms" />

					<logic:present name="paymentTermsListFromSession">
					<bean:define id="paymentTermsList" name="paymentTermsListFromSession"/>
						  <logic:iterate id="paymentTermsVO" name="paymentTermsList" indexId="rowCount8">
							<logic:present name="paymentTermsVO" property="paymentTerm">

								<bean:define id="term" name="paymentTermsVO" property="paymentTerm"/>
								 <tr>
								  <td style="text-align:center" class="iCargoTableTd">
									
								  <% MaintainProductForm mform = (MaintainProductForm)request.getAttribute("MaintainProductForm");
									String[] payment = mform.getRestrictedTermsCheck();
									String paymentTerm = " ";
									if(payment!=null){
									for(int i=0;i<payment.length; i++){
									if(payment[i].equals((String)term)){

											 paymentTerm =(String)term;
											 break;
									}
									}

									if(((String)term).equals(paymentTerm)){
									  %>
									  <input type="checkbox" name="restrictedTermsCheck" onclick="toggleTableHeaderCheckbox('restrictedTermsCheck',this.form.checkAllPaymentTerm)"
										value="<%=(String)term%>" checked="checked"  title='<common:message  bundle="MaintainProduct" key="products.defaults.RestPaymntTerm"
									scope="request"/>'/>
									  <%}else{%>
									  <input type="checkbox" name="restrictedTermsCheck" onclick="toggleTableHeaderCheckbox('restrictedTermsCheck',this.form.checkAllPaymentTerm)"
									  value="<%=(String)term%>" title='<common:message  bundle="MaintainProduct" key="products.defaults.RestPaymntTerm"
									scope="request"/>' />

									 <%}
									 }
									 else{
									  %><input type="checkbox" name="restrictedTermsCheck" onclick="toggleTableHeaderCheckbox('restrictedTermsCheck',this.form.checkAllPaymentTerm)"
									  value="<%=(String)term%>" title='<common:message  bundle="MaintainProduct" key="products.defaults.RestPaymntTerm"
									scope="request"/>'  />
									  <%}
									  %>
									  
								  </td><td class="iCargoTableTd"><bean:write name="term" />
								  <html:hidden value="<%=(String)term%>" property="paymentRestriction"/>
							</td>
						</tr>
					</logic:present>
				</logic:iterate>
			</logic:present>
		</tbody>
	</table>
</div>

