package com.example.SSGPaymtCertProject.pattern.observer;

import com.example.SSGPaymtCertProject.pattern.observer.StudyObserver;

public class StudyTerran implements StudyObserver {
    public void notify(String ment) {
        if(ment != null && ment.contains("marine")) {
            System.out.println("marine is speedy! " + ment);
        }
    }
}
