package com.example.SSGPaymtCertProject.pattern.observer;

import com.example.SSGPaymtCertProject.pattern.observer.StudyObserver;

public class StudyZerg implements StudyObserver {
    public void notify(String ment) {
        if(ment != null && ment.contains("dron")) {
            System.out.println("dron is cute! " + ment);
        }
    }
}
