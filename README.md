# Obligatorisk oppgave 3 i Algoritmer og Datastrukturer
Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende student:
* Navn Hadil Alshihabi, S362080, s36208@oslomet.no


# Oppgavebeskrivelse
I oppgave 1 så brukte jeg to nodereferanser r og n. Referansen n starter i rotnoden. Den flyttes så nedover i treet - 
til venstre når verdi er mindre enn nodeverdien og til høyre ellers. Også bruker vi  compare-metoden for å sammenligning.


I oppgave 2 så brukte jeg reaksjon for å utføre oppgaven men den passerte aldri ved testen. Derfor fikk jeg laget en hjelp- 
metodet som er mer kompakt. Jeg vet det er vanskjelig å forstå og den er ikke relvant i vårt oblig men jeg fikk skrevet den 
ved hjelp fra en studentassistent. 

I oppgave 3 brukte jeg Programkode 5.1.7 fra kompendiet for å løse oppgaven. Jeg brukte if-setening og while-løkka for 
å retunere første node i postorden med r som rot. 

I oppgave 4 har jeg brukt funksjonen nestePostorden fra forrige oppgave hvor jeg finner den første noden r i postorden. 
Jeg har brukt en While-løkka kall for r = nestePostorden(r); hvor den gir den neste osv. til r blir null. 

I opgave 5 bruker jeg en serialize-metoden og en kø til å traversere treet. Deserialize funksjoner tar imot arrayet og 
legger inn verdiene om igjen i nivå orden. 

I oppgave 6 bruker jeg tre funksjoner. I fjern metoden bruker jeg til å retunere false dersom treet har ingen nullverdier og sammenlinger vi.
I fjernAlle metoden bruker jeg if-setning for å sjekke om treet ikke er tomt og bruker en while-løkke for å returnere antallet forekomster. 
Til slutt bruker jeg nullstill funksjonen for å sette rot til null og antall til 0.


