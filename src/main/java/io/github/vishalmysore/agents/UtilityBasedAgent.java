package io.github.vishalmysore.agents;



import com.t4a.processor.AIProcessingException;
import com.t4a.processor.GeminiV2ActionProcessor;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.java.Log;

/**
 * A Utility-Based Agent is a type of artificial intelligence agent that not only considers the goal
 * to achieve but also measures how beneficial or useful the goal is. It uses a utility function to map
 * each state to a measure of its utility, or usefulness. The agent's objective is to maximize the total
 * utility over a sequence of actions. The utility function is used to rank the different possible actions
 * based on their expected outcomes.
 * In the context of this project, if we wanted to implement a
 * Utility-Based Agent, we would need to define a utility function that can measure the usefulness
 * of each goal. This could be based on various factors such as the urgency of the goal,
 * the resources required to achieve the goal, the potential benefits of achieving the goal, etc
 *
 */
@Log
public class UtilityBasedAgent extends Agent {

    private GeminiV2ActionProcessor processor;

    public UtilityBasedAgent() {
        processor = new GeminiV2ActionProcessor();
        log.info("UtilityBasedAgent constructor");
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
                        int utility = calculateUtility(goal); // calculate the utility of the goal
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("action to be taken based on goal: " + goal + ", utility: " + utility);
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

    private int calculateUtility(String goal) {
        // Implement your utility function here
        // For example, you might assign different utility values to different goals
        switch (goal) {
            case "Book a hotel":
                return 10;
            case "Book an airline ticket":
                return 20;
            // add more cases for other goals
            default:
                return 0;
        }
    }
}