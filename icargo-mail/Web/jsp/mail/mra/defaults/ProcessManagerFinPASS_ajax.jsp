
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>


<ihtml:form action="/mailtracking.mra.defaults.processmanager.finalizepass.do">


<bean:define id="form"
			     name="MRAProcessManagerForm"
			     type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProcessManagerForm"
			     toScope="page" />
				 
				 
<logic:present name="form" property="completionFlag">
<bean:define id="completionFlag" name="form" property="completionFlag" />
<div id="completionFlagAjax"><%=completionFlag%></div>
</logic:present>

<logic:present name="form" property="finalizedInvoiceCount">
<bean:define id="finalizedInvoiceCount" name="form" property="finalizedInvoiceCount" />
<div id="countOfInvoicesAjax"><%=finalizedInvoiceCount%></div>
</logic:present>


<logic:present name="form" property="nextFetchValue">
<bean:define id="nextFetchValue" name="form" property="nextFetchValue" />
<div id="ajaxNextFetch"><%=nextFetchValue%></div>
</logic:present>


<logic:present name="form" property="totalInvoiceCount">
<bean:define id="totalInvoiceCount" name="form" property="totalInvoiceCount" />
<div id="totalInvoicesAjax"><%=totalInvoiceCount%></div>
</logic:present>




</ihtml:form>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>

