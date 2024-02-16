import React, { Component, Fragment } from 'react';
import FilterPanel from './FilterPanel.jsx';
import LoadPlanFilterPanel from './LoadPlanFilterPanel.jsx'

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
                                <li className={this.props.activeTab === "BookingView" ? "nav-item ui-tabs-active ui-state-active" : "nav-item"}>
                                    <a className="nav-link" onClick={() => this.changeTab("BookingView")}>
                                        Booking View
                                       </a>
                                </li>
                                <li className={this.props.activeTab === "LoadPlanView" ? "nav-item ui-tabs-active ui-state-active" : "nav-item"}>
                                    <a className="nav-link" onClick={() => this.changeTab("LoadPlanView")} >
                                        Load Plan View
                                        </a>
                                </li>
                            </ul>

                            {this.props.activeTab === "BookingView" && (<FilterPanel screenMode={this.props.screenMode} initialValues={this.props.initialValues} />)}
                            {this.props.activeTab === "LoadPlanView" && (<LoadPlanFilterPanel screenMode={this.props.screenMode} initialValues={this.props.initialValues} />)}

                        </div>
                    </div>
                </div>
            </Fragment>
        )
    }
}