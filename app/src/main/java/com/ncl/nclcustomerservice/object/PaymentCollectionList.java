package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * Created by SupraSoft on 3/6/2019.
 */
@Entity
public class PaymentCollectionList implements Serializable {
    @PrimaryKey
    @Nonnull
    @SerializedName("payment_collection_id")
    @Expose
    public int paymentCollectionId;
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("contact_id")
    @Expose
    public String contactId;
    @SerializedName("contact_name")
    @Expose
    public String contactName;
    @SerializedName("invoice_number")
    @Expose
    public String invoiceNumber;
    @SerializedName("payment_mode")
    @Expose
    public String paymentMode;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("payment_date")
    @Expose
    public String paymentDate;
    @SerializedName("cheque_date")
    @Expose
    public String cheque_date;
    @SerializedName("bank_name")
    @Expose
    public String bankName;
    @SerializedName("cheque_no")
    @Expose
    public String chequeNo;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("transfer_type")
    @Expose
    public String transferType;
    @SerializedName("transaction_ref_no")
    @Expose
    public String transactionRefNo;
    @SerializedName("created_date_time")
    @Expose
    public String created_date_time;
    @SerializedName("sales_calls_temp_id")
    @Expose
    public String sales_calls_temp_id;
    @SerializedName("Division")
    @Expose
    public String division;
    @SerializedName("CustomerSAPCode")
    @Expose
    public String sap_code;
    @SerializedName("customer_location")
    @Expose
    public String location;
    @SerializedName("comments_by_commercial_team")
    @Expose
    public String comments_by_commercial_team;
    @SerializedName("sales_owner_name")
    @Expose
    public String sales_owner_name;
    @SerializedName("sales_owner_id")
    @Expose
    public String sales_owner_id;

    private final static long serialVersionUID = -670278784449663508L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("paymentCollectionId", paymentCollectionId).append("customerName", customerName).append("customerId", customerId).append("contactId", contactId).append("contactName", contactName).append("invoiceNumber", invoiceNumber).append("paymentMode", paymentMode).append("amount", amount).append("paymentDate", paymentDate).append("bankName", bankName).append("chequeNo", chequeNo).append("status", status).append("transferType", transferType).append("transactionRefNo", transactionRefNo).toString();
    }
}
