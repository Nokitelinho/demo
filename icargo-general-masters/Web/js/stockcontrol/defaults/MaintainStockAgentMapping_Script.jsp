<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>

function screenSpecificEventRegister(){

   var frm=targetFormName;
	//onScreenloadSetHeight();
	with(frm){

		evtHandler.addEvents("stockHolders","validateFields(this,-1,'Stockholder Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("agents","validateFields(this,-1,'Agent Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("stockHolder","validateFields(this,-1,'Stockholder Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("agent","validateFields(this,-1,'Agent Code',0,true,true)",EVT_BLUR);
		evtHandler.addEvents("btClear","selectAction(this.form,'Clear')",EVT_CLICK);
		evtHandler.addEvents("btSave","selectAction(this.form,'Save')",EVT_CLICK);
		evtHandler.addEvents("btList","selectAction(this.form,'List')",EVT_CLICK);
		evtHandler.addEvents("btClose","closeFun()",EVT_CLICK);
		evtHandler.addEvents("btClose","setFocus()",EVT_BLUR);
		evtHandler.addIDEvents("addRow","addRow()",EVT_CLICK);
		evtHandler.addIDEvents("deleteRow","deleteRow()",EVT_CLICK);
		evtHandler.addIDEvents("stockHolderLov","displayStockHolderLov()",EVT_CLICK);
		evtHandler.addIDEvents("agentLov","displayAgentLov()",EVT_CLICK);
		// evtHandler.addEvents("checkbox","checkBox()",EVT_CLICK);
		// evtHandler.addEvents("checkAll","checkAll()",EVT_CLICK);
		if(!(targetFormName.elements.stockHolder.disabled)){
			targetFormName.elements.stockHolder.focus();
		}
	}
}


function onScreenloadSetHeight(){
	var height = document.body.clientHeight;
	document.getElementById('pageDiv').style.height = ((height*85)/100)+'px';
	document.getElementById('div1').style.height = (((height*85)/100)-140)+'px';
	//document.getElementById('pageDiv').style.height = 400+'px';
}


function closeFun(){
	location.href = appPath + "/home.jsp";
}

function setFocus(){
	if(!event.shiftKey){
		if(!(targetFormName.elements.stockHolder.disabled)){
			targetFormName.elements.stockHolder.focus();
		}
	}
}


function selectAction(frm,actionType) {

	if(actionType=="Clear") {

		frm.action="stockcontrol.defaults.clearmaintainstockagentmapping.do";
		frm.submit();

	}

	if(actionType=="List") {

		frm.action="stockcontrol.defaults.listmaintainstockagentmapping.do";
		frm.submit();

	}

	if(actionType=="Save") {

		frm.action="stockcontrol.defaults.savemaintainstockagentmapping.do";
		frm.submit();

	}


}


function addRow() {
	var frm=targetFormName;
	frm.action="stockcontrol.defaults.addrowmaintainstockagentmapping.do";
	frm.submit();
}

function deleteRow() {

	var frm=targetFormName;
	if(validateSelectedCheckBoxes(frm,'checkbox','','1')){
		frm.action="stockcontrol.defaults.deleterowmaintainstockagentmapping.do";
		frm.submit();
	}
}

function submitPage(lastPg, displayPg) {

	targetFormName.elements.lastPageNum.value=lastPg;
	targetFormName.elements.displayPage.value=displayPg;
	targetFormName.action="stockcontrol.defaults.navigatemaintainstockagentmapping.do";
	targetFormName.submit();
}


function displayStockHolderLov(){

	var strAction="stockcontrol.defaults.screenloadstockholderlov.do";
	var stockHolderCode='stockHolder';
	var val=targetFormName.elements.stockHolder.value;
	var stockHolderType='';
	var typeVal='';
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically starts
		var clientHeight = document.body.clientHeight;
		var clientWidth = document.body.clientWidth;
		
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically ends
	openPopUp(strUrl,650,500);
}


function displayAgentLov(){

//var paramCode=targetFormName.agent.value;
//alert(paramCode);
//displayLOV('shared.defaults.agent.screenloadagentlov.do','N','Y','shared.defaults.agent.screenloadagentlov.do',paramCode,'','1','agent','','0');
	var textfiledDesc="";

	var code = targetFormName.elements.agent.value;
	var strAction="shared.defaults.agent.screenloadagentlov.do";
	var StrUrl=strAction+"?textfiledObj=agent&formNumber=1&textfiledDesc=&customerCode=&customerName="+"&agentCode="+code;
/*
* Modified by A-1944 on 17th May 2007
* Not to set the attribute scrollbars for popups
*/
//modified by A-5221 for ICRD-41909 
//var myWindow = window.open(StrUrl, "LOV", 'width=500,height=400,screenX=100,screenY=30,left=250,top=100')
	var myWindow = openPopUp(StrUrl,'500','400');
}

function checkBox() {
alert("HHHHHlllllooooo");
	var checkboxes=document.getElementsByName("checkbox");
	var checkAll=IC.util.dom.getElementById("checkAll");
	var count=0;
	for(var i=0;i<checkboxes.length;i++) {
		if(checkboxes[i].checked)
			count++;
		else{
			count=0;
			break;
		}
	}
	if(count>0){
		checkAll.checked=true;
	}else{
		checkAll.checked=false;
	}
}


function checkAll() {

	var checkboxes=document.getElementsByName("checkbox");
	var checkAll=IC.util.dom.getElementById("checkAll");

	if(checkAll.checked) {
		for(var i=0;i<checkboxes.length;i++)
			checkboxes[i].checked=true;
	}else{
		for(var i=0;i<checkboxes.length;i++)
			checkboxes[i].checked=false;
	}
}


function displayStockHoldersLov(rowCount) {

	var strAction="stockcontrol.defaults.screenloadstockholderlov.do";
	var stockHolderCode='stockHolders['+rowCount+']';
	var stockHoldersArray=document.getElementsByName("stockHolders");
	var val=stockHoldersArray[rowCount].value;
	var stockHolderType='';
	var typeVal='';
	if(rowCount=="0" && stockHoldersArray.length==1)
		var strUrl = strAction+"?code="+val+"&codeName=stockHolders&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	else
		var strUrl = strAction+"?code="+val+"&index="+rowCount+"&codeName=stockHolders&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically starts
		/**	var clientHeight = document.body.clientHeight;
			var clientWidth = document.body.clientWidth;
			var _reqWidth=(clientWidth*45)/100;
			var _reqHeight = (clientHeight*50)/100; */
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically ends
	//openPopUp(strUrl,_reqWidth,_reqHeight);
	openPopUp(strUrl,650,500);


}

function displayAgentsLov(rowCount) {

var textfiledDesc="";
var agentArray=document.getElementsByName("agents");
var code = agentArray[rowCount].value;
var strAction="shared.defaults.agent.screenloadagentlov.do";
if(rowCount=="0" && agentArray.length==1)
	var StrUrl=strAction+"?textfiledObj=agents&formNumber=1&textfiledDesc=&customerCode=&customerName="+"&agentCode="+code;
	else
	var StrUrl=strAction+"?textfiledObj=agents["+rowCount+"]&formNumber=1&textfiledDesc=&customerCode=&customerName="+"&agentCode="+code;
/*
* Modified by A-1944 on 17th May 2007
* Not to set the attribute scrollbars for popups
*/
//modified by A-5221 for ICRD-41909 
//var myWindow = window.open(StrUrl, "LOV", 'width=500,height=400,screenX=100,screenY=30,left=250,top=100')
var myWindow=openPopUp(StrUrl,'500','400');

}
