import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import ActionButtonPanel from '../panels/ActionButtonPanel.jsx';
import { dispatchAction,asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import {onClose,markAsHbaAction} from '../../actions/commonaction';

const mapStateToProps= (state) =>{
    return {
        position:state.commonReducer.position,
        hbaType:state.commonReducer.hbaType,
        uldReferenceNumber:state.commonReducer.uldReferenceNumber,
        flightNumber:state.commonReducer.flightNumber,
        flightSequenceNumber:state.commonReducer.flightSequenceNumber,
        legSerialNumber:state.commonReducer.legSerialNumber,
        carrierId:state.commonReducer.carrierId,
        carrierCode:state.commonReducer.carrierCode,
        assignedPort:state.commonReducer.assignedPort,
        containerNumber:state.commonReducer.containerNumber,
        type:state.commonReducer.type
    }
  }


const mapDispatchToProps = (dispatch) => {
  return {


    onClose: () => {
        dispatch(dispatchAction(onClose)());
    }
    ,
    markAsHba:()=>{
      dispatch(asyncDispatch(markAsHbaAction)()).then(() =>{
        dispatch(asyncDispatch(onClose)())
       } );
      },
  }
}
const ActionButtonContainer = connectContainer(
    mapStateToProps,
  mapDispatchToProps
)(ActionButtonPanel)


export default ActionButtonContainer