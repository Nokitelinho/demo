<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>



function screenSpecificEventRegister() {
	var frm=targetFormName;

 	with(targetFormName){
				evtHandler.addEvents("btCancel","CloseFn()",EVT_CLICK);
				evtHandler.addEvents("btOk","OK(targetFormName)",EVT_CLICK);
				evtHandler.addEvents("btncreateCCA","createCCA(targetFormName)",EVT_CLICK);
 	}
 	
	onScreenLoad();
}

function onScreenLoad(){

	if(targetFormName.elements.popupon.value=='Y'){
		targetFormName.elements.popupon.value='N';
		window.close();
		window.opener.submitForm(window.opener.targetFormName,'mailtracking.mra.defaults.maintaincca.listccadetailsforOK.do');
	}
}
function OK(targetFormName){

	targetFormName.elements.popupon.value='Y';	
	var frm=targetFormName;
	var counter=0;
	var checkBoxesId = document.getElementsByName('rowCount');
	var popupheight = document.body.clientHeight*50/100;
	var popupwidth = document.body.clientWidth*30/100;

   if(validateSelectedCheckBoxes(frm,'rowCount','1','1')){
		for (var i =0; i < checkBoxesId.length; i++){
			if(checkBoxesId[i].checked){
				counter=i;
			}
		}
		targetFormName.elements.selectedRow.value=counter;
		frm.action="mailtracking.mra.defaults.maintaincca.selectccaOK.do";
		frm.submit();
	}
}

function createCCA(targetFormName){
var checkBoxesId = document.getElementsByName('rowCount');
var ccaSta=  document.getElementsByName('ccaStatus');
var hasEntered='N';
for (var i =0; i < checkBoxesId.length; i++){


if(hasEntered!='Y'){
if(ccaSta[i].value=="N"){
		showDialog({msg:"<common:message bundle='maintainCCA' key='mailtracking.mra.defaults.maintaincca.cannotcreate' />",type:1,parentWindow:self});
		hasEntered='Y';
		
	}
	}
	}
	if(hasEntered=='N'){
		window.opener.IC.util.common.childUnloadEventHandler();
		window.close();
		window.opener.submitForm(window.opener.targetFormName,'mailtracking.mra.defaults.maintaincca.listccadetailsforOK.do?createCCAFlg=Y&fromScreen=creatCCA');
	}
}

function CloseFn(){
	window.opener.IC.util.common.childUnloadEventHandler();
	window.close();
	window.opener.submitForm(window.opener.targetFormName,'mailtracking.mra.defaults.maintaincca.clearscreen.do');
}