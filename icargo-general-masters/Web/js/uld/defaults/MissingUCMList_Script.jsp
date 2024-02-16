<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{

	var frm = targetFormName;
	if((!frm.elements.carrierCode.disabled)&&(!frm.elements.carrierCode.readOnly)){
	frm.elements.carrierCode.focus();
	}
	with(frm)
	{
		evtHandler.addEvents("btClose","closeFun(this.frm)",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus(this.form)",EVT_BLUR);
		evtHandler.addEvents("btList","listEvent()",EVT_CLICK);
		evtHandler.addEvents("btClear","clearEvent()",EVT_CLICK);
	}
 applySortOnTable("missingUCMTable",new Array("String","Date","String","None","String","None")); 
}

function resetFocus(frm){
	 if(!event.shiftKey){ 
				if((!frm.elements.carrierCode.disabled)  
				 &&	(frm.elements.carrierCode.readOnly == false)){
					frm.elements.carrierCode.focus();
				}								
	}	
}

function closeFun(frm)
{
	location.href = appPath+"/home.jsp";
}
function listEvent()
{
	targetFormName.elements.lastPageNum.value=0;
	targetFormName.elements.displayPage.value=1;
	submitForm(targetFormName, 'uld.defaults.listmissingucms.do');

}
function clearEvent()
{

	submitForm(targetFormName, 'uld.defaults.clearmissingucms.do');
	//submitFormWithUnsaveCheck('uld.defaults.clearmissingucms.do');
}

function submitList(strLastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	submitForm(targetFormName, 'uld.defaults.listmissingucms.do');
}