<%--
* Project	 		: iCargo
* Module Code & Name: ULD
* File Name			: PopulateDamageDesc_Ajax.jsp
* Date				: 13/03/2018
* Author(s)			: A-8176
--%>
<%@ page language="java" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

<%@ include file="/jsp/includes/ajaxPageHeader.jsp" %>

   <business:sessionBean id="damageDescCodes" moduleName="uld.defaults"
 	  screenID="uld.defaults.updatemultipleulddetails" method="get"
 	  attribute="ULDDamageChecklistVO" />
 	
 	 <bean:define id="dmgDescCodesOnetime" name="damageDescCodes"  />
    
<div id="_ajBillingPeriods">
		
	<logic:iterate id="vo" name="dmgDescCodesOnetime">
		<bean:define id="defaults" name="vo" property="description" />
		<div dmgDescCode="<%=(String)defaults%>"></div>
	</logic:iterate>

</div>
<%@ include file="/jsp/includes/ajaxPageFooter.jsp" %> 

