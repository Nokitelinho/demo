<%--/**********************************************************************
* Project	 				: iCargo
* Module Code & Name		: mailtracking-ErrorHandling
* File Name					: MailTrackingErrorHandlingFilter.jsp
* Date						: 30-Jul-2014
* Author(s)					: Mini S Nair
 ************************************************************************/
 --%>


<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>


<bean:define id="form" name="MailTrackingErrorHandlingForm" type="com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailTrackingErrorHandlingForm" toScope="page" />
<business:sessionBean id="oneTimeValues" moduleName="admin.monitoring" screenID="admin.monitoring.errorhandling" method="get" attribute="oneTimeValues" />

	
	
<ihtml:form action="mailtracking.defaults.errorhandling.listErrors.do">
<div class="ic-row">
<div id="errorhandlingFilter">
	<div class="ic-row">
	<div class="ic-input ic-mandatory ic-split-10 marginL10">
		<label>
			<common:message key="mailtracking.defaults.errorhandling.lbl.arpcod" />
		</label>
		<ihtml:text property="airportcode" maxlength="4" value="<%=form.getAirportcode()%>" componentID="CMP_Mailtracking_Defaults_ErrorHandling_AirportCode" style="text-align:left"/>
		<div class="lovImg">
		<img id="arpcodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.airportcode.value,'Airport','1','airportcode','',0)">
	</div></div>
	<div class="ic-input ic-split-10">
		<label>		
			<common:message key="icargo.mailtracking.defaults.errorhandling.lbl.mailbag" />
		</label>
		<ihtml:text property="mailbag" maxlength="29"  componentID="CMP_Mailtracking_Defaults_ErrorHandling_MailBag" style="text-align:left"/>
	</div>
	<div class="ic-input ic-split-12">
		<label>	
			<common:message  key="icargo.mailtracking.defaults.errorhandling.lbl.function"/>
		</label>					
			<ihtml:select property="function" componentID="CMP_Mailtracking_Defaults_ErrorHandling_Function" >
									<html:option value=""><common:message key="combo.select"/></html:option>
									<logic:present name="oneTimeValues" >
										<logic:iterate id="oneTimeValue" name="oneTimeValues">
											<bean:define id="parameterCode" name="oneTimeValue" property="key"/>
											<logic:equal name="parameterCode" value="mailtracking.defaults.hhttransactions">
												<bean:define id="parameterValues" name="oneTimeValue" property="value"/>
												<logic:iterate id="parameterValue" name="parameterValues" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
													<logic:present name="parameterValue" property="fieldValue">
														<bean:define id="fieldValue" name="parameterValue" property="fieldValue"/>
														<bean:define id="fieldDescription" name="parameterValue" property="fieldDescription"/>
														<ihtml:option value="<%=(String)fieldValue%>"><%=(String)fieldDescription%></ihtml:option>
													</logic:present>
												</logic:iterate>
											</logic:equal>
										</logic:iterate>
									</logic:present>  						
			</ihtml:select>						
		</div>			
		<div class="ic-input ic-split-12">
			<label>	
				<common:message key="icargo.mailtracking.defaults.errorhandling.lbl.container" />
			</label>
			<ihtml:text property="container" maxlength="13"  componentID="CMP_Mailtracking_Defaults_ErrorHandling_Container" style="text-align:left"/>
		</div>
		<div class="ic-input ic-split-12">
			<label>	
				<common:message key="icargo.mailtracking.defaults.errorhandling.lbl.flightnumber" />
			</label>
			<ibusiness:flightnumber id="fltNo" carrierCodeProperty="flightCarrierCode"  flightCodeProperty="flightNumber" carriercodevalue="<%=form.getFlightCarrierCode()%>" flightcodevalue="<%=form.getFlightNumber()%>" tabindex="1" componentID="CMP_Mailtracking_Defaults_ErrorHandling_FlightNumber"/>
		</div>
		<div class="ic-input ic-split-12">
			<label>	
				<common:message key="icargo.mailtracking.defaults.errorhandling.lbl.flightdate" />
			</label>
			<ibusiness:calendar property="flightdate" id="flightDate" type="image" value="" componentID="CMP_Mailtracking_Defaults_ErrorHandling_FlightDate" tabindex="2"/>
		</div>
		<div class="ic-input ic-split-15">
			<label>	
				<common:message key="icargo.mailtracking.defaults.errorhandling.tableheader.errordesc" />
			</label>
			<ihtml:hidden property="parameterCodeValue" />	
			<ihtml:text property="errorCode" componentID="CMP_Mailtracking_Defaults_ErrorHandling_errorcode" styleClass="iCargoTextFieldLong"/> <!--Component ID added by a-7871 for ICRD-220420-->
			<div class="lovImg">
			<img id="errorCodeLov" src="<%=request.getContextPath()%>/images/lov.png" width="22" height="22" onclick="displayOneTimeLOV('screenloadOneTime.do','N','Y','screenloadOneTime.do',targetFormName.elements.errorCode.value, targetFormName.elements.errorCode.value,'Error Description','0','errorCode','errorCode',0,'mailtracking.defaults.errorcodes','')"/>
										
										
				
		</div>
	</div>		
		<div class="ic-button-container marginT10 paddR5">			
								<ihtml:nbutton property="btnList"  componentID="CMP_Mailtracking_Defaults_ErrorHandling_LIST_BUTTON" onclick="return btList()" >
										<common:message  key="icargo.mailtracking.defaults.errorhandling.btn.list" />
								</ihtml:nbutton>	
							
							
								<ihtml:nbutton property="btclear"  componentID="CMP_Mailtracking_Defaults_ErrorHandling_CLEAR_BUTTON" styleClass="btn-inline btn-secondary" onclick="return clearfn()">
										<common:message  key="icargo.mailtracking.defaults.errorhandling.btn.clear" />
								</ihtml:nbutton>	
		</div>
	</div>
				
				
				
				
</div>    
</div> 
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
