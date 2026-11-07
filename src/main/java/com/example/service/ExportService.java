package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.FontFactory;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

/**
 * Servicio que genera los reportes descargables (PDF y Excel) a partir de
 * una tabla: un título, los encabezados y las filas de datos.
 */
@Service
public class ExportService {

    private static final int ANCHO_MAXIMO_COLUMNA = 60;

    /**
     * Generar un PDF con los datos en una tabla
     */
    public byte[] generarPdf(String titulo, List<String> encabezados, List<Object[]> filas) {
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        Document documento = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(documento, salida);
        documento.open();

        Paragraph parrafoTitulo = new Paragraph(titulo, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
        parrafoTitulo.setSpacingAfter(12);
        documento.add(parrafoTitulo);

        PdfPTable tabla = new PdfPTable(encabezados.size());
        tabla.setWidthPercentage(100);
        tabla.setHeaderRows(1);

        for (String encabezado : encabezados) {
            PdfPCell celda = new PdfPCell(new Phrase(encabezado, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setGrayFill(0.9f);
            celda.setPadding(5);
            tabla.addCell(celda);
        }

        for (Object[] fila : filas) {
            for (Object valor : fila) {
                PdfPCell celda = new PdfPCell(new Phrase(texto(valor), FontFactory.getFont(FontFactory.HELVETICA, 9)));
                celda.setPadding(4);
                tabla.addCell(celda);
            }
        }

        documento.add(tabla);
        documento.close();
        return salida.toByteArray();
    }

    /**
     * Generar un archivo Excel (.xlsx) con los datos en una hoja
     */
    public byte[] generarExcel(String titulo, List<String> encabezados, List<Object[]> filas) throws IOException {
        try (Workbook libro = new XSSFWorkbook(); ByteArrayOutputStream salida = new ByteArrayOutputStream()) {
            Sheet hoja = libro.createSheet(titulo);
            int[] anchos = new int[encabezados.size()];

            Font fuenteEncabezado = libro.createFont();
            fuenteEncabezado.setBold(true);
            CellStyle estiloEncabezado = libro.createCellStyle();
            estiloEncabezado.setFont(fuenteEncabezado);

            Row filaEncabezado = hoja.createRow(0);
            for (int i = 0; i < encabezados.size(); i++) {
                Cell celda = filaEncabezado.createCell(i);
                celda.setCellValue(encabezados.get(i));
                celda.setCellStyle(estiloEncabezado);
                anchos[i] = encabezados.get(i).length();
            }

            for (int i = 0; i < filas.size(); i++) {
                Row filaHoja = hoja.createRow(i + 1);
                Object[] fila = filas.get(i);
                for (int j = 0; j < fila.length; j++) {
                    Cell celda = filaHoja.createCell(j);
                    if (fila[j] instanceof Number numero) {
                        celda.setCellValue(numero.doubleValue());
                    } else {
                        celda.setCellValue(texto(fila[j]));
                    }
                    anchos[j] = Math.max(anchos[j], texto(fila[j]).length());
                }
            }

            // Se calcula a mano porque autoSizeColumn necesita fuentes del sistema (AWT),
            // que no existen en la imagen Docker alpine
            for (int i = 0; i < anchos.length; i++) {
                hoja.setColumnWidth(i, (Math.min(anchos[i], ANCHO_MAXIMO_COLUMNA) + 2) * 256);
            }

            libro.write(salida);
            return salida.toByteArray();
        }
    }

    private String texto(Object valor) {
        return valor != null ? valor.toString() : "";
    }
}
