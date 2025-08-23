package com.spring.boot.springbootcapstone3.Service;

import com.spring.boot.springbootcapstone3.API.ApiException;
import com.spring.boot.springbootcapstone3.Model.Contract;
import com.spring.boot.springbootcapstone3.Repository.ContractRepository;
import com.spring.boot.springbootcapstone3.Repository.VendorRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final VendorService vendorService;

    private final ContractService contractService;
    private final ContractRepository contractRepository;


    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("organzaition@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }


    public void sendEmailWithContract(String toEmail, String subject, String body, Integer contractId, File pdf) {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


            //SimpleMailMessage message = new SimpleMailMessage();

            helper.setFrom("organzaition@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            //How to use with your email sender
            //File pdf = contractService.generateContractPdfFile(contractId);
// In your mail code (example with MimeMessageHelper):
// helper.addAttachment("contract-" + contractId + ".pdf", pdf);


            helper.addAttachment("contract-" + contractId + ".pdf", pdf);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new ApiException("Failed to compose/send email with attachment");
        } catch (Exception e) {
            throw new ApiException("Internal server error while sending email");
        }


    }


    public void sendEmailWithContractHtml(String toEmail, String subject,
                                          Integer contractId, String vendorName, File pdf) {

        if (toEmail == null || !toEmail.contains("@"))
            throw new ApiException("Invalid recipient email");
        if (pdf == null || !pdf.exists())
            throw new ApiException("Contract PDF not found");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

            helper.setFrom("organzaition@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);

            String htmlBody = buildContractHtml(vendorName, contractId, "#"); // غيّر الرابط إذا عندك
            if (htmlBody == null || htmlBody.isBlank()) {
                throw new ApiException("Template returned empty HTML");
            }

            // أرسل الإيميل كـ HTML
            helper.setText(htmlBody, true);

            helper.addAttachment("contract-" + contractId + ".pdf", pdf);

            // (اختياري) BCC لنفسك للتأكد
            // helper.addBcc("organzaition@gmail.com");

            mailSender.send(message);
        } catch (jakarta.mail.MessagingException e) {
            throw new ApiException("Failed to compose/send email with attachment");
        } catch (Exception e) {
            throw new ApiException("Internal server error while sending email");
        }
    }


    public String buildContractHtml(String vendorName, Integer contractId, String actionUrl) {
        return String.format("""
<!doctype html>
<html lang="ar" dir="rtl">
<head><meta charset="UTF-8"></head>
<body style="font-family:Tahoma,Arial,sans-serif;background:#f6f7fb;margin:0;padding:20px">
  <div style="max-width:600px;margin:auto;background:#fff;border:1px solid #eee;border-radius:10px;overflow:hidden">
    <div style="background:#0f172a;color:#fff;padding:16px 20px;font-weight:bold">عقد خدمات التشجير</div>
    <div style="padding:20px;color:#0f172a;line-height:1.8">
      <div style="background:#e2e8f0;display:inline-block;padding:4px 10px;border-radius:9999px;font-size:12px;margin-bottom:10px">
        رقم العقد: %s
      </div>
      <h2 style="margin:0 0 12px">مرحبًا %s،</h2>
      <p>أرفقنا لكم نسخة الـPDF الخاصة بالعقد. يُرجى المراجعة.</p>
      <p style="margin-top:16px">
        <a href="%s" style="display:inline-block;background:#0f172a;color:#fff;text-decoration:none;padding:10px 14px;border-radius:8px">
          عرض تفاصيل الطلب
        </a>
      </p>
    </div>
    <div style="padding:12px 20px;font-size:12px;color:#475569;background:#fafafa;border-top:1px solid #eee">
      © %s Landscaping System — هذا إيميل مُرسل آليًا.
    </div>
  </div>
</body>
</html>
""", contractId, vendorName, actionUrl, java.time.Year.now());
    }



}



