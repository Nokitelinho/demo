import { connectContainer } from "icoreact/lib/ico/framework/component/common/app/container";
import { showCustomerLovPopup,validateFilter,toggleScreenMode } from '../../actions/commonaction';
import { dispatchAction } from "icoreact/lib/ico/framework/component/common/actions";
import {focus} from 'icoreact/lib/ico/framework/component/common/form';
import FilterPanel from '../panels/FilterPanel.jsx';

const mapStateToProps = (state) => {
  return {
      screenMode: state.commonReducer.screenMode,
      showCustomerLovPopupFlag: state.commonReducer.showCustomerLovPopupFlag,
      filterDetails: state.commonReducer.filterDetails,
      
      initialValues: {
        customerCode:state.commonReducer.customerCodeValue ? state.commonReducer.customerCodeValue : ""
      }
  };
};
const mapDispatchToProps = (dispatch) => {
  return {
    showCustomerPopup: () => {
      dispatch(dispatchAction(showCustomerLovPopup)());
    },
    onList: ()=>{
      dispatch(dispatchAction(validateFilter)())
    },
    onToggleScreenMode: (values)=>{
      dispatch(dispatchAction(toggleScreenMode)())
    },
    handleClear: () => {
      dispatch({
        type: "CLEAR",
        data:"initial"
      });
    },
    focusCustomerCodeField: () => { 
      dispatch(focus('brokermappingform','customerCode'))
    }
  };
};

const FilterContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(FilterPanel)

export default FilterContainer;