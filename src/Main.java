import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Map<Integer, Client> clients = new HashMap<>();
        Client c1 = new ClientFidele(1, "mohamed", "mohamed@gmail.com", "Bronze");
        Client c2 = new ClientFidele(2, "adam", "adam@gmail.com", "Silver");
        Client c3 = new ClientFidele(3, "saad", "saad@gmail.com", "Gold");

        c1.getAchats().add(new Achat(100, LocalDate.now()));
        c1.getAchats().add(new Achat(50, LocalDate.now()));

        c2.getAchats().add(new Achat(300, LocalDate.now()));
        c2.getAchats().add(new Achat(300, LocalDate.now()));

        clients.put(c1.getId(), c1);
        clients.put(c2.getId(), c2);
        clients.put(c3.getId(), c3);

        System.out.println("=== CLIENTS CRÉÉS ===");
        clients.values().forEach(System.out::println);

        // Test exception pour historique vide
        try {
            if (c3.getAchats().isEmpty()) {
                throw new HistoriqueAchatVideException("Le client " + c3.getNom() + " n'a aucun achat !");
            }
        } catch (HistoriqueAchatVideException e) {
            System.out.println(" EXCEPTION CAPTURÉE : " + e.getMessage());
        }

        // Calcul du total par client
        System.out.println("\n=== TOTAL DES ACHATS PAR CLIENT ===");
        clients.values().forEach(cl -> {
            double totalAchats = cl.getAchats().stream().mapToDouble(Achat::getMontant).sum();
            System.out.println(cl.getNom() + " a dépensé : " + totalAchats + "€");
        });

        // Filtrer les clients Gold (>500€)
        List<Client> goldClients = clients.values().stream()
                .filter(cl -> cl.getAchats().stream().mapToDouble(Achat::getMontant).sum() > 500)
                .collect(Collectors.toList());
        System.out.println("\n=== CLIENTS GOLD ===");
        goldClients.forEach(System.out::println);

        // Trier les clients par total décroissant
        List<Client> sorted = clients.values().stream()
                .sorted((cl1, cl2) -> Double.compare(
                        cl2.getAchats().stream().mapToDouble(Achat::getMontant).sum(),
                        cl1.getAchats().stream().mapToDouble(Achat::getMontant).sum()))
                .collect(Collectors.toList());
        System.out.println("\n=== CLIENTS TRIÉS PAR ACHATS DÉCROISSANTS ===");
        sorted.forEach(System.out::println);
    }
}
