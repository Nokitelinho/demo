<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: AirportFacilityMaster_Button.jsp
* Date				: 04-Sep-2008
* Author(s)			: A-3448
 --%>
 
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm" %>

<business:sessionBean id="facilityCodeFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityCode"/>
		   
<business:sessionBean id="facilityTypeCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="FacilityType"/>	   
		   
<business:sessionBean id="contentCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.airportfacilitymaster"
		   method="get"
		   attribute="Content"/>		   
	<bean:define id="templateRowCount" value="0"/>
	<tr template="true" id="facilityTemplateRow" style="display:none">
	<ihtml:hidden property="operationFlag" value="NOOP"/>
	<ihtml:hidden property="sequenceNumber" value="" />
	<ihtml:hidden property="contentVal" name="contentVal" value=""/>
	<td class="ic-center">
		<input type="checkbox" name="selectedRows" id="selectedRows" value="" />
	</td>				
	<td>				 
		<logic:present name="facilityCodeFromSession">					
			<bean:define id="facvalue" name="facilityCodeFromSession"/>				   
			<logic:iterate id="iterator1" name="facvalue" type="com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO">
			<logic:present name="iterator1" property="facilityCode"> 
			<bean:define id="facilityCode" name="iterator1" property="facilityCode" />			    
			<ihtml:hidden property="facilityCodeVal" value="<%=(String)facilityCode%>"/>				    
			</logic:present>
			<logic:notPresent name="iterator1" property="facilityCode">
			<ihtml:hidden property="facilityCodeVal" value=""/>
			</logic:notPresent>
			</logic:iterate>
			</logic:present>
		
		<logic:notPresent name="facilityCodeFromSession">			    
		<ihtml:hidden property="facilityCodeVal"  value=""/>
		</logic:notPresent>			    
		
		<html:select styleClass="iCargoMediumComboBox" property="facility" title="Facility Type">				
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
	</td>				
	<td>
		<logic:equal name="AirportFacilityMasterForm" property="wareHouseFlag" value="facilityCode">
				<ihtml:select property="facilityCode" indexId="templateRowCount" componentID="CMP_ULD_DEFAULTS_FACILITYCODE_COMBO">
					<logic:present name="facilityCodeFromSession">
						<bean:define id="facvalue" name="facilityCodeFromSession"/>
						<ihtml:options collection="facvalue" property="facilityCode" labelProperty="facilityCode"/>
					</logic:present>
				</ihtml:select>
					<ihtml:text property="whsFacilityCode" name="whsFacilityCode" indexId="templateRowCount" styleClass="iCargoEditableTextFieldRowColor1" style="width:80px;border:0px;visibility:hidden" value="" maxlength="10"/>

		</logic:equal>
		<logic:notEqual name="AirportFacilityMasterForm" property="wareHouseFlag" value="facilityCode">
				<ihtml:text property="facilityCode" componentID="CMP_ULD_DEFAULTS_FACILITY_CODE" name="facilityCode" indexId="templateRowCount"   value="" maxlength="10"/>
				<ihtml:select property="whsFacilityCode" indexId="templateRowCount" componentID="CMP_ULD_DEFAULTS_FACILITYCODE_COMBO" style="visibility:hidden">
					<logic:present name="facilityCodeFromSession">
						<bean:define id="facvalue" name="facilityCodeFromSession"/>
						<ihtml:options collection="facvalue" property="facilityCode" labelProperty="facilityCode"/>
					</logic:present>
				</ihtml:select>
		</logic:notEqual>
	</td>
	<td >
		<ihtml:text property="description" name="description" componentID="CMP_ULD_DEFAULTS_FACILITY_DESC" indexId="templateRowCount" styleClass="iCargoEditableTextFieldRowColor1"  value="" maxlength="50"/>
	</td>				
	<td>
		<html:select styleClass="iCargoMediumComboBox" property="content" title="Content">
			<logic:present name="contentCombo">
				<logic:iterate id="StatusIterator" name="contentCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
					<logic:present name="StatusIterator" property="fieldValue">
						<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
						<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
					</logic:present>
				</logic:iterate>
			</logic:present>
		</html:select>
	</td>
	<td>
		<input name="defaultFlag" type="checkbox" id="<%=String.valueOf(templateRowCount)%>" value="N" onclick="singleDefaultSelect(this)"/>
	</td>
</tr>

