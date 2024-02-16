<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){

	var frm=targetFormName;


   onScreenLoad();
	with(frm){

	evtHandler.addEvents("btList","listBooking()",EVT_CLICK);
    evtHandler.addEvents("btClear","clear()",EVT_CLICK);
    evtHandler.addEvents("btnAttach","attachMailAWB()",EVT_CLICK);
    evtHandler.addEvents("btClose","window.close()",EVT_CLICK);

		evtHandler.addIDEvents("originOELov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.orginOfBooking.value,'Airport','1','orginOfBooking','','0')",EVT_CLICK);
		evtHandler.addIDEvents("destnOELov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.viaPointOfBooking.value,'Airport','1','viaPointOfBooking','','0')",EVT_CLICK);
		evtHandler.addIDEvents("destnLov","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.destinationOfBooking.value,'Airport','1','destinationOfBooking','','0')",EVT_CLICK);
		evtHandler.addIDEvents("stnlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.elements.stationOfBooking.value,'Station','1','stationOfBooking','','0')",EVT_CLICK);
		evtHandler.addIDEvents("agentlov","showLOV()",EVT_CLICK);
	    evtHandler.addIDEvents("productLov","displayProductLov('products.defaults.screenloadProductLov.do',targetFormName.elements.mailProduct.value,'mailProduct','0')",EVT_CLICK);
        evtHandler.addIDEvents("custCodeLov","showCustomerLov()",EVT_CLICK);
		evtHandler.addIDEvents("sccLOV","displayLOV('showScc.do','Y','Y','showScc.do',targetFormName.elements.mailScc,'mailScc','1','mailScc','',0)",EVT_CLICK);
	}




}

function onScreenLoad(){

  if(targetFormName.elements.popUpFlag.value == "N"){
			frm = self.opener.targetFormName;
			frm.action="mailtracking.defaults.mailsearch.listload.do?navigationMode=list";
			window.opener.IC.util.common.childUnloadEventHandler(); 
			frm.submit();
      	   window.close();	
          	}
}
	function showLOV(){
	var textfiledDesc="";
    var formCount=1;
	var code = targetFormName.elements.agentCode.value;
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=agentCode&formNumber=1&textfiledDesc="+textfiledDesc+"&agentCode="+code;
	openPopUpWithHeight(appPath+"/shared.defaults.agent.screenloadagentlov.do"+"?textfiledObj=agentCode"+"&formNumber="+formCount+"&textfiledDesc="+textfiledDesc+"&agentCode="+code,'500');
}


function displayProductLov(strAction,lovValue,textfiledObj,formNumber){
	var index = 0;
	var strUrl = strAction+"?mailProduct="+lovValue+"&productObject="+textfiledObj+"&formNumber="+formNumber+"&sourceScreen=listBooking&activeProduct=Y&rowIndex="+index;
	displayLOV(strUrl,'Y','Y',strUrl,targetFormName.elements.mailProduct.value,'productCode','1','mailProduct','',0);
}

function showCustomerLov()
{

    var encodedStrCode = targetFormName.elements.customerCode.value;
	var strUrl = "showCustomer.do?multiselect=N&pagination=Y&lovaction=showCustomer.do&code="+encodedStrCode+"&title=Customer Code&formCount=1&lovTxtFieldName=customerCode&lovDescriptionTxtFieldName=";
	var options = {url:strUrl}
	var lov = CustomLovHandler.invokeCustomerLov(options);
	lov._parentOkFnHook="checkForCustomer()";
	self._lovOkFnHook="checkForCustomer()";
	lov._isLov = "true";
	window._isLov="true";
}

function clear()
{
submitForm(targetFormName,'mailtracking.defaults.listconsignment.mailbookingpopup.clear.do');
}

function listBooking()
{ 

targetFormName.elements.actionName.value="LIST";//added by A-7371 as part of ICRD-228233
submitForm(targetFormName,'mailtracking.defaults.listconsignment.mailbookingpopup.list.do');

}

function attachMailAWB()
{


var frm = targetFormName;
var consignmentNumber=targetFormName.elements.consignmentDocument.value;
  if(validateSelectedCheckBoxes(targetFormName,'selectedMailbagId',1,1)){
	var chkboxes = document.getElementsByName("selectedMailbagId");

  

 
  if( consignmentNumber.length>0 ){
     showDialog({msg:'<common:message bundle="searchconsignmentResources" key="mailtracking.defaults.mailbookingpopup.msg.warning.attachmultiple" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1'); 
						}
						});
						
						}else if(targetFormName.elements.destMismatchFlag.value=='Y'){
							
						}
						
						else{ 
						
					
						var chkboxes = document.getElementsByName("selectedMailbagId");

	var selectedIndex="";
	if(chkboxes != null){

	for(var i=0;i<(chkboxes.length);i++) {

	   if(chkboxes[i].checked==true) {

	      selectedIndex = i;

			submitForm(targetFormName,"mailtracking.defaults.listconsignment.mailbookingpopup.attachMailAWB.do?selectedMail="+selectedIndex);
			 
			}

	}
	}
						
	

						
						
						}
						
  
  
  
  
	
	}

	}





function performPagination(lastPg,displayPg){
var frm = targetFormName;
frm.elements.actionName.value="NAVIGATION";//modified by A-7371 as part of ICRD-228233
document.forms[0].elements.lastPageNum.value=lastPg;
	document.forms[0].elements.displayPage.value=displayPg;
	document.forms[0].action="mailtracking.defaults.listconsignment.mailbookingpopup.list.do";
	document.forms[0].submit();

}
/**
*function to Confirm Dialog
*/
function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
	    if(dialogId == 'id_1'){
		var chkboxes = document.getElementsByName("selectedMailbagId");

	var selectedIndex="";
	if(chkboxes != null){

	for(var i=0;i<(chkboxes.length);i++) {

	   if(chkboxes[i].checked==true) {

	      selectedIndex = i;

			submitForm(targetFormName,"mailtracking.defaults.listconsignment.mailbookingpopup.attachMailAWB.do?selectedMail="+selectedIndex);
			 
			}

	}
	}
	    }
	   
	}
}


/**
*function to Non-Confirm Dialog
*/
function screenNonConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
		
		
	}
}

function confirmPopupMessage(){ 
 if(targetFormName.elements.destMismatchFlag.value=='Y'){
	
	 
 var selectedIndex=targetFormName.elements.selectedAwbIndex.value;

	     
targetFormName.elements.destMismatchFlag.value="N";
			submitForm(targetFormName,"mailtracking.defaults.listconsignment.mailbookingpopup.attachMailAWB.do?selectedAwbIndex="+selectedIndex);
			
			
  }
   
   
  }
  function nonconfirmPopupMessage(){
	  targetFormName.elements.destMismatchFlag.value="";
	}
	
