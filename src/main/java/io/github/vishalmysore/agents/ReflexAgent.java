package io.github.vishalmysore.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * A reflex agent is a type of AI agent that chooses actions based solely on the current
 * percept, without considering the history of past percepts. It's a simple type of agent that
 * reacts to its current environment without any consideration of the consequences of its actions.
 */
public class ReflexAgent extends Agent {
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                // Receive the next message
                ACLMessage msg = receive();

                if (msg != null) {
                    // React to the message
                    System.out.println("Received message: " + msg.getContent());

                    // Create a reply
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Message received");

                    // Send the reply
                    send(reply);
                } else {
                    block();
                }
            }
        });
    }
}
