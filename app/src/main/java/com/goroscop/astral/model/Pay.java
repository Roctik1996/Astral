package com.goroscop.astral.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pay {

    @SerializedName("preauth_payment_link")
    @Expose
    private String preauthPaymentLink;

    public String getPreauthPaymentLink() {
        return preauthPaymentLink;
    }

    public void setPreauthPaymentLink(String preauthPaymentLink) {
        this.preauthPaymentLink = preauthPaymentLink;
    }
}
