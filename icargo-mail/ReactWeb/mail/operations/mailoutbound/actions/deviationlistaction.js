import { makeRequest } from 'icoreact/lib/ico/framework/component/util/ajax';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import * as constant from '../constants/constants';
import { getCurrentDate } from 'icoreact/lib/ico/framework/component/util/util';
import moment from 'moment';
export function applyDeviationListFilter(values) {
    const { args, dispatch, getState } = values;
    const state = getState();
    let mailbagFilter = {};
    let displayPage = '';
    const activeDeviationTab = (state.deviationListReducer.activeDeviationListTab) ? state.deviationListReducer.activeDeviationListTab : ''
    if (args) {
        mailbagFilter = state.deviationListReducer.filterValues;

        if (activeDeviationTab === constant.LIST_VIEW) {
            displayPage = args && args.displayPage ? args.displayPage : state.deviationListReducer.listDisplayPage
        }
        else {
            displayPage = args && args.displayPage ? args.displayPage : state.deviationListReducer.groupDisplayPage
        }
    }
    else {
        mailbagFilter = (state.form.deviationListFilter) ? state.form.deviationListFilter.values : (state.deviationListReducer.filterValues) ? state.deviationListReducer.filterValues : ''
        const flightNumber = (mailbagFilter.flightnumber) ? mailbagFilter.flightnumber : {};
        if (!isEmpty(flightNumber)) {
            mailbagFilter.carrierCode = flightNumber.carrierCode;
            mailbagFilter.flightNumber = flightNumber.flightNumber;
            mailbagFilter.flightDate = flightNumber.flightDate;
        }
        displayPage = '1';
    }
    if(mailbagFilter.scanPort == null ||mailbagFilter.scanPort === undefined || mailbagFilter.scanPort.trim().length == 0){
        mailbagFilter.scanPort = state.filterReducer.filterValues ?  state.filterReducer.filterValues.airportCode : state.commonReducer.airportCode;
    }
    if(mailbagFilter.fromDate == null ||mailbagFilter.fromDate === undefined || mailbagFilter.fromDate.trim().length == 0){
        mailbagFilter.fromDate = getCurrentDate();
    }
    if(mailbagFilter.toDate == null ||mailbagFilter.toDate === undefined || mailbagFilter.toDate.trim().length == 0){
        mailbagFilter.toDate = getCurrentDate();
    }
    mailbagFilter.displayPage = displayPage;
    mailbagFilter.deviationListActiveTab = activeDeviationTab;
    const data = { mailbagFilter };
    const url = 'rest/mail/operations/outbound/listDeviationList';

    if (state.deviationListReducer.deviationFilterApplied === false) {
        return Promise.resolve({})
    }
    else {
        return makeRequest({
            url,
            data: { ...data }
        }).then(function (response) {
            handleResponse(dispatch, response, data);
            return response
        }).catch(error => {
            return error;
        });
    }

}

export function onClickDeviationLlist(values) {
    const { dispatch, getState, args } = values;
    let state = getState();
    let selectedDeviationList = [...state.deviationListReducer.selectedDeviationList];
    if (args) {
        //to unselect all
        if (args.selectAll && args.selectAll === true) {
            if (state.search && state.search['deviationListReducer.deviationlistMailbagsArray'] && state.search['deviationListReducer.deviationlistMailbagsArray'].result.length > 0) {
                selectedDeviationList = state.search['deviationListReducer.deviationlistMailbagsArray'].result.map(item => {
                    return parseInt(item);
                })
            } else {
                selectedDeviationList = state.deviationListReducer.deviationlistMailbagsArray.map(item => {
                    return item.mailSequenceNumber;
                })
            }
        } else if (args.unSelectAll && args.unSelectAll === true) {
            selectedDeviationList = [];
        } else {
            // to select and unselct individual 
            if (args.isRowSelected) {
                selectedDeviationList.push(args.id)
            } else {
                selectedDeviationList = selectedDeviationList.filter(item => item !== args.id)
            }
        }

    }
    dispatch({ type: constant.DEVIATION_MAIL_SELECTED, data: { selectedDeviationList: [...selectedDeviationList] } });
}
export function onClearFilter(values) {
    const { dispatch, getState } = values;
    const state = getState();
    dispatch({
        type: "@@redux-form/INITIALIZE", meta: { form: 'deviationListFilter', keepDirty: true },
        payload: {
            fromDate: moment(state.commonReducer.toDate,'DD-MMM-YYYY').subtract('days',2).format('DD-MMM-YYYY'),
            toDate: state.commonReducer.toDate,
            scanPort: state.commonReducer.airportCode,
            mailStatus: 'E'
        }
    })
    dispatch({ type: "@@redux-form/RESET", meta: { form: 'deviationListFilter' } })

}
function handleResponse(dispatch, response, data) {
    if (!isEmpty(response.results)) {
        const { mailbagFilter } = data;
        if (mailbagFilter.deviationListActiveTab === constant.LIST_VIEW) {
            const { lyinglistMailbags, carditSummary } = response.results[0];
            dispatch({ type: constant.DEVIATION_LIST_VIEW, deviationlistMailbags: { ...lyinglistMailbags }, data, carditSummary });
        }
        if (mailbagFilter.deviationListActiveTab === constant.GROUP_VIEW) {
            const { lyinglistGroupedMailbags, carditSummary } = response.results[0];
            dispatch({ type: constant.DEVIATION_GROUP_VIEW, deviationlistGroupedMailbags: { ...lyinglistGroupedMailbags }, data, carditSummary });
        }
    } else {
        if (!isEmpty(response.errors)) {
            dispatch({ type: constant.LIST_NO_RECORDS });
        }
    }
    dispatch({ type: constant.DEVIATION_FILTER_APPLIED, data });
}