package io.github.vishalmysore.agents;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<String> interactions;

    public History() {
        interactions = new ArrayList<>();
    }

    public void addInteraction(String query, String reply) {
        interactions.add("Query: " + query + ", Reply: " + reply);
    }

    public String getHistory() {
        return String.join("\n", interactions);
    }
}