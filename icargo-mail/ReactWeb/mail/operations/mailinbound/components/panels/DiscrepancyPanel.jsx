

import React, { Fragment } from 'react';
import icpopup, { PopupFooter, PopupBody } from 'icoreact/lib/ico/framework/component/common/modal';
import { wrapForm } from 'icoreact/lib/ico/framework/component/common/form';
import { IButton } from 'icoreact/lib/ico/framework/html/elements';
import PropTypes from 'prop-types';



import { IColumn, ITable, Columns, Cell, HeadCell, RowCell, Content } from 'icoreact/lib/ico/framework/component/common/grid'



class DiscrepancyPanel extends React.Component {

    constructor(props) {
        super(props);

        this.state = {

        }
    }



    render() {
        return (
            <Fragment>

                <PopupBody>
                    <div className="d-flex" style={{ height: '300px' }}>
              
                     <ITable

                         rowCount={this.props.discrepancyData?this.props.discrepancyData.length:0}               
                            headerHeight={35}
                         className="table-list"
                         gridClassName="table_grid"
                         headerClassName="table-head"
                         tableId='discrepancyPanelTable'
                            rowClassName="table-row"
                         rowHeight={50}
                         data={this.props.discrepancyData?this.props.discrepancyData:null}>
 
                 <Columns >
 
                 <IColumn key={1} dataKey="" label="Mail Bag Id" width={150} flexGrow={1}>
                                 <Cell>
                                        <HeadCell disableSort>
                                         {(cellProps) => (
                                             <Content>{cellProps.label}</Content>)
                                         }
                                     </HeadCell>
                                     <RowCell >
                                         {(cellProps) => (
                                             <Content>{cellProps.rowData.mailIdentifier}</Content>)
                                         }
                                     </RowCell>
                                 </Cell>
                 </IColumn>
                 <IColumn key={2} dataKey="" label="Discrepancy" width={100} flexGrow={1}>
                                 <Cell>
                                        <HeadCell disableSort>
                                         {(cellProps) => (
                                             <Content>{cellProps.label}</Content>)
                                         }
                                     </HeadCell>
                                     <RowCell >
                                         {(cellProps) => (
                                             <Content>{cellProps.rowData.discrepancyType}</Content>)
                                         }
                                     </RowCell>
                                 </Cell>
                 </IColumn>
                 </Columns >
             </ITable>
                    </div>
                </PopupBody>
                <PopupFooter>
                    <IButton category="primary" bType="CLOSE" accesskey="O" onClick={this.props.onClose}>Close</IButton>
                </PopupFooter>
            </Fragment>

        )
    }
}
DiscrepancyPanel.propTypes = {
    discrepancyData: PropTypes.array,
    onClose: PropTypes.func
}
//export default icpopup(PopUpComponent, { title: 'Discrepancy' })
export default icpopup(wrapForm('discrepancyForm')(DiscrepancyPanel), { title: 'Discrepancy'})


