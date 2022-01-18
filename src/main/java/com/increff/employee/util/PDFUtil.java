package com.increff.employee.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
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
    public static byte[] createPDF() throws FOPException, TransformerException, IOException {
        File xmlfile = new File("billDataXML.xml");
        File xsltfile = new File("template.xsl");

        File pdffile = new File("resultPDF.pdf");
        FopFactory fopFactory = FopFactory.newInstance();
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = new java.io.FileOutputStream(pdffile);
        out = new java.io.ByteArrayOutputStream();

        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
        transformer.setParameter("versionParam", "2.0");
        Source src = new StreamSource(xmlfile);
        Result res = new SAXResult(fop.getDefaultHandler());
        transformer.transform(src, res);
        out.close();
        out.flush();
        byte[] byteArray = ((java.io.ByteArrayOutputStream) out).toByteArray();

        byte[] encodedBytes = java.util.Base64.getEncoder().encode(byteArray);

        return encodedBytes;

    }

    public static void createResponse(HttpServletResponse response, byte[] encodedBytes) throws IOException {
        String pdfFileName = "output.pdf";
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
}
