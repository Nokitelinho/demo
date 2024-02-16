import React, { Component, Fragment } from 'react';
import { IButtonbar, IButtonbarItem } from 'icoreact/lib/ico/framework/component/common/buttonbar';
import { ICustomHeaderHolder } from 'icoreact/lib/ico/framework/component/common/grid'
import { Row, Col, Container } from "reactstrap";

export default class AddMailbagTabPanel extends Component {
    constructor(props) {
        super(props);
        // this.toggleView = this.toggleView.bind(this);
    }
    changeTab = (currentTab) => {
        this.props.changeAddMailbagTab(currentTab)
    }

    render() {
        let active = 0;
        if (this.props.activeMailbagAddTab === 'EXCEL_VIEW' || this.props.activeMailbagAddTab === 'initial') {
            active = 0;
        } else {
            active = 1;
        }
        return (
            <div className="card-header">
                <Row>
                    <Col><h4>Mailbag Details</h4></Col>
                    <Col md={12} sm={14} lg={12} >
                    {this.props.screenMode === 'initial' ? "" :
                       <ICustomHeaderHolder tableId='mailBagsTable' />
                    }
                    </Col>
                    <Col md={3} sm={3} lg={2} >
                        <div className="tab tab-icon">
                            <IButtonbar active={active}>
                                <IButtonbarItem>
                                    <div className="btnbar" data-tab="EXCEL_VIEW" onClick={() => this.changeTab('EXCEL_VIEW')}><i className="icon ico-htable align-middle"></i></div>
                                </IButtonbarItem>
                                <IButtonbarItem>
                                    <div className="btnbar" data-tab="NORMAL_VIEW" onClick={() => this.changeTab('NORMAL_VIEW')}><i className="icon ico-itable align-middle"></i></div>
                                </IButtonbarItem>
                            </IButtonbar>
                        </div>
                    </Col>
                </Row>
            </div>
        )
    }
}