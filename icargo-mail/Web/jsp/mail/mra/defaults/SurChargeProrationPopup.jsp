<%--******************************************************************
* Project	 				: iCargo
* Module Code & Name		: MRA.DEFAULTS
* File Name					: SurChargePrortionPopup.jsp
* Date						: 12-JUN-2015
 *********************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "java.util.*" %>
			
	
<html:html>
<head> 
	
	
		
	
			
	
<title><common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.title" bundle="mailproration" scope="request" /></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/SurChargeProrationPopup_Script.jsp"/>
</head>
<body>
	
	
<business:sessionBean id="SectorDetails"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.viewproration"
		  method="get"
		  attribute="SectorDetails" />
<business:sessionBean id="SurchargeProrationVOs"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.viewproration"
		  method="get"
		  attribute="SurchargeProrationVOs" />
		  
<business:sessionBean id="KEY_SYSPARAMETERS"
  	moduleName="mailtracking.mra.defaults"
  	screenID="mailtracking.mra.defaults.viewproration"
	method="get" attribute="systemparametres" />		  
		  
	  
		  
<bean:define id="form"
			 name="MRAViewProrationForm"
			 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm"
			 toScope="page" />

<div class="iCargoPopUpContent" >
 <ihtml:form action="/mailtracking.mra.defaults.viewproration.surchargedetails.do" styleClass="ic-main-form" >
<ihtml:hidden property="fromAction" />
 <%String roundingReq="true";%>
	<ihtml:hidden property="overrideRounding" value="N" />
	<logic:present name="KEY_SYSPARAMETERS">
		<logic:iterate id="sysPar" name="KEY_SYSPARAMETERS">
			<bean:define id="parameterCode" name="sysPar" property="key"/>
			<logic:equal name="parameterCode" value="mailtracking.mra.overrideroundingvalue">
				<bean:define id="parameterValue" name="sysPar" property="value"/>
			<logic:notEqual name="parameterValue" value="N">
				<%form.setOverrideRounding("Y");%>
			</logic:notEqual>
			</logic:equal>
		</logic:iterate>
	</logic:present>	







	<div class="ic-content-main">
	<span class="ic-page-title"><common:message  key="mailtracking.mra.defaults.proration.surchgdetailpopup.title" /></td>
		</span>
		<div class="ic-head-container">
		<div class="ic-filter-panel">
			<div class="ic-input-container">
				<div class="ic-row">
					<h4>
					<common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.surchgdetails" />
					</h4>
								
				</div>
			
							
						<div class="ic-row ">
					
						<div class="ic-input">
							<label>
							<common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.sector" />
							<label>
							<ihtml:select componentID="CMP_MRA_DEFAULTS_PRORATION_SURCHGPOPUP_SECTOR" property="sector">
								<logic:present name="SectorDetails">
									<%Set<String>sectorValuesSet = new HashSet<String>(); %>
									<logic:iterate id="sectorDetails" name="SectorDetails">
										<bean:define id="sectorid" name="sectorDetails" property="key" />
										<bean:define id="sectorValue" name="sectorDetails" property="value" type="String"/>
										<%if(sectorValuesSet.add(sectorValue)){ %>
										<ihtml:option value="<%=sectorid.toString() %>"><%=sectorValue%></ihtml:option>
										<%}%>							
									</logic:iterate>
								</logic:present>
							</ihtml:select>
						</div>	
							<div class="ic-button-container">
								<ihtml:nbutton property="btList" componentID="CMP_MRA_DEFAULTS_PRORATION_SURCHGPOPUP_LIST" accesskey="L" >
								<common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.btn.btnList" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="CMP_MRA_DEFAULTS_PRORATION_SURCHGPOPUP_CLEAR" accesskey="C" >
								<common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.btn.btnClear" />
								</ihtml:nbutton>
							</div>
						
				</div>						
			
		</div>
	</div>
	</div>
	<div class="ic-main-container">
		<div class="tableContainer" id="div1" style="height:158px">
		    <table class="fixed-header-table" id="table_Id">
				<thead >
					<tr>
						<td class="iCargoTableHeaderLabel" width="20%" ><common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.chargehead" /></td>
						<td class="iCargoTableHeaderLabel" width="17%" ><common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.amtinusd" /></td>
						<td class="iCargoTableHeaderLabel" width="24%" ><common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.amtinbase" /></td>
						<td class="iCargoTableHeaderLabel" width="17%" ><common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.amtinxdr" /></td>
						<td class="iCargoTableHeaderLabel" width="25%"><common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.amtincntrcurr" /></td>
					</tr>
				</thead>
				<tbody>
				<logic:present name="SurchargeProrationVOs">
				<logic:iterate id="surchargeProrationVO" name="SurchargeProrationVOs">
				<tr>

					
			
					
										



										
					
					
					
					
					
					
					<td class="iCargoTableDataTd" style="text-align:right">
					<bean:write name="surchargeProrationVO" property="chargeHead" />
					</td>
					
					
					<logic:present name="surchargeProrationVO" property="prorationAmtInUsd">
												<td  style="text-align:right"  class="iCargoTableDataTd">
										  <logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="surchargeProrationVO"
													moneyproperty="prorationAmtInUsd"
													showCurrencySymbol="false" property="prorationAmtInUsd" overrideRounding = "true" />
                                   </logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay   name="surchargeProrationVO"
													moneyproperty="prorationAmtInUsd"
													showCurrencySymbol="false" property="prorationAmtInUsd" />
													</logic:notEqual>
												</td>
					    						</logic:present>
					    						<logic:notPresent name="surchargeProrationVO" property="prorationAmtInUsd">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
									 		</logic:notPresent>
					
					
					
					
					
					<logic:present name="surchargeProrationVO" property="prorationAmtInBaseCurr">
												<td  style="text-align:right"  class="iCargoTableDataTd">
										  <logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="surchargeProrationVO"
													moneyproperty="prorationAmtInBaseCurr"
													showCurrencySymbol="false" property="prorationAmtInBaseCurr" overrideRounding = "true" />
                                   </logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay   name="surchargeProrationVO"
													moneyproperty="prorationAmtInBaseCurr"
													showCurrencySymbol="false" property="prorationAmtInBaseCurr" />
													</logic:notEqual>
												</td>
					    						</logic:present>
					    						<logic:notPresent name="surchargeProrationVO" property="prorationAmtInBaseCurr">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
									 		</logic:notPresent>
					
					
					<logic:present name="surchargeProrationVO" property="prorationAmtInSdr">
												<td  style="text-align:right"  class="iCargoTableDataTd">
										  <logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="surchargeProrationVO"
													moneyproperty="prorationAmtInSdr"
													showCurrencySymbol="false" property="prorationAmtInSdr" overrideRounding = "true" />
                                   </logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay   name="surchargeProrationVO"
													moneyproperty="prorationAmtInSdr"
													showCurrencySymbol="false" property="prorationAmtInSdr" />
													</logic:notEqual>
												</td>
					    						</logic:present>
					    						<logic:notPresent name="surchargeProrationVO" property="prorationAmtInSdr">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
									 		</logic:notPresent>
					
					
					
					
					<logic:present name="surchargeProrationVO" property="prorationValue">
												<td  style="text-align:right"  class="iCargoTableDataTd">
										  <logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="surchargeProrationVO"
													moneyproperty="prorationValue"
													showCurrencySymbol="false" property="prorationValue" overrideRounding = "true" />
                                   </logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay   name="surchargeProrationVO"
													moneyproperty="prorationValue"
													showCurrencySymbol="false" property="prorationValue" />
													</logic:notEqual>
												</td>
					    						</logic:present>
					    						<logic:notPresent name="surchargeProrationVO" property="prorationValue">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
									 		</logic:notPresent>
					
					
				</tr>
				</logic:iterate>
				</logic:present>
				</tbody>
				<tfoot>
					<tr>
						<td><h4>Total</h4></td>
						
						<td class="iCargoTableDataTd" >
						<bean:write name="form" property="totalSurchgInUsd" />
						</td>
						<td class="iCargoTableDataTd">
						<bean:write name="form" property="totalSurchgInBas" />
						</td>
						<td class="iCargoTableDataTd">
						<bean:write name="form" property="totalSurchgInSdr" /></td>
						<td class="iCargoTableDataTd"><bean:write name="form" property="totalSurchgInCur" /></td>
					</tr>
				</tfoot>
			</table>
		</div>
		</div>
	 <div class="ic-foot-container paddR5">

		<div class="ic-button-container">
			<ihtml:nbutton property="btClose" componentID="CMP_MRA_DEFAULTS_PRORATION_SURCHGPOPUP_CLOSE" onclick="window.close();">
				<common:message key="mailtracking.mra.defaults.proration.surchgdetailpopup.btn.btnClose" />
			</ihtml:nbutton>
		</div>
	</div>
	</div>
</ihtml:form>
</div>
			

		
	</body>
</html:html>
