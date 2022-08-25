package com.ericsson.util; 

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.AbstractList;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

public class GeneratePDF {

	private Document doc = null;
	private int nameImage = 1;
	private int nameImageToText = 1;
	private String pdfName;
	private Color darkGreen;
	private String pdfPath;

	public GeneratePDF(String path, String name) {
		darkGreen = new Color(0, 128, 0);
		pdfPath = path;
		pdfName = name;
		init();
	}

	private void init() {
		try {
			File diretorio = new File(pdfPath);
			if (!diretorio.exists()) {
				diretorio.mkdirs();
				doc = new Document();
				System.out.println("PDF PATH" + pdfPath);
				System.out.println("PDF NAME" + pdfName);
				PdfWriter.getInstance(doc, new FileOutputStream(pdfPath
						+ pdfName));
			} else {
				doc = new Document();
				PdfWriter.getInstance(doc, new FileOutputStream(pdfPath
						+ pdfName));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		doc.open();
	}

	public void creatDir(String path) {
		this.pdfPath = path;
	}

	public void closePDF() {
		try {
			doc.close();
			nameImage = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addImagePDF(String stringImage, String name) {
		String namePicture = name + nameImage + ".png";
		ImageUtil.getInstance().saveImage(
				ImageUtil.getInstance().convertStringToImage(stringImage),
				namePicture);
		try {
			Font f1 = new Font(Font.TIMES_ROMAN, 11, Font.BOLD, Color.black);
			Paragraph info2 = new Paragraph("     ", f1);
			doc.add(info2);
//			if (nameImage == 1) {
//				doc.add(Chunk.NEXTPAGE);
//			}
			Image img2;
			img2 = Image.getInstance(namePicture);
			img2.setAlignment(Element.ALIGN_CENTER);
			img2.scalePercent(30);
			doc.add(img2);

//			Paragraph info3 = new Paragraph("Picture" + nameImage, f1);
//			info3.setAlignment(Element.ALIGN_CENTER);
//			doc.add(info3);
			if(nameImage==2){
			doc.add(Chunk.NEXTPAGE);
			nameImage=0;
			}
			nameImage++;
			new File(namePicture).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addTextsPDF(AbstractList<String> steps){
		for (int i=0;i<steps.size();i++) {
			addText("Step "+ (i+1) +": " + steps.get(i));
		}
	}
	
	public void addText(String text) {
		try {
			Font f1;
			Paragraph info;
			if (text.contains("CRUD")) {
				f1 = new Font(Font.TIMES_ROMAN, 11, Font.BOLD, Color.BLACK);
				info = new Paragraph(text, f1);
				info.setAlignment(Element.ALIGN_CENTER);
			} else if (text.contains("(Picture")) {
				text = text + nameImageToText + ")";
				nameImageToText++;
				if (text.contains("ERRO")) {
					f1 = new Font(Font.TIMES_ROMAN, 9, Font.BOLD, Color.RED);
					info = new Paragraph(text, f1);
				} else {
					f1 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, Color.BLACK);
					info = new Paragraph(text, f1);
				}
				info.setAlignment(Element.ALIGN_JUSTIFIED);
			} else if (text.contains("PICTUR") || text.contains("STEP")) {
				f1 = new Font(Font.TIMES_ROMAN, 11, Font.BOLD, Color.BLACK);
				info = new Paragraph(text, f1);
				info.setAlignment(Element.ALIGN_JUSTIFIED);
			} else if (text.contains("Failed")) {
				f1 = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.RED);
				info = new Paragraph(text, f1);
				info.setAlignment(Element.ALIGN_JUSTIFIED);
			} else if (text.contains("successfully") || text.contains("Passed")) {
				f1 = new Font(Font.TIMES_ROMAN, 12, Font.BOLD, darkGreen);
				info = new Paragraph(text, f1);
				info.setAlignment(Element.ALIGN_JUSTIFIED);
			} else {
				f1 = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black);
				info = new Paragraph(text, f1);
				info.setAlignment(Element.ALIGN_JUSTIFIED);
			}
			Paragraph info2 = new Paragraph("     ", f1);
			doc.add(info2);
			doc.add(info);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public void addTitle(String text) throws MalformedURLException, IOException{
		try {
			Image img = Image.getInstance("src/test/resources/dama.png");
			img.setAlignment(Element.ALIGN_CENTER);
			img.scalePercent(30);
			doc.add(img);
			Font font = new Font(Font.TIMES_ROMAN, 14, Font.BOLD, Color.BLACK);
			Paragraph info = new Paragraph(text, font);
			info.setAlignment(Element.ALIGN_JUSTIFIED);
			doc.add(info);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public String getArchivePath() {
		return pdfName;
	}
}
