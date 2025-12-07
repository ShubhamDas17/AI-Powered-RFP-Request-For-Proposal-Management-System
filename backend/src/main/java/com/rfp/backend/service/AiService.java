package com.rfp.backend.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AiService {

    // -------------------------
    // MAIN EVALUATION FUNCTION
    // -------------------------
    public String evaluateProposal(String rfpText, String proposalText, String vendor) {

        int rfpQty = extractNumber(rfpText);
        int rfpDays = extractDays(rfpText);
        int rfpBudget = extractBudget(rfpText);

        int propQty = extractNumber(proposalText);
        int propDays = extractDays(proposalText);
        int propBudget = extractBudget(proposalText);

        int score = 100;
        StringBuilder analysis = new StringBuilder();

        // QUANTITY CHECK
        if (propQty == -1) {
            score -= 30;
            analysis.append("❌ Proposal missing quantity. ");
        } else if (propQty < rfpQty) {
            score -= 40;
            analysis.append(String.format("❌ Quantity mismatch (RFP: %d, Proposal: %d). ", rfpQty, propQty));
        } else {
            analysis.append("✔ Quantity requirement met. ");
        }

        // DELIVERY DAYS CHECK
        if (propDays == -1) {
            score -= 20;
            analysis.append("❌ Delivery time missing. ");
        } else if (propDays > rfpDays) {
            score -= 25;
            analysis.append(String.format("❌ Delivery too late (RFP: %d days, Proposal: %d days). ", rfpDays, propDays));
        } else {
            analysis.append("✔ Delivery time acceptable. ");
        }

        // BUDGET CHECK
        if (propBudget == -1) {
            score -= 20;
            analysis.append("❌ Budget missing. ");
        } else if (propBudget > rfpBudget) {
            score -= 30;
            analysis.append(String.format("❌ Over budget (RFP: %d, Proposal: %d). ", rfpBudget, propBudget));
        } else {
            analysis.append("✔ Budget within limit. ");
        }

        score = Math.max(0, Math.min(score, 100));

        return """
        {
          "score": %d,
          "analysis": "%s"
        }
        """.formatted(score, analysis.toString());
    }

    // -------------------------
    // STRUCTURED DATA EXTRACTOR (RETURN DUMMY JSON)
    // -------------------------
    public String extractStructuredDataFromText(String text) {
        return """
        {
          "item": "Laptop",
          "quantity": "40",
          "delivery_time": "20 days",
          "budget": "50000"
        }
        """;
    }

    // -------------------------
    // REGEX FUNCTIONS
    // -------------------------
    private int extractNumber(String text) {
        Matcher m = Pattern.compile("(\\d+)").matcher(text);
        return m.find() ? Integer.parseInt(m.group(1)) : -1;
    }

    private int extractDays(String text) {
        Matcher m = Pattern.compile("(\\d+)\\s*days").matcher(text.toLowerCase());
        return m.find() ? Integer.parseInt(m.group(1)) : -1;
    }

    private int extractBudget(String text) {
        Matcher m = Pattern.compile("(\\d{4,})").matcher(text);
        return m.find() ? Integer.parseInt(m.group(1)) : -1;
    }

    public String testAI() {
        return """
        {
          "score": 100,
          "analysis": "Improved scoring engine active"
        }
        """;
    }
}
