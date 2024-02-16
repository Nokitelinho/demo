import React, { Component } from 'react';
import { Row, Label } from "reactstrap";
import { IToolTip } from 'icoreact/lib/ico/framework/component/common/tooltip'
import PropTypes from 'prop-types';
import { IMessage, IButton } from 'icoreact/lib/ico/framework/html/elements';
import { Key,Constants,ComponentId } from '../../constants/constants.js';

export default class BatchListPanel extends Component {
    constructor(props) {
        super(props);
    }

    selectBatch=(event)=> {
        const selectedBatchId= event.currentTarget.getAttribute('data-batchkey');
        this.props.onSelect(selectedBatchId);
    }
    findArrayElement(array, value) {
        return array.find((element) => {
            return element.value === value;
        })
    }
    render() {
        let status = this.props.oneTimeValues['mail.mra.receivablemanagement.batchstatus'].map((value) => ({ value: value.fieldValue, label: value.fieldDescription }));
        let source = "Mail";

        const batches = this.props.batchLists.map((batchList, index) => {
            const key = batchList.batchId + '~' +batchList.gpaCode+'~'+ batchList.batchSequenceNum;

            return (
                <li className={(this.props.batchListMode === 'onList' && index === 0) || this.props.selectedBatchId === key ? 'd-flex flex-column selected' : 'flex-column d-flex'} onClick={this.selectBatch} data-batchkey={key} key={key}>
                    <Row>
                        <span className="col-22 info-msg strong">
                            {batchList.batchId}
                        </span>                      
                    </Row>
                     <Row>                      
                          <div className="col-24 text-right">
                            <span className={ "badge badge-pill light badge-info inline-block pad-y-2xs"}>{(this.findArrayElement(status, batchList.batchStatus)).label}</span>
                        </div>
                   </Row>
                    <Row>
                        <span className="col-auto text-grey"><IMessage msgkey={Key.BTH_CNT_LBL} /></span>
                        <span className="col text-left text-black pl-0">{batchList.recordCount}</span>
                    </Row>
                    <Row className="mar-t-2xs">
                        <span className="col-auto text-grey"><IMessage msgkey={Key.BTH_DAT_LBL} /></span>
                        <span className="col text-left text-black pl-0">{batchList.batchDate}</span>
                   </Row>
                    <Row className="mar-t-2xs">
                        <span className="col-auto text-grey"><IMessage msgkey={Key.BTH_AMT_LBL} /></span>
                        <span className="col text-left text-black pl-0">{batchList.batchamount}{` `}{batchList.currencyCode}</span>
                   </Row>
                     <Row className="mar-t-2xs">
                        <span className="col-auto text-grey"><IMessage msgkey={Key.BTH_APLAMT_LBL} /></span>
                        <span className="col text-left text-black pl-0">{batchList.appliedAmount}{` `}{batchList.currencyCode}</span>
                    </Row>
                    <Row className="mar-t-2xs">
                        <span className="col-auto text-grey"><IMessage msgkey={Key.BTH_UNAPL_LBL} /></span>
                        <span className="col text-left text-black pl-0">{batchList.unAppliedAmount}{` `}{batchList.currencyCode}</span>
                    </Row>
                    <Row className="mar-t-2xs">
                        <span className="col-auto text-grey"><IMessage msgkey={Key.BTH_SRC_LBL} /></span>
                        <span className="col text-left text-black pl-0">{ batchList.source}</span>
                    </Row>

                </li>
            );
        });

        return (
            <div className="card flex-column w-100">
                <div className="card-header">
                    <h4 className="d-flex"><IMessage msgkey={Key.BTH_LIST} /></h4>
                </div>
                <div className="card-body p-0 m-0 overflow-y position-relative">
                    <ul className="p-0 m-0 rule-master-flight-list position-absolute all-0">
                        {batches}
                    </ul>
                </div>
            </div>
        );
    }
}

BatchListPanel.propTypes = {
    onSelect: PropTypes.func,
    oneTimeValues: PropTypes.object,
    batchLists: PropTypes.array,
    batchListMode: PropTypes.string,
    selectedBatchId:PropTypes.string
};
