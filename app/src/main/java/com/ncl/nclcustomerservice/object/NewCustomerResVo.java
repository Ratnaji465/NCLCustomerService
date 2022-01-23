package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewCustomerResVo implements Serializable {
    @SerializedName("contact_list")
    @Expose
    public ContactListResVo contactList;

    public static class ContactListResVo implements Serializable{
        @SerializedName("Contractors")
        @Expose
        public List<CustomerContactResponseVo.ContactContractorList> contactContractorLists;
    }
}
