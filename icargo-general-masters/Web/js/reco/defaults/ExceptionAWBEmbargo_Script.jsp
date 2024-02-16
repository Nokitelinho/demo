<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
var frm=targetFormName;
	
  with(frm){
		evtHandler.addEvents("btList","sub('List')",EVT_CLICK);
		evtHandler.addEvents("btClear","sub('Clear')",EVT_CLICK);
   	evtHandler.addEvents("btnClose","closeException()",EVT_CLICK); 
		evtHandler.addEvents("btSave","saveException()",EVT_CLICK); 	
		evtHandler.addIDEvents("add","addTemplateRow('exceptionTemplateRow','exceptionTableBody','hiddenOpFlag')",EVT_CLICK);
		evtHandler.addIDEvents("delete","deleteTableRow('check','hiddenOpFlag')",EVT_CLICK);
		evtHandler.addEvents("startDate","operationFlagChangeOnEdit(this,'hiddenOpFlag')",EVT_BLUR);
		evtHandler.addEvents("endDate","operationFlagChangeOnEdit(this,'hiddenOpFlag')",EVT_BLUR);
		evtHandler.addEvents("remarks","operationFlagChangeOnEdit(this,'hiddenOpFlag')",EVT_BLUR);
   }
   	//disableListFields(frm);
   setFocus(frm);
}

function setFocus(frm){
		frm.elements.shipmentPrefixFilter.focus();
}
function closeException(){
	submitFormWithUnsaveCheck(appPath + "/home.jsp");
}
function submitPage(lastPg,displayPg){
	var frm=targetFormName;
	frm.elements.pageNumber.value=lastPg;
	frm.elements.displayPage.value=displayPg;
	submitForm(frm,'reco.defaults.expawbemblist.do?navigationMode=Y');

}

function screenConfirmMessage(frm,dialogId){
	while(frm.elements.currentDialogId.value == ''){


		}

		if(frm.elements.currentDialogOption.value == 'Y') {
			if(dialogId == 'id_1'){
				frm.action = "home.do";
				frm.submit();
			}
	}
}
function screenNonConfirmMessage(frm,dialogId) {
	while(frm.elements.currentDialogId.value == ''){

		}

		if(frm.elements.currentDialogOption.value == 'N') {
			if(dialogId == 'id_1'){

			}
	}
}

function saveException(){
	submitForm(targetFormName,'reco.defaults.expawbembsave.do');
}

function doCheckAll(frm)
{
var chk = document.getElementsByName("check");
	if(targetFormName.elements.checkAllBox.checked == true)
	{
		for(var i=0; i<chk.length; i++)
		{
			chk[i].checked = true;
		}
	}
	if(targetFormName.elements.checkAllBox.checked == false)
	{
		for(var i=0; i<chk.length; i++)
		{
			chk[i].checked = false;
		}
	}
}

function sub(action){
	if(action == 'List'){
		targetFormName.elements.pageNumber.value="";
		targetFormName.elements.displayPage.value="1";
		submitForm(targetFormName,'reco.defaults.expawbemblist.do');
	}
	else if(action == 'Clear')
	{
		submitForm(targetFormName,'reco.defaults.clearexception.do');
	}
}


function disableListFields(frm) {
	var frm = targetFormName;
	var hidden = document.getElementsByName('shipmentPrefix');
	for(var i=0;i<hidden.length;i++) {
		if(frm.elements.hiddenOpFlag[i].value!='NOOP')
		{
			disableField(frm.elements.shipmentPrefix[i]);
			disableField(frm.elements.masterDocumentNumber[i]);
	}
	
	}
	
}