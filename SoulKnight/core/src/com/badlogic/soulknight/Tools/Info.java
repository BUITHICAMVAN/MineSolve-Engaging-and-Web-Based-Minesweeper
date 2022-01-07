package com.badlogic.soulknight.Tools;

public class Info {
    private String type;
    private Detail detail;

    public Info(String type) {
        this.type = type;
    }

    public Info(String type, Detail detail) {
        this.type = type;
        this.detail = detail;
    }

    public String getType() {
        return type;
    }
}
