import React, { Component, Fragment } from 'react';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import FilterContainer from '../components/containers/filtercontainer';
import DetailsContainer from '../components/containers/detailscontainer';
import Actionbuttonscontainer from '../components/containers/actionbuttonscontainer';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { generatepassbillingfileScreenLoad } from '../actions/commonactions';
import { asyncDispatch } from 'icoreact/lib/ico/framework/component/common/actions';

class App extends Component {

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
                    {<Actionbuttonscontainer />}
                </div>
            </Fragment>
        );
    }

}

const decApp = LoadingHOC('screenLoadComplete', true)(App);

const mapStateToProps = (state) => {
    return {
        screenLoadComplete: state.commonReducer.screenLoadComplete
    };
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        onMount: (props) => {
            const { dynaProps: { __fromScreen } } = props
            if (__fromScreen && (__fromScreen === 'cra.defaults.invoicegenerationlog')) {
                dispatch(asyncDispatch(generatepassbillingfileScreenLoad)())
            }
            else {
                dispatch(asyncDispatch(generatepassbillingfileScreenLoad)(props));
            }
        }
    }
}
export default connectContainer(mapStateToProps, mapDispatchToProps)(decApp);