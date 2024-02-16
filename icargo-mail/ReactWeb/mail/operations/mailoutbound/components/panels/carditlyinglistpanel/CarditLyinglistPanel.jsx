import React, { Component } from 'react';
import {CarditLyinglistTabPanel} from './CarditLyinglistTabPanel.jsx';
import CarditContainer from '../../containers/carditcontainer.js'
import LyingListContainer from '../../containers/lyinglistcontainer.js';
import DeviationListContainer from '../../containers/deviationlistcontainer'
import PropTypes from 'prop-types';
export default class CarditLyinglistPanel extends Component {
    
    constructor(props) {
        super(props);
        
      }
    toggleView1=()=> {
        this.props.toggleCarditDisplay();
    }
    toggleView=()=> {
        this.props.toggleCarditDisplay();
    }

       render() {
        return (
              <div className="card">
                {(this.props.carditView === 'expanded') ?
                    <div className="card-body p-0 cardit-lying-box">

                        <CarditLyinglistTabPanel activeMainTab={this.props.activeMainTab} changeDetailsTab={this.props.changeTab} onExpandClick={this.props.onExpandClick} enableDeviationListTab={this.props.enableDeviationListTab}/>
                        {
                            this.props.activeMainTab === 'CARDIT_LIST' &&
                            <CarditContainer />
                        }
                        {
                            this.props.activeMainTab === 'DEVIATION_LIST' &&
                             <DeviationListContainer/>
                        }
                        {
                            this.props.activeMainTab === 'LYING_LIST' &&
                             <LyingListContainer/>
                        }

                        {/* <img style={{ cursor: 'pointer' }} id="expand_img" className="img m-l-5" src={expandImg} onClick={this.props.allMailbagIconAction} /> */}

                        {/* <a href="#" className="p-l-10 resize-trigger" onClick={this.toggleView}><i className={this.props.carditView === 'initial' ? "icon ico-arrow-expand-right" : "icon ico-expand-blue"}></i></a> */}

                    </div>
                    : null
                    // <a href="#" className="p-l-10 resize-trigger" onClick={this.toggleView}><i className={this.props.carditView === 'initial' ? "icon ico-arrow-expand-right" : "icon ico-expand-blue"}></i></a>
                }
            </div>
        )
    }
}
CarditLyinglistPanel.propTypes={
    toggleCarditDisplay: PropTypes.func,
    carditView:PropTypes.string,
    activeMainTab:PropTypes.string,
    changeTab: PropTypes.func,
    onExpandClick:PropTypes.func
}