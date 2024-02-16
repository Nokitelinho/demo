import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import {listAwbDetails} from '../../actions/filteraction';
import {attachAwb} from '../../actions/detailsaction';
import { asyncDispatch,dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {Constants} from '../../constants/constants.js'


const mapStateToProps= (state) =>{
  return {
      screenMode:state.filterReducer.screenMode,
      displayMode:state.commonReducer.displayMode,
      awbDetails:{...state.filterReducer.awbDetails},
      oneTimeValues: state.commonReducer.oneTimeValues,
  }
}

const mapDispatchToProps = (dispatch,ownProps) => {
  return {
        listAwbDetails: (displayPage,pageSize) => {
            dispatch(asyncDispatch(listAwbDetails)({'displayPage':displayPage,'pageSize':pageSize,mode:Constants.LIST}))
        },
        attachAwb:(value)=>{
            dispatch(asyncDispatch(attachAwb)({index:value}));
        }, 
    }
  
}



  const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer