# EconomyBridgeV2

Bridge entre QuickShop et Skript pour économie dynamique.

## Fonction

- Détecte les achats QuickShop
- Enregistre dans data.yml
- Skript peut lire les ventes
- Permet marché dynamique

## Installation

1. Mettre QuickShop.jar dans /libs
2. Compiler avec Java 21
3. Mettre EconomyBridgeV2.jar dans /plugins
4. Redémarrer serveur

## Fichier généré

plugins/EconomyBridgeV2/data.yml

## Exemple Skript

every 2 seconds:
    set {_item} to yaml value "last.item" from file "plugins/EconomyBridgeV2/data.yml"
    set {_amount} to yaml value "last.amount" from file "plugins/EconomyBridgeV2/data.yml"

    if {_item} is not "NONE":
        broadcast "Achat %{_item}% x%{_amount}%"