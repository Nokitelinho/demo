import React, { Component, Fragment } from 'react';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { screenLoad } from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import PopupContainer from '../components/containers/popupcontainer.js';
import { ActionType} from '../constants/constants';


class AdvancePayment extends Component {
    constructor(props) {
        super(props)
    }
    render() {
        return (

            <Fragment>
                <div className="header-panel animated fadeInDown"><FilterContainer /></div>
                <DetailsContainer />  
                <ActionButtonContainer />
                <PopupContainer /> 
            </Fragment>
        );
    }
}
const decApp = LoadingHOC('relisted', true)(AdvancePayment);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,

    };
}

const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {
            dispatch( { type: ActionType.CLEAR_INDEXES});
            dispatch(asyncDispatch(screenLoad)());
      }
	  
    }
}

const advancePaymentContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default advancePaymentContainer;
