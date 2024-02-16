import React, { Fragment } from 'react';
import { Row, Col, Container, Input } from "reactstrap";
import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form'
import { ITextField, ISelect, IButton, ICheckbox, IRadio } from 'icoreact/lib/ico/framework/html/elements';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import PropTypes from 'prop-types'
import ImagePreviewPopup from 'icoreact/lib/ico/framework/component/business/imagepreview';
class ViewDamage extends React.PureComponent {
    constructor(props) {
        super(props);
        //this.doReturnMail=this.doReturnMail.bind(this);
        this.state = {
            showImagePreviewPopUp:false  ,
            id:[]          
		};   
    }
    doReturnMail = (values) => {
        this.props.doReturnMail(values);
    }
    togglePopUp = () => {
        
        this.props.imagepopupclose();
        this.setState( {showImagePreviewPopUp:false,id:[]} );
    }
    showPopUp = (e) => {
            let idArr=[];
            let fileNameArr=e.fileName.split("-DMG-");
            for(var i=0;i<fileNameArr.length;i++){
                idArr.push(fileNameArr[i]+'-DMG-'+e.damageCode+'-DMG-'+e.mailbagId);
            }
        this.setState( {showImagePreviewPopUp:true,id:idArr
        } );
        
    }  
    render() {
        const rowCount = (this.props.damageDetails) ? this.props.damageDetails.length : 0;
        //let PACodes = [{ "label": "Available", "value": "Y" },
        //        { "label": "Not Available", "value": "N" }]

        return (
            <Fragment>
                <PopupBody>
                    <div className="d-flex" style={{ height: "200px" }}>
                        <ITable
                            rowCount={rowCount}
                            headerHeight={35}
                            className="table-list"
                            gridClassName="table_grid"
                            headerClassName="table-head"
                            rowHeight={35}
                            rowClassName='table-row'
                            data={this.props.damageDetails}>
                            <Columns>
                                <IColumn
                                    width={60}
                                    flexGrow={0}
                                    dataKey="damageCode"
                                    label="Damage Code">
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
                                    width={130}
                                    flexGrow={1}
                                    dataKey="damageDescription"
                                    label="Description">
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
                                    width={50}
                                    flexGrow={0}
                                    dataKey="airportCode"
                                    label="Airport">
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
                                    width={60}
                                    flexGrow={0}
                                    dataKey="paCode"
                                    label="PA Code">
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
                                    width={80}
                                    flexGrow={0}
                                    dataKey=""
                                    label="Date">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content>{cellProps.rowData.damageDate.slice(0, 11)}</Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    width={65}
                                    flexGrow={0}
                                    dataKey=""
                                    label="Returned">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                                <Content><input type="checkbox" className="align-self-center" checked={cellProps.rowData.returnedFlag === 'Y' ? true : false} /></Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                                <IColumn
                                    width={100}
                                    flexGrow={1}
                                    dataKey="userCode"
                                    label="User">
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
                                    width={100}
                                    flexGrow={1}
                                    dataKey="image"
                                    label="Image">
                                    <Cell>
                                        <HeadCell disableSort>
                                            {(cellProps) => (
                                                <Content>{cellProps.label}</Content>)
                                            }
                                        </HeadCell>
                                        <RowCell>
                                            {(cellProps) => (
                                              
                                                <Content> 
                                                   {cellProps.rowData.fileName!=null &&
                                                 <div className='mar-l-xs badge badge-pill light badge-info' onClick={() =>this.showPopUp(cellProps.rowData)}>View Image</div>
                                                    } </Content>)
                                            }
                                        </RowCell>
                                    </Cell>
                                </IColumn>
                            </Columns>
                        </ITable>
                        <ImagePreviewPopup show={this.state.showImagePreviewPopUp} 
                                                title={'Damage Images'}
                                                className={'imagePreview modal_50w'}
                                                width='100%'
                                                toggleFn={this.togglePopUp}
                                                imageids={this.state.id}
                                                displayMode={'raw'}
                                                fetchUrl={'rest/mail/operations/mailbagenquiry/getDamageImages'}
                                
                                                />
                    </div>
                </PopupBody >
                <PopupFooter>
                    <IButton color="default" onClick={this.props.toggleFn}>OK</IButton>
                </PopupFooter>
                
            </Fragment>
        )
    }
}

ViewDamage.propTypes = {
    toggleFn: PropTypes.func,
    onSaveContainer: PropTypes.func
}
export default icpopup(wrapForm('viewDamageForm')(ViewDamage), { title: 'View Damage', className: 'modal_700px' })
