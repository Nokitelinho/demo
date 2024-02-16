<%-- *************************************************

* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: LoanBorrowULD_ULDDetails.jsp
* Date				: 31-jul-2008
* Author(s)			: A3353

****************************************************** --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


<bean:define id="form"
      name="maintainULDTransactionForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.MaintainULDTransactionForm"
      toScope="page" />

<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.loanborrowuld"
		method="get"
		attribute="oneTimeValues" />

<business:sessionBean
		id="uldNature"
		moduleName="uld.defaults"
		screenID="uld.defaults.loanborrowuld"
		method="get"
		attribute="uldNature" />

<business:sessionBean
		id="uldCondition"
		moduleName="uld.defaults"
		screenID="uld.defaults.loanborrowuld"
		method="get"
		attribute="ULDServiceabilityVOs" />



<business:sessionBean id="transactionVOSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="transactionVO" />
	<logic:present name="transactionVOSession">
		<bean:define id="transactionVOSession" name="transactionVOSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="txnTypesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="txnTypes" />
	<logic:present name="txnTypesSession">
		<bean:define id="txnTypesSession" name="txnTypesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="txnNaturesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="txnNatures" />
	<logic:present name="txnNaturesSession">
		<bean:define id="txnNaturesSession" name="txnNaturesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="partyTypesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="partyTypes" />
	<logic:present name="partyTypesSession">
		<bean:define id="partyTypesSession" name="partyTypesSession" toScope="page"/>
	</logic:present>
<business:sessionBean id="accCodesSession" moduleName="uld.defaults" screenID="uld.defaults.loanborrowuld" method="get" attribute="accessoryCodes" />
	<logic:present name="accCodesSession">
		<bean:define id="accCodesSession" name="accCodesSession" toScope="page"/>
	</logic:present>



	<h4><common:message key="uld.defaults.loanBorrowULD.lbl.ulddetails"/></h4>
		<div class="ic-row ">
		<div class="ic-border">
		<div class="ic-row ic-right">
			<a href="javascript:void(0)" id="addLinkUld" class="iCargoLink" onclick="addUldDetails1()" ><common:message key="uld.defaults.loanBorrowULD.lnk.add"/></a>
                |
			<a href="javascript:void(0)" id="deleteLinkUld" class="iCargoLink" onclick="deleteUldDetails1()" onblur="showAddAccLinkFocus();"><common:message key="uld.defaults.loanBorrowULD.lnk.delete"/></a> &nbsp;
		</div>
		<div class="ic-row">
		<div id="parentulddiv" class="tableContainer" style="height:200px">
			<table  class="fixed-header-table">
                <thead>
 				<tr class="iCargoTableHeadingLeft">
					<td class="iCargoTableHeader" width="3%"><input type="checkbox" name="masterUld" onclick="updateHeaderCheckBox(this.form,this.form.masterUld,this.form.uldDetails)" /></td>
				    <td class="iCargoTableHeader" width="17%"><common:message key="uld.defaults.loanBorrowULD.lbl.uldnum"/><span class="iCargoMandatoryFieldIcon">*</span></td>
				    <td class="iCargoTableHeader" width="12%">Ctrl. Rcpt. No.</td>
				    <td class="iCargoTableHeader" width="10%">ULD nature</td>
				    <td class="iCargoTableHeader" width="10%"><common:message key="uld.defaults.loanBorrowULD.lbl.deststation"/></td>
				    <td class="iCargoTableHeader" width="10%"><common:message key="uld.defaults.loanBorrowULD.lbl.uldCondition"/></td>
					<td class="iCargoTableHeader" width="7%"><common:message key="uld.defaults.loanBorrowULD.lbl.damagedflg"/></td>
					<td class="iCargoTableHeader" width="10%"><common:message key="uld.defaults.loanBorrowULD.lbl.odlncode"/></td>
					<td class="iCargoTableHeader" width="21%"><common:message key="uld.defaults.loanBorrowULD.lbl.damagedescription"/></td>
				</tr>
                </thead>
                <tbody>
					<logic:present name="transactionVOSession" property="uldTransactionDetailsVOs">
					<bean:define id="uldTransactionDetailsVOs" name="transactionVOSession" property="uldTransactionDetailsVOs" toScope="page"/>
						<logic:iterate id="uldTransactionDetailsVO" name="uldTransactionDetailsVOs" indexId="index">
							<tr>
								 <td class="iCargoTableDataTd">
									<input type="checkbox" name="uldDetails" value="<%=index%>" onclick="toggleTableHeaderCheckbox('uldDetails',this.form.masterUld)"/>
								</td>
								 <td class="iCargoTableDataTd">
									<logic:notPresent name="uldTransactionDetailsVO" property="uldNumber">
										<ibusiness:uld id="uldno" uldProperty="uldNum" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_LOANDTL_ULDNUM" style="width:90px;text-transform: uppercase" onblur="getULDDetails(this);"/>
									</logic:notPresent>
									<logic:present name="uldTransactionDetailsVO" property="uldNumber">
										<bean:define id="uld" name="uldTransactionDetailsVO" property="uldNumber" />
										<ibusiness:uld id="uldno" uldProperty="uldNum" uldValue="<%=(String)uld%>" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_LOANDTL_ULDNUM" style="width:90px;text-transform: uppercase" onblur="getULDDetails(this);"/>
									</logic:present>
										<img name="uldNumLOV" id="uldNumLOV<%=index%>" indexId="index" src="<%=request.getContextPath()%>/images/lov.gif" width="18" height="18" onclick="showUldNumLOV(this)"/>
								</td>
								<td class="iCargoTableDataTd">
								<logic:present name="uldTransactionDetailsVO" property="crnToDisplay">
									<bean:define id="controlReceiptNumberPrefix" name="uldTransactionDetailsVO" property="controlReceiptNumberPrefix" toScope="page" />
									<bean:define id="crnToDisplay" name="uldTransactionDetailsVO" property="crnToDisplay" toScope="page"/>
									<ihtml:text property="crnPrefix" value="<%=(String)controlReceiptNumberPrefix%>" readonly="true" style="width:35px" indexId="index"/>
									<ihtml:text property="crn" value="<%=(String)crnToDisplay%>" maxlength="7"  style="width:60px" indexId="index"/>
								</logic:present>
								</td>
								<td class="iCargoTableDataTd">
									<logic:present name="uldTransactionDetailsVO" property="uldNature">
										<bean:define id="uldNat" name="uldTransactionDetailsVO" property="uldNature" />
										<ihtml:select property="uldNature" value="<%=(String)uldNat%>" indexId="index" style="width:82px" componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_ULDNATURE">
										<bean:define id="contents" name="uldNature"/>
										<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription" />
										</ihtml:select>
									</logic:present>
										<logic:notPresent name="uldTransactionDetailsVO" property="uldNature">
										<ihtml:select property="uldNature" value=""  indexId="index" style="width:82px" componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_ULDNATURE">
										<bean:define id="contents" name="uldNature"/>
										<ihtml:options collection="contents" property="fieldValue" labelProperty="fieldDescription" />
										</ihtml:select>
									</logic:notPresent>
								</td>
								<td class="iCargoTableDataTd">
								<logic:notPresent name="uldTransactionDetailsVO" property="txStationCode">
										<ihtml:text property="destnAirport" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_DESTSTATION" maxlength="3" value="" style="width:40px" indexId="index"/>
									</logic:notPresent>
									<logic:present name="uldTransactionDetailsVO" property="txStationCode">
										<bean:define id="destn" name="uldTransactionDetailsVO" property="txStationCode" />
										<ihtml:text property="destnAirport" componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_DESTSTATION" maxlength="3" value="<%=(String)destn%>" style="width:40px" indexId="index"/>
									</logic:present>
									 <img src="<%= request.getContextPath()%>/images/lov.gif" width="18" height="18" id="destnAirportImg" name="destnAirportImg" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destnAirport.value,'destnAirport','1','destnAirport','',<%=index%>)" alt="Airport LOV"/>
								</td>
								<td class="iCargoTableDataTd" >
								 <logic:present name="uldCondition"> <!--Added by A-7978 for ICRD-248049-->
									 <logic:present name="uldTransactionDetailsVO" property="uldConditionCode">
											<bean:define id="uldCond" name="uldTransactionDetailsVO" property="uldConditionCode" />
											<ihtml:select property="uldCondition" value="<%=(String)uldCond%>" indexId="index" componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_CONDITION" >
											<bean:define id="ULDServiceabilityVO" name="uldCondition"/>
											<ihtml:options collection="ULDServiceabilityVO" property="code" labelProperty="description" />
											</ihtml:select>
										</logic:present>
											<logic:notPresent name="uldTransactionDetailsVO" property="uldConditionCode">
											<ihtml:select property="uldCondition" value=""  indexId="index" componentID="CMB_ULD_DEFAULTS_LOANBORROWULD_CONDITION" >
											<bean:define id="ULDServiceabilityVO" name="uldCondition"/>
											<ihtml:options collection="ULDServiceabilityVO" property="code" labelProperty="description" />
											</ihtml:select>
									</logic:notPresent>
								</logic:present>
								</td>
								<td class="iCargoTableDataTd ic-center" >
								<logic:present name="uldTransactionDetailsVO" property="damageFlagFromScreen">
									  <bean:define id="dgflg" name="uldTransactionDetailsVO" property="damageFlagFromScreen" />
										<logic:equal name="dgflg" value="Y">
										   <input type="checkbox" title = "Damaged" name="damagedFlag"   checked value="<%=index%>"/>
										</logic:equal>
										<logic:notEqual name="dgflg" value="Y">
											<input type="checkbox" title = "Damaged" name="damagedFlag"  value="<%=index%>"/>
										</logic:notEqual>
								</logic:present>
								<logic:notPresent name="uldTransactionDetailsVO" property="damageFlagFromScreen">
									<input type="checkbox" title = "Damaged" name="damagedFlag"  value="<%=index%>" />
								</logic:notPresent>								
								</td>  
							<td class="iCargoTableDataTd" >
										<logic:notPresent name="uldTransactionDetailsVO" property="odlnCode">
											<ihtml:text property="odlnCode" indexId="index"  componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_ODLNCODE" maxlength="2" value=""  style="width:40px" />
										</logic:notPresent>
										<logic:present name="uldTransactionDetailsVO" property="odlnCode">
											<bean:define id="odlnCode" name="uldTransactionDetailsVO" property="odlnCode" />
											<ihtml:text property="odlnCode" indexId="index"  componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_ODLNCODE" maxlength="2" value="<%=(String)odlnCode%>"  style="width:40px" />
										</logic:present>
										<img id="listOdlnGroupLov<%=index%>" src="<%=request.getContextPath()%>/images/lov.gif" width="18" height="18" alt="Odln Group LOV" onclick="groupLovDisplay(this)"/>
									 </td>
								<td class="iCargoTableDataTd" >
									<logic:notPresent name="uldTransactionDetailsVO" property="damageRemark">
										<ihtml:text property="damageRemark" indexId="index"  componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_DAMAGEREMARK" maxlength="100" value=""  style="width:185px"/>
									</logic:notPresent>
									<logic:present name="uldTransactionDetailsVO" property="damageRemark">
										<bean:define id="damageRemark" name="uldTransactionDetailsVO" property="damageRemark" />
										<ihtml:text property="damageRemark" indexId="index"  componentID="TXT_ULD_DEFAULTS_LOANBORROWULD_DAMAGEREMARK" maxlength="100" value="<%=(String)damageRemark%>"  style="width:185px"/>
									</logic:present>									
								</td>
							</tr>
                        </logic:iterate>
					</logic:present>
 				</tbody>
                </table>
		   
		</div>
		</div>
		</div>

		</div>
 			
 		

 



