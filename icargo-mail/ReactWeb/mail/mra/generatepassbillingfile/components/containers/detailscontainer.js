import React, { PureComponent, Fragment } from 'react';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import DetailsPanel from '../panels/DetailsPanel.jsx';
const mapStateToProps = (state) => {
    return {
        screenMode: state.filterpanelreducer.screenMode,
    }
}
const mapDispatchToProps = (dispatch) => {
    return {

    }
}

export default connectContainer(mapStateToProps, mapDispatchToProps)(DetailsPanel);
