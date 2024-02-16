<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister()
{

	var frm=targetFormName;
	with(frm){		
		evtHandler.addEvents("btClose","onClickClose(this.form)",EVT_CLICK);

	}
}


function onClickClose(frm){
window.close();
}