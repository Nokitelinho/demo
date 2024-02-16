import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { reset } from 'redux-form';
import { change} from 'icoreact/lib/ico/framework/component/common/form';
import * as constant from '../constants/constants';
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {getCurrentDate} from 'icoreact/lib/ico/framework/component/util/util';

export function validateForm(data) {
    let isValid = true;
    let error = ""
    if (!data) {
        isValid = false;
        error = "Please enter filter values"
    } else {
        if (data.filterType === 'F') {

            if (!data.flightnumber.flightDate || !data.flightnumber.flightNumber || !data.airportCode) {
                if(!data.flightnumber.flightDate && !data.flightnumber.flightNumber) {
                   if (!(data.fromDate && data.toDate && data.airportCode)) {
                    isValid = false;
                    error = "Please enter Flight date, Flight number and uplift airport or Flight date range and uplift airport"
                   }else if(!(data.fromTime && !data.toTime)){
                    isValid = false;
                    error = "Please enter From time and To time"
                   }
                }
                else if(!data.airportCode){
                    isValid = false;
                    error = "Please enter Uplift Airport"
                }
                else if(!data.flightnumber.flightDate){
                    isValid = false;
                    error = "Please enter Flight Date" 
                }
                else if (!data.flightnumber.flightNumber){
                    isValid = false;
                    error = "Please enter Flight Number" 
                }
            }
           
        }
        else {
            if (!(data.carrierCode) ) {
                    isValid = false;
                    error = "Please enter Carrier Code"
                }
            
        }
    }

    let validObject = {
        valid: isValid,
        msg: error
    }
    return validObject;
}

//The main outbound list
export const listDetails = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();
	let recordsPerPage = state.commonReducer.defaultPageSize;
    if(args && args.recordsPerPage) {
        recordsPerPage = args.recordsPerPage;
    }
    let displayPage='';
   // const mode = state.commonReducer.displayMode;

     // Validationg form  
      
     if (state.form.outboundFilter.values.filterType === 'F') {

       /* if (!state.form.outboundFilter.values.flightnumber.flightDate || !state.form.outboundFilter.values.flightnumber.flightNumber || !state.form.outboundFilter.values.airportCode) {
            if(!state.form.outboundFilter.values.flightnumber.flightDate && !state.form.outboundFilter.values.flightnumber.flightNumber) {
               if (!(state.form.outboundFilter.values.fromDate && state.form.outboundFilter.values.toDate && state.form.outboundFilter.values.airportCode)) {
                return Promise.reject(new Error("Please enter Flight date, Flight number and uplift airport or Flight date range and uplift airport"));
               }
            }
            else if(!state.form.outboundFilter.values.airportCode){
                return Promise.reject(new Error("Please enter Uplift Airport"));
            }
            else if(!state.form.outboundFilter.values.flightnumber.flightDate){
                return Promise.reject(new Error("Please enter Flight Date" ));
            }
            else if (!state.form.outboundFilter.values.flightnumber.flightNumber){
                return Promise.reject(new Error( "Please enter Flight Number" ));
            }
        }*/
        if(!state.form.outboundFilter.values.airportCode){
            return Promise.reject(new Error("Please enter Uplift Airport"));
        }
        else if(state.form.outboundFilter.values.airportCode && !state.form.outboundFilter.values.fromDate && !state.form.outboundFilter.values.toDate &&
            !(state.form.outboundFilter.values.flightnumber&&state.form.outboundFilter.values.flightnumber.flightDate)){
                return Promise.reject(new Error( "Please enter  Flight Date or Date Range along with Uplift Airport" ));   
        }
        else if((state.form.outboundFilter.values.airportCode && !(state.form.outboundFilter.values.fromDate && state.form.outboundFilter.values.toDate))){
            if(!(state.form.outboundFilter.values.flightnumber&&state.form.outboundFilter.values.flightnumber.flightDate)){
            return Promise.reject(new Error( "Please enter  Date Range along with Uplift Airport" ));  
            } 
        }
        
       /* else if(state.form.outboundFilter.values.flightnumber.carrierCode && state.form.outboundFilter.values.flightnumber.flightNumber === undefined ){
            return Promise.reject(new Error( "Please enter Flight Number" ));      
        }*/
        else if( !state.form.outboundFilter.values.flightnumber||(( state.form.outboundFilter.values.flightnumber.carrierCode=== undefined || !state.form.outboundFilter.values.flightnumber.carrierCode)&& state.form.outboundFilter.values.flightnumber.flightNumber )||
        !state.form.outboundFilter.values.flightnumber.carrierCode ){
            return Promise.reject(new Error( "Please enter Flight Carrier Code" ));      
        }else if((state.form.outboundFilter.values.fromDate && state.form.outboundFilter.values.toDate) && !(state.form.outboundFilter.values.fromTime && state.form.outboundFilter.values.toTime)){
            return Promise.reject(new Error("Please enter from time and to time")); 
        }
       
    }
    else {
        if (!(state.form.outboundFilter.values.carrierCode) ) {
            return Promise.reject(new Error( "Please enter Carrier Code"));
            }
        if(!state.form.outboundFilter.values.airportCode){
             return Promise.reject(new Error("Please enter Uplift Airport"));
        }
    }


    
    let mode=state.commonReducer.displayMode;
    let loggedAirport= state.commonReducer.airportCode
    let flightActionsEnabled= 'false';
    const assignTo = state.commonReducer.flightCarrierflag
    const flightCarrierFilter = (state.form.outboundFilter.values) ? state.form.outboundFilter.values : {}
    flightCarrierFilter.assignTo = assignTo;
    const flightNumber = (state.form.outboundFilter.values) ? state.form.outboundFilter.values.flightnumber : {};
    if(flightCarrierFilter.airportCode ===loggedAirport ){
        flightActionsEnabled='true';
    }
   
   
  
    if (assignTo === 'F') {
        if (!isEmpty(flightNumber)) {
            flightCarrierFilter.flightNumber = flightNumber.flightNumber;
            flightCarrierFilter.flightDate = flightNumber.flightDate;
            flightCarrierFilter.carrierCode = flightNumber.carrierCode
        }
        
       
            if(args) {
                if(args.displayPage!=undefined && args.displayPage!=null){
                 displayPage = args.displayPage;
                }
                else{
                    displayPage=state.filterReducer.flightDisplayPage;
                }
                 //for 1st page listing
                if(args.mode==='display') {
                   mode='display';
               }
                flightCarrierFilter.flightDisplayPage = displayPage;
            }
           else
         {
            flightCarrierFilter.flightDisplayPage = state.filterReducer.flightDisplayPage;
            mode = state.commonReducer.displayMode;
            displayPage=state.filterReducer.flightDisplayPage
        }
  
    } else  if (assignTo === 'C') {
           
            flightCarrierFilter.flightnumber.flightNumber ="";
            flightCarrierFilter.flightnumber.flightDate="";
            flightCarrierFilter.fromDate=getCurrentDate();
            flightCarrierFilter.toDate= getCurrentDate();
            flightCarrierFilter.flightOperationalStatus = "";
            flightCarrierFilter.operatingReference = state.commonReducer.defaultOperatingReference;
            flightCarrierFilter.fromTime ="00:00";
            flightCarrierFilter.toTime="23:59";
        
            
             //for 1st time listing
            if(args) {
                displayPage = args.displayPage;
                 //for 1st page listing
                if(args.mode==='display') {
                    mode='display';
                }
                flightCarrierFilter.carrierDisplayPage = displayPage;
            } else {
                flightCarrierFilter.carrierDisplayPage = state.filterReducer.carrierDisplayPage;
                mode = state.commonReducer.displayMode;
                
           }
            flightCarrierFilter.carrierCode=flightCarrierFilter.carrierCode;
       
    }
    flightCarrierFilter.recordsPerPage = recordsPerPage;
    const data = { flightCarrierFilter };
    const url = 'rest/mail/operations/outbound/list';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        if (args && args.mode === 'EXPORT') {
            if(assignTo === 'F'){
            let mailflightspage = response.results[0].mailflightspage
            let exportAcceptedDetails = { ...response.results[0].mailflightspage }
           
           for (var i = 0; i < mailflightspage.results.length; i++) {
           exportAcceptedDetails.results[i].flightNumber = mailflightspage.results[i].carrierCode != ''? mailflightspage.results[i].carrierCode+'-'+mailflightspage.results[i].flightNumber :''
           exportAcceptedDetails.results[i].flightDetails= mailflightspage.results[i].flightRoute != ''? mailflightspage.results[i].flightRoute+'-'+mailflightspage.results[i].aircraftType+'-' + mailflightspage.results[i].flightDate:''
        return exportAcceptedDetails;
            }
        }else{
            let mailcarrierspage = response.results[0].mailcarrierspage
            return mailcarrierspage;
        }
        }
        if(args && args.fromMainList === true) {
            dispatch(dispatchAction(updateInitialFlightFilterValues)({flightCarrierFilter}));
        }
        if(assignTo === 'F'&&response&&response.results&&response.results[0]&&response.results[0].mailflightspage
        &&response.results[0].mailflightspage.results&&response.results[0].mailflightspage.results.length>0){
        let mailflightspage = response.results[0].mailflightspage
        for (var i = 0; i < mailflightspage.results.length; i++) {
           let  flightData={carrierCode:mailflightspage.results[i].carrierCode,flightNumber:mailflightspage.results[i].flightNumber,flightCarrierId:mailflightspage.results[i].carrierId,flightSequenceNumber:mailflightspage.results[i].flightSequenceNumber,flightDate:mailflightspage.results[i].flightDate,legSerialNumber:mailflightspage.results[i].legSerialNumber }
           dispatch({ type: 'FLIGHT_DATA_SUCCESS', flightData: flightData});
        }
    }
        let tableFilter = populateInitailFilters(flightCarrierFilter);
        handleResponse(dispatch, response, data,mode,flightActionsEnabled,tableFilter);
        return response
    })
        .catch(error => {
            return error;
        });
    //  dispatch( { type: 'LIST_DETAILS', data:data});

}

function updateInitialFlightFilterValues(values) {
    const {  dispatch, args } = values;
    if(args.flightCarrierFilter) {
        dispatch( { type: constant.SAVE_INITIAL_FLIGHT_FILTER, data: populateInitailFilters(args.flightCarrierFilter)});
    }
}

function populateInitailFilters(flightCarrierFilter) {
    let filter = {}
    if(flightCarrierFilter) {
        filter = {
            destination: flightCarrierFilter.destination ? flightCarrierFilter.destination: '',
            flightnumber: {
                flightNumber:flightCarrierFilter.flightNumber ? flightCarrierFilter.flightNumber:'',
                flightDate:flightCarrierFilter.flightDate ? flightCarrierFilter.flightDate:'',
                carrierCode:flightCarrierFilter.carrierCode ? flightCarrierFilter.carrierCode:'',
            },
            flightOperationalStatus :flightCarrierFilter.flightOperationalStatus ? flightCarrierFilter.flightOperationalStatus :'',
            flightStatus: flightCarrierFilter.flightStatus ? flightCarrierFilter.flightStatus :[]
        }
    }
    return filter;
}
export const fetchFlightPreAdviceDetails = (values) =>{
    const {  dispatch, getState } = values;
    const state = getState();
    const flightCarrierFilter = (state.filterReducer.filterValues) ? state.filterReducer.filterValues : {}
    let List=state.filterReducer.flightDetails.results;
    let mailAcceptanceList=[];
    for (var i = 0; i < List.length; i++) {
        let acceptance={};
        acceptance.companyCode=List[i].companyCode;
        acceptance.carrierId=List[i].carrierId;
        acceptance.flightRoute=List[i].flightRoute;
        acceptance.flightNumber=List[i].flightNumber;
        acceptance.flightSequenceNumber=List[i].flightSequenceNumber;
        acceptance.flightDate=List[i].flightDate;
        mailAcceptanceList.push(acceptance);
    }
 const data = { mailAcceptanceList,flightCarrierFilter}
 const url = 'rest/mail/operations/outbound/fetchFlightPreAdviceDetails';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponse(dispatch, response, data, constant.FETCH_PREADVICE);
        return response
    })
        .catch(error => {
            return error;
        });
}
export const fetchFlightCapacityDetails = (values) =>{
    const {  dispatch, getState } = values;
    const state = getState();
   // const mode = state.commonReducer.displayMode;
    const flightCarrierFilter = (state.filterReducer.filterValues) ? state.filterReducer.filterValues : {}
    let loggedAirport= state.commonReducer.airportCode
    let flightActionsEnabled= 'false';
    if(flightCarrierFilter.airportCode === loggedAirport ){
        flightActionsEnabled='true';
    }
    let List=state.filterReducer.flightDetails.results;
    let mailAcceptanceList=[];
    for (var i = 0; i < List.length; i++) {
        let acceptance={};
        acceptance.companyCode=List[i].companyCode;
        acceptance.carrierId=List[i].carrierId;
        acceptance.flightRoute=List[i].flightRoute;
        acceptance.flightNumber=List[i].flightNumber;
        acceptance.flightSequenceNumber=List[i].flightSequenceNumber;
        acceptance.flightDate=List[i].flightDate;
        mailAcceptanceList.push(acceptance);
    }
 // const data = { mailAcceptanceList:{...mailAcceptanceList,pouList: null,preadvice: null,preassignFlag: null,totalContainerWeight: null, totalCapacity:null,totalWeight: null,numericalScreenId: null,containerPageInfo:null, mailCapacity:null,mailbags:null,flightpk:null,containerDetails:null},flightCarrierFilter };
 const data = { mailAcceptanceList,flightCarrierFilter}
 const url = 'rest/mail/operations/outbound/fetchFlightCapacityDetails';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        
        handleResponse(dispatch, response, data, constant.FETCH_CAPACITY,flightActionsEnabled);
        return response
    })
        .catch(error => {
            return error;
        });
    //  dispatch( { type: 'LIST_DETAILS', data:data});

}
export const fetchFlightVolumeDetails = (values) =>{
    const {  dispatch, getState } = values;
    const state = getState();
   // const mode = state.commonReducer.displayMode;
    const flightCarrierFilter = (state.filterReducer.filterValues) ? state.filterReducer.filterValues : {}
    let loggedAirport= state.commonReducer.airportCode
    let flightActionsEnabled= 'false';
    if(flightCarrierFilter.airportCode === loggedAirport ){
        flightActionsEnabled='true';
    }
    let List=state.filterReducer.flightDetails.results;
    let mailAcceptanceList=[];
    for (var i = 0; i < List.length; i++) {
        let acceptance={};
        acceptance.companyCode=List[i].companyCode;
        acceptance.carrierId=List[i].carrierId;
        acceptance.flightRoute=List[i].flightRoute;
        acceptance.flightNumber=List[i].flightNumber;
        acceptance.flightSequenceNumber=List[i].flightSequenceNumber;
        acceptance.flightDate=List[i].flightDate;
        mailAcceptanceList.push(acceptance);
    }
 // const data = { mailAcceptanceList:{...mailAcceptanceList,pouList: null,preadvice: null,preassignFlag: null,totalContainerWeight: null, totalCapacity:null,totalWeight: null,numericalScreenId: null,containerPageInfo:null, mailCapacity:null,mailbags:null,flightpk:null,containerDetails:null},flightCarrierFilter };
 const data = { mailAcceptanceList,flightCarrierFilter}
 const url = 'rest/mail/operations/outbound/fetchFlightVolumeDetails';
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        handleResponse(dispatch, response, data, constant.FETCH_VOLUME,flightActionsEnabled);
        return response
    })
        .catch(error => {
            return error;
        });
    //  dispatch( { type: 'LIST_DETAILS', data:data});
}
function handleResponse(dispatch, response, data, mode,flightActionsEnabled, tableFilter={}) {
    
    if (!isEmpty(response.results)) {
        if(mode === 'display') {
        const { mailflightspage, mailcarrierspage, wareHouses } = response.results[0];
        const filterData = response.results[0].flightCarrierFilter;
        const summaryFilter = makeSummaryFilter(filterData);
        if (data.flightCarrierFilter.assignTo === 'F') {
            dispatch({ type: constant.LIST_DETAILS_FLIGHT, mailflightspage,tableFilter, wareHouses, data, mode,summaryFilter,flightActionsEnabled });
        }
        if (data.flightCarrierFilter.assignTo === 'C') {
            dispatch({ type: constant.LIST_DETAILS_CARRIER, mailcarrierspage,wareHouses, data, mode,flightActionsEnabled });
        }
      }
      else if(mode === 'multi') {
        const { mailflightspage, mailcarrierspage, wareHouses } = response.results[0];
        const filterData = response.results[0].flightCarrierFilter;
        const summaryFilter = makeSummaryFilter(filterData);
        if (data.flightCarrierFilter.assignTo === 'F') {
            dispatch({ type: constant.LIST_DETAILS_FLIGHT, mailflightspage,tableFilter, wareHouses, data,summaryFilter, mode,flightActionsEnabled });
        }
        if (data.flightCarrierFilter.assignTo === 'C') {
            dispatch({ type:  constant.LIST_DETAILS_CARRIER, mailcarrierspage,wareHouses, data, mode,flightActionsEnabled });
        }
      }
      else if(mode === constant.FETCH_PREADVICE) {
        const { flightPreAdviceDetails } = response.results[0];
         dispatch({ type: constant.LIST_PREADVICE_FLIGHT,flightPreAdviceDetails,mode});
      }
      else if(mode === constant.FETCH_CAPACITY) {
           const { flightCapacityDetails } = response.results[0];
            dispatch({ type: constant.LIST_CAPACITY_FLIGHT,flightCapacityDetails,mode});
      }
      else if(mode === constant.FETCH_VOLUME) {
        const { flightVolumeDetails } = response.results[0];
         dispatch({ type: constant.LIST_VOLUME_FLIGHT,flightVolumeDetails,mode});
      }
      //}

    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.CLEAR_TABLE });
            if(mode !== constant.FETCH_CAPACITY  && data && data.flightCarrierFilter.assignTo === 'F') {
                dispatch({ type: constant.CLEAR_FLIGHT_TABLE_DATA });
            }
        }
    }
}

export const clearDetails = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({ type: constant.CLEAR_FILTER });
    dispatch(reset(constant.OUTBOUND_FILTER));
    dispatch(change(constant.OUTBOUND_FILTER, 'flightnumber.carrierCode', state.commonReducer.flightCarrierCode));
    dispatch(change(constant.OUTBOUND_FILTER, 'filterType', state.commonReducer.flightCarrierflag));
    dispatch(reset('MailbagFilter'));
    dispatch(reset('carditFilter'));
    dispatch({ type: constant.CLEAR_FLIGHT_FILTER });
}

export function toggleFilter(values) {
    const { dispatch, args,getState } = values;
    const state = getState();
    dispatch(reset('outboundFilter'));
    dispatch({ type: constant.TOGGLE_FILTER, screenMode:args }) ;
    let flightCarrierflag = state.filterReducer.filterValues.filterType ? state.filterReducer.filterValues.filterType:state.commonReducer.flightCarrierflag
    dispatch({ type: constant.CHOOSE_FILTER, data:flightCarrierflag }) ;
}

export function toggleCarditView() {
    return { type: constant.TOGGLE_CARDIT_VIEW };
}



export const clearFilter = (values) => {
    const { dispatch } = values;
    dispatch({ type: constant.CLEAR_FILTER });
    dispatch(reset(constant.OUTBOUND_FILTER));

}
export function makeSummaryFilter(data){
    let filter={};
    let popOver={};
    let count = 0;
    let popoverCount =0
    if(data.flightNumber && data.flightNumber.length > 0){
        filter={...filter, flightNumber:data.flightNumber};
        count++;
    }
    if(data.flightDate && data.flightDate.length > 0){
        filter={...filter, flightDate:data.flightDate};
        count++;
    }
    if(data.airportCode && data.airportCode.length > 0){
        filter={...filter, airportCode:data.airportCode};
        count++;
    }
    if(data.destination && data.destination.length > 0){
        filter={...filter, destination:data.destination};
        count++;
    }
    if(data.fromDate && data.fromDate.length > 0){
        filter={...filter, fromDate:data.fromDate};
        count++;
    }
    if(data.toDate && data.toDate.length > 0){
        if(count<5){
            filter={...filter, toDate:data.toDate};
            count++;
        }else{
            popOver={...popOver, toDate:data.toDate};
            popoverCount++;
        } 
    }
    if(data.flightOperationalStatus && data.flightOperationalStatus.length > 0){
        if(count<5){
            filter={...filter, flightOperationalStatus:data.flightOperationalStatus};
            count++;
        }else{
            popOver={...popOver, flightOperationalStatus:data.flightOperationalStatus};
            popoverCount++;
        }  
    }
    if(data.operatingReference && data.operatingReference.length > 0){
        if(count<5){
            filter={...filter, operatingReference:data.operatingReference};
            count++;
        }else{
            popOver={...popOver, operatingReference:data.operatingReference};
            popoverCount++;
        }  
    }
    if(data.flightStatus && data.flightStatus.length > 0){
        if(count<5){   
            filter={...filter, flightStatus:data.flightStatus};
             count++;
        }else{
            popOver={...popOver, flightStatus:data.flightStatus};
            popoverCount++;
        }  
    }
    if(data.mailFlightOnly && data.mailFlightOnly === true){
        if(count<5){   
            filter={...filter, mailFlightOnly:data.mailFlightOnly};
             count++;
        }else{
            popOver={...popOver, mailFlightOnly:data.mailFlightOnly};
            popoverCount++;
        }  
    }
    const summaryFilter = {filter:filter, popOver:popOver, popoverCount:popoverCount};
    return summaryFilter;

}

