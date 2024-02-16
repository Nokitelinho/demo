<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister()
{
   	var frm=targetFormName;
	if(frm.elements.sequenceNo.disabled==false){
			frm.elements.sequenceNo.focus();
		}
    with(frm){
   	evtHandler.addEvents("btList","onClickList()",EVT_CLICK);
   	evtHandler.addEvents("btClear","onClickClear()",EVT_CLICK);
   	evtHandler.addEvents("btOk","submitFrm(this.form)",EVT_CLICK);
    evtHandler.addEvents("btClose","window.close();",EVT_CLICK);
    	onScreenLoad();
 }
}

function onScreenLoad(){
	if(targetFormName.elements.listSuccess.value == 'Y'){			
		enableField(targetFormName.elements.btOk);
	}else{		
		disableField(targetFormName.elements.btOk);
	}
}

function submitFrm(frm)
{
	if(validateSelectedCheckBoxes(frm,'suChecked',1,1)){
	var textfiledObj=frm.elements.textfiledObj.value;
	var formNumber=frm.elements.formNumber.value;
	var textfiledTime=frm.elements.textfiledTime.value;
	var textfiledDate=frm.elements.textfiledDate.value;
	var seqNumber="";
	var date="";
	var time="";
	var index=frm.elements.rowCount.value;
	for(var i=0;i<targetFormName.elements.length;i++)
	{
		if(targetFormName.elements[i].type =='checkbox')
		{
			if(targetFormName.elements[i].checked)
			{
			seqNumber=targetFormName.elements[i].value;
			var datas=new Array();
			datas=seqNumber.split(";");
			seqNumber=datas[0];
			date=datas[1];
			time=datas[2];
			}
		}
	}
	var objCode = eval("window.opener.document.forms["+formNumber+"]."+textfiledObj);
	objCode.value=seqNumber;
	if(textfiledTime!=""){
	var objDesc = eval("window.opener.document.forms["+formNumber+"]."+textfiledTime);
	objDesc.value=time;
	}
	if(textfiledDate!=""){
	var objDesc = eval("window.opener.document.forms["+formNumber+"]."+textfiledDate);
	objDesc.value=date;
	}
	window.close();
	self.opener.childWindow=null;
	}
}

function submitList(strLastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	targetFormName.action ="uld.defaults.messaging.screenloadscmseqnolov.do";
	targetFormName.submit();
	disablePage();
}

function onClickList(){
	targetFormName.elements.displayPage.value="1";
	submitForm(targetFormName,"uld.defaults.messaging.screenloadscmseqnolov.do");
}

function onClickClear(){
	//submitForm(targetFormName,"uld.defaults.messaging.clearscmseqnolov.do");
	submitFormWithUnsaveCheck('uld.defaults.messaging.clearscmseqnolov.do');
}

function singleSelectCb(checkVal)
{
	for(var i=0;i<document.forms[0].elements.length;i++)
	{

		if((document.forms[0].elements[i].type =='checkbox' && document.forms[0].elements[i].name == 'suChecked'))
		{

			if(document.forms[0].elements[i].checked == true)
			{


				if(document.forms[0].elements[i].id != 'scmlov'+checkVal)
				{


					document.forms[0].elements[i].checked = false;
				}
			}
		}
	}
}