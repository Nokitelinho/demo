import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import {toggleCarditView} from '../../actions/filteraction';

const mapStateToProps= (state) =>{
  return {
      flightData:state.filterReducer.flightData, 
      carditView:state.commonReducer.carditView,
      flights:state.filterReducer.flightDetails
     
     
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    
    onExpandPanel:()=>{
        dispatch(toggleCarditView());
    }
     
    }
}


  const DetailsContainer = connectContainer(
  mapStateToProps,
  mapDispatchToProps
)(DetailsPanel)

export default DetailsContainer