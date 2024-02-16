<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
		//onScreenloadSetHeight();
		evtHandler.addEvents("stockHolderCode","validateFields(this,-1,'Stock Holder Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("btList","submitStockHolderForm(targetFormName,'list')",EVT_CLICK);
		evtHandler.addEvents("btClear","submitStockHolderForm(targetFormName,'clear')",EVT_CLICK);
		if(targetFormName.elements.checkStockHolder){
			evtHandler.addEvents("checkStockHolder","singleSelect(this,'checkStockHolder')",EVT_CLICK);
		}
  
		evtHandler.addEvents("btCreate","submitStockHolderForm(targetFormName,'create')",EVT_CLICK);
		evtHandler.addEvents("btDetails","onClickDetails()",EVT_CLICK);
		evtHandler.addEvents("btCancel","submitStockHolderForm(targetFormName,'cancel')",EVT_CLICK);
		evtHandler.addEvents("btClose","location.href('home.jsp')",EVT_CLICK);
		evtHandler.addEvents("btMonitor","onclickMonitor()",EVT_CLICK);
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);
	}
	applySortOnTable("listStockHolderTable",new Array("None","None","String","String","String","String","String","String","String"));
	collapseAllRows();
//	DivSetVisible(true);
	setContextPath();
	onscreenload();
	onChangeOfDocTyp();
}

function onscreenload(){
	targetFormName.elements.airlineName.disabled=true;
}

function onScreenloadSetHeight(){
	var height = document.body.clientHeight;
	document.getElementById('pageDiv').style.height = ((height*85)/100)+'px';
	document.getElementById('div1').style.height = (((height*85)/100)-190)+'px';

}


function submitStockHolderForm(frm,strAction){


	if(strAction=="clear"){


		submitForm(frm,"stockcontrol.defaults.clearscreen.do");
	}else if(strAction=="list"){
		if(validFilter()){
			frm.elements.displayPage.value="1";
	        frm.elements.lastPageNum.value="0";
			submitForm(frm,"stockcontrol.defaults.liststockholder.do?navigationMode=FILTER");
		}
	}else if(strAction=="cancel"){
		if(validateSelectedCheckBoxes(frm,'checkStockHolder',1,1)){
			showDialog({	
				msg		:	"<common:message bundle="liststockholderresources" key="stockcontrol.defaults.liststockholder.doyouwanttocancelthestockholder" scope="request"/>",
				type	:	4, 
				parentWindow: self,
				parentForm: targetFormName,
				dialogId:'id_1',
				onClose: function () {
							screenConfirmDialog(targetFormName,'id_1');
							screenNonConfirmDialog(targetFormName,'id_1');
						}
			});
			
		}
	}else if(strAction=="create"){
		frm.elements.fromStockHolderList.value="StockHolderList";
		submitForm(frm,"stockcontrol.defaults.fiterdetailsstockholder.do");
	}




}


function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'stockcontrol.defaults.cancelstockholder.do');
		}
	}
}

function screenNonConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'N') {
		if(dialogId == 'id_1'){

		}
	}
}

function validFilter(){
	var frm = targetFormName;
	if(frm.elements.stockHolderType.value == ""){
		//alert('Please specify a valid stock holder type');
		showDialog({	
			msg		:	"<common:message bundle="liststockholderresources" key="stockcontrol.defaults.liststockholder.pleasespecifyavalidstockholdertype" scope="request"/>",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		frm.elements.stockHolderType.focus();
		return false;
	}
	/*if(frm.stockHolderCode.value == ""){
		//alert('Please specify a valid stock holder code');
		showDialog('<common:message bundle="liststockholderresources" key="stockcontrol.defaults.liststockholder.pleasespecifyavalidstockholdercode" scope="request"/>',1,self);
		frm.stockHolderCode.focus();
		return false;
	}
	if(frm.docType.value == ""){
		//alert('Please specify a valid document type');
		showDialog('<common:message bundle="liststockholderresources" key="stockcontrol.defaults.liststockholder.pleasespecifyavaliddocumenttype" scope="request"/>',1,self);
		frm.docType.focus();
		return false;
	}*/
	return true;
}

function onClickDetails(){
   var isChecked = 'N';
   var code;
   var checkValue;
   var frm = targetFormName;
   if(frm.elements.checkStockHolder){
	   if(!frm.elements.checkStockHolder.length){
	       if(frm.elements.checkStockHolder.checked==false){
			  	//alert('Please select a row.');
				showDialog({	
					msg		:	"<common:message bundle="liststockholderresources" key="stockcontrol.defaults.liststockholder.pleaseselectarow" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
			  	return;
	       	}else{
	       		checkValue = (frm.elements.checkStockHolder.value).split("-");
	     		code = checkValue[0];
	           	isChecked = 'Y';
	    	}
	  	}else{
	    	for(var i=0;i<frm.elements.checkStockHolder.length;i++){
	     		if(frm.elements.checkStockHolder[i].checked==true ){
	     	  		if( i>0&& isChecked == 'Y'){
	     	   			//alert("Please select only one row");
						showDialog({	
							msg		:	"<common:message bundle="liststockholderresources" key="stockcontrol.defaults.liststockholder.pleaseselectarow" scope="request"/>",
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
						});
	     	   			return;
					}
					isChecked = 'Y';
					checkValue = (frm.elements.checkStockHolder[i].value).split("-");
					code = checkValue[0];
		      	}
	    	}
	   }
	}
  	if(isChecked == 'Y'){
		frm.elements.fromStockHolderList.value="StockHolderList";
	   	frm.action="stockcontrol.defaults.viewstockholder.do?stockHolderCode="+code;
		//   alert(frm.action);
	}else{
	   //alert("Please select 1 row(s)");
		showDialog({	
			msg		:	"<common:message bundle="liststockholderresources" key="stockcontrol.defaults.liststockholder.pleaseselectarow" scope="request"/>",
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
		});
		return;
	}
	submitForm(frm,frm.action);
}

function displayLov(strAction){
	var frm = targetFormName;
	var stockHolderCode='stockHolderCode';
	var stockHolderType='stockHolderType';
	var val=frm.elements.stockHolderCode.value;
	var typeVal=frm.elements.stockHolderType.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically starts
		/*var clientHeight = document.body.clientHeight;
		var clientWidth = document.body.clientWidth;
		var _reqWidth=(clientWidth*45)/100;
		var _reqHeight = (clientHeight*50)/100;*/
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically ends
	openPopUp(strUrl,650,500);
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 *
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 */

 function singleSelect(checkVal){
 	var frm = targetFormName;
 	for(var i=0;i<frm.elements.length;i++){
 		if(frm.elements[i].type =='checkbox' ){
 			if(frm.elements[i].checked == true &&
				frm.elements[i].name=="checkStockHolder"){
 				if(frm.elements[i].value != checkVal.value){
 					frm.elements[i].checked = false;
 				}
 			}
 		}
 	}
 }
function setContextPath(){

	var temp = '<%=request.getContextPath()%>';
	contextPathForTreeTable(temp);
}



 function submitPage(lastPg,displayPg)
		{
		 //alert("DP-------"+ displayPg);
		 //alert("LP---->"+ lastPg);
		var frm = targetFormName;
		  frm.elements.lastPageNum.value=lastPg;
		  frm.elements.displayPage.value=displayPg;
		//  submitForm(targetFormName,appPath + "/flight.operation.findoperationalflights.do");
		submitForm(frm,"stockcontrol.defaults.liststockholder.do?navigationMode=NAVIGATION");
		}

		function validateIsChecked(check){
		 	var cnt=0;
			var val=document.getElementsByName(check);
			for(var i=0;i<val.length;i++){
				if(val[i].checked){
					cnt++;
				}
			}
			if(cnt==0){
				showDialog({	
					msg		:	"<common:message bundle="liststockholderresources" key="stockcontrol.defaults.liststockholder.pleaseselectarow" scope="request"/>",
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
				});
				return false;
			}
			return true;
}






function onclickMonitor(){

    var frmList = "Y";
    var selected  ;
    if(targetFormName.elements.checkStockHolder){
    	if(!targetFormName.elements.checkStockHolder.length){
    			selected = targetFormName.elements.checkStockHolder.value;
    	}else{
		 for(var i=0;i<targetFormName.elements.checkStockHolder.length;i++){
			 if(targetFormName.elements.checkStockHolder[i].checked==true){
			 	selected = targetFormName.elements.checkStockHolder[i].value;
			 }
		 }
	}
	//alert(selected);
	var displayPage = targetFormName.elements.displayPage.value;
	var checkList = targetFormName.elements.checkList.value;
	var listFlag = targetFormName.elements.listFlag.value;
	var disableButn = targetFormName.elements.disableButn.value;

	if(validateIsChecked('checkStockHolder')){
   	  strUrl = "stockcontrol.defaults.screenloadmonitorstock.do?mode="+frmList+"&selected="+selected+"&fromMonitorStock=Y"+"&displayPage="+displayPage+"&listFlagFromListScreen="+listFlag+"&disableButnFromListScreen="+disableButn;
	  targetFormName.action =strUrl;
	  targetFormName.submit();
          disablePage();
          }
      }
 }
//Modified by A-5131 for ICRD-24891
 function showPartnerAirlines(){	
	if(targetFormName.elements.partnerAirline.checked){
		targetFormName.elements.awbPrefix.disabled=false;
		targetFormName.elements.airlineName.disabled=false;
	}else{
		targetFormName.elements.awbPrefix.value='';
		targetFormName.elements.airlineName.value='';
		targetFormName.elements.airlineName.disabled=true;
		targetFormName.elements.awbPrefix.disabled=true;
	}
}

function populateAirlineName(){		
	if(targetFormName.elements.awbPrefix.value!=""){
		var splits=targetFormName.elements.awbPrefix.value.split("-");
		targetFormName.elements.airlineName.value=splits[1];
	}
	//Added by A-5131 for ICRD-24891
	else{
		targetFormName.elements.airlineName.value='';
		targetFormName.elements.partnerAirline.checked=false;
		targetFormName.elements.airlineName.disabled=true;
		targetFormName.elements.awbPrefix.disabled=true;
	}
}
function onChangeOfDocTyp(){
	if(targetFormName.elements.docType.value=="INVOICE"){
		targetFormName.elements.partnerAirline.disabled=true;	
		targetFormName.elements.partnerAirline.checked=false;
		showPartnerAirlines();
	} else {
		targetFormName.elements.partnerAirline.disabled=false;		
		showPartnerAirlines();
	}
}

