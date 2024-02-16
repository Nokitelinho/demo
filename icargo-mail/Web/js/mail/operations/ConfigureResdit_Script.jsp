<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister(){

   with(targetFormName){

		evtHandler.addEvents("btList","listDetails()",EVT_CLICK);
		evtHandler.addEvents("btClear","clearDetails()",EVT_CLICK);
		evtHandler.addEvents("btClose","closeDetails()",EVT_CLICK);
		evtHandler.addEvents("btClose","resetFocus()",EVT_BLUR);
		evtHandler.addEvents("btSave","saveResdit()",EVT_CLICK);
		evtHandler.addIDEvents("airlineLOV","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airline.value,'Airline Code','1','airline','',0)", EVT_CLICK);
   	}
   	onScreenLoad();

}

function onScreenLoad(){

	disableDetails();
	setFocus();

}

function resetFocus(){
	 if(!event.shiftKey){ 
		if((targetFormName.elements.airline.disabled == false) && (!targetFormName.elements.airline.readOnly)){
			targetFormName.elements.airline.focus();
		}
		else if(targetFormName.elements.receivedResditFlag[0].disabled == false){
			targetFormName.elements.receivedResditFlag[0].focus();
		}	
	}	
}

function setFocus(){
	if((targetFormName.elements.airline.disabled == false) && (!targetFormName.elements.airline.readOnly)){
		targetFormName.elements.airline.focus();
	}
	else if(targetFormName.elements.receivedResditFlag[0].disabled == false) {
		targetFormName.elements.receivedResditFlag[0].focus();
	}

}


function disableDetails(){

	if(targetFormName.elements.airline.disabled == true) {
		targetFormName.elements.airlineLOV.disabled = true;
	}

}



function listDetails(){

   submitForm(targetFormName,'mailtracking.defaults.configureresdit.listconfigureresdit.do');
}

function clearDetails(){

	submitForm(targetFormName,'mailtracking.defaults.configureresdit.clearconfigureresdit.do');
}

function closeDetails(){
	location.href = appPath + "/home.jsp";
}

function saveResdit(){
	targetFormName.elements.reserved.value = getResditFlag('receivedResditFlag');
	targetFormName.elements.assigned.value = getResditFlag('assignedResditFlag');
	targetFormName.elements.uplifted.value = getResditFlag('upliftedResditFlag');
	targetFormName.elements.handedover.value = getResditFlag('handedOverResditFlag');
	targetFormName.elements.handedoverRecieved.value = getResditFlag('handedOverReceivedResditFlag');
	targetFormName.elements.departed.value = getResditFlag('loadedResditFlag');
	targetFormName.elements.delivered.value = getResditFlag('deliveredResditFlag');
	targetFormName.elements.readyForDelivery.value = getResditFlag('readyForDeliveryFlag');
	targetFormName.elements.transportCompleted.value = getResditFlag('transportationCompletedResditFlag');
	targetFormName.elements.mailArrived.value = getResditFlag('arrivedResditFlag');
	targetFormName.elements.returned.value = getResditFlag('returnedResditFlag');
	submitForm(targetFormName,'mailtracking.defaults.configureresdit.saveconfigureresdit.do');

}

function getResditFlag(objName){
	var checkObjects = document.getElementsByName(objName);
	var checkFlag = "";
	for(var i = 0; i < checkObjects.length; i++) {
			if(checkFlag == "") {
				if(checkObjects[i].checked) {
					checkFlag = "Y";
				}
				else {
					checkFlag = "N";
				}
			}
			else {
				if(checkObjects[i].checked) {
					checkFlag = checkFlag + ",Y";
				}
				else {
					checkFlag = checkFlag + ",N";;
				}
			}

	}
	return checkFlag;
}