<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=document.forms[0];

	with(frm){

		evtHandler.addEvents("btnList","callList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","submitForm(this.form,'clearInvoiceLOV.do')",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		
	}
}



function setValueOnOk(multiselect,strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,arrayIndex){

	var isMultiselect=eval("window.opener.targetFormName.elements."+multiselect);

	if(multiselect=='Y'){
		setMultipleValues(strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,arrayIndex);
	}
	else{
	setValueOfCodeOnOk(strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName,arrayIndex);
	}

}

function setValueOfCodeOnOk(strFormCount,strLovTxtFieldName,strLovDescriptionTxtFieldName)
{
   var concatenatedValue=targetFormName.elements.selectedValues.value;
       var args = setValueOfCodeOnOk.arguments;
       var index = args[3];
       if (concatenatedValue != ""){
   		var codeAndValue=concatenatedValue.split("&");
   		var strCodeArray=window.opener.document.getElementsByName(strLovTxtFieldName);
   			if(index!=0){
   			for(var i=0;i<=strCodeArray.length;i++)
   			  {
   			    if(i==index){
   					 strCodeArray[index].value=codeAndValue[0];
   		 	    }
   			}
   		    }
   		    else {
			//alert(strCodeArray[0]);
   			 strCodeArray[0].value=codeAndValue[0];
   		}
   		/*if (strLovTxtFieldName != ""){
   				var objCode = eval("window.opener.document.forms["+strFormCount+"]."+strLovTxtFieldName);
   				objCode.value = codeAndValue[0];
   		}*/
   			//If the name of the decsription text field is passed, set the parent's description field.
   		/*if(strLovDescriptionTxtFieldName !=""){
   			var objDesc = eval("window.opener.document.forms["+strFormCount+"]."+strLovDescriptionTxtFieldName);
   		 	objDesc.value =codeAndValue[1];
   		}*/
           if(strLovDescriptionTxtFieldName !=""){
   				var strDescArray=window.opener.document.getElementsByName(strLovDescriptionTxtFieldName);
   				if(index!=0){
   					for(var i=0;i<=strDescArray.length;i++)
   					  {
   					    if(i==index){
   							 strDescArray[index].value=codeAndValue[0];
   							 //alert(strDescArray[index].value);
   						  }
   					}
   				    }
   				    else {
   					 strDescArray[0].value=codeAndValue[1];
   			}
   	  }


   			//Close the LOV Pop Up.
   	}
	if(window.opener){
		if(window._parentOkFnHook!=null && window._parentOkFnHook!="" ){
			try{
				eval("window.opener."+window._parentOkFnHook);
			}catch(e){
				//TODO
			}
		} else if(window.opener._lovOkFnHook!=null && window.opener._lovOkFnHook!=""){
			try{
				eval("window.opener."+window.opener._lovOkFnHook);
			}catch(e){
				//TODO
			}
		}
	}
	window.close();
}