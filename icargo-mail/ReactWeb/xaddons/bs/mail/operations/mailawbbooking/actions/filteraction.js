import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {handleResponse} from './handleresponse.js'
import { ActionType, Urls, Errors, Constants } from '../constants/constants.js'
import { clearError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

export const toggleFilter = (screenMode) => {  
  return {type: ActionType.TOGGLE_FILTER, screenMode };
}


export const listAwbDetails = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();

    dispatch(clearError());
    const displayPage=args&&args.displayPage?args.displayPage:state.filterReducer.displayPage;
    const pageSize=args&&args.pageSize? args.pageSize:10;
    let awbFilter=state.form.mailAwbBookingFilter ?state.form.mailAwbBookingFilter.values:{}
    const flightNumber=state.form.mailAwbBookingFilter && state.form.mailAwbBookingFilter.values?state.form.mailAwbBookingFilter.values.flightnumber:{};
    if(!isEmpty(flightNumber)) {
        awbFilter={...awbFilter, bookingFlightNumber:flightNumber.flightNumber,
                            bookingCarrierCode:flightNumber.carrierCode}
    }
    awbFilter={...awbFilter,screenName:"MTK067"}
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


export const clearFilter=(values)=> {
    const {dispatch} = values;  
    const payLoad={type: ActionType.CLEAR_FILTER,dispatch}
    handleResponse(payLoad);  
}


const makeSummaryFilter=(response)=> {

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
        flightnumber={flightNumber : data.bookingFlightNumber,
                        carrierCode : data.bookingCarrierCode}

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



