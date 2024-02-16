<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: AirportFacilityMaster_Button.jsp
* Date				: 04-Sep-2008
* Author(s)			: A-3278
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm" %>

<bean:define id="form"
		name="AirportFacilityMasterForm"
		type="com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AirportFacilityMasterForm"
		toScope="page" />
		
<div class="ic-foot-container" >
<div class="ic-button-container paddR5">
	<logic:notEqual name="AirportFacilityMasterForm" property="screenName" value="screenLoad">
		<ihtml:nbutton property="btOk" componentID="CMP_ULD_DEF_OK_BTN" accesskey="K" >
			<common:message key="uld.defaults.btn.btok" />
		</ihtml:nbutton>
	</logic:notEqual>
	<logic:equal name="AirportFacilityMasterForm" property="screenName" value="screenLoad">
	<ihtml:nbutton property="btSave" componentID="CMP_ULD_DEF_SAVE_BTN" accesskey="S" >
	<common:message key="uld.defaults.btn.btsave" />
	</ihtml:nbutton>
	</logic:equal>
	<ihtml:nbutton property="btClose" componentID="CMP_ULD_DEFAULTS_CLOSE_BTN" accesskey="O" >
	<common:message key="uld.defaults.btn.btclose" />
	</ihtml:nbutton>
</div>
</div>

