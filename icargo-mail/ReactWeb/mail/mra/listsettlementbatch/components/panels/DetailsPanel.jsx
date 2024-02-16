import React from 'react';
import BatchListContainer from '../containers/batchlistcontainer'
import BatchDetailContainer from '../containers/batchdetailcontainer'
import PropTypes from 'prop-types';
import { IMessage } from 'icoreact/lib/ico/framework/html/elements';
import { Key} from '../../constants/constants.js';

class DetailsPanel extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        return (
            <section className="section-panel mar-b-md">
                {(this.props.noRecords === true) ? "" :
                    <div className="row inside-row-pannel">
                        <div className="col-5 d-flex">
                            <BatchListContainer batchLists={this.props.batchLists} batchListMode={this.props.batchListMode} />
                        </div>
                        <div className="col-19 d-flex">
                            {(this.props.batchDetailStatus === "show" ?
                                <div className="card w-100">
                                    <BatchDetailContainer batchDetailsList={this.props.batchDetailsList} /> </div> :
                                <div className="card w-100">
                                    <div className="card-header d-flex justify-content-end inside-header-panel">
                                        <h4 className="flex"><IMessage msgkey={Key.BATCH_DTL_LBL} /></h4>
                                    </div>
                                    <div className="card-body">
                                        <IMessage msgkey={Key.BATCH_DTL_NOREC} />
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                }
            </section>
        )
    }
}
DetailsPanel.propTypes = {
    noRecords: PropTypes.bool,
    batchLists: PropTypes.array,
    batchListMode: PropTypes.string,
    batchDetailStatus: PropTypes.string,
    batchDetailsList: PropTypes.object,
};

export default DetailsPanel;