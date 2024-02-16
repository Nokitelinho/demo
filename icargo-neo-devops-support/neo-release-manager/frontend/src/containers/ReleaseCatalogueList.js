import React, { useState,useEffect } from 'react';
import { useRecoilState } from 'recoil';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableContainer from '@material-ui/core/TableContainer';
import TablePagination from '@material-ui/core/TablePagination';

import { Typography} from '@material-ui/core';
import moment from 'moment';
import Tooltip from '@material-ui/core/Tooltip';
import TextField from '@material-ui/core/TextField';
import MenuItem from '@material-ui/core/MenuItem';
import IconButton from '@material-ui/core/IconButton';
import GetAppIcon from '@material-ui/icons/GetApp';
import { saveAs } from 'file-saver';
import tenantState from '../store/TenantAtom';

import { getFromBEnd,NoListRow,asApiURL } from '../components/Common'; 
import  commonStyles,{ HeadingCell} from '../components/styles';


const getArtReleaseBOM = async (tenant,artifactId,artifactVersion) => {
  return fetch(asApiURL(`/deployment/${tenant}/${artifactId}/${artifactVersion}/bom`))
  .then(response => response.blob())
  .then(blob => saveAs(blob, `${artifactId}-${artifactVersion}-${tenant}.yaml`));
}



export default function ReleaseCatalogueList() {
  const c = commonStyles();
  const  [artefactRelList, setArtefactRelList] = useState([]);
  const [tenant, setTenant] = useRecoilState(tenantState);
  const [artefactId, setArtefactId] = useState('');
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(10);
  const resourceUrl =artefactId?`/releases/latest?artifactId=${artefactId}`:'/releases/latest';

  useEffect(() =>{
    getFromBEnd(resourceUrl)
      .then(setArtefactRelList)      
  },[artefactId]);

  const  [artefactList, setArtefactList] = useState([]);
  useEffect(() =>{
    getFromBEnd('/artifacts')
      .then(setArtefactList)      
  },[]);

  const onArtefactChange = (event) => {
    const artefactId = event.target.value;
    setArtefactId(artefactId);
  }


  const rows = artefactRelList;

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };


  return (
    <div className={c.margin}>   
     <TableContainer component={Paper} className={c.paper}>
      <Tooltip placement="top-start" title="Latest one &  others within 15 days prior for each artifact">
        <Typography variant="h6">Latest Artefact Releases</Typography>
      </Tooltip>  
      <TextField  id="artefactId" select label="Filter By ArtefactId" value={artefactId}
      onChange={onArtefactChange}
      style={{width:'30ch', marginBottom:'2rem'}} >
      {artefactList?artefactList.map((row,idx) => (
          <MenuItem key={idx} value={row.artifactId}>
              {row.artifactId}
          </MenuItem>
        )):''}
      </TextField>  
      <Table aria-label="simple table">
        <TableHead>
          <TableRow>
            <HeadingCell align="left">Artefact Id</HeadingCell>
            <HeadingCell align="left">Version</HeadingCell>
            <HeadingCell align="left">Branch</HeadingCell>
            <HeadingCell align="left">Description</HeadingCell>
            <HeadingCell align="left">Built Time(UTC)</HeadingCell>
            <HeadingCell/>
            <HeadingCell/>
          </TableRow>
        </TableHead>
       <TableBody>
          {rows?rows
           .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
          .map((row) => (
            <TableRow key={row.artifactVersion}>
              <TableCell align="left" style={{width:'50ch'}}>{row.artifactId}</TableCell>
              <TableCell align="left" style={{width:'15ch'}}>{row.artifactVersion}</TableCell>
              
              <TableCell align="left" style={{width:'15ch'}}>{row.branch}</TableCell>
              <TableCell align="left" style={{width:'50ch'}}>{row.description}</TableCell> 
              <TableCell align="left" style={{width:'35ch'}}>{moment(row.releaseTime,moment.ISO_8601).format("LLLL")}</TableCell> 
             <TableCell/>
              <TableCell>
              <Tooltip title="Download BOM">
                <IconButton aria-label="expand row" size="small" onClick={()=>getArtReleaseBOM(tenant.value,row.artifactId,row.artifactVersion)} >
                  <GetAppIcon/>
                </IconButton>
              </Tooltip>
              </TableCell>             
            </TableRow>
          )):<NoListRow message="No Releases?"/>}
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