<%@ include file="/jsp/includes/js_contenttype.jsp" %>

<%@ include file="/jsp/includes/tlds.jsp" %>
var rangeMap = new Hashtable();
function screenSpecificEventRegister(){
//onScreenloadSetHeight();
	var frm=targetFormName;
	//Bug ID : ICRD-14963 - Jeena - Added to get the reference of Monitor Stock Screen-STK007 even other screen navigation is done
	//commented by A-5117 for the issue of ICRD-18458 
	//frm.fromMonitorStock.value = 'Y';
	with(frm){

		evtHandler.addEvents("stockHolderCode","validateFields(this,-1,'Stock Holder Code',0,true,true)",EVT_BLUR);
		evtHandler.addIDEvents("stockHolderCodeLov","displayStockHolderLov('stockcontrol.defaults.screenloadstockholderlov.do')",EVT_CLICK);
		evtHandler.addEvents("btnList","onClickList()",EVT_CLICK);
		evtHandler.addEvents("btnClear","onClickClear()",EVT_CLICK);
		if(frm.elements.check){
			evtHandler.addEvents("check","singleSelect(this)",EVT_CLICK);
		}
		evtHandler.addEvents("btnMonitor","onclickMonitorStock('stockcontrol.defaults.listmonitorstock.do')",EVT_CLICK);
		evtHandler.addEvents("btnViewRange","onclickViewRange('stockcontrol.defaults.screenloadviewrange.do')",EVT_CLICK);
		evtHandler.addEvents("return","onclickPopups('stockcontrol.defaults.screenloadreturnstockrange.do','return')",EVT_CLICK);
		evtHandler.addEvents("delete","onclickPopups('stockcontrol.defaults.screenloaddeletestockrange.do','delete')",EVT_CLICK);
		evtHandler.addEvents("transfer","onclickPopups('stockcontrol.defaults.screenloadtransferstockrange.do','transfer')",EVT_CLICK);
		evtHandler.addEvents("btnCreateStock","onclickPopUpCreateStock()",EVT_CLICK);
		evtHandler.addEvents("btnAllocateStock","onclickAllocateStock()",EVT_CLICK);
		evtHandler.addEvents("btnCreateRequest","onclickCreateRequest()",EVT_CLICK);
		//evtHandler.addEvents("btnClose","location.href('home.jsp')",EVT_CLICK);
		evtHandler.addEvents("btnClose","onclickClose()",EVT_CLICK);
		evtHandler.addEvents("btnPrint","onclickPrint()",EVT_CLICK);
		evtHandler.addEvents("partnerAirline","showPartnerAirlines()",EVT_CLICK);
		evtHandler.addEvents("awbPrefix","populateAirlineName()",EVT_CHANGE);
		evtHandler.addEvents("docType","onChangeOfDocTyp()",EVT_CHANGE);
	}
	
	/*
		Manage awbPrefix combo on load
	*/
	showPartnerAirlines();
	
	collapseAllRows();
	setFocus();
	onReLoad();
	setContextPath();
//	DivSetVisible(true);
	//onChangeOfDocTyp();

		if(targetFormName.elements.reportGenerateMode.value == "generatereport") {		
			generateReport(targetFormName,"/stockcontrol.defaults.monitorstock.generatestockallocationreport.do?reportGenerateMode="+targetFormName.elements.reportGenerateMode.value);	
			targetFormName.elements.reportGenerateMode.value="";				
		}
	
	
	
}

function onScreenloadSetHeight(){
	var height = document.body.clientHeight;
	var MainDivHeight=((height*95)/100);
	document.getElementById('pageDiv').style.height = MainDivHeight+'px';
	//document.getElementById('div1').style.height = (MainDivHeight-330)+'px';
}

function setContextPath(){
	var temp = '<%=request.getContextPath()%>';
	contextPathForTreeTable(temp);
}

/**
 * This function submits the form to preserve the current selected values while clicking next and prev.
 *
 * @param strlastPageNum - The last page number
 * @param strDisplayPage - The page which has to be displayed.
 */

function singleSelect(checkVal){
	var checkedObj = eval(checkVal);
	singleSelectCb(targetFormName,checkedObj.value,checkedObj.name);

}

function onClickList(){
	if(targetFormName.elements.stockHolderCode.value==""){
		//alert('Stock Holder Code is mandatory');
		showDialog({	
				msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.stockholdercodeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
						 targetFormName.elements.stockHolderCode.focus();
					}
			});
		
		return;
	}
  	if(targetFormName.elements.docType.value==""){
		//alert('Document type is mandatory');
		showDialog({	
				msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.documenttypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
						 targetFormName.elements.docType.focus();
					}
			});
		
		return;
	}
 	if(targetFormName.elements.subType.value==""){
		//alert('Document sub type is mandatory');
		showDialog({	
				msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.documentsubtypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
						 targetFormName.elements.subType.focus();
					}
			});
		
		return;
	}
   // targetFormName.action="stockcontrol.defaults.listmonitorstock.do";
	//targetFormName.submit();
	//disablePage();
	targetFormName.elements.displayPage.value=1;
    targetFormName.elements.lastPageNum.value=0;
	submitForm(targetFormName,"stockcontrol.defaults.listmonitorstock.do");
}

function onClickClear(){
	//targetFormName.action="stockcontrol.defaults.clearmonitorstock.do";
	//targetFormName.submit();
	//disablePage();
	submitForm(targetFormName,"stockcontrol.defaults.clearmonitorstock.do");
}

function setFocus(){
	targetFormName.elements.stockHolderType.focus();
}

function onclickPopUp(strAction,str){
	if(str=="delete"){
    	openPopUp(strAction,700,600);
  	}
 	if(str=="transfer"){
    	openPopUp(strAction,700,680);
  	}
  	if(str=="return"){
    	openPopUp(strAction,700,620);
  	}
}

function displayStockHolderLov(strAction){
	var stockHolderCode='stockHolderCode';
	var stockHolderType='stockHolderType';
	var val=targetFormName.elements.stockHolderCode.value;
	var typeVal=targetFormName.elements.stockHolderType.value;
	var strUrl = strAction+"?code="+val+"&codeName="+stockHolderCode+"&stockHolderTypeValue="+typeVal+"&typeName="+stockHolderType;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically starts
	var clientHeight = document.body.clientHeight;
	var clientWidth = document.body.clientWidth;
	var _reqWidth=(clientWidth*45)/100;
	var _reqHeight = (clientHeight*50)/100;
	//Added by A-2850 on 15-oct-07 for setting width and height dynamically ends
	openPopUp(strUrl,_reqWidth,_reqHeight);
}

function onclickPopups(strAction,str){
	var isChecked = 'N';
	var code;
	if(targetFormName.elements.check){
	  	if(!targetFormName.elements.check.length){
	     	if(targetFormName.elements.check.checked==false){
		   		//alert("Please select 1 row(s)");
				showDialog({	
					msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonerow" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					
				});
		   		return;
	        }else{
	           	code = targetFormName.elements.check.value;
	           	isChecked = 'Y';
	    	}
	    }else{
	     	for(var i=0;i<targetFormName.elements.check.length;i++){
	     		if(targetFormName.elements.check[i].checked==true ){
	     	  		if( i>0&& isChecked == 'Y'){
	     	  			//alert("Please select only one row");
						showDialog({	
							msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonlyonerow" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
							
						});
	     	   			return;
					}
					isChecked = 'Y';
					code =  targetFormName.elements.check[i].value;
	      		}
	    	}
	    }
	}
	if(isChecked == 'Y'){
	  	if(str=="transfer" || str=="return" || str=="delete"){
	  		if(code=="HQ"){
	  			//alert('Cannot '+str+' from Headquarters');
	  			var msg = '<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.cannot" scope="request"/>'+str+"  "+'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.fromheadquaters" scope="request"/>';
	  			
				showDialog({	
							msg		:	msg,
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
							
						});
	  			return;
	  		}
	  	}
		strAction=strAction+"?stockHolder="+code+"&&documentRange="+targetFormName.elements.documentRange.value;		
	}else{
	   //alert("Please select 1 row(s)");
	   showDialog({	
			msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonerow" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
			
		});
	   return;
	}
	onclickPopUp(strAction,str);
}

function onclickCreateRequest(){
	targetFormName.elements.buttonStatusFlag.value = "fromMonitorStock";
    //targetFormName.action="stockcontrol.defaults.screenloadmaintainstockreq.do";
   // targetFormName.action="stockcontrol.defaults.createstockrequest.do";
	//targetFormName.submit();
	//disablePage();
	submitForm(targetFormName,"stockcontrol.defaults.createstockrequest.do");
}

function onclickAllocateStock(){
	var isChecked = 'N';
	var code;
	targetFormName.elements.buttonStatusFlag.value = "fromMonitorStock";
	if(targetFormName.elements.check){
	  	if(!targetFormName.elements.check.length){
	     	if(targetFormName.elements.check.checked==false){
		   		//alert("Please select 1 row(s)");
				showDialog({	
					msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonerow" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					
				});
		   		return;
	        }else{
	           	code = targetFormName.elements.check.value;
	           	isChecked = 'Y';
	    	}
	    }else{
	     	for(var i=0;i<targetFormName.elements.check.length;i++){
	     		if(targetFormName.elements.check[i].checked==true ){
	     	  		if( i>0&& isChecked == 'Y'){
	     	   			//alert("Please select only one row");	     	   			
						showDialog({	
							msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonlyonerow" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
							
						});
	     	   			return;
					}
					isChecked = 'Y';
					code =  targetFormName.elements.check[i].value;
	      		}
	    	}
	    }
	}
	if(isChecked == 'Y'){
		if(code=="HQ"){
			showDialog({	
				msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.cannotallocatetohq" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				
			});
			return;
		}else{
			strAction="stockcontrol.defaults.monitorallocatestock.do";
			strAction=strAction+"?code="+code;
	 	}
	}else{
	  	//alert("Please select 1 row(s)");
		showDialog({	
			msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonerow" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
			
		});
	    return;
	}
 	targetFormName.action=strAction;
    //	targetFormName.submit();
   // Added as a part of ICRD-251731
	submitForm(targetFormName,strAction);
   	disablePage();
}

function onclickMonitorStock(strAction){
	var isChecked = 'N';
	var code;
	if(targetFormName.elements.check){
		if(!targetFormName.elements.check.length){
			 if(targetFormName.elements.check.checked==false){
				//alert("Please select 1 row(s)");				
				showDialog({	
					msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonerow" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					
				});
				return;
			 }else{
				 code = targetFormName.elements.check.value;
				 isChecked = 'Y';
			 }
		}else{
			for(var i=0;i<targetFormName.elements.check.length;i++){
				if(targetFormName.elements.check[i].checked==true )	     	{
					if( i>0&& isChecked == 'Y'){
						//alert("Please select only one row");						
						showDialog({	
							msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonlyonerow" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
							
						});
						return;
					}
					isChecked = 'Y';
					code =  targetFormName.elements.check[i].value;
				}
			}
		}
	}
	if(isChecked == 'Y'){
		targetFormName.elements.stockHolderType.value="";
	  	targetFormName.action = strAction;
	  	targetFormName.elements.stockHolderCode.value = code;
	}else{
	    //alert("Please select 1 row(s)");	   
		showDialog({	
			msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonerow" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
			
		});
	    return;
	}
	//targetFormName.submit();
// changed as a part of ICRD-250664
	submitForm(targetFormName,strAction);
	disablePage();
}

function onclickPopUpCreateStock(){
	targetFormName.elements.buttonStatusFlag.value = "fromMonitorStock";
    //targetFormName.action="stockcontrol.defaults.createstock.do";
	//targetFormName.submit();
	//disablePage();
	submitForm(targetFormName,"stockcontrol.defaults.createstock.do");

    /*targetFormName.action="stockcontrol.defaults.screenloadcreatestock.do";
	targetFormName.submit();
	disablePage();*/
}

/*function onclickPopUpS(){
    targetFormName.action="stockcontrol.defaults.screenloadblackliststock.do";
	targetFormName.submit();
}*/

function onclickViewRange(strAction){
	var isChecked = 'N';
	var code;
	if(targetFormName.elements.check){
		if(!targetFormName.elements.check.length){
	    	if(targetFormName.elements.check.checked==false){
		   		//alert("Please select 1 row(s)");		   		
				showDialog({	
					msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonerow" scope="request"/>',
					type	:	1, 
					parentWindow:self,
					parentForm:targetFormName,
					
				});
		   		return;
	        }else{
	           	code = targetFormName.elements.check.value;
	           	isChecked = 'Y';
	    	}
	    }else{
	     	for(var i=0;i<targetFormName.elements.check.length;i++){
	     		if(targetFormName.elements.check[i].checked==true ){
	     	  		if( i>0&& isChecked == 'Y'){
	     	   			//alert("Please select only one row");	     	   			
						showDialog({	
							msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonlyonerow" scope="request"/>',
							type	:	1, 
							parentWindow:self,
							parentForm:targetFormName,
							
						});
	     	   			return;
					}
					isChecked = 'Y';
		 			code =  targetFormName.elements.check[i].value;
	      		}
	    	}
	    }
	}
  	if(isChecked == 'Y'){
		strAction=strAction+"?stockHolder="+code;
	}else{
	   	//alert("Please select 1 row(s)");	    
		showDialog({	
			msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.pleaseselectonerow" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
			
		});
	    return;
	}

    openPopUp(strAction,850,575);
	//var myWindow = window.open(strAction, "LOV", 'scrollbar,width=750,height=350,screenX=100,screenY=30,left=250,top=100');
}

function onReLoad(){
	var code=targetFormName.elements.code.value;
	if(targetFormName.elements.flag.value=="Y"){
		strAction="stockcontrol.defaults.screenloadmonitorallocatestock.do";
		strAction=strAction+"?stockHolderCode="+code+"&status=ALL";
		targetFormName.action = strAction;
		targetFormName.submit();
		disablePage();
	}
	if(targetFormName.elements.flag.value=="N"){
		strAction="stockcontrol.defaults.screenloadmonitorallocatenewstock.do";
		strAction=strAction+"?stockHolderCode="+code;
		targetFormName.action = strAction;
		targetFormName.submit();
		disablePage();
	}
	
}

function submitPage(lastPageNum,displayPage){
	targetFormName.elements.lastPageNum.value = lastPageNum;
	targetFormName.elements.displayPage.value = displayPage;
	if(targetFormName.elements.stockHolderCode.value==""){
			//alert('Stock Holder Code is mandatory');			
			showDialog({	
				msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.stockholdercodeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
						 targetFormName.elements.stockHolderCode.focus();
					}
			});
			
			return;
		}
	  	if(targetFormName.elements.docType.value==""){
			//alert('Document type is mandatory');			
			showDialog({	
				msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.documenttypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
						 targetFormName.elements.docType.focus();
					}
			});
			
			return;
		}
	 	if(targetFormName.elements.subType.value==""){
			//alert('Document sub type is mandatory');			
			showDialog({	
				msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.documentsubtypeismandatory" scope="request"/>',
				type	:	1, 
				parentWindow:self,
				parentForm:targetFormName,
				onClose : function () {
						targetFormName.elements.subType.focus();
					}
			});
			
			return;
		}
	   // targetFormName.action="stockcontrol.defaults.listmonitorstock.do";
		//targetFormName.submit();
		//disablePage();
	submitForm(targetFormName,"stockcontrol.defaults.listmonitorstock.do");
}


function onclickClose(){

//alert('close command');
        var frm = targetFormName;
 	if(frm.elements.fromListMonitor.value=='Y'){
		window.close();

	}else if(frm.elements.fromMonitorStock.value == 'Y'){
		//alert('action from monitor screen');
		var displayPage = frm.elements.displayPage.value;
		var fromMonitorStock="Y";
		var checkList = frm.elements.checkList.value;
		var listFlagFromListScreen = frm.elements.listFlagFromListScreen.value;
		var disableButnFromListScreen = frm.elements.disableButnFromListScreen.value;
		submitForm(frm,"stockcontrol.defaults.liststockholder.do?fromMonitorStock="+fromMonitorStock+"&displayPage="+displayPage+"&checkList="+checkList+"&listFlag="+listFlagFromListScreen+"&disableButn="+disableButnFromListScreen);
	}else{
	  // location.href('home.jsp')   
	  //Added by A-5201 for ICRD-24963
	  //location.href("stockcontrol.defaults.closeMonitorStock.do");
		submitForm(frm,"stockcontrol.defaults.closeMonitorStock.do");	  	 
	}
}

function onclickPrint(){
	if(targetFormName.elements.stockHolderCode.value==""){
		//alert('Stock Holder Code is mandatory');		
		showDialog({	
			msg		:	'<common:message bundle="monitorstockresources" key="stockcontrol.defaults.monitorstock.stockholdercodeismandatory" scope="request"/>',
			type	:	1, 
			parentWindow:self,
			parentForm:targetFormName,
			onClose : function () {
						 targetFormName.elements.stockHolderCode.focus();
					}
		});
		
		return;
	}	
	generateReport(targetFormName,"/stockcontrol.defaults.generateawbstockreport.do");  
	
	
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
var strAction='stockcontrol.defaults.documentrange.do';
var oldOne=null;
oldOne = rangeMap.get(targetFormName.elements.docType.value);
	if(!oldOne){	
		asyncSubmit(targetFormName,strAction,__extraFn,null);
	} else {	
		targetFormName.elements.documentRange.value=oldOne;
	}
}
function stateChange(tableinfo) {
targetFormName.elements.documentRange.value = tableinfo.document.getElementById('documentRange').innerHTML;
rangeMap.put(targetFormName.elements.docType.value,targetFormName.elements.documentRange.value);
}