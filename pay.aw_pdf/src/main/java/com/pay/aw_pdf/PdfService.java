package com.pay.aw_pdf;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfService {

	public void exportPdf(HttpServletResponse response) throws Exception {

		Rectangle pagesize = new Rectangle(700, 1000);
		Document doc = new Document(pagesize);
		PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());

		// Define a custom header using PdfPageEventHelper
		PdfHeaderFooter instance = new PdfHeaderFooter();
		writer.setPageEvent(instance);

		doc.open();

		// for adding image
//		Image image = Image.getInstance("images/images.jfif");
//		image.scaleAbsolute(500.0f, 80.0f);
//		image.setAbsolutePosition(50, PageSize.A4.getHeight() - 100); // Adjust position as needed
//		doc.add(image);

		// title
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);// to choose font
		fontTitle.setSize(22);

		Paragraph paragraph = new Paragraph("Title of the page.", fontTitle);
		paragraph.setAlignment(paragraph.ALIGN_CENTER);
//        doc.add(paragraph);

		// paragraph
		Font fontPara = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE);
		fontPara.setSize(17);

		Paragraph newPara = new Paragraph("PARAGRAPH OF PAGE", fontPara);// to choose font
		newPara.setAlignment(newPara.ALIGN_LEFT);
//		doc.add(newPara);

//		 Create a table with 1 rows and 1 column
		PdfPTable t2 = new PdfPTable(1);
		t2.setWidthPercentage(100);

		PdfPCell cell1 = new PdfPCell();
		cell1.setBorder(Rectangle.LEFT | Rectangle.RIGHT);

		// Get the current date and format it
		Date currentDate = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = dateFormat.format(currentDate);

		Font t2font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
		Font t2font2 = FontFactory.getFont(FontFactory.HELVETICA, 14);

		Chunk c1 = new Chunk("Booking Date :", t2font1);
		Chunk c2 = new Chunk(formattedDate, t2font2);

		Phrase phrase = new Phrase();
		phrase.add(c1);
		phrase.add(c2);

		cell1.addElement(phrase);
		cell1.setPadding(8f);

//        for cell
		t2.addCell(cell1);

		// Add the table to the document
		doc.add(t2);

		// Create a second table with 1 row and 2 columns
		PdfPTable t3 = new PdfPTable(2);
		t3.setWidthPercentage(100);

		PdfPTable nestedTable = new PdfPTable(1);
		PdfPCell nestedCell = new PdfPCell();
//        nestedCell.setBorderWidth(0); // No border for the nested cell

		// Define fonts for different content
		Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);

		// Add content to the nested table
		nestedCell.setPadding(15f);
		nestedCell.addElement(new Phrase("Order ID :", boldFont));
		nestedCell.addElement(new Phrase("35366\n ", normalFont));
		nestedCell.addElement(new Phrase("Ticket ID :", boldFont));
		nestedCell.addElement(new Phrase("870\n ", normalFont));
		nestedCell.addElement(new Phrase("Ticket Class :", boldFont));
		nestedCell.addElement(new Phrase("SEH - Ultra Early Bird", normalFont));
		// to add border to left cell
		nestedCell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP);

		// Add nested table to the nested cell
		nestedCell.addElement(nestedTable);

		t3.addCell(nestedCell);

		// Add an image to the right column
		Image image = Image.getInstance("images/qr-code-bc94057f452f4806af70fd34540f72ad.png");
		image.scaleAbsolute(150.00f, 150.00f);
//		t3.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

		// Create a cell for the image
		PdfPCell imageCell = new PdfPCell(image);
		imageCell.setPadding(15f);
		imageCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		imageCell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
//		imageCell.setBorder(PdfPCell.NO_BORDER); // Remove cell border

		// Add the cell to the table
		t3.addCell(imageCell);

		// Add the second table to the document
		doc.add(t3);

		// Create a third table with 1 row and 2 columns
		PdfPTable t4 = new PdfPTable(2);
		t4.setWidthPercentage(100);

		// create nested table inside t4
		PdfPTable nesTable1 = new PdfPTable(1);
		PdfPCell nesCell = new PdfPCell();

		// Add content to the nested table
		nesCell.setPadding(10f);
		// to add border to left cell
		nesCell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP);
		nesCell.addElement(new Phrase("Event Details :", boldFont));
		nesCell.addElement(new Phrase("SEH - Pay.aw \n ", normalFont));

		// create nested table to add cal image to nesTable1
		float columnWidths[] = { 30f, 130f };
		PdfPTable nesTable2 = new PdfPTable(columnWidths);

		PdfPCell nesCellCal = new PdfPCell();
		// Add content to the 2ndnested table
		Image cal = Image.getInstance("images/download11.png");
		cal.scaleAbsolute(30.00f, 30.00f);
		nesCellCal.setBorder(0);
		nesCellCal.addElement(cal);

		nesTable2.addCell(nesCellCal);

		PdfPCell dateCell = new PdfPCell();
		dateCell.addElement(new Phrase("Apr 05 2024", normalFont));
		dateCell.setBorder(0);

		nesTable2.addCell(dateCell);

		nesCell.addElement(nesTable2);

		// create nested table to add clock image to nesTable1
		PdfPTable nesTable3 = new PdfPTable(columnWidths);

		PdfPCell nesCellClock = new PdfPCell();
		Image clock = Image.getInstance("images/clock1.png");
		clock.scaleAbsolute(30.00f, 20.00f);
		nesCellClock.setBorder(0);
		nesCellClock.addElement(clock);

		nesTable3.addCell(nesCellClock);

		PdfPCell eTime = new PdfPCell();
		eTime.addElement(new Phrase("10.00 PM", normalFont));
		eTime.setBorder(0);

		nesTable3.addCell(eTime);

		nesCell.addElement(nesTable3);

		// create nested table to add location image to nesTable1
		PdfPTable nesTable4 = new PdfPTable(columnWidths);

		PdfPCell nesCellLoc = new PdfPCell();
		Image location = Image.getInstance("images/location2.png");
		location.scaleAbsolute(30.00f, 30.00f);
		nesCellLoc.setBorder(0);
		nesCellLoc.addElement(location);

		nesTable4.addCell(nesCellLoc);

		PdfPCell venue = new PdfPCell();
		venue.addElement(new Phrase("The Vanue Aruba, \n Weststraat z/n", normalFont));
		venue.setBorder(0);

		nesTable4.addCell(venue);

		nesCell.addElement(nesTable4);

		// Add nested table to the nested cell
		nesCell.addElement(nesTable1);

		t4.addCell(nesCell);

		// Create a cell for the image
		PdfPCell imageCell1 = new PdfPCell();
		// Add an image to the right column
		Image img = Image.getInstance("images/CampaignPage-Banner-Payaw-Personal.jpg");
		img.scaleAbsolute(210f, 340f);
		imageCell1.addElement(img);
		imageCell1.setPadding(45f);
		imageCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
		imageCell1.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);

		// Add the cell to the table
		t4.addCell(imageCell1);

		doc.add(t4);

		PdfPTable t5 = new PdfPTable(1);
		t5.setWidthPercentage(100);
		PdfPCell t5cell = new PdfPCell();
		t5cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);

		PdfPTable nesTable5 = new PdfPTable(1);
		nesTable5.setWidthPercentage(100);
		PdfPCell nestedCellt5 = new PdfPCell();
		nestedCellt5.addElement(new Phrase("Payment Summary :", boldFont));
		nestedCellt5.addElement(new Phrase("Ticket Subtotal", normalFont));
		nestedCellt5.setBorder(0);
		nesTable5.addCell(nestedCellt5);

		t5cell.addElement(nesTable5);

		float columnWidth4t5[] = { 150f, 30f };
		PdfPTable nesTabt5 = new PdfPTable(columnWidth4t5);
		PdfPCell nesCellt5 = new PdfPCell();
		nesCellt5.setBorder(0);
		nesCellt5.setPadding(2.9f);
		nesCellt5.addElement(new Phrase("2 SEH - Ultra Early Bird  @  Af1.   55.00", normalFont));
		nesCellt5.addElement(new Phrase("1 SEH - VIP Single  @  Af1.   100.00", normalFont));

		nesTabt5.addCell(nesCellt5);

		PdfPCell total = new PdfPCell();
		total.setBorder(0);
		total.setPadding(2.9f);
		total.addElement(new Phrase("Af1.  110.00", normalFont));
		total.addElement(new Phrase("Af1.  100.00", normalFont));

		nesTabt5.addCell(total);

		t5cell.addElement(nesTabt5);

		PdfPTable lastTab = new PdfPTable(columnWidth4t5);
		PdfPCell totalAmountCell = new PdfPCell();
		totalAmountCell.setBorder(Rectangle.TOP);
		totalAmountCell.addElement(new Phrase("Total Amount ", boldFont));

		lastTab.addCell(totalAmountCell);

		PdfPCell amount = new PdfPCell();
		amount.setBorder(Rectangle.TOP);
		amount.addElement(new Phrase("Af1.   210.00", boldFont));

		lastTab.addCell(amount);

		t5cell.addElement(lastTab);

		t5.addCell(t5cell);

		doc.add(t5);

		// Create a second table with 1 row and 2 columns
		PdfPTable t6 = new PdfPTable(1);
		t6.setWidthPercentage(100);

		PdfPCell t6cell = new PdfPCell();
		t6cell.setBorder(0);
		t6cell.setBorder(Rectangle.LEFT| Rectangle.RIGHT);
		t6cell.setBackgroundColor(BaseColor.GRAY);

		float columns[] = { 55f, 45f };
		PdfPTable addInfo = new PdfPTable(columns);
		addInfo.setWidthPercentage(100);

		PdfPCell footerCellLeft = new PdfPCell();
		footerCellLeft.setPaddingLeft(40f);
		footerCellLeft.setPaddingBottom(20f);
		footerCellLeft.addElement(new Phrase("Information ", boldFont));
		footerCellLeft.setBorder(0);

		addInfo.addCell(footerCellLeft);

		PdfPCell footerCellRight = new PdfPCell();
		footerCellRight.setPaddingLeft(50f);
		footerCellRight.setPaddingBottom(20f);
		footerCellRight.addElement(new Phrase("Social", boldFont));
		footerCellRight.setBorder(0);

		addInfo.addCell(footerCellRight);

		t6cell.addElement(addInfo);

		float column[] = { 5f, 25f, 5f, 5f, 5f };
		PdfPTable icons = new PdfPTable(column);
		icons.setWidthPercentage(80);

		PdfPCell addPhone = new PdfPCell();
		Image phoneIcon = Image.getInstance("images/phone3.png");
		phoneIcon.scaleAbsolute(20.00f, 20.00f);
		addPhone.setPaddingTop(8f);
		addPhone.setBorder(0);
		addPhone.addElement(phoneIcon);

		icons.addCell(addPhone);

		PdfPCell addPhnNo = new PdfPCell();
		addPhnNo.setBorder(0);
		addPhnNo.addElement(new Phrase("143", t2font2));

		icons.addCell(addPhnNo);

		PdfPCell addFB = new PdfPCell();
		Image fbIcon = Image.getInstance("images/icons8-facebook-48.png");
		fbIcon.scaleAbsolute(35f, 35f);
		addFB.setBorder(0);
		addFB.addElement(fbIcon);

		icons.addCell(addFB);

		PdfPCell adduTube = new PdfPCell();
		Image uTubeIcon = Image.getInstance("images/icons8-youtube-24.png");
		uTubeIcon.scaleAbsolute(30f, 30f);
		adduTube.setBorder(0);
		adduTube.addElement(uTubeIcon);

		icons.addCell(adduTube);

		PdfPCell addInsta = new PdfPCell();
		Image instaIcon = Image.getInstance("images/icons8-instagram-50.png");
		instaIcon.scaleAbsolute(30f, 30f);
		addInsta.setBorder(0);
		addInsta.addElement(instaIcon);

		icons.addCell(addInsta);

		t6cell.addElement(icons);

		float addWp[] = { 8f, 92f };
		PdfPTable whatsApp = new PdfPTable(addWp);
		whatsApp.setWidthPercentage(80);

		PdfPCell addWhatsapp = new PdfPCell();
		Image wpIcon = Image.getInstance("images/icons8-whatsapp-32.png");
		wpIcon.scaleAbsolute(30f, 30f);
		addWhatsapp.setPadding(10f);
		addWhatsapp.setBorder(0);
		addWhatsapp.addElement(wpIcon);

		whatsApp.addCell(addWhatsapp);

		PdfPCell wpNo = new PdfPCell();
		wpNo.setPadding(15f);
		wpNo.setBorder(0);
		wpNo.addElement(new Phrase("12345675", normalFont));

		whatsApp.addCell(wpNo);

		t6cell.addElement(whatsApp);

		float addMail[] = { 8f, 92f };
		PdfPTable Mail = new PdfPTable(addMail);
		Mail.setWidthPercentage(80);

		PdfPCell addingmail = new PdfPCell();
		Image mailIcon = Image.getInstance("images/icons8-mail-24 (2).png");
		mailIcon.scaleAbsolute(30f, 30f);
		addingmail.setPadding(10f);
		addingmail.setBorder(0);
		addingmail.addElement(mailIcon);

		Mail.addCell(addingmail);

		PdfPCell Mname = new PdfPCell();
		Mname.setPadding(15f);
		Mname.setBorder(0);
		Mname.addElement(new Phrase("coustomercare@pay.aw", normalFont));

		Mail.addCell(Mname);

		t6cell.addElement(Mail);

		float addGlobe[] = { 8f, 92f };
		PdfPTable globe = new PdfPTable(addMail);
		globe.setWidthPercentage(80);

		PdfPCell addingGlobe = new PdfPCell();
		Image globeIcon = Image.getInstance("images/icons8-globe-48.png");
		globeIcon.scaleAbsolute(40f, 40f);
		addingGlobe.setPadding(5f);
		addingGlobe.setBorder(0);
		addingGlobe.addElement(globeIcon);

		globe.addCell(addingGlobe);

		PdfPCell name = new PdfPCell();
		name.setPadding(15f);
		name.setBorder(0);
		name.addElement(new Phrase("pay.aw", normalFont));

		globe.addCell(name);

		t6cell.addElement(globe);

		t6.addCell(t6cell);

		doc.add(t6);
		doc.close();
	}
}

// Define a custom header/footer class

class PdfHeaderFooter extends PdfPageEventHelper {

	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		try {

//			create a table with 1 rows and 1 column
			PdfPTable t1 = new PdfPTable(1);
			t1.setWidthPercentage(100);

			// Add the image to the header
			Image image = Image.getInstance("images/download1.jfif");
			image.scaleAbsolute(500.0f, 80.0f);

			t1.addCell(image);
			document.add(t1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
