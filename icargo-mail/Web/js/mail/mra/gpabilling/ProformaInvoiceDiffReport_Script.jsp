<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{		
		onScreenLoad();	
		var frm = targetFormName;
			with(frm)
			{
			evtHandler.addEvents("btnPrint","print()",EVT_CLICK);
			evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
			evtHandler.addIDEvents("countryLOV","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.elements.country.value,'Country','0','country','',0)", EVT_CLICK);
			
		}
			
}
function onScreenLoad(){
	
}

function print(){

	var frm = targetFormName; 
		
	generateReport(targetFormName,"/mailtracking.mra.gpabilling.printproformainvoicediffreport.do");
	
	
}
function closeScreen(){

	submitForm(targetFormName,'mailtracking.mra.gpabilling.closeproformainvoicediffreport.do');

}