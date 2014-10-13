/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2014 Neil C Smith.
 * 
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details.
 * 
 * You should have received a copy of the GNU General Public License version 3
 * along with this work; if not, see http://www.gnu.org/licenses/
 * 
 * 
 * Please visit http://neilcsmith.net if you need additional information or
 * have any questions.
 */
package net.neilcsmith.praxis.live.core.ui;

import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import net.neilcsmith.praxis.live.core.HubSettings;
import net.neilcsmith.praxis.live.core.HubSlaveInfo;

final class NetworkPanel extends javax.swing.JPanel {

    private final static int DEFAULT_PORT = 13178;

    private final NetworkController controller;
    private final SlaveTableModel slaveModel;

    NetworkPanel(NetworkController ctrl) {
        this.controller = ctrl;
        initComponents();
        slaveModel = new SlaveTableModel();
        slaveTable.setModel(slaveModel);
        slaveTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                removeSlaveBtn.setEnabled(slaveTable.getSelectedRow() != -1);
            }
        });
        launcherLocation.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                controller.changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controller.changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                controller.changed();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        enableBox = new javax.swing.JCheckBox();
        localSlavePanel = new javax.swing.JPanel();
        launcherLocation = new javax.swing.JTextField();
        launcherLocationLabel = new javax.swing.JLabel();
        slavePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        slaveTable = new javax.swing.JTable();
        addSlaveBtn = new javax.swing.JButton();
        removeSlaveBtn = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(enableBox, org.openide.util.NbBundle.getMessage(NetworkPanel.class, "NetworkPanel.enableBox.text")); // NOI18N
        enableBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableBoxActionPerformed(evt);
            }
        });

        localSlavePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(NetworkPanel.class, "NetworkPanel.localSlavePanel.border.title"))); // NOI18N

        launcherLocation.setText(org.openide.util.NbBundle.getMessage(NetworkPanel.class, "NetworkPanel.launcherLocation.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(launcherLocationLabel, org.openide.util.NbBundle.getMessage(NetworkPanel.class, "NetworkPanel.launcherLocationLabel.text")); // NOI18N

        javax.swing.GroupLayout localSlavePanelLayout = new javax.swing.GroupLayout(localSlavePanel);
        localSlavePanel.setLayout(localSlavePanelLayout);
        localSlavePanelLayout.setHorizontalGroup(
            localSlavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(localSlavePanelLayout.createSequentialGroup()
                .addComponent(launcherLocationLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(launcherLocation)
        );
        localSlavePanelLayout.setVerticalGroup(
            localSlavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(localSlavePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(launcherLocationLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(launcherLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        slavePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(NetworkPanel.class, "NetworkPanel.slavePanel.border.title"))); // NOI18N

        slaveTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        slaveTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        slaveTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(slaveTable);

        org.openide.awt.Mnemonics.setLocalizedText(addSlaveBtn, org.openide.util.NbBundle.getMessage(NetworkPanel.class, "NetworkPanel.addSlaveBtn.text")); // NOI18N
        addSlaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSlaveBtnActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(removeSlaveBtn, org.openide.util.NbBundle.getMessage(NetworkPanel.class, "NetworkPanel.removeSlaveBtn.text")); // NOI18N
        removeSlaveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeSlaveBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout slavePanelLayout = new javax.swing.GroupLayout(slavePanel);
        slavePanel.setLayout(slavePanelLayout);
        slavePanelLayout.setHorizontalGroup(
            slavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
            .addGroup(slavePanelLayout.createSequentialGroup()
                .addComponent(addSlaveBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeSlaveBtn)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        slavePanelLayout.setVerticalGroup(
            slavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(slavePanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addGap(4, 4, 4)
                .addGroup(slavePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addSlaveBtn)
                    .addComponent(removeSlaveBtn)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(localSlavePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(enableBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(slavePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(enableBox)
                .addGap(18, 18, 18)
                .addComponent(slavePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(localSlavePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void enableBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableBoxActionPerformed
        boolean enabled = enableBox.isSelected();
        slaveTable.setEnabled(enabled);
        addSlaveBtn.setEnabled(enabled);
        slaveTable.clearSelection();
        controller.changed();
    }//GEN-LAST:event_enableBoxActionPerformed

    private void addSlaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSlaveBtnActionPerformed
        slaveModel.addSlave();
    }//GEN-LAST:event_addSlaveBtnActionPerformed

    private void removeSlaveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeSlaveBtnActionPerformed
        int sel = slaveTable.getSelectedRow();
        if (sel > -1) {
            slaveModel.removeSlave(sel);
        }
    }//GEN-LAST:event_removeSlaveBtnActionPerformed

    void load() {
        enableBox.setSelected(HubSettings.getDefault().isDistributedHub());
        enableBoxActionPerformed(null);
        File launcher = HubSettings.getDefault().getLocalSlaveLauncher();
        if (launcher == null) {
            launcherLocation.setText("");
        } else {
            launcherLocation.setText(launcher.getAbsolutePath());
        }
        slaveModel.load(HubSettings.getDefault().getSlaveInfo());
    }

    void store() {
        HubSettings.getDefault().setDistributedHub(enableBox.isSelected());
        HubSettings.getDefault().setSlaveInfo(slaveModel.slaves);
        String path = launcherLocation.getText().trim();
        if (!path.isEmpty()) {
            File l = new File(path);
            l = l.getAbsoluteFile();
            if (l.exists()) {
                HubSettings.getDefault().setLocalSlaveLauncher(l);
            }
        } else {
            HubSettings.getDefault().setLocalSlaveLauncher(null);
        }
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSlaveBtn;
    private javax.swing.JCheckBox enableBox;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField launcherLocation;
    private javax.swing.JLabel launcherLocationLabel;
    private javax.swing.JPanel localSlavePanel;
    private javax.swing.JButton removeSlaveBtn;
    private javax.swing.JPanel slavePanel;
    private javax.swing.JTable slaveTable;
    // End of variables declaration//GEN-END:variables

    private class SlaveTableModel extends AbstractTableModel {

        private final List<HubSlaveInfo> slaves;

        private SlaveTableModel() {
            slaves = new ArrayList<>();
        }

        private void load(List<HubSlaveInfo> storedSlaves) {
            slaves.clear();
            slaves.addAll(storedSlaves);
            fireTableDataChanged();
        }

        private void addSlave() {
            HubSlaveInfo slave = new HubSlaveInfo("localhost", DEFAULT_PORT, "*", "*", false);
            slaves.add(slave);
            fireTableRowsInserted(slaves.size() - 1, slaves.size() - 1);
        }

        private void removeSlave(int index) {
            slaves.remove(index);
            fireTableRowsDeleted(index, index);
        }

        @Override
        public boolean isCellEditable(int arg0, int arg1) {
            return true;
        }

        @Override
        public int getRowCount() {
            return slaves.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Host";
                case 1:
                    return "Port";
                case 2:
                    return "ID Pattern";
                case 3:
                    return "Type Pattern";
                case 4:
                    return "Autostart";
                default:
                    return "";
            }
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
                case 4:
                    return Boolean.class;
                default:
                    return String.class;
            }
        }

        @Override
        public Object getValueAt(int row, int column) {
            HubSlaveInfo info = slaves.get(row);
            switch (column) {
                case 0:
                    return info.getHost();
                case 1:
                    return info.getPort();
                case 2:
                    return info.getId();
                case 3:
                    return info.getType();
                case 4:
                    return info.isAutoStart();
            }
            throw new ArrayIndexOutOfBoundsException();
        }

        @Override
        public void setValueAt(Object val, final int row, final int column) {
            try {
                switch (column) {
                    case 0:
                        updateHost(val.toString(), row);
                        break;
                    case 1:
                        updatePort(val.toString(), row);
                        break;
                    case 2:
                        updateID(val.toString(), row);
                        break;
                    case 3:
                        updateType(val.toString(), row);
                        break;
                    case 4:
                        updateAutostart((Boolean) val, row);
                        break;
                }
                fireTableRowsUpdated(row, row);
                controller.changed();
            } catch (Exception ex) {
                EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        slaveTable.editCellAt(row, column);
                    }
                });
                
            }

        }

        private void updateHost(String host, int row) {
            HubSlaveInfo oldSlave = slaves.get(row);
            if (host.isEmpty()) {
                host = "localhost";
            }
            boolean autostart = oldSlave.isAutoStart() && host.equals("localhost");
            HubSlaveInfo slave = new HubSlaveInfo(host,
                    oldSlave.getPort(),
                    oldSlave.getId(),
                    oldSlave.getType(),
                    autostart);
            slaves.set(row, slave);
        }

        private void updatePort(String portString, int row) {
            HubSlaveInfo oldSlave = slaves.get(row);
            int port = portString.isEmpty() ? DEFAULT_PORT : Integer.parseInt(portString);
            HubSlaveInfo slave = new HubSlaveInfo(oldSlave.getHost(),
                    port,
                    oldSlave.getId(),
                    oldSlave.getType(),
                    oldSlave.isAutoStart());
            slaves.set(row, slave);
        }

        private void updateID(String id, int row) {
            HubSlaveInfo oldSlave = slaves.get(row);
            id = id.isEmpty() ? "*" : id;
            HubSlaveInfo slave = new HubSlaveInfo(oldSlave.getHost(),
                    oldSlave.getPort(),
                    id,
                    oldSlave.getType(),
                    oldSlave.isAutoStart());
            slaves.set(row, slave);
        }

        private void updateType(String type, int row) {
            HubSlaveInfo oldSlave = slaves.get(row);
            type = type.isEmpty() ? "*" : type;
            HubSlaveInfo slave = new HubSlaveInfo(oldSlave.getHost(),
                    oldSlave.getPort(),
                    oldSlave.getId(),
                    type,
                    oldSlave.isAutoStart());
            slaves.set(row, slave);
        }

        private void updateAutostart(boolean autostart, int row) {
            HubSlaveInfo oldSlave = slaves.get(row);
            autostart = autostart && "localhost".equals(oldSlave.getHost());
            HubSlaveInfo slave = new HubSlaveInfo(oldSlave.getHost(),
                    oldSlave.getPort(),
                    oldSlave.getId(),
                    oldSlave.getType(),
                    autostart);
            slaves.set(row, slave);
        }

    }

}
