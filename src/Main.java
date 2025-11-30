import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = databaseConn.getConnection();

        Map<Integer, Client> clients=databaseConn.recupererDonnees();

        Client.exporterGoldJSON(clients, "gold_clients.json");
        //clients.values().forEach(System.out::println);
        //databaseConn.ajouterclient("elmahi","saad@gmail.com","gold");
        //databaseConn.ajouterAchat(23.21, 2, LocalDate.now());*
        Scanner scanner= new Scanner(System.in);
        int choix=1;
        do{


            System.out.println("\033[1;36m");
            System.out.println("╔══════════════════════════════════════════════════════════════╗");
            System.out.println("║                  GESTION DES CLIENTS FIDÈLES                 ║");
            System.out.println("╠══════════════════════════════════════════════════════════════╣");
            System.out.println("║ \033[0m\033[1;32m1\033[0m - Afficher les clients                                     \033[1;36m║");
            System.out.println("║ \033[0m\033[1;32m2\033[0m - Ajouter un client                                        \033[1;36m║");
            System.out.println("║ \033[0m\033[1;32m3\033[0m - Ajouter un achat                                         \033[1;36m║");
            System.out.println("║ \033[0m\033[1;32m4\033[0m - Calculer total des achats d'un client                    \033[1;36m║");
            System.out.println("║ \033[0m\033[1;32m5\033[0m - Exporter JSON des clients Gold                           \033[1;36m║");
            System.out.println("║ \033[0m\033[1;31m0\033[0m - Quitter                                                  \033[1;36m║");
            System.out.println("╚══════════════════════════════════════════════════════════════╝");
            System.out.print("\033[0m➤ Votre choix : ");
        choix = scanner.nextInt();
        scanner.nextLine();
        switch(choix) {
            case 1:
                clients.values().forEach(System.out::println);
                break;
            case 2:
                System.out.println("saisir nom du client :  ");
                String nom = scanner.nextLine();
                System.out.println("saisir email du client :  ");
                String email = scanner.nextLine();

                String nv;
                do {
                    System.out.println("saisir niveau de fidelete du client (bronze - silver - gold):  ");
                    nv = scanner.nextLine();
                } while (!nv.equals("gold") && !nv.equals("silver") && !nv.equals("bronze") );
                databaseConn.ajouterclient(nom, email, nv);
                //mise a j collection clients
                clients=databaseConn.recupererDonnees();
                break;
            case 3:
                System.out.print("id client : ");
                int idClient = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Montant : ");
                double montant = scanner.nextDouble();
                scanner.nextLine(); // fix

                databaseConn.ajouterAchat(montant, idClient, LocalDate.now());
                clients = databaseConn.recupererDonnees();
                break;

            case 4: // Total achats d'un client
                System.out.print("ID du client : ");
                int idC = scanner.nextInt();
                scanner.nextLine();

                Client client = clients.get(idC);
                if (client != null) {
                    try {
                        System.out.println("Total : " + client.calculerTotal() + " €");
                    } catch (HistoriqueAchatVideException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Client introuvable !");
                }
                break;
            case 5:
            Client.exporterGoldJSON(clients, "gold_clients.json");
            break;
            case 0:
                System.out.println("AU REVOIR !");
                break;

            default:
                System.out.println("Choix invalide !");


        }
        }while(choix!=0);
        scanner.close();
      /*



        // Test exception pour historique vide
        try {
            if (c3.getAchats().isEmpty()) {
                throw new HistoriqueAchatVideException("Le client " + c3.getNom() + " n'a aucun achat !");
            }
        } catch (HistoriqueAchatVideException e) {
            System.out.println(" EXCEPTION CAPTURÉE : " + e.getMessage());
        }






        */

    }
}
