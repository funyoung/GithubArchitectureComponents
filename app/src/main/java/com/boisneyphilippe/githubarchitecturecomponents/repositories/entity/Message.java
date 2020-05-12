package com.boisneyphilippe.githubarchitecturecomponents.repositories.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author yangfeng
 */
@Entity
public class Message {
    private String icon;
    private String text;
    private long momentID;

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("offset")
    @Expose
    private int offset;

    @SerializedName("avatar_url")
    @Expose
    private String avatar_url;

    @SerializedName("message")
    @Expose
    private String message;

    // --- CONSTRUCTORS ---
    @Ignore
    public Message() { }

    public Message(@NonNull String id, int offset, String avatar_url, String message) {
        this.id = id;
        this.offset = offset;
        this.avatar_url = avatar_url;
        this.message = message;
    }

    // --- GETTER ---

    public String getId() { return id; }
    public String getAvatar_url() { return avatar_url; }
    public int getOffset() { return offset; }
    public String getMessage() { return message; }

    // --- SETTER ---

    public void setId(String id) { this.id = id; }
    public void setAvatar_url(String avatar_url) { this.avatar_url = avatar_url; }
    public void setOffset(int offset) { this.offset = offset; }
    public void setMessage(String message) { this.message = message; }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getMomentID() {
        return momentID;
    }

    public void setMomentID(long momentID) {
        this.momentID = momentID;
    }
}
