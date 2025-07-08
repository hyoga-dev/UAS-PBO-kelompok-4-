package toko.utils;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.FileWriter;
import java.io.IOException;

public class ExportCSV {

    public static void exportTableToCSV(JTable table, String path) {
        try (FileWriter csv = new FileWriter(path)) {
            TableModel model = table.getModel();

            // Tulis header kolom
            for (int i = 0; i < model.getColumnCount(); i++) {
                csv.write(model.getColumnName(i));
                if (i != model.getColumnCount() - 1) csv.write(",");
            }
            csv.write("\n");

            // Tulis isi data
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    csv.write(model.getValueAt(row, col).toString());
                    if (col != model.getColumnCount() - 1) csv.write(",");
                }
                csv.write("\n");
            }

            System.out.println("Data berhasil diekspor ke " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
