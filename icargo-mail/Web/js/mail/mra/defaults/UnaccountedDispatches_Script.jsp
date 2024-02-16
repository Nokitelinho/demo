<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister()
{
   var frm=targetFormName;

   //initial focus on page load
      if(frm.fromDate.disabled == false) {
         frm.fromDate.focus();
   }

   with(frm){

	evtHandler.addEvents("btList","listDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClear","clearDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btClose","closeDetails(this.form)",EVT_CLICK);
	evtHandler.addEvents("btPrint","createReport(this.form)",EVT_CLICK);
	evtHandler.addEvents("btDispatchEnq","dispatchEnquiryView(this.form)",EVT_CLICK);
	evtHandler.addEvents("btAccounting","accounting(this.form)",EVT_CLICK);
	evtHandler.addEvents("btManualAccounting","manualAccountingView(this.form)",EVT_CLICK);



	// LOVS
   	evtHandler.addIDEvents("originLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.origin.value,'Origin','1','origin','',0)", EVT_CLICK);
	evtHandler.addIDEvents("destinationLOV","displayLOV('showAirport.do','N','Y','showAirport.do',targetFormName.destination.value,'Destination','1','destination','',0)", EVT_CLICK);
	}
}


function closeDetails(frm){
     location.href = appPath + "/home.jsp";
}


function clearDetails(frm){
     submitForm(frm,'mailtracking.mra.defaults.unaccounteddispatches.clearsearch.do');
}

function listDetails(frm){
	 frm.displayPage.value = 1;
     submitForm(frm,'mailtracking.mra.defaults.unaccounteddispatches.listload.do');
}


function dispatchEnquiryView(frm){
   var chkbox = document.getElementsByName("selectCheckBox");
   var flownType = "F";//FLOWN_DETAILS
      	if(chkbox.length > 0){
      	  if(validateSelectedCheckBoxes(frm,'selectCheckBox','1','1')){
   			for(var i=0;i<chkbox.length;i++) {
   	          if(chkbox[i].checked){
   	          if(chkbox[i].value.split('#')[1]=="R1"){
   	          	if(chkbox[i].value.split('-')[4] != "null"){
   					submitForm(frm,'mailtracking.mra.defaults.unaccounteddispatches.viewDispatchesEnquiry.do?fromScreen=fromunnaccounteddispatches&selectedDispatch='+chkbox[i].value);
   				}else{
	     	  		showDialog("Despatch imported to RA with Exception.",1,self);   				
   				}
	     	  }else{
	     	  	showDialog("Despatch not imported to RA.",1,self);
	     	  }
	         }
	      }
	  }
    }
}



function createReport(frm){
	generateReport(frm,'/mailtracking.mra.defaults.unaccounteddispatches.validate.do');

}


function selectNextDetails(strLastPageNum,strDisplayPage){

	var frm = targetFormName;
 	frm.lastPageNum.value= strLastPageNum;
	frm.displayPage.value = strDisplayPage;
	submitForm(targetFormName,'mailtracking.mra.defaults.unaccounteddispatches.listload.do');
}

function accounting(frm){
		var selectedRows = document.getElementsByName('selectCheckBox');
		var count=[];
		var counter = 0;
			for(var i=0;i<selectedRows.length;i++) {
				if(selectedRows[i].checked)	{
					count[counter]=i;
					counter++;
				}
			}

		submitForm(frm,'mailtracking.mra.defaults.unaccounteddispatches.account.do?selectedRows='+count);
}


function manualAccountingView(frm){

var selectedRows = document.getElementsByName('selectCheckBox');

var counter = 0;
if(validateSelectedCheckBoxes(frm,'selectCheckBox','1','1')){
for(var i=0;i<selectedRows.length;i++) {
	if(selectedRows[i].checked)	{

	counter=i;
	}
}


  submitForm(frm,'mailtracking.mra.defaults.unaccounteddispatches.viewmanualaccounting.do?fromScreen=fromunnaccounteddispatches&selectedDispatch='+counter);

}

}
