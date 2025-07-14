package com.management.app.Service;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.itextpdf.signatures.*;

import com.management.app.Entity.Device;
import com.management.app.Entity.Institution;
import com.management.app.Entity.Protocols;
import com.management.app.Entity.Report;
import com.management.app.Entity.Resume;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.List;

@Service
public class PdfGenerator {

	//private String image = "./src/main/resources/static/images/Logo_StMartin.png";
	private String image = "./src/main/resources/static/images/ESEGACHETA.png";
	
	//
	private String jclogo = "./src/main/resources/static/images/Enhanced_Logo_JC.png";
	
	private String vobologo = "./src/main/resources/static/images/vobo.png";

	private String voxlogo = "./src/main/resources/static/images/vistomalo.png";

	private String vowarnlogo = "./src/main/resources/static/images/warningpng.png";

	private Cell createValueCell(String text, PdfFont font) {
	    return new Cell()
	            .add(new Paragraph((text != null && !text.trim().isEmpty()) ? text : "ND")
	            .setFont(font)
	            .setFontSize(8));
	}

	private Cell createLabelCell(String text, PdfFont font) {
	    return new Cell()
	            .add(new Paragraph(text).setFont(font).setFontSize(8))
	            .setBackgroundColor(new DeviceRgb(230, 230, 250))
	            .setTextAlignment(TextAlignment.LEFT);
	}

	private Paragraph createSectionTitle(String text, PdfFont font, DeviceRgb backgroundColor) {
	    return new Paragraph(text)
	            .setFont(font)
	            .setFontSize(9)
	            .setTextAlignment(TextAlignment.CENTER)
	            .setBackgroundColor(backgroundColor)
	            .setMarginTop(5)
	            .setMarginBottom(2)
	            .setPadding(3);
	}
	private String getSafeDate(java.sql.Date date) {
	    if (date == null) return "ND";

	    LocalDate localDate = date.toLocalDate(); // ✅ en Java 8+ funciona
	    if (localDate.getYear() <= 1111) return "ND";

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    return localDate.format(formatter);
	}

	private String calculateWarranty(Resume resume) {
	    Date installDate = resume.getFecha_instalacion();
	    Date endWarrantyDate = resume.getFecha_vencimientogarantia();

	    if (installDate == null || endWarrantyDate == null) return "ND";

	    try {
	        LocalDate install = installDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        LocalDate end = endWarrantyDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

	        int years = end.getYear() - install.getYear();
	        return (years >= 0 ? years : 0) + " AÑOS";
	    } catch (Exception e) {
	        return "ND";
	    }
	}

	private String getPeriodicidadTexto(int p) {
		String frecuencia;
		switch (p) {
		    case 0:
		    	frecuencia = "ANUAL";
			    break;
		    case 1:
		        frecuencia = "ANUAL";
		        break;
		    case 2:
		        frecuencia = "SEMESTRAL";
		        break;
		    case 3:
		        frecuencia = "CUATRIMESTRAL";
		        break;
		    default:
		        frecuencia = "TRIMESTRAL";
		        break;
		}
		return frecuencia;
	}

	private String getResponsableTexto(int estado) {
	    String estadosTR = "NR";
	    switch(estado) {
	    
	    	case 1:
	    		estadosTR = "PROPIO";
	    		break;
	    	case 2:
	    		estadosTR = "CONTRATADO";
	    		break;
	    	case 3:
	    		estadosTR = "GARANTIA";
	    		break;
	    	case 4:
	    		estadosTR = "COMODATO";
	    		break;
	    }
		
		return estadosTR;
	}

	private String getClasificacionBiomedica(Resume resume) {
		String classbiomed = "NA";
		switch (resume.getClase_biomedica()) {
			case 1:
	    		classbiomed = "DIAGNÓSTICO";
	    		break;
			case 2:
				classbiomed = "TRATAMIENTO Y MANTENIMIENTO A LA VIDA";
	    		break;
			case 3:
				classbiomed = "REHABILITACIÓN";
	    		break;
			case 4:
				classbiomed = "PREVENCIÓN";
	    		break;
			case 5:
				classbiomed = "ANÁLISIS DE LABORATORIO";
	    		break;
	    };
	    return classbiomed;
	    
	}

	public ByteArrayOutputStream getHVPDF(Device device, Resume resume) throws IOException {
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(baos);
	    PdfDocument pdf = new PdfDocument(writer);
	    Document document = new Document(pdf, PageSize.LETTER);
	    document.setMargins(5, 5, 20, 10);

	    PdfFont calibri = PdfFontFactory.createFont("C:/Users/juand/Downloads/calibri.ttf");
	    PdfFont calibriBold = PdfFontFactory.createFont("C:/Users/juand/Downloads/calibri-bold.ttf");
	    DeviceRgb mainBlue = new DeviceRgb(78, 115, 223);

	    // 1. ENCABEZADO
	    ImageData logoData = ImageDataFactory.create(image);
	    Image logo = new Image(logoData).scaleAbsolute(65, 35);
	    Table encabezado = new Table(UnitValue.createPercentArray(new float[]{2, 5, 3, 1, 1, 1, 1})).useAllAvailableWidth();
	    encabezado.addCell(new Cell(4, 2).add(logo).setBorder(Border.NO_BORDER));
	    encabezado.addCell(new Cell(1, 5).add(new Paragraph(device.getInstitution().getNombre())
	            .setFont(calibriBold).setFontSize(10).setTextAlignment(TextAlignment.CENTER)));
	    encabezado.addCell(new Cell(3, 3).add(new Paragraph("HOJA DE VIDA EQUIPO BIOMÉDICO Y EQUIPO INDUSTRIAL DE USO MÉDICO\nIII NIVEL DE ATENCIÓN")
	            .setFont(calibriBold).setFontSize(9).setTextAlignment(TextAlignment.CENTER)));
	    encabezado.addCell(new Cell().add(new Paragraph("CÓDIGO").setFont(calibriBold).setFontSize(7)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("FR-DO-01").setFont(calibriBold).setFontSize(7)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("VERSIÓN").setFont(calibriBold).setFontSize(7)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("02").setFont(calibriBold).setFontSize(7)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("FECHA DE ELABORACIÓN").setFont(calibriBold).setFontSize(7)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("mar-25").setFont(calibriBold).setFontSize(7)).setTextAlignment(TextAlignment.CENTER));
	    document.add(encabezado);

	    // 2. DATOS DEL EQUIPO
	    document.add(createSectionTitle("1. DATOS DEL EQUIPO", calibriBold, mainBlue));
	    Table datos = new Table(UnitValue.createPercentArray(new float[]{3, 6, 6})).useAllAvailableWidth();
	    datos.addCell(createLabelCell("EQUIPO", calibriBold));
	    datos.addCell(createValueCell(device.getName_device(), calibri));
	    Image foto = new Image(ImageDataFactory.create("./src/main/resources/static" + device.getFoto())).scaleToFit(80, 70);
	    datos.addCell(new Cell(6, 1).add(foto).setTextAlignment(TextAlignment.CENTER));
	    datos.addCell(createLabelCell("MARCA", calibriBold));
	    datos.addCell(createValueCell(device.getMarca(), calibri));
	    datos.addCell(createLabelCell("MODELO", calibriBold));
	    datos.addCell(createValueCell(device.getModelo(), calibri));
	    datos.addCell(createLabelCell("SERIE", calibriBold));
	    datos.addCell(createValueCell(device.getSerie(), calibri));
	    datos.addCell(createLabelCell("INVENTARIO", calibriBold));
	    datos.addCell(createValueCell(device.getInventario(), calibri));
	    datos.addCell(createLabelCell("SERVICIO", calibriBold));
	    datos.addCell(createValueCell(device.getService().getNombre_servicio(), calibri));
	    datos.addCell(createLabelCell("UBICACIÓN", calibriBold));
	    datos.addCell(createValueCell(device.getService().getUbicacion_servicio(), calibri));
	    datos.addCell(createLabelCell("DESCRIPCIÓN", calibriBold));
	    datos.addCell(new Cell(1, 2).add(new Paragraph(resume.getDescripcion()).setFont(calibri).setFontSize(8)));
	    document.add(datos);

	    // 3. IDENTIFICACIÓN
	    document.add(createSectionTitle("2. IDENTIFICACIÓN", calibriBold, mainBlue));
	    Table ident = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
	    ident.addCell(createLabelCell("INSTITUCIÓN", calibriBold));
	    ident.addCell(createValueCell(device.getInstitution().getNombre(), calibri));
	    ident.addCell(createLabelCell("DEPARTAMENTO", calibriBold));
	    ident.addCell(createValueCell(device.getInstitution().getDepartamento(), calibri));
	    ident.addCell(createLabelCell("MUNICIPIO", calibriBold));
	    ident.addCell(createValueCell(device.getInstitution().getCiudad(), calibri));
	    ident.addCell(createLabelCell("REGISTRO SANITARIO", calibriBold));
	    ident.addCell(createValueCell(device.getRegistro_invima(), calibri));
	    ident.addCell(createLabelCell("PERMISO", calibriBold));
	    ident.addCell(createValueCell(resume.getContrato(), calibri));
	    ident.addCell(createLabelCell("CÓDIGO PRESTADOR", calibriBold));
	    ident.addCell(createValueCell(device.getInstitution().getCodigo_prestador(), calibri));
	    document.add(ident);

	    // 4. REGISTRO HISTÓRICO
	    document.add(createSectionTitle("3. REGISTRO HISTÓRICO", calibriBold, mainBlue));
	    Table hist = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
	    hist.addCell(createLabelCell("FECHA DE ADQUISICIÓN", calibriBold));
	    hist.addCell(createValueCell(getSafeDate(resume.getFecha_compra()), calibri));
	    hist.addCell(createLabelCell("FECHA DE INSTALACIÓN", calibriBold));
	    hist.addCell(createValueCell(getSafeDate(resume.getFecha_instalacion()), calibri));
	    hist.addCell(createLabelCell("FECHA DE INICIO OPERACIÓN", calibriBold));
	    hist.addCell(createValueCell(getSafeDate(resume.getFecha_iniciooperacion()), calibri));
	    hist.addCell(createLabelCell("FABRICANTE", calibriBold));
	    hist.addCell(createValueCell(resume.getFabricante(), calibri));
	    hist.addCell(createLabelCell("FECHA FABRICACIÓN", calibriBold));
	    hist.addCell(createValueCell(getSafeDate(resume.getFecha_fabricacion()), calibri));
	    hist.addCell(createLabelCell("VENCIMIENTO GARANTÍA", calibriBold));
	    hist.addCell(createValueCell(getSafeDate(resume.getFecha_vencimientogarantia()), calibri));
	    hist.addCell(createLabelCell("TIEMPO GARANTÍA", calibriBold));
	    hist.addCell(createValueCell(calculateWarranty(resume), calibri));
	    hist.addCell(createLabelCell("COSTO", calibriBold));
	    hist.addCell(createValueCell(resume.getCosto_compra(), calibri));
	    hist.addCell(createLabelCell("PROVEEDOR", calibriBold));
	    hist.addCell(createValueCell(resume.getProveedor(), calibri));
	    hist.addCell(createLabelCell("REPRESENTANTE", calibriBold));
	    hist.addCell(createValueCell(resume.getRepresentante(), calibri));
	    hist.addCell(createLabelCell("CONTACTO", calibriBold));
	    hist.addCell(createValueCell(resume.getTelefonoproveedor(), calibri));
	    document.add(hist);

        /////////////////
	    // 5. REGISTRO TÉCNICO
	    document.add(createSectionTitle("4. REGISTRO TÉCNICO", calibriBold, mainBlue));
	    Table tecn = new Table(UnitValue.createPercentArray(4)).useAllAvailableWidth();
	    tecn.addCell(createLabelCell("TENSIÓN MÁXIMA", calibriBold));
	    tecn.addCell(createValueCell(resume.getVmaxoperacion(), calibri));
	    tecn.addCell(createLabelCell("TENSIÓN MÍNIMA", calibriBold));
	    tecn.addCell(createValueCell(resume.getVminoperacion(), calibri));
	    tecn.addCell(createLabelCell("CORRIENTE MÁXIMA", calibriBold));
	    tecn.addCell(createValueCell(resume.getImaxoperacion(), calibri));
	    tecn.addCell(createLabelCell("CORRIENTE MÍNIMA", calibriBold));
	    tecn.addCell(createValueCell(resume.getIminoperacion(), calibri));
	    tecn.addCell(createLabelCell("POTENCIA", calibriBold));
	    tecn.addCell(createValueCell(resume.getWconsumida(), calibri));
	    tecn.addCell(createLabelCell("FRECUENCIA", calibriBold));
	    tecn.addCell(createValueCell(resume.getFrecuencia(), calibri));
	    tecn.addCell(createLabelCell("PESO", calibriBold));
	    tecn.addCell(createValueCell(resume.getPeso(), calibri));
	    tecn.addCell(createLabelCell("VELOCIDAD", calibriBold));
	    tecn.addCell(createValueCell(resume.getVelocidad(), calibri));
	    tecn.addCell(createLabelCell("TEMPERATURA", calibriBold));
	    tecn.addCell(createValueCell(resume.getTemperatura(), calibri));
	    tecn.addCell(createLabelCell("PRESIÓN", calibriBold));
	    tecn.addCell(createValueCell(resume.getPresion(), calibri));
	    tecn.addCell(createLabelCell("CAPACIDAD", calibriBold));
	    tecn.addCell(createValueCell(resume.getCapacidad(), calibri));
	    tecn.addCell(createLabelCell("OTROS", calibriBold));
	    tecn.addCell(new Cell(1, 3).add(new Paragraph(resume.getOtrosdatostecnicos()).setFont(calibri).setFontSize(8)));
	    document.add(tecn);

	    // 6. REGISTRO DE APOYO TÉCNICO (simplificado)
	    document.add(createSectionTitle("5. REGISTRO DE APOYO TÉCNICO", calibriBold, mainBlue));
	    Table apoyo = new Table(2).useAllAvailableWidth();
	    apoyo.addCell(createLabelCell("MANUAL OPERACIÓN", calibriBold));
	    apoyo.addCell(createValueCell("X", calibri)); // simulado
	    apoyo.addCell(createLabelCell("MANUAL SERVICIO", calibriBold));
	    apoyo.addCell(createValueCell("X", calibri)); // simulado
	    apoyo.addCell(createLabelCell("CLASIFICACIÓN BIOMÉDICA", calibriBold));
	    apoyo.addCell(createValueCell(getClasificacionBiomedica(resume), calibri));
	    document.add(apoyo);

	    // 7. MANTENIMIENTO
	    document.add(createSectionTitle("6. MANTENIMIENTO", calibriBold, mainBlue));
	    Table mtto = new Table(2).useAllAvailableWidth();
	    mtto.addCell(createLabelCell("PERIODICIDAD MANTENIMIENTO", calibriBold));
	    mtto.addCell(createValueCell(getPeriodicidadTexto(device.getPeriodicidad()), calibri));
	    mtto.addCell(createLabelCell("RESPONSABLE MANTENIMIENTO", calibriBold));
	    mtto.addCell(createValueCell(getResponsableTexto(device.getEstado()), calibri));
	    mtto.addCell(createLabelCell("¿REQUIERE CALIBRACIÓN/VALIDACIÓN?", calibriBold));
	    String caltext = (resume.isRequierecalibracion() || resume.isCalval()) ? "SI" : "NO";
	    mtto.addCell(createValueCell(caltext, calibri));
	    mtto.addCell(createLabelCell("EMPRESA CALIBRACIÓN", calibriBold));
	    mtto.addCell(createValueCell("-", calibri)); // Puedes personalizar esto
	    document.add(mtto);

	    // 8. ACCESORIOS
	    document.add(createSectionTitle("7. COMPONENTES Y/O ACCESORIOS", calibriBold, mainBlue));
	    Table accesorios = new Table(1).useAllAvailableWidth();
	    accesorios.addCell(createValueCell(resume.getAccesorio1(), calibri));
	    accesorios.addCell(createValueCell(resume.getAccesorio2(), calibri));
	    accesorios.addCell(createValueCell(resume.getAccesorio3(), calibri));
	    accesorios.addCell(createValueCell(resume.getAccesorio4(), calibri));
	    document.add(accesorios);

	    // 9. OBSERVACIONES
	    document.add(createSectionTitle("8. OBSERVACIONES", calibriBold, mainBlue));
	    Table observaciones = new Table(1).useAllAvailableWidth();
	    observaciones.addCell(new Cell().add(new Paragraph(resume.getObservacion()).setFont(calibri).setFontSize(8)).setMinHeight(30));
	    observaciones.addCell(new Cell().add(new Paragraph("ND: NO DISPONIBLE NR: NO REGISTRA NE: NO ESPECIFICA NA: NO APLICA").setFont(calibri).setFontSize(7)).setBorder(Border.NO_BORDER));
	    document.add(observaciones);

	    document.close();
	    return baos;
	}
	public ByteArrayOutputStream getInventoryPDF(List<Device> devices, Institution institution) throws IOException {
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(bos);
	    PdfDocument pdfDoc = new PdfDocument(writer);
	    Document document = new Document(pdfDoc, PageSize.LETTER.rotate());
	    document.setMargins(20, 20, 20, 20);

	    // Fonts
	    PdfFont calibri = PdfFontFactory.createFont("C:/Users/juand/Downloads/calibri.ttf");
	    PdfFont bold = PdfFontFactory.createFont("C:/Users/juand/Downloads/calibri-bold.ttf");
	    PdfFont normal = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
	  
	    // Logo
	    ImageData logoData = ImageDataFactory.create("path/to/logo.png");
	    Image logo = new Image(logoData).scaleAbsolute(65, 35);

	    Table encabezado = new Table(UnitValue.createPercentArray(new float[]{2, 5, 2, 2, 2, 2, 2})).useAllAvailableWidth();
	    encabezado.addCell(new Cell(4, 2).add(logo).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
	    encabezado.addCell(new Cell().add(new Paragraph(institution.getNombre()).setFont(bold)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE).setHeight(40).setBorderBottom(null).setBorderTop(null).setBorderLeft(null).setBorderRight(null).setBorder(null).setBorderTop(null).setBorderLeft(null).setBorderRight(null).setBorderBottom(null));
	    encabezado.addCell(new Cell(3, 3).add(new Paragraph("PLAN DE MANTENIMIENTO PREVENTIVO BIOMÉDICO").setFont(bold)).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE));
	    encabezado.addCell(new Cell().add(new Paragraph("CÓDIGO").setFont(bold)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("FO-DO-02").setFont(bold)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("VERSION").setFont(bold)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("01").setFont(bold)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("FECHA DE ELABORACIÓN").setFont(bold)).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("mar-25").setFont(bold)).setTextAlignment(TextAlignment.CENTER));

	    encabezado.setMarginBottom(10);
	    document.add(encabezado);

	    // Tabla cabecera info institución
	    Table infoTable = new Table(UnitValue.createPercentArray(new float[]{3, 5, 3, 5, 3, 3})).useAllAvailableWidth();
	    infoTable.addCell(new Cell().add(new Paragraph("Departamento:").setFont(bold)));
	    infoTable.addCell(new Cell().add(new Paragraph(institution.getDepartamento()).setFont(calibri)));
	    infoTable.addCell(new Cell().add(new Paragraph("Municipio:").setFont(bold)));
	    infoTable.addCell(new Cell().add(new Paragraph(institution.getCiudad()).setFont(calibri)));
	    infoTable.addCell(new Cell().add(new Paragraph("Año:").setFont(bold)));
	    infoTable.addCell(new Cell().add(new Paragraph("2025").setFont(calibri)));
	    infoTable.setMarginBottom(10);
	    document.add(infoTable);

	    // Tabla de inventario
	    Table tabla = new Table(UnitValue.createPercentArray(new float[]{1, 4, 3, 2, 2, 2, 3, 4, 1})).useAllAvailableWidth();
	    String[] headers = {"N°", "Descripción", "Localización", "Inventario", "Frecuencia", "Fecha (Mes)", "Contratado", "Actividad", "N°"};
	    for (String h : headers) {
	        tabla.addHeaderCell(new Cell().add(new Paragraph(h).setFont(bold)).setTextAlignment(TextAlignment.CENTER));
	    }

	    int totalActividades = 0;
	    for (int i = 0; i < devices.size(); i++) {
	        Device d = devices.get(i);
	        tabla.addCell(new Cell().add(new Paragraph(String.valueOf(i + 1)).setFont(calibri)).setTextAlignment(TextAlignment.CENTER));

	        String descripcion = d.getName_device() + "\n" + d.getMarca() + "\n" + d.getModelo() + "\n" + d.getSerie();
	        tabla.addCell(new Cell().add(new Paragraph(descripcion).setFont(calibri)).setTextAlignment(TextAlignment.CENTER));

	        String localizacion = d.getService().getNombre_servicio() + "\n" + d.getService().getUbicacion_servicio();
	        tabla.addCell(new Cell().add(new Paragraph(localizacion).setFont(calibri)).setTextAlignment(TextAlignment.CENTER));

	        tabla.addCell(new Cell().add(new Paragraph(d.getInventario()).setFont(calibri)).setTextAlignment(TextAlignment.CENTER));

	        String frecuencia;
	        int periodicidad = d.getPeriodicidad();
	        if (periodicidad == 0 || periodicidad == 1) {
	            frecuencia = "ANUAL";
	        } else if (periodicidad == 2) {
	            frecuencia = "SEMESTRAL";
	        } else if (periodicidad == 3) {
	            frecuencia = "CUATRIMESTRAL";
	        } else {
	            frecuencia = "TRIMESTRAL";
	        }
	        tabla.addCell(new Cell().add(new Paragraph(frecuencia).setFont(calibri)).setTextAlignment(TextAlignment.CENTER));

	        String meses = String.join("\n", Arrays.asList(d.getMeses_mantenimiento().split(",")));
	        tabla.addCell(new Cell().add(new Paragraph(meses).setFont(calibri)).setTextAlignment(TextAlignment.CENTER));

	        tabla.addCell(new Cell().add(new Paragraph("Contratado").setFont(calibri)).setTextAlignment(TextAlignment.CENTER));

	        tabla.addCell(new Cell().add(new Paragraph(d.getType().getActividad()).setFont(calibri)).setTextAlignment(TextAlignment.CENTER));

	        tabla.addCell(new Cell().add(new Paragraph(String.valueOf(d.getPeriodicidad())).setFont(calibri)).setTextAlignment(TextAlignment.CENTER));
	        totalActividades += d.getPeriodicidad();
	    }

	    tabla.addCell(new Cell(1, 8).add(new Paragraph("TOTAL ACTIVIDADES EQUIPOS PROPIOS").setFont(calibri)));
	    tabla.addCell(new Cell().add(new Paragraph(String.valueOf(totalActividades)).setFont(calibri)).setTextAlignment(TextAlignment.CENTER));

	    tabla.setMarginBottom(10);
	    document.add(tabla);

	    document.close();
	    return bos;
	}

	public ByteArrayOutputStream getReportPDFFinal(Report report, Institution institution, List<Protocols> protocolos, String firmaEjecutorBase64, String firmaReceptorBase64) throws Exception {
	    ByteArrayOutputStream unsignedPdf = new ByteArrayOutputStream();
	    PdfWriter writer = new PdfWriter(unsignedPdf);
	    PdfDocument pdfDoc = new PdfDocument(writer);
	    Document document = new Document(pdfDoc, PageSize.LETTER);
	    document.setMargins(30, 30, 30, 30);

	    // COLORES Y FUENTES
	    DeviceRgb azul = new DeviceRgb(179, 199, 228);
	    DeviceRgb gris = new DeviceRgb(199, 200, 202);
	    PdfFont calibri = PdfFontFactory.createFont("C:/Users/juand/Downloads/calibri.ttf", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
	    PdfFont calibriBold = PdfFontFactory.createFont("C:/Users/juand/Downloads/calibri-bold.ttf", PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

	    // LOGO
	    Image logo = new Image(ImageDataFactory.create("./src/main/resources/static/images/logoJC.png")).scaleAbsolute(60, 60);

	    // ENCABEZADO
	    Table encabezado = new Table(UnitValue.createPercentArray(new float[]{1, 2, 1})).useAllAvailableWidth();
	    encabezado.addCell(new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
	    encabezado.addCell(new Cell().add(new Paragraph("ORDEN DE TRABAJO").setFont(calibriBold).setFontSize(20)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
	    encabezado.addCell(new Cell().add(new Paragraph("Versión 1\nFMB-001").setFont(calibri).setFontSize(10)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
	    document.add(encabezado);
	    document.add(new Paragraph("\n"));

	    // INFORMACIÓN OTT
	    Table info = crearSeccion("INFORMACIÓN DE LA ORDEN DE TRABAJO", azul);
	    info.addCell(celda("N° Orden de trabajo", calibriBold));
	    info.addCell(celda("OTT-" + report.getId_Report(), calibri, 2));
	    info.addCell(celda("Institución", calibriBold));
	    info.addCell(celda(institution.getNombre(), calibri, 2));
	    info.addCell(celda("Fecha", calibriBold));
	    info.addCell(celda(String.valueOf(report.getFecha()), calibri, 2));
	    info.addCell(celda("Dirección", calibriBold));
	    info.addCell(celda(institution.getDireccion(), calibri, 5));
	    document.add(info);

	    // INFORMACIÓN EQUIPO
	    Table equipo = crearSeccion("INFORMACIÓN DEL EQUIPO", azul);
	    equipo.addCell(celda("Equipo", calibriBold));
	    equipo.addCell(celda(report.getDevice().getName_device(), calibri));
	    equipo.addCell(celda("Marca", calibriBold));
	    equipo.addCell(celda(report.getDevice().getMarca(), calibri));
	    equipo.addCell(celda("Modelo", calibriBold));
	    equipo.addCell(celda(report.getDevice().getModelo(), calibri));
	    equipo.addCell(celda("Serie", calibriBold));
	    equipo.addCell(celda(report.getDevice().getSerie(), calibri));
	    equipo.addCell(celda("Código interno", calibriBold));
	    equipo.addCell(celda(report.getDevice().getInventario(), calibri));
	    equipo.addCell(celda("Ubicación", calibriBold));
	    equipo.addCell(celda(report.getServicio() + " " + report.getUbicacion(), calibri));
	    document.add(equipo);

	    // TRABAJO REALIZADO
	    Table trabajo = crearSeccion("DESCRIPCIÓN DEL TRABAJO REALIZADO", azul);
	    trabajo.addCell(celda("Motivo", calibriBold));
	    trabajo.addCell(celda(report.getMotivo(), calibri, 5));
	    trabajo.addCell(celda("Resumen", calibriBold));
	    trabajo.addCell(celda(report.getTrabajo_realizado(), calibri, 5));
	    trabajo.addCell(celda("Recomendaciones", calibriBold));
	    trabajo.addCell(celda(report.getObservaciones(), calibri, 5));
	    document.add(trabajo);

	    // PROTOCOLOS (Solo si tipo 3)
	    if(report.getTipo_mantenimiento() == 3) {
	        Table protos = crearSeccion("PROTOCOLOS DE MANTENIMIENTO", azul);
	        protos.addCell(encabezadoCelda("Actividad", calibriBold, 4));
	        protos.addCell(encabezadoCelda("Observación", calibriBold));
	        protos.addCell(encabezadoCelda("Aprobación", calibriBold));

	        List<String> obs = Arrays.asList(report.getObservacionPruebas().split(","));
	        List<String> aprobadas = Arrays.asList(report.getRutapruebas().split(";")[1].split(","));

	        for (int i = 0; i < protocolos.size(); i++) {
	            Protocols p = protocolos.get(i);
	            protos.addCell(celda(p.getPaso(), calibri, 4));
	            protos.addCell(celda(obs.get(i), calibri));

	            boolean aprobado = aprobadas.contains(String.valueOf(p.getId_Protocols()));
	            Image check = new Image(ImageDataFactory.create(aprobado ? "./src/main/resources/static/img/check.png" : "./src/main/resources/static/img/cross.png")).scaleAbsolute(10, 10);
	            protos.addCell(new Cell().add(check).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
	        }
	        document.add(protos);
	    }

	    // REPUESTOS
	    Table repuestos = crearSeccion("REPUESTOS INSTALADOS", azul);
	    repuestos.addCell(encabezadoCelda("Descripción", calibriBold, 5));
	    repuestos.addCell(encabezadoCelda("Cantidad", calibriBold));
	    repuestos.addCell(celda(report.getRepuesto_cambiado(), calibri, 5));
	    repuestos.addCell(celda(report.getCantidad_repuesto(), calibri));
	    document.add(repuestos);

	    // DETALLES DE INTERVENCIÓN
	    Table intervencion = crearSeccion("DETALLES DE LA INTERVENCIÓN", azul);
	    intervencion.addCell(celda("Fecha", calibriBold));
	    intervencion.addCell(celda(String.valueOf(report.getFecha()), calibri));
	    intervencion.addCell(celda("Hora inicio", calibriBold));
	    intervencion.addCell(celda(String.valueOf(report.getHora_inicio()), calibri));
	    intervencion.addCell(celda("Hora final", calibriBold));
	    intervencion.addCell(celda(String.valueOf(report.getHora_terminacion()), calibri));
	    intervencion.addCell(celda("Total horas", calibriBold));
	    intervencion.addCell(celda(String.valueOf(report.getTotal_horas()), calibri));
	    document.add(intervencion);

	    // CONFORMIDAD + FIRMAS
	    Table conformidad = crearSeccion("CONFORMIDAD", azul);
	    conformidad.addCell(encabezadoCelda("Responsable mantenimiento", calibriBold, 3));
	    conformidad.addCell(encabezadoCelda("Recibe", calibriBold, 3));
	    conformidad.addCell(firmaCell(firmaEjecutorBase64, report.getResponsable(), calibri, 3));
	    conformidad.addCell(firmaCell(firmaReceptorBase64, report.getAutor_recibido(), calibri, 3));
	    document.add(conformidad);

	    document.close();

	    ByteArrayOutputStream signedPdf = new ByteArrayOutputStream();
	    firmarDigitalmente(
	        new ByteArrayInputStream(unsignedPdf.toByteArray()),
	        signedPdf,
	        "./src/main/resources/static/certificados/certificado_final.p12",
	        "jdcgnr"
	    );
	    return signedPdf;
	}

	private Table crearSeccion(String titulo, DeviceRgb color) {
	    Table t = new Table(UnitValue.createPercentArray(6)).useAllAvailableWidth();
	    t.addCell(new Cell(1, 6).add(new Paragraph(titulo).setFontSize(10).setBold()).setBackgroundColor(color).setBorder(Border.NO_BORDER));
	    return t;
	}

	private Cell celda(String texto, PdfFont font) {
	    return new Cell().add(new Paragraph(texto).setFont(font).setFontSize(9)).setBorder(Border.NO_BORDER);
	}

	private Cell celda(String texto, PdfFont font, int colspan) {
	    return new Cell(1, colspan).add(new Paragraph(texto).setFont(font).setFontSize(9)).setBorder(Border.NO_BORDER);
	}

	private Cell encabezadoCelda(String texto, PdfFont font) {
	    return new Cell().add(new Paragraph(texto).setFont(font).setFontSize(9)).setBackgroundColor(new DeviceRgb(199,200,202)).setBorder(Border.NO_BORDER);
	}

	private Cell encabezadoCelda(String texto, PdfFont font, int colspan) {
	    return new Cell(1, colspan).add(new Paragraph(texto).setFont(font).setFontSize(9)).setBackgroundColor(new DeviceRgb(199,200,202)).setBorder(Border.NO_BORDER);
	}

	private Cell firmaCell(String base64Image, String nombre, PdfFont font, int colspan) {
	    Cell cell = new Cell(2, colspan).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER);
	    try {
	        if (base64Image != null && !base64Image.trim().isEmpty()) {
	            String[] parts = base64Image.split(",");
	            String base64 = parts.length == 2 ? parts[1] : parts[0];
	            byte[] imageBytes = Base64.getDecoder().decode(base64);
	            ImageData imageData = ImageDataFactory.create(imageBytes);
	            Image firma = new Image(imageData).scaleAbsolute(80, 40);
	            cell.add(firma);
	        } else {
	            cell.add(new Paragraph("Firma no disponible").setFont(font).setFontSize(8));
	        }
	    } catch (Exception e) {
	        cell.add(new Paragraph("Error al cargar firma").setFont(font).setFontSize(8));
	        System.err.println("Error al insertar firma: " + e.getMessage());
	    }

	    cell.add(new Paragraph(nombre).setFont(font).setFontSize(10));
	    return cell;
	}



	private void firmarDigitalmente(InputStream srcPdf, OutputStream destPdf, String p12Path, String p12Password) throws Exception {
	    Security.addProvider(new BouncyCastleProvider());
	    KeyStore ks = KeyStore.getInstance("PKCS12");
	    ks.load(new FileInputStream(p12Path), p12Password.toCharArray());
	    String alias = ks.aliases().nextElement();
	    PrivateKey pk = (PrivateKey) ks.getKey(alias, p12Password.toCharArray());
	    Certificate[] chain = ks.getCertificateChain(alias);
	    PdfSigner signer = new PdfSigner(new PdfReader(srcPdf), destPdf, new StampingProperties().useAppendMode());
	    signer.getSignatureAppearance().setReason("Firma digital").setLocation("Colombia").setReuseAppearance(false).setPageRect(new Rectangle(0,0,0,0)).setPageNumber(1);
	    signer.setFieldName("firma_digital_final");
	    IExternalSignature pks = new PrivateKeySignature(pk, DigestAlgorithms.SHA256, BouncyCastleProvider.PROVIDER_NAME);
	    IExternalDigest digest = new BouncyCastleDigest();
	    signer.signDetached(digest, pks, chain, null, null, null, 0, PdfSigner.CryptoStandard.CADES);
	}

	
}

