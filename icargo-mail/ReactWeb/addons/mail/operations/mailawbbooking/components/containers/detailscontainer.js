import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { listAwbDetails, onlistLoadPlanDetails, onlistLoadPlanFilter ,onlistManifestDetails,onlistManifestFilter} from '../../actions/filteraction';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {Constants} from '../../constants/constants.js'
import { updateSortVariables, applyAwbFilter, onClearAwbFilter, onClearLoadPlanFilter,onClearManifestFilter } from '../../actions/detailsaction';
import { createSelector } from 'icoreact/lib/ico/framework/component/common/app/util';
import moment from 'moment';

    const getAwbSortDetails = (state) => state.filterReducer.sort;
    const getLoadPlanSortDetails = (state) => state.filterReducer.sort;
const getManifestSortDetails = (state) => state.filterReducer.sort;
    const getAwbTableResults = (state) =>  state.filterReducer.awbDetails && state.filterReducer.awbDetails.results ? state.filterReducer.awbDetails.results : [];
    const getLoadPlanTableResults = (state) => state.filterReducer.loadPlanBookingDetails && state.filterReducer.loadPlanBookingDetails.results? state.filterReducer.loadPlanBookingDetails.results : [];
const getManifestTableResults = (state) => state.filterReducer.manifestBookingDetails && state.filterReducer.manifestBookingDetails.results ? state.filterReducer.manifestBookingDetails.results : [];

    const getAwbSortedDetails = createSelector([getAwbSortDetails, getAwbTableResults], (sortDetails, awbs) => {
        if (sortDetails) {
            const sortBy = sortDetails.sortBy;
            const sortorder = sortDetails.sortByItem;
               
                 if(sortBy === 'bookedPieces'){ 
                    for(let i=0; i<awbs.length; i++){
                      awbs[i] = {...awbs[i], 'bookedPieces':parseFloat(awbs[i].bookedPieces)} 
            }
        }
                  else if(sortBy === 'bookedWeight'){ 
                    for(let i=0; i<awbs.length; i++){
                      awbs[i] = {...awbs[i], 'bookedWeight':parseFloat(awbs[i].bookedWeight)} 
            }
        } else if (sortBy === 'bookedVolume') {
                    for(let i=0; i<awbs.length; i++){
                      awbs[i] = {...awbs[i], 'bookedVolume':parseFloat(awbs[i].bookedVolume)} 
                  }
                }
                
                  awbs = awbs.sort((record1, record2) => {
                    let sortVal = 0;
                    let data1;
                    let data2;
                    data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
                    data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];

                if(sortBy==='shipmentDate'|| sortBy==='bookingDate'){
                  if((moment.utc(data1).diff(moment.utc(data2)))>0){
                      sortVal=1;
                  }else if((moment.utc(data1).diff(moment.utc(data2)))<0){
                      sortVal=-1;
                  }
              }else{
                if(data1===null){
                   data1='';
                }    
                if(data2===null){
                   data2='';
                }
                if (data1 > data2) {
                    sortVal = 1;
                }
                if (data1 < data2) {
                    sortVal = -1;
                }
            }
                if (sortorder === 'DSC') {
                    sortVal = sortVal * -1;
                } 
                return sortVal;
             
            });
        }
        return [...awbs];
        });

        const getLoadPlanSortedDetails = createSelector([getLoadPlanSortDetails, getLoadPlanTableResults], (sortDetails, loadPlanawbs) => {
            if (sortDetails) {
                const sortBy = sortDetails.sortBy;
                const sortorder = sortDetails.sortByItem;
                   
                     if(sortBy === 'standardPieces'){ 
                        for(let i=0; i<loadPlanawbs.length; i++){
                            loadPlanawbs[i] = {...loadPlanawbs[i], 'standardPieces':parseFloat(loadPlanawbs[i].standardPieces)} 
            }
        }
                      else if(sortBy === 'standardWeight'){ 
                        for(let i=0; i<loadPlanawbs.length; i++){
                            loadPlanawbs[i] = {...loadPlanawbs[i], 'standardWeight':parseFloat(loadPlanawbs[i].standardWeight)} 
            }
        } else if (sortBy === 'volume') {
                        for(let i=0; i<loadPlanawbs.length; i++){
                            loadPlanawbs[i] = {...loadPlanawbs[i], 'volume':parseFloat(loadPlanawbs[i].volume)} 
            }
        } else if (sortBy === 'plannedWeight') {
                        for(let i=0; i<loadPlanawbs.length; i++){
                            loadPlanawbs[i] = {...loadPlanawbs[i], 'plannedWeight':parseFloat(loadPlanawbs[i].plannedWeight)} 
            }
        } else if (sortBy === 'plannedPieces') {
                        for(let i=0; i<loadPlanawbs.length; i++){
                            loadPlanawbs[i] = {...loadPlanawbs[i], 'plannedPieces':parseFloat(loadPlanawbs[i].plannedPieces)} 
            }
        }
                    
                    loadPlanawbs = loadPlanawbs.sort((record1, record2) => {
                        let sortVal = 0;
                        let data1;
                        let data2;
                        data1 = record1[sortBy] && typeof record1[sortBy] === "object"?record1[sortBy].systemValue:record1[sortBy];
                        data2 =record2[sortBy] && typeof record2[sortBy] === "object"? record2[sortBy].systemValue:record2[sortBy];
    
                    if(sortBy==='flightDate'){
                      if((moment.utc(data1).diff(moment.utc(data2)))>0){
                          sortVal=1;
                      }else if((moment.utc(data1).diff(moment.utc(data2)))<0){
                          sortVal=-1;
                      }
                  }else{
                    if(data1===null){
                       data1='';
                    }    
                    if(data2===null){
                       data2='';
                    }
                    if (data1 > data2) {
                        sortVal = 1;
                    }
                    if (data1 < data2) {
                        sortVal = -1;
                }
            }
                    if (sortorder === 'DSC') {
                        sortVal = sortVal * -1;
                    } 
                    return sortVal;
                 
                });
            }
            return [...loadPlanawbs];
            });

const getManifestSortedDetails = createSelector([getManifestSortDetails, getManifestTableResults], (sortDetails, manifestawbs) => {
    if (sortDetails) {
        const sortBy = sortDetails.sortBy;
        const sortorder = sortDetails.sortByItem;
        if (sortBy === 'standardPieces') {
            for (let i = 0; i < manifestawbs.length; i++) {
                manifestawbs[i] = { ...manifestawbs[i], 'standardPieces': parseFloat(manifestawbs[i].standardPieces) }
            }
        }
        else if (sortBy === 'standardWeight') {
            for (let i = 0; i < manifestawbs.length; i++) {
                manifestawbs[i] = { ...manifestawbs[i], 'standardWeight': parseFloat(manifestawbs[i].standardWeight) }
            }
        } else if (sortBy === 'volume') {
            for (let i = 0; i < manifestawbs.length; i++) {
                manifestawbs[i] = { ...manifestawbs[i], 'volume': parseFloat(manifestawbs[i].volume) }
            }
        } else if (sortBy === 'plannedWeight') {
            for (let i = 0; i < manifestawbs.length; i++) {
                manifestawbs[i] = { ...manifestawbs[i], 'plannedWeight': parseFloat(manifestawbs[i].plannedWeight) }
            }
        } else if (sortBy === 'plannedPieces') {
            for (let i = 0; i < manifestawbs.length; i++) {
                manifestawbs[i] = { ...manifestawbs[i], 'plannedPieces': parseFloat(manifestawbs[i].plannedPieces) }
            }
        }
        manifestawbs = manifestawbs.sort((record1, record2) => {
            let sortVal = 0;
            let data1;
            let data2;
            data1 = record1[sortBy] && typeof record1[sortBy] === "object" ? record1[sortBy].systemValue : record1[sortBy];
            data2 = record2[sortBy] && typeof record2[sortBy] === "object" ? record2[sortBy].systemValue : record2[sortBy];
            if (sortBy === 'flightDate') {
                if ((moment.utc(data1).diff(moment.utc(data2))) > 0) {
                    sortVal = 1;
                } else if ((moment.utc(data1).diff(moment.utc(data2))) < 0) {
                    sortVal = -1;
                }
            } else {
                if (data1 === null) {
                    data1 = '';
                }
                if (data2 === null) {
                    data2 = '';
                }
                if (data1 > data2) {
                    sortVal = 1;
                }
                if (data1 < data2) {
                    sortVal = -1;
                }
            }
            if (sortorder === 'DSC') {
                sortVal = sortVal * -1;
            }
            return sortVal;
        });
    }
    return [...manifestawbs];
});
const mapStateToProps= (state) =>{
  return {
      screenMode:state.filterReducer.screenMode,
      displayMode:state.commonReducer.displayMode,
      awbDetails:state.filterReducer.awbDetails,
      oneTimeValues: state.commonReducer.oneTimeValues,
      displayPage:state.filterReducer.displayPage,
      pageSize:state.filterReducer.pageSize,
      activeTab:state.commonReducer.activeTab,
      clearFlag: state.filterReducer.clearFlag,
      loadPlanBookingDetails: state.filterReducer.loadPlanBookingDetails,
        manifestBookingDetails:state.filterReducer.manifestBookingDetails,
      awblist:getAwbSortedDetails(state),
      loadPlanAwbList:getLoadPlanSortedDetails(state),
        manifestAwbList:getManifestSortedDetails(state),
      filter:{
            bookingFrom:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.bookingFrom?state.filterReducer.summaryFilter.filter.bookingFrom
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.bookingFrom?
                state.filterReducer.summaryFilter.popOver.bookingFrom:null,

            bookingTo:state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.filter&& state.filterReducer.summaryFilter.filter.bookingTo?state.filterReducer.summaryFilter.filter.bookingTo
                :state.filterReducer.summaryFilter&&state.filterReducer.summaryFilter.popOver&& state.filterReducer.summaryFilter.popOver.bookingTo?
                state.filterReducer.summaryFilter.popOver.bookingTo:null,

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
                state.filterReducer.summaryFilter.popOver.shipmentStatus:null  ,    
        },
        loadPlanFilter:{
            plannedFlightDateFrom:state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter && state.filterReducer.loadPlanSummaryFilter.filter.plannedFlightDateFrom?
                state.filterReducer.loadPlanSummaryFilter.filter.plannedFlightDateFrom:state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.plannedFlightDateFrom?
                state.filterReducer.loadPlanSummaryFilter.popOver.plannedFlightDateFrom:null,
            plannedFlightDateTo:state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter && state.filterReducer.loadPlanSummaryFilter.filter.plannedFlightDateTo?
                state.filterReducer.loadPlanSummaryFilter.filter.plannedFlightDateTo:state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.plannedFlightDateTo?
                state.filterReducer.loadPlanSummaryFilter.popOver.plannedFlightDateTo:null,     
            pol:state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter && state.filterReducer.loadPlanSummaryFilter.filter.pol?
                state.filterReducer.loadPlanSummaryFilter.filter.pol:state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.pol?
                state.filterReducer.loadPlanSummaryFilter.popOver.pol:null,
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
            pou: state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter&&state.filterReducer.loadPlanSummaryFilter.filter.pou?state.filterReducer.loadPlanSummaryFilter.filter.pou:
                    state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.pou?
                    state.filterReducer.loadPlanSummaryFilter.popOver.pou:null,
            mailScc: state.filterReducer.loadPlanSummaryFilter&& state.filterReducer.loadPlanSummaryFilter.filter&&state.filterReducer.loadPlanSummaryFilter.filter.mailScc?state.filterReducer.loadPlanSummaryFilter.filter.mailScc:
                    state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.mailScc?
                    state.filterReducer.loadPlanSummaryFilter.popOver.mailScc:null,
            flightnumber: state.filterReducer.loadPlanSummaryFilter && state.filterReducer.loadPlanSummaryFilter.filter && state.filterReducer.loadPlanSummaryFilter.filter.flightNumber ? {
                carrierCode: state.filterReducer.loadPlanSummaryFilter.filter.flightNumber.split(' ')[0],
                flightNumber: state.filterReducer.loadPlanSummaryFilter.filter.flightNumber.split(' ')[1]
            } :
                    state.filterReducer.loadPlanSummaryFilter&&state.filterReducer.loadPlanSummaryFilter.popOver&& state.filterReducer.loadPlanSummaryFilter.popOver.flightNumber?
                    state.filterReducer.loadPlanSummaryFilter.popOver.flightNumber:null   
        },
        manifestFilter: {
            manifestFrom: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.manifestFrom ?
                state.filterReducer.manifestSummaryFilter.filter.manifestFrom : state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.manifestFrom ?
                    state.filterReducer.manifestSummaryFilter.popOver.manifestFrom : null,
            manifestTo: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.manifestTo ?
                state.filterReducer.manifestSummaryFilter.filter.manifestTo : state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.manifestTo ?
                    state.filterReducer.manifestSummaryFilter.popOver.manifestTo : null,
            pol: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.pol ?
                state.filterReducer.manifestSummaryFilter.filter.pol : state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.pol ?
                    state.filterReducer.manifestSummaryFilter.popOver.pol : null,
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
            pou: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.pou ? state.filterReducer.manifestSummaryFilter.filter.pou :
                state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.pou ?
                    state.filterReducer.manifestSummaryFilter.popOver.pou : null,
            mailScc: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.mailScc ? state.filterReducer.manifestSummaryFilter.filter.mailScc :
                state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.mailScc ?
                    state.filterReducer.manifestSummaryFilter.popOver.mailScc : null,
            flightnumber: state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.filter && state.filterReducer.manifestSummaryFilter.filter.flightNumber ? {
                carrierCode: state.filterReducer.manifestSummaryFilter.filter.flightNumber.split(' ')[0],
                flightNumber: state.filterReducer.manifestSummaryFilter.filter.flightNumber.split(' ')[1]
            } :
                state.filterReducer.manifestSummaryFilter && state.filterReducer.manifestSummaryFilter.popOver && state.filterReducer.manifestSummaryFilter.popOver.flightNumber ?
                    state.filterReducer.manifestSummaryFilter.popOver.flightNumber : null
        }
    }
}

const mapDispatchToProps = (dispatch,ownProps) => {
  return {
        saveSelectedAwbsIndex:(indexes)=> {
            dispatch({ type: 'SAVE_SELECTED_INDEX',indexes})
          },
        updateSortVariables: (sortBy, sortByItem) => {
            dispatch(dispatchAction(updateSortVariables)({ sortBy, sortByItem }))
        },
        onApplyAwbFilter:(displayPage,pageSize)=>{
          dispatch(dispatchAction(applyAwbFilter)({'displayPage':displayPage,'pageSize':pageSize,mode:Constants.LIST}));
       },
        exportToExcel:(displayPage,pageSize)=>{
            return dispatch(asyncDispatch(listAwbDetails)({'displayPage':displayPage,'pageSize':pageSize, mode:'EXPORT'}))
        },
        onClearAwbFilter:()=>{
            dispatch(dispatchAction(onClearAwbFilter)());
       },
        onlistAwbDetails: (displayPage,pageSize) => {
            dispatch(asyncDispatch(listAwbDetails)({'displayPage':displayPage,'pageSize':pageSize,mode:Constants.LIST}))
        },
        onlistLoadPlanDetails :(displayPage,pageSize) =>{
            dispatch(asyncDispatch(onlistLoadPlanDetails)({'displayPage':displayPage,'pageSize':pageSize,mode:Constants.LIST,'buttonClick':null}))
        },
        LoadPlanExportToExcel:(displayPage,pageSize)=>{
            return dispatch(asyncDispatch(onlistLoadPlanDetails)({'displayPage':displayPage,'pageSize':pageSize, mode:'EXPORT'}))
        },
        onApplyLoadPlanFilter:(displayPage,pageSize)=>{
            dispatch(dispatchAction(onlistLoadPlanFilter)({'displayPage':displayPage,'pageSize':pageSize,mode:Constants.LIST}));
        },
        onClearLoadPlanFilter:()=>{
            dispatch(dispatchAction(onClearLoadPlanFilter)());
       },
       saveSelectedLoadPlanAwbsIndex:(indexes)=> {
        dispatch({ type: 'SAVE_SELECTED_LOAD_PLAN_AWB_INDEX',indexes})
        },
        onlistManifestDetails: (displayPage,pageSize) => {
            dispatch(asyncDispatch(onlistManifestDetails)({ 'displayPage': displayPage, 'pageSize': pageSize, mode: Constants.LIST, 'buttonClick':null }))
        },
        manifestExportToExcel: (displayPage, pageSize) => {
            return dispatch(asyncDispatch(onlistManifestDetails)({ 'displayPage': displayPage, 'pageSize': pageSize, mode: 'EXPORT' }))
        },
        onApplyManifestFilter: (displayPage, pageSize) => {
            dispatch(dispatchAction(onlistManifestFilter)({ 'displayPage': displayPage, 'pageSize': pageSize, mode: Constants.LIST }));
        },
        onClearManifestFilter: () => {
            dispatch(dispatchAction(onClearManifestFilter)());
        },
        saveSelectedManifestAwbsIndex: (indexes) => {
            dispatch({ type: 'SAVE_SELECTED_MANIFEST_AWB_INDEX', indexes })
        },
    }
  
}



  const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer