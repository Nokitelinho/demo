<%@ include file="/jsp/includes/js_contenttype.jsp" %>



function screenSpecificEventRegister()
{
   var frm=targetFormName;
   with(frm){

   	//CLICK Events

     	evtHandler.addEvents("btClose","closeCommand()",EVT_CLICK);
     	evtHandler.addEvents("btPrint","printCommand()",EVT_CLICK);
		evtHandler.addEvents("btClear","clearCommand()",EVT_CLICK);
		evtHandler.addEvents("btList","listCommand()",EVT_CLICK);

		//added by A-8149 for ICRD-248207
		evtHandler.addEvents("linkFirst","displayFirst()",EVT_CLICK);
		evtHandler.addEvents("linkPrev","displayPrevious()",EVT_CLICK);
		evtHandler.addEvents("linkNext","displayNext()",EVT_CLICK);
		evtHandler.addEvents("linkLast","displayLast()",EVT_CLICK);

    }
	onScreenLoad();
}

function onScreenLoad(){
	frm=targetFormName;
	if(frm.elements.btnDisableReq.value != "Y"){
		disableField(targetFormName.elements.btList);
		disableField(targetFormName.elements.mailbagId);
	}
	if(frm.elements.mailbagId.value==null || frm.elements.mailbagId.value.length == 0)
		disableField(targetFormName.elements.btPrint);
	if(frm.elements.displayPopupPage.value=="0"){ 	//added by A-8149 for ICRD-248207
		disableLink(document.getElementById('linkFirst'));
		disableLink(document.getElementById('linkPrev'));
	}
	if(frm.elements.displayPopupPage.value==(frm.elements.totalViewRecords.value-1)){  //added by A-8149 for ICRD-248207
		disableLink(document.getElementById('linkNext'));
		disableLink(document.getElementById('linkLast'));
	}
}

function printCommand(){
   frm=targetFormName;
   generateReport(frm,'/mailtracking.defaults.mbHistory.print.do');
}

function displayFirst(){  //added by A-8149 for ICRD-248207
	frm=targetFormName;
	frm.elements.displayPopupPage.value="0";
	frm.action="mailtracking.defaults.mbHistory.displaynexthistory.do";
	frm.submit();

}

function displayPrevious(){  //added by A-8149 for ICRD-248207
	frm=targetFormName;
	var pageNum=parseInt(frm.elements.displayPopupPage.value,10);
	frm.elements.displayPopupPage.value=(pageNum-1);
	frm.action="mailtracking.defaults.mbHistory.displaynexthistory.do";
	frm.submit();

}

function displayNext(){  //added by A-8149 for ICRD-248207
	frm=targetFormName;
	var pageNum=parseInt(frm.elements.displayPopupPage.value,10);
	frm.elements.displayPopupPage.value=(pageNum+1);
	frm.action="mailtracking.defaults.mbHistory.displaynexthistory.do";
	frm.submit();

}

function displayLast(){  //added by A-8149 for ICRD-248207
	frm=targetFormName;
	var totalRecords=parseInt(frm.elements.totalViewRecords.value,10);
	frm.elements.displayPopupPage.value=(totalRecords-1);
	frm.action="mailtracking.defaults.mbHistory.displaynexthistory.do";
	frm.submit();

}

function clearCommand(){
   frm=targetFormName;
   submitForm(frm,'mailtracking.defaults.mbHistory.clear.do');
}
function listCommand(){
   frm=targetFormName;
   if(frm.elements.mailbagId.value==null || frm.elements.mailbagId.value.length == 0){
		showDialog({msg:'Invalid Mail bag entered',type:1,parentWindow:self});	
 		frm.elements.mailbagId.focus();
		return;
   }
   submitForm(frm,'mailtracking.defaults.mbHistory.list.do');
}
function closeCommand(){
	frm=targetFormName;
	submitForm(frm,'mailtracking.defaults.mbHistory.close.do');
}

