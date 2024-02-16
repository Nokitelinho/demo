import React,{useState,useEffect} from 'react';
import ReactDOM from 'react-dom';
import MaterialTable from 'material-table'
import { forwardRef } from 'react';

import AddBox from '@material-ui/icons/AddBox';
import ArrowDownward from '@material-ui/icons/ArrowDownward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import { postToBEnd,deleteToBEnd } from '../components/Common'; 
import { getFromBEnd,NoListRow } from '../components/Common'; 
import commonStyles from '../components/styles';
import clsx from 'clsx';
import Paper from '@material-ui/core/Paper';

const tableIcons = {
    Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
    Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
    Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
    Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} />),
    DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
    Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
    Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
    Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
    FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
    LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
    NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
    PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
    ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
    Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
    SortArrow: forwardRef((props, ref) => <ArrowDownward {...props} ref={ref} />),
    ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
    ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
  };





export default function MaintainTenants(props) {
    const { useState } = React;
    const classes = commonStyles();
    const isValidId =  rowData => {
        let error=true
        const badTenantId=/[^A-Za-z0-9_-]/i
        if (badTenantId.test(rowData.tenantId)) {
            //error  = 'Invalid TenantId .Should only contain (A-Za-z0-9-+_)';
            error = false
        }
        return error
    }
    const  [data, setData] = useState([]);
    useEffect(() =>{
        getFromBEnd('/tenants')
        .then(setData)      
    },[]);    

    const columns = [
        {
        title: 'Tenant Id', field: 'tenantId',validate: isValidId,
        },
        { title: 'Description', field: 'description' },
    ];



  const onRowAdd = async (newData)=>{
    console.log('Added '+JSON.stringify(newData));
    postToBEnd("/tenants",newData).then(res=>setData([...data, newData]));
   }  

   const onRowUpdate = async (newData,oldData)=>{
    const dataUpdate = [...data];
    const index = oldData.tableData.id;
    dataUpdate[index] = newData;
    postToBEnd("/tenants",newData).then(res=>setData([...dataUpdate]));
   }

   const onRowDelete = async (oldData)=>{
        const dataDelete = [...data];
        const index = oldData.tableData.id;
        dataDelete.splice(index, 1);
        deleteToBEnd(`/tenants/${oldData.tenantId}`).then(res=> setData([...dataDelete]))       
   }

  return (
   <div className={clsx(classes.margin,classes.paper)}>   
        <MaterialTable
        title="Maintain Tenants"
        columns={columns}
        data={data}
        icons={tableIcons}
        editable={{
            onRowAdd: onRowAdd,
            onRowUpdate: onRowUpdate,
            onRowDelete: onRowDelete,
        }}
        />
    </div>
  )
}
