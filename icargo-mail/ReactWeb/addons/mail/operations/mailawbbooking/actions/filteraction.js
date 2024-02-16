import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {handleResponse} from './handleresponse.js'
import { ActionType, Urls, Forms} from '../constants/constants.js'
import { clearError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { reset } from 'redux-form';
import { change} from 'icoreact/lib/ico/framework/component/common/form';
import { InitialSummaryFilter } from './detailsaction';

export const toggleFilter = (screenMode) => {  
  return {type: ActionType.TOGGLE_FILTER, screenMode };
}


export const listAwbDetails = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();

    
    const displayPage=args&&args.displayPage?args.displayPage:state.filterReducer.displayPage;
    const pageSize=args&&args.pageSize? args.pageSize:state.filterReducer.pageSize;
    let awbFilter=state.form.mailAwbBookingFilter ?state.form.mailAwbBookingFilter.values:{}
    const flightNumber=state.form.mailAwbBookingFilter && state.form.mailAwbBookingFilter.values?state.form.mailAwbBookingFilter.values.flightnumber:{};
    if(!isEmpty(flightNumber)) {
        awbFilter = {
            ...awbFilter, bookingFlightNumber: flightNumber.flightNumber,
            bookingCarrierCode: flightNumber.carrierCode
        }
    }
    awbFilter={...awbFilter,screenName:"MTK056"}
    let summaryFilter = InitialSummaryFilter(awbFilter);
    dispatch({type:'FILTER_DETAILS', summaryFilter:summaryFilter})

    if(awbFilter && state.filterReducer.flagForMandatoryFieldsError===1 && (awbFilter.bookingFrom==="" || awbFilter.bookingTo==="")){
        return Promise.reject(new Error("Provide Mandatory values in filters"));
    }
    const data={awbFilter,displayPage,pageSize,oneTimeValues:state.commonReducer.oneTimeValues};
    const url= Urls.LIST_MAIL_AWB_BOOKING;dispatch(clearError());
            return makeRequest({
                url,
                data: {...data}
            }).then(function (response) {
                response={...response,summaryFilter:makeSummaryFilter(response)};
                if (args && args.mode === 'EXPORT') {
                    let awbDetails = response.results[0].mailBookingDetailsCollectionPage
                    let exportawbDetails = {...response.results[0].mailBookingDetailsCollectionPage}

                    for (var i = 0; i < exportawbDetails.results.length; i++) {
                        exportawbDetails.results[i].bookingFlightNumber = awbDetails.results[i].bookingCarrierCode != ''? awbDetails.results[i].bookingCarrierCode+''+awbDetails.results[i].bookingFlightNumber :'';
                        if(awbDetails.results[i].shipmentPrefix || awbDetails.results[i].masterDocumentNumber) {
                            exportawbDetails.results[i].masterDocumentNumber = (awbDetails.results[i].shipmentPrefix || '') +'-'+ (awbDetails.results[i].masterDocumentNumber || '')
                        }
                       
                    }

                    return exportawbDetails;
                }
                const payLoad={type:ActionType.LIST_AWB_SUCCESS,response:response,dispatch}
                handleResponse(payLoad);
                return response
                
            })
            .catch(error => {
                return error;
            });

}


export const clearFilter=(values)=> {
    const {dispatch, getState} = values;  
    const state = getState();
    let displayPage= state.filterReducer.displayPage?state.filterReducer.displayPage:1;
    let clearFlag= true;
    dispatch({type:ActionType.CLEAR_FILTER})
    dispatch({type:'GET_DISPLAY_PAGE',displayPage,clearFlag})
    dispatch(reset(Forms.MAIL_AWBBOOKING_FILTER));
    dispatch(change(Forms.MAIL_AWBBOOKING_FILTER, 'bookingFrom', state.filterReducer.awbFilter.bookingFrom));
    dispatch(change(Forms.MAIL_AWBBOOKING_FILTER, 'bookingTo', state.filterReducer.awbFilter.bookingTo));
}


export const makeSummaryFilter=(response)=> {

    let filter = {};
    let popOver = {};
    let count = 0;
    let popoverCount = 0;
    let data = response&& response.results&& response.results[0]
                && response.results[0].awbFilter?response.results[0].awbFilter:null;
    if(data==null){
        return null;
    }

    if ((data.documentNumber && data.documentNumber.length > 0)||
            (data.shipmentPrefix && data.shipmentPrefix.length > 0)) {
        if (count < 6) {
            filter = { ...filter, shipmentPrefix: data.shipmentPrefix,masterDocumentNumber: data.masterDocumentNumber};
            count++;
        } else {
            popOver = { ...popOver, shipmentPrefix: data.shipmentPrefix,masterDocumentNumber: data.masterDocumentNumber };
            popoverCount++;
        }
    }
    if (data.bookingFrom && data.bookingFrom.length > 0) {
        if (count < 6) {
            filter = { ...filter, bookingFrom: data.bookingFrom };
            count++;
        } else {
            popOver = { ...popOver, bookingFrom: data.bookingFrom };
            popoverCount++;
        }
    }
    if (data.bookingTo && data.bookingTo.length > 0) {
        if (count < 6) {
            filter = { ...filter, bookingTo: data.bookingTo };
            count++;
        } else {
            popOver = { ...popOver, bookingTo: data.bookingTo };
            popoverCount++;
        }
    }
    if (data.mailScc && data.mailScc.length > 0) {
        if (count < 6) {
            filter = { ...filter, mailScc: data.mailScc };
            count++;
        } else {
            popOver = { ...popOver, mailScc: data.mailScc };
            popoverCount++;
        }
    }
    if (data.mailProduct && data.mailProduct.length > 0) {
        if (count < 6) {
            filter = { ...filter, mailProduct: data.mailProduct };
            count++;
        } else {
            popOver = { ...popOver, mailProduct: data.mailProduct };
            popoverCount++;
        }
    }
    if (data.orginOfBooking && data.orginOfBooking.length > 0) {
        if (count < 6) {
            filter = { ...filter, orginOfBooking: data.orginOfBooking };
            count++;
        } else {
            popOver = { ...popOver, orginOfBooking: data.orginOfBooking };
            popoverCount++;
        }
    }
    if (data.viaPointOfBooking && data.viaPointOfBooking.length > 0) {
        if (count < 6) {
            filter = { ...filter, viaPointOfBooking: data.viaPointOfBooking };
            count++;
        } else {
            popOver = { ...popOver, viaPointOfBooking: data.viaPointOfBooking };
            popoverCount++;
        }
    }
    if (data.destinationOfBooking && data.destinationOfBooking.length > 0) {
        if (count < 6) {
            filter = { ...filter, destinationOfBooking: data.destinationOfBooking };
            count++;
        } else {
            popOver = { ...popOver, destinationOfBooking: data.destinationOfBooking };
            popoverCount++;
        }
    }
    if (data.stationOfBooking && data.stationOfBooking.length > 0) {
        if (count < 6) {
            filter = { ...filter, stationOfBooking: data.stationOfBooking };
            count++;
        } else {
            popOver = { ...popOver, stationOfBooking: data.stationOfBooking };
            popoverCount++;
        }
    }
    if (data.shipmentDate && data.shipmentDate.length > 0) {
        if (count < 6) {
            filter = { ...filter, shipmentDate: data.shipmentDate };
            count++;
        } else {
            popOver = { ...popOver, shipmentDate: data.shipmentDate };
            popoverCount++;
        }
    }
    if (data.bookingCarrierCode && data.bookingCarrierCode.length > 0 && data.bookingFlightNumber && data.bookingFlightNumber.length > 0) {

        let flightnumber = {};
        flightnumber = {
            flightNumber: data.bookingFlightNumber,
            carrierCode: data.bookingCarrierCode
        }

        if (count < 6) {
            filter = { ...filter, flightnumber: flightnumber };
            count++;
        } else {
            popOver = { ...popOver, flightnumber: flightnumber };
            popoverCount++;
        }

    }
    if (data.bookingFlightFrom && data.bookingFlightFrom.length > 0) {
        if (count < 6) {
            filter = { ...filter, bookingFlightFrom: data.bookingFlightFrom };
            count++;
        } else {
            popOver = { ...popOver, bookingFlightFrom: data.bookingFlightFrom };
            popoverCount++;
        }
    }
    
    if (data.bookingFlightTo && data.bookingFlightTo.length > 0) {
        if (count < 6) {
            filter = { ...filter, bookingFlightTo: data.bookingFlightTo };
            count++;
        } else {
            popOver = { ...popOver, bookingFlightTo: data.bookingFlightTo };
            popoverCount++;
        }
    }
    if (data.agentCode && data.agentCode.length > 0) {
        if (count < 6) {
            filter = { ...filter, agentCode: data.agentCode };
            count++;
        } else {
            popOver = { ...popOver, agentCode: data.agentCode };
            popoverCount++;
        }
    }
    if (data.customerCode && data.customerCode.length > 0) {
        if (count < 6) {
            filter = { ...filter, customerCode: data.customerCode };
            count++;
        } else {
            popOver = { ...popOver, customerCode: data.customerCode };
            popoverCount++;
        }
    }
    if (data.bookingUserId && data.bookingUserId.length > 0) {
        if (count < 6) {
            filter = { ...filter, bookingUserId: data.bookingUserId };
            count++;
        } else {
            popOver = { ...popOver, bookingUserId: data.bookingUserId };
            popoverCount++;
        }
    }
    if (data.bookingStatus && data.bookingStatus.length > 0) {
        if (count < 6) {
            filter = { ...filter, bookingStatus: data.bookingStatus };
            count++;
        } else {
            popOver = { ...popOver, bookingStatus: data.bookingStatus };
            popoverCount++;
        }
    }
   
    const summaryFilter = { filter: filter, popOver: popOver, popoverCount: popoverCount };
    return summaryFilter;
}

export function changeTab(values){
    const {dispatch , args} = values
    const currentTab = args.currentTab;
    dispatch({type:'CHANGE_TAB',currentTab});
  }

export function onlistLoadPlanDetails(values){
    const {dispatch,args,getState} = values
    const state = getState();

    dispatch(clearError());
    const displayPage=args&&args.displayPage?args.displayPage:state.filterReducer.displayPage;
    const pageSize=args&&args.pageSize? args.pageSize:state.filterReducer.pageSize;
    var onListButtonClick = args&&args.buttonClick!==null?(args.buttonClick==='false'?false:true):state.filterReducer.onListButtonClick;
    state.filterReducer.onListButtonClick=onListButtonClick;
    let awbFilter=state.form.loadPlanViewForm ?state.form.loadPlanViewForm.values:{}
    const flightNumber=state.form.loadPlanViewForm && state.form.loadPlanViewForm.values?state.form.loadPlanViewForm.values.flightnumber:{};
    if(!isEmpty(flightNumber)) {
        awbFilter = {
            ...awbFilter, flightNumber: flightNumber.flightNumber,
            carrierCode: flightNumber.carrierCode
        }
    }
    awbFilter={...awbFilter, onListButtonClick:onListButtonClick}

    let summaryFilter = InitialLoadPlanSummaryFilter(awbFilter);
    dispatch({type:'LOAD_PLAN_FILTER_DETAILS', summaryFilter:summaryFilter})

    if(onListButtonClick && (awbFilter.plannedFlightDateFrom===null || awbFilter.plannedFlightDateTo===null || awbFilter.pol===null||
        awbFilter.plannedFlightDateFrom==="" || awbFilter.plannedFlightDateTo==="" || awbFilter.pol==="")){
        return Promise.reject(new Error("Provide Mandatory values in filters"));
    }

    const data={awbFilter,displayPage,pageSize};
    const url= 'rest/addons/mail/operations/mailawbbooking/listLoadPlanBookings';
    dispatch(clearError());
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        response={...response,loadPlanSummaryFilter:makeLoadPlanSummaryFilter(response)};
        if (args && args.mode === 'EXPORT') {
            let loadPlanDetails = response.results[0].loadPlanDetailsCollectionPage
            let exportLoadPlanDetails = {...response.results[0].loadPlanDetailsCollectionPage}

            for (var i = 0; i < exportLoadPlanDetails.results.length; i++) {
                exportLoadPlanDetails.results[i].flightNumber = loadPlanDetails.results[i].carrierCode != ''? loadPlanDetails.results[i].carrierCode+''+loadPlanDetails.results[i].flightNumber :''; 
            }

            return exportLoadPlanDetails;
        }
        const payLoad={type:'LIST_LOAD_PLAN_SUCCESS',response:response,dispatch}
        handleResponse(payLoad);
        return response
        
    })
    .catch(error => {
        return error;
    });
}

export const makeLoadPlanSummaryFilter=(response)=> {

    let filter = {};
    let popOver = {};
    let count = 0;
    let popoverCount = 0;
    let data = response&& response.results&& response.results[0]
                && response.results[0].loadPlanFilter?response.results[0].loadPlanFilter:null;
    if(data==null){
        return null;
    }

    if ((data.masterDocumentNumber && data.masterDocumentNumber.length > 0)||
            (data.shipmentPrefix && data.shipmentPrefix.length > 0)) {
        if (count < 6) {
            filter = { ...filter, shipmentPrefix: data.shipmentPrefix,masterDocumentNumber: data.masterDocumentNumber};
            count++;
        } else {
            popOver = { ...popOver, shipmentPrefix: data.shipmentPrefix,masterDocumentNumber: data.masterDocumentNumber };
            popoverCount++;
        }
    }
    if (data.plannedFlightDateFrom && data.plannedFlightDateFrom.length > 0) {
        if (count < 6) {
            filter = { ...filter, plannedFlightDateFrom: data.plannedFlightDateFrom };
            count++;
        } else {
            popOver = { ...popOver, plannedFlightDateFrom: data.plannedFlightDateFrom };
            popoverCount++;
        }
    }
    if (data.plannedFlightDateTo && data.plannedFlightDateTo.length > 0) {
        if (count < 6) {
            filter = { ...filter, plannedFlightDateTo: data.plannedFlightDateTo };
            count++;
        } else {
            popOver = { ...popOver, plannedFlightDateTo: data.plannedFlightDateTo };
            popoverCount++;
        }
    }
    if (data.flightNumber && data.flightNumber.length > 0) {
        if (count < 6) {
            filter = { ...filter, flightNumber: data.carrierCode.concat(' ').concat(data.flightNumber) };
            count++;
        } else {
            popOver = { ...popOver, flightNumber: data.carrierCode.concat(' ').concat(data.flightNumber) };
            popoverCount++;
        }
    }
    if (data.flightDate && data.flightDate.length > 0) {
        if (count < 6) {
            filter = { ...filter, flightDate: data.flightDate };
            count++;
        } else {
            popOver = { ...popOver, flightDate: data.flightDate };
            popoverCount++;
        }
    }
    if (data.origin && data.origin.length > 0) {
        if (count < 6) {
            filter = { ...filter, origin: data.origin };
            count++;
        } else {
            popOver = { ...popOver, origin: data.origin };
            popoverCount++;
        }
    }
    if (data.destination && data.destination.length > 0) {
        if (count < 6) {
            filter = { ...filter, destination: data.destination };
            count++;
        } else {
            popOver = { ...popOver, destination: data.destination };
            popoverCount++;
        }
    }
    if (data.pol && data.pol.length > 0) {
        if (count < 6) {
            filter = { ...filter, pol: data.pol };
            count++;
        } else {
            popOver = { ...popOver, pol: data.pol };
            popoverCount++;
        }
    }
    if (data.pou && data.pou.length > 0) {
        if (count < 6) {
            filter = { ...filter, pou: data.pou };
            count++;
        } else {
            popOver = { ...popOver, pou: data.pou };
            popoverCount++;
        }
    }
    if (data.mailScc && data.mailScc.length > 0) {
        if (count < 6) {
            filter = { ...filter, mailScc: data.mailScc };
            count++;
        } else {
            popOver = { ...popOver, mailScc: data.mailScc };
            popoverCount++;
        }
    }
    
   
    const loadPlanSummaryFilter = { filter: filter, popOver: popOver, popoverCount: popoverCount };
    return loadPlanSummaryFilter;
}

export const clearLoadplanFilter=(values)=> {
    const {dispatch, getState} = values;  
    const state = getState();
    let displayPage= state.filterReducer.displayPage?state.filterReducer.displayPage:1;
    let clearFlag= true;
    dispatch({type:'CLEAR_LOAD_PLAN_FILTER'})
    dispatch({type:'GET_DISPLAY_PAGE',displayPage,clearFlag})
    dispatch(reset('loadPlanViewForm'));
    dispatch(change('loadPlanViewForm', 'plannedFlightDateFrom', state.filterReducer.loadPlanFilter.plannedFlightDateFrom));
    dispatch(change('loadPlanViewForm', 'plannedFlightDateTo', state.filterReducer.loadPlanFilter.plannedFlightDateTo));
    dispatch(change('loadPlanViewForm', 'pol', state.filterReducer.loadPlanFilter.pol));

}

export const InitialLoadPlanSummaryFilter=(loadPlanFilter)=> {

    let filter = {};
    let popOver = {};
    let count = 0;
    let popoverCount = 0;
    let data = loadPlanFilter?loadPlanFilter:null;
    if(data==null){
        return null;
    }

    if ((data.masterDocumentNumber && data.masterDocumentNumber.length > 0)||
            (data.shipmentPrefix && data.shipmentPrefix.length > 0)) {
        if (count < 6) {
            filter = { ...filter, shipmentPrefix: data.shipmentPrefix,masterDocumentNumber: data.masterDocumentNumber};
            count++;
        } else {
            popOver = { ...popOver, shipmentPrefix: data.shipmentPrefix,masterDocumentNumber: data.masterDocumentNumber };
            popoverCount++;
        }
    }
    if (data.plannedFlightDateFrom && data.plannedFlightDateFrom.length > 0) {
        if (count < 6) {
            filter = { ...filter, plannedFlightDateFrom: data.plannedFlightDateFrom };
            count++;
        } else {
            popOver = { ...popOver, plannedFlightDateFrom: data.plannedFlightDateFrom };
            popoverCount++;
        }
    }
    if (data.plannedFlightDateTo && data.plannedFlightDateTo.length > 0) {
        if (count < 6) {
            filter = { ...filter, plannedFlightDateTo: data.plannedFlightDateTo };
            count++;
        } else {
            popOver = { ...popOver, plannedFlightDateTo: data.plannedFlightDateTo };
            popoverCount++;
        }
    }
    if (data.flightNumber && data.flightNumber.length > 0) {
        if (count < 6) {
            filter = { ...filter, flightNumber: data.carrierCode.concat(' ').concat(data.flightNumber) };
            count++;
        } else {
            popOver = { ...popOver, flightNumber: data.carrierCode.concat(' ').concat(data.flightNumber) };
            popoverCount++;
        }
    }
    if (data.flightDate && data.flightDate.length > 0) {
        if (count < 6) {
            filter = { ...filter, flightDate: data.flightDate };
            count++;
        } else {
            popOver = { ...popOver, flightDate: data.flightDate };
            popoverCount++;
        }
    }
    if (data.origin && data.origin.length > 0) {
        if (count < 6) {
            filter = { ...filter, origin: data.origin };
            count++;
        } else {
            popOver = { ...popOver, origin: data.origin };
            popoverCount++;
        }
    }
    if (data.destination && data.destination.length > 0) {
        if (count < 6) {
            filter = { ...filter, destination: data.destination };
            count++;
        } else {
            popOver = { ...popOver, destination: data.destination };
            popoverCount++;
        }
    }
    if (data.pol && data.pol.length > 0) {
        if (count < 6) {
            filter = { ...filter, pol: data.pol };
            count++;
        } else {
            popOver = { ...popOver, pol: data.pol };
            popoverCount++;
        }
    }
    if (data.pou && data.pou.length > 0) {
        if (count < 6) {
            filter = { ...filter, pou: data.pou };
            count++;
        } else {
            popOver = { ...popOver, pou: data.pou };
            popoverCount++;
        }
    }
    if (data.mailScc && data.mailScc.length > 0) {
        if (count < 6) {
            filter = { ...filter, mailScc: data.mailScc };
            count++;
        } else {
            popOver = { ...popOver, mailScc: data.mailScc };
            popoverCount++;
        }
    }
    
   
    const loadPlanSummaryFilter = { filter: filter, popOver: popOver, popoverCount: popoverCount };
    return loadPlanSummaryFilter;
}

export function onlistLoadPlanFilter(values){
    const {dispatch,args,getState} = values
    const state = getState();

    dispatch(clearError());
    const displayPage=args&&args.displayPage?args.displayPage:state.filterReducer.displayPage;
    const pageSize=args&&args.pageSize? args.pageSize:state.filterReducer.pageSize;
    var onListButtonClick = state.form.loadPlanViewFilterForm && state.form.loadPlanViewFilterForm.values&&state.form.loadPlanViewFilterForm.values.pol?true:false
    let awbFilter=state.form.loadPlanViewFilterForm ?state.form.loadPlanViewFilterForm.values:{}
    const flightNumber=state.form.loadPlanViewFilterForm && state.form.loadPlanViewFilterForm.values?state.form.loadPlanViewFilterForm.values.flightnumber:{};
    if(!isEmpty(flightNumber)) {
        awbFilter = {
            ...awbFilter, flightNumber: flightNumber.flightNumber,
            carrierCode: flightNumber.carrierCode
        }
    }
    awbFilter={...awbFilter, onListButtonClick:onListButtonClick}

    let summaryFilter = InitialLoadPlanSummaryFilter(awbFilter);
    dispatch({type:'LOAD_PLAN_FILTER_DETAILS', summaryFilter:summaryFilter})

    const data={awbFilter,displayPage,pageSize};
    const url= 'rest/addons/mail/operations/mailawbbooking/listLoadPlanBookings';
    dispatch(clearError());
    return makeRequest({
        url,
        data: {...data}
    }).then(function (response) {
        response={...response,loadPlanSummaryFilter:makeLoadPlanSummaryFilter(response)};
        const payLoad={type:'LIST_LOAD_PLAN_SUCCESS',response:response,dispatch}
        handleResponse(payLoad);
        return response
    })
        .catch(error => {
            return error;
        });
}
export function onlistManifestDetails(values) {
    const { dispatch, args, getState } = values
    const state = getState();
    dispatch(clearError());
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
    const pageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize;
    var onListButtonClick = args && args.buttonClick === 'false' ? false : true;
    let awbFilter = state.form.manifestViewForm ? state.form.manifestViewForm.values : {}
    const flightNumber = state.form.manifestViewForm && state.form.manifestViewForm.values ? state.form.manifestViewForm.values.flightnumber : {};
    if (!isEmpty(flightNumber)) {
        awbFilter = {
            ...awbFilter, flightNumber: flightNumber.flightNumber,
            carrierCode: flightNumber.carrierCode
        }
    }
    awbFilter = { ...awbFilter, onListButtonClick: onListButtonClick }
    let summaryFilter = initialManifestSummaryFilter(awbFilter);
    dispatch({ type: 'MANIFEST_FILTER_DETAILS', summaryFilter: summaryFilter })
    if ((awbFilter.manifestDateFrom === null || awbFilter.manifestDateTo === null || awbFilter.pou === null ||
        awbFilter.manifestDateFrom === "" || awbFilter.manifestDateTo === "" || awbFilter.pou === "")) {
        return Promise.reject(new Error("Provide Mandatory values in filters"));
    }
    else {
        const data = { awbFilter, displayPage, pageSize };
        const url = 'rest/addons/mail/operations/mailawbbooking/listManifestBookings';
        dispatch(clearError());
        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
            response = { ...response, manifestSummaryFilter: makeManifestSummaryFilter(response) };
            if (args && args.mode === 'EXPORT') {
                let manifestDetails = response.results[0].manifestDetailsCollectionPage
                let exportManifestDetails = { ...response.results[0].manifestDetailsCollectionPage }
                for (var i = 0; i < exportManifestDetails.results.length; i++) {
                    exportManifestDetails.results[i].flightNumber = manifestDetails.results[i].carrierCode != '' ? manifestDetails.results[i].carrierCode + '' + manifestDetails.results[i].flightNumber : '';
                }
                return exportManifestDetails;
            }
            const payLoad = { type: 'LIST_MANIFEST_SUCCESS', response: response, dispatch }
            handleResponse(payLoad);
            return response
        })
            .catch(error => {
                dispatch({ type: 'ERROR_NO_RECORDS' })
                return error;
            });
    }
}
export const makeManifestSummaryFilter = (response) => {
    let filter = {};
    let popOver = {};
    let count = 0;
    let popoverCount = 0;
    let data = response && response.results && response.results[0]
        && response.results[0].manifestFilter ? response.results[0].manifestFilter : null;
    if (data == null) {
        return null;
    }
    if ((data.masterDocumentNumber && data.masterDocumentNumber.length > 0) ||
        (data.shipmentPrefix && data.shipmentPrefix.length > 0)) {
        if (count < 6) {
            filter = { ...filter, shipmentPrefix: data.shipmentPrefix, masterDocumentNumber: data.masterDocumentNumber };
            count++;
        } else {
            popOver = { ...popOver, shipmentPrefix: data.shipmentPrefix, masterDocumentNumber: data.masterDocumentNumber };
            popoverCount++;
        }
    }
    if (data.manifestDateFrom && data.manifestDateFrom.length > 0) {
        if (count < 6) {
            filter = { ...filter, manifestDateFrom: data.manifestDateFrom };
            count++;
        } else {
            popOver = { ...popOver, manifestDateFrom: data.manifestDateFrom };
            popoverCount++;
        }
    }
    if (data.manifestDateTo && data.manifestDateTo.length > 0) {
        if (count < 6) {
            filter = { ...filter, manifestDateTo: data.manifestDateTo };
            count++;
        } else {
            popOver = { ...popOver, manifestDateTo: data.manifestDateTo };
            popoverCount++;
        }
    }
    if (data.flightNumber && data.flightNumber.length > 0) {
        if (count < 6) {
            filter = { ...filter, flightNumber: data.carrierCode.concat(' ').concat(data.flightNumber) };
            count++;
        } else {
            popOver = { ...popOver, flightNumber: data.carrierCode.concat(' ').concat(data.flightNumber) };
            popoverCount++;
        }
    }
    if (data.flightDate && data.flightDate.length > 0) {
        if (count < 6) {
            filter = { ...filter, flightDate: data.flightDate };
            count++;
        } else {
            popOver = { ...popOver, flightDate: data.flightDate };
            popoverCount++;
        }
    }
    if (data.origin && data.origin.length > 0) {
        if (count < 6) {
            filter = { ...filter, origin: data.origin };
            count++;
        } else {
            popOver = { ...popOver, origin: data.origin };
            popoverCount++;
        }
    }
    if (data.destination && data.destination.length > 0) {
        if (count < 6) {
            filter = { ...filter, destination: data.destination };
            count++;
        } else {
            popOver = { ...popOver, destination: data.destination };
            popoverCount++;
        }
    }
    if (data.pol && data.pol.length > 0) {
        if (count < 6) {
            filter = { ...filter, pol: data.pol };
            count++;
        } else {
            popOver = { ...popOver, pol: data.pol };
            popoverCount++;
        }
    }
    if (data.pou && data.pou.length > 0) {
        if (count < 6) {
            filter = { ...filter, pou: data.pou };
            count++;
        } else {
            popOver = { ...popOver, pou: data.pou };
            popoverCount++;
        }
    }
    if (data.mailScc && data.mailScc.length > 0) {
        if (count < 6) {
            filter = { ...filter, mailScc: data.mailScc };
            count++;
        } else {
            popOver = { ...popOver, mailScc: data.mailScc };
            popoverCount++;
        }
    }
    const manifestSummaryFilter = { filter: filter, popOver: popOver, popoverCount: popoverCount };
    return manifestSummaryFilter;
}
export const clearManifestFilter = (values) => {
    const { dispatch, getState } = values;
    const state = getState();
    let displayPage = state.filterReducer.displayPage ? state.filterReducer.displayPage : 1;
    let clearFlag = true;
    dispatch({ type: 'CLEAR_MANIFEST_FILTER' })
    dispatch({ type: 'GET_DISPLAY_PAGE', displayPage, clearFlag })
    dispatch(reset('manifestViewForm'));
    dispatch(change('manifestViewForm', 'manifestDateFrom', state.filterReducer.manifestFilter.manifestDateFrom));
    dispatch(change('manifestViewForm', 'manifestDateTo', state.filterReducer.manifestFilter.manifestDateTo));
    dispatch(change('manifestViewForm', 'pou', state.filterReducer.manifestFilter.pou));
}
export const initialManifestSummaryFilter = (manifestFilter) => {
    let filter = {};
    let popOver = {};
    let count = 0;
    let popoverCount = 0;
    let data = manifestFilter ? manifestFilter : null;
    if (data == null) {
        return null;
    }
    if ((data.masterDocumentNumber && data.masterDocumentNumber.length > 0) ||
        (data.shipmentPrefix && data.shipmentPrefix.length > 0)) {
        if (count < 6) {
            filter = { ...filter, shipmentPrefix: data.shipmentPrefix, masterDocumentNumber: data.masterDocumentNumber };
            count++;
        } else {
            popOver = { ...popOver, shipmentPrefix: data.shipmentPrefix, masterDocumentNumber: data.masterDocumentNumber };
            popoverCount++;
        }
    }
    if (data.manifestDateFrom && data.manifestDateFrom.length > 0) {
        if (count < 6) {
            filter = { ...filter, manifestDateFrom: data.manifestDateFrom };
            count++;
        } else {
            popOver = { ...popOver, manifestDateFrom: data.manifestDateFrom };
            popoverCount++;
        }
    }
    if (data.manifestDateTo && data.manifestDateTo.length > 0) {
        if (count < 6) {
            filter = { ...filter, manifestDateTo: data.manifestDateTo };
            count++;
        } else {
            popOver = { ...popOver, manifestDateTo: data.manifestDateTo };
            popoverCount++;
        }
    }
    if (data.flightNumber && data.flightNumber.length > 0) {
        if (count < 6) {
            filter = { ...filter, flightNumber: data.carrierCode.concat(' ').concat(data.flightNumber) };
            count++;
        } else {
            popOver = { ...popOver, flightNumber: data.carrierCode.concat(' ').concat(data.flightNumber) };
            popoverCount++;
        }
    }
    if (data.flightDate && data.flightDate.length > 0) {
        if (count < 6) {
            filter = { ...filter, flightDate: data.flightDate };
            count++;
        } else {
            popOver = { ...popOver, flightDate: data.flightDate };
            popoverCount++;
        }
    }
    if (data.origin && data.origin.length > 0) {
        if (count < 6) {
            filter = { ...filter, origin: data.origin };
            count++;
        } else {
            popOver = { ...popOver, origin: data.origin };
            popoverCount++;
        }
    }
    if (data.destination && data.destination.length > 0) {
        if (count < 6) {
            filter = { ...filter, destination: data.destination };
            count++;
        } else {
            popOver = { ...popOver, destination: data.destination };
            popoverCount++;
        }
    }
    if (data.pol && data.pol.length > 0) {
        if (count < 6) {
            filter = { ...filter, pol: data.pol };
            count++;
        } else {
            popOver = { ...popOver, pol: data.pol };
            popoverCount++;
        }
    }
    if (data.pou && data.pou.length > 0) {
        if (count < 6) {
            filter = { ...filter, pou: data.pou };
            count++;
        } else {
            popOver = { ...popOver, pou: data.pou };
            popoverCount++;
        }
    }
    if (data.mailScc && data.mailScc.length > 0) {
        if (count < 6) {
            filter = { ...filter, mailScc: data.mailScc };
            count++;
        } else {
            popOver = { ...popOver, mailScc: data.mailScc };
            popoverCount++;
        }
    }
    const manifestSummaryFilter = { filter: filter, popOver: popOver, popoverCount: popoverCount };
    return manifestSummaryFilter;
}
export function onlistManifestFilter(values) {
    const { dispatch, args, getState } = values
    const state = getState();
    dispatch(clearError());
    const displayPage = args && args.displayPage ? args.displayPage : state.filterReducer.displayPage;
    const pageSize = args && args.pageSize ? args.pageSize : state.filterReducer.pageSize;
    var onListButtonClick = state.form.manifestViewFilterForm && state.form.manifestViewFilterForm.values&&state.form.manifestViewFilterForm.values.pol?true:false
    let awbFilter = state.form.manifestViewFilterForm ? state.form.manifestViewFilterForm.values : {}
    const flightNumber = state.form.manifestViewFilterForm && state.form.manifestViewFilterForm.values ? state.form.manifestViewFilterForm.values.flightnumber : {};
    if (!isEmpty(flightNumber)) {
        awbFilter = {
            ...awbFilter, flightNumber: flightNumber.flightNumber,
            carrierCode: flightNumber.carrierCode
        }
    }
    awbFilter={...awbFilter, onListButtonClick:onListButtonClick}
    let summaryFilter = initialManifestSummaryFilter(awbFilter);
    dispatch({ type: 'MANIFEST_FILTER_DETAILS', summaryFilter: summaryFilter })
    const data = { awbFilter, displayPage, pageSize };
    const url = 'rest/addons/mail/operations/mailawbbooking/listManifestBookings';
    dispatch(clearError());
    return makeRequest({
        url,
        data: { ...data }
    }).then(function (response) {
        response = { ...response, manifestSummaryFilter: makeManifestSummaryFilter(response) };
        const payLoad = { type: 'LIST_MANIFEST_SUCCESS', response: response, dispatch }
        handleResponse(payLoad);
        return response
        
    })
    .catch(error => {
        return error;
    });
}