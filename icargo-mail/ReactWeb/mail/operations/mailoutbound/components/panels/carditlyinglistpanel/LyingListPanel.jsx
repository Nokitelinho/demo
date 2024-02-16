import React, { Component, Fragment } from 'react';
import { LyingListTabPanel } from './LyingListTabPanel.jsx'
import LyingListGroupViewTable from './LyingListGroupViewTable.jsx'
import LyingListViewTable from './LyingListViewTable.jsx'
import CarditSummary from '../custompanels/CarditSummaryPanel.js'
import PropTypes from 'prop-types';

export default class LyingListPanel extends Component {
    
    constructor(props) {
        super(props);
      //  this.toggleView1 = this.toggleView1.bind(this);
        
      }
   

       render() {
        return (
          
                 <Fragment>
                    <LyingListTabPanel activeLyingListTab={this.props.activeLyingListTab} changeDetailsTab={this.props.changeLyingListTab} />
                          {
                               this.props.activeLyingListTab === 'GROUP_VIEW' &&
                                <div className="d-flex flex-column flex-grow-1">
                                <CarditSummary summary={this.props.lyingSummary} />
                                <LyingListGroupViewTable oneTimeValues={this.props.oneTimeValues}  initialValues={this.props.initialValues} onApplyFilter={this.props.onApplyFilter} lyingGroupMailbags={this.props.lyingGroupMailbags} carditSummary={this.props.carditSummary} getNewPage={this.props.onApplyFilter} onClearFilter={this.props.onClearFilter}/>
                               </div>
                            }
                            {
                                this.props.activeLyingListTab === 'LIST_VIEW' &&
                                 <div className="d-flex flex-column flex-grow-1">
                                 <CarditSummary summary={this.props.lyingSummary} />
                                 <LyingListViewTable oneTimeValues={this.props.oneTimeValues}  initialValues={this.props.initialValues} onApplyFilter={this.props.onApplyFilter} lyinglistMailbags={this.props.lyinglistMailbags} carditSummary={this.props.carditSummary} selectLyingMailbags={this.props.selectLyingMailbags} getNewPage={this.props.onApplyFilter} onClearFilter={this.props.onClearFilter}/>
                                </div>
                            }
                      </Fragment>
                  
                   
           
        )
    }
}
LyingListPanel.propTypes = {
    activeLyingListTab: PropTypes.string,
    changeLyingListTab:PropTypes.func,
    lyingSummary:PropTypes.array,
    oneTimeValues:PropTypes.object,
    onApplyFilter:PropTypes.func,
    lyingGroupMailbags:PropTypes.object,
    lyinglistMailbags:PropTypes.object,
    carditSummary:PropTypes.object,
    onClearFilter:PropTypes.func,
    initialValues:PropTypes.object,
    selectLyingMailbags:PropTypes.func,
}