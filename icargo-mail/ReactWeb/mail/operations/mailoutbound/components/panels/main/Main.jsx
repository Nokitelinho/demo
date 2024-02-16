import React from 'react';
import App from './App.jsx';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init'
import config from '../../../../app.config.js';
import store from '../store/store.js';

class MainPage extends React.Component {


    render() {
        return (
            <div className="container-top" style={{height:'100%'}}>
                <App></App>
            </div>
        );
    }
}
export default initapp({store,screenId:'mail.operations.ux.outbound',screenAction:'mail/operations/outbound',appConfig:config})(MainPage)