import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import AddContainer from '../panels/AddContainer.jsx';
import {toggleCarditView} from '../../actions/filteraction';
import { dispatchAction } from 'icoreact/lib/ico/framework/component/common/actions';
import {listDetails} from '../../actions/filteraction';
const mapStateToProps= (state) =>{
  return {
      flightData:state.filterReducer.flightData,
      displayMode:state.commonReducer.displayMode,   
      carditView:state.commonReducer.carditView
     
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
      onExpandPanel:()=>{
          dispatch(toggleCarditView());
      },
      onlistDetails: (displayPage) => {
      dispatch(dispatchAction(listDetails)({displayPage}));
    }
    }
}


  const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(AddContainer)

export default DetailsContainer