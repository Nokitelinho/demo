<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=document.forms[0];

	with(frm){

		//evtHandler.addEvents("btnList","list()",EVT_CLICK);
		
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addEvents("btnList","list()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
		

	}
}
function list(){

	targetFormName.elements.lovActionType.value="dsnList";
	targetFormName.elements.displayPage.value="1";
	//alert("ss"+targetFormName.lovActionType.value);
submitForm(targetFormName,'mailtracking.mra.defaults.dsnlov.list.do');
}
function clear(){


submitForm(targetFormName,'mailtracking.mra.defaults.dsnlov.clear.do');
}


