import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Contatti {

        // Array dinamico di oggetti di tipo Contatto, richiamabile ovunque
        private static ArrayList<Contatto> rubrica = new ArrayList<Contatto>();   // Creo un vettore dinamico di oggetti contatto

    public static void main(String[] args) {

        // Creazione frame e panello

        JFrame frame = new JFrame("Contatti");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 10, 10));

        // Pannello di sinistra
        JPanel lista = new JPanel();
        lista.setLayout(new BorderLayout());

        // Pannello di destra
        JPanel visualizer = new JPanel();
        visualizer.setLayout(new GridLayout(6, 1, 10 ,10));

        // Creazione componenti

        DefaultListModel modello = new DefaultListModel(); // Creo un modello della default list model in modo da renderla dinamica
        JList contatti = new JList(modello);
        JButton nuovo = new JButton("Aggiungi");
        JButton modifica = new JButton("Modifica"); modifica.setVisible(false);
        JButton conferma = new JButton("Conferma"); conferma.setVisible(false);
        JTextField nome = new JTextField(); nome.setEditable(false);
        JTextField cognome = new JTextField(); cognome.setEditable(false);
        JTextField numero = new JTextField(); numero.setEditable(false);
        JButton registra = new JButton("Registra"); registra.setVisible(false);

        // Aggiunta pannelli

        lista.add(contatti, "Center");
        lista.add(nuovo, "South");
        visualizer.add(new JLabel("Nome:"));
        visualizer.add(nome);
        visualizer.add(new JLabel("Cognome:"));
        visualizer.add(cognome);
        visualizer.add(new JLabel("Numero:"));
        visualizer.add(numero);
        visualizer.add(modifica);
        visualizer.add(conferma);
        visualizer.add(registra);

        //  Sezione Ascoltatori

        conferma.addActionListener(new Gestore(modello, nome, cognome, numero, registra, nuovo, contatti, modifica, conferma));
        modifica.addActionListener(new Gestore(modello, nome, cognome, numero, registra, nuovo, contatti, modifica, conferma));
        contatti.addListSelectionListener(new Gestore(modello, nome, cognome, numero, registra, nuovo, contatti, modifica, conferma));
        nuovo.addActionListener(new Gestore(modello, nome, cognome, numero, registra, nuovo, contatti, modifica, conferma));
        registra.addActionListener(new Gestore(modello, nome, cognome, numero, registra, nuovo, contatti, modifica, conferma));

        // Sezione Pannello principale e Frame

        panel.add(lista);
        panel.add(visualizer);
        frame.setVisible(true);
        frame.getContentPane().add(panel);
        frame.setSize(320, 320);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    // Classe Gestore utile a gestire gli eventi

    public static class Gestore implements ActionListener, ListSelectionListener {

        // Attributi

        private DefaultListModel model;
        private JList lista;
        private JTextField nome;
        private JTextField cognome;
        private JTextField numero;
        private JButton registra, aggiungi, modifica, conferma;

        // Costruttore

        public Gestore(DefaultListModel model, JTextField nome, JTextField cognome, JTextField numero, JButton registra, JButton aggiungi, JList lista, JButton modifica, JButton conferma){
            this.model = model;
            this.nome = nome;
            this.cognome = cognome;
            this.numero = numero;
            this.registra = registra;
            this.aggiungi = aggiungi;
            this.modifica = modifica;
            this.conferma = conferma;
            this.lista = lista;
        }

        // Gestore bottoni, in base al bottone premuto esegue del codice diverso

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();

            if(s.equals("Aggiungi")){
                nome.setText("");
                cognome.setText("");
                numero.setText("");
                nome.setEditable(true);
                cognome.setEditable(true);
                numero.setEditable(true);
                registra.setVisible(true);
                registra.setEnabled(true);
                aggiungi.setEnabled(false);
                modifica.setVisible(false);
            }
            else if(s.equals("Registra")){
                String name, surname, number;

                name = nome.getText();
                surname = cognome.getText();
                number = numero.getText();
                nome.setText("");
                cognome.setText("");
                numero.setText("");


                Contatto user = new Contatto(name, surname, number);
                model.addElement(name + " " + surname);
                rubrica.add(user);
                registra.setEnabled(false);
                registra.setVisible(false);
                aggiungi.setEnabled(true);
                nome.setEditable(false);
                cognome.setEditable(false);
                numero.setEditable(false);
            }
            else if(s.equals("Modifica")){
                nome.setEditable(true);
                cognome.setEditable(true);
                numero.setEditable(true);
                conferma.setVisible(true);
                modifica.setVisible(false);
                conferma.setVisible(true);

            }
            else if(s.equals("Conferma")){
                modifica.setVisible(true);
                conferma.setVisible(false);
                int i = lista.getSelectedIndex();
                Contatto user = rubrica.get(i);
                user.setNome(nome.getText());
                user.setCognome(cognome.getText());
                user.setNumero(numero.getText());
                rubrica.set(i, user);
                model.setElementAt(user.getNome() + " " + user.getCognome(), i);
                nome.setEditable(false);
                cognome.setEditable(false);
                numero.setEditable(false);
            }
        }

        // Gestore della lista

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int i = lista.getSelectedIndex();
            modifica.setVisible(true);

            Contatto[] vet = rubrica.toArray(new Contatto[0]); // Non so che cosa faccia però funziona così

            nome.setText(vet[i].getNome());
            cognome.setText(vet[i].getCognome());
            numero.setText(vet[i].getNumero());
            }
        }
    }

    // Classe Contatto, essenzialmente tutto ciò che è un contatto

    class Contatto{
        // Attributi
        private String nome;
        private String cognome;
        private String numero;

        //  Costruttore con parametri
        public Contatto(String nome, String cognome, String numero){
            this.nome = nome;
            this.cognome = cognome;
            this.numero = numero;
        }

        // Costruttore senza parametri

        public Contatto(){

        }

        // Metodi set del Contatto

        public void setNome(String nome){this.nome = nome;}
        public void setCognome(String cognome){this.cognome = cognome;}
        public void setNumero(String numero){this.numero = numero;}

        // Metodi get del Contatto

        public String getNome(){return this.nome;}
        public String getCognome(){return this.cognome;}
        public String getNumero(){return this.numero;}
    }

