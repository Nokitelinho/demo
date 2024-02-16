import * as constant from '../constants/constants';
import moment from 'moment';
const intialState = {
    activeDeviationListTab: 'LIST_VIEW', 
    listDisplayPage: '',
    groupDisplayPage: '',
    deviationlistMailbags: '',
    deviationlistMailbagsArray: [],
    deviationGroupMailbags: '',
    deviationSummary: '',
    deviationFilterApplied: true,
    filterValues: {
    },
    selectedDeviationList: []
}
const deviationListReducer = (state = intialState, action) => {
    switch (action.type) {
        case constant.SCREENLOAD_SUCCESS: {
            return {
                ...state,
                filterValues: {
                    fromDate: moment(action.data.fromDate,'DD-MMM-YYYY').subtract('days',2).format('DD-MMM-YYYY'),
                    toDate: action.data.toDate,
                    mailStatus: 'E'
                }
            }
        }
        case constant.CHANGE_DEVIATION_TAB:
            return {
                ...state,
                activeDeviationListTab: action.data,
                selectedDeviationList: [],
                deviationlistMailbags: '',
                deviationlistMailbagsArray: [],
                deviationGroupMailbags: '',
                deviationSummary: {}
            }
        case constant.DEVIATION_LIST_VIEW:
            return {
                ...state,
                deviationlistMailbags: action.deviationlistMailbags,
                deviationlistMailbagsArray: action.deviationlistMailbags ? action.deviationlistMailbags.results : [],
                listDisplayPage: action.data.mailbagFilter.displayPage,
                deviationSummary: action.carditSummary,
                selectedDeviationList: []
            }
        case constant.DEVIATION_GROUP_VIEW:
            return {
                ...state,
                deviationGroupMailbags: action.deviationlistGroupedMailbags,
                groupDisplayPage: action.data.mailbagFilter.displayPage,
                deviationSummary: action.carditSummary,
                selectedDeviationList: []
            }
        case constant.DEVIATION_FILTER_APPLIED:
            return {
                ...state,
                filterValues: { ...action.data.mailbagFilter },
            }
        case constant.DEVIATION_MAIL_SELECTED:
            return {
                ...state,
                selectedDeviationList: action.data.selectedDeviationList
            }
        case constant.CLEAR_DEVIATIONLIST_FILTER:
            return {
                ...state,
                filterValues: action.data
            }
        case constant.LIST_NO_RECORDS: 
            return {
                ...state,
                selectedDeviationList: [],
                deviationlistMailbags: '',
                deviationlistMailbagsArray: [],
                deviationGroupMailbags: '',
                deviationSummary: {}
            }
        case constant.CHANGE_CARDITLYINGLIST_TAB:
            return {
                ...state,
                selectedDeviationList: []
            }
        case constant.CLEAR_FLIGHT_FILTER:
            return {
                ...intialState,
            }
        default:
            return state;
    }
}

export default deviationListReducer;


