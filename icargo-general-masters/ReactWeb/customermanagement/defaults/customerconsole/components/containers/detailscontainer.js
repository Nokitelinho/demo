

import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';

const mapStateToProps = (state) => {
    return {
        invoiceDisplayMode: state.invoiceReducer.invoiceDisplayMode,
        customerDetails: state.filterReducer.customerDetails,
        ageingReceivables:state.filterReducer.ageingReceivables,
        outstandingReceivables:state.filterReducer.outstandingReceivables,
        receivablesCreditInfo:state.filterReducer.receivablesCreditInfo,
        statusView:state.filterReducer.statusView

    }
}



const DetailsContainer = connectContainer(
    mapStateToProps,
    null
)(DetailsPanel)

export default DetailsContainer