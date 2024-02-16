import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
import { dispatchAction, asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';

const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
        mailRuleConfigList: state.filterpanelreducer.mailRuleConfigList
    }
}
const mapDispatchToProps = (dispatch) => {
    return {


    }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(DetailsPanel);
