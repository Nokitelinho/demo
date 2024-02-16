import React, { useState,useEffect } from 'react';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableContainer from '@material-ui/core/TableContainer';
import TablePagination from '@material-ui/core/TablePagination';
import IconButton from '@material-ui/core/IconButton';
import Checkbox from '@material-ui/core/Checkbox';
import EditIcon from '@material-ui/icons/Edit';
import { useHistory } from "react-router-dom";


import { getFromBEnd,NoListRow,asWebPath } from '../components/Common'; 
import  commonStyles,{ HeadingCell} from '../components/styles';



import Tooltip from '@material-ui/core/Tooltip';



export default function ArtefactList() {
  const c = commonStyles();
  const  [artefactList, setArtefactList] = useState({});
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);

  useEffect(() =>{
    getFromBEnd('/artifacts')
      .then(list=>setArtefactList({
        ...artefactList,
        artefacts:list,
      }))      
  },[]);

  const rows = artefactList.artefacts?artefactList.artefacts:[];
  const history = useHistory();

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };



  return (
    <div className={c.margin}>  
     <TableContainer component={Paper}>
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <HeadingCell>Artefact Id</HeadingCell>
            <HeadingCell align="left">Domain</HeadingCell>
            <HeadingCell align="left">Port</HeadingCell>
            <HeadingCell align="left">Type</HeadingCell>
            <HeadingCell align="left">Technology</HeadingCell>
            <HeadingCell align="left">Context</HeadingCell>
            <HeadingCell align="left">Description</HeadingCell>
            <HeadingCell align="left">Private</HeadingCell>
            <HeadingCell align="left">Public</HeadingCell>
            <HeadingCell align="left">Enterprise</HeadingCell>
            <HeadingCell/>
            <HeadingCell/>
          </TableRow>
        </TableHead>
       <TableBody>
          {rows?rows
            .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
            .map((row) => (
            <TableRow key={row.artifactId}>
              <TableCell component="th" scope="row">
                {row.artifactId}
              </TableCell>
              <TableCell align="left">{row.domain}</TableCell>
              <TableCell align="left">{row.port}</TableCell>
              <TableCell align="left">{row.serviceType}</TableCell>
              <TableCell align="left">{row.serviceTechnology}</TableCell>
              <TableCell align="left">{row.contextPath}</TableCell>
               <TableCell align="left">{row.description}</TableCell>
              <TableCell align="left"><Checkbox disabled checked={row.hostsPrivateApi}/></TableCell>
              <TableCell align="left"><Checkbox disabled checked={row.hostsPublicApi}/></TableCell>
              <TableCell align="left"><Checkbox disabled checked={row.hostsEnterpriseApi}/></TableCell>
              <TableCell align="left">
                <Tooltip title="Edit">
                  <IconButton aria-label="expand row" size="small"  onClick={()=>history.push(asWebPath(`/maintain-artefact/${row.artifactId}`))} >
                    <EditIcon/>
                  </IconButton>
                </Tooltip>
              </TableCell>              
             <TableCell/>
            </TableRow>
          )):<NoListRow message="No Artefacts?"/>}
        </TableBody>        
        </Table>
       </TableContainer>    
      <TablePagination
        rowsPerPageOptions={[10,25, 50]}
        component="div"
        count={rows.length}
        rowsPerPage={rowsPerPage}
        page={page}
        onChangePage={handleChangePage}
        onChangeRowsPerPage={handleChangeRowsPerPage}
      />             
       </div>
  );
}