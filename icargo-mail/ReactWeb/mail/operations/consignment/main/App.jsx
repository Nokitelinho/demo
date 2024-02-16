import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import ActionButtonContainer from '../components/containers/actionbuttoncontainer.js';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { screenLoad } from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import { maintainconsignment } from '../actions/filteraction.js';

class MaintainConsignment extends Component {
    constructor(props) {
        super(props)
    }
    render() {
        return (
            <Fragment>
                <div className="header-panel animated fadeInDown">
                    <FilterContainer />
                </div>
                <div className="section-panel animated fadeInUp">
                    <DetailsContainer />
                </div>
                <div className="footer-panel">
                    <ActionButtonContainer />
                </div>
            </Fragment>
        );
    }
}
const decApp = LoadingHOC('relisted', true)(MaintainConsignment);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,

    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {
            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && ((__fromScreen === 'mail.operations.ux.carditenquiry')||(__fromScreen === 'mail.operations.ux.mailoutbound')||
              (__fromScreen === 'mail.operations.ux.consignmentsecuritydeclaration') )) {
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(maintainconsignment)({'displayPage':'1',action:'LIST'}))
                        } else {
                            return response
                        }
                    });
            }
            else {
                dispatch(asyncDispatch(screenLoad)(props));
            }
        }
    }
}

const MaintainConsignmentContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default MaintainConsignmentContainer;

