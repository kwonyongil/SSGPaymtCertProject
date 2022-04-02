package com.example.SSGPaymtCertProject.pattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @since 2022. 03. 06
 * @author kwon-yong-il
 * 옵저버 패턴 예시
 * 사용법 (Subject 구현)
 */
public class StudyStarFeed implements StudySubject {

    private final List<StudyObserver> observers = new ArrayList<>();

    public void registerObserver(StudyObserver o) {
        this.observers.add(o);
    }
    public void notifyObservers(String ment) {
        observers.forEach(o -> o.notify(ment));
    }
}
/*
  StudyStarFeed f = new StudyStarFeed();
  f.registerObserver(new StudyProtos());
  f.registerObserver(new StudyTerran());
  f.registerObserver(new StudyZert());
  f.notifyObservers("Observer notify all");
  예제에서는 실행해야 할 동작이 비교적 간단하므로 람다 표현식으로 불필요 코드를 제거했다.
  하지만 옵저버가 상태를 가지며, 여러 메서드를 정의하는 등 복잡하다면
  람다 표현식보다 기존의 클래스 구현방식을 고수하는 것이 좋다.

  f.registerObserver((String ment) -> {
	if(ment != null && ment.contains("zilot")) {
		System.out.println("zilot is strong! " + ment);
	}
  });
 */
