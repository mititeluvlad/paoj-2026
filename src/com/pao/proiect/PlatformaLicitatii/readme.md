# Platforma de Licitatii

## 1.1 - Actiuni / interogari posibile in sistem

1. Adauga utilizator (client sau administrator)
2. Login utilizator
3. Afiseaza toti utilizatorii
4. Creeaza licitatie
5. Afiseaza toate licitatiile
6. Adauga oferta la o licitatie
7. Afiseaza istoricul personal al ofertelor
8. Aproba o oferta
9. Inchide licitatia si genereaza tranzactie
10. Afiseaza tranzactii sortate
11. Cauta licitatii dupa numele produsului
12. Afiseaza licitatii sortate crescator dupa pret
13. Afiseaza clientii sortati dupa username
14. Afiseaza administratorii filtrati dupa departament

---

## 1.2 - Tipuri de obiecte din domeniu

- Utilizator
- Client
- Administrator
- Licitatie
- Oferta
- Produs
- CategorieProdus
- Tranzactie

---

## 2 - Implementare Java

### Clase principale
- Utilizator
- Client
- Administrator
- Licitatie
- Oferta
- Produs
- CategorieProdus
- Tranzactie

### Structura OOP
- Mostenire: Utilizator -> Client / Administrator
- Clasa abstracta: Utilizator
- Clasa imutabila: CategorieProdus / Tranzactie 

### Colectii
- List (oferte, tranzactii)
- Map (licitatii, istoric personal)
- Sortari cu Comparable si Comparator

### Exceptii custom
- LicitatieInchisaException
- OfertaPreaMicaException
- OfertaInexistentaException
- NuExistaOferteAprobateException

### Servicii
- UtilizatorService (Singleton)
- LicitatieService (Singleton)