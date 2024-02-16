<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

function screenSpecificEventRegister()
{
   var frm=targetFormName;
	preWarningMessages();
   onScreenLoad();
   with(frm){

     //CLICK Events
     	evtHandler.addEvents("btnSave","saveUpload()",EVT_CLICK);
     	evtHandler.addEvents("btnSaveAll","saveAll()",EVT_CLICK);
     	evtHandler.addEvents("btnClose","closeUpload()",EVT_CLICK);

        evtHandler.addEvents("btnFlightDetails","flightDetails()",EVT_CLICK);
     	evtHandler.addEvents("btnAcceptAcknowledge","acceptAcknowledge()",EVT_CLICK);
     	evtHandler.addEvents("btnArriveAcknowledge","arriveAcknowledge()",EVT_CLICK);
     	evtHandler.addEvents("btnReturnAcknowledge","returnAcknowledge()",EVT_CLICK);
     	evtHandler.addEvents("btnOffloadAcknowledge","offloadAcknowledge()",EVT_CLICK);
		evtHandler.addEvents("btnReassignmailAcknowledge","reassignmailAcknowledge()",EVT_CLICK);
     	evtHandler.addEvents("btnTransferAcknowledge","transferAcknowledge()",EVT_CLICK);
     	evtHandler.addEvents("btnReassigndespatchAcknowledge","reassigndespatchAcknowledge()",EVT_CLICK);

     	evtHandler.addEvents("btnContDetails","contDetails()",EVT_CLICK);
     	evtHandler.addEvents("btnReturnReason","returnReason()",EVT_CLICK);
     	evtHandler.addEvents("btnOffloadReason","offloadReason()",EVT_CLICK);

   }
}

function preWarningMessages(){

   collapseAllRows();



}


function screenSpecificTabSetup(){
   setupPanes('container1','tab1');
   displayTabPane('container1','tab1');
}


function onScreenLoad(){
  frm=targetFormName;
  collapseAllRows();
  if(frm.continerFlag.value == "OPEN"){

         frm.continerFlag.value = "";
         var select = frm.continerDetails.value;
         var strAction="mailtracking.defaults.upload.screenloadcontainerdtls.do";
         var strUrl=strAction+"?continerDetails="+select;
         openPopUp(strUrl,500,190);

  }

  if(frm.elements.flightFlag.value == "Y"){

           frm.elements.flightFlag.value = "";
           var select = frm.elements.flightDetails.value;

           var strAction="mailtracking.defaults.upload.screenloadflightdetails.do";
           var strUrl=strAction+"?flightDetails="+select;
           openPopUp(strUrl,425,175);

  }

  if(frm.elements.disableStat.value=="SAVED"){

      frm.btnReturnAcknowledge.disabled=false;
      frm.btnArriveAcknowledge.disabled=false;
      frm.btnAcceptAcknowledge.disabled=false;
      frm.btnTransferAcknowledge.disabled=false;
      frm.btnOffloadAcknowledge.disabled=false;
      frm.btnReassignmailAcknowledge.disabled=false;
      frm.btnTransferAcknowledge.disabled = false;
      frm.btnReassigndespatchAcknowledge.disabled = false;
  }else{
      frm.btnReturnAcknowledge.disabled=true;
      frm.btnArriveAcknowledge.disabled=true;
      frm.btnAcceptAcknowledge.disabled=true;
      frm.btnTransferAcknowledge.disabled=true;
      frm.btnOffloadAcknowledge.disabled=true;
      frm.btnReassignmailAcknowledge.disabled=true;
      frm.btnTransferAcknowledge.disabled = true;
      frm.btnReassigndespatchAcknowledge.disabled = true;
  }


  if(frm.elements.preassignFlag.value == "Y"){
       frm.btnFlightDetails.disabled=true;
  }else{
       frm.btnFlightDetails.disabled=false;
  }


  colorTableRows();

}


/**
 *@param frm
 *@param action
 */
function submitAction(frm,action){
	var actionName = appPath+action;
	submitForm(frm,actionName);
}

function saveUpload(){
   frm=targetFormName;
   if(frm.elements.TABPANE_ID_FLD.value == "tab1"){
   	submitAction(frm,'/mailtracking.defaults.upload.saveaccept.do?savemode=ACP');
   }
   if(frm.elements.TABPANE_ID_FLD.value == "tab2"){
      	submitAction(frm,'/mailtracking.defaults.upload.savearrive.do?savemode=ARR');
   }
   if(frm.elements.TABPANE_ID_FLD.value == "tab3"){
      	submitAction(frm,'/mailtracking.defaults.upload.savereturn.do?savemode=RET');
   }
   if(frm.elements.TABPANE_ID_FLD.value == "tab4"){
         	submitAction(frm,'/mailtracking.defaults.upload.savereassign.do?savemode=RSGM');
   }
   if(frm.elements.TABPANE_ID_FLD.value == "tab5"){
         	submitAction(frm,'/mailtracking.defaults.upload.savereassigndespatch.do?savemode=RSGD');
   }
   if(frm.elements.TABPANE_ID_FLD.value == "tab6"){
               	submitAction(frm,'/mailtracking.defaults.upload.savetransfer.do?savemode=TRA');
   }
   if(frm.elements.TABPANE_ID_FLD.value == "tab7"){
      	submitAction(frm,'/mailtracking.defaults.upload.saveoffload.do?savemode=OFL');
   }

}




function flightDetails(){

     frm=targetFormName;
    var chkbox =document.getElementsByName("masterMailTag");
       	if(chkbox.length > 0){
		for(var i=0; i<chkbox.length;i++){
		if(chkbox[i].checked) {
        if(validateSelectedCheckBoxes(frm,'masterMailTag',chkbox.length,'1')){
	  var selectMail = "";
	  var cnt1 = 0;
	  for(var i=0; i<chkbox.length;i++){
	      if(chkbox[i].checked) {
	         if(cnt1 == 0){
	   	       selectMail = chkbox[i].value;
		       cnt1 = 1;
		 }else{
	   	       selectMail = selectMail + "," + chkbox[i].value;
			    }
		 }
	      }
	  }
	  frm.elements.flightDetails.value = selectMail;
	  frm.elements.flightFlag.value = "Y";
	  if(frm.elements.preassignFlag.value == "N"){
             submitAction(frm,'/mailtracking.defaults.upload.updatemaildetails.do');
          }

       }
       }
	 }else{
		return;
	 }

}

function closeUpload(){

     var strAction="mailtracking.defaults.upload.uploadsummary.do";
     openPopUp(strAction,700,550);

}



function saveAll(){
   frm=targetFormName;
   submitAction(frm,'/mailtracking.defaults.upload.saveall.do?enablemode=true');
}




function acceptAcknowledge(){
  frm=targetFormName;
  var check = validateSelectedCheckBoxes(frm, 'selectAccept', 1000000000, 1);
		if(check) {

		var chkbox =document.getElementsByName("selectAccept");

		 var select=new Array();
		 var y=0;
		  for(var i=0; i<chkbox.length;i++){
			 if(chkbox[i].checked) {
				select[y] = chkbox[i].value;

				y++;
			 }}


	      var ackFlag =document.getElementsByName("ackFlag");
	      for(var i=0;i<select.length;i++){
			  var first=(select[i].split("-"))[0];
			  var second=(select[i].split("-"))[1];

		for(var j=0;j<ackFlag.length;j++){

			var firstack=(ackFlag[j].value.split("-"))[0];
			var secondack=(ackFlag[j].value.split("-"))[1];

			if((parseInt(first)==parseInt(firstack)) && (parseInt(second)==parseInt(secondack))
			&& (((ackFlag[j].value.split("-"))[3])=="ACC")){
				var box=(ackFlag[j].value.split("-"))[2];
				// alert(ackFlag[j].value);
				 //alert(select[i]);
	      if(box=="ACCNOACK"){

			  showDialog({msg:"Some of the selected exceptions cannot be acknowledged.", type:1,parentWindow:self,parentformname:frm, DialogId:'id_1'});
			  return;
		}

	}

     }}



  submitAction(frm,'/mailtracking.defaults.upload.acceptacknowledge.do');
}

}

function arriveAcknowledge(){

  frm=targetFormName;
  var check = validateSelectedCheckBoxes(frm, 'selectArrive', 1000000000, 1);
		if(check) {

			var chkbox =document.getElementsByName("selectArrive");

			 var select=new Array();
			 var y=0;
			 for(var i=0; i<chkbox.length;i++){
			    if(chkbox[i].checked) {
			 	select[y] = chkbox[i].value;
				y++;
			    }
			 }


			 var ackFlag =document.getElementsByName("ackFlag");
			 for(var i=0;i<select.length;i++){
			   var first=(select[i].split("-"))[0];

			   for(var j=0;j<ackFlag.length;j++){

				var firstack=(ackFlag[j].value.split("-"))[0];

				if((parseInt(first)==parseInt(firstack))
				    && (((ackFlag[j].value.split("-"))[3])=="ARR")){
				      var box=(ackFlag[j].value.split("-"))[2];
				      if(box=="ARRNOACK"){
					  showDialog({msg:"Some of the selected exceptions cannot be acknowledged.", type:1,parentWindow: self,parentformname: frm, DialogId:'id_1'});
					  return;
				      }
				}

			     }
			  }



  submitAction(frm,'/mailtracking.defaults.upload.arriveacknowledge.do');
}

}

function returnAcknowledge(){
  frm=targetFormName;
  var check = validateSelectedCheckBoxes(frm, 'selectReturn', 1000000000, 1);
if(check){
var chkbox =document.getElementsByName("selectReturn");

						 var select=new Array();
						 var y=0;
						  for(var i=0; i<chkbox.length;i++){
							 if(chkbox[i].checked) {
								select[y] = chkbox[i].value;

								y++;
							 }}


			      var ackFlag =document.getElementsByName("ackFlag");
			      for(var i=0;i<select.length;i++){
					  var first=select[i];


				for(var j=0;j<ackFlag.length;j++){

					var firstack=(ackFlag[j].value.split("-"))[0];


					if((parseInt(first)==parseInt(firstack))
					&& (((ackFlag[j].value.split("-"))[2])=="RET")){
						var box=(ackFlag[j].value.split("-"))[1];
						 //alert(ackFlag[j].value);
						 //alert(select[i]);
									      if(box=="RETNOACK"){

											  showDialog({msg:"Some of the selected exceptions cannot be acknowledged.", type:1,parentWindow: self,parentformname:frm, DialogId:'id_1'});
											  return;
				}

						}

			     }}


  submitAction(frm,'/mailtracking.defaults.upload.returnacknowledge.do');}


}


function offloadAcknowledge(){
  frm=targetFormName;
  var check = validateSelectedCheckBoxes(frm, 'selectOffload', 1000000000, 1);
	if(check){
	var chkbox =document.getElementsByName("selectOffload");
	    var select=new Array();
	    var y=0;
	    for(var i=0; i<chkbox.length;i++){
		 if(chkbox[i].checked) {
			select[y] = chkbox[i].value;
			y++;
		 }
	    }
      var ackFlag =document.getElementsByName("ackFlag");
      for(var i=0;i<select.length;i++){
	   var first=select[i];
           for(var j=0;j<ackFlag.length;j++){
		var firstack=(ackFlag[j].value.split("-"))[0];
		if((parseInt(first)==parseInt(firstack))
		&& (((ackFlag[j].value.split("-"))[2])=="OFL")){
			var box=(ackFlag[j].value.split("-"))[1];
			if(box=="OFLNOACK"){
			  showDialog({msg:"Some of the selected exceptions cannot be acknowledged.", 1, parentWindow:self,parentformname:frm,DialogId: 'id_1'});
			   return;
			}
		}
           }
       }


  submitAction(frm,'/mailtracking.defaults.upload.offloadacknowledge.do');}


}

function reassignmailAcknowledge(){
  frm=targetFormName;
  var check = validateSelectedCheckBoxes(frm, 'selectReassignmail', 1000000000, 1);
	if(check){
	var chkbox =document.getElementsByName("selectReassignmail");
	    var select=new Array();
	    var y=0;
	    for(var i=0; i<chkbox.length;i++){
		 if(chkbox[i].checked) {
			select[y] = chkbox[i].value;
			y++;
		 }
	    }
      var ackFlag =document.getElementsByName("ackFlag");
      for(var i=0;i<select.length;i++){
	   var first=select[i];
           for(var j=0;j<ackFlag.length;j++){
		var firstack=(ackFlag[j].value.split("-"))[0];
		if((parseInt(first)==parseInt(firstack))
		&& (((ackFlag[j].value.split("-"))[2])=="RSGM")){
			var box=(ackFlag[j].value.split("-"))[1];
			if(box=="RSGMNOACK"){
			  showDialog({msg:"Some of the selected exceptions cannot be acknowledged.", type:1, parentWindow:self,parentformname: frm, DialogId:'id_1'});
			   return;
			}
		}
           }
       }


  submitAction(frm,'/mailtracking.defaults.upload.reassignmailacknowledge.do');}


}


function transferAcknowledge(){
  frm=targetFormName;
  var check = validateSelectedCheckBoxes(frm, 'selectTransfer', 1000000000, 1);
 	if(check){
	var chkbox =document.getElementsByName("selectTransfer");
	    var select=new Array();
	    var y=0;
	    for(var i=0; i<chkbox.length;i++){
		 if(chkbox[i].checked) {
			select[y] = chkbox[i].value;
			y++;
		 }
	    }
      var ackFlag =document.getElementsByName("ackFlag");
      for(var i=0;i<select.length;i++){
	   var first=select[i];
           for(var j=0;j<ackFlag.length;j++){
		var firstack=(ackFlag[j].value.split("-"))[0];
		if((parseInt(first)==parseInt(firstack))
		&& (((ackFlag[j].value.split("-"))[2])=="TRA")){
			var box=(ackFlag[j].value.split("-"))[1];
			if(box=="TRANOACK"){
			  showDialog({msg:"Some of the selected exceptions cannot be acknowledged.",type:1,parentWindow:self, parentformname:frm,DialogId:'id_1'});
			   return;
			}
		}
           }
       }


  //submitAction(frm,'/mailtracking.defaults.upload.transferacknowledge.do');

  }


}


function reassigndespatchAcknowledge(){
  frm=targetFormName;
  var check = validateSelectedCheckBoxes(frm, 'selectReassigndespatch', 1000000000, 1);
 	if(check){
	var chkbox =document.getElementsByName("selectReassigndespatch");
	    var select=new Array();
	    var y=0;
	    for(var i=0; i<chkbox.length;i++){
		 if(chkbox[i].checked) {
			select[y] = chkbox[i].value;
			y++;
		 }
	    }
      var ackFlag =document.getElementsByName("ackFlag");
      for(var i=0;i<select.length;i++){
	   var first=select[i];
           for(var j=0;j<ackFlag.length;j++){
		var firstack=(ackFlag[j].value.split("-"))[0];
		if((parseInt(first)==parseInt(firstack))
		&& (((ackFlag[j].value.split("-"))[2])=="RSGD")){
			var box=(ackFlag[j].value.split("-"))[1];
			if(box=="RSGDNOACK"){
			  showDialog({msg:"Some of the selected exceptions cannot be acknowledged.",type:1,parentWindow:self, parentformname:frm,DialogId:'id_1'});
			   return;
			}
		}
           }
       }


  submitAction(frm,'/mailtracking.defaults.upload.reassigndespatchack.do');

  }


}



function contDetails(){
  frm=targetFormName;
  var chkbox =document.getElementsByName("selectArrive");
  if(validateSelectedCheckBoxes(frm,'selectArrive',chkbox.length,'1')){

            var selectMail = "";
            for(var i=0; i<chkbox.length;i++){
        	if(chkbox[i].checked) {
        	    selectMail = chkbox[i].value;
        	}
            }

            frm.elements.continerDetails.value = selectMail;

         submitAction(frm,'/mailtracking.defaults.upload.checkflight.do');
   }

}


function returnReason(){
  frm=targetFormName;
  var chkbox =document.getElementsByName("selectReturn");
    if(validateSelectedCheckBoxes(frm,'selectReturn',chkbox.length,'1')){

             var selectMail = "";
             var cnt1 = 0;
             for(var i=0; i<chkbox.length;i++){
      	  if(chkbox[i].checked) {
      	     if(cnt1 == 0){
      		  selectMail = chkbox[i].value;
      		  cnt1 = 1;
      	    }else{
      		  selectMail = selectMail + "," + chkbox[i].value;
      	    }
      	 }
           }
           var strAction="mailtracking.defaults.upload.screenloadreturnreason.do";
           var strUrl=strAction+"?returnDetails="+selectMail;
           openPopUp(strUrl,400,170);
   }
}



function offloadReason(){
  frm=targetFormName;
  var chkbox =document.getElementsByName("selectOffload");
    if(validateSelectedCheckBoxes(frm,'selectOffload',chkbox.length,'1')){

             var selectMail = "";
             var cnt1 = 0;
             for(var i=0; i<chkbox.length;i++){
      	     if(chkbox[i].checked) {
      	    	 if(cnt1 == 0){
			selectMail = chkbox[i].value;
			cnt1 = 1;
      	    	 }else{
      		  	selectMail = selectMail + "," + chkbox[i].value;
      	    	 }
      	    }
           }
           var strAction="mailtracking.defaults.upload.screenloadoffloadreason.do";
           var strUrl=strAction+"?offloadDetails="+selectMail;
           openPopUp(strUrl,400,170);
   }
}




function singleSelectContainer(frm,checkVal,type){
     singleSelectCb(frm,checkVal,type);
   selectAllChildCheckbox();

}


function singleSelectMail(frm,checkVal,type)
{
	//singleSelectCb(frm,checkVal,type);
	checkAllMail(checkVal);

}

function selectAllChildCheckbox(){

var selectMail = document.getElementsByName("masterMailTag");
var masterAccept = document.getElementsByName("masterAccept");
var selectAccept = document.getElementsByName("selectAccept");

	 var selectContainer = "";

	 for(var i=0; i<selectMail.length;i++){
	     if(selectMail[i].checked){
	         selectContainer = selectMail[i].value;
	         masterAccept[i].checked = true;
	     }else{
	         masterAccept[i].checked = false;
	     }
	 }

	 if(selectContainer != ""){
	      for(var j=0; j<selectAccept.length;j++){
	         var DSN = (selectAccept[j].value.split("-"))[0];
	         if(selectContainer == DSN){
		      selectAccept[j].checked = true;
		 }else{
		 	selectAccept[j].checked = false;
		 }
	     }
	 }else{

	    for(var j=0; j<selectAccept.length;j++){
		 selectAccept[j].checked = false;
	    }
	 }
}


function checkAllMail(checkVal){

var selectMail = document.getElementsByName("masterMailTag");
var masterAccept = document.getElementsByName("masterAccept");
var selectAccept = document.getElementsByName("selectAccept");


    frm=targetFormName;


	  for(var j=0; j<selectAccept.length;j++){
	     var DSN = (selectAccept[j].value.split("-"))[0];
	     if(checkVal != ""){
		if(checkVal == DSN){

		  for(var i=0; i<masterAccept.length;i++){

			if(masterAccept[i].value == checkVal){

			   if(masterAccept[i].checked){
				 selectAccept[j].checked = true;
			   }else{
			      selectAccept[j].checked = false;
			   }
			}
		  }

	        }else{
	      	    selectAccept[j].checked = false;
	        }
	     }
	 }



}


/*
 *Checking one individual checkbox to show error
 */

function selectCheckAccept(frm,accept){

var chkbox =document.getElementsByName("selectAccept");
singleSelectCb(frm,accept,chkbox)

      for(var i=0; i<chkbox.length;i++){
  	  if(chkbox[i].checked) {
  	      if(accept == (chkbox[i].value)){
		    chkbox[i].checked = true;
	       }else{
	            chkbox[i].checked = false;
	       }
    	  }
       }
  var infoAccept =document.getElementsByName("excepInfoAccept");
     if(accept != ""){
        for(var i=0; i<infoAccept.length;i++){
           if(accept == (infoAccept[i].value.split("@"))[0]){
               frm.elements.acceptInfo.value=(infoAccept[i].value.split("@"))[1];
           }
     	}
     }

}


function selectCheckReassign(frm,reassign){

var chkbox =document.getElementsByName("selectReassignmail");
singleSelectCb(frm,reassign,chkbox)

      for(var i=0; i<chkbox.length;i++){
  	  if(chkbox[i].checked) {
  	      if(reassign == (chkbox[i].value)){
		    chkbox[i].checked = true;
	       }else{
	            chkbox[i].checked = false;
	       }
    	  }
       }
  var infoReassignmail =document.getElementsByName("excepInfoReassignmail");
     if(reassign != ""){
        for(var i=0; i<infoReassignmail.length;i++){
            if(reassign == (infoReassignmail[i].value.split("@"))[0]){
               frm.elements.reassignmailInfo.value=(infoReassignmail[i].value.split("@"))[1];
           }
     	}
     }

}


function selectCheckReassignDesp(frm,reassigndespatch){

var chkbox =document.getElementsByName("selectReassigndespatch");
singleSelectCb(frm,reassigndespatch,chkbox)

      for(var i=0; i<chkbox.length;i++){
  	  if(chkbox[i].checked) {
  	      if(reassigndespatch == (chkbox[i].value)){
		    chkbox[i].checked = true;
	       }else{
	            chkbox[i].checked = false;
	       }
    	  }
       }
  var infoReassigndespatch =document.getElementsByName("excepInfoReassigndespatch");
     if(reassigndespatch != ""){
        for(var i=0; i<infoReassigndespatch.length;i++){
            if(reassigndespatch == (infoReassigndespatch[i].value.split("@"))[0]){
               frm.elements.reassigndespatchInfo.value=(infoReassigndespatch[i].value.split("@"))[1];
           }
     	}
     }

}


function selectCheckArrive(frm,arrive){

 var chkbox =document.getElementsByName("selectArrive");
 singleSelectCb(frm,arrive,chkbox)
      for(var i=0; i<chkbox.length;i++){
  	  if(chkbox[i].checked) {
  	      if(arrive == chkbox[i].value){
  	          chkbox[i].checked = true;
	      }else{
	          chkbox[i].checked = false;
	      }
    	  }
       }

  var infoArrive =document.getElementsByName("excepInfoArrive");
     if(arrive != ""){
        for(var i=0; i<infoArrive.length;i++){
        	 if(arrive == (infoArrive[i].value.split("@"))[0]){
                 frm.elements.arriveInfo.value=(infoArrive[i].value.split("@"))[1];
             }
     	}

     }
}


function selectCheckReturn(frm,returnmailbags){

 var chkbox =document.getElementsByName("selectReturn");
 singleSelectCb(frm,returnmailbags,chkbox)
      for(var i=0; i<chkbox.length;i++){
  	  if(chkbox[i].checked) {
  	      if(returnmailbags == chkbox[i].value){
  	          chkbox[i].checked = true;
	      }else{
	          chkbox[i].checked = false;
	      }
    	  }
       }

  var infoArrive =document.getElementsByName("excepInfoReturn");
     if(returnmailbags != ""){
        for(var i=0; i<infoArrive.length;i++){
             if(returnmailbags == (infoArrive[i].value.split("-"))[0]){
                 frm.elements.returnInfo.value=(infoArrive[i].value.split("-"))[1];
             }
     	}

     }
}


function selectCheckOffload(frm,offload){
  frm=targetFormName;
  var chkbox =document.getElementsByName("selectOffload");
  singleSelectCb(frm,offload,chkbox)
     for(var i=0; i<chkbox.length;i++){
	if(chkbox[i].checked) {
	      if(offload == chkbox[i].value){
		  chkbox[i].checked = true;
	      }else{
		  chkbox[i].checked = false;
	      }
	}
     }
  var infoOffload =document.getElementsByName("excepInfoOffload");
     if(offload != ""){
        for(var i=0; i<infoOffload.length;i++){
           if(offload == (infoOffload[i].value.split("@"))[0]){
               frm.elements.offloadInfo.value=(infoOffload[i].value.split("@"))[1];
           }
     	}
     }
}


function selectCheckTransfer(frm,transfer){
  frm=targetFormName;
  var chkbox =document.getElementsByName("selectTransfer");
  singleSelectCb(frm,transfer,chkbox)
     for(var i=0; i<chkbox.length;i++){
	if(chkbox[i].checked) {
	      if(transfer == chkbox[i].value){
		  chkbox[i].checked = true;
	      }else{
		  chkbox[i].checked = false;
	      }
	}
     }
  var infoTransfer =document.getElementsByName("excepInfoTransfer");
     if(transfer != ""){
        for(var i=0; i<infoTransfer.length;i++){
           if(transfer == (infoTransfer[i].value.split("@"))[0]){
               frm.elements.transferInfo.value=(infoTransfer[i].value.split("@"))[1];
           }
     	}
     }
}


function mailWeight(mode){
 frm=targetFormName;
 var weightArr =document.getElementsByName(mode);
 var weight =document.getElementsByName(mode);

   for(var i=0;i<weightArr.length;i++){
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

}

function mailDSN(mode){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName(mode);
 var mailDSN =document.getElementsByName(mode);
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


function mailRSN(mode){
 frm=targetFormName;
 var mailRSNArr =document.getElementsByName(mode);
 var mailRSN =document.getElementsByName(mode);
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }
   }
}


function validateMailDetails(){

  frm=targetFormName;

        var mailOOE =document.getElementsByName("mailOOE");
        var mailDOE =document.getElementsByName("mailDOE");
        var mailSC =document.getElementsByName("mailSC");
        var mailYr =document.getElementsByName("mailYr");
        var mailDSN =document.getElementsByName("mailDSN");
        var mailRSN =document.getElementsByName("mailRSN");
        var mailWt =document.getElementsByName("mailWt");
        var mailScanDate =document.getElementsByName("mailScanDate");
        var mailScanTime =document.getElementsByName("mailScanTime");

        for(var i=0;i<mailOOE.length;i++){
  	  if(mailOOE[i].value.length == 0){
  	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailooeempty" />',type:1,parentWindow:self});
  	     showPane(event,'pane2', frm.tab2);
  	     mailOOE[i].focus();
  	     return "N";
  	  }
  	  if(mailOOE[i].value.length != 6){
	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailooelength" />',type:1,parentWindow:self});
	     showPane(event,'pane2', frm.tab2);
	     mailOOE[i].focus();
	     return "N";
          }

          if(mailDOE[i].value.length == 0){
   	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildoeempty" />',type:1,parentWindow:self});
   	     showPane(event,'pane2', frm.tab2);
   	     mailOOE[i].focus();
   	     return "N";
   	  }
   	  if(mailDOE[i].value.length != 6){
	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildoelength" />',type:1,parentWindow:self});
	     showPane(event,'pane2', frm.tab2);
	     mailDOE[i].focus();
	     return "N";
          }

          if(mailSC[i].value.length == 0){
   	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailscempty" />',type:1,parentWindow:self});
   	     showPane(event,'pane2', frm.tab2);
   	     mailSC[i].focus();
   	     return "N";
   	  }
   	  if(mailSC[i].value.length != 2){
	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailsclength" />',type:1,parentWindow:self});
	     showPane(event,'pane2', frm.tab2);
	     mailSC[i].focus();
	     return "N";
          }


          if(mailYr[i].value.length == 0){
    	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailyearempty" />',type:1,parentWindow:self});
    	     showPane(event,'pane2', frm.tab2);
    	     mailYr[i].focus();
    	     return "N";
    	  }

    	  if(mailDSN[i].value.length == 0){
    	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildsnempty" />',type:1,parentWindow:self});
    	     showPane(event,'pane2', frm.tab2);
    	     mailDSN[i].focus();
    	     return "N";
    	  }

     	  if(mailRSN[i].value.length == 0){
     	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailrsnempty" />',type:1,parentWindow:self});
     	     showPane(event,'pane2', frm.tab2);
     	     mailRSN[i].focus();
     	     return "N";
     	  }


	  if(mailWt[i].value.length == 0){
	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailwgtempty" />',type:1,parentWindow:self});
	     showPane(event,'pane2', frm.tab2);
	     mailWt[i].focus();
	     return "N";
	  }
	  if(mailWt[i].value == 0){
	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailwgtzero" />',type:1,parentWindow:self});
	     showPane(event,'pane2', frm.tab2);
	     mailWt[i].focus();
	     return "N";
	  }

     	  if(mailScanDate[i].value.length == 0){
     	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.maildateempty" />',type:1,parentWindow:self});
     	     showPane(event,'pane2', frm.tab2);
     	     mailScanDate[i].focus();
     	     return "N";
       	  }

	  if(mailScanTime[i].value.length == 0){
	     showDialog({msg:'<bean:message bundle="mailAcceptanceResources" key="mailtracking.defaults.acceptmail.msg.alrt.mailtimeempty" />',type:1,parentWindow:self});
	     showPane(event,'pane2', frm.tab2);
	     mailScanTime[i].focus();
	     return "N";
	  }
       }



 }



  /**
   * To Color the Text in Table Rows
   */
  function colorTableRows(){
     frm=targetFormName;

    var colorText = "";
    var boldText = "bold";

    var acceptColor =document.getElementsByName("acceptColor");

    var acceptOoe =document.getElementsByName("acceptOoe");
    var acceptDoe =document.getElementsByName("acceptDoe");
    var acceptSc =document.getElementsByName("acceptSc");
    var acceptYr =document.getElementsByName("acceptYr");
    var acceptDsn =document.getElementsByName("acceptDsn");
    var acceptRsn =document.getElementsByName("acceptRsn");
    var acceptHni =document.getElementsByName("acceptHni");
    var acceptRi =document.getElementsByName("acceptRi");
    var acceptWt =document.getElementsByName("acceptWt");
    var acceptScanDate =document.getElementsByName("acceptScanDate");


    for(var i=0; i<acceptColor.length;i++){

       colorText = acceptColor[i].value;

       acceptOoe[i].style.color = colorText;
       acceptDoe[i].style.color = colorText;
       acceptSc[i].style.color = colorText;
       acceptYr[i].style.color = colorText;
       acceptDsn[i].style.color = colorText;
       acceptRsn[i].style.color = colorText;
       acceptHni[i].style.color = colorText;
       acceptRi[i].style.color = colorText;
       acceptWt[i].style.color = colorText;
       acceptScanDate[i].style.color = colorText;

       acceptOoe[i].style.fontWeight = boldText;
       acceptDoe[i].style.fontWeight = boldText;
       acceptSc[i].style.fontWeight = boldText;
       acceptYr[i].style.fontWeight = boldText;
       acceptDsn[i].style.fontWeight = boldText;
       acceptRsn[i].style.fontWeight = boldText;
       acceptHni[i].style.fontWeight = boldText;
       acceptRi[i].style.fontWeight = boldText;
       acceptWt[i].style.fontWeight = boldText;
       acceptScanDate[i].style.fontWeight = boldText;

    }

    var arriveColor =document.getElementsByName("arriveColor");

      var arriveOoe =document.getElementsByName("arriveOoe");
      var arriveDoe =document.getElementsByName("arriveDoe");
      var arriveSc =document.getElementsByName("arriveSc");
      var arriveYr =document.getElementsByName("arriveYr");
      var arriveDsn =document.getElementsByName("arriveDsn");
      var arriveRsn =document.getElementsByName("arriveRsn");
      var arriveHni =document.getElementsByName("arriveHni");
      var arriveRi =document.getElementsByName("arriveRi");
      var arriveWt =document.getElementsByName("arriveWt");
      var arriveScanDate =document.getElementsByName("arriveScanDate");

      for(var i=0; i<arriveColor.length;i++){

         colorText = arriveColor[i].value;

         arriveOoe[i].style.color = colorText;
         arriveDoe[i].style.color = colorText;
         arriveSc[i].style.color = colorText;
         arriveYr[i].style.color = colorText;
         arriveDsn[i].style.color = colorText;
         arriveRsn[i].style.color = colorText;
         arriveHni[i].style.color = colorText;
         arriveRi[i].style.color = colorText;
         arriveWt[i].style.color = colorText;
         arriveScanDate[i].style.color = colorText;

         arriveOoe[i].style.fontWeight = boldText;
         arriveDoe[i].style.fontWeight = boldText;
         arriveSc[i].style.fontWeight = boldText;
         arriveYr[i].style.fontWeight = boldText;
         arriveDsn[i].style.fontWeight = boldText;
         arriveRsn[i].style.fontWeight = boldText;
         arriveHni[i].style.fontWeight = boldText;
         arriveRi[i].style.fontWeight = boldText;
         arriveWt[i].style.fontWeight = boldText;
         arriveScanDate[i].style.fontWeight = boldText;

    }

    var returnColor =document.getElementsByName("returnColor");

      var returnOoe =document.getElementsByName("returnOoe");
      var returnDoe =document.getElementsByName("returnDoe");
      var returnSc =document.getElementsByName("returnSc");
      var returnYr =document.getElementsByName("returnYr");
      var returnDsn =document.getElementsByName("returnDsn");
      var returnRsn =document.getElementsByName("returnRsn");
      var returnCat =document.getElementsByName("returnCat");
      var returnHni =document.getElementsByName("returnHni");
      var returnRi =document.getElementsByName("returnRi");
      var returnWt =document.getElementsByName("returnWt");
      var returnScanDate =document.getElementsByName("returnScanDate");
      var paCode=document.getElementsByName("paCode");


      for(var i=0; i<returnColor.length;i++){

         colorText = returnColor[i].value;

         returnOoe[i].style.color = colorText;
         returnDoe[i].style.color = colorText;
         returnSc[i].style.color = colorText;
         returnYr[i].style.color = colorText;
         returnDsn[i].style.color = colorText;
         returnRsn[i].style.color = colorText;
         returnHni[i].style.color = colorText;
         returnRi[i].style.color = colorText;
         returnWt[i].style.color = colorText;
         returnScanDate[i].style.color = colorText;
         returnCat[i].style.color = colorText;
         paCode[i].style.color = colorText;
         returnOoe[i].style.fontWeight = boldText;
         returnDoe[i].style.fontWeight = boldText;
         returnSc[i].style.fontWeight = boldText;
         returnYr[i].style.fontWeight = boldText;
         returnDsn[i].style.fontWeight = boldText;
         returnRsn[i].style.fontWeight = boldText;
         returnHni[i].style.fontWeight = boldText;
         returnRi[i].style.fontWeight = boldText;
         returnWt[i].style.fontWeight = boldText;
         returnScanDate[i].style.fontWeight = boldText;
         returnCat[i].style.fontWeight = boldText;
         paCode[i].style.fontWeight = boldText;


    }


    var reassignmailColor =document.getElementsByName("reassignmailColor");

            var reassignmailOoe =document.getElementsByName("reassignmailOoe");
            var reassignmailDoe =document.getElementsByName("reassignmailDoe");
            var reassignmailCat =document.getElementsByName("reassignmailCat");
            var reassignmailSc =document.getElementsByName("reassignmailSc");
            var reassignmailYr =document.getElementsByName("reassignmailYr");
            var reassignmailDsn =document.getElementsByName("reassignmailDsn");
            var reassignmailRsn =document.getElementsByName("reassignmailRsn");
            var reassignmailHni =document.getElementsByName("reassignmailHni");
            var reassignmailRi =document.getElementsByName("reassignmailRi");
            var reassignmailWt =document.getElementsByName("reassignmailWt");
            var reassignmailScanDate =document.getElementsByName("reassignmailScanDate");
            var reassignmailFltNo =document.getElementsByName("reassignmailFltNo");
            var reassignmailFltDate =document.getElementsByName("reassignmailFltDate");
            var reassignmailContainerFltNo =document.getElementsByName("reassignmailContainerFltNo");
            var reassignmailContainerFltDate =document.getElementsByName("reassignmailContainerFltDate");
            var reassignmailContainerNo =document.getElementsByName("reassignmailContainerNo");
            var reassignmailPou =document.getElementsByName("reassignmailPou");


            for(var i=0; i<reassignmailColor.length;i++){

               colorText = reassignmailColor[i].value;

               reassignmailOoe[i].style.color = colorText;
               reassignmailDoe[i].style.color = colorText;
               reassignmailCat[i].style.color = colorText;
               reassignmailSc[i].style.color = colorText;
               reassignmailYr[i].style.color = colorText;
               reassignmailDsn[i].style.color = colorText;
               reassignmailRsn[i].style.color = colorText;
               reassignmailHni[i].style.color = colorText;
               reassignmailRi[i].style.color = colorText;
               reassignmailWt[i].style.color = colorText;
               reassignmailScanDate[i].style.color = colorText;
               reassignmailFltNo[i].style.color = colorText;
               reassignmailFltDate[i].style.color = colorText;
               reassignmailContainerFltNo[i].style.color = colorText;
               reassignmailContainerFltDate[i].style.color = colorText;
               reassignmailContainerNo[i].style.color = colorText;
               reassignmailPou[i].style.color = colorText;


               reassignmailOoe[i].style.fontWeight = boldText;
               reassignmailDoe[i].style.fontWeight = boldText;
               reassignmailCat[i].style.fontWeight = boldText;
               reassignmailSc[i].style.fontWeight = boldText;
               reassignmailYr[i].style.fontWeight = boldText;
               reassignmailDsn[i].style.fontWeight = boldText;
               reassignmailRsn[i].style.fontWeight = boldText;
               reassignmailHni[i].style.fontWeight = boldText;
               reassignmailRi[i].style.fontWeight = boldText;
               reassignmailWt[i].style.fontWeight = boldText;
               reassignmailScanDate[i].style.fontWeight = boldText;
               reassignmailFltNo[i].style.fontWeight = boldText;
 	      reassignmailFltDate[i].style.fontWeight = boldText;
 	      reassignmailContainerFltNo[i].style.fontWeight = boldText;
 	      reassignmailContainerFltDate[i].style.fontWeight = boldText;
 	      reassignmailContainerNo[i].style.fontWeight = boldText;
               reassignmailPou[i].style.fontWeight = boldText;


    }

    var transferColor =document.getElementsByName("transferColor");

         var transferOoe =document.getElementsByName("transferOoe");
         var transferDoe =document.getElementsByName("transferDoe");
         var transferCat =document.getElementsByName("transferCat");
         var transferSc =document.getElementsByName("transferSc");
         var transferYr =document.getElementsByName("transferYr");
         var transferDsn =document.getElementsByName("transferDsn");
         var transferRsn =document.getElementsByName("transferRsn");
         var transferHni =document.getElementsByName("transferHni");
         var transferRi =document.getElementsByName("transferRi");
         var transferWt =document.getElementsByName("transferWt");
         var transferScanDate =document.getElementsByName("transferScanDate");

         for(var i=0; i<transferColor.length;i++){

            colorText = transferColor[i].value;

            transferOoe[i].style.color = colorText;
            transferDoe[i].style.color = colorText;
            transferCat[i].style.color = colorText;
            transferSc[i].style.color = colorText;
            transferYr[i].style.color = colorText;
            transferDsn[i].style.color = colorText;
            transferRsn[i].style.color = colorText;
            transferHni[i].style.color = colorText;
            transferRi[i].style.color = colorText;
            transferWt[i].style.color = colorText;
            transferScanDate[i].style.color = colorText;

            transferOoe[i].style.fontWeight = boldText;
            transferDoe[i].style.fontWeight = boldText;
            transferCat[i].style.fontWeight = boldText;
            transferSc[i].style.fontWeight = boldText;
            transferYr[i].style.fontWeight = boldText;
            transferDsn[i].style.fontWeight = boldText;
            transferRsn[i].style.fontWeight = boldText;
            transferHni[i].style.fontWeight = boldText;
            transferRi[i].style.fontWeight = boldText;
            transferWt[i].style.fontWeight = boldText;
            transferScanDate[i].style.fontWeight = boldText;
    }

    var offloadColor =document.getElementsByName("offloadColor");

            var offloadOoe =document.getElementsByName("offloadOoe");
            var offloadDoe =document.getElementsByName("offloadDoe");
            var offloadCat =document.getElementsByName("offloadCat");
            var offloadSc =document.getElementsByName("offloadSc");
            var offloadYr =document.getElementsByName("offloadYr");
            var offloadDsn =document.getElementsByName("offloadDsn");
            var offloadRsn =document.getElementsByName("offloadRsn");
            var offloadHni =document.getElementsByName("offloadHni");
            var offloadRi =document.getElementsByName("offloadRi");
            var offloadWt =document.getElementsByName("offloadWt");
            var offloadScanDate =document.getElementsByName("offloadScanDate");
            var offloadMailBagFltNo =document.getElementsByName("offloadMailBagFltNo");
            var offloadMailBagFltDate =document.getElementsByName("offloadMailBagFltDate");
            var offloadReasonDescription = document.getElementsByName("offloadReasonDescription");
            var offloadRemark = document.getElementsByName("offloadRemark");


            for(var i=0; i<offloadColor.length;i++){
               colorText = offloadColor[i].value;

               offloadOoe[i].style.color = colorText;
               offloadDoe[i].style.color = colorText;
               offloadCat[i].style.color = colorText;
               offloadSc[i].style.color = colorText;
               offloadYr[i].style.color = colorText;
               offloadDsn[i].style.color = colorText;
               offloadRsn[i].style.color = colorText;
               offloadHni[i].style.color = colorText;
               offloadRi[i].style.color = colorText;
               offloadWt[i].style.color = colorText;
               offloadScanDate[i].style.color = colorText;
               offloadMailBagFltNo[i].style.color = colorText;
               offloadMailBagFltDate[i].style.color = colorText;
               offloadReasonDescription[i].style.color = colorText;
               offloadRemark[i].style.color = colorText;

               offloadOoe[i].style.fontWeight = boldText;
               offloadDoe[i].style.fontWeight = boldText;
               offloadCat[i].style.fontWeight = boldText;
               offloadSc[i].style.fontWeight = boldText;
               offloadYr[i].style.fontWeight = boldText;
               offloadDsn[i].style.fontWeight = boldText;
               offloadRsn[i].style.fontWeight = boldText;
               offloadHni[i].style.fontWeight = boldText;
               offloadRi[i].style.fontWeight = boldText;
               offloadWt[i].style.fontWeight = boldText;
               offloadScanDate[i].style.fontWeight = boldText;
               offloadMailBagFltNo[i].style.fontWeight = boldText;
               offloadMailBagFltDate[i].style.fontWeight = boldText;
               offloadReasonDescription[i].style.fontWeight = boldText;
               offloadRemark[i].style.fontWeight = boldText;

   }
}