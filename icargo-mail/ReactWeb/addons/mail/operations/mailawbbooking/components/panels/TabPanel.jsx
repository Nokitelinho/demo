import React, { Component, Fragment } from 'react';
import FilterPanel from './FilterPanel.jsx';
import LoadPlanFilterPanel from './LoadPlanFilterPanel.jsx';
import ManifestFilterPanel from './ManifestFilterPanel.jsx';

export default class TabPanel extends Component {
    constructor(props) {
        super(props);
 
    }

    changeTab = (currentTab) => {
        this.props.changeTab(currentTab)
    }

    render() {
        return (
            <Fragment>
              <div className="card p-0 inner-panel">
                <div className="card-body p-0 inner-panel">
                  <div className="tabs inner-panel">
                    <ul className="nav nav-tabs nav-tabs-large">
                      <li className={ this.props.activeTab==="BookingView"?"nav-item ui-tabs-active ui-state-active":"nav-item"}>
                        <a className="nav-link" onClick={() => this.changeTab("BookingView") }> 
                        Booking View 
                        </a>
                      </li>
                      <li className={ this.props.activeTab==="LoadPlanView"?"nav-item ui-tabs-active ui-state-active":"nav-item"}>
                        <a className="nav-link" onClick={() => this.changeTab("LoadPlanView")} >
                        Load Plan View
                        </a>
                      </li>
                <li className={this.props.activeTab === "ManifestView" ? "nav-item ui-tabs-active ui-state-active" : "nav-item"}>
                  <a className="nav-link" onClick={() => this.changeTab("ManifestView")} >
                    Manifest View
                        </a>
                </li>
                     </ul> 

                     {this.props.activeTab === "BookingView" && (<FilterPanel screenMode ={this.props.screenMode} filterValues={this.props.filterValues}
                     airportCode={this.props.airportCode} oneTimeValues={this.props.oneTimeValues} displayPage ={this.props.displayPage} activeTab={this.props.activeTab}
                     pageSize={this.props.pageSize} initialValues={this.props.initialValues} filter={this.props.filter} popOver={this.props.popOver}
                     popoverCount ={this.props.popoverCount} onlistAwbDetails={this.props.onlistAwbDetails} onclearAwbDetails={this.props.onclearAwbDetails}
                     onToggleFilter={this.props.onToggleFilter} togglePopover={this.props.togglePopover}/> )}

                     {this.props.activeTab === "LoadPlanView" && (<LoadPlanFilterPanel  screenMode ={this.props.screenMode} onToggleFilter={this.props.onToggleFilter}
                     onlistLoadPlanDetails = {this.props.onlistLoadPlanDetails} loadPlanFilter={this.props.loadPlanFilter} loadPlanPopOver={this.props.loadPlanPopOver}
                     loadPlanPopoverCount={this.props.loadPlanPopoverCount} initialValues={this.props.initialValues} onclearLoadPlanDetails={this.props.onclearLoadPlanDetails}
                flightnumber={this.props.flightnumber} />)}
              {this.props.activeTab === "ManifestView" && (<ManifestFilterPanel screenMode={this.props.screenMode} onToggleFilter={this.props.onToggleFilter}
                onlistManifestDetails={this.props.onlistManifestDetails} manifestFilter={this.props.manifestFilter} manifestPopOver={this.props.manifestPopOver}
                manifestPopoverCount={this.props.manifestPopoverCount} initialValues={this.props.initialValues} onclearManifestDetails={this.props.onclearManifestDetails}
                     flightnumber={this.props.flightnumber} />) }
                    </div>
                </div>
              </div>
            </Fragment>
            )
        }
    }