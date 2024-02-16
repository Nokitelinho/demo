<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

	function screenSpecificEventRegister(){
		var frm=targetFormName;
		with(frm){
			evtHandler.addEvents("btnList","listfn()",EVT_CLICK);
			evtHandler.addEvents("btnClear","clearLOVForm()",EVT_CLICK);
		}
		callOnLoad();
	}

	function screenSpecificBodyOnUnload(){
		self.opener.childWindow=null;
	}

    function listfn(){
    	var frm = targetFormName;
		frm.elements.displayPage.value="1";
		//Added by A-5220 for ICRD-32647 starts
		frm.elements.lastPageNumber.value="0";		
		//Added by A-5220 for ICRD-32647 ends
		submitForm(frm,'products.defaults.screenloadCommodityLov.do');
	}

	function submitAction(frm,strAction){
				submitForm(frm,strAction);
		}

		function closeScreen(frm,strAction,actn,nextAction){

			if(actn=='OK'){
				if(validateSelectedCheckBoxes(frm,'commodityChecked',25,1)){
					//submitForm(frm,strAction);
					frm.action=strAction;
					frm.submit();
				}
			}else{
				window.opener.targetFormName.elements.lovAction.value ="";
				close();
			}

		}

			function preserveSelectedvalues(strlastPageNum,strDisplayPage)
			{
				var currentSelectedValues = document.forms[0].elements.selectedValues.value;

				var str = "";
				for(var i=0;i<document.forms[0].elements.length;i++)
				{
					if(document.forms[0].elements[i].type =='checkbox')
					{
						if(document.forms[0].elements[i].checked)
						{
							var val = document.forms[0].elements[i].value;
							if(currentSelectedValues.indexOf(val) == -1 && currentSelectedValues != "")
								currentSelectedValues = currentSelectedValues+","+val;
							else if(currentSelectedValues.indexOf(val) == -1 && currentSelectedValues == ""){
								currentSelectedValues = val;
								}
						}
					}
				}
				document.forms[0].elements.selectedValues.value=currentSelectedValues;
				document.forms[0].elements.lastPageNumber.value=strlastPageNum;
				document.forms[0].elements.displayPage.value=strDisplayPage;
				document.forms[0].action='products.defaults.screenloadCommodityLov.do';
				document.forms[0].submit();
	}

	function callOnLoad(){
			window.opener.document.forms[1].elements.lovAction.value ="";
			document.forms[0].elements.code.focus();
	}

	function callFun(frm,strAction){
		if(frm == "Y"){
			window.opener.recreateTableDetails(strAction);
			close();
		}
	}