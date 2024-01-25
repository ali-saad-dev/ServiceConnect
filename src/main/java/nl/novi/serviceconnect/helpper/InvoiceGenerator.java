package nl.novi.serviceconnect.helpper;

import nl.novi.serviceconnect.models.ServiceRequest;
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
    public static String generateInvoice(ServiceRequest serviceRequest) {
        try {
            String directoryPath = "invoicesPdf";

            Path directory = Paths.get(directoryPath);
            if (!directory.toFile().exists()) {
                directory.toFile().mkdirs();
            }

            String fileName = "invoice_" + System.currentTimeMillis() + ".pdf";

            String filePath = Paths.get(directoryPath, fileName).toString();

            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            generateInvoiceContent(document, serviceRequest);

            document.save(filePath);
            document.close();

            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void generateInvoiceContent(PDDocument document, ServiceRequest serviceRequest) throws IOException {

        PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(0));

        contentStream.beginText();

        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

        float margin = 50;
        float yStart = document.getPage(0).getMediaBox().getHeight() - margin;
        float yPosition = yStart;
        float marginY = 20;

        contentStream.newLineAtOffset(margin, yPosition);
        contentStream.showText("Invoice");
        yPosition -= marginY;

        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Service Request ID: " + serviceRequest.getId());
        yPosition -= marginY;

        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Service Name: " + serviceRequest.getService().getName());
        yPosition -= marginY;

        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Message: " + serviceRequest.getMessage());
        yPosition -= marginY;

        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Service Description: " + serviceRequest.getService().getDescription());
        yPosition -= marginY;

        contentStream.newLineAtOffset(0, -marginY);
        contentStream.showText("Total Amount: " + serviceRequest.getService().getPrice());
        yPosition -= marginY;

        contentStream.endText();
        contentStream.close();
    }
}
