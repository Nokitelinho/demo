import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import TabPanel from '../panels/TabPanel.jsx'
import { toggleFilter, listAwbDetails, clearFilter, changeTab, onlistLoadPlanDetails, clearLoadplanFilter,onlistManifestDetails,clearManifestFilter } from '../../actions/filteraction';
import {asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {Constants} from '../../constants/constants.js'

const mapStateToProps = (state) => {
  return {
     screenMode:state.filterReducer.screenMode,
     filterValues:  state.filterReducer.filterValues,
     airportCode:state.commonReducer.airportCode,
     oneTimeValues: state.commonReducer.oneTimeValues,
     displayPage:state.filterReducer.displayPage,
     activeTab:state.commonReducer.activeTab,
     pageSize:state.filterReducer.pageSize,
        initialValues: {
            bookingFrom: state.filterReducer.awbFilter.bookingFrom ? state.filterReducer.awbFilter.bookingFrom : null,
                     bookingTo:state.filterReducer.awbFilter.bookingTo?state.filterReducer.awbFilter.bookingTo:null,
            agentCode:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.agentCode?state.filterReducer.summaryFilter.filter.agentCode
                  :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.agentCode?
                  state.filterReducer.summaryFilter.popOver.agentCode:null,
            shipmentPrefix: state.filterReducer.summaryFilter&& state.filterReducer.summaryFilter.filter && state.filterReducer.summaryFilter.filter.shipmentPrefix?
                  state.filterReducer.summaryFilter.filter.shipmentPrefix:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.shipmentPrefix?
                  state.filterReducer.summaryFilter.popOver.shipmentPrefix:null,
            masterDocumentNumber: state.filterReducer.summaryFilter&& state.filterReducer.summaryFilter.filter && state.filterReducer.summaryFilter.filter.masterDocumentNumber?
                  state.filterReducer.summaryFilter.filter.masterDocumentNumber:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.masterDocumentNumber?
                  state.filterReducer.summaryFilter.popOver.masterDocumentNumber:null,   
            mailScc:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.mailScc?state.filterReducer.summaryFilter.filter.mailScc
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.mailScc?
                state.filterReducer.summaryFilter.popOver.mailScc:null,
            mailProduct:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.mailProduct?state.filterReducer.summaryFilter.filter.mailProduct
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.mailProduct?
                state.filterReducer.summaryFilter.popOver.mailProduct:null,
            orginOfBooking:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.orginOfBooking?state.filterReducer.summaryFilter.filter.orginOfBooking
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.orginOfBooking?
                state.filterReducer.summaryFilter.popOver.orginOfBooking:null,
            destinationOfBooking:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.destinationOfBooking?state.filterReducer.summaryFilter.filter.destinationOfBooking
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.destinationOfBooking?
                state.filterReducer.summaryFilter.popOver.destinationOfBooking:null,
            viaPointOfBooking:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.viaPointOfBooking?state.filterReducer.summaryFilter.filter.viaPointOfBooking
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.viaPointOfBooking?
                state.filterReducer.summaryFilter.popOver.viaPointOfBooking:null,
            shipmentDate:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.shipmentDate?state.filterReducer.summaryFilter.filter.shipmentDate
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.shipmentDate?
                state.filterReducer.summaryFilter.popOver.shipmentDate:null,
            flightnumber : state.filterReducer.summaryFilter&& state.filterReducer.summaryFilter.filter&&state.filterReducer.summaryFilter.filter.flightnumber?state.filterReducer.summaryFilter.filter.flightnumber:
                state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.flightnumber?
                state.filterReducer.summaryFilter.popOver.flightnumber:null,
            bookingFlightFrom:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.bookingFlightFrom?state.filterReducer.summaryFilter.filter.bookingFlightFrom
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.bookingFlightFrom?
                state.filterReducer.summaryFilter.popOver.bookingFlightFrom:null,
            bookingFlightTo:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.bookingFlightTo?state.filterReducer.summaryFilter.filter.bookingFlightTo
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.bookingFlightTo?
                state.filterReducer.summaryFilter.popOver.bookingFlightTo:null,
            customerCode:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.customerCode?state.filterReducer.summaryFilter.filter.customerCode
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.customerCode?
                state.filterReducer.summaryFilter.popOver.customerCode:null,
            bookingUserId:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.bookingUserId?state.filterReducer.summaryFilter.filter.bookingUserId
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.bookingUserId?
                state.filterReducer.summaryFilter.popOver.bookingUserId:null,
            bookingStatus:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.bookingStatus?state.filterReducer.summaryFilter.filter.bookingStatus
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.bookingStatus?
                state.filterReducer.summaryFilter.popOver.bookingStatus:null,
            shipmentStatus:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.shipmentStatus?state.filterReducer.summaryFilter.filter.shipmentStatus
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.shipmentStatus?
                state.filterReducer.summaryFilter.popOver.shipmentStatus:null,
            plannedFlightDateFrom:state.filterReducer.loadPlanFilter.plannedFlightDateFrom?state.filterReducer.loadPlanFilter.plannedFlightDateFrom:null,
            plannedFlightDateTo:state.filterReducer.loadPlanFilter.plannedFlightDateTo?state.filterReducer.loadPlanFilter.plannedFlightDateTo:null,     

            shipmentPrefix: state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter && state.filterReducer.loadPlanSummaryFilter.filter.shipmentPrefix?
                  state.filterReducer.loadPlanSummaryFilter.filter.shipmentPrefix:state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.shipmentPrefix?
                  state.filterReducer.loadPlanSummaryFilter.popOver.shipmentPrefix:null,
            masterDocumentNumber: state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter && state.filterReducer.loadPlanSummaryFilter.filter.masterDocumentNumber?
                  state.filterReducer.loadPlanSummaryFilter.filter.masterDocumentNumber:state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.masterDocumentNumber?
                  state.filterReducer.loadPlanSummaryFilter.popOver.masterDocumentNumber:null,     
            flightDate: state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter&&state.filterReducer.loadPlanSummaryFilter.filter.flightDate?state.filterReducer.loadPlanSummaryFilter.filter.flightDate:
                state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.flightDate?
                state.filterReducer.loadPlanSummaryFilter.popOver.flightDate:null,  
            origin: state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter&&state.filterReducer.loadPlanSummaryFilter.filter.origin?state.filterReducer.loadPlanSummaryFilter.filter.origin:
                state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.origin?
                state.filterReducer.loadPlanSummaryFilter.popOver.origin:null,
            destination: state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter&&state.filterReducer.loadPlanSummaryFilter.filter.destination?state.filterReducer.loadPlanSummaryFilter.filter.destination:
                state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.destination?
                state.filterReducer.loadPlanSummaryFilter.popOver.destination:null,

            mailScc: state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter&&state.filterReducer.loadPlanSummaryFilter.filter.mailScc?state.filterReducer.loadPlanSummaryFilter.filter.mailScc:
                state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.mailScc?
                state.filterReducer.loadPlanSummaryFilter.popOver.mailScc:null,
            flightnumber: state.filterReducer.loadPlanSummaryFilter && state.filterReducer.loadPlanSummaryFilter.filter && state.filterReducer.loadPlanSummaryFilter.filter.flightNumber ? {
                carrierCode: state.filterReducer.loadPlanSummaryFilter.filter.flightNumber.split(' ')[0],
                flightNumber: state.filterReducer.loadPlanSummaryFilter.filter.flightNumber.split(' ')[1]
            } :
                state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.flightNumber?
                    state.filterReducer.loadPlanSummaryFilter.popOver.flightNumber : null,
            pol: state.commonReducer.activeTab === 'LoadPlanView' && state.filterReducer.loadPlanFilter.pol ?
                state.filterReducer.loadPlanFilter.pol : state.commonReducer.activeTab === 'ManifestView' && state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.pol ? state.filterReducer.manifestSummaryFilter.filter.pol : state.commonReducer.activeTab === 'ManifestView' &&
                    state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.pol ?
                    state.filterReducer.manifestSummaryFilter.popOver.pol : null,
            pou: state.commonReducer.activeTab === 'LoadPlanView' && state.filterReducer.loadPlanSummaryFilter && state.filterReducer.loadPlanSummaryFilter.filter && state.filterReducer.loadPlanSummaryFilter.filter.pou ? state.filterReducer.loadPlanSummaryFilter.filter.pou :
                state.commonReducer.activeTab === 'LoadPlanView' && state.filterReducer.loadPlanSummaryFilter && state.filterReducer.loadPlanSummaryFilter.popOver && state.filterReducer.loadPlanSummaryFilter.popOver.pou ?
                    state.filterReducer.loadPlanSummaryFilter.popOver.pou : state.commonReducer.activeTab === 'ManifestView' && state.filterReducer.manifestFilter.pou ?
                        state.filterReducer.manifestFilter.pou : null,
            manifestDateFrom: state.filterReducer.manifestFilter.manifestDateFrom ? state.filterReducer.manifestFilter.manifestDateFrom : null,
            manifestDateTo: state.filterReducer.manifestFilter.manifestDateTo ? state.filterReducer.manifestFilter.manifestDateTo : null,
           
            shipmentPrefix: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.shipmentPrefix ?
                state.filterReducer.manifestSummaryFilter.filter.shipmentPrefix : state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.shipmentPrefix ?
                    state.filterReducer.manifestSummaryFilter.popOver.shipmentPrefix : null,
            masterDocumentNumber: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.masterDocumentNumber ?
                state.filterReducer.manifestSummaryFilter.filter.masterDocumentNumber : state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.masterDocumentNumber ?
                    state.filterReducer.manifestSummaryFilter.popOver.masterDocumentNumber : null,
            flightDate: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.flightDate ? state.filterReducer.manifestSummaryFilter.filter.flightDate :
                state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.flightDate ?
                    state.filterReducer.manifestSummaryFilter.popOver.flightDate : null,
            origin: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.origin ? state.filterReducer.manifestSummaryFilter.filter.origin :
                state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.origin ?
                    state.filterReducer.manifestSummaryFilter.popOver.origin : null,
            destination: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.destination ? state.filterReducer.manifestSummaryFilter.filter.destination :
                state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.destination ?
                    state.filterReducer.manifestSummaryFilter.popOver.destination : null,

            mailScc: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.mailScc ? state.filterReducer.manifestSummaryFilter.filter.mailScc :
                state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.mailScc ?
                    state.filterReducer.manifestSummaryFilter.popOver.mailScc : null,
            flightnumber: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.flightNumber ? {
                carrierCode: state.filterReducer.manifestSummaryFilter.filter.flightNumber.split(' ')[0],
                flightNumber: state.filterReducer.manifestSummaryFilter.filter.flightNumber.split(' ')[1]
            } :
                state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.flightNumber ?
                    state.filterReducer.manifestSummaryFilter.popOver.flightNumber : null
  },
     filter:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter?
              state.filterReducer.summaryFilter.filter:null,
     popOver:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver?
              state.filterReducer.summaryFilter.popOver:null,
     popoverCount:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popoverCount?
              state.filterReducer.summaryFilter.popoverCount:null,
     loadPlanFilter: state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.filter?
            state.filterReducer.loadPlanSummaryFilter.filter:null,
     loadPlanPopOver:state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver?
            state.filterReducer.loadPlanSummaryFilter.popOver:null,
     loadPlanPopoverCount:state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popoverCount?
            state.filterReducer.loadPlanSummaryFilter.popoverCount : null,
        manifestFilter: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter ?
            state.filterReducer.manifestSummaryFilter.filter : null,
        manifestPopOver: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver ?
            state.filterReducer.manifestSummaryFilter.popOver : null,
        manifestPopoverCount: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popoverCount ?
            state.filterReducer.manifestSummaryFilter.popoverCount : null
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
    onToggleFilter: (screenMode) => {
          dispatch(toggleFilter(screenMode));
    },
   onlistAwbDetails: () => {
          dispatch(asyncDispatch(listAwbDetails)({'displayPage':'1','pageSize':'20',mode:Constants.LIST}))
    },
    onclearAwbDetails: () => {
          dispatch(dispatchAction(clearFilter)());
    },
    changeTab: (currentTab) => {
        dispatch(dispatchAction(changeTab)({currentTab}));
        if(currentTab==='LoadPlanView'){
            dispatch(asyncDispatch(onlistLoadPlanDetails)({'displayPage':'1','pageSize':'20',mode:Constants.LIST,'buttonClick':'false'}))
        }else if(currentTab==='BookingView'){
            dispatch(asyncDispatch(listAwbDetails)({'displayPage':'1','pageSize':'20',mode:Constants.LIST}))
        }
            else if (currentTab === 'ManifestView') {
               dispatch(asyncDispatch(onlistManifestDetails)({ 'displayPage': '1', 'pageSize': '20', mode: Constants.LIST,'buttonClick': 'false' }))
            }
      },
    onlistLoadPlanDetails :() =>{
        dispatch(asyncDispatch(onlistLoadPlanDetails)({'displayPage':'1','pageSize':'20',mode:Constants.LIST,'buttonClick':'true'}))
    } ,
    onclearLoadPlanDetails: () => {
        dispatch(dispatchAction(clearLoadplanFilter)());
        },
        onlistManifestDetails: () => {
            dispatch(asyncDispatch(onlistManifestDetails)({ 'displayPage': '1', 'pageSize': '20', mode: Constants.LIST, 'buttonClick': 'true' }))
        },
        onclearManifestDetails: () => {
            dispatch(dispatchAction(clearManifestFilter)());
    }

  }
}
const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(TabPanel)


export default FilterContainer