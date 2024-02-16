import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { selectContainers,onSaveFunction,onOKRemarks,ondoneGroupRemarks,onbucketSort,onRaiseClaim,onAccept,originInputChange,claimHistory,moveToAction,moveToActionIndividual,onReset } from '../../actions/commonaction';
import { requestValidationError } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {listInvoicDetails,clearFilter} from '../../actions/filteraction';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';



const mapStateToProps= (state) =>{
  return {


      displayMode:state.filterReducer.displayMode,
      noData:state.filterReducer.noData,
      mailbags:state.filterReducer.mailbagsdetails,
      selectedMailbagIndex:state.filterReducer.selectedMailbagIndex,
    tableFilter: state.filterReducer.tableFilter,
    oneTimeValues: state.commonReducer.oneTimeValues,
    //origin:filter(state.commonReducer.origin),
   // displayOrigin:filter(state.commonReducer.displayOrigin),
    origin:state.commonReducer.origin,
    displayOrigin:state.commonReducer.displayOrigin,
    displayDestination:state.commonReducer.displayDestination,
    invoicFilterForm:state.form.invoicFilter,
    clmlessfiv:state.filterReducer.clmlessfiv,
    clmfivtoten:state.filterReducer.clmfivtoten,
    clmtentotwentyfiv:state.filterReducer.clmtentotwentyfiv,
    clmtwentyfivtofifty:state.filterReducer.clmtwentyfivtofifty,
    clmfiftytohundred:state.filterReducer.clmfiftytohundred,
    clmhundredtofivhundred:state.filterReducer.clmhundredtofivhundred,
    clmgrtfivhundred:state.filterReducer.clmgrtfivhundred,
    fromScreen:state.filterReducer.fromScreen,
    filterValues: state.filterReducer.filterValues,
    mailSubClassCodes:state.commonReducer.mailSubClassCodes,
    currencyCode:state.filterReducer.currencyCode,
    cntawtinc: state.filterReducer.cntawtinc,
    cntovrnotmra: state.filterReducer.cntovrnotmra,
    clmzropay: state.filterReducer.clmzropay,
    clmnoinc: state.filterReducer.clmnoinc,
    clmratdif: state.filterReducer.clmratdif,
    clmwgtdif: state.filterReducer.clmwgtdif,
    clmmisscn: state.filterReducer.clmmisscn,
    clmlatdlv: state.filterReducer.clmlatdlv,
    clmsrvrsp: state.filterReducer.clmsrvrsp,
    latdlv: state.filterReducer.latdlv,
    dlvscnwrg: state.filterReducer.dlvscnwrg,
    misorgscn: state.filterReducer.misorgscn,
    misdstscn: state.filterReducer.misdstscn,
    fulpaid: state.filterReducer.fulpaid,
    ovrratdif: state.filterReducer.ovrratdif,
    ovrwgtdif: state.filterReducer.ovrwgtdif,
    ovrclsdif: state.filterReducer.ovrclsdif,
    ovrsrvrsp: state.filterReducer.ovrsrvrsp,
    ovroth: state.filterReducer.ovroth,
    clmoth: state.filterReducer.clmoth,
    clmnotinv: state.filterReducer.clmnotinv,
    ovrpayacp: state.filterReducer.ovrpayacp,
    ovrpayrej: state.filterReducer.ovrpayrej,
    clmfrcmjr:state.filterReducer.clmfrcmjr,
    dummyorg: state.filterReducer.dummyorg,
    dummydst: state.filterReducer.dummydst,
    shrpayacp:state.filterReducer.shrpayacp,
    clmstagen:state.filterReducer.clmstagen,
    clmstasub:state.filterReducer.clmstasub,
    amotobeact:state.filterReducer.amotobeact,
    amotact:state.filterReducer.amotact
  }
}

/*const filter=(data)=>{
  let origin=[];
  data.forEach(element => {
    const originExpanded={value:element,label:element};
    origin.push(originExpanded);
  });


  return origin;
}*/


/*const filter=(data)=>{
  let origin=[];
  let i=1;
  for(i=0;i<10;i++){
    const originExpanded={value:data[i],label:data[i]};
    if(!isEmpty(originExpanded)){
    origin.push(originExpanded);
  }
  }
  return origin;
}*/

const mapDispatchToProps = (dispatch,ownProps) => {
  return {
   selectContainers: (data) => {
           dispatch(dispatchAction(selectContainers)(data))
      },
   saveSelectedMailbagsIndex:(indexes)=> {
     dispatch({ type: 'SAVE_SELECTED_INDEX',indexes})
   },
   disableAcceptButton:()=> {
    dispatch({ type: 'DISABLE_ACCEPT'})
  },
  enableAcceptButton:()=> {
    dispatch({ type: 'ENABLE_ACCEPT'})
  },
   /* onSaveFunction: (data) => {
      dispatch(asyncDispatch(onSaveFunction)(data)).then(() => {
        dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
      })
    },*/

    onOKRemarks: (data) => {
      dispatch(asyncDispatch(onOKRemarks)(data)).then(() => {
        dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
         } )
    },

   ondoneGroupRemarks: (data) => {
      dispatch(asyncDispatch(ondoneGroupRemarks)(data))
    },
    onbucketSort: (data) => {
      dispatch(asyncDispatch(onbucketSort)(data))
    },
    displayError: (message, target) => {
      dispatch(requestValidationError(message, target))
   },
  /*onRaiseClaim: (data) => {
    dispatch(asyncDispatch(onRaiseClaim)(data)).then((response) => {
      dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
    })
  },
  onAccept: (data) => {
    dispatch(asyncDispatch(onAccept)(data)).then(() => {
      dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
    })
  },*/
originInputChange:(data) => {
    dispatch(dispatchAction(originInputChange)(data));

  },
  claimHistory : (data) =>{
    dispatch(dispatchAction(claimHistory)(data))

  },
  moveToAction :(data) => {
    dispatch(asyncDispatch(moveToAction)(data)).then(() => {
      dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
    })
  },
  moveToActionIndividual :(data) => {
    dispatch(asyncDispatch(moveToActionIndividual)(data)).then(() => {
      dispatch(asyncDispatch(listInvoicDetails)({ 'displayPage': '1', mode: 'LIST' }))
    })
  },
  onlistInvoicDetails: (displayPage, pageSize) => {
    dispatch(asyncDispatch(listInvoicDetails)({displayPage,mode:'LIST',pageSize}))
   },
   onReset: (data) => {
    dispatch(dispatchAction(onReset)(data));
  },










   }
}

  const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer