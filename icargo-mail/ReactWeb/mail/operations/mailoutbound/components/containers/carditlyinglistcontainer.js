import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import CarditLyinglistPanel from '../panels/carditlyinglistpanel/CarditLyinglistPanel.jsx';
import {onExpandClick} from '../../actions/commonaction';
import {applyDeviationListFilter} from '../../actions/deviationlistaction';
import {applyLyingListFilter} from '../../actions/lyinglistaction';
import {dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import * as constant from '../../constants/constants';


const mapStateToProps = (state) => {
  return {
     carditView:state.commonReducer.carditView,
     activeMainTab:state.commonReducer.activeMainTab,
     enableDeviationListTab: state.commonReducer.enableDeviationListTab
  }
}
const mapDispatchToProps = (dispatch) => {
 return {
        changeTab: (currentTab) => {
            dispatch({ type: constant.CHANGE_CARDITLYINGLIST_TAB, data: currentTab })
            if(currentTab ==='DEVIATION_LIST') {
              dispatch(asyncDispatch(applyDeviationListFilter)());
            }else if(currentTab ==='LYING_LIST'){
              dispatch({ type: constant.LYING_FILTER_APPLIED})
              dispatch(asyncDispatch(applyLyingListFilter)())
            }
        },
        onExpandClick:() =>{
          dispatch(dispatchAction(onExpandClick)());
        }
    }
}
const CarditLyinglistContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(CarditLyinglistPanel)


export default CarditLyinglistContainer