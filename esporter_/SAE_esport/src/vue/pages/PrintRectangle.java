package vue.pages;

import Modele.*;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PrintRectangle implements Printable {
    private String resultat;
    private static final int maxLineLength = 120;

    /** Constructeur par défaut de PrintRectangle */
    public PrintRectangle(String resultat) {
        this.resultat = resultat;
    }


    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        // Par défaut, retourne NO_SUCH_PAGE => la page n'existe pas
        int retValue = Printable.NO_SUCH_PAGE;
        if (pageIndex == 0) {// Dessin de la première page
            // Récupère la dimension de la zone imprimable
            double xLeft = pageFormat.getImageableX();
            double yTop = pageFormat.getImageableY();
            double width = pageFormat.getImageableWidth() - 25;
            double height = pageFormat.getImageableHeight() - 40;

            // Dessine le rectangle
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Monospace", Font.PLAIN, 12));
            int yOffset = 40;
            String line = "";
            for (int i = 0; i < resultat.length(); i++) {
                if (resultat.charAt(i) != '\n') {
                    line = line + resultat.charAt(i);
                } else if (line.length() > maxLineLength) {
                    graphics.drawString(line.substring(0, maxLineLength), (int) xLeft + 25, yOffset);
                    line = line.substring(maxLineLength);
                    yOffset += 10;
                } else {
                    graphics.drawString(line, (int) xLeft + 25, yOffset);
                    line = "";
                    yOffset += 10;
                }
            }
            // La page est valide
            retValue = Printable.PAGE_EXISTS;
        }
        return retValue;

    }

    private static String getSaisonResultat(Saison saison) {
        String resultat = "";
        resultat += "Saison " + saison.getAnnee() + " : " + saison.getNom() + "\n\n";
        resultat += "Classement de la saison :\n";
        List<Equipe> e = saison.getClassement().keySet().stream().sorted((e1, e2) -> saison.getClassement().get(e2) - saison.getClassement().get(e1)).collect(Collectors.toList());
        for (Equipe equipe : e) {
            String lin = "| " + equipe.getFullName();
            String res = "| " + saison.getClassement().get(equipe) + "pts |\n";
            for (int i = 0; i < 60 - lin.length() + res.length(); i++) {
                lin += "  ";
            }
            lin += res;
            resultat += lin;
        }
        resultat += "\n\n";
        resultat += "Liste des tournois de la saison :\n";

        List<Tournoi> tournois = Objects.requireNonNull(Tournoi.getBySaison(saison.getAnnee())).stream().sorted((t1, t2) -> new CustomDateTime(t1.getDateDebut()).compareTo(new CustomDateTime(t2.getDateDebut()))).collect(Collectors.toList());
        for (Tournoi tournoi : tournois) {
            if (tournoi.isFinished()) {
                for (Match match : tournoi.getListMatchs()) {
                    if (match.getType() == TypeMatch.FINALE) {
                        resultat += "| " + tournoi.getNom() + " : Vainqueur : " + (match.getScore(match.getEquipe1()) == Resultat.VICTOIRE ? match.getEquipe1().getFullName() : match.getEquipe2().getFullName()) + "\n";
                        break;
                    }
                }
            } else if (tournoi.isStarted()){
                List<Equipe> equipes = tournoi.getListEquipes().keySet().stream().sorted((e1, e2) -> saison.getClassement().get(e2) - saison.getClassement().get(e1)).collect(Collectors.toList());
                resultat += "| " + tournoi.getNom() + " : En cours, premier actuel : " + equipes.get(0).getFullName() + "\n";
            } else {
                resultat += "| " + tournoi.getNom() + " : à venir\n";
            }
        }
        return resultat;
    }

    public static void impression(Saison saison) {
        // Récupère un PrinterJob
        PrinterJob job = PrinterJob.getPrinterJob();
        // Définit son contenu à imprimer
        job.setPrintable(new PrintRectangle(getSaisonResultat(saison)));
        // Affiche une boîte de choix d'imprimante
        if (job.printDialog()){
            try {
                // Effectue l'impression
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String getTournoiResultat(Tournoi tournoi) {
        String resultat = "";
        resultat += "Tournoi : " + tournoi.getNom() + "\n\n";
        resultat += "Podium du tournoi :\n";
        int i = 0;
        for (HashMap<Equipe, Integer> mapEquipe : tournoi.getPodium()) {
            i++;
            for (Equipe equipe : mapEquipe.keySet()) {
                resultat += "| " + equipe.getFullName() + " : " + (i == 1 ? i + "er" : i + "eme") + "\n";
            }
        }
        resultat += "\n\n";
        resultat += "Classement du tournoi :\n";
        List<Equipe> e = tournoi.getListEquipes().keySet().stream().sorted((e1, e2) -> tournoi.getListEquipes().get(e2) - tournoi.getListEquipes().get(e1)).collect(Collectors.toList());
        for (Equipe equipe : e) {
            String lin = "| " + equipe.getFullName();
            String res = "| " + tournoi.getListEquipes().get(equipe) + "pts |\n";
            for (i = 0; i < 60 - lin.length() + res.length(); i++) {
                lin += "  ";
            }
            lin += res;
            resultat += lin;
        }

        resultat += "\n\n";
        resultat += "Liste des matchs du tournoi :\n";
        for (i = 0; i < 120; i++) {
            resultat += "-";
        }
        resultat += "\n";
        List<Match> matchs = tournoi.getListMatchs().stream().sorted((m1, m2) -> m1.getDateDebutMatch().compareTo(m2.getDateDebutMatch())).collect(Collectors.toList());
        Collections.reverse(matchs);
        for (Match match : matchs) {
            String lin = "| " + match.getType() + "   " + match.getEquipe1().getFullName() + "  |  " + match.getScore(match.getEquipe1()) + " - " + match.getScore(match.getEquipe2()) + "  |  " + match.getEquipe2().getFullName() + "\n";
            resultat += lin;
            for (i = 0; i < 120; i++) {
                resultat += "-";
            }
            resultat += "\n";
        }
        return resultat;
    }

    public static void impression(Tournoi tournoi) {
        // Récupère un PrinterJob
        PrinterJob job = PrinterJob.getPrinterJob();
        // Définit son contenu à imprimer
        job.setPrintable(new PrintRectangle(getTournoiResultat(tournoi))); // TODO: mettre le résultat du tournoi en forme;
        // Affiche une boîte de choix d'imprimante
        if (job.printDialog()){
            try {
                // Effectue l'impression
                job.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }

}

