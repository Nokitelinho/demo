<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister() {

	var frm = targetFormName;

	with(frm) {

		evtHandler.addEvents("btnOk","Okfn()",EVT_CLICK);
		evtHandler.addIDEvents("btnClose","closefn()",EVT_CLICK);
		evtHandler.addIDEvents("addLink","addfn()",EVT_CLICK);
		evtHandler.addIDEvents("deleteLink","deletefn()",EVT_CLICK);

		evtHandler.addIDEvents("addfltLink","addfltfn()",EVT_CLICK);
		evtHandler.addIDEvents("deletefltLink","deletefltfn()",EVT_CLICK);

		evtHandler.addEvents("originOE","validateFields(this, -1, 'originOE', 1, true, true)",EVT_BLUR);
		evtHandler.addEvents("destinationOE","validateFields(this, -1, 'destinationOE', 1, true, true)",EVT_BLUR);
		evtHandler.addEvents("mailCategory","validateFields(this, -1, 'mailCategory', 1, true, true)",EVT_BLUR);
		evtHandler.addEvents("mailSubClass","validateFields(this, -1, 'mailSubClass', 0, true, true)",EVT_BLUR);

		evtHandler.addEvents("year","validateFields(this, -1, 'year', 3, true, true)",EVT_BLUR);
		//evtHandler.addEvents("dsn","appendZero(targetFormName.elements.dsn)",EVT_BLUR);
		evtHandler.addEvents("receptacleSerialNum","appendZero(targetFormName.elements.receptacleSerialNum)",EVT_BLUR);
		evtHandler.addEvents("noOfMailBag","validateFields(this, -1, 'noOfMailBag', 3, true, true)",EVT_BLUR);

		evtHandler.addEvents("weight","validateNegatives(targetFormName.elements.weight)",EVT_BLUR);
		evtHandler.addEvents("rate","validateNegatives(targetFormName.elements.rate)",EVT_BLUR);

		evtHandler.addEvents("amount","validateNegatives(targetFormName.elements.amount)",EVT_BLUR);

		evtHandler.addEvents("tax","validateNegatives(targetFormName.elements.tax)",EVT_BLUR);
		//evtHandler.addEvents("total","validateNegatives(targetFormName.elements.total)",EVT_BLUR);

		evtHandler.addEvents("mailBag","populatefields(targetFormName.elements.mailBag)",EVT_BLUR);
		evtHandler.addEvents("mailSubClassLov","displayLOV('mailtracking.defaults.subclaslov.list.do','N','Y','mailtracking.defaults.subclaslov.list.do',targetFormName.elements.mailSubClass.value,'Subclass','0','mailSubClass','',0)",EVT_CLICK);

		evtHandler.addEvents("originOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.originOE.value,'originOE','0','originOE','',0)",EVT_CLICK);
		evtHandler.addEvents("destOELov","displayLOV('mailtracking.defaults.oelov.list.do','N','Y','mailtracking.defaults.oelov.list.do',targetFormName.elements.destinationOE.value,'destinationOE','0','destinationOE','',0)",EVT_CLICK);
		evtHandler.addEvents("dsn","openDsnPopUp()",EVT_BLUR);
		// For toggling of CheckBox

		if(document.getElementsByName("rowchk").length>0){
			evtHandler.addEvents("headerChk","updateHeaderCheckBox(this.form,this.form.headerChk,this.form.rowchk)",EVT_CLICK);
			evtHandler.addEvents("rowchk","toggleTableHeaderCheckbox('rowchk',this.form.headerChk)",EVT_CLICK);
		}




	}
	onScreenLoad();
}

function onScreenLoad(){

//alert(targetFormName.elements.showDsnPopUp.value);


if(targetFormName.elements.showDsnPopUp.value=="true"){
////alert("----------------");

//alert("-------------"+targetFormName.elements.dsn.value+targetFormName.elements.date.value);
	var dsn=targetFormName.elements.dsn.value;
	var dat=targetFormName.elements.date.value;
	var frmPg="GpaReporting";

	////alert("-------------");
	targetFormName.elements.showDsnPopUp.value="false";
	openPopUp("mailtracking.mra.defaults.despatchenquiry.popup.do?code="+dsn+"&dsnFilterDate="+dat+"&fromPage="+frmPg,725,300);
}
 flag=targetFormName.elements.screenFlag.value;


		/*if(!targetFormName.elements.date.disabled){

		      targetFormName.elements.date.focus();
		}*/


		targetFormName.elements.date.focus();

		if(targetFormName.elements.dsnFlag.value=="true" && (!(targetFormName.elements.basistype.value=="M" )))

		{
   targetFormName.elements.noOfMailBag.focus();
		}


 	if(targetFormName.elements.popUpStatusFlag.value=="MODIFY"){

 		disableLink(document.getElementById('addLink'));
		disableLink(document.getElementById('deleteLink'));
		disableField(targetFormName.elements.originOELov);
		disableField(targetFormName.elements.destOELov);
		disableField(targetFormName.elements.mailSubClassLov);

 	}

	if(flag == 'mainScreen'){
		targetFormName.elements.screenFlag.value ="";

		window.opener.recreateTableDetails("mailtracking.mra.gpareporting.capturegpareportpopup.closeanddisplaymainscreen.do","div1","refreshParentTable");
		//opener.submitForm(window.opener.targetFormName,"mailtracking.mra.gpareporting.capturegpareportpopup.close.do");

	        window.close();
	}

 }



function displayNext(){


	_selectedVal = arguments[0];
	selectedIndex = arguments[2];

	targetFormName.elements.displayPopUpPage.value  = selectedIndex;

	if(isFormModified()){


	}else{

	}

	submitForm(targetFormName,"mailtracking.mra.gpareporting.capturegpareportpopup.selectnextpage.do");


}


function Okfn(){


	submitForm(targetFormName,"mailtracking.mra.gpareporting.capturegpareportpopup.ok.do");


}


function deletefn(){
	var frm = targetFormName;
	frm.action="mailtracking.mra.gpareporting.capturegpareportpopup.deletepage.do";
	frm.submit();

}

function addfn(){

	targetFormName.elements.action="mailtracking.mra.gpareporting.capturegpareportpopup.addpage.do";
	targetFormName.submit();

}


function closefn(){
		 targetFormName.elements.screenFlag.value ="";
		 opener.submitForm(window.opener.targetFormName,"mailtracking.mra.gpareporting.capturegpareportpopup.close.do");
	         window.close();

}


function validateNegatives(obj){


	if(obj!=targetFormName.elements.weight){
		validateNeg(obj);
	}else{
		validateFields(targetFormName.elements.weight, -1, 'weight', 2, true, true,3,1)
	}

	var total=0;
	var amt=0;
	var taxamt=0;

	var wgt=0;
	var rate=0;
	var totAmt=0;


	       //updating value of amount on tab out
	      if(targetFormName.elements.weight.value!=''){

			wgt=parseFloat(targetFormName.elements.weight.value);
		}

	      if(targetFormName.elements.rate.value!=''){

			rate=parseFloat(targetFormName.elements.rate.value);
		}
	      totAmt=wgt * rate;

	      targetFormName.elements.amount.value =  parseFloat(totAmt).toFixed(2);
	      targetFormName.elements.amount.defaultValue = parseFloat(totAmt).toFixed(2);

	     //updating value of total on tab out

	      if(targetFormName.elements.amount.value!=''){

	      		amt=parseFloat(targetFormName.elements.amount.value);
		}

	      if(targetFormName.elements.tax.value!=''){

			taxamt=parseFloat(targetFormName.elements.tax.value);
		}
	      total=amt + taxamt;

	      targetFormName.elements.total.value =  parseFloat(total).toFixed(2);
	      targetFormName.elements.total.defaultValue = parseFloat(total).toFixed(2);



}

function validateNeg(obj){
	var amount = obj.value;
	    if(obj.value.length > 1) {
		if(amount.charAt(0) == '-') {
			amount	= obj.value.substring(1);
		}
	    }
	targetFormName.elements.amtForValidation.value =  amount;

	if(validateFields(targetFormName.elements.amtForValidation, -1, 'charge', 2, true, false,16,2)==false){

		obj.focus();

	}


}

function populatefields(mail){


	var mailbagstring = mail.value;
	var originE;
	var destE;
	var category;
	var subclass;
	var year;
	var dsn;
	var rsn;
	var hn;
	var indicator;
	var weight;

	if(!mail.disabled) {

	    if(mail.value.length >0) {


			originE= mailbagstring.substring(0,6);
			destE= mailbagstring.substring(6,12);
			category= mailbagstring.substring(12,13);
			subclass= mailbagstring.substring(13,15);
			year= mailbagstring.substring(15,16);
			dsn= mailbagstring.substring(16,20);
			rsn= mailbagstring.substring(20,23);
			hn= mailbagstring.substring(23,24);
			indicator= mailbagstring.substring(24,25);
			weight= mailbagstring.substring(25,29);

			targetFormName.elements.originOE.value =   originE;
			targetFormName.elements.destinationOE.value = destE;
			targetFormName.elements.mailCategory.value = category;
			targetFormName.elements.mailSubClass.value =   subclass;
			targetFormName.elements.year.value =  year;
			targetFormName.elements.dsn.value =  dsn;
			targetFormName.elements.weight.value =  weight/10;

	    }

	 }



}

function addfltfn(){

	action="mailtracking.mra.gpareporting.capturegpareportpopup.addflight.do";
	submitForm(targetFormName,action);

}

function deletefltfn(){

	targetFormName.elements.selectedRows.value = '';
	var check=",";
	var chkbox = document.getElementsByName("rowchk");

	if(chkbox.length > 0){
		if(validateSelectedCheckBoxes(targetFormName,'rowchk','chkbox.length+1','1')){
		      for(var i=0;i<chkbox.length;i++) {
				if(chkbox[i].checked){

					if(check==','){

						check=i;
					}
					else{
						check=check+","+i;

					}
				  }
			}


		targetFormName.elements.selectedRows.value = check;

		action="mailtracking.mra.gpareporting.capturegpareportpopup.deleteflight.do";
		submitForm(targetFormName,action);

		}
	}



}

function appendZero(obj){

	var actualSize=0;

	if(obj==targetFormName.elements.dsn){

	     if(validateFields(targetFormName.elements.dsn, -1, 'DSN', 3, true, true)==false){

		   targetFormName.elements.dsn.focus();

	      }else{
	      	actualSize=4;
	      }

	}else{
	     if(validateFields(targetFormName.elements.receptacleSerialNum, -1, 'RSN', 3, true, true)==false){

		   targetFormName.elements.receptacleSerialNum.focus();

	      }else{
	      	actualSize=3;
	      }
	}

	if(obj.value.length!=0){
		if(obj.value.length < actualSize){
			var diffInLength = actualSize - obj.value.length;
			for(var i=0;i<diffInLength;i++){
				obj.value = '0'+obj.value;
				obj.defaultValue = '0'+obj.defaultValue;
			}
		}
	}

return;

}

/*
//Function for toggling checkbox

function select(){

	toggleTableHeaderCheckbox('rowchk', targetFormName.elements.headerChk);

}

function selectAll(){

	updateHeaderCheckBox(targetFormName, targetFormName.elements.headerChk, targetFormName.elements.rowchk);
}
*/

function openDsnPopUp(){


	appendZero(targetFormName.elements.dsn);
	action="mailtracking.mra.gpareporting.capturegpareportpopup.showdsnpopup.do";
	submitForm(targetFormName,action);


}