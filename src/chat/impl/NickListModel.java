package chat.impl;

import java.util.Date;
import java.util.Hashtable;
import javax.swing.DefaultListModel;

public class NickListModel extends DefaultListModel {
  
  private Hashtable tstamps;
  //============================================================================
  /** Creates a new instance of NickTableModel */
  public NickListModel() {
    super();
    tstamps = new Hashtable();
  }

  //============================================================================
  /** Aggiunge un nick in ordine alfabateco 
   *  (Aggiorna solo la data se il nick e' gia' in lista) 
   */
  synchronized void addNick(String nick) {
    int i = 0;
    for (i = 0; i < getSize(); i++) {
      int comparison = nick.compareTo((String) getElementAt(i));
      if (comparison > 0) { // nick viene dopo quello in esame
        continue;
      } else if (comparison == 0) { // trovato !
        tstamps.put(nick, new Date());
        return;
      } else { // Non trovato
        break;
      }
    }
    add(i, nick);
    tstamps.put(nick, new Date());
  }
  
  //============================================================================
  /** Elimina i nick da cui non si e' piu' avuta risposta 
   *    da prima di landmark. Procede all'indietro, altrimenti cambierebbe
   *    la posizione per gli elementi successivi. 
   */
  synchronized void purgeNicks(long landmark) {
    for (int i = getSize()-1; i >= 0; i--) {
      if (((Date)tstamps.get(get(i))).getTime() < landmark)
        remove(i);      
    }
  }
  
}
