import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { dispatchAction,asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import {onClose} from '../../actions/commonaction';
import { performListFlightsDetails } from '../../actions/filteraction';
import {openListAWBPopup} from '../../actions/commonaction.js';
import {listCarditEnquiry, performdetachAWB } from '../../actions/filteraction';
import { requestWarning } from 'icoreact/lib/ico/framework/component/common/store/commonactions';

const mapStateToProps= (state) =>{
    return {
        selectedMailbagIndex:state.filterReducer.selectedMailbagIndex,
		filterValues:  state.filterReducer.filterValues,
    }
  }


const mapDispatchToProps = (dispatch) => {
  return {


    onClose: () => {
        dispatch(dispatchAction(onClose)());
    },
     
   listFlightDetails: () => {
        dispatch(dispatchAction(performListFlightsDetails)())
    },   
	 
	loadListAWBPopup:()=>{
            dispatch(asyncDispatch(openListAWBPopup)())
			},
 detachAWB: () => {
      dispatch(asyncDispatch(performdetachAWB)()).then((response) => {
        if (response && response.status == "success") {
         dispatch(asyncDispatch(listCarditEnquiry)({displayPage:'1',pageSize:'100',mode:'LIST',detachAWB:'TRUE'}))



        }
      }
    
      )}
    
  }
}
const ActionButtonContainer = connectContainer(
    mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)


export default ActionButtonContainer