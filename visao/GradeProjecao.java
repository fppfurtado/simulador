/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Fernando
 */
public class GradeProjecao extends JTable implements PropertyChangeListener {

    public static boolean DEBUG = false;
    private final DuasColunasModel model;
    //private PainelDesign painelDesign = null;
    //private Class classeEdicaoCelula;

    public GradeProjecao(String col1, String col2) {

        super();

        if (DEBUG) {
            System.out.println("new GradeDuasColunas");
        }

        this.model = new DuasColunasModel(col1, col2);

        setModel(model);
        setFillsViewportHeight(true);
        //setDefaultRenderer(Boolean.class, new RenderizadorTabelaProjecao());
        //setDefaultEditor(Boolean.class, new EditorTabelaProjecao());

    }
    
    public void limparTabela() {

        if (DEBUG) {
            System.out.println("GradeDuasColunas.limparTabela");
        }

        model.idsRadios.clear();
        model.valoresCheckbox.clear();
        model.fireTableDataChanged();

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (DEBUG) {
            System.out.println("GradeProjecao.propertyChange: " + evt.getPropertyName());
        }

        PainelDesign painelDesign = (PainelDesign) evt.getSource();
        Map<Integer, Radio2D> mapaRadios2D = painelDesign.getRadios2D();

        switch (evt.getPropertyName()) {

            case "flagSimulacao":

                boolean simulacao = (Boolean) evt.getNewValue();

                //System.out.println("flagSimulacao: "+simulacao);
                if (simulacao) {

                    limparTabela();

                    //Map<Integer, Radio> mapaRadios = ambiente.getRadios();
                    //Map<Integer, Radio2D> mapaRadios = painelDesign.getRadios2D();
                    for (Map.Entry<Integer, Radio2D> parRadio2D : mapaRadios2D.entrySet()) {
                        //adicionarRadio(parRadio2D.getKey());
                        //System.out.println("adicionado: "+parRadio2D.getKey()+", "+false);
                        model.adicionarRegistro(new Object[]{parRadio2D.getKey(), false});
                    }

                } else {
                    //System.out.println("limparndo tabela");
                    limparTabela();
                    //System.out.println("limparndo lista radios selecionados");
                }

                break;

            case "moved":

                List<Line2D> linhas = new ArrayList();

                if (painelDesign.isFlagSimulacao()) {

                    for (Map.Entry<Integer, Boolean> parCheckBox : model.valoresCheckbox.entrySet()) {

                        if (parCheckBox.getValue()) {

                            Radio2D r2D = mapaRadios2D.get(parCheckBox.getKey());
                            Rectangle forma = (Rectangle) r2D.getForma();

                            Point localizacao = new Point(
                                    (int) forma.getCenterX(),
                                    (int) forma.getCenterY()
                            );

                            linhas.add(new Line2D.Float(localizacao, (Point) evt.getNewValue()));

                        }

                    }

                }

                if (linhas.size() > 0) {
                    painelDesign.desenharLinhas(linhas);
                }

                break;

            case BarraFerramentas.BT_SELECAO:

                Point pontoClicado = (Point) evt.getNewValue();

                if (model.idsRadios.size() > 0 && painelDesign.isFlagSimulacao()) {
                    firePropertyChange(BarraFerramentas.BT_SELECAO, pontoClicado, model.valoresCheckbox);
                }

                break;

        }

    }

    class DuasColunasModel extends AbstractTableModel {

        protected static final int COLUNA_1 = 0;
        protected static final int COLUNA_2 = 1;
        private final String[] colunas;
        private final List<Integer> idsRadios = new ArrayList();
        private final Map<Integer, Boolean> valoresCheckbox = new HashMap<>();

        public DuasColunasModel(String col1, String col2) {
            this.colunas = new String[]{col1, col2};
        }

        private void adicionarRegistro(Object[] registro) {

            if (DEBUG) {
                System.out.println("DuasColunasModel.adicionarRegistro: "+registro[0]+", "+registro[1]);
            }

            int objId = (Integer) registro[COLUNA_1];

            idsRadios.add(objId);
            valoresCheckbox.put(objId, (boolean)registro[COLUNA_2]);
            
            //System.out.println("mapeados: "+objId+", "+registro[COLUNA_2]);

            //System.out.println(registro[COLUNA_1]+": "+registro[COLUNA_2]);
            fireTableDataChanged();

        }

        @Override
        public int getRowCount() {

            if (DEBUG) {
                System.out.println("DuasColunasModel.getRowCount");
            }

            return idsRadios.size();

        }

        @Override
        public int getColumnCount() {

            if (DEBUG) {
                System.out.println("DuasColunasModel.getColumnCount");
            }

            return colunas.length;

        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {

            if (DEBUG) {
                //System.out.println("contem "+rowIndex+"? "+valoresCheckbox.containsKey(rowIndex));
                    
                System.out.print("DuasColunasModel.getValueAt("+rowIndex+", "+columnIndex+"): ");
            }

            switch (columnIndex) {
                case COLUNA_1:
                    //System.out.println(idsRadios.get(rowIndex));
                    return idsRadios.get(rowIndex);
                case COLUNA_2:
                    //System.out.println(valoresCheckbox.get(rowIndex));
                    return valoresCheckbox.get(idsRadios.get(rowIndex));
                default:
                    return null;
            }

        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {

            if (DEBUG) {
                System.out.println("DuasColunasModel.getColumnClass");
            }

            return columnIndex == COLUNA_1 ? Integer.class : Boolean.class;
            //return classeEdicaoCelula != null ? classeEdicaoCelula : super.getColumnClass(columnIndex);

        }

        @Override
        public String getColumnName(int column) {

            if (DEBUG) {
                System.out.println("DuasColunasModel.getColumnName");
            }

            return colunas[column];

        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {

            if (DEBUG) {
                System.out.println("DuasColunasModel.isCellEditable");
            }

            return columnIndex != COLUNA_1;

        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

            if (DEBUG) {
                System.out.println("DuasColunasModel.setValueAt(" + rowIndex + ", " + columnIndex + "): " + aValue);
            }

            switch (columnIndex) {
                case COLUNA_1:
                    idsRadios.remove(rowIndex);
                    idsRadios.add(rowIndex, (Integer) aValue);
                    break;
                case COLUNA_2:
                    int objId = idsRadios.get(rowIndex);
                    //System.out.println("checkbox radio "+objId+" antes: "+valoresCheckbox.get(objId));
                    valoresCheckbox.replace(objId, (Boolean) aValue);
                    //System.out.println("checkbox  radio "+objId+" depois: "+valoresCheckbox.get(objId));
                    //firePropertyChange("gradeProjecao", this, objId);
                    break;
                default:
                    throw new IllegalArgumentException("Valor inválido em DuasColunasModel.setValueAt");
            }

        }

    }

//    class RenderizadorTabelaProjecao extends JCheckBox implements TableCellRenderer {
//
//        @Override
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//
//            System.out.println("RenderizadorTabelaProjecao.getTableCellRenderizadorComponent - value: " + value);
//
//            if (isSelected) {
//                //if (selectedBorder == null) {
//                //selectedBorder = 
//                setBackground(table.getSelectionBackground());
//                //}
//                //setBorder(BorderFactory.createMatteBorder(2,5,2,5,table.getSelectionBackground()));
//            } else {
//                //if (unselectedBorder == null) {
//                setBackground(table.getBackground());
//                //setBorder(BorderFactory.createMatteBorder(2,5,2,5,table.getBackground()));
//            }
//            //setBackground(table.getBackground());
//            setSelected((Boolean) value);
//
//            return this;
//
//        }
//
//    }
//
//    class EditorTabelaProjecao extends AbstractCellEditor implements TableCellEditor, ActionListener {
//
//        private final JCheckBox cb;
//        private Boolean valor;
//
//        public EditorTabelaProjecao() {
//            this.cb = new JCheckBox();
//            this.cb.addActionListener(this);
//        }
//
//        @Override
//        public Object getCellEditorValue() {
//            System.out.println("EditorTabelaProjecao.getCellEditorValue: " + valor);
//            return valor;
//        }
//
//        @Override
//        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
//
//            System.out.println("EditorTabelaProjecao.getTableCellEditorComponent");
////            System.out.println("value: "+value);
////            System.out.println("isSelected: "+isSelected);
////            System.out.println("row: "+row);
////            System.out.println("column: "+column);
//
//            valor = !(Boolean) value;
//
//            cb.setSelected((Boolean) value);
//
//            if (isSelected) {
//                cb.setBackground(table.getSelectionBackground());
//            } else {
//                cb.setBackground(table.getBackground());
//            }
//
//            return cb;
//
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            System.out.println("EditorTabelaProjecao.actionPerformed");
//            fireEditingStopped();
//        }
//    }

}
