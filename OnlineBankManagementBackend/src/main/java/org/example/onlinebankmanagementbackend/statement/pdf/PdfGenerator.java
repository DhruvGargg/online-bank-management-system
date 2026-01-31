package org.example.onlinebankmanagementbackend.statement.pdf;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.example.onlinebankmanagementbackend.entity.Account;
import org.example.onlinebankmanagementbackend.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PdfGenerator {

    public byte[] generateAccountStatement(
            Account account,
            List<Transaction> transactions
    ) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("Account Statement", titleFont));
            document.add(new Paragraph("Account Number: " + account.getAccountNumber(), normalFont));
            document.add(new Paragraph("Account Holder: " + account.getUser().getName(), normalFont));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            table.addCell("Txn ID");
            table.addCell("Type");
            table.addCell("Amount");
            table.addCell("Status");
            table.addCell("Date");

            for (Transaction transaction : transactions)
            {
                table.addCell(transaction.getTransactionExternalId());
                table.addCell(transaction.getTransactionType().name());
                table.addCell(transaction.getTransactionStatus().name());
                table.addCell(transaction.getTransactionCreatedAt().toString());
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed");
        }

        return out.toByteArray();
    }
}
