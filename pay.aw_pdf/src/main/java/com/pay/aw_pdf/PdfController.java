package com.pay.aw_pdf;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PdfController {

	@Autowired
	PdfService pdfService;
	
	@GetMapping("/generate")
	public void genPdf(HttpServletResponse httpServletResponse) throws Exception {
		
//		to attach pdf to httpServletResponse
		httpServletResponse.setContentType("application/pdf");
		
		// Set the response header for the PDF file attachment with a dynamic filename
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=generated-pdf.pdf";
        httpServletResponse.setHeader(headerKey, headerValue);
		
        pdfService.exportPdf(httpServletResponse);
	}
	
}
