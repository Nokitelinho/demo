<%--******************************************************************
* Project	 				: iCargo
* Module Code & Name		: MRA.GPABILLING
* File Name					: SurChargeDetailPopup.jsp
* Date						: 11-JUN-2015
 *********************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<bean:define id="form"
		 name="SurchargeBillingDetailsForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.SurchargeBillingDetailsForm"
		 toScope="page" />
		
			
	
<html:html>
<head> 
	
	
		
			
	
					
	
<title><common:message bundle="gpabillingentries" key="mailtracking.mra.defaults.billingentries.surchgdetailpopup.title" /></title>
<meta name="decorator" content="popuppanelrestyledui">
</head>
<body>
	
	
<bean:define id="form"
		 name="SurchargeBillingDetailsForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.SurchargeBillingDetailsForm"
		 toScope="page" />
<business:sessionBean id="SurchargeBillingDetailVOs"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.gpabilling.surcharge.surchargepopup"
	   	method="get" attribute="SurchargeBillingDetailVOs" />
 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.gpabilling"
		 screenID="mailtracking.mra.gpabilling.surcharge.surchargepopup"
	 	  method="get"
	  attribute="OneTimeVOs" />
<div class="iCargoPopUpContent" style="height:100%;">
    <ihtml:form action="/mailtracking.mra.gpabilling.surcharge.do" styleclass="ic-main-form">
        <div class="ic-content-main">
		    <span class="ic-page-title ic-display-none">
                <common:message  key="mailtracking.mra.defaults.billingentries.surchgdetailpopup.title" />
	        </span>
			    <div class="ic-main-container">
				    <div align="top"  class="tableContainer" id="surchargeDetailsDiv" style="height:197px;width:100%;" >
		                <table class="fixed-header-table" >
						    <thead>
								<tr>
									<td class="iCargoTableHeaderLabel" colspan=3>
										<div class="iCargoHeadingLabel">
											<common:message key="mailtracking.mra.defaults.billingentries.surchgdetailpopup.surchgdetails" />
										</div>
									</td>						
								</tr>
								<tr>
									<td class="iCargoTableHeaderLabel" width="34%"><common:message key="mailtracking.mra.defaults.billingentries.surchgdetailpopup.chargehead" /></td>
									<td class="iCargoTableHeaderLabel" width="33%"><common:message key="mailtracking.mra.defaults.billingentries.surchgdetailpopup.rate" /></td>
									<td class="iCargoTableHeaderLabel" width="33%"><common:message key="mailtracking.mra.defaults.billingentries.surchgdetailpopup.amount" /></td>
								</tr>
							</thead>
							<tbody>
								<logic:present name="SurchargeBillingDetailVOs">
									<logic:iterate id="surchargeBillingDetailvo" name="SurchargeBillingDetailVOs">
										<tr>
											<td class="iCargoTableDataTd">
											<bean:write property="chargeHeadDesc" name="surchargeBillingDetailvo" />
											</td>
											<td class="iCargoTableDataTd">
											<logic:notEqual name="surchargeBillingDetailvo" property="applicableRate" value = "0">
											<bean:write property="applicableRate" name="surchargeBillingDetailvo"/>
											</logic:notEqual>
											</td>
											<td class="iCargoTableDataTd">
											<logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay showCurrencySymbol="false" name="surchargeBillingDetailvo" moneyproperty="chargeAmt" property="chargeAmt" overrideRounding = "true"/>
											</logic:equal>	
											<logic:notEqual name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay showCurrencySymbol="false" name="surchargeBillingDetailvo" moneyproperty="chargeAmt" property="chargeAmt" />
											</logic:notEqual>												
											</td>					
										</tr>
										
									</logic:iterate>
								</logic:present>	
							</tbody>
							<tfoot>
								<tr>
									<td>Total</td>
									<td></td>
									<td>
									<div class="ic-left">
									<bean:write property="totalAmount" name="form"/>
									</div>
									</td>
								</tr>
				            </tfoot>
						</table>
					</div>
                </div>	
                <div class="ic-foot-container"> 
				    <div class="ic-button-container paddR5">
					    <ihtml:nbutton property="btClose" componentID="CMP_MRA_GPABILLING_SURCHGPOPUP_CLOSE" onclick="window.close();">
				            <common:message key="mailtracking.mra.defaults.billingentries.surchgdetailpopup.btn.btnClose" />
			           </ihtml:nbutton>
					</div>
                </div>				
		</div>
	
</ihtml:form>
</div>

	</body>
</html:html>
