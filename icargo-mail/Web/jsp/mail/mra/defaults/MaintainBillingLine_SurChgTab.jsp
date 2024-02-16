
<%@ page language="java"%>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm"%>

<bean:define id="form" name="BillingLineForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm"
	toScope="page" />
<business:sessionBean id="KEY_ONETIMES"
	moduleName="mailtracking.mra.defaults"
	screenID="mailtracking.mra.defaults.maintainbillingmatrix" method="get"
	attribute="oneTimeVOs" />
<business:sessionBean id="chargeRatingBasis"
			moduleName="mailtracking.mra.defaults"
			screenID="mailtracking.mra.defaults.maintainbillingmatrix"
			method="get"
			attribute="ratingBasis" />

<business:sessionBean id="billingLineSurchargeChargeVO"
	moduleName="mailtracking.mra.defaults"
	screenID="mailtracking.mra.defaults.maintainbillingmatrix" method="get"
	attribute="billingLineSurWeightBreakDetails" />			
<!--CONTENT STARTS-->
<div id="pane2"  class="content">
<div class="ic-border" style="width:99%;height:305px;">
<div class="ic-row ic-center ic-mandatory">
<div class="ic-split-50 ic-input">
<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.chargeHead"	scope="request" /></label>
<ihtml:select componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ChargeHead"	property="chargeHead" >
	<ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
	<logic:present name="KEY_ONETIMES">
		<logic:iterate id="oneTimeValue" name="KEY_ONETIMES">
			<bean:define id="parameterCode" name="oneTimeValue" property="key" />
			<logic:equal name="parameterCode" value="mailtracking.mra.surchargeChargeHead">
				<bean:define id="parameterValues" name="oneTimeValue" property="value" />
				<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
					<logic:present name="parameterValue" property="fieldValue">
						<bean:define id="fieldValue" name="parameterValue"	property="fieldValue" />
						<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription" />
						<ihtml:option value="<%=String.valueOf(fieldValue).toUpperCase() %>">
							<%=String.valueOf(fieldDescription)%>
						</ihtml:option>
					</logic:present>
				</logic:iterate>
			</logic:equal>
		</logic:iterate>
	</logic:present>
</ihtml:select>
</div>
<div class="ic-split-50 ic-input">
<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.billingbasis"	scope="request" /></label>
<ihtml:select componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_Billingbasis"	property="ratingBasisOther">
	<logic:present name="chargeRatingBasis">
		<logic:iterate id="rate_basis" name="chargeRatingBasis">
		<bean:define id="fieldVal" name="rate_basis" property="fieldValue" />
		<bean:define id="fieldDescription" name="rate_basis" property="fieldDescription" />
		<ihtml:option value="<%=String.valueOf(fieldVal).toUpperCase() %>">
		<%=String.valueOf(fieldDescription)%>
		</ihtml:option>
		</logic:iterate>
	</logic:present>
</ihtml:select>
</div>
</div>

<div id="surchargeDivContainer">
				<div id="surchargeFlatRateDiv" class="ic-mandatory ">
					<div class="ic-row">
				   <div class="ic-input ic-split-50">
					<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.flatRateOther"	scope="request" /></label>
					<ihtml:text property="flatRateOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FlatRateOther"
								maxlength="100" readonly="false" tabindex="51" /> 
				</div></div></div>
				
				<div id="surchargeFlatChargeDiv" class="ic-mandatory">
				<div class="ic-row">
				   <div class="ic-input ic-split-50">
				<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.flatChargeOther"	scope="request" /></label>
					<ihtml:text property="flatChargeOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FlatChargeOther"
								maxlength="100" readonly="false" tabindex="51" /> 
				</div></div></div>
				<div id="surchargeWeightBreakDiv">
				<div class="ic-row">
				<div class="ic-input ic-split-50">
				<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.minimumChargeOther"	scope="request" /></label>
					<ihtml:text property="minimumChargeOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_MinimumChargeOther"
								maxlength="100" readonly="false" tabindex="51" /> 
				</div>
				<div class="ic-input ic-split-50">
				<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.normalRateOther"	scope="request" /></label>
					<ihtml:text property="normalRateOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_NormalRateOther"
								maxlength="100" readonly="false" tabindex="51" /> 
				</div>
				</div>
				<div class="ic-row">
				<div class="ic-button-container">
					<a href="#" class="iCargoLink" onclick="addWeightBreakRow()"> Add</a> 
					<a href="#" class="iCargoLink" onclick="deleteWeightBreakRow()">Delete</a>
				</div>
				</div>
				<div class="tableContainer" id="div1" style="width:100%;height:170px;">
				
				<table class="fixed-header-table" style="width:100%;" >
					<thead>
						<tr class="iCargoTableHeadingLeft">
						  <td width="5%" class="iCargoTableHeaderLabel ic-center">
							<input type="checkbox" name="headerOtherCheckBox" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.headerOtherCheckBox,this.form.wbIndexOther)">
						  </td>
						  <td width="35%">
						  <common:message	key="mailtacking.mra.defaults.maintainbillingline.wbFrmWgtOther"	scope="request" />
						  </td>
						  <td width="35%">
						  <common:message	key="mailtacking.mra.defaults.maintainbillingline.wbApplicableRateOther"	scope="request" />
						  </td>
						 </tr>
					</thead>
					
					<tbody  id="surchargeWeightBreakRow">
					<logic:present name="billingLineSurchargeChargeVO">
				<logic:iterate id="billingLineChargeid" name="billingLineSurchargeChargeVO" indexId="rowCount" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">
				<logic:present property="ratingBasisOther" name="BillingLineForm">
				<logic:equal property="ratingBasisOther" name="BillingLineForm"	value="WB">
					<tr>
						<td  class="iCargoTableDataTd ic-center">
							<input type="checkbox" name="wbIndexOther" value="checkbox">
							<ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
						</td>
						<td class="iCargoTableDataTd ic-center" >
						<logic:present  name="billingLineChargeid" property="frmWgt">
							<ihtml:text property="wbFrmWgtOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbFrmWgtOther"
								maxlength="100" readonly="false" tabindex="51"  value="<%= Double.toString(billingLineChargeid.getFrmWgt())%>" /> 
						</logic:present>
						<logic:notPresent  name="billingLineChargeid" property="frmWgt">
							<ihtml:text property="wbFrmWgtOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbFrmWgtOther"
								maxlength="100" readonly="false" tabindex="51" value="" /> 
						</logic:notPresent>
						</td>
						<td class="iCargoTableDataTd ic-center">
						<logic:present  name="billingLineChargeid" property="frmWgt">
							<ihtml:text property="wbApplicableRateOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbApplicableRateOther"
								maxlength="100" readonly="false" tabindex="51" value="<%= Double.toString(billingLineChargeid.getApplicableRateCharge())%>" /> 
						</logic:present>
						<logic:notPresent  name="billingLineChargeid" property="frmWgt">
							<ihtml:text property="wbApplicableRateOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbApplicableRateOther"
								maxlength="100" readonly="false" tabindex="51" value="" /> 
						</logic:notPresent>
						</td>
					</tr>
				</logic:equal>
				</logic:present>
				</logic:iterate>
				</logic:present>
					
					<tr template="true" id="surchargeWeightBreakTemplateRow" style="display:none" >
						<td  class="iCargoTableHeaderLabel ic-center">
							<input type="checkbox" name="wbIndexOther" value="checkbox" onclick="toggleTableHeaderCheckbox('wbIndexOther',this.form.headerOtherCheckBox)"/>
							<ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
						  </td>
						<td class="iCargoTableDataTd ic-center" >
							<ihtml:text property="wbFrmWgtOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbFrmWgtOther"
								maxlength="100" readonly="false" tabindex="51" value="" /> 
						</td>
						<td class="iCargoTableDataTd ic-center">
							<ihtml:text property="wbApplicableRateOther"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbApplicableRateOther"
								maxlength="100" readonly="false" tabindex="51" value="" /> 
						</td>
					</tr>
					</tbody>
					</table>
					</div>
				</div>
			</div>
</div>
</div>