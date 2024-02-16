<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: ULDServiceabilityCodeMaster_Button.jsp
* Date				: 26-Aug-2010
* Author(s)			: A-2052
 --%>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import = "com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm" %>

		

<div class="ic-button-container paddR5">
	
	<logic:notEqual name="ULDServiceabilityForm" property="screenName" value="screenLoad">
		<ihtml:button property="btOk" componentID="CMP_ULD_DEF_OK_BTN">
			<common:message key="uld.defaults.btn.btok" />
		</ihtml:button>
	</logic:notEqual>
	<logic:equal name="ULDServiceabilityForm" property="screenName" value="screenLoad">
	<ihtml:button property="btSave" componentID="CMP_ULD_DEF_SAVE_BTN">
	<common:message key="uld.defaults.btn.btsave" />
	</ihtml:button>
	</logic:equal>
	<ihtml:button property="btClose" styleClass="btn-inline btn-secondary"  componentID="CMP_ULD_DEFAULTS_CLOSE_BTN">
	<common:message key="uld.defaults.btn.btclose" />
	</ihtml:button>
	
</div>

