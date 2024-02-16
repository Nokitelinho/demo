<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister()

{
   	var frm=document.forms[0];
    with(frm){
		evtHandler.addEvents("btOk","submitFrm(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","window.close();",EVT_CLICK);
		evtHandler.addEvents("btList","listfrm(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","clearfrm(this.form)",EVT_CLICK);
	}
}

function submitList(strLastPageNum,strDisplayPage){
	document.forms[0].elements.lastPageNum.value= strLastPageNum;
	document.forms[0].elements.displayPage.value = strDisplayPage;
	document.forms[0].action ="customermanagement.defaults.lov.listloyaltyproglov.do";
	document.forms[0].submit();
	disablePage();
}
function listfrm(frm){
	//Added by A-5220 for ICRD-32647 starts
	frm.elements.displayPage.value=1;
	frm.elements.lastPageNum.value=0;
	//Added by A-5220 for ICRD-32647 ends
	submitForm(frm,'customermanagement.defaults.lov.listloyaltyproglov.do');
}

function clearfrm(frm){
	submitForm(frm,'customermanagement.defaults.lov.clearloyaltyproglov.do');
}

function setValueOnDoubleClickForLoyaltyPgm(){
		var frm = targetFormName;
       	var textfiledObj=frm.elements.textfiledObj.value;
		var formNumber=frm.elements.formNumber.value;
		var textfiledDesc=frm.elements.textfiledDesc.value;
		var str="";
		var array="";
		var desc="";
        var index=frm.elements.rowCount.value;
		for(var i=0;i<document.forms[0].elements.length;i++)
		{
			if(document.forms[0].elements[i].type =='checkbox')
			{
				if(document.forms[0].elements[i].checked)
				{
					 array=document.forms[0].elements[i].value.split(",");
					 str =array[0];				    
				}
			}
		}
        var objCode = eval("window.opener.document.forms["+formNumber+"]."+textfiledObj);
		objCode.value=str;
		if(textfiledDesc!=""){
			var objDesc = eval("window.opener.document.forms["+formNumber+"]."+textfiledDesc);
			objDesc.value=str;
		}
        window.close();
        self.opener.childWindow=null;
}

function submitFrm(frm){
	if(validateSelectedCheckBoxes(frm,'suChecked',1,1)){
        var textfiledObj=frm.elements.textfiledObj.value;
		var formNumber=frm.elements.formNumber.value;
		var textfiledDesc=frm.elements.textfiledDesc.value;
		var textfiledFromDate=frm.elements.textfiledFromDate.value;
		var textfiledToDate=frm.elements.textfiledToDate.value;

		var str="";
		var array="";
		var desc="";
		var fromDate="";
		var toDate="";
        var index=frm.elements.rowCount.value;

		for(var i=0;i<document.forms[0].elements.length;i++)
		{
			if(document.forms[0].elements[i].type =='checkbox')
			{
				if(document.forms[0].elements[i].checked)
				{

					 array=document.forms[0].elements[i].value.split(",");
					 str =array[0];
				     desc =array[1];
				     fromDate =array[2];
				     toDate =array[3];

				}
			}
		}
           var objCode = eval("window.opener.document.forms["+formNumber+"]."+textfiledObj);
			  objCode.value=str;

			   if(textfiledDesc!=""){
					var objDesc = eval("window.opener.document.forms["+formNumber+"]."+textfiledDesc);
					  objDesc.value=desc;
				}

				if(textfiledFromDate!=""){
					var objFromDate = eval("window.opener.document.forms["+formNumber+"]."+textfiledFromDate);
					  objFromDate.value=fromDate;
				}

				if(textfiledToDate!=""){
					var objToDate = eval("window.opener.document.forms["+formNumber+"]."+textfiledToDate);
					  objToDate.value=toDate;
				}



       window.close();
       self.opener.childWindow=null;
	}
}

function setLoyalityLovOnDblClick(codeObject,codeValue,formNum){
		var objCode = eval("window.opener.document.forms["+formNum+"]."+codeObject);
		objCode.value=codeValue;
		window.close();
	}


