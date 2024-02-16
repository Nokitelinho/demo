<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister(){

	var frm=targetFormName;
	if(frm.code.disabled==false){
			frm.code.focus();
		}
	with(frm){
		window.ounload = self.opener.childWindow=null;
		evtHandler.addEvents("btClose","closelov()",EVT_CLICK);
		evtHandler.addEvents("btList","onClickButton('List')",EVT_CLICK);
		evtHandler.addEvents("btClear","onClickButton('Clear')",EVT_CLICK);
	}
}

function onClickButton(buttonName){
	if( buttonName == 'List'){
			targetFormName.elements.displayPage.value=1;
			targetFormName.elements.lastPageNum.value=0;
			submitForm(targetFormName,'uld.defaults.transaction.finduldnumberlov.do');
	}else if( buttonName == 'Clear'){
			submitFormWithUnsaveCheck('uld.defaults.uldnumberlovclear.do');
	}
}
function closelov() {
	self.close();
}
function callFunction(lastPg,displayPg){
	targetFormName.elements.lastPageNum.value = lastPg;
	targetFormName.elements.displayPage.value = displayPg;

	var frm=targetFormName;
	var action = "uld.defaults.transaction.finduldnumberlov.do";
	submitForm(frm, action);
}