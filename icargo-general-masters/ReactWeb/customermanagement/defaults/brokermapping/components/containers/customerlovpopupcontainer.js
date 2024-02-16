import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import CustomerLovPopupPanel from '../panels/popup/CustomerLovPopupPanel.jsx';
import {
    dispatchAction,
    asyncDispatch
  } from 'icoreact/lib/ico/framework/component/common/actions';
  import {
    validateCustomerForm, clearFilter,closeCustomerPopup,handleClear ,updateCustomerListform
  } from '../../actions/commonaction';
 import { CLEAR_FILTER,DISPLAY_SELECTED_INDEX,DISPLAY_SELECTED_CUSTOMER } from '../../constants/constants.js';

  const mapStateToProps = state => {
    return {
        showCustomerLovPopupFlag: state.commonReducer.showCustomerLovPopupFlag,
        customerListDetails :state.commonReducer.customerListDetails,
        selectedCustomerDetails:state.commonReducer.selectedCustomerIndex,
        oneTimeValues: state.commonReducer.oneTimeValues,
        initialValues: {
        customerType:["AG","CU","TMP"],
        stationCode:state.commonReducer.station?state.commonReducer.station:"",
        }
    };
}

const mapDispatchToProps = dispatch => {
    return {
        onList: () => {
            dispatch(dispatchAction(updateCustomerListform)());
          },
          onClearFilter: () => {
          dispatch(asyncDispatch(clearFilter)());    
          },
          closeCustomerPopup: () => {
            dispatch(dispatchAction(closeCustomerPopup)());
          },
          handleClear: () => {
            dispatch({
              type: CLEAR_FILTER
            });
          },
          displaySelectedIndex: (values) => {
            dispatch({type:DISPLAY_SELECTED_INDEX,data:values})
          },
          selectCustomer: ( customerCode) => {
            dispatch({type:DISPLAY_SELECTED_CUSTOMER,data:customerCode})
          },
          selectPoaCustomer:(customerDetails)=>{
            dispatch({type:"DISPLAY_SELECTED_CUSTOMERDETAILS",data:customerDetails})
          }
    };
}

const CustomerLovPopupContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
  )(CustomerLovPopupPanel);
  
  export default CustomerLovPopupContainer;