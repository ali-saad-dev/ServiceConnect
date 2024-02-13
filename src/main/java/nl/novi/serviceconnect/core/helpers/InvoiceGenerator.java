package nl.novi.serviceconnect.core.helpers;

import nl.novi.serviceconnect.infrastructure.models.ServiceRequest;
import nl.novi.serviceconnect.infrastructure.models.Transaction;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class InvoiceGenerator {

    public static void generateInvoice(ServiceRequest serviceRequest, Transaction transaction) {
        try {
            String directoryPath = "invoicesPdf";

            Path directory = Paths.get(directoryPath);
            if (!directory.toFile().exists()) {
                directory.toFile().mkdirs();
            }

            String fileName = "invoice-" + serviceRequest.getId() + ".pdf";

            String filePath = Paths.get(directoryPath, fileName).toString();

            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            generateInvoiceContent(document, serviceRequest, transaction);

            document.save(filePath);
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateInvoiceContent(PDDocument document, ServiceRequest serviceRequest, Transaction transaction) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(0));

        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

        float margin = 50;
        float yPosition = document.getPage(0).getMediaBox().getHeight() - margin;
        float marginY = 20;



        String title = "Service Connect Invoice";
        float titleWidth = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD).getStringWidth(title) / 1000 * 12;
        float titlePosition = (document.getPage(0).getMediaBox().getWidth() - titleWidth) / 2;
        contentStream.newLineAtOffset(titlePosition, yPosition);
        contentStream.showText(title);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
        contentStream.newLineAtOffset(margin,yPosition - marginY);
        contentStream.showText("Date: " + transaction.getTransactionDate());
        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Invoice #: " + serviceRequest.getId());
        contentStream.newLineAtOffset(0, -marginY);


        contentStream.endText();
        contentStream.moveTo(margin, yPosition - 3 * marginY);
        contentStream.lineTo(document.getPage(0).getMediaBox().getWidth() - margin, yPosition - 3 * marginY);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(margin, yPosition - 4 * marginY);


        contentStream.showText("From: " + serviceRequest.getService().getUser().getUsername());
        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("To: " + serviceRequest.getUser().getUsername());


        contentStream.endText();
        contentStream.moveTo(margin, yPosition - 8 * marginY);
        contentStream.lineTo(document.getPage(0).getMediaBox().getWidth() - margin, yPosition - 8 * marginY);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(margin, yPosition - 9 * marginY);

        contentStream.showText("Service Name: " + serviceRequest.getService().getName());
        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Service Description: " + serviceRequest.getService().getDescription());
        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Service Category: " + serviceRequest.getService().getCategory().getName());
        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Service Price: " + serviceRequest.getService().getPrice());


        contentStream.endText();
        contentStream.moveTo(margin, yPosition - 13 * marginY);
        contentStream.lineTo(document.getPage(0).getMediaBox().getWidth() - margin, yPosition - 13 * marginY);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(margin, yPosition - 14 * marginY);


        contentStream.showText("Subtotal: " + serviceRequest.getService().getPrice());
        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Tax: " + "0");
        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Total Amount: " + serviceRequest.getService().getPrice());


        contentStream.endText();
        contentStream.moveTo(margin, yPosition - 17 * marginY);
        contentStream.lineTo(document.getPage(0).getMediaBox().getWidth() - margin, yPosition - 17 * marginY);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(margin, yPosition - 18 * marginY);


        contentStream.showText("Notes: Thank you for using our Service connect");

        contentStream.endText();
        contentStream.close();
    }
}
