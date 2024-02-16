<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: RelocateULD.jsp
* Date				: 06-Jun-2006
* Author(s)			: A-2122
 --%>


<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.RelocateULDForm" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

<html:html locale="true">
<head>
	
		<%@ include file="/jsp/includes/customcss.jsp"%>
			
	
<title>
<common:message bundle="relocateuldResources" key="uld.defaults.relocateuld.popup.lbl.relocateULDTitle" />
</title>
<meta name="decorator" content="popup_panel">
<common:include type="script" src="/js/uld/defaults/RelocateULD_Script.jsp"/>
</head>
<body >
	



<bean:define id="form"
		name="relocateULDForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.RelocateULDForm"
		toScope="page" />

<business:sessionBean
		id="oneTimeValues"
		moduleName="uld.defaults"
		screenID="uld.defaults.listuld"
		method="get"
		attribute="LocationValues" />


<div  class="iCargoPopUpContent ic-masterbg" >
<ihtml:form action="/uld.defaults.listuld.screenloadrelocateuld.do" styleClass="ic-main-form">

<ihtml:hidden property="uldNumber"/>
<ihtml:hidden property="checkFlag"/>
<ihtml:hidden property="currentStation"/>
<input type="hidden" name="currentDialogId" />
<input type="hidden" name="currentDialogOption" />

	<div class="ic-content-main">
	<span class="ic-page-title">
	<common:message key="uld.defaults.relocateuld.popup.lbl.relocateULDHeading" />
	</span>
		<div class="ic-head-container">
			<div class="ic-filter-panel">
				<div class="ic-input-container">
					<div class=" ic-border">
						<div class="ic-row ic-label-40">
							<div class="ic-input ic-split-30">
								<label><common:message key="uld.defaults.relocateuld.popup.lbl.fromLocation" /></label>
								<logic:present name="relocateULDForm"  property="fromLocation" >
								<%System.out.println("....");%>
								<ihtml:text  componentID="CMP_ULD_DEFAULTS_RELOCATEULD_POPUP_FROMLOCATION" name="relocateULDForm"  property="fromLocation"  maxlength="12" disabled="true"/>
								 </logic:present>
								<logic:notPresent name="relocateULDForm"  property="fromLocation" >
								<%System.out.println("...++.");%>
								<ihtml:text  componentID="CMP_ULD_DEFAULTS_RELOCATEULD_POPUP_FROMLOCATION" name="relocateULDForm"  property="fromLocation"  maxlength="12" disabled="true" value=""/>
								</logic:notPresent>
							</div>

								<div class="ic-input ic-split-30 ic-mandatory">
							<label><common:message key="uld.defaults.relocateuld.popup.lbl.locationType" /></label>
							<ihtml:select property="locationType" componentID="CMP_ULD_DEFAULTS_RELOCATEULD_POPUP_TOLOCATION">
							<html:option value=""><common:message key="combo.select"/></html:option>
								 <logic:present name="oneTimeValues">
									<logic:iterate id="oneTimeValue" name="oneTimeValues">
										<bean:define id="parameterCode" name="oneTimeValue" property="key" />
										<logic:equal name="parameterCode" value="uld.defaults.facilitytypes">
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

							 </div>
		
							<div class="ic-input ic-split-40 ic-mandatory">
								<label><common:message key="uld.defaults.relocateuld.popup.lbl.toLocation" /></label>
								<ihtml:text  componentID="CMP_ULD_DEFAULTS_RELOCATEULD_POPUP_LOCATION" name="relocateULDForm"  property="toLocation"  maxlength="12" />
								<div class="lovImg">
								<img name="facilitycodelov" id="facilitycodelov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" alt="FacilityCode LOV"/>
								</div>
							</div>

		  </div>
                        <div class="ic-row ">
									<div class="ic-input ic-split-100 ic-label-11">
                          <label><common:message key="uld.defaults.relocateuld.popup.lbl.remarks" /></label>
                           <ihtml:textarea  rows="2" cols="85" componentID="CMP_ULD_DEFAULTS_RELOCATEULD_POPUP_REMARKS"  name="relocateULDForm"   property="remarks"  onblur="validateMaxLength(this,100)" />
                          </div>
                         </div>
						  </div>
							</div>
                        </div>
						  </div>
						  
                    <div class="ic-foot-container">
				   <div class="ic-button-container">
			  <ihtml:nbutton property="btSave"
			       componentID="CMP_ULD_DEFAULTS_RELOCATEULD_POPUP_SAVE_BUTTON"  tabindex="5">
			       <common:message key="uld.defaults.relocateuld.popup.btn.btSave" />
			</ihtml:nbutton>

			<ihtml:nbutton property="btClose"
			      componentID="CMP_ULD_DEFAULTS_RELOCATEULD_POPUP__CLOSE_BUTTON"  tabindex="6">
			      <common:message key="uld.defaults.relocateuld.popup.btn.btClose" />
              		</ihtml:nbutton>
                          </div>
                        </div>
                      

</div>
</ihtml:form>
</div>

			
		 
	</body>
</html:html>

