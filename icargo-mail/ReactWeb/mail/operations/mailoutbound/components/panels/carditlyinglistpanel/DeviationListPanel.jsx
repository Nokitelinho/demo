import React, { Component, Fragment } from 'react';
import { DeviationListTabPanel } from './DeviationListTabPanel.jsx'
import DeviationListGroupViewTable from './DeviationListGroupViewTable.jsx'
import DeviationListViewTable from './DeviationListViewTable.jsx'
import CarditSummary from '../custompanels/CarditSummaryPanel.js'
import PropTypes from 'prop-types';

export default class DeviationListPanel extends Component {

    constructor(props) {
        super(props);
    }
    render() {
        return (
            <Fragment>
                <DeviationListTabPanel activeDeviationListTab={this.props.activeDeviationListTab} changeDetailsTab={this.props.changeDeviationListTab} />
                {
                    this.props.activeDeviationListTab === 'GROUP_VIEW' &&
                    <div className="d-flex flex-column flex-grow-1">
                        <CarditSummary summary={this.props.deviationSummary} />
                        <DeviationListGroupViewTable
                            oneTimeValues={this.props.oneTimeValues}
                            initialValues={this.props.initialValues}
                            onApplyFilter={this.props.onApplyFilter}
                            deviationGroupMailbags={this.props.deviationGroupMailbags}
                            carditSummary={this.props.carditSummary}
                            getNewPage={this.props.onApplyFilter}
                            onClearFilter={this.props.onClearFilter}
                            deviationListFilter={this.props.deviationListFilter} />
                    </div>
                }
                {
                    this.props.activeDeviationListTab === 'LIST_VIEW' &&
                    <div className="d-flex flex-column flex-grow-1">
                        <CarditSummary summary={this.props.deviationSummary} />
                        <DeviationListViewTable
                            oneTimeValues={this.props.oneTimeValues}
                            initialValues={this.props.initialValues}
                            onApplyFilter={this.props.onApplyFilter}
                            deviationlistMailbags={this.props.deviationlistMailbags}
                            carditSummary={this.props.carditSummary}
                            onClickDeviationLlist={this.props.onClickDeviationLlist}
                            getNewPage={this.props.onApplyFilter}
                            onClearFilter={this.props.onClearFilter}
                            deviationListFilter={this.props.deviationListFilter}  />
                    </div>
                }
            </Fragment>
        )
    }
}

DeviationListPanel.propTypes = {
    activeDeviationListTab: PropTypes.string,
    changeDeviationListTab: PropTypes.func,
    deviationSummary: PropTypes.array,
    oneTimeValues: PropTypes.object,
    onApplyFilter: PropTypes.func,
    deviationGroupMailbags: PropTypes.object,
    deviationlistMailbags: PropTypes.object,
    carditSummary: PropTypes.object,
    onClearFilter: PropTypes.func,
    initialValues: PropTypes.object,
    onClickDeviationLlist: PropTypes.func,
}