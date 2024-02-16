<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister()
{

	with(targetFormName){


	evtHandler.addEvents("btnOk","ok()",EVT_CLICK);
	//evtHandler.addEvents("btClose","close()",EVT_CLICK);
	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
	}
onloadPopup();

}

function ok(){
	submitForm(targetFormName,'mailtracking.mra.airlinebilling.inward.airlineexceptions.remarksok.do');

}

function onloadPopup(){
var status=targetFormName.elements.screenStatus.value;

if(status=='ok'){
				window.close();

				window.opener.targetFormName.action="mailtracking.mra.airlinebilling.inward.airlineexceptions.accept.do";


				window.opener.targetFormName.submit();
}
}


function closeScreen(){


	window.close();

}

