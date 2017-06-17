# TripApp (FTN-PMA-E2-17)

# Opšte o aplikaciji #
  Aplikacija služi za kupovinu i validaciju karata za gradski saobraćajni prevoz. Putnici, koji poseduju naloge, putem mobilnog telefona   kupuju karte, koje mogu biti različitih vrsta, odnosno vremenskog ograničenja ( 1 sat, 1 dan, 7 dana, 30 dana). Njima se potom skidaju   sredstva sa naloga i dobijaju kartu sa QR kodom.

## Putnici ##
 
   Putnici kreiraju nalog, dopunjavaju sredstva na svom racunu skeniranjem QR kodova za dopunu i kupuju karte za vožnju.

 ## Kontrolori ##
 
  Kontrolori kamerom skeniraju karte (QR kodove) putnicima i proveravaju validnost istih. Ukoliko je karta nevalidna, kontrolor dobija     odgovarajuci ispis i/ili zvučni signal. Kontrolor može da pregleda istoriju kontrola koje je izvršio.

 ## Administratori ##
 
  Upravljaju serverskim delom aplikacije i to tako što:
  - Upravljaju nalozima kontrolera i po potrebi putnika.
  - Upravljaju linijama prevoza i redom vožnje.

 ## Mapa linija ##
 
  Aplikacija sadrži i pregled svih linija prevoza na mapi. Postoji mogućnost pregleda pojedinačnih linija na mapi.

 ## Notifikacije ##
 
  - Aplikacija šalje notifikacije 5 minuta pre isteka aktivne karte u trajanju od 1 sat.

# Uloge #

## Putnik ##
  - Može da se uloguje i/ili kreira novi nalog.
  - Može da dopuni stanje pomoću skeniranja QR koda za dopunu.
  - Može da kupuje različite tipove karata.
## Kontrolor ##
  - Može da izvrši kontrolu karata skeniranje QR koda kamerom telefona.
  - Može da vrši uvid u istoriju kontrola koje je uradio.
## Admin ##
  - Može da administrira druge korisnike na web delu sistema.
  - Može da menja linije i red vožnje.

# Delovi Aplikacije #
## Master view ##

- Za putnika -  spisak tipova karata
- Za kontrolora - opcija za skeniranje karata, spisak ocitanih karata

## Detail view ##

- Karta detaljno

## Settings ##

- Ukljucivanje/iskljucivanje notifikacija i podešavanje zvuka i vibracije
- Send usage statistics
- Koliko unazad da pamti karte

## About ##

 Verzija aplikacije

# Skica aplikacije #
![Skica 01](https://github.com/sergiosuperstar/FTN-PMA-E2-17/blob/master/Documentation/Images/Android%20App%20-%20Skica%20%231.png "skica 01")

# Model podataka #
![Model podataka](https://github.com/sergiosuperstar/FTN-PMA-E2-17/blob/master/Documentation/Images/android-model.JPG "Model podataka")
