package io.github.vishalmysore.agents;

import com.t4a.processor.AIProcessingException;
import com.t4a.processor.GeminiV2ActionProcessor;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.java.Log;

/**
 * A Goal-Based Agent is a type of artificial intelligence agent that operates based on specific goals.
 * It extends the concept of a simple reflex agent by considering future actions to achieve its goals.
 * This type of agent not only reacts to its current perceptual inputs but also uses a set of goal-based
 * behaviors to guide its actions. The agent maintains a set of predefined goals and chooses its actions
 * based on these goals. The goal-based agent is more advanced and can handle complex environments and
 * tasks as it has the ability to consider the future consequences of its current actions, making it
 * more flexible and intelligent in decision-making.
 *
 * GoalBasedAgent receives a user's intent as a message, determines the user's goal among a set of predefined goals
 * (represented as an enum), and sends a reply indicating the action to be taken based on the determined goal.
 * The determination of the goal is done by querying a GeminiV2ActionProcessor with the user's intent and a
 * list of all possible goals.
 */
@Log
public class GoalBasedAgent extends Agent {

    private GeminiV2ActionProcessor processor;

    public GoalBasedAgent() {

        processor = new GeminiV2ActionProcessor();
        log.info("GeminiAIAgentMulti constructor");
    }
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                // Receive the next message
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
                        //take action based on goal
                        reply.setContent("action to be taken based on goal: " + goal);
                    } catch (AIProcessingException e) {
                        reply.setContent("Failed to process the query. Please try again later.");
                        reply.setPerformative(ACLMessage.FAILURE);
                    }



                    // Send the reply
                    send(reply);
                } else {
                    block();
                }
            }
        });
    }
}
