<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

	function screenSpecificEventRegister(){
		var frm=targetFormName;
			with(frm){
			evtHandler.addEvents("btnList","listfn()",EVT_CLICK);
			evtHandler.addEvents("btnClear","clearLOVForm()",EVT_CLICK);

			if(document.getElementsByName("mileStoneChecked").length>0){
				evtHandler.addEvents("checkbox5","updateHeaderCheckBox(this.form,this,this.form.elements.mileStoneChecked)",EVT_CLICK);
				evtHandler.addEvents("mileStoneChecked","toggleTableHeaderCheckbox('mileStoneChecked',this.form.elements.checkbox5)",EVT_CLICK);
			}
			}
		callOnLoad();
	}

	function screenSpecificBodyOnUnload(){
		self.opener.childWindow=null;
	}

	function closeScreen(frm,strAction,actn,nextAction){
		if(actn=='OK'){
			if(validateSelectedCheckBoxes(frm,'mileStoneChecked',25,1)){
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
			close()
		}


	}