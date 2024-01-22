package nl.novi.serviceconnect.helpper;

import nl.novi.serviceconnect.models.ServiceRequest;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;


import java.io.File;
import java.io.IOException;



public class InvoiceGenerator {
    public static File generateInvoice(ServiceRequest serviceRequest) {
        try {
            // Generate a unique file name for each invoice
            String fileName = "invoice_" + System.currentTimeMillis() + ".pdf";

            // Create a temporary file for the PDF invoice
            File invoiceFile = new File(fileName);

            // Create a new PDF document
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Generate the content of the invoice
            generateInvoiceContent(document, serviceRequest);

            // Save the PDF document to the temporary file
            document.save(invoiceFile);
            document.close();

            return invoiceFile;
        } catch (IOException e) {
            // Handle the exception (e.g., log or throw a custom exception)
            e.printStackTrace();
            return null;
        }
    }

    private static void generateInvoiceContent(PDDocument document, ServiceRequest serviceRequest) throws IOException {

        // Create a new content stream for the PDF page
        PDPageContentStream contentStream = new PDPageContentStream(document, document.getPage(0));

        // Begin text section
        contentStream.beginText();

        // Set font and font size
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

        // Set starting point for the text
        float margin = 50;  // Adjust the left margin as needed
        float yStart = document.getPage(0).getMediaBox().getHeight() - margin;
        float yPosition = yStart;
        float marginY = 20;  // Adjust the vertical spacing as needed

        // Customize this method to generate the content of the PDF invoice based on your requirements
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



        // Add more details as needed

        // End text section
        contentStream.endText();

        // Close the content stream
        contentStream.close();
    }
}
