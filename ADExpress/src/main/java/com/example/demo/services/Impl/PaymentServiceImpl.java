package com.example.demo.services.Impl;

import com.example.demo.models.Customer;
import com.example.demo.models.Packages;
import com.example.demo.models.PaymentOrder;
import com.example.demo.services.PaymentService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    protected CustomerServiceImpl customerService;

    public final String success_url = "/customers/home";
    public final String cancel_url = "";

    @Value("${adexpress.paypal.clientId}")
    String client_id;
    @Value("${adexpress.paypal.clientSecret}")
    String secret_id;
    @Value("${adexpress.paypal.mode}")
    String environmentMode;

    private static final String currencyType = "USD";
    private static final String paymentMethod = "paypal";
    private static final String intent = "sale";
    private APIContext apiContext;

    @PostConstruct
    public void init(){
        // works like conduit between paypal and our application and used for authentication.
        this.apiContext = new APIContext(client_id,secret_id,environmentMode);
    }

    @Override
    public String makePayment(PaymentOrder paymentOrder) throws Exception {
        Customer customer = customerService.Login(paymentOrder.getCustomer_username());
        System.out.println("paymentController "+customer.getAddress());
        Payer payer = getPayerInformation(customer);
        System.out.println("payerinfo: "+payer.getPayerInfo());
        RedirectUrls redirectUrls = getRedirectsUrl();

        List<Transaction>transactionList = getTransactionInfo(paymentOrder);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setTransactions(transactionList);
        System.out.println("transactions: "+payment.getTransactions());
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);
        try{

            Payment approvedLink = payment.create(apiContext);
            return this.approvalLink(approvedLink);

        }catch (Exception e){
            throw new Exception("null");
        }
    }

    @Override
    public PaymentOrder executePayment() {
        return null;
    }

    /**
     * Used to obtain a payer information. Firstly we need to set a payment method which obviously is 'paypal', then set the information about a payer.
     *
     * @param customer
     * @return Payer
     */
    @Override
    public Payer getPayerInformation(Customer customer) {
        Payer payer = new Payer();
        payer.setPaymentMethod(paymentMethod);

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName(customer.getName());
        payerInfo.setLastName(customer.getLast_name());
        payerInfo.setEmail(customer.getEmail());

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    /**
     * This method handles a transaction information. Firstly we need to set a transaction details by specifying a delivery price,
     * service price and the package price. Then set the total amount(delivery+service+package) and the currency type. Then set the
     * item information such as package name, the category of package ant etc. And finally return the List of transactions.
     * @param paymentOrder
     * @return List<Transaction>
     */
    @Override
    public List<Transaction> getTransactionInfo(PaymentOrder paymentOrder) {
        Double total =  BigDecimal.valueOf(paymentOrder.getShipping()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        Details details = new Details();
        //details.setShipping(String.valueOf(total));
        total =  BigDecimal.valueOf(paymentOrder.getTax_price()).setScale(2, RoundingMode.HALF_UP).doubleValue();
       // details.setTax(String.valueOf(total));
        total =  BigDecimal.valueOf(paymentOrder.getPackage_price()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        //details.setSubtotal(String.valueOf(total));

        Amount amount = new Amount();
        amount.setCurrency(currencyType);

        total = BigDecimal.valueOf(paymentOrder.getTotal_price()).setScale(2, RoundingMode.HALF_UP).doubleValue();

        amount.setTotal(String.valueOf(total));

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(paymentOrder.getPackage_name());

        Item item = new Item();
        item.setName(paymentOrder.getPackage_name());
        item.setCurrency("USD");

        total =  BigDecimal.valueOf(paymentOrder.getTotal_price()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        System.out.println("PROBA+ "+total);
        item.setPrice(String.valueOf(total));

       // total =  BigDecimal.valueOf(paymentOrder.getTax_price()).setScale(2, RoundingMode.HALF_UP).doubleValue();
      //  System.out.println(total);
        //item.setTax(String.valueOf(total));
        item.setQuantity("1");

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);

        ItemList items = new ItemList();
        items.setItems(itemList);

        transaction.setItemList(items);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        return transactionList;
    }

    /**
     * This method handles the urls against the given action. If the user rejects a payment, paypal can refund us to the
     * specific page(even if this page is local)
     * @return RedirectUrls
     */
    @Override
    public RedirectUrls getRedirectsUrl() {
        String baseUrl = "http://localhost:4200";

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(baseUrl+cancel_url);
        redirectUrls.setReturnUrl(baseUrl+success_url);

        return redirectUrls;
    }

    @Override
    public String approvalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }

}
