<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister()
{
	var frm=targetFormName;
	if(frm.agreementNo.disabled==false){
			frm.agreementNo.focus();
		}
	with(frm){

		evtHandler.addEvents("btok","OnClickOK(this.form)",EVT_CLICK);
		evtHandler.addEvents("btclose","onClickClose(this.form)",EVT_CLICK);
		evtHandler.addEvents("btList","onClickButton('List')",EVT_CLICK);
		evtHandler.addEvents("btClear","onClickButton('Clear')",EVT_CLICK);
		onUnload();
	}
}

function onClickButton(buttonName){
	if( buttonName == 'List'){
		submitForm(targetFormName,'uld.defaults.screenloadagreementnolov.do');
	}else if( buttonName == 'Clear'){
		submitForm(targetFormName,'uld.defaults.clearloadagreementnolov.do');
	}
}
	function OnClickOK(frm)
	{
		var str="";
		for(var i=0;i<targetFormName.elements.length;i++)
		{
			if(targetFormName.elements[i].type =='checkbox')
			{
				if(targetFormName.elements[i].checked)
				{
					str = targetFormName.elements[i].value;
				}
			}
		}

		window.opener.targetFormName.elements.agreementNumber.value=str;
       		 window.close();
}

function callFunction(lastPg,displayPg){
	targetFormName.elements.lastPageNumber.value = lastPg;
	targetFormName.elements.displayPage.value = displayPg;
	var frm=targetFormName;
	var action = "uld.defaults.screenloadagreementnolov.do";
	submitForm(frm, action);
}

function onClickClose(frm){
window.close();
}

function onUnload(){
self.opener.childWindow=null;
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 *
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 */
function singleSelect(checkVal)
{
	for(var i=0;i<targetFormName.elements.length;i++)
	{
		if(targetFormName.elements[i].type =='checkbox')
		{
			if(targetFormName.elements[i].checked == true)
			{
				if(targetFormName.elements[i].id != checkVal.id)
				{
					targetFormName.elements[i].checked = false;
				}
			}
		}
	}
}

function setValueOnDoubleClick(strCode,strDesc,strLovTxtFieldName,strLovDescriptionTxtFieldName,index)
{
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
					 strDescArray[index].value=strDesc;
				  }
			}
		    }
		    else {
			 strDescArray[0].value=strDesc;
			
			}
	}

	if(window.opener){
			if(window._parentOkFnHook!=null && window._parentOkFnHook!="" ){
				try{
				eval("window.opener."+window._parentOkFnHook);
				}catch(e){
					//TODO
				}
			}else if(window.opener._lovOkFnHook!=null && window.opener._lovOkFnHook!=""){
				try{
											eval("window.opener."+window.opener._lovOkFnHook);
										}catch(e){
										//TODO
										}
			}
	}

	window.close();
}