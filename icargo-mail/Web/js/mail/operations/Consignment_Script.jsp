<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;
    //onScreenloadSetHeight();
	if((!targetFormName.elements.conDocNo.disabled) && (!targetFormName.elements.conDocNo.readOnly)
		&& (targetFormName.elements.disableListSuccess.value != "Y")){
	targetFormName.elements.conDocNo.focus();
	}
   onScreenLoad();
   insertNewRoutingDetails();
   with(frm){

     //CLICK Events
        evtHandler.addEvents("btnPrint","printConsignment()",EVT_CLICK);
        evtHandler.addEvents("btnList","list()",EVT_CLICK);
     	evtHandler.addEvents("btnClear","clear()",EVT_CLICK);
     	evtHandler.addIDEvents("lnkAddRoute","addRoute()",EVT_CLICK);
     	evtHandler.addIDEvents("lnkDeleteRoute","deleteRoute()",EVT_CLICK);
     	evtHandler.addIDEvents("lnkAddMail","addMail()",EVT_CLICK);
     	evtHandler.addIDEvents("lnkDeleteMail","deleteMail()",EVT_CLICK);
		 evtHandler.addIDEvents("lnkAddMultiple","addMultiple()",EVT_CLICK);
     	evtHandler.addEvents("btnDelete","deleteConDoc()",EVT_CLICK);
     	evtHandler.addEvents("btnSave","saveConDoc()",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeConDoc()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);
        evtHandler.addEvents("type","contypeTabOut()",EVT_BLUR);
		evtHandler.addEvents("btnPrintMailTag","printMailtag()",EVT_CLICK); //added by a-7871 for ICRD-234913
     	//Invoking Lov
	evtHandler.addEvents("polLov","invokeLov(this,'polLov')",EVT_CLICK);
	evtHandler.addEvents("pouLov","invokeLov(this,'pouLov')",EVT_CLICK);
	evtHandler.addEvents("OOELov","invokeLov(this,'OOELov')",EVT_CLICK);
	evtHandler.addEvents("DOELov","invokeLov(this,'DOELov')",EVT_CLICK);
	evtHandler.addEvents("SCLov","invokeLov(this,'SCLov')",EVT_CLICK);
    evtHandler.addEvents("currencyCodeLov","invokeLov(this,'currencyCodeLov')",EVT_CLICK);
     	if(frm.elements.btnSave.disabled){
     	    disableLink(document.getElementById('lnkAddRoute'));
	    disableLink(document.getElementById('lnkDeleteRoute'));
	    disableLink(document.getElementById('lnkAddMail'));
	    disableLink(document.getElementById('lnkDeleteMail'));
		disableLink(document.getElementById('lnkAddMultiple'));
  	}

  	if(frm.selectRoute != null){
 	      evtHandler.addEvents("selectRoute","toggleTableHeaderCheckbox('selectRoute',targetFormName.masterRoute)",EVT_CLICK);
	}

	if(frm.selectMail != null){
	      evtHandler.addEvents("selectMail","toggleTableHeaderCheckbox('selectMail',targetFormName.masterMail)",EVT_CLICK);
	}

     //BLUR Events
        evtHandler.addEvents("dsn","dsnPadding()",EVT_BLUR);
	evtHandler.addEvents("rsn","rsnPadding()",EVT_BLUR);
	//Commented for ICRD-59258
	//evtHandler.addEvents("weight","validateWeight()",EVT_BLUR);

	evtHandler.addEvents("flightCarrierCode","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);
	evtHandler.addEvents("flightNumber","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);
	evtHandler.addEvents("depDate","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);
	evtHandler.addEvents("pol","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);
	evtHandler.addEvents("pou","operationFlagChangeOnEdit(this,'routeOpFlag');",EVT_BLUR);

	evtHandler.addEvents("statedBags","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
	evtHandler.addEvents("weight","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);
	evtHandler.addEvents("uldNum","operationFlagChangeOnEdit(this,'mailOpFlag');",EVT_BLUR);

	evtHandler.addEvents("mailbagId","populateMailDetails(this)",EVT_BLUR);
	evtHandler.addEvents("originOE","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("destinationOE","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("category","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("subClass","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("year","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("dsn","populateMailbagId(this)",EVT_BLUR);
	evtHandler.addEvents("rsn","populateMailbagId(this)",EVT_BLUR);
	evtHandler.addEvents("mailHI","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("mailRI","populateMailbagId(this)",EVT_CHANGE);
	evtHandler.addEvents("weight","populateMailbagId(this)",EVT_BLUR);
	evtHandler.addEvents("weightUnit","populateMailbagId(this)",EVT_CHANGE);

   	}
}

function onScreenLoad(){
  frm=targetFormName;
  frm.elements.weight.value
  
  
	 if(frm.elements.disableListSuccess.value == "Y"){
	  	frm.elements.conDocNo.disabled = true;
	  	frm.elements.paCode.disabled = true;
	  	frm.elements.conDate.disabled = true;
		//added by A-6344 for ICRD-90687 start
		frm.elements.btn_conDate.disabled= true;
		//added by A-6344 for ICRD-90687 end
	  	//frm.type.disabled = true;
	  	enableField(frm.elements.btnDelete);

	  	var _direction = document.getElementsByName('direction');
	  	for(var _radioCnt=0;_radioCnt<_direction.length;_radioCnt++){
	  		_direction[_radioCnt].disabled = true;
	  	}
		if(!document.getElementById("lnkAddRoute").disabled){
			document.getElementById("lnkAddRoute").focus();
		}
     }else{
	  	frm.elements.conDocNo.focus();
	  	frm.elements.conDocNo.disabled = false;
	  	frm.elements.paCode.disabled = false;
	  	disableField(frm.elements.btnDelete);

     }
	 frm.elements.subType.disabled = true;
if(frm.elements.type.value == "CN46"){

	frm.elements.subType.disabled = false;

}
     if(frm.elements.tableFocus.value == "M"){
       	frm.elements.tableFocus.value = "";
     }
     if(frm.elements.tableFocus.value == "R"){
       	frm.elements.tableFocus.value = "";
     }
     if(frm.elements.duplicateFlightStatus.value == "Y"){
	          openPopUp("flight.operation.duplicateflight.do","600","280");
     }
     if(frm.elements.afterPopupSaveFlag.value == "Y"){
		frm.elements.afterPopupSaveFlag.value = "N";
		frm.elements.fromPopupflg.value = "N";
   		/*var originOE =document.getElementsByName("originOE");
  		var mailOOElength=originOE.length;
		if(originOE[originOE.length-2].disabled==false){
			originOE[originOE.length-2].focus();
		}*/
		var mailbagId =document.getElementsByName("mailbagId");
		if(mailbagId[mailbagId.length-2].disabled==false){
			mailbagId[mailbagId.length-2].focus();
		}
    }
    /* To make Combo Boxes Visible, ineffect to the set z-index*/
    //setSelectVisibilityInTable('div1');
}

function resetFocus(){
	 if(!event.shiftKey){
		if((!targetFormName.elements.conDocNo.disabled) && (!targetFormName.elements.conDocNo.readOnly)
			&& (targetFormName.elements.disableListSuccess.value != "Y")){
			targetFormName.elements.conDocNo.focus();
		}
		else if(!document.getElementById("lnkAddRoute").disabled){
			document.getElementById("lnkAddRoute").focus();
		}
	}
}

function onScreenloadSetHeight(){
	var height = document.body.clientHeight;;
	document.getElementById('pageDiv').style.height = ((height*95)/100)+'px';
	//alert((height*95)/100);
}


function invokeLov(obj,name){

   var index = obj.id.split(name)[1];

   if(name == "polLov"){
         displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.pol.value,'Airport','1','pol','',index);
   }
   if(name == "pouLov"){
         displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.elements.pou.value,'Airport','1','pou','',index);
   }
   if(name == "OOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.originOE.value,'OfficeOfExchange','1','originOE','',index);
   }
   if(name == "DOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destinationOE.value,'OfficeOfExchange','1','destinationOE','',index);
   }
   if(name == "SCLov"){
         displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.subClass.value,'OfficeOfExchange','1','subClass','',index);
   }
	if(name == "currencyCodeLov"){
	displayLOV('showCurrency.do','N','Y','showCurrency.do','targetFormName.currencyCode.value','Currency Lov','1','currencyCode','',index);

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
   frm=targetFormName;
   targetFormName.elements.lastPageNum.value = 0;
   targetFormName.elements.displayPage.value = 1;
   submitAction(frm,'/mailtracking.defaults.consignment.listconsignment.do?countTotalFlag=N');
}


function printConsignment(){
   frm=targetFormName;
   generateReport(frm,'/mailtracking.defaults.consignment.printscreen.do');
   formatNumberForLocale();
}

function clear(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.consignment.clearconsignment.do');

}

function insertNewRoutingDetails(){

 if(targetFormName.elements.newRoutingFlag.value == 'Y'){
    targetFormName.elements.newRoutingFlag.value = "";
	addRoute();
	targetFormName.elements.conDate.disabled=false;
	targetFormName.elements.conDate.focus();

 }
}

function contypeTabOut(){
 frm=targetFormName;

if(frm.elements.type.value == "CN46"){

	frm.elements.subType.disabled = false;

}else{
frm.elements.subType.value="";
	frm.elements.subType.disabled = true;
}
   targetFormName.elements.flightCarrierCode[0].focus();
}

function addMail(){
   frm=targetFormName;

     var incomplete = validateDespatchDetails();
     if(incomplete == "N"){
       return;
     }
     var incomplete = validateRoutingDetails();
     if(incomplete == "N"){
        return;
     }

   var originOE =document.getElementsByName("originOE");
   var mailbagId =document.getElementsByName("mailbagId");
   var mailOOElength=originOE.length;
   var selectedRows = document.getElementsByName('selectMail');
   var _opFlag = document.getElementsByName('mailOpFlag');
   var _row_Count = 1;
   var _rowEdit_Count = 0;
   var _maxPageLimt = frm.elements.maxPageLimit.value;
   for(var cnt=0;cnt<_opFlag.length;cnt++){
	   if("NOOP"!= _opFlag[cnt].value && "D"!= _opFlag[cnt].value ){
	   	++_row_Count;
	   	if("I" == _opFlag[cnt].value || "U" == _opFlag[cnt].value ){
	   		++_rowEdit_Count;
	   	}
	   }
   }
   if(_row_Count > _maxPageLimt){
	  targetFormName.elements.fromPopupflg.value="Y";
	  if(_rowEdit_Count > 0){
	  	showDialog({msg:'Data would be saved before proceeding further.Do you wish to continue?', type:4, parentWindow:self, parentForm:targetFormName, dialogId:'id_3',
		onClose: function (result) {
	  	screenConfirmDialog(targetFormName,'id_3');
	  	screenNonConfirmDialog(targetFormName,'id_3');
		}
		});
	  }else{
		submitAction(frm,'/mailtracking.defaults.consignment.addnewmaildirect.do');
	  }

   }else{
	   //submitAction(frm,'/mailtracking.defaults.consignment.addmail.do');
	  // recreateMultiTableDetails("mailtracking.defaults.consignment.addmail.do","div1","div2","ajaxUpdate");
	   addTemplateRow('mailTemplateRow','mailTableBody','mailOpFlag');
   }

   if(mailOOElength>1){
   	  frm.elements.originOE[mailOOElength-1].value=frm.elements.originOE[mailOOElength-2].value;
   	  frm.elements.destinationOE[mailOOElength-1].value=frm.elements.destinationOE[mailOOElength-2].value;
   	  frm.elements.category[mailOOElength-1].value=frm.elements.category[mailOOElength-2].value;
   	  frm.elements.mailClass[mailOOElength-1].value=frm.elements.mailClass[mailOOElength-2].value;
   	  frm.elements.subClass[mailOOElength-1].value=frm.elements.subClass[mailOOElength-2].value;
   	  frm.elements.year[mailOOElength-1].value=frm.elements.year[mailOOElength-2].value;
   	  frm.elements.dsn[mailOOElength-1].value=frm.elements.dsn[mailOOElength-2].value;
   	  frm.elements.mailHI[mailOOElength-1].value=frm.elements.mailHI[mailOOElength-2].value;
   	  frm.elements.mailRI[mailOOElength-1].value=frm.elements.mailRI[mailOOElength-2].value;
	  frm.elements.weightUnit[mailOOElength-1].value=frm.elements.weightUnit[mailOOElength-2].value;//added by A-8353 for ICRD-274933
  }

   if(mailbagId[mailbagId.length-2].disabled==false){
   	  mailbagId[mailbagId.length-2].focus();
   }

}

function deleteMail(){
   frm=targetFormName;
   var selectMail = document.getElementsByName('selectMail');

   if(selectMail.length > 0){
       if(validateSelectedCheckBoxes(frm,'selectMail',selectMail.length,1)){
            //submitAction(frm,'/mailtracking.defaults.consignment.deletemail.do');
            //recreateMultiTableDetails("mailtracking.defaults.consignment.deletemail.do","div1","div2","ajaxUpdate");
			/*for(var i=0; i<selectMail.length; i++)//commented by A-7371 as part ICRD-264960
			{
				if(selectMail[i].checked == true)
				{
					frm.elements.mailOpFlag[i].value = 'D';
				}
			    
			}*/

            deleteTableRow('selectMail','mailOpFlag');
           // document.getElementById('div1').scrollTop=0;
           /***********************************************/
		   //Added by Gopinath M @TRV on 03-Sep-2009
		   frm.masterMail.checked=false;
		   /***********************************************/
            //setSelectVisibilityInTable('div1');
       }
   }
}

function addRoute(){
   frm=targetFormName;
   //submitAction(frm,'/mailtracking.defaults.consignment.addroute.do');
   //recreateMultiTableDetails("mailtracking.defaults.consignment.addroute.do","div1","div2","ajaxUpdate");

   addTemplateRow('routeTemplateRow','routeTableBody','routeOpFlag');

}


function deleteRoute(){
   frm=targetFormName;
   var selectRoute = document.getElementsByName('selectRoute');
   if(selectRoute.length > 0){
       if(validateSelectedCheckBoxes(frm,'selectRoute',selectRoute.length,1)){
   	    //submitAction(frm,'/mailtracking.defaults.consignment.deleteroute.do');
   	    //recreateMultiTableDetails("mailtracking.defaults.consignment.deleteroute.do","div1","div2","ajaxUpdate");

   	    deleteTableRow('selectRoute','routeOpFlag');
   	    /*******************************************************************/
		//Added by Gopinath M @TRV on 03-Sep-2009
		frm.masterRoute.checked=false;
		/*******************************************************************/
       }
   }

}

function deleteConDoc(){
   frm=targetFormName;
   showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.warn.delete" />', type:4, parentWindow:self, parentForm:frm, dialogId:'id_1',
   onClose: function (result) {
   screenConfirmDialog(frm,'id_1');
   screenNonConfirmDialog(frm,'id_1');
}

   });

}


function closeConDoc(){
   frm=targetFormName;
    if(targetFormName.elements.fromScreen.value!=''){
       if(targetFormName.elements.fromScreen.value=="carditEnquiry"){
       submitForm(targetFormName,"mailtracking.defaults.consignment.close.do?fromScreen=carditEnquiry");
       }
   }
   if(hasFormChanged(targetFormName)){
 	showDialog({msg:'Consignment document has not been saved.Do you wish to continue?', type:4, parentWindow:self, parentForm:targetFormName, 	  dialogId:'id_2',
		onClose: function (result) {
 	screenConfirmDialog(targetFormName,'id_2');
 	screenNonConfirmDialog(targetFormName,'id_2');
		}
	});

   }else{

 	//location.href = appPath + "/home.jsp";
 	submitForm(targetFormName,"mailtracking.defaults.consignment.close.do");

   }

}

function saveConDoc(){
   frm=targetFormName;

   var incomplete = validateDespatchDetails();
   if(incomplete == "N"){
        return;
   }
   var incomplete = validateRoutingDetails();
   if(incomplete == "N"){
        return;
   }
   var incomplete = mailWeight();
   if(incomplete == "N"){
        return;
   }
   var originOE =document.getElementsByName("originOE");
   if(originOE.length == 1){
   	showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.nomaildetails" />',type:1,parentWindow:self});
        return;
   }
   submitAction(frm,'/mailtracking.defaults.consignment.saveconsignment.do');
}

//added by a-7871 for ICRD-234913 starts
function printMailtag(){
 frm=targetFormName;
 var chkboxes = document.getElementsByName('selectMail');
 var selIndex ='';
 var isSelected=false;
 if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){
			if(chkboxes[index].checked == true){
			isSelected=true;
				if(selIndex.trim().length == 0){
				if(!isNaN(chkboxes[index].value))
					selIndex=chkboxes[index].value;
					}
					else{
					if(!isNaN(chkboxes[index].value))
					selIndex=selIndex+"-"+chkboxes[index].value;
				    }
					}
					}
					}
					if(isSelected==false)
	{
	selIndex="ALL";
	}
 openPopUp("mailtracking.defaults.consignment.printmailtag.do?selectedIndexes="+selIndex,500,250);
}
//added by a-7871 for ICRD-234913 ends
function dsnPadding(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("dsn");
 var mailDSN =document.getElementsByName("dsn");

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


function rsnPadding(){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("rsn");
 var mailRSN =document.getElementsByName("rsn");

   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }

   }
}
//Commented for ICRD-59258
/*
function validateWeight(){
 frm=targetFormName;

  var weightArr =document.getElementsByName("weight");
  var mailRSN =document.getElementsByName("rsn");

      for(var i=0;i<weightArr.length;i++){
	       if(mailRSN[i].value.length != 0){

			if(weightArr[i].value.indexOf(".") < 0){
                if(weightArr[i].value.length > 3){
			weightArr[i].focus();
			showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
			return;
		}
	     }else{
                 if(weightArr[i].value.indexOf(".") > 3){
	  	        weightArr[i].focus();
			showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
			return;
		 }
	         if(weightArr[i].value.length > 6){
	            weightArr[i].focus();
	 	  	showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
	 	 	return;
	         }

	        if(weightArr[i].value.split(".")[1].length> 1||(weightArr[i].value.split(".")[1].length==2 && weightArr[i].value.split(".")[1].substring(1)!=0)){
				weightArr[i].focus();
			showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
			return;
	         }
	       }
         }else{
			   if(weightArr[i].value.indexOf(".") < 0){
						   if(weightArr[i].value.length > 4){
					weightArr[i].focus();
					showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
					return;
				}
				 }else{
							if(weightArr[i].value.indexOf(".") > 4){
						weightArr[i].focus();
					showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
					return;
		 		}
		 		}
				if(weightArr[i].value.split(".").length> 1){
		 		if(weightArr[i].value.split(".")[1].length> 2){
					weightArr[i].focus();
				showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
				return;
	         }
			 }

      }
   }
}
*/
function validateDespatchDetails(){

frm=targetFormName;

   var opFlag =document.getElementsByName("mailOpFlag");

    var originOE =document.getElementsByName("originOE");
    var destinationOE =document.getElementsByName("destinationOE");
	var subClassDSN =document.getElementsByName("subClass");
     var year =document.getElementsByName("year");
      var dsn =document.getElementsByName("dsn");
	  var mailweight =document.getElementsByName("weight");
    for(var i=0;i<originOE.length;i++){

     if(opFlag[i].value != "NOOP"){

        if(originOE[i].value.length == 0){
           showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.ooeempty" />',type:1,parentWindow:self});
           originOE[i].focus();
           return "N";
       }
       if(originOE[i].value.length != 6){
	  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.ooelength" />',type:1,parentWindow:self});
	  originOE[i].focus();
	  return "N";
       }

       if(destinationOE[i].value.length == 0){
                 showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.doeempty" />',type:1,parentWindow:self});
                 destinationOE[i].focus();
                 return "N";
             }
             if(destinationOE[i].value.length != 6){
     	    showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.doelength" />',type:1,parentWindow:self});
     	    destinationOE[i].focus();
     	    return "N";
       }

       if(subClassDSN[i].value.length != 0){
     		if(subClassDSN[i].value.length != 2){
     		  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.sclength" />',type:1,parentWindow:self});
     		  subClassDSN[i].focus();
     		  return "N";
     		}
          }

          if(year[i].value.length == 0){
	               showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.yearempty" />',type:1,parentWindow:self});
	               year[i].focus();
	               return "N";
         }

      	 if(dsn[i].value.length == 0){
      	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.dsnempty" />',type:1,parentWindow:self});
      	     dsn[i].focus();
      	     return "N";
      	 }

       if(mailweight[i].value== 0 && opFlag[i].value != 'D'){
      
           showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.mailweightempty" />',type:1,parentWindow:self});
           mailweight[i].focus();
           return "N";
       }
	   if(mailweight[i].value>999.9)
	   {
	   showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',type:1,parentWindow:self});
           mailweight[i].focus();
           return "N";

       }
     }

    }



       var rsn =document.getElementsByName("rsn");
       var hi =document.getElementsByName("mailHI");
       var ri =document.getElementsByName("mailRI");
       var numBags =document.getElementsByName("numBags");
       var subClass =document.getElementsByName("subClass");
	   var mailclass=document.getElementsByName("mailClass");
       for(var i=0;i<rsn.length;i++){
        if(opFlag[i].value != "NOOP"){
	  if(rsn[i].value.length != 0){

	      if(subClass[i].value.length == 0){
		  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.scempty" />',type:1,parentWindow:self});
		  subClass[i].focus();
		  return "N";
	      }
	      if(subClass[i].value.length != 2){
		  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.sclength" />',type:1,parentWindow:self});
		  subClass[i].focus();
		  return "N";
	      }
		   if(mailclass[i].value.length == 0){
		  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.classempty" />',type:1,parentWindow:self});
		  mailclass[i].focus();
		  return "N";
	      }
	      if(mailclass[i].value.length != 1){
		  showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.classlength" />',type:1,parentWindow:self});
		  mailclass[i].focus();
		  return "N";
	      }

	     // numBags[i].value=1;
	     if(numBags[i].value > 1){

		     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.numbagsone" />',type:1,parentWindow:self});
		     numBags[i].focus();
		     return "N";
	     }
	     if(hi[i].value == ""){
		     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.hiempty" />',type:1,parentWindow:self});
		     hi[i].focus();
		     return "N";
	     }
	     if(ri[i].value == ""){
	     	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.riempty" />',type:1,parentWindow:self});
	     	     ri[i].focus();
	     	     return "N";
	     }
	  }else{

	     if(hi[i].value != ""){
		     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.hinotempty" />',type:1,parentWindow:self});
		     hi[i].focus();
		     return "N";
	     }
	     if(ri[i].value != ""){
		     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.rinotempty" />',type:1,parentWindow:self});
		     ri[i].focus();
		     return "N";
	     }

	  }
	}
	}




      var numBags =document.getElementsByName("numBags");
       for(var i=0;i<numBags.length;i++){
       if(opFlag[i].value != "NOOP"){
           if(numBags[i].value.length == 0){
              showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.numbagsempty" />',type:1,parentWindow:self});
              numBags[i].focus();
              return "N";
          }
          if(numBags[i].value == 0){
	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.numbagszero" />',type:1,parentWindow:self});
	     numBags[i].focus();
	     return "N";
	  }
       }
       }

     /* var weight =document.getElementsByName("weight");
       for(var i=0;i<weight.length;i++){
       if(opFlag[i].value != "NOOP"){
           if(weight[i].value.length == 0){
              showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.weightempty" />',1,self);
              weight[i].focus();
              return "N";
          }
           if(weight[i].value == 0 || weight[i].value == 0.00){
	     showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.weightzero" />',1,self);
	     weight[i].focus();
	     return "N";
	  }
	}

       }

      var weightArr =document.getElementsByName("weight");
      var mailRSN =document.getElementsByName("rsn");

      for(var i=0;i<weightArr.length;i++){

      if(opFlag[i].value != "NOOP"){

       if(mailRSN[i].value.length != 0){

	     if(weightArr[i].value.indexOf(".") < 0){
		if(weightArr[i].value.length > 3){
		  weightArr[i].focus();
			showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
			return "N";
      		}
      	     }else{
                 if(weightArr[i].value.indexOf(".") > 4){
      	  	        weightArr[i].focus();
      			showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
      			return "N";
      		 }
      		 if(weightArr[i].value.length > 6){
      		 	weightArr[i].focus();
      		 	showDialog('<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.invalidweight" />',1,self);
      			return "N";
      		 }
	   }
	   }
       }
     }*/

}

var _tableDiv="";
function refreshTableDetails(strAction,divId)
{
var __extraFn='updateMailDetailSection';
_tableDiv=divId;

asyncSubmit(targetFormName,strAction,__extraFn,null,null);
}

function updateMailDetailSection(_tableInfo){
   //var  oldDivId = "mailDiv";
    _innerFrm = _tableInfo.document.getElementsByTagName("form")[0];

     _newDivId = _tableInfo.document.getElementById("mailDiv");


 	document.getElementById(_tableDiv).innerHTML =  _newDivId.innerHTML;


}
function validateRoutingDetails(){


	var opFlag =document.getElementsByName("routeOpFlag");

      var flightCarrierCode =document.getElementsByName("flightCarrierCode");
      var flightNumber =document.getElementsByName("flightNumber");
       var depDate =document.getElementsByName("depDate");
      var pol =document.getElementsByName("pol");
       var pou =document.getElementsByName("pou");
      for(var i=0;i<flightCarrierCode.length;i++){

	  if(flightCarrierCode[i].value.length == 0 && flightNumber[i].value.length == 0 && depDate[i].value.length == 0 && pol[i].value.length == 0 && pou[i].value.length == 0){

	  }

       else if(opFlag[i].value != "NOOP"){

       	  if(flightCarrierCode[i].value.length == 0){
       	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.fltcarcodeempty" />',type:1,parentWindow:self});
       	     flightCarrierCode[i].focus();
       	     return "N";
       	 }


       if(flightNumber[i].value.length == 0){
       	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.fltnumempty" />',type:1,parentWindow:self});
       	     flightNumber[i].focus();
       	     return "N";
       	 }

       if(depDate[i].value.length == 0){
       	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.fltdtempty" />',type:1,parentWindow:self});
       	     depDate[i].focus();
       	     return "N";
	 }

       if(pol[i].value.length == 0){
             	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.polempty" />',type:1,parentWindow:self});
             	     pol[i].focus();
             	     return "N";
      	 }

	  if(pou[i].value.length == 0){
	     showDialog({msg:'<bean:message bundle="consignmentResources" key="mailtracking.defaults.consignment.msg.alrt.pouempty" />',type:1,parentWindow:self});
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


 ////////////////// FOR ASYNC SUBMIT ///////////////////////////////////////////////

 var _currDivIdMail = "";
 var _currDivIdRoute = "";

 function recreateMultiTableDetails(strAction,divId1,divId2){

 	var __extraFn="updateMultiTableCode";

 	if(arguments[3]!=null){
 		__extraFn=arguments[3];
 	}

 	_currDivIdMail = divId1;
 	_currDivIdRoute = divId2;

 	asyncSubmit(targetFormName,strAction,__extraFn,null,null);

 }

 function ajaxUpdate(_tableInfo){

 	_innerFrm=_tableInfo.document.getElementsByTagName("form")[0];

 	var _disableListSuccess = _innerFrm.disableListSuccess.value;
  	targetFormName.elements.disableListSuccess.value = _disableListSuccess;

  	var _tableFocus = _innerFrm.tableFocus.value;
  	targetFormName.elements.tableFocus.value = _tableFocus;

 	onScreenLoad();
    if(_asyncErrorsExist) return;

    updateMultiTableCode(_tableInfo);

 }


 function updateMultiTableCode(_tableInfo){

 	_despatch=_tableInfo.document.getElementById("_mail");
	_mailTag=_tableInfo.document.getElementById("_route")	;
	document.getElementById(_currDivIdMail).innerHTML=_despatch.innerHTML;
	document.getElementById(_currDivIdRoute).innerHTML=_mailTag.innerHTML;

 }

 var lstPageNum=0;
 var dispPageNum=0;
 function submitList(strLastPageNum,strDisplayPage){

    var incomplete = validateDespatchDetails();
    if(incomplete == "N"){
       return;
    }
    var incomplete = validateRoutingDetails();
    if(incomplete == "N"){
       return;
    }

	lstPageNum= strLastPageNum;
	dispPageNum = strDisplayPage;
	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	var _opFlag = document.getElementsByName('mailOpFlag');
	var _row_Count = 1;
	var _rowEdit_Count = 0;
	var _maxPageLimt = targetFormName.elements.maxPageLimit.value;
	for(var cnt=0;cnt<_opFlag.length;cnt++){
	    if("I" == _opFlag[cnt].value || "U" == _opFlag[cnt].value  || "D" == _opFlag[cnt].value){
		    ++_rowEdit_Count;
	    }
	}
	if(_rowEdit_Count > 0){
		showDialog({msg:'Data would be saved before proceeding further.Do you wish to continue?', type:4, parentWindow:self, parentForm:targetFormName, dialogId:'id_4',

		onClose: function (result) {
		screenConfirmDialog(targetFormName,'id_4');
		screenNonConfirmDialog(targetFormName,'id_4');
		}

		});

	}else{
		 submitForm(targetFormName, appPath + '/mailtracking.defaults.consignment.listconsignment.do?countTotalFlag=Y');
	}
 }


  function addMultiple()
  {
<!--Modified by A-7938 for ICRD-243958-->
  openPopUp('mailtracking.defaults.consignment.addmultiplescreenload.do',550,390);//Modified by A-7371 for ICRD-219894

  }

 ////////////////////////////////////////////////////////////////////////////////////////
 	//-----------------Ajax call for Mail Tag Details -----------------------------------//
function populateMailDetails(obj){
if(obj.value!=null && obj.value.length>0){//added as part of ICRD-258154

	var  _rowCount =null;
	try{
		_rowCount=obj.getAttribute("rowCount");

	}catch(err){
		return;
	}
	if(targetFormName.elements.mailbagId[_rowCount].value.length==12){
		
		disableField(targetFormName.elements.originOE);
		disableField(targetFormName.elements.destinationOE);
		disableField(targetFormName.elements.category);
		disableField(targetFormName.elements.subClass);
		disableField(targetFormName.elements.year);
		disableField(targetFormName.elements.dsn);
		disableField(targetFormName.elements.rsn);
		disableField(targetFormName.elements.mailHI);
		disableField(targetFormName.elements.mailRI);
		disableField(targetFormName.elements.weight);
        disableField(targetFormName.elements.mailClass);
        targetFormName.elements.numBags[_rowCount].value="1"
		
	}
	globalObj=obj;
	var funct_to_overwrite = "refreshMailDetails";
	var strAction = 'mailtracking.defaults.consignment.populatemailtagdetails.do?selectMail='+_rowCount;

  	asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
}
}

 function refreshMailDetails(_tableInfo){
var _rowCount = globalObj.getAttribute("rowCount");
if(targetFormName.elements.mailbagId.length>0){
targetFormName.elements.originOE[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailOOE").innerHTML;
targetFormName.elements.destinationOE[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailDOE").innerHTML;
targetFormName.elements.category[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailCat").innerHTML;
targetFormName.elements.mailClass[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailClass").innerHTML;
targetFormName.elements.subClass[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailSC").innerHTML;
targetFormName.elements.year[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailYr").innerHTML;
targetFormName.elements.dsn[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailDSN").innerHTML;
targetFormName.elements.rsn[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailRSN").innerHTML;
targetFormName.elements.mailHI[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailHNI").innerHTML;
targetFormName.elements.mailRI[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailRI").innerHTML;
targetFormName.elements.weight[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailWt").innerHTML;
targetFormName.elements.mailbagId[_rowCount].value=_tableInfo.document.getElementById("_ajax_mailbagId").innerHTML;
}
dsnPadding();
rsnPadding();
 var incomplete= mailWeight();
  }

function populateMailbagId(obj){
	var  _rowCount =null;
	try{
		_rowCount=obj.getAttribute("rowCount");

	}catch(err){
		return;
	}
		if(targetFormName.elements.originOE[_rowCount].value!=null&&targetFormName.elements.originOE[_rowCount].value!=""&&
		targetFormName.elements.destinationOE[_rowCount].value!=null&&targetFormName.elements.destinationOE[_rowCount].value!=""&&
		targetFormName.elements.category[_rowCount].value!=null&&targetFormName.elements.category[_rowCount].value!=""&&
		targetFormName.elements.subClass[_rowCount].value!=null&&targetFormName.elements.subClass[_rowCount].value!=""&&
		targetFormName.elements.year[_rowCount].value!=null&&targetFormName.elements.year[_rowCount].value!=""&&
		targetFormName.elements.dsn[_rowCount].value!=null&&targetFormName.elements.dsn[_rowCount].value!=""&&
		targetFormName.elements.rsn[_rowCount].value!=null&&targetFormName.elements.rsn[_rowCount].value!=""&&
		targetFormName.elements.mailHI[_rowCount].value!=null&&targetFormName.elements.mailHI[_rowCount].value!=""&&
		targetFormName.elements.mailRI[_rowCount].value!=null&&targetFormName.elements.mailRI[_rowCount].value!=""&&
		targetFormName.elements.weight[_rowCount].value!=null&&targetFormName.elements.weight[_rowCount].value!=""){
		targetFormName.elements.mailbagId[_rowCount].value="";
		populateMailDetails(obj);
	}else{
		targetFormName.elements.mailbagId[_rowCount].value="";
		}
		
}
 function mailWeight(){
 frm=targetFormName;
 var weightArr =document.getElementsByName("weight");
 var weight =document.getElementsByName("weight");
 var weightUnit=document.getElementsByName("weightUnit");
   for(var i=0;i<weightArr.length;i++){
	  
	   var spiltWeight= weight[i].value.split('.');
	   if(spiltWeight.length>1){
	   if (weightUnit[i].value=='K'){
	   if(spiltWeight[1].length>1){
		showDialog({msg:'Weight in Kilogram can accept only 1 value after  decimal point',type:1,parentWindow:self});
	    targetFormName.elements.weight[weightArr.length].focus();
	   return "N";   
	   }
	   }
      else	{
		if(spiltWeight[1]>0) {
		   showDialog({msg:'Weight in pound and hectogram cannot have decimal value',type:1,parentWindow:self});
	       targetFormName.elements.mailWt[weightArr.length].focus();
	       return "N";   
		} 
	  }
	}  
   }
} 