<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

	function screenSpecificEventRegister(){
		var frm=targetFormName;
		with(frm){
		if(document.getElementsByName("priorityChecked").length>0){
			evtHandler.addEvents("selectAllDetails","updateHeaderCheckBox(this.form,this.form.elements.selectAllDetails,this.form.elements.priorityChecked)",EVT_CLICK);
			evtHandler.addEvents("priorityChecked","toggleTableHeaderCheckbox('priorityChecked',this.form.elements.selectAllDetails)",EVT_CLICK);
		}
		}
		callOnLoad();
	}


	function screenSpecificBodyOnUnload(){
	}

	function submitAction(frm,strAction){
			frm.action=strAction;
			frm.submit();
			disablePage();
	}


	function closeScreen(frm,strAction,actn,nextAction){

		if(actn=='OK'){
			if(validateSelectedCheckBoxes(frm,'priorityChecked',25,1)){
					if(frm.elements.source.value != "OFFERCONDITION" && frm.elements.source.value != "fromBCTLScreen"){		
				frm.action=strAction;
				frm.submit();
				disablePage();
							
		}else{
						setValueForDifferentModes(targetFormName.elements.multiselect.value,targetFormName.elements.formCount.value,targetFormName.elements.lovTxtFieldName.value,targetFormName.elements.lovDescriptionTxtFieldName.value,targetFormName.elements.index.value);	
					}
			}
		}else{
			window.opener.document.forms[0].elements.lovAction.value ="";
			close();
		}

	}


	function callOnLoad(){
			window.opener.document.forms[0].elements.lovAction.value ="";
	}

	function callFun(frm,strAction){

		if(frm == "Y"){
			window.opener.recreateTableDetails(strAction);
			close()
		}


	}