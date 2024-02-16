<%--
* Project	 		: iCargo
* Module Code & Name: Reco
* File Name			: MaintainEmbargoLanguageAjaxTemp.jsp
* Date				: 11/09/2017
* Author(s)			: A-7815
--%>
<%@ include file="/jsp/includes/tlds.jsp" %>

 <%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
 <head>
	<%@page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
 </head>
  <ihtml:form action="reco.defaults.maintainscreenload.do">
	<bean:define 
			id="form" 
			name="MaintainEmbargoRulesForm" 
			toScope="page" 
			type="com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm"/>
	<div id="embargoDesc">
		<bean:write name="form" property="currentEmbargoDesc" />
	</div>
  </ihtml:form >
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>