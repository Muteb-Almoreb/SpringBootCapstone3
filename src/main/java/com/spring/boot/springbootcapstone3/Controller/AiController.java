package com.spring.boot.springbootcapstone3.Controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.spring.boot.springbootcapstone3.Model.Offer;
import com.spring.boot.springbootcapstone3.Model.ServiceRequest;
import com.spring.boot.springbootcapstone3.Service.AiService;
import com.spring.boot.springbootcapstone3.Service.OfferService;
import com.spring.boot.springbootcapstone3.Service.ServiceRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;


@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;
    private final OfferService offerService;
    private final ServiceRequestService serviceRequestService;


//    @GetMapping(value = "/tree-plan")
//    public ResponseEntity<String> generateMonthlyTreePlan(
//            @RequestParam Integer serviceRequestId,
//            @RequestParam Integer offerId) {
//
//
//        Offer offer = offerService.getById(offerId);
//
//        ServiceRequest sr = serviceRequestService.getById(serviceRequestId);
//
//        String offerDescription = offer.getDescription();
//        String location = sr.getLocation();
//
//        String plan = aiService.generateMonthlyTreePlan(offerDescription, location);
//
//
//
//        return ResponseEntity.status(200).body(plan);
//    }

    @GetMapping(value = "/tree-plan")
    public ResponseEntity<byte[]> generateMonthlyTreePlan(
            @RequestParam Integer serviceRequestId,
            @RequestParam Integer offerId) {

        Offer offer = offerService.getById(offerId);
        ServiceRequest sr = serviceRequestService.getById(serviceRequestId);

        String offerDescription = offer.getDescription();
        String location = sr.getLocation();

        String plan = aiService.generateMonthlyTreePlan(offerDescription, location);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Monthly Tree Plan"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(plan));
            document.close();

            byte[] pdfBytes = out.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "tree-plan.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


}
