import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import LyingListPanel from '../panels/carditlyinglistpanel/LyingListPanel.jsx'
import {applyLyingListFilter} from '../../actions/lyinglistaction';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { reset } from 'redux-form';
import * as constant from '../../constants/constants';
import {getCurrentDate} from 'icoreact/lib/ico/framework/component/util/util';

const mapStateToProps = (state) => {
  return {
     oneTimeValues:state.commonReducer.oneTimeValues,
     lyinglistMailbags:state.lyingListReducer.lyinglistMailbags,
     lyingGroupMailbags:state.lyingListReducer.lyingGroupMailbags,
     lyingSummary:state.lyingListReducer.lyingSummary,
     activeLyingListTab: state.lyingListReducer.activeLyingListTab,
     initialValues:{scanPort:state.commonReducer.airportCode, fromDate: getCurrentDate(), toDate:getCurrentDate(),...state.lyingListReducer.filterValues},
     filterValues:state.lyingListReducer.filterValues,
     MailbagFilter: state.form.MailbagFilter
    // initialValues:{...state.carditReducer.filterValues}
  }
}
const mapDispatchToProps = (dispatch) => {
  return {
   changeLyingListTab: (currentTab) => {
            dispatch({ type: constant.CHANGE_LYING_TAB, data: currentTab })
            dispatch(asyncDispatch(applyLyingListFilter)({tabChange:true}))
           
    },
   onApplyFilter:(value)=>{
     if(!value) {
      dispatch({ type: constant.LYING_FILTER_APPLIED})
      dispatch(asyncDispatch(applyLyingListFilter)())
     }
    else {
      dispatch(asyncDispatch(applyLyingListFilter)({'displayPage':value}))
    }
  },
  onClearFilter:()=>{
    dispatch({ type: constant.CLEAR_LYINGLIST_FILTER})
    dispatch(reset('MailbagFilter'));
},
    
      selectLyingMailbags: (indexes) => {
       dispatch({ type: constant.LYING_MAIL_SELECTED,indexes, count: indexes.length })
    }
    

  }
}
const LyingListContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(LyingListPanel)


export default LyingListContainer