import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {screenLoad} from './commonaction.js'
import {reset} from 'redux-form';
import {change } from 'icoreact/lib/ico/framework/component/common/form';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

export function maintainconsignment(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const consignmentFilter  = state.form.consignmentFilter.values?state.form.consignmentFilter.values:
        state.listconsignmentreducer.navigationFilter;

//dispatch(reset('consignmentFilter'));
//dispatch(reset('OtherDetails'));
//dispatch(reset('routingdetails'));
//dispatch({ type: 'CLEAR_FILTER'});
 
    const {displayPage,action} = args;
    const pageSize = args && args.pageSize ? args.pageSize : 25;
    const data = {displayPage,pageSize,...consignmentFilter};
    const url = 'rest/mail/operations/consignment/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        if (args && args.mode === 'EXPORT') {

            let mailBagDetails = response.results[0].consignment.mailsInConsignmentPage
            let exportMailbags = {...response.results[0].consignment.mailsInConsignmentPage}
            let exportDataCount = mailBagDetails.results.length;

            for (var i = 0; i < exportDataCount; i++) {
                exportMailbags.results[i].weight= mailBagDetails.results[i].statedWeight
                exportMailbags.results[i].mailbagWeight = mailBagDetails.results[i].statedWeight
                }
            return exportMailbags;
    }
        handleResponse(dispatch, response,action, state, consignmentFilter);
        return response
    })
    .catch(error => {
        return error;
    });


}

export const clearFilter=(values)=> {  
    const {dispatch} =values;
     dispatch({ type: 'CLEAR_FILTER'});
    dispatch(asyncDispatch(screenLoad)());
}

export const onChangeScreenMode=(values)=>{
    const { args, dispatch } = values;
    const mode = args;
    dispatch({ type: 'CHANGE_MODE',mode});
}
function handleResponse(dispatch,response,action, state, consignmentFilter) {
    
    if(!isEmpty(response.results)){
        console.log("Results",response.results[0]);
        const {consignment} = response.results[0];
        let mailbags=consignment.mailsInConsignmentPage?consignment.mailsInConsignmentPage.results:[];
        let mails=[];
        mailbags.forEach(element => {
            element={...element,
                mailbagWeight:{ displayValue:populateWeight(element.statedWeight),roundedDisplayValue: populateWeight(element.statedWeight),displayUnit:element.displayUnit,unitSelect:false, disabled:true},existingMailbag : true};
                mails.push(element);
        });
        mailbags=mails;
        const emptyPage = {actualPageSize: 0,
            defaultPageSize: 25,
            endIndex: 0,
            hasNextPage: false,
            lastPageIndex: 1,
            pageNumber: 1,
            results: [],
            startIndex: 0,
            totalRecordCount: 0
            }
        //mailbags={aaa:mailbags.statedWeight};
        const dataList=consignment.mailsInConsignmentPage?consignment.mailsInConsignmentPage:emptyPage;
        const routingdetails=consignment.consignmentRouting?consignment.consignmentRouting:[];       
        const oneTimeType = response.results[0].oneTimeType;
        const oneTimeSubType = response.results[0].oneTimeSubType;
        const oneTimeCat = response.results[0].oneTimeCat;
        const oneTimeMailServiceLevel = response.results[0].oneTimeMailServiceLevel;
        const oneTimeTransportStage = response.results[0].oneTimeTransportStageQualifier;
        let excelMailbags=consignment.excelMailBags?consignment.excelMailBags:[];
        let mailData=[];
        excelMailbags.forEach(element => { 
               element.statedWeight=populateWeight(element.statedWeight)
                mailData.push(element);
        });
        excelMailbags=mailData;
        const isDomestic = consignment.domestic;
        const oneTimeMailClass  = response.results[0].oneTimeMailClass;
        let view = 'NORMAL_VIEW'
        //response.results[0].pageSize===25?view='EXCEL_VIEW':view='NORMAL_VIEW';
        state.form.mailBagsTable?
        state.form.mailBagsTable.values.mailBagsTable =mailbags:''
    
        if (action==='LIST') {
           dispatch({ type: 'CLEAR_FILTER'});
           dispatch( { type: 'LIST_SUCCESS', mailbags, routingdetails,consignment,oneTimeType,oneTimeSubType,oneTimeCat, dataList, excelMailbags, isDomestic,oneTimeMailClass,view, consignmentFilter,oneTimeMailServiceLevel,oneTimeTransportStage});
        }
       
    }
}

export function save(values) {
    const { dispatch, getState } = values;
    const state = getState();
    const tab=state.listconsignmentreducer.activeMailbagAddTab;
    let consignment=state.listconsignmentreducer.consignmentmodel;
    consignment.mailsInConsignmentPage=null;
    consignment.statedWeight=null;
    let routingDetails = [];
    let isvalidate=false;
    let routingList=state.form.routingdetails?(state.form.routingdetails.values.routingdetails):[];
    state.listconsignmentreducer.routingdetails=routingList;
    for(let i=0;i<routingList.length;i++){
        if( routingList[i].onwardCarrierCode!==undefined&&routingList[i].onwardCarrierCode.length>0&& (routingList[i].onwardFlightNumber===undefined||routingList[i].onwardFlightNumber==='')){
            isvalidate =true;
            break;
    }  
   
    
}
if(isvalidate){
    dispatch(requestValidationError("Type the flight number in the second part of the Flight No field in the Onward Routing section.", ''));
    return Promise.resolve({});
 } 
    if(!consignment.domestic){
    for(let j=0;j<routingList.length;j++){
        if( routingList[j].onwardCarrierCode===undefined&& routingList[j].onwardFlightDate===undefined&& routingList[j].onwardFlightNumber===undefined){
            routingList.splice(j,1) ;
                j--; 
    }
    
}
}

routingList = routingList.map((value) => ({ ...value,operationFlag: value.__opFlag }));
let weightUnit= state.commonReducer.defWeightUnit;
    let deletedRoutingDetails= state.form.routingdetails.values.deleted ? state.form.routingdetails.values.deleted :[];
    deletedRoutingDetails.map((value) => ({ ...value,operationFlag:'D'}));
    routingDetails.push(...routingList);
    routingDetails.push(...deletedRoutingDetails);

    let mailbags = [];
    let mailbagslist =[];
    let deletedMails=[];

    if(tab==="EXCEL_VIEW") {
        state.handsontableReducer.mailBags&&state.handsontableReducer.mailBags.data?
                mailbagslist.push(...state.handsontableReducer.mailBags.data):""
        // mailbagslist = state.handsontableReducer.mailBags ?
        //         state.handsontableReducer.mailBags.insertedRows : null;
        if(state.handsontableReducer.mailBags && state.handsontableReducer.mailBags.insertedRows) {
           let tempMailbags = state.handsontableReducer.mailBags.insertedRows; 
           for(let i=0;i<tempMailbags.length;i++){
            let foundMailbag = mailbagslist.find(mailbag=> {
                return mailbag.mailId ==tempMailbags[i].mailId &&  
                (mailbag.__opFlag == tempMailbags[i].__opFlag || mailbag.operationFlag==tempMailbags[i].operationFlag
                    || mailbag.__opFlag==tempMailbags[i].operationFlag || mailbag.operationFlag==tempMailbags[i].__opFlag
                    || (mailbag.__opFlag === undefined && mailbag.operationFlag === undefined) )

            });
            if(!foundMailbag) {
                mailbagslist.push(tempMailbags[i]) ;
            }
          }
           
         }
        const excelmailbags = state.listconsignmentreducer.excelMailbags;
        mailbags= populateMailbagId(mailbagslist, excelmailbags,weightUnit);  
        let mailservicelevels = state.commonReducer.oneTimeMailServiceLevel;
        let mailserviceLevelsValues=[];
        for(let i=0;i<mailservicelevels.length;i++){
            mailserviceLevelsValues[i]=mailservicelevels[i].fieldDescription;
        }
        const isDomestic = state.listconsignmentreducer.isDomestic;   
        const validObject = validateExcelMailBags(mailbags,mailserviceLevelsValues, isDomestic);
        if (!validObject.valid) {
            dispatch(requestValidationError(validObject.msg, ''));
            return Promise.resolve({}); 
          }

    }
    else {
        mailbagslist=state.form.mailBagsTable?
                        state.form.mailBagsTable.values.mailBagsTable :null
        const isDomestic = state.listconsignmentreducer.isDomestic;               
        if(isDomestic){
            mailbagslist = mailbagslist.map((value) => 
                                ({ ...value,operationFlag: value.__opFlag }));
        }
        else{
            mailbagslist = mailbagslist.map((value) => 
                                ({ ...value,operationFlag: value.__opFlag,statedWeight:value.mailbagWeight.roundedDisplayValue,
                                    displayUnit:value.mailbagWeight.displayUnit }));
        }
        
        deletedMails= state.form.mailBagsTable.values ? state.form.mailBagsTable.values.deleted :[];
        if(deletedMails){
           deletedMails= deletedMails.map((value) => ({ ...value,operationFlag:'D'
            }));
            mailbags.push(...deletedMails);
        }
        mailbags.push(...mailbagslist);
    }
    let operationFlag= "I";
    for(let i=0;i<mailbags.length;i++){
        let data = mailbags[i];
        if(data.operationFlag!=='I'){
            operationFlag= "U";
        }
    }
    let INT_REGEX = /[0-9]/
    if(mailbags != null && mailbags.length > 0){
        for (let i = 0; i < mailbags.length; i++) {
            if (mailbags[i].mailId.length === 29 && !(INT_REGEX).test(mailbags[i].year)) {
                return Promise.reject(new Error("Invalid Mailbag:"+mailbags[i].mailId));
            }
        }
    }
    consignment.operationFlag=operationFlag;
    consignment.consignmentRouting=routingDetails;
    consignment.mailsInConsignment=mailbags;
    consignment.consignmentDate = state.form.OtherDetails.values.consignmentdate;
    consignment.type = state.form.OtherDetails.values.consignmenttype;
    consignment.operation = state.form.OtherDetails.values.flighttype;
    consignment.subType = state.form.OtherDetails.values.consignmentsubtype;
    consignment.remarks = state.form.OtherDetails.values.remarks;
    consignment.excelMailBags=[];
    const data={consignment}
    
    const url = 'rest/mail/operations/consignment/save';
    return makeRequest({
        url,data: { ...data }
    }).then(function (response) {
        if(!isEmpty(response.errors.INFO)){
            state.handsontableReducer && state.handsontableReducer.mailBags ?
                state.handsontableReducer.mailBags.insertedRows=[] : '';
                state.handsontableReducer && state.handsontableReducer.mailBags ?
        		state.handsontableReducer.mailBags.data=[] : '';
        }
        
        return response
    })
        .catch(error => {
            return error;
        });
}

export function saveBuildup(args){
    const {dispatch,state,requestData,action,source} =args;    
    //console.log(requestData);
    
    let url = 'rest/operations/flthandling/exportmanifest/savebuildup';
    return makeRequest({url,
            data: requestData
        }
    ).then(function (response) {
        handleSaveBuildupResponse(dispatch,state,response,action,source);
        return response;
    })
    .catch(error => {
        return error;
    });
}

export function validateMailBagForm(mailBags, routingDetails, isDomestic,activeTab,deletedMailbagslist) {
    let isValid = true;
    let error = ""

    if(mailBags.length===0 && deletedMailbagslist.length===0){
        isValid = false;
        error = "Please enter new mailbag details";
    }
else {
    for(let i=0;i<mailBags.length;i++){
        let data = mailBags[i];
    
    if(!data) {
        isValid = false;
        error = "Please enter new mailbag details"
    }else if(activeTab==='EXCEL_VIEW'){
        if(data.mailId && !data.mailId==="" && data.mailId.length===29){
            isValid = true;
            error = "";
        }
    }
        else{
        if(!data.mailId || data.mailId.indexOf(' ') >= 0){
            isValid = false;
            error = "Invalid mail id."
        }
        if(!data.originExchangeOffice || data.originExchangeOffice==="") {
            isValid = false;
            error = "Type or select the Origin OE(Origin Office of Exchange) in the Mail Details section."
        }else if(!data.destinationExchangeOffice || data.destinationExchangeOffice==="") {
            isValid = false;
            error = "Type or select the Dest OE(Destination Office of Exchange) in the Mail Details section."
        }else if(data.originExchangeOffice.length!==6) {
            isValid = false;
            error = "Origin OE(Origin Office of Exchange) should contain 6 alphabets."
        }else if(data.destinationExchangeOffice.length!==6) {
            isValid = false;
            error = "Dest OE(Destination Office of Exchange) should contain 6 alphabets."
        }else if(!data.mailSubclass || data.mailSubclass==="") {
            isValid = false;
            error = "Type or select the SC(Sub Class)."
        }else if(!data.mailCategoryCode || data.mailCategoryCode==="") {
            isValid = false;
            error = "Select the Cat(category)."
        }else if(!data.mailClass || data.mailClass==="") {
            isValid = false;
            error = "Select the Class."
        }else if(data.mailSubclass.length!==2) {
            isValid = false;
            error = "SC(Sub Class) should contain 2 characters."
        }else if(data.year==="") {
            isValid = false;
            error = "Type the Yr(Year) in the Mail Details section."
        }else if(!data.dsn || data.dsn==="") {
            isValid = false;
            error = "Type the DSN(Despatch Serial Number) in the Mail Details section."
        }else if(data.dsn.length!==4) {
            isValid = false;
            error = "The size of DSN(Despatch Serial Number) should be 4."
        }else if(!data.statedBags || data.statedBags==="") {
            isValid = false;
            error = "Type the Std Bags(Stated Bags) in the Mail Details section."
        }else if(data.mailbagWeight){
            if(!data.mailbagWeight.roundedDisplayValue || data.mailbagWeight.roundedDisplayValue==="") {
            isValid = false;
            error = "The mailbag Wt (Weight) cannot be zero."
        }
    }if(data.statedBags!=="1" && data.statedBags!==1){
                isValid = false;
                error = "The value of Std Bags(Stated Bags) should be one."
            }
        else if(!data.receptacleSerialNumber || data.receptacleSerialNumber==="") {
            isValid = false;
            error = "The size of RSN should be 3."
        }else if(!data.highestNumberedReceptacle || data.highestNumberedReceptacle==="") {
            isValid = false;
            error = "Select the HNI"
        }else if(!data.registeredOrInsuredIndicator || data.registeredOrInsuredIndicator==="") {
            isValid = false;
            error = "Select the RI"
        }else if(data.receptacleSerialNumber.length!==3) {
            isValid = false;
            error = "The size of RSN should be 3."
        }else if(isNaN(data.receptacleSerialNumber)){
                isValid = false;
                error = "The RSN should be a number."
           }else if(isNaN(data.dsn)){
                isValid = false;
                error = "The DSN(Despatch Serial Number) should be a number."
           }else if(data.mailbagWeight){
            if(isNaN(data.mailbagWeight.roundedDisplayValue)){
                isValid = false;
                error = "The weight should be a number."
            }else if(data.mailbagWeight.roundedDisplayValue){
                if(data.mailbagWeight.displayUnit==="K"){
                    if(data.mailbagWeight.roundedDisplayValue>999.9){
                        isValid = false;
                        error = "Invalid weight format."
                    }
                }
                else if(data.mailbagWeight.displayUnit==="L"){
                    if(data.mailbagWeight.roundedDisplayValue>2204){
                        isValid = false;
                        error = "Invalid weight format."
                    }
                }
                else if(data.mailbagWeight.displayUnit==="H"){
                    if(data.mailbagWeight.roundedDisplayValue>9999){
                        isValid = false;
                        error = "Invalid weight format."
                    }
                }
           }
        }
           else if(data.statedWeight&& !isNaN(data.statedWeight)){
                if(data.statedWeight.length>4){
                    isValid = false;
                    error = "Invalid weight format."
                }
            }
           } 

    }
}
    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}

export const getFormData= (values) =>{
    const{ dispatch, getState } = values;
    const state = getState();
    const tab=state.listconsignmentreducer.activeMailbagAddTab;

    let mailbagslist=[];
     let deletedMailbagslist=[];

    if(tab==="EXCEL_VIEW") {
        const insertedRows = state.handsontableReducer.mailBags ?
                                state.handsontableReducer.mailBags.insertedRows : null;
        const data = state.handsontableReducer.mailBags ?
                             state.handsontableReducer.mailBags.data : null; 
                             
        mailbagslist.push(...insertedRows);
        mailbagslist.push(...data);                      
    }
    else {
        mailbagslist=state.form.mailBagsTable?
                        state.form.mailBagsTable.values.mailBagsTable :null
        deletedMailbagslist=state.form.mailBagsTable?
                        state.form.mailBagsTable.values.deleted :null
    }

    let routingList=state.form.routingdetails?(state.form.routingdetails.values.routingdetails):[];

    dispatch({ type: 'FORM_DATA', mailbagslist, routingList,deletedMailbagslist});  
  }

export function validateMailBagFormDomestic(mailBag, routingDetails, activeTab) {
    let isValid = true;
    let error = ""

    
    if(activeTab==='NORMAL_VIEW')
    for(let i=0;i<mailBag.length;i++){
        const currBag = mailBag[i];

        if(!currBag) {
            isValid = false;
            error = "Please enter new mailbag details"
        }else if(!currBag.mailId || currBag.mailId==="") {
            isValid = false;
            error = "Please provide mail id."
        }
        else if (currBag.mailId.length !== 12) {
            isValid = false;
            error = "Please provide valid mail id."
        }
        else{
            const mailId = currBag.mailId;
            const weight = mailId.toString().substring(mailId.length-2);
            if(isNaN(weight)){
                isValid = false;
                error = "The last two characters of mail id should be numbers"
            }
        }
    }
        
        let data = routingDetails[0];
    
    if(!data) {
        isValid = false;
        error = "Please enter routing details"
    }else{
        if(!data.onwardCarrierCode || data.onwardCarrierCode==="") {
            isValid = false;
            error = "Please provide carrier code."
        }else if(!data.onwardFlightNumber || data.onwardFlightNumber==="") {
            isValid = false;
            error = "Please provide flight number."
        }else if(!data.onwardFlightDate || data.onwardFlightDate==="") {
            isValid = false;
            error = "Please provide flight date."
        }else if(!data.pol || data.pol==="") {
            isValid = false;
            error = "Please provide pol."
        }else if(!data.pou || data.pou==="") {
            isValid = false;
            error = "Please provide pou."
        }

}


    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}

export function validateMailBagRowDomestic(mailBags) {
    let isValid = true;
    let error = ""

    let data = mailBags;
        
        if(!data) {
            isValid = false;
            error = "Please enter new mailbag details"
        }else if(!data.mailId || data.mailId==="") {
            isValid = false;
            error = "Please provide mail id."
        }
        else if (data.mailId.length !== 12) {
            isValid = false;
            error = "Please provide valid mail id."
        }
        else{
            const mailId = data.mailId;
            const weight = mailId.toString().substring(mailId.length-2);
            if(isNaN(weight)){
                isValid = false;
                error = "The last two characters of mail id should be numbers"
            }
        }

        let validObject = {
            valid: isValid,
            msg: error
        }
        return validObject;    
}

function populateMailbagId(mailbags, excelmailbags,weightUnit) {


    for(let i=0;i<mailbags.length;i++){
        let selectedMailbagRow = mailbags[i];
        let mailbagId="";
         if(selectedMailbagRow.mailId) {
        if (selectedMailbagRow.mailId.length === 29) {
            selectedMailbagRow.originExchangeOffice=selectedMailbagRow.mailId.substring(0, 6);
            selectedMailbagRow.destinationExchangeOffice=selectedMailbagRow.mailId.substring(6, 12);
            selectedMailbagRow.mailCategoryCode=selectedMailbagRow.mailId.substring(12, 13);
            selectedMailbagRow.mailSubclass=selectedMailbagRow.mailId.substring(13, 15);
            selectedMailbagRow.year=selectedMailbagRow.mailId.substring(15, 16);
            selectedMailbagRow.dsn=selectedMailbagRow.mailId.substring(16, 20);
            selectedMailbagRow.receptacleSerialNumber=selectedMailbagRow.mailId.substring(20, 23);
            selectedMailbagRow.highestNumberedReceptacle=selectedMailbagRow.mailId.substring(23, 24);
            selectedMailbagRow.registeredOrInsuredIndicator=selectedMailbagRow.mailId.substring(24, 25);
            selectedMailbagRow.statedWeight=selectedMailbagRow.mailId.substring(25, 29);
            let weight = populateMailWeight(selectedMailbagRow.statedWeight,weightUnit);
            selectedMailbagRow.statedWeight= weight;
            selectedMailbagRow.statedBags=1;
            selectedMailbagRow.mailClass=selectedMailbagRow.mailSubclass.substring(0, 1);
            selectedMailbagRow.displayUnit = weightUnit;
        }
        }
        else if(selectedMailbagRow.originExchangeOffice && selectedMailbagRow.destinationExchangeOffice && selectedMailbagRow.mailCategoryCode && selectedMailbagRow.mailSubclass &&selectedMailbagRow.year && selectedMailbagRow.dsn && selectedMailbagRow.receptacleSerialNumber) {
            const dsnValue = populateMailDsn(selectedMailbagRow.dsn);
            selectedMailbagRow.dsn = dsnValue;
            const rsn = populateMailRsn(selectedMailbagRow.receptacleSerialNumber);
            selectedMailbagRow.receptacleSerialNumber = rsn;
            mailbagId= mailbagId+selectedMailbagRow.originExchangeOffice +selectedMailbagRow.destinationExchangeOffice +selectedMailbagRow.mailCategoryCode +selectedMailbagRow.mailSubclass +selectedMailbagRow.year + dsnValue + rsn +selectedMailbagRow.highestNumberedReceptacle +selectedMailbagRow.registeredOrInsuredIndicator;
            let mailweight =populateMailBagIdFromWeight(selectedMailbagRow.statedWeight,weightUnit);
            selectedMailbagRow.mailId=mailbagId + mailweight;
            selectedMailbagRow.displayUnit = weightUnit;
            }
    else if(selectedMailbagRow.originExchangeOffice || selectedMailbagRow.destinationExchangeOffice || selectedMailbagRow.mailCategoryCode || selectedMailbagRow.mailSubclass || selectedMailbagRow.year || selectedMailbagRow.dsn || selectedMailbagRow.receptacleSerialNumber){
        
    }else{
        mailbags.splice(i, 1);
        i--;
        continue;
    }
    let existingmailbag = false;
    for(let i=0;i<excelmailbags.length;i++){
        let excelbag = excelmailbags[i];
        if(excelbag.mailSequenceNumber){
            if(excelbag.mailSequenceNumber===selectedMailbagRow.mailSequenceNumber){
                existingmailbag = true;
                break;
            }
        }else{
            continue;
        }
        
    }
    if(existingmailbag===true){
        selectedMailbagRow.operationFlag = 'U';
    }else{
        selectedMailbagRow.operationFlag = 'I';
    }
    const reqDeliveryTime = selectedMailbagRow.reqDeliveryTime;
    if(reqDeliveryTime){
        const rqDlvTimAndDate = reqDeliveryTime.split(" ");
        selectedMailbagRow.requiredDlvDate = rqDlvTimAndDate[0];
        selectedMailbagRow.requiredDlvTime = rqDlvTimAndDate[1];
    }
    

}
    return mailbags;
    }

    function populateMailBagIdWeight(weight,unit){
        let weightResult="";
         if(unit=='H'){
            weightResult=weight;
         }   
         else if(unit=='K'){
            weightResult=weight/10;
         }
         else if(unit=='L'){
             weightResult=weight*4.5392;
         }
         weightResult= Math.round(weightResult);
         weightResult=populateWeight(String(weightResult));
         return weightResult;
    }
    function populateMailBagIdFromWeight(weight,unit){
        let weightResult="";
         if(unit=='H'){
            weightResult=weight;
         }   
         else if(unit=='K'){
            weightResult=weight*10;
         }
         else if(unit=='L'){
             weightResult=weight/4.5392;
         }
         weightResult= Math.round(weightResult);
         weightResult=populateWeight(String(weightResult));
         return weightResult;
    }
 //For Clear filter at filter panel
export const clearPanelFilter=(values)=> {
    const{ dispatch, getState } = values;
    const state = getState();
    const screenMode = state.listconsignmentreducer.screenMode?state.listconsignmentreducer.screenMode:'';
    const routingdetails = state.listconsignmentreducer.routingdetails;
    state.handsontableReducer && state.handsontableReducer.mailBags ?
        		state.handsontableReducer.mailBags.insertedRows=[]:'';
 
    dispatch(reset('consignmentFilter'));
    dispatch(reset('OtherDetails'));
    if(screenMode==='initial'){
        dispatch(reset('routingdetails'));
    }else if(routingdetails.length===0){
        dispatch(reset('routingdetails'));
    }else{
        dispatch(change('routingdetails','form',[]));
    }    
    dispatch({ type: 'CLEAR_FILTER'});
  
    }

    function populateWeight(weight){
        let len = 0;
        if(weight % 1 != 0){
            len = weight.length -1;
        }else{
            len = weight.length;
        }
        if(len == 1){
            weight = "000"+weight;
        }
        if(len == 2){
                    weight = "00"+weight;
        }
        if(len == 3){
                    weight = "0"+weight;
        }
    
        return weight;
    }

function validateExcelMailBags(mailBags,mailserviceLevelsValues, isDomestic) {
        let isValid = true;
        let error = ""
    
        for(let i=0;i<mailBags.length;i++){
            let data = mailBags[i];
        
        if(!data) {
            isValid = false;
            error = "Please enter new mailbag details"
        }else if((data.mailId && data.mailId.length === 12) && isDomestic){
            isValid = true;
            error = "";
        }else{
            if(!data.mailId || data.mailId.indexOf(' ') >= 0){
                isValid = false;
                error = "Invalid mail id."
            }
            if(!data.originExchangeOffice || data.originExchangeOffice==="") {
                isValid = false;
                error = "Type or select the Origin OE(Origin Office of Exchange) in the Mail Details section."
            }else if(!data.destinationExchangeOffice || data.destinationExchangeOffice==="") {
                isValid = false;
                error = "Type or select the Dest OE(Destination Office of Exchange) in the Mail Details section."
            }else if(data.originExchangeOffice.length!==6) {
                isValid = false;
                error = "Origin OE(Origin Office of Exchange) should contain 6 alphabets."
            }else if(data.destinationExchangeOffice.length!==6) {
                isValid = false;
                error = "Dest OE(Destination Office of Exchange) should contain 6 alphabets."
            }else if(!data.mailSubclass || data.mailSubclass==="") {
                isValid = false;
                error = "Type or select the SC(Sub Class)."
            }else if(!data.mailCategoryCode || data.mailCategoryCode==="") {
                isValid = false;
                error = "Select the Cat(category)."
            }else if(!data.mailClass || data.mailClass==="") {
                isValid = false;
                error = "Select the Class."
            }else if(data.mailSubclass.length!==2) {
                isValid = false;
                error = "SC(Sub Class) should contain 2 characters."
            }else if(!data.year || data.year==="") {
                isValid = false;
                error = "Type the Yr(Year) in the Mail Details section."
            }else if(!data.dsn || data.dsn==="") {
                isValid = false;
                error = "Type the DSN(Despatch Serial Number) in the Mail Details section."
            }else if(!data.statedBags || data.statedBags==="") {
                isValid = false;
                error = "Type the Std Bags(Stated Bags) in the Mail Details section."
            }
            else if(data.mailServiceLevel) {
                let serviceFlag=0;
               for(let i=0;i<mailserviceLevelsValues.length;i++){
                   if(data.mailServiceLevel===mailserviceLevelsValues[i]){
                    serviceFlag = 1;
                    break;
                   }
                }
                if(serviceFlag === 0){
                isValid = false;
                error = "Invalid mail service level" }
            }else if(!data.statedWeight || data.statedWeight==="") {
            isValid = false;
            error = "The mailbag Wt (Weight) cannot be zero."
        }else if(data.statedWeight){
            const weight = data.statedWeight;
            const unit = data.displayUnit;
            let weightResult="";
         if(unit=='H'){
            weightResult=weight;
         }   
         else if(unit=='K'){
            weightResult=weight*10;
         }
         else if(unit=='L'){
             weightResult=weight*4.5392;
         }
         weightResult= Math.round(weightResult);
         if(weightResult>9999){
            isValid = false;
            error = "Weight should be between 1Hg and 9999Hg"
         }
    }if(data.statedBags!==1) {
                if(data.statedBags!=="1"){
                    isValid = false;
                    error = "The value of Std Bags(Stated Bags) should be one."
                }
            }if(data.statedBags!=="1" && data.statedBags!==1){
                isValid = false;
                error = "The value of Std Bags(Stated Bags) should be one."
            }
        else if(!data.receptacleSerialNumber || data.receptacleSerialNumber==="") {
            isValid = false;
            error = "The size of RSN should be 3."
        }else if(!data.highestNumberedReceptacle || data.highestNumberedReceptacle==="") {
            isValid = false;
            error = "Select the HNI"
        }else if(!data.registeredOrInsuredIndicator || data.registeredOrInsuredIndicator==="") {
            isValid = false;
            error = "Select the RI"
        }else if(data.receptacleSerialNumber.length!==3) {
                //isValid = false;
                //error = "The size of RSN should be 3."
            }else if(isNaN(data.receptacleSerialNumber)){
                    isValid = false;
                    error = "The RSN should be a number."
               }else if(isNaN(data.dsn)){
                    isValid = false;
                    error = "The DSN(Despatch Serial Number) should be a number."
               }else if(data.mailbagWeight){
                if(isNaN(data.mailbagWeight.roundedDisplayValue)){
                    isValid = false;
                    error = "The weight should be a number."
                }
            }else if(data.statedWeight&& !isNaN(data.statedWeight)){
                if(data.statedWeight.includes(".")){
                    if(data.statedWeight.length>5){
                        isValid = false;
                        error = "Invalid weight format."
                    }
                }else{
                    if(data.statedWeight.length>4){
                        isValid = false;
                        error = "Invalid weight format."
                    }
                }
                    
                }
               }
        }
    
        let validObject = {
            valid: isValid,
            msg: error
        }
        return validObject;
    }

    function populateMailDsn(dsn){
        var digits=dsn.length;
       var count=4-digits;
       for(let k=0;k<count;k++){
        dsn="0"+dsn;
       }
       return dsn;
    }
    
    function populateMailRsn(rsn){
        var digits=rsn.length;
       var count=3-digits;
       for(let k=0;k<count;k++){
        rsn="0"+rsn;
       }
       return rsn;
    }

    function populateMailWeight(weight,unit){
        let weightResult="";
     if(unit=='H'){
            weightResult=weight;
            weightResult= Math.round(weightResult);
         }   
         else if(unit=='K'){
            weightResult=weight/10;
            weightResult= Math.round(weightResult * 10)/ 10;
         }
         else if(unit=='L'){
             weightResult=weight/4.5392;
             weightResult= Math.round(weightResult);
         }
         
         weightResult=populateWeight(String(weightResult));
         return weightResult;
    }