import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import CarditPanel from '../panels/carditlyinglistpanel/CarditPanel.jsx';
import {applyCarditFilter,validateCarditFilterForm} from '../../actions/carditaction';
import {openViewConsignmentPopup} from '../../actions/commonaction'
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';
import * as constant from '../../constants/constants';

const mapStateToProps = (state) => {
  return {
     carditMailbags:state.carditReducer.carditMailbags,
     carditGroupMailbags:state.carditReducer.carditGroupMailbags,
     carditSummary:state.carditReducer.carditSummary,
     activeCarditTab: state.carditReducer.activeCarditTab,
     initialValues:{airportCode:state.commonReducer.airportCode,...state.carditReducer.carditfilterValues,flightType:'C'},
     filterValues:state.carditReducer.carditfilterValues,
     oneTimeValues:state.commonReducer.oneTimeValues,
     carditFilter: state.form.carditFilter
    // initialValues:{...state.carditReducer.filterValues}
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
   changeCarditsTab: (currentTab) => {
            dispatch({ type: constant.CHANGE_CARDITS_TAB, data: currentTab })
            dispatch(asyncDispatch(applyCarditFilter)({tabChange:true}))
           
    },
   onApplyCarditFilter:(values)=>{
   
        dispatch({ type: constant.CARDIT_FILTER_APPLIED})
        let validObject = validateCarditFilterForm(values)
        if (!validObject.valid) {
          dispatch(requestValidationError(validObject.msg, ''));
        }
        else {
        dispatch(asyncDispatch(applyCarditFilter)())
        }
     
     
  },
  onNextClickCarditList:(values) =>{
    dispatch(asyncDispatch(applyCarditFilter)({'displayPage':values}))
  },
    onClearCarditFilter:()=>{
       dispatch({ type: constant.CLEAR_FILTER_APPLIED})
  },
    
      selectCarditMailbags: (indexes) => {
       dispatch({ type: constant.CARDIT_MAIL_SELECTED,indexes, count: indexes.length })
    },
    openViewConsignmentPopup:(data) => {
      dispatch(dispatchAction(openViewConsignmentPopup)({index :data.index}));
    }
    

  }
}
const CarditContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(CarditPanel)


export default CarditContainer