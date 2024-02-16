/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibsplc.icargo.txprobe.tools.es.gui;

import com.ibsplc.icargo.txprobe.tools.es.ElasticSearchExportClient;
import com.ibsplc.icargo.txprobe.tools.es.Exporter;
import com.ibsplc.icargo.txprobe.tools.es.OptionsBean;
import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbeData;
import com.ibsplc.icargo.txprobe.tools.utils.ElasticSearchUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.prefs.Preferences;

/**
 *
 * @author A-2394
 */
public class TxProbeExportForm extends javax.swing.JFrame {

	private static final long serialVersionUID = -8091585862434441280L;

	private ElasticSearchExportClient client = new ElasticSearchExportClient();
	private Map<String, RestHighLevelClient> connMap = new HashMap<>(2);
	private SimpleDateFormat optionsSdf = new SimpleDateFormat("yyyyMMddHHmm");
	private ExecutorService service = Executors.newSingleThreadExecutor();
    /**
     * Creates new form TxProbeExportForm
     */
    public TxProbeExportForm() {
        initComponents();
        refreshComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_correlationId = new javax.swing.JLabel();
        correlationId = new javax.swing.JTextField();
        lbl_invocationId = new javax.swing.JLabel();
        invocationId = new javax.swing.JTextField();
        lbl_nodeId = new javax.swing.JLabel();
        nodeId = new javax.swing.JTextField();
        lbl_userId = new javax.swing.JLabel();
        userId = new javax.swing.JTextField();
        lbl_module = new javax.swing.JLabel();
        module = new javax.swing.JTextField();
        lbl_subModule = new javax.swing.JLabel();
        submodule = new javax.swing.JTextField();
        lbl_startTime = new javax.swing.JLabel();
        startTime = new javax.swing.JTextField();
        lbl_endTime = new javax.swing.JLabel();
        endTime = new javax.swing.JTextField();
        lbl_errorsOnly = new javax.swing.JLabel();
        errorsOnly = new javax.swing.JCheckBox();
        lbl_esUrl = new javax.swing.JLabel();
        esUrl = new javax.swing.JTextField();
        btnClear = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        lbl_invocationId1 = new javax.swing.JLabel();
        format = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnExport = new javax.swing.JButton();
        lbl_status = new javax.swing.JLabel();
        lbl_status.setFont(new Font("Ariel", Font.BOLD, 12));
        lbl_probeType = new javax.swing.JLabel();
        probeType = new javax.swing.JComboBox<>();
        errorType = new javax.swing.JTextField();
        errorType.setToolTipText("BSE, SYE, RTE etc");
        lbl_errorType = new javax.swing.JLabel();
        lbl_depth = new javax.swing.JLabel();
        depth = new javax.swing.JTextField();
        lbl_search = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        search.setToolTipText("Search String ( Strings can be separated by + (AND) or | (OR) to apply conditions ) "
			+ "default operator is AND for separator SPACE");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TxProbe Export");
        setResizable(false);

        lbl_correlationId.setText("Correlation Id");
        lbl_invocationId.setText("Invocation Id");
        lbl_nodeId.setText("Node Id");
        lbl_userId.setText("User Id");
        lbl_module.setText("Module");
        lbl_subModule.setText("Sub Module");
        lbl_startTime.setText("Start Time");
        lbl_endTime.setText("End Time");
        lbl_errorsOnly.setText("Error Only");
        lbl_esUrl.setText("ES Url");
        btnClear.setText("Clear");

        btnClear.addActionListener(e -> btnClearMouseClicked());
        btnSearch.setText("Search");
        btnSearch.addActionListener(e -> btnSearchMouseClicked());

        lbl_invocationId1.setText("Format");

        format.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "html", "txt", "xls" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {}, new String [] { "Start Time", "CorrelationId", "Probe Type", "Operation"}) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(jTable1.getSelectedRow() > -1){
					tableRowSelected(e);
				}
			}
		});

        jScrollPane1.setViewportView(jTable1);

        btnExport.setText("Export");
        btnExport.setEnabled(false);

        lbl_probeType.setText("Probe Type");
        probeType.setModel(new javax.swing.DefaultComboBoxModel<Probe>(Probe.values()));
        btnExport.addActionListener(e -> btnExportMouseClicked());
        lbl_errorType.setText("Error Type");
        lbl_depth.setText("Depth");
        lbl_search.setText("Search");
        startTime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
            	validateDate(startTime);
            }
        });

        endTime.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
            	validateDate(endTime);
            }
        });

        esUrl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
            	disableAll();
            	CompletableFuture<RestHighLevelClient> cf = CompletableFuture.supplyAsync(() -> {
					RestHighLevelClient c = connect(esUrl);
            		enableAll();
            		if(c != null)
            			putInPrefs("esUrl", esUrl.getText());
	            	return c;
	            }, service);

            }
        });

        super.addWindowListener(new WindowAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				TxProbeExportForm.this.connMap.forEach((k, v) -> ElasticSearchUtils.close(v));
			}

		});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                        .addComponent(lbl_esUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(esUrl, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(41, 41, 41)
                                        .addComponent(lbl_search, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lbl_status, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                                .addComponent(btnClear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExport)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch)))
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_errorsOnly, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_correlationId, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                            .addComponent(lbl_module, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(correlationId, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(module))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_invocationId)
                                    .addComponent(lbl_subModule, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(errorsOnly)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_invocationId1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(format, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_probeType, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(probeType, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(lbl_errorType, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(errorType))
                            .addComponent(submodule, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                            .addComponent(invocationId, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_startTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_nodeId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nodeId, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(startTime))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_userId, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_endTime))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(userId, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(endTime)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(lbl_depth, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(depth, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_correlationId)
                    .addComponent(correlationId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_invocationId)
                    .addComponent(invocationId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_nodeId)
                    .addComponent(nodeId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_userId)
                    .addComponent(userId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_module)
                    .addComponent(module, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_subModule)
                    .addComponent(submodule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_startTime)
                    .addComponent(startTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_endTime)
                    .addComponent(endTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(errorsOnly, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_errorType)
                        .addComponent(errorType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_depth)
                            .addComponent(depth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_invocationId1)
                        .addComponent(format, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_probeType)
                        .addComponent(probeType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_errorsOnly, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(esUrl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_esUrl)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_search)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClear)
                    .addComponent(btnSearch)
                    .addComponent(btnExport)
                    .addComponent(lbl_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClearMouseClicked() {//GEN-FIRST:event_btnClearMouseClicked
    	refreshComponents();
    }//GEN-LAST:event_btnClearMouseClicked

    private void btnSearchMouseClicked() {//GEN-FIRST:event_btnSearchMouseClicked
    	OptionsBean options = new OptionsBean();
    	options.setCorrelationId(emptyOrNull(correlationId));
    	options.setEndDate(emptyOrNull(endTime));
    	options.setStartDate(emptyOrNull(startTime));
    	options.setErrorsOnly(errorsOnly.isSelected());
    	options.setFormat(format.getSelectedItem().toString());
    	options.setInvocationId(emptyOrNull(invocationId));
    	options.setModule(emptyOrNull(module));
    	options.setNodeName(emptyOrNull(nodeId));
    	options.setSubmodule(emptyOrNull(submodule));
    	options.setUser(emptyOrNull(userId));
    	options.setErrorType(emptyOrNull(errorType));
    	options.setDepth(emptyOrZero(depth));
    	options.setSearch(emptyOrNull(search));
    	if(probeType.getSelectedIndex() >= 0)
    		options.setProbeType(probeType.getSelectedItem().toString());
		RestHighLevelClient tc = connect(esUrl);
    	if(tc == null)
    		return;
    	if(options.getStartDate() == null || options.getEndDate() == null){
    		error("Start time and End time is mandatory.");
    		return;
    	}
    	CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> performListing(options, tc), service);
    	cf.handle((t, v) -> { enableAll() ; return v; });
    	cf.exceptionally(t -> {
    		t.printStackTrace();
    		error(t + " - " + t.getMessage());
    		return null;
    	});
    }//GEN-LAST:event_btnSearchMouseClicked


    private Void performListing(OptionsBean filter, RestHighLevelClient conn){
    	disableAll();
    	btnExport.setEnabled(false);
    	Map<String, Long> uuids = null;
    	if(filter.getCorrelationId() != null){
    		uuids = new HashMap<>(2);
    		uuids.put(filter.getCorrelationId(), Long.valueOf(0));
    	}else
    		uuids = executeAndMap(filter, conn);
    	if(uuids == null)
    		return null;
    	info("Fetching data for " + uuids.size() + " transactions ...");
    	Map<String, List<ProbeData>> hitMap = client.fetchForUuids(conn, uuids, true);
    	List<String[]> rows = client.showSummary(hitMap, false);
    	if(rows.isEmpty()){
    		info("No data fetched");
    	}else
    		info("Fetched " + rows.size() + " transactions");
    	DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
    	dtm.setRowCount(0);
    	for(int x = 0 ; x < rows.size(); x++){
    		String[] row = rows.get(x);
    		dtm.addRow(row);
    	}
    	return null;
    }

    private Map<String, Long> executeAndMap(OptionsBean filter, RestHighLevelClient conn){
    	info("Querying step 1 ...");
    	try {
			return client.executeAndMap(conn, filter);
		} catch (Exception e) {
			error(e + " - " + e.getMessage());
			e.printStackTrace();
		}
    	return null;
    }

    private void btnExportMouseClicked() {//GEN-FIRST:event_btnExportMouseClicked
        int[] rows = jTable1.getSelectedRows();
        Set<String> uuids = new HashSet<>(rows.length);
        for(int x = 0; x < rows.length ; x++){
        	uuids.add(jTable1.getValueAt(rows[x], 1).toString());
        }
        CompletableFuture<Void> cf = CompletableFuture.supplyAsync(() -> doExport(uuids), service);
    }//GEN-LAST:event_btnExportMouseClicked

    private Void doExport(Set<String> uuids){
    	File dir = new File(System.getProperty("user.dir"));
    	Exporter exp = client.resolveExporter(format.getSelectedItem().toString());
		RestHighLevelClient tc = connect(esUrl);
    	for(String uuid : uuids){
    		info("<html><body>Exporting " + uuid + "<br>Directory " + dir.getAbsolutePath() + "</body></html>");
    		try {
				exp.doExport(dir, client.fetchForUuid(tc, uuid, false));
			} catch (Exception e) {
				error(e + " - " + e.getMessage());
			}
    	}
    	return null;
    }

    private void tableRowSelected(ListSelectionEvent e){
    	btnExport.setEnabled(true);
    }

    private Date validateDate(JTextField field){
    	String timeStr = field.getText();
    	if(timeStr == null || timeStr.isEmpty()){
    		clearStatus();
    		return null;
    	}
    	try {
			Date d = sdf.parse(timeStr);
			clearStatus();
			return d;
		} catch (ParseException e) {
			error("Invalid date time format, at position " + e.getErrorOffset() + " [ format yyyy-MM-dd HH:mm ]");
			field.requestFocusInWindow();
		}
    	return null;
    }

    private RestHighLevelClient connect(JTextField field){
    	String url = field.getText();
    	if(url == null || url.isEmpty() || url.equals("estc://")){
    		error("Please provide a valid ElasticSearch Url");
    		field.requestFocusInWindow();
    		return null;
    	}
		RestHighLevelClient tc = connMap.computeIfAbsent(url, k -> {
    		info("Connecting ...");
			RestHighLevelClient v = null;
			try {
				v = client.connect(k);
				ClusterHealthResponse res = v.cluster().health(Requests.clusterHealthRequest("_all"), RequestOptions.DEFAULT);
				info("Cluster Health : {}" + res.getStatus());
			} catch (Exception e) {
				error(e + " - " + e.getMessage());
				field.requestFocusInWindow();
			}
    		return v;
    	});
    	return tc;
    }
    
    private String emptyOrNull(JTextField f){
    	if(f.getText() == null || f.getText().trim().isEmpty())
    		return null;
    	if(startTime == f || endTime == f){
    		Date d = validateDate(f);
    		if(d == null)
    			return null;
    		return optionsSdf.format(d);
    	}
    	return f.getText().trim();
    }
    
    private int emptyOrZero(JTextField f){
    	if(f.getText() == null || f.getText().trim().isEmpty())
    		return 0;
    	try {
			return Integer.parseInt(f.getText().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
    	return 0;
    }
    
    private void error(String s){
    	lbl_status.setText(s);
    	lbl_status.setForeground(Color.RED);
    }
    
    private void info(String s){
    	lbl_status.setText(s);
    	lbl_status.setForeground(Color.BLUE);
    }
    
    private void clearStatus(){
    	lbl_status.setText("");
    	lbl_status.setForeground(Color.BLACK);
    }
    
    private void refreshComponents(){
    	btnExport.setEnabled(false);
    	correlationId.setText("");
    	module.setText("");
    	nodeId.setText("");
    	submodule.setText("");
    	userId.setText("");
    	invocationId.setText("");
    	esUrl.setText(getFromPrefs("esUrl", "estc://"));
    	
    	Calendar cal = new GregorianCalendar();
    	endTime.setText(sdf.format(cal.getTime()));
    	cal.add(Calendar.MINUTE, -15);
    	startTime.setText(sdf.format(cal.getTime()));
    	
    	errorType.setText("");
    	errorsOnly.setSelected(false);
    	format.setSelectedIndex(0);
    	lbl_status.setText("");
    	DefaultTableModel.class.cast(jTable1.getModel()).setRowCount(0);
    	probeType.setSelectedIndex(-1);
    	depth.setText("1");
    	search.setText("");
    }
    
    private void putInPrefs(String key, String val){
    	try {
			Preferences pref = Preferences.userNodeForPackage(getClass());
			pref.put(key, val);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private String getFromPrefs(String key, String def){
    	try {
			Preferences pref = Preferences.userNodeForPackage(getClass());
			return pref.get(key, def);
		} catch (Exception e) {
			e.printStackTrace();
			return def;
		}
    }
    
    private void disableAll(){
    	correlationId.setEnabled(false);
    	module.setEnabled(false);
    	nodeId.setEnabled(false);
    	submodule.setEnabled(false);
    	userId.setEnabled(false);
    	invocationId.setEnabled(false);
    	esUrl.setEnabled(false);
    	endTime.setEnabled(false);
    	startTime.setEnabled(false);
    	errorsOnly.setEnabled(false);
    	format.setEnabled(false);
    	btnClear.setEnabled(false);
    	btnSearch.setEnabled(false);
    	btnExport.setEnabled(false);
    	probeType.setEnabled(false);
    	errorType.setEnabled(false);
    	depth.setEnabled(false);
    	search.setEnabled(false);
    }
    
    private void enableAll(){
    	correlationId.setEnabled(true);
    	module.setEnabled(true);
    	nodeId.setEnabled(true);
    	submodule.setEnabled(true);
    	userId.setEnabled(true);
    	invocationId.setEnabled(true);
    	esUrl.setEnabled(true);
    	endTime.setEnabled(true);
    	startTime.setEnabled(true);
    	errorsOnly.setEnabled(true);
    	format.setEnabled(true);
    	btnClear.setEnabled(true);
    	btnSearch.setEnabled(true);
    	probeType.setEnabled(true);
    	errorType.setEnabled(true);
    	depth.setEnabled(true);
    	search.setEnabled(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TxProbeExportForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TxProbeExportForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TxProbeExportForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TxProbeExportForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TxProbeExportForm().setVisible(true);
            }
        });
    }
    
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnSearch;
    private javax.swing.JTextField correlationId;
    private javax.swing.JTextField depth;
    private javax.swing.JTextField endTime;
    private javax.swing.JTextField errorType;
    private javax.swing.JCheckBox errorsOnly;
    private javax.swing.JLabel lbl_esUrl;
    private javax.swing.JComboBox<String> format;
    private javax.swing.JTextField invocationId;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField esUrl;
    private javax.swing.JLabel lbl_correlationId;
    private javax.swing.JLabel lbl_depth;
    private javax.swing.JLabel lbl_endTime;
    private javax.swing.JLabel lbl_errorType;
    private javax.swing.JLabel lbl_errorsOnly;
    private javax.swing.JLabel lbl_invocationId;
    private javax.swing.JLabel lbl_invocationId1;
    private javax.swing.JLabel lbl_module;
    private javax.swing.JLabel lbl_nodeId;
    private javax.swing.JLabel lbl_probeType;
    private javax.swing.JLabel lbl_search;
    private javax.swing.JLabel lbl_startTime;
    private javax.swing.JLabel lbl_status;
    private javax.swing.JLabel lbl_subModule;
    private javax.swing.JLabel lbl_userId;
    private javax.swing.JTextField module;
    private javax.swing.JTextField nodeId;
    private javax.swing.JComboBox<Probe> probeType;
	private javax.swing.JTextField search;
    private javax.swing.JTextField startTime;
    private javax.swing.JTextField submodule;
    private javax.swing.JTextField userId;
    // End of variables declaration//GEN-END:variables
}