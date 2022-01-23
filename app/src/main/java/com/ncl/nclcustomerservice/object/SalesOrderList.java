package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/2/2018.
 */
@Entity
public class SalesOrderList implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("sales_order_id")
    @Expose
    public int salesOrderId;
    @SerializedName("sales_order_number")
    @Expose
    public String salesOrderNumber;
    @SerializedName("Customer")
    @Expose
    public String customer;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("contract_Name")
    @Expose
    public String contractName;
    @SerializedName("contract_id")
    @Expose
    public String contractId;
    @SerializedName("OrderType")
    @Expose
    public String orderType;
    @SerializedName("OrderType_form")
    @Expose
    public String orderTypeForm;
    @SerializedName("SalesOrganisation")
    @Expose
    public String salesOrganisation;
    @SerializedName("DistributionChannel")
    @Expose
    public String distributionChannel;
    @SerializedName("Division")
    @Expose
    public String division;
    @SerializedName("Soldtopartycode_id")
    @Expose
    public String soldtopartycodeId;
    @SerializedName("Shiptopartycode_id")
    @Expose
    public String shiptopartycodeId;
    @SerializedName("BilltopartyCode_id")
    @Expose
    public String billtopartyCodeId;
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
    @SerializedName("Remarks")
    @Expose
    public String remarks;
    @SerializedName("CashDiscount")
    @Expose
    public String cashDiscount;
    @SerializedName("SchemeDiscount")
    @Expose
    public String schemeDiscount;
    @SerializedName("QuntityDiscount")
    @Expose
    public String quntityDiscount;
    @SerializedName("withoutdiscountamount")
    @Expose
    public String withoutdiscountamount;
    @SerializedName("Freight")
    @Expose
    public String freight;
    @SerializedName("freight_amount")
    @Expose
    public String freightAmount;
    @SerializedName("discountAmount")
    @Expose
    public String discountAmount;
    @SerializedName("IGST")
    @Expose
    public String iGST;
    @SerializedName("CGST")
    @Expose
    public String cGST;
    @SerializedName("SGST")
    @Expose
    public String sGST;
    @SerializedName("Total")
    @Expose
    public String total;
    @SerializedName("created_date_time")
    @Expose
    public String created_date_time;
    @Ignore
    @SerializedName("sales_order_product_list")
    @Expose
    public List<SalesOrderLineItem> salesOrderProductList = null;
    @Ignore
    @SerializedName("salesPersonsProducts")
    @Expose
    public List<SalesPersonLineItem> salesPersonsProducts = null;
    @Ignore
    @SerializedName("approval_process")
    @Expose
    public List<ApprovalProcess> approvalProcess = null;
    @SerializedName("sales_calls_temp_id")
    @Expose
    public String sales_calls_temp_id ;

    @SerializedName("delivered_by")
    @Expose
    public String delivered_by;
    @SerializedName("delivered_by_customer_id")
    @Expose
    public String delivered_by_customer_id;
    @SerializedName("delivered_by_customer_name")
    @Expose
    public String delivered_by_customer_name;
    @SerializedName("date_of_delivery")
    @Expose
    public String date_of_delivery;
    @SerializedName("division_name")
    @Expose
    public String division_name;
    @SerializedName("OwnerName")
    @Expose
    public String owner_name;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesOrderId", salesOrderId).append("contractId", contractId).append("contractName", contractName).append("salesOrderNumber", salesOrderNumber).append("customer", customer).append("orderType", orderType).append("salesOrganisation", salesOrganisation).append("distributionChannel", distributionChannel).append("division", division).append("soldtopartycode", soldtopartycode).append("shiptopartycode", shiptopartycode).append("soldtopartycodeId", soldtopartycodeId).append("shiptopartycodeId", shiptopartycodeId).append("billtopartyCodeId", billtopartyCodeId).append("billtopartyCode", billtopartyCode).append("ponumber", ponumber).append("remarks", remarks).append("cashDiscount", cashDiscount).append("schemeDiscount", schemeDiscount).append("quntityDiscount", quntityDiscount).append("withoutdiscountamount", withoutdiscountamount).append("freight", freight).append("freightAmount", freightAmount).append("discountAmount", discountAmount).append("iGST", iGST).append("cGST", cGST).append("sGST", sGST).append("total", total).append("salesOrderProductList", salesOrderProductList).append("approvalProcess", approvalProcess).append("customerId", customerId).toString();
    }

}
