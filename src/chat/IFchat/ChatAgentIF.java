package chat.IFchat;


public interface ChatAgentIF {
  
  // Recupera l'ID dell'Agent
  public String getId();
  
  // Invia un messaggio (broadcast oppure ad un target)
  public void sendMessage(String target, String message);
  
  // Invia le richieste di presenza
  public void peerInquiry();

  // Termina la sessione (deregister)
  public void endSession();
  
  // Setta il GUI Tool
  public void setChatTool(ChatToolIF tool);
}
