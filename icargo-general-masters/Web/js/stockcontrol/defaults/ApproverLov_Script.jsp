<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	//targetFormName.btok.disabled=true; commented by A-5844 for ICRD-90326
	with(frm){

		/*evtHandler.addEvents("code","validateFields(this,-1,'Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("description","validateFields(this,-1,'Description',0,true,true)",EVT_BLUR);*/
		evtHandler.addEvents("btlist","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btClear","clearLOVForm()",EVT_CLICK);
		if(targetFormName.elements.checkBox){
			evtHandler.addEvents("checkBox","singleSelect(this)",EVT_CLICK);
		}
		//evtHandler.addEvents("btok","submitForm(targetFormName,'stockcontrol.defaults.selectfromlov.do')",EVT_CLICK);
		evtHandler.addEvents("btok","doActionOk()",EVT_CLICK);
		evtHandler.addEvents("btcancel","window.close()",EVT_CLICK);
		evtHandler.addEvents("checkbox","enableButton()",EVT_CLICK);
	}
	//heightOnScreenload();
	callOnLoad();
}

function onClickList(){
	var frm = targetFormName;
	frm.action = "stockcontrol.defaults.listLov.do";
	frm.submit();
	disablePage();
}

function doActionOk(){
	var frm = targetFormName;
	var checkbox = document.getElementsByName('checkBox');
	var len = checkbox.length;
	var flag = new Boolean(false);
	if(len==0){
		
		showDialog({	
				msg		:	'<common:message bundle="approverlovresources" key="stockcontrol.defaults.approverlov.error.pleaseenteapprover" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		//alert("Select a row");
	}
	else{
		for(var i=0;i<len;i++){
			if(checkbox[i].checked == true){
				flag=true;
				submitForm(frm,'stockcontrol.defaults.selectfromlov.do');

			}

		}
		if(flag==false){
			
			showDialog({	
				msg		:	'<common:message bundle="approverlovresources" key="stockcontrol.defaults.approverlov.error.pleaseenteapprover" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		}
	}
}
function heightOnScreenload(){
		var clientHeight = document.body.clientHeight;
		var _mainDivHeight = (clientHeight*90)/100;
		var _tableDivHeight=(clientHeight*49)/100;
		IC.util.dom.getElementById('mainDiv').style.height=_mainDivHeight+'px';
		IC.util.dom.getElementById('div1').style.height=_tableDivHeight+'px';
	}

function singleSelect(checkVal){
	var frm = targetFormName;
	for(var i=0;i<frm.elements.length;i++){
		if(frm.elements[i].type =='checkbox'){
			if(frm.elements[i].checked == true){
				if(frm.elements[i].value != checkVal.value){
					frm.elements[i].checked = false;
				}
			}
		}
	}
}

function submitFrm(frm,strAction){
   	if(validateSelectedCheckBoxes(frm,'checkBox',1,1)){
   		var parentFrm = window.opener.targetFormName;
		parentFrm.elements.nextAction.value="";
		//TODO : doesnt work in submitForm
		targetFormName.action = "stockcontrol.defaults.selectfromlov.do";
		targetFormName.submit();
		disablePage();
	}
}

function submitAction(frm,strAction){
	submitForm(frm,"stockcontrol.defaults.listLov.do");
}

function callFun(frm){
	if(frm == "Y"){
		 var parentFrm = window.opener.targetFormName;
		 window.opener.IC.util.common.childUnloadEventHandler();
		 window.opener.submitForm(parentFrm,"stockcontrol.defaults.reload.do");
		 parentFrm.elements.stockHolderType.disabled=false;
		 close();
	}
}

function submit(strLastPageNum,strDisplayPage){
        var frm=targetFormName;
	frm.elements.lastPageNum.value= strLastPageNum;
	frm.elements.displayPage.value = strDisplayPage;
	submitForm(frm,"stockcontrol.defaults.listLov.do");
}

function callOnLoad(){
	var parentFrm = window.opener.targetFormName;
	parentFrm.elements.nextAction.value ="";
	var isCheck =targetFormName.elements.isValueSelected.value;
	if( isCheck == "true" )
	{
	   window.close();
	}
}

function enableButton(){
	var frm = targetFormName;
	var checkbox = document.getElementsByName('checkBox');
	var len = checkbox.length;
	var flag = new Boolean(false);
	for(var i=0;i<len;i++){
			if(checkbox[i].checked == true){
				targetFormName.elements.btok.disabled = false;
				flag = true;
				break;
			}
	}
	if(flag==false){
			targetFormName.elements.btok.disabled = true;
	}
}

function clearLOVForm(){
	var frm = targetFormName;
	IC.util.dom.getElementById("stockHolderType").value = "";
	IC.util.dom.getElementById("code").value = "";
	IC.util.dom.getElementById("description").value = "";

}