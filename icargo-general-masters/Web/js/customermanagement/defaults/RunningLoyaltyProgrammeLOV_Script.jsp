<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister()

{

   	var frm=document.forms[0];
    with(frm){

   	evtHandler.addEvents("btOk","submitFrm(this.form)",EVT_CLICK);
    evtHandler.addEvents("btClose","window.close();",EVT_CLICK);

 }
}

//Added by A-5220 for ICRD-32647 starts	

function submitPage(lastPageNum, displayPage){

	targetFormName.elements.lastPageNum.value = lastPageNum;
	targetFormName.elements.displayPage.value = displayPage;
	submitForm(targetFormName, 'customermanagement.defaults.lov.screenloadrunningloyaltyproglov.do');

}			  

//Added by A-5220 for ICRD-32647 ends

function submitFrm(frm)
{


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


