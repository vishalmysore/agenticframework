package io.github.vishalmysore.agents;

import com.t4a.processor.AIProcessingException;
import com.t4a.processor.GeminiV2ActionProcessor;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import lombok.extern.java.Log;

/**A Learning Agent is a type of artificial intelligence agent that improves its performance
 * and decision-making capabilities over time by learning from its experiences. It uses machine
 * learning algorithms to learn from the results of its actions and improve its future actions based on
 * what it has learned.
 * In the context of this project, if we wanted to implement a Learning Agent, we would need to define a
 * learning algorithm that can learn from the results of previous interactions and improve the execution
 * of actions. This could involve using reinforcement learning, supervised learning, or
 * other machine learning techniques to learn from the feedback received from users or the environment.
 */
@Log
public class LearningAgent extends Agent {

    private GeminiV2ActionProcessor processor;
    private History history;

    public LearningAgent() {
        processor = new GeminiV2ActionProcessor();
        history = new History();
        log.info("LearningAgent constructor");
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
                        String goal = processor.query(query + "\n past context is here " + history.getHistory());
                        history.addInteraction(query, goal);
                        reply.setPerformative(ACLMessage.INFORM);
                        // Learn from the previous interactions and improve the execution of actions
                        learnAndImprove(goal);
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
     * This method could learn from the previous interactions and improve the execution of actions.
     * @param goal
     */
    private void learnAndImprove(String goal) {
        // Implement your learning logic here
        // For example, you might use a machine learning algorithm to learn from the results of
        // previous actions and improve future actions
        // or based on user feedback thumpsup or thumps down you can improve the execution of actions
    }
}