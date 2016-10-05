package com.sheyi.testify.models.sendables;

/**
 * Created by andela on 30/09/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommentPayload {

    @SerializedName("text")
    @Expose
    private String text;

    /**
     *
     * @return
     * The text
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @param text
     * The text
     */
    public void setText(String text) {
        this.text = text;
    }

}