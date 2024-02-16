<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister(){
debugger;
				evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
				}
function closeScreen(){
window.close();
}

function screenSpecificTabSetup(){
   setupPanes('container1','tab1');
   displayTabPane('container1','tab1');
}

