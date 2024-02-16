<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/ux/tlds.jsp" %>
function screenSpecificEventRegister()
{
   var frm=targetFormName;
   with(frm){
   	//CLICK Events
     	evtHandler.addEvents("btnPostalClose","closefn()",EVT_CLICK);
		evtHandler.addEvents("btnPostalUpdate","updatefn()",EVT_CLICK);
    }
	onScreenLoad();
}
function onScreenLoad(){
}
function closefn(){
popupUtils.closePopupDialog();
}
function updatefn(){
	var action = "mail.operations.ux.mailperformance.postalcalendar.editcalendar.do?postalCalendarAction=EDIT"+"&postalDiscEftDate="+targetFormName.elements.postalDiscEftDate.value+"&postalIncCalcDate="+targetFormName.elements.postalIncCalcDate.value+"&postalIncEftDate="+targetFormName.elements.postalIncEftDate.value+"&postalIncRecvDate="+targetFormName.elements.postalIncRecvDate.value+"&postalClaimGenDate="+targetFormName.elements.postalClaimGenDate.value;
	window.parent.document.forms[1].action = action;
	window.parent.IC.util.common.childUnloadEventHandler();
	window.parent.document.forms[1].submit();
	popupUtils.closePopupDialog();
}