import React, { Component, Fragment } from 'react';
import FilterContainer from '../components/containers/filtercontainer.js';
import DetailsContainer from '../components/containers/detailscontainer.js';
import { asyncDispatch ,dispatchAction} from 'icoreact/lib/ico/framework/component/common/actions';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { screenLoad,listMailbagsEnquiry} from '../actions/commonaction.js';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import { listMailbagsEnquiryOnNavigation } from '../actions/filteraction.js';
import { isEmpty } from 'icoreact/lib/ico/framework/component/util';
import ActionButtonContainer from  '../components/containers/actionbuttoncontainer.js';

class MailbagEnquiry extends Component {
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
                   <ActionButtonContainer/>
                </div>
            </Fragment>
        );
    }
}
const decApp = LoadingHOC('relisted', true)(MailbagEnquiry);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,

    };
}
const mapDispatchToProps = (dispatch) => {
    return {
        onMount: (props) => {

            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && (__fromScreen === 'mail.operations.ux.containerenquiry' || __fromScreen === 'OFFLOAD' || __fromScreen === 'MailInbound' || __fromScreen === 'MailOutbound') ){
                dispatch(asyncDispatch(screenLoad)())
                    .then((response) => {
                        if (isEmpty(response.errors)) {
                            dispatch(asyncDispatch(listMailbagsEnquiryOnNavigation)({ autoList: true }))
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

const MailbagEnquiryContainer = connectContainer(
    mapStateToProps,
    mapDispatchToProps
)(decApp)

export default MailbagEnquiryContainer;

