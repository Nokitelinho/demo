import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import screenLoad from './commonaction';
import {reset} from 'redux-form';
import {asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError ,clearError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { CLEAR_FILTER, LIST_SUCCESS,RETAIN_VALUES,NO_DATA,MAIL_ICON,BLG_LIST,LIST_BLGSUCCESS,LIST, CONSIGNMENT_LIST, LIST_CONSSUCCESS} from '../constants/constants';
import { RERATE_MAILS, SURCHARGE_DETAILS,TOGGLE_FILTER,CHANGE_DETAILS,AUTO_MCA,UPDATE_BILLING,LIST_ACCOUNTING_ENTRIES} from '../constants/constants';
import { navigateToScreen } from 'icoreact/lib/ico/framework/action/navigateaction';
import { resetTable}  from 'icoreact/lib/ico/framework/component/common/store/commonactions';

export function toggleFilter(screenFilterMode) {  
    return {type: TOGGLE_FILTER,screenFilterMode };
  }

//For Clear 
export const clearFilter=(values)=> {
    const {dispatch} = values;  
    dispatch(clearError());
    dispatch({ type: CLEAR_FILTER });
    dispatch(reset('gpaBillingEntryFilter'));
  
}

//For List
export function listBillingDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
    let defaultPageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize
    const fromBillingList = args.fromBillingList;
    const fromConsignmentList = args.fromConsignmentList;
    let gpaBillingEntryFilter  = (state.form.gpaBillingEntryFilter.values)?state.form.gpaBillingEntryFilter.values:{}
   
    //Added by A-8164 for decreasing the export to excel wait time
    if (args && args.mode === 'EXPORT') {
        gpaBillingEntryFilter={...gpaBillingEntryFilter,exportToExcel:true}
        let maxFetchCount=state.commonReducer.maxFetchCount&&Math.trunc(state.commonReducer.maxFetchCount);
        let totalRecordCount=state.filterReducer.totalRecordCount&&Math.trunc(state.filterReducer.totalRecordCount);
        if(maxFetchCount!=null&&maxFetchCount>0){
            if(defaultPageSize==null||totalRecordCount!=null&&(totalRecordCount/maxFetchCount)>defaultPageSize){
                defaultPageSize=5000;
                if(totalRecordCount<defaultPageSize){
                    defaultPageSize=totalRecordCount;
                }
                else if((totalRecordCount/maxFetchCount)>defaultPageSize){
                    defaultPageSize=Math.trunc(totalRecordCount/maxFetchCount)+1;
                }
            }
        }
        
    }
    gpaBillingEntryFilter.displayPage=displayPage;
    gpaBillingEntryFilter.defaultPageSize=defaultPageSize; 
    const data = {gpaBillingEntryFilter,fromBillingList,fromConsignmentList};
    const url = 'rest/mail/mra/gpabilling/gpabillingenquiry/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        if (args && args.mode === 'EXPORT') {
            let mailbags = response&&response.results&&response.results[0]&&response.results[0].gpaBillingEntryDetails?
                response.results[0].gpaBillingEntryDetails:null;
            if(mailbags==null){
                return response
            }
            return mailbags;
        } 
        if (response && response.errors!==null)
            {
                dispatch( { type: NO_DATA,data});
            }     
        handleResponse(dispatch, response,args.mode,data);
        return response
    })
    .catch(error => {
        return error;
    });
 
}

//For consignment listing
export function listConsignmentDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
    const defaultPageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize
    const gpaBillingEntryFilter  = (state.form.gpaBillingEntryFilter.values)?state.form.gpaBillingEntryFilter.values:{}
    gpaBillingEntryFilter.displayPage=displayPage;
    gpaBillingEntryFilter.defaultPageSize=defaultPageSize; 
    const data = {gpaBillingEntryFilter};
    const url = 'rest/mail/mra/gpabilling/gpabillingenquiry/listconsignment';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        if (response && response.errors!==null)
            {
                dispatch( { type: NO_DATA,data});
            }      
        handleResponse(dispatch, response,args.mode,data);
        return response
    })
    .catch(error => {
        return error;
    });
}
//For billing entries pagination
export function listBillingEntriesDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
    const defaultPageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize
    const fromBillingList = args.fromBillingList;
    const fromConsignmentList = args.fromConsignmentList;
    const gpaBillingEntryFilter  = (state.form.gpaBillingEntryFilter.values)?state.form.gpaBillingEntryFilter.values:{}
    //const gpaBillingEntryDetails = state.filterReducer.mailbagsdetails;
    //const consignmentDetails = state.filterReducer.consignmentdetails; 
    gpaBillingEntryFilter.displayPage=displayPage;
    gpaBillingEntryFilter.defaultPageSize=defaultPageSize;
   // const data = {gpaBillingEntryFilter,fromBillingList,fromConsignmentList,gpaBillingEntryDetails,consignmentDetails};
   const data = {gpaBillingEntryFilter,fromBillingList,fromConsignmentList};
    const url = 'rest/mail/mra/gpabilling/gpabillingenquiry/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {   
        handleResponse(dispatch, response,args.mode,data);
        return response
    })
    .catch(error => {
        return error;
    });
}
function handleResponse(dispatch,response,action,data) {
    
    if(!isEmpty(response.results)){
        console.log(response.results[0]);
        const { gpaBillingEntryDetails, consignmentDetails} = response.results[0];
        console.log(gpaBillingEntryDetails); 
        console.log(consignmentDetails); 
        if (action===LIST) {
            const summaryFilter = makeSummaryFilter(data);
            if(gpaBillingEntryDetails !=null ) {
           dispatch( { type: LIST_SUCCESS,gpaBillingEntryDetails,consignmentDetails,data ,summaryFilter});
            }
            else {
                dispatch( { type: NO_DATA,data}); 
            }
        }
        if (action===BLG_LIST){
            if(gpaBillingEntryDetails !=null ) {
           dispatch( { type: LIST_BLGSUCCESS,gpaBillingEntryDetails,consignmentDetails,data });
            }
            else {
                dispatch( { type: NO_DATA,data}); 
            }
        }
           if (action===CONSIGNMENT_LIST){
            if(consignmentDetails !=null ) {
           dispatch( { type: LIST_CONSSUCCESS,gpaBillingEntryDetails,consignmentDetails,data });
            }
            else {
                dispatch( { type: NO_DATA,data}); 
            }
           }
    } else {
        if(!isEmpty(response.errors)){
             dispatch( { type: RETAIN_VALUES});
        }
    }
}

//For Re-Rate
export function reRateMailbags(values){
    const {dispatch, getState } = values;
    const state = getState();
    const url='rest/mail/mra/gpabilling/gpabillingenquiry/rerate';
    let indexes=[]; 
    let data={};
    const gpaBillingEntryDetails = state.filterReducer.mailbagsdetails;
    const consignmentDetails = state.filterReducer.consignmentdetails; 
        indexes=state.filterReducer.selectedMailbagIndex;
    if(indexes == undefined){
         const message = 'Please select a row';
         const target = '';
         dispatch(requestValidationError(message, target));
     }        
    let selectedBillingDetails=[];
    for(var i=0; i<indexes.length;i++) {
        selectedBillingDetails.push(gpaBillingEntryDetails.results[indexes[i]]);
    }
    data ={selectedBillingDetails}    
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        // if(response.results){
        //  const message = 'Re-rate initiated.Please check ProcessLog Screen for Status';
        //  const target = '';
        //   dispatch(requestValidationError(message, target));
        // }
        handleButtonResponse(dispatch, response, RERATE_MAILS); 
        return response;
    })
    .catch(error => {
        return error;
    });
}

//Navigation to DSN Routing
export function navigateToRouting(values){
     const { args,dispatch, getState } = values;
     const state = getState();
     const gpaBillingEntryDetails = state.filterReducer.mailbagsdetails;
     let indexes=[]; 
     indexes=state.filterReducer.selectedMailbagIndex;
     if(indexes == undefined){
         const message = 'Please select a row';
         const target = '';
         dispatch(requestValidationError(message, target));
         return Promise.resolve("Error");  
     }
     if(indexes.length!==1){
        const message = 'Please select single row';
         const target = '';
          dispatch(requestValidationError(message, target));
        return Promise.resolve("Error");  
     }
    let selectedBillingDetails=[];
    let mailbag=[];
    for(var i=0; i<indexes.length;i++) {
        selectedBillingDetails.push(gpaBillingEntryDetails.results[indexes[i]]);
    }
    mailbag = selectedBillingDetails[0].mailbagID;
        const params = {
        ...args,
    dsn:mailbag,
    parentScreen: "LIST BILLING ENTRIES" ,
    fromScreen:'listgpabillingentriesux'
    }

 var url = "mailtracking.mra.defaults.despatchrouting.listdsnpopup.do"
    navigateToScreen(url, params);
}



//A-8331 on navigating to accounting entries
export function navigateToListAccountingEntries(values){
     const { args,dispatch, getState } = values;
     const state = getState();
     const gpaBillingEntryDetails = state.filterReducer.mailbagsdetails;
     let indexes=[]; 
     indexes=state.filterReducer.selectedMailbagIndex;
     if(indexes == undefined){
         const message = 'Please select a row';
         const target = '';
         dispatch(requestValidationError(message, target));
         return Promise.resolve("Error");  
     }
     if(indexes.length!==1){
        const message = 'Please select single row';
         const target = '';
          dispatch(requestValidationError(message, target));
        return Promise.resolve("Error");  
     }
    let selectedBillingDetails=[];
    let mailbag=[];
    for(var i=0; i<indexes.length;i++) {
        selectedBillingDetails.push(gpaBillingEntryDetails.results[indexes[i]]);
    }
    mailbag = selectedBillingDetails[0].mailbagID;
        const params = {
        ...args,
        navigationMode: 'listGPABillingEntries',
        parentScreen: "LIST BILLING ENTRIES" 
    }
 
     var url = "cra.accounting.listaccountingentrieslist.do?dsn="+mailbag
    navigateToScreen(url, params);
}







//Surcharge Details POP UP
export function onSurchargeDetailsPopup(values){
    const {dispatch, getState } = values;
    const state = getState();   
    let indexes=[]; 
    let data={} 
    const gpaBillingEntryDetails = state.filterReducer.mailbagsdetails;
    indexes=state.filterReducer.selectedMailbagIndex;  
     if(indexes == undefined){
         const message = 'Please select a row';
         const target = '';
         dispatch(requestValidationError(message, target));
     }   
     if(indexes.length!==1){
        const message = 'Please select single row';
         const target = '';         
          dispatch(requestValidationError(message, target));
     }
     let selectedBillingDetails=[];
     for(var i=0; i<indexes.length;i++) {
         selectedBillingDetails.push(gpaBillingEntryDetails.results[indexes[i]]);
     }
     data ={selectedBillingDetails}  
     return makeRequest({
             url: 'rest/mail/mra/gpabilling/gpabillingenquiry/surchargedetails',
             data: {...data}
         }).then(function (response) {
             handleButtonResponse(dispatch, response, SURCHARGE_DETAILS);            
             return response;
         })
         .catch(error => {
             return error;
     });
}

function handleButtonResponse(dispatch, response, action){
    if(action === SURCHARGE_DETAILS && !isEmpty(response.results)){
        dispatch({ type: SURCHARGE_DETAILS, data: response.results[0]})
    }
    if(action === CHANGE_DETAILS && !isEmpty(response.results)){
        dispatch({ type: CHANGE_DETAILS, data: response.results[0]})
    } 
    if(action === AUTO_MCA && !isEmpty(response.results)){
        //let filterpath = response&&response.gpaBillingFilter?response.gpaBillingFilter:null;
        let filterpath =response&&response.results[0]&& response.results[0].gpaBillingEntryFilter?response.results[0].gpaBillingEntryFilter:null;
        let path= response&&response.results[0]&&response.results[0].selectedBillingDetails[0] ? response.results[0].selectedBillingDetails[0]:null;
        if ((path!=null) && (filterpath!=null)){
            
        navigateToScreen("mailtracking.mra.defaults.maintaincca.screenload.do?fromScreen=listbillingentriesuxopenPopUp&revGpaCode="+path.gpaCode+
        "&revisedRate="+path.applicableRate+"&revCurCode="+path.currency+"&ccaStatus="+path.billingStatus

        +"&category="+path.category+"&gpaCode="+path.gpaCode+"&rate="+path.applicableRate+"&curCode="+path.currency+
        "&blgFromDate="+filterpath.fromDate+"&blgToDate="+filterpath.toDate+"&blgCsgDocNum="+filterpath.consignmentNumber+
        "&blgBillingStatus="+filterpath.billingStatus+"&blgGpaCode="+filterpath.gpaCode+"&blgOriginOfficeofExchange="+
        filterpath.originOE+"&blgDestinationOfficeofExchange="+filterpath.destinationOE+"&blgOrigin="+filterpath.origin+"&blgDestination="+filterpath.destination+
        "&blgCategory="+filterpath.category+"&blgSubclass="+filterpath.subClass+"&blgYear="+filterpath.year+"&blgDSN="+
        filterpath.dsn+"&blgRSN="+filterpath.rsn+"&blgHNI="+filterpath.hni+"&blgRI="+filterpath.ri+"&blgMailbagId="+filterpath.mailbag+
        "&blgUSPSPerformancemet="+filterpath.uspsMailPerformance+"&blgratebasis="+filterpath.rateBasis+"&blgTotalRecords="+filterpath.totalRecords+
        "&blgDefaultPageSize="+filterpath.defaultPageSize+"&blgDisplayPage="+filterpath.displayPage
        +"&blgpaBuilt="+filterpath.paBuilt
        );
    }
}
    if(action === UPDATE_BILLING && !isEmpty(response.results)){
        dispatch({ type: UPDATE_BILLING, data: response.results[0]})
    }
    if(action === RERATE_MAILS&& !isEmpty(response.results)){
        dispatch({ type: RERATE_MAILS, data: response.results[0]})
        dispatch(resetTable('mailbagtable'));
    }
}

//Change Status POP UP
export function onChangeStatusPopup(values){
    const {dispatch, getState } = values;
    const state = getState();   
    let indexes=[]; 
    let data={} 
    const gpaBillingEntryDetails = state.filterReducer.mailbagsdetails;
   const gpaBillingEntryFilter  = (state.form.gpaBillingEntryFilter.values)?state.form.gpaBillingEntryFilter.values:{}    
    indexes=state.filterReducer.selectedMailbagIndex;  
     if(indexes == undefined){
         const message = 'Please select a row';
         const target = '';
         dispatch(requestValidationError(message, target));
     }    
     let selectedBillingDetails=[];
     for(var i=0; i<indexes.length;i++) {
         selectedBillingDetails.push(gpaBillingEntryDetails.results[indexes[i]]);
     }   
     data ={selectedBillingDetails,gpaBillingEntryFilter}  
     return makeRequest({
             url: 'rest/mail/mra/gpabilling/gpabillingenquiry/changestatusdetails',
             data: {...data}
         }).then(function (response) {
             handleButtonResponse(dispatch, response, CHANGE_DETAILS);            
             return response;
         })
         .catch(error => {
             return error;
     });            
}


//Navigate to Auto MCA
export function onAutoMCA(values){
   const {dispatch, getState } = values;
   const state = getState();   
   const gpaBillingEntryFilter  = (state.form.gpaBillingEntryFilter.values)?state.form.gpaBillingEntryFilter.values:{}
   const gpaBillingEntryDetails = state.filterReducer.mailbagsdetails;
   const records = gpaBillingEntryDetails.results.length; 
    let selectedBillingDetails=[];
     for(var i=0; i<records;i++) {
         selectedBillingDetails.push(gpaBillingEntryDetails.results[i]);
     }  
   let data = {gpaBillingEntryFilter,selectedBillingDetails,records};
    const url = 'rest/mail/mra/gpabilling/gpabillingenquiry/automca';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) { 
      //  response={...response,gpaBillingFilter:gpaBillingEntryFilter} 

        handleButtonResponse(dispatch, response, AUTO_MCA);
        return response
    })
    .catch(error => {
        return error;
    });

}

export function updateSortVariables(data) {
     const { dispatch } = data;
     dispatch({ type: 'UPDATE_SORT_VARIABLE', data: data.args })

 }

export function updateBillingDetails(values){


    const { args,dispatch, getState } = values;
    const state = getState();
    const url='rest/mail/mra/gpabilling/gpabillingenquiry/updatebilling';
    let indexes=[]; 
    let data={};
    if(args.indexes.length!==0){
    if(args.indexes) {
        indexes.push(args.indexes);
    }
    }
    const consignmentDetails = state.filterReducer.consignmentdetails; 
    const gpaBillingEntryFilter  = (state.form.gpaBillingEntryFilter.values)?state.form.gpaBillingEntryFilter.values:{}
    const gpaBillingEntryDetails = state.filterReducer.mailbagsdetails;
    let selectedConsignmentDetails=[];
    //indexes=state.filterReducer.selectedMailbagIndex;
    if(indexes.length!==0){  
     for(var i=0; i<indexes[0].length;i++) {
         selectedConsignmentDetails.push(consignmentDetails.results[indexes[0][i]]);
     }
    }  
    data ={selectedConsignmentDetails,gpaBillingEntryFilter}   
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) { 
        if (response && response.errors!==null)
            {
                dispatch( { type: NO_DATA,data});
            }  
        handleButtonResponse(dispatch, response, UPDATE_BILLING);
        return response
    })
    .catch(error => {
        return error;
    }); 
    
}
export function allMailbagIconAction(values){
    const {args, dispatch, getState } = values;
    const state = getState();
    const showMailbagIcon = state.filterReducer.showMailbagIcon;
    return {type: MAIL_ICON,showMailbagIcon };
}

//export function changeScreenload(values){
//    const {args, dispatch, getState } = values;
   // const state = getState();
   // const billingStatus=state.filterReducer.billingStatus;
   // const remarks=state.filterReducer.remarks;
   // dispatch(change('statusMailForm', `billingStatus`   , billingStatus ));
    //dispatch(change('statusMailForm', `newMailbagsTable.${index}.mailbagId`   , mailbagId ));
//}
function makeSummaryFilter(data){
    
    let filter={};
    let popOver={};
    let count = 0;
    let popoverCount =0;
    if(data.gpaBillingEntryFilter.fromDate && data.gpaBillingEntryFilter.fromDate.length > 0){
        filter={...filter, fromDate:data.gpaBillingEntryFilter.fromDate};
        count++;
    }
    if(data.gpaBillingEntryFilter.toDate && data.gpaBillingEntryFilter.toDate.length > 0){
        filter={...filter, toDate:data.gpaBillingEntryFilter.toDate};
        count++;
    }
    if(data.gpaBillingEntryFilter.consignmentNumber && data.gpaBillingEntryFilter.consignmentNumber.length > 0){
        filter={...filter, consignmentNumber:data.gpaBillingEntryFilter.consignmentNumber};
        count++;
    }
    if(data.gpaBillingEntryFilter.billingStatus && data.gpaBillingEntryFilter.billingStatus.length > 0){
        filter={...filter, billingStatus:data.gpaBillingEntryFilter.billingStatus};
        count++;
    }
    if(data.gpaBillingEntryFilter.gpaCode && data.gpaBillingEntryFilter.gpaCode.length > 0){
        filter={...filter, gpaCode:data.gpaBillingEntryFilter.gpaCode};
        count++;
    }
    if(data.gpaBillingEntryFilter.originOE && data.gpaBillingEntryFilter.originOE.length > 0){
        filter={...filter, originOE:data.gpaBillingEntryFilter.originOE};
        count++;
    }
    if(data.gpaBillingEntryFilter.destinationOE && data.gpaBillingEntryFilter.destinationOE.length > 0){
        if(count<6){
            filter={...filter, destinationOE:data.gpaBillingEntryFilter.destinationOE};
            count++;
        }else{
            popOver={...popOver, destinationOE:data.gpaBillingEntryFilter.destinationOE};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.origin && data.gpaBillingEntryFilter.origin.length > 0){
        if(count<6){
            filter={...filter, origin:data.gpaBillingEntryFilter.origin};
            count++;
        }else{
            popOver={...popOver, origin:data.gpaBillingEntryFilter.origin};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.destination && data.gpaBillingEntryFilter.destination.length > 0){
        if(count<6){
            filter={...filter, destination:data.gpaBillingEntryFilter.destination};
            count++;
        }else{
            popOver={...popOver, destination:data.gpaBillingEntryFilter.destination};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.category && data.gpaBillingEntryFilter.category.length > 0){
        if(count<6){
            filter={...filter, category:data.gpaBillingEntryFilter.category};
            count++;
        }else{
            popOver={...popOver, category:data.gpaBillingEntryFilter.category};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.subClass && data.gpaBillingEntryFilter.subClass.length > 0){
        if(count<6){
            filter={...filter, subClass:data.gpaBillingEntryFilter.subClass};
            count++;
        }else{
            popOver={...popOver, subClass:data.gpaBillingEntryFilter.subClass};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.year && data.gpaBillingEntryFilter.year.length > 0){
        if(count<6){
            filter={...filter, year:data.gpaBillingEntryFilter.year};
            count++;
        }else{
            popOver={...popOver, year:data.gpaBillingEntryFilter.year};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.dsn && data.gpaBillingEntryFilter.dsn.length > 0){
        if(count<6){
            filter={...filter, dsn:data.gpaBillingEntryFilter.dsn};
            count++;
        }else{
            popOver={...popOver, dsn:data.gpaBillingEntryFilter.dsn};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.rsn && data.gpaBillingEntryFilter.rsn.length > 0){
        if(count<6){
            filter={...filter, rsn:data.gpaBillingEntryFilter.rsn};
            count++;
        }else{
            popOver={...popOver, rsn:data.gpaBillingEntryFilter.rsn};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.hni && data.gpaBillingEntryFilter.hni.length > 0){
        if(count<6){
            filter={...filter, hni:data.gpaBillingEntryFilter.hni};
            count++;
        }else{
            popOver={...popOver, hni:data.gpaBillingEntryFilter.hni};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.ri && data.gpaBillingEntryFilter.ri.length > 0){
        if(count<6){
            filter={...filter, ri:data.gpaBillingEntryFilter.ri};
            count++;
        }else{
            popOver={...popOver, ri:data.gpaBillingEntryFilter.ri};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.mailbag && data.gpaBillingEntryFilter.mailbag.length > 0){
        if(count<6){
            filter={...filter, mailbag:data.gpaBillingEntryFilter.mailbag};
            count++;
        }else{
            popOver={...popOver, mailbag:data.gpaBillingEntryFilter.mailbag};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.uspsMailPerformance && data.gpaBillingEntryFilter.uspsMailPerformance.length > 0){
        if(count<6){
            filter={...filter, uspsMailPerformance:data.gpaBillingEntryFilter.uspsMailPerformance};
            count++;
        }else{
            popOver={...popOver, uspsMailPerformance:data.gpaBillingEntryFilter.uspsMailPerformance};
            popoverCount++;
        }  
    }
    if(data.gpaBillingEntryFilter.rateBasis && data.gpaBillingEntryFilter.rateBasis.length > 0){
        if(count<6){
            filter={...filter, rateBasis:data.gpaBillingEntryFilter.rateBasis};
            count++;
        }else{
            popOver={...popOver, rateBasis:data.gpaBillingEntryFilter.rateBasis};
            popoverCount++;
        }  
    }
     if(data.gpaBillingEntryFilter.paBuilt && data.gpaBillingEntryFilter.paBuilt.length > 0){
         if(count<6){
             filter={...filter, paBuilt:data.gpaBillingEntryFilter.paBuilt};
             count++;
         }else{
            popOver={...popOver, paBuilt:data.gpaBillingEntryFilter.paBuilt};
            popoverCount++;
         }  
     }
    
    const summaryFilter = {filter:filter, popOver:popOver, popoverCount:popoverCount};
    return summaryFilter;
}