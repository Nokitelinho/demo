<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	
	var frm=targetFormName;
	
	with(frm){
		
		evtHandler.addEvents("btnList","listGroupDetails()",EVT_CLICK);	
		evtHandler.addEvents("btnClear","clearGroupDetails()",EVT_CLICK);	
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);	
		evtHandler.addIDEvents("groupnamelov","groupLovDisplay(this)",EVT_CLICK);
	}
}
function listGroupDetails(){
	submitForm(targetFormName,"customermanagement.defaults.listgroupdetailsforcustomer.do");
}
function clearGroupDetails(){
	submitForm(targetFormName,"customermanagement.defaults.cleargroupdetailsforcustomer.do");
}
function groupLovDisplay(fld) {

	var frm=targetFormName;
	var groupType = frm.elements.groupType.value;
	var groupCategory;
	if (targetFormName.elements.category && targetFormName.elements.category.value !=''){
		groupCategory=targetFormName.elements.category.value;
	}else{
		groupCategory='GEN';
	}
	if(groupType ==null || groupType==""){
		groupType='AGTGRP';
	}
	
	showParameterGroupLov('shared.generalmastergrouping.screenloadgroupnamelov.do','N',targetFormName.elements.groupName.value,groupType,groupCategory,targetFormName.elements.groupDescription.value,'groupName','groupDescription','1','0');
	
}
/**
  * showParameterGroupLov function is for displaying the ParameterGroup LOV
  * strAction - Action mapping for the ParameterGroup LOV;
  * multiSelect - Y/N flag for multiple Selection;
  * groupNameVal - the value passed for Group Name(s) field in Parent screen;
  * groupTypeVal - The value passed for Group Type field in Parent screen;
  * textfiledObj - The text field object for group name(s) in Parent screen;
  * formNumber - The Form number for the parent screen;
  * index - The index of the array.If it is a single textfield set it as 0;
  */
  //Modified as a part of ICRD-32410
function showParameterGroupLov(strAction,multiSelect,groupNameVal,groupTypeVal,groupCategory,groupDescription,textfieldObj,textfieldDescription,formNumber,index){

	var objCode = eval("targetFormName.elements."+textfieldObj);
	//var objCode = eval("document.forms["+formNumber+"]."+textfieldObj);

	if(objCode.length == null){
		groupNameVal = objCode.value;
	}else if(objCode.length != null){
		groupNameVal = objCode[index].value;
	}

	var strUrl = strAction+"?multiSelect="+multiSelect+"&pagination=Y&groupName="+groupNameVal+"&groupType="+groupTypeVal+"&groupCategory="+groupCategory+"&groupDescription="+groupDescription+"&textfieldObj="+textfieldObj+"&textfieldDescription="+textfieldDescription+"&formNumber="+formNumber+"&index="+index;
	openPopUp(strUrl,550,575);
	
}