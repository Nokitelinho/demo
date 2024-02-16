import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {reset} from 'redux-form';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

export function toggleFilter(screenMode) {  
  return {type: 'TOGGLE_FILTER',screenMode };
}

export function listMailbagsEnquiry(values) {
    
    const { args, dispatch, getState } = values;
    const state = getState();
    const {displayPage,action} = args;

    const filter  = (state.form.mailbagFilter.values)?state.form.mailbagFilter.values:state.filterReducer.filterValues
    const pageSize = args && args.pageSize ? args.pageSize : state.listmailbagsreducer.pageSize; 
    filter.displayPage=displayPage;
    filter.pageNumber= displayPage;
	filter.pageSize=pageSize;
    const { flightnumber, ...rest } = filter;
    const mailbagFilter={...rest,...flightnumber,flightnumber:flightnumber};
    if(args.orgDesChangeFlag){

        mailbagFilter.mailDestination=null;
        mailbagFilter.mailOrigin=null;
    }
    //let validFilter = validateMailbagFilter(mailbagFilter,dispatch)
    state.form.mailbagtable?dispatch(reset('mailbagtable')):'';
   // if(validFilter){

            const data = {mailbagFilter};
            const url = 'rest/mail/operations/mailbagenquiry/fetchMailbagDetailsForEnquiry';
            return makeRequest({
                url,
                data: {...data}
            }).then(function (response) {

                if (args && args.mode === 'EXPORT') {
                    let mailStatus = new Map();
                    let operationType = new Map();
                    if (!isEmpty(state.commonReducer.oneTimeValues)) {
                        state.commonReducer.oneTimeValues['mailtracking.defaults.mailstatus'].forEach(status => {
                            mailStatus.set(status.fieldValue, status.fieldDescription);
                        });
                        state.commonReducer.oneTimeValues['mailtracking.defaults.operationtype'].forEach(status => {
                            operationType.set(status.fieldValue, status.fieldDescription);
                        });
                    }
                        let mailbags = response.results[0].mailbags;
                        let exportMailbags = { ...response.results[0].mailbags };
                        for (var i = 0; i < mailbags.results.length; i++) {
                            exportMailbags.results[i].flightNumber = mailbags.results[i].carrierCode != ''? mailbags.results[i].carrierCode+''+mailbags.results[i].flightNumber :'';
                            exportMailbags.results[i].latestStatus = mailbags.results[i].latestStatus!=''?mailStatus.get(mailbags.results[i].latestStatus):'';
                            exportMailbags.results[i].operationalStatus=mailbags.results[i].operationalStatus!=''?operationType.get(mailbags.results[i].operationalStatus):'';
                            if(mailbags.results[i].shipmentPrefix || mailbags.results[i].masterDocumentNumber) {
                                exportMailbags.results[i].awb = (mailbags.results[i].shipmentPrefix || '') +'-'+ (mailbags.results[i].masterDocumentNumber || '')
                            }
                           
                        }
                    
                        return exportMailbags;
                }
                handleResponse(dispatch, response,action,data);
                state.form.mailbagtable?dispatch(reset('mailbagtable')):'';
                return response
            })
            .catch(error => {
                return error;
            });
        //}else{
            //return Promise.resolve("Error");            
        //}

}

export function applyMailbagFilter(values) {
    const { dispatch, getState } = values;
    const state = getState(); 
    const mailbagTableFilter  = (state.form.mailbagTableFilter.values)?state.form.mailbagTableFilter.values:{}
    dispatch( { type: 'LIST_FILTER',mailbagTableFilter});
    return Object.keys(mailbagTableFilter).length;
   
}

export const clearFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('mailbagFilter'));
      
    
}

export const onClearMailFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_TABLE_FILTER'});
    dispatch(reset('mailTableFilter'));
      
    
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
        let capFlag = false;
        const pageSize=data.mailbagFilter&&data.mailbagFilter.pageSize?data.mailbagFilter.pageSize:100;
        if(data.mailbagFilter.operationalStatus==='CAP'){
            capFlag = true;
        }
        if (action==='LIST') {
           dispatch( { type: 'LIST_SUCCESS', pageSize:pageSize,mailbags,data, summaryFilter, capFlag });
        }
       
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: 'NO_DATA',data});
        }
    }
}

export function listMailbagsEnquiryOnNavigation(values) {
    
    const { args, dispatch, getState } = values;
    const state = getState();
    const action = 'LIST';
    const filter  = (state.filterReducer.filterValues)?state.filterReducer.filterValues:state.form.mailbagFilter.values
    const mailBagsToList = (state.filterReducer.filterValues.mailbagIdsFromInbound)?state.filterReducer.filterValues.mailbagIdsFromInbound:[]; 	//Added by A-8164 for mailinbound 

    filter.displayPage="1";
    filter.pageNumber= "1";

    const { flightnumber, ...rest } = filter;
    const mailbagFilter={...flightnumber,...rest,mailBagsToList,flightnumber};

    let validFilter = validateMailbagFilter(mailbagFilter,dispatch)
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
				if(response.results && "mail.operations.ux.containerenquiry"===response.results[0].fromScreen && data.mailbagFilter.fromDate===""){
                   data.mailbagFilter.fromDate=response.results[0].mailbagFilter.fromDate;
                   data.mailbagFilter.toDate=response.results[0].mailbagFilter.toDate;
                 if(data.mailbagFilter.scanPort===""){
                    data.mailbagFilter.scanPort=response.results[0].mailbagFilter.scanPort;
                  }
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

function makeSummaryFilter(data){
    
    let filter={};
    let popOver={};
    let count = 0;
    let popoverCount =0;
    
    if(data.mailbagFilter.mailbagId && data.mailbagFilter.mailbagId.length > 0){
        filter={...filter, mailbagId:data.mailbagFilter.mailbagId};
        count++;
    }
    if(data.mailbagFilter.ooe && data.mailbagFilter.ooe.length > 0){
        filter={...filter, ooe:data.mailbagFilter.ooe};
        count++;
    }
    if(data.mailbagFilter.doe && data.mailbagFilter.doe.length > 0){
        filter={...filter, doe:data.mailbagFilter.doe};
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
    if(data.mailbagFilter.flightNumber && data.mailbagFilter.flightNumber.length > 0){
        if(count<6){
            if(data.mailbagFilter.flightNumber==="-1"){
                filter={...filter, flightnumber:data.mailbagFilter.carrierCode};
            }
            else{
            filter={...filter, flightnumber:data.mailbagFilter.carrierCode+ ' ' +data.mailbagFilter.flightNumber};
            }
            count++;
        }else{
            popOver={...popOver, flightnumber:data.mailbagFilter.carrierCode+ ' ' +data.mailbagFilter.flightNumber};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.flightDate && data.mailbagFilter.flightDate.length > 0){
        if(count<6){
            filter={...filter, flightDate:data.mailbagFilter.flightDate};
            count++;
        }else{
            popOver={...popOver, flightDate:data.mailbagFilter.flightDate};
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
    if(data.mailbagFilter.serviceLevel && data.mailbagFilter.serviceLevel.length > 0){
        if(count<6){
            filter={...filter, serviceLevel:data.mailbagFilter.serviceLevel};
            count++;
        }else{
            popOver={...popOver, serviceLevel:data.mailbagFilter.serviceLevel};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.transportServWindowDate && data.mailbagFilter.transportServWindowDate.length > 0){
        if(count<6){
            filter={...filter, transportServWindowDate:data.mailbagFilter.transportServWindowDate};
            count++;
        }else{
            popOver={...popOver, transportServWindowDate:data.mailbagFilter.transportServWindowDate};
            popoverCount++;
        }  
    }
    if(data.mailbagFilter.transportServWindowTime && data.mailbagFilter.transportServWindowTime.length > 0){
        if(count<6){
            filter={...filter, transportServWindowTime:data.mailbagFilter.transportServWindowTime};
            count++;
        }else{
            popOver={...popOver, transportServWindowTime:data.mailbagFilter.transportServWindowTime};
            popoverCount++;
        }  
    }
    const summaryFilter = {filter:filter, popOver:popOver, popoverCount:popoverCount};
    return summaryFilter;
}