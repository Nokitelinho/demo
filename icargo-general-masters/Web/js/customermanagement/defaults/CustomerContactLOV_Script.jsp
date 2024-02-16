<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){
   	var frm=document.forms[0];
   	with(frm){
   		evtHandler.addEvents("btOk","submitFrm(this.form)",EVT_CLICK);
   		evtHandler.addEvents("btClose","window.close();",EVT_CLICK);
   	}
}


function submitFrm(frm){
	if(validateSelectedCheckBoxes(frm,'suChecked',1,1)){
		var textfiledObj=frm.elements.textfiledObj.value;
		var formNumber=frm.elements.formNumber.value;
		var textfiledDesc=frm.elements.textfiledDesc.value;
		var str="";
		var array="";
		var desc="";
		var index=frm.elements.rowCount.value;
		
		for(var i=0;i<document.forms[0].elements.length;i++){
			if(document.forms[0].elements[i].type =='checkbox'){
				if(document.forms[0].elements[i].checked){
					array=document.forms[0].elements[i].value.split(",");
					str =array[0];
					desc =array[1];			
				}
			}
		}
           	var objCode = eval("window.opener.document.forms["+formNumber+"]."+textfiledObj);          
           	objCode.value=str;
           	if(textfiledDesc!=""){
           		var objDesc = eval("window.opener.document.forms["+formNumber+"]."+textfiledDesc);
           		objDesc.value=desc;
           	}
           	window.close();
           	self.opener.childWindow=null;
	}
}


function submitList(strLastPageNum,strDisplayPage){
	document.forms[0].elements.lastPageNum.value= strLastPageNum;
	document.forms[0].elements.displayPage.value = strDisplayPage;
	document.forms[0].action ="customermanagement.defaults.customerlisting.screenloadcustcontactlov.do";
	document.forms[0].submit();
	disablePage();
}

