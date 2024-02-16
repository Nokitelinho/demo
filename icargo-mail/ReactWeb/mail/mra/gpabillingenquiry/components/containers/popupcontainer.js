import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { PopupPanel } from '../panels/PopupPanel.jsx';
import { dispatchAction, asyncDispatch, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import {saveChangeStatusPopup,oncloseChangeStatusPopup,oncloseSurchargePopup,oncloseBillingPopup} from '../../actions/popupactions';
import {listBillingDetails,listBillingEntriesDetails} from '../../actions/filteraction';

const mapStateToProps = (state) => {
    return {
        surchargeDetails:state.filterReducer.surchargeDetails,
        showSurchargePopup:state.filterReducer.showSurchargePopup,
        showChangeStatusPopup:state.filterReducer.showChangeStatusPopup,
        dsn:state.filterReducer.dsn,
        oneTimeValuesStatus:state.filterReducer.oneTimeValuesStatus,
        showMailbagIcon:state.filterReducer.showMailbagIcon,
        comparerow:state.screenPopupReducer.comparerow,
        mailbagsdetails : state.filterReducer.mailbagsdetails,
        mailbagsdetailslist: state.filterReducer.mailbagsdetailslist,
        billingStatus:state.filterReducer.billingStatus,
        remarks:state.filterReducer.remarks,
     //   initialValues:!isEmpty(state.filterReducer.billingStatus)?{...state.filterReducer.billingStatus}:'',
        //initialValues:!isEmpty(state.filterReducer.statusChangeValues) ? {...state.filterReducer.statusChangeValues} : null,
    }
}

const mapDispatchToProps = (dispatch) => {
    return {
    closeSurchargePopup:()=>{
      dispatch(dispatchAction(oncloseSurchargePopup)())
        },
    closeBillingPopup:()=>{
      dispatch(dispatchAction(oncloseBillingPopup)())
        },   
    listmailbagdetails: (displayPage,pageSize) => {
     dispatch(asyncDispatch(listBillingEntriesDetails)({displayPage,pageSize,mode:'BLG_LIST',fromBillingList:'Y'}))
    }, 
    closeChangeStatusPopup:()=>{
        dispatch(dispatchAction(oncloseChangeStatusPopup)())
        }, 
    saveChangeStatusPopup:()=>{
        dispatch(asyncDispatch(saveChangeStatusPopup)()).then((response) => {
        dispatch(asyncDispatch(listBillingDetails)({'displayPage':'1',mode:'LIST'}))
          }); 
        }       
    }
}

const PopupContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(PopupPanel)
export default PopupContainer
