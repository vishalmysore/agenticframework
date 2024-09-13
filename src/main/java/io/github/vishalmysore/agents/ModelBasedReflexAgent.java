package io.github.vishalmysore.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * A model-based reflex agent that maintains an internal model of the world
 * and chooses actions based on the current percept and the history of past percepts.
 * The agent's internal model of the world is represented by a state file.
 */
public class ModelBasedReflexAgent extends Agent {
    private static final String STATE_FILE = "state.txt";

    /**
     * Sets up the agent's behaviours. In this case, the agent has a single cyclic behaviour
     * that receives messages, updates the state file based on the content of the messages,
     * and sends a reply.
     */
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            /**
             * Defines the agent's actions. The agent receives the next message, writes the
             * message content to the state file, reads the entire state file to determine its
             * history, reacts to the message based on the current state and history, and sends a reply.
             */
            @Override
            public void action() {
                // Receive the next message
                ACLMessage msg = receive();

                if (msg != null) {
                    // Write the message content to the state file
                    try {
                        Files.write(Paths.get(STATE_FILE), msg.getContent().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Read the entire state file
                    String history = null;
                    try {
                        history = new String(Files.readAllBytes(Paths.get(STATE_FILE)));
                        //this will be passed to AI as context
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // React to the message based on the current state and history
                    // ...

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