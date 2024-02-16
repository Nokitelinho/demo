import {handleResponse} from './handleresponse.js'
import { ActionType, Urls } from '../constants/constants.js'
import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { clearError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { makeSummaryFilter } from './filteraction';

export const attachAwb = (values) =>{
    const { args, dispatch, getState } = values;
    const state = getState();
    let activeTab = state.commonReducer.activeTab;
    if(activeTab=== 'BookingView'){
    const mailBookingDetailsCollection=[];
    const selectedAwbIndexes = state.detailsReducer.selectedAwbIndex?state.detailsReducer.selectedAwbIndex:[]
    const awbDetails  = state.filterReducer.awbDetails&&state.filterReducer.awbDetails.results?
                            state.filterReducer.awbDetails.results:{}
    if(selectedAwbIndexes.length===0){
        return Promise.reject(new Error("Please select at least one row"));
    }else if(selectedAwbIndexes.length>1){
        return Promise.reject(new Error("Please select only one row"));
    }                     
    for(var i=0; i<selectedAwbIndexes.length; i++){
        var awbDetailSelected = awbDetails[selectedAwbIndexes[i]];
            awbDetailSelected={...awbDetailSelected, attachmentSource:'BKG'}
        mailBookingDetailsCollection.push(awbDetailSelected);
    }

    const carditFilter=state.commonReducer.carditFilter;
    const selectedMailBags=state.commonReducer.selectedMailbagVos;
    const data={mailBookingDetailsCollection,carditFilter,selectedMailBags};
    const warningFlag=args&&args.warningFlag?args.warningFlag:null;
    const url= Urls.ATTACH_AWB;
            return makeRequest({
                url,
                data: {...data,warningFlag}
            }).then(function (response) {
                const payLoad={type:ActionType.ATTACH_AWB,response:response,dispatch}
                handleResponse(payLoad);
                return response
                
            })
            .catch(error => {
                return error;
            });    
        }else if(activeTab==='LoadPlanView')   {
            const loadPlanDetailsCollection=[];
            const selectedLoadPlanAwbIndexes = state.detailsReducer.selectedLoadPlanAwbIndex?state.detailsReducer.selectedLoadPlanAwbIndex:[]
            const loadPlanBookingDetails  = state.filterReducer.loadPlanBookingDetails&&state.filterReducer.loadPlanBookingDetails.results?
                                    state.filterReducer.loadPlanBookingDetails.results:{}
            if(selectedLoadPlanAwbIndexes.length===0){
                return Promise.reject(new Error("Please select at least one row"));
            }else if(selectedLoadPlanAwbIndexes.length>1){
                return Promise.reject(new Error("Please select only one row"));
            }                     
            for(var i=0; i<selectedLoadPlanAwbIndexes.length; i++){
                const awbDetailSelected = loadPlanBookingDetails[selectedLoadPlanAwbIndexes[i]];
                awbDetailSelected={...awbDetailSelected, attachmentSource:'LODPLN'}
                loadPlanDetailsCollection.push(awbDetailSelected);
            }
        
            const carditFilter=state.commonReducer.carditFilter;
            const selectedMailBags=state.commonReducer.selectedMailbagVos;
            const data={loadPlanDetailsCollection,carditFilter,selectedMailBags};
            const warningFlag=args&&args.warningFlag?args.warningFlag:null;
            const url= 'rest/addons/mail/operations/mailawbbooking/attachLoadPlanAwb'
                    return makeRequest({
                        url,
                        data: {...data,warningFlag}
                    }).then(function (response) {
                        const payLoad={type:'ATTACH_LOAD_PLAN_AWB',response:response,dispatch}
                        handleResponse(payLoad);
                        return response
                        
                    })
                    .catch(error => {
                        return error;
                    });    
        }       
    else if (activeTab === 'ManifestView') {
        const manifestDetailsCollection = [];
        const selectedManifestAwbIndexes = state.detailsReducer.selectedManifestAwbIndex ? state.detailsReducer.selectedManifestAwbIndex : []
        const manifestBookingDetails = state.filterReducer.manifestBookingDetails && state.filterReducer.manifestBookingDetails.results ?
            state.filterReducer.manifestBookingDetails.results : {}
        if (selectedManifestAwbIndexes.length === 0) {
            return Promise.reject(new Error("Please select at least one row"));
        } else if (selectedManifestAwbIndexes.length > 1) {
            return Promise.reject(new Error("Please select only one row"));
        }
        for (var i = 0; i < selectedManifestAwbIndexes.length; i++) {
            const awbDetailSelected = manifestBookingDetails[selectedManifestAwbIndexes[i]];
            awbDetailSelected = { ...awbDetailSelected, attachmentSource: 'MANFST' }
            manifestDetailsCollection.push(awbDetailSelected);
        }

        const carditFilter = state.commonReducer.carditFilter;
        const selectedMailBags = state.commonReducer.selectedMailbagVos;
        const data = { manifestDetailsCollection, carditFilter, selectedMailBags };
        const warningFlag = args && args.warningFlag ? args.warningFlag : null;
        const url = 'rest/addons/mail/operations/mailawbbooking/attachManifestAwb'
        return makeRequest({
            url,
            data: { ...data, warningFlag }
        }).then(function (response) {
            const payLoad = { type: 'ATTACH_MANIFEST_AWB', response: response, dispatch }
            handleResponse(payLoad);
            return response

        })
            .catch(error => {
                return error;
            });
    }
}

export const updateSortVariables= (values) => {
    const {dispatch} = values;
    dispatch({type:'UPDATE_SORT_VARIABLE',data: values.args})
}

export const applyAwbFilter =(values) =>{
    const { args, dispatch, getState } = values;
    const state = getState();

    dispatch(clearError());
    const displayPage=args&&args.displayPage?args.displayPage:state.filterReducer.displayPage;
    const pageSize=args&&args.pageSize? args.pageSize:state.filterReducer.pageSize;
    let awbFilter=state.form.awbDetailsFilterForm ?state.form.awbDetailsFilterForm.values:{}
    const flightNumber=state.form.awbDetailsFilterForm && state.form.awbDetailsFilterForm.values?state.form.awbDetailsFilterForm.values.flightnumber:{};
    if(!isEmpty(flightNumber)) {
        awbFilter = {
            ...awbFilter, bookingFlightNumber: flightNumber.flightNumber,
            bookingCarrierCode: flightNumber.carrierCode
        }
    }
    awbFilter={...awbFilter,screenName:"MTK056"}
    let summaryFilter = InitialSummaryFilter(awbFilter);
    dispatch({type:'FILTER_DETAILS', summaryFilter:summaryFilter})
    // if(awbFilter && state.filterReducer.flagForMandatoryFieldsError===1 && (awbFilter.bookingFrom==="" || awbFilter.bookingTo==="")){
    //     return Promise.reject(new Error("Booking From and Booking To are mandatory"));
    // }
    const data={awbFilter,displayPage,pageSize,oneTimeValues:state.commonReducer.oneTimeValues};
    const url= Urls.LIST_MAIL_AWB_BOOKING;
            return makeRequest({
                url,
                data: {...data}
            }).then(function (response) {
                response={...response,summaryFilter:makeSummaryFilter(response)};
                const payLoad={type:ActionType.LIST_AWB_SUCCESS,response:response,dispatch}
                handleResponse(payLoad);
                return response
                
            })
            .catch(error => {
                return error;
            });
}

export const InitialSummaryFilter=(awbFilter)=> {

    let filter = {};
    let popOver = {};
    let count = 0;
    let popoverCount = 0;
    let data = awbFilter?awbFilter:null;
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

export const onClearAwbFilter=(values)=>{
    const {  dispatch } = values;
    dispatch({
        type: "@@redux-form/INITIALIZE", meta: { form: 'awbDetailsFilterForm', keepDirty: true },
        payload: {}
    })
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'awbDetailsFilterForm' } })
}

export const onClearLoadPlanFilter=(values)=>{
    const {  dispatch } = values;
    dispatch({
        type: "@@redux-form/INITIALIZE", meta: { form: 'loadPlanViewFilterForm', keepDirty: true },
        payload: {}
    })
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'loadPlanViewFilterForm' } })
}
export const onClearManifestFilter = (values) => {
    const { dispatch } = values;
    dispatch({
        type: "@@redux-form/INITIALIZE", meta: { form: 'manifestViewFilterForm', keepDirty: true },
        payload: {}
    })
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'manifestViewFilterForm' } })
}

