package es.sergiopt.utils;

import java.net.URL;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;

public class AyudaUtil {

    private HelpBroker helpBroker;

    public AyudaUtil() {
        try {
            URL url = getClass().getResource("/ayuda/helpset.hs");
            if (url == null) {
                System.err.println("No se encontró el archivo de ayuda en /ayuda/helpset.hs");
                return;
            }
            
            HelpSet helpSet = new HelpSet(null, url);
            helpBroker = helpSet.createHelpBroker();
            
        } catch (HelpSetException e) {
            System.err.println("Error al cargar el sistema de ayuda: " + e.getMessage());
        }
    }

    public void mostrarAyuda() {
        if (helpBroker != null) {
            try {
                helpBroker.setDisplayed(true);
            
            } catch (Exception e) {
                System.err.println("Error al mostrar la ayuda: " + e.getMessage());
            }
        }
    }

    public HelpBroker getHelpBroker() {
        return helpBroker;
    }
}
