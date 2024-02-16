import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import {onClose} from '../../actions/commonaction';
import {dispatchAction,asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import {attachAwb,attachLoadPlanAwb} from '../../actions/detailsaction';
import FooterPanel from '../panels/FooterPanel.jsx';

const mapStateToProps=(state)=>{
    return {
        activeTab:state.commonReducer.activeTab
    }

}

const mapDispatchToProps=(dispatch, ownProps)=>{
    return {
        onClose: () => {
            dispatch(dispatchAction(onClose)());
        },
        attachAwb:()=>{
            dispatch(asyncDispatch(attachAwb)());
            // }else if(activeTab==='LoadPlanView'){
            //     dispatch(asyncDispatch(attachLoadPlanAwb)());   
            // }
        }
        
    }

}

const FooterContainer= connectContainer(
    mapStateToProps,
    mapDispatchToProps
  )(FooterPanel)

export default FooterContainer