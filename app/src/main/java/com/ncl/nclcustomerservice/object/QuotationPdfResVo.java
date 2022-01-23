package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/16/2018.
 */

public class QuotationPdfResVo  implements Serializable
{

    @SerializedName("pdf_url")
    @Expose
    public String pdfUrl;
    private final static long serialVersionUID = 3008362501743646008L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("pdfUrl", pdfUrl).toString();
    }

}
