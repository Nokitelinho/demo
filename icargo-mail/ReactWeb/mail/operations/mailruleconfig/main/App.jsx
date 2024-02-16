import React, { Component, Fragment } from 'react';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import FilterContainer from '../components/containers/filtercontainer';
import DetailsContainer from '../components/containers/detailscontainer';
import Actionbuttonscontainer from '../components/containers/actionbuttonscontainer';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { mailruleconfigScreenLoad } from '../actions/commonactions';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';
import PopupContainer from '../components/containers/popupcontainer';

class MailRuleConfig extends Component {
    constructor(props) {
        super(props)
    }
    render() {

        return (
            <Fragment>
                
                <div className="header-panel animated fadeInDown">
                    <FilterContainer />
                </div>
                <div className="section-panel animated fadeInUp"  >
                    <DetailsContainer />
                </div>
                <div className="footer-panel">
                    <Actionbuttonscontainer />
                </div>
               <PopupContainer/>
                
            </Fragment>
        );
    }

}

const decApp = LoadingHOC('screenLoadComplete', true)(MailRuleConfig);

const mapStateToProps = (state) => {
    return {
        screenLoadComplete: state.commonReducer.screenLoadComplete,
    };
}

const mapDispatchToProps = (dispatch) => {
    return {
        onMount: () => {
            dispatch(asyncDispatch(mailruleconfigScreenLoad)());
        }
    }
}
export default connectContainer(mapStateToProps, mapDispatchToProps)(decApp);