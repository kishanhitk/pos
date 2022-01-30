package com.increff.employee.util;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.fop.apps.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import com.increff.employee.model.InvoiceData;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class PDFUtils {

    public static void generatePDFFromJavaObject(InvoiceData invoiceData) throws Exception {
        ByteArrayOutputStream xmlSource = getXMLSource(invoiceData);
        StreamSource streamSource = new StreamSource(new ByteArrayInputStream(xmlSource.toByteArray()));
        generatePDF(streamSource);
    }

    private static ByteArrayOutputStream getXMLSource(InvoiceData invoiceData) {
        JAXBContext context;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            context = JAXBContext.newInstance(InvoiceData.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(invoiceData, outStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return outStream;
    }

    private static void generatePDF(StreamSource streamSource)
            throws FOPException, TransformerException, IOException {
        File xsltFile = new File("template.xsl");

        // create an instance of fop factory
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());

        // a user agent is needed for transformation
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        // Setup output

        try (OutputStream out = new java.io.FileOutputStream("invoice.pdf")) {
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
            transformer.transform(streamSource, res);
        }
    }
}
