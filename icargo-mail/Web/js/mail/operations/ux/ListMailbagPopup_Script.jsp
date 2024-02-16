<%@ include file="/jsp/includes/js_contenttype.jsp" %>
<%@ include file="/jsp/includes/tlds.jsp" %>


function screenSpecificEventRegister(){
  var frm=targetFormName;
  doScreenLoadCheck(frm);
  with(frm){
	evtHandler.addEvents("btOK","okfn(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btOKLying","okfnLying(targetFormName)",EVT_CLICK);
	evtHandler.addEvents("btClose","closefn()",EVT_CLICK);
	evtHandler.addIDEvents("btnClearFilter","clearFilter(this.form)",EVT_CLICK);
	evtHandler.addIDEvents("btnClearFilterLy","clearFilterLy(this.form)",EVT_CLICK);
	evtHandler.addIDEvents("btAdd","addToTruck(this.form)",EVT_CLICK);

}
	onscreenload(frm);
}



function doScreenLoadCheck(frm){

	if(frm.elements.okForScreenClose.value == 'YES'){	
		if(frm.elements.refreshParent.value=='YES'){			
				window.parent.recreateMailBagTableDetails("addons.trucking.updateSession.do",'MailBagDetailsTable','recreateMailBagDetailsTemplateRow');
				popupUtils.closePopupDialog();       
		}else{
			popupUtils.closePopupDialog();       
		}
	} 
	
}

function addToTruck(targetFormName){
	var action = "mail.operations.ux.listmailpopup.truckOrderNavigatePopupOk.do";     
	submitForm(targetFormName, action);
}

function onscreenload(frm){
	IC.util.widget.createDataTable("cardittable",{tableId:"cardittable",hasChild:false,scrollingY:'54.8vh',scrollingX:'68.8vh'});
	IC.util.widget.createDataTable("lyinglisttable",{tableId:"lyinglisttable",hasChild:false,scrollingY:'54.8vh'});
	jquery('#tabs').tabs({
		
		active: (targetFormName.elements.paginationFlag.value == 'Lying' || targetFormName.elements.filterFlag.value == 'LyingFliter')? 1 : 0
		
	});

			 
	if(targetFormName.elements.paginationFlag.value == 'Lying' || targetFormName.elements.filterFlag.value == 'LyingFliter'){
		
	jquery('#lyingList').show();
	jquery('#cardit').hide();
	targetFormName.elements.filterFlag.value='';
	targetFormName.elements.paginationFlag.value='';

	}	 
}

function closefn(){
popupUtils.closePopupDialog();
}

function showEntriesReloading(obj){
	targetFormName.elements.defaultPageSize.value=obj.value;
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	targetFormName.elements.displayPageForLyingList.value="1";
	targetFormName.elements.lastPageNumForLylingList.value="0";
	var action = "mail.operations.ux.listmailpopup.screenLoad.do";     
	submitForm(targetFormName, action);
	}
	
	
//Cardit Tab functions starts....................
function okfn(targetFormName){
	var mailbagId=jquery('#mailbagId').val();
	var ooe=jquery('#ooe').val();
	var doe=jquery('#doe').val();
	var mailCategoryCode=jquery('#mailCategoryCode').val();
	var mailSubclass=jquery('#mailSubclass').val();
	var year=jquery('#year').val();
	var dsn=jquery('#despatchSerialNumber').val();
	var rsn=jquery('#receptacleSerialNumber').val();
	var consignmentNumber=jquery('#consignmentNumber').val();
	var fromDate=jquery('#fromDate').val();
	var toDate=jquery('#toDate').val();
	var paCode=jquery('#paCode').val();
	var flightNumber=jquery('#flightNumber').val();
	var flightDate=jquery('#flightDate').val();
	var upliftAirport=jquery('#upliftAirport').val();
	var uldNumber=jquery('#uldNumber').val();
	var originAirportCode=jquery('#originAirportCode').val();
	var destinationAirportCode=jquery('#destinationAirportCode').val();
	var acceptstatus=jquery('#status').val();
	
	submitForm(targetFormName,"mail.operations.ux.listmailpopup.screenLoad.do?mailbagId="+mailbagId+"&ooe="+ooe+"&doe="+doe+"&mailCategoryCode="+mailCategoryCode+"&mailSubclass="+mailSubclass+"&year="+year+"&despatchSerialNumber="+dsn+"&receptacleSerialNumber="+rsn+"&consignmentNumber="+consignmentNumber+"&fromDate="+fromDate+"&toDate="+toDate+"&paCode="+paCode+"&flightNumber="+flightNumber+"&flightDate="+flightDate+"&upliftAirport="+upliftAirport+"&uldNumber="+uldNumber+"&originAirportCode="+originAirportCode+"&destinationAirportCode="+destinationAirportCode+"&status="+acceptstatus+"&filterType=C");
	
	
}

function submitPage(lastPageNum,displayPage){
	targetFormName.elements.lastPageNum.value=lastPageNum;
	targetFormName.elements.displayPage.value=displayPage;
	submitForm(targetFormName, "mail.operations.ux.listmailpopup.screenLoad.do");

}
function clearFilter(targetFormName){
	document.getElementById('mailbagId').value="";
	document.getElementById('ooe').value="";
	document.getElementById('doe').value="";
	document.getElementById('mailCategoryCode').value="";
	document.getElementById('mailSubclass').value="";
	document.getElementById('year').value="";
	document.getElementById('despatchSerialNumber').value="";
	document.getElementById('receptacleSerialNumber').value="";
	document.getElementById('consignmentNumber').value="";
	document.getElementById('fromDate').value="";
	document.getElementById('toDate').value="";
	document.getElementById('paCode').value="";
	document.getElementById('flightNumber').value="";
	document.getElementById('flightDate').value="";
	document.getElementById('upliftAirport').value="";
	document.getElementById('uldNumber').value="";
	document.getElementById('originAirportCode').value="";
	document.getElementById('destinationAirportCode').value="";
	document.getElementById('status').value="";
	

}
//Cardit Tab functions ends....................



//Lying List Tab functions starts....................

function okfnLying(targetFormName){
	var mailbagIdLy=jquery('#mailbagIdLy').val();
	var ooeLy=jquery('#ooeLy').val();
	var doeLy=jquery('#doeLy').val();
	var mailCategoryCodeLy=jquery('#mailCategoryCodeLy').val();
	var mailSubclassLy=jquery('#mailSubclassLy').val();
	var yearLy=jquery('#yearLy').val();
	var dsnLy=jquery('#despatchSerialNumberLy').val();
	var rsnLy=jquery('#receptacleSerialNumberLy').val();
	var consignmentNumberLy=jquery('#consignmentNumberLy').val();
	var fromDateLy=jquery('#fromDateLy').val();
	var toDateLy=jquery('#toDateLy').val();
	var paCodeLy=jquery('#paCodeLy').val();
	var flightNumberLy=jquery('#flightNumberLy').val();
	var flightDateLy=jquery('#flightDateLy').val();
	var upliftAirportLy=jquery('#upliftAirportLy').val();
	var uldNumberLy=jquery('#uldNumberLy').val();
	var originAirportCodeLy=jquery('#originAirportCodeLy').val();
	var destinationAirportCodeLy=jquery('#destinationAirportCodeLy').val();
	
	
	submitForm(targetFormName,"mail.operations.ux.listmailpopup.screenLoad.do?filterFlag=LyingFliter&mailbagId="+mailbagIdLy+"&ooe="+ooeLy+"&doe="+doeLy+"&mailCategoryCode="+mailCategoryCodeLy+"&mailSubclass="+mailSubclassLy+"&year="+yearLy+"&despatchSerialNumber="+dsnLy+"&receptacleSerialNumber="+rsnLy+"&consignmentNumber="+consignmentNumberLy+"&fromDate="+fromDateLy+"&toDate="+toDateLy+"&paCode="+paCodeLy+"&flightNumber="+flightNumberLy+"&flightDate="+flightDateLy+"&upliftAirport="+upliftAirportLy+"&uldNumber="+uldNumberLy+"&originAirportCode="+originAirportCodeLy+"&destinationAirportCode="+destinationAirportCodeLy+"&filterType=L");
	
	
	
}
function clearFilterLy(targetFormName){
	
	document.getElementById('mailbagIdLy').value="";
	document.getElementById('ooeLy').value="";
	document.getElementById('doeLy').value="";
	document.getElementById('mailCategoryCodeLy').value="";
	document.getElementById('mailSubclassLy').value="";
	document.getElementById('yearLy').value="";
	document.getElementById('despatchSerialNumberLy').value="";
	document.getElementById('receptacleSerialNumberLy').value="";
	document.getElementById('consignmentNumberLy').value="";
	document.getElementById('fromDateLy').value="";
	document.getElementById('toDateLy').value="";
	document.getElementById('paCodeLy').value="";
	document.getElementById('flightNumberLy').value="";
	document.getElementById('flightDateLy').value="";
	document.getElementById('upliftAirportLy').value="";
	document.getElementById('uldNumberLy').value="";
	document.getElementById('originAirportCodeLy').value="";
	document.getElementById('destinationAirportCodeLy').value="";

}


function submitPageForLyingList(lastPageNumForLylingList,displayPageForLyingList){
	targetFormName.elements.lastPageNumForLylingList.value=lastPageNumForLylingList;
	targetFormName.elements.displayPageForLyingList.value=displayPageForLyingList;
	submitForm(targetFormName, "mail.operations.ux.listmailpopup.screenLoad.do?paginationFlag=Lying");
    
}

//Lying List Tab functions ends....................








	   











