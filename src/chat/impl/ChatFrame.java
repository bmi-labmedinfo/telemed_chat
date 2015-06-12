package chat.impl;

import chat.IFchat.ChatAgentIF;
import chat.IFchat.ChatToolIF;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatFrame extends javax.swing.JFrame implements ChatToolIF {

    /**
     * Pointer to the public panel always available
     */
    private PublicPanel publicpanel;

    /**
     * Pointer to the network agent
     */
    private ChatAgentIF agent;


  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jTabbedPane1 = new javax.swing.JTabbedPane();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Telemedicina: Chat Agent");
    setBackground(new java.awt.Color(255, 0, 51));
    setForeground(new java.awt.Color(0, 204, 204));
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosed(java.awt.event.WindowEvent evt) {
        endSession(evt);
      }
    });

    jTabbedPane1.setBackground(new java.awt.Color(233, 236, 111));
    getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

  private void endSession(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_endSession
// TODO add your handling code here:
      dispose();
      agent.endSession();
  }//GEN-LAST:event_endSession

    //============================================================================
    /**
     * Costruttore.
     *
     * @param listener The Agent (may be null)
     */
    public ChatFrame() {
        initComponents();
        this.getContentPane().setBackground(Color.WHITE);
        publicpanel = new PublicPanel(ChatFrame.this);
        jTabbedPane1.add("Public", publicpanel);
        this.pack();
        this.setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    //============================================================================
    /**
     * Notifica la presenza di un utente al publicpanel.
     */
    public void peerNotify(String nick) {
        publicpanel.peerAdvertise(nick);
    }

    //============================================================================
    /**
     * Notifica un nuovo messaggio.
     */
    public void appendMessage(boolean pvt, String nick, String message) {
        if (!pvt) {
            publicpanel.appendMessage(nick, message);
        } else {
            PrivatePanel privatepanel = null;

      // La synchro non serve, poiche' il thread di dispatching dei
            //    messaggi sappiamo che e' uno solo.
            for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
                if (jTabbedPane1.getTitleAt(i).equals(nick)) {
                    privatepanel = (PrivatePanel)jTabbedPane1.getComponentAt(i);
                    break;
                }
            }
            if (privatepanel == null) {
                privatepanel = new PrivatePanel(nick, this);
                jTabbedPane1.add(nick, privatepanel);
            }
            jTabbedPane1.setSelectedComponent(privatepanel);
            privatepanel.appendMessage(nick, message);
        }
       
    }

    //============================================================================
    /**
     * Imposta il handler (i.e. l'agente) se non e' stato impostato dal
 costruttore.
     */
    public void setChatAgent(ChatAgentIF handler) {
        this.agent = handler;
        if (handler != null) {
            publicpanel.setMyNick(handler.getId());
            this.setTitle(this.getTitle() + ":" + handler.getId());
        }
    }


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTabbedPane jTabbedPane1;
  // End of variables declaration//GEN-END:variables

    //============================================================================
    /**
     * Richiede su canale di comunicazione un aggiornamento della lista utenti.
     */
    void doUpdateUsers() {
        agent.peerInquiry();
    }

    //============================================================================
    /**
     * Invia un messaggio sul canale di comunicazione.
     */
    void sendMessage(String target, String message) {
        agent.sendMessage(target, message);
    }

    //============================================================================
    /**
     * Chiude un pannello privato
     */
    void closePanel(PrivatePanel panel) {
        jTabbedPane1.setSelectedComponent(publicpanel);
        jTabbedPane1.remove(panel);
    }


    public ChatAgentIF getAgent() {
        return agent;
    }
}
