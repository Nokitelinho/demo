<%@ page import="com.ibsplc.xibase.util.time.TimeConvertor"%>
<%@ page import="com.ibsplc.icargo.framework.util.time.LocalDate"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>
<bean:define id="form"
	name="DespatchRoutingForm"
	type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchRoutingForm"
	toScope="request" />
<ihtml:form action="/mailtracking.mra.defaults.despatchrouting.screenLoad.do" styleClass="ic-main-form">
<ihtml:hidden  property="bsaValidationStatus" value="<%=form.getBsaValidationStatus()%>"/>
</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>
