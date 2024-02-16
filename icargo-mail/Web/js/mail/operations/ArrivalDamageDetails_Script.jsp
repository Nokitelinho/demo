<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
   var frm=targetFormName;
   onScreenLoad();
   
   with(frm){
		//CLICK Events
		evtHandler.addEvents("btOk","okPopup(this.form)",EVT_CLICK);
		evtHandler.addEvents("btCancel","cancelPopup(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("addLink","addDamage()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deleteDamage()",EVT_CLICK);
		evtHandler.addEvents("damageCheckAll","updateHeaderCheckBox(this.form,targetFormName.damageCheckAll,targetFormName.damageSubCheck)",EVT_CLICK);
		if(frm.damageSubCheck != null){
			evtHandler.addEvents("damageSubCheck","toggleTableHeaderCheckbox('damageSubCheck',targetFormName.damageCheckAll)",EVT_CLICK);
		}
   	}
}

function onScreenLoad(){

	if(targetFormName.elements.selectedMailBag.value == "CLOSE"){
		targetFormName.elements.selectedMailBag.value = "";
		window.opener.submitForm(window.opener.targetFormName,'mailtracking.defaults.mailarrival.refresharrivemail.do');
		window.closeNow();
	}
}

function addDamage(){
	submitForm(targetFormName,'mailtracking.defaults.arrivaldamagedetails.add.do');
}

function deleteDamage(){

	if(validateSelectedCheckBoxes(targetFormName,'damageSubCheck','1000000000','1')){	
		submitForm(targetFormName,'mailtracking.defaults.arrivaldamagedetails.delete.do');
	}
}

function okPopup(frm){
	submitForm(targetFormName,'mailtracking.defaults.arrivaldamagedetails.ok.do');
}

function cancelPopup(frm){
	submitForm(targetFormName,'mailtracking.defaults.arrivaldamagedetails.cancel.do');
}