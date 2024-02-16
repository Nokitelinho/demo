
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
<business:sessionBean id="billingLineMailChargeVO"
	moduleName="mailtracking.mra.defaults"
	screenID="mailtracking.mra.defaults.maintainbillingmatrix" method="get"
	attribute="billingLineMailWeightBreakDetails" />
	
	<!--CONTENT STARTS-->
	<div id="pane1"  class="content">
	<div class="ic-border" style="width:99%;height:305px;">
	<div class="ic-row ic-center ic-mandatory">
		<div class="ic-split-50  ic-input">
		<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.billingbasis"	scope="request" /></label>
		<ihtml:select componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_RatingBasisMail"	property="ratingBasisMail">
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

		<div id="mailchargeDivContainer" >
			<div id="mailchargeFlatRateDiv" class="ic-mandatory ic-split-50  ic-input">
				<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.flatRateMail"	scope="request" /></label>
				<ihtml:text property="flatRateMail"
							componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FlatRateMail"
							maxlength="100" readonly="false" tabindex="51" /> 
			</div>
			<div id="mailchargeFlatChargeDiv" class=" ic-mandatory ic-split-50  ic-input" >
					<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.flatChargeMail"	scope="request" /></label>
					<ihtml:text property="flatChargeMail"
							componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FlatChargeMail"
							maxlength="100" readonly="false" tabindex="51" /> 
			</div>
			<!--Added by a7540-->
			<div id="mailchargeUSPSDiv">
			  <div class="tableContainer" id="div2" style="width:99%;height:137px;">
			     <table class="fixed-header-table" style="width:100%;" >
				<thead>
					<tr class="iCargoTableHeadingLeft">
					  <td width="50%">
					  <common:message	key="mailtacking.mra.defaults.maintainbillingline.parameter" scope="request" />
					  </td>
					  <td width="20%">
					  <common:message	key="mailtacking.mra.defaults.maintainbillingline.rates"	scope="request" />
					  </td>
					 </tr>
				</thead>
			<tbody id="mailchargeUSPSrows">
				<tr>
				  <td><label><common:message	key="mailtacking.mra.defaults.maintainbillingline.uspsRateOne" scope="request" /></label>
				  </td>
				  <td class="iCargoTableDataTd ic-center" >
				  <logic:present name="BillingLineForm" property= "uspsRateOne"> 
					<ihtml:text property="uspsRateOne"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uspsRateOne"
						readonly="false" style="width:60px;" value="<%=String.valueOf(form.getUspsRateOne())%>"/>
				 </logic:present>
				 <logic:notPresent name="BillingLineForm" property= "uspsRateOne"> 
				 <ihtml:text property="uspsRateOne"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uspsRateOne"
						readonly="false" style="width:60px;" value=""/>
				 </logic:notPresent>
				</td>
				 </td>
				</tr>
				<tr>
				  <td><label><common:message	key="mailtacking.mra.defaults.maintainbillingline.uspsRateTwo" scope="request" /></label>
				  </td>
				  <td class="iCargoTableDataTd ic-center" >
				   <logic:present name="BillingLineForm" property= "uspsRateTwo"> 
							<ihtml:text property="uspsRateTwo"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uspsRateTwo"
							    readonly="false" style="width:60px;" value="<%=String.valueOf(form.getUspsRateTwo())%>"/> 
				</logic:present>
				 <logic:notPresent name="BillingLineForm" property= "uspsRateTwo"> 
				 <ihtml:text property="uspsRateTwo"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uspsRateTwo"
						readonly="false" style="width:60px;" value=""/>
				 </logic:notPresent>
				 </td>
				</tr>
				<tr>
				  <td><label><common:message	key="mailtacking.mra.defaults.maintainbillingline.uspsRateThr" scope="request" /></label>
				  </td>
				  <td class="iCargoTableDataTd ic-center" >
				  <logic:present name="BillingLineForm" property= "uspsRateThr"> 
							<ihtml:text property="uspsRateThr"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uspsRateThr"
							    readonly="false" style="width:60px;" value="<%=String.valueOf(form.getUspsRateThr())%>"/>
				  </logic:present>
				 <logic:notPresent name="BillingLineForm" property= "uspsRateThr">
                     <ihtml:text property="uspsRateThr"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uspsRateThr"
						readonly="false" style="width:60px;" value=""/>
				 </logic:notPresent>				 

				 </td>
				</tr>
				<tr>
				  <td><label><common:message	key="mailtacking.mra.defaults.maintainbillingline.uspsRateFour" scope="request" /></label>
				  </td>
				  <td class="iCargoTableDataTd ic-center" >
				      <logic:present name="BillingLineForm" property= "uspsRateFour"> 
							<ihtml:text property="uspsRateFour"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uspsRateFour"
							    readonly="false" style="width:60px;" value="<%=String.valueOf(form.getUspsRateFour())%>" />
					   </logic:present>
				 <logic:notPresent name="BillingLineForm" property= "uspsRateFour">
				   <ihtml:text property="uspsRateFour"
						componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uspsRateFour"
						readonly="false" style="width:60px;" value=""/>
				 </logic:notPresent>	
				</td>
				</tr>
				<tr>
				  <td><label><common:message	key="mailtacking.mra.defaults.maintainbillingline.uspsTot" scope="request" /></label>
				  </td>
				  <td class="iCargoTableDataTd ic-center" >
						<ihtml:text property="uspsTot"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_uspsTot"
							    readonly="false" style="width:60px;"  /> 
				 </td>
				</tr>
			  </tbody>
		    </table>
		</div>
			<div></div><div></div>
			<div>
			    <label><common:message	key="mailtacking.mra.defaults.maintainbillingline.conDiscount"	scope="request" /></label>
				<ihtml:text property="conDiscount"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_ConDisc"
							    readonly="false" style="width:60px;" /> 
							</div>
			</div>
			<div id="mailchargeWeightBreakDiv" >
				<div class="ic-row">
					<div class="ic-input ic-split-50">
					<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.minimumChargeMail"	scope="request" /></label>
					<ihtml:text property="minimumChargeMail"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_MinimumChargeMail"
								maxlength="100" readonly="false" tabindex="51" /> 
					</div>
					<div class="ic-input ic-split-50">
					<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.normalRateMail"	scope="request" /></label>
					<ihtml:text property="normalRateMail"
							componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_NormalRateMail"
							maxlength="100" readonly="false" tabindex="51" />
					</div>
				</div>
				<div class="ic-row">
					<div class="ic-button-container paddR5">
						<a href="#" class="iCargoLink" onclick="addMailWeightBreakRow()"> Add</a> 
						<a href="#" class="iCargoLink"onclick="deleteMailWeightBreakRow()">Delete</a>
					</div>
				</div>
				<div>
			<div class="tableContainer" id="div1" style="width:99%;height:170px;">
			
				<table class="fixed-header-table" style="width:100%;" >
				<thead>
					<tr class="iCargoTableHeadingLeft">
					  <td width="5%" class="iCargoTableHeaderLabel ic_inline_chcekbox ">
						<input type="checkbox" name="headerMailCheckBox" value="mailcheckbox" onclick="updateHeaderCheckBox(this.form,this.form.headerMailCheckBox,this.form.wbIndexMail)">
					  </td>
					  <td width="35%">
					  <common:message	key="mailtacking.mra.defaults.maintainbillingline.wbFrmWgtMail"	scope="request" />
					  </td>
					  <td width="35%">
					  <common:message	key="mailtacking.mra.defaults.maintainbillingline.wbApplicableRateMail"	scope="request" />
					  </td>
					 </tr>
				</thead>
				<tbody  id="mailchargeWeightBreakRow">
				<logic:present name="billingLineMailChargeVO">
				<logic:iterate id="billingLineChargeid" name="billingLineMailChargeVO" indexId="rowCount" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">
				
				<logic:present property="ratingBasisMail" name="BillingLineForm">
				<logic:equal property="ratingBasisMail" name="BillingLineForm"	value="WB">
					<tr>
						<td  class="iCargoTableDataTd ic_inline_chcekbox  ic-center">
							<input type="checkbox" name="wbIndexMail" value="checkbox" onclick="toggleTableHeaderCheckbox('wbIndexMail',this.form.headerMailCheckBox)"/>
							<ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
							
						</td>
						<td class="iCargoTableDataTd ic-center" >
						<logic:present  name="billingLineChargeid" property="frmWgt">
						
							<ihtml:text property="wbFrmWgtMail"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbFrmWgtOther"
								maxlength="100" readonly="false" tabindex="51"  value="<%= Double.toString(billingLineChargeid.getFrmWgt())%>" /> 
						</logic:present>
						<logic:notPresent  name="billingLineChargeid" property="frmWgt">
							<ihtml:text property="wbFrmWgtMail"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbFrmWgtOther"
								maxlength="100" readonly="false" tabindex="51" value="" /> 
						</logic:notPresent>
						</td>
						<td class="iCargoTableDataTd ic-center">
						<logic:present  name="billingLineChargeid" property="frmWgt">
							<ihtml:text property="wbApplicableRateMail"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbApplicableRateOther"
								maxlength="100" readonly="false" tabindex="51" value="<%= Double.toString(billingLineChargeid.getApplicableRateCharge())%>" /> 
						</logic:present>
						<logic:notPresent  name="billingLineChargeid" property="frmWgt">
							<ihtml:text property="wbApplicableRateMail"
								componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbApplicableRateOther"
								maxlength="100" readonly="false" tabindex="51" value="" /> 
						</logic:notPresent>
						</td>
					</tr>
				</logic:equal>
				</logic:present>
				</logic:iterate>
				</logic:present>
				<tr template="true" id="mailchargeWeightBreakTemplateRow" style="display:none" >
					<td  class="iCargoTableHeaderLabel ic_inline_chcekbox ic-center">
						<input type="checkbox" name="wbIndexMail" value="checkbox" onclick="toggleTableHeaderCheckbox('wbIndexMail',this.form.headerMailCheckBox)"/>
						<ihtml:hidden property="mailOpFlag" value="NOOP"/>
					  </td>
					<td class="iCargoTableDataTd ic-center" >
						<ihtml:text property="wbFrmWgtMail"
							componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbFrmWgtMail"
							maxlength="100" readonly="false" tabindex="51" value="" /> 
					</td>
					<td class="iCargoTableDataTd ic-center">
						<ihtml:text property="wbApplicableRateMail"
							componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbApplicableRateMail"
							maxlength="100" readonly="false" tabindex="51" value=""/> 
					</td>
				</tr>
				</tbody>
				</table>
				</div>
				</div>
			</div>
		</div>
	</div>
	</div>