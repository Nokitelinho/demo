<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;
	with(frm){

		
		evtHandler.addEvents("rangeFrom","validateFields(this,-1,'Range From',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("rangeTo","validateRangeTo(this)",EVT_BLUR);
		evtHandler.addEvents("numberOfDocs","validateFields(this,-1,'No of Docs',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
		if(targetFormName.elements.availableCheckAll){
			evtHandler.addEvents("availableCheckAll","doAvailCheckAll(targetFormName)",EVT_CLICK);
		}
		if(targetFormName.elements.availableRangeNo){
			evtHandler.addEvents("availableRangeNo","undoAvailCheckAll(targetFormName)",EVT_CLICK);
		}
		evtHandler.addEvents("btnMoveRange","onClickTransfer()",EVT_CLICK);
		if(targetFormName.elements.allocatedCheckAll){
			evtHandler.addEvents("allocatedCheckAll","doAllocCheckAll(targetFormName)",EVT_CLICK);
		}
		if(targetFormName.elements.allocatedRangeNo){
			evtHandler.addEvents("allocatedRangeNo","undoAllocCheckAll(targetFormName)",EVT_CLICK);
			evtHandler.addEvents("stockRangeFrom","if(validateFields(this,-1,'Range From',0,true,true)){calculateNumberOfDocs(this);}",EVT_BLUR);
			evtHandler.addEvents("stockRangeTo","if(validateFields(this,-1,'Range To',0,true,true)){calculateNumberOfDocs(this);}",EVT_BLUR);
			evtHandler.addEvents("noOfDocs","validateFields(this,-1,'No Of Docs',3,true,true)",EVT_BLUR);
		}

		evtHandler.addEvents("availableTotalnoofDocs","validateFields(this,-1,'Available Total No of Docs',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("allocatedTotalnoofDocs","validateFields(this,-1,'Allocated Total No of Docs',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("btnOk","onClickOk()",EVT_CLICK);
		evtHandler.addEvents("btnCancel","onClickCancel()",EVT_CLICK);
		evtHandler.addEvents("rangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("rangeTo","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("stockRangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("stockRangeTo","validateRange(this)",EVT_FOCUS);

	}
	setFocus();
	// DivSetVisible(true);
	callOnLoad();

}

function validateRangeTo(range){
	if(validateFields(range,-1,'Range To',0,true,true)){
		calculateNumberOfDocsText(range);
	}
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
function calculateNumberOfDocsText(obj){
var frm = targetFormName;
if(frm.elements.rangeFrom.value!=""
			&& frm.elements.rangeTo.value!=""){
				if(getLong(frm.elements.rangeTo.value)<
					getLong(frm.elements.rangeFrom.value)){
					//alert('Range To has to be greater than Range From');
					showDialog({	
						msg		:	"<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.rangetohastobegreaterthanrangefrom" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						
					    onClose: function () {
								frm.elements.rangeFrom.focus();
							}
					    }); 
					return;
				}
				frm.elements.numberOfDocs.value =
					getLong(frm.elements.rangeTo.value) -
						getLong(frm.elements.rangeFrom.value) + 1;
		}
}
function calculateNumberOfDocs(obj){
	var frm = targetFormName;
	if(!frm.elements.stockRangeFrom.length){
		if(frm.elements.stockRangeFrom.value!=""
				&& frm.elements.stockRangeTo.value!=""){
				if(getLong(frm.elements.stockRangeTo.value)<
					getLong(frm.elements.stockRangeFrom.value)){
					//alert('Range To has to be greater than Range From');
					showDialog({	
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.rangetohastobegreaterthanrangefrom" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					    onClose: function () {
								frm.elements.stockRangeFrom.focus();
							}
					    }); 
					return;
				}
				frm.elements.noOfDocs.value =getLong(frm.elements.stockRangeTo.value) -
					getLong(frm.elements.stockRangeFrom.value) + 1;
		}else{
			if(frm.elements.rangeFrom.parentElement.parentElement.id != "rangeTableTemplateRow"
					&& frm.elements.rangeFrom.parentElement.parentElement.id != "rangeTableTemplateRow"
					&& frm.elements.hiddenOpFlag.value != "D" && frm.elements.hiddenOpFlag.value != "D"
					&& frm.elements.hiddenOpFlag.value != "NOOP" && frm.elements.hiddenOpFlag.value != "NOOP"){
				frm.elements.noOfDocs.value = "0";
			}
		}
		frm.elements.allocatedTotalnoofDocs.value = frm.elements.noOfDocs.value;
	}else{
		var id = obj.id.substring(11);
		if(frm.elements.stockRangeFrom[id].value!=""
			&& frm.elements.stockRangeTo[id].value!=""){
			if(getLong(frm.elements.stockRangeTo[id].value)<
				getLong(frm.elements.stockRangeFrom[id].value)){
				//alert('Range To has to be greater than Range From');
				showDialog({	
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.rangetohastobegreaterthanrangefrom" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					    onClose: function () {
								frm.elements.stockRangeFrom[id].focus();
							}
					    }); 
				return;
			}
			frm.elements.noOfDocs[id].value =
				getLong(frm.elements.stockRangeTo[id].value) -
					getLong(frm.elements.stockRangeFrom[id].value) + 1;
		}
		var rangeFrom=document.getElementsByName('stockRangeFrom');
		sum=0;
		var numberOfDocuments = "";
		for(i=0;i<rangeFrom.length;i++){
			numberOfDocuments = "";
			if(frm.elements.hiddenOpFlag[i].value != "D" &&
				frm.elements.hiddenOpFlag[i].value != "NOOP"){
			   numberOfDocuments = frm.elements.noOfDocs[i].value;
			}
			if(numberOfDocuments == ""){
				numberOfDocuments = 0;
			}
			sum = parseInt(sum) + parseInt(numberOfDocuments);
		}
		frm.elements.allocatedTotalnoofDocs.value = sum;
	}
}

function doAvailCheckAll(frm){
	var chke = document.getElementsByName("availableRangeNo");
 	if(frm.elements.availableCheckAll.checked == true){
   		for(var i=0; i<chke.length; i++){
   			chke[i].checked = true;
  		}
 	}
 	if(frm.elements.availableCheckAll.checked == false){
  		for(var i=0; i<chke.length; i++){
  	 		chke[i].checked = false;
  		}
 	}
}

function doAllocCheckAll(frm){
 	var chke = document.getElementsByName("allocatedRangeNo");
 	if(frm.elements.allocatedCheckAll.checked == true){
   		for(var i=0; i<chke.length; i++){
   			chke[i].checked = true;
  		}
 	}
 	if(frm.elements.allocatedCheckAll.checked == false){
  		for(var i=0; i<chke.length; i++){
  	 		chke[i].checked = false;
  		}
 	}
}

function undoAvailCheckAll(frm){
 	var chke =document.getElementsByName("availableRangeNo");
	var flag=0;
	for(var i=0; i<chke.length; i++){
	 	flag=0;
		if(chke[i].checked == true){
			flag=1;
		}
		if(flag==0){
			break;
		}
	}
	if(flag==0){
		frm.elements.availableCheckAll.checked=false;
	}
}

function undoAllocCheckAll(frm){
 	var chke =document.getElementsByName("allocatedRangeNo");
	var flag=0;
	for(var i=0; i<chke.length; i++){
	 	flag=0;
		if(chke[i].checked == true){
			flag=1;
		}
		if(flag==0){
			break;
		}
	}
	if(flag==0){
		frm.elements.allocatedCheckAll.checked=false;
	}
}

/*function callOnLoad(){
	var frm = targetFormName;
	if(frm.elements.stockRangeFrom){
		if(!frm.elements.stockRangeFrom.length){
			frm.elements.allocatedTotalnoofDocs.value = frm.elements.noOfDocs.value;
		}else{
			var rangeFrom=document.getElementsByName('stockRangeFrom');
			sum=0;
			for(i=0;i<rangeFrom.length;i++){
				sum = parseInt(sum) + parseInt(frm.elements.noOfDocs[i].value);
			}
			frm.elements.allocatedTotalnoofDocs.value = sum;
		}
	}else{
		frm.elements.allocatedTotalnoofDocs.value = "0";
	}
}*/

/*function callOnLoad(){
	var sum=0;
	var frm = targetFormName;
	if(frm.elements.stockRangeFrom){
		if(!frm.elements.stockRangeFrom.length){
			frm.elements.noOfDocs.value="0";
			if(frm.elements.stockRangeFrom.value!=""
				&& frm.elements.stockRangeTo.value!=""){
				frm.elements.noOfDocs.value =
					getLong(frm.elements.stockRangeTo.value) -
						getLong(frm.elements.stockRangeFrom.value) + 1;
				frm.elements.allocatedTotalnoofDocs.value=frm.elements.noOfDocs.value;
			}
		}else{
			for(i=0;i<stockRangeFrom.length;i++){
				frm.elements.noOfDocs[i].value =
					getLong(frm.elements.stockRangeTo[i].value) -
						getLong(frm.elements.stockRangeFrom[i].value) + 1;
				sum+=frm.elements.noOfDocs[i].value;
			}
			frm.elements.allocatedTotalnoofDocs.value=sum;
		}
	}else{
		frm.elements.allocatedTotalnoofDocs.value = "0";
	}
}*/

function onClickCancel(){
	window.close();
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
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.pleaseselectarow" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 		
		return false;
	}
	return true;
}

function onClickOk(){
	var frm = targetFormName;
	var rangeFrom=document.getElementsByName('stockRangeFrom');
	var rangeTo=document.getElementsByName('stockRangeTo');
	var hiddenOpFlag=document.getElementsByName('hiddenOpFlag');
	var emptyRowCount = 0;

	for(var i=0;i<rangeFrom.length;i++){
		if(hiddenOpFlag[i].value != "D" && hiddenOpFlag[i].value != "NOOP")
		{
			if(rangeFrom[i].value==""){
			showDialog({	
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.pleasespecifyrangefrom" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					    onClose: function () {
								frm.elements.stockRangeFrom[i].focus();
							}
					    }); 
				return;
				
			}else if(rangeTo[i].value==""){
			showDialog({	
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.pleasespecifyrangeto" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,						
					    onClose: function () {
								frm.elements.stockRangeTo[i].focus();
							}
					    }); 			
				
				return;
			}
			
		}
	}
	
	submitForm(frm,'stockcontrol.defaults.closeeditrange.do');
}

function onClickAdd(){
	var frm = targetFormName;
	frm.elements.manual.disabled=false;
	//submitForm(frm,'stockcontrol.defaults.addroweditrange.do');
	addTemplateRow('rangeTableTemplateRow','rangeTableBody','hiddenOpFlag');
}

function onClickDelete(){
	var frm = targetFormName;
	var checkValues = document.getElementsByName('allocatedRangeNo');
	var hiddenOpFlag = document.getElementsByName('hiddenOpFlag');
	var count = 0;
	if(checkValues.length > 0){
		for(var i=0;i<checkValues.length;i++){
			if(checkValues[i].parentElement.parentElement.id !=
							"rangeTableTemplateRow" && hiddenOpFlag[i].value != "D" &&
							hiddenOpFlag[i].value != "NOOP"){
				count++;
			}

		}
	}
	if(count==0){
		//alert('No rows available to delete');
		 showDialog({	
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.norowsavailabletodelete" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 		
	}else{
		if(validateIsChecked('allocatedRangeNo')){
			frm.elements.manual.disabled=false;
			deleteTableRow('allocatedRangeNo','hiddenOpFlag');
			//submitForm(frm,'stockcontrol.defaults.deleteroweditrange.do');
			var sum=0;
			var numberOfDocuments = "";
			var rangeFrom = document.getElementsByName('stockRangeFrom');
			for(i=0;i<rangeFrom.length;i++){
				numberOfDocuments = "";
				if(frm.elements.hiddenOpFlag[i].value != "D" &&
					frm.elements.hiddenOpFlag[i].value != "NOOP"){
				   numberOfDocuments = frm.elements.noOfDocs[i].value;
				}
				if(numberOfDocuments == ""){
					numberOfDocuments = 0;
				}
				sum = parseInt(sum) + parseInt(numberOfDocuments);
			}
			frm.elements.allocatedTotalnoofDocs.value = sum;

		}
	}
}

function onClickList(){
	var frm = targetFormName;
	if(frm.elements.numberOfDocs.value=="0"){
		frm.elements.numberOfDocs.value="";
	}
	/*
	cOMMENTED by A-7740 AS A PART OF ICRD-225106
	if(frm.elements.rangeFrom.value==""){
		//alert('Please enter Range From');
		showDialog({	
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.pleasespecifyrangefrom" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						
					    onClose: function () {
								frm.elements.rangeFrom.focus();
							}
					    }); 
		return;
	} */
	if(frm.elements.numberOfDocs.value==""){
		if(frm.elements.rangeTo.value==""){
			//alert('Please enter Range To');
			showDialog({	
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.pleasespecifyrangeto" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						
					    onClose: function () {
								frm.elements.rangeTo.focus();
							}
					    }); 			
			
			return;
		}
	}
	if(frm.elements.rangeTo.value!=""){
		if(getLong(frm.elements.rangeFrom.value)>getLong(frm.elements.rangeTo.value)){
			//alert('Range To has to be greater than Range From');
			showDialog({	
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.rangetohastobegreaterthanrangefrom" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
						
					    onClose: function () {
								frm.elements.rangeFrom.focus();
							}
					    }); 	
			return;
		}
	}
	frm.elements.manual.disabled=false;
	submitForm(frm,'stockcontrol.defaults.listeditrange.do');
}

function onClickTransfer(){
	var frm = targetFormName;
	if(!frm.elements.availableRangeNo){
		//alert('No rows available for transfer');
		 showDialog({	
						msg		:	"<common:message bundle="editrangeresources" key="stockcontrol.defaults.editrange.norowsavailablefortransfer" scope="request"/>",
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName
					    }); 		
	}else{
		if(validateIsChecked('availableRangeNo')){
			frm.elements.manual.disabled=false;
			submitForm(frm,'stockcontrol.defaults.transfereditrange.do');
		}
	}
}

function setFocus(){
	var frm = targetFormName;
	var parentFrm = window.opener.targetFormName;
	frm.elements.rangeFrom.focus();
	if(frm.elements.isValidRange.value=="Y"){
	window.opener.IC.util.common.childUnloadEventHandler();
		window.opener.submitForm(parentFrm,'stockcontrol.defaults.reloadAllocateStock.do');
		window.close();
	}
}

function updateCheckAllCheckBox(){
	var frm = targetFormName;
	var allocatedRangeNo = document.getElementsByName('allocatedRangeNo');
	var availableRangeNo = document.getElementsByName('availableRangeNo');
	if(allocatedRangeNo==null || allocatedRangeNo.length==0){
		frm.elements.allocatedCheckAll.checked=false;
	}
	if(availableRangeNo==null || availableRangeNo.length==0){
		frm.elements.availableCheckAll.checked=false;
	}
}

function callOnLoad(){
	var sum=0;
	var frm = targetFormName;
	if(frm.elements.stockRangeFrom){
		if(!frm.elements.stockRangeFrom.length){
		 	if(frm.elements.stockRangeFrom.value!=""
				&& frm.elements.stockRangeTo.value!=""){
					frm.elements.noOfDocs.value =
						getLong(frm.elements.stockRangeTo.value) -
							getLong(frm.elements.stockRangeFrom.value) + 1;
					frm.elements.allocatedTotalnoofDocs.value=frm.elements.noOfDocs.value;
			}
		}else{
			var stockRangeFrom=document.getElementsByName('stockRangeFrom');
			sum = 0;
			for(i=0;i<stockRangeFrom.length;i++){
				if(frm.elements.stockRangeFrom[i].value!=""
					&& frm.elements.stockRangeTo[i].value!=""){
					frm.elements.noOfDocs[i].value =
						getLong(frm.elements.stockRangeTo[i].value) -
							getLong(frm.elements.stockRangeFrom[i].value) + 1;
					sum=  parseInt(sum)+parseInt(frm.elements.noOfDocs[i].value);
				}
			}
			frm.elements.allocatedTotalnoofDocs.value=sum;
		}
	}else{
		frm.elements.allocatedTotalnoofDocs.value = "0";
	}
	updateCheckAllCheckBox();
}
function validateRange(object){
			object.maxLength=targetFormName.maxRange.value;		
}