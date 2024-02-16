<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/ux/tlds.jsp" %>

function screenSpecificEventRegister(){

IC.util.widget.createDataTable("lovTable",{tableId:"lovTable",hasChild:false,scrollingY:'39vh'
                                });

	with(targetFormName){

		evtHandler.addEvents("btnList","list()",EVT_CLICK);
		evtHandler.addEvents("btnClear","submitForm(this.form,'clearInvoiceLOV.do')",EVT_CLICK);
		evtHandler.addEvents("btnCancel","close()",EVT_CLICK);
		//evtHandler.addEvents("btnOK","triggerOkAction()",EVT_CLICK);
		evtHandler.addEvents("btnOk","setValue()",EVT_CLICK);
		
	}
}

function setValueOnDoubleClick(strFormCount, strCode, strDesc,strLovTxtFieldName, strLovDescriptionTxtFieldName,index){

	targetFormName.selectedValues.value = strCode;
	setValues(strFormCount, strLovTxtFieldName, strLovDescriptionTxtFieldName, index);
}
function setValues(strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,index){

	var currentSelectedValues = targetFormName.selectedValues.value;
	targetFormName.selectedValues.value=currentSelectedValues;
	var options = "";
	var newValue="";
	var concatenatedValue=currentSelectedValues;
	var strCodeArray = "";
	if(strLovTxtFieldName != null && strLovTxtFieldName.length>0){
		  strCodeArray=window.parent.document.getElementsByName(strLovTxtFieldName);
	}
	var frm = targetFormName;
	for(var i=0; i < frm.elements.length; i++){
		if(frm.elements[i].type=='checkbox'){
			if(frm.elements[i].name=='selectCheckBox'){
				if(frm.elements[i].checked == true){
					if(newValue==''){
						newValue = frm.elements[i].value;
					}else{
						newValue = newValue+','+frm.elements[i].value;
					}
				}
			}
		}
	}
		if(concatenatedValue==''){
			concatenatedValue=newValue;
		}
		var codeAndValue=concatenatedValue.split("&");
			if(concatenatedValue == ""){
				codeAndValue[0] = "";			
			}
			if(index != null){
				for(var i=0; i<strCodeArray.length ; i++){
					if(i ==  index){
							if(strCodeArray[i] != null)
							strCodeArray[i].value=codeAndValue[0];													
					}
				}
			}else{
					if(strCodeArray != null)
					strCodeArray.value=codeAndValue[0];			
			}
	window.close();
}
function setValue(){

targetFormName.elements.lovaction.value="OK";
	var optionsArray = {
		codeFldNameInScrn:targetFormName.elements.lovTxtFieldName.value,
		descriptionFldNameInScrn:targetFormName.elements.lovDescriptionTxtFieldName.value,	
		index:targetFormName.elements.index.value,	
		isMultiSelect:targetFormName.elements.multiselect.value,
		delimitterStr:'&'
	}	
	lovUtils.setValueForDifferentModes(optionsArray);
	if(targetFormName.elements.lovaction.value=="OK"){
	
		window.parent.IC.util.common.childUnloadEventHandler();
		window.parent.document.forms[1].submit();
	    popupUtils.closePopupDialog();
		}
}



function list(){
targetFormName.elements.lastPageNum.value="0";
targetFormName.elements.displayPage.value="1";

submitForm(targetFormName,'showNewInvoiceLOV.do');
}

function close(){
window.parent.IC.util.common.childUnloadEventHandler();
		window.parent.document.forms[1].submit();
	    popupUtils.closePopupDialog();

}

function submitPage(lastPageNum,displayPage){

	targetFormName.elements.lastPageNum.value=lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
	 targetFormName.elements.pagination.value = 'Y';
	submitForm(targetFormName, "showNewInvoiceLOV.do");

}
function showEntriesReloading(obj){

	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	var action = "showNewInvoiceLOV.do";     
	submitForm(targetFormName, action);
	}