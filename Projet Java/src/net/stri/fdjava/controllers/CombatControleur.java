package net.stri.fdjava.controllers;

import java.util.ArrayList;
import java.util.List;

import net.stri.fdjava.models.entity.Heros;
import net.stri.fdjava.models.entity.Monstre;
import net.stri.fdjava.models.world.Combat;
import net.stri.fdjava.utils.Nombre;

public class CombatControleur {

	private Heros heros;
	private DonjonController controleur;

	public CombatControleur(Heros heros, DonjonController controleur) {
		super();
		this.heros = heros;
		this.controleur = controleur;
	}

	private Combat combat() {
		return this.controleur.getCombatActuel();
	}

	public boolean estTermine() {
		return this.controleur.getCombatActuel() == null || combat().getNombreMonstres() == 0;
	}

	public String attaqueHeros() {
		int degatsInflige = combat().herosAttaque();
		boolean mort = false;
		if (combat().getMonstres().get(0).getPtsVie() <= 0) {
			combat().tuer(0);
			mort = true;
		}
		String text = String.valueOf(degatsInflige) + " points de dégats.§f";
		if (mort) {
			text += "\n" + "§aVous avez tué le monstre!";
			int pieceOr = Nombre.nombreAleatoire(50, 100);
			text += "\n" + "§fVous gagnez également §e" + pieceOr + " §fpièces d'or !";
			this.heros.ajouterPieceOr(pieceOr);
		}
		return text;
	}

	public String attaquerMonstre() {
		int degat = combat().monstreAttaque();
		return String.valueOf(degat);
	}

	public String attaqueHeros(int num) {
		int degatsInflige = this.controleur.getCombatActuel().herosAttaque(num);
		boolean mort = false;
		Monstre m = combat().getMonstres().get(num);
		if (m.getPtsVie() <= 0) {
			combat().tuer(m);
			mort = true;
		}
		String text = String.valueOf(degatsInflige) + " points de dégats.§f";
		if (mort) {
			text += "\n" + "§aVous avez tué le monstre!";
			int pieceOr = Nombre.nombreAleatoire(50, 100);
			text += "\n" + "§fVous gagnez §e" + pieceOr + " §fpièces d'or !";
			text += "\n" + "§aVous regagnez votre vie";
			controleur.remplirVieHeros();
			this.heros.ajouterPieceOr(pieceOr);
		}
		return text;
	}

	public String attaqueMonstre(int num) {
		int degatsInflige = this.controleur.getCombatActuel().monstreAttaque(num);
		return String.valueOf(degatsInflige);
	}

	public List<String> attaqueMonstres() {
		List<String> display = new ArrayList<>();
		for (int i = 0; i < nombreMonstre(); i++) {
			Monstre m = combat().getMonstres().get(i);
			display
				.add(
					"§e->§f Le " + m.getNom() + " vous a attaqué et vous a infligé §c" + attaqueMonstre(i)
						+ " points de dégats§f.");
			display.add("§fVous avez maintenant §a"+this.heros.getPtsVie()+" points de vie");
		}
		return display;
	}

	public int nombreMonstre() {
		return this.controleur.getCombatActuel().getNombreMonstres();
	}

	public String getNomMonstre(int i) {
		return this.controleur.getCombatActuel().getMonstres().get(i).getNom();
	}

	public List<String> getMonstres(boolean inclureIndex) {
		List<String> affichage = new ArrayList<>();
		int index = 0;
		for (Monstre monstre : this.controleur.getCombatActuel().getMonstres()) {
			if (inclureIndex) {
				affichage
					.add(
						"§f - (" + index + ") §e" + monstre.getNom() + " §f| Dégats: §c" + monstre.getPtsForce() + "§f, Vie: §a"
							+ monstre.getPtsVie());
			} else {
				affichage
					.add(
						"§f - §e" + monstre.getNom() + " §f| Dégats: §c" + monstre.getPtsForce() + "§f, Vie: §a"
							+ monstre.getPtsVie());
			}
			index++;
		}
		return affichage;
	}

}
