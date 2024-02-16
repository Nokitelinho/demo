<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
var rangeMap = new Hashtable();
function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){
        //disableField(targetFormName.btnRevoke);               //  Added by A-5200 for the BUG-ID-24902
		evtHandler.addEvents("rangeFrom","validateFields(this,-1,'Range From',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("rangeTo","validateFields(this,-1,'Range To',0,true,true)",EVT_BLUR);
	evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		if(targetFormName.elements.blacklistCheck){
			evtHandler.addEvents("blacklistCheck","singleSelect(this)",EVT_CLICK);
		}
 
		evtHandler.addEvents("btnRevoke","onClickRevoke('stockcontrol.defaults.screenloadrevokeblacklistedstock.do')",EVT_CLICK);
		evtHandler.addEvents("btnClose","closeScreen()",EVT_CLICK);
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);
		evtHandler.addEvents("rangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("rangeTo","validateRange(this)",EVT_FOCUS);
	}
	applySortOnTable("blklistedStockTable",new Array("None","String","String","String","String","String","Date","Number","Number","String"));
	 
	
	/*
		Enable partner airline combo if checked on screenload
	*/
	showPartnerAirlines();
	
	setFocus();
	onChangeOfDocTyp();
					
//	DivSetVisible(true);
}

function closeScreen(){
location.href = appPath + "/home.jsp";

}
function setFocus(){
	var frm = targetFormName;
	frm.elements.docType.focus();
}

/**Functions for alphanumeric
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

function validateIsChecked(check){
 	var cnt=0;
	var val=document.getElementsByName(check);
	for(var i=0;i<val.length;i++){
		if(val[i].checked){
			cnt++;
		}
	}
	if(cnt==0){
		//alert('Please select a row!');		
		
		showDialog({	
				msg		:	'<common:message bundle="listblacklistedstockresources" key="stockcontrol.defaults.listblacklistedstock.pleaseselectarow" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
			});
		return false;
	}
	return true;
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

function displayLov(strAction){
	var frm = targetFormName;
	var stockHolderCode='stockHolderCode';
	var val=frm.elements.stockHolderCode.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode;
	openPopUp(strUrl,540,440);
}

function onClickClose(){
	window.close();
}

function submitList(strLastPageNum,strDisplayPage){
	var frm = targetFormName;
	frm.elements.lastPageNumber.value= strLastPageNum;
	frm.elements.displayPage.value = strDisplayPage;
	submitForm(frm,"stockcontrol.defaults.listblacklistedstock.do");
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 *
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 */
function singleSelect(checkVal){
enableField(targetFormName.elements.btnRevoke);
//targetFormName.btnRevoke.disabled = false;      //  Added by A-5200 for the BUG-ID-24902
	var frm = targetFormName;
	for(var i=0;i<frm.elements.length;i++){
		if(frm.elements[i].type =='checkbox'){
			if(frm.elements[i].checked == true){
				if(frm.elements[i].value != checkVal.value){
					frm.elements[i].checked = false;
				}
			}
		}
	}
}



function onClickList(){

	var frm=targetFormName;
	frm.elements.listButton.value="Y";
	if(frm.elements.docType.value==""){
		//alert('Select document type');
		showDialog({	
				msg		:	'<common:message bundle="listblacklistedstockresources" key="stockcontrol.defaults.listblacklistedstock.selectdocumenttype" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		frm.elements.docType.focus();
		return;
	}
	if(frm.elements.subType.value==""){
		//alert('Select document sub type.');
		showDialog({	
				msg		:	'<common:message bundle="listblacklistedstockresources" key="stockcontrol.defaults.listblacklistedstock.selectdocumentsubtype" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		frm.elements.subType.focus();
		return;
	}
	/* if(frm.elements.fromDate.value==""){
		//alert('Please enter the From Date');
		showDialog({	
				msg		:	'<common:message bundle="listblacklistedstockresources" key="stockcontrol.defaults.listblacklistedstock.pleaseenterthefromdate" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		frm.elements.fromDate.focus();
		return;
	}
	if(frm.elements.toDate.value==""){
		//alert('Please enter the To Date');
		showDialog({	
				msg		:	'<common:message bundle="listblacklistedstockresources" key="stockcontrol.defaults.listblacklistedstock.pleaseenterthetodate" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		frm.elements.toDate.focus();
		return;
	} */
	if(frm.elements.rangeTo.value!=""){
		if(getLong(frm.elements.rangeFrom.value)>getLong(frm.elements.rangeTo.value)){
			//alert('Range To has to be greater than Range From');
			showDialog({	
				msg		:	'<common:message bundle="listblacklistedstockresources" key="stockcontrol.defaults.listblacklistedstock.rangetohastobegreaterthanrangefrom" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
			frm.elements.rangeFrom.focus();
			return;
		}
	}
		submitForm(frm,'stockcontrol.defaults.listblacklistedstock.do?countTotalFlag=YES');
		//targetFormName.action=appPath + "/stockcontrol.defaults.listblacklistedstock.do?countTotalFlag=YES";//Added by A-5214 as part from the ICRD-20507
}

function onClickClear(){
	var frm = targetFormName;
	submitForm(frm,'stockcontrol.defaults.clearblacklistedstock.do');
}

function onClickRevoke(stringAction){
		var checkVal=document.getElementsByName('blacklistCheck');
		var frm = targetFormName;
		var checkbox="";
		if(!frm.elements.blacklistCheck){
			//alert('No rows available to revoke');
			showDialog({	
				msg		:	'<common:message bundle="listblacklistedstockresources" key="stockcontrol.defaults.listblacklistedstock.norowsavailabletorevoke" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
		}else{
			if(validateIsChecked('blacklistCheck')){
				for(var i=0;i<checkVal.length;i++){
					if(checkVal[i].checked==true){
						checkbox=i;
						break;
					}
				}
				var strUrl = stringAction+"?checkbox="+checkbox;
				openPopUp(strUrl,650,380);
			}
		}
		//alert('checkbox'+checkbox);
		//frm.action = 'stockcontrol.defaults.revokeblacklistedstock.do';
		//frm.submit();
		//var childWindow = window.open(stringAction, "LOV", 'scrollbars,status,width=480,height=380,screenX=100,screenY=30,left=250,top=100')
}

function setFocus(){
	var frm = targetFormName;
	frm.elements.docType.focus();
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