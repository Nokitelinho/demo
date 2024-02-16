import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { PopupPanel } from '../panels/PopupPanel.jsx';
import { onCloseMailRule, saveMailRuleConfig,validateMailRuleConfigForm } from '../../actions/popupactions';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { requestValidationError} from 'icoreact/lib/ico/framework/component/common/store/commonactions';

const mapStateToProps = (state) => {
    return {
        showAddPopup: state.detailspanelreducer.showAddPopup,
        oneTimeMap: state.commonReducer.oneTimeMap,
        screenMode: state.filterpanelreducer.screenMode,
        mailRuleConfigList: state.filterpanelreducer.mailRuleConfigList,
        selectedMailRule:state.detailspanelreducer.selectedMailRule,
        initialValues:{...state.detailspanelreducer.selectedMailRule}
        }    
}

const mapDispatchToProps = (dispatch) => {
    return {
        onCloseMailRule: () => {
            dispatch(dispatchAction(onCloseMailRule)());

        },

        saveMailRuleConfig: () => {
             dispatch(asyncDispatch(saveMailRuleConfig)({validation:true}));
         },
    }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(PopupPanel)
