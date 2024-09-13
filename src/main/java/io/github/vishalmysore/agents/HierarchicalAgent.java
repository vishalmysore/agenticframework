package io.github.vishalmysore.agents;

import com.t4a.processor.AIProcessingException;
import com.t4a.processor.GeminiV2ActionProcessor;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.java.Log;

/**
 * This agent is responsible for executing the subtasks of the goal in a hierarchical manner.
 * A Hierarchical Agent is a type of artificial intelligence agent that operates based on a hierarchy of
 * goals or tasks. It breaks down complex tasks into simpler subtasks, forming a hierarchy.
 * The agent starts from the top of the hierarchy and works its way down, executing the subtasks in order.
 * This type of agent is useful in situations where tasks can naturally be decomposed into subtasks.
 */
@Log
public class HierarchicalAgent extends Agent {

    private GeminiV2ActionProcessor processor;

    public HierarchicalAgent() {
        processor = new GeminiV2ActionProcessor();
        log.info("HierarchicalAgent constructor");
    }

    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive();

                if (msg != null) {
                    String userIntent =  msg.getContent();
                    StringBuilder goalsList = new StringBuilder();
                    for (Goals goal : Goals.values()) {
                        goalsList.append(goal.getDescription()).append(", ");
                    }

                    if (goalsList.length() > 0) {
                        goalsList.setLength(goalsList.length() - 2); // remove last comma and space
                    }

                    String query = "this is the user query " + userIntent + " what is user goal among these: " + goalsList.toString();
                    ACLMessage reply = msg.createReply();

                    try {
                        String goal = processor.query(query);
                        reply.setPerformative(ACLMessage.INFORM);
                        // Execute the subtasks of the goal in a hierarchical manner
                        executeSubtasks(goal);
                        reply.setContent("action to be taken based on goal: " + goal);
                    } catch (AIProcessingException e) {
                        reply.setContent("Failed to process the query. Please try again later.");
                        reply.setPerformative(ACLMessage.FAILURE);
                    }

                    send(reply);
                } else {
                    block();
                }
            }
        });
    }

    /**
     * This method could learn from the previous interactions and improve the execution of subtasks.
     * or it could get context from the user and execute the subtasks accordingly.
     * @param goal
     */
    private void executeSubtasks(String goal) {
        // Implement  logic here to break down the goal into subtasks and execute them in a hierarchical manner
        // For example, if the goal is "Plan a trip", the subtasks might be "Book a flight", "Book a hotel", "Plan sightseeing", etc.
    }
}