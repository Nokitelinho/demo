import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { asyncDispatch,dispatchAction, dispatchPrint } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {reset} from 'redux-form';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import {reRateMailbags,navigateToRouting,onSurchargeDetailsPopup,onChangeStatusPopup,onAutoMCA,updateBillingDetails,navigateToListAccountingEntries} from '../../actions/filteraction';
import {onClose} from '../../actions/commonaction';

const mapStateToProps = (state) => {
  return {
    displayMode:state.filterReducer.displayMode,
    mcaPrivilege:state.commonReducer.mcaPrivilege,
    mailbagsdetails : state.filterReducer.mailbagsdetails,
  }
}
const mapDispatchToProps = (dispatch, ownProps) => {
  return {
          onReRate:()=>{
          dispatch(asyncDispatch(reRateMailbags)())
          },
          onCloseFunction:()=>{
          dispatch(dispatchAction(onClose)())
          },
          navigateToRouting:(mailbag)=>{
          dispatch(asyncDispatch(navigateToRouting)(mailbag))  
          },
          onSurchargeDetailsPopup:()=>{
          dispatch(asyncDispatch(onSurchargeDetailsPopup)()) 
          },
          onChangeStatusPopup:()=>{
          dispatch(asyncDispatch(onChangeStatusPopup)())   
          },
          onAutoMCA:()=>{
          dispatch(asyncDispatch(onAutoMCA)())   
          },
          onListAccountingEntries:()=>{
           dispatch(asyncDispatch(navigateToListAccountingEntries)())  
          }
  }
}
const ActionButtonContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)

export default ActionButtonContainer