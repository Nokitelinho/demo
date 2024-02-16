import React, { Component } from 'react';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid';
import { Row, Col } from "reactstrap";
import PropTypes from 'prop-types';
export class DeviationListTabPanel extends Component {
    constructor(props) {
        super(props)
    }
    changeTab = (currentTab) => {
        this.props.changeDetailsTab(currentTab)
    }
    render() {
        let active = 0;
        if (this.props.activeDeviationListTab === 'GROUP_VIEW') {
            active = 0;
        } else {
            active = 1;
        }
        return (
            <Row>
                <div className="col-24">
                    <div className="tab tab-sm">
                        <IButtonbar active={active}>
                            <IButtonbarItem>
                                <div className="btnbar" data-tab="GROUP_VIEW" onClick={() => this.changeTab('GROUP_VIEW')}>Group View</div>
                            </IButtonbarItem>
                            <IButtonbarItem>
                                <div className="btnbar" data-tab="LIST_VIEW" onClick={() => this.changeTab('LIST_VIEW')}>List View</div>
                            </IButtonbarItem>
                        </IButtonbar>
                    </div>
                </div>
                {this.props.activeDeviationListTab === 'LIST_VIEW' &&
                    <Col sm="24" md="24" lg="24">
                        <ICustomHeaderHolder tableId='deviationlisttable' />
                    </Col>
                }
                {this.props.activeDeviationListTab === 'GROUP_VIEW' &&
                    <Col sm="24" md="24" lg="24">
                        <ICustomHeaderHolder tableId='deviationlistgrouptable' />
                    </Col>
                }
            </Row>
        );
    }
}
DeviationListTabPanel.propTypes = {
    activeDeviationListTab: PropTypes.string,
    changeDetailsTab: PropTypes.func
}