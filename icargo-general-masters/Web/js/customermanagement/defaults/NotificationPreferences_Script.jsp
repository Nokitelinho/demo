<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@include file="/jsp/includes/tlds.jsp"%>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
		evtHandler.addEvents("btnOk","okCommand()",EVT_CLICK);
		evtHandler.addEvents("btnClose","okClose()",EVT_CLICK);
		}
	reloadParent();
}

function okClose(){	
		IC.util.common.childUnloadEventHandler();
	window.close();
	}
function childWindowCloseEvent(){

	okClose();
	}
function okCommand(){
		getCheckBoxValues();
		var selectedContactIndex = targetFormName.selectedContactIndex.value;
		var action="customermanagement.defaults.okNotificationPreferences.do?selectedContactIndex="+selectedContactIndex;
		submitForm(targetFormName, action);
}

function reloadParent(){
	var frm = targetFormName;
	if(frm.elements.reloadParent.value == "true"){
		window.close();
	}
}
	
function getCheckBoxValues(){

	var emailFlag = document.getElementsByName('emailCheck');
	var mobileFlag = document.getElementsByName('mobileCheck');
	var faxFlag = document.getElementsByName('faxCheck');
	
	var emailFlagStr ="";
	var mobileFlagStr = "";
	var faxFlagStr = "";
	
	/* for(var i=0; i<targetFormName.elements.emailFlag.length; i++){
			if(targetFormName.elements.emailFlag[i].checked){
			      targetFormName.elements.emailFlag[i].value='Y';
			}else{
			     targetFormName.elements.emailFlag[i].value='N';
			}
		}
		for(var i=0; i<targetFormName.elements.mobileFlag.length; i++){
			if(targetFormName.elements.mobileFlag[i].checked){
			      targetFormName.elements.mobileFlag[i].value='Y';
			}else{
			     targetFormName.elements.mobileFlag[i].value='N';
			}
		}
		for(var i=0; i<targetFormName.elements.faxFlag.length; i++){
			if(targetFormName.elements.faxFlag[i].checked){
			      targetFormName.elements.faxFlag[i].value='Y';
			}else{
			     targetFormName.elements.faxFlag[i].value='N';
			}
		} */
	
	
	if(targetFormName.elements.emailCheck){
		for(var i=0; i<emailFlag.length; i++){
			if(emailFlag[i].checked){
				emailFlagStr=emailFlagStr.concat("-Y");
			}else{
				emailFlagStr=emailFlagStr.concat("-N");
			}
		}
		targetFormName.elements.emailFlag.value=emailFlagStr;
	}	
	
	if(targetFormName.elements.mobileCheck){
		for(var i=0; i<mobileFlag.length; i++){
			if(mobileFlag[i].checked){
				mobileFlagStr=mobileFlagStr.concat("-Y");
			}else{
				mobileFlagStr=mobileFlagStr.concat("-N");
			}
		}
		targetFormName.elements.mobileFlag.value=mobileFlagStr;
	}
	
	if(targetFormName.elements.faxCheck){
		for(var i=0; i<faxFlag.length; i++){
			if(faxFlag[i].checked){
				faxFlagStr=faxFlagStr.concat("-Y");
			}else{
				faxFlagStr=faxFlagStr.concat("-N");
			}
		}
		targetFormName.elements.faxFlag.value=faxFlagStr;
	} 
}