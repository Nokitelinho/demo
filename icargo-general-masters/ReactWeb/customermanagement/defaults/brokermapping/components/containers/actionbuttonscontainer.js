import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { validateSave,findPoaHistoryDetails} from '../../actions/commonaction';
import { dispatchAction,asyncDispatch } from "icoreact/lib/ico/framework/component/common/actions";

import ButtonsPanel from '../panels/ActionButtonsPanel.jsx';

const mapStateToProps = (state) => {
    return {
        showActionButtons:state.commonReducer.showActionButtons,
        disableCustomerFields:state.commonReducer.disableCustomerFields,
        showDeletePopUp:state.commonReducer.showDeletePopUp,
        showSinglePoa:state.commonReducer.showSinglePoa,
        showViewHistory:state.commonReducer.showViewHistory
    };
}

const mapDispatchToProps = (dispatch) => {
    return {
        onSave:()=>{
            dispatch(dispatchAction(validateSave)())
        },
        toogleDeletePopUp:()=>{
            dispatch({
                type:"TOOGLE_DELETE_POP_UP"
            })
        },
        onClickSinglePoa:()=>{
            dispatch({type:"SHOW_SINGLEPOA_POPUP"})
        },
        findPoaHistory:()=>{
            dispatch(asyncDispatch(findPoaHistoryDetails)())
        }
      };
}

const ActionButtonsContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(ButtonsPanel)

export default ActionButtonsContainer;