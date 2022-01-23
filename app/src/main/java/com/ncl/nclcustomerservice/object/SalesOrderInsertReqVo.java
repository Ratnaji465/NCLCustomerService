package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/12/2018.
 */

public class SalesOrderInsertReqVo implements Serializable {
    @SerializedName("requesterid")
    @Expose
    public int requesterid;
    @SerializedName("requestname")
    @Expose
    public String requestname;
    @SerializedName("sales_order_id")
    @Expose
    public int salesOrderId;
    @SerializedName("Customer")
    @Expose
    public String customer;
    @SerializedName("OrderType")
    @Expose
    public String orderType;
    @SerializedName("Soldtopartycode")
    @Expose
    public String soldtopartycode;
    @SerializedName("Shiptopartycode")
    @Expose
    public String shiptopartycode;
    @SerializedName("BilltopartyCode")
    @Expose
    public String billtopartyCode;
    @SerializedName("Ponumber")
    @Expose
    public String ponumber;
    @SerializedName("remarks")
    @Expose
    public String remarks;
    @SerializedName("contracts_id")
    @Expose
    public String contractId;
    @SerializedName("CashDiscount")
    @Expose
    public String cashDiscount;
    @SerializedName("withoutdiscountamount")
    @Expose
    public String withoutdiscountamount;
    @SerializedName("SchemeDiscount")
    @Expose
    public String schemeDiscount;
    @SerializedName("QuntityDiscount")
    @Expose
    public String quntityDiscount;
    @SerializedName("Freight")
    @Expose
    public String freight;
    @SerializedName("freight_amount")
    @Expose
    public String freightAmount;
    @SerializedName("IGST")
    @Expose
    public String iGST;
    @SerializedName("CGST")
    @Expose
    public String cGST;
    @SerializedName("SGST")
    @Expose
    public String sGST;
    @SerializedName("discountAmount")
    @Expose
    public String discountAmount;
    @SerializedName("Total")
    @Expose
    public String total;
    @SerializedName("sales_order_prodct")
    @Expose
    public List<SalesOrderLineItem> salesOrderProdct = null;
    @SerializedName("salesPersonsProducts")
    @Expose
    public List<SalesPersonLineItem> salesPersonsProducts = null;
    @SerializedName("date_of_delivery")
    @Expose
    public String date_of_delivery;
    @SerializedName("delivered_by")
    @Expose
    public String delivered_by;
    @SerializedName("delivered_by_customer_id")
    @Expose
    public String delivered_by_customer_id;
    @SerializedName("delivered_by_customer_name")
    @Expose
    public String delivered_by_customer_name;
    @SerializedName("insert_by")
    @Expose
    public String insert_by;
    @SerializedName("sales_calls_temp_id")
    @Expose
    public String sales_calls_temp_id;
    @SerializedName("order_status")
    @Expose
    public String order_status;
    @SerializedName("order_status_comments")
    @Expose
    public String order_status_comments;
    @SerializedName("Division")
    @Expose
    public String division;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesOrderId", salesOrderId).append("contractId", contractId).append("customer", customer).append("orderType", orderType).append("soldtopartycode", soldtopartycode).append("shiptopartycode", shiptopartycode).append("billtopartyCode", billtopartyCode).append("ponumber", ponumber).append("remarks", remarks).append("cashDiscount", cashDiscount).append("withoutdiscountamount", withoutdiscountamount).append("schemeDiscount", schemeDiscount).append("quntityDiscount", quntityDiscount).append("freight", freight).append("iGST", iGST).append("cGST", cGST).append("sGST", sGST).append("discountAmount", discountAmount).append("freightAmount", freightAmount).append("total", total).append("salesOrderProdct", salesOrderProdct).toString();
    }

}
