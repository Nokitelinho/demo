<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister()
{

	with(targetFormName){
	evtHandler.addEvents("btList","listSurchargeDeatils()",EVT_CLICK);
	evtHandler.addEvents("btnClear","clearscreen()",EVT_CLICK);
	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
	}
	screenLoad();
}
function screenLoad(){
	if("viewproration"==targetFormName.elements.fromAction.value&&""!=targetFormName.elements.sector.value){
	submitForm(targetFormName,'mailtracking.mra.defaults.viewproration.surchargedetails.do?fromAction=List');
	}
}
function listSurchargeDeatils(){

	submitForm(targetFormName,'mailtracking.mra.defaults.viewproration.surchargedetails.do?fromAction=List');
}
function clearscreen(){
	submitForm(targetFormName,'mailtracking.mra.defaults.viewproration.surchargedetails.do?fromAction=Clear');
}
function closeScreen(){
	window.close();
}

