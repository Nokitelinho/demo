<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
   		onScreenLoad();
		evtHandler.addEvents("btnClose","onClickClose()",EVT_CLICK);
		addIEonScroll();
   		DivSetVisible(true);
		}
}

function onScreenLoad(){
		//alert('inside screenLoad');
		var frm=targetFormName;
}


function onClickClose(frm){
window.close();
}