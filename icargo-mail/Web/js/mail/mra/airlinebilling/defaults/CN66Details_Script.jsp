<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
function screenSpecificEventRegister()
{

	var frm=targetFormName;
	with(targetFormName){
		evtHandler.addEvents("btnclose","closeScreen()",EVT_CLICK);
		evtHandler.addIDEvents("btNext","nextFunction()",EVT_CLICK);
		evtHandler.addEvents("btnok","okFunction()",EVT_CLICK);
		evtHandler.addIDEvents("carriagefromLov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriageFrom.value,'Carriage From','0','carriageFrom','',0)",EVT_CLICK);
		evtHandler.addIDEvents("carriagetoLov","displayLOV('showStation.do','N','Y','showStation.do',targetFormName.carriageTo.value,'Carriage To','0','carriageTo','',0)",EVT_CLICK);
		evtHandler.addEvents("allCheck","updateHeaderCheckBox(targetFormName, targetFormName.allCheck, targetFormName.check)", EVT_CLICK);
		evtHandler.addEvents("check","toggleTableHeaderCheckbox('check',targetFormName.allCheck)", EVT_CLICK);
		evtHandler.addEvents("despatchNumber","showDsnPopup()",EVT_BLUR);

		evtHandler.addEvents("totalLcWeight","restrictFloat(this,13,2)",EVT_KEYPRESS);
		evtHandler.addEvents("totalCpWeight","restrictFloat(this,13,2)",EVT_KEYPRESS);
		evtHandler.addEvents("totalEmsWeight","restrictFloat(this,13,2)",EVT_KEYPRESS);//Added as part of ICRD-101397
		evtHandler.addEvents("totalSvWeight","restrictFloat(this,13,2)",EVT_KEYPRESS);
		evtHandler.addEvents("rate","change(this)",EVT_BLUR);
		evtHandler.addEvents("totalLcWeight","change(this)",EVT_BLUR);
		evtHandler.addEvents("totalCpWeight","change(this)",EVT_BLUR);
		evtHandler.addEvents("totalEmsWeight","change(this)",EVT_BLUR);
		evtHandler.addEvents("totalSvWeight","change(this)",EVT_BLUR);
		evtHandler.addEvents("despatchNumber","mailDSN()",EVT_BLUR);
		evtHandler.addEvents("receptacleSerialNo","mailRSN()",EVT_BLUR);
		targetFormName.elements.tempRowIndicator.value="N";


	}

	onloadPopup();
	disableButtons();
	update();

}

function okFunction(){
	var carFrom=new Array();
	var carTo=new Array();
	var checkboxes=new Array();
	var categories=new Array();
	var carrFlag='false';
	var rowFlag='false';
	var catFlag='false';
	carfrom=document.getElementsByName('carriageFrom');
	carto=document.getElementsByName('carriageTo');
	checkboxes=document.getElementsByName('check');
	categories=document.getElementsByName('mailCategoryCode');
		if(checkboxes==null || checkboxes.length==0){
			//showDialog('Empty rows are not allowed', 1, self);
			showDialog({msg:'Empty rows are not allowed',type:1,parentWindow:self});
		}
		else{
			rowFlag='true';
		}
		for(var i=0;i<categories.length-1;i++){
					if(categories[i].value.length==0){
						//showDialog('Please enter Mail Category', 1, self);
						showDialog({msg:'Please enter Mail Category',type:1,parentWindow:self});
						break;
					}
					else{
						catFlag='true';
					}
		}
		if(carfrom[0].value.trim().length>0 && carto[0].value.trim().length>0){
			carrFlag='true';
			}
		else{
			//showDialog('Please enter CarriageFrom and CarriageTo', 1, self);
			showDialog({msg:'Please enter CarriageFrom and CarriageTo',type:1,parentWindow:self});
		}
		var frm = targetFormName;
					var err=0;
					//("***"+targetFormName.tempRowIndicator.value);
					if(frm.elements.operationFlag != null){
						var operationFlagLength = frm.elements.operationFlag.length;
						if(frm.elements.operationFlag.length > 1){
						if(frm.elements.operationFlag[operationFlagLength - 2].value!="NOOP" &&  frm.elements.operationFlag[operationFlagLength - 2].value!="D"){

							if(frm.elements.mailCategoryCode[operationFlagLength - 2].value == ''){
									//showDialog('Category is mandatory', 1, self);
									showDialog({msg:'Category is mandatory',type:1,parentWindow:self});
									err++;
									frm.mailCategoryCode[operationFlagLength - 2].focus();
									return;
							}
							if(frm.elements.origin[operationFlagLength - 2].value == ''){
							err++;
								//showDialog('Origin is mandatory', 1, self);
								showDialog({msg:'Origin is mandatory',type:1,parentWindow:self});
								frm.origin[operationFlagLength - 2].focus();
								return;
							}
							if(frm.elements.destination[operationFlagLength - 2].value == ''){
							err++;
								//showDialog('Destination is mandatory', 1, self);
								showDialog({msg:'Destination is mandatory',type:1,parentWindow:self});
								frm.destination[operationFlagLength - 2].focus();
								return;
							}

							if(frm.elements.despatchDate[operationFlagLength - 2].value == ''){
							err++;
								//showDialog('Despatch Date is mandatory', 1, self);
								showDialog({msg:'Despatch Date is mandatory',type:1,parentWindow:self});
								frm.despatchDate[operationFlagLength - 2].focus();
								return;
							}
							if(frm.elements.despatchNumber[operationFlagLength - 2].value == ''){
							err++;
								//showDialog('DSN is mandatory', 1, self);
								showDialog({msg:'DSN is mandatory',type:1,parentWindow:self});
								frm.despatchNumber[operationFlagLength - 2].focus();
								return;
							}
							if((frm.elements.totalLcWeight[operationFlagLength - 2].value == '' || frm.elements.totalLcWeight[operationFlagLength - 2].value == '0' || frm.elements.totalLcWeight[operationFlagLength - 2].value == '0.00')
							&&(frm.elements.totalCpWeight[operationFlagLength - 2].value == '' || frm.elements.totalCpWeight[operationFlagLength - 2].value == '0' || frm.elements.totalCpWeight[operationFlagLength - 2].value == '0.00')
							//&&(frm.totalSalWeight[operationFlagLength - 2].value == '' || frm.totalSalWeight[operationFlagLength - 2].value == '0' || frm.totalSalWeight[operationFlagLength - 2].value == '0.00')
							//&&(frm.totalUldWeight[operationFlagLength - 2].value == '' || frm.totalUldWeight[operationFlagLength - 2].value == '0' || frm.totalUldWeight[operationFlagLength - 2].value == '0.00')
							&&(frm.elements.totalSvWeight[operationFlagLength - 2].value == '' || frm.elements.totalSvWeight[operationFlagLength - 2].value == '0' || frm.elements.totalSvWeight[operationFlagLength - 2].value == '0.00')
							//Added as part of ICRD-101397
							&&(frm.elements.totalEmsWeight[operationFlagLength - 2].value == '' || frm.elements.totalEmsWeight[operationFlagLength - 2].value == '0' || frm.elements.totalEmsWeight[operationFlagLength - 2].value == '0.00')) {
							err++;
								//showDialog('Weight is mandatory', 1, self);
								showDialog({msg:'Weight is mandatory',type:1,parentWindow:self}); //modified by A-8236 for ICRD-252745
								return;
							}

							if(frm.elements.rate[operationFlagLength - 2].value == '' ||frm.elements.rate[operationFlagLength - 2].value == '0' || frm.elements.rate[operationFlagLength - 2].value =='0.00'||frm.elements.rate[operationFlagLength - 2].value == '0.0'){
							err++;
								//showDialog('Rate is mandatory', 1, self);
								showDialog({msg:'Rate is mandatory',type:1,parentWindow:self});
								frm.rate[operationFlagLength - 2].focus();
								return;
							}

							if(frm.elements.amount[operationFlagLength - 2].value == ''||frm.elements.amount[operationFlagLength - 2].value == '0' || frm.elements.amount[operationFlagLength - 2].value =='0.00'){
							err++;
								//showDialog('Amount is mandatory', 1, self);
								showDialog({msg:'Amount is mandatory',type:1,parentWindow:self});
								frm.amount[operationFlagLength - 2].focus();
								return;
							}
						   }



						}
	}
		if(carrFlag=='true' && rowFlag=='true' && catFlag=='true'){
			window.opener.IC.util.common.childUnloadEventHandler();
			submitForm(targetFormName,'mailtracking.mra.airlinebilling.defaults.captureCN66.okcn66.do');
		}
}


function onloadPopup(){

	var status=new Array();
	status=document.getElementsByName("screenStatus");

	updateCpStatus();



	if(status[0].value=="ok"){

			window.close();
			window.opener.targetFormName.action="mailtracking.mra.airlinebilling.defaults.captureCN66.reloadcn66Details.do";
			window.opener.targetFormName.submit();
	}
	if(targetFormName.elements.popupFlag.value=="popup"){

		targetFormName.elements.popupFlag.value="";

		var despatchDate = document.getElementsByName('despatchDate');

		despatchDate[despatchDate.length - 2].focus();
	}

	else if(status[0].value=="add"){


	var mailCategoryCode = document.getElementsByName('mailCategoryCode');

	//mailCategoryCode[mailCategoryCode.length].focus();

	}

}
function closeScreen(){


	//submitFormWithUnsaveCheck(appPath + "/home.jsp");
	window.close();
}


function addRow(){
	//Mandatory fields check
	var frm = targetFormName;
	//("***"+targetFormName.tempRowIndicator.value);
	if(frm.elements.operationFlag != null){
		var operationFlagLength = frm.elements.operationFlag.length;
		if(frm.elements.operationFlag.length > 1){
		if(frm.elements.operationFlag[operationFlagLength - 2].value!="NOOP" &&  frm.elements.operationFlag[operationFlagLength - 2].value!="D"){

			if(frm.elements.mailCategoryCode[operationFlagLength - 2].value == ''){
					//showDialog('Category is mandatory', 1, self);
					showDialog({msg:'Category is mandatory',type:1,parentWindow:self});
					frm.mailCategoryCode[operationFlagLength - 2].focus();
					return;
			}
			if(frm.elements.origin[operationFlagLength - 2].value == ''){
				//showDialog('Origin is mandatory', 1, self);
				showDialog({msg:'Origin is mandatory',type:1,parentWindow:self});
				frm.origin[operationFlagLength - 2].focus();
				return;
			}
			if(frm.elements.destination[operationFlagLength - 2].value == ''){
				//showDialog('Destination is mandatory', 1, self);
				showDialog({msg:'Destination is mandatory',type:1,parentWindow:self});
				frm.destination[operationFlagLength - 2].focus();
				return;
			}
			//if(frm.flightNumber[operationFlagLength - 2].value == '' && frm.carrierCode[operationFlagLength - 2].value == ''){
				//showDialog('Flight Number is mandatory', 1, self);
				//frm.carrierCode[operationFlagLength - 2].focus();
				//return;
			//}
			if(frm.elements.despatchDate[operationFlagLength - 2].value == ''){
				//showDialog('Despatch Date is mandatory', 1, self);
				showDialog({msg:'Despatch Date is mandatory',type:1,parentWindow:self});
				frm.despatchDate[operationFlagLength - 2].focus();
				return;
			}
			if(frm.elements.despatchNumber[operationFlagLength - 2].value == ''){
				//showDialog('DSN is mandatory', 1, self);
				showDialog({msg:'DSN is mandatory',type:1,parentWindow:self});
				frm.despatchNumber[operationFlagLength - 2].focus();
				return;
			}
			if((frm.elements.totalLcWeight[operationFlagLength - 2].value == '' || frm.elements.totalLcWeight[operationFlagLength - 2].value == '0' || frm.elements.totalLcWeight[operationFlagLength - 2].value == '0.00')
			&&(frm.elements.totalCpWeight[operationFlagLength - 2].value == '' || frm.elements.totalCpWeight[operationFlagLength - 2].value == '0' || frm.elements.totalCpWeight[operationFlagLength - 2].value == '0.00')
			//&&(frm.totalSalWeight[operationFlagLength - 2].value == '' || frm.totalSalWeight[operationFlagLength - 2].value == '0' || frm.totalSalWeight[operationFlagLength - 2].value == '0.00')
			//&&(frm.totalUldWeight[operationFlagLength - 2].value == '' || frm.totalUldWeight[operationFlagLength - 2].value == '0' || frm.totalUldWeight[operationFlagLength - 2].value == '0.00')
			&&(frm.elements.totalEmsWeight[operationFlagLength - 2].value == '' || frm.elements.totalEmsWeight[operationFlagLength - 2].value == '0' || frm.elements.totalEmsWeight[operationFlagLength - 2].value == '0.00')
			&&(frm.elements.totalSvWeight[operationFlagLength - 2].value == '' || frm.elements.totalSvWeight[operationFlagLength - 2].value == '0' || frm.elements.totalSvWeight[operationFlagLength - 2].value == '0.00')) {
				//showDialog('Weight is mandatory', 1, self);
				showDialog({msg:'Weight is mandatory',type:1,parentWindow:self});
				return;
			}

			if(frm.elements.rate[operationFlagLength - 2].value == '' ||frm.elements.rate[operationFlagLength - 2].value == '0' || frm.rate[operationFlagLength - 2].value =='0.00'||frm.elements.rate[operationFlagLength - 2].value == '0.0'){
				//showDialog('Rate is mandatory', 1, self);
				showDialog({msg:'Rate is mandatory',type:1,parentWindow:self});
				frm.rate[operationFlagLength - 2].focus();
				return;
			}

			if(frm.elements.amount[operationFlagLength - 2].value == ''||frm.elements.amount[operationFlagLength - 2].value == '0' || frm.elements.amount[operationFlagLength - 2].value =='0.00'){
				//showDialog('Amount is mandatory', 1, self);
				showDialog({msg:'Amount is mandatory',type:1,parentWindow:self});
				frm.amount[operationFlagLength - 2].focus();
				return;
			}
		   }



		}
	}


	check=document.getElementsByName('check');
	var count=document.getElementsByName('count');
	var dsn=document.getElementsByName("despatchNumber");
	count=check.length-1;


submitForm(targetFormName,"mailtracking.mra.airlinebilling.defaults.captureCN66.cn66details.add.do?count="+count);

}

function deleteRow(){

var chkBoxIds = document.getElementsByName('check');
var chkcount = chkBoxIds.length+1;
var count=document.getElementsByName('count');

for(var i=0;i<chkBoxIds.length-1;i++) {

	if(chkBoxIds[i].checked){

		//alert("i"+i);

		count=i;

		}
	}

if(validateSelectedCheckBoxes(targetFormName,'check',chkcount,'1')){

submitForm(targetFormName,"mailtracking.mra.airlinebilling.defaults.captureCN66.cn66details.delete.do?count="+count);

}
}


function nextFunction(){
	submitForm(targetFormName,"mailtracking.mra.airlinebilling.defaults.captureCN66.nextCN66.do");
}


function disableButtons(){
	var screenstatus=new Array();
	screenstatus=document.getElementsByName("screenStatus");
	var flag=screenstatus[0].value;

	if(flag=="add"){
		disableLink(btNext);
	}
	if(flag=="nonext"){
		targetFormName.elements.carriageFrom.disabled=true;
		targetFormName.elements.carriageTo.disabled=true;
		disableLink(btNext);
		disableLink(btnAdd);
		disableLink(btnDelete);
		var catcode=new Array();
		catcode=document.getElementsByName('mailCategoryCode');
		for(var i=0;i<catcode.length;i++){
			catcode[i].disabled=true;
		}
	}
	if(flag=="next"){
		targetFormName.elements.carriageFrom.disabled=true;
		targetFormName.elements.carriageTo.disabled=true;
		disableLink(btnAdd);
		disableLink(btnDelete);
		var catcode=new Array();
		catcode=document.getElementsByName('mailCategoryCode');
		for(var i=0;i<catcode.length;i++){
			catcode[i].disabled=true;
		}
	}
}

function updateCpStatus() {



	var operationFlag = document.getElementsByName('operationFlag');
	var lc=document.getElementsByName("totalLcWeight");
	var cp=document.getElementsByName("totalCpWeight");
	var ems=document.getElementsByName("totalEmsWeight");//Added as part of ICRD-101397
	//var sal=document.getElementsByName("totalSalWeight");
	//var uld=document.getElementsByName("totalUldWeight");
	var sv=document.getElementsByName("totalSvWeight");

	for(var rowCount=0;rowCount<lc.length;rowCount++) {

		if(operationFlag[rowCount].value!="D" && operationFlag[rowCount].value!="NOOP"){
					var flag;

					if(lc[rowCount].value>0.0) flag="lc";
					if(cp[rowCount].value>0.0) flag="cp";
					if(ems[rowCount].value>0.0) flag="ems";
					//if(sal[rowCount].value>0.0) flag="sal";
					//if(uld[rowCount].value>0.0) flag="uld";
					if(sv[rowCount].value>0.0) flag="sv";


					if(lc[rowCount].value ==0.0 && cp[rowCount].value==0.0
						//&& sal[rowCount].value==0.0
						//&& uld[rowCount].value==0.0
						&& ems[rowCount].value==0.0
						&& sv[rowCount].value==0.0){
							/*enable all values*/

							targetFormName.elements.totalLcWeight[rowCount].disabled=false;
							targetFormName.elements.totalCpWeight[rowCount].disabled=false;
							targetFormName.elements.totalEmsWeight[rowCount].disabled=false;
							//targetFormName.totalSalWeight[rowCount].disabled=false;
							//targetFormName.totalUldWeight[rowCount].disabled=false;
							targetFormName.elements.totalSvWeight[rowCount].disabled=false;
							//change(this);
							}


							if(flag=="lc"){
							targetFormName.elements.totalCpWeight[rowCount].disabled=true;
							targetFormName.elements.totalEmsWeight[rowCount].disabled=true;
							//targetFormName.totalSalWeight[rowCount].disabled=true;
							//targetFormName.totalUldWeight[rowCount].disabled=true;
							targetFormName.elements.totalSvWeight[rowCount].disabled=true;
							flag="";
							//change(this);
							}
							if(flag=="cp"){
							targetFormName.elements.totalLcWeight[rowCount].disabled=true;
							targetFormName.elements.totalEmsWeight[rowCount].disabled=true;
							//targetFormName.totalSalWeight[rowCount].disabled=true;
							//targetFormName.totalUldWeight[rowCount].disabled=true;
							targetFormName.elements.totalSvWeight[rowCount].disabled=true;
							flag="";
							//change(this);
							}
							/*if(flag=="sal"){
							targetFormName.totalCpWeight[rowCount].disabled=true;
							targetFormName.totalLcWeight[rowCount].disabled=true;
							//targetFormName.totalUldWeight[rowCount].disabled=true;
							targetFormName.totalSvWeight[rowCount].disabled=true;
							flag="";
							change(rowCount);
							}*/
							if(flag=="uld"){
							targetFormName.elements.totalCpWeight[rowCount].disabled=true;
							targetFormName.elements.totalEmsWeight[rowCount].disabled=true;
							//targetFormName.totalSalWeight[rowCount].disabled=true;
							targetFormName.elements.totalLcWeight[rowCount].disabled=true;
							targetFormName.elements.totalSvWeight[rowCount].disabled=true;
							flag="";
							//change(this);
							}
							if(flag=="sv"){

							targetFormName.elements.totalLcWeight[rowCount].disabled=true;

							targetFormName.elements.totalCpWeight[rowCount].disabled=true;
							targetFormName.elements.totalEmsWeight[rowCount].disabled=true;
							//targetFormName.totalSalWeight[rowCount].disabled=true;
							//targetFormName.totalUldWeight[rowCount].disabled=true;
							flag="";
							//change(this);
							}
							if(flag=="ems"){
							targetFormName.elements.totalLcWeight[rowCount].disabled=true;
							targetFormName.elements.totalCpWeight[rowCount].disabled=true;
							targetFormName.elements.totalSvWeight[rowCount].disabled=true;
							//targetFormName.totalSalWeight[rowCount].disabled=true;
							//targetFormName.totalUldWeight[rowCount].disabled=true;

							flag="";
							//change(this);
							}


						/*if(lc[rowCount].value>0.0){
						targetFormName.totalCpWeight[rowCount].disabled=true;
						targetFormName.totalSalWeight[rowCount].disabled=true;
						//targetFormName.totalUldWeight[rowCount].disabled=true;
						targetFormName.totalSvWeight[rowCount].disabled=true;
						change(rowCount);
						break;

						    }
						else if(lc[rowCount].value==0.0){
						targetFormName.totalCpWeight[rowCount].disabled=false;
						targetFormName.totalSalWeight[rowCount].disabled=false;
						//targetFormName.totalUldWeight[rowCount].disabled=false;
						targetFormName.totalSvWeight[rowCount].disabled=false;
						change(rowCount);
						}

						if(cp[rowCount].value>0.0){
						targetFormName.totalLcWeight[rowCount].disabled=true;
						targetFormName.totalSalWeight[rowCount].disabled=true;
						//targetFormName.totalUldWeight[rowCount].disabled=true;
						targetFormName.totalSvWeight[rowCount].disabled=true;
						change(rowCount);
						break;
						    }
						else if(cp[rowCount].value==0.0){
						targetFormName.totalLcWeight[rowCount].disabled=false;
						targetFormName.totalSalWeight[rowCount].disabled=false;
						//targetFormName.totalUldWeight[rowCount].disabled=false;
						targetFormName.totalSvWeight[rowCount].disabled=false;
						change(rowCount);
						}

						if(sal[rowCount].value>0.0){
						targetFormName.totalLcWeight[rowCount].disabled=true;
						targetFormName.totalCpWeight[rowCount].disabled=true;
						//targetFormName.totalUldWeight[rowCount].disabled=true;
						targetFormName.totalSvWeight[rowCount].disabled=true;
						change(rowCount);
						break;
						    }
						else if(sal[rowCount].value==0.0){
						targetFormName.totalLcWeight[rowCount].disabled=false;
						targetFormName.totalCpWeight[rowCount].disabled=false;
						//targetFormName.totalUldWeight[rowCount].disabled=false;
						targetFormName.totalSvWeight[rowCount].disabled=false;
						change(rowCount);
						}

						//if(uld[rowCount].value>0.0){
						//targetFormName.totalLcWeight[rowCount].disabled=true;
						//targetFormName.totalCpWeight[rowCount].disabled=true;
						//targetFormName.totalSalWeight[rowCount].disabled=true;
						//targetFormName.totalSvWeight[rowCount].disabled=true;
						//change(rowCount);
						//break;
						//    }
						//else if(uld[rowCount].value==0.0){
						//targetFormName.totalLcWeight[rowCount].disabled=false;
						//targetFormName.totalCpWeight[rowCount].disabled=false;
						//targetFormName.totalSalWeight[rowCount].disabled=false;
						//targetFormName.totalSvWeight[rowCount].disabled=false;
						//change(rowCount);
						//}

						if(sv[rowCount].value>0.0){
						targetFormName.totalLcWeight[rowCount].disabled=true;
						targetFormName.totalCpWeight[rowCount].disabled=true;
						targetFormName.totalSalWeight[rowCount].disabled=true;
						//targetFormName.totalUldWeight[rowCount].disabled=true;
						change(rowCount);
						break;
						    }
						//else if(uld[rowCount].value==0.0){
						//targetFormName.totalLcWeight[rowCount].disabled=false;
						//targetFormName.totalCpWeight[rowCount].disabled=false;
						//targetFormName.totalSalWeight[rowCount].disabled=false;
						//targetFormName.totalUldWeight[rowCount].disabled=false;
						//change(rowCount);
						//}*/


					}
	}

}

function updateLcStatus(obj) {
	var name =obj.name;

	rowCount = obj.id.split(name)[1];
	rowCount = eval(rowCount);
	var wtLCAO = document.getElementsByName('totalLcWeight');
	var wtCP = document.getElementsByName('totalCpWeight');
	var code = "weight";
	if(validateFields(obj,-1,code,2,true,true,8,4)){
			if(obj.value != null && obj.value.trim().length >0) {
				if(parseFloat(obj.value) >0) {
					wtLCAO[rowCount].value=0.0;
					wtLCAO[rowCount].disabled=true;
				}else {
					if(wtCP[rowCount].value ==0.0 ) {
						wtLCAO[rowCount].disabled=false;
						wtCP[rowCount].disabled=false;
					}
				}
			}
			else{
				obj.value=0;
			}
	}
}

function updateCheck(){
  var chkBoxIds = document.getElementsByName('check');
	    var isChecked = 0;
	    var length= chkBoxIds.length;
		for(var i=0;i<chkBoxIds.length;i++){
			if(chkBoxIds[i].checked){
				isChecked = isChecked + 1;
			}
		}
		if(isChecked != length){
			document.forms[1].allCheck.checked=false;
		}
}

function showOriginLOV(obj){
	var index = obj.id.split(obj.name)[1];
	index = eval(index);
	displayLOV('showStation.do','N','Y','showStation.do',targetFormName.origin.value,'Origin','0','origin','',index);
}

function showDestinationLOV(obj){
	var index = obj.id.split(obj.name)[1];
	index = eval(index);
	displayLOV('showStation.do','N','Y','showStation.do',targetFormName.destination.value,'Destination','0','destination','',index);
}

function change(obj){

	var exgrate=document.getElementsByName("rate");
	var lc=document.getElementsByName("totalLcWeight");
	var cp=document.getElementsByName("totalCpWeight");
	var ems=document.getElementsByName("totalEmsWeight");
	//var sal=document.getElementsByName("totalSalWeight");
	//var uld=document.getElementsByName("totalUldWeight");
	var sv=document.getElementsByName("totalSvWeight");
	var val;
	var operation = new MoneyFldOperations();
	var rowcount = jquery(obj).attr('rowCount');
	targetFormName.elements.rowCount.value=rowcount;
	if(lc[rowcount].value>0.0) {
	val=lc[rowcount].value;
	}

	else if(cp[rowcount].value>0.0) val=cp[rowcount].value;
	//else if(sal[rowcount].value>0.0) val=sal[rowcount].value;
	//else if(uld[rowcount].value>0.0) val=uld[rowcount].value;
	else if(ems[rowcount].value>0.0) val=ems[rowcount].value;
	else if(sv[rowcount].value>0.0) val=sv[rowcount].value;

	//var ans=val*exgrate[rowcount].value;
	//targetFormName.amount[rowcount].value=ans;
	operation.multiplyRawMonies(val,exgrate[rowcount].value,
	targetFormName.elements.blgCurCode.value,'false','onMultiplyAdjAmt');
	updateCpStatus();

}


function onMultiplyAdjAmt(){
	setMoneyValue(targetFormName.elements.amount[targetFormName.elements.rowCount.value],arguments[0]);

}

function showDsnPopup(){

var form=targetFormName;
var dsn=document.getElementsByName("despatchNumber");
check=document.getElementsByName('check');
var count=document.getElementsByName('count');
var checks=0;
count=check.length-1;

for(var i=0;i<check.length-1;i++) {
					checks=checks+1;
					if(dsn[i].value==""){
						//showDialog("Please enter DSN",1,self);
						showDialog({msg:'Please enter DSN',type:1,parentWindow:self});
						return;
					}
	else{
		targetFormName.elements.focFlag.value="Y";
		var actionURL="mailtracking.mra.airlinebilling.capturecn66.showdsnpopup.do?count="+count;
		submitForm(targetFormName,actionURL);
	}
}

}

function update(){

	var dsn=document.getElementsByName("despatchNumber");
	var check=document.getElementsByName('check');
	//var count=document.getElementsByName('count');
	//count=targetFormName.count.value;
	var count=check.length-1;
	var frmPg="FromCN66Details";
	for(var i=0;i<check.length;i++) {
		//if(check[i].checked=="true"){
			if(targetFormName.elements.showDsnPopUp.value=="true"){
				targetFormName.elements.showDsnPopUp.value="false";
				openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do?code="+dsn[count-1].value+"&fromPage="+frmPg,725,450);
				return;
			}
		//}
	}
	var despatchDate = document.getElementsByName('despatchDate');
	if(despatchDate != null && despatchDate.length > 1) {
		despatchDate[despatchDate.length - 2].focus();
	}
	var mailCategoryCode = document.getElementsByName('mailCategoryCode');
	var despatchDate = document.getElementsByName('despatchDate');
	var origin = document.getElementsByName('origin');
//alert('targetFormName.focFlag.value'+targetFormName.focFlag.value);
if(targetFormName.elements.focFlag.value=="Y"){

		if(despatchDate != null && despatchDate.length > 1) {
			despatchDate[despatchDate.length - 2].focus();
		}
targetFormName.elements.focFlag.value="N";
}else{

	if(mailCategoryCode != null && mailCategoryCode.length > 1 ) {
		if(mailCategoryCode[mailCategoryCode.length - 2].disabled==false){

		 	mailCategoryCode[mailCategoryCode.length - 2].focus();
	 }else if(origin!= null && origin.length > 1 ){
		 if(origin[origin.length - 2].disabled==false){

		 		 	origin[origin.length - 2].focus();
	 }

	 }
	}
}
}





function mailDSN(){
 frm=targetFormName;
 var mailDSNArr =document.getElementsByName("despatchNumber");
 var mailDSN =document.getElementsByName("despatchNumber");
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


function mailRSN(){

 frm=targetFormName;
 var mailRSNArr =document.getElementsByName("receptacleSerialNo");
 var mailRSN =document.getElementsByName("receptacleSerialNo");
   for(var i=0;i<mailRSNArr.length;i++){
      if(mailRSNArr[i].value.length == 1){
          mailRSN[i].value = "00"+mailRSNArr[i].value;
      }
      if(mailRSNArr[i].value.length == 2){
          mailRSN[i].value = "0"+mailRSNArr[i].value;
      }
   }
}