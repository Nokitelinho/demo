<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;
   onScreenLoad();
   with(frm){

   	//CLICK Events
   	evtHandler.addEvents("btSave","saveDetails(this.form)",EVT_CLICK);
   	evtHandler.addEvents("btClose","closePopup(this.form)",EVT_CLICK);
	evtHandler.addEvents("addLink","addDamage()",EVT_CLICK);
	evtHandler.addEvents("deleteLink","deleteDamage()",EVT_CLICK);

	evtHandler.addEvents("returnCheckAll","updateHeaderCheckBox(this.form,targetFormName.returnCheckAll,targetFormName.returnSubCheck)",EVT_CLICK);
	if(frm.returnSubCheck != null){
		evtHandler.addEvents("returnSubCheck","toggleTableHeaderCheckbox('returnSubCheck',targetFormName.returnCheckAll)",EVT_CLICK);
	}

   	}
}

function onScreenLoad(){
	frm=targetFormName;
	var statusFlag = targetFormName.elements.actionStatusFlag.value;
	
	if(statusFlag == "CLOSE"){

		window.opener.targetFormName.displayPage.value=1;
		window.opener.targetFormName.lastPageNum.value=0;
		window.opener.targetFormName.status.value="";
		window.opener.targetFormName.action = "mailtracking.defaults.dsnenquiry.listdetails.do";
		window.opener.targetFormName.submit();
		//window.opener.recreateTableDetails("mailtracking.defaults.dsnenquiry.list.do","div1","chkListFlow");
		window.closeNow();
	}
	else if(statusFlag == "SHOWPOPUP"){

  		var frmscreen = "EMPTY_ULD";
		if(frm.elements.fromScreen.value == "DSN_ENQUIRY"){
				        frm.elements.fromScreen.value = "";
				        frm = self.opener.targetFormName;
				    	frm.action="mailtracking.defaults.dsnenquiry.listdetails.do?status="+frmscreen;
				    	frm.method="post";
				    	frm.submit();
				   
				    	window.closeNow();
				    	return;
    		}
  		//var strUrl = "mailtracking.defaults.emptyulds.screenload.do?fromScreen=RETURNDSN";
  		//submitForm(frm,strUrl);

	}
}

function addDamage(){
var incomplete = validateBags(targetFormName);
      if(incomplete == "N"){
        return;
      }
      incomplete = validateWeight(targetFormName);
      if(incomplete == "N"){
           return;
   	}
	incomplete = validateReturnedBags(targetFormName);
	      if(incomplete == "N"){
	           return;
   	}
	incomplete = validateReturnedWgt(targetFormName);
	      if(incomplete == "N"){
	           return;
  	 }
	
	
	
	//submitForm(targetFormName,'mailtracking.defaults.returndsn.add.do');
	submitForm(targetFormName,'mailtracking.defaults.returndsn.update.do?actionStatusFlag=ADD');
}

function deleteDamage(){

	
	//submitForm(targetFormName,'mailtracking.defaults.returndsn.delete.do');
	var check = validateSelectedCheckBoxes(frm, 'returnSubCheck', 1000000000000, 1);
	if (check){
	submitForm(targetFormName,'mailtracking.defaults.returndsn.update.do?actionStatusFlag=DELETE');
}
}

function saveDetails(frm){

	var incomplete = validateBags(targetFormName);
	      if(incomplete == "N"){
	        return;
	      }
	      incomplete = validateWeight(targetFormName);
	      if(incomplete == "N"){
	           return;
	   	}
		incomplete = validateReturnedBags(targetFormName);
		      if(incomplete == "N"){
		           return;
	   	}
		incomplete = validateReturnedWgt(targetFormName);
		      if(incomplete == "N"){
		           return;
	  	 }
	
		
	if(frm.postalAdmin.length == 0 ){
		showDialog('<bean:message bundle="returnDsnResources" key="mailtracking.defaults.returndsn.msg.warn.noPOA" />',1,self);
	}else{			
		//submitForm(targetFormName,'mailtracking.defaults.returndsn.save.do');
		submitForm(targetFormName,'mailtracking.defaults.returndsn.update.do?actionStatusFlag=SAVE');
		window.close();
	}	
	
}

function closePopup(frm){

	//submitForm(targetFormName,'mailtracking.defaults.returndsn.close.do');
	window.close();
}

function submitDamagePage(lastPg,displayPg){
var incomplete = validateBags(targetFormName);
      if(incomplete == "N"){
        return;
      }
      incomplete = validateWeight(targetFormName);
      if(incomplete == "N"){
           return;
   	}
	incomplete = validateReturnedBags(targetFormName);
	      if(incomplete == "N"){
	           return;
   	}
	incomplete = validateReturnedWgt(targetFormName);
	      if(incomplete == "N"){
	           return;
  	 }
	
	
	targetFormName.elements.lastPage.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	//submitForm(targetFormName,'mailtracking.defaults.returndsn.link.do');
	//targetFormName.action="mailtracking.defaults.returndsn.link.do";
	//targetFormName.submit();
	submitForm(targetFormName,'mailtracking.defaults.returndsn.update.do?actionStatusFlag=LINK');
}

function doCheck(fld){

	/*if (fld.value != fld.defaultValue) {
		//alert('changed');
		if(parseInt(fld.value) < parseInt(fld.defaultValue)){
			fld.value = fld.defaultValue;
			alert('Value less than the current value not allowed');
			fld.focus();
		}

	}*/

}

	function validateBags(frm){
	
	var dmgBags = document.getElementsByName("damagedBags");
	var dmgWt = document.getElementsByName("damagedWeight");
	
		for(var i=0;i<dmgBags.length;i++){
			if(parseInt(dmgBags[i].value) == 0 && Number(dmgWt[i].value) >0){
			showDialog({msg:'<bean:message bundle="returnDsnResources" key="mailtracking.defaults.returnDsn.msg.alrt.nobagsbutwt" />',type:1,parentWindow:self});
			 dmgBags[i].focus();
	           	return "N";
			
			}else if (parseInt(dmgBags[i].value) == 0 && Number(dmgWt[i].value) == 0){
				showDialog({msg:'Damaged bags cannot be zero',type:1,parentWindow:self});
				dmgBags[i].focus();
	           	return "N";
			}
			
			if(parseInt(dmgBags[i].value) > parseInt(frm.dmgNOB.value)){
	
				 showDialog({msg:'<bean:message bundle="returnDsnResources" key="mailtracking.defaults.returnDsn.msg.alrt.dmgNOB" />',type:1,parentWindow:self});
				 dmgBags[i].focus();
	           		return "N";
			}
		}
	
	}
	
	function validateWeight(frm){
	
	var dmgBags = document.getElementsByName("damagedBags");
	var dmgWt = document.getElementsByName("damagedWeight");
		for(var i=0;i<dmgWt.length;i++){
		
		if(Number(dmgWt[i].value) == 0 && parseInt(dmgBags[i].value) >0){
					showDialog({msg:'<bean:message bundle="returnDsnResources" key="mailtracking.defaults.returnDsn.msg.alrt.nowtbutbags" />',type:1,parentWindow:self});
					 dmgWt[i].focus();
			           	return "N";
					
		}
			
			if(Number(dmgWt[i].value) > Number(frm.dmgWeight.value)){
	
				 showDialog({msg:'<bean:message bundle="returnDsnResources" key="mailtracking.defaults.returnDsn.msg.alrt.dmgWT" />',type:1,parentWindow:self});
				 dmgWt[i].focus();
	           		return "N";
			}
		}
		}
		
	function validateReturnedBags(frm){
	var n =0;
	var dmgRtBags = document.getElementsByName("returnedBags");
	var dmgRtWgt = document.getElementsByName("returnedWeight");
	for(var i=0;i<dmgRtBags.length;i++){
	
	if(parseInt(dmgRtBags[i].value) == 0 && Number(dmgRtWgt[i].value) >0){
				showDialog({msg:'<bean:message bundle="returnDsnResources" key="mailtracking.defaults.returnDsn.msg.alrt.nortbagsbutwt" />',type:1,parentWindow:self});
				 dmgRtBags[i].focus();
		           	return "N";
				
	}
	
	n=parseInt(n) + parseInt(dmgRtBags[i].value);
	}
	if(parseInt(n)>parseInt(frm.dmgNOB.value)){
				 showDialog({msg:'<bean:message bundle="returnDsnResources" key="mailtracking.defaults.returnDsn.msg.alrt.rtDmgNOB" />',type:1,parentWindow:self});
	           		return "N";
	}
	}
	
	function validateReturnedWgt(frm){
	var n =0;
	var dmgRtWgt = document.getElementsByName("returnedWeight");
	var dmgRtBags = document.getElementsByName("returnedBags");	
	for(var i=0;i<dmgRtWgt.length;i++){
	
	if(Number(dmgRtWgt[i].value) == 0 && parseInt(dmgRtBags[i].value) >0){
						showDialog({msg:'<bean:message bundle="returnDsnResources" key="mailtracking.defaults.returnDsn.msg.alrt.nortwtbutbags" />',type:1,parentWindow:self});
						 dmgRtWgt[i].focus();
				           	return "N";
						
		}
	
	n=Number(n)+Number(dmgRtWgt[i].value);
	}
	if(Number(n)>Number(frm.dmgWeight.value)){
				 showDialog({msg:'<bean:message bundle="returnDsnResources" key="mailtracking.defaults.returnDsn.msg.alrt.rtDmgWT" />',type:1,parentWindow:self});
	           		return "N";
	}
	}
	
	
	
	