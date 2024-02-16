<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ page import="org.apache.struts.action.ActionMessages"%>
<%@ page import="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"%>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

<html:html>
	<ihtml:form action="reco.defaults.maintainscreenload.do">

		<div id="descriptionTable_Temp">	
			
			<ihtml:textarea property="embargoDesc" maxlength="4000" 
					style="width:250px;height:70px;" 
					componentID="CMP_Reco_Defaults_MaintainEmbargo_EmbargoDescription">
			</ihtml:textarea>
			<a href="#" class="iCargoLink" id="moreLink" onclick="prepareAttributesForDesc(this,'moreDescdiv','moreDescdiv')">
				<common:message key="maintainembargo.moredescription" />
			</a>
												
		</div>						
							
	</ihtml:form>
</html:html>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
