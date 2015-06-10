package chat.IFchat;

public interface ChatToolIF {
  
  // Mostra che un nick e' disponibile
  public void peerNotify(String nick);

  // Aggiunge un messaggio alla finestra (pubblica o privata)
  public void appendMessage(boolean pvt, String nick, String message);
  
  // Setta il communication agent
  public void setChatAgent(ChatAgentIF listener);
}
