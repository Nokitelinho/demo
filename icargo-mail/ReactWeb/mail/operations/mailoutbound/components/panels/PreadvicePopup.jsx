import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { Col, Row } from 'reactstrap'
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';
import {Label} from "reactstrap";
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
class PreAdvice extends React.PureComponent {
    constructor(props) {
        super(props);
        this.onClickOK = this.onClickOK.bind(this);

    }
    onClickOK = () => {
        this.props.onClickOK();
    }
    render() {
        
        let results = this.props.selectedFlight&&this.props.selectedFlight.preadvice&&this.props.selectedFlight.preadvice.preadviceDetails ?this.props.selectedFlight.preadvice.preadviceDetails:[] ;        
        const rowCount = results ? results.length : 0;
        return (
            <Fragment>
                <PopupBody>
                    <div className="pad-md">
                        <Row>
                            <Col xs="4">
                                <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.flightnumber" />
                                    </Label>
                                <div className="form-control-data">{this.props.selectedFlight.flightNumber}</div>
                            </Col>
                            <Col xs="8">
                                <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.flightdate" />
                                    </Label>
                                <div className="form-control-data">{this.props.selectedFlight.flightDate}:{this.props.selectedFlight.flightTime}({this.props.selectedFlight.flightDateDesc})</div>
                            </Col>
                            <Col xs="4">
                                <Label className="form-control-label">
                                        <IMessage msgkey="mail.operations.mailoutbound.flightroute" />
                                    </Label>
                                <div className="form-control-data">{this.props.selectedFlight.flightRoute}</div>
                            </Col>
                            <Col xs="4">
                                <Label className="form-control-label">
                                        {/* <IMessage msgkey="mail.operations.mailoutbound.scandate" /> */}
                                    </Label>
                                <div className="form-control-data">{this.props.selectedFlight.aircraftType}</div>
                            </Col>
                            <Col xs="4">
                                <Label className="form-control-label">
                                        {/* <IMessage msgkey="mail.operations.mailoutbound.scandate" /> */}
                                    </Label>
                                <div className="form-control-data">{this.props.selectedFlight.flightOperationalStatus == 'O' || this.props.selectedFlight.flightOperationalStatus == 'N' ? 'Open' : 'Closed'}</div>
                            </Col>
                        </Row>
                    </div>
                    <div className="border-top d-flex" style={{ height: '200px' }}>
                        <ITable
                            rowCount={rowCount}
                            data={results}
                            headerHeight={35}
                            rowHeight={45}
                            rowClassName="table-row">
                            <Columns>
                                <IColumn
                                    dataKey="mailCategory"
                                    label="Mail Category"
                                    flexGrow={0}
                                    width={100}
                                    selectColumn={true}
                                    sortByItem={true}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.cellData}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>

                                <IColumn
                                    dataKey="originExchangeOffice"
                                    label="OOE"
                                    width={80}
                                    flexGrow={1}
                                    selectColumn={true}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.cellData}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>

                                <IColumn
                                    dataKey="destinationExchangeOffice"
                                    label="DOE"
                                    width={80}
                                    flexGrow={1}
                                    selectColumn={true}

                                >
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.cellData}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>

                                <IColumn
                                    dataKey="uldNumbr"
                                    label="ULD No."
                                    width={100}
                                    flexGrow={0}
                                    id="FlightDate"
                                    selectColumn={true}>
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.cellData}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey="totalbags"
                                    label="No of bags"
                                    width={100}
                                    flexGrow={0}
                                    id="accepted">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.cellData}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    dataKey=""
                                    label="Weight"
                                    width={100}
                                    flexGrow={0}
                                    id="accepted">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                               <Content>{cellProps.rowData.totalWeight ? cellProps.rowData.totalWeight.roundedDisplayValue : ''}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                            </Columns>
                        </ITable>
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton category="primary" onClick={this.onClickOK}>Ok</IButton>{' '}
                </PopupFooter>
            </Fragment>
        )
    }
}

PreAdvice.propTypes = {
    onSaveContainer: PropTypes.func,
    onClickOK:PropTypes.func,
    selectedFlight:PropTypes.object,
    
}

export default icpopup(wrapForm('preAdviceForm')(PreAdvice), { title: 'Preadvice', className: 'modal_60w' })
