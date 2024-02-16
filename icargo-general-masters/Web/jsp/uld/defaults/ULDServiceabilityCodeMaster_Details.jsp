<%--
* Project	 		: iCargo
* Module Code & Name		: ULD
* File Name			: ULDServiceabilityCodeMaster_Details.jsp
* Date				: 25-Aug-2008
* Author(s)			: A-3278
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm" %>	
	

<business:sessionBean id="KEY_DETAILS"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.uldserviceability"
		   method="get"
		   attribute="ULDServiceabilityVOs"/>

<business:sessionBean id="partyTypeValueFromSession"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.uldserviceability"
		   method="get"
		   attribute="PartyTypeValue"/>

<business:sessionBean id="partyTypeCombo"
		   moduleName="uld.defaults"
		   screenID="uld.defaults.uldserviceability"
		   method="get"
		   attribute="PartyType"/>


<% String chkBoxFlag="false";%>


	<div class="ic-row ic-input">
		<label>
			<common:message key="uld.defaults.partytype" />
		</label>
		<logic:present name="partyTypeValueFromSession" >
		<bean:define id="partyTypeValueFromSession" name="partyTypeValueFromSession"/>
		<html:select styleClass="iCargoMediumComboBox" property="partyType" title="Party Type" value="<%=partyTypeValueFromSession.toString()%>">		
		<logic:present name="partyTypeCombo">
			<logic:iterate id="StatusIterator" name="partyTypeCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
				<logic:present name="StatusIterator" property="fieldValue">
					<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
					<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
				</logic:present>
			</logic:iterate>
		</logic:present>
	
		<logic:notPresent name="partyTypeCombo">
		</logic:notPresent>
		</html:select>
		</logic:present>
	
		<logic:notPresent name="partyTypeValueFromSession">
		<html:select styleClass="iCargoMediumComboBox" property="partyType" title="Party Type">		
		<logic:present name="partyTypeCombo">
			<logic:iterate id="StatusIterator" name="partyTypeCombo" type="com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO">
				<logic:present name="StatusIterator" property="fieldValue">
					<bean:define id="fieldDescription" name="StatusIterator" property="fieldDescription" />
					<html:option value="<%=StatusIterator.getFieldValue()%>"><%=(String)fieldDescription%></html:option>
				</logic:present>
			</logic:iterate>
		</logic:present>
	
		<logic:notPresent name="partyTypeCombo">
		</logic:notPresent>
		</html:select>
		</logic:notPresent>
		<div class="ic-button-container">
			<ihtml:button property="btList" componentID="CMP_ULD_DEFAULTS_LIST_BTN">
		<common:message key="uld.defaults.btn.btlist" />
		</ihtml:button>

		<ihtml:button property="btClear"  styleClass="btn-inline btn-secondary" componentID="CMP_ULD_DEFAULTS_CLEAR_BTN">
		<common:message key="uld.defaults.btn.btclear" />
		</ihtml:button>
		</div>
	</div>

