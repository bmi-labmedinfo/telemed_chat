package chat;

import chat.comm.ChatHandler;

//========================================
/**
 * La Classe principale che integra le varie componenti della applicazione.
 * Consente di attivare i vari agenti (qualora ce ne fossero) con una unica
 * chiamata differenziandoli in funzione dei parametri che vengono dati sulla
 * riga di input.
 *
 * @author Giordano Lanzola (giordano@aim.unipv.it)
 */
class Main {

    //========================================
    // PARTE STATIC DELLA CLASSE
    //========================================
    /**
     * Metodo main()
     */
    public static void main(String[] args) {
        int howmany = 0;
        // Blocco di controllo della stringa di argomento.
        try {
            if (args.length != 1) {
                throw new Exception();
            }
            howmany = Integer.parseInt(args[0]);
        } catch (Exception ex) {
            System.out.println("Uso: Main number ");
            System.exit(0);
        }

        for (int i = 0; i < howmany; i++) {
            // Creo Chat handler
            final ChatHandler ca = new ChatHandler(null, false);
// Creo ChatFrame, e imposto reciprocamente le referenze
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    chat.IFchat.ChatToolIF tool = new chat.impl.ChatFrame();
                    tool.setChatAgent(ca);
                    ca.setChatTool(tool);
                }
            });
        }
    }
}
