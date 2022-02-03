package com.increff.employee.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

public class PDFUtil {
    public static void createPDF() throws FOPException, TransformerException, IOException {
        // the XSL FO file
        File xsltFile = new File("template.xsl");
        // the XML file which provides the input
        StreamSource xmlSource = new StreamSource(new File("billDataXML.xml"));
        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance();
        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output
        OutputStream out;
        out = new java.io.FileOutputStream("bill.pdf");
        try {
            // Construct fop with desired output format
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(fop.getDefaultHandler());

            // Start XSLT transformation and FOP processing
            // That's where the XML is first transformed to XSL-FO and then
            // PDF is created
            transformer.transform(xmlSource, res);
        } finally {
            out.close();
        }
    }

    public static void createResponse(HttpServletResponse response, byte[] encodedBytes, int orderId)
            throws IOException {
        String pdfFileName = "order-" + orderId + "-invoice.pdf";
        response.reset();
        response.addHeader("Pragma", "public");
        response.addHeader("Cache-Control", "max-age=0");
        response.setHeader("Content-disposition", "attachment;filename=" + pdfFileName);
        response.setContentType("application/pdf");
        response.setContentLength(encodedBytes.length);
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(encodedBytes);
        servletOutputStream.flush();
        servletOutputStream.close();
    }

    public static byte[] getPDF() {
        byte[] encodedBytes = null;
        try {
            createPDF();
            File file = new File("bill.pdf");
            byte[] bytes = new byte[(int) file.length()];
            encodedBytes = java.util.Base64.getEncoder().encode(bytes);
        } catch (FOPException | TransformerException | IOException e) {
            e.printStackTrace();
        }
        return encodedBytes;
    }
}
