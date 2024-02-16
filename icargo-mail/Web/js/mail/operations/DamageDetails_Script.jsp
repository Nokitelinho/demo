<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad();
   with(frm){

   	//CLICK Events
   	evtHandler.addEvents("btOk","okPopup(this.form)",EVT_CLICK);
   	evtHandler.addEvents("btCancel","cancelPopup(this.form)",EVT_CLICK);
	evtHandler.addIDEvents("addLink","addDamage()",EVT_CLICK);
	evtHandler.addIDEvents("deleteLink","deleteDamage()",EVT_CLICK);

	//evtHandler.addEvents("damageCheckAll","updateHeaderCheckBox(this.form,targetFormName.elements.damageCheckAll,targetFormName.elements.damageSubCheck)",EVT_CLICK);
	
	if(frm.damageSubCheck != null){
		evtHandler.addEvents("damageSubCheck","toggleTableHeaderCheckbox('damageSubCheck',targetFormName.elements.damageCheckAll)",EVT_CLICK);
	}

   	}
}

function onScreenLoad(){

	if(targetFormName.elements.selectedMailBag.value == "CLOSE"){
		targetFormName.elements.selectedMailBag.value = "";
		//window.opener.targetFormName.action = "mailtracking.defaults.mailacceptance.refreshacceptmail.do";
		//window.opener.recreateMultiTableDetails("mailtracking.defaults.mailacceptance.refreshacceptmail.do","","","ajaxUpdate");
		//window.opener.targetFormName.submit();
		window.opener.submitForm(window.opener.targetFormName,'mailtracking.defaults.mailacceptance.refreshacceptmail.do');
		window.closeNow();
	}
}

function addDamage(){

	submitForm(targetFormName,'mailtracking.defaults.damagedetails.add.do');
}

function deleteDamage(){

	if(validateSelectedCheckBoxes(targetFormName,'damageSubCheck','1000000000','1')){
	
	      submitForm(targetFormName,'mailtracking.defaults.damagedetails.delete.do');
	}
}

function okPopup(frm){
	window.opener.IC.util.common.childUnloadEventHandler();
	submitForm(targetFormName,'mailtracking.defaults.damagedetails.ok.do');
}

function cancelPopup(frm){

	submitForm(targetFormName,'mailtracking.defaults.damagedetails.cancel.do');
}