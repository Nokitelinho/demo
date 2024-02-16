<%@ include file="/jsp/includes/js_contenttype.jsp" %>



function screenSpecificEventRegister(){
   		var frm = document.forms[0];
   		with(frm){
   		
   		evtHandler.addEvents("btnPicClose","onClickClose(this.form)",EVT_CLICK);
   		
   		}
   	}

function onClickClose(frm){
	window.close();
}