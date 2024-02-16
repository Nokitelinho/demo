import React, { Component, Fragment } from 'react';
import { connectContainer } from 'icoreact/lib/ico/framework/component/common/app/container';
import { LoadingHOC } from 'icoreact/lib/ico/framework/component/common/loading';
import FilterContainer from '../components/containers/filtercontainer.js'
import ActionButtonsContainer from '../components/containers/actionbuttonscontainer.js'
import DetailsContainer from '../components/containers/detailscontainer'
import PropTypes from 'prop-types';

class CustomerConsole extends Component {

    constructor(props) {
        super(props)
    }

    render() {
        return (
            <Fragment>
                <FilterContainer></FilterContainer>
                { !this.props.noRecordFound ? <DetailsContainer></DetailsContainer> : ""}
                <ActionButtonsContainer></ActionButtonsContainer>
            </Fragment>
        );

    }
}

const decApp = LoadingHOC('relisted', true)(CustomerConsole);
const mapStateToProps = (state) => {
    return {
        relisted: state.commonReducer.relisted,
        filterDisplayMode: state.filterReducer.filterDisplayMode,
        noRecordFound: state.filterReducer.noRecordFound

    };
}


const CustomerConsoleContainer = connectContainer(
    mapStateToProps,
    null
)(decApp)

CustomerConsole.propTypes = {
    noRecordFound: PropTypes.bool
};
export default CustomerConsoleContainer;