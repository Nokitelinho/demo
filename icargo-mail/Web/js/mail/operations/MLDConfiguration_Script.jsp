<%@ page contentType="text/javascript"%>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister()
{ 
   var frm=targetFormName

   onScreenLoad();
   with(frm){

   	//CLICK Events
     	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
     	evtHandler.addEvents("btSave","saveDetails()",EVT_CLICK);
     	evtHandler.addEvents("btClose","closeScreen()",EVT_CLICK);
		evtHandler.addIDEvents("addLink","add()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deleteRow()",EVT_CLICK);
		
     

	  	}
}

function onScreenLoad(){
	
	var frm=targetFormName;
	if( 'Y' != frm.elements.listFlag.value){
	 disableLink(document.getElementById('addLink'));
	 disableLink(document.getElementById('deleteLink'));
	}
	if('Y'==frm.elements.listFlag.value){
		var mldversionLov = document.getElementById("mldversionLov");
		var mldver = frm.elements.mldversion;
		disableField(mldversionLov);
		disableField(mldver);

	}
	
	var mldVersion=frm.elements.mldversion.value;

	if(mldVersion!=null&&""!=mldVersion){
	disableCheckBox(frm,mldVersion);
	}

}

function closeScreen(){
location.href = appPath + "/home.jsp";
}

function disableCheckBox(frm,mldVersion){

	var allocateFlag=document.getElementsByName('allocatedRequired');
	var upliftFlag=document.getElementsByName('upliftedRequired');
	var deliveredFlag=document.getElementsByName('deliveredRequired');	
	var hndFlag=document.getElementsByName('hNDRequired');	
	var recvdFlag=document.getElementsByName('receivedRequired');	
	
	var stagedFlag=document.getElementsByName('stagedRequired');
	var nestedFlag=document.getElementsByName('nestedRequired');
	var receivedFromFightFlag=document.getElementsByName('receivedFromFightRequired');
	var transferredFromOALFlag=document.getElementsByName('transferredFromOALRequired');
	var receivedFromOALFlag=document.getElementsByName('receivedFromOALRequired');
	var returnedFlag=document.getElementsByName('returnedRequired');

	if('1'== mldVersion)
	{
		for(var i=0;i<stagedFlag.length;i++){		
		stagedFlag[i].disabled=true;
		}
		for(var i=0;i<nestedFlag.length;i++){		
		nestedFlag[i].disabled=true;
		}
		for(var i=0;i<receivedFromFightFlag.length;i++){		
		receivedFromFightFlag[i].disabled=true;
		}
		for(var i=0;i<transferredFromOALFlag.length;i++){		
		transferredFromOALFlag[i].disabled=true;
		}
		for(var i=0;i<receivedFromOALFlag.length;i++){		
		receivedFromOALFlag[i].disabled=true;
		}
		for(var i=0;i<returnedFlag.length;i++){		
		returnedFlag[i].disabled=true;
		}

	}
		if('2'== mldVersion)
	{
		for(var i=0;i<allocateFlag.length;i++){		
		allocateFlag[i].disabled=true;
		}
		for(var i=0;i<upliftFlag.length;i++){		
		upliftFlag[i].disabled=true;
		}
		for(var i=0;i<hndFlag.length;i++){		
		hndFlag[i].disabled=true;
		}
	}
}



function listDetails(frm){

	
	

	submitForm(frm,"mailtracking.defaults.mldconfiguartion.list.do");
}

function saveDetails(){
var frm=targetFormName
	updateCheckBoxFlag(frm);
	submitForm(frm,"mailtracking.defaults.mldconfiguartion.save.do");
	
}

function clearDetails(frm){

	submitForm(frm,"mailtracking.defaults.mldconfiguartion.clear.do");
}

function add(){


 addTemplateRow('mldConfigurationTemplateRow','mldConfigurationTableBody','operationFlag');
	
}


function deleteRow(){

frm=targetFormName;
 var chkbox =document.getElementsByName("rowId");
 var operationFlag =document.getElementsByName("operationFlag");
	if(validateSelectedCheckBoxes(frm,'rowId',chkbox.length,'1')){
         
               
                   
          deleteTableRow('rowId','operationFlag');
        
      }
}

   




			
			
			function invokeAirlineLOV(_index){
var index1 = _index.id.split("airlineLovRow")[1];
displayLOV('showAirline.do','N','Y','showAirline.do',document.forms[1].carrierCode.value,'Airline','1','carrierCode','',index1)
}

			function invokeAirportLOV(_index){
var index1 = _index.id.split("airportCodelovRow")[1];
displayLOV('showAirport.do','N','Y','showAirport.do',document.forms[1].airportCode.value,'Airport','1','airportCode','',index1)
}
function updateCheckBoxFlag(frm){


	var allocateFlag=document.getElementsByName('allocatedRequired');
	var upliftFlag=document.getElementsByName('upliftedRequired');
	var deliveredFlag=document.getElementsByName('deliveredRequired');	
	var hndFlag=document.getElementsByName('hNDRequired');	
	var recvdFlag=document.getElementsByName('receivedRequired');	
	var allocateCheck="";
	var upliftCheck="";
	var deliveredCheck="";
	var transCheck="";
	var recvdCheck="";
	
	//Added for CRQ ICRD-135130 by A-8061 starts
	
	var stagedFlag=document.getElementsByName('stagedRequired');
	var nestedFlag=document.getElementsByName('nestedRequired');
	var receivedFromFightFlag=document.getElementsByName('receivedFromFightRequired');
	var transferredFromOALFlag=document.getElementsByName('transferredFromOALRequired');
	var receivedFromOALFlag=document.getElementsByName('receivedFromOALRequired');
	var returnedFlag=document.getElementsByName('returnedRequired');
	var stagedCheck="";
	var nestedCheck="";
	var receivedFromFightCheck="";
	var transferredFromOALCheck="";
	var receivedFromOALCheck="";
	var returnedCheck="";

		for(var i=0;i<stagedFlag.length;i++){		
		if(stagedFlag[i].checked)			
			stagedCheck=stagedCheck+"Y-";		
		else			
			stagedCheck=stagedCheck+"N-";	
	}
		for(var i=0;i<nestedFlag.length;i++){		
		if(nestedFlag[i].checked)			
			nestedCheck=nestedCheck+"Y-";		
		else			
			nestedCheck=nestedCheck+"N-";	
	}
		for(var i=0;i<receivedFromFightFlag.length;i++){		
		if(receivedFromFightFlag[i].checked)			
			receivedFromFightCheck=receivedFromFightCheck+"Y-";		
		else			
			receivedFromFightCheck=receivedFromFightCheck+"N-";	
	}
		for(var i=0;i<transferredFromOALFlag.length;i++){		
		if(transferredFromOALFlag[i].checked)			
			transferredFromOALCheck=transferredFromOALCheck+"Y-";		
		else			
			transferredFromOALCheck=transferredFromOALCheck+"N-";	
	}
		for(var i=0;i<receivedFromOALFlag.length;i++){		
		if(receivedFromOALFlag[i].checked)			
			receivedFromOALCheck=receivedFromOALCheck+"Y-";		
		else			
			receivedFromOALCheck=receivedFromOALCheck+"N-";	
	}
		for(var i=0;i<returnedFlag.length;i++){		
		if(returnedFlag[i].checked)			
			returnedCheck=returnedCheck+"Y-";		
		else			
			returnedCheck=returnedCheck+"N-";	
	}
	
	
	//Added for CRQ ICRD-135130 by A-8061 end
	
	for(var i=0;i<allocateFlag.length;i++){		
		if(allocateFlag[i].checked)			
			allocateCheck=allocateCheck+"Y-";		
		else			
			allocateCheck=allocateCheck+"N-";	
	}
	for(var i=0;i<upliftFlag.length;i++){		
		if(upliftFlag[i].checked)			
			upliftCheck=upliftCheck+"Y-";		
		else			
			upliftCheck=upliftCheck+"N-";	
	}
	for(var i=0;i<deliveredFlag.length;i++){		
		if(deliveredFlag[i].checked)			
			deliveredCheck=deliveredCheck+"Y-";			
		else			
			deliveredCheck=deliveredCheck+"N-";	
	}
	for(var i=0;i<hndFlag.length;i++){		
		if(hndFlag[i].checked)			
			transCheck=transCheck+"Y-";		
		else			
			transCheck=transCheck+"N-";	
	}
	for(var i=0;i<recvdFlag.length;i++){		
		if(recvdFlag[i].checked)			
			recvdCheck=recvdCheck+"Y-";		
		else			
			recvdCheck=recvdCheck+"N-";	
	}
	
	
	if(allocateFlag.length>0){	
	var frm=document.forms[1];
	frm.elements.isAllocatedCheck.value=allocateCheck;
	}
	if(upliftFlag.length>0){
	frm.elements.isUpliftedCheck.value=upliftCheck;
	}
	if(deliveredFlag.length>0){
	frm.elements.isdlvCheck.value=deliveredCheck;
	}
	if(hndFlag.length>0){
	frm.elements.isHndcheck.value=transCheck;
	}
	if(recvdFlag.length>0){
	frm.elements.isReceivedCheck.value=recvdCheck;
	}
	
	//Added for CRQ ICRD-135130 by A-8061 starts

	
	if(stagedFlag.length>0){
	frm.elements.isStagedCheck.value=stagedCheck;
	}
	if(nestedFlag.length>0){
	frm.elements.isNestedCheck.value=nestedCheck;
	}
	if(receivedFromFightFlag.length>0){
	frm.elements.isReceivedFromFightCheck.value=receivedFromFightCheck;
	}
	if(transferredFromOALFlag.length>0){
	frm.elements.isTransferredFromOALcheck.value=transferredFromOALCheck;
	}
	if(receivedFromOALFlag.length>0){
	frm.elements.isReceivedFromOALcheck.value=receivedFromOALCheck;
	}
	if(returnedFlag.length>0){
	frm.elements.isReturnedCheck.value=returnedCheck;
	}
	
	//Added for CRQ ICRD-135130 by A-8061 end
}		