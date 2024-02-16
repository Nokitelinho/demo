import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { openPopup } from 'icoreact/lib/ico/framework/action/openpopup';
import { asyncDispatch, dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions'; 
import {reset} from 'redux-form';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import {closePopupWindow} from 'icoreact/lib/ico/framework/component/common/modal/popuputils';

export function screenLoad(args) {
    const{dispatch}=args;
    const url='rest/mail/operations/mailbagenquiry/fetchDetailsOnScreenload';
    return makeRequest({
        url,data:{}
    }).then(function(response) {
        handleScreenloadResponse(dispatch, response);
        return response;
    })   

}


export function handleScreenloadResponse(dispatch, response) {
    if (response.results) {
        dispatch(screenLoadSuccess(response.results[0]));
       
    }
}
export function screenLoadSuccess(data) {
    return { type: 'SCREENLOAD_SUCCESS', data };
}

export function saveActualWeight(values) {
        const { getState,args } = values;
        const state = getState();
        const index= args.rowIndex;
        const actualweight = args.actualWeight; 
        // let mailbagId="";
        // const mailbags=state.form.mailbagtable?state.form.mailbagtable.values.mailbagtable :null;
       const mailbags=state.listmailbagsreducer.mailbagsdetails&&state.listmailbagsreducer.mailbagsdetails.results?state.listmailbagsreducer.mailbagsdetails.results:null;
        const selectedMailbags=[mailbags[index]];
        selectedMailbags[0].actualWeight = actualweight;
    
        const data = { selectedMailbags };
        const url='rest/mail/operations/mailbagenquiry/saveActualWeight';
         return makeRequest({
            url,data: {...data}
        }).then(function(response) {
            return response;
        }) 
        .catch(error => {
            return error;
        });
        }

        export function validateActualWeight(data) {
            let isValid = true;
            let error = "";
            let isNum=true;
        
            if(data==undefined) {
                isValid = false;
            }else{
                if(data==="") {
                    isValid = false;
                    }
                   else if(isNaN(data)){
                    if(data.includes(".")&& !isNaN(data)){
                        isNum=true; 
                    }
                    else{
                        isNum=false;
                    }
                   } 
                   
                }
        
            let validObject = {
                valid: isValid,
                msg: error,
                numeric:isNum
            }
            return validObject;
        }

        export const warningHandler = (action, dispatch) => {
            switch (action.type) {
                case "__DELEGATE_WARNING_ONOK":
                    if (action.executionContext) {
                        const warningCode = action.warningCode;
                        if (warningCode[0] === "mail.operations.mailbagenquiry.actualweight") {
                            dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                        }
                        if (warningCode[0] ===  "mailtracking.defaults.warn.ulddoesnot.exists" ||
                        warningCode[0] == "mailtracking.defaults.warn.uldisnotinairport" ||
                        warningCode[0] == "mailtracking.defaults.warn.uldisnotinthesystem" ||
                        warningCode[0] == "mailtracking.defaults.warn.uldisnotoperational" ||
                        warningCode[0] == "mail.defaults.warning.uldisnotinthesystem" ||
                        warningCode[0] == "mail.defaults.error.uldisnotinairport" ||
                        warningCode[0] == "mail.defaults.warning.uldisnotinairport" ||
                        warningCode[0] == "mail.defaults.error.uldislost" ||
                        warningCode[0] == "mail.defaults.warning.uldislost" ||
                        warningCode[0] == "mail.defaults.error.uldisnotoperational" ||
                        warningCode[0] == "mail.defaults.warning.uldisnotoperational" ||
                        warningCode[0] == "mail.defaults.error.uldnotinairlinestock" ||
                        warningCode[0] == "mail.defaults.error.uldnotinairlinestock" ||
                        warningCode[0] == "mail.defaults.warning.uldnotinairlinestock" ||
                        warningCode[0] == "mail.defaults.error.uldisnotinthesystem" ) {
                            action.executionContext.args.showWarning='N';
                            dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args));
                        }
                        if (warningCode == "mail.operations.securityscreeningwarning") {
                            action.executionContext.args.screenWarning=true;
                            dispatch(asyncDispatch(action.executionContext.functionRecord)(action.executionContext.args)).then(()=>{
                                dispatch(asyncDispatch(listMailbagsEnquiry)({'displayPage':'1',action:'LIST','orgDesChangeFlag':true}))         
                              });
                        }
                    }
                    break;
                case "__DELEGATE_WARNING_ONCANCEL":
                    if (action.executionContext) {
                        const warningCode = action.warningCode;
                        if (warningCode == 'mail.operations.mailbagenquiry.actualweight') {
                            dispatch(asyncDispatch(listMailbagsEnquiry)({'displayPage':'1',action:'LIST'}));
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        export function openHistoryPopup(values) {
            const { args, dispatch, getState } = values;
            let state = getState();
            let selectedMailbags = [];
            for(let i=0; i<state.listmailbagsreducer.selectedMailbagIndex.length;i++){
                selectedMailbags.push(state.listmailbagsreducer.mailbagsdetails.results[state.listmailbagsreducer.selectedMailbagIndex[i]])
            }
            let data = selectedMailbags[0];
            if(args&&args.rowIdx&&args.rowIdx.index){
                const index = args.rowIdx.index;
                data=state.listmailbagsreducer.mailbagsdetails.results[index];
                let mailbagsSelected=[];
                mailbagsSelected.push(data);
                selectedMailbags=mailbagsSelected;
            }
            let mailbagId=data.mailbagId;
            let mailSequenceNumber=data.mailSequenceNumber;
            let mailBagIds=mailbagId;
            let isPopup = 'Y';
            for(let i=1;i<selectedMailbags.length;i++) {
                mailBagIds=mailBagIds+'-'+selectedMailbags[i].mailbagId;
            } 
            const fromScreenId='mail.operations.ux.mailbagenquiry';
            var url = 'mail.operations.ux.mbHistory.list.do?formCount=1&mailbagId='+encodeURIComponent(mailbagId)+'&fromScreenId='+fromScreenId+'&mailSequenceNumber='+mailSequenceNumber+'&totalViewRecords='+encodeURIComponent(mailBagIds)+'&isPopUp='+isPopup;
            var closeButtonIds = ['btnClose'];
            var optionsArray = {
            url,
            dialogWidth: "1000",
            dialogHeight: "540",
            closeButtonIds: closeButtonIds,
            popupTitle: 'Mailbag History'
            }
            
            state.listmailbagsreducer.mailbagsdetails?dispatch(dispatchAction(openPopup)(optionsArray)):"";
            
            }

            export function validateMailbags(values) {

            	const { args, dispatch, getState } = values;
                const state = getState();
                const { functionName, rowIdx } = args;
                let selectedMailbags = [];
                if (rowIdx){
                    selectedMailbags.push(state.listmailbagsreducer.mailbagsdetails.results[rowIdx])
                }else{
                    selectedMailbags = (state.listmailbagsreducer.selectedMailbags) ? state.listmailbagsreducer.selectedMailbags : []
                }
                let url = '';
                let status = "";
                if(functionName==='RETURN'){
                    status="RETURNMAIL"
                }else if(functionName==='REASSIGN'){
                    status="REASSIGNMAIL"
                }else if(functionName==='TRANSFER'){
                    status="TRANSFERMAIL"
                }
                const data = { selectedMailbags, status };
            
            
                url = 'rest/mail/operations/mailbagenquiry/validateMailbags';
            
                return makeRequest({
                    url,
                    data: { ...data }
                }).then(function (response) {
                   handleValidationResponse(dispatch, response);
                    return response
                })
                    .catch(error => {
                        return error;
                    });
            
            
            }

export function onApplyMailbagFilter(values) {
    const {dispatch, getState } = values;
    const state = getState();
    const tableFilter  = (state.form.mailbagTableFilter.values)?state.form.mailbagTableFilter.values:{}
    tableFilter.undefined?delete tableFilter.undefined:''; 
    const { flightnumber, ...rest } = tableFilter;
    const mailbagTableFilter={...flightnumber,...rest};
    dispatch( { type: 'LIST_FILTER',mailbagTableFilter});
    return Object.keys(mailbagTableFilter).length;               
}

export const onClearMailbagFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_TABLE_FILTER'});  
    dispatch(reset('mailbagTableFilter'));
}

export function updateSortVariables(data) {
    const { dispatch } = data;
    dispatch({ type: 'UPDATE_SORT_VARIABLE', data: data.args })
}

export function listMailbagsEnquiry(values) {
    
    const { args, dispatch, getState } = values;
    const state = getState();
    const {displayPage,action} = args;

    const filter  = (state.form.mailbagFilter.values)?state.form.mailbagFilter.values:state.filterReducer.filterValues
    
    filter.displayPage=displayPage;
    filter.pageNumber= displayPage;

    const { flightnumber, ...rest } = filter;
    const mailbagFilter={...flightnumber,...rest};

    state.form.mailbagtable?dispatch(reset('mailbagtable')):'';
    let validFilter = validateMailbagFilter(mailbagFilter,dispatch);
    if(validFilter){

            const data = {mailbagFilter};
            const url = 'rest/mail/operations/mailbagenquiry/fetchMailbagDetailsForEnquiry';
            return makeRequest({
                url,
                data: {...data}
            }).then(function (response) {

                if (args && args.mode === 'EXPORT') {
                        let mailbags = response.results[0].mailbags
                    
                        return mailbags;
                }
                handleResponse(dispatch, response,action,data);
                return response
            })
            .catch(error => {
                return error;
            });
        }else{
            return Promise.resolve("Error");            
        }

}

function validateMailbagFilter(mailbagFilter,dispatch) {
    let isValid = true;
    let error = ""
    if (mailbagFilter.operationalStatus && mailbagFilter.operationalStatus === 'NAR') {
         if (!mailbagFilter.airport || mailbagFilter.airport.trim().length == 0) {
            isValid = false;
            error = "Please enter Airport Code"
         }
    } 
    if (!isValid) {
        dispatch(requestValidationError(error, ''));
    }
    return isValid;
}
function handleResponse(dispatch,response,action,data) {
    
    if(!isEmpty(response.results)){
        console.log(response.results[0]);
        const {mailbags} = response.results[0];
        const summaryFilter = makeSummaryFilter(data);
        if (action==='LIST') {
           dispatch( { type: 'LIST_SUCCESS', mailbags,data, summaryFilter });
        }
       
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: 'NO_DATA'});
        }
    }
}

function makeSummaryFilter(data){
    
    let filter={};
    let popOver={};
    let count = 0;
    let popoverCount =0;
    if(data.mailbagFilter.mailbagId && data.mailbagFilter.mailbagId.length > 0){
        filter={...filter, mailbagId:data.mailbagFilter.mailbagId};
        count++;
    }
    if(data.mailbagFilter.mailOrigin && data.mailbagFilter.mailOrigin.length > 0){
        filter={...filter, mailOrigin:data.mailbagFilter.mailOrigin};
        count++;
    }
    if(data.mailbagFilter.mailDestination && data.mailbagFilter.mailDestination.length > 0){
        filter={...filter, mailDestination:data.mailbagFilter.mailDestination};
        count++;
    }
    if(data.mailbagFilter.ooe && data.mailbagFilter.fromDate.ooe > 0){
        filter={...filter, ooe:data.mailbagFilter.ooe};
        count++;
    }
    if(data.mailbagFilter.doe && data.mailbagFilter.doe.length > 0){
        filter={...filter, doe:data.mailbagFilter.doe};
        count++;
    }
    if(data.mailbagFilter.mailCategoryCode && data.mailbagFilter.mailCategoryCode.length > 0){
        filter={...filter, mailCategoryCode:data.mailbagFilter.mailCategoryCode};
        count++;
    }
    if(data.mailbagFilter.mailSubclass && data.mailbagFilter.mailSubclass.length > 0){
        filter={...filter, mailSubclass:data.mailbagFilter.mailSubclass};
        count++;
    }
    if(data.mailbagFilter.despatchSerialNumber && data.mailbagFilter.despatchSerialNumber.length > 0){
        filter={...filter, despatchSerialNumber:data.mailbagFilter.despatchSerialNumber};
        count++;
    }
    if(data.mailbagFilter.operationalStatus && data.mailbagFilter.operationalStatus.length > 0){
        if(count<6){
            filter={...filter, operationalStatus:data.mailbagFilter.operationalStatus};
            count++;
        }else{
            popOver={...popOver, operationalStatus:data.mailbagFilter.operationalStatus};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.scanPort && data.mailbagFilter.scanPort.length > 0){
        if(count<6){
            filter={...filter, scanPort:data.mailbagFilter.scanPort};
            count++;
        }else{
            popOver={...popOver, scanPort:data.mailbagFilter.scanPort};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.carditStatus && data.mailbagFilter.carditStatus.length > 0){
        if(count<6){
            filter={...filter, carditStatus:data.mailbagFilter.carditStatus};
            count++;
        }else{
            popOver={...popOver, carditStatus:data.mailbagFilter.carditStatus};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.fromDate && data.mailbagFilter.fromDate.length > 0){
        if(count<6){
            filter={...filter, fromDate:data.mailbagFilter.fromDate};
            count++;
        }else{
            popOver={...popOver, fromDate:data.mailbagFilter.fromDate};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.toDate && data.mailbagFilter.toDate.length > 0){
        if(count<6){
            filter={...filter, toDate:data.mailbagFilter.toDate};
            count++;
        }else{
            popOver={...popOver, toDate:data.mailbagFilter.toDate};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.rdtDate && data.mailbagFilter.rdtDate.length > 0){
        if(count<6){
            filter={...filter, rdtDate:data.mailbagFilter.rdtDate};
            count++;
        }else{
            popOver={...popOver, rdtDate:data.mailbagFilter.rdtDate};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.containerNo && data.mailbagFilter.containerNo.length > 0){
        if(count<6){
            filter={...filter, containerNo:data.mailbagFilter.containerNo};
            count++;
        }else{
            popOver={...popOver, containerNo:data.mailbagFilter.containerNo};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.consignmentNo && data.mailbagFilter.consignmentNo.length > 0){
        if(count<6){
            filter={...filter, consignmentNo:data.mailbagFilter.consignmentNo};
            count++;
        }else{
            popOver={...popOver, consignmentNo:data.mailbagFilter.consignmentNo};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.year && data.mailbagFilter.year.length > 0){
        if(count<6){
            filter={...filter, year:data.mailbagFilter.year};
            count++;
        }else{
            popOver={...popOver, year:data.mailbagFilter.year};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.receptacleSerialNumber && data.mailbagFilter.receptacleSerialNumber.length > 0){
        if(count<6){
            filter={...filter, receptacleSerialNumber:data.mailbagFilter.receptacleSerialNumber};
            count++;
        }else{
            popOver={...popOver, receptacleSerialNumber:data.mailbagFilter.receptacleSerialNumber};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.userID && data.mailbagFilter.userID.length > 0){
        if(count<6){
            filter={...filter, userID:data.mailbagFilter.userID};
            count++;
        }else{
            popOver={...popOver, userID:data.mailbagFilter.userID};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.upuCode && data.mailbagFilter.upuCode.length > 0){
        if(count<6){
            filter={...filter, upuCode:data.mailbagFilter.upuCode};
            count++;
        }else{
            popOver={...popOver, upuCode:data.mailbagFilter.upuCode};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.flightnumber && data.mailbagFilter.flightnumber.length > 0){
        if(count<6){
            filter={...filter, flightnumber:data.mailbagFilter.flightnumber};
            count++;
        }else{
            popOver={...popOver, flightnumber:data.mailbagFilter.flightnumber};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.damageFlag && data.mailbagFilter.damageFlag === true){
        if(count<6){
            filter={...filter, damageFlag:data.mailbagFilter.damageFlag};
            count++;
        }else{
            popOver={...popOver, damageFlag:data.mailbagFilter.damageFlag};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.transitFlag && data.mailbagFilter.transitFlag === true){
        if(count<6){
            filter={...filter, transitFlag:data.mailbagFilter.transitFlag};
            count++;
        }else{
            popOver={...popOver, transitFlag:data.mailbagFilter.transitFlag};
            popoverCount++;
        }  
    }
    const summaryFilter = {filter:filter, popOver:popOver, popoverCount:popoverCount};
    return summaryFilter;
}						   

//Added by A-7929 as part of ICRD-ICRD-325156
export function onCloseFunction(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const params = {
        fromScreen: "MTK057"
    }
   
    //let fromScreen=state.commonreducer.fromScreen;
    let isPopup=state.__commonReducer.screenConfig.isPopup;
    /*let isFromPopUp =state.__commonReducer.screenConfig.tabId;
    if(isFromPopUp==="popupContainer"){
        isPopup='true';
    }  */
    if(fromScreen === 'mail.operations.ux.containerenquiry'){
        navigateToScreen('mail.operations.ux.containerenquiry.defaultscreenload.do',params);
    }
    else if(isPopup == "true") closePopupWindow();
    else{
     navigateToScreen('home.jsp', {});
    }
}

function handleValidationResponse(dispatch, response) {
    
    if(!isEmpty(response.results)){
        const scanDate = response.results[0].scanDate;
        const scanTime = response.results[0].scanTime;
		const paCodes  = response.results[0].postalAdministrations;
        const ownAirlineCode = response.results[0].ownAirlineCode;
        const partnerCarriers = response.results[0].partnerCarriers;        ;
           dispatch( { type: 'VALIDATION_SUCCESS', scanDate, scanTime, paCodes,ownAirlineCode,partnerCarriers});
    }
}