<%@ include file="/jsp/includes/js_contenttype.jsp" %>
function screenSpecificEventRegister()
{
	var frm=targetFormName;

	with(targetFormName){
	evtHandler.addEvents("btnSave","saveDetails()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
	}
    //onLoadPopuplist(targetFormName);
	onLoadPopup(targetFormName);


}
function onLoadPopup(frm){
 if(frm.elements.formStatusFlag.value == "SAVE"){

    window.close();
     //window.opener.document.forms[1].action="mailtracking.mra.defaults.openmaintainblgmatrixscreen.do?operationFlag="+frm.opFlag.value;
	  //window.opener.IC.util.common.childUnloadEventHandler();
     //window.opener.document.forms[1].submit();
	 
	 //submitForm(frm,'mailtracking.mra.defaults.maintainbillingmatrix.list.do');
	  //window.close();
      //window.opener.document.forms[1].action="mailtracking.mra.defaults.openmaintainblgmatrixscreen.do?operationFlag="+frm.opFlag.value;
	  //window.opener.IC.util.common.childUnloadEventHandler();
     //window.opener.document.forms[1].submit();
	 window.opener.document.forms[1].action="mailtracking.mra.defaults.maintainbillingmatrix.list.do?operationFlag="+frm;
	  window.opener.IC.util.common.childUnloadEventHandler();
      window.opener.document.forms[1].submit();


  }
}
function onLoadPopuplist(frm){
	
}

function saveDetails(){
	submitForm(targetFormName,"mailtracking.mra.defaults.maintainbillingmatrix.savechangeenddate.do");
	//onLoadPopup(targetFormName);
    //submitForm(targetFormName,"mailtracking.mra.defaults.listcopybillingline.do");
}
function closeScreen(){
	window.close();
}

