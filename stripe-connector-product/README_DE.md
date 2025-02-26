# Stripe Connector

**Stripe** ist eine mächtige **Zahlungsplattform**, die es Unternehmen ermöglicht, **Online- und Offline-Zahlungen** zu akzeptieren. Die Plattform bietet eine leistungsfähige API zur Integration verschiedener Zahlungsmethoden, darunter **Kredit-/Debitkarten, digitale Wallets, Banküberweisungen** und mehr.

Wir stellen einen **Connector** zur Verfügung, der die Funktionalität von Stripe nahtlos in einen **Axon Ivy Geschäftsprozess** integriert und eine automatisierte sowie effiziente Zahlungsabwicklung innerhalb von Axon Ivy Anwendungen ermöglicht.


### 1. Generierung eines Payment Links
 * Basierend auf **priceId** und **Menge** kann ein **Zahlungslink** generiert werden.
 * Dieser Link leitet den Benutzer zur sicheren **Stripe-Zahlungsseite** weiter, wo die Zahlung abgeschlossen werden kann.

### 2. Eingebettetes Stripe-Zahlungsformular
 * Die Stripe-Zahlungsmaske kann direkt in die **UI von Axon Ivy** eingebunden werden.
 * Dadurch können Zahlungen **innerhalb der Anwendung** durchgeführt werden, ohne zu Stripe weitergeleitet zu werden.


## Was ist ein Payment Link?
Ein **Payment Link** ist eine von Stripe generierte URL, über die Kunden direkt eine Zahlung vornehmen können. Er enthält vordefinierte **Preise und Mengen** und ermöglicht eine einfache Zahlungsabwicklung über verschiedene Methoden.


## Was ist die priceId?
Die **priceId** ist eine eindeutige Kennung für einen Preis in Stripe. Sie wird automatisch generiert, wenn ein Preis für ein Produkt im **Stripe Dashboard** erstellt wird. Diese **priceId** wird verwendet, um Zahlungen korrekt zuzuordnen und kann in der API oder beim Erstellen eines Payment Links genutzt werden.  
