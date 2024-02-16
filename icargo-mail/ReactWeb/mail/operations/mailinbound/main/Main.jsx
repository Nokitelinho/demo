import React from 'react';
import store from '../store/store'
import 'icoreact/lib/ico/framework/assets/styles/scss/main.scss'
import App from './App.jsx';
import { initapp } from 'icoreact/lib/ico/framework/component/common/app/init'
import config from '../../../../app.config.js';

class MainPage extends React.Component {
    
    render() {
        return (
                <App></App>
        );
    }
}
export default initapp({store,screenId:'mail.operations.ux.mailinbound',screenAction:'mail/operations/mailinbound',appConfig:config, showStatusBar : true})(MainPage)