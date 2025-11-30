import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Map<Integer, Client> clients = databaseConn.recupererDonnees();
        Client.exporterGoldJSON(clients, "gold_clients.json");

        try (Scanner scanner = new Scanner(System.in)) {
            int choix;
            do {
                System.out.println("\033[1;36m");
                System.out.println("╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║                  GESTION DES CLIENTS FIDÈLES                 ║");
                System.out.println("╠══════════════════════════════════════════════════════════════╣");
                System.out.println("║ \033[0m\033[1;32m1\033[0m - Afficher les clients                                     \033[1;36m║");
                System.out.println("║ \033[0m\033[1;32m2\033[0m - Ajouter un client                                        \033[1;36m║");
                System.out.println("║ \033[0m\033[1;32m3\033[0m - Ajouter un achat                                         \033[1;36m║");
                System.out.println("║ \033[0m\033[1;32m4\033[0m - Calculer total des achats d'un client                    \033[1;36m║");
                System.out.println("║ \033[0m\033[1;32m5\033[0m - Exporter JSON des clients Gold                           \033[1;36m║");
                System.out.println("║ \033[0m\033[1;32m6\033[0m - Trier les clients par ordre décroissant des achats       \033[1;36m║");
                System.out.println("║ \033[0m\033[1;32m7\033[0m - Afficher les clients ayant dépensé plus de 500€          \033[1;36m║");
                System.out.println("║ \033[0m\033[1;32m8\033[0m - Afficher tous les achats                                 \033[1;36m║");
                System.out.println("║ \033[0m\033[1;31m0\033[0m - Quitter                                                  \033[1;36m║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝");
                System.out.print("\033[0m➤ Votre choix : ");
                choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1 -> clients.values().forEach(System.out::println);
                    case 2 -> {
                        System.out.println("saisir nom du client :  ");
                        String nom = scanner.nextLine();
                        System.out.println("saisir email du client :  ");
                        String email = scanner.nextLine();
                        String nv;
                        do {
                            System.out.println("saisir niveau de fidelete du client (bronze - silver - gold):  ");
                            nv = scanner.nextLine();
                        } while (!nv.equals("gold") && !nv.equals("silver") && !nv.equals("bronze"));
                        databaseConn.ajouterclient(nom, email, nv);
                        clients = databaseConn.recupererDonnees();
                    }
                    case 3 -> {
                        System.out.print("id client : ");
                        int idClient = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Montant : ");
                        double montant = scanner.nextDouble();
                        scanner.nextLine();
                        databaseConn.ajouterAchat(montant, idClient, LocalDate.now());
                        clients = databaseConn.recupererDonnees();
                    }
                    case 4 -> {
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
                    }
                    case 5 -> Client.exporterGoldJSON(clients, "gold_clients2.json");
                    case 6 -> Client.trierClientDec(clients);
                    case 7 -> Client.clientPlus500(clients);
                    case 8 -> {
                        System.out.println("\n=== TOUS LES ACHATS ===");
                        for (Client c : clients.values()) {
                            System.out.println("Achats de " + c.getNom() + ":");
                            if (c.getAchats().isEmpty()) {
                                System.out.println("  Aucun achat.");
                            } else {
                                for (Achat a : c.getAchats()) {
                                    System.out.println("  - " + a.getMontant() + "€ le " + a.getDateAchat());
                                }
                            }
                        }
                    }
                    case 0 -> System.out.println("AU REVOIR !");
                    default -> System.out.println("Choix invalide !");
                }
            } while (choix != 0);
        }
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
