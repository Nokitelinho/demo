<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	//onLoadSetHeight();
	with(frm){
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);

	}
	frm.elements.btnClose.focus();
	viewDetailsOnDblClick();
//	DivSetVisible(true);

}
function onLoadSetHeight(){
alert();
	var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	var MainDivHeight=((height*95)/100);
	document.getElementById('mainDiv').style.height = MainDivHeight+'px';
	//document.getElementById('stockDetailsTableDiv').style.height = (MainDivHeight-100)+'px';
			
}

function viewDetailsOnDblClick(){
	return;
}