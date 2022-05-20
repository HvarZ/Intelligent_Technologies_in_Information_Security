package com.RainCarnation.service;

public interface Measurable {
    default double getX() {
        return 0;
    }
    default double getY() {
        return 0;
    }
    default String getId() {
        return "";
    }

    default String getArea() {
        return "";
    }
}
