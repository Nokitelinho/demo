<%@ include file="/jsp/includes/js_contenttype.jsp" %>

function screenSpecificEventRegister(){
	var frm=targetFormName;


	with(frm){

	evtHandler.addEvents("btnClose","window.close();",EVT_CLICK);
	evtHandler.addEvents("btnOK","onClickOk()",EVT_CLICK);
	onScreenLoad();

	}
}




function onScreenLoad(){

if(targetFormName.canClose.value=='close'){
targetFormName.canClose.value="";
self.opener.submitForm(self.opener.targetFormName,'uld.defaults.messaging.listucmerrorlog.do?flightValidationStatus=Y');
window.close();
}
}



// Function to Select or deselect second table while selecting first table

function selectCheckWithSameULDNumber(obj){

	var uldNums1=new Array();
	var uldNums2=new Array();

	var selectedNumbers=new Array();
	var deSelectedNumbers=new Array();

	var selectedUldsSecond=new Array();
	var selectedpousSecond=new Array();


	var pous1=document.getElementsByName('pouFirst');
	var pous2=document.getElementsByName('pouSecond');

	var uldNumbers1=document.getElementsByName('uldNumbersFirst');
	var uldNumbers2=document.getElementsByName('uldNumbersSecond');


	for(var i=0;i<uldNumbers1.length;i++){

		var a=(uldNumbers1[i].value+','+pous1[i].value);
		uldNums1[i]=a;
	}

	for(var i=0;i<uldNumbers2.length;i++){

		uldNums2[i]=uldNumbers2[i].value+','+pous2[i].value;
   }


	//var uldNumsFirst=document.getElementsByName('uldNumbersFirst');
	//var uldNumsSecond=document.getElementsByName('uldNumbersSecond');




if(targetFormName.selectedUcmsFirst.length>1){
for(var k=0;k<targetFormName.selectedUcmsFirst.length;k++){
	if(targetFormName.selectedUcmsFirst[k].checked){
		selectedNumbers[k]=uldNums1[k];
		}

	}
}else{
	if(targetFormName.selectedUcmsFirst.checked){
			selectedNumbers[0]=uldNums1[0];
		}

}


if(targetFormName.selectedUcmsFirst.length>1){
for(var m=0;m<targetFormName.selectedUcmsFirst.length;m++){
	if(!targetFormName.selectedUcmsFirst[m].checked){
		deSelectedNumbers[m]=uldNums1[m];
		}

	}
}else{
		if(!targetFormName.selectedUcmsFirst.checked){
		deSelectedNumbers[0]=uldNums1[0];

	}

}


//


if(targetFormName.selectedUcmsSecond.length>1){
for(var k=0;k<targetFormName.selectedUcmsSecond.length;k++){
	if(targetFormName.selectedUcmsSecond[k].checked){
		selectedUldsSecond[k]=uldNumbers2[k].value;
		selectedpousSecond[k]=pous2[k].value;
		}

	}
}else{
	if(targetFormName.selectedUcmsSecond.checked){
			selectedUldsSecond[0]=uldNumbers2[0].value;
			selectedpousSecond[0]=pous2[0].value;
		}
}


// If the same ULD with diffrent POU is already selected
for(var j=0;j<selectedUldsSecond.length;j++){
	if(uldNumbers1[obj.value].value==selectedUldsSecond[j] &&
         pous1[obj.value].value!=selectedpousSecond[j]){
		showDialog("This ULD is already Selected For another POU",2,self,targetFormName,'');
		//alert"This ULD is already Selected For another POU");
		 obj.checked=false;
		return;
	}
}

//



for(i in selectedNumbers){
	for(var j=0;j<uldNums2.length;j++){

		if(selectedNumbers[i]==uldNums2[j]){
			if(uldNums2.length==1){
			targetFormName.selectedUcmsSecond.checked=true;
			}else{
			targetFormName.selectedUcmsSecond[j].checked=true;
			}

		}

	}

}

	for(p in deSelectedNumbers){
		for(var l=0;l<uldNums2.length;l++){
			if(deSelectedNumbers[p]==uldNums2[l]){
				if(uldNums2.length==1){
							targetFormName.selectedUcmsSecond.checked=false;
			}else{
				targetFormName.selectedUcmsSecond[l].checked=false;
			}

			}

		}

	}
}


function selectCheckWithSameULDNumber2(obj){


var uldNums1=new Array();
var uldNums2=new Array();

var selectedNumbers=new Array();
var deSelectedNumbers=new Array();

var selectedUldsFirst=new Array();
var selectedpousFirst=new Array();

var pous1=document.getElementsByName('pouFirst');
var pous2=document.getElementsByName('pouSecond');

var uldNumbers1=document.getElementsByName('uldNumbersFirst');
var uldNumbers2=document.getElementsByName('uldNumbersSecond');


for(var i=0;i<uldNumbers1.length;i++){
	uldNums1[i]=uldNumbers1[i].value+','+pous1[i].value;
}

for(var i=0;i<uldNumbers2.length;i++){

	uldNums2[i]=uldNumbers2[i].value+','+pous2[i].value;
}


//var uldNums1=document.getElementsByName('uldNumbersFirst');
//var uldNums2=document.getElementsByName('uldNumbersSecond');


var selectedNumbers=new Array();
var deSelectedNumbers=new Array();



if(uldNums2.length>1){
	for(var k=0;k<targetFormName.selectedUcmsSecond.length;k++){
		if(targetFormName.selectedUcmsSecond[k].checked){
			selectedNumbers[k]=uldNums2[k];
			}
		}
	}else{
		if(targetFormName.selectedUcmsSecond.checked){
				selectedNumbers[0]=uldNums2[0];
		}
}

if(uldNums2.length>1){
	for(var m=0;m<targetFormName.selectedUcmsSecond.length;m++){
		if(!targetFormName.selectedUcmsSecond[m].checked){
			deSelectedNumbers[m]=uldNums2[m];
			}

		}

	}else{
		if(!targetFormName.selectedUcmsSecond.checked){
				deSelectedNumbers[0]=uldNums2[0];
		}
}


//

if(targetFormName.selectedUcmsFirst.length>1){
for(var k=0;k<targetFormName.selectedUcmsFirst.length;k++){
	if(targetFormName.selectedUcmsFirst[k].checked){
		selectedUldsFirst[k]=uldNumbers1[k].value;
		selectedpousFirst[k]=pous1[k].value;
		}

	}
}else{
	if(targetFormName.selectedUcmsFirst.checked){
			selectedUldsFirst[0]=uldNumbers1[0].value;
			selectedpousFirst[0]=pous1[0].value;
		}
}


// If the same ULD with diffrent POU is already selected in the First Table
for(var j=0;j<selectedUldsFirst.length;j++){
	if(uldNumbers2[obj.value].value==selectedUldsFirst[j] &&
         pous2[obj.value].value!=selectedpousFirst[j]){
		 showDialog("This ULD is already Selected For another POU",2,self,targetFormName,'');
		 //alert("This ULD is already Selected For another POU");
		 obj.checked=false;
		return;
	}
}

//

for(i in selectedNumbers){
		for(var j=0;j<uldNums1.length;j++){
			if(selectedNumbers[i]==uldNums1[j]){
			if(uldNums1.length==1){
						targetFormName.selectedUcmsFirst.checked=true;
				}else{
			targetFormName.selectedUcmsFirst[j].checked=true;
		  }

			}

	}

}

for(p in deSelectedNumbers){
	for(var l=0;l<uldNums1.length;l++){
		if(deSelectedNumbers[p]==uldNums1[l]){
				if(uldNums1.length==1){
							targetFormName.selectedUcmsFirst.checked=false;
			}else{
				targetFormName.selectedUcmsFirst[l].checked=false;
			}

		}

	  }

}

}

function onClickOk(){
	//submitForm(targetFormName,"uld.defaults.messaging.duplicateucmreconcilesave.do")
	openPopUp("msgbroker.message.newmessage.do?openPopUpFlg=UPDATEDESPATCH&hideMessageDetails=Y&hideMessageRemarks=Y&messageType=UCM&targetAction=uld.defaults.messaging.duplicateucmreconcilesave.do",1050,320);
}