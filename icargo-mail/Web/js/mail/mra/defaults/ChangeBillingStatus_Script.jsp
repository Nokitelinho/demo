<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister()
{

	with(targetFormName){


	evtHandler.addEvents("btnSave","save()",EVT_CLICK);
	evtHandler.addEvents("btnClose","closeFun()",EVT_CLICK);
	}


	onloadPopup();

}

function save(){

//alert("fromScreen..."+targetFormName.fromScreen.value);

if(targetFormName.elements.fromScreen.value=="fromGpaBilling"){

	popupSaveFromGpa();

}
else{
window.opener.IC.util.common.childUnloadEventHandler();
submitForm(targetFormName,'mailtracking.mra.defaults.changestatussave.do');

}

}

function onloadPopup(){


if(targetFormName.elements.screenStatus.value=="popupSave" && targetFormName.elements.fromScreen.value=="fromGpaBilling"){
window.opener.IC.util.common.childUnloadEventHandler();
window.close();
window.opener.targetFormName.action="mailtracking.mra.gpabilling.listbillingentries.do";
window.opener.targetFormName.submit();
}

else if(targetFormName.elements.screenStatus.value=="popupSave" && targetFormName.elements.fromScreen.value=="fromInterlineBilling"){
window.opener.IC.util.common.childUnloadEventHandler();
window.close();

window.opener.submitForm(window.opener.targetFormName,'mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.list.do');

}

}
function closeFun(){

	window.close();
}

function popupSaveFromGpa(){

var isbill='';
isbill=document.forms[0].elements.isBillable.value;
var chgStatus=document.forms[0].elements.billingStatus.value;
var holdflag='true';
var billflag='true';

if(isbill=='onhold'){
if(chgStatus=='OH'){

showDialog({msg:"Please Change The Status",type:1,parentwindow:self});
return;
}
else if(chgStatus!='BB'){

showDialog({msg:"You Can Change the Status to Billable Only",type:1,parentwindow:self});
return;
}
else{
window.opener.IC.util.common.childUnloadEventHandler();
submitForm(targetFormName,'mailtracking.mra.defaults.changestatussave.do');

}
}
else if(isbill=='billable'){
if(chgStatus=='BB'){
showDialog({msg:"Please Change The Status",type:1,parentwindow:self});
return;
}
else if(chgStatus!='OH'){

showDialog({msg:"You Can Change the Status to OnHold Only",type:1,parentwindow:self});
return;
}
else{
window.opener.IC.util.common.childUnloadEventHandler();
submitForm(targetFormName,'mailtracking.mra.defaults.changestatussave.do');

}

}
else if(isbill=='proformabillable'){
if(chgStatus=='PB'){
showDialog({msg:"Please Change The Status",type:1,parentwindow:self});
return;
}
else if(chgStatus!='PO'){

showDialog({msg:"You Can Change the Status to ProformaOnHold Only",type:1,parentwindow:self});
return;
}
else{

window.opener.IC.util.common.childUnloadEventHandler();
submitForm(targetFormName,'mailtracking.mra.defaults.changestatussave.do');

}

}
else if(isbill=='proformaonhold'){
if(chgStatus=='PO'){
showDialog({msg:"Please Change The Status",type:1,parentwindow:self});
return;
}
else if(chgStatus!='PB'){

showDialog({msg:"You Can Change the Status to ProformaBillable Only",type:1,parentwindow:self});
return;
}
else{

window.opener.IC.util.common.childUnloadEventHandler();
submitForm(targetFormName,'mailtracking.mra.defaults.changestatussave.do');
}

}
else if(isbill=='withdrawnproforma'){

if(chgStatus=='WP'){
showDialog({msg:"Please Change The Status",type:1,parentwindow:self});
return;
}
else if(chgStatus!='OH'&& chgStatus!='BB'){

showDialog({msg:"You Can Change the Status to Billable/OnHold",type:1,parentwindow:self});
return;
}
else{

window.opener.IC.util.common.childUnloadEventHandler();
submitForm(targetFormName,'mailtracking.mra.defaults.changestatussave.do');

}

}

/*else if(isbill=='withdrawndirect'){
//Commented as a part of ICRD-211662
if(chgStatus=='WD'){
showDialog({msg:"Please Change The Status",type:1,parentwindow:self});
return;
}
 if(chgStatus!='OH'&& chgStatus!='BB'){

showDialog({msg:"You Can Change the Status to Billable/OnHold",type:1,parentwindow:self});
return;
}
else{

window.opener.IC.util.common.childUnloadEventHandler();
submitForm(targetFormName,'mailtracking.mra.defaults.changestatussave.do');

}*/


//Commented as a part of ICRD-211662
/*else if(isbill=='directbillable'){

if(chgStatus=='DB'){
showDialog({msg:"Please Change The Status",type:1,parentwindow:self});
return;
}
else if(chgStatus!='WD'){

showDialog({msg:"You Can Change the Status to Withdrawn-Direct only",type:1,parentwindow:self});
return;
}
}*/
else{

window.opener.IC.util.common.childUnloadEventHandler();
submitForm(targetFormName,'mailtracking.mra.defaults.changestatussave.do');

}




}
