package chat.comm;

import chat.IFchat.ChatAgentIF;
import chat.IFchat.ChatToolIF;
import java.util.HashMap;
import java.util.Map;

//==============================================================================
public class ChatHandler implements ChatAgentIF {

    static private Map<String, ChatToolIF> tools = new HashMap<String, ChatToolIF>();
    private static int counter = 0;
    private static final String prefix = "User";
    private String id;

//    static synchronized private String generateId(){
//        return prefix+ ++counter;
//    }
    //==================================================
    /**
     * Restituisce la descrizione del ruolo dell'Agente
     *
     * @return Descrizione del Ruolo
     */
    public String getDescription() {
        return "Chat Agent";
    }

    //============================================================================
    /**
     * Crea una nuova istanza di ChatHandler.
     *
     * @param conn La connessione per interagire con il Server.
     * @param debug Flag per i messaggi di Output.
     */
    public ChatHandler(Object conn, boolean debug) {
//        this.id = generateId();
        this.id = prefix + ++counter;
    }

    //===================================================
    @Override
    public void setChatTool(ChatToolIF gui) {
        this.tools.put(id, gui);
    }


//==================================================
    /**
     * Invia una richiesta di inquiry. La richiesta viene ovviamente fatta in
     * broadcast.
     */
    public void peerInquiry() {
        for (Map.Entry<String, ChatToolIF> entrySet : tools.entrySet()) {
            String key = entrySet.getKey();
            ChatToolIF value = entrySet.getValue();
            if (tools.containsKey(id) && !id.equals(key)) {
                value.peerNotify(id);
            }
        }
    }

    //==================================================
    public void sendMessage(String target, String message) {
        if (target == null) {
            for (Map.Entry<String, ChatToolIF> entrySet : tools.entrySet()) {
                String key = entrySet.getKey();
                ChatToolIF value = entrySet.getValue();
                if (!id.equals(key)) {
                    value.appendMessage(false, id, message);
                }
            }
        } else {
            try {
                tools.get(target).appendMessage(true, id, message);
            } catch (NullPointerException e) {
                ChatToolIF gui = (ChatToolIF) tools.get(id);
                gui.appendMessage(true, target, "[OFFLINE] I won't be able to read your message");
            }
        }
    }

    //============================================================================
    /**
     * Effettua il deregister dell'agente ed esce.
     */
    @Override
    public void endSession() {
        ChatHandler.removeUser(id);
        if(tools.isEmpty())
            System.exit(0);
        System.out.println("cache utenti " + tools.size());
    }

    @Override
    public String getId() {
        return id;
    }

    public static void removeUser(String id) {
        tools.remove(id);
    }

}
