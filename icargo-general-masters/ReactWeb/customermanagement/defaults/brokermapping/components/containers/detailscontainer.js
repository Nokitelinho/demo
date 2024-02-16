import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';

import DetailsPanel from '../panels/DetailsPanel.jsx';

const mapStateToProps = (state) => {
    return {
        screenMode:state.commonReducer.screenMode,
        disableCustomerFields:state.commonReducer.disableCustomerFields,
        selectedCustomertype:state.commonReducer. selectedCustomertype
    }
}
const DetailsContainer = connectContainer(
    mapStateToProps,
    null
)(DetailsPanel)

export default DetailsContainer