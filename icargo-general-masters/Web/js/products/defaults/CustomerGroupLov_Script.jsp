<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

	function screenSpecificEventRegister(){
		var frm=targetFormName;
		with(frm){
		if(document.getElementsByName("customerGroupChecked").length>0){
			evtHandler.addEvents("selectAllCustomer","updateHeaderCheckBox(this.form,this,this.form.elements.customerGroupChecked)",EVT_CLICK);
			evtHandler.addEvents("customerGroupChecked","toggleTableHeaderCheckbox('customerGroupChecked',this.form.elements.selectAllCustomer)",EVT_CLICK);
			}
		}
		callOnLoad();
	}

	function screenSpecificBodyOnUnload(){
		self.opener.childWindow=null;
	}

	function submitAction(frm,strAction){
			frm.action=strAction;
			frm.submit();
	}
	function closeScreen(frm,strAction,actn,nextAction){
		if(actn=='OK'){
			if(validateSelectedCheckBoxes(frm,'customerGroupChecked',25,1)){
				frm.action=strAction;
				frm.submit();
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