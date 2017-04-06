# TripApp (FTN-PMA-E2-17)

#Opšte o aplikaciji#

 ##Putnici##
 
  Aplikacija služi za kupovinu i validaciju karata za gradski saobraćajni prevoz. Putnici, koji poseduju naloge, putem mobilnog telefona   kupuju karte, koje mogu biti različitih vrsta, odnosno vremenskog ograničenja ( 1 sat, 1 dan, 7 dana, 30 dana). Njima se potom skidaju   sredstva sa naloga i dobijaju kartu sa QR kodom. Putnici mogu da dopune sredstva kupovinom kartice za dopunu sa QR kodom i skeniranjem   iste.

 1.2. Kontrolori
 
  Kontrolori kamerom skeniraju karte (QR kodove) putnicima i proveravaju validnost istih. Ukoliko je karta nevalidna, kontrolor dobija     odgovarajuci ispis i/ili zvučni signal.

 1.3. Administratori
 
  Upravljaju nalozima kontrolera i po potrebi putnika.
  Upravljaju linijama prevoza i redom vožnje.

 1.4. Linije i red vožnje
 
  Aplikacija sadrži i pregled svih linija prevoza na mapi. Postoji mogućnost pregleda pojedinačnih linija na mapi.
  Aplikacija sadrži red vožnje po linijama sa vremenskom tabelom polazaka.

 1.5. Notifikacije
 
  Aplikacija šalje notifikacije kada je karta postala aktivna (5 minuta posle kupovine, da bi se izbegla kupovina kada počne kontrola).
  Aplikacija šalje notifikacije 10 minuta pre isteka aktivne karte u trajanju od 1 sat.
  Aplikacija šalje notifikacije 1 sat pre isteka dnevne karte.
  Aplikacija šalje notifikacije 1 dan pre isteka nedeljne karte.
  Aplikacija šalje notifikacije 3 dana pre isteka mesečne karte.

2. Uloge:

- Putnik
  Može da se uloguje i/ili kreira novi nalog.
  Može da dopuni stanje pomoću skeniranja QR koda za dopunu.
  Može da kupuje različite tipove karata.
- Kontrolor
  Može da izvrši kontrolu karata skeniranje QR koda kamerom telefona.
  Može da vrši uvid u istoriju kontrola koje je uradio.
- Admin 
  Može da administrira druge korisnike na web delu sistema.
  Može da menja linije i red vožnje.

3. Master view :

- Za putnika -  spisak tipova karata
- Za kontrolora - opcija za skeniranje karata, spisak ocitanih karata

4. Detail view:

- Putnik
- Kontrolor
- Karta detaljno

5. Settings:

- 
- Menjanje formata vremena i datuma
- Menjanje redosleda karata?
- Ukljucivanje/iskljucivanje zvucnih signala (npr. Za neispravnu kartu)
- Send usage statistics
- Koliko unazad da pamti karte
- Remember me

6. About

 Verzija aplikacije i slicno



7. Dodatni predlozi:

 Uplaćivanje e-novca za karte (skidanjem kredita/povećavanjem racuna)


8. Skica aplikacije
![Skica 01](https://github.com/sergiosuperstar/FTN-PMA-E2-17/blob/master/Documentation/Images/Android%20App%20-%20Skica%20%231.png "skica 01")
