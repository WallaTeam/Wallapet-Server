/*
 * Nombre: GeneradorPdf.java
 * Version: 2.0
 * Autor: Sergio Soro.
 * Fecha 14-5-2015
 * Descripcion: Este fichero implementa la clase que crea los pdf de intercambio
 * entre los usuarios cuando se produce una compra/venta.
 * Copyright (C) 2015 Hyena Technologies
 */

package servicios;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

public class GeneradorPdf {

	private static final Logger logger =
			Logger.getLogger(GeneradorPdf.class.getName());
	private String nombreAnuncio;
	private String tipoAnuncio;
	private String descripcionAnuncio;
	private String especieAnuncio;
	private double precioAnuncio;
	private String nombre;
	private String direccion;
	private String email;
	private int telefono;
	private boolean cliente;
	private String ruta;
	private String rutaND;
	private String rutaCabecera;

	/**
	 * Pre: parametro != null.
	 * Post: Inicializa los datos con los que se rellenara el pdf.
	 */
	public GeneradorPdf(String nombreAnuncio, String tipoAnuncio,
			String descripcionAnuncio, double precioAnuncio,
			String especieAnuncio, String nombre,
			String direccion, String email, int telefono,String ruta,
			boolean cliente, String rutaCabecera, String rutaND) {
		this.nombreAnuncio = nombreAnuncio;
		this.tipoAnuncio = tipoAnuncio;
		this.descripcionAnuncio = descripcionAnuncio;
		this.especieAnuncio = especieAnuncio;
		this.precioAnuncio = precioAnuncio;
		this.nombre = nombre;
		this.direccion = direccion;
		this.email = email;
		this.telefono = telefono;
		this.cliente = cliente;
		this.ruta = ruta;
		this.rutaCabecera = rutaCabecera;
		this.rutaND = rutaND;
	}

	/**
	 * Pre: Cierto.
	 * Post: Crea un documento pdf con los datos del anuncio y los del comprador o
	 * 		 los del vendedor.Guarda el pdf para poder enviarlo posteriormente.
	 */
	public PDDocument getPdf() {
		// Create a document and add a page to it
		try {
			if (ruta.equals("null") || ruta == ""){
				logger.log(Level.INFO, "Comienzo creación pdf sin fotos");
				PDDocument document = new PDDocument();
				PDPage page = new PDPage();
				page.setMediaBox(page.PAGE_SIZE_A4);

				PDPage PAGE_SIZE_A4 = new PDPage();

				document.addPage(page);

				BufferedImage awtImage = ImageIO
						.read(new File(rutaCabecera));
				// Resize image
				BufferedImage awtImage2;
				awtImage2 = resize(awtImage, 100, 100);
				PDXObjectImage img = new PDPixelMap(document, awtImage2);

				System.out.println(rutaND);

				// Imagen anuncio no disponible
				BufferedImage image = ImageIO.read(new File(rutaND));
				BufferedImage awtImage5;
				awtImage5 = resize(image,240,170);
				PDXObjectImage img2 = new PDPixelMap(document, awtImage5);




				// Create a new font object selecting one of the PDF base fonts
				PDFont font = PDType1Font.HELVETICA_BOLD;

				// Start a new content stream which will "hold" the to be created
				// content
				PDPageContentStream contentStream = new PDPageContentStream(
						document, page, true, true);

				contentStream.setFont(font, 6);
				contentStream.drawImage(img, 30, 700);
				contentStream.drawImage(img2, 320, 427);

				drawTable(page, contentStream);

				// Make sure that the content stream is closed
				contentStream.close();

				// Save the results and ensure that the document is properly closed

				document.save("/var/lib/tomcat7/webapps/Wallapet/" + email + ".pdf");
				document.close();
				logger.log(Level.INFO, "Termina pdf");
				return document;
			}
			else {
				logger.log(Level.INFO, "Comienzo creación pdf con fotos");
				PDDocument document = new PDDocument();
				PDPage page = new PDPage();
				page.setMediaBox(page.PAGE_SIZE_A4);

				PDPage PAGE_SIZE_A4 = new PDPage();

				document.addPage(page);

				// Image to use
				// PDXObjectImage img = new PDJpeg(document, new FileInputStream(
				// new File("datos/wallapet.png")));
				logger.log(Level.INFO, "rutaCabecera" + rutaCabecera);
				BufferedImage awtImage = ImageIO
						.read(new File(rutaCabecera));
				// Resize image
				BufferedImage awtImage2;
				awtImage2 = resize(awtImage, 100, 100);
				PDXObjectImage img = new PDPixelMap(document, awtImage2);




				logger.log(Level.INFO, "Primera imagen añadida con fotos");


				// Imagen anuncio
				logger.log(Level.INFO, "rutaImagen" + ruta);
				URL url = new URL(ruta);
				BufferedImage image = ImageIO.read(url);
				BufferedImage awtImage5;
				awtImage5 = resize(image,240,170);
				PDXObjectImage img2 = new PDPixelMap(document, awtImage5);


				logger.log(Level.INFO, "Segunda imagen añadida con fotos");

				// Create a new font object selecting one of the PDF base fonts
				PDFont font = PDType1Font.HELVETICA_BOLD;

				// Start a new content stream which will "hold" the to be created
				// content
				PDPageContentStream contentStream = new PDPageContentStream(
						document, page, true, true);

				contentStream.setFont(font, 6);
				contentStream.drawImage(img, 30, 700);
				contentStream.drawImage(img2, 320, 427);

				drawTable(page, contentStream);

				// Make sure that the content stream is closed
				contentStream.close();

				// Save the results and ensure that the document is properly closed

				document.save("/var/lib/tomcat7/webapps/Wallapet/" + email + ".pdf");
				document.close();
				logger.log(Level.INFO, "Cerramos documento");
				return document;

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception is: ");
			return null;
		}

	}


	/**
	 * Pre: img != null; newW != null; newH != null.
	 * Post: Modifica el tamaño de la imagen img por el que se indica en newW y newH
	 */
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		return dimg;
	}


	/**
	 * Pre: page != null; contentStream != null.
	 * Post: Modifica el contenido de page mediante contentStream para crear el pdf.
	 */
	private void drawTable(PDPage page, PDPageContentStream contentStream) {
		try {


			// Encabezado

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 56);
			contentStream.moveTextPositionByAmount(250, 702);
			contentStream.drawString("WALLAPET");
			contentStream.endText();
			contentStream.drawLine(30, (float) 682.5, 570, (float) 682.5);

			// TABLA1

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
			contentStream.moveTextPositionByAmount(35, 610);
			contentStream.drawString("Anuncio");
			contentStream.endText();

			contentStream.drawLine(30, (float) 600, 570, (float) 600);
			contentStream.drawLine(30, (float) 575, 300, (float) 575);
			contentStream.drawLine(30, (float) 550, 300, (float) 550);
			contentStream.drawLine(30, (float) 525, 300, (float) 525);
			contentStream.drawLine(30, (float) 500, 300, (float) 500);
			contentStream.drawLine(30, (float) 425, 570, (float) 425);
			contentStream.drawLine(30, (float) 600, 30, (float) 425);
			contentStream.drawLine(300, (float) 600, 300, (float) 425);
			contentStream.drawLine(570, (float) 600, 570, (float) 425);

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(35, 580);
			contentStream.drawString("Nombre:");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(90, 580);
			contentStream.drawString(nombreAnuncio);
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(35, 555);
			contentStream.drawString("Tipo de mascota:");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(130, 555);
			contentStream.drawString(especieAnuncio);
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(35, 530);
			contentStream.drawString("Tipo de anuncio:");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(128, 530);
			contentStream.drawString(tipoAnuncio);
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(35, 505);
			contentStream.drawString("Precio:");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(80, 505);
			contentStream.drawString(Double.toString(precioAnuncio));
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(35, 485);
			contentStream.drawString("Descripción:");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(108, 485);
			contentStream.drawString(descripcionAnuncio);
			contentStream.endText();

			// TABLA2

			if (cliente == true) {
				contentStream.beginText();
				contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
				contentStream.moveTextPositionByAmount(35, 390);
				contentStream.drawString("Datos cliente");
				contentStream.endText();
			} else {
				contentStream.beginText();
				contentStream.setFont(PDType1Font.TIMES_BOLD, 16);
				contentStream.moveTextPositionByAmount(35, 390);
				contentStream.drawString("Datos propietario");
				contentStream.endText();
			}

			contentStream.drawLine(30, (float) 375, 570, (float) 375);
			contentStream.drawLine(30, (float) 350, 570, (float) 350);
			contentStream.drawLine(30, (float) 325, 570, (float) 325);
			contentStream.drawLine(30, (float) 300, 570, (float) 300);
			contentStream.drawLine(30, (float) 275, 570, (float) 275);
			contentStream.drawLine(30, (float) 375, 30, (float) 275);
			contentStream.drawLine(570, (float) 375, 570, (float) 275);

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(35, 355);
			contentStream.drawString("Nombre:");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(90, 355);
			contentStream.drawString(nombre);
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(35, 330);
			contentStream.drawString("Dirección:");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(95, 330);
			contentStream.drawString(direccion);
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(35, 305);
			contentStream.drawString("E-mail:");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(80, 305);
			contentStream.drawString(email);
			contentStream.endText();

			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(35, 280);
			contentStream.drawString("Telefono:");
			contentStream.endText();
			
			contentStream.beginText();
			contentStream.setFont(PDType1Font.TIMES_BOLD, 12);
			contentStream.moveTextPositionByAmount(90, 280);
			contentStream.drawString(Integer.toString(telefono));
			contentStream.endText();



		} catch (IOException ioe) {
			ioe.printStackTrace();
			// Package.log.error( " drawTable :" + ioe);
			final String errormsg = "Could not drawTable ";
			// Package.log.error("In RuleThread drawTable " + errormsg, ioe);
			throw new RuntimeException(errormsg, ioe);
		} catch (Exception ex) {
			ex.printStackTrace();
			// Package.log.error( " drawTable :" + ex);
			final String errormsg = "Could not drawTable ";
			// Package.log.error("In RuleThread drawTable " + errormsg, ex);
			throw new RuntimeException(errormsg, ex);
		}
	}

}
