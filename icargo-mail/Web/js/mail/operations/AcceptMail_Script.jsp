<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
var mailOOEPrv =null;
var mailDOEPrv =null;
var mailSCPrv =null;
var prvRowCnt =null;
function screenSpecificEventRegister()
{
   var frm=targetFormName;

   onScreenLoad();
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btnList","listContainerDetails()",EVT_CLICK);
     	evtHandler.addIDEvents("addLink","addMails()",EVT_CLICK);
		evtHandler.addIDEvents("modifyLink","modifyMails()",EVT_CLICK);
     	evtHandler.addIDEvents("deleteLink","deleteMails()",EVT_CLICK);
     	evtHandler.addEvents("btOk","acceptMails()",EVT_CLICK);
     	evtHandler.addEvents("btnCancel","cancelMails()",EVT_CLICK);
     	evtHandler.addEvents("btnCaptureDamage","showDamagePopup()",EVT_CLICK);
     	evtHandler.addEvents("btnLookup","lookupDocument()",EVT_CLICK);
     	//evtHandler.addEvents("btnCarditEnquiry","carditEnquiry()",EVT_CLICK);
		evtHandler.addEvents("btnScanTime","changeScanTime()",EVT_CLICK);
		evtHandler.addEvents("btnNewContainer","newContainer()",EVT_CLICK);
		evtHandler.addEvents("btnCarIdList","listCartIds()",EVT_CLICK);
	
	//Invoking Lov
		evtHandler.addEvents("despatchPALov","invokeLov(this,'despatchPALov')",EVT_CLICK);
		evtHandler.addEvents("despatchOOELov","invokeLov(this,'despatchOOELov')",EVT_CLICK);
		evtHandler.addEvents("despatchDOELov","invokeLov(this,'despatchDOELov')",EVT_CLICK);
		evtHandler.addEvents("mailOOELov","invokeLov(this,'mailOOELov')",EVT_CLICK);
		evtHandler.addEvents("mailDOELov","invokeLov(this,'mailDOELov')",EVT_CLICK);
		evtHandler.addEvents("mailSCLov","invokeLov(this,'mailSCLov')",EVT_CLICK);
		evtHandler.addEvents("despatchSCLov","invokeLov(this,'despatchSCLov')",EVT_CLICK);
		evtHandler.addEvents("mailCarrierLov","invokeLov(this,'mailCarrierLov')",EVT_CLICK);
	evtHandler.addIDEvents("paLov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.elements.paCode.value,'PA','1','paCode','',0)", EVT_CLICK);
	
	 	if(frm.selectDespatch != null){
	    evtHandler.addEvents("selectDespatch","toggleTableHeaderCheckbox('selectDespatch',targetFormName.masterDespatch)",EVT_CLICK);
		}
		if(frm.selectMailTag != null){
			evtHandler.addEvents("selectMailTag","toggleTableHeaderCheckbox('selectMailTag',targetFormName.masterMailTag)",EVT_CLICK);
		}

    //BLUR Events
        evtHandler.addEvents("despatchDSN","despatchDSN()",EVT_BLUR);
        evtHandler.addEvents("mailDSN","mailDSN()",EVT_BLUR);
		evtHandler.addEvents("mailRSN","mailRSN()",EVT_BLUR);
		evtHandler.addEvents("mailWt","mailWeight()",EVT_BLUR);
		evtHandler.addEvents("containerNo","validatecontainernumberformat()",EVT_BLUR);
	
		evtHandler.addEvents("accNoBags","operationFlagChangeOnEdit(this,'despatchOpFlag');",EVT_BLUR);
		evtHandler.addEvents("accWt","operationFlagChangeOnEdit(this,'despatchOpFlag');populateDespatchVolume();",EVT_BLUR);
	
		/*	evtHandler.addEvents("mailScanDate","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
			evtHandler.addEvents("mailScanTime","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
			evtHandler.addEvents("mailCarrier","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
			evtHandler.addEvents("mailDamaged","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
		*/
			evtHandler.addEvents("statedWt","operationFlagChangeOnEdit(this,'despatchOpFlag');populateDespatchVolume()",EVT_BLUR);
	//CHANGE Events
     	//evtHandler.addEvents("pou","defaultDestn(this.form)", EVT_CHANGE);
     	evtHandler.addEvents("barrowCheck", "disableBulkDestn(this.form)", EVT_CHANGE);
     	evtHandler.addEvents("pou","populateBulkDestn(this.form)", EVT_CHANGE);

    //EVT_KEYPRESS
		//evtHandler.addEvents("statedWt","decimalLimitter()",EVT_KEYPRESS);
		//evtHandler.addEvents("accWt","decimalLimitter()",EVT_KEYPRESS);

		evtHandler.addEvents("paBuilt","enContainerJnyIDField()",EVT_CLICK);
		evtHandler.addEvents("barrowCheck","setValue()",EVT_BLUR);
		evtHandler.addEvents("mailbagId","populateMailDetails(this)",EVT_BLUR);
		evtHandler.addEvents("mailOOE","populateMailbagId(this)",EVT_CHANGE);//modified for ICRD-276741
		evtHandler.addEvents("mailDOE","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("mailCat","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("mailSC","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("mailYr","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("mailDSN","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("mailRSN","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("mailHNI","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("mailRI","populateMailbagId(this)",EVT_CHANGE);
		evtHandler.addEvents("mailWt","populateMailbagId(this)",EVT_BLUR);
		evtHandler.addEvents("mailOOE","triggerEvent(this)",EVT_BLUR);
		evtHandler.addEvents("mailDOE","triggerEvent(this)",EVT_BLUR);
		evtHandler.addEvents("mailSC","triggerEvent(this)",EVT_BLUR);
		evtHandler.addEvents("weightUnit","populateMailbagId(this)",EVT_CHANGE);//added by A-8353 for ICRD 274944 
   	}
	var globalObj;
}

function validatecontainernumberformat(){
var frm=targetFormName;
if(!frm.elements.barrowCheck.checked){

     validateFields(frm.containerNo,-1,'Container Number',0,true,true);
}

}
/***************************REAPPLYING EVENTS****************************/
var applySpecifcEventsForMail = "NO";
var applySpecifcEventsForDespatch = "NO";
function reapplyEvent() { 
	var evtHandler=new EventHandler();
	evtHandler.initEventHandler();

	if(applySpecifcEventsForMail == "YES"){
		applySpecifcEventsForMail = "NO";
		
		var _blurMapp1=getUniqueComponentNamesForEvent(EVT_CLICK);
		
		updateEventsFromMap(_blurMapp1,EVT_CLICK);
	}
	if(applySpecifcEventsForDespatch == "YES"){	
		applySpecifcEventsForDespatch = "NO";
		var _blurMapp1=getUniqueComponentNamesForEvent(EVT_CLICK);
		var _blurMapp2=getUniqueComponentNamesForEvent(EVT_FOCUS);
		var _blurMapp3=getUniqueComponentNamesForEvent(EVT_BLUR);

		updateEventsFromMap(_blurMapp1,EVT_CLICK);
		updateEventsFromMap(_blurMapp2,EVT_FOCUS);
		updateEventsFromMap(_blurMapp3,EVT_BLUR);
	}
	
	//purge(document.getElementById("div1"));	
	//purge(document.getElementById("div2"));	

	applyClickEventsForTableRows();
}
/**************************************************************************/


function screenSpecificTabSetup(){
   setupPanes('container1','tab1');
   displayTabPane('container1','tab2');
}

function invokeLov(obj,name){

   var index = obj.id.split(name)[1];

   if(name == "despatchPALov"){
         displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.despatchPA.value,'PA','0','despatchPA','',index);
   }
   if(name == "despatchOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.despatchOOE.value,'OfficeOfExchange','0','despatchOOE','',index);
   }
   if(name == "despatchDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.despatchDOE.value,'OfficeOfExchange','0','despatchDOE','',index);
   }
   if(name == "mailOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.mailOOE.value,'OfficeOfExchange','0','mailOOE','',index);
   }
   if(name == "mailDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.mailDOE.value,'OfficeOfExchange','0','mailDOE','',index);
   }
   if(name == "mailSCLov"){
         displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.mailSC.value,'OfficeOfExchange','0','mailSC','',index);
   }
   if(name == "mailCarrierLov"){
         displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.mailCarrier.value,'Airline','0','mailCarrier','',index);
   }
     if(name == "despatchSCLov"){
        displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.despatchSC.value,'OfficeOfExchange','0','despatchSC','',index);
   }
  }


function onScreenLoad(){
  frm=targetFormName;
  if(targetFormName.elements.containerNo.readOnly==false || targetFormName.elements.containerNo.disabled==false){
      	    			targetFormName.elements.containerNo.focus();
          	}

   var str = frm.elements.assignToFlight.value;
   frm.elements.containerJnyId.disabled=true;
   disableField(document.getElementById('paLov'));
   if(frm.paBuilt){
   		if(frm.elements.paBuilt.checked){
   			frm.elements.paCode.disabled=false;
			enableField(document.getElementById('paLov'));
   		}else{
   			frm.elements.paCode.disabled=true;
			disableField(document.getElementById('paLov'));
  	 	}
   }else{
  	 	frm.elements.paCode.disabled=true;
		disableField(document.getElementById('paLov'));
   }
   if(frm.elements.containerType.value == "B"){
   frm.elements.barrowCheck.checked=true;
   }
   if(frm.elements.barrowCheck.checked){
   frm.elements.containerType.value="B";
      //disableField(frm.destn);
      //disableField(frm.destnImage);
      //frm.destn.disabled = true;
      //frm.destnImage.disabled = true;
   }else{
		frm.elements.containerType.value="U";
      	enableField(frm.elements.destn);
      	enableField(frm.elements.destnImage);
      //frm.destn.disabled = false;
      //frm.destnImage.disabled = false;
   }

   if(str == "CARRIER"){
   		disableField(frm.elements.pou);
   		disableField(frm.elements.destn);
   		disableField(frm.elements.destnImage);
   		frm.elements.destn.disabled = true;
   	    /*frm.pou.disabled = true;
        frm.destn.disabled = true;
        frm.destnImage.disabled = true;*/
   }


   if(frm.elements.warehouse.value == ""){
   	  disableField(frm.elements.location);
      //frm.location.disabled = true;
   }


   if(frm.elements.preassignFlag.value == "Y"){
   		   disableField(frm.elements.btnNewContainer);
   		   disableField(frm.elements.containerNo);
         /* frm.btnNewContainer.disabled = true;
          frm.containerNo.disabled = true;*/
   }else{

       if(frm.elements.disableFlag.value == "Y"){
       	    enableField(frm.elements.btnList);
            enableField(frm.elements.containerNo);
            enableField(frm.elements.barrowCheck);
			disableField(document.getElementById("addLink"));
			disableField(document.getElementById("modifyLink"));
			disableField(document.getElementById("deleteLink"));
           /*frm.btnList.disabled = false;
            frm.containerNo.disabled = false;
            frm.containerType.disabled = false;*/

            disableField(frm.elements.btOk);
            disableField(frm.elements.btnLookup);
			//disableField(frm.btnCarditEnquiry);
			disableField(frm.elements.btnCaptureDamage);
			disableField(frm.elements.btnScanTime);
			disableField(frm.elements.btnCarIdList);
			disableField(frm.elements.bellyCarditId);


            //frm.btOk.disabled = true;
            //frm.btnLookup.disabled = true;
	  if(str != "CARRIER"){
	    //frm.destn.value = frm.pou.value;
	    frm.elements.prevPou.value = frm.elements.pou.value;
	  }
       }else{
	        //Added by a-7540 
	         if(frm.elements.addRowEnableFlag.value=='Y'){	//added by A-7371 as part of ICRD-271301
			 frm.elements.addRowEnableFlag.value="";
	          //addTemplateRow('mailTemplateRow','mailTableBody','mailOpFlag');  //commented by A-7540 for ICRD-322097 as it is not required for POSM
			 }
            displayTabPane('container1','tab2');
		    populateScanDateTimeforExport();
            disableField(frm.elements.btnList);
            disableField(frm.elements.containerNo);
            disableField(frm.elements.barrowCheck);
            disableField(frm.elements.pou);
			disableField(frm.elements.destn);
			disableField(frm.elements.destnImage);
            //frm.btnList.disabled = true;
            //frm.containerNo.disabled = true;
            //frm.containerType.disabled = true;
	       //frm.btOk.disabled = false;
	       //frm.btnLookup.disabled = false;

            enableField(frm.elements.btOk);
            enableField(frm.elements.btnLookup);
			//enableField(frm.btnCarditEnquiry);
			enableField(frm.elements.btnCaptureDamage);
			enableField(frm.elements.btnScanTime);
			enableField(frm.elements.btnCarIdList);
			enableField(frm.elements.bellyCarditId);
       }

   }

   handleTab();
 
   if(frm.elements.embargoFlag.value == "embargo_exists"){
        frm.elements.embargoFlag.value = "";
		openPopUp("reco.defaults.showEmbargo.do", 900,400);
   }

   if(frm.elements.popupCloseFlag.value == "Y"){
        frm = self.opener.targetFormName;
       	frm.action="mailtracking.defaults.mailacceptance.refreshmailacceptance.do?assignToFlight="+str;
       	frm.method="post";
		window.opener.IC.util.common.childUnloadEventHandler(); 
       	frm.submit();
       	window.closeNow();
       	return;
    }

    if(frm.elements.popupCloseFlag.value == "showDamagePopup"){
    	var selectMailBags = "";
    	var chkbox =document.getElementsByName("selectMailTag");
    	var opFlag =document.getElementsByName("mailOpFlag");
    	for(var i=0; i<chkbox.length;i++){
    	  if(opFlag[i].value != "NOOP"){
    	    if(chkbox[i].checked) {
    		selectMailBags = chkbox[i].value;
    		var strAction="mailtracking.defaults.damagedetails.screenload.do";
    		var strUrl=strAction+"?selectedMailBag="+selectMailBags+"&damageFromScreen=ACCEPTMAIL";
    		openPopUp(strUrl,600,240);
    		frm.elements.popupCloseFlag.value = "";
    		break;
    	    }
    	  }
    	}
	}


	if(frm.elements.popupCloseFlag.value == "showScanTimePopup"){
	    frm.elements.popupCloseFlag.value = "";
	    var selectMail = "";
	    var cnt1 = 0;
	    var chkbox =document.getElementsByName("selectMailTag");
	    var opFlag =document.getElementsByName("mailOpFlag");
	    if(validateSelectedCheckBoxes(frm,'selectMailTag',chkbox.length,1)){

	    for(var i=0; i<chkbox.length;i++){

	     if(opFlag[i].value != "NOOP"){
		if(chkbox[i].checked) {
		   if(cnt1 == 0){
			  selectMail = chkbox[i].value;
			  cnt1 = 1;
		   }else{
			  selectMail = selectMail +","+chkbox[i].value;
		   }
		}
	      }
	    }

	      var strAction="mailtracking.defaults.mailacceptance.changescantime.do";
	      var strUrl=strAction+"?scanTimeFlag="+selectMail;
	      openPopUp(strUrl,500,190);
	   }

    }

    if(frm.elements.popupCloseFlag.value == "showLookUpPopup"){
        frm.elements.popupCloseFlag.value = "";
		var strAction="mailtracking.defaults.mailsearch.screenload.do";
		var dens = frm.density.value;
		var consignmentDoc = frm.consignmentDocNum.value;
    	var strUrl=strAction+"?screenMode=POPUP&density="+dens+"&consignmentDocument="+consignmentDoc;
		
	 	  openPopUp(strUrl,1350,660);
    }

    if(frm.elements.popupCloseFlag.value == "showCaptureMailtag"){     
	var chkbox =document.getElementsByName("selectMailTag");
	var opFlag =document.getElementsByName("mailOpFlag");
	var flag="true";
	var selectedMails = "";
	var cnt1 = 0;
	for(var i=0; i<chkbox.length-1;i++){
		if(chkbox[i].checked) {
		  
			if(cnt1 == 0){
				selectedMails = chkbox[i].value;
				cnt1 = 1;
			}else{
				selectedMails = selectedMails + "," + chkbox[i].value;
			}
			
		}
	 }



        frm.elements.popupCloseFlag.value = "";
	var dens = targetFormName.elements.density.value;
	//openPopUp("mailtracking.defaults.mailacceptance.screenloadcapturemailtagdetails.do?selectedRow="+selectedMails,"600","280");

	var strUrl = "mailtracking.defaults.mailacceptance.screenloadcapturemailtagdetails.do";
	var actStr = strUrl+"?selectedRow="+selectedMails+"&density="+dens+"&modify="+targetFormName.elements.modify.value;
	openPopUp(actStr,"610","320");
    }

}

function handleTab(){
    if(frm.elements.disableFlag.value != "Y"){
	if(targetFormName.TABPANE_ID_FLD.value == "tab1"){

		disableField(targetFormName.elements.btnCaptureDamage);
		disableField(targetFormName.elements.btnScanTime);
		//disableField(targetFormName.btnLookup);
		//disableField(targetFormName.btnCarditEnquiry);

		/*targetFormName.btnCaptureDamage.disabled = true;
		targetFormName.btnScanTime.disabled = true;
		targetFormName.btnLookup.disabled = true;*/
	}
	else{
		enableField(targetFormName.elements.btnCaptureDamage);
		enableField(targetFormName.elements.btnScanTime);
		enableField(targetFormName.elements.btnLookup);
		//enableField(targetFormName.btnCarditEnquiry);

		/*targetFormName.btnCaptureDamage.disabled = false;
		targetFormName.btnScanTime.disabled = false;
		targetFormName.btnLookup.disabled = false;*/
	}
    }
}

function selectTab(selectedtab){
	if(targetFormName.elements.disableFlag.value != "Y"){
	if(selectedtab == "tab1"){
		applySpecifcEventsForDespatch = "YES";
		reapplyEvent();

		disableField(targetFormName.elements.btnCaptureDamage);
		disableField(targetFormName.elements.btnScanTime);
		disableField(document.getElementById("modifyLink"));
		//disableField(targetFormName.btnCarditEnquiry);
		//disableField(targetFormName.btnLookup);

		/*targetFormName.btnCaptureDamage.disabled = true;
		targetFormName.btnScanTime.disabled = true;
		targetFormName.btnLookup.disabled = true;*/
	}
	else{

		enableField(targetFormName.elements.btnCaptureDamage);
		enableField(targetFormName.elements.btnScanTime);
		enableField(targetFormName.elements.btnLookup);
		enableField(document.getElementById("modifyLink"));
		//enableField(targetFormName.btnCarditEnquiry);
		/*targetFormName.btnCaptureDamage.disabled = false;
		targetFormName.btnScanTime.disabled = false;
		targetFormName.btnLookup.disabled = false;*/
	}
	}
}

function showDamagePopup(){

	var chkbox =document.getElementsByName("selectMailTag");
	var opFlag =document.getElementsByName("mailOpFlag");
	var damagedMails =document.getElementsByName("mailDamaged");
	if(validateSelectedCheckBoxes(frm,'selectMailTag',1,1)){

		  for(var i=0; i<chkbox.length;i++){

		     if(opFlag[i].value != "NOOP"){

			 if(chkbox[i].checked) {
			 	if(damagedMails[i].checked){

					damageMailBags();
					frm.popupCloseFlag.value = "showDamagePopup";
					submitForm(targetFormName,'mailtracking.defaults.mailacceptance.updateacceptmail.do?modify=N');
					//recreateMultiTableDetails("mailtracking.defaults.mailacceptance.capturedamage.do","div1","div2","ajaxUpdate");

			 	}
			 	else{
//showDialog("<bean:message bundle='mailAcceptanceResources' key='mailtracking.defaults.damagedetails.msg.info.damagedMailNotSelected' />", 1, self);
//showDialog({msg:'Accepted mailbags cannot be modified',type:1,parentWindow:self});
			 		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.damagedetails.msg.info.damagedMailNotSelected" />',type:1,parentWindow:self});			
			 	}
			}
		     }
		  }
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


function listContainerDetails(){

   frm=targetFormName;
   damageMailBags();
    var incomplete = validateDespatchDetails();
      if(incomplete == "N"){
        return;
      }
      incomplete = validateMailDetails();
      if(incomplete == "N"){
           return;
   }

   frm.elements.overrideUMSFlag.value = "";
   //window.opener.IC.util.common.childUnloadEventHandler();
   // submitAction(frm,'/mailtracking.defaults.mailacceptance.listflight.do');
   submitForm(frm,'mailtracking.defaults.mailacceptance.listacceptmail.do');

}


function addMails(){
   frm=targetFormName;
frm.elements.suggestValue.value="add";
   if(frm.elements.disableFlag.value != "Y"){

   damageMailBags();
   var mailOOE =document.getElementsByName("mailOOE");
   var mailOOElength=mailOOE.length;
   var conDocNo =document.getElementsByName("despatchPA");
   var conDocNolength=conDocNo.length;
   var incomplete = "";


   if(frm.TABPANE_ID_FLD.value == "tab1"){
      incomplete = validateDespatchDetails();
      if(incomplete == "N"){
           return;
      }
      //submitAction(frm,'/mailtracking.defaults.mailacceptance.addacceptmail.do');
      //recreateMultiTableDetails("mailtracking.defaults.mailacceptance.addacceptmail.do","div1","div2","ajaxUpdate");

       addTemplateRow('despatchTemplateRow','despatchTableBody','despatchOpFlag');
       if(conDocNolength==1){
		//frm.despatchDate[conDocNolength-1].value=frm.hiddenScanDate.value;
	  }

	if(conDocNolength>1){
		frm.conDocNo[conDocNolength-1].value=frm.conDocNo[conDocNolength-2].value;
		frm.despatchPA[conDocNolength-1].value=frm.despatchPA[conDocNolength-2].value;
		frm.despatchOOE[conDocNolength-1].value=frm.despatchOOE[conDocNolength-2].value;
		frm.despatchDOE[conDocNolength-1].value=frm.despatchDOE[conDocNolength-2].value;
		frm.despatchSC[conDocNolength-1].value=frm.despatchSC[conDocNolength-2].value;
		frm.despatchDSN[conDocNolength-1].value=frm.despatchDSN[conDocNolength-2].value;
		frm.despatchYear[conDocNolength-1].value=frm.despatchYear[conDocNolength-2].value;
		frm.despatchCat[conDocNolength-1].value=frm.despatchCat[conDocNolength-2].value;
		frm.despatchClass[conDocNolength-1].value=frm.despatchClass[conDocNolength-2].value;
		frm.despatchDate[conDocNolength-1].value=frm.hiddenScanDate.value;
		//frm.statedNoBags[conDocNolength-1].value=frm.statedNoBags[conDocNolength-2].value;
		//frm.statedWt[conDocNolength-1].value=frm.statedWt[conDocNolength-2].value;
		//frm.accNoBags[conDocNolength-1].value=frm.accNoBags[conDocNolength-2].value;
		//frm.accWt[conDocNolength-1].value=frm.accWt[conDocNolength-2].value;
		//frm.stdVolume[conDocNolength-1].value=frm.stdVolume[conDocNolength-2].value;

	}
		conDocNo[conDocNolength-1].focus();
      displayTabPane('container1','tab1');

   }else{
	      incomplete = validateMailDetails();

	      if(incomplete == "N"){
		   return;
	      }
	      //submitAction(frm,'/mailtracking.defaults.mailacceptance.addmailbags.do');
	      //recreateMultiTableDetails("mailtracking.defaults.mailacceptance.addmailbags.do","div1","div2","ajaxUpdate");

	     /* addTemplateRow('mailTemplateRow','mailTableBody','mailOpFlag');
		 if(mailOOElength==1){

			 frm.mailScanTime[mailOOElength-1].value=frm.hiddenScanTime.value;
		     frm.mailScanDate[mailOOElength-1].value=frm.hiddenScanDate.value;
		  }
		   if(mailOOElength>1){
			  frm.mailOOE[mailOOElength-1].value=frm.mailOOE[mailOOElength-2].value;
			  frm.mailDOE[mailOOElength-1].value=frm.mailDOE[mailOOElength-2].value;
			  frm.mailCat[mailOOElength-1].value=frm.mailCat[mailOOElength-2].value;
			  frm.mailSC[mailOOElength-1].value=frm.mailSC[mailOOElength-2].value;
			  frm.mailYr[mailOOElength-1].value=frm.mailYr[mailOOElength-2].value;
			  frm.mailDSN[mailOOElength-1].value=frm.mailDSN[mailOOElength-2].value;
			  frm.mailHNI[mailOOElength-1].value=frm.mailHNI[mailOOElength-2].value;
			  frm.mailRI[mailOOElength-1].value=frm.mailRI[mailOOElength-2].value;
			  frm.mailCarrier[mailOOElength-1].value=frm.mailCarrier[mailOOElength-2].value;
			  frm.mailScanTime[mailOOElength-1].value=frm.hiddenScanTime.value;
			  frm.mailScanDate[mailOOElength-1].value=frm.hiddenScanDate.value;


		  }

		mailOOE[mailOOElength-1].focus();*/
		addTemplateRow('mailTemplateRow','mailTableBody','mailOpFlag');
		displayTabPane('container1','tab2');
		populateScanDateTimeforExport();


		/*var chkbox =document.getElementsByName("selectMailTag");
		var opFlag =document.getElementsByName("mailOpFlag");
		var flag="true";
		var selectedMails = "";
		var cnt1 = 0;
		for(var i=0; i<chkbox.length-1;i++){
			if(chkbox[i].checked) {
				  if(opFlag[i].value!='NOOP'){
				if(cnt1 == 0){
					selectedMails = chkbox[i].value;
					cnt1 = 1;
				}else{
					selectedMails = selectedMails + "," + chkbox[i].value;
				}
			} }
		 } 

		 if (flag=="false")
			
			showDialog({msg:'Accepted mailbags cannot be modified',type:1,parentWindow:self});
			
		 else{		 	
			frm.elements.popupCloseFlag.value = "showCaptureMailtag";
			submitForm(targetFormName,'mailtracking.defaults.mailacceptance.updateacceptmail.do?modify=N');
			
		}*/
		
		
		  /* if(mailOOElength>1){//commented as part of ICRD-224577 starts
	  frm.mailOOE[mailOOElength-1].value=frm.mailOOE[mailOOElength-2].value;
	  frm.mailDOE[mailOOElength-1].value=frm.mailDOE[mailOOElength-2].value;
	  frm.mailCat[mailOOElength-1].value=frm.mailCat[mailOOElength-2].value;
	  frm.mailSC[mailOOElength-1].value=frm.mailSC[mailOOElength-2].value;
	  frm.mailYr[mailOOElength-1].value=frm.mailYr[mailOOElength-2].value;
	  frm.mailDSN[mailOOElength-1].value=frm.mailDSN[mailOOElength-2].value;


  }*/////commented as part of ICRD-224577 ends
  
 // added as part of of ICRD-224577 starts
		   if(mailOOElength>1){
  
  for(i=mailOOElength-2;i>=0;--i){
  if(frm.mailOpFlag[i].value=='I'){
      break;
	  }
	  
  
  }
		   if(frm.mailOpFlag[i+1].value=='NOOP'){
				if(i>=0){
		frm.mailOOE[mailOOElength-1].value=frm.mailOOE[i].value;
	  frm.mailDOE[mailOOElength-1].value=frm.mailDOE[i].value;
	  frm.mailCat[mailOOElength-1].value=frm.mailCat[i].value;
	  frm.mailSC[mailOOElength-1].value=frm.mailSC[i].value;
	  frm.mailYr[mailOOElength-1].value=frm.mailYr[i].value;
	  frm.mailDSN[mailOOElength-1].value=frm.mailDSN[i].value;
	  frm.weightUnit[mailOOElength-1].value=frm.weightUnit[i].value;//added by A-8353 for ICRD-274933
	  }
}
else{		
	  frm.mailOOE[mailOOElength-1].value=frm.mailOOE[mailOOElength-2].value;
	  frm.mailDOE[mailOOElength-1].value=frm.mailDOE[mailOOElength-2].value;
	  frm.mailCat[mailOOElength-1].value=frm.mailCat[mailOOElength-2].value;
	  frm.mailSC[mailOOElength-1].value=frm.mailSC[mailOOElength-2].value;
	  frm.mailYr[mailOOElength-1].value=frm.mailYr[mailOOElength-2].value;
	  frm.mailDSN[mailOOElength-1].value=frm.mailDSN[mailOOElength-2].value;
      frm.weightUnit[mailOOElength-1].value=frm.weightUnit[mailOOElength-2].value;//added by A-8353 for ICRD-274933
  }
  } // added as part of of ICRD-224577 ends
  
  
        	
     	 }
    }
}

function deleteMails(){
   frm=targetFormName;


   if(frm.elements.disableFlag.value != "Y"){

   damageMailBags();

   var despatchOpFlag =document.getElementsByName("despatchOpFlag");
   var mailOpFlag =document.getElementsByName("mailOpFlag");
   var chkbox1 =document.getElementsByName("selectDespatch");
   var chkbox2 =document.getElementsByName("selectMailTag");
   var deleteAgreeFlag =document.getElementsByName("deleteAgreeFlag");
   
   if(frm.TABPANE_ID_FLD.value == "tab1"){
     // if(validateSelectedCheckBoxes(frm,'selectDespatch',chkbox1.length,'1')){
		  if(validateSelectedCheckBoxes(frm,'selectDespatch',1000000000,1)){
          //submitAction(frm,'/mailtracking.defaults.mailacceptance.deleteacceptmail.do');
          //recreateMultiTableDetails("mailtracking.defaults.mailacceptance.deleteacceptmail.do","div1","div2","ajaxUpdate");

          for(var i=0; i<chkbox1.length;i++){
              if(chkbox1[i].checked ){
                  if(despatchOpFlag[i].value != "I" && despatchOpFlag[i].value != "NOOP" ){
                        showDialog({msg:'Despatch cannot be deleted.Already exist in the system.',type:1,parentWindow:self});
                       return;
                  }
              }
          }

	  deleteTableRow('selectDespatch','despatchOpFlag');
	  displayTabPane('container1','tab1');



      }
   }else{
      //if(validateSelectedCheckBoxes(frm,'selectMailTag',chkbox2.length,'1')){
      	if(validateSelectedCheckBoxes(frm,'selectMailTag',1000000000,1)){
          //submitAction(frm,'/mailtracking.defaults.mailacceptance.deletemailbags.do');
          //recreateMultiTableDetails("mailtracking.defaults.mailacceptance.deletemailbags.do","div1","div2","ajaxUpdate");

		  // Added as part of CRQ ICRD-118163 by A-5526 starts
var validateDeletion='N';
var warningFlag='N';

var deleteFlag=frm.elements.deleteAgreeFlag.value;

          for(var i=0; i<(chkbox2.length)-1;i++){
		if(chkbox2[i].checked ){
		var selectForDelete=deleteFlag.indexOf(i);
		
		var w1=deleteFlag.substring(selectForDelete+1,selectForDelete+2);
		if(w1=='R'){
		
		showDialog({msg:'Mailbag is already returned so can not be deleted',type:1,parentWindow:self});
		warningFlag='N';
		break;
		}
		
		else if(w1=='A'){
	
		showDialog({msg:'Mailbag is already arrived/transferred so can not be deleted',type:1,parentWindow:self});
		warningFlag='N';
		break;
		}
		else if(w1=='D'){
		showDialog({msg:'Mailbag is already delivered so can not be deleted',type:1,parentWindow:self});
		warningFlag='N';
		break;
		} 
		else{
		warningFlag='Y'
		}
		 if(mailOpFlag[i].value != "I" ){
			showDialog({msg:'Mailbag is already accepted so can not be deleted',type:1,parentWindow:self});

		    }
		
		    if(mailOpFlag[i].value != "I" && mailOpFlag[i].value != "NOOP"){
			 validateDeletion='Y';

		    }
		}
          }
		  
		  
		 
		  
		  
		   
          
		  
		
		 
		if(validateDeletion=='Y' && warningFlag=='Y'){ 
				showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.delete" scope="request"/>',type :4, parentWindow:self,parentForm:frm,dialogId:'id_1',
						onClose:function(){
						screenConfirmDialog(frm,'id_1');
						screenNonConfirmDialog(frm,'id_1'); 
				for(var i=0; i<chkbox2.length-1;i++){
		chkbox2[i].checked =false;
		  
			 }
						}
						});
				
				
		// Added as part of CRQ ICRD-118163 by A-5526 ends		
	}
	else if(w1=='R' || w1=='A' || w1=='D'){
	
	}
	
	else{
	

          deleteTableRow('selectMailTag','mailOpFlag');
          displayTabPane('container1','tab2');
		  }

      }
   }

   }

}


function acceptMails(){
   frm=targetFormName;
   frm.elements.consignmentDocNum.value="";
    frm.elements.suggestValue.value="";
   damageMailBags();

   var incomplete = validateDespatchDetails();
   if(incomplete == "N"){
     return;
   }
   incomplete = validateMailDetails();
   if(incomplete == "N"){
        return;
   }
   incomplete=mailWeight();
   if(incomplete == "N"){
        return;
   }
   if(frm.elements.paBuilt.checked){
   		if(frm.elements.paCode.value == 0){
			
			showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.paCode" scope="request"/>',type:1,parentWindow:self});			
            // showPane(event,'pane1', frm.tab1);
            frm.elements.paCode.disabled = false;
	    	frm.elements.paCode.focus();
            return;            
    	}
    }

   submitAction(frm,'/mailtracking.defaults.mailacceptance.acceptmail.do');
}


function lookupDocument(){
    frm=targetFormName;

    damageMailBags();
    var incomplete = validateDespatchDetails();
       if(incomplete == "N"){
            return;
       }
       incomplete = validateMailDetails();
       if(incomplete == "N"){
               return;
       }

      frm.elements.popupCloseFlag.value = "showLookUpPopup";
      submitForm(frm,'mailtracking.defaults.mailacceptance.updateacceptmail.do?modify=N');

 }



 function changeScanTime(){
       frm=targetFormName;

       damageMailBags();
       var incomplete = validateDespatchDetails();
       if(incomplete == "N"){
  	  return;
       }
       incomplete = validateMailDetails();
       if(incomplete == "N"){
 	     return;
       }

       var chkbox =document.getElementsByName("selectMailTag");
       if(chkbox.length > 0){
	   if(validateSelectedCheckBoxes(frm,'selectMailTag',chkbox.length,1)){

	        frm.popupCloseFlag.value = "showScanTimePopup";
	        submitForm(frm,'mailtracking.defaults.mailacceptance.updateacceptmail.do?modify=N');
           }
       }

  }
function carditEnquiry(){
	var strAction="mailtracking.defaults.mailsearch.screenload.do";
	var dens = frm.density.value;
    	var strUrl=strAction+"?screenMode=POPUP_CARDIT&density="+dens;
	openPopUp(strUrl,950,580);

}
function cancelMails(){
   frm=targetFormName;
   window.closeNow();
}

function damageMailBags(){


frm=targetFormName;
var chkbox =document.getElementsByName("mailDamaged");
  if(chkbox.length > 0){
    for(var i=0; i<chkbox.length;i++){
       if(chkbox[i].checked ){
          chkbox[i].value = i;
       }
    }
  }

  if(frm.paBuilt.checked){
       frm.paBuilt.value="Y";
    }else{
       frm.paBuilt.value="N";
  }
 

}

function despatchDSN(){
 frm=targetFormName;
 var despatchDSNArr =document.getElementsByName("despatchDSN");
 var despatchDSN =document.getElementsByName("despatchDSN");
   for(var i=0;i<despatchDSNArr.length;i++){
      if(despatchDSNArr[i].value.length == 1){
          despatchDSN[i].value = "000"+despatchDSNArr[i].value;
      }
      if(despatchDSNArr[i].value.length == 2){
                despatchDSN[i].value = "00"+despatchDSNArr[i].value;
      }
      if(despatchDSNArr[i].value.length == 3){
                despatchDSN[i].value = "0"+despatchDSNArr[i].value;
      }
   }

}


function mailWeight(){
 frm=targetFormName;
 var weightArr =document.getElementsByName("mailWt");
 var weight =document.getElementsByName("mailWt");
 var weightUnit=document.getElementsByName("weightUnit");
   for(var i=0;i<weightArr.length;i++){
	  
	   var spiltWeight= weight[i].value.split('.');
	   if(spiltWeight.length>1){
	   if (weightUnit[i].value=='K'){
	   if(spiltWeight[1].length>1) {
		showDialog({msg:'Weight in Kilogram can accept only 1 value after  decimal point',type:1,parentWindow:self});
	  //targetFormName.elements.mailWt[weightArr.length-1].focus();
	   return "N";   
	   }
	   }
      else	{
		if(spiltWeight[1]>0) {
		   showDialog({msg:'Weight in pound and hectogram cannot have decimal value',type:1,parentWindow:self});
	       //targetFormName.elements.mailWt[weightArr.length-1].focus();
	       return "N";   
		} 
	  } 
	   }
      if(weightArr[i].value.length == 1){
          weight[i].value = "000"+weightArr[i].value;
      }
      if(weightArr[i].value.length == 2){
                weight[i].value = "00"+weightArr[i].value;
      }
      if(weightArr[i].value.length == 3){
                weight[i].value = "0"+weightArr[i].value;
      }
   }
 //  populateMailVolume();

}

function mailDSN(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("mailDSN");
 var mailDSN =document.getElementsByName("mailDSN");
   for(var i=0;i<mailDSNArr.length;i++){
      if(mailDSNArr[i].value.length == 1){
          mailDSN[i].value = "000"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 2){
                mailDSN[i].value = "00"+mailDSNArr[i].value;
      }
      if(mailDSNArr[i].value.length == 3){
                mailDSN[i].value = "0"+mailDSNArr[i].value;
      }
   }

}


function mailRSN(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("mailRSN");
 var mailRSN =document.getElementsByName("mailRSN");
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }
   }
}



function validateDespatchDetails(){

frm=targetFormName;

var opFlag =document.getElementsByName("despatchOpFlag");

  var conDocNo =document.getElementsByName("conDocNo");
  var despatchDate =document.getElementsByName("despatchDate");
  var despatchPA =document.getElementsByName("despatchPA");
  var despatchOOE =document.getElementsByName("despatchOOE");
  var despatchDOE =document.getElementsByName("despatchDOE");
  var despatchClass =document.getElementsByName("despatchClass");
  var despatchDSN =document.getElementsByName("despatchDSN");
  var despatchYear =document.getElementsByName("despatchYear");
  var statedNoBags =document.getElementsByName("statedNoBags");
  var statedWt =document.getElementsByName("statedWt");
  var accNoBags =document.getElementsByName("accNoBags");
  var accWt =document.getElementsByName("accWt");
  var stdVolume = document.getElementsByName("stdVolume");
  var accVolume = document.getElementsByName("accVolume");

  for(var i=0;i<conDocNo.length;i++){

     if(opFlag[i].value != "NOOP"){

       /* if(conDocNo[i].value.length == 0){
           showDialog('<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.condocnoempty" />',1,self);
           showPane(event,'pane1', frm.tab1);
           conDocNo[i].focus();
           return "N";
        }*/

        if(conDocNo[i].value.length > 0 && despatchDate[i].value.length == 0){
           
			showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.consigndateempty" scope="request"/>',type:1,parentWindow:self});			
            showPane(event,'pane1', frm.tab1);
            despatchDate[i].focus();
            return "N";
        }else if(despatchDate[i].value.length > 0){
        	var date=new Date();
			var d1 = date.getDate();
			if((d1.toString()).length==1){
				d1="0"+d1;
			}
			var month=getMonthString(date.getMonth());
			var year=date.getFullYear();
			var disDate=d1+'-'+month+'-'+year;		    
      	  	if(!(compareDates(disDate,despatchDate[i].value,2))){
		 		
				showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.consigndategreaterthancurrentdate" scope="request"/>',type:1,parentWindow:self});			
				showPane(event,'pane1', frm.tab1);
				despatchDate[i].focus();
            	return "N";
			}
        }

        if(despatchPA[i].value.length == 0){
         
		   showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchpaempty" scope="request"/>',type:1,parentWindow:self});			
           showPane(event,'pane1', frm.tab1);
           despatchPA[i].focus();
           return "N";
        }

        if(despatchOOE[i].value.length == 0){
        
		   showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchooeempty" scope="request"/>',type:1,parentWindow:self});			
           showPane(event,'pane1', frm.tab1);
           despatchOOE[i].focus();
           return "N";
        }
        if(despatchOOE[i].value.length != 6){
	  
	  showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchooelength" scope="request"/>',type:1,parentWindow:self});			
	  showPane(event,'pane1', frm.tab1);
	  despatchOOE[i].focus();
	  return "N";
        }

        if(despatchDOE[i].value.length == 0){
            
			showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchdoeempty" scope="request"/>',type:1,parentWindow:self});			
            showPane(event,'pane1', frm.tab1);
            despatchDOE[i].focus();
            return "N";
        }
        if(despatchDOE[i].value.length != 6){
	    
		showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchdoelength" scope="request"/>',type:1,parentWindow:self});			
	    showPane(event,'pane1', frm.tab1);
	    despatchDOE[i].focus();
	    return "N";
        }

        if(despatchClass[i].value.length == 0){
            
            showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchclassempty" scope="request"/>',type:1,parentWindow:self});			
			showPane(event,'pane1', frm.tab1);
            despatchClass[i].focus();
            return "N";
        }

        if(despatchDSN[i].value.length == 0){
            
			 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchdsnempty" scope="request"/>',type:1,parentWindow:self});			
             showPane(event,'pane1', frm.tab1);
             despatchDSN[i].focus();
             return "N";
        }

        if(despatchYear[i].value.length == 0){
           
			 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchyearempty" scope="request"/>',type:1,parentWindow:self});			
             showPane(event,'pane1', frm.tab1);
             despatchYear[i].focus();
             return "N";
        }

        if(statedNoBags[i].value.length == 0){
             
			  showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchstdbagsempty" scope="request"/>',type:1,parentWindow:self});			
              showPane(event,'pane1', frm.tab1);
              statedNoBags[i].focus();
              return "N";
        }
        if(statedNoBags[i].value == 0){
	    
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.stdbagszero" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane1', frm.tab1);
	     statedNoBags[i].focus();
	     return "N";
        }

        if(statedWt[i].value.length == 0){
             
			  showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchstdwgtempty" scope="request"/>',type:1,parentWindow:self});			
              showPane(event,'pane1', frm.tab1);
              statedWt[i].focus();
              return "N";
        }
        if(stdVolume[i].value.length == 0){
              
			  showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.stdvolumeempty" scope="request"/>',type:1,parentWindow:self});			
              showPane(event,'pane1', frm.tab1);
              stdVolume[i].focus();
              return "N";
        }
        if(stdVolume[i].value == 0){
	    
		 showDialog({msg:'<common:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.stdvolumezero" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane1', frm.tab1);
	     stdVolume[i].focus();
	     return "N";
        }
        if(statedWt[i].value == 0){
	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.stdwgtzero" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane1', frm.tab1);
	     statedWt[i].focus();
	     return "N";
        }

 	if(accNoBags[i].value.length == 0){
 	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchaccbagsempty" scope="request"/>',type:1,parentWindow:self});			
 	     showPane(event,'pane1', frm.tab1);
 	     accNoBags[i].focus();
 	     return "N";
 	}
 	if(accNoBags[i].value == 0){
	    
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.accbagszero" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane1', frm.tab1);
	     accNoBags[i].focus();
	     return "N";
	}

       /*if(parseInt(accNoBags[i].value) > parseInt(statedNoBags[i].value)){
	     showDialog("Accepted Pcs cannot be greater than Stated Pcs",1,self);
	     showPane(event,'pane1', frm.tab1);
	     accNoBags[i].focus();
	     return "N";
	}*/

        if(accWt[i].value.length == 0){
  	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.despatchaccwgtempty" scope="request"/>',type:1,parentWindow:self});			
  	     showPane(event,'pane1', frm.tab1);
  	     accWt[i].focus();
  	     return "N";
  	}

  	if(accWt[i].value == 0){
	    
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.accwgtzero" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane1', frm.tab1);
	     accWt[i].focus();
	     return "N";
	}

     /* if(parseInt(accWt[i].value) > parseInt(statedWt[i].value)){
	       showDialog("Accepted Wt cannot be greater than Stated Wt",1,self);
	       showPane(event,'pane1', frm.tab1);
	       accWt[i].focus();
	       return "N";
        }*/


    if(accVolume[i].value.length == 0){
         
		  showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.accvolumeempty" scope="request"/>',type:1,parentWindow:self});			
          showPane(event,'pane1', frm.tab1);
          stdVolume[i].focus();
          return "N";
    }
    if(accVolume[i].value == 0){
	    
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.accvolumezero" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane1', frm.tab1);
	     stdVolume[i].focus();
	     return "N";
    }

   }
  }

}


/* 
     vF = 0 check for >
     vF = 1 check for <
     vF = 2 check for >=
     vF = 3 check for <=
     vF = 4 check for ==

*/
//@Author RENO K ABRAHAM
function compareDates(d1, d2 , vF){
    var pattern = /^(\d{1,2})-(\w{3})-(\d{4})$/;
    mRd1 = d1.toUpperCase().match(pattern);
    mRd2 = d2.toUpperCase().match(pattern);
    if(mRd1 == null){nv(d1); return false;}else if(mRd2 == null){nv(d2); return false;} 
    ddD1 = mRd1[1]; mmD1 = matchInt(mRd1[2]);yyD1 = mRd1[3]; 
    ddD2 = mRd2[1]; mmD2 = matchInt(mRd2[2]);yyD2 = mRd2[3];
    var dt2 = new Date(); dt2.setDate(ddD2);dt2.setMonth(mmD2-1);dt2.setYear(yyD2);    
    var dt1 = new Date(); dt1.setDate(ddD1);dt1.setMonth(mmD1-1);dt1.setYear(yyD1);    
    if(vF == 0) return (dt1>dt2); if(vF == 1) return (dt1<dt2);
    if(vF == 2) return (dt1>=dt2);if(vF == 3) return (dt1<=dt2);
    if(vF == 4) return (dt1==dt2);return false;
}
function nv(field) {}
function matchInt(monthStr) {
	var mi;
	monthStr = monthStr.toUpperCase();
	if(monthStr == 'JAN' ) mi= 1;if(monthStr == 'FEB' ) mi= 2;if(monthStr == 'MAR' ) mi= 3;if(monthStr == 'APR' ) mi= 4;
	if(monthStr == 'MAY' ) mi= 5;if(monthStr == 'JUN' ) mi= 6;if(monthStr == 'JUL' ) mi= 7;if(monthStr == 'AUG' ) mi= 8;
	if(monthStr == 'SEP' ) mi= 9;if(monthStr == 'OCT' ) mi= 10;if(monthStr == 'NOV' ) mi= 11;if(monthStr == 'DEC' ) mi= 12;
	return mi;
}

function getMonthString(num) {
    var arrMon=new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
    return arrMon[num];
}



function validateMailDetails(){

  frm=targetFormName;

  var opFlag =document.getElementsByName("mailOpFlag");

  var mailOOE =document.getElementsByName("mailOOE");
  var mailDOE =document.getElementsByName("mailDOE");
  var mailCat =document.getElementsByName("mailCat"); // Added by A-8164 for ICRD-257590
  var mailSC =document.getElementsByName("mailSC");
  var mailYr =document.getElementsByName("mailYr");
  var mailDSN =document.getElementsByName("mailDSN");
  var mailRSN =document.getElementsByName("mailRSN");  

  var mailHNI =document.getElementsByName("mailHNI"); // Added by A-8164 for ICRD-257590
  var mailRI =document.getElementsByName("mailRI");	

  var mailWt =document.getElementsByName("mailWt");
  var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");
   var mailVolume =document.getElementsByName("mailVolume");

  for(var i=0;i<mailOOE.length;i++){
    if(opFlag[i].value != "NOOP"){

  	  if(mailOOE[i].value.length == 0){
  	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailooeempty" scope="request"/>',type:1,parentWindow:self});			
  	     showPane(event,'pane2', frm.tab2);
  	     mailOOE[i].focus();
  	     return "N";
  	  }
  	  if(mailOOE[i].value.length != 6){
	    
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailooelength" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane2', frm.tab2);
	     mailOOE[i].focus();
	     return "N";
          }

   	  if(mailDOE[i].value.length == 0){
   	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildoeempty" scope="request"/>',type:1,parentWindow:self});			
   	     showPane(event,'pane2', frm.tab2);
   	     mailOOE[i].focus();
   	     return "N";
   	  }
   	  if(mailDOE[i].value.length != 6){
	    
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildoelength" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane2', frm.tab2);
	     mailDOE[i].focus();
	     return "N";
          }
		if(mailCat[i].value.length == 0){  // Added by A-8164 for ICRD-257590
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailCatempty" scope="request"/>',type:1,parentWindow:self});			
   	     showPane(event,'pane2', frm.tab2);
   	     mailCat[i].focus();
   	     return "N";
   	  }
	  if(mailHNI[i].value.length == 0){  // Added by A-8164 for ICRD-257590
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailhniempty" scope="request"/>',type:1,parentWindow:self});			
   	     showPane(event,'pane2', frm.tab2);
   	     mailHNI[i].focus();
   	     return "N";
   	  }
	  if(mailRI[i].value.length == 0){  // Added by A-8164 for ICRD-257590
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailriempty" scope="request"/>',type:1,parentWindow:self});			
   	     showPane(event,'pane2', frm.tab2);
   	     mailRI[i].focus();
   	     return "N";
   	  }

   	  if(mailSC[i].value.length == 0){
   	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailscempty" scope="request"/>',type:1,parentWindow:self});			
   	     showPane(event,'pane2', frm.tab2);
   	     mailSC[i].focus();
   	     return "N";
   	  }
   	  if(mailSC[i].value.length != 2){
	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailsclength" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane2', frm.tab2);
	     mailSC[i].focus();
	     return "N";
          }

          if(mailYr[i].value.length == 0){
    	     
			 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailyearempty" scope="request"/>',type:1,parentWindow:self});			
    	     showPane(event,'pane2', frm.tab2);
    	     mailYr[i].focus();
    	     return "N";
    	 }

         if(mailDSN[i].value.length == 0){
    	     showDialog('<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildsnempty" />',1,self);			 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildsnempty" scope="request"/>',type:1,parentWindow:self});			
    	     showPane(event,'pane2', frm.tab2);
    	     mailDSN[i].focus();
    	     return "N";
    	 }

         if(mailRSN[i].value.length == 0){
     	      showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailrsnempty" scope="request"/>',type:1,parentWindow:self});			
     	     showPane(event,'pane2', frm.tab2);
     	     mailRSN[i].focus();
     	     return "N";
     	 }
         if(mailWt[i].value.length == 0){
	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailwgtempty" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane2', frm.tab2);
	     mailWt[i].focus();
	     return "N";
	 }

	 if(mailWt[i].value == 0){
	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailwgtzero" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane2', frm.tab2);
	     mailWt[i].focus();
	     return "N";
	 }
		// Commented for ICRD-71578
 		 /*if(mailVolume[i].value.length == 0){
		     showDialog('<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailvolumeempty" />',1,self);
		     showPane(event,'pane2', frm.tab2);
		     mailVolume[i].focus();
		     return "N";
	 	}

		 if(mailVolume[i].value == 0){
		     showDialog('<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailvolumezero" />',1,self);
		     showPane(event,'pane2', frm.tab2);
		     mailVolume[i].focus();
		     return "N";
		 }*/
        if(mailScanDate[i].value.length == 0){
     	     
			 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildateempty" scope="request"/>',type:1,parentWindow:self});			
     	     showPane(event,'pane2', frm.tab2);
     	     mailScanDate[i].focus();
     	     return "N";
     	 }

        if(mailScanTime[i].value.length == 0){
	     
		 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailtimeempty" scope="request"/>',type:1,parentWindow:self});			
	     showPane(event,'pane2', frm.tab2);
	     mailScanTime[i].focus();
	     return "N";
	}

      }

   }



 }


  function newContainer(){
       frm=targetFormName;

       damageMailBags();
       	var incomplete = validateDespatchDetails();
       	if(incomplete == "N"){
       	       return;
       	}
       	incomplete = validateMailDetails();
       	if(incomplete == "N"){
       	       return;
        }
//Added by A-6991 for ICRD-196734 Starts
  if(frm.elements.paBuilt.checked){
   		if(frm.elements.paCode.value == 0){

			showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.paCode" scope="request"/>',type:1,parentWindow:self});
            // showPane(event,'pane1', frm.tab1);
            frm.elements.paCode.disabled = false;
	    	frm.elements.paCode.focus();
            return;
    	}
    }
//Added by A-6991 for ICRD-196734 Ends	
       frm.overrideUMSFlag.value = "";
       submitForm(frm,'mailtracking.defaults.mailacceptance.newcontainer.do');
  }


  /**
   * on change of awb suggest component
   * submit the form and the nextawb action
   */
  function callULDSuggestion(uldField, uldNumber, selectedIndex) {
  	var frm = targetFormName;

  	damageMailBags();
	var incomplete = validateDespatchDetails();
	if(incomplete == "N"){
	       return;
	}
	incomplete = validateMailDetails();
	if(incomplete == "N"){
	       return;
        }

  	frm.elements.suggestValue.value = selectedIndex;
  	action="mailtracking.defaults.mailacceptance.selectnextcontainer.do";
  	submitForm(targetFormName, action);
  }

/*
 * @author A-3227
 * Limits the Accp Wgt / Stated Weight to Five digit Integer and two digit Decimal
 */
function decimalLimitter(){
  var opFlag =document.getElementsByName("despatchOpFlag");
  var statedWt =document.getElementsByName("statedWt");
  var accWt =document.getElementsByName("accWt");

  for(var i=0;i<statedWt.length;i++){
     if(opFlag[i].value != "NOOP"){
		var splStWgt = statedWt[i].value.split(".");
		var splAcpWgt = accWt[i].value.split(".");

		if(splStWgt.length==1){
			statedWt[i].maxLength = 5;
			if(event.keyCode==46){	//KEYCODE FOR "."
				statedWt[i].maxLength += 3;
			}
		}else{
			statedWt[i].maxLength = 8;
			if(splStWgt[1].length > 1){
				statedWt[i].maxLength = statedWt[i].length;
			}
		}
		if(splAcpWgt.length==1){
			accWt[i].maxLength = 5;
			if(event.keyCode==46){	//KEYCODE FOR "."
				accWt[i].maxLength += 3;
			}
		}else{
			accWt[i].maxLength = 8;
			if(splAcpWgt[1].length > 1){
				accWt[i].maxLength = accWt[i].length;
			}
		}
	}
    }
}

////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////

 var _currDivIdDespatch = "";
 var _currDivIdMailTag = "";

 function recreateMultiTableDetails(strAction,divId1,divId2){

 	var __extraFn="updateMultiTableCode";

 	if(arguments[3]!=null){
 		__extraFn=arguments[3];
 	}

 	_currDivIdDespatch = divId1;
 	_currDivIdMailTag = divId2;
	//showProgressBar('Loading . . . ');
	applySpecifcEventsForMail = "YES";
 	asyncSubmit(targetFormName,strAction,__extraFn,null,null);

 }

 function ajaxUpdate(_tableInfo){

 	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];

	targetFormName.elements.popupCloseFlag.value = _innerFrm.popupCloseFlag.value;  	
	targetFormName.elements.assignToFlight.value = _innerFrm.assignToFlight.value;	
	targetFormName.elements.suggestValue.value = _innerFrm.suggestValue.value;
	targetFormName.elements.preassignFlag.value = _innerFrm.preassignFlag.value;
	targetFormName.elements.disableFlag.value = _innerFrm.disableFlag.value;
	targetFormName.elements.warningOveride.value = _innerFrm.warningOveride.value;
	targetFormName.elements.overrideUMSFlag.value = _innerFrm.overrideUMSFlag.value;
	targetFormName.elements.prevPou.value = _innerFrm.prevPou.value;
	targetFormName.elements.hiddenScanDate.value = _innerFrm.hiddenScanDate.value;
	targetFormName.elements.hiddenScanTime.value = _innerFrm.hiddenScanTime.value;
	targetFormName.elements.density.value = _innerFrm.density.value;
  	targetFormName.elements.consignmentDocNum.value = _innerFrm.consignmentDocNum.value;

 	onScreenLoad();

        if(_asyncErrorsExist) return;

        updateMultiTableCode(_tableInfo);
        //closeProgressBar();

 }


 function updateMultiTableCode(_tableInfo){

 	_despatch=_tableInfo.document.getElementById("_despatch");
	_mailTag=_tableInfo.document.getElementById("_mailTag")	;
	
	purge(document.getElementById(_currDivIdDespatch));	
	purge(document.getElementById(_currDivIdMailTag));
	
	document.getElementById(_currDivIdDespatch).innerHTML=_despatch.innerHTML;
	document.getElementById(_currDivIdMailTag).innerHTML=_mailTag.innerHTML;

 	/*if(_currDivIdDespatch != ""){
 		_strDespatch = getActualData(_tableInfo, "Despatch");
 		document.getElementById(_currDivIdDespatch).innerHTML=_strDespatch;
 	}

 	if(_currDivIdMailTag != ""){
		_strMailTag = getActualData(_tableInfo, "MailTag");
		document.getElementById(_currDivIdMailTag).innerHTML=_strMailTag;
 	}

 	cleanupMultipleTmpTable();
 	reapplyEvent();*/
 	//applyScrollTableStyles();
 }
 
 function purge(d) {
     var a = d.attributes, i, l, n;
     if (a) {
         l = a.length;
         for (i = 0; i < l; i += 1) {
             n = a[i].name;
             if (typeof d[n] === 'function') {
                 d[n] = null;
             }
         }
     }
     a = d.childNodes;
     if (a) {
         l = a.length;
         for (i = 0; i < l; i += 1) {
             purge(d.childNodes[i]);
         }
     }
 }

 /*function getActualData(_tableInfo, _identifier){

 document.getElementById("tmpSpan").innerHTML=_tableInfo;
 var _tmpSpanId="";
 	if(_identifier=='Despatch'){
 		_tmpSpanId = document.getElementById("_despatch");
 	}

 	if(_identifier=='MailTag'){
	 	_tmpSpanId = document.getElementById("_mailTag");
 	}

 	return _tmpSpanId.innerHTML;

 }
 */

 /*function cleanupMultipleTmpTable(){
 	document.getElementById("tmpSpan").innerHTML="";
 }*/

 /**
  *Method for excecuting after confirmation
  */
 function confirmPopupMessage(){

   frm = targetFormName;
   frm.elements.disableFlag.value = "N";
   if(frm.elements.overrideUMSFlag.value != "Y"){

      frm.elements.warningOveride.value = "Y";

   }
   submitAction(frm,'/mailtracking.defaults.mailacceptance.listacceptmail.do');

 }

  /**
  *Method for excecuting after nonconfirmation
  */
 function nonconfirmPopupMessage(){

   frm = targetFormName;
   frm.elements.disableFlag.value = "Y";
   frm.elements.overrideUMSFlag.value = "";

 }

 function defaultDestn(frm) {
 	if(frm.elements.destn.value.toUpperCase() == frm.elements.prevPou.value.toUpperCase()) {
		frm.elements.destn.value = frm.elements.pou.value;
	}
 	frm.elements.prevPou.value = frm.elements.pou.value;
}


function disableBulkDestn(frm){

   var str = frm.elements.assignToFlight.value;

   if(frm.elements.barrowCheck.checked){
    	 if(frm.elements.paBuilt.checked){
	      	frm.elements.paBuilt.checked=false;
	 		 }
    	frm.elements.paBuilt.disabled = true;
		frm.elements.containerJnyId.disabled = true;
		frm.elements.paCode.disabled=true;
      	if(str != "CARRIER"){
      		frm.elements.destn.value="";
      		frm.elements.pou.value="";
      		}
      		 frm.elements.destn.disabled = true;
  	 }else{
   		frm.elements.paBuilt.disabled = false;
		frm.elements.containerJnyId.disabled = false;
		frm.elements.paCode.disabled=false;
      if(str != "CARRIER"){
	     frm.elements.destn.value="";
         frm.elements.destn.disabled = false;
         frm.elements.destnImage.disabled = false;
      }
   }
}

function populateBulkDestn(frm){
if(frm.elements.barrowCheck.checked){
		frm.elements.containerType.value="B";
		frm.elements.destn.value=frm.pou.value;
		frm.elements.destn.disabled = true;
		frm.elements.destnImage.disabled = true;
	}
}


function populateMailVolume(){
	var frm = targetFormName;
	var density = frm.elements.density.value;
	var weight = document.getElementsByName("mailWt");
	var volume = document.getElementsByName("mailVolume");
	for(var i=0;i<weight.length;i++){
		/*if(volume[i].value =="" || volume[i].value =="0" || volume[i].value =="0.0"){*/
			var w = weight[i].value;
			var wt = w/(10*density);
			var strWt=wt.toString();
			var s = strWt.indexOf(".");
			var prefix = strWt.substring(0,s);
			var suffix = strWt.substring(s,s+5);
			if(wt != 0 && prefix == 0 && suffix < 0.01){
				volume[i].value =  0.01;
			}else{				
				//volume[i].value =  prefix+round(Number(suffix), 5);
				volume[i].value = prefix+suffix;
				
			}			
		/*}*/
	}
}
function populateDespatchVolume(){
	var frm = targetFormName;
	var density = frm.elements.density.value;
	var statedWt = document.getElementsByName("statedWt");
	var stdVolume = document.getElementsByName("stdVolume");
	for(var i=0;i<statedWt.length;i++){
		//if(stdVolume[i].value =="" || stdVolume[i].value =="0" || stdVolume[i].value =="0.0" ){
			var w = statedWt[i].value;
			var wt = w/(density);
			var strWt=wt.toString();
			var s = strWt.indexOf(".");
			var prefix = strWt.substring(0,s);
			var suffix = strWt.substring(s,s+3);
			if(wt != 0 && prefix == 0 && suffix < 0.01){
				stdVolume[i].value =  0.01;
			}else{	
				//stdVolume[i].value =  prefix+round(Number(suffix), 5);
				stdVolume[i].value =  prefix+suffix;
				
			}
		//}
	}
	var accWt = document.getElementsByName("accWt");
	var accVolume = document.getElementsByName("accVolume");
	for(var i=0;i<accWt.length;i++){
		//if(accVolume[i].value =="" || accVolume[i].value =="0" || accVolume[i].value =="0.0" ){
			var w = accWt[i].value;
			var wt = w/(density);
			var strWt=wt.toString();
			var s = strWt.indexOf(".");
			var prefix = strWt.substring(0,s);
			var suffix = strWt.substring(s,s+3);
			if(wt != 0 && prefix == 0 && suffix < 0.01){
				accVolume[i].value =  0.01;
			}else{				
				//accVolume[i].value =  prefix+round(Number(suffix), 5);
				accVolume[i].value =  prefix+suffix;				
			}
		//}
	}
}

function listCartIds(){
	var frm = targetFormName;
	var bellyCarditId = frm.bellyCarditId.value;
	   if(bellyCarditId != null && bellyCarditId.length == 0){
			 showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.cartIdsempty" scope="request"/>',type:1,parentWindow:self});			
			 showPane(event,'pane1', frm.tab1);
             bellyCarditId.focus();
	   }else{
			submitAction(frm,'/mailtracking.defaults.mailacceptance.listcartids.do');
	   }
}

function enContainerJnyIDField(){
	var frm = targetFormName;
	if(frm.elements.paBuilt.checked){
       frm.elements.containerJnyId.disabled=false;
	   frm.elements.paCode.disabled=false;
	   document.getElementById('paLov').disabled=false;
    }else{
       frm.elements.containerJnyId.disabled=true;
	   frm.elements.paCode.disabled=true;
	   document.getElementById('paLov').disabled=true;
	}
}

function round(num, decimals) {
	var multiplier = Math.pow(10, decimals);
	if (typeof(num) != "number") {
		num = Number(num);
	}
	if (decimals > 0) {
		return Math.round(num * multiplier) / multiplier;
	} else {
		return Math.round(num);
	}
}
function selectTableRow(event){

 var _element = getFnKeyTargetField(event);

if(_element.name == "mailDamaged"){

 return;
 }else{
 
 var _tr = this;		  //mozilla
	var _element = getFnKeyTargetField(event);
	 var e = window.event ? window.event : event;
	var theCode=e.keyCode;      
	if(theBrowser=="gko"){ // checkbox clik	
		var obj = getFnKeyTargetField(e);
		if(obj)   
			if(obj.type!=null){	
				if(obj.type.toUpperCase()=="CHECKBOX"){
					return;
				} 
			} 
	}

	try{
		_firstTd = _tr.getElementsByTagName("td")[0];
		_chkBox = _firstTd.getElementsByTagName("input")[0];

		if(_chkBox.getAttribute("type") == "hidden") {
			_chkBox = _firstTd.getElementsByTagName("input")[1];
		}

		if(_chkBox.getAttribute("type") == "checkbox" || _chkBox.getAttribute("type") == "radio" ){
			_chkBox.click();
		}
	}catch(e)
	{
		
	}

	_table = getParentTable(_tr);
		if(_table){
			var rowSelectionEvent = _table.getAttribute("rowSelectionEvent");

			if(rowSelectionEvent){
				eval('if(window.'+rowSelectionEvent+'){'+rowSelectionEvent+'(_tr);}');
			}
	}
 
 
 }
 
 }

 function screenConfirmDialog(frm,dialogId){
 while(frm.elements.currentDialogId.value == ''){

 	}
if(frm.elements.currentDialogOption.value == 'Y') {
 if(dialogId == 'id_1'){ deleteTableRow('selectMailTag','mailOpFlag');
          displayTabPane('container1','tab2');
		}
 }
 }

 function screenNonConfirmDialog(frm,dialogId){
 
 }
 ////////////////////////////////////////////////////////////////////////////////////////
 function setValue(){
	var frm=targetFormName;
	if(frm.elements.barrowCheck.checked){
	frm.elements.containerType.value="B";
	}else{
	frm.elements.containerType.value="U";
	   }
	}
	
	
	//-----------------Ajax call for Mail Tag Details -----------------------------------//
function populateMailDetails(obj){
	var  _rowCount =null;
	try{
		_rowCount=obj.getAttribute("rowCount");
		
	}catch(err){
		return;
	}
	
	if(targetFormName.elements.mailbagId[_rowCount].value.length==12){
		
		disableField(targetFormName.elements.mailOOE);
		disableField(targetFormName.elements.mailDOE);
		disableField(targetFormName.elements.mailCat);
		disableField(targetFormName.elements.mailSC);
		disableField(targetFormName.elements.mailYr);
		disableField(targetFormName.elements.mailDSN);
		disableField(targetFormName.elements.mailRSN);
		disableField(targetFormName.elements.mailHNI);
		disableField(targetFormName.elements.mailRI);
		disableField(targetFormName.elements.mailWt);
		disableField(targetFormName.elements.mailVolume);
		disableField(targetFormName.elements.weightUnit);
	}
	globalObj=obj;
	var funct_to_overwrite = "refreshMailDetails";
	var strAction = 'mailtracking.defaults.mailacceptance.populatemailtagdetails.do?selectMailTag='+_rowCount;

  	asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
}

 function refreshMailDetails(_tableInfo){
var _rowCount = globalObj.getAttribute("rowCount");
//added by A-7371 for ICRD-224610
if(_tableInfo.document.getElementById("_ajax_inValidId").innerHTML=='true'){
	showDialog({msg:'Invalid Mail Bag ID',type:1,parentWindow:self});
	targetFormName.elements.mailWt[_rowCount].focus();
	return;
}

if(targetFormName.elements.mailbagId.length>0){
targetFormName.elements.mailOOE[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailOOE").innerHTML;
targetFormName.elements.mailDOE[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailDOE").innerHTML;
targetFormName.elements.mailCat[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailCat").innerHTML;
targetFormName.elements.mailSC[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailSC").innerHTML;
targetFormName.elements.mailYr[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailYr").innerHTML;
targetFormName.elements.mailDSN[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailDSN").innerHTML;
targetFormName.elements.mailRSN[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailRSN").innerHTML;
targetFormName.elements.mailHNI[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailHNI").innerHTML;
targetFormName.elements.mailRI[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailRI").innerHTML;
targetFormName.elements.mailWt[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailWt").innerHTML;
targetFormName.elements.mailVolume[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailVol").innerHTML;
targetFormName.elements.mailbagId[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailbagId").innerHTML;
mailOOEPrv=targetFormName.elements.mailOOE[_rowCount].value;
mailDOEPrv=targetFormName.elements.mailDOE[_rowCount].value;
mailSCPrv=targetFormName.elements.mailSC[_rowCount].value;
prvRowCnt=_rowCount;
}
//populateMailVolume();
var incomplete=mailWeight();
mailDSN();
mailRSN();
  }
//------------------------------------------------------------------------//

function populateScanDateTimeforExport(){
  var selectedRows =document.getElementsByName("selectMailTag");
  var mailScanDate =document.getElementsByName("mailScanDate");
  var mailScanTime =document.getElementsByName("mailScanTime");
  var opFlag =document.getElementsByName("mailOpFlag");
	if(selectedRows.length > 0){
		for(var i=0; i<selectedRows.length;i++){
			if(opFlag[i].value != "NOOP"){
					if(mailScanDate[i].value == "" || mailScanTime[i].value==""){
						mailScanDate[i].value = frm.elements.hiddenScanDate.value;
						mailScanTime[i].value = frm.elements.hiddenScanTime.value;
				}
			}
		}
	}
}

function populateMailbagId(obj){
	var  _rowCount =null;
	try{
		_rowCount=obj.getAttribute("rowCount");
		
	}catch(err){
		return;
	}
	
	if(_rowCount==null &&_rowCount==""){
		
			try{
			_rowCount=obj.getAttribute("index");
			}catch(err){
			return; 
			}
	}
	if(targetFormName.elements.mailOOE[_rowCount].value!=null&&targetFormName.elements.mailOOE[_rowCount].value!=""&&
	targetFormName.elements.mailDOE[_rowCount].value!=null&&targetFormName.elements.mailDOE[_rowCount].value!=""&&
	targetFormName.elements.mailCat[_rowCount].value!=null&&targetFormName.elements.mailCat[_rowCount].value!=""&&
	targetFormName.elements.mailSC[_rowCount].value!=null&&targetFormName.elements.mailSC[_rowCount].value!=""&&
	targetFormName.elements.mailYr[_rowCount].value!=null&&targetFormName.elements.mailYr[_rowCount].value!=""&&
	targetFormName.elements.mailDSN[_rowCount].value!=null&&targetFormName.elements.mailDSN[_rowCount].value!=""&&
	targetFormName.elements.mailRSN[_rowCount].value!=null&&targetFormName.elements.mailRSN[_rowCount].value!=""&&
	targetFormName.elements.mailHNI[_rowCount].value!=null&&targetFormName.elements.mailHNI[_rowCount].value!=""&&
	targetFormName.elements.mailRI[_rowCount].value!=null&&targetFormName.elements.mailRI[_rowCount].value!=""&&
	targetFormName.elements.mailWt[_rowCount].value!=null&&targetFormName.elements.mailWt[_rowCount].value!=""){
	targetFormName.elements.mailbagId[_rowCount].value="";
	populateMailDetails(obj);
	}else{
	targetFormName.elements.mailbagId[_rowCount].value="";
	}
	
	}
function modifyMails(){
	var chkbox =document.getElementsByName("selectMailTag");
	var selectedMails = "";
	/*var count = 0;   //Commented by A-6991 for ICRD-208593
	for(var i=0; i<chkbox.length-1;i++){
		if(chkbox[i].checked) {
			count++;
			selectedMails = chkbox[i].value;
		 }
	}
	if(count == 0){
		showDialog({msg:'Please select a row',type:1,parentWindow:self});
	}else if (count > 1){
		showDialog({msg:'Please select one row only',type:1,parentWindow:self});
	}else{
		damageMailBags();
		targetFormName.elements.popupCloseFlag.value = "showCaptureMailtag";
		submitForm(targetFormName,'mailtracking.defaults.mailacceptance.updateacceptmail.do?modify=Y');
	   }*/
	   if(validateSelectedCheckBoxes(targetFormName,'selectMailTag',1,1)){
							damageMailBags();
							targetFormName.elements.popupCloseFlag.value = "showCaptureMailtag";
							submitForm(targetFormName,'mailtracking.defaults.mailacceptance.updateacceptmail.do?modify=Y');
							return;
				
	   }
	}
//Added by A-7540 for ICRD-243365	
 function confirmPopupMessage(){ 
 if(frm.elements.warningStatus.value=="latvalidation"){
  frm.elements.canDiscardLATValidation.value =true;
   submitAction(frm,'/mailtracking.defaults.mailacceptance.acceptmail.do');
  }
   if(frm.elements.warningStatus.value=="coterminus"){
   frm.elements.canDiscardCoterminus.value =true;
   submitAction(frm,'/mailtracking.defaults.mailacceptance.acceptmail.do');
   }
    if(frm.elements.warningStatus.value=="uldvalidation"){  //added by A-8149 for ICRD-276070
 frm.elements.canDiscardUldValidation.value =true;
   submitAction(frm,'/mailtracking.defaults.mailacceptance.listacceptmail.do');
  }
   
  }
  
  
  function nonconfirmPopupMessage(){
	}
function triggerEvent(obj) {
    var _rowCount = null;
    var mailOOECur = null;
    var mailDOECur = null;
    var mailSCCur = null;
    try {
        _rowCount = obj.getAttribute("rowCount");
    } catch (err) {
        return;
    }
    mailOOECur = targetFormName.elements.mailOOE[_rowCount].value;
    mailDOECur = targetFormName.elements.mailDOE[_rowCount].value;
    mailSCCur = targetFormName.elements.mailSC[_rowCount].value;
    if (mailOOECur != mailOOEPrv && prvRowCnt == _rowCount) {
        mailOOEPrv = null;
        prvRowCnt = _rowCount;
        var event = new Event('change');
        targetFormName.elements.mailOOE[_rowCount].dispatchEvent(event);
    }
    if (mailDOECur != mailDOEPrv && prvRowCnt == _rowCount) {
        mailDOEPrv = null;
        prvRowCnt = _rowCount;
        var event = new Event('change');
        targetFormName.elements.mailDOE[_rowCount].dispatchEvent(event);
    }
    if (mailSCCur != mailSCPrv && prvRowCnt == _rowCount) {
        mailSCPrv = null;
        prvRowCnt = _rowCount;
        var event = new Event('change');
        targetFormName.elements.mailSC[_rowCount].dispatchEvent(event);
    }
	}