package com.example.SSGPaymtCertProject.pattern.observer;

import com.example.SSGPaymtCertProject.pattern.observer.StudyObserver;

public class StudyProtos implements StudyObserver {
    public void notify(String ment) {
        if(ment != null && ment.contains("zilot")) {
            System.out.println("zilot is strong! " + ment);
        }
    }
}
