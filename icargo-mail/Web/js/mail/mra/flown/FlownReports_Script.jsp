<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {

	var frm = targetFormName;
	//togglePanel(1,targetFormName.reportId);
	
	//initial focus on page load
	if(frm.elements.reportId.disabled == false){
	   frm.elements.reportId.focus();	
	}
	
	with(frm) {

		//evtHandler.addEvents("reportId","togglePanel(1,targetFormName.reportId)",EVT_CHANGE);
		
		evtHandler.addEvents("reportId","comboChanged()",EVT_CHANGE);
		evtHandler.addEvents("btnPrint","selectAction(targetFormName.reportId)",EVT_CLICK);
		evtHandler.addEvents("btnClose","wdClose()",EVT_CLICK);
		evtHandler.addEvents("btnClose","resetFocus()",EVT_BLUR);//added by T-1927 for ICRD-18408
		evtHandler.addIDEvents("accMonLov","showAccountCalenderLOV()",EVT_CLICK);

	}
}


    function wdClose() {
    		location.href = appPath + "/home.jsp";
	}
	//added by T-1927 for ICRD-18408
	function resetFocus(){
	if(!event.shiftKey){
		if(targetFormName.elements.reportId.disabled == false){
			targetFormName.elements.reportId.focus();
		}
	}
	}

	function togglePanel(iState,comboObj) // 1 visible, 0 hidden
	{

		if(comboObj != null) {

	   	var divID = comboObj.value;
	   	
	   	

		var divValues = ['ListOfFlightsWithFlightStatus','ListOfFlownMails','MailRevenueReport'];
		var divCount = divValues.length;

		for(var index=0; index<divCount; index++) {

			if(divValues[index] == divID) {
			

				var divObj = document.layers ? document.layers[divID] :
							 document.getElementById ?  document.getElementById(divID).style :
							 document.all[divID].style;

				divObj.visibility = document.layers ? (1 ? "show" : "hide") :
								 (1 ? "visible" : "hidden");

			}
			else {

				var divAlt = document.layers ? document.layers[divValues[index]] :
							 document.getElementById ?  document.getElementById(divValues[index]).style :
							 document.all[divValues[index]].style;

				divAlt.visibility = document.layers ? (0 ? "show" : "hide") :
					 (0 ? "visible" : "hidden");

				}

		}
		}

	}
	
	function showAccountCalenderLOV(){
	   var multiselect="N";
		var pagination="Y"; //Added by A-7794 for ICRD-266613
				var code= targetFormName.elements.accountMonth.value;
				var title="Billing Period LOV";
				var formCount= "1";
				var lovTxtFieldName = "accountMonth";
				var lovNameTxtFieldName="accountMonth";
				var index=0;
			
			openPopUp("cra.accounting.showAccountCalendarLOVDetails.do?multiselect="+multiselect+"&pagination="+pagination+"&accountingMonth="+code+"&lovNameTxtFieldName="+lovNameTxtFieldName+"&title="+title+"&formCount="+formCount+"&lovTxtFieldName="+lovTxtFieldName+"",480,380);
	}


	function selectAction(selectedAction){

		var frm = targetFormName;

		divname=selectedAction.value;
		
		

		if(divname=="ListOfFlightsWithFlightStatus"){
		
				
		
			
			generateReport(targetFormName,"/mra.flown.flownreports.listofflightswithflightstatus.do");


		}
		 if(divname=="MailRevenueReport"){
		
		  if(frm.elements.accountMonth.value == null||frm.elements.accountMonth.value==''){
		  
		  
			//showDialog('<common:message bundle="flownreportsresources" key="mra.flown.report.msg.err.accmon.mandatory" scope="request"/>', 1, self);
			showDialog({msg:'<common:message bundle="flownreportsresources" key="mra.flown.report.msg.err.accmon.mandatory" scope="request"/>',type:1,parentWindow:self});
			return;
		  
		}else{
		      submitForm(targetFormName,'mailtracking.mra.flown.printMailRevenueReport.do');
		  }
		
		
		}

		if(divname=="ListOfFlownMails"){

			if(	frm.elements.flightOrigin.value != "" && frm.elements.flightDestination.value != "" &&
				frm.elements.flightOrigin.value == frm.elements.flightDestination.value ){

				//showDialog('<common:message bundle="flownreportsresources" key="mra.flown.report.msg.err.fltorgdst" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="flownreportsresources" key="mra.flown.report.msg.err.fltorgdst" scope="request"/>',type:1,parentWindow:self});
				return;

			}else if( 	frm.elements.mailOrigin.value != "" && frm.elements.mailDestination.value != "" &&
						frm.elements.mailOrigin.value == frm.elements.mailDestination.value){

				//showDialog('<common:message bundle="flownreportsresources" key="mra.flown.report.msg.err.mailorgdst" scope="request"/>', 1, self);
				showDialog({msg:'<common:message bundle="flownreportsresources" key="mra.flown.report.msg.err.mailorgdst" scope="request"/>',type:1,parentWindow:self});
				return;

			}else{

				generateReport(targetFormName,"/mra.flown.flownreports.listofflownmails.do");

			}
		}

	}
	
	
	function comboChanged(){
	
	
	 
	 if(targetFormName.elements.reportId.value=="ListOfFlightsWithFlightStatus"){
	 
	 	targetFormName.elements.comboFlag.value="ListOfFlights";
	 
	 }else if(targetFormName.elements.reportId.value=="MailRevenueReport"){
	 		
	 	targetFormName.elements.comboFlag.value="MailRevenue";
	 
	 }else if(targetFormName.elements.reportId.value=="ListOfFlownMails"){
	 		
	 	targetFormName.elements.comboFlag.value="ListOfFlown";
	 
	 }
	 
	 
	 	
	 submitForm(targetFormName,'mra.flown.flownreports.screenloadflownreports.do');
	 	
 }