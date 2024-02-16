import React from 'react';
import App from './App.jsx';
import store from '../store/store.js';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init'
import config from '../../../../app.config.js';


class MainPage extends React.Component {

    render() {
        return (           
                <App></App>          
        );
    }
}
export default initapp({store,screenId:'mail.operations.ux.mailbagenquiry',screenAction:'mail/operations/mailbagenquiry',appConfig:config, showStatusBar : true })(MainPage)