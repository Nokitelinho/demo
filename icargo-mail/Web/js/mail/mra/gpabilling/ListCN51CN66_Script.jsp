<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

   function screenSpecificEventRegister(){
   	var frm = targetFormName;

   	setupPanes('container1','tab.Gen');

	displayTabPane('container1','tab.Gen');
	with(frm){
	evtHandler.addEvents("btnSurcharge","viewSurcharge()",EVT_CLICK);
	evtHandler.addEvents("btnViewAccount","onViewAccount()",EVT_CLICK);

	evtHandler.addEvents("btnList","onList()",EVT_CLICK);

	evtHandler.addEvents("btn51Print","onPrint()",EVT_CLICK);
	evtHandler.addEvents("btn51Print1","onPrint1()",EVT_CLICK);

	evtHandler.addEvents("btnInvPrint","onPrintInvReport()",EVT_CLICK);

	evtHandler.addEvents("btn66Print","onPrintCN66()",EVT_CLICK);
	evtHandler.addEvents("btn66Print1","onPrintCN661()",EVT_CLICK);

	evtHandler.addEvents("countrylov","displayLOV('showCountry.do','N','Y','showCountry.do',targetFormName.elements.countryFilter.value,'CountryFilter','1','countryFilter','',0)",EVT_CLICK);

	evtHandler.addEvents("btnClear","onClear()",EVT_CLICK);
	evtHandler.addEvents("btnMca","onMca()",EVT_CLICK);


	evtHandler.addEvents("btnTabList","onTabList()",EVT_CLICK);

	evtHandler.addEvents("btnTabClear","onTabClear()",EVT_CLICK);

	evtHandler.addEvents("btnSave","onSave()",EVT_CLICK);

	evtHandler.addEvents("btnClose","onClose()",EVT_CLICK);
	evtHandler.addEvents("stationlov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.elements.origin.value,'Origin','1','origin','','0')",EVT_CLICK);
	evtHandler.addEvents("stationCodelov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.elements.destination.value,'Destination','1','destination','','0')",EVT_CLICK);
	evtHandler.addEvents("invoicenumberlov","invoiceLOV()",EVT_CLICK);
	evtHandler.addEvents("gpacodelov","displayLOV('mailtracking.defaults.palov.list.do','N','Y','mailtracking.defaults.palov.list.do',targetFormName.gpaCode.value,'GPA Code','1','gpaCode','',0)",EVT_CLICK);
	evtHandler.addEvents("btn51PrintTK","onPrintTKCN51()",EVT_CLICK);
	evtHandler.addEvents("btnInvPrintTK","onPrintTKInvReport()",EVT_CLICK);

   //FOR SQ SPECIFIC 
evtHandler.addEvents("btn51PrintSQ","onPrintSQCN51()",EVT_CLICK);
evtHandler.addEvents("btn66PrintSQ","onPrintSQCN66()",EVT_CLICK);
   evtHandler.addEvents("btnInvPrintSQ","onPrintSQInvReport()",EVT_CLICK);
   
    evtHandler.addEvents("btnWithdraw","withdrawMails()",EVT_CLICK);//Added by A-6991 for ICRD-211662
	evtHandler.addEvents("btnFinalizeInv","finalizeProformaInvoice()",EVT_CLICK)
	//Added by A-7794 as part of ICRD-234354
	evtHandler.addEvents("btn51PrintKE","onPrintKECN51()",EVT_CLICK);
	evtHandler.addEvents("btn66PrintKE","onPrintKECN66()",EVT_CLICK);

	}
	applySortOnTable("CN51table",new Array("String","String","String","Number","Number","Number","Number","Number","Number","String"));
	applySortOnTable("listcn51cn66",new Array("None","String","Date","Number","Number","Number","Number","String","String","String","String","String","Number","Number","Number","Number","Number","Number","Number"));
	callOnScreenLoad();
    }
    var currentTab="pane1";
   function viewSurcharge(){
	if(currentTab!="pane2"){
		showDialog({msg:"Please select a record from CN66 tab",type:1,parentWindow:self});
	}else{
		if(validateSelectedCheckBoxes(targetFormName,'selectContainer',1,1) ){
		var surchg = document.getElementsByName("surChg");
		var chkboxes = document.getElementsByName('selectContainer');
		var selIndex ='';
		if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){

			if(chkboxes[index].checked == true){
			if(surchg[index].value==0.0){
			//showDialog('Surcharge does not exist for the selected mail',1,self);
			showDialog({msg:"Surcharge does not exist for the selected mail",type:1,parentWindow:self});
			return;
			}
			else{
			selIndex=chkboxes[index].value;
			}
			}
		}
		}
	openPopUp("mailtracking.mra.gpabilling.cn51cn66surcharge.do?selectedRow="+selIndex,430,250);
		}
	}
	}

//Added by A-6991 for ICRD-211662 Starts
   function withdrawMails(){
   var frm = targetFormName;
   showDialog({
				msg		:'<common:message bundle="listcn51cn66" key="mailtracking.mra.gpabilling.listcn51cn66.withdarw.msg.confirm" scope="request"/>',
				type	:	4,
				parentWindow:self,
				parentForm:targetFormName,
                dialogId:'id_1',
            onClose:function(result){
			screenConfirmDialog(targetFormName,'id_1');
		    screenNonConfirmDialog(targetFormName,'id_1');
			}
 	    });
	function screenConfirmDialog(frm, action) {
	if(frm.elements.currentDialogOption.value == 'Y') {
		if (action == "id_1") {

	if(currentTab!="pane2"){
		showDialog({msg:"Please select a record from CN66 tab",type:1,parentWindow:self});
	}else{
		var chkboxes = document.getElementsByName('selectContainer');
		var selIndex ='';
		var len=0;
		if(chkboxes != null){
		for(var index=0;index<chkboxes.length;index++){

			if(chkboxes[index].checked)	{
					len=len+1;
					selIndex=selIndex+index+",";
				}
			/*if(chkboxes[index].checked == true){
			selIndex = selIndex+"," +index;
			}*/
			str=selIndex.substr(0,(selIndex.length-1));
			}

          if(selIndex.length ==0){
			showDialog({msg:"Please select a record to withdraw",type:1,parentWindow:self});
			return;
			}
		submitForm(frm,'mailtracking.mra.gpabilling.cn51cn66withdrawmail.do?selectedRow='+str);
		}


	}
	}
	}
	}
	function screenNonConfirmDialog(frm, action) {
	if(frm.elements.currentDialogOption.value == 'N') {
		if (action == "id_1") {
		}
	}
}
	}

function finalizeProformaInvoice(){
   frm = targetFormName;

		submitForm(frm,'mailtracking.mra.gpabilling.cn51cn66finalizeinvoice.do?fileName='+targetFormName.elements.fileName.value);

	}
//Added by A-6991 for ICRD-211662 Ends


function updateCurrentPane(pane){
currentTab=pane;
    }
	function invoiceLOV(){
		var height = document.body.clientHeight;
		var _reqHeight = (height*49)/100;
		displayLOV('showInvoiceLOV.do','N','Y','showInvoiceLOV.do',targetFormName.elements.invoiceNumber.value,'InvoiceNumber','1','invoiceNumber','gpaCode',0,_reqHeight);
	}
    function callOnScreenLoad() {
         if(targetFormName.elements.invoiceNumber.readOnly==false || targetFormName.elements.invoiceNumber.disabled==false){
          	    			targetFormName.elements.invoiceNumber.focus();
		}
if(targetFormName.accEntryFlag.value == "N"){
			disableField(targetFormName.btnViewAccount);
		}else{
			enableField(targetFormName.btnViewAccount);
		}

    if(targetFormName.btnStatus.value=="N"){
		if(targetFormName.btn51Print!=null &&
			targetFormName.btn66Print!=null && targetFormName.btnInvPrint!=null){
     		//targetFormName.btnSave.disabled =true;
			disableField(targetFormName.btnTabList);
			disableField(targetFormName.btn51Print);
			disableField(targetFormName.btn66Print);
			disableField(targetFormName.btnInvPrint);
			disableField(targetFormName.btnViewAccount);
			disableField(targetFormName.btnSurcharge);
		}
	}
	if(targetFormName.btnStatus.value =="N"){
			disableField(targetFormName.btnTabClear);
	}
	//Added by A-6991 for ICRD-211662 Starts
	 if(targetFormName.elements.invoiceStatus.value != 'P' && targetFormName.elements.invoiceStatus.value != 'N'){
		  targetFormName.elements.btnFinalizeInv.disabled=true;

		  targetFormName.elements.btnWithdraw.disabled=true;
	 }else{
		  targetFormName.elements.btnFinalizeInv.disabled=false;
		  targetFormName.elements.btnWithdraw.disabled=false;
	 }
	//Added by A-6991 for ICRD-211662 Ends
    }

    function onPrintCN66(){
             frm = targetFormName;
             generateReport(frm,'/mtk.mra.gpabilling.generatecn66repPrint.do');
       }
	   function onPrintTKCN51(){
			frm = targetFormName;
             generateReport(frm,'/mtk.mra.gpabilling.generatecn51tkrepPrint.do');
       }
	    function onPrintCN661(){
             frm = targetFormName;
             generateReport(frm,'/mtk.mra.gpabilling.generatecn666erepPrint.do');
       }
       function onPrint(){
         frm = targetFormName;
         //generateReport(frm,'/mtk.mra.gpabilling.generatecn66repPrint.do');
         generateReport(frm,'/mailtracking.mra.gpabilling.generatecn51report.do');
		// generateReport(frm,'/mailtracking.mra.gpabilling.generateinvoicereport.do');
       }
	    function onPrint1(){
         frm = targetFormName;
         //generateReport(frm,'/mtk.mra.gpabilling.generatecn66repPrint.do');
         generateReport(frm,'/mailtracking.mra.gpabilling.generatecn516ereport.do');
        // generateReport(frm,'/mailtracking.mra.gpabilling.generateinvoicereport.do');

       }

       function onPrintInvReport(){
             frm = targetFormName;
            generateReport(frm,'/mailtracking.mra.gpabilling.generateinvoicereport.do');
		}
		function onPrintTKInvReport(){
             frm = targetFormName;
             generateReport(frm,'/mtk.mra.gpabilling.generatecnInvoicetkrPrint.do');
		        }
// FOR SQ_SPECIFIC
		function onPrintSQInvReport()
        { 
         frm = targetFormName;
         generateReport(frm,'/mtk.mra.gpabilling.generatecnInvoicesqPrint.do');
        }
		 
	    function onPrintSQCN66(){
             frm = targetFormName;
             generateReport(frm,'/mtk.mra.gpabilling.generatecnGPAInvoicesqPrint.do');
        }
function onPrintSQCN51(){
				frm = targetFormName;
             generateReport(frm,'/mtk.mra.gpabilling.generatecn51sqrepPrint.do');

       }
   function onList() {
        frm = targetFormName;

       // if(frm.invoiceNumber.value==""||frm.gpaCode.value=="" ){
	//		showDialog("Please enter mandatory fields", 1, self);
	//		frm.invoiceNumber.focus();
	//}
	//if(frm.invoiceNumber.value!=""&&frm.gpaCode.value!=""){
        submitForm(frm,'mailtracking.mra.gpabilling.onlistcn51cn66.do');
       // }
    }



    function onClear(){
   	frm = targetFormName;
	submitForm(frm,'mailtracking.mra.gpabilling.onClearcn51cn66.do');
    }
    //Added by a-4810 for icrd-13639
    function onMca(){
    frm = targetFormName;
    var ccano = "";
    var chkbox =document.getElementsByName("selectContainer");
     var ccarefno =document.getElementsByName("ccaRefNumber");
       if(validateSelectedCheckBoxes(targetFormName,'selectContainer',1,1)){
		  for(var i=0; i<chkbox.length;i++){
		         if(chkbox[i].checked) {
		     	      ccano = ccarefno[i].value;
		     	   if(ccarefno[i].value == "")
		     	      {
                        showDialog({msg:"<common:message bundle='listcn51cn66' key='mailtracking.mra.gpabilling.cn51cn66.msg.err.nomcanumber' />",type:1,parentWindow:self,parentformname:targetFormName,DialogId:'id_1'});
								  return;
					  }
		 }
	}
}
	else{
	return;
	}

		submitForm(frm,"mailtracking.mra.defaults.maintaincca.updateccadetails.do?ccaNum="+ccano+"&fromScreen="+"CN51CN66");
    }

    function onTabList(){
     	frm = targetFormName;
     	if(frm.elements.category.value ==""&&
     	     frm.elements.origin.value ==""&&
     	       frm.elements.destination.value ==""&&
     	           frm.elements.dsnNumber.value ==""){
     	 submitForm(frm,'mailtracking.mra.gpabilling.onlistcn51cn66.do');

	   }
	 else{
	     submitForm(frm,'mailtracking.mra.gpabilling.onCN66TabListcn51cn66.do');
        }

    }
function submitCN66Page(lastPg,displayPg){

var frm = targetFormName;
	targetFormName.elements.cn66LastPageNumber.value=lastPg;
	targetFormName.elements.displayPageCN66.value=displayPg;
	targetFormName.elements.checkButton.value="NEXT";
	submitForm(targetFormName,'mailtracking.mra.gpabilling.onlistcn51cn66.do');
}

function submitCN51Page(lastPg,displayPg){

var frm = targetFormName;
	frm.elements.lastPageNum.value=lastPg;
	frm.elements.displayPage.value=displayPg;
	submitForm(targetFormName,'mailtracking.mra.gpabilling.onlistcn51cn66.do');
}
    function onTabClear(){
        frm = targetFormName;
    	submitForm(frm,'mailtracking.mra.gpabilling.onCN66TabClearcn51cn66.do');
    }

    function onSave(){
        frm = targetFormName;
   	submitForm(frm,'mailtracking.mra.gpabilling.onSavecn51cn66.do');
    }

    function onClose(){
        frm = targetFormName;
	submitForm(frm,'mailtracking.mra.gpabilling.onClosecn51cn66.do');

    }

    function onViewAccount(){
		frm = targetFormName;
		var chkboxes = document.getElementsByName('selectContainer');
		var selIndex ='';
		var len=0;
		var comcount = 0;
		if(chkboxes != null){
				for(var index=0;index<chkboxes.length;index++){

					if(chkboxes[index].checked)	{
							len=len+1;
							selIndex=selIndex+index+",";
						}
					str=selIndex.substr(0,(selIndex.length-1));
					}
					 comcount = (selIndex.match(/,/g) || []).length;
				  if(comcount>1){
					showDialog({msg:"Please select single row",type:1,parentWindow:self});
					return;
					}
				submitForm(frm,'mailtracking.mra.gpabilling.listcn51cn66.viewaccount.do?selectedRow='+str);
			}
    }
    function onPrintKECN51(){
			frm = targetFormName;
             generateReport(frm,'/mail.mra.gpabilling.generatekecn51report.do');
       }
	function onPrintKECN66(){
			frm = targetFormName;
             generateReport(frm,'/mail.mra.gpabilling.generatekecn66report.do');
       }
