<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;
   targetFormName.elements.conDocNo.focus();
  
  onScreenLoad();
  insertNewRoutingDetails();
   with(frm){

     //CLICK Events
      
        evtHandler.addEvents("btnList","list()",EVT_CLICK);
     	evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
     	evtHandler.addEvents("lnkAddRoute","addRoute()",EVT_CLICK);
     	evtHandler.addEvents("lnkDeleteRoute","deleteRoute()",EVT_CLICK);
     	
     	evtHandler.addEvents("btnSave","saveConDoc()",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeConDoc()",EVT_CLICK);

        evtHandler.addEvents("contype","contypeTabOut()",EVT_BLUR);
     	//Invoking Lov
	evtHandler.addEvents("polLov","invokeLov(this,'polLov')",EVT_CLICK);
	evtHandler.addEvents("pouLov","invokeLov(this,'pouLov')",EVT_CLICK);
	
     	if(frm.elements.btnSave.disabled){
     	    disableLink(document.getElementById('lnkAddRoute'));
	    disableLink(document.getElementById('lnkDeleteRoute'));
	  
  	}

  	if(frm.elements.selectRoute != null){
 	      evtHandler.addEvents("selectRoute","toggleTableHeaderCheckbox('selectRoute',targetFormName.masterRoute)",EVT_CLICK);
	}

	

     //BLUR Events
      

	evtHandler.addEvents("flightRouteCarrierCode","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);
	evtHandler.addEvents("flightRouteNumber","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);
	evtHandler.addEvents("depRouteDate","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);
	evtHandler.addEvents("polRoute","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);
	evtHandler.addEvents("pouRoute","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);

   	}
}

function onScreenLoad(){
  frm=targetFormName;
     if(frm.elements.routingStatus.value=='OK'){
     frm.elements.routingStatus.value="";
	 if(frm.elements.fromScreen.value=="MAILARRIVAL"){
	 frm.elements.fromScreen.value="";
	   window.opener.targetFormName.action = "mailtracking.defaults.mailarrival.listflight.do";
	 } else{
     window.opener.targetFormName.action = "mailtracking.defaults.mailmanifest.listmailmanifest.do";
	 }
	window.opener.targetFormName.submit();				
	window.closeNow();
     }
}
function insertNewRoutingDetails(){

 if(targetFormName.elements.newRoutingFlag.value == 'Y'){ 
    targetFormName.elements.newRoutingFlag.value = "N";	
	addRoute();
	targetFormName.elements.conDate.disabled=false;
	targetFormName.elements.conDate.focus();
	 
 }
}
function contypeTabOut(){
    if(targetFormName.elements.newRoutingFlag.value == 'N'){
	targetFormName.elements.newRoutingFlag.value = "";	
   targetFormName.elements.flightRouteCarrierCode[0].focus();
   }
}



function invokeLov(obj,name){

   var index = obj.id.split(name)[1];     
	 _pol = targetFormName.elements.polRoute[index].value;
	 _pou = targetFormName.elements.pouRoute[index].value;
   if(name == "polLov"){
         displayLOV('showAirport.do','N','Y','showAirport.do',_pol,'Airport','1','polRoute','',index);
   }
   if(name == "pouLov"){
         displayLOV('showAirport.do','N','Y','showAirport.do',_pou,'Airport','1','pouRoute','',index);
   }
  
}



/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

function list(){
   /*frm=targetFormName;
   targetFormName.elements.lastPageNum.value = 0;
   targetFormName.elements.displayPage.value = 1;
  submitAction(frm,'/mailtracking.defaults.consignment.listconsignment.do?countTotalFlag=N');*/
  submitAction(targetFormName,'/mailtracking.defaults.mailmanifest.attachroutinglist.do');
  
}



function clear(){
   frm=targetFormName;
  // submitAction(frm,'/mailtracking.defaults.consignment.clearconsignment.do');
  submitAction(targetFormName,'/mailtracking.defaults.mailmanifest.attachroutingclear.do');

}



function addRoute(){
   frm=targetFormName;
   addTemplateRow('routeTemplateRow','routeTableBody','routeOpFlag');

}


function deleteRoute(){
   frm=targetFormName;
   var selectRoute = document.getElementsByName('selectRoute');
   if(selectRoute.length > 0){
       if(validateSelectedCheckBoxes(frm,'selectRoute',selectRoute.length,1)){
   	   

   	    deleteTableRow('selectRoute','routeOpFlag');
       }
   }

}



function closeConDoc(){
	if(frm.elements.fromScreen.value=="MAILARRIVAL"){
	   frm.elements.fromScreen.value="";
	   window.opener.targetFormName.action = "mailtracking.defaults.mailarrival.listflight.do";
	 } else{
     window.opener.targetFormName.action = "mailtracking.defaults.mailmanifest.listmailmanifest.do";
	 }
	window.opener.targetFormName.submit();				
	window.closeNow();
}

function saveConDoc(){
   frm=targetFormName;

   
   var incomplete = validateRoutingDetails();
   if(incomplete == "N"){
        return;
   }

  submitAction(targetFormName,'/mailtracking.defaults.mailmanifest.attachroutingsave.do');
  // submitAction(frm,'/mailtracking.defaults.consignment.saveconsignment.do');
}



function validateRoutingDetails(){


	var opFlag =document.getElementsByName("routeOpFlag");

      var flightCarrierCode =document.getElementsByName("flightCarrierCode");
      var flightNumber =document.getElementsByName("flightNumber");
       var depDate =document.getElementsByName("depDate");
      var pol =document.getElementsByName("pol");
       var pou =document.getElementsByName("pou");
      for(var i=0;i<flightCarrierCode.length;i++){

       if(opFlag[i].value != "NOOP"){

       	  if(flightCarrierCode[i].value.length == 0){
		  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.fltcarcodeempty" />', type:1, parentWindow:self}); 
       	    
       	     flightCarrierCode[i].focus();
       	     return "N";
       	 }


       if(flightNumber[i].value.length == 0){
	   showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.fltnumempty" />', type:1, parentWindow:self}); 
       	    
       	     flightNumber[i].focus();
       	     return "N";
       	 }

       if(depDate[i].value.length == 0){
	   showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.fltdtempty" />', type:1, parentWindow:self}); 
       	    
       	     depDate[i].focus();
       	     return "N";
	 }

       if(pol[i].value.length == 0){
	   showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.polempty" />', type:1, parentWindow:self}); 
             	  
             	     pol[i].focus();
             	     return "N";
      	 }

	  if(pou[i].value.length == 0){
	  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.pouempty" />', type:1, parentWindow:self}); 
	    
	     pou[i].focus();
	     return "N";
	 }

	}

      }

}


/**
*function to Confirm Dialog
*/
function screenConfirmDialog(frm, dialogId) {

	while(frm.elements.currentDialogId.value == ''){

	}

	if(frm.elements.currentDialogOption.value == 'Y') {
	    if(dialogId == 'id_1'){
		submitAction(frm,'/mailtracking.defaults.consignment.deleteconsignment.do');
	    }
	    if(dialogId == 'id_2'){
	        location.href = appPath + "/home.jsp";
	    }
	    if(dialogId == 'id_3'){
		//submitAction(frm,'/mailtracking.defaults.consignment.saveconsignment.do?fromPopupflg=Y');
		submitAction(frm,'/mailtracking.defaults.consignment.addmail.do?fromPopupflg=Y');
		
	    }
	    if(dialogId == 'id_4'){
		 submitForm(targetFormName, appPath + '/mailtracking.defaults.consignment.saveandlistconsignment.do?countTotalFlag=Y&fromPopupflg=Y');		
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
		if(dialogId == 'id_2'){

		}
		if(dialogId == 'id_3'){
			return;
		}
		if(dialogId == 'id_4'){
		    submitForm(targetFormName, appPath + '/mailtracking.defaults.consignment.listconsignment.do?countTotalFlag=Y&fromPopupflg=N');
		}
	}
}


 