<%--/***********************************************************************
* Project	     	 : iCargo
* Module Code & Name 	 : mra
* File Name          	 : ListBillingMatrix_Ajax.jsp
* Date                 	 : 13-July-2008
* Author(s)              : A-3108
*************************************************************************/
--%>


<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>


<bean:define id="form"
	     name="ListBillingMatrixForm"
	     type="com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingMatrixForm"
  		  toScope="page" />

	
<ihtml:form action="/mailtracking.mra.defaults.listbillingmatrix.populategpaname.do">

<div id="ajaxDiv">
 <label><common:message key="mailtracking.mra.defaults.listbillingmatrix.poaname" /></label>
<ihtml:text property="poaName" readonly="true" componentID="CMP_MRA_DEFAULTS_LISTBILLINGMATRIX_PANAME" style="text-transform : uppercase"  />
</div>
  							  
 </ihtml:form>
 <%@ include file="/jsp/includes/ajaxPageFooter.jsp" %>