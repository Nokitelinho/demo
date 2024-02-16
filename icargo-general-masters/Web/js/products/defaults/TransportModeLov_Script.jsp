<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

	function screenSpecificEventRegister(){
		callOnLoad();
	}

function screenSpecificBodyOnUnload(){
}

function selectAction(frm,strAction){
	frm.action=strAction;
	frm.submit();

}

function closeScreen(frm,strAction,actn,nextAction){
		if(actn=='OK'){
			if(validateSelectedCheckBoxes(frm,'transportModeChecked',25,1)){
				frm.action=strAction;
				frm.submit();
				disablePage();
			}
		}else{
			window.opener.document.forms[1].elements.lovAction.value ="";
			close();
		}

	}

	function callOnLoad(){
			window.opener.document.forms[1].elements.lovAction.value ="";
	}

	function callFun(frm,strAction){
		if(frm == "Y"){
			window.opener.recreateTableDetails(strAction);
			close();
		}


	}