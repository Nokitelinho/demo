<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
var rangeMap = new Hashtable();
function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
                evtHandler.addIDEvents("stockHolderCodeLov","displayStockHolderLov()",EVT_CLICK);
		evtHandler.addEvents("rangeFrom","validateFields(targetFormName.elements.rangeFrom,-1,'Range From',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("rangeTo","validateFields(targetFormName.elements.rangeTo,-1,'Range To',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("remarks","validateRemarks(this)",EVT_BLUR);
		evtHandler.addEvents("btblacklist","onclickBlacklist()",EVT_CLICK);
		evtHandler.addEvents("btvoid","onclickVoid()",EVT_CLICK);
		evtHandler.addEvents("btclose","onClickClose()",EVT_CLICK);
		evtHandler.addEvents("btclose","shiftFocus()",EVT_BLUR);
		evtHandler.addEvents("btnClear","clearLOVForm()",EVT_CLICK);
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);
		evtHandler.addEvents("rangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("rangeTo","validateRange(this)",EVT_FOCUS);
	}
	
	/*
		Enable partner airline combo if checked on screenload
	*/
	showPartnerAirlines();
	
	setFocus();
	// DivSetVisible(true);
}

function onClickClose(){
	var frm = targetFormName;
	//alert("onClickClose");
	//alert(frm.fromScreen.value);
	//alert(frm.docType.value);
	if(targetFormName.elements.fromScreen.value == 'confirmStock'){
	submitForm(targetFormName,"stockcontrol.defaults.listconfirmstock.do?btn=C");
	}
	else{
	submitFormWithUnsaveCheck('home.jsp');
}
}
function shiftFocus(){
	if(!event.shiftKey){
	if(!(targetFormName.elements.docType.disabled)){
		targetFormName.elements.docType.focus();
	}
		
}
}

function validateRemarks(remarks){
	return validateMaxLength(remarks,250);
}

function setFocus(){
	var frm = targetFormName;
	if(!(targetFormName.elements.docType.disabled)){
		targetFormName.elements.docType.focus();
	}
}

function onclickBlacklist(){
	var frm = targetFormName;
	var rangeFrom=document.getElementsByName('rangeFrom');
	var rangeTo=document.getElementsByName('rangeTo');
	var stockHolderCode=document.getElementsByName('stockHolderCode');
	if(isValidEntry()){
	   if(frm.elements.rangeFrom.value!=""&&frm.elements.rangeTo.value!=""&&frm.elements.stockHolderCode.value!=""){		
		
		showDialog({	
					msg		:	"<common:message bundle="blackliststockresources" key="stockcontrol.defaults.blackliststock.doyouwanttoblacklistthestockUsingRange" scope="request"/>",
					type	:	4, 
					parentWindow:self,
					parentForm:targetFormName,
					dialogId:'id_1',
					onClose: function () {
								screenConfirmDialog(frm,'id_1');
		                        screenNonConfirmDialog(frm,'id_1');
							}
				});
	   }else{
		
		showDialog({	
					msg		:	"<common:message bundle="blackliststockresources" key="stockcontrol.defaults.blackliststock.doyouwanttoblacklistthestock" scope="request"/>",
					type	:	4, 
					parentWindow:self,
					parentForm:targetFormName,
					dialogId:'id_1',
					onClose: function () {
								screenConfirmDialog(frm,'id_1');
		                        screenNonConfirmDialog(frm,'id_1');
							}
				});
	   }

		/*
	    if(confirm('Do you want to blacklist the stock ?')){
		submitForm(frm,"stockcontrol.defaults.blackliststock.do");
		}*/
	}
}

function onclickVoid(){
	submitForm(targetFormName, "stockcontrol.defaults.void.do");
}
function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'stockcontrol.defaults.blackliststock.do');
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

function isValidEntry(){
	var frm = targetFormName;
	if(frm.elements.docType.value==""){
		//alert('Select document type ');		
		showDialog({	
						msg		:	"<common:message bundle="blackliststockresources" key="stockcontrol.defaults.blackliststock.selectthedocumenttype" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						 onClose: function () {					  
								frm.elements.docType.focus();
							}
					});  
		
		return;
	}
	var rangeFrom=document.getElementsByName('rangeFrom');
	var rangeTo=document.getElementsByName('rangeTo');
	var stockHolderCode=document.getElementsByName('stockHolderCode');
		
	if(stockHolderCode){
			if(stockHolderCode.length){
				for(var i=0;i<stockHolderCode.length;i++){
					if((stockHolderCode[i].value=="")&&(rangeFrom[i].value=="")&&(rangeTo[i].value=="")){
						//alert('Specify the stock range details');						
						showDialog({	
						msg		:	"<common:message bundle="blackliststockresources" key="stockcontrol.defaults.plsenterValues" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose: function () {					  
								if(stockHolderCode[i].value==""){
							frm.elements.stockHolderCode.focus();
						   }
							}
					    }); 
						
						return false;
					}
				}
			}else{
				if((stockHolderCode.value=="")&&(rangeFrom.value=="")&&(rangeTo.value=="")){
					//alert('Specify the stock range details');					
					showDialog({	
						msg		:	"<common:message bundle="blackliststockresources" key="stockcontrol.defaults.plsenterValues" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose: function () {					  
								frm.elements.stockHolderCode.focus();
							}
					    }); 
					
					return false;
				}
			}
	}
	
	if(getLong(frm.elements.rangeTo.value)<
		getLong(frm.elements.rangeFrom.value)){
			//alert('Range To has to be greater than Range From');			
			showDialog({	
						msg		:	"<common:message bundle="blackliststockresources" key="stockcontrol.defaults.blackliststock.rangetohastobegreaterthanrangefrom" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						onClose: function () {					  
								frm.elements.rangeFrom.focus();
							}
					    }); 
			
			return;
	}


	function getLong(range){
		var  base=1;
		var sNumber=0;
		for(var i=range.length-1;i>=0;i--){
			sNumber+=base*parseInt(calculateBase(range.substring(i,i+1)));
			var j=parseInt(asciiOf(range.substring(i,i+1)));
			if (j>57) base*=26;
			else base*=10;
		}
		return sNumber;
	}
	function calculateBase(xChar){
		var charAscii = parseInt(asciiOf(xChar));
		var base=0;
		if(charAscii>57){
			base= charAscii-65;
		}else{
			base=parseInt(xChar);
		}
		return base;
	}
	function asciiOf(yChar){
		var validString = "abcdefghijklmnopqrstuvwxyz";
		var ascii = '';
		if (validString.indexOf(yChar) != "-1") {
			for(var i= 96;i<123;i++){
				var s= String.fromCharCode(i);
				if(s == yChar){
					ascii = i;
					break;
				}
			}
		}
		var validString1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if (validString1.indexOf(yChar) != "-1") {
			for(var i= 65;i<92;i++){
				var s1= String.fromCharCode(i);
				if(s1 == yChar){
					ascii = i;
					break;
				}
			}
		}
		var validString2 = "0123456789";
		if (validString2.indexOf(yChar) != "-1") {
			for(var i= 48;i<58;i++){
				var s3= String.fromCharCode(i);
				if(s3 == yChar){
					ascii = i;
					break;
				}
			}
		}
		return ascii;
	}
	return true;
}

function displayStockHolderLov(){

	var strAction="stockcontrol.defaults.screenloadstockholderlov.do";
	var stockHolderCode='stockHolderCode';
	var val=targetFormName.elements.stockHolderCode.value;
	var stockHolderType='';
	var typeVal='';
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically starts
		var clientHeight = document.body.clientHeight;
		var clientWidth = document.body.clientWidth;
		var _reqWidth=(clientWidth*45)/100;
		var _reqHeight = (clientHeight*50)/100;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically ends
	openPopUp(strUrl,_reqWidth,_reqHeight);
}

function showPartnerAirlines(){	
	if(targetFormName.elements.partnerAirline.checked){
		//enableField(targetFormName.awbPrefix);
		targetFormName.elements.awbPrefix.disabled=false;
	}else{
		//targetFormName.awbPrefix.value="";
		//targetFormName.airlineName.value="";		
		//disableField(targetFormName.awbPrefix);
		targetFormName.elements.awbPrefix.value="";
		targetFormName.elements.airlineName.value="";		
		targetFormName.elements.awbPrefix.disabled=true;
	}
}

function populateAirlineName(){		
	if(targetFormName.elements.awbPrefix.value!=""){
		var splits=targetFormName.elements.awbPrefix.value.split("-");
		targetFormName.elements.airlineName.value=splits[1];
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
	findDocRange();
}
function findDocRange(){
__extraFn="stateChange";

var strAction='stockcontrol.defaults.documentrange.do?docTyp='+targetFormName.elements.docType.value;
var oldOne=null;
oldOne = rangeMap.get(targetFormName.elements.docType.value);
	if(!oldOne){	
		asyncSubmit(targetFormName,strAction,__extraFn,null);
	} else {	
		targetFormName.elements.documentRange.value=oldOne;
	}
}
function stateChange(tableinfo) {
targetFormName.elements.documentRange.value=tableinfo.document.getElementById('documentRange').innerHTML;
rangeMap.put(targetFormName.elements.docType.value,targetFormName.elements.documentRange.value);
}
function validateRange(object){	
			object.maxLength=targetFormName.elements.documentRange.value;
}