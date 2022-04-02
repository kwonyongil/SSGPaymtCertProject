package com.example.SSGPaymtCertProject.pattern.chain;

public class HeaderTextProcessing extends StudyProcessingObject<String> {
    public String handleWork(String text) {
        return "From Raoul, Mario and Alan: " + text;
    }
}
