<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister(){


	with(targetFormName){

		evtHandler.addEvents("btnList","doList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","doClear()",EVT_CLICK);
		evtHandler.addEvents("okButton","doOk()",EVT_CLICK);
		evtHandler.addEvents("btnClose","window.close()",EVT_CLICK);
		evtHandler.addEvents("code","mailDSN()",EVT_BLUR);
	}

	screenload();

}


function doList(){


targetFormName.elements.lastPageNum.value= 0;
targetFormName.elements.displayPage.value = 1;

submitForm(targetFormName,'mailtracking.mra.defaults.dsnpopup.list.do');

}

function submitPage1(strLastPageNum,strDisplayPage){

	targetFormName.elements.lastPageNum.value= strLastPageNum;
	targetFormName.elements.displayPage.value = strDisplayPage;
	submitForm(targetFormName, 'mailtracking.mra.defaults.dsnpopup.list.do');
}

function doClear(){
submitForm(targetFormName,'mailtracking.mra.defaults.dsnpopup.clear.do');
}


function doOk(){
	if(validateSelectedCheckBoxes(targetFormName,'check','1',1)){
	submitForm(targetFormName,'mailtracking.mra.defaults.dsnpopup.ok.do');
	}
}

function screenload(){


	if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="despatchenq"){

	window.close();
	window.opener.targetFormName.action
		= "mailtracking.mra.defaults.despatchenquiry.list.do";
	window.opener.targetFormName.submit();
	}
	else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="RateAuditDetails"){

	window.close();
	window.opener.targetFormName.action
		= "mailtracking.mra.defaults.listrateauditdetails.list.do";
	window.opener.targetFormName.submit();
	}else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="MAINTAIN_CCA"){
		window.close();
		window.opener.submitForm(window.opener.targetFormName,'mailtracking.mra.defaults.maintaincca.listccadetailsforOK.do');
	}else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="despatchrouting"){
		window.close();
		window.opener.targetFormName.action
			= "mailtracking.mra.defaults.despatchrouting.listscreen.do";
		window.opener.targetFormName.submit();
	}else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="ManualProration"){

		window.close();
		window.opener.targetFormName.action
			= "mailtracking.mra.defaults.manualproration.listscreen.do";
		window.opener.targetFormName.submit();
	}else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="ViewProration"){
		window.close();
		window.opener.targetFormName.action	= "mailtracking.mra.defaults.viewproration.listproration.do";
		window.opener.targetFormName.submit();
	}else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="ProrationLog"){

		window.close();
		window.opener.targetFormName.action
			= "mailtracking.mra.defaults.prorationlog.listscreen.do";
		window.opener.targetFormName.submit();
		}
	//added by a-3447 for gpa reporting.
	else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="GpaReporting"){

		window.close();
		window.opener.targetFormName.action= "mailtracking.mra.gpareporting.capturegpareportpopup.showdsnpopup.do";
		window.opener.targetFormName.submit();
		}
	else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="FromCN66Details"){
	
		self.opener.targetFormName.action="mailtracking.mra.airlinebilling.capturecn66.dsnpopupok.do";
		self.opener.targetFormName.submit();
		self.closeNow();
			
			
		}
   else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="rejectionmemo"){
			window.close();
			window.opener.targetFormName.action
				= "mailtracking.mra.airlinebilling.inward.rejectionmemo.list.do";
			window.opener.targetFormName.submit();
		}
	else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="ManualAccountingEntry"){
	window.close();
	window.opener.targetFormName.action
	= "cra.accounting.manualaccountingentry.dsnpopupok.do";
	window.opener.targetFormName.submit();
	}

	else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="ManualAccounting"){
		window.close();
		window.opener.targetFormName.action
		= "cra.accounting.manualaccounting.dsnpopupok.do";
		window.opener.targetFormName.submit();
	}
	else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="ListAccountingEntry"){

	window.close();
	window.opener.targetFormName.action
	= "cra.accounting.listaccountingentriesformra.do";
	window.opener.targetFormName.submit();
	}
	else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="ListAccountingEntryAccountLevel"){

	window.close();
	window.opener.targetFormName.action
	= "cra.accounting.listaccountingentriesacclevelformra.do";
	window.opener.targetFormName.submit();
	}
	else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="ListAccountingEntrySummary"){

	window.close();
	window.opener.targetFormName.action
	= "cra.accounting.listaccountingentriessummaryformra.do";
	window.opener.targetFormName.submit();
	}
	else if(targetFormName.elements.okFlag.value=="OK" && targetFormName.elements.fromPage.value=="ReconcilationDetailsForOthers"){

	window.close();
	window.opener.targetFormName.action
	= "cra.accounting.reconcillationdetailsforothers.do";
	window.opener.targetFormName.submit();
	}
}

function mailDSN(){

 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("code");
 var mailDSN =document.getElementsByName("code");
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