package io.github.vishalmysore.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * A meta-agent that selects an agent based on the current
 * percept and forwards the percept to the selected agent.
 */
public class MetaAgent extends Agent {

    private ReflexAgent simpleReflexAgent;
    private UtilityBasedAgent utilityBasedAgent;
    private LearningAgent learningAgent;

    public MetaAgent() {
        simpleReflexAgent = new ReflexAgent();
        utilityBasedAgent = new UtilityBasedAgent();
        learningAgent = new LearningAgent();
    }

    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = receive();

                if (msg != null) {
                    String percept = msg.getContent();
                    String agentName = selectAgent(percept).getLocalName();
                    msg.addReceiver(new jade.core.AID(agentName, jade.core.AID.ISLOCALNAME));
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Action based on percept: " + agentName);

                    send(reply);
                } else {
                    block();
                }
            }
        });
    }

    /**
     * This method selects an agent based on the current percept.
     * @param percept
     * @return agent
     */
    private Agent selectAgent(String percept) {
        // Implement our logic here to select an agent based on the percept
        // For example, if the percept is "It's raining", you might select the UtilityBasedAgent
        // If the percept is "It's sunny", you might select the SimpleReflexAgent
        // If the percept is "It's cloudy", you might select the LearningAgent
        return null;
    }
}
