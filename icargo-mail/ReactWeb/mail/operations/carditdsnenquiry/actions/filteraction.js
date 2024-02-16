import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax'
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import {handleResponse} from './handleresponse.js'
import { ActionType, Urls, Errors, Constants } from '../constants/constants.js'
import { clearError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

export const toggleFilter = (screenMode) => {  
  return {type: ActionType.TOGGLE_FILTER, screenMode };
}


export const listCarditDsnEnquiry = (values) => {
    const { args, dispatch, getState } = values;
    const state = getState();

    dispatch(clearError());
    handleResponse({type:ActionType.CLEAR_TABLE_FILTER,dispatch});
    const displayPage=args&&args.displayPage?args.displayPage:state.filterReducer.displayPage;
    const pageSize=args&&args.pageSize? args.pageSize:state.filterReducer.pageSize;
    let carditFilter=state.form.carditDsnFilter.values?state.form.carditDsnFilter.values:{}
    const flightNumber=state.form.carditDsnFilter.values?state.form.carditDsnFilter.values.flightnumber:{};
    if(!isEmpty(flightNumber)) {
        carditFilter={...carditFilter, flightNumber:flightNumber.flightNumber,
                        flightDate:flightNumber.flightDate,carrierCode:flightNumber.carrierCode}
    }
    const uldNumber=state.form.carditDsnFilter.values?state.form.carditDsnFilter.values.uldValue:{};
    if(!isEmpty(uldNumber)) {
        carditFilter={...carditFilter,uldNumber:state.form.carditDsnFilter.values.uldValue}
    }
    const masterDocumentNumber=state.form.carditDsnFilter.values?state.form.carditDsnFilter.values.masterDocumentNumber:{};
    if(!isEmpty(masterDocumentNumber)) {
        carditFilter={...carditFilter,uldNumber:state.form.carditDsnFilter.values.uldValue,documentNumber:masterDocumentNumber}
    }
    const error=validateFilterValues(carditFilter);
    if(error){
        const payLoad={type:ActionType.CLEAR_DSN_TABLE,dispatch}
        handleResponse(payLoad);
        return Promise.reject(new Error(error));
    }
    const data={carditFilter,displayPage,pageSize};
    const url= Urls.LIST_CARDIT_DSN_ENQUIRY;
            return makeRequest({
                url,
                data: {...data}
            }).then(function (response) {
                if (args && args.mode === Constants.EXPORT) {
                    let dsnDetails = response.results[0].dsnDetailsCollectionPage;
                    return dsnDetails;
                }else{
                response={...response,summaryFilter:makeSummaryFilter(response)};
                const payLoad={type:ActionType.LIST_DSN_SUCCESS,response:response,dispatch}
                handleResponse(payLoad);
                return response
                }
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
                && response.results[0].carditFilter?response.results[0].carditFilter:null;
    if(data==null){
        return null;
    }
    if (data.ooe && data.ooe.length > 0) {
        if (count < 6) {
            filter = { ...filter, ooe: data.ooe };
            count++;
        }  else {
            popOver = { ...filter, ooe: data.ooe };
            popoverCount++;
        }
    }
    if (data.doe && data.doe.length > 0) {
        if (count < 6) {
            filter = { ...filter, doe: data.doe };
            count++;
        }  else {
            popOver = { ...filter, doe: data.doe };
            popoverCount++;
        }
    }
    if (data.mailCategoryCode && data.mailCategoryCode.length > 0) {
        filter = { ...filter, mailCategoryCode: data.mailCategoryCode };
        count++;
    }
    if (data.mailSubclass && data.mailSubclass.length > 0) {
        if (count < 6) {
            filter = { ...filter, mailSubclass: data.mailSubclass };
            count++;
        }  else {
            popOver = { ...filter, mailSubclass: data.mailSubclass };
            popoverCount++;
        }
    }
    if (data.year && data.year.length > 0) {
        if (count < 6) {
            filter = { ...filter, year: data.year };
            count++;
        }  else {
            popOver = { ...filter, year: data.year };
            popoverCount++;
        }
    }
    if (data.despatchSerialNumber && data.despatchSerialNumber.length > 0) {
        if (count < 6) {
            filter = { ...filter, despatchSerialNumber: data.despatchSerialNumber };
            count++;
        }  else {
            popOver = { ...filter, despatchSerialNumber: data.despatchSerialNumber };
            popoverCount++;
        }
    }
    if (data.conDocNo && data.conDocNo.length > 0) {
        if (count < 6) {
            filter = { ...filter, conDocNo: data.conDocNo };
            count++;
        } else {
            popOver = { ...popOver, conDocNo: data.conDocNo };
            popoverCount++;
        }
    }
    if (data.consignmentDate && data.consignmentDate.length > 0) {
        if (count < 6) {
            filter = { ...filter, consignmentDate: data.consignmentDate };
            count++;
        } else {
            popOver = { ...popOver, consignmentDate: data.consignmentDate };
            popoverCount++;
        }
    }
    if (data.rdtDate && data.rdtDate.length > 0) {
        if (count < 6) {
            filter = { ...filter, rdtDate: data.rdtDate };
            count++;
        } else {
            popOver = { ...popOver, rdtDate: data.rdtDate };
            popoverCount++;
        }
    }
    if (data.rdtTime && data.rdtTime.length > 0) {
        if (count < 6) {
            filter = { ...filter, rdtTime: data.rdtTime };
            count++;
        } else {
            popOver = { ...popOver, rdtTime: data.rdtTime };
            popoverCount++;
        }
    }
    if (data.flightNumber && data.flightNumber.length > 0 && data.carrierCode && data.carrierCode.length > 0
        && data.flightDate && data.flightDate.length > 0) {

        let flightnumber = {};
        flightnumber={flightNumber : data.flightNumber,
                        carrierCode : data.carrierCode,
                            flightDate : data.flightDate}

        if (count < 6) {
            filter = { ...filter, flightnumber: flightnumber };
            count++;
        } else {
            popOver = { ...popOver, flightnumber: flightnumber };
            popoverCount++;
        }

    }
    if (data.airportCode && data.airportCode.length > 0) {
        if (count < 6) {
            filter = { ...filter, airportCode: data.airportCode };
            count++;
        } else {
            popOver = { ...popOver, airportCode: data.airportCode };
            popoverCount++;
        }
    }
    if (data.uldNumber && data.uldNumber.length > 0) {
        if (count < 6) {
            filter = { ...filter, uldNumber: data.uldNumber };
            count++;
        } else {
            popOver = { ...popOver, uldNumber: data.uldNumber };
            popoverCount++;
        }
    }
    if (data.fromDate && data.fromDate.length > 0) {
        if (count < 6) {
            filter = { ...filter, fromDate: data.fromDate };
            count++;
        } else {
            popOver = { ...popOver, fromDate: data.fromDate };
            popoverCount++;
        }
    }
    if (data.toDate && data.toDate.length > 0) {
        if (count < 6) {
            filter = { ...filter, toDate: data.toDate };
            count++;
        } else {
            popOver = { ...popOver, toDate: data.toDate };
            popoverCount++;
        }
    }
    if (data.mailStatus && data.mailStatus.length > 0) {
        if (count < 6) {
            filter = { ...filter, mailStatus: data.mailStatus };
            count++;
        } else {
            popOver = { ...popOver, mailStatus: data.mailStatus };
            popoverCount++;
        }
    }
    if (data.paCode && data.paCode.length > 0) {
        if (count < 6) {
            filter = { ...filter, paCode: data.paCode };
            count++;
        } else {
            popOver = { ...popOver, paCode: data.paCode };
            popoverCount++;
        }
    }
    if (data.awbAttached && data.awbAttached.length > 0) {
        if (count < 6) {
            filter = { ...filter, awbAttached: data.awbAttached };
            count++;
        } else {
            popOver = { ...popOver, awbAttached: data.awbAttached };
            popoverCount++;
        }
    }
    if (data.flightType && data.flightType.length > 0) {
        if (count < 6) {
            filter = { ...filter, flightType: data.flightType };
            count++;
        } else {
            popOver = { ...popOver, flightType: data.flightType };
            popoverCount++;
        }
    }
    if ((data.documentNumber && data.documentNumber.length > 0)||
            (data.shipmentPrefix && data.shipmentPrefix.length > 0)) {
        if (count < 6) {
            filter = { ...filter, shipmentPrefix: data.shipmentPrefix,documentNumber: data.documentNumber};
            count++;
        } else {
            popOver = { ...popOver, shipmentPrefix: data.shipmentPrefix,documentNumber: data.documentNumber };
            popoverCount++;
        }
    }

    const summaryFilter = { filter: filter, popOver: popOver, popoverCount: popoverCount };
    return summaryFilter;
}

const validateFilterValues=(filterValues)=>{

    if(isEmpty(filterValues)){
        return Errors.MANDATORY_FILTER_ERR
    }
    else{
        if(filterValues.despatchSerialNumber==undefined&&filterValues.conDocNo==undefined&&
            (filterValues.fromDate==undefined||filterValues.toDate==undefined)&&filterValues.consignmentDate==undefined&&
                (filterValues.shipmentPrefix==undefined||filterValues.documentNumber==undefined)&&(filterValues.carrierCode==undefined||
                    filterValues.flightNumber==undefined||filterValues.flightDate==undefined)){
                        return Errors.MANDATORY_FILTER_ERR
                    }
        return null;
    }
}

