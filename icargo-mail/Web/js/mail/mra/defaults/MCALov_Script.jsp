<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
window.onload=screenSpecificEventRegister;
 function screenSpecificEventRegister(){
	var frm=document.forms[0];
	with(frm){
		
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addEvents("btnList","list()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
	}
}
function list(){
var frm=document.forms[0];
//Added by A-5220 for ICRD-32647 starts
frm.displayPage.value=1;
frm.lastPageNum.value=0;
//Added by A-5220 for ICRD-32647 ends
submitForm(frm,'mailtracking.mra.defaults.mcalov.list.do');
}

function clear(){

var frm=document.forms[0];
submitForm(frm,'mailtracking.mra.defaults.mcalov.clear.do');
}
function setValueofInvoiceNoAndCsgNum(multiselect,strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,strLovNameTxtFieldName,arrayIndex,strCode,strDesc,strName){
	var isMultiselect=eval("window.opener.targetFormName."+multiselect);
	if(multiselect=='Y'){
		setMultipleValues(strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,arrayIndex);
	}
	else{
	setValueOfInvoiceNoAndCsgDocNum(strCode,strDesc,strName,strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,strLovNameTxtFieldName);
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
function setValueOfInvoiceNoAndCsgDocNum(strCode,strDesc,strName,strFormCount,strLovTxtFieldName,strLovNameTxtFieldName,strLovDescriptionTxtFieldName)
{
    var concatenatedValue=targetFormName.selectedValues.value;
    var codeAndValue=concatenatedValue.split("\u00A5");
	var objCode = "";
	var objDesc= "";
	var objName= "";
		if(strLovTxtFieldName != null && strLovTxtFieldName.length>0){
			  objCode = eval("window.opener.targetFormName."+strLovTxtFieldName);
		}
		if(strLovDescriptionTxtFieldName != null && strLovDescriptionTxtFieldName.length>0){
			objDesc = eval("window.opener.targetFormName."+strLovDescriptionTxtFieldName);
		}
		if(strLovNameTxtFieldName != null && strLovNameTxtFieldName.length>0){
			objName = eval("window.opener.targetFormName."+strLovNameTxtFieldName);
		}
    /*var objCode = eval("window.opener.document.forms["+strFormCount+"]."+strLovTxtFieldName);
    var objDesc = eval("window.opener.document.forms["+strFormCount+"]."+strLovDescriptionTxtFieldName);
    var objName = eval("window.opener.document.forms["+strFormCount+"]."+strLovNameTxtFieldName);
*/
	var strCodeArray = "";
	var strDescArray = "";
	var strNameArray = "";
		if(strLovTxtFieldName != null && strLovTxtFieldName.length>0){
			  strCodeArray=window.opener.document.getElementsByName(strLovTxtFieldName);
		}
		if(strLovDescriptionTxtFieldName != null && strLovDescriptionTxtFieldName.length>0){
			 strDescArray=window.opener.document.getElementsByName(strLovDescriptionTxtFieldName);
		}
		if(strLovNameTxtFieldName != null && strLovNameTxtFieldName.length>0){
			  strNameArray=window.opener.document.getElementsByName(strLovNameTxtFieldName);
		}
/*
    var strCodeArray=window.opener.document.getElementsByName(strLovTxtFieldName);
    var strDescArray=window.opener.document.getElementsByName(strLovDescriptionTxtFieldName);
    var strNameArray=window.opener.document.getElementsByName(strLovNameTxtFieldName);
*/
    var args = setValueOfInvoiceNoAndCsgDocNum.arguments;
    var index = args[7];
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
			}
		}
	}
}
	else {
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
	else{
		objCode.value = codeAndValue[0];
		if(strLovDescriptionTxtFieldName !=""){
			objDesc.value =codeAndValue[1];
		}
		if(strLovNameTxtFieldName !=""){
			objName.value =codeAndValue[2];
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



