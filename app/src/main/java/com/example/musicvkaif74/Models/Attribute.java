package com.example.musicvkaif74.Models;

public class Attribute {
    private long attributeId;
    private String attributeName;
    private String attributeType;
    private String value;
    private long valueId;

    @Override
    public String toString() {
        return "Attribute{" +
                "attributeId=" + attributeId +
                ", attributeName='" + attributeName + '\'' +
                ", attributeType='" + attributeType + '\'' +
                ", value='" + value + '\'' +
                ", valueId=" + valueId +
                '}';
    }
}
