package com.example.SSGPaymtCertProject.pattern.chain;

/**
 * 의무체인 패턴
 * @param <T> 의무체인 패턴
 */
public abstract class StudyProcessingObject<T> {
    protected StudyProcessingObject<T> successor;
    public void setSuccessor(StudyProcessingObject<T> successor) {
        this.successor = successor;
    }

    public T handle(T input) {
        T r = handleWork(input);
        if(successor != null) {
            return successor.handle(r);
        }
        return r;
    }
    abstract protected T handleWork(T input);
}
/*
ProcessingObject<String> p1 = new HeaderTextProcessing();
ProcessingObject<String> p2 = new SpellCheckerProcessing();
p1.setSuccessor(p2); // 두 작업 처리 객체를 연결한다.
String result = p1.handle("Aren’t labdas really sexy?!");
System.out.println(result);

출력 : From Raoul, Mario and Alan: Aren’t lambdas really sexy ?! 출력

람다 표현식(함수 체인(즉, 함수 조합) 과 비슷하다!)
UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
UnaryOperator<String> SpellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");
Function<String, String> pipeline =
headerProcessing.andThen(SpellCheckerProcessing);
동작 체인으로 두 함수를 조합한다.
String result = pipeline.apply("Aren’t labdas really sexy?!");
 */