<%--******************************************************************
* Project	 				: iCargo
* Module Code & Name		: MRA.DEFAULTS
* File Name					: SurChargeCCADetailPopup.jsp
* Date						: 24-JUN-2015
 *********************************************************************--%>

<%@ include file="/jsp/includes/tlds.jsp" %>	
			
	
<html:html>
<head> 
		
	
		
			
	
	
<title><common:message bundle="maintainCCA" key="mailtracking.mra.defaults.maintaincca.lbl.surchgpopup.title" /></title>
<meta name="decorator" content="popuppanelrestyledui">
<common:include type="script" src="/js/mail/mra/defaults/SurChargeCCADetailPopup_Script.jsp"/>
</head>
<body>
	
	

<bean:define id="form"
		 name="mraMaintainCCAForm"
		 type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm"
		 toScope="page" />
	 <business:sessionBean id="OneTimeValues"
	 	 moduleName="mailtracking.mra.defaults"
		 screenID="mailtracking.mra.defaults.maintaincca"
	 	  method="get"
	  attribute="OneTimeVOs" />
	  
	<business:sessionBean id="SurchargeCCAdetailsVOs"
		moduleName="mailtracking.mra.gpabilling"
		screenID="mailtracking.mra.defaults.maintaincca"
	   	method="get" attribute="SurchargeCCAdetailsVOs" />
<div class="iCargoPopUpContent" >
 <ihtml:form action="/mailtracking.mra.defaults.maintaincca.surchargedetails.do" styleClass="ic-main-form" >
<ihtml:hidden property="revCurCode" />
<ihtml:hidden property="surChargeAction" />
<ihtml:hidden property="revGrossWeight" />

	<div class="ic-content-main">
		<span class="ic-page-title"><common:message  key="mailtracking.mra.defaults.maintaincca.lbl.surchgpopup.title" /></td>
		</span>
<div class="ic-head-container">
		   			<div class="ic-button-container">
		     	<a href="#" class="iCargoLink" id="addLink" onclick="addSurcharge()">Add</a> | 
		     	<a href="#" class="iCargoLink" id="deleteLink" onclick="deleteSurcharge()" >Delete</a>
		     </div>
		</div>
		<div class="ic-main-container">
		<div class="tableContainer" id="surchargeDetailsDiv" style="height:280px" >
		    <table id="surchargeTable" class="fixed-header-table" style="width:100%;">
				<thead >					
					<tr>
						<td class="iCargoTableHeaderLabel" rowspan="2" width="5%"></td>
						<td class="iCargoTableHeaderLabel" rowspan="2" width="31%"><common:message key="mailtracking.mra.defaults.maintaincca.surchgdetailpopup.chgheadname" /></td>
						<td class="iCargoTableHeaderLabel" colspan="2" width="32%"><common:message key="mailtracking.mra.defaults.maintaincca.surchgdetailpopup.revised" /></td>
						<td class="iCargoTableHeaderLabel" colspan="2" width="32%"><common:message key="mailtracking.mra.defaults.maintaincca.surchgdetailpopup.original" /></td>
					</tr>
					<tr>
					    <td class="iCargoTableHeaderLabel" width ="16"><span  readonly="true">Rate</span></td>
						<td class="iCargoTableHeaderLabel" width ="16"><span  readonly="true">Amount</span></td>
						<td class="iCargoTableHeaderLabel" width ="16"><span  readonly="true">Rate</span></td></td>
						<td class="iCargoTableHeaderLabel" width ="16"><span  readonly="true">Amount</span></td>
					</tr>
	
				</thead>
				<tbody id="surchargeTableBody">
				<logic:present name="SurchargeCCAdetailsVOs">
				<logic:iterate id="surchargeCCAdetailsVO" name="SurchargeCCAdetailsVOs">
				<tr>
					<input type="hidden" name="surchargeOpFlag" value="U"/>
					<td ><input type="checkbox" name="chkSurChg" value="" disabled="true"/></td>
					<td class="iCargoTableDataTd">
						<ihtml:select property="chargeHeadName" name="surchargeCCAdetailsVO" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CHGHEADNAME" disabled="true">
							
							<logic:present name="OneTimeValues">
									<logic:iterate id="oneTimeValue" name="OneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue"
											property="key" />
										<logic:equal name="parameterCode"
												value="mailtracking.mra.surchargeChargeHead">
											<bean:define id="parameterValues" name="oneTimeValue"
												property="value" />
											<logic:iterate id="parameterValue"
												name="parameterValues"
												type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue"
													property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue"
														property="fieldValue" />
													<bean:define id="fieldDescription"
														name="parameterValue" property="fieldDescription" />
													<ihtml:option
														value="<%=String.valueOf(fieldValue).toUpperCase() %>">
														<%=String.valueOf(fieldDescription)%>
													</ihtml:option>
												</logic:present>
											</logic:iterate>
										</logic:equal>
									</logic:iterate>
								</logic:present>
						</ihtml:select>
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text property="surchargeRevRate" name="surchargeCCAdetailsVO" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVISEDRATE" />
					</td>
					<td class="iCargoTableDataTd">
					<ibusiness:moneyDisplay showCurrencySymbol="false" name="surchargeCCAdetailsVO"  moneyproperty="revSurCharge" property="revSurCharge" overrideRounding="true"/>
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text property="surchareOrgRate" name="surchargeCCAdetailsVO" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ORIGINALRATE" readonly="true" />
					</td>
					<td class="iCargoTableDataTd">
					<ibusiness:moneyDisplay showCurrencySymbol="false" name="surchargeCCAdetailsVO"  moneyproperty="orgSurCharge" property="orgSurCharge" overrideRounding="true"/>
					</td>
				</tr>
				</logic:iterate>
				</logic:present>			
				<!-- template row starts-->
				<tr template="true" id="surchargeTemplateRow" style="display:none">					
					<input type="hidden" name="surchargeOpFlag" value="NOOP"/>
					<td class="iCargoTableDataTd"><input type="checkbox" name="chkSurChg" value=""/></td>
					<td class="iCargoTableDataTd">
						<ihtml:select property="chargeHeadName" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CHGHEADNAME">
							
							<logic:present name="OneTimeValues">
									<logic:iterate id="oneTimeValue" name="OneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue"
											property="key" />
										<logic:equal name="parameterCode"
												value="mailtracking.mra.surchargeChargeHead">
											<bean:define id="parameterValues" name="oneTimeValue"
												property="value" />
											<logic:iterate id="parameterValue"
												name="parameterValues"
												type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue"
													property="fieldValue">
													<bean:define id="fieldValue" name="parameterValue"
														property="fieldValue" />
													<bean:define id="fieldDescription"
														name="parameterValue" property="fieldDescription" />
													<ihtml:option
														value="<%=String.valueOf(fieldValue).toUpperCase() %>">
														<%=String.valueOf(fieldDescription)%>
													</ihtml:option>
												</logic:present>
											</logic:iterate>
										</logic:equal>
									</logic:iterate>
								</logic:present>
						</ihtml:select>
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text property="surchargeRevRate"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVISEDRATE" />
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text property="revSurCharge" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVSURCHG" />
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text property="surchareOrgRate"  componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ORIGINALRATE" value="0" readonly="true"/>
					</td>
					<td class="iCargoTableDataTd">
					<ihtml:text property="orgSurCharge" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ORGSURCHG" readonly="true" value="0" />
					</td>

				</tr>
				<!-- template row ends -->
				</tbody>
				<tfoot>
				<tr>	
						<td colspan=2>
						<label><common:message key="mailtracking.mra.defaults.maintaincca.surchgdetailpopup.total" /></label>
						</td>
						<td>
							<div align="left">
							<ihtml:text property="revSurChargeTotal" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_REVSURCHG" readonly="true" />
							</div>
						</td>
						<td>
							<div align="left">
							<ihtml:text property="orgSurChargeTotal" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_ORGSURCHG" readonly="true" />
							</div>
						</td>
					</tr>
				</tfoot>
				
			</table>
		</div>
		</div>
	 <div class="ic-foot-container">	
			<div class="ic-button-container">	
			<ihtml:nbutton property="btOk" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_OK">
				<common:message key="mailtracking.mra.defaults.maintaincca.surchgdetailpopup.btn.btnOk" />
			</ihtml:nbutton>
			<ihtml:nbutton property="btClose" componentID="CMP_MAILTRACKING_MRA_DEFAULTS_MAINTAINCCA_CLOSE" onclick="window.close();">
				<common:message key="mailtracking.mra.defaults.maintaincca.surchgdetailpopup.btn.btnClose" />
			</ihtml:nbutton>
		</div>
		</div>
		</div>
	



</ihtml:form>
</div>
	</body>
</html:html>
