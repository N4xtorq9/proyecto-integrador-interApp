package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.openpdf.text.pdf.PdfReader;
import org.openpdf.text.pdf.parser.PdfTextExtractor;

class ExportServiceTest {

    private static final List<String> ENCABEZADOS = List.of("ID", "Categoría", "Stock");
    private static final List<Object[]> FILAS = List.of(
        new Object[] {1L, "Electrónica", 5},
        new Object[] {2L, null, 0});

    private final ExportService exportService = new ExportService();

    @Test
    void generaPdfConTituloYDatos() throws IOException {
        byte[] pdf = exportService.generarPdf("Categorías", ENCABEZADOS, FILAS);

        try (PdfReader lector = new PdfReader(pdf)) {
            String texto = new PdfTextExtractor(lector).getTextFromPage(1);
            assertTrue(texto.contains("Categorías"));
            assertTrue(texto.contains("Electrónica"));
        }
    }

    @Test
    void generaPdfSinFilas() {
        assertTrue(exportService.generarPdf("Categorías", ENCABEZADOS, List.of()).length > 0);
    }

    @Test
    void generaExcelConEncabezadosYDatos() throws IOException {
        byte[] excel = exportService.generarExcel("Categorías", ENCABEZADOS, FILAS);

        try (Workbook libro = new XSSFWorkbook(new ByteArrayInputStream(excel))) {
            Sheet hoja = libro.getSheet("Categorías");
            assertEquals("Categoría", hoja.getRow(0).getCell(1).getStringCellValue());

            Row primeraFila = hoja.getRow(1);
            assertEquals(1, primeraFila.getCell(0).getNumericCellValue());
            assertEquals("Electrónica", primeraFila.getCell(1).getStringCellValue());
            assertEquals("", hoja.getRow(2).getCell(1).getStringCellValue());
        }
    }
}
