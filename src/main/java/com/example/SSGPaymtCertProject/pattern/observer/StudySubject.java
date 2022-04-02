package com.example.SSGPaymtCertProject.pattern.observer;

import com.example.SSGPaymtCertProject.pattern.observer.StudyObserver;

/**
 * 옵저버 패턴 주제(Subject)
 */
public interface StudySubject {
    void registerObserver(StudyObserver o);
    void notifyObservers(String tweet);
}
