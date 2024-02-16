<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=document.forms[0];
	with(frm){
		evtHandler.addEvents("btnList","callList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","submitForm(this.form,'mra.airlinebilling.defaults.clearInvoiceLov.do')",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
	}
}
function submitList(strLastPageNum,strDisplayPage){
	document.forms[0].lastPageNum.value= strLastPageNum;
	document.forms[0].displayPage.value = strDisplayPage;
	document.forms[0].submit();
	disablePage();
}


function setValueofInvoiceNoAndClearancePrd(multiselect,strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,strLovNameTxtFieldName,strLovDateField,arrayIndex,strCode,strDesc,strName,strDate){
	
	var isMultiselect=eval("window.opener.targetFormName."+multiselect);

	if(multiselect=='Y'){
		setMultipleValues(strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,arrayIndex);
	}
	else{
		

	setValueOfInvoiceNoAndClrPrd(strCode,strDesc,strName,strDate,strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,strLovNameTxtFieldName,strLovDateField);
	}

}

/**
 * This function sets the selected row code+desc+name into the parent text box and closes the LOV pop up.
 *
 * @param strCode - The selected code
 * @param strDesc - The selected description
 * @param strName - The selected name
 * @param strFormCount -  The form number correspinding to the text field for code and description in the parent.
 * @param strLovTxtFieldName - The name of the LOV Code text field in the parent.
 * @param strLovDescriptionTxtFieldName - The name of the LOV Description text field in the parent.
 * @param strLovNameTxtFieldName - The name of the LOV Name text field in the parent.
 */
function setValueOfInvoiceNoAndClrPrd(strCode,strDesc,strName,strDate,strFormCount,strLovTxtFieldName,strLovNameTxtFieldName,strLovDescriptionTxtFieldName,strLovDateField)
{
	
    var concatenatedValue=targetFormName.elements.selectedValues.value;
	//Modified as part of Bug ICRD-100930 by A-5526.Done the required changes in jsp also
    var codeAndValue=concatenatedValue.split("~");

	var objCode = "";
	var objDesc= "";
	var objName= "";
	var objDate= "";

		if(strLovTxtFieldName != null && strLovTxtFieldName.length>0){
			  objCode = eval("window.opener.targetFormName."+strLovTxtFieldName);
		}


		if(strLovDescriptionTxtFieldName != null && strLovDescriptionTxtFieldName.length>0){
			objDesc = eval("window.opener.targetFormName."+strLovDescriptionTxtFieldName);
		}


		if(strLovNameTxtFieldName != null && strLovNameTxtFieldName.length>0){
			objName = eval("window.opener.targetFormName."+strLovNameTxtFieldName);
		}
        if(strLovDateField != null && strLovDateField.length>0){
			objDate = eval("window.opener.targetFormName."+strLovDateField);
		}


    /*var objCode = eval("window.opener.document.forms["+strFormCount+"]."+strLovTxtFieldName);
    var objDesc = eval("window.opener.document.forms["+strFormCount+"]."+strLovDescriptionTxtFieldName);
    var objName = eval("window.opener.document.forms["+strFormCount+"]."+strLovNameTxtFieldName);
*/

	var strCodeArray = "";
	var strDescArray = "";
	var strNameArray = "";
	
var strDateArray = "";
		if(strLovTxtFieldName != null && strLovTxtFieldName.length>0){
			  strCodeArray=window.opener.document.getElementsByName(strLovTxtFieldName);
		}

		if(strLovDescriptionTxtFieldName != null && strLovDescriptionTxtFieldName.length>0){
			 strDescArray=window.opener.document.getElementsByName(strLovDescriptionTxtFieldName);
		}

		if(strLovNameTxtFieldName != null && strLovNameTxtFieldName.length>0){
			  strNameArray=window.opener.document.getElementsByName(strLovNameTxtFieldName);
		}
        if(strLovDateField != null && strLovDateField.length>0){
			  strDateArray=window.opener.document.getElementsByName(strLovDateField);
		}

/*
    var strCodeArray=window.opener.document.getElementsByName(strLovTxtFieldName);
    var strDescArray=window.opener.document.getElementsByName(strLovDescriptionTxtFieldName);
    var strNameArray=window.opener.document.getElementsByName(strLovNameTxtFieldName);
*/


    var args = setValueOfInvoiceNoAndClrPrd.arguments;
    var index = args[9];
    if(index != null){
	if(index != 0){
		for(var i=0;i<=strCodeArray.length;i++){

			if(i==index){
				 strCodeArray[index].value = codeAndValue[0];
				 if(strLovDescriptionTxtFieldName != null && strLovDescriptionTxtFieldName.length>0){
				 	strDescArray[index].value = codeAndValue[1];
				 }
				 if(strLovNameTxtFieldName != null && strLovNameTxtFieldName.length>0){
				 	 strNameArray[index].value = codeAndValue[2];
				 }
				 if(strLovDateField != null && strLovDateField.length>0){
				 	 strDateArray[index].value = codeAndValue[3];
				 }

			}
		}
	}else {
		 strCodeArray[0].value=codeAndValue[0];
		}
		if(strLovDescriptionTxtFieldName !=""){
			if(index!=0){
				for(var i=0;i<=strDescArray.length;i++){
					if(i==index){
						 strDescArray[index].value=codeAndValue[1];
					  }
				}
			}
			else {
			 	strDescArray[0].value=codeAndValue[1];
			}
		}
		if(strLovNameTxtFieldName !=""){
			if(index!=0){
				for(var i=0;i<=strNameArray.length;i++){
					if(i==index){
						 strNameArray[index].value=codeAndValue[2];
					  }
				}
			}
			else {
				strNameArray[0].value=codeAndValue[2];
			}
		}
		if(strLovDateField !=""){
			if(index!=0){
				for(var i=0;i<=strDateArray.length;i++){
					if(i==index){
						 strDateArray[index].value=codeAndValue[3];
					  }
				}
			}
			else {
				strDateArray[0].value=codeAndValue[3];
			}
		}
		
	}
	
	
	
	else{
		objCode.value = codeAndValue[0];

		if(strLovDescriptionTxtFieldName !=""&& strDescArray.length>0){
			objDesc.value=codeAndValue[1];
		}
		if(strLovNameTxtFieldName !=""){
			objName.value =codeAndValue[2];
		}
		if(strLovDateField !=""){
			objDate.value =codeAndValue[3];
		}
	}

	if(window.opener){

		if(window._parentOkFnHook!=null && window._parentOkFnHook!="" ){
			try{
			eval("window.opener."+window._parentOkFnHook);
			}catch(e){
				//TODO
			}
		}

		else if(window.opener._lovOkFnHook!=null && window.opener._lovOkFnHook!=""){
					try{
						eval("window.opener."+window.opener._lovOkFnHook);
					}catch(e){
					//TODO
						}
		}
	}
 
   	window.close();
}




//for populating the Name,Description as well as the Address Fields in the parent
function setAllValuesOnDoubleClick(strCode,strDesc,strAddr,strLovTxtFieldName,strLovaddressTxtFieldName,strLovDescriptionTxtFieldName,index)
{

	
	//var codeValue=targetFormName.selectedValues.value;
	var strCodeArray=window.opener.document.getElementsByName(strLovTxtFieldName);

	if(index!=0){
	for(var i=0;i<=strCodeArray.length;i++)
	  {
	    if(i==index){
			 strCodeArray[index].value=strCode;
 	    }
	}
    }
    else {
	 strCodeArray[0].value=strCode;
	}
	if(strLovDescriptionTxtFieldName !=""){
		var strDescArray=window.opener.document.getElementsByName(strLovDescriptionTxtFieldName);
		if(index!=0){
			for(var i=0;i<=strDescArray.length;i++)
			  {
			    if(i==index){
					if(strDesc == 'null'){
						strDescArray[index].value=" ";
					}else{
					 strDescArray[index].value=strDesc;
				  }

				  }
			}
		    }
		    else {
			 strDescArray[0].value=strDesc;
	}
	}
	if(strLovaddressTxtFieldName !=""){
			var strAddrArray=window.opener.document.getElementsByName(strLovaddressTxtFieldName);
			if(index!=0){
				for(var i=0;i<=strAddrArray.length;i++)
				  {
				    if(i==index){

						if(strAddr == 'null') {
							strAddrArray[index].value=" ";
						}else{
						 strAddrArray[index].value=strAddr;
					  }

						}
					}
			}else {
				if(strAddr == 'null') {
					strAddrArray[0].value=" ";
				}else{
				 strAddrArray[0].value=strAddr;
				}

		}
		}

	if(window.opener){

			if(window._parentOkFnHook!=null && window._parentOkFnHook!="" ){
				try{
				eval("window.opener."+window._parentOkFnHook);
				}catch(e){
					//TODO
				}
			}
	}else if(window.opener._lovOkFnHook!=null && window.opener._lovOkFnHook!=""){
			try{
				eval("window.opener."+window.opener._lovOkFnHook);
			}catch(e){
			//TODO
			}
		}

	window.close();
}