
<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* Author(s)			: Yasheen
--%>

<%@ page language="java" %>
<%@ include file="/jsp/includes/tlds.jsp"%>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>	

<ihtml:form action="/uld.defaults.uldauditlist.do">



	<div id="_filter">
	
	<div class="ic-section" style="height:80%;">
		<div class="ic-input-round-border" style="height:100%;">
			<div class="ic-input ic-col-50">
				<label>
					<common:message key="uld.defaults.uldaudit.lbl.uldnumber"/><span class="iCargoMandatoryFieldIcon">*</span>
				</label>
				<ibusiness:uld id="uldno" uldProperty="uldNumber" componentID="CMP_ULD_Defaults_ULDAudit_ULDNumber" style="text-transform: uppercase"/>
			</div>
		</div>
	</div>
		
	<div class="ic-button-container">
				<ihtml:nbutton accesskey = "l" property="btList" componentID="CMP_ULD_Defaults_ULDAudit_List">
					<common:message key="uld.defaults.uldaudit.btn.audlist" />
				</ihtml:nbutton>

				<ihtml:nbutton accesskey = "c" property="btClear" componentID="CMP_ULD_Defaults_ULDAudit_Clear">
					<common:message key="uld.defaults.uldaudit.btn.audclear" />
				</ihtml:nbutton>
	</div>
		
		
	</div>
   </ihtml:form>
	
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

