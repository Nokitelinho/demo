<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: MaintainAccessoriesStock.jsp
* Date				: 20-Jan-2006
* Author(s)			: A-2122
 --%>


<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">

<head>
		
	

<title>
<common:message bundle="maintainaccessoriesstockResources" key="uld.defaults.stock.maintainAccessories.lbl.maintainAccessoriesStockTitle" />
</title>

<meta name="decorator" content="mainpanelrestyledui">

<common:include type="script" src="/js/uld/defaults/MaintainAccessoriesStock_Script.jsp"/>

</head>


<body>	
	

<business:sessionBean id="KEY_ACCESSORYDETAILS"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.maintainaccessoriesstock"
		   method="get"
		   attribute="accessoriesStockConfigVO"/>

<business:sessionBean id="oneTimeValues"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.maintainaccessoriesstock"
		   method="get"
		   attribute="oneTimeValues"/>

<business:sessionBean id="navigationAccs"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintainaccessoriesstock"
		method="get"
		attribute="accessoriesStockConfigVOMap" />





<bean:define id="form"
		 name="maintainAccessoriesStockForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.stock.MaintainAccessoriesStockForm"
		 toScope="page" />


<div class="iCargoContent" id="pageDiv" >

	<ihtml:form action="/uld.defaults.stock.screenloadmaintainaccessoriesstock.do">

	      <ihtml:hidden property="displayPage"/>
	      <ihtml:hidden property="lastPageNum"/>
	      <ihtml:hidden property="currentPage"/>
	      <ihtml:hidden property="totalRecords"/>
	      <ihtml:hidden property="accCodesSelected"/>
	      <ihtml:hidden property="airCodesSelected"/>
	      <ihtml:hidden property="stationsSelected"/>
	       <ihtml:hidden property="accessoryDisableStatus"/>

	      <ihtml:hidden property="statusFlag"/>
	      <ihtml:hidden property="detailsFlag"/>
	      <ihtml:hidden property="modeFlag"/>
	      <ihtml:hidden property="lovFlag"/>
	      <ihtml:hidden property="screenStatusFlag"/>

	<div class="ic-content-main ic-center" style="width:75%;">
		<span class="ic-page-title ic-display-none">
			<common:message  key="uld.defaults.stock.maintainAccessories.lbl.maintainAccessoriesStockHeading" />
        </span>
		
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-row">
					<div class="ic-button-container">
			<logic:present name="navigationAccs">
			<common:popuppaginationtag
				pageURL="javascript:NavigateAcsrsDetails('lastPageNum','displayPage')"
				linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink"
				displayPage="<%=form.getDisplayPage()%>"
				totalRecords="<%=form.getTotalRecords()%>" />
				</logic:present>
					</div>
				</div>
				<div class="ic-row ">
					<div class="ic-input ic-split-25 ic-label-30">
						<label>
							<common:message  key="uld.defaults.stock.maintainAccessories.lbl.accessoryCode" /><span class="iCargoMandatoryFieldIcon">*</span>
                        </label>
						<logic:present name="KEY_ACCESSORYDETAILS" property="accessoryCode">
							<bean:define id="accessoryCod" name="KEY_ACCESSORYDETAILS" property="accessoryCode"/>
							<ihtml:select  property="accessoryCode" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_ACCESSORYCODE"  value ="<%=accessoryCod.toString()%>" >
								<logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										<logic:equal name="parameterCode" value="uld.defaults.accessoryCode">
											<bean:define id="parameterValues" name="oneTimeValue" property="value" />
											<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
												<logic:present name="parameterValue">
												<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
													<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
													<html:option value="<%=(String)fieldValue%>">
														<%=(String)fieldDescription%>
													</html:option>
												</logic:present>
											</logic:iterate>
										</logic:equal>
									</logic:iterate>
								</logic:present>
							</ihtml:select>
						</logic:present>
						<logic:notPresent name="KEY_ACCESSORYDETAILS" property="accessoryCode">
									<ihtml:select property="accessoryCode"  componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_ACCESSORYCODE" >
							 <logic:present name="oneTimeValues">
								<logic:iterate id="oneTimeValue" name="oneTimeValues">
									<bean:define id="parameterCode" name="oneTimeValue" property="key" />
									<logic:equal name="parameterCode" value="uld.defaults.accessoryCode">
									<bean:define id="parameterValues" name="oneTimeValue" property="value" />
										<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
											<logic:present name="parameterValue">
											<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
												<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
												<html:option value="<%=(String)fieldValue%>">
													<%=(String)fieldDescription%>
												</html:option>
											</logic:present>
										</logic:iterate>
									</logic:equal>
								</logic:iterate>
							</logic:present>
						</ihtml:select>
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-25 ic-label-30">
						<label>
                          	<common:message  key="uld.defaults.stock.maintainAccessories.lbl.airlineCode" /><span class="iCargoMandatoryFieldIcon">*</span>
                        </label>
						<logic:present name="KEY_ACCESSORYDETAILS" property="airlineCode">
							<bean:define id="airlinecod" name="KEY_ACCESSORYDETAILS" property="airlineCode"/>
							<ihtml:text property="airlineCode" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES__AIRLINECODE" name="maintainAccessoriesStockForm" value ="<%=airlinecod.toString()%>" maxlength="3"  />
						</logic:present>
						<logic:notPresent name="KEY_ACCESSORYDETAILS" property="airlineCode">
							<ihtml:text property="airlineCode" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES__AIRLINECODE" name="maintainAccessoriesStockForm"   value ="<%=form.getAirlineCode()%>" maxlength="3" />
						</logic:notPresent>
						<button type="button" class="iCargoLovButton" name="airlinelov" id="airlinelov"  />
					</div>
					<div class="ic-input ic-split-25 ic-label-30">
						<label>
							<common:message key="uld.defaults.stock.maintainAccessories.lbl.station" /><span class="iCargoMandatoryFieldIcon">*</span>
						</label>
						<logic:present name="KEY_ACCESSORYDETAILS" property="stationCode">
							<bean:define id="stationCod" name="KEY_ACCESSORYDETAILS" property="stationCode"/>
							<ihtml:text property="stationCode" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_STATION" name="maintainAccessoriesStockForm" value ="<%=stationCod.toString()%>" maxlength="3"  />
						</logic:present>
						<logic:notPresent name="KEY_ACCESSORYDETAILS" property="stationCode">
							<ihtml:text property="stationCode" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_STATION" name="maintainAccessoriesStockForm"   value ="<%=form.getStationCode()%>" maxlength="3" />
						</logic:notPresent>
						<button type="button" class="iCargoLovButton" name="airportlov" id="airportlov"  />
                    </div>
					<bean:define id="airlineId" name="form" property="airlineIdentifier"/>
				    <ihtml:hidden property="airlineIdentifier" name="maintainAccessoriesStockForm" value ="<%=String.valueOf(airlineId)%>"/>
				
					<div class="ic-button-container ic-input ic-split-25 ic-label-30">
						<ihtml:nbutton property="btList" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_LIST_BUTTON" accesskey="L">
							<common:message key="uld.defaults.stock.maintainAccessories.btn.btList" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btClear" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_CLEAR_BUTTON" accesskey="C">
							<common:message  key="uld.defaults.stock.maintainAccessories.btn.btClear" />
						</ihtml:nbutton>
					</div>
				</div>
			</div>
		</div>
		
		<div class="ic-main-container">	
			<div class="ic-border" style="height:420px">
				<div class="ic-row ">
					<div class="ic-input ic-split-35 ic-label-30">
						<label>
							<common:message key="uld.defaults.stock.maintainAccessories.lbl.accessoryDescription"  />
						</label>
						<logic:present name="KEY_ACCESSORYDETAILS" property="accessoryDescription">
						<bean:define id="acessoryDesc" name="KEY_ACCESSORYDETAILS" property="accessoryDescription"/>
						<ihtml:text property="accessoryDescription" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_ACCESSORYDESCRIPTION" name="maintainAccessoriesStockForm" value ="<%=acessoryDesc.toString()%>" maxlength="20" accesskey="C" />
						</logic:present>

						<logic:notPresent name="KEY_ACCESSORYDETAILS" property="accessoryDescription">
						<ihtml:text property="accessoryDescription" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_ACCESSORYDESCRIPTION" name="maintainAccessoriesStockForm"   value ="" maxlength="20"/>
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-25 ic-label-22">
						<label>
							<common:message  key="uld.defaults.stock.maintainAccessories.lbl.available" />
						</label>
						<logic:present name="KEY_ACCESSORYDETAILS" property="available">
						<bean:define id="available" name="KEY_ACCESSORYDETAILS" property="available"/>
							<ihtml:text property="available" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_AVAILABLE" style="text-align:right"  name="maintainAccessoriesStockForm"  value ="<%=available.toString()%>"  maxlength="5" />
							</logic:present>

							<logic:notPresent name="KEY_ACCESSORYDETAILS" property="available">
							<ihtml:text property="available" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_AVAILABLE" name="maintainAccessoriesStockForm"  value ="" maxlength="5" />
						</logic:notPresent>
					</div>
					<div class="ic-input ic-split-25 ic-label-20">
						<label>
							<common:message key="uld.defaults.stock.maintainAccessories.lbl.loaned" />
						</label>
						<logic:present name="KEY_ACCESSORYDETAILS" property="loaned">
						<bean:define id="loaned" name="KEY_ACCESSORYDETAILS" property="loaned"/>
						<ihtml:text property="loaned" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_LOANED" style="text-align:right" name="maintainAccessoriesStockForm"  value ="<%=loaned.toString()%>" maxlength="5" disabled="true"/>
						</logic:present>

					   <logic:notPresent name="KEY_ACCESSORYDETAILS" property="loaned">
						<ihtml:text property="loaned" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_LOANED" name="maintainAccessoriesStockForm"  value="" maxlength="5" disabled="true" />
					   </logic:notPresent>
					</div>
					<div class="ic-input ic-split-15 ic-label-45 ic-left">
						<label>
							<common:message key="uld.defaults.stock.maintainAccessories.lbl.minqty" />
						</label>
						<span class="iCargoMandatoryFieldIcon">*</span>
						<logic:present name="KEY_ACCESSORYDETAILS" property="minimumQuantity">
							<bean:define id="minimumQuantity" name="KEY_ACCESSORYDETAILS" property="minimumQuantity"/>
							<logic:equal name="form"  property="statusFlag" value="I">
								<ihtml:text property="minimumQuantity" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_MINIMUMQUANTITY" style="text-align:right" name="maintainAccessoriesStockForm"  value ="" maxlength="5"/>
							</logic:equal>
							<logic:equal name="form"  property="statusFlag" value="U">
								<ihtml:text property="minimumQuantity" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_MINIMUMQUANTITY" style="text-align:right" name="maintainAccessoriesStockForm"  value ="<%=minimumQuantity.toString()%>" maxlength="5"/>
							</logic:equal>
							<logic:equal name="form"  property="statusFlag" value="">
								<ihtml:text property="minimumQuantity" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_MINIMUMQUANTITY" style="text-align:right" name="maintainAccessoriesStockForm"  value ="" maxlength="5"/>
							</logic:equal>
						</logic:present>
						<logic:notPresent name="KEY_ACCESSORYDETAILS" property="minimumQuantity">
							<ihtml:text property="minimumQuantity" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_MINIMUMQUANTITY" style="text-align:right" name="maintainAccessoriesStockForm"  value="" maxlength="5"/>
						</logic:notPresent>
					</div>
				</div>
				<div class="ic-row ">
					<div class="ic-input ic-split-100 ic-label-10 ic-left">
						<label>
							<common:message key="uld.defaults.stock.maintainAccessories.lbl.remarks" />
						</label>
							<logic:present name="KEY_ACCESSORYDETAILS" property="remarks">
					<bean:define id="remarks" name="KEY_ACCESSORYDETAILS" property="remarks"/>
						<ihtml:textarea property="remarks" rows="3" cols="30" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_REMARKS"  name="maintainAccessoriesStockForm"    onblur="validateMaxLength(this,500)" value ="<%=remarks.toString()%>"  />

							 </logic:present>

							 <logic:notPresent name="KEY_ACCESSORYDETAILS" property="remarks">

								<ihtml:textarea property="remarks" rows="3" cols="30" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_REMARKS"  name="maintainAccessoriesStockForm"    onblur="validateMaxLength(this,500)"  value =""  />
							 </logic:notPresent>
					</div>
				</div>
			</div>
		</div>
		<div class="ic-foot-container">
			<div class="ic-button-container paddR15">
				<ihtml:nbutton property="btSave" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_SAVE_BUTTON" accesskey="S">
				<common:message  key="uld.defaults.stock.maintainAccessories.btn.btSave" />
				</ihtml:nbutton>

				<ihtml:nbutton property="btClose" componentID="TXT_ULD_DEFAULTS_MAINTAINACCESSORIES_CLOSE_BUTTON" accesskey="O">
				<common:message  key="uld.defaults.stock.maintainAccessories.btn.btClose" />
				</ihtml:nbutton>
			</div>
        </div>
			</div>
		
	
		</ihtml:form>
   	</div>				
	
	</body>
</html:html>

