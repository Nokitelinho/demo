<%--
 /************************************************************************
 
	* Project	 			:  iCargo
	* Module Code & Name	:  MRA - Defaults
	* File Name				:  AWMProrationPopUp.jsp
	* Date					:  04-May-2018
	* Author(s)				:  A-7371

  *************************************************************************/
 --%>
<%@ include file="/jsp/includes/tlds.jsp" %>

 				


<html:html>

  
<head> 
		
	
		
			
	

	<title><common:message key="mailtracking.mra.defaults.proration.awmprorate" bundle="mailproration" scope="request" /></title>
	<meta name="decorator" content="popuppanelrestyledui"> 
	<common:include type="script" src="/js/mail/mra/defaults/AWMProrationPopUp_Script.jsp"/>

</head>

<body>
	
<business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.viewproration"
	 	  method="get"
	  attribute="OneTimeVOs" />
<business:sessionBean id="AWMProrationVOs"
		  moduleName="mailtracking.mra.defaults"
		  screenID="mailtracking.mra.defaults.viewproration"
		  method="get"
		  attribute="AWMProrationVOs" />
		  
  <bean:define id="form"
			 name="MRAViewProrationForm"
			 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm"
			 toScope="page" />
			 
	<div class="iCargoPopUpContent" >
 <ihtml:form action="/mailtracking.mra.defaults.viewproration.awmproratedetails.do" styleClass="ic-main-form" >
 <jsp:include page="/jsp/includes/tab_support.jsp" />
<ihtml:hidden property="fromAction" />
	<div class="ic-content-main">
	<span class="ic-page-title"><common:message  key="mailtracking.mra.defaults.proration.awmprorate" /></td>
		</span>
	<div id="container1" >
	<ul class="tabs">
			<button type="button" id="tab1" onClick="return showPane(event,'pane1', this);" accesskey="m" class="tab"><common:message key="mailtracking.mra.defaults.proration.awmproratemailchg" /></button><b>|</b>
			  <button type="button" id="tab2" onClick="return showPane(event,'pane2', this);" accesskey="s" class="tab"><common:message key="mailtracking.mra.defaults.proration.awmproratesurchg" /></button>
	</ul>
	<div class="tab-panes">
		<div id="pane1" class="content" >
 <div class="ic-row ">
			  <h4><common:message key="mailtracking.mra.defaults.proration.awmprorate.secondaryprorate" /></h4>
			 
				  <div class="tableContainer table-border-solid" id="div2" style="height:270px;">
					<table width="100%" class="fixed-header-table" id="secondarytable" >
					  <thead>
					   <tr class="ic-th-all">
										<th id="td0" style="width:6%"/>
											<th id="td1" style="width:5%"/>
											<th id="td2" style="width:5%"/>
											<th id="td3" style="width:6%"/>
											<th id="td4" style="width:6%"/>
											<th id="td5" style="width:5%"/>
											<th id="td6" style="width:6%"/>
											<th id="td7" style="width:9%"/>
											<th id="td8" style="width:7%"/>
											<th id="td9" style="width:9%"/>
											<th id="td10" style="width:7%"/>
											<th id="td11" style="width:9%"/>
											<th id="td12" style="width:7%"/>
											<th id="td13" style="width:9%"/>
											
											

					  </tr>
						<tr class="iCargoTableHeadingLeft">

						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span  readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.carrcode"/></span>

						  </td>
						  <td colspan="2" class="iCargoTableHeaderLabel">
							<span  readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.sector" /> </span>

						  </td>
						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.noofmailbags" /></span>

						  </td>
						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span  readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.wt" /></span>

						  </td>
						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.proratemethod" /></span>

						  </td>

						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.proratepercentage" /></span>

						  </td>

						  <td colspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.usd" /></span>

						  </td>
						  <td colspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.basecurrency" /></span>

						  </td>
						  <td colspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.sdr" /></span>

						  </td>

						  <td rowspan="2" class="iCargoTableHeaderLabel">
							<span   readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.sectorstatus" /></span>


						  </td>
						</tr>
						<tr>
							<td class="iCargoTableHeaderLabel">	
							<span  style="width:40px;" readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.from" /></span></td>
							<td class="iCargoTableHeaderLabel">	
							<span  style="width:60px;" readonly="true" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.to" /></span></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.mailchg" /></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.surchg" /></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.mailchg" /></td>
							<td class="iCargoTableHeaderLabel"><common:message key="mailtracking.mra.defaults.proration.awmprorate.surchg" /></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.mailchg" /></td>
							<td class="iCargoTableHeaderLabel" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.surchg" /></td>
						</tr>
					  </thead>

					      <tbody>

					    					  <logic:present name="AWMProrationVOs">
					    						<logic:iterate id="ProrationVO" name="AWMProrationVOs"  >
					    					  <tr>

					    						<logic:present name="ProrationVO" property="carrierCode">
					    							<bean:define id="carrierCode" name="ProrationVO" property="carrierCode" />
					    							<td  class="iCargoTableDataTd">
					    							<%=String.valueOf(carrierCode)%> </td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="carrierCode">
					    							<td class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="sectorFrom">
					    							  <bean:define id="sectorFrom" name="ProrationVO" property="sectorFrom" />
					    							  <td  class="iCargoTableDataTd">
					    							  <%=String.valueOf(sectorFrom)%></td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="sectorFrom">
					    						  	 <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="sectorTo">
					    							  <bean:define id="sectorTo" name="ProrationVO" property="sectorTo" />
					    							  <td  class="iCargoTableDataTd">
					    							  <%=String.valueOf(sectorTo)%></td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="sectorTo">
					    							 <td width="10%" class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>

					    						<logic:present name="ProrationVO" property="numberOfPieces">
					    							  <bean:define id="numberOfPieces" name="ProrationVO" property="numberOfPieces" />
					    							  <td  style="text-align:right" width="10%" class="iCargoTableDataTd">
					    							  <%=String.valueOf(numberOfPieces)%></td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="numberOfPieces">
					    							 <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="weight">
					    							  <bean:define id="weight" name="ProrationVO" property="weight" />
					    							  <td  style="text-align:right" width="10%" class="iCargoTableDataTd">
													  <!-- Modified by A-7794 as part of ICRD-267355 -->
													  <common:write name="ProrationVO" property="weight" unitFormatting="true" />
					    							  
					    							  </td>

					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="weight">
					    							 <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>

					    						<logic:present name="ProrationVO" property="prorationType">
					    							  <bean:define id="prorationType" name="ProrationVO" property="prorationType" />
					    							  <td width="10%" class="iCargoTableDataTd">
					    							  <%=String.valueOf(prorationType)%></td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationType">
					    							 <td class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="prorationPercentage">
					    							  <bean:define id="prorationPercentage" name="ProrationVO" property="prorationPercentage" />
					    							  <td  style="text-align:right"  class="iCargoTableDataTd">
					    							   <bean:write name="ProrationVO" property="prorationPercentage" format="####"/>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationPercentage">
					    							 <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="prorationAmtInUsd">
					    							<td  style="text-align:right"  class="iCargoTableDataTd">
													  <logic:equal name="form" property="overrideRounding" value = "Y">
					    							<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInUsd"
					    								showCurrencySymbol="false" property="prorationAmtInUsd" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInUsd"
					    								showCurrencySymbol="false" property="prorationAmtInUsd"/>		
													</logic:notEqual>														
					    							</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationAmtInUsd">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>
											<logic:present name="ProrationVO" property="surProrationAmtInUsd">
												<td  style="text-align:right"  class="iCargoTableDataTd">
										  <logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInUsd"
													showCurrencySymbol="false" property="surProrationAmtInUsd" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
										<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInUsd"
													showCurrencySymbol="false" property="surProrationAmtInUsd"/>			
													</logic:notEqual>													
												</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="surProrationAmtInUsd">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
									 		</logic:notPresent>
								

					    						<logic:present name="ProrationVO" property="prorationAmtInBaseCurr">
					    							<td  style="text-align:right"  class="iCargoTableDataTd">
													  <logic:equal name="form" property="overrideRounding" value = "Y">
					    							<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInBaseCurr"
					    								showCurrencySymbol="false" property="prorationAmtInBaseCurr" overrideRounding = "true"/>													
					    							</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInBaseCurr"
					    								showCurrencySymbol="false" property="prorationAmtInBaseCurr"/>	
													</logic:notEqual>
					    							</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationAmtInBaseCurr">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>
											<logic:present name="ProrationVO" property="surProrationAmtInBaseCurr">
												<td  style="text-align:right"  class="iCargoTableDataTd">
												<logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInBaseCurr"
													showCurrencySymbol="false" property="surProrationAmtInBaseCurr" overrideRounding = "true"/>												
												</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInBaseCurr"
													showCurrencySymbol="false" property="surProrationAmtInBaseCurr"/>		
													</logic:notEqual>
												</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="surProrationAmtInBaseCurr">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
											</logic:notPresent>
								

					    						<logic:present name="ProrationVO" property="prorationAmtInSdr">
					    							<td  style="text-align:right"  class="iCargoTableDataTd">
													 <logic:equal name="form" property="overrideRounding" value = "Y">
					    							<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInSdr"
					    								showCurrencySymbol="false" property="prorationAmtInSdr" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
									<ibusiness:moneyDisplay   name="ProrationVO"
					    								moneyproperty="prorationAmtInSdr"
					    								showCurrencySymbol="false" property="prorationAmtInSdr"/>				
													</logic:notEqual>														
					    							</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="prorationAmtInSdr">
											  <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>
											<logic:present name="ProrationVO" property="surProrationAmtInSdr">
												<td  style="text-align:right"  class="iCargoTableDataTd">
													 <logic:equal name="form" property="overrideRounding" value = "Y">
												<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInSdr"
													showCurrencySymbol="false" property="surProrationAmtInSdr" overrideRounding = "true"/>
</logic:equal>
													<logic:notEqual name="form" property="overrideRounding" value = "Y">
											<ibusiness:moneyDisplay   name="ProrationVO"
													moneyproperty="surProrationAmtInSdr"
													showCurrencySymbol="false" property="surProrationAmtInSdr"/>		
													</logic:notEqual>													
												</td>
					    						</logic:present>
					    						<logic:notPresent name="ProrationVO" property="surProrationAmtInSdr">
					    							  <td  class="iCargoTableDataTd">&nbsp;</td>
					    						</logic:notPresent>


					    						<logic:present name="ProrationVO" property="sectorStatus">
												  <td  class="iCargoTableDataTd">
												  <logic:present name="OneTimeValues">
												<logic:iterate id="oneTimeValue" name="OneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue"
														property="key" />
													<logic:equal name="parameterCode"
														value="mailtracking.mra.proration.sectorstatus">
														<bean:define id="parameterValues" name="oneTimeValue"
															property="value" />
														<logic:iterate id="parameterValue"
															name="parameterValues"
															type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
															<logic:present name="parameterValue"
																property="fieldValue">
															<bean:define id="fieldValue" name="parameterValue"
																property="fieldValue" />
																<logic:equal name="ProrationVO" property="sectorStatus" value="<%=String.valueOf(fieldValue).toUpperCase() %>">
																<bean:define id="fieldDescription"
																	 name="parameterValue" property="fieldDescription" />
															     <%=String.valueOf(fieldDescription)%>
												             </logic:equal>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present></td>
											</logic:present>
											<logic:notPresent name="ProrationVO" property="sectorStatus">
												  <td  class="iCargoTableDataTd">&nbsp;</td>
											</logic:notPresent>


					    					  </tr>
					    					  	</logic:iterate>
					  					</logic:present>

					  </tbody>
					  <tfoot>
					  <tr >
						  <td style="border:none"><h4>Total</h4></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						  <td  style="text-align:right">
									<logic:equal name="form" property="overrideRounding" value = "Y">
						  <ibusiness:moneyDisplay    property="totalAWMProrationAmtInUsd"  name="form" currencyCode="USD" overrideRounding = "true"/>
									</logic:equal>
									<logic:notEqual name="form" property="overrideRounding" value = "Y">
						  <ibusiness:moneyDisplay    property="totalAWMProrationAmtInUsd"  name="form" currencyCode="USD"/>

									</logic:notEqual>			
						  </td>
						  <td style="border:none"></td>
						  <td  style="text-align:right">
									<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMProrationAmtInBaseCurr" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"  overrideRounding = "true"/>
									</logic:equal>
									<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMProrationAmtInBaseCurr" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"/>
									</logic:notEqual>	
						  </td>
						  <td style="border:none"></td>
						  <td  style="text-align:right">
									<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMProrationAmtInSdr" name="form" currencyCode="XDR" overrideRounding = "true"/>
									</logic:equal>
									<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMProrationAmtInSdr" name="form" currencyCode="XDR"/>
									</logic:notEqual>	
						  </td>
						  <td style="border:none"></td>
						  <td style="border:none"></td>
						</tr>
					  </tfoot>
					</table>
				  </div>
				</div>
		</div>
		<div id="pane2" class="content" >
		<div class="tableContainer" id="div1" style="height:270px">
		    <table class="fixed-header-table" id="table_Id">
				<thead >
					<tr>
					    <td class="iCargoTableHeaderLabel" width="20%" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.sector" /></td>
						<td class="iCargoTableHeaderLabel" width="20%" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.chargehead" /></td>
						<td class="iCargoTableHeaderLabel" width="17%" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.amtinusd" /></td>
						<td class="iCargoTableHeaderLabel" width="24%" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.amtinbase" /></td>
						<td class="iCargoTableHeaderLabel" width="17%" ><common:message key="mailtracking.mra.defaults.proration.awmprorate.amtinxdr" /></td>
						<td class="iCargoTableHeaderLabel" width="25%"><common:message key="mailtracking.mra.defaults.proration.awmprorate.amtincntrcurr" /></td>
					</tr>
				</thead>
				<tbody>
				<logic:present name="AWMProrationVOs" >
			<logic:iterate id="ProrationVO" name="AWMProrationVOs"  >
				<logic:present name="ProrationVO" property="awmProrationSurchargeDetailsVO">
				<logic:iterate id="awmProrationSurchargeDetailsVO" name="ProrationVO"  property="awmProrationSurchargeDetailsVO" >


													  
		
				<logic:present name="awmProrationSurchargeDetailsVO" property="chargeHead">
				<tr>
					<td class="iCargoTableDataTd" style="text-align:right">
					<logic:present name="awmProrationSurchargeDetailsVO" property="sector">
					    							  <bean:define id="sector" name="awmProrationSurchargeDetailsVO" property="sector" />
					    							  <%=String.valueOf(sector)%>
					    						</logic:present>
												<logic:notPresent name="awmProrationSurchargeDetailsVO" property="sector">
					    						  	 &nbsp;
					    						</logic:notPresent>
					</td>
					<td class="iCargoTableDataTd" style="text-align:right">

					<logic:present name="awmProrationSurchargeDetailsVO" property="chargeHead">
					    							  <bean:define id="chargeHead" name="awmProrationSurchargeDetailsVO" property="chargeHead" />
					    							  <%=String.valueOf(chargeHead)%>
					    						</logic:present>
												<logic:notPresent name="awmProrationSurchargeDetailsVO" property="chargeHead">
					    						  	&nbsp;
					    						</logic:notPresent></td>
												
					<td class="iCargoTableDataTd" style="text-align:right">
					<logic:equal name="form" property="overrideRounding" value = "Y">
					<ibusiness:moneyDisplay   name="awmProrationSurchargeDetailsVO"
					moneyproperty="surProrationAmtInUsd"
					showCurrencySymbol="false" property="surProrationAmtInUsd" overrideRounding = "true"/>
					</logic:equal>
					<logic:notEqual name="form" property="overrideRounding" value = "Y">
					<ibusiness:moneyDisplay   name="awmProrationSurchargeDetailsVO"
					moneyproperty="surProrationAmtInUsd"
					showCurrencySymbol="false" property="surProrationAmtInUsd"/>		
					</logic:notEqual>	
					</td>
					<td class="iCargoTableDataTd" style="text-align:right" > 
					<logic:equal name="form" property="overrideRounding" value = "Y">
					<ibusiness:moneyDisplay   name="awmProrationSurchargeDetailsVO"
					moneyproperty="surProrationAmtInBaseCurr"
					showCurrencySymbol="false" property="surProrationAmtInBaseCurr" overrideRounding = "true"/>
					</logic:equal>
					<logic:notEqual name="form" property="overrideRounding" value = "Y">
					<ibusiness:moneyDisplay   name="awmProrationSurchargeDetailsVO"
					moneyproperty="surProrationAmtInBaseCurr"
					showCurrencySymbol="false" property="surProrationAmtInBaseCurr"/>		
					</logic:notEqual>	
					</td>
					<td class="iCargoTableDataTd" style="text-align:right"> 
					<logic:equal name="form" property="overrideRounding" value = "Y">
					<ibusiness:moneyDisplay   name="awmProrationSurchargeDetailsVO"
					moneyproperty="surProrationAmtInSdr"
					showCurrencySymbol="false" property="surProrationAmtInSdr" overrideRounding = "true"/>
					</logic:equal>
					<logic:notEqual name="form" property="overrideRounding" value = "Y">
					<ibusiness:moneyDisplay   name="awmProrationSurchargeDetailsVO"
					moneyproperty="surProrationAmtInSdr"
					showCurrencySymbol="false" property="surProrationAmtInSdr"/>		
					</logic:notEqual>	
					</td>
					<td class="iCargoTableDataTd" style="text-align:right"> 
					<logic:equal name="form" property="overrideRounding" value = "Y">
					<ibusiness:moneyDisplay   name="awmProrationSurchargeDetailsVO"
					moneyproperty="surProratedAmtInCtrCur"
					showCurrencySymbol="false" property="surProratedAmtInCtrCur" overrideRounding = "true"/>
					</logic:equal>
					<logic:notEqual name="form" property="overrideRounding" value = "Y">
					<ibusiness:moneyDisplay   name="awmProrationSurchargeDetailsVO"
					moneyproperty="surProratedAmtInCtrCur"
					showCurrencySymbol="false" property="surProratedAmtInCtrCur"/>
					</logic:notEqual>	
					
					</td>
				</tr>
					
					</logic:present>
				</logic:iterate>
				
				<tr style="background-color:#eeeeee;">
						<td><h4>Sub-Total</h4></td>
						<td style="border:none"></td>
						<td class="iCargoTableDataTd" style="text-align:right"> 
						<logic:equal name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
						moneyproperty="surProrationAmtInUsd"
						showCurrencySymbol="false" property="surProrationAmtInUsd" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
						moneyproperty="surProrationAmtInUsd"
						showCurrencySymbol="false" property="surProrationAmtInUsd"/>		
						</logic:notEqual>	
						</td>
						<td class="iCargoTableDataTd" style="text-align:right"> 
						<logic:equal name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
						moneyproperty="surProrationAmtInBaseCurr"
						showCurrencySymbol="false" property="surProrationAmtInBaseCurr" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
						moneyproperty="surProrationAmtInBaseCurr"
						showCurrencySymbol="false" property="surProrationAmtInBaseCurr"/>		
						</logic:notEqual>	
						</td>
						<td class="iCargoTableDataTd" style="text-align:right"> 
						<logic:equal name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
						moneyproperty="surProrationAmtInSdr"
						showCurrencySymbol="false" property="surProrationAmtInSdr" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
						moneyproperty="surProrationAmtInSdr"
						showCurrencySymbol="false" property="surProrationAmtInSdr"/>		
						</logic:notEqual>	
						</td>
						<td class="iCargoTableDataTd" style="text-align:right">
						<logic:equal name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
						moneyproperty="surProratedAmtInCtrCur"
						showCurrencySymbol="false" property="surProratedAmtInCtrCur" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
						<ibusiness:moneyDisplay   name="ProrationVO"
						moneyproperty="surProratedAmtInCtrCur"
						showCurrencySymbol="false" property="surProratedAmtInCtrCur"/>		
						</logic:notEqual>	
						</td>
					</tr>
				
				
				</tbody>
				<tfoot>
					<tr>
						<td><h4>Total</h4></td>
						<td style="border:none"></td>
						<td class="iCargoTableDataTd" style="text-align:right"> 
						<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMSurProrationAmtInUsd" name="form" currencyCode="USD" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMSurProrationAmtInUsd" name="form" currencyCode="USD"/>
						</logic:notEqual>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right"> 
						<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMSurProrationAmtInBaseCurr" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"  overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMSurProrationAmtInBaseCurr" name="form"  currencyCode="<%=String.valueOf(form.getBaseCurrency())%>"/>
						</logic:notEqual>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right"> 
								<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMSurProrationAmtInSdr" name="form" currencyCode="XDR" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMSurProrationAmtInSdr" name="form" currencyCode="XDR"/>
						</logic:notEqual>
						</td>
						<td class="iCargoTableDataTd" style="text-align:right"> 
						
													
						<logic:equal name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMSurProrationAmtInCtrCur" name="form" currencyCode="<%=String.valueOf(form.getContractCurrency())%>" overrideRounding = "true"/>
						</logic:equal>
						<logic:notEqual name="form" property="overrideRounding" value = "Y">
							<ibusiness:moneyDisplay  property="totalAWMSurProrationAmtInCtrCur" name="form" currencyCode="<%=String.valueOf(form.getContractCurrency())%>"/>
						</logic:notEqual>
						</td>
					</tr>
				</tfoot>
			   	</logic:present>
						<logic:notPresent name="ProrationVO" property="awmProrationSurchargeDetailsVO">	
						<tfoot>
						<tr>
						<td><h4>Sub-Total</h4></td>
						<td style="border:none"></td>
						<td style="border:none"></td>

						<td style="border:none"></td>

						<td style="border:none"></td>

						<td style="border:none"></td>

					</tr>
							
					<tr>
						<td><h4>Total</h4></td>
						<td style="border:none"></td>
						<td style="border:none"></td>

						<td style="border:none"></td>

						<td style="border:none"></td>

						<td style="border:none"></td>

					</tr>
					</tfoot>
						</logic:notPresent>
                        
					

			 </logic:iterate>
			</logic:present>
			</table>
		</div>

		</div>
	</div>
</div>
	<div class="ic-foot-container ">

		<div class="ic-button-container">
			<ihtml:nbutton property="btClose" componentID="CMP_MRA_DEFAULTS_AWMPRORATIONPOPUP_CLOSE" onclick="window.close();">
				<common:message key="mailtracking.mra.defaults.proration.awmprorationpopup.btn.btnClose" />
			</ihtml:nbutton>
		</div>
	</div>
	</div>
</ihtml:form>

</div>
		
	</body>
</html:html>
