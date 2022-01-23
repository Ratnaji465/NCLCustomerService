package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 3/6/2019.
 */

public class PaymentCollectionReqVo implements Serializable {
    @SerializedName("requesterid")
    @Expose
    public int requesterid;
    @SerializedName("requestname")
    @Expose
    public String requestname;
    @SerializedName("payment_collection_id")
    @Expose
    public String paymentCollectionId;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("contact_id")
    @Expose
    public String contactId;
    @SerializedName("payment_mode")
    @Expose
    public String paymentMode;
    @SerializedName("invoice_number")
    @Expose
    public String invoiceNumber;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("payment_date")
    @Expose
    public String paymentDate;
    @SerializedName("cheque_date")
    @Expose
    public String chequeDate;
    @SerializedName("bank_name")
    @Expose
    public String bankName;
    @SerializedName("customer_location")
    @Expose
    public String customerLocation;
    @SerializedName("cheque_no")
    @Expose
    public String chequeNo;
    @SerializedName("comments_by_commercial_team")
    @Expose
    public String commentsByCommercialTeam;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("transfer_type")
    @Expose
    public String transferType;
    @SerializedName("transaction_ref_no")
    @Expose
    public String transactionRefNo;
    @SerializedName("insert_by")
    @Expose
    public String insert_by;
    @SerializedName("sales_calls_temp_id")
    @Expose
    public String sales_calls_temp_id ;
    @SerializedName("Division")
    @Expose
    public String division ;
    private final static long serialVersionUID = -1023479314400831662L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("paymentCollectionId", paymentCollectionId).append("customerId", customerId).append("contactId", contactId).append("paymentMode", paymentMode).append("invoiceNumber", invoiceNumber).append("amount", amount).append("paymentDate", paymentDate).append("bankName", bankName).append("chequeNo", chequeNo).append("status", status).append("transferType", transferType).append("transactionRefNo", transactionRefNo).toString();
    }
}
