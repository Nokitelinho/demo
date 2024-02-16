<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>


function screenSpecificEventRegister()
{
	var frm=targetFormName;


	with(targetFormName){

	evtHandler.addEvents("orglov","showOrginLov(this)",EVT_CLICK);
	evtHandler.addEvents("destlov","showDestinationLov(this)",EVT_CLICK);
	evtHandler.addEvents("btnOk","ok()",EVT_CLICK);
	evtHandler.addEvents("btClose","window.close()",EVT_CLICK);
	}
onloadPopup(frm);

}
function showDestinationLov(obj) {

	var index = obj.id.split("destlov")[1];
	if(targetFormName.elements.orgDstLevel.value == 'A'){
		displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.popupDestn.value,'destination code','1','popupDestn','',index);
	}else{
	displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.popupDestn.value,'destination code','1','popupDestn','',index);
	}

}
function showOrginLov(obj) {

	var index = obj.id.split("orglov")[1];
	if(targetFormName.elements.orgDstLevel.value == 'A'){
		displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.popupOrigin.value,'origin code','1','popupOrigin','',index);
	}else{
	displayLOV('showCity.do','N','Y','showCity.do',targetFormName.elements.popupOrigin.value,'origin code','1','popupOrigin','',index);
	}

}

function ok(){

	var org=new Array();
	org=document.getElementsByName("popupOrigin");
	var dst=new Array();
	dst=document.getElementsByName("popupDestn");
	var flag=0;
    var opFlag = new Array();
	opFlag=document.getElementsByName("operationFlag");

	//alert(opflag[0].value);

     for(var i=0;i<org.length-1;i++){

			//alert("else");

			//alert(opflag[i].value);
		if((org[i].value=="" || dst[i].value=="") && (opFlag[i].value!="NOOP")){
    	showDialog({msg:"Please enter origin and destination", type:1, parentWindow:self});
    	flag++;

		}

     }

		if(flag==0){


	submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.addratelineok.do');

	}


}
function addRateLine()
{
var org=new Array();
	org=document.getElementsByName("popupOrigin");
	var dst=new Array();
	dst=document.getElementsByName("popupDestn");
	var opFlag = new Array();
	opFlag=document.getElementsByName("operationFlag");
	var flag=0;

           //alert("org len"+org.length);
		for(var i=0;i<org.length-1;i++){

		if((org[i].value=="" || dst[i].value=="") && (opFlag[i].value!="NOOP")){
    	showDialog({msg:"Please enter origin and destination", type:1, parentWindow:self});
    	flag++;
		}
	 }
		if(flag==0){
			//alert("inside");
			//submitForm(targetFormName,'mailtracking.mra.defaults.maintainupuratecard.addratelinerow.do');
			//alert('opflag'+targetFormName.operationFlag.value);
			addTemplateRow('targetWeightRow','targetWeightTableBody','operationFlag');
	}

}


function deleteRateLine()
{
var chkBoxIds = document.getElementsByName('popCheck');
	var isError = 1;
	for(var i = 0; i < chkBoxIds.length; i++){
		if(chkBoxIds[i].checked){
			isError = 0;
		}
	}
	if(isError == 1) {
		showDialog({msg:'Please select a row',type:1,parentWindow:self});
	}
	else{
		deleteTableRow('popCheck','operationFlag');
	}
}

function onloadPopup(frm){
	var arr=new Array();
	//alert("onloadPopup");
	arr=document.getElementsByName("popupOrigin");

//alert(arr.length);
//document.getElementsByName("popupOrigin")[arr.length-2].focus();
var flag=targetFormName.elements.okFlag.value;

//alert('flag '+flag);


 if(flag == 'OK'){

		window.opener.targetFormName.action
						= "mailtracking.mra.defaults.maintainupuratecard.screenreload.do";
		window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.targetFormName.submit();
		window.close();
	}
}

function confirmMessage() {
	}
function screenConfirmDialog(targetFormName, dialogId) {
}
function screenNonConfirmDialog(targetFormName, dialogId) {
}
function nonconfirmMessage() {
	window.close();
}
