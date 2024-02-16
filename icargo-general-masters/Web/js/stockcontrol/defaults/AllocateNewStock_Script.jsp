<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
var rangeMap = new Hashtable();
function screenSpecificEventRegister(){
	var frm=targetFormName;

	with(frm){
		evtHandler.addEvents("rangeFrom","validateFields(this,-1,'Range From',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("rangeTo","validateFields(this,-1,'Range To',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("numberOfDocs","validateFields(this,-1,'No of Docs',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("btnlist","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","clearLOVForm()",EVT_CLICK);
		if(targetFormName.elements.availableCheckAll){
			evtHandler.addEvents("availableCheckAll","doAvailCheckAll(targetFormName)",EVT_CLICK);
		}
		if(targetFormName.elements.availableRangeNo){
			//evtHandler.addEvents("availableRangeNo","undoAvailCheckAll(targetFormName)",EVT_CLICK);
		}
		evtHandler.addEvents("btnMoveRange","onClickTransfer()",EVT_CLICK);
		if(targetFormName.elements.allocatedCheckAll){
			evtHandler.addEvents("allocatedCheckAll","doAllocCheckAll(targetFormName)",EVT_CLICK);
		}
		if(targetFormName.elements.allocatedRangeNo){
			//evtHandler.addEvents("allocatedRangeNo","undoAllocCheckAll(targetFormName)",EVT_CLICK);
		}
		if(targetFormName.elements.allocatedRangeNo){
		evtHandler.addEvents("stockRangeFrom","validateStockRangeFrom(this)",EVT_BLUR);
		//evtHandler.addEvents("stockRangeFrom","if(validateFields(this,-1,'Range From',0,true,true)){calculateNumberOfDocs(this);}",EVT_BLUR);

		evtHandler.addEvents("stockRangeTo","validateStockRangeTo(this)",EVT_BLUR);
		//evtHandler.addEvents("stockRangeTo","if(validateFields(this,-1,'Range To',0,true,true)){calculateNumberOfDocs(this);}",EVT_BLUR);

		evtHandler.addEvents("noOfDocs","validateFields(this,-1,'No Of Docs',3,true,true)",EVT_BLUR);
		}
		evtHandler.addEvents("availableTotalnoofDocs","validateFields(this,-1,'Available Total No of Docs',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("allocatedTotalnoofDocs","validateFields(this,-1,'Allocated Total No of Docs',3,true,true)",EVT_BLUR);
		evtHandler.addEvents("btnSave","onClickSave()",EVT_CLICK);
		//evtHandler.addEvents("btnClose","location.href('home.jsp')",EVT_CLICK);
		evtHandler.addEvents("btnClose","doClose()",EVT_CLICK);
		evtHandler.addEvents("btnClose","shiftFocus()",EVT_BLUR);
		
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);		
		evtHandler.addEvents("rangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("rangeTo","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("stockRangeFrom","validateRange(this)",EVT_FOCUS);
		evtHandler.addEvents("stockRangeTo","validateRange(this)",EVT_FOCUS);
	}
	
	/*
		Manage awbPrefix combo on load
	*/
	showPartnerAirlines();
	
	applySortOnTable("rangeTable",new Array("None","Number","Number","Number"));
	setFocus();
	DivSetVisible(true);
	onScreenloadSetHeight();
	callOnLoad();
	if(targetFormName.elements.isError==null || targetFormName.elements.isError.value==""){
		onChangeOfDocTyp();
	}
	
	if(frm.elements.reportGenerateMode.value == "generatereport" && frm.elements.statusFlag.value == "success") {	
	targetFormName.elements.reportGenerateMode.value="";		
	generateReport(frm,"/stockcontrol.defaults.allocatenewstock.generatestockallocationreport.do");	
	//alert(frm.reportGenerateMode.value);
	
	}
}

function validateStockRangeFrom(range){
	if(validateFields(range,-1,'Range From',0,true,true)){
		//calculateNumberOfDocs(range);
	}
}

function validateStockRangeTo(range){
	if(validateFields(range,-1,'Range To',0,true,true)){
		calculateNumberOfDocs(range);
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

function calculateNumberOfDocs(obj){
	var frm = targetFormName;
	if(!frm.elements.stockRangeFrom.length){
		if(frm.elements.stockRangeFrom.value!=""
				&& frm.elements.stockRangeTo.value!=""){
				if(getLong(frm.elements.stockRangeTo.value)<
						getLong(frm.elements.stockRangeFrom.value)){
					//alert('Range To has to be greater than Range From');
					
					showDialog({	
						msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.rangetohastobegreaterthanrangefrom" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:frm,
						onClose : function () {
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
						msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.rangetohastobegreaterthanrangefrom" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:frm,
						onClose : function () {
								 frm.elements.stockRangeFrom[id].focus();
					}
					});
					return;
			}
			frm.elements.noOfDocs[id].value =
				getLong(frm.elements.stockRangeTo[id].value) -
					getLong(frm.elements.stockRangeFrom[id].value) + 1;
		}else{
			if(frm.elements.rangeFrom[id].parentElement.parentElement.id != "rangeTableTemplateRow"
					&& frm.elements.rangeFrom[id].parentElement.parentElement.id != "rangeTableTemplateRow"
					&& frm.elements.hiddenOpFlag[id].value != "D" && frm.elements.hiddenOpFlag[id].value != "D"
					&& frm.elements.hiddenOpFlag[id].value != "NOOP" && frm.elements.hiddenOpFlag[id].value != "NOOP"){
				frm.elements.noOfDocs[id].value = "0";
			}
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
	if(frm.stockRangeFrom){
		if(!frm.stockRangeFrom.length){
			frm.allocatedTotalnoofDocs.value = frm.noOfDocs.value;
		}else{
			var rangeFrom=document.getElementsByName('stockRangeFrom');
			sum=0;
			for(i=0;i<rangeFrom.length;i++){
				sum = parseInt(sum) + parseInt(frm.noOfDocs[i].value);
			}
			frm.allocatedTotalnoofDocs.value = sum;
		}
	}else{
		frm.allocatedTotalnoofDocs.value = "0";
	}
}*/

/*function callOnLoad(){
	var sum=0;
	var frm = targetFormName;
	if(frm.stockRangeFrom){
		if(!frm.stockRangeFrom.length){
			frm.noOfDocs.value="0";
			if(frm.stockRangeFrom.value!=""
				&& frm.stockRangeTo.value!=""){
				frm.noOfDocs.value =
					getLong(frm.stockRangeTo.value) -
						getLong(frm.stockRangeFrom.value) + 1;
				frm.allocatedTotalnoofDocs.value=frm.noOfDocs.value;
			}
		}else{
			for(i=0;i<stockRangeFrom.length;i++){
				frm.noOfDocs[i].value =
					getLong(frm.stockRangeTo[i].value) -
						getLong(frm.stockRangeFrom[i].value) + 1;
					sum+=frm.noOfDocs[i].value;
			}
			frm.allocatedTotalnoofDocs.value=sum;
		}
	}else{
		frm.allocatedTotalnoofDocs.value = "0";
	}
}*/

//Added By A-2872 For Inserting LOV Instead Of combo

function displayLov(strAction){
	var frm = targetFormName;
	var stockControlFor='stockControlFor';
	var val=frm.elements.stockControlFor.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockControlFor;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically starts
		var clientHeight = document.body.clientHeight;
		var clientWidth = document.body.clientWidth;
		var _reqWidth=(clientWidth*45)/100;
		var _reqHeight = (clientHeight*50)/100;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically ends
	openPopUp(strUrl,_reqWidth,_reqHeight);
}


function displayLov1(strAction){
	var frm = targetFormName;
	var stockHolderCode='stockHolderCode';
	var stockHolderType='stockHolderType';
	var val=frm.elements.stockHolderCode.value;
	var typeVal=frm.elements.stockHolderType.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically starts
		var clientHeight = document.body.clientHeight;
		var clientWidth = document.body.clientWidth;
		var _reqWidth=(clientWidth*45)/100;
		var _reqHeight = (clientHeight*50)/100;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically ends
	openPopUp(strUrl,_reqWidth,_reqHeight);
}


// End

function onClickClose(){
	//window.close();
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
						msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.pleaseselectarow" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
					
					});
		return false;
	}
	return true;
}

function onClickAdd(){
	/*var frm = targetFormName;
	submitForm(frm,'stockcontrol.defaults.addrowallocatenewstock.do');*/
	addTemplateRow('rangeTableTemplateRow','rangeTableBody','hiddenOpFlag');
}

function onClickDelete(){
	var frm = targetFormName;
	if(!frm.elements.allocatedRangeNo){
		//alert('No rows available to delete');
		
		showDialog({	
						msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.norowsavailabletodelete" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:targetFormName,
					
					});
	}else{
		if(validateIsChecked('allocatedRangeNo')){
			deleteTableRow('allocatedRangeNo','hiddenOpFlag');
			//submitForm(frm,'stockcontrol.defaults.deleterowallocatenewstock.do');
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
	if(frm.elements.numberOfDocs.value == "0"){
		frm.elements.numberOfDocs.value = "";
	}
	if(frm.elements.docType.value==""){
		//alert('Select document type');
		
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.selectdocumenttype" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.docType.focus();
					}
			});
		return;
	}
	if(frm.elements.subType.value==""){
		//alert('Select document sub type.');
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.selectdocumentsubtype" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.subType.focus();
					}
			});
		return;
	}
	if(frm.elements.stockControlFor.value==""){
		//alert('Please enter the Stock Control For');
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.pleaseenterthestockcontrolfor" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.stockControlFor.focus();
					}
			});
		return;
	}
	if(frm.elements.stockHolderCode.value==""){
		//alert('Please enter the stock holder code');
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.pleaseenterthestockholdercode" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.stockHolderCode.focus();
					}
			});
		return;
	}
	/*cOMMENTED by A-7740 AS A PART OF ICRD-225106
	if(frm.elements.rangeFrom.value==""){
		//alert('Please specify Range From');
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.pleasespecifyrangefrom" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.rangeFrom.focus();
					}
			});
		return;
	}*/
	if(frm.elements.numberOfDocs.value==""){
		if(frm.elements.rangeTo.value==""){
			//alert('Please specify Range To');
			showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.pleasespecifyrangeto" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
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
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.rangetohastobegreaterthanrangefrom" scope="request"/>',
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
	submitForm(frm,'stockcontrol.defaults.listallocatenewstock.do');
}

function onClickTransfer(){
	var frm = targetFormName;
	if(!frm.elements.availableRangeNo){
		//alert('No rows available for transfer');
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.norowsavailablefortransfer" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
			});
	}else{
		if(validateIsChecked('availableRangeNo')){
			submitForm(frm,'stockcontrol.defaults.transferallocatenewstock.do');
		}
	}
}

function onClickSave(){
	var frm = targetFormName;
	if(frm.elements.docType.value==""){
		//alert('Select document type');
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.selectdocumenttype" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.docType.focus();
					}
			});
		return;
	}
	if(frm.elements.subType.value==""){
		//alert('Select document sub type.');
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.selectdocumentsubtype" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.subType.focus();
					}
			});
		return;
	}
	if(frm.elements.stockControlFor.value==""){
		//alert('Please enter the Stock Control For');
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.pleaseenterthestockcontrolfor" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.stockControlFor.focus();
					}
			});
		return;
	}
	if(frm.elements.stockHolderCode.value==""){
		//alert('Please enter the stock holder code');
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.pleaseenterthestockholdercode" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
				onClose : function () {
						frm.elements.stockHolderCode.focus();
					}
			});
		return;
	}
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
	/*if(!frm.allocatedRangeNo){
		//alert('No stock range is specified to save');
		showDialog('<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.nostockrangeisspecifiedtosave" scope="request"/>',1,self);
		return;
	}*/
	if(count==0){
		showDialog({	
				msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.nostockrangeisspecifiedtosave" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:frm,
			});
		return;
	}
	else{
		var rangeFrom=document.getElementsByName('stockRangeFrom');
		var rangeTo=document.getElementsByName('stockRangeTo');
		var count = 0;
		var hiddenOpFlag=document.getElementsByName('hiddenOpFlag');
		var emptyRowCount = 0;
		if(rangeFrom.length=="1"){
			if(rangeFrom.parentElement.parentElement.id !=
							"rangeTableTemplateRow" && hiddenOpFlag.value != "D" &&
							hiddenOpFlag.value != "NOOP"){
				if(frm.elements.rangeFrom.value==""){
					 //alert('Enter the Range From field');
					 showDialog({	
						msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.entertherangefromfield" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:frm,
						onClose : function () {
								 frm.elements.rangeFrom.focus();
							}
					});
					 return;
				}else if(frm.elements.rangeTo.value==""){
					 //alert('Enter the Range To field');
					 showDialog({	
						msg		:	'<common:message bundle="createstockresources" key="stockcontrol.defaults.createstock.entertherangetofield" scope="request"/>',
						type	:	1, 
						parentWindow:self,
						parentForm:frm,
						onClose : function () {
								  frm.elements.rangeTo.focus();
							}
					});
					 return;
				}
			}
		}else{
			for(var i=0;i<rangeFrom.length;i++){
				if(rangeFrom[i].parentElement.parentElement.id !=
								"rangeTableTemplateRow" && hiddenOpFlag[i].value != "D" &&
								hiddenOpFlag[i].value != "NOOP"){
					if(rangeFrom[i].value=="" && rangeTo[i].value==""){
						emptyRowCount++;
					}
					if(emptyRowCount > 1){
						if(rangeFrom[i].value==""){
							//alert('Please specify Range From');
							 showDialog({	
								msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.pleasespecifyrangefrom" scope="request"/>',
								type	:	1, 
								parentWindow:self,
								parentForm:frm,
								onClose : function () {
										  frm.elements.stockRangeFrom[i-1].focus();
									}
							});
							return;
						}else if(rangeTo[i].value==""){
							//alert('Please specify Range To');
							 showDialog({	
								msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.pleasespecifyrangeto" scope="request"/>',
								type	:	1, 
								parentWindow:self,
								parentForm:frm,
								onClose : function () {
										  frm.elements.stockRangeTo[i-1].focus();
									}
							});
							return;
						}
						/*if(getLong(rangeFrom[i].value)>getLong(rangeTo[i].value)){
							alert('Range To has to be greater than Range From');
							frm.stockRangeFrom[i-1].focus();
							return;
						}*/
					}
				}
			}
		}
	}
	/*for(var i=0;i<(rangeFrom.length)-1;i++){
		for(var j=i+1;j<rangeFrom.length;j++){
			if(rangeFrom[i].value=="" || rangeFrom[i].value=="" || rangeFrom[j].value=="" || rangeFrom[j].value=="")
				return;
			if(rangeFrom[i].value==rangeFrom[j].value
				&&	rangeTo[i].value==rangeTo[j].value){
					//alert('Duplicate ranges cannot be entered');
					showDialog('<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.duplicaterangescannotbeentered" scope="request"/>',1,self);
					frm.stockRangeFrom.focus();
					return;
			}
			if(rangeTo[i].value==rangeFrom[j].value){
				//alert('Continuous ranges cannot be entered');
				showDialog('<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.continuousrangescannotbeentered" scope="request"/>',1,self);
				frm.stockRangeFrom.focus();
				return;
			}
			if(rangeTo[i].value==rangeTo[j].value){
				//alert('The end ranges cannot be the same');
				showDialog('<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.endrangescannotbethesame" scope="request"/>',1,self);
				frm.stockRangeFrom.focus();
				return;
			}
			if(rangeFrom[i].value<=rangeFrom[j].value
				&& rangeTo[i].value>rangeTo[j].value){
					//alert('Overlapping ranges cannot be entered');
					showDialog('<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.overlappingrangescannotbeentered" scope="request"/>',1,self);
					frm.stockRangeFrom.focus();
					return;
			}
		}
	}*/
	//callOnLoad();
	showDialog({	
		msg		:	'<common:message bundle="allocatenewstockresources" key="stockcontrol.defaults.allocatenewstock.doyouwanttoallocatethestock" scope="request"/>',
		type	:	4, 
		parentWindow:self,
		parentForm:frm,
		dialogId:'id_1',
		onClose : function () {
				 screenConfirmDialog(frm,'id_1');
				screenNonConfirmDialog(frm,'id_1');
			}
	});
	
	/*
	if(confirm('Do you want to allocate the stock?')){
		submitForm(frm,'stockcontrol.defaults.saveallocatenewstock.do');
	}*/
}

function screenConfirmDialog(frm, dialogId) {
	while(frm.elements.currentDialogId.value == ''){

	}
	if(frm.elements.currentDialogOption.value == 'Y') {
		if(dialogId == 'id_1'){
			submitForm(frm,'stockcontrol.defaults.saveallocatenewstock.do');
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

function setFocus(){
	var frm = targetFormName;
	if(!(targetFormName.elements.docType.disabled)){
		targetFormName.elements.docType.focus();
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
	} else{
		frm.elements.availableCheckAll.checked=true;
		doAvailCheckAll(targetFormName)
	}
}

function callOnLoad(){
	var frm = targetFormName;
	var sum=0;
	if(frm.elements.stockRangeFrom){
		if(!frm.elements.stockRangeFrom.length){
		 	if(frm.elements.stockRangeFrom.value!=""
				&& frm.elements.stockRangeTo.value!=""){
					frm.elements.noOfDocs.value =
						getLong(frm.elements.stockRangeTo.value) -
							getLong(frm.elements.stockRangeFrom.value) + 1;
					frm.elements.allocatedTotalnoofDocs.value=frm.elements.noOfDocs.value;
					
					frm.elements.noOfDocs.defaultValue =
							getLong(frm.elements.stockRangeTo.value) -
							getLong(frm.elements.stockRangeFrom.value) + 1;
					frm.elements.allocatedTotalnoofDocs.defaultValue=frm.elements.noOfDocs.value;
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
					frm.elements.noOfDocs[i].defaultValue =
						getLong(frm.elements.stockRangeTo[i].value) -
					        getLong(frm.elements.stockRangeFrom[i].value) + 1;
					sum=  parseInt(sum)+parseInt(frm.elements.noOfDocs[i].value);
				}
			}
			frm.elements.allocatedTotalnoofDocs.value=sum;
			frm.elements.allocatedTotalnoofDocs.defaultValue=sum;
		}
	}else{
		frm.elements.allocatedTotalnoofDocs.value = "0";
		frm.elements.allocatedTotalnoofDocs.defaultValue = "0";
	}
	updateCheckAllCheckBox();
}




/*Added By A-2850
  Method Added for the Resolution Purpose*/

function onScreenloadSetHeight(){
	    var height = document.body.clientHeight;
		var mainDivHeight=((height*95)/100);
		//document.getElementById('pageDiv').style.height = mainDivHeight+'px';
		//document.getElementById('div1').style.height = (mainDivHeight-320)+'px';
		//document.getElementById('div2').style.height = (mainDivHeight-320)+'px';
		//commented for ICRD-24298 
		//document.getElementById('pageDiv').style.height = ((mainDivHeight*80)/100)+'px';
		//document.getElementById('div1').style.height = (((mainDivHeight*80)/100)-320)+'px';
		//document.getElementById('div2').style.height = (((mainDivHeight*80)/100)-320)+'px';


}





function doClose(){
	submitFormWithUnsaveCheck("stockcontrol.defaults.closeallocatenewstock.do");
}
function shiftFocus(){
	if(!event.shiftKey){
		if(!(targetFormName.elements.docType.disabled)){
			targetFormName.elements.docType.focus();
		}
	}
}

function showPartnerAirlines(){	
	var partnerPrefix = targetFormName.elements.partnerPrefix.value;
	if(targetFormName.elements.partnerAirline.checked){
		//enableField(targetFormName.awbPrefix);
		jquery('select[name="awbPrefix"] option[value="'+partnerPrefix+'"]').remove();
		targetFormName.elements.awbPrefix.disabled=false;
	}else{
		//targetFormName.awbPrefix.value="";
		//targetFormName.airlineName.value="";		
		//disableField(targetFormName.awbPrefix);
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
function onClickImage(index,frmRange){
    var index1 = index;
	var rangeFrom=frmRange.toString();
	var check = (frmRange%7).toString();
	var docnum = rangeFrom+check;
	openPopUp("stockcontrol.defaults.utiliseddocsinfo.do?fromRange="+docnum,200,200);
}