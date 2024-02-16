import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import screenLoad from './commonaction';
import {reset} from 'redux-form';
import {asyncDispatch} from 'icoreact/lib/ico/framework/component/common/actions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';



export function toggleFilter(screenMode) {  
    return {type: 'TOGGLE_FILTER',screenMode };
  }

  //Container Clear action
  export const clearFilter=(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('offloadFilter'));
    dispatch(reset('offloadMailFilter'));
    dispatch(reset('offloadDSNFilter'));
} 
// Mail Clear action
/*export const clearMailFilter =(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('offloadMailFilter'));
}
// DSN Clear action
export const clearDSNFilter =(values)=> {
    const {dispatch} = values;  
    dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('offloadDSNFilter'));
}*/

//For Container List
export function onListDetails(values) {
  const { args, dispatch, getState } = values;
  const state = getState();
  const screenMode=state.filterReducer.screenMode;
  let offloadFilter=null;
   let mailbagIdSaved=null;
   let mailbags=null;
   let containerNo=null;
   let containers=null;
  const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
  const defaultPageSize = args && args.defaultPageSize ? args.defaultPageSize : state.filterReducer.defaultPageSize; 
  if(state.commonReducer.activeOffloadTab === 'CONTAINER_VIEW'){
   offloadFilter  = (state.form.offloadFilter.values)?state.form.offloadFilter.values:
                                state.filterReducer.filterValues?state.filterReducer.filterValues:{};
  const carrierCode =  state.form.offloadFilter.values?state.form.offloadFilter.values.flightnumber.carrierCode:
                                state.filterReducer.filterValues&&state.filterReducer.carrierCode?
                                        state.filterReducer.filterValues.carrierCode:'';
  offloadFilter.flightCarrierCode = carrierCode;
  const flightNumber =  state.form.offloadFilter.values?state.form.offloadFilter.values.flightnumber.flightNumber:
                                state.filterReducer.filterValues&& state.filterReducer.filterValues.flightNumber?
                                        state.filterReducer.filterValues.flightNumber:'';
  offloadFilter.flightNumber = flightNumber;
  const flightDate =  state.form.offloadFilter.values?state.form.offloadFilter.values.flightnumber.flightDate:
                                state.filterReducer.filterValues&&state.filterReducer.filterValues.flightDate?
                                        state.filterReducer.filterValues.flightDate:'';
  //const containerType = state.form.offloadFilter.values &&  state.form.offloadFilter.values.containerType ? state.form.offloadFilter.values.containerType:'ALL';
  //offloadFilter.containerType = 'U';
  offloadFilter.flightDate = flightDate;
  offloadFilter.displayPage=displayPage;
  offloadFilter.type='U';
  offloadFilter.defaultPageSize=defaultPageSize;
  containers=state.filterReducer.containers;
  containerNo = state.filterReducer.filterValues&&
        state.filterReducer.filterValues.containerNo?state.filterReducer.filterValues.containerNo:null;
  }
  else{
    const offloadMailFilter  = (state.form.offloadMailFilter.values)?state.form.offloadMailFilter.values:{};
    const carrierCode =  state.form.offloadMailFilter.values?state.form.offloadMailFilter.values.flightnumber.carrierCode:'';
    offloadMailFilter.flightCarrierCode = carrierCode;
    const flightNumber =  state.form.offloadMailFilter.values?state.form.offloadMailFilter.values.flightnumber.flightNumber:'';
    offloadMailFilter.flightNumber = flightNumber;
    const flightDate =  state.form.offloadMailFilter.values?state.form.offloadMailFilter.values.flightnumber.flightDate:'';
    offloadMailFilter.flightDate = flightDate;
    offloadMailFilter.displayPage=displayPage;
    offloadMailFilter.type='M';
    offloadMailFilter.defaultPageSize=defaultPageSize;
    mailbagIdSaved = state.filterReducer.filterValues&&
        state.filterReducer.filterValues.mailbagId?state.filterReducer.filterValues.mailbagId:null;
    mailbags=state.filterReducer.mailbags?state.filterReducer.mailbags:null;
     offloadFilter = offloadMailFilter;
  }

  state.form.containertable?dispatch(reset('containertable')):''; 
  state.form.mailtable?dispatch(reset('mailtable')):''; 
  const data = {offloadFilter};
  const url = 'rest/mail/operations/offload/list';
  return makeRequest({
      url,
      data: {...data}
  }).then(function (response) {
    if(response && response.errors && response.errors!=null && !isEmpty(response.errors)){
        dispatch( { type: 'CLEAR_TABLE'});
        return Promise.reject(new Error(response.errors.ERROR[0].description)); 
   }else{
      if(state.commonReducer.activeOffloadTab === 'CONTAINER_VIEW'){
        if((containerNo==null||containerNo=='' )&& containers!=null){
            if (response.results[0].tabId === 'MTK060'){
            response={...response,fromScreen:'MAIL_OUTBOUND'}
        }else{
            response={...response,fromScreen:'CONTAINER_ENQUIRY'}
        }

           
            let results=response.results[0].offloadDetailsPageResult.results;
            let resultContainer=[];
            let resultCount=0;
            results.forEach(function(element) {
                if(containers.includes(element.containerNo)){
                    resultContainer.push(element);
                    resultCount++;
                }
            }, this);
        response.results[0].offloadDetailsPageResult.actualPageSize=resultCount;
        response.results[0].offloadDetailsPageResult.endIndex=resultCount+1;
        response.results[0].offloadDetailsPageResult.totalRecordCount=resultCount;
        response.results[0].offloadDetailsPageResult.results=resultContainer;    
        }
      }
    else {
        if((mailbagIdSaved==null||mailbagIdSaved=='')&&mailbags!=null){
            if(response.results[0].tabId === 'MTK060'){
                response={...response,fromScreen:'MAIL_OUTBOUND'};
            }else{
                response={...response,fromScreen:'MAILBAG_ENQUIRY'};
            }
            
            let results=response.results[0].offloadDetailsPageResult.results;
            let resultMailbags=[];
            let resultCount=0;
            results.forEach(function(element) {
                if(mailbags.includes(element.mailbagId)){
                    resultMailbags.push(element);
                    resultCount++;
                }
            }, this);
        response.results[0].offloadDetailsPageResult.actualPageSize=resultCount;
        response.results[0].offloadDetailsPageResult.endIndex=resultCount+1;
        response.results[0].offloadDetailsPageResult.totalRecordCount=resultCount;
        response.results[0].offloadDetailsPageResult.results=resultMailbags;    
        }
    }
        if (args && args.mode === 'EXPORT') {
        let containerDetails = response.results[0].offloadDetailsPageResult
        return containerDetails;
}
      handleResponse(dispatch, response,args.mode,data,screenMode,displayPage);
      return response
}
  })
  .catch(error => {
      return error;
  });
}

//Mail Details List  
/*export function onListMailDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode;
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
    const defaultPageSize = args && args.defaultPageSize ? args.defaultPageSize : state.filterReducer.defaultPageSize; 
    const offloadMailFilter  = (state.form.offloadMailFilter.values)?state.form.offloadMailFilter.values:{};
    const carrierCode =  state.form.offloadMailFilter.values?state.form.offloadMailFilter.values.flightnumber.carrierCode:'';
    offloadMailFilter.flightCarrierCode = carrierCode;
    const flightNumber =  state.form.offloadMailFilter.values?state.form.offloadMailFilter.values.flightnumber.flightNumber:'';
    offloadMailFilter.flightNumber = flightNumber;
    const flightDate =  state.form.offloadMailFilter.values?state.form.offloadMailFilter.values.flightnumber.flightDate:'';
    //const containerType = (state.form.offloadMailFilter.values && state.form.offloadMailFilter.values.containerType) ? state.form.offloadMailFilter.values.containerType:'ALL';
    //offloadMailFilter.containerType = containerType;
    offloadMailFilter.flightDate = flightDate;
    offloadMailFilter.displayPage=displayPage;
    offloadMailFilter.type='M';
    offloadMailFilter.defaultPageSize=defaultPageSize;

    const mailbagId = state.filterReducer.filterValues&&
        state.filterReducer.filterValues.mailbagId?state.filterReducer.filterValues.mailbagId:null;
    const mailbags=state.filterReducer.mailbags?state.filterReducer.mailbags:null;
    const offloadFilter = offloadMailFilter;
    const data = {offloadFilter};
    const url = 'rest/mail/operations/offload/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
          if((mailbagId==null||mailbagId=='')&&mailbags!=null){
            response={...response,fromScreen:'MAILBAG_ENQUIRY'};
            let results=response.results[0].offloadDetailsPageResult.results;
            let resultMailbags=[];
            let resultCount=0;
            results.forEach(function(element) {
                if(mailbags.includes(element.mailbagId)){
                    resultMailbags.push(element);
                    resultCount++;
                }
            }, this);
        response.results[0].offloadDetailsPageResult.actualPageSize=resultCount;
        response.results[0].offloadDetailsPageResult.endIndex=resultCount+1;
        response.results[0].offloadDetailsPageResult.totalRecordCount=resultCount;
        response.results[0].offloadDetailsPageResult.results=resultMailbags;    
        }
          if (args && args.mode === 'EXPORT') {
          let mailbagDetails = response.results[0].offloadDetailsPageResult
          return mailbagDetails;
  }
        handleResponse(dispatch, response,args.mode,data,screenMode,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
  }*/

// For DSN List

export function onListDSNDetails(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode;
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
    const defaultPageSize = args && args.defaultPageSize ? args.defaultPageSize : state.filterReducer.defaultPageSize; 
    const offloadDSNFilter  = (state.form.offloadDSNFilter.values)?state.form.offloadDSNFilter.values:{};
    const carrierCode =  state.form.offloadDSNFilter.values?state.form.offloadDSNFilter.values.flightnumber.carrierCode:'';
    offloadDSNFilter.flightCarrierCode = carrierCode;
    const flightNumber =  state.form.offloadDSNFilter.values?state.form.offloadDSNFilter.values.flightnumber.flightNumber:'';
    offloadDSNFilter.flightNumber = flightNumber;
    const flightDate =  state.form.offloadDSNFilter.values?state.form.offloadDSNFilter.values.flightnumber.flightDate:'';
   //const containerType = (state.form.offloadDSNFilter.values && state.form.offloadDSNFilter.values.containerType) ? state.form.offloadDSNFilter.values.containerType:'ALL';
   // offloadDSNFilter.containerType = containerType;
    offloadDSNFilter.flightDate = flightDate;
    offloadDSNFilter.displayPage=displayPage;
    offloadDSNFilter.type='D';
    offloadDSNFilter.defaultPageSize=defaultPageSize;

    const offloadFilter = offloadDSNFilter;
    const data = {offloadFilter};
    const url = 'rest/mail/operations/offload/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
          if (args && args.mode === 'EXPORT') {
          let dsnDetails = response.results[0].offloadDetailsPageResult
          return dsnDetails;
  }
        handleResponse(dispatch, response,args.mode,data,screenMode,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
  }
//For listing after navigation from containerEnquiry
export function offloadOnNavigation(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode;
    const displayPage = '1';
    const defaultPageSize = '500'; 
    let offloadFilter  = {};
    let containers=[];
    const carrierCode =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.carrierCode?state.filterReducer.filterValues.carrierCode:'';
    const flightNumber =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.flightNumber?state.filterReducer.filterValues.flightNumber:'';
    const flightDate =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.flightDate?state.filterReducer.filterValues.flightDate:'';
    const containerNo = state.filterReducer.filterValues&&
        state.filterReducer.filterValues.containerNo?state.filterReducer.filterValues.containerNo:null;
    if(containerNo==null||containerNo==''){
        containers=state.filterReducer.containers;
    }
    const flightnumber=state.filterReducer.filterValues.flightnumber;
    offloadFilter={flightCarrierCode:carrierCode,
                   carrierCode:carrierCode,
                   flightNumber:flightNumber,
                   containerType:'U',
                   containerNo: containerNo,
                   flightDate:flightDate,
                   displayPage:displayPage,
                   type:'U',
                   defaultPageSize:defaultPageSize,
                   flightnumber:flightnumber
                    }
    const data = {offloadFilter};
    const url = 'rest/mail/operations/offload/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        response={...response,fromScreen:'CONTAINER_ENQUIRY'}
        if(containerNo==null||containerNo==''){
            let results=response.results[0].offloadDetailsPageResult.results;
            let resultContainer=[];
            let resultCount=0;
            results.forEach(function(element) {
                if(containers.includes(element.containerNo)){
                    resultContainer.push(element);
                    resultCount++;
                }
            }, this);
        response.results[0].offloadDetailsPageResult.actualPageSize=resultCount;
        response.results[0].offloadDetailsPageResult.endIndex=resultCount+1;
        response.results[0].offloadDetailsPageResult.totalRecordCount=resultCount;
        response.results[0].offloadDetailsPageResult.results=resultContainer;    
        }
        handleResponse(dispatch, response,'LIST',data,screenMode,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
}
//For listing after navigation from mailbagEnquiry
export function offloadOnNavigationFromMailbagEnquiry(values){
    const { args, dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode;
    const displayPage = '1';
    const defaultPageSize = '500'; 
    let mailbags=[];
    let offloadFilter = {};
    const offloadMailFilter  = {};
    const carrierCode = state.filterReducer.filterValues&&
             state.filterReducer.filterValues.carrierCode?state.filterReducer.filterValues.carrierCode:'';
    const flightNumber =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.flightNumber?state.filterReducer.filterValues.flightNumber:'';
    const flightDate =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.flightDate?state.filterReducer.filterValues.flightDate:'';
    const mailbagId = state.filterReducer.filterValues&&
        state.filterReducer.filterValues.mailbagId?state.filterReducer.filterValues.mailbagId:null;
    if(mailbagId==null||mailbagId==''){
        mailbags=state.filterReducer.mailbags;
    }
    const flightnumber=state.filterReducer.filterValues.flightnumber;
    offloadFilter={flightCarrierCode:carrierCode,
                   flightNumber:flightNumber,
                   containerType:'ALL',
                   mailbagId: mailbagId,
                   flightDate:flightDate,
                   displayPage:displayPage,
                   type:'M',
                   defaultPageSize:defaultPageSize,
                   flightnumber:flightnumber
                 }
    const data = {offloadFilter};
    const url = 'rest/mail/operations/offload/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        response={...response,fromScreen:'MAILBAG_ENQUIRY'}
        if(mailbagId==null||mailbagId==''){
            let results=response.results[0].offloadDetailsPageResult.results;
            let resultMailbags=[];
            let resultCount=0;
            results.forEach(function(element) {
                if(mailbags.includes(element.mailbagId)){
                    resultMailbags.push(element);
                    resultCount++;
                }
            }, this);
        response.results[0].offloadDetailsPageResult.actualPageSize=resultCount;
        response.results[0].offloadDetailsPageResult.endIndex=resultCount+1;
        response.results[0].offloadDetailsPageResult.totalRecordCount=resultCount;
        response.results[0].offloadDetailsPageResult.results=resultMailbags;    
        }
        handleResponse(dispatch, response,'LIST',data,screenMode,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
}
function handleResponse(dispatch,response,action,data,screenMode,displayPage) {
  
  if(!isEmpty(response.results)){
      console.log(response.results[0]);
      const {offloadDetailsPageResult} = response.results[0];
      const offloadFilter = response.results[0].offloadFilter;
      const summaryFilter = makeSummaryFilter(offloadFilter);
      if (action==="LIST") {
          if(offloadDetailsPageResult !=null ) {
              if(displayPage > 1) {
                  dispatch( { type: 'LIST_SUCCESS_PAGINATION',offloadDetailsPageResult,data,screenMode,displayPage,fromScreen:response.fromScreen,summaryFilter });
              }
              else{
                dispatch( { type: 'LIST_SUCCESS',offloadDetailsPageResult,data,fromScreen:response.fromScreen,summaryFilter });
              }
          }
          else {
              dispatch( { type: 'NO_DATA',data}); 
          }
      }
      
  } 
  else {
      if(!isEmpty(response.errors)){
           dispatch( { type: 'RETAIN_VALUES',data});
      }
  }
}

export function validateTab(currTab, mailBags) {


    let isValid = true;
    let error = ""

    if(!isEmpty(mailBags)){
        isValid=false;
    }



    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;

}

export function changeTab(values){


    const { args,dispatch,getState } = values;
    const state = getState();
   // dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('offloadFilter'));
    dispatch(reset('offloadMailFilter'));
    dispatch(reset('offloadDSNFilter'));
    dispatch({type:'CHANGE_OFFLOAD_OK_TAB',currentTab: args });
}

export function changeTabOnNoChange(dispatch, currentTab){


    //const { args,dispatch,getState } = values;
    //const state = getState();
   // dispatch({ type: 'CLEAR_FILTER'});
    dispatch(reset('offloadFilter'));
    dispatch(reset('offloadMailFilter'));
    dispatch(reset('offloadDSNFilter'));
    dispatch({type:'CHANGE_OFFLOAD_OK_TAB',currentTab: currentTab });
}


function makeSummaryFilter(data){
    
    let filter={};
    let popOver={};
    let count = 0;
    let popoverCount =0;
    if(data.flightCarrierCode && data.flightCarrierCode.length > 0){
        filter={...filter, flightCarrierCode:data.flightCarrierCode};
        count++;
    }
    if(data.flightNumber && data.flightNumber.length > 0){
        filter={...filter, flightNumber:data.flightNumber};
        count++;
    }
    if(data.flightDate && data.flightDate.length > 0){
        filter={...filter, flightDate:data.flightDate};
        count++;
    }
   
    if(data.containerType && data.containerType.length > 0){
        filter={...filter, containerType:data.containerType};
        count++;
    }
    if(data.upliftAirport && data.upliftAirport.length > 0){
        filter={...filter, upliftAirport:data.upliftAirport};
        count++;
    }

    if(data.containerNo && data.containerNo.length > 0 ){
        if(count<7){
            filter={...filter, containerNo:data.containerNo};
            count++;
        }else{
            popOver={...popOver, containerNo:data.containerNo};
            popoverCount++;
        }  
    } 
    if(data.mailClass && data.mailClass.length > 0 ){
        if(count<7){
            filter={...filter, mailClass:data.mailClass};
            count++;
        }else{
            popOver={...popOver, mailClass:data.mailClass};
            popoverCount++;
        }  
    }
    if(data.year && data.year.length > 0 ){
        if(count<7){
            filter={...filter, year:data.year};
            count++;
        }else{
            popOver={...popOver, year:data.year};
            popoverCount++;
        }  
    }
    if(data.ooe && data.ooe.length > 0 ){
        if(count<7){
            filter={...filter, ooe:data.ooe};
            count++;
        }else{
            popOver={...popOver, ooe:data.ooe};
            popoverCount++;
        }  
    }
    if(data.dsn && data.dsn.length > 0 ){
        if(count<7){
            filter={...filter, dsn:data.dsn};
            count++;
        }else{
            popOver={...popOver, dsn:data.dsn};
            popoverCount++;
        }  
    }
    if(data.doe && data.doe.length > 0 ){
        if(count<7){
            filter={...filter, doe:data.doe};
            count++;
        }else{
            popOver={...popOver, doe:data.doe};
            popoverCount++;
        }  
    }

    if(data.mailCategoryCode && data.mailCategoryCode.length > 0 ){
        if(count<7){
            filter={...filter, mailCategoryCode:data.mailCategoryCode};
            count++;
        }else{
            popOver={...popOver, mailCategoryCode:data.mailCategoryCode};
            popoverCount++;
        }  
    }

    if(data.mailSubclass && data.mailSubclass.length > 0 ){
        if(count<7){
            filter={...filter, mailSubclass:data.mailSubclass};
            count++;
        }else{
            popOver={...popOver, mailSubclass:data.mailSubclass};
            popoverCount++;
        }  
    }
   
    if(data.mailbagId && data.mailbagId.length > 0 ){
        if(count<7){
            filter={...filter, mailbagId:data.mailbagId};
            count++;
        }else{
            popOver={...popOver, mailbagId:data.mailbagId};
            popoverCount++;
        }  
    }


    if(data.rsn && data.rsn.length > 0 ){
        if(count<7){
            filter={...filter, rsn:data.rsn};
            count++;
        }else{
            popOver={...popOver, rsn:data.rsn};
            popoverCount++;
        }  
    }



    const summaryFilter = {filter:filter, popOver:popOver, popoverCount:popoverCount};
    return summaryFilter;
}

export function offloadMailOnNavigationFromMailbagOutbound(values){
    const { dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode;
    const displayPage = '1';
    const defaultPageSize = '500'; 
    let mailbags=[];
    let offloadFilter = {};
    const carrierCode = state.filterReducer.filterValues&&
             state.filterReducer.filterValues.carrierCode?state.filterReducer.filterValues.carrierCode:'';
    const flightNumber =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.flightNumber?state.filterReducer.filterValues.flightNumber:'';
    const flightDate =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.flightDate?state.filterReducer.filterValues.flightDate:'';   
    const mailbagId = state.filterReducer.filterValues&&
        state.filterReducer.filterValues.mailbagId?state.filterReducer.filterValues.mailbagId:null;
    const containerNo = state.filterReducer.filterValues&&
        state.filterReducer.filterValues.containerNo?state.filterReducer.filterValues.containerNo:null;
    if(mailbagId==null||mailbagId==''){
        mailbags=state.filterReducer.mailbags;
    }
    const flightnumber=state.filterReducer.filterValues.flightnumber;
    offloadFilter={flightCarrierCode:carrierCode,
                   flightNumber:flightNumber,
                   containerType:'ALL',
                   mailbagId: mailbagId,
                   containerNo:containerNo,
                   flightDate:flightDate,
                   displayPage:displayPage,
                   type:'M',
                   defaultPageSize:defaultPageSize,
                   flightnumber:flightnumber,
                   mailbags: mailbags
                 }
    const data = {offloadFilter};
    const url = 'rest/mail/operations/offload/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        response={...response,fromScreen:'MAIL_OUTBOUND'}
        if(mailbagId==null||mailbagId==''){
            let results=response.results[0].offloadDetailsPageResult.results;
            let resultMailbags=[];
            let resultCount=0;
            results.forEach(function(element) {
                if(mailbags.includes(element.mailbagId)){
                    resultMailbags.push(element);
                    resultCount++;
                }
            }, this);
        response.results[0].offloadDetailsPageResult.actualPageSize=resultCount;
        response.results[0].offloadDetailsPageResult.endIndex=resultCount+1;
        response.results[0].offloadDetailsPageResult.totalRecordCount=resultCount;
        response.results[0].offloadDetailsPageResult.results=resultMailbags;    
        }
        handleResponse(dispatch, response,'LIST',data,screenMode,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
}

export function offloadContainerOnNavigationFromMailbagOutbound(values){
    const { dispatch, getState } = values;
    const state = getState();
    const screenMode=state.filterReducer.screenMode;
    const displayPage = '1';
    const defaultPageSize = '500'; 
    let offloadFilter  = {};
    let containers=[];
    const carrierCode =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.carrierCode?state.filterReducer.filterValues.carrierCode:'';
    const flightNumber =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.flightNumber?state.filterReducer.filterValues.flightNumber:'';
    const flightDate =  state.filterReducer.filterValues&&
        state.filterReducer.filterValues.flightDate?state.filterReducer.filterValues.flightDate:'';
    const containerNo = state.filterReducer.filterValues&&
        state.filterReducer.filterValues.containerNo?state.filterReducer.filterValues.containerNo:null;
    if(containerNo==null||containerNo==''){
        containers=state.filterReducer.containers;
    }
    const flightnumber=state.filterReducer.filterValues.flightnumber;
    offloadFilter={flightCarrierCode:carrierCode,
                   carrierCode:carrierCode,
                   flightNumber:flightNumber,
                   containerType:'ALL',
                   containerNo: containerNo,
                   flightDate:flightDate,
                   displayPage:displayPage,
                   type:'U',
                   defaultPageSize:defaultPageSize,
                   flightnumber:flightnumber
                    }
    const data = {offloadFilter};
    const url = 'rest/mail/operations/offload/list';
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        response={...response,fromScreen:'MAIL_OUTBOUND'}
        if(containerNo==null||containerNo==''){
            let results=response.results[0].offloadDetailsPageResult.results;
            let resultContainer=[];
            let resultCount=0;
            results.forEach(function(element) {
                if(containers.includes(element.containerNo)){
                    resultContainer.push(element);
                    resultCount++;
                }
            }, this);
        response.results[0].offloadDetailsPageResult.actualPageSize=resultCount;
        response.results[0].offloadDetailsPageResult.endIndex=resultCount+1;
        response.results[0].offloadDetailsPageResult.totalRecordCount=resultCount;
        response.results[0].offloadDetailsPageResult.results=resultContainer;    
        }
        handleResponse(dispatch, response,'LIST',data,screenMode,displayPage);
        return response
    })
    .catch(error => {
        return error;
    });
}

export function changeOffloadTab(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    const currentTab = args;
    let isChanged = false;
    const serverResults  = state.filterReducer.containerdetails?state.filterReducer.containerdetails.results:[];
    let tableValues = [];
    if (state.commonReducer.activeOffloadTab === 'CONTAINER_VIEW') {
        tableValues = (state.form.containertable && state.form.containertable.values.containertable) ? state.form.containertable.values.containertable : [];
    }else if(state.commonReducer.activeOffloadTab === 'MAIL_VIEW'){
        tableValues = (state.form.mailtable && state.form.mailtable.values.mailtable) ? state.form.mailtable.values.mailtable : [];
    }else{
        tableValues = (state.form.dsntable && state.form.dsntable.values.dsntable) ? state.form.dsntable.values.dsntable : [];
    }

    if(!isEmpty(serverResults)){
        for(let i=0; i<serverResults.length; i++){
            if(serverResults[i].offloadReason !== tableValues[i].offloadReason){
                isChanged = true;
            }else if(serverResults[i].remarks !== tableValues[i].remarks){
                isChanged = true;
            }
        }
    }

    if(isChanged){
        dispatch(requestWarning([{code:"mail.operations.offload.clear", description:"Unsaved data exists.Do you wish to proceed?"}],{functionRecord:changeTab,args:currentTab})) 
      }
        else {
          
      dispatch(changeTabOnNoChange(dispatch, currentTab));
          
         }
}





