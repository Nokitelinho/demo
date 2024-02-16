<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	onScreenLoad(frm);
	with(frm){
		evtHandler.addIDEvents("airport_btn","invokeLOV('ARP')",EVT_CLICK);
		evtHandler.addIDEvents("airportOrigin_btn","invokeLOV('ORG_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("airportDest_btn","invokeLOV('DEST_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("hoairportcode_btn","invokeLOV('HO_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("coAirport_btn","invokeLOV('CO_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("pacode_btn","invokeLOV('GPA')",EVT_CLICK);
		evtHandler.addIDEvents("hoExchangeOffice_btn","invokeLOV('HO_EXGOFC')",EVT_CLICK);  //8331
		evtHandler.addIDEvents("hoMailSubClass_btn","invokeLOV('HO_SUBCLS')",EVT_CLICK);   //8331
		evtHandler.addIDEvents("coPacode_btn","invokeLOV('CO_GPA')",EVT_CLICK);
		evtHandler.addIDEvents("rdtPacode_btn","invokeLOV('RDT_GPA')",EVT_CLICK);
		evtHandler.addIDEvents("conPacode_btn","invokeLOV('CON_GPA')",EVT_CLICK);
		evtHandler.addIDEvents("incPaCode_btn","invokeLOV('INC_GPA')",EVT_CLICK);
		evtHandler.addIDEvents("originAirport_btn","invokeLOV('CON_ORG_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("rdtAirport_btn","invokeLOV('RDT_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("originAirports_btn","invokeLOV('CON_ORG_ARPS')",EVT_CLICK);
		evtHandler.addIDEvents("destinationAirport_btn","invokeLOV('CON_DEST_ARP')",EVT_CLICK);
		evtHandler.addIDEvents("destinationAirports_btn","invokeLOV('CON_DEST_ARPS')",EVT_CLICK);
		evtHandler.addIDEvents("calPacode_btn","invokeLOV('CAL_GPA')",EVT_CLICK);
		evtHandler.addIDEvents("PAcode_btn","invokeLOV('SERSTD_GPA')",EVT_CLICK);
		evtHandler.addIDEvents("hopacode_btn","invokeLOV('HO_GPA')",EVT_CLICK);
		evtHandler.addIDEvents("btnCotAdd","addCotDetails()",EVT_CLICK);
		evtHandler.addIDEvents("btnSerStdAdd","addServiceStdDetails()",EVT_CLICK);
		evtHandler.addIDEvents("btnCalAdd","addCalDetails()",EVT_CLICK);
		 evtHandler.addIDEvents("btnHotAdd","addHotDetails()",EVT_CLICK);
		evtHandler.addIDEvents("btnContractAdd","addCidDetails()",EVT_CLICK);
		evtHandler.addIDEvents("btnIncentiveAdd","addIncentiveDetails()",EVT_CLICK);
		evtHandler.addEvents("disIncSrvPercentage","restrictPercentage(this,3,2)",EVT_BLUR);//Added for ICRD-307530
		evtHandler.addEvents("disIncNonSrvPercentage","restrictPercentage(this,3,2)",EVT_BLUR);//Added for ICRD-307530
		evtHandler.addEvents("disIncBothSrvPercentage","restrictPercentage(this,3,2)",EVT_BLUR);//Added for ICRD-307530
		evtHandler.addEvents("incPercentage","restrictPercentage(this,3,2)",EVT_BLUR);//Added for ICRD-307530
		evtHandler.addIDEvents("btnList","listDetails(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("btnClose","closeScreen()",EVT_CLICK);
		evtHandler.addIDEvents("btnClear","clearDetails(this.form)",EVT_CLICK);
		evtHandler.addIDEvents("btnSave","saveDetails(this.form)",EVT_CLICK);
		evtHandler.addEvents("btnDelete","deleteDetails()",EVT_CLICK);
		evtHandler.addEvents("airportCodes","operationFlagChangeOnEdit(this,'operationFlag');",EVT_BLUR);
		evtHandler.addEvents("periods","operationFlagChangeOnEdit(this,'calOperationFlags');",EVT_BLUR);
		evtHandler.addEvents("airLOV","airLOVfunc(this)",EVT_CLICK);
	//	evtHandler.addIDEvents("exgofcLOV","airLOVfunc(this)",EVT_CLICK);
		//evtHandler.addIDEvents("exgofcLOV","exgLOVfunc(this)",EVT_CLICK);   //8331
		evtHandler.addEvents("rdtairLOV","rdtairLOVfunc(this)",EVT_CLICK);                     // Added by A-8331 as part of ICRD-301783
		evtHandler.addEvents("coAirLOV","coAirLOVfunc(this)",EVT_CLICK);
		evtHandler.addEvents("originLOV","originLOVfunc(this)",EVT_CLICK);
		evtHandler.addEvents("serviceOriginLOVTemp","serviceOriginLOVTempfunc(this)",EVT_CLICK);
		evtHandler.addEvents("serviceDestLOVTemp","serviceDestLOVTempfunc(this)",EVT_CLICK);
		evtHandler.addEvents("destLOV","destLOVfunc(this)",EVT_CLICK);
		evtHandler.addEvents("originLOVTemp","originLOVTempfunc(this)",EVT_CLICK);
		evtHandler.addEvents("destLOVTemp","destLOVTempfunc(this)",EVT_CLICK);
		evtHandler.addEvents("conOrgairLOV","conOrgairLOVfunc(this)",EVT_CLICK);
		evtHandler.addEvents("conDestairLOV","conDestairLOVfunc(this)",EVT_CLICK);
        evtHandler.addEvents("hoAirportLOV","hoAirportLOVfunc(this)",EVT_CLICK);
		 evtHandler.addEvents("exchangeOfficeLOV","exchangeOfficeLOVfunc(this)",EVT_CLICK);
		  evtHandler.addEvents("subClassLOV","subClassLOVfunc(this)",EVT_CLICK);
		evtHandler.addEvents("parameterLOV","parameterLOVfunc(this)",EVT_CLICK);
		evtHandler.addEvents("formulaLOV","formulaLOVfunc(this)",EVT_CLICK);
		evtHandler.addEvents("basisLOV","basisLOVfunc(this)",EVT_CLICK);
		evtHandler.addIDEvents("btnRdtAdd","addRdtDetails()",EVT_CLICK);
		evtHandler.addIDEvents("btnPostalClose","closefn()",EVT_CLICK);

		 //Added as part of ICRD-304479
if(frm.serviceStdrowId != null){
		evtHandler.addEvents("serviceStdrowId","toggleTableHeaderCheckbox('serviceStdrowId',targetFormName.checkAll)",EVT_CLICK);
	}
	if(frm.conRowId != null){
		evtHandler.addEvents("conRowId","toggleTableHeaderCheckbox('conRowId',targetFormName.contractIDcheckAll)",EVT_CLICK);
	}
    if(frm.elements.incRowId != null){
		evtHandler.addEvents("incRowId","toggleTableHeaderCheckbox('incRowId',targetFormName.elements.checkAllIncentive)",EVT_CLICK);
	}
	if(frm.elements.disIncSrvRowId != null){

		evtHandler.addEvents("disIncSrvRowId","toggleTableHeaderCheckbox('disIncSrvRowId',this.form.checkAllDisincentive)",EVT_CLICK);
	}
	if(frm.elements.disIncNonSrvDetailRowId != null){
		evtHandler.addEvents("disIncNonSrvDetailRowId","toggleTableHeaderCheckbox('disIncNonSrvDetailRowId',this.form.checkAllNonSrvDisincentive)",EVT_CLICK);
	}
	if(frm.elements.disIncBothSrvRowId != null){
		evtHandler.addEvents("disIncBothSrvRowId","toggleTableHeaderCheckbox('disIncBothSrvRowId',this.form.checkAllBothDisincentive)",EVT_CLICK);

	}
	}
}
var fl=true;
function clearDetails(frm){
	var clearCheck=document.querySelector('input[name="mainRadio"]:checked').value;
	if(clearCheck==='coTerminus')
		submitForm(frm,"mail.operations.ux.mailperformance.coterminus.clear.do");
	else if(clearCheck==='postalCalendar')
		submitForm(frm,"mail.operations.ux.mailperformance.postalcalendar.clear.do");
	else if(clearCheck==='handoverTime'){
		submitForm(frm,"mail.operations.ux.mailperformance.handovertime.clear.do");
	} else if(clearCheck==='contractID'){
		submitForm(frm,"mail.operations.ux.mailperformance.contractid.clear.do");
	} else if(clearCheck==='serviceStandards')
		submitForm(frm,"mail.operations.ux.mailperformance.servicestandards.clear.do");
	else if(clearCheck==='incentives')
		submitForm(frm,"mail.operations.ux.mailperformance.incentiveconfiguration.clear.do");
		else if(clearCheck==='RDTOffset')
		submitForm(frm,"mail.operations.ux.mailperformance.rdtoffset.clear.do");
}
function updateOperationFlag(frm){
	var operationFlagCheck=document.querySelector('input[name="mainRadio"]:checked').value;
	if(operationFlagCheck==='coTerminus'){
	var operationFlag = eval(frm.elements.operationFlag);
 	var rowId = eval(frm.rowId);
	var description = eval(frm.elements.pacode);
	if (rowId != null) {
	 		if (rowId.length > 1) {
	 			for (var i = 0; i < rowId.length; i++) {
	 				var checkBoxValue = rowId[i].value;
	 				if((operationFlag[checkBoxValue].value !='D')&&
	 										(operationFlag[checkBoxValue].value !='U')) {
	 					if (description[checkBoxValue].value != description[checkBoxValue].defaultValue) {
                                if(operationFlag[checkBoxValue].value !='I'){
	 								frm.elements.operationFlag[checkBoxValue].value='U';
								}
	 					}
	 				}
				}
	 		}else {
	 			if(operationFlag.value !='D'){
	 				if (description.value !=description.defaultValue) {
							if(operationFlag.value !='I'){
	 							frm.elements.operationFlag.value = 'U';
							}
	 				}
	 			}
	 		}
	 	}
}
		else if(operationFlagCheck==='postalCalendar'){
			var operationFlag = eval(frm.elements.calOperationFlags);
			var rowId = eval(frm.calRowIds);
			var description = eval(frm.elements.pacode);
			if (rowId != null) {
					if (rowId.length > 1) {
						for (var i = 0; i < rowId.length; i++) {
							var checkBoxValue = rowId[i].value;
							if((operationFlag[checkBoxValue].value !='D')&&
													(operationFlag[checkBoxValue].value !='U')) {
								if (description[checkBoxValue].value != description[checkBoxValue].defaultValue) {
										if(operationFlag[checkBoxValue].value !='I'){
											frm.elements.calOperationFlags[checkBoxValue].value='U';
										}
								}
							}
						}
					}else {
						if(operationFlag.value !='D'){
							if (description.value !=description.defaultValue) {
									if(operationFlag.value !='I'){
										frm.elements.calOperationFlags.value = 'U';
									}
							}
						}
					}
				}
		}
		else if(operationFlagCheck==='handoverTime'){
			var operationFlag = eval(frm.elements.hoOperationFlags);
			var rowId = eval(frm.hoRowId);
			var description = eval(frm.elements.hoPaCode);
			if (rowId != null) {
					if (rowId.length > 1) {
						for (var i = 0; i < rowId.length; i++) {
							var checkBoxValue = rowId[i].value;
							if((operationFlag[checkBoxValue].value !='D')&&
													(operationFlag[checkBoxValue].value !='U')) {
								if (description[checkBoxValue].value != description[checkBoxValue].defaultValue) {
										if(operationFlag[checkBoxValue].value !='I'){
											frm.elements.hoOperationFlags[checkBoxValue].value='U';
										}
								}
							}
						}
					}else {
						if(operationFlag.value !='D'){
							if (description.value !=description.defaultValue) {
									if(operationFlag.value !='I'){
										frm.elements.hoOperationFlags.value = 'U';
									}
							}
						}
					}
				}
		}else if(operationFlagCheck==='contractID'){
			var operationFlag = eval(frm.elements.conOperationFlags);
			var rowId = eval(frm.conRowId);
			var description = eval(frm.elements.conPaCode);
			if (rowId != null) {
					if (rowId.length > 1) {
						for (var i = 0; i < rowId.length; i++) {
							var checkBoxValue = rowId[i].value;
							if((operationFlag[checkBoxValue].value !='D')&&
													(operationFlag[checkBoxValue].value !='U')) {
								if (description[checkBoxValue].value != description[checkBoxValue].defaultValue) {
										if(operationFlag[checkBoxValue].value !='I'){
											frm.elements.conOperationFlags[checkBoxValue].value='U';
										}
								}
							}
						}
					}else {
						if(operationFlag.value !='D'){
							if (description.value !=description.defaultValue) {
									if(operationFlag.value !='I'){
										frm.elements.conOperationFlags.value = 'U';
									}
							}
						}
					}
				}
	}else if(operationFlagCheck==='incentives'){
		var incFlagCheck=document.querySelector('input[name="incFlag"]:checked').value;
		var srvCheck = document.querySelector('input[name="serviceResponsiveFlag"]:checked').value;
		if(incFlagCheck ==='incentive'){
			var operationFlag = eval(frm.elements.incOperationFlags);
			var rowId = eval(frm.incRowId);
			var description = eval(frm.elements.incPaCode);
			if (rowId != null) {
				if (rowId.length > 1) {
					for (var i = 0; i < rowId.length; i++) {
						var checkBoxValue = rowId[i].value;
						if((operationFlag[checkBoxValue].value !='D')&&
												(operationFlag[checkBoxValue].value !='U')) {
							if (description[checkBoxValue].value != description[checkBoxValue].defaultValue) {
									if(operationFlag[checkBoxValue].value !='I'){
										frm.elements.incOperationFlags[checkBoxValue].value='U';
									}
							}
						}
					}
				}else {
					if(operationFlag.value !='D'){
						if (description.value !=description.defaultValue) {
								if(operationFlag.value !='I'){
									frm.elements.incOperationFlags.value = 'U';
								}
						}
					}
				}
			}
		}
		else{

			if(srvCheck=='Y'){
				var operationFlag = eval(frm.elements.disIncSrvOperationFlags);
				var rowId = eval(frm.disIncSrvRowId);
			var description = eval(frm.elements.incPaCode);
			if (rowId != null) {
				if (rowId.length > 1) {
					for (var i = 0; i < rowId.length; i++) {
						var checkBoxValue = rowId[i].value;
						if((operationFlag[checkBoxValue].value !='D')&&
												(operationFlag[checkBoxValue].value !='U')) {
							if (description[checkBoxValue].value != description[checkBoxValue].defaultValue) {
									if(operationFlag[checkBoxValue].value !='I'){
											frm.elements.disIncSrvOperationFlags[checkBoxValue].value='U';
									}
							}
						}
					}
				}else {
					if(operationFlag.value !='D'){
						if (description.value !=description.defaultValue) {
								if(operationFlag.value !='I'){
										frm.elements.disIncSrvOperationFlags.value = 'U';
									}
							}
						}
					}
				}
			}else if(srvCheck=='N'){

				var operationFlag = eval(frm.elements.disIncNonSrvOperationFlags);
				var rowId = eval(frm.disIncNonSrvRowId);
				var description = eval(frm.elements.incPaCode);
				if (rowId != null) {
					if (rowId.length > 1) {
						for (var i = 0; i < rowId.length; i++) {
							var checkBoxValue = rowId[i].value;
							if((operationFlag[checkBoxValue].value !='D')&&
													(operationFlag[checkBoxValue].value !='U')) {
								if (description[checkBoxValue].value != description[checkBoxValue].defaultValue) {
										if(operationFlag[checkBoxValue].value !='I'){
											frm.elements.disIncNonSrvOperationFlags[checkBoxValue].value='U';
										}
								}
							}
						}
					}else {
						if(operationFlag.value !='D'){
							if (description.value !=description.defaultValue) {
									if(operationFlag.value !='I'){
										frm.elements.disIncNonSrvOperationFlags.value = 'U';
									}
							}
						}
					}
				}
			}else if(srvCheck=='B'){
				var operationFlag = eval(frm.elements.disIncBothSrvOperationFlags);
				var rowId = eval(frm.disIncBothSrvRowId);
				var description = eval(frm.elements.incPaCode);
				if (rowId.length > 1) {
					for (var i = 0; i < rowId.length; i++) {
						var checkBoxValue = rowId[i].value;
						if((operationFlag[checkBoxValue].value !='D')&&
												(operationFlag[checkBoxValue].value !='U')) {
							if (description[checkBoxValue].value != description[checkBoxValue].defaultValue) {
									if(operationFlag[checkBoxValue].value !='I'){
										frm.elements.disIncBothSrvOperationFlags[checkBoxValue].value='U';
									}
							}
						}
					}
				}else {
					if(operationFlag.value !='D'){
						if (description.value !=description.defaultValue) {
								if(operationFlag.value !='I'){
									frm.elements.disIncBothSrvOperationFlags.value = 'U';
								}
						}
					}
				}
			}
		}
	}
}
function closeScreen(){
location.href = appPath + "/home.jsp";
}
function saveDetails(frm){
	var saveCheck=document.querySelector('input[name="mainRadio"]:checked').value;
	if(saveCheck==='coTerminus'){
	var chkbox =document.getElementsByName("truckFlag");
	if(chkbox.length > 0){
		for(var i=0; i<chkbox.length;i++){
			if(chkbox[i].checked ){
			chkbox[i].value = i;
			}
		}
	}
	submitForm(frm,"mail.operations.ux.mailperformance.coterminus.save.do");
	}
	else if(saveCheck==='postalCalendar'){
		submitForm(frm,"mail.operations.ux.mailperformance.postalcalendar.save.do");
	}else if(saveCheck==='handoverTime'){
		submitForm(frm,"mail.operations.ux.mailperformance.handovertime.save.do");
	}

	else if(saveCheck==='contractID'){
		var fromdates =document.getElementsByName("cidFromDates");
		var todates =document.getElementsByName("cidToDates");
		var origin =document.getElementsByName("originAirports");
		var chkbox =document.getElementsByName("amot");
	    if(chkbox.length > 0){
		    for(var i=0; i<chkbox.length;i++){
			  if(chkbox[i].checked ){
			  chkbox[i].value = i;
			  }
		    }
	    }
		var dest =document.getElementsByName("destinationAirports");
		for(var i=0; i<fromdates.length;i++){

			if((origin[i].value.length>0 && dest[i].value.length>0) && (origin[i].value == dest[i].value)){
				showDialog({
					msg :'Origin and Destination cannot be the same',
					type:1,
					parentWindow:self
				});
			   return;
			}
		}
		submitForm(frm,"mail.operations.ux.mailperformance.contractid.save.do");

	}else if(saveCheck==='serviceStandards'){
	var chkbox =document.getElementsByName("scanWaived");
	if(chkbox.length > 0){
		for(var i=0; i<chkbox.length;i++){
			if(chkbox[i].checked ){
			chkbox[i].value = i;
			}
		}
	}
		submitForm(frm,"mail.operations.ux.mailperformance.servicestandards.save.do");
	}












	else if(saveCheck==='incentives')
	{

	   var fromdates =document.getElementsByName("disIncSrvValidFrom");
		var todates =document.getElementsByName("disIncSrvValidTo");
		var incType = document.querySelector('input[name="incFlag"]:checked').value;

	   if(document.querySelector('input[name="serviceResponsiveFlag"]:checked') != null){
		var srvCheck = document.querySelector('input[name="serviceResponsiveFlag"]:checked').value;
		}

		frm = targetFormName;
		 if(incType==='disincentive'){
			if(srvCheck=='Y'){

				for(var i=0; i<fromdates.length;i++){
			if(dates.compare(fromdates[i].value,todates[i].value)>0)
			{
				showDialog({
					msg :'From date cannot be greater than To Date.',
					type:1,
					parentWindow:self
				});
			   return;
				}
				}

			}else if(srvCheck=='N'){

				for(var i=0; i<fromdates.length;i++){
			if(dates.compare(fromdates[i].value,todates[i].value)>0)
			{
				showDialog({
					msg :'From date cannot be greater than To Date.',
					type:1,
					parentWindow:self
				});
			   return;
			}
			}

			}else {


					for(var i=0; i<fromdates.length;i++){
			if(dates.compare(fromdates[i].value,todates[i].value)>0)
			{
				showDialog({
					msg :'From date cannot be greater than To Date.',
					type:1,
					parentWindow:self
				});
			   return;
			}
			}

		 }
		 }
		submitForm(frm,"mail.operations.ux.mailperformance.incentiveconfiguration.save.do");
	}

















	else if(saveCheck==='RDTOffset'){
		submitForm(frm,"mail.operations.ux.mailperformance.rdtoffset.save.do");
	}
}
function deleteDetails(){
	var deleteCheck=document.querySelector('input[name="mainRadio"]:checked').value;
	if(deleteCheck==='coTerminus'){
		frm = targetFormName;
    	    if(validateSelectedCheckBoxes(frm,'coRowId',1000000000,1))
	          deleteTableRow('coRowId','coOperationFlag');
	}
	else if(deleteCheck==='postalCalendar'){
		frm = targetFormName;
    	    if(validateSelectedCheckBoxes(frm,'calRowIds',1000000000,1))
				deleteTableRow('calRowIds','calOperationFlags');
	}
	else if(deleteCheck==='handoverTime'){
		frm = targetFormName;
    	    if(validateSelectedCheckBoxes(frm,'hoRowId',1000000000,1))
	          deleteTableRow('hoRowId','hoOperationFlags');
	}
	else if(deleteCheck==='serviceStandards'){
		frm = targetFormName;
    	    if(validateSelectedCheckBoxes(frm,'serviceStdrowId',1000000000,1))
				deleteTableRow('serviceStdrowId','serviceStdoperationFlag');
	}  else if(deleteCheck==='contractID'){
		frm = targetFormName;
    	    if(validateSelectedCheckBoxes(frm,'conRowId',1000000000,1))
	          deleteTableRow('conRowId','conOperationFlags');
	} else if(deleteCheck==='incentives'){
		var incType = document.querySelector('input[name="incFlag"]:checked').value;
		if(document.querySelector('input[name="serviceResponsiveFlag"]:checked') != null){
		var srvCheck = document.querySelector('input[name="serviceResponsiveFlag"]:checked').value;
		}
		frm = targetFormName;
		if(incType==='incentive'){
    	    if(validateSelectedCheckBoxes(frm,'incRowId',1000000000,1))
	          deleteTableRow('incRowId','incOperationFlags');
		}else if(incType==='disincentive'){
			if(srvCheck=='Y'){
				if(validateSelectedCheckBoxes(frm,'disIncSrvRowId',1000000000,1))
				  deleteTableRow('disIncSrvRowId','disIncSrvOperationFlags');
			}else if(srvCheck=='N'){
				if(validateSelectedCheckBoxes(frm,'disIncNonSrvRowId',1000000000,1))
				  deleteTableRow('disIncNonSrvRowId','disIncNonSrvOperationFlags');
			}else {
				if(validateSelectedCheckBoxes(frm,'disIncBothSrvRowId',1000000000,1))
				  deleteTableRow('disIncBothSrvRowId','disIncBothSrvOperationFlags');
			}
	}
	//added by A-8527 for ICRD-298762
	}
	else if(deleteCheck==='RDTOffset'){
		frm = targetFormName;
    	    if(validateSelectedCheckBoxes(frm,'rdtRowId',1000000000,1))
	          deleteTableRow('rdtRowId','rdtOperationFlag');
	}
}
function deleteRow(obj){
	var index = obj.id.split('delete')[1];
	if(index.split('0')[0]==''){
		index = index.split('0')[1];
	}
	deleteTableRow1('rowId','operationFlag',index);
}
//Added by A-8331
function deleteRow(obj){
	var index = obj.id.split('delete')[1];
	if(index.split('0')[0]==''){
		index = index.split('0')[1];
	}
	deleteTableRow1('coRowId','coOperationFlag',index);
}
function deleteRow1(obj){
	WebuiPopovers.hideAll();
	var index = obj.id.split('delete')[1];
	if(index.split('0')[0]==''){
		index = index.split('0')[1];
	}
	deleteTableRow1('calRowIds','calOperationFlags',index);
}
function editPostalCalendar(obj){
WebuiPopovers.hideAll();
var index = obj.id.split('edit')[1];
	if(index.split('0')[0]==''){
		index = index.split('0')[1];
	}
		var popupTitle = "Modify Postal Calendar";
		var urlString = "mail.operations.ux.mailperformance.postalcalendar.editcalendar.do?selectedRow="+index;
		var closeButtonIds = "btnOk";
		var optionsArray = {
			actionUrl : urlString,
			dialogWidth:"700",
			dialogHeight:"300",
			closeButtonIds : closeButtonIds,
			popupTitle: popupTitle
	   }
		popupUtils.openPopUp(optionsArray);
	}
function deleteRow2(obj){
	var index = obj.id.split('delete')[1];
	if(index.split('0')[0]==''){
		index = index.split('0')[1];
	}
	deleteTableRow1('hoRowId','hoOperationFlags',index);
}
function deleteContactRow(obj){
	var index = obj.id.split('delete')[1];
	if(index.split('0')[0]==''){
		index = index.split('0')[1];
	}
	deleteTableRow1('conRowId','conOperationFlags',index);
}
function deleteIncentiveRow(obj){
	var index = obj.id.split('delete')[1];
	if(index.split('0')[0]==''){
		index = index.split('0')[1];
	}
	deleteTableRow1('incRowId','incOperationFlags',index);
}
function deleteDisIncentiveRow(obj){
	var index = obj.id.split('delete')[1];
	if(index.split('0')[0]==''){
		index = index.split('0')[1];
	}
	deleteTableRow1('disIncRowId','disIncOperationFlags',index);
}
function addCotDetails(){
	addTemplateRow('coTerminusTemplateRow','coTerminusTableBody','coOperationFlag');

}
function addServiceStdDetails(){
	addTemplateRow('serviceStandardTemplateRow','serviceStandardTableBody','serviceStdoperationFlag');
}
function addCalDetails(){
	addTemplateRow('cgrTemplateRow','cgrTableBody','calOperationFlags');
}
function addHotDetails(){
	addTemplateRow('handoverTimeTemplateRow','handoverTimeTableBody','hoOperationFlags');
}
function addCidDetails(){
	addTemplateRow('contractIdTemplateRow','contractIdTableBody','conOperationFlags');
}
function addRdtDetails(){
	addTemplateRow('rdtOffsetTemplateRow','rdtOffsetTableBody','rdtOperationFlag');

}

function addIncentiveDetails(){
	/* var incCheck = document.querySelector('input[name="incFlag"]:checked').value;
	if(incCheck=='incentive'){
		addTemplateRow('incentiveTemplateRow','incentiveTableBody','incOperationFlags');
	}else if(incCheck=='disincentive'){
		addTemplateRow('disIncentiveTemplateRow','disIncentiveTableBody','disIncOperationFlags');
	} */
	var incCheck = document.querySelector('input[name="incFlag"]:checked').value;
	if(document.querySelector('input[name="serviceResponsiveFlag"]:checked') != null){
	var srvCheck = document.querySelector('input[name="serviceResponsiveFlag"]:checked').value;
	}
	if(incCheck=='incentive'){
		addTemplateRow('incentiveTemplateRow','incentiveTableBody','incOperationFlags');
	}else if(incCheck=='disincentive'){
		if(srvCheck=='Y'){//add row in Service-Responsive table
			addTemplateRow('disIncentiveSrvTemplateRow','disIncentiveSrvTableBody','disIncSrvOperationFlags');
		}else if(srvCheck=='N'){//add row in NonService-Responsive table
			addTemplateRow('disIncentiveNonSrvTemplateRow','disIncentiveNonSrvTableBody','disIncNonSrvOperationFlags');
		}else{//add row in Both Service-Responsive table
			addTemplateRow('disIncentiveTemplateRow','disIncentiveTableBody','disIncBothSrvOperationFlags');
		}
	}
}
function onScreenLoad(frm){

	/* IC.util.widget.createDataTable("rdtOffestTable",{tableId:"rdtOffestTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"48vh",
                                });
	document.getElementById('rdtOffestTable_wrapper').style.width = '100%';   */  // Modified by A-8331 as part of ICRD-301783
	var checkVal=frm.elements.screenFlag.value;
	var checkFlag=frm.elements.statusFlag.value;
	if(checkVal=='ctRadioBtn'){
		if(checkFlag=='List_fail'||checkFlag=='Save_success'){
			/*IC.util.element.disable(document.getElementById('btnSave'));
			IC.util.element.disable(document.getElementById('btnCotAdd'));
			IC.util.element.disable(document.getElementById('btnCotDelete'));*/
			document.getElementById("btnSave").disabled=true;
			document.getElementById("btnCotAdd").disabled=true;
			document.getElementById("btnCotDelete").disabled=true;
		}
		else{
			/*IC.util.element.enable(document.getElementById('btnSave'));
			IC.util.element.enable(document.getElementById('btnCotAdd'));
			IC.util.element.enable(document.getElementById('btnCotDelete'));*/
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnCotAdd").disabled=false;
			document.getElementById("btnCotDelete").disabled=false;
		}
		document.getElementById(checkVal).checked="true";
		togglepanel('pane4','pane2','pane3','pane1','pane5','pane6','pane7','paneb4','paneb2','paneb3','paneb1','paneb5','paneb6','paneb7');
	}
	else if(checkVal=='rdtRadioBtn'){

		if(checkFlag=='List_fail'||checkFlag=='Save_success'){
			document.getElementById("btnSave").disabled=true;
			document.getElementById("btnRdtAdd").disabled=true;
			document.getElementById("btnRdtDelete").disabled=true;
		}
		else{
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnRdtAdd").disabled=false;
			document.getElementById("btnRdtDelete").disabled=false;
		}
		document.getElementById(checkVal).checked="true";
		 togglepanel('pane3','pane2','pane4','pane1','pane5','pane6','pane7','paneb3','paneb2','paneb4','paneb1','paneb5','paneb6','paneb7');
	}

	else if(checkVal=='postCalender'){
		if(checkFlag=='List_fail'||checkFlag=='Save_success'){
			/*IC.util.element.disable(document.getElementById('btnSave'));
			IC.util.element.disable(document.getElementById('btnCalAdd'));
			IC.util.element.disable(document.getElementById('btnCalDelete'));*/
			document.getElementById("btnSave").disabled=true;
			document.getElementById("btnCalAdd").disabled=true;
			document.getElementById("btnCalDelete").disabled=true;
		}
		else{
			/*IC.util.element.enable(document.getElementById('btnSave'));
			IC.util.element.enable(document.getElementById('btnCalAdd'));
			IC.util.element.enable(document.getElementById('btnCalDelete'));*/
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnCalAdd").disabled=false;
			document.getElementById("btnCalDelete").disabled=false;
			if(frm.elements.postalCalendarAction.value=='EDIT'){
			_divIdSeg = "invoicPostalCalendar";
			currDivId = document.getElementById("postalCal").id;
			var strAction = "mail.operations.ux.mailperformance.postalcalendar.list.do?postalCalendarAction=RELOAD";
			asyncSubmit(targetFormName,strAction,_divIdSeg,null,null,currDivId);
			}
		}
		document.getElementById(checkVal).checked="true";
		togglepanel('pane5','pane2','pane3','pane1','pane4','pane6','pane7','paneb5','paneb2','paneb3','paneb1','paneb4','paneb6','paneb7');
	}
	else if(checkVal=='hoRadiobtn'){
		if(checkFlag=='List_failure'||checkFlag=='Save_success' ||checkFlag=='Clear_screen'){     //Modified by A-8399 as part of ICRD-293432
			document.getElementById("btnSave").disabled=true;
			document.getElementById("btnHotAdd").disabled=true;
			document.getElementById("btnHotDelete").disabled=true;
		}else{
            document.getElementById("btnSave").disabled=false;
            document.getElementById("btnHotAdd").disabled=false;
			document.getElementById("btnHotDelete").disabled=false;
         }
         document.getElementById(checkVal).checked="true";
         togglepanel('pane2','pane5','pane3','pane1','pane4','pane6','pane7','paneb2','paneb5','paneb3','paneb1','paneb4','paneb6','paneb7');
		 setTimeout(function(){
		 IC.util.widget.createDataTable("handoverTimeTable",{tableId:"handoverTimeTable",hasChild:false,scrollingY:'43vh'});
	document.getElementById('handoverTimeTable_wrapper').style.width = '100%';
	IC.util.widget.recalculateTableContainerHeightForUx(jquery("handoverTimeTable"),{hideEmptyBody:true})}, 100);
    }
	else if(checkVal=='serviceStandards'){
		if(checkFlag=='List_fail'||checkFlag=='Save_success'||checkFlag=='Clear_screen'){
			document.getElementById("btnSave").disabled=true;
			document.getElementById("btnSerStdAdd").disabled=true;
			document.getElementById("btnDelete").disabled=true;
		}
		else if(checkFlag=='List_fail_NoRecords'){
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnSerStdAdd").disabled=false;
			document.getElementById("btnDelete").disabled=false;
		}
	else{
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnSerStdAdd").disabled=false;
			document.getElementById("btnDelete").disabled=false;
			if(checkFlag=='List_success'){
				document.getElementById("scanningWaived").disabled=true;
				var scanWaivedFlag=document.getElementsByName("scanWaived");
				if(scanWaivedFlag.length > 0){
					for(var i=0; i<scanWaivedFlag.length;i++){
						if(scanWaivedFlag[i].value=='Y'){
							scanWaivedFlag[i].checked=true;
						}
					}
				}
			}
		}
		document.getElementById(checkVal).checked="true";
		togglepanel('pane1','pane2','pane3','pane4','pane5','pane6','pane7','paneb1','paneb2','paneb3','paneb4','paneb5','paneb6','paneb7');
	}  else if(checkVal=='cidRadiobtn'){
		if(checkFlag=='List_fail'||checkFlag=='Save_success'){
			document.getElementById("btnSave").disabled=true;
			document.getElementById("btnContractAdd").disabled=true;
			document.getElementById("btnContractDelete").disabled=true;
		}else{
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnContractAdd").disabled=false;
			document.getElementById("btnContractDelete").disabled=false;
		}
		document.getElementById(checkVal).checked="true";
		togglepanel('pane6','pane5','pane3','pane1','pane4','pane2','pane7','paneb6','paneb5','paneb3','paneb1','paneb4','paneb2','paneb7');
	} else if(checkVal=='incentiveRadioBtn'){
		//var incVal = document.querySelector('input[name="incRadio"]:checked').value;
		if(checkFlag=='List_fail'||checkFlag=='Save_success'){
			document.getElementById("btnSave").disabled=true;
			document.getElementById("btnIncentiveAdd").disabled=true;
			document.getElementById("btnIncentiveDelete").disabled=true;
			toggleResponsivePanel('disincentive');
			toggleServiceResponsivePanel('ServiceResponsive');

		}else if(checkFlag=='List_fail_NoRecords'){
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnIncentiveAdd").disabled=false;
		    document.getElementById("btnIncentiveDelete").disabled=false;

			document.getElementById("disIncentiveRadioBtn").checked="true";
			document.getElementById("srvRespRadioBtn").checked="true";
			toggleResponsivePanel('disincentive');
			toggleServiceResponsivePanel('ServiceResponsive');
			/* if(frm.elements.disIncFlag.value=='N'){
				if(frm.elements.servRespFlag.value == 'Y'){
				}else if(frm.elements.servRespFlag.value == 'N'){
					document.getElementById("nonSrvRespRadioBtn").checked="true";
					toggleServiceResponsivePanel('NonServiceResponsive');
				}else if(frm.elements.servRespFlag.value == 'B'){
					document.getElementById("bothRadioBtn").checked="true";
					toggleServiceResponsivePanel('BothServiceResponsive');
				}
			}else{
				document.getElementById("incentRadioBtn").checked="true";
			} */
		}else if(checkFlag=='Save_fail'){
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnIncentiveAdd").disabled=false;
			document.getElementById("btnIncentiveDelete").disabled=false;

			if(frm.elements.disIncFlag.value=='N'){
				toggleResponsivePanel('disincentive');
			document.getElementById("disIncentiveRadioBtn").checked="true";
				if(frm.elements.servRespFlag.value == 'Y'){
			document.getElementById("srvRespRadioBtn").checked="true";
					toggleServiceResponsivePanel('ServiceResponsive');
				}else if(frm.elements.servRespFlag.value == 'N'){
					document.getElementById("nonSrvRespRadioBtn").checked="true";
					toggleServiceResponsivePanel('NonServiceResponsive');
				}else if(frm.elements.servRespFlag.value == 'B'){
					document.getElementById("bothRadioBtn").checked="true";
					toggleServiceResponsivePanel('BothServiceResponsive');
				}
			}else{
				document.getElementById("incentRadioBtn").checked="true";
				toggleResponsivePanel('incentive');
			}
		}else{
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnIncentiveAdd").disabled=false;
			document.getElementById("btnIncentiveDelete").disabled=false;
		document.getElementById("disIncentiveRadioBtn").checked="true";
		document.getElementById("srvRespRadioBtn").checked="true";
			toggleResponsivePanel('disincentive');
			toggleServiceResponsivePanel('ServiceResponsive');
		}
		document.getElementById(checkVal).checked="true";
		togglepanel('pane7','pane6','pane5','pane3','pane1','pane4','pane2','paneb7','paneb6','paneb5','paneb3','paneb1','paneb4','paneb2');

	}
	else{
		togglepanel('pane1','pane2','pane3','pane4','pane5','pane6','pane7','paneb1','paneb2','paneb3','paneb4','paneb5','paneb6','paneb7');
		/*IC.util.element.disable(document.getElementById('btnSave'));
		IC.util.element.disable(document.getElementById('btnCotAdd'));*/
		document.getElementById("btnSave").disabled=true;
		document.getElementById("btnCotAdd").disabled=true;
		document.getElementById("btnCalAdd").disabled=true;
        document.getElementById("btnHotAdd").disabled=true;
		document.getElementById("btnContractAdd").disabled=true;
		document.getElementById("btnContractDelete").disabled=true;
		document.getElementById("btnCotDelete").disabled=true;
		document.getElementById("btnCalDelete").disabled=true;
		document.getElementById("btnSerStdAdd").disabled=true;
		document.getElementById("btnDelete").disabled=true;
		document.getElementById("btnIncentiveAdd").disabled=true;
		document.getElementById("btnIncentiveDelete").disabled=true;
	}
	var check=document.querySelector('input[name="mainRadio"]:checked').value;
	var incCheck = document.querySelector('input[name="incFlag"]:checked').value;
	if(check=='coTerminus'){
			IC.util.widget.createDataTable("coTermTable",{tableId:"coTermTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"48vh",
                                });
			document.getElementById('coTermTable_wrapper').style.width = '100%';

		}
		else if(check=='serviceStandards'){
			IC.util.widget.createDataTable("srvStdTable",{tableId:"srvStdTable",hasChild:false,scrollingY:"48vh"});

		}
		else if(check=='handoverTime'){
			IC.util.widget.createDataTable("handoverTable",{tableId:"handoverTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"48vh" });

		}  else if(check=='contractID'){
		 IC.util.widget.createDataTable("contractIdTable",{tableId:"contractIdTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"48vh"
         });
		document.getElementById('contractIdTable_wrapper').style.width = '100%';
		}else if(check=='incentives'){
			if(incCheck=='incentive'){
				 IC.util.widget.createDataTable("incentiveTable",{tableId:"incentiveTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"35vh"
				 });
				document.getElementById('incentiveTable_wrapper').style.width = '100%';
			}
			else if(incCheck=='disincentive'){
				 IC.util.widget.createDataTable("disIncentiveTable",{tableId:"disIncentiveTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"35vh"
				 });
				 IC.util.widget.createDataTable("disIncentiveSrvTable",{tableId:"disIncentiveSrvTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"35vh"
				 });
				 IC.util.widget.createDataTable("disIncentiveNonSrvTable",{tableId:"disIncentiveNonSrvTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"35vh"
				 });
				document.getElementById('disIncentiveSrvTable_wrapper').style.width = '100%';
				document.getElementById('disIncentiveNonSrvTable_wrapper').style.width = '100%';
				document.getElementById('disIncentiveTable_wrapper').style.width = '100%';
			}
              // Added by A-8331 as part of ICRD-301783
			}else if(check=='RDTOffset'){
			IC.util.widget.createDataTable("rdtOffestTable",{tableId:"rdtOffestTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"48vh",
                                });
	           document.getElementById('rdtOffestTable_wrapper').style.width = '100%';

    }
	//Added By A-8331
	if(checkFlag=='Save_fail' && checkVal=='postCalender')
		{
			listDetails(frm);
		}
}
function invokeLOV(LOVType){
	var optionsArray =getLOVOptions(LOVType);
	if(optionsArray){
		if(LOVType=='AGT' || LOVType=='CST'){
			lovUtils.showLovPanel(optionsArray)
		}
		else
			lovUtils.displayLOV(optionsArray);
	}
}
function airLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("airLOV")[1];
	var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'Y' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'airportCodes',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'airportCodes',
				lovIconId					: 'airLOV',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}

function exchangeOfficeLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("exchangeOfficeLOV")[1];
	var strUrl ='mailtracking.defaults.ux.oelov.list.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Exchange Office',
				codeFldNameInScrn			: 'exchangeOffice',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'exchangeOffice',
				lovIconId					: 'exchangeOfficeLOV',
				maxlength					: 6
			}
			lovUtils.displayLOV(optionsArray);
}
function subClassLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("subClassLOV")[1];
	var strUrl ='mailtracking.defaults.ux.subclaslov.list.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Mail Sub Class',
				codeFldNameInScrn			: 'mailSubClass',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'mailSubClass',
				lovIconId					: 'subClassLOV',
				maxlength					: 2
			}
			lovUtils.displayLOV(optionsArray);
}
               //Added by A-8331 as part of ICRD-301783
function rdtairLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("rdtairLOV")[1];
	var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'airportCodes',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'airportCodes',
				lovIconId					: 'rdtairLOV',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}







function coAirLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("coAirLOV")[1];
	var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'Y' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'coAirportCodes',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'coAirportCodes',
				lovIconId					: 'coAirLOV',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}
function originLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("Origin")[1];
	var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'Y' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Origin',
				codeFldNameInScrn			: 'originCode',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'originCode',
				lovIconId					: 'Origin',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}
function serviceOriginLOVTempfunc(obj){
	var optionsArray;
	var ind = obj.id.split("serviceOriginLOVTemp")[1];
	var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Origin',
				codeFldNameInScrn			: 'originCode',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'originCode',
				lovIconId					: 'serviceOriginLOVTemp',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}
function serviceDestLOVTempfunc(obj){
	var optionsArray;
	var ind = obj.id.split("serviceDestLOVTemp")[1];
	var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Destination',
				codeFldNameInScrn			: 'destinationCode',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'destinationCode',
				lovIconId					: 'serviceDestLOVTemp',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}

function destLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("Dest")[1];
	var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'Y' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Destination',
				codeFldNameInScrn			: 'destinationCode',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'destinationCode',
				lovIconId					: 'Dest',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}
function originLOVTempfunc(obj){
	var optionsArray;
	var ind = obj.id.split("originLOVTemp")[1];
	var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'Y' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Origin',
				codeFldNameInScrn			: 'originCode',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'originCode',
				lovIconId					: 'originLOVTemp',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}
function destLOVTempfunc(obj){
	var optionsArray;
	var ind = obj.id.split("destLOVTemp")[1];
	var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'Y' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Destination',
				codeFldNameInScrn			: 'destinationCode',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'destinationCode',
				lovIconId					: 'destLOVTemp',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}
 function hoAirportLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("hoAirportLOV")[1];
	var strUrl ='ux.showAirport.do?formCount=1&maxlength=3';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'hoAirportCodes',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'hoAirportCodes',
				lovIconId					: 'hoAirportLOV',
				maxlength					: 3
			}
			lovUtils.displayLOV(optionsArray);
}
//for Inc/Disincentive
function parameterLOVfunc(obj){

	var ind  = null;
	var code = null;
	if(obj.id.search("disIncParameterLOV") != -1){
		ind = obj.id.split("disIncParameterLOV")[1];
		var field = document.getElementById("disIncParameter"+ind);
		code= field.value;
	}else if(obj.id.search("disIncNonSrvParameterLOV") != -1){
		ind = obj.id.split("disIncNonSrvParameterLOV")[1];
		var field = document.getElementById("disIncNonSrvParameter"+ind);
		code= field.value;
	}else{
		ind = obj.id.split("disIncBothSrvParameterLOV")[1];
		var field = document.getElementById("disIncBothSrvParameter"+ind);
		code= field.value;
	}
	var popupTitle = "Parameter";

	var srvFlag=document.querySelector('input[name="serviceResponsiveFlag"]:checked').value;
	var urlString = "mail.operations.ux.mailperformance.incentiveconfiguration.parameter.screenload.do?index="+ind+"&code="+code+"&serviceResponseFlag="+srvFlag;
	var closeButtonIds = ['btnClose'];
	var optionsArray = {
		actionUrl : urlString,
		dialogWidth:"600",
		dialogHeight:"350",
		closeButtonIds : closeButtonIds,
		popupTitle: popupTitle
   }
	popupUtils.openPopUp(optionsArray);
}
function formulaLOVfunc(obj){

	var ind  = null;
	var code = null;
	if(obj.id.search("srvformulaLOV") != -1){
		ind = obj.id.split("srvformulaLOV")[1];
		var field = document.getElementById("srvformula"+ind)
		code= field.value;
	}else if(obj.id.search("nonSrvformulaLOV") != -1){
		ind = obj.id.split("nonSrvformulaLOV")[1];
		var field = document.getElementById("nonSrvformula"+ind)
		code= field.value;
	}else{
		ind = obj.id.split("bothSrvformulaLOV")[1];
		var field = document.getElementById("bothSrvformula"+ind)
		code= field.value;
	}
	var srvFlag=document.querySelector('input[name="serviceResponsiveFlag"]:checked').value;
	var popupTitle = "Formula";
	var urlString = "mail.operations.ux.mailperformance.incentiveconfiguration.formula.screenload.do?index="+ind+"&code="+code+"&serviceResponseFlag="+srvFlag;
	var closeButtonIds = ['btnClose'];
	var optionsArray = {
		actionUrl : urlString,
		dialogWidth:"700",
		dialogHeight:"350",
		closeButtonIds : closeButtonIds,
		popupTitle: popupTitle
			}
	popupUtils.openPopUp(optionsArray);
}
function basisLOVfunc(obj){

	var ind = null;
	var field = null;
	var code = null;
	if(obj.id.search("srvBasisLOV") != -1){
		ind = obj.id.split("srvBasisLOV")[1];
		field = document.getElementById("tempSrvBasis"+ind)
		code= field.value;
		if(code.includes("String"))
		{
			code= field.nextElementSibling.value;
		}
	
	}else if(obj.id.search("nonSrvBasisLOV") != -1){
		ind = obj.id.split("nonSrvBasisLOV")[1];
		field = document.getElementById("tempNonSrvBasis"+ind)
		code= field.value;
		if(code.includes("String"))
		{
			code= field.nextElementSibling.value;
		}
	}else{
		ind = obj.id.split("bothSrvBasisLOV")[1];
		field = document.getElementById("tempBothSrvBasis"+ind)
		code= field.value;
		if(code.includes("String"))
		{
			code= field.nextElementSibling.value;
		}
	}
	var srvFlag=document.querySelector('input[name="serviceResponsiveFlag"]:checked').value;
	var popupTitle = "Basis";
	var strUrl ='mail.operations.ux.mailperformance.incentiveconfiguration.basis.screenload.do?serviceResponseFlag='+srvFlag+'&index='+ind+"&code="+code;

	var closeButtonIds = ['btnClose'];
	var optionsArray = {
		actionUrl : strUrl,
		dialogWidth:"600",
		dialogHeight:"500",
		closeButtonIds : closeButtonIds,
		popupTitle: popupTitle
			}
	popupUtils.openPopUp(optionsArray);
}
//Contract
function conOrgairLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("airLOV")[1];
	var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'originAirports',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'originAirports',
				lovIconId					: 'airLOV',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}
function conDestairLOVfunc(obj){
	var optionsArray;
	var ind = obj.id.split("airLOV")[1];
	var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'destinationAirports',
				descriptionFldNameInScrn	: '' ,
				index						: ind,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'destinationAirports',
				lovIconId					: 'airLOV',
				maxlength					: 30
			}
			lovUtils.displayLOV(optionsArray);
}
//ends
function getLOVOptions(LOVType){
var optionsArray;
	switch(LOVType) {
		case 'ARP':
		var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'Y' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'airport',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'airport',
				lovIconId					: 'airport_btn',
				maxlength					: 3
			}
			 break;
		case 'ORG_ARP':
		var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Origin',
				codeFldNameInScrn			: 'serviceStandardsOrigin',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620, //modified by A-8353 for ICRD-293368
				fieldToFocus				: 'serviceStandardsOrigin',
				lovIconId					: 'airportOrigin_btn',
				maxlength					: 3
			}
			 break;
		case 'DEST_ARP':
		var strUrl ='ux.showAirport.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Destination',
				codeFldNameInScrn			: 'serviceStandardsDestination',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'serviceStandardsDestination',
				lovIconId					: 'airportDest_btn',
				maxlength					: 3
			}
			 break;
			 case 'CON_ORG_ARP':
		var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'originAirport',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'originAirport',
				lovIconId					: 'originAirport_btn',
				maxlength					: 3
			}
			 break;

			 case 'RDT_ARP':
		var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'rdtAirport',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'rdtAirport',
				lovIconId					: 'rdtAirport_btn',
				maxlength					: 3
			}
			 break;

		case 'CON_DEST_ARP':
		var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'destinationAirport',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'destinationAirport',
				lovIconId					: 'destinationAirport_btn',
				maxlength					: 3
			}
			 break;
        case 'HO_ARP':
		var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'hoAirport',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'hoairportcode',
				lovIconId					: 'hoairportcode_btn',
				maxlength					: 3
			}
			 break;
			 case 'CO_ARP':
		var strUrl ='ux.showAirport.do?formCount=1&maxlength=10';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Airport',
				codeFldNameInScrn			: 'coAirport',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'coAirport',
				lovIconId					: 'coAirport_btn',
				maxlength					: 3
			}
			 break;
				case 'GPA':
		var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'GPA Code',
				codeFldNameInScrn			: 'pacode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'pacode',
				lovIconId					: 'pacode_btn',
				maxlength					: 3
			}
			 break;
			 case 'CO_GPA':
		var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'GPA Code',
				codeFldNameInScrn			: 'coPacode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'coPacode',
				lovIconId					: 'coPacode_btn',
				maxlength					: 5
			}
			 break;
			 case 'RDT_GPA':
		var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'GPA Code',
				codeFldNameInScrn			: 'rdtPacode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'rdtPacode',
				lovIconId					: 'rdtPacode_btn',
				maxlength					: 5
			}
			 break;
			 case 'CAL_GPA':
		var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'GPA Code',
				codeFldNameInScrn			: 'calPacode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'calPacode',
				lovIconId					: 'calPacode_btn',
				maxlength					: 3
			}
			 break;
			  case 'SERSTD_GPA':
		var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
		optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'GPA Code',
				codeFldNameInScrn			: 'serviceStandardsPacode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'serviceStandardsPacode',
				lovIconId					: 'PAcode_btn',
				maxlength					: 3
			}
			 break;
             case 'CON_GPA':
			var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1';
			optionsArray = {
					mainActionUrl				: strUrl,
					isMultiSelect				: 'N' ,
					isPageable					: 'Y',
					paginationActionUrl			: strUrl,
					lovTitle					: 'GPA Code',
					codeFldNameInScrn			: 'conPaCode',						//Modified by A-8399 as part of ICRD-303455
					descriptionFldNameInScrn	: '' ,
					index						: 0,
					closeButtonIds 				: ['btnOk','btnClose'],
					dialogWidth					: 600,
					dialogHeight				: 500,
					fieldToFocus				: 'conPaCode',						//Modified by A-8399 as part of ICRD-303455
					lovIconId					: 'conPacode_btn',
					maxlength					: 3
				}
			 break;
			 case 'HO_GPA':
			var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1&maxlength=10';
			optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'GPA Code',
				codeFldNameInScrn			: 'hoPaCode',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnOk','btnClose'],
				dialogWidth					: 600,
				dialogHeight				: 500,
				fieldToFocus				: 'hopacode',
				lovIconId					: 'hopacode_btn',
				maxlength					: 3
			}
			 break;
			 case 'INC_GPA':
			var strUrl ='mailtracking.defaults.ux.palov.list.do?formCount=1&maxlength=10';
			optionsArray = {
					mainActionUrl				: strUrl,
					isMultiSelect				: 'N' ,
					isPageable					: 'Y',
					paginationActionUrl			: strUrl,
					lovTitle					: 'GPA Code',
					codeFldNameInScrn			: 'incPaCode',
					descriptionFldNameInScrn	: '' ,
					index						: 0,
					closeButtonIds 				: ['btnOk','btnClose'],
					dialogWidth					: 600,
					dialogHeight				: 500,
					fieldToFocus				: 'incPaCode',
					lovIconId					: 'incPaCode_btn',
					maxlength					: 3
				}
			 break;
		  case 'HO_EXGOFC':
			var strUrl ='mailtracking.defaults.ux.oelov.list.do?formCount=1&maxlength=10';
					optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Office Of Exchange',
				codeFldNameInScrn			: 'hoExchangeOffice',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'hoExchangeOffice',
				lovIconId					: 'hoExchangeOffice_btn',
				maxlength					: 6
			}
			break;
     case 'HO_SUBCLS':
			var strUrl ='mailtracking.defaults.ux.subclaslov.list.do?formCount=1&maxlength=10';
					optionsArray = {
				mainActionUrl				: strUrl,
				isMultiSelect				: 'N' ,
				isPageable					: 'Y',
				paginationActionUrl			: strUrl,
				lovTitle					: 'Sub Class',
				codeFldNameInScrn			: 'hoMailSubClass',
				descriptionFldNameInScrn	: '' ,
				index						: 0,
				closeButtonIds 				: ['btnClose','btnOk'],
				dialogWidth					: 600,
				dialogHeight				: 620,
				fieldToFocus				: 'hoMailSubClass',
				lovIconId					: 'hoMailSubClass_btn',
				maxlength					: 2
				}
			 break;
		default:
			optionsArray = {
			}
		}
		return optionsArray;
}
function togglepanel(showDivId, hideDivId1, hideDivId2, hideDivId3, hideDivId4, hideDivId5,hideDivId6,
							showDivIdb, hideDivIdb1, hideDivIdb2, hideDivIdb3, hideDivIdb4, hideDivIdb5,hideDivIdb6){
	jquery('#'+showDivId).show();
	jquery('#'+hideDivId1).hide();
	jquery('#'+hideDivId2).hide();
	jquery('#'+hideDivId3).hide();
	jquery('#'+hideDivId4).hide();
	jquery('#'+hideDivId5).hide();
	jquery('#'+hideDivId6).hide();
	jquery('#'+showDivIdb).show();
	jquery('#'+hideDivIdb1).hide();
	jquery('#'+hideDivIdb2).hide();
	jquery('#'+hideDivIdb3).hide();
	jquery('#'+hideDivIdb4).hide();
	jquery('#'+hideDivIdb5).hide();
	jquery('#'+hideDivIdb6).hide();
	/*var obj = document.getElementById(showDivId).style;
	obj.visibility = "visible";
	obj.display = "";
	obj = document.getElementById(hideDivId1).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivId2).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivId3).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivId4).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivId5).style;
	obj.visibility = "hidden";
	obj.display = "none";
	var obj = document.getElementById(showDivIdb).style;
	obj.visibility = "visible";
	obj.display = "";
	obj = document.getElementById(hideDivIdb1).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivIdb2).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivIdb3).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivIdb4).style;
	obj.visibility = "hidden";
	obj.display = "none";
	obj = document.getElementById(hideDivIdb5).style;
	obj.visibility = "hidden";
	obj.display = "none";
	*/
}
function listDetails(frm){
	document.getElementById("btnSave").disabled=false;
	document.getElementsByName("btnAdd").disabled=false;
	var listCheck=document.querySelector('input[name="mainRadio"]:checked').value;
	if(listCheck==='coTerminus'){
		var coTgpaCheck=frm.elements.coPacode.value;			
		if(""===coTgpaCheck){		
			showDialog({msg:'Type or Select GPA',type:1,parentWindow:self});			
		}else{
		submitForm(frm,"mail.operations.ux.mailperformance.coterminus.list.do");
		}
	}	
	if(listCheck==='postalCalendar'){
	var gpaCheck=frm.elements.calPacode.value;
	if(""===gpaCheck){
	showDialog({msg:'Type or Select GPA',type:1,parentWindow:self});
			document.getElementById("btnSave").disabled=true;
			document.getElementById("btnCalAdd").disabled=true;
			document.getElementById("btnCalDelete").disabled=true;
	}else{
		var valueCheck=frm.elements.filterCalender.value;
		if(valueCheck==='C'){
			_divIdSeg = "cgrPostalCalendar";
			currDivId = document.getElementById("postalCal").id;
			var strAction = "mail.operations.ux.mailperformance.postalcalendar.list.do";
			asyncSubmit(targetFormName,strAction,_divIdSeg,null,null,currDivId);
		}
		else if(valueCheck==='I'){
			_divIdSeg = "invoicPostalCalendar";
			currDivId = document.getElementById("postalCal").id;
			var strAction = "mail.operations.ux.mailperformance.postalcalendar.list.do";
			asyncSubmit(targetFormName,strAction,_divIdSeg,null,null,currDivId);
		}
			/*IC.util.element.enable(document.getElementById('btnSave'));
			IC.util.element.enable(document.getElementById('btnCalAdd'));
			IC.util.element.enable(document.getElementById('btnCalDelete'));*/
			document.getElementById("btnSave").disabled=false;
			document.getElementById("btnCalAdd").disabled=false;
			document.getElementById("btnCalDelete").disabled=false;
		frm.elements.calPacode.disabled=true;
		}
	}
     if(listCheck==='handoverTime')	  {
		submitForm(frm,"mail.operations.ux.mailperformance.handovertime.list.do");
     }
	if(listCheck==='serviceStandards')
		submitForm(frm,"mail.operations.ux.mailperformance.servicestandards.list.do");
	 if(listCheck==='contractID')	  {
		submitForm(frm,"mail.operations.ux.mailperformance.contractid.list.do");
     }
	 if(listCheck==='incentives'){
		submitForm(frm,"mail.operations.ux.mailperformance.incentiveconfiguration.list.do");
     }
	 if(listCheck==='RDTOffset'){
		submitForm(frm,"mail.operations.ux.mailperformance.rdtoffset.list.do");
     }
}
function invoicPostalCalendar(_tableInfo){
	_resultDiv = _tableInfo.document.getElementById("ajax_div").innerHTML;
	_str = _tableInfo.getTableData();
		document.getElementById("postalCal").innerHTML=_resultDiv;
		document.getElementById("postalCal").style.display = "block";
	IC.util.widget.createDataTable("invoicTable",{tableId:"invoicTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"48vh",
                                });
	if(targetFormName.calRowIds != null){
		evtHandler.addEvents("calRowIds","toggleTableHeaderCheckbox('calRowIds',targetFormName.elements.calCheckAll)",EVT_CLICK);
	}
	if(fl==true)fl=false; else fl=true;
	if(document.getElementsByName("calRowIds").length==1&&fl==false){
		showDialog({msg:'No results found. Click OK to proceed',type:1,parentWindow:self});
	}
	document.getElementById('invoicTable_wrapper').style.width = '100%';

}
function cgrPostalCalendar(_tableInfo){
	_resultDiv = _tableInfo.document.getElementById("ajax_div1").innerHTML;
	_str = _tableInfo.getTableData();
	document.getElementById("postalCal").innerHTML=_resultDiv;
	document.getElementById("postalCal").style.display = "block";
	IC.util.widget.createDataTable("cgrTable",{tableId:"cgrTable",childTemplatingReqrd:false,hasChild:false,scrollingY:"48vh",
                                });
	if(targetFormName.calRowIds != null){
		evtHandler.addEvents("calRowIds","toggleTableHeaderCheckbox('calRowIds',targetFormName.elements.calCheckAll)",EVT_CLICK);
	}
	if(fl==true)fl=false; else fl=true;
	if(document.getElementsByName("calRowIds").length==1&&fl==false){
		showDialog({msg:'No results found. Click OK to proceed',type:1,parentWindow:self});
	}
	document.getElementById('cgrTable_wrapper').style.width = '100%';

}
function deleteTableRow1(checkBoxName,hiddenFlag,index) {
	var check1 = validateSelectedCheckBoxes1(targetFormName,checkBoxName, 10, 1);
	if(check1) {
		var childChkBoxObj = document.getElementsByName(checkBoxName);
		var chkBoxLength = childChkBoxObj.length;
		if (chkBoxLength > 1) {
			for(var j = 0; j < chkBoxLength; j++) {
				if(j==index) {
					childChkBoxObj[j].checked=true;
					break;
				}
			}
		}
		if (chkBoxLength > 1) {
			for(var j = 0; j < chkBoxLength; j++) {
				if(childChkBoxObj[j].checked) {
					if(childChkBoxObj[j].parentNode){
						_tr = childChkBoxObj[j].parentNode.parentNode;
						if(_tr.tagName != "TR") {
							_tr = _tr.parentNode;
						}
						if(_tr.getAttribute("template")!=null || _tr.tagName != "TR") {
							continue;
						}
						elems = _tr.getElementsByTagName("input");
						_tr.style.display = 'none';
						if(elems && elems.length > 0) {
							for(var k=0;k<elems.length;k++) {
								elems[k].style.display = 'none';
							}
						}
						if(document.getElementsByName(hiddenFlag)[j].value=="I"){
							document.getElementsByName(hiddenFlag)[j].value="NOOP";
						}
						else if(document.getElementsByName(hiddenFlag)[j].value!="NOOP"){
							document.getElementsByName(hiddenFlag)[j].value = "D";
						}
					}
				}
			}
		}
	}
var childChkBoxObj = document.getElementsByName(checkBoxName);
	var chkBoxLength = childChkBoxObj.length;
	if (chkBoxLength > 1) {
		_table = getParentTable(childChkBoxObj[0]);
	} else {
		_table = getParentTable(childChkBoxObj);
	}
	if(_table) {
		var delRowEvent = _table.getAttribute("delRowEvent");
		if(delRowEvent){
			try{
			eval('if(window.'+delRowEvent+'){'+delRowEvent+'();}');
			}catch(e){}
		}
		jquery(_table).trigger(IC.consts.customEvent.TABLE_ROW_UPDATE,[false,false,true]);
	}
}
 function validateSelectedCheckBoxes1(frmObj,childCheckBoxName,maxNumSelected,minSelected)
{
	var nMaxNumSelected = 0;
	if(maxNumSelected != null)
		nMaxNumSelected  = parseInt(maxNumSelected);
	var nMinSelected = 0;
	if(minSelected != null)
		nMinSelected  = parseInt(minSelected);
	if(nMinSelected > nMaxNumSelected){
		showDialog({
			msg:'Minimum number of records cannot be more than the maximum number of records selected',
			type:1,
			parentWindow:self
		});
		return false;
	}
		return true;
}
function toggleResponsivePanel(incType){
	if(incType=="incentive"){
		document.getElementById("serviceResponsiveSection").style.display = "none";
		document.getElementById("serviceResponsiveSection").style.visibility = "hidden";
		document.getElementById("srvDisIncentive").style.display = "none";
		document.getElementById("srvDisIncentive").style.visibility = "hidden";
		document.getElementById("nonSrvDisIncentive").style.display = "none";
		document.getElementById("nonSrvDisIncentive").style.visibility = "hidden";
		document.getElementById("bothDisIncentive").style.display = "none";
		document.getElementById("bothDisIncentive").style.visibility = "hidden";
		document.getElementById("incentive").style.display = "";
		document.getElementById("incentive").style.visibility = "visible";
		document.getElementById("incentiveTable").style.display = "";
		document.getElementById("incentiveTable").style.visibility = "visible";
	}else{
		document.getElementById("serviceResponsiveSection").style.display = "";
		document.getElementById("serviceResponsiveSection").style.visibility = "visible";
		if(targetFormName.elements.serviceResponsiveFlag.value == 'Y'){
			document.getElementById("srvRespRadioBtn").checked="true";
			document.getElementById("srvDisIncentive").style.display = "";
			document.getElementById("srvDisIncentive").style.visibility = "visible";
			document.getElementById("nonSrvDisIncentive").style.display = "none";
			document.getElementById("nonSrvDisIncentive").style.visibility = "hidden";
			document.getElementById("bothDisIncentive").style.display = "none";
			document.getElementById("bothDisIncentive").style.visibility = "hidden";
												document.getElementById("disIncentiveNonSrvTable").style.display = "none";
												document.getElementById("disIncentiveTable").style.display = "none";
												document.getElementById("disIncentiveSrvTable").style.display = "";
												document.getElementById("disIncentiveSrvTable").style.visibility = "visible";
		}else if(targetFormName.elements.serviceResponsiveFlag.value == 'N'){
			document.getElementById("nonSrvRespRadioBtn").checked="true";
			document.getElementById("srvDisIncentive").style.display = "none";
			document.getElementById("srvDisIncentive").style.visibility = "hidden";
			document.getElementById("nonSrvDisIncentive").style.display = "";
			document.getElementById("nonSrvDisIncentive").style.visibility = "visible";
			document.getElementById("bothDisIncentive").style.display = "none";
			document.getElementById("bothDisIncentive").style.visibility = "hidden";
												document.getElementById("disIncentiveSrvTable").style.display = "none";
												document.getElementById("disIncentiveTable").style.display = "none";
												document.getElementById("disIncentiveNonSrvTable").style.display = "";
		}else if(targetFormName.elements.serviceResponsiveFlag.value == 'B'){
			document.getElementById("bothRadioBtn").checked="true";
			document.getElementById("srvDisIncentive").style.display = "none";
			document.getElementById("srvDisIncentive").style.visibility = "hidden";
			document.getElementById("nonSrvDisIncentive").style.display = "";
			document.getElementById("nonSrvDisIncentive").style.visibility = "visible";
			document.getElementById("bothDisIncentive").style.display = "";
			document.getElementById("bothDisIncentive").style.visibility = "visible";
												document.getElementById("disIncentiveNonSrvTable").style.display = "none";
												document.getElementById("disIncentiveSrvTable").style.display = "none";
												document.getElementById("disIncentiveTable").style.display = "";
		}
		document.getElementById("incentive").style.display = "none";
		document.getElementById("incentive").style.visibility = "hidden";
		document.getElementById("incentiveTable").style.display = "none";
	}
}
function toggleServiceResponsivePanel(servRespType){
	var rowId = eval(targetFormName.disIncSrvRowId);
	var nonSrvRowId = eval(targetFormName.disIncNonSrvRowId);
	var bothSrvRowId = eval(targetFormName.disIncBothSrvRowId);
	var i ;
	if(servRespType == "ServiceResponsive"){
		document.getElementById("srvDisIncentive").style.display = "";
		document.getElementById("srvDisIncentive").style.visibility = "visible";
		document.getElementById("nonSrvDisIncentive").style.display = "none";
		document.getElementById("nonSrvDisIncentive").style.visibility = "hidden";
		document.getElementById("bothDisIncentive").style.display = "none";
		document.getElementById("bothDisIncentive").style.visibility = "hidden";
		document.getElementById("disIncentiveNonSrvTable").style.display = "none";
		document.getElementById("disIncentiveTable").style.display = "none";
	    document.getElementById("disIncentiveSrvTable").style.display = "";
		jquery(".dataTables_scrollHeadInner").width("auto");
		jquery(".check-box-cell").css('width','41px');
		jquery(".param").css('width','259px');
		jquery(".formul").css('width','493px');
		jquery(".bas").css('width','188px');
		jquery(".perc").css('width','86px');
		jquery(".valfrm").css('width','153px');
		jquery(".valto").css('width','154px');
	}else if(servRespType == "NonServiceResponsive"){
		document.getElementById("nonSrvDisIncentive").style.display = "";
		document.getElementById("nonSrvDisIncentive").style.visibility = "visible";
		document.getElementById("srvDisIncentive").style.display = "none";
		document.getElementById("srvDisIncentive").style.visibility = "hidden";
		document.getElementById("bothDisIncentive").style.display = "none";
		document.getElementById("bothDisIncentive").style.visibility = "hidden";
		document.getElementById("disIncentiveSrvTable").style.display = "none";
	    document.getElementById("disIncentiveTable").style.display = "none";
	    document.getElementById("disIncentiveNonSrvTable").style.display = "";
		jquery(".dataTables_scrollHeadInner").width("auto");
		jquery(".check-box-cell").css('width','41px');
		jquery(".param").css('width','259px');
		jquery(".formul").css('width','493px');
		jquery(".bas").css('width','188px');
		jquery(".perc").css('width','86px');
		jquery(".valfrm").css('width','153px');
		jquery(".valto").css('width','154px');
	}else if(servRespType == "BothServiceResponsive"){
		document.getElementById("bothDisIncentive").style.display = "";
		document.getElementById("bothDisIncentive").style.visibility = "visible";
		document.getElementById("srvDisIncentive").style.display = "none";
		document.getElementById("srvDisIncentive").style.visibility = "hidden";
		document.getElementById("nonSrvDisIncentive").style.display = "none";
		document.getElementById("nonSrvDisIncentive").style.visibility = "hidden";
		document.getElementById("disIncentiveNonSrvTable").style.display = "none";
		document.getElementById("disIncentiveSrvTable").style.display = "none";
		document.getElementById("disIncentiveTable").style.display = "";
		jquery(".dataTables_scrollHeadInner").width("auto");
		jquery(".check-box-cell").css('width','41px');
		jquery(".param").css('width','259px');
		jquery(".formul").css('width','493px');
		jquery(".bas").css('width','188px');
		jquery(".perc").css('width','86px');
		jquery(".valfrm").css('width','153px');
		jquery(".valto").css('width','154px');
	}
	/* for(i=0;i<rowId.length;i++){
		if(servRespType == "ServiceResponsive"){
			if(document.getElementById("disIncentiveServiceResp"+i))
				document.getElementById("disIncentiveServiceResp"+i).style.display = "";
			if(document.getElementById("disIncentiveNonServiceResp"+i))
				document.getElementById("disIncentiveNonServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveBothServiceResp"+i))
				document.getElementById("disIncentiveBothServiceResp"+i).style.display = "none";
		}else if(servRespType == "NonServiceResponsive"){

			if(document.getElementById("disIncentiveServiceResp"+i))
				document.getElementById("disIncentiveServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveNonServiceResp"+i))
				document.getElementById("disIncentiveNonServiceResp"+i).style.display = "";
			if(document.getElementById("disIncentiveBothServiceResp"+i))
				document.getElementById("disIncentiveBothServiceResp"+i).style.display = "none";
		}else if(servRespType == "BothServiceResponsive"){
			if(document.getElementById("disIncentiveServiceResp"+i))
				document.getElementById("disIncentiveServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveNonServiceResp"+i))
				document.getElementById("disIncentiveNonServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveBothServiceResp"+i))
				document.getElementById("disIncentiveBothServiceResp"+i).style.display = "";
		}
	}
	for(i=0;i<nonSrvRowId.length;i++){
		 if(servRespType == "ServiceResponsive"){
			if(document.getElementById("disIncentiveServiceResp"+i))
				document.getElementById("disIncentiveServiceResp"+i).style.display = "";
			if(document.getElementById("disIncentiveNonServiceResp"+i))
				document.getElementById("disIncentiveNonServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveBothServiceResp"+i))
				document.getElementById("disIncentiveBothServiceResp"+i).style.display = "none";
		}else if(servRespType == "NonServiceResponsive"){
			if(document.getElementById("disIncentiveServiceResp"+i))
				document.getElementById("disIncentiveServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveNonServiceResp"+i))
				document.getElementById("disIncentiveNonServiceResp"+i).style.display = "";
			if(document.getElementById("disIncentiveBothServiceResp"+i))
				document.getElementById("disIncentiveBothServiceResp"+i).style.display = "none";
		}else if(servRespType == "BothServiceResponsive"){
			if(document.getElementById("disIncentiveServiceResp"+i))
				document.getElementById("disIncentiveServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveNonServiceResp"+i))
				document.getElementById("disIncentiveNonServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveBothServiceResp"+i))
				document.getElementById("disIncentiveBothServiceResp"+i).style.display = "";
		}
	}
	for(i=0;i<bothSrvRowId.length;i++){
		if(servRespType == "ServiceResponsive"){
			if(document.getElementById("disIncentiveServiceResp"+i))
				document.getElementById("disIncentiveServiceResp"+i).style.display = "";
			if(document.getElementById("disIncentiveNonServiceResp"+i))
				document.getElementById("disIncentiveNonServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveBothServiceResp"+i))
				document.getElementById("disIncentiveBothServiceResp"+i).style.display = "none";
		}else if(servRespType == "NonServiceResponsive"){
			if(document.getElementById("disIncentiveServiceResp"+i))
				document.getElementById("disIncentiveServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveNonServiceResp"+i))
				document.getElementById("disIncentiveNonServiceResp"+i).style.display = "";
			if(document.getElementById("disIncentiveBothServiceResp"+i))
				document.getElementById("disIncentiveBothServiceResp"+i).style.display = "none";
		}else if(servRespType == "BothServiceResponsive"){
			if(document.getElementById("disIncentiveServiceResp"+i))
				document.getElementById("disIncentiveServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveNonServiceResp"+i))
				document.getElementById("disIncentiveNonServiceResp"+i).style.display = "none";
			if(document.getElementById("disIncentiveBothServiceResp"+i))
				document.getElementById("disIncentiveBothServiceResp"+i).style.display = "";
		}
	} */
}
 function restrictPercentage(fieldObj,dec,prec){
	validateDecimal(fieldObj,dec,prec);
	if(fieldObj.value>100){
	showDialog({msg:"Maximum allowed percentage is 100", type:1, parentWindow:self, parentForm:targetFormName});
	}
}

//Added by A-7540 for ICRD-314176
function submitPage(lastPageNum,displayPage){
	targetFormName.elements.lastPageNum.value=lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
	submitForm(targetFormName,"mail.operations.ux.mailperformance.servicestandards.list.do");

}
//Added by A-8527 for ICRD-352718 starts
function submithandoverPage(lastPageNum,displayPage){
	targetFormName.elements.lastPageNum.value=lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
	submitForm(targetFormName,"mail.operations.ux.mailperformance.handovertime.list.do");
}
function showhandoverEntriesReloading(obj) {
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	submitForm(targetFormName,"mail.operations.ux.mailperformance.handovertime.list.do");
}
//Added by A-8527 for ICRD-352718 Ends
//Added by A-7540 for ICRD-314176
function showEntriesReloading(obj) {
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	submitForm(targetFormName,"mail.operations.ux.mailperformance.servicestandards.list.do");
}
function closefn(){
popupUtils.closePopupDialog();
}
function pop(obj){
WebuiPopovers.hideAll();
						jquery('.dropdown-toggle').webuiPopover({
			content: function () {
				return jquery(this).next('.dropdown-box').html();
			},
			width: '180',
			padding: false,
			style: ' dropdown',
		});
}

function deleteRow(obj){
	var index = obj.id.split('delete')[1];
	if(index.split('0')[0]==''){
		index = index.split('0')[1];
	}
	deleteTableRow1('rdtRowId','rdtOperationFlag',index);
}