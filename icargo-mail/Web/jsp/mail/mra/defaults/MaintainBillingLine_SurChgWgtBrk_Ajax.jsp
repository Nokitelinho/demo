<%--
 /***********************************************************************
* Project	 			:  iCargo
* Module Code & Name			:  Mailtracking
* File Name				:  MaintainBillingLine_SurChgWgtBrk_Ajax.jsp
* Date					:  
* Author(s)				:  A-5255
*************************************************************************/
 --%>

 

 <%@ page language="java" %>
 <%@ include file="/jsp/includes/tlds.jsp"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm"%>
<%@ page import="com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO"%>
 <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
 
 
 <ihtml:form action="/mailtracking.mra.defaults.maintainbillingline.screenload.do">
 
 <bean:define id="form"
		 name="BillingLineForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingLineForm"
		 toScope="page" />
<business:sessionBean id="billingLineSurChargeVO"
	moduleName="mailtracking.mra.defaults"
	screenID="mailtracking.mra.defaults.maintainbillingmatrix" method="get"
	attribute="billingLineSurWeightBreakDetails" />
	
<div id="ratingbasisdiv">
@<%= form.getRatingBasisOther()%>#
</div>
<div id="surchargeWeightBreakDivAjax">
<logic:present name="form" property="ratingBasisOther">
<logic:equal name="form" property="ratingBasisOther" value="WB">
<div class="ic-row">
	<div class="ic-input ic-split">
		<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.minimumChargeOther"	scope="request" /></label>
		<ihtml:text property="minimumChargeOther"
			componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_MinimumChargeOther"
			maxlength="100" readonly="false" tabindex="51" /> 
	</div>
	<div class="ic-input ic-split">
	<label><common:message	key="mailtacking.mra.defaults.maintainbillingline.normalRateOther"	scope="request" /></label>
	<ihtml:text property="normalRateOther"
			componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_NormalRateOther"
			maxlength="100" readonly="false" tabindex="51" /> 
	</div>
</div>
<div class="ic-row">
<div class="ic-button-container">
	<a class="iCargoLink" onclick="addWeightBreakRow()"> add</a> | 
	<a class="iCargoLink" onclick="deleteWeightBreakRow()">delete</a>
	</div>
</div>
<div class="tableContainer" id="div1"	style="width:100%;height:100px;">

	<table class="fixed-header-table" >
		<thead>
			<tr class="iCargoTableHeadingLeft">
				<td width="5%" class="iCargoTableHeaderLabel">
					<input type="checkbox" name="headerOtherCheckBox" value="checkbox" onclick="updateHeaderCheckBox(this.form,this.form.headerOtherCheckBox,this.form.wbIndexOther)">
				</td>
				<td width="35%">
					<common:message	key="mailtacking.mra.defaults.maintainbillingline.wbFrmWgtOther" scope="request" />
				</td>
				<td width="15%">
					<common:message	key="mailtacking.mra.defaults.maintainbillingline.wbApplicableRateOther" scope="request" />
				</td>
			</tr>
		</thead>
	<tbody  id="surchargeWeightBreakRow">	
	<logic:present name="billingLineSurChargeVO">
	<logic:iterate id="billingLineChargeid" name="billingLineSurChargeVO" indexId="rowCount" type="com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineChargeVO">
	<logic:present name="billingLineChargeid" property="operationalFlag" >
	<logic:present property="ratingBasisOther" name="BillingLineForm">
	<logic:equal property="ratingBasisOther" name="BillingLineForm"	value="WB">
		<tr>
			<td  class="iCargoTableHeaderLabel ic-center">
				<input type="checkbox" name="wbIndexOther" value="checkbox">
				<ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
				
			</td>
			<td class="iCargoTableDataTd" >
			<logic:present  name="billingLineChargeid" property="frmWgt">
			
				<ihtml:text property="wbFrmWgtOther"
					componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbFrmWgtOther"
					maxlength="100" readonly="false" tabindex="51"  value="<%= Double.toString(billingLineChargeid.getFrmWgt())%>"/> 
			</logic:present>
			<logic:notPresent  name="billingLineChargeid" property="frmWgt">
				<ihtml:text property="wbFrmWgtOther"
					componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbFrmWgtOther"
					maxlength="100" readonly="false" tabindex="51" value="" /> 
			</logic:notPresent>
			</td>
			<td class="iCargoTableDataTd">
			<logic:present  name="billingLineChargeid" property="frmWgt">
				<ihtml:text property="wbApplicableRateOther"
					componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbApplicableRateOther"
					maxlength="100" readonly="false" tabindex="51" value="<%= Double.toString(billingLineChargeid.getApplicableRateCharge())%>"/> 
			</logic:present>
			<logic:notPresent  name="billingLineChargeid" property="frmWgt">
				<ihtml:text property="wbApplicableRateOther"
					componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbApplicableRateOther"
					maxlength="100" readonly="false" tabindex="51" value=""/> 
			</logic:notPresent>
			</td>
		</tr>
	</logic:equal>
	</logic:present>
	</logic:present>	
	</logic:iterate>
	</logic:present>
	<tr template="true" id="surchargeWeightBreakTemplateRow" style="display:none" >
		<td  class="iCargoTableHeaderLabel ic-center">
			<input type="checkbox" name="wbIndexOther" value="checkbox">
			<ihtml:hidden property="hiddenOpFlag" value="NOOP"/>
		</td>
		<td class="iCargoTableDataTd" >
			<ihtml:text property="wbFrmWgtOther"
				componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbFrmWgtOther"
				maxlength="100" readonly="false" tabindex="51" value=""/> 
		</td>
		<td class="iCargoTableDataTd">
			<ihtml:text property="wbApplicableRateOther"
				componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_WbApplicableRateOther"
				maxlength="100" readonly="false" tabindex="51" value=""/> 
		</td>
	</tr>
	</tbody>
	</table>
 </div>
 </logic:equal>
 <logic:equal name="form" property="ratingBasisOther" value="FR">
 <div class="ic-row ic-input ">
 <label><common:message	key="mailtacking.mra.defaults.maintainbillingline.flatRateOther"	scope="request" /></label>
 <ihtml:text property="flatRateOther"
			componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FlatRateOther"
			maxlength="100" readonly="false" tabindex="51" /> 
 </div>
  </logic:equal>
<logic:equal name="form" property="ratingBasisOther" value="FC">
 <div class="ic-row ic-input">
 <label><common:message	key="mailtacking.mra.defaults.maintainbillingline.flatChargeOther"	scope="request" /></label>
 <ihtml:text property="flatChargeOther"
				componentID="CMP_Mailtracking_Mra_Defaults_MaintainBlgMatrix_FlatChargeOther"
				maxlength="100" readonly="false" tabindex="51" /> 
 </div>
</logic:equal>
 </logic:present>
  </div>
 
 
 
 
  </ihtml:form>
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>