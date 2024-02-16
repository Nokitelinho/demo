<%@ include file="/jsp/includes/js_contenttype.jsp" %>


function screenSpecificEventRegister(){


	with(targetFormName){
		screenload();
		evtHandler.addEvents("btList","doList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","doClear()",EVT_CLICK);
		evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("btnCCADetails","doDetails()",EVT_CLICK);
		evtHandler.addEvents("btnPrint","doPrint()",EVT_CLICK);
		evtHandler.addEvents("mailOOELov","invokeLov(this,'mailOOELov')",EVT_CLICK);
	evtHandler.addEvents("mailDOELov","invokeLov(this,'mailDOELov')",EVT_CLICK);
	evtHandler.addIDEvents("subClassFilterLOV","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.subClass.value,'Subclass','1','subClass','',0)", EVT_CLICK);
		//evtHandler.addEvents("airlineCodeLov","displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.airlineCode.value,'Airline Code','1','airlineCode','',0)",EVT_CLICK);
		//evtHandler.addEvents("gpaCodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.gpaCode.value,'GPA Code','1','gpaCode','',0)",EVT_CLICK);
		//evtHandler.addIDEvents("gpaCodelov","displayLOVCodeNameAndDesc('mailtracking.defaults.palov.list.do','N','Y',
		//'mailtracking.defaults.palov.list.do',targetFormName.gpaCode.value,'gpaCode','1','gpaCode','gpaName','',0)",EVT_CLICK);
		
		evtHandler.addIDEvents("gpaCodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.gpaCode.value,'gpaCode','1','gpaCode','gpaName',0)",EVT_CLICK);
	
	
	
		
		
		
		
		
		
		evtHandler.addEvents("issueParty","comboChanged()",EVT_CHANGE);
		evtHandler.addIDEvents("stationlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.origin.value,'Origin','1','origin','','0')",EVT_CLICK);
		evtHandler.addIDEvents("stationCodelov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.destination.value,'Destination','1','destination','','0')",EVT_CLICK);
		evtHandler.addIDEvents("gpaCode","populateName()",EVT_BLUR);
		evtHandler.addIDEvents("mcanolov","mcaLov()",EVT_CLICK);
		evtHandler.addEvents("dsnlov","displayLOV('mailtracking.mra.defaults.dsnlov.screenload.do','N','Y','mailtracking.mra.defaults.dsnlov.screenload.do',targetFormName.dsn.value,'DSN No','1','dsn','','',0)",EVT_CLICK);
        evtHandler.addEvents("btnDelete","deleteAction()",EVT_CLICK);
		evtHandler.addEvents("btnReject","rejectAction()",EVT_CLICK);
		evtHandler.addEvents("btnAccept","acceptAction()",EVT_CLICK);
	//evtHandler.addEvents("mcanolov","displayLOV('mailtracking.mra.defaults.mcalov.screenload.do','N','Y','mailtracking.mra.defaults.mcalov.screenload.do',targetFormName.ccaNum.value,'MCA No','1','ccaNum','dsn',0)",EVT_CLICK);
	}
 applySortOnTable("listCCATable",new Array("None","String","Date","String","String","String","String","String","String","Number","String","String")); 

}
function invokeLov(obj,name){   
var index = obj.id.split(name)[1];

 if(name == "mailOOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.originOfficeOfExchange.value,'OfficeOfExchange','0','originOfficeOfExchange','',index);
   }
   if(name == "mailDOELov"){
         displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.destinationOfficeOfExchange.value,'OfficeOfExchange','0','destinationOfficeOfExchange','',index);
   }
}
function mcaLov(){
var height = document.body.clientHeight;
				var _reqHeight = (height*49)/100;
displayLOVCodeNameAndDesc('mailtracking.mra.defaults.mcalov.screenload.do','N','Y','mailtracking.mra.defaults.mcalov.screenload.do',targetFormName.ccaNum.value,'MCA No','1','ccaNum','dsn','',0,_reqHeight);
}
/**
*Added for screen resolution
*/
function screenload(){
	// initial focus on page load
	if(targetFormName.elements.ccaNum.disabled == false) {
	     targetFormName.elements.ccaNum.focus();
        }
}
/**
*Function for list
*/
function doList(){
	if(targetFormName.elements.toDate.value=="" ){
		showDialog({msg:'ToDate is Mandatory',type:1, parentWindow:self});
		return;
	}

	targetFormName.elements.lastPageNum.value=0;
	targetFormName.elements.displayPage.value=1;
	submitForm(targetFormName,"mailtracking.mra.defaults.listcca.list.do?countTotalFlag=YES");//Added by A-5201 as part for the ICRD-21098
}
/**
*Function for Clear
*/
function doClear(){
	submitForm(targetFormName,'mailtracking.mra.defaults.listcca.clear.do');


}
//-----------------Ajax call for GPA Name -----------------------------------//
function populateName(){
	
	var funct_to_overwrite = "overWriteOldDiv"; 	
	var strAction = 'mailtracking.mra.defaults.listcca.populategpaname.do'
		
  	asyncSubmit(targetFormName,strAction,funct_to_overwrite,null,null);
}

 function overWriteOldDiv(_tableInfo){ 
     
  	var  oldDivId = "gpanameDiv";
  	
    _innerFrm=_tableInfo.document.getElementsByTagName("form")[0];   
    
     	
    var newDivId =_tableInfo.document.getElementById("_gpanameDiv");
   
 	document.getElementById(oldDivId).innerHTML =  newDivId.innerHTML;
 	 	 
  }
//------------------------------------------------------------------------//  


/**
*Function for Print
*/
function doPrint(){
	generateReport(targetFormName,'/mailtracking.mra.defaults.listcca.print.do');

}


/**
*Function for Close
*/
function doClose(){
	location.href = appPath+"/home.jsp";

}
/**
*Function for viewing the Details
*/
function doDetails(){
	var flag="listCCA";
	var selectedUlds = "";
		var selectedRows = document.getElementsByName('selectedRows');
		var count=0;
		if(validateSelectedCheckBoxes(targetFormName,'selectedRows','1','1')){
			for(var i=0;i<selectedRows.length;i++) {
				if(selectedRows[i].checked)	{
					count=i;
					
					
				}
			}
			var ccaStatus=document.getElementsByName('ccaStatus');
			//alert("CCASTATUS"+ccaStatus);
			var fromScreen='listCCA';
			var privilegeFlag=targetFormName.elements.comboFlag.value;
			//alert(privilegeFlag);
			submitForm(targetFormName,'mailtracking.mra.defaults.listcca.listccadetail.do?count='+count+'&fromScreen='+fromScreen+'&privilegeFlag='+privilegeFlag+'&ccaStatus='+ccaStatus);


		}
}

function submitList(strLastPageNum,strDisplayPage){
	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	submitForm(targetFormName, 'mailtracking.mra.defaults.listcca.list.do');
}


function comboChanged(){

	if(targetFormName.issueParty.value=="GPA"){
		targetFormName.elements.airlineCode.disabled=false;
		targetFormName.elements.comboFlag.value="GPA";

	}else if(targetFormName.issueParty.value=="ARL"){
		targetFormName.elements.airlineCode.disabled=false;
		targetFormName.elements.comboFlag.value="Airline";

	}
	else{

		targetFormName.elements.airlineCode.disabled=true;
	}
}

function onclickToLOV(){

if(targetFormName.elements.comboFlag.value==""){

	showDialog({msg:'Please select an issuing party',type:1,parentWindow:self});

	return;
}

if(targetFormName.elements.comboFlag.value=="GPA"){

displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',"",'GPACode','1','airlineCode','',0);

		}

		else {

		displayLOV('showAirline.do','N','Y','showAirline.do',targetFormName.elements.airlineCode.value,'Airline Code','1','airlineCode','',0);
		}


	}
//Added by A-7359 for ICRD - 224586 starts here
function callbackListCCA (collapse,collapseFilterOrginalHeight,mainContainerHeight){       
               if(!collapse){
                              collapseFilterOrginalHeight=collapseFilterOrginalHeight*(-1);
               }
               IC.util.widget.updateTableContainerHeight("#div1",+collapseFilterOrginalHeight);
}
//Added by A-7359 for ICRD - 224586 ends here



//Added by A-7540
function deleteAction(){
if(validateSelectedCheckBoxes(targetFormName,'selectedRows','',1)){
	submitForm(targetFormName,'mailtracking.mra.defaults.listcca.delete.do');
	}

  }
function rejectAction(){
if(validateSelectedCheckBoxes(targetFormName,'selectedRows','',1)){
	   submitForm(targetFormName,'mailtracking.mra.defaults.listcca.reject.do');
     }
   }
 

function acceptAction(){
var ccaStatus=document.getElementsByName('ccaStatus');

if(validateSelectedCheckBoxes(targetFormName,'selectedRows','',1)){
	submitForm(targetFormName,'mailtracking.mra.defaults.listcca.accept.do');
     }
  }