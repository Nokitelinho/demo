<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
   		var frm = targetFormName;
		if(frm.elements.uldNo.disabled==false){
			frm.elements.uldNo.focus();
		}
   		with(frm){
   		onLoad();
   		onUnload();
   		evtHandler.addEvents("btList","list(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClear","clearScreen(this.form)",EVT_CLICK);
		evtHandler.addEvents("btOk","OnClickOK(this.form)",EVT_CLICK);
		evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
		//var chkBoxObj=document.getElementsByName("rowId");
		//if(chkBoxObj!=null && chkBoxObj.length>0){
		//	evtHandler.addIDEvents("rowId","checkOne(this,targetFormName.elements.masterRowId,'rowId')",EVT_CLICK);
		//}
   		}
   	}

function list(frm){
//alert("hai");
	submitForm(frm,'uld.defaults.lov.listscreen.do');
}


function clearScreen(frm){
//alert("inside clear");
 	//submitForm(frm,'uld.defaults.lov.clearscreen.do');
 	submitFormWithUnsaveCheck('uld.defaults.lov.clearscreen.do')
}


function closeScreen(){
 	window.close();
 }

function submitPage(lastPg,displayPg){
  targetFormName.elements.lastPageNum.value=lastPg;
  targetFormName.displayPage.value=displayPg;
  //submitForm(targetFormName, appPath + '/operations.shipment.clearancelistinglistcommand.do');
  submitForm(targetFormName,'uld.defaults.lov.listscreen.do');
 
}

function checkAll(parentbox,name){
	var chkBoxObj=document.getElementsByName(name);
	if(chkBoxObj!=null){
		var chkBoxLength = chkBoxObj.length;
		for(var i=0;i<chkBoxLength;i++)
		{
			chkBoxObj[i].checked=parentbox.checked;
		}
	}
}

function checkOne(checkbox,parentObj,childbox){

	var childObjs=document.getElementsByName(childbox);
	var isAllCheck=true;
	if(checkbox.checked){
		var chkBoxLength = childObjs.length;
		for(var i=0;i<chkBoxLength;i++)
		{
			if(!childObjs[i].checked)
			{
				isAllCheck=false;
				break;
			}
		}

	}else{
		isAllCheck=false;

	}
	if(isAllCheck){
		parentObj.checked=false;
		//checkAll(parentObj,"rowId");
	}else{
	parentObj.checked=false;
	}
}

function OnClickOK(frm){
	AllCheck();
	var str="";

	if(targetFormName.elements.pageURL.value=="ADDREPAIR")
         {

	for(var i=0;i<targetFormName.elements.length;i++)
	{
		if(targetFormName.elements[i].type =='checkbox')
		{
			if(targetFormName.elements[i].checked)
			{
			if(str=="")
			{str = targetFormName.elements[i].value;}
			else
			{str = str+","+targetFormName.elements[i].value;}



			}
		}
	}

	}
	else
	{
	if(validateSelectedCheckBoxes(targetFormName,'rowId',1,0)){

		for(var i=0;i<targetFormName.elements.length;i++)
		{
			if(targetFormName.elements[i].type =='checkbox')
			{
				if(targetFormName.elements[i].checked)
				{
				if(str=="")
				{str = targetFormName.elements[i].value;}
				else
				{str = str+","+targetFormName.elements[i].value;}



				}
			}
		}
	}else{return;}

	}


         if(targetFormName.elements.pageURL.value=="ADDREPAIR"||
         targetFormName.elements.pageURL.value=="MODREPAIR")
         {
         window.opener.targetFormName.elements.dmgRepairRefNo.value=str;
		 
		 window.opener.targetFormName.elements.allChecked.value=targetFormName.elements.allChecked.value;
		 
         }
         else
         {
	window.opener.targetFormName.elements.damageRefNo.value=str;
	//window.opener.targetFormName.elements.damageRefNo.defaultValue=str;
	}
	var chkbox = document.getElementsByName("rowId");
	
	for(var i=0;i<chkbox.length;i++){
		chkbox[i].defaultChecked=chkbox[i].checked;
		chkbox[i].defaultValue=chkbox[i].value;
		}

	
	

	clearScreen(frm);
	window.opener.IC.util.common.childUnloadEventHandler();
	 window.close();


}


function AllCheck(){
	
	var totalcount = targetFormName.elements.rowId.length;
	var count = 0;
	for(var i=0;i<targetFormName.elements.rowId.length;i++){
		if(targetFormName.elements.rowId[i].checked){
			count++;
		}
	}
	
	if(count==totalcount){
		targetFormName.elements.allChecked.value="Y";
			
	}


}

function onLoad(){

if(targetFormName.elements.pageURL.value=="ADDREPAIR" ||
         targetFormName.elements.pageURL.value=="MODREPAIR")
{
targetFormName.elements.uldNo.readonly="true";
targetFormName.elements.btList.disabled="true";
targetFormName.elements.btClear.disabled="true";

}



}


function onUnload(){
self.opener.childWindow=null;
}


function submitClick(chkbox){

   str=chkbox;
   if(targetFormName.elements.pageURL.value=="ADDREPAIR"||
         targetFormName.elements.pageURL.value=="MODREPAIR")
         {
         window.opener.targetFormName.elements.dmgRepairRefNo.value=str;
         }
         else
         {
	window.opener.targetFormName.elements.damageRefNo.value=str;
	
	}
	var chkbox = document.getElementsByName("rowId");
	for(var i=0;i<chkbox.length;i++){
		chkbox[i].defaultChecked=chkbox[i].checked;
		chkbox[i].defaultValue=chkbox[i].value;
	}
	clearScreen(targetFormName);
	 window.close();
}

