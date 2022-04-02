package com.example.SSGPaymtCertProject.pattern.chain;

public class SpellCheckerProcessing extends StudyProcessingObject<String> {
    public String handleWork(String text) {
        return text.replaceAll("labda", "lambda");
    }
}
