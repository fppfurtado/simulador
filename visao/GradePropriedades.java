/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulador.visao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Fernando
 */
public class GradePropriedades extends JTable {

    public static boolean DEBUG = false;
    private final DuasColunasModel model;
    private Class classeEdicaoCelula;

    public GradePropriedades(String col1, String col2) {

        super();

        if (DEBUG) {
            System.out.println("new GradeDuasColunas");
        }

        this.model = new DuasColunasModel(col1, col2);
        setModel(model);
        setFillsViewportHeight(true);

    }

    private void adicionarLinha(Object[] linha) {

        if (DEBUG) {
            System.out.println("GradeDuasColunas.adicionarLinha");
        }

        model.adicionarRegistro(linha);

    }

    public void limparDados() {

        model.propriedades.clear();
        model.ObjetosProprietarios.clear();
        model.fireTableDataChanged();

    }
    
    public void exibirPropriedadesObjeto(Object obj) {

        limparDados();

        Class superClasse = obj.getClass().getSuperclass();
        Field[] propriedadesClasse = superClasse.getDeclaredFields();
        
        for(Field propriedade : propriedadesClasse) {
        
            if (propriedade.isAnnotationPresent(Resource.class)) {
                adicionarLinha(new Object[]{propriedade, obj});
            }
        
        }        
        
        propriedadesClasse = obj.getClass().getDeclaredFields();

        for (Field propriedade : propriedadesClasse) {

            if (propriedade.isAnnotationPresent(Resource.class)) {
                adicionarLinha(new Object[]{propriedade, obj});
            }

        }

        Class[] classesInternas = obj.getClass().getDeclaredClasses();

        for (int i = 0; i < classesInternas.length; i++) {
            for (Field propriedade : propriedadesClasse) {
                
                if (propriedade.getClass().equals(classesInternas[0])) {
                    
                    Field[] propriedadesClasseInterna = propriedade.getClass().getDeclaredFields();

                    for (Field objetoClasseInterna : propriedadesClasseInterna) {

                        if (objetoClasseInterna.isAnnotationPresent(Resource.class)) {
                            adicionarLinha(new Object[]{propriedade, objetoClasseInterna});
                        }

                    }
                    
                }
                
            }
        }

    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        classeEdicaoCelula = null;
        int modelColumn = convertColumnIndexToModel(column);
        if (modelColumn == 1) {
            Class classeLinha = getModel().getValueAt(row, modelColumn).getClass();
            return getDefaultRenderer(classeLinha);
        } else {
            return super.getCellRenderer(row, column);
        }
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        classeEdicaoCelula = null;
        int modelColumn = convertColumnIndexToModel(column);
        if (modelColumn == 1) {
            
            Object obj = getModel().getValueAt(row, modelColumn);
            classeEdicaoCelula = getModel().getValueAt(row, modelColumn).getClass();
            //System.out.println("classe do item: "+classeEdicaoCelula);
            if(obj instanceof Enum) {
                
                Enum objEnum = (Enum) obj;
                Enum[] enumeracoes = objEnum.getClass().getEnumConstants();
                JComboBox combo = new JComboBox(enumeracoes);
                
                return new DefaultCellEditor(combo);
                
            } else {
                return getDefaultEditor(classeEdicaoCelula);
            }
            
            
            
            
        } else {
            return super.getCellEditor(row, column);
        }
    }

    class DuasColunasModel extends AbstractTableModel {

        public static final int COLUNA_1 = 0;
        public static final int COLUNA_2 = 1;
        private final String[] colunas;
        private final List<Field> propriedades = new ArrayList();
        private final List ObjetosProprietarios = new ArrayList();

        public DuasColunasModel(String col1, String col2) {
            this.colunas = new String[]{col1, col2};
        }

        private void adicionarRegistro(Object[] registro) {

            if (DEBUG) {
                System.out.println("DuasColunasModel.adicionarRegistro");
            }

            propriedades.add((Field)registro[0]);
            ObjetosProprietarios.add(registro[1]);

            //System.out.println(registro[0] + ": " + registro[1]);
            fireTableDataChanged();

        }

        @Override
        public int getRowCount() {

            if (DEBUG) {
                System.out.println("DuasColunasModel.getRowCount");
            }

            return propriedades.size();

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
                System.out.println("DuasColunasModel.getValueAt");
            }

            switch (columnIndex) {
                case COLUNA_1:
                    return propriedades.get(rowIndex).getName();
                case COLUNA_2:

                    Field propriedade = propriedades.get(rowIndex);
                    propriedade.setAccessible(true);

                    try {
                        return propriedades.get(rowIndex).get(ObjetosProprietarios.get(rowIndex));
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        JOptionPane.showMessageDialog(null, "Acesso negado a propriedade " + propriedade.getName());
                        ex.printStackTrace();
                    }
                
                default:
                    return null;
            }

        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {

            if (DEBUG) {
                System.out.println("DuasColunasModel.getColumnClass");
            }

            //return columnIndex == COLUNA_1 ? propriedades.get(0).getClass() : ObjetosProprietarios.get(0).getClass();
            return classeEdicaoCelula != null ? classeEdicaoCelula : super.getColumnClass(columnIndex);

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
                System.out.println("DuasColunasModel.setValueAt");
            }

            Field propriedade = propriedades.get(rowIndex);
            propriedade.setAccessible(true);
            try {
                propriedade.set(ObjetosProprietarios.get(rowIndex), aValue);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                JOptionPane.showMessageDialog(null, "Não foi possível alterar a propriedade " + propriedade.getName());
                ex.printStackTrace();
            }
            
        }

    }

}
