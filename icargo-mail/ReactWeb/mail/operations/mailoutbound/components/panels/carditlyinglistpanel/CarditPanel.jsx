import React, { Component, Fragment } from 'react';
import { CarditTabPanel } from './CarditTabPanel.jsx';
import CarditGroupViewTable from './CarditGroupViewTable.jsx'
import CarditListViewTable from './CarditListViewTable.jsx'
import CarditSummary from '../custompanels/CarditSummaryPanel.js';
import PropTypes from 'prop-types';
export default class CarditPanel extends Component {

    constructor(props) {
        super(props);
        //  this.toggleView1 = this.toggleView1.bind(this);

    }


    render() {
        return (
            <Fragment>
                <CarditTabPanel activeCarditTab={this.props.activeCarditTab} changeDetailsTab={this.props.changeCarditsTab} />
                {
                    this.props.activeCarditTab === 'GROUP_VIEW' &&
                   <div className="d-flex flex-column flex-grow-1">
                        <CarditSummary summary={this.props.carditSummary} />
                        <CarditGroupViewTable onApplyCarditFilter={this.props.onApplyCarditFilter} onClearCarditFilter= {this.props.onClearCarditFilter} oneTimeValues={this.props.oneTimeValues} initialValues={this.props.initialValues} carditGroupMailbags={this.props.carditGroupMailbags} carditSummary={this.props.carditSummary} getNewPage={this.props.onNextClickCarditList} carditFilter={this.props.carditFilter}/>
                    </div>
                }
                {
                    this.props.activeCarditTab === 'LIST_VIEW' &&
                  <div className="d-flex flex-column flex-grow-1">
                        <CarditSummary summary={this.props.carditSummary} />
                        <CarditListViewTable oneTimeValues={this.props.oneTimeValues} initialValues={this.props.initialValues} onClearCarditFilter= {this.props.onClearCarditFilter} onApplyCarditFilter={this.props.onApplyCarditFilter} carditMailbags={this.props.carditMailbags} carditSummary={this.props.carditSummary} selectCarditMailbags={this.props.selectCarditMailbags} getNewPage={this.props.onNextClickCarditList} openViewConsignmentPopup={this.props.openViewConsignmentPopup} carditFilter={this.props.carditFilter} />
                    </div>
                }
            </Fragment>
        )
    }
}
CarditPanel.propTypes = {
    activeCarditTab: PropTypes.string,
    changeCarditsTab: PropTypes.func,
    carditSummary: PropTypes.object,
    onApplyCarditFilter: PropTypes.func,
    onClearCarditFilter: PropTypes.func,
    oneTimeValues: PropTypes.object,
    initialValues: PropTypes.object,
    carditGroupMailbags: PropTypes.object,
    onNextClickCarditList: PropTypes.func,
    openViewConsignmentPopup: PropTypes.func,
    carditFilter:PropTypes.object,
    carditMailbags:PropTypes.object,
    selectCarditMailbags:PropTypes.object,
}