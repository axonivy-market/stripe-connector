# Stripe Connector

**Stripe** is a comprehensive **payment processing platform** that enables
businesses to accept **online and in-person payments**. It provides a powerful
API to integrate various payment methods, including **credit/debit cards,
digital wallets, bank transfers**, and more.

We provide a **connector** that seamlessly integrates Stripe's functionality
into a **business process**, enabling automated and efficient payment handling
within Axon Ivy.


### 1. Generating a Payment L ink
 * Weiter basisbezogen **priceId** und **Menge**, eine **Abzahlung Band** kann
   sein generiert.
 * Dieses Band leitet um den Nutzer zu das festes **Streifen Abzahlung Seite**,
   #wo kann die Abzahlung sein vervollständigt.

### 2. #Eingraben Streifen Abzahlung Form
 * Die Streifen Abzahlung Form kann sein #eingraben direkt hinein die **#Axon
   Efeu UI**.
 * Dies erlaubt Abzahlungen zu sein verarbeitet **innerhalb den Antrag** ohne
   #umleiten zu #Streiften.

## Was ist ein Abzahlung Band?
Ein **Abzahlung Band** ist ein URL generiert bei #Streiften jener erlaubt
Kundinnen zu machen direkt eine Abzahlung. Es zügelt #vordefiniert **Preise und
Mengen** und aktivieren nahtlose Abzahlung verarbeiten durch verschiedene
Methoden.


## Was ist das priceId?
Das **priceId** ist eine einmalige Bezeichnung für einen Preis in #Streiften. Es
ist automatisch #wann generiert einen Preis ist geschafft herein für ein Produkt
das **Streifen Armaturenbrett**. Dies **priceId** ist benutzt zu richtig
assoziieren Abzahlungen und können sein genutzt via die API oder #wann schaffend
ein Abzahlung Band.



## Demo

### Nutzung Fall: Schaff ein paymentLink basisbezogen auf Menge und priceId (bitte folgen Installation Fremdenführer zu bekommen den priceId)
![](images/create_paymentLink.png)

#### Wir können dieses Band benutzen zu umleiten zu die Streifen Abzahlung Website und machen eine Abzahlung.
![](images/redirect_to_paymentLink.png)

### Nutzung Fall: #Eingraben das Streifen Austesten Session Seite hinein die Form.

- Bitte #einlesen den Preis ID und Menge, dann unterzieht Klick.
- Die Streifen Abzahlung Austesten Session Form will sein #ausschmelzen unten.
  ![](images/create_embed_checkout_session.png)

- Nach füllen #hinaus die Abzahlung Form und klickend "Abonniert," die Form will
  sein automatisch zu einer erfolgreichen Mitteilung umgeleitet Seite.
  ![](images/return_successful_page.png)

## Einrichtung
1. Schaff ein **Konto**: **[Hier](https://stripe.com/en-de) **
2. Geh zu dem Armaturenbrett und Suche **Entwickler** ->**API Schlüssel** und
   Schaffen **Geheimen Schlüssel und publishable Schlüssel**
   ![](images/create_api_keys.png)
3. Füg zu neue Produkte und setzen ihre Preise ![](images/Create_products.png)
   ![](images/add_product.png)
4. Greif zu die Produkte zu bekommen den Preis ID von diesem Produkt.
   ![](images/go_to_product_get_priceId.png)
  - Du kannst kopieren das priceId von hier ![](images/copy_priceId.png)
5. Öffne das `Konfiguration/Variablen.yaml` In eurem Designer und setzen dem
   secretKey und PublishableKey
6. Speicher die #abgeändert Lagen und starten einen Demo Arbeitsgang

```
@variables.yaml@
```
