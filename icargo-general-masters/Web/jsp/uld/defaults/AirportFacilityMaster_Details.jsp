<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: AirportFacilityMaster_Details.jsp
* Date				: 25-Aug-2008
* Author(s)			: A-3278
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm" %>	
	

<business:sessionBean id="KEY_DETAILS"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="ULDAirportLocationVOs"/>

<business:sessionBean id="airportCodeFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="AirportCode"/>

<business:sessionBean id="facilityCodeFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityCode"/>

<business:sessionBean id="facilityTypeValueFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityTypeValue"/>

<business:sessionBean id="facilityTypeCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityType"/>
	
<!-- Added by a-3278 for QF1006 on 04Aug08 -->		   
<business:sessionBean id="contentValueFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="ContentValue"/>

<business:sessionBean id="contentCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="Content"/>
<!-- Added by a-3278 for QF1006 on 04Aug08 ends -->	

<bean:define id="form"
		name="AirportFacilityMasterForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm"
		toScope="page" />
<% String chkBoxFlag="false";%>
	<!-- Added by a-3278 for QF1006 on 04Aug08 -->
	<div class="ic-head-container">
	
			<div class="ic-input-container">
				<div class="ic-filter-panel">
				 <div class="ic-row "><h3><common:message key="uld.defaults.searchcriteria" /></h3></div>
						<div class="ic-row ">
		<div class="ic-input ic-mandatory ic-split-40 ic-label-45">
		   
			<label>
				<common:message key="uld.defaults.airportcode" />
			</label>
			
		<logic:present name="airportCodeFromSession">
		<bean:define id="airportCodeFromSession" name="airportCodeFromSession" />
		<ihtml:text componentID="CMP_ULD_DEFAULTS_AIRPORTCODE" property="airportCode" value="<%=(String)airportCodeFromSession%>" name="AirportFacilityMasterForm" maxlength="3" style="text-transform : uppercase"/>
		</logic:present>
	
		<logic:notPresent name="airportCodeFromSession">
		<ihtml:text componentID="CMP_ULD_DEFAULTS_AIRPORTCODE" property="airportCode" value="" name="AirportFacilityMasterForm" maxlength="3" style="text-transform : uppercase"/>
		</logic:notPresent>
		<button type="button" class="iCargoLovButton" name="airportlovorigin" id="airportlovorigin"></button>
		</div>
		<div class="ic-input ic-split-30">
			<label>
				<common:message key="uld.defaults.facilitytype" />
			</label>
			<logic:present name="facilityTypeValueFromSession" >
			<bean:define id="facilityTypeValueFromSession" name="facilityTypeValueFromSession"/>
			<html:select styleClass="iCargoMediumComboBox" property="facilityType"  title="Facility Type" value="<%=facilityTypeValueFromSession.toString()%>">
			<html:option value=""><common:message key="combo.select"/></html:option>
			<logic:present name="facilityTypeCombo">
				<logic:iterate id="StatusIterator" name="facilityTypeCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
					<logic:present name="StatusIterator" property="fieldValue">
						<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
						<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
					</logic:present>
				</logic:iterate>
			</logic:present>
		
			<logic:notPresent name="facilityTypeCombo">
			</logic:notPresent>
			</html:select>
			</logic:present>
		
			<logic:notPresent name="facilityTypeValueFromSession">
			<html:select styleClass="iCargoMediumComboBox" property="facilityType" title="Facility Type">
			<html:option value=""><common:message key="combo.select"/></html:option>
			<logic:present name="facilityTypeCombo">
				<logic:iterate id="StatusIterator" name="facilityTypeCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
					<logic:present name="StatusIterator" property="fieldValue">
						<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
						<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
					</logic:present>
				</logic:iterate>
			</logic:present>
		
			<logic:notPresent name="facilityTypeCombo">
			</logic:notPresent>
			</html:select>
			</logic:notPresent>
		</div>
		
			<div class="ic-button-container" >
				<ihtml:nbutton property="btList" componentID="CMP_ULD_DEFAULTS_LIST_BTN" accesskey="L" >
				<common:message key="uld.defaults.btn.btlist" />
				</ihtml:nbutton>

				<ihtml:nbutton property="btClear" componentID="CMP_ULD_DEFAULTS_CLEAR_BTN" accesskey="C" >
				<common:message key="uld.defaults.btn.btclear" />
				</ihtml:nbutton>
			</div>
		
		</div>
		</div>
		</div>
</div>
