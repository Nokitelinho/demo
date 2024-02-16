<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
var rangeMap = new Hashtable();
function screenSpecificEventRegister(){
	var frm=targetFormName;

	onHistory('dontdo');
	with(frm){
		
		evtHandler.addIDEvents("stockHolderCodeLov","displayStockHolderLov()",EVT_CLICK);
		evtHandler.addEvents("rangeFrom","validateFields(this,-1,'Range From',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("rangeTo","validateFields(this,-1,'Range To',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		evtHandler.addEvents("btnClose","location.href='home.jsp'",EVT_CLICK);
		evtHandler.addEvents("history","onHistory('dodo')",EVT_CLICK);
		evtHandler.addEvents("btnDisplay","onclickViewStock()",EVT_CLICK);
		evtHandler.addEvents("stockStatus","changeStatus(this)",EVT_CHANGE);
		evtHandler.addEvents("btnPrint","onClickPrint()",EVT_CLICK);
		evtHandler.addEvents("awb","autoTabOut()",EVT_KEYPRESS);
		//evtHandler.addEvents("rangeFrom","restrictInt(this,7)",EVT_KEYPRESS);
		//evtHandler.addEvents("rangeTo","restrictInt(this,7)",EVT_KEYPRESS);
		evtHandler.addEvents("btnAgtCSV","onClickAgtCSV()",EVT_CLICK);
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);	
		evtHandler.addEvents("rangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("rangeTo","validateRange(this)",EVT_FOCUS);		
		
	}
	applySortOnTable("listAwbStockHistoryTable",new Array("String","String","String","String","String","String","String","String","Number","Date"));
					
    //DivSetVisible(true);
    //Added by A-4803 for bug ICRD-14360
    onScreenLoad(targetFormName);
    
    setFocus();
	showPartnerAirlines();
	onChangeOfDocTyp();
    
  }
  //Added by A-4803 for bug ICRD-14360
  var errorflag = "";
function onScreenLoad(targetFormName){
	var status = targetFormName.elements.statusFlag.value;
	if(status == "Y"){
		enableField(targetFormName.elements.btnPrint);
  	}else{
		disableField(targetFormName.elements.btnPrint);
		//document.getElementById('history').checked=true;
		//onHistory('dodo');
  	}
}

 function autoTabOut(frm){ 
 var frm=targetFormName;
	restrictInt(frm.elements.awb,7);
	if(frm.elements.awb.value.length==7){
	frm.elements.btnDisplay.focus();
	}
  	
  }

var stockValue="";


  function changeStatus(obj){

  	targetFormName.elements.stockStatus.value=obj.value;
  	stockValue = obj.value;
  }

function setFocus(){
	var frm = targetFormName;
	//alert(frm.onList.value);
	if(frm.elements.onList.value == "Y"){
	frm.elements.awb.focus();
	}else{
	frm.elements.stockHolderCode.focus();
	}
}

function onClickClose(){
	window.close();
}

function onClickPrint()
{
	var frm = targetFormName;
	var stockStatus = '';
	if(targetFormName.elements.history.checked==true){
		stockStatus = frm.elements.stockStatus[1].value;
	}else{
		stockStatus = frm.elements.stockStatus[0].value;
	}
	

	generateReport(frm,'/stockcontrol.defaults.printstockrangehistory.do?stockStatus='+stockStatus);
}

function onClickList(){
	
	var frm=targetFormName;
	
	/**if(frm.startDate.value==""){
		//alert('Please enter the From Date');
	showDialog('<common:message bundle="listawbstockhistoryresources" key="stockcontrol.defaults.listawbstockhistory.pleaseenterthestartdate" scope="request"/>',1,self);
	frm.startDate.focus();
	return;
	}
	if(frm.endDate.value==""){
		//alert('Please enter the To Date');
	showDialog('<common:message bundle="listawbstockhistoryresources" key="stockcontrol.defaults.listawbstockhistory.pleaseentertheenddate" scope="request"/>',1,self);
	frm.endDate.focus();
	return;
	}*/
	if(frm.elements.rangeTo.value!=""){
		if(getLong(frm.elements.rangeFrom.value)>getLong(frm.elements.rangeTo.value)){
			//alert('Range To has to be greater than Range From');
			
			showDialog({	
						msg		:	'<common:message bundle="listawbstockhistoryresources" key="stockcontrol.defaults.listawbstockhistory.rangetohastobegreaterthanrangefrom" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:frm,
						onClose : function () {
								 frm.elements.rangeFrom.focus();
					}
				});
			return;
		}
	}
	
	//targetFormName.stockStatus.value = tempVal;
	//alert(targetFormName.stockStatus.value);
	
	
	//subFunction();							  
    frm.elements.onList.value='Y';
	var stockStatus = '';
	if(frm.elements.history.checked==true){
		stockStatus = frm.elements.stockStatus[1].value;
	}else{
		stockStatus = frm.elements.stockStatus[0].value;
	}
	targetFormName.elements.displayPage.value="1";
	targetFormName.elements.lastPageNum.value="0";
	//Added by A-5220 for ICRD-20959 starts
	targetFormName.elements.navigationMode.value='list';
	//Added by A-5220 for ICRD-20959 ends
	submitForm(frm,'stockcontrol.defaults.listawbstockhistory.do?stockStatus='+stockStatus);
}

function submitPage(lastPg,displayPg){
    var stockStatus = '';
	if(targetFormName.elements.history.checked==true){
		stockStatus =targetFormName.elements.stockStatus[1].value;
	}else{
		stockStatus =targetFormName.elements.stockStatus[0].value;
	}
	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	//Added by A-5220 for ICRD-20959 starts
	targetFormName.elements.navigationMode.value="navigation";
	//Added by A-5220 for ICRD-20959 ends
	submitForm(targetFormName, 'stockcontrol.defaults.listawbstockhistory.do?stockStatus='+stockStatus);

}
/**
Functions for alphanumeric
*/
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

function subFunction(){
	var frm = targetFormName;
	var stockStatus = eval(document.getElementsByName("stockStatus"));
	
	for(var i=0; i<stockStatus.length; i++){
	
		if(stockStatus[i].value != null && stockStatus[i].value.length >0){
			targetFormName.elements.stockStatus.value=stockStatus[i].value;
			break;
		}else{
			targetFormName.elements.stockStatus.value="";
		}
	}
	
	

}

function onClickClear(){
	var frm = targetFormName;
	//alert('Data Clear');
	frm.elements.onList.value="N";
	//alert(frm.onList.value);
	submitForm(frm,'stockcontrol.defaults.clearawbstockhistory.do');
}

function onHistory(obj){
	var frm = targetFormName;
	if(frm.elements.history.checked==true)
	{
		
		//document.getElementById('divid1').style.visibility = 'hidden'; 
		document.getElementById('divid2').style.display='none';
		document.getElementById('divid1').style.display='block';
		
		//document.getElementById('btnDisplay').disabled='true';
		
	}
	
	else if (frm.elements.history.checked==false) 
	{
		 	
		// 	document.getElementById('divid2').style.visibility = 'hidden'; 
		 	document.getElementById('divid2').style.display='block';
			document.getElementById('divid1').style.display='none';
		
		
	 }
	 
	if(obj == 'dodo'){
		targetFormName.elements.stockStatus.value="";
		var stockStatus = eval(document.getElementsByName("stockStatus"));
		//alert(stockStatus.length);
		//Commented for ICRD-92597 by A-5237
		/*for(var i=0; i<stockStatus.length; i++){
		    if(document.getElementById('history').checked==true){
			for (var j=0; j<stockStatus[i].options.length; j++){
             if (stockStatus[i].options[j].value=="R"){
  stockStatus[i].options[j].selected="true";
             }
			 }
		    }else{
			document.getElementsByName("stockStatus")[i].value="";
		}
	}*/
	}
	
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

function onclickViewStock(){
	var frm = targetFormName;
	//alert("display window");
	var awbnum=frm.elements.awb.value;
	if(awbnum==""||awbnum.length<7){
	//alert('Please enter valid awbnum');
	showDialog({	
						msg		:	'<common:message bundle="listawbstockhistoryresources" key="stockcontrol.defaults.listawbstockhistory.pleaseentervalidawbnum" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:frm,
						onClose : function () {
								frm.elements.awb.focus();
					}
				});
	return;
	}
			var clientHeight = document.body.clientHeight;
			var clientWidth = document.body.clientWidth;
			var _reqWidth=(clientWidth*50)/100;
			var _reqHeight = (clientHeight*35)/100;
	
 	//openPopUp('stockcontrol.defaults.screenloadviewawbstock.do',_reqWidth,_reqHeight);
 	  openPopUp("stockcontrol.defaults.screenloadviewawbstock.do?awb="+frm.elements.awb.value+"&awp="+frm.elements.awp.value,_reqWidth,_reqHeight);
 	}
function onClickAgtCSV()
{
	var frm = targetFormName;
	generateReport(frm, "/stockcontrol.defaults.printagentCSV.do");
}

function showPartnerAirlines(){	
	var partnerPrefix = targetFormName.elements.partnerPrefix.value;
	if(targetFormName.elements.partnerAirline.checked){
		jquery('select[name="awbPrefix"] option[value="'+partnerPrefix+'"]').remove();
		targetFormName.elements.awbPrefix.disabled=false;
	}else{
		targetFormName.elements.airlineName.value="";				
		jquery('select[name="awbPrefix"]').append("<option value='" + partnerPrefix + "'> " + partnerPrefix + "</option>");
		jquery('select[name="awbPrefix"]').val(partnerPrefix);			
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
	if(targetFormName.elements.docType.value==""){
		targetFormName.elements.rangeFrom.value="";
		targetFormName.elements.rangeTo.value="";
		disableField(targetFormName.elements.rangeFrom);	
		disableField(targetFormName.elements.rangeTo);		
	} else {
		enableField(targetFormName.elements.rangeFrom);	
		enableField(targetFormName.elements.rangeTo);		
	}
	if(targetFormName.elements.errorFlag.value == 'Y'){
	errorflag = "Y";
	}
	if(targetFormName.elements.docType.value=="INVOICE"){
		targetFormName.elements.partnerAirline.disabled=true;	
		targetFormName.elements.partnerAirline.checked=false;
		showPartnerAirlines();
	} else {
		targetFormName.elements.partnerAirline.disabled=false;		
		showPartnerAirlines();
	}
	if(errorflag != "Y"){
	findDocRange();
	}
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

