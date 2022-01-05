package com.github.valb3r.ij20213bugwitheditortextfield;

import com.intellij.ui.EditorTextField
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.AbstractTableCellEditor
import java.awt.Component
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.table.DefaultTableModel
import javax.swing.table.TableCellEditor
import javax.swing.table.TableCellRenderer
import javax.swing.table.TableModel

open class Buggy {

    private lateinit var propertiesPanel: JPanel

    init {
        val model = DefaultTableModel()
        val table = MultiEditJTable(model)
        table.autoResizeMode = JTable.AUTO_RESIZE_OFF
        table.rowHeight = 24
        model.addColumn("First")
        model.addColumn("Second")
        model.addRow(arrayOf("See right:", EditorTextField("Should be visible")))
        model.fireTableDataChanged()
        val scrollPane = JBScrollPane(table)
        propertiesPanel.removeAll()
        propertiesPanel.add(scrollPane)
        table.getColumn("First").minWidth = 200
        table.getColumn("Second").minWidth = 200
        propertiesPanel.updateUI()
    }

    fun getContent() = this.propertiesPanel
}

class MultiEditJTable(tableModel: TableModel): JBTable(tableModel) {

    override fun getCellEditor(row: Int, column: Int): TableCellEditor {
        return when (val value = modelValue(row, column)) {
            is EditorTextField -> EditorTextFieldCellEditor(value)
            else -> super.getCellEditor(row, column)
        }
    }

    override fun getCellRenderer(row: Int, column: Int): TableCellRenderer {
        return when (val value = modelValue(row, column)) {
            is EditorTextField -> EditorTextFieldCellRenderer(value)
            else -> super.getCellRenderer(row, column)
        }
    }

    private fun modelValue(row: Int, column: Int): Any? {
        val modelRow = convertRowIndexToModel(row)
        val modelColumn = convertColumnIndexToModel(column)
        return super.dataModel.getValueAt(modelRow, modelColumn)
    }
}

class EditorTextFieldCellEditor(val field: EditorTextField): AbstractTableCellEditor() {

    override fun getTableCellEditorComponent(table: JTable?, value: Any?, isSelected: Boolean, row: Int, column: Int): Component {
        return field
    }

    override fun getCellEditorValue(): Any {
        return field
    }
}

class EditorTextFieldCellRenderer(val field: EditorTextField): TableCellRenderer {

    override fun getTableCellRendererComponent(
        table: JTable?, value: Any?, isSelected: Boolean, hasFocus: Boolean, row: Int, column: Int): Component {
        // FIXME Recent IntelliJ's (2021.3) renders EditorTextField as empty cells when they are not edited, so replacing them with label instead
        return field
    }
}
