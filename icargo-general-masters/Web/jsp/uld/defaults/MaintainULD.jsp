<%--
* Project	 		: iCargo
* Module Code & Name: Uld
* File Name			: MaintainULD.jsp
* Date				: 18-Jan-2006
* Author(s)			: A-2001
 --%>

<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<html:html locale="true">
<head>
	
<title>
	<common:message bundle="maintainuldResources" key="uld.defaults.maintainUld.lbl.maintainUldTitle" />
</title>
<meta name="decorator" content="mainpanelrestyledui">
<common:include src="/js/uld/defaults/MaintainULD_Script.jsp" type="script"/>
<% String styleWidth="";%>
<logic:present parameter="isPopUp">
		<% styleWidth="width:100%";%>
		</logic:present>
</head>
<body style="<%=styleWidth%>">
	

<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintainuld"
		method="get"
		attribute="oneTimeValues" />
		
<business:sessionBean
		id="uldVO"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintainuld"
		method="get"
		attribute="uLDVO" />

<business:sessionBean
		id="navigationUlds"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintainuld"
		method="get"
		attribute="uldNosForNavigation" />

<business:sessionBean 
		id="KEY_WEIGHTVO"
		  moduleName="uld.defaults"
		  screenID="uld.defaults.maintainuld"
		  method="get"
		  attribute="weightVO" />

<business:sessionBean
		id="currencies"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintainuld"
		method="get"
		attribute="currencies" />

<business:sessionBean
		id="nonOperationalDamageCodes"
		moduleName="uld.defaults"
		screenID="uld.defaults.maintainuld"
		method="get"
		attribute="nonOperationalDamageCodes" />
<bean:define id="nonOperationalDamageCodes" name="nonOperationalDamageCodes"/>

<bean:define id="form"
		 name="maintainULDForm"  type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm"
		 toScope="page" />
<input type="hidden" id="disableDefStatus" value="Y"/>
  <div class="<%=iCargoContentClass%>" style="width:100%;height:100%;overflow:auto;" id="pageDiv">
	<ihtml:form action="/uld.defaults.screenloadmaintainuld.do" styleClass="ic-main-form">
	      <ihtml:hidden property="screenloadstatus"/>
	      <ihtml:hidden property="statusFlag"/>
	      <ihtml:hidden property="closeStatus"/>
	      <ihtml:hidden property="displayPage"/>
	      <ihtml:hidden property="lastPageNum"/>
	      <ihtml:hidden property="currentPage"/>
	      <ihtml:hidden property="totalRecords"/>
	      <ihtml:hidden property="structuralFlag" />
	      <ihtml:hidden property="transitStatus" />
	      <ihtml:hidden property="transitDisable" />
	      <ihtml:hidden property="saveIndFlag" />
		 
	      <input type="hidden" name="nonOperationalDamageCodes" value="<%=(String)nonOperationalDamageCodes%>" />
	     <input type="hidden" name="currentDialogId" />
	     <input type="hidden" name="currentDialogOption" />

               <div class="ic-content-main">
   	                   <span class="ic-page-title ic-display-none">
				  		<common:message key="uld.defaults.maintainUld.lbl.maintainUldHeading" />
				 	</span>
				<div class="ic-head-container">
						<div class="ic-filter-panel">
						
							<div class="ic-row ic-round-border">
							<div class="ic-input ic-split-25 ic-label-33 ic-mandatory">
								<label><common:message key="uld.defaults.maintainUld.lbl.UldNo" /></label>
							<ibusiness:uld id="uld" uldProperty="uldNumber" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_ULDNO"/>
							</div>
							<div class="ic-button-container">
								<ihtml:nbutton property="btnList" componentID="BTN_ULD_DEFAULTS_MAINTAINULD_LIST" >
									<common:message key="uld.defaults.maintainUld.btn.List" />
								</ihtml:nbutton>
								<ihtml:nbutton property="btnClear" componentID="BTN_ULD_DEFAULTS_MAINTAINULD_CLEAR">
									<common:message key="uld.defaults.maintainUld.btn.Clear" />
								</ihtml:nbutton>
							  </div>
							</div>
							</div>
							</div>
							<div class="ic-main-container">
							  <div class="ic-input-container ">
				<div class="ic-row ic-round-border">		  
				<div class="ic-input ic-split-20">
								<html:checkbox property="createMultiple" value="Y" />
					            <common:message key="uld.defaults.maintainUld.lbl.createMultiple" />
						</div>
						<div class="ic-input ic-split-15 ic-label-33 ic-mandatory">
								<label><common:message key="uld.defaults.maintainUld.lbl.UldTypeCode" /></label>
								<ihtml:text property="uldType" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_ULDTYPECODE" name="maintainULDForm" maxlength="3" />
							</div>
							<div class="ic-input ic-split-15 ic-label-33 ic-mandatory">
								<label><common:message key="uld.defaults.maintainUld.lbl.AirlineCode" /></label>
								<ihtml:text property="ownerAirlineCode" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_AIRLINECODE" name="maintainULDForm" maxlength="3" />
								</div>
							<div class="ic-input ic-split-15 ic-label-33">
								<label><common:message key="uld.defaults.maintainUld.lbl.ULDOwnerCode" /></label>
								<ihtml:text property="uldOwnerCode" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_ULDOWNERCODE" name="maintainULDForm" maxlength="3" />
							</div>
							<div class="ic-input ic-split-15 ic-label-33">
								<label><common:message key="uld.defaults.maintainUld.lbl.TotalNo.ofUlds" /></label>
								<ihtml:text property="totalNoofUlds" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_TOTALULDS" name="maintainULDForm" />
							</div>
							&nbsp;&nbsp;
							<div class="ic-button-container-center paddR10">
								<ihtml:nbutton property="btnSpecifyRange" componentID="BTN_ULD_DEFAULTS_MAINTAINULD_SPECIFYRANGE">
									<common:message key="uld.defaults.maintainUld.btn.SpecifyRange" />
								</ihtml:nbutton>
						</div>
                       </div>
					   <div class="ic-row">
               <div class="ic-bold-label">
               	<h3><common:message key="uld.defaults.maintainUld.lbl.uldDetails" /></h3>
			<logic:present name="navigationUlds">
			   <common:popuppaginationtag 
			  	pageURL="javascript:NavigateZoneDetails('lastPageNum','displayPage')"
			  	linkStyleClass="iCargoLink" disabledLinkStyleClass="iCargoLink" 
			  	displayPage="<%=form.getDisplayPage()%>"
			  	totalRecords="<%=form.getTotalRecords()%>" />
			 </logic:present>
                	</div>
					</div>
					<div class="ic-row ic-round-border">
					<div class="ic-bold-label">
						&nbsp;&nbsp;<b><common:message key="uld.defaults.maintainUld.lbl.structuralDetails" /></b>
							</div>
						<div class="ic-border">
						<div class="ic-row">
						<div class="ic-input ic-split-20 ic-label-33">
					 								  		<label><common:message key="uld.defaults.maintainUld.lbl.TareWt." /></label>
													<!--<ihtml:text property="displayTareWeight" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_TAREWT" name="maintainULDForm"  maxlength="10" style="text-align:right" />-->
													
													<logic:present name="KEY_WEIGHTVO">
													<bean:define id="unitRoundingVO" name="KEY_WEIGHTVO" />
													<% request.setAttribute("unitVO",unitRoundingVO); %>
													<logic:present name="form" property="displayTareWeight">
														<bean:define id="tareWt" name="form" property="displayTareWeight"/>
														<ibusiness:unitCombo unitTxtName="displayTareWeight" style="text-align:right" 
																label="" title="Stated Weight" dataName="unitVO" unitValueMaxLength="12" 
																unitValueStyle="iCargoTextFieldSmall" unitValue="<%= String.valueOf(tareWt) %>" 
																unitListName="displayTareWeightUnit"  
																componentID="CMB_ULD_DEFAULTS_MAINTAINULD_STRLWTLIMIT"	
																unitListValue="<%=form.getDisplayTareWeightUnit()%>" />
																	</logic:present>
													<logic:notPresent name="form" property="displayTareWeight">
														<ibusiness:unitCombo unitTxtName="displayTareWeight" style="text-align:right" 
																label="" title="Stated Weight" dataName="unitVO" unitValueMaxLength="12" 
																unitValueStyle="iCargoTextFieldSmall" unitValue="" 
																unitListName="displayTareWeightUnit"  
																componentID="CMB_ULD_DEFAULTS_MAINTAINULD_STRLWTLIMIT"	
																unitListValue="<%=form.getDisplayTareWeightUnit()%>" />
													</logic:notPresent>
												</logic:present>
													
																		</div>
																		<div class="ic-input ic-split-20 ">
								 		<label><common:message key="uld.defaults.maintainUld.lbl.StructuralWt.Limit" /></label>
								 			   <logic:present name="KEY_WEIGHTVO">
												<bean:define id="unitRoundingVO" name="KEY_WEIGHTVO" />
												<% request.setAttribute("unitVO",unitRoundingVO); %>
												<logic:present name="form" property="displayStructuralWeight">
													<bean:define id="statedWt" name="form" property="displayStructuralWeight"/>
													<ibusiness:unitCombo unitTxtName="displayStructuralWeight" style="text-align:right" 
															label="" title="Stated Weight" dataName="unitVO" unitValueMaxLength="12" 
															unitValueStyle="iCargoTextFieldSmall" unitValue="<%= String.valueOf(statedWt) %>" 
															unitListName="displayStructuralWeightUnit"  
															componentID="CMB_ULD_DEFAULTS_MAINTAINULD_STRLWTLIMIT"	
															unitListValue="<%=form.getDisplayStructuralWeightUnit()%>" />
																</logic:present>
												<logic:notPresent name="form" property="displayStructuralWeight">
													<ibusiness:unitCombo unitTxtName="displayStructuralWeight" style="text-align:right" 
															label="" title="Stated Weight" dataName="unitVO" unitValueMaxLength="12" 
															unitValueStyle="iCargoTextFieldSmall" unitValue="" 
															unitListName="displayStructuralWeightUnit"  
															componentID="CMB_ULD_DEFAULTS_MAINTAINULD_STRLWTLIMIT"	
															unitListValue="<%=form.getDisplayStructuralWeightUnit()%>" />
												</logic:notPresent>
						  					</logic:present>
								  </div>
								 <div class="ic-input ic-split-20 ic-label-33">
										<label><common:message key="uld.defaults.maintainUld.lbl.UldContour" /></label>
								 								   <logic:present name="uldVO">
										<ihtml:select property="uldContour" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_ULDCONTOUR" name="uldVO">
										 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="operations.flthandling.uldcontour">
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
									
									 <logic:notPresent name="uldVO">
										<ihtml:select property="uldContour" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_ULDCONTOUR">
										 <ihtml:option value=""><common:message key="combo.select"/></ihtml:option>
											<logic:present name="oneTimeValues">
												<logic:iterate id="oneTimeValue" name="oneTimeValues">
													<bean:define id="parameterCode" name="oneTimeValue" property="key" />
													<logic:equal name="parameterCode" value="operations.flthandling.uldcontour">
													<bean:define id="parameterValues" name="oneTimeValue" property="value" />
														<logic:present name="parameterValues">
															<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
																<logic:present name="parameterValue">
																<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
																	<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
																	<html:option value="<%=(String)fieldValue%>">
																		<%=(String)fieldDescription%>
																	</html:option>
																</logic:present>
															</logic:iterate>
														</logic:present>
													</logic:equal>
												</logic:iterate>
										</logic:present>
										</ihtml:select>
									</logic:notPresent>	
									</div>
									</div>
							        <div class="ic-row">
									 <div class="ic-input ic-split-100 ">
									<label>	&nbsp;&nbsp;	</label>
								<label><common:message key="uld.defaults.maintainUld.lbl.BaseDimension" />
								<common:message key="uld.defaults.maintainUld.lbl.BaseLength" />
													  	<ihtml:text property="displayBaseLength" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_BASELENGHT" name="maintainULDForm"  maxlength="8" style="text-align:right" />
								<common:message key="uld.defaults.maintainUld.lbl.BaseWidth" />
									<ihtml:text property="displayBaseWidth" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_BASEWIDTH" name="maintainULDForm"  maxlength="8" style="text-align:right" />
									<common:message key="uld.defaults.maintainUld.lbl.BaseHeight" />
									<ihtml:text property="displayBaseHeight" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_BASEHEIGHT" name="maintainULDForm"  maxlength="8" style="text-align:right" />
									<!--
									<ihtml:select property="displayDimensionUnit" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_BASEUNIT">
										  <logic:present name="oneTimeValues">
										  	<logic:iterate id="oneTimeValue" name="oneTimeValues">
										  	<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										  		<logic:equal name="parameterCode" value="shared.defaults.dimensionUnitCodes">
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
									</ihtml:select> -->
									<ibusiness:customUnitCombo unitTxtName="displayDimensionUnit" title="Dimension Unit" componentID="CMB_ULD_DEFAULTS_MAINTAINULD_BASEUNIT" unitTypePassed="DIM" unitListName="displayDimensionUnit" unitListValue="<%=form.getDisplayDimensionUnit()%>"/>
								  </label>
								  </div>
								  </div>
								  </div>
								  

													
													
								  <div class="ic-row">
               <div class="ic-bold-label">
							&nbsp;&nbsp;<b><common:message key="uld.defaults.maintainUld.lbl.operationalDetails" /></b>
						</div>
						</div>
						<div class="ic-border">
								<jsp:include page="MaintainULDOperationalDetails.jsp"/>




			


                            </div>
							 <div class="ic-row">
               <div class="ic-bold-label">
							&nbsp;&nbsp;<b><common:message key="uld.defaults.maintainUld.lbl.assetDetails" /></b>
						</div>
						</div>
						<div class="ic-border">
						<div class="ic-row">
					  <div class="ic-input ic-split-20 ic-label-45">
								  	<label><common:message key="uld.defaults.maintainUld.lbl.Vendor" /></label>
								  <ihtml:text property="vendor" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_VENDOR" name="maintainULDForm"  maxlength="20" style="width:100px"  />
								 </div>
								  <div class="ic-input ic-split-20 ic-label-45">
								  	<label><common:message key="uld.defaults.maintainUld.lbl.Manufacturer" /></label>
										<ihtml:text property="manufacturer" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_MANUFACTURER" name="maintainULDForm"  maxlength="20" style="width:100px"  />							
											<div class="lovImg">										
											<img name="manufacturerLov" id="manufacturerLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" 
											onclick="displayOneTimeLOV('screenloadOneTime.do','N','N','screenloadOneTime.do',document.forms[1].manufacturer.value,document.forms[1].manufacturerDesc.value,'Manufacturer','1',
											'manufacturer','manufacturerDesc','0','uld.defaults.manufacturer','')" alt="Manufacturer LOV"/>
											</div>
								</div>
								  <div class="ic-input ic-split-20 ic-label-45">
								  	<label><common:message key="uld.defaults.maintainUld.lbl.UldSerialNo." /></label>
								  <ihtml:text property="uldSerialNumber" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_ULDSERIALNO" name="maintainULDForm"  maxlength="11" />
								   </div>
								    <div class="ic-input ic-split-20 ic-label-45">
										<label><common:message key="uld.defaults.maintainUld.lbl.manfDate" /></label>
								 	    <ibusiness:calendar type="image" id="ManfDate" componentID="CAL_ULD_DEFAULTS_MAINTAINULD_MANUFACTUREDATE" property="manufactureDate" value="<%=form.getManufactureDate()%>" maxlength="11" />
								</div>
								   </div>
									<div class="ic-row">

									 <div class="ic-input ic-split-20 ic-label-45 ic-mandatory">
								  	<label><common:message key="uld.defaults.maintainUld.lbl.PurchaseDate" /></label>
								  <ibusiness:calendar type="image" id="purchaseDate" componentID="CAL_ULD_DEFAULTS_MAINTAINULD_PURCHASEDATE" property="purchaseDate" value="<%=form.getPurchaseDate()%>" maxlength="11" />
								 </div>
								   <div class="ic-input ic-split-20 ic-label-45">
								  	<label><common:message key="uld.defaults.maintainUld.lbl.PurchaseInvNo" /></label>
								  <ihtml:text property="purchaseInvoiceNumber" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_PURCHASEINVNO" name="maintainULDForm"  maxlength="12" />
								  </div>
								  <div class="ic-input ic-split-20 ic-label-45 ic-mandatory">
								  	<label><common:message key="uld.defaults.maintainUld.lbl.ULDPrice" /></label>
								  	<ihtml:text property="uldPrice" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_ULDPRICE" name="maintainULDForm"  maxlength="13" style="text-align:right" />
									<ihtml:text property="uldPriceUnit" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_ULDCURRENCY" maxlength="3" style="width:30px"/>
									<div class="lovImg">
										<img name="currencyLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
									</div>
								 </div>
								  <div class="ic-input ic-split-20 ic-label-45">
								  <label><common:message key="uld.defaults.maintainUld.lbl.lifeSpan" /></label>
								  <ihtml:text property="lifeSpan" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_LIFESPAN" name="maintainULDForm"  maxlength="2" style="text-align:right" />
								 </div>
								  </div>
								   	<div class="ic-row">
								  <div class="ic-input ic-split-20 ic-label-45">
								  <label><common:message key="uld.defaults.maintainUld.lbl.IATAReplacementCost" /></label>
								  <ihtml:text property="iataReplacementCost" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_IATARPLMTCOST" name="maintainULDForm"  maxlength="13" style="text-align:right" />
									<ihtml:text property="iataReplacementCostUnit" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_IATARPLCMTUNT" maxlength="3"/>
									<div class="lovImg">
										<img name="iataReplacementCostUnitLov" height="22" src="<%=request.getContextPath()%>/images/lov.png" width="22" />
									</div>
								   </div>
								   <div class="ic-input ic-split-20 ic-label-45 ic-mandatory">
								<label><common:message key="uld.defaults.maintainUld.lbl.CurrentValue" /></label>
								  <ihtml:text property="currentValue" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_CURRENTVALUE" name="maintainULDForm"  maxlength="13" style="text-align:right" readonly="true" />
									<ihtml:text property="currentValueUnit" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_CURNTVALUEUNT" maxlength="3" readonly="true"/>
									<div class="lovImg">
										<img name="currentValueUnitLov" height="16" src="<%=request.getContextPath()%>/images/lov.png" width="16" disabled="true"/>
									</div>	
								  </div>
								   <div class="ic-input ic-split-20 ic-label-45">
								  <label><common:message key="uld.defaults.maintainUld.lbl.tsoNumber" /></label>
								 <ihtml:text property="tsoNumber" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_TSONUMBER" name="maintainULDForm"  maxlength="15" style="text-align:right" />
								  </div>

								</div>
						  	<div class="ic-row">
						  <div class="ic-input ic-split-75 ic-label-33">
						 <label><common:message key="uld.defaults.maintainUld.lbl.Remarks" /></label>
						 <ihtml:textarea property="remarks"  rows="2" cols="80" componentID="TXT_ULD_DEFAULTS_MAINTAINULD_REMARKS" name="maintainULDForm"/>
							</div>
            			</div>
                 </div>
				 	  </div>
				 </div>
				  </div>
				      <div class="ic-foot-container">
                      <div class="ic-row">
                      <div class="ic-button-container paddR10">
						<ihtml:nbutton property="btnMovementHistory" componentID="BTN_ULD_DEFAULTS_MAINTAINULD_MOVEMENTHISTORY">
							<common:message key="uld.defaults.maintainUld.btn.MovementHistory" />
						</ihtml:nbutton>

						<ihtml:nbutton property="btnDelete" componentID="BTN_ULD_DEFAULTS_MAINTAINULD_DELETE">
							<common:message key="uld.defaults.maintainUld.btn.Delete" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnSave" componentID="BTN_ULD_DEFAULTS_MAINTAINULD_SAVE">
							<common:message key="uld.defaults.maintainUld.btn.Save" />
						</ihtml:nbutton>
						<ihtml:nbutton property="btnClose" componentID="BTN_ULD_DEFAULTS_MULTIPLEULD_CLOSE">
							<common:message key="uld.defaults.maintainUld.btn.Close" />
						</ihtml:nbutton>
			   	 </div>
				 </div>
			   </div>
            </div>
			
          </ihtml:form>
       
	   </div>
	</body>
</html:html>

