/*
 * TxProbeExportWeb.java Created on 30-Dec-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es.www;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbeData;
import com.ibsplc.icargo.txprobe.api.ProbeDataConstants;
import com.ibsplc.icargo.txprobe.tools.es.*;
import com.ibsplc.icargo.txprobe.tools.es.ElasticSearchExportClient.ErrorSeverity;
import com.vaadin.annotations.Theme;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.themes.ValoTheme;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			30-Dec-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
@SpringUI(path = "/txprobe/export-web")
@Theme("valo")
@Service("exportWebUI")
public class TxProbeExportWeb extends com.vaadin.ui.UI implements ProbeDataConstants {

    private static final long serialVersionUID = -3702807531816956330L;

    static Logger logger = LoggerFactory.getLogger(TxProbeExportWeb.class);

    private TextField correlationId = new TextField("Correlation Id");
    private TextField invocationId = new TextField("Invocation Id");
    private TextField nodeId = new TextField("Node Id");
    private TextField userId = new TextField("User Id");
    private TextField module = new TextField("Module");
    private TextField submodule = new TextField("SubModule");
    private PopupDateField startTime = new PopupDateField("Start Time");
    private PopupDateField endTime = new PopupDateField("End Time");
    private CheckBox errorsOnly = new CheckBox();
    private ComboBox exportFormat = new ComboBox("Export Format");
    private TextField errorType = new TextField("Error Type");
    private ComboBox probeType = new ComboBox("Probe Type");
    private TextField depth = new TextField("Depth");
    private TextField search = new TextField("Search");
    private Button clearBtn = new Button("Clear");
    private Button exportBtn = new Button("Export");
    private Button searchBtn = new Button("Search");
    private Table probeTable = new Table("TxProbe Transactions");
    private Label status = new Label();

    private SimpleDateFormat optionsSdf = new SimpleDateFormat("yyyyMMddHHmm");
    static String ESURL = System.getProperty("es.serverUrl");
    static ElasticSearchExportClient CLIENT = new ElasticSearchExportClient();
    static TransportClientPool POOL;

    @Override
    protected void init(VaadinRequest request) {
        if (POOL == null)
            POOL = new TransportClientPool(CLIENT, ESURL);
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        layout.addComponent(createFilterPanel());
        layout.addComponent(createTable());
        setContent(layout);
        clearAction(null);
    }

    private void searchAction(ClickEvent event) {
        OptionsBean options = constructFilter();
        try {
            POOL.execute(c -> {
                performListing(options, c);
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private OptionsBean constructFilter() {
        OptionsBean options = new OptionsBean();
        options.setCorrelationId(emptyOrNull(correlationId));
        options.setEndDate(emptyOrNull(endTime));
        options.setStartDate(emptyOrNull(startTime));
        options.setErrorsOnly(errorsOnly.getValue());
        options.setFormat("html");
        options.setInvocationId(emptyOrNull(invocationId));
        options.setModule(emptyOrNull(module));
        options.setNodeName(emptyOrNull(nodeId));
        options.setSubmodule(emptyOrNull(submodule));
        options.setUser(emptyOrNull(userId));
        options.setErrorType(emptyOrNull(errorType));
        options.setDepth(emptyOrZero(depth));
        options.setSearch(emptyOrNull(search));
        options.setProbeType(emptyOrNull(probeType));
        return options;
    }

    private Void performListing(OptionsBean filter, RestHighLevelClient conn) {
        //disableAll();
        //btnExport.setEnabled(false);
        Map<String, Long> uuids = null;
        if (filter.getCorrelationId() != null) {
            uuids = new HashMap<>(2);
            uuids.put(filter.getCorrelationId(), Long.valueOf(0));
        } else
            uuids = executeAndMap(filter, conn);
        if (uuids == null)
            return null;
        this.probeTable.removeAllItems();
        this.probeTable.clear();
        status.setValue("Fetching data for " + uuids.size() + " transactions ...");
        Map<String, List<ProbeData>> hitMap = CLIENT.fetchForUuids(conn, uuids, true);
        List<Map.Entry<ErrorSeverity, Object[]>> rows = CLIENT.showDetailedSummary(hitMap, uuids, false);
        if (rows.isEmpty()) {
            logger.debug("No data fetched");
            Notification.show("No data featched", Type.HUMANIZED_MESSAGE);
        } else {
            status.setValue("Fetched " + rows.size() + " transactions");
            for (int x = 0; x < rows.size(); x++) {
                Object[] row = rows.get(x).getValue();
                String itemId = new StringBuilder().append(rows.get(x).getKey()).append(':').append(x).toString();
                this.probeTable.addItem(row, itemId);
            }
        }
        return null;
    }

    private Map<String, Long> executeAndMap(OptionsBean filter, RestHighLevelClient conn) {
        status.setValue("Querying step 1 ...");
        try {
            return CLIENT.executeAndMap(conn, filter);
        } catch (Exception e) {
            logger.warn("Exception in executeAndMap phase", e);
        }
        return null;
    }

    private void clearAction(ClickEvent event) {
        correlationId.clear();
        invocationId.clear();
        nodeId.clear();
        userId.clear();
        module.clear();
        submodule.clear();
        startTime.clear();
        endTime.clear();
        errorsOnly.clear();
        errorType.clear();
        probeType.clear();
        depth.clear();
        search.clear();
        probeTable.removeAllItems();
        probeTable.clear();
        Calendar cal = new GregorianCalendar();
        endTime.setValue(cal.getTime());
        cal.add(Calendar.MINUTE, -15);
        startTime.setValue(cal.getTime());
        depth.setValue("1");
        status.setValue(resolveESHealth());
    }

    private StreamResource exportAction(ClickEvent event) {
        Set<String> selected = resolveSelectedIds();
        if (selected.isEmpty()) {
            Notification.show("Please select a row", Type.WARNING_MESSAGE);
            return null;
        }
        StreamResource resource = exportToClient(selected.iterator().next());
        return resource;
    }

    private StreamResource exportToClient(String correlationId) {
        try {
            File dir = new File(System.getProperty("java.io.tmpdir"));
            Exporter exporter = null;
            String fileName = correlationId;
            switch (exportFormat.getValue().toString()) {
                case "Text":
                    exporter = new TextExporter();
                    fileName = fileName + ".txprobe.log";
                    break;
                case "Excel":
                    exporter = new XLSXExporter();
                    fileName = fileName + ".txprobe.xlsx";
                    break;
                case "Html":
                default:
                    exporter = new HtmlExporter();
                    fileName = fileName + ".txprobe.html";
                    break;
            }
            List<ProbeData> pds = POOL.execute(c -> CLIENT.fetchForUuid(c, correlationId, false));
            exporter.doExport(dir, pds);
            File file = new File(dir, fileName);
            PurgingStreamSource source = new PurgingStreamSource(file);
            StreamResource resource = new StreamResource(source, source.name());
            resource.setCacheTime(0);
            resource.setMIMEType("text/html");
            return resource;
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Error while exporting data : " + e, e.getMessage(), Type.ERROR_MESSAGE);
        }
        return null;
    }

    static StreamResource dummy_resource;

    static {
        StreamResource res = new StreamResource(new PurgingStreamSource(new File("_dummy_")), "_dummy_");
        res.setCacheTime(0);
        TxProbeExportWeb.dummy_resource = res;
    }

    /**
     * @author A-2394
     */
    class OnDemandFileDownloader extends FileDownloader {
        private static final long serialVersionUID = -1543369180748590217L;


        public OnDemandFileDownloader() {
            super(dummy_resource);
        }

        @Override
        public boolean handleConnectorRequest(VaadinRequest request, VaadinResponse response, String path) throws IOException {
            StreamResource res = exportAction(null);
            if (res != null) {
                setFileDownloadResource(res);
            }
            return super.handleConnectorRequest(request, response, path);
        }

    }

    /**
     * @author A-2394
     */
    static class PurgingStreamSource implements StreamResource.StreamSource {
        private static final long serialVersionUID = 2993127200884776817L;

        final File file;

        public String name() {
            return file.getName();
        }

        public PurgingStreamSource(File file) {
            super();
            this.file = file;
            file.deleteOnExit();
        }

        /* (non-Javadoc)
         * @see com.vaadin.server.StreamResource.StreamSource#getStream()
         */
        @Override
        public InputStream getStream() {
            if (file != null && file.exists()) {
                try {
                    InputStream is = new FileInputStream(file) {
                        @Override
                        public void close() throws IOException {
                            logger.debug("deleting file : " + file);
                            super.close();
                            if (file.exists())
                                file.delete();
                        }
                    };
                    return is;
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

    private Set<String> resolveSelectedIds() {
        Object selected = this.probeTable.getValue();
        if (selected == null)
            return Collections.emptySet();
        if (selected instanceof Set<?>) {
            Set<?> selectedObjectIds = (Set<?>) selected;
            Set<String> answer = new HashSet<>(selectedObjectIds.size());
            selectedObjectIds.forEach(sobi -> answer.add(this.probeTable.getContainerProperty(sobi, "correlationId").getValue().toString()));
            return answer;
        }
        Set<String> answer = new HashSet<>(2);
        answer.add(this.probeTable.getContainerProperty(selected, "correlationId").getValue().toString());
        return answer;
    }

    private String emptyOrNull(AbstractField<?> f) {
        if (f.getValue() == null || f.getValue().toString().trim().isEmpty())
            return null;
        if (startTime == f || endTime == f) {
            Date d = (Date) f.getValue();
            if (d == null)
                return null;
            return optionsSdf.format(d);
        }
        return f.getValue().toString();
    }

    private int emptyOrZero(AbstractField<?> f) {
        if (f.getValue() == null || f.getValue().toString().trim().isEmpty())
            return 0;
        try {
            return Integer.parseInt(f.getValue().toString().trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected Component createTable() {
        probeTable.addContainerProperty("startTime", String.class, "", "Start Time", null, Align.LEFT);
        probeTable.addContainerProperty("correlationId", String.class, "", "Correlation Id", null, Align.LEFT);
        probeTable.addContainerProperty("probeType", String.class, "", "Probe Type", null, Align.LEFT);
        probeTable.addContainerProperty("user", String.class, "", "User", null, Align.LEFT);
        probeTable.addContainerProperty("nodeName", String.class, "", "Node", null, Align.LEFT);
        probeTable.addContainerProperty("elapsedTime", Integer.class, 0, "Execution Time", null, Align.LEFT);
        probeTable.addContainerProperty("depth", Integer.class, 0, "Depth", null, Align.LEFT);
        probeTable.addContainerProperty("operation", String.class, "", "Operation", null, Align.LEFT);
        probeTable.setSizeFull();
        probeTable.addStyleName("header-wrap");
        probeTable.addStyleName("cell-wrapper");
        probeTable.setSelectable(true);
        probeTable.setEditable(false);
        probeTable.setPageLength(25);
        probeTable.setImmediate(true);
        probeTable.setCellStyleGenerator(new Table.CellStyleGenerator() {
            private static final long serialVersionUID = -1427487900874700065L;

            @Override
            public String getStyle(Table source, Object itemId, Object propertyId) {
                // column styling
                if (propertyId != null)
                    return null;
                if (itemId != null && itemId.toString().contains(":")) {
                    final String sev = itemId.toString().substring(0, itemId.toString().indexOf(':'));
                    switch (sev) {
                        case "GREEN":
                            return "highlight-green";
                        case "YELLOW":
                            return "highlight-yellow";
                        case "RED":
                            return "highlight-red";
                    }
                }
                return null;
            }
        });

        //probeTable.setItemDescriptionGenerator(this::generateDescription);
        return probeTable;
    }

    protected Layout createActionPanel() {
        HorizontalLayout layout = new HorizontalLayout();
        clearBtn.addStyleName(ValoTheme.BUTTON_TINY);
        clearBtn.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        exportBtn.addStyleName(ValoTheme.BUTTON_TINY);
        searchBtn.addStyleName(ValoTheme.BUTTON_TINY);
        searchBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
        layout.setSpacing(true);
        layout.addComponent(clearBtn);
        layout.addComponent(exportBtn);
        layout.addComponent(searchBtn);

        searchBtn.addClickListener(this::searchAction);
        clearBtn.addClickListener(this::clearAction);
        //exportBtn.addClickListener(this::exportAction);
        FileDownloader fileDownloader = new OnDemandFileDownloader();
        fileDownloader.extend(exportBtn);
        return layout;
    }

    protected Layout createFilterPanel() {
        GridLayout layout = new GridLayout(7, 4);
        layout.setMargin(false);
        layout.setSpacing(true);
        layout.setDefaultComponentAlignment(Alignment.BOTTOM_LEFT);
        // apply style
        correlationId.addStyleName(ValoTheme.TEXTFIELD_TINY);
        invocationId.addStyleName(ValoTheme.TEXTFIELD_TINY);
        nodeId.addStyleName(ValoTheme.TEXTFIELD_TINY);
        userId.addStyleName(ValoTheme.TEXTFIELD_TINY);
        probeType.addStyleName(ValoTheme.TEXTFIELD_TINY);
        module.addStyleName(ValoTheme.TEXTFIELD_TINY);
        submodule.addStyleName(ValoTheme.TEXTFIELD_TINY);
        startTime.addStyleName(ValoTheme.DATEFIELD_TINY);
        endTime.addStyleName(ValoTheme.DATEFIELD_TINY);
        depth.addStyleName(ValoTheme.TEXTFIELD_TINY);
        errorsOnly.addStyleName(ValoTheme.COMBOBOX_TINY);
        errorType.addStyleName(ValoTheme.TEXTFIELD_TINY);
        search.addStyleName(ValoTheme.TEXTFIELD_TINY);
        exportFormat.addStyleName(ValoTheme.TEXTFIELD_TINY);
        search.setColumns(50);
        Label errorOnlyLbl = new Label("Errors Only");
        errorOnlyLbl.addStyleName(ValoTheme.TEXTFIELD_SMALL);

        errorType.setDescription("Error types can be BSE,SYE or RTE. It can be negated by ! operator");
        errorType.setMaxLength(4);
        errorType.setColumns(4);
        startTime.setResolution(Resolution.MINUTE);
        endTime.setResolution(Resolution.MINUTE);
        startTime.setDateFormat("yyyy-MM-dd HH:mm");
        startTime.setWidth(16, Unit.EM);
        endTime.setDateFormat("yyyy-MM-dd HH:mm");
        endTime.setWidth(16, Unit.EM);
        depth.setMaxLength(5);
        depth.setColumns(5);
        status.addStyleName(ValoTheme.LABEL_BOLD);
        status.setImmediate(true);

        probeType.setNullSelectionItemId("");
        probeType.setNullSelectionAllowed(true);
        probeType.addItems(Probe.values());

        exportFormat.addItems("Html", "Excel", "Text");
        exportFormat.setNullSelectionAllowed(false);
        exportFormat.setValue("Html");

        startTime.setRequired(true);
        endTime.setRequired(true);
        //layout.addStyleName(ValoTheme.LAYOUT_CARD);
        Label filter = new Label("Filter");
        filter.addStyleName(ValoTheme.LABEL_BOLD);
        filter.addStyleName(ValoTheme.LABEL_H4);

        layout.addComponent(filter, 0, 0, 4, 0);
        layout.addComponent(correlationId, 0, 1);
        layout.addComponent(invocationId, 1, 1);
        layout.addComponent(nodeId, 2, 1);
        layout.addComponent(userId, 3, 1);
        layout.addComponent(probeType, 4, 1);
        layout.addComponent(module, 5, 1);
        layout.addComponent(submodule, 6, 1);

        layout.addComponent(startTime, 0, 2);
        layout.addComponent(endTime, 1, 2);
        layout.addComponent(depth, 2, 2);
        layout.addComponent(errorType, 3, 2);
        VerticalLayout comboLayout = new VerticalLayout(errorOnlyLbl, errorsOnly);
        layout.addComponent(comboLayout, 4, 2);
        layout.addComponent(exportFormat, 5, 2);

        layout.addComponent(search, 0, 3, 2, 3);
        layout.addComponent(status, 3, 3, 4, 3);
        Layout btnLayout = createActionPanel();
        //layout.setDefaultComponentAlignment(Alignment.BOTTOM_RIGHT);
        layout.addComponent(btnLayout, 6, 3);
        return layout;
    }

    private String resolveESHealth() {
        try {
            return POOL.execute(this::getHealth);
        } catch (Exception e) {
            return e.toString();
        }
    }

    private String getHealth(RestHighLevelClient wrapper) throws Exception {
        ClusterHealthResponse res;
        res = wrapper.cluster().health(Requests.clusterHealthRequest("_all"), RequestOptions.DEFAULT);
        return "Cluster Health : " + res.getStatus().toString();
    }
}
