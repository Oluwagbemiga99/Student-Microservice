package com.portal.student.service;

import com.portal.student.model.Account;
import com.portal.student.model.Invoice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IntegrationService {
    private final RestTemplate restTemplate;

    @Value("${finance.host}")
    private String financeHost;
    @Value("${finance.student.create}")
    private String studentCreatedFinanceSubscriber;
    @Value("${library.host}")
    private String libraryHost;
    @Value("${library.student.create}")
    private String studentCreatedLibrarySubscriber;
    @Value("${finance.course.enrol}")
    private String courseEnrolmentFinanceSubscriber;
    @Value("${finance.account.status}")
    private String accountStatusPublisher;

    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyStudentCreated(Account account) {
        restTemplate.postForObject(financeHost + studentCreatedFinanceSubscriber, account, Account.class);
        restTemplate.postForObject(libraryHost + studentCreatedLibrarySubscriber, account, Account.class);
    }

    public void createCourseFeeInvoice(Invoice invoice) {
        restTemplate.postForObject(financeHost + courseEnrolmentFinanceSubscriber, invoice, Invoice.class);
    }

    public Account getStudentPaymentStatus(int id) {
        return restTemplate.getForObject(financeHost + accountStatusPublisher + id, Account.class);
    }
}
